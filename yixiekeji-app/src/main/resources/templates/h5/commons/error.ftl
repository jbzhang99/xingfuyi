<#include "/h5/commons/_head.ftl" />
<body class="bgff">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">请求错误</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div>
		<div class="errorbox">
		   <div class="arrow-fl"></div>
	       <p  class="clr53">
		       <span><i class="fa fa-exclamation-triangle"></i>&nbsp;错误信息:</span><br>
		       ${(info)!''}
	   	  </p>
	   	  <p class="mar_top">您可以稍后再试，或联系客服。</p>
	   	  <p class="text-center mar_top"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="btn s-btn">首页</a></p>
	   </div>
    </div>
	<!-- 主体结束 -->

	
	<#include "/h5/commons/_statistic.ftl" />
	
<script src="${(domainUrlUtil.staticResources)!}/js/xback.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/_pageBackStack.js"></script>

</body>
</html>