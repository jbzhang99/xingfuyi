<#include "/h5/commons/_head.ftl" />
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">购物车</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div style="padding-bottom:60px;" id="cartListDiv">
		<#include "/h5/cart/cartlist.ftl" />
    </div>
	<!-- 主体结束 -->

	<!-- 合计层 -->
	<#if (cartInfoVO.cartListVOs??) && (cartInfoVO.cartListVOs?size &gt; 0) >
	<div class="totallayer">
		<div class="flex flex-align-center" style="height:100%; position:absolute; bottom:0; left:0; width:100%;">
			<div class="pad10">
				<input type='checkbox' id="checkAllFoot" onchange="checkedChangeAll(this)" autocomplete="off" <#if cartInfoVO.totalNumber == cartInfoVO.totalCheckedNumber>checked="checked"</#if>>
				&nbsp;全选
			</div>
			<div class="padt_b10 flex-1">
			   <span class="font16">合计<font id="cartAmountFont">¥${cartInfoVO.checkedDiscountedCartAmount!'0.00'}</font></span>
			 </div>
			 <div class="flex-1 go-pay font16">
			 	<a href="javascript:;" class="block" onclick="toOrder()">
			 		去结算<font id="totalNumberFont">(${cartInfoVO.totalCheckedNumber!0})</font>
			 	</a>
			 </div>
		</div>
	</div>
	</#if>
	
	<!-- footer -->
	<#include "/h5/commons/_statistic.ftl" />

<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/index.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/common.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.hDialog.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/xback.js"></script>
<script>
	$(document).ready(function () {
		
		$("#addToCart").click(function(){
			//未登录不能添加购物车
			if (!isUserLogin()) {
				// 未登录跳转到登陆页面
				var toUrl = domain + "/product/${(productId)!0}.html?goodId=" + $("#productGoodsId").val();
				window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
				return;
			}
		});
		
	});
	
	function cartminus(obj) {
		var numberObj = $(obj).parent().children("#number");
		var number = parseInt(numberObj.val(), 10);
		if (number <= 1) {
			number = 1;
		} else {
			number--;
		}
		numberObj.val(number);
		var cartId = $(obj).parent().children("#cartId").val();
		updateSingle(cartId, number);
		getNewCartInfo();
	}

	function cartplus(obj) {
		var numberObj = $(obj).parent().children("#number");
		var number = parseInt(numberObj.val(), 10);
		var productStock = parseInt($(obj).parent().children("#productStock").val());
		if (number >= productStock) {
			number = productStock;
		} else {
			number++;
		}
		numberObj.val(number);
		var cartId = $(obj).parent().children("#cartId").val();
		updateSingle(cartId, number);
		getNewCartInfo();
	}
	
	// 数量输入框失去焦点
	function modify(obj) {
		var number = parseInt($(obj).val(), 10);
		var productStock = $(obj).parent().children("#productStock").val();
		if (number == null || parseInt(number) < 1) {
			number = 1;
		} else {
			if (number > parseInt(productStock)) {
				number = parseInt(productStock);
			}
		}
		$(obj).val(number);
		var cartId = $(obj).parent().children("#cartId").val();
		updateSingle(cartId, number);
		getNewCartInfo();
	}
	
	//更新购物车某某件商品的数量
	function updateSingle(id,count){
		$.ajax({
			type : "POST",
			url :  domain+"/cart/updateCartById.html",
			data : {id:id,count:count},
			dataType : "json",
			async:false,
			success : function(data) {
			},
			error : function() {
				// alert("数据加载失败！");
		    	$.dialog('alert','提示','数据加载失败',2000);
			}
		});
	}
	
	// 异步加载购物车信息
	function getNewCartInfo(){
		$.ajax({
			type : "POST",
			url  : domain+"/cart/getcartinfo.html?rd=" + Math.random(),
			async:false,
			success : function(data) {
				$("#cartListDiv").empty();
				$("#cartListDiv").append(data);
				
				$("#cartAmountFont").html("￥" + $("#cartAmount").val());
				$("#totalNumberFont").html("(" + $("#totalCheckedNumber").val() + ")");
				
				var checkedNum = parseInt($("#totalCheckedNumber").val());
				var totalNum = parseInt($("#totalNumber").val());
				if (checkedNum != null && checkedNum > 0 && totalNum != null && totalNum > 0 && checkedNum == totalNum) {
					$("#checkAllFoot").prop("checked", true);
				} else {
					$("#checkAllFoot").prop("checked", false);
				}
			}
		});
	}
	
	// 删除购物车数据
	function deleteCart(cartId) {
		/* if(confirm("是否确定删除!")){
			$.ajax({
				type : "GET",
				url :  domain+"/cart/deleteCartById.html",
				data : {id:cartId},
				dataType : "json",
				success : function(data) {
					if(data.success){
						getNewCartInfo();
					}else {
						// alert(data.message);
				    	$.dialog('alert','提示',data.message,2000);
					}
				}
			});	
		} */
		$.dialog('confirm','提示','是否确定删除!',0,function(){
			$.closeDialog();
			$.ajax({
				type : "GET",
				url :  domain+"/cart/deleteCartById.html",
				data : {id:cartId},
				dataType : "json",
				success : function(data) {
					if(data.success){
						getNewCartInfo();
					}else {
						// alert(data.message);
				    	$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		});
	}
	
	//选中
	function checkedChange(obj){
		var checked = 0;
		if ($(obj).prop('checked')) {
			checked = 1;
		}
		var id = $(obj).val();
		$.ajax({
			type : "GET",
			url :  domain+"/cart/cartchecked.html",
			data : {id:id,checked:checked},
			dataType : "json",
			success : function(data) {
				if(data.success){
					//重新加载单品信息
					getNewCartInfo();
				}else {
					$.dialog('alert','提示',data.message,2000);
				}
			},
			error : function() {
				$.dialog('alert','提示','数据加载失败',2000);
			}
		});	
	}
	
	//全部选中
	function checkedChangeAll(obj){
		var checked = 0;
		if ($(obj).prop('checked')) {
			checked = 1;
		}
		$.ajax({
			type : "GET",
			url :  domain+"/cart/cartcheckedall.html",
			data : {checked:checked},
			dataType : "json",
			success : function(data) {
				if(data.success){
					//重新加载单品信息
					getNewCartInfo();
				}else {
					$.dialog('alert','提示',data.message,2000);
				}
			},
			error : function() {
				$.dialog('alert','提示','数据加载失败',2000);
			}
		});	
	}
	
	//去结算
	function toOrder(){
		var judge = false ; 
		$("[name='checkItem']").each(function(){
		    if($(this).attr("checked")){
		    	judge=true;
		   }
		});
		if(judge){
			window.location.href = domain+"/order/info.html";
		}else{
			$.dialog('alert','提示','请选择需要结算的商品',2000);
		}
	}
</script>
<script src="${(domainUrlUtil.staticResources)!}/js/_pageBackStack.js"></script>
</body>
</html>