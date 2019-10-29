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
         <div class="flex-1 text-right" id="manage" >管理</div>

   	  	 <#--<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>-->
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
			<div class="flex-1">
				<#--<input type='checkbox' id="checkAllFoot" onchange="checkedChangeAll(this)" autocomplete="off" <#if cartInfoVO.totalNumber == cartInfoVO.totalCheckedNumber>checked="checked"</#if>>-->
				<#--&nbsp;全选-->
                    <#if cartInfoVO.totalNumber == cartInfoVO.totalCheckedNumber>
                        <span style="margin-left: 16px;" id="checkAllFoot" onclick="checkedChangeAll(this)"  class="selectCheckboxY"></span> <span style="font-size: 16px;float: left">全选</span>

                    <#else>
                        <span style="margin-left: 16px;" id="checkAllFoot" onclick="checkedChangeAll(this)"  class="selectCheckboxN"></span> <span style="font-size: 16px;float: left">全选</span>
                    </#if>



			</div>
            <div class="flex-2 flex-align-center" style="display: flex;">
                <div class="padt_b10 flex-1 manage" style="text-align: right;font-family:PingFangSC-Regular,PingFangSC;font-weight:400;">
                   <span class="font16">合计:<font id="cartAmountFont" style="font-family:PingFangSC-Regular,PingFangSC;font-weight:400;color:#E7250C;">&nbsp; ¥${cartInfoVO.checkedDiscountedCartAmount!'0.00'}</font></span>
                </div>
                <div class="flex-1  font16 manage">
                    <a href="javascript:;" class="block" onclick="toOrder()" style="height: 45px;margin: 15px;background:linear-gradient(90deg,rgba(231,37,12,1) 0%,rgba(254,117,101,1) 100%);border-radius: 30px;line-height: 45px;color: #ffffff;">
                        去结算<font id="totalNumberFont">(${cartInfoVO.totalCheckedNumber!0})</font>
                    </a>
                </div>
                <div class="padt_b10 flex-1 complete" style="text-align: right;font-family:PingFangSC-Regular,PingFangSC;font-weight:400;">
                    &nbsp;
                </div>
                <div class="flex-1  font16 complete"  style="height: 30px;margin: 15px;border:1px solid rgba(231,37,12,1); border-radius: 30px;line-height: 30px;color: rgba(231,37,12,1);" onclick="deleteCart()">
                    删除
                </div>





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
//					$("#checkAllFoot").prop("checked", true);
                    $("#checkAllFoot").removeClass("selectCheckboxN").addClass("selectCheckboxY");
				} else {
//					$("#checkAllFoot").prop("checked", false);
                    $("#checkAllFoot").removeClass("selectCheckboxY").addClass("selectCheckboxN");
				}
			}
		});
	}
	// 删除购物车数据
	function deleteCart() {
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
//		$.dialog('confirm','提示','是否确定删除!',0,function(){
//			$.closeDialog();
//			$.ajax({
//				type : "GET",
//				url :  domain+"/cart/deleteCartById.html",
//				data : {id:cartId},
//				dataType : "json",
//				success : function(data) {
//					if(data.success){
//						getNewCartInfo();
//					}else {
//						// alert(data.message);
//				    	$.dialog('alert','提示',data.message,2000);
//					}
//				}
//			});
//		});
        var judge = false ;
        var ids  = [];
        $("[name='checkItem']").each(function(){
            if($(this).attr("class")=="selectCheckboxY"){
                judge=true;
                ids.push(parseInt($(this).attr("value")))
            }

        });
        if(judge){
            $.dialog('confirm','提示','是否确定删除!',0,function(){
                $.closeDialog();
                $.ajax({
                    type : "post",
                    url :  domain+"/cart/delCartByIds",
                    data : JSON.stringify(ids),
                    dataType : "json",
                    contentType:"application/json",
                    success : function(data) {
                        if(data.success){
                            getNewCartInfo();
                        }else {
                            $.dialog('alert','提示',data.message,2000);
                        }
                    }
                });
		    });
        }else{
            layer.open({content: '请选择商品',skin: 'msg',time: 2 });
        }


	}

	//选中
	function checkedChange(obj){
		var checked = 0;
//		if ($(obj).prop('checked')) {
//			checked = 1;
//		}
        var className = String($(obj).attr("class"));
        if(className=="selectCheckboxN"){//body没有类名
            $(obj).removeClass("selectCheckboxN").addClass("selectCheckboxY");
            checked = 1;
        }else {
            $(obj).removeClass("selectCheckboxY").addClass("selectCheckboxN");
            checked = 0;
        }

        var goods = $(obj).closest(".one-shop").find(".oder-list"); //获取本店铺的所有商品
        var goodsC = $(obj).closest(".one-shop").find(".selectCheckboxY"); //获取本店铺所有被选中的商品
        var Shops = $(obj).closest(".one-shop").find("#ShopCheck"); //获取店铺

        if(goods.length==goodsC.length){
            Shops.removeClass("selectShopCheckboxN").addClass("selectShopCheckboxY");
        }else{
            Shops.removeClass("selectShopCheckboxY").addClass("selectShopCheckboxN");
        }

        var ShopNum = $(obj).closest("#cartListDiv").find(".one-shop"); //获取店铺
        var ShopCheckedNum = $(obj).closest("#cartListDiv").find(".selectShopCheckboxY"); //获取店铺被选中
        if(ShopNum.length == ShopCheckedNum.length){
            $("#checkAllFoot").removeClass("selectCheckboxN").addClass("selectCheckboxY");
        }else{
            $("#checkAllFoot").removeClass("selectCheckboxY").addClass("selectCheckboxN");
        }



		var id = $(obj).attr("value");
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
    function checkedShop(obj) {
        var className = String($(obj).attr("class"));
        if(className=="selectShopCheckboxN"){//body没有类名
            $(obj).removeClass("selectShopCheckboxN").addClass("selectShopCheckboxY");
            $(obj).closest(".one-shop").find(".checksty span").removeClass("selectCheckboxN").addClass("selectCheckboxY");
        }else {
            $(obj).removeClass("selectShopCheckboxY").addClass("selectShopCheckboxN");
            $(obj).closest(".one-shop").find(".checksty span").removeClass("selectCheckboxY").addClass("selectCheckboxN");

        }

        var ShopNum = $(obj).closest("#cartListDiv").find(".one-shop"); //获取店铺
        var ShopCheckedNum = $(obj).closest("#cartListDiv").find(".selectShopCheckboxY"); //获取店铺被选中
        if(ShopNum.length == ShopCheckedNum.length){
            $("#checkAllFoot").removeClass("selectCheckboxN").addClass("selectCheckboxY");
        }else{
            $("#checkAllFoot").removeClass("selectCheckboxY").addClass("selectCheckboxN");
        }
    }
	//全部选中
	function checkedChangeAll(obj){
        var className = String($(obj).attr("class"));
        var checked = 0;
        if(className=="selectCheckboxN"){//body没有类名
            $(obj).removeClass("selectCheckboxN").addClass("selectCheckboxY");
            $("#cartListDiv").find(".checksty span").removeClass("selectCheckboxN").addClass("selectCheckboxY");
            $("#cartListDiv .one-shop").find("#ShopCheck").removeClass("selectShopCheckboxN").addClass("selectShopCheckboxY");
            checked = 1;

        }else {
            $(obj).removeClass("selectCheckboxY").addClass("selectCheckboxN");
            $("#cartListDiv").find(".checksty span").removeClass("selectCheckboxY").addClass("selectCheckboxN");
            $("#cartListDiv .one-shop").find("#ShopCheck").removeClass("selectShopCheckboxY").addClass("selectShopCheckboxN");
            checked = 0;
        }



//		if ($(obj).prop('checked')) {
//			checked = 1;
//		}
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
		    if($(this).attr("class")=="selectCheckboxY"){
		    	judge=true;
		   }
		});
		if(judge){
			window.location.href = domain+"/order/info.html";
		}else{
			$.dialog('alert','提示','请选择需要结算的商品',2000);
		}
	}

	$("#manage").click(function(){
        //未登录不能添加购物车
        if ($("#manage").text()=="管理") {
            $("#manage").text("完成");
            $(".manage").hide()
            $(".complete").show()
        }else{
            $("#manage").text("管理");
            $(".manage").show()
            $(".complete").hide()
        }
    });

</script>
<script src="${(domainUrlUtil.staticResources)!}/js/_pageBackStack.js"></script>
</body>
</html>
