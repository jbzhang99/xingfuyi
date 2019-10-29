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
   	  	 <div class="flex-2 text-center">我的收藏</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	<div class="flex flex-align-center goods-nav" id="colect_nav">
		<div class="flex-1 <#if type?? && type == 'product' > clr53 </#if>" id="productNumberDiv">关注商品(${productCount!"0"})</div>
		<div class="flex-1 <#if type?? && type == 'seller' > clr53 </#if>" id="sellerNumberDiv">关注店铺(${sellerCount!"0"})</div>
	</div>
	<input type="hidden" id="pageSize" value="${pageSize!'5'}"/>
	<div class="s-container" id="container">
		<div id="productDiv" class="collect-items" <#if type?? && type != 'product' >style="display:none;"</#if>>
			<input type="hidden" id="productCount" value="${productCount!'0'}"/>
    		<input type="hidden" id="productPageIndex" value="1"/>
    		<#include "/h5/member/collect/collectproductlist.ftl" />
		</div>
  		<div <#if type?? && type != 'seller' >style="display:none;"</#if> id="sellerDiv" class="collect-items">
  			<input type="hidden" id="sellerCount" value="${sellerCount!'0'}"/>
    		<input type="hidden" id="sellerPageIndex" value="1"/>
    		<#include "/h5/member/collect/collectsellerlist.ftl" />
  		</div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		// 总数
		var productCount = parseInt($("#productCount").val());
		// 当前加载的页数
		var productPageIndex = parseInt($("#productPageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (productCount <= (pageSize * productPageIndex)) {
			$("#addMoreProductDiv").hide();
			$("#noMoreProductDiv").show();
		}
		
		// 总数
		var sellerCount = parseInt($("#sellerCount").val());
		// 当前加载的页数
		var sellerPageIndex = parseInt($("#sellerPageIndex").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (sellerCount <= (pageSize * sellerPageIndex)) {
			$("#addMoreSellerDiv").hide();
			$("#noMoreSellerDiv").show();
		}
	});
	
	// 取消关注商品
	function cancelCollectProduct(obj, productId) {
		/* if(confirm("是否取消关注")){
			$.ajax({
				type:"GET",
				url:domain+"/member/cancelcollectproduct.html",
				dataType:"json",
				async : false,
				data : {productId:productId},
				success:function(data){
					if(data.success){
						// $(obj).parents(".pos_relative").remove();
						// var number = parseInt($("#productCount").val()) - 1;
						// $("#productNumberDiv").html("关注商品(" + number + ")");
						// $("#productCount").val(number);
						window.location.href=domain+"/member/collect.html?type=product";
					}else{
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		} */
		$.dialog('confirm','提示','是否取消关注!',0,function(){
			$.closeDialog();
			$.ajax({
				type:"GET",
				url:domain+"/member/cancelcollectproduct.html",
				dataType:"json",
				async : false,
				data : {productId:productId},
				success:function(data){
					if(data.success){
						window.location.href=domain+"/member/collect.html?type=product";
					}else{
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		});
	}
	
	// 取消关注店铺
	function cancelCollectSeller(obj, sellerId) {
		/* if(confirm("是否取消关注")){
			$.ajax({
				type:"GET",
				url:domain+"/member/cancelcollectshop.html",
				dataType:"json",
				async : false,
				data : {sellerId:sellerId},
				success:function(data){
					if(data.success){
						// $(obj).parents(".pos_relative").remove();
						// var number = parseInt($("#sellerCount").val()) - 1;
						// $("#sellerNumberDiv").html("关注店铺(" + number + ")");
						// $("#sellerCount").val(number);
						window.location.href=domain+"/member/collect.html?type=seller";
					}else{
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		} */
		$.dialog('confirm','提示','是否取消关注!',0,function(){
			$.closeDialog();
			$.ajax({
				type:"GET",
				url:domain+"/member/cancelcollectshop.html",
				dataType:"json",
				async : false,
				data : {sellerId:sellerId},
				success:function(data){
					if(data.success){
						window.location.href=domain+"/member/collect.html?type=seller";
					}else{
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		});
	}
	
	// 加载更多收藏商品
	function addMoreProductCollect() {
		// 总数
		var productCount = parseInt($("#productCount").val());
		// 当前加载的页数
		var productPageIndex = parseInt($("#productPageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (productCount <= (pageSize * productPageIndex)) {
			$("#addMoreProductDiv").hide();
			$("#noMoreProductDiv").show();
			return;
		}
		$("#addMoreProductDiv").remove();
		$("#noMoreProductDiv").remove();
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/morecollectproduct.html",
			dataType:"html",
			data:{pageIndex:productPageIndex+1},
			success:function(data){
				//加载数据
				$("#productDiv").append(data);
				// 页码加1
				$("#productPageIndex").val(productPageIndex + 1)
				if (productCount <= (pageSize * (productPageIndex+1))) {
					$("#addMoreProductDiv").hide();
					$("#noMoreProductDiv").show();
					return;
				}
			}
		});
	}
	
	// 加载更多收藏店铺
	function addMoreSellerCollect() {
		// 总数
		var sellerCount = parseInt($("#sellerCount").val());
		// 当前加载的页数
		var sellerPageIndex = parseInt($("#sellerPageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (sellerCount <= (pageSize * sellerPageIndex)) {
			$("#addMoreSellerDiv").hide();
			$("#noMoreSellerDiv").show();
			return;
		}
		$("#addMoreSellerDiv").remove();
		$("#noMoreSellerDiv").remove();
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/morecollectseller.html",
			dataType:"html",
			data:{pageIndex:sellerPageIndex+1},
			success:function(data){
				//加载数据
				$("#sellerDiv").append(data);
				// 页码加1
				$("#sellerPageIndex").val(sellerPageIndex + 1)
				if (sellerCount <= (pageSize * (sellerPageIndex+1))) {
					$("#addMoreSellerDiv").hide();
					$("#noMoreSellerDiv").show();
					return;
				}
			}
		});
	}
</script>

</body>
</html>