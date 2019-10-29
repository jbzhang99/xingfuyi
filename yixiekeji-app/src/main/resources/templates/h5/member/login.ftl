<#include "/h5/commons/_head.ftl" />
<body style="background:#f2f2f2;">
   <!-- 头部 -->
   <header>
   	  <div class="flex flex-align-center login-header">
   	  	 <div class="padlr10 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">登录</div>
   	  </div>
   	  
   </header>
   <!-- 头部 end-->
   
	<div class="s-container">
	  <div class="pad10">
	  	  <div class="tiperror"></div>
	  	  <form id="loginForm" onSubmit="return false;">
	  	  	<input type="hidden" id="toUrl" name="toUrl" value="${(toUrl)!''}"/>
	  	    <div class="form-group">
	  	      <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名">
	  	    </div>
	  	    <div class="form-group">
	  	      <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
	  	    </div>
	  	    <div class="form-group" style="position:relative;">
	  	      <input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="请输入验证码" maxlength="6" style="width:50%">
	  	      <span class="captcha-img">
	  	      	<!-- <img src="" width="63" height="25"> -->
	  	      	<img style="cursor:pointer;" src="${(domainUrlUtil.urlResources)!}/verify.html" id="code_img" onclick="refreshCode();" width="63" height="25" />
	  	      </span>
	  	      
	  	    </div>
	  	    <button type="submit" class="btn btn-block btn-login" id="loginBtn">登录</button>
	  	  </form>
	  	  <div class="flex flex-pack-justify login-option">
	  	  	 <div><a href="${(domainUrlUtil.urlResources)!}/register.html">快速注册</a></div>
	  	  	 <div><a href="${(domainUrlUtil.urlResources)!}/forgetpassword.html">找回密码</a></div>
	  	  </div>
	  </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

		if(isAndroid){
			window.phoneFunc.jsCallWebView("tologin");
		}else if(isiOS){
			window.webkit.messageHandlers.tologin.postMessage(null);
		}
		
		
		$("#loginBtn").click(function(){
			var userName = $("#userName").val();
			if (userName == null || userName == "") {
				$(".tiperror").html("请输入用户名");
				return false;
			}
			var password = $("#password").val();
			if (password == null || password == "") {
				$(".tiperror").html("请输入密码");
				return false;
			}
			var verifyCode = $("#verifyCode").val();
			if (verifyCode == null || verifyCode == "") {
				$(".tiperror").html("请输入验证码");
				return false;
			}
			$("#loginBtn").attr("disabled","disabled");
			var params = $('#loginForm').serialize();
			var toUrl = $('#toUrl').val();
			$.ajax({
				type:"POST",
				url:domain+"/dologin.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						// 跳转到TODO
						if (toUrl != null && toUrl != "") {
							window.location=toUrl;
						} else {
							window.location=domain+"/member/index.html";
						}
					}else{
						$(".tiperror").html(data.message);
						//刷新验证码
						refreshCode();
						$("#loginBtn").removeAttr("disabled");
						return false;
					}
				},
				error:function(){
					$(".tiperror").html("异常，请重试！");
					$("#loginBtn").removeAttr("disabled");
					return false;
				}
			});
		});
		
	});
	
	
	//刷新验证码
	function refreshCode(){
		jQuery("#code_img").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?d"+new Date().getTime());
	}
</script>
         
 
</body>
</html>