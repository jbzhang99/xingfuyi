
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
   	  	 <div class="flex-2 text-center">邮件绑定</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

	<div class="clr53 text-center" style="padding:50px 0;">
		<#if sucess?? && sucess == "true">
			<div  >
				恭喜您绑定邮件成功。<a href="${(domainUrlUtil.urlResources)!}/index.html" style="text-decoration:underline;">去购物吧！</a>
			</div>
	 	<#else>
			<div >
				额，绑定失败了，<a href="${(domainUrlUtil.urlResources)!}/member/info.html" style="text-decoration:underline;">再试一次吧！</a>
				<br>
				${(message)!""}
			</div>
	  	</#if>
	</div>

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		
		
	});
</script>


</body>
</html>