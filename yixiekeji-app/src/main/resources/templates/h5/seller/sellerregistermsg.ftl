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
   	  	 <div class="flex-2 text-center">商家入驻申请</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	<div class="s-container" id="container">
		<div style="height:70%;" class="flex flex-pack-center flex-align-center">
			<div>
				<br>
				<br>
				<br>
				<br>
				<br>
				<p class="text-center">${(message)!""}</p>
		        <p class="mar_top text-center"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="a_btn">去逛逛</a></p>
			</div>
		</div>
	</div>
	<!-- 主体结束 -->
	
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
<script>
	$(document).ready(function () {
		
		
	});

</script>

</body>
</html>