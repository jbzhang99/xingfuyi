<#include "/h5/commons/_head.ftl" />
<body>
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
	   	   	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">规格参数</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	<#-- <#include "/h5/product/producthead.ftl" />  -->
	<div class="flex goods-nav">
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/info/${(productId)!0}.html?type=${(type)!0}">详细介绍</a></div>
		<div class="flex-1 active"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/spec/${(productId)!0}.html?type=${(type)!0}">规格参数</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/comment/${(productId)!0}.html?type=${(type)!0}">商品评价</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/ask/${(productId)!0}.html?type=${(type)!0}">商品咨询</a></div>
	</div>
	<div class="s-container" id="container">
	    <div class="pad10">
		  <table width="100%" class="goods-table"  cellspacing="0" cellpadding="0" >
		  	 <tr>
		  	 	<td>商品名称</td>
		  	 	<td>${(product.name1)!'' }</td>
		  	 </tr>
		  	  <tr>
		  	 	<td>包装清单</td>
		  	 	<td>${(product.packing)!'' }</td>
		  	 </tr>
		  	 <tr>
		  	 	<td>店铺</td>
		  	 	<td>${(seller.sellerName)!''}</td>
		  	 </tr>
		  	 <tr>
		  	 	<td>上架时间</td>
		  	 	<td>${(product.upTime?string("yyyy-MM-dd HH:mm:ss"))!'' }</td>
		  	 </tr>
		  	 
		  	 <#if productAttr?? && productAttr?size &gt; 0>
				 <#list productAttr as attr>
				 	 <tr>
				  	 	<td>${(attr.name)!''}</td>
				  	 	<td>${(attr.value)!''}</td>
				  	 </tr>
				 </#list>
			 </#if>
		  </table>
		</div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />


</body>
</html>