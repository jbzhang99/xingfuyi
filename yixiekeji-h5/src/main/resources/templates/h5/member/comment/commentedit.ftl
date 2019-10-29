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
   	  	 <div class="flex-2 text-center">我要评价</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class=""  >
	    <#if order?? && order.orderProductList?? && order.orderProductList?size &gt; 0 >
		<#list  order.orderProductList as product>
	    <div class="mar-bt pos_relative">
	    	<dl class="flex list-dl">
	    		<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html?goodId=${(product.productGoodsId)!0}' class="block">
	    			<dt style="width:80px; height:80px;"><img src="${(domainUrlUtil.imageResources)!}${product.productLeadLittle}"></dt>
	    		</a>
	    		<dd class="padl flex-2">
	    			<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html?goodId=${(product.productGoodsId)!0}' class="block">
	    				<div class="product_name mar_top">${(product.productName)!''}&nbsp;${(product.specInfo)!''}</div>
	    			</a>
	    			<div class="flex flex-pack-justify goods-sun">
	    			<div>购买时间：<font>${(order.createTime?string("yyyy-MM-dd"))!''}</font></div>
	    			<div><a href="javascript:;" class="cla4" onclick="addComment(this,'${(order.orderSn)!0}','${(product.productId)!0}','${(product.productGoodsId)!0}','${(product.id)!0}')">我要评价</a></div>
	    			</div>
	    		</dd>
	    	</dl>

	    	<!-- 评价框 -->
	    	<div class="commentDetailDiv" style="display:none"></div>
	    </div>
	    </#list>
		</#if>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		
	});

	function addComment(obj, orderSn, productId, productGoodsId,ordersProductId) {
		// 当前按钮对应的评论详细区域
		var detailDiv = $(obj).parents(".list-dl").siblings('.commentDetailDiv');
		// 点击我要评价后，首先判断当前的是否显示，如果显示则隐藏后返回（不清除数据）
		if (!detailDiv.is(':hidden')) {
			detailDiv.hide();
			return;
		}
		// 如果当前的不显示，则判断当前commentDetailDiv是否为空，不为空则直接显示，返回
		if ((detailDiv.html()).length > 0) {
			detailDiv.show();
			return;
		}
		// 如果当前commentDetailDiv是空，则清除所有commentDetailDiv的值，并隐藏所有commentDetailDiv，ajax调用后加载commentDetailDiv内容，并显示
		$(".commentDetailDiv").each(function(){
			$(this).empty();
			$(this).hide();
		});
		$.ajax({
			type:"GET",
			url:domain+"/member/addcommentdetail.html",
			dataType:"html",
			async : false,
			data : {orderSn:orderSn,productId:productId,productGoodsId:productGoodsId,ordersProductId:ordersProductId},
			success:function(data){
				//加载数据
				detailDiv.empty();
				detailDiv.html(data);
				detailDiv.show();
			}
		});
	}
</script>
 
</body>
</html>