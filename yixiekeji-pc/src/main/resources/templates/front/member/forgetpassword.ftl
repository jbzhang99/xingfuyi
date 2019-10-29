<!Doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<link  rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/bootstrap.min.css'>
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/user.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/base.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/register.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/jquery.alerts.css"/>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/jquery-1.9.1.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/bootstrap.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/jquery.validate.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.alerts.js"></script>
		<script type="text/javascript">
			var domain = '${(domainUrlUtil.urlResources)!}';
		</script>
	</head>
	<body style="background: #f2f2f2;">
		<div class='container'>
			<div class='header-wrap clearfix'>
				<div class='snlogo fl'>
					<a href='${(domainUrlUtil.urlResources)!}/index.html'>
						<img src='${(domainUrlUtil.staticResources)!}/img/yxkjlogo.png'>
					</a>
				</div>
				<div class="login-character fl">找回密码</div>
			</div>
		</div>
		<div class="login_content">
			<div class='nc-login-layout wp-1210' >
				<!-- 找回密码 -->
				<div class='nc-signup nc-signup-sign'>
					<div class='signup-title signup-title-log'>
						<h3><span class="sp-l fl">找回密码</span><span class="sp-r fr"><a href="${(domainUrlUtil.urlResources)!}/login.html" class="sign-a">立即登陆</a></span></h3>
					</div>
					<div class='signup-content'>
						<form class="form-horizontal" id='forgetform'>
						  	<div class="form-group">
							    <div class="form-group-user clearfix">
							    		<div class="input_icon account_icon"></div>
							      	<input type="text" placeholder="登陆账号" id="name" name='name'>
							      	<p class='forget-tip'>登陆账号不能为空</p>
							    </div>
						  	</div>
						  	<div class="form-group">
							    <div class="form-group-user clearfix">
							    		<div class="input_icon password_icon"></div>
							      	<input type="text" placeholder="绑定的手机号" name="email" id="email">
							      	<p class='forget-tip'>绑定的手机号</p>
							    </div>
						  	</div>
						  	<div class="form-group form-group-yz">
							    <div class="form-group-user clearfix" style="padding:0 22px;">
							    		<div class="input_icon randomcode_icon"></div>
							      	<input type="text" placeholder="验证码" id="verifyCode" name="verifyCode">
							      	<div class='' style="float:right;">
								    	<img style="cursor:pointer;" src="${(domainUrlUtil.urlResources)!}/verify.html" id="code_img" onclick="refreshCode();"  />
					    				<a href='javascript:void(0);' onclick="refreshCode();">看不清，换一张</a>
								    </div>
							    </div>
						  	</div>
						  	<div class='form-group form-group-h'>
						  		<div class=''>
						  		</div>
						  	</div>
						  	<a href='javascript:void(0)' id="forgetPasswordBtn" class="ahover">
						  	<div class='form-group form-group-dl'>
						  		<div class=''>
						  			重置密码
						  		</div>
						  	</div>
						  	</a>
						  	<div class='form-group'>
						  	</div>
						</form>
					</div>
				</div>
				<!-- 找回密码 -->
			</div>
		</div>
		
<script type="text/javascript">
function refreshCodeR(){
  jQuery("#code_img_r").attr("src",domain+"/verify.html?d"+new Date().getTime());
}
	    
$(function(){
	$("#forgetPasswordBtn").click(function(){
		var name = $("#name").val();
		if(name == '') {
			jAlert("用户名不能为空");
			return;
		}
		if(name.length < 6) {
			jAlert("用户名至少6位");
			return;
		}
		var email = $("#email").val();
		if(email == '') {
			jAlert("邮箱或者手机号不能为空");
			return;
		}
		
		var verifyCode = $("#verifyCode").val();
		if(verifyCode == '') {
			jAlert("请输入验证码");
			return;
		}
		if(verifyCode.length != 4) {
			jAlert("请输入4位验证码");
			return;
		}
	
		if ($("#forgetform").valid()) {
			$(".ahover").attr("disabled","disabled");
			var params = $('#forgetform').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/doforgetpassword.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						jAlert('密码重置成功，请查收您的短信！', '提示',function(){
							window.location=domain+"/index.html"
						});
					}else{
						jAlert(data.message);
						refreshCode();//刷新验证码
						$(".ahover").removeAttr("disabled");
					}
				}
			});
		}
	});
});
</script>
<#include "/front/commons/_endbig.ftl" />