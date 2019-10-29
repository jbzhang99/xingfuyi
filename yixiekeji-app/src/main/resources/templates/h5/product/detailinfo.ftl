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
   	  	 <div class="flex-2 text-center">商品详情</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	
	<#-- <#include "/h5/product/producthead.ftl" />  -->
	<div class="flex goods-nav">
		<div class="flex-1 active"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/info/${(productId)!0}.html?type=${(type)!0}">详细介绍</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/spec/${(productId)!0}.html?type=${(type)!0}">规格参数</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/comment/${(productId)!0}.html?type=${(type)!0}">商品评价</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/ask/${(productId)!0}.html?type=${(type)!0}">商品咨询</a></div>
	</div>
	<div class="s-container" id="container">
	    <div class="pad10 imgwidth">
		  <p><#noescape> ${(product.description)!''}</#noescape></p>
		</div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />


</body>
</html>