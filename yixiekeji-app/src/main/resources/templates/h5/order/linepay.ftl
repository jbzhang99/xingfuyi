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
   	  	 <div class="flex-2 text-center">支付成功</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div>
         
				<div id='CartSucess'>
					<div class='errorbox'>
               <i class="fa fa-smile-o"></i>&nbsp;支付成功，请到&nbsp;<a href='javascript:;' onclick="tome();" class="btn s-btn">订单中心</a>&nbsp;查看您的订单！
					</div>
				</div>
		
    </div>
	<!-- 主体结束 -->

	<script>
		// 必须在引用_footer.ftl或_pageBackStack.ftl前定义
		var intoBackStack = false;
	</script>
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
	
<script type="text/javascript">
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	
	function tome(){
		if(isAndroid){
  			window.phoneFunc.jsCallWebView("tome");
  		}else{
  			window.webkit.messageHandlers.tome.postMessage(null);
  	  	}
	}
</script>

</body>
</html>