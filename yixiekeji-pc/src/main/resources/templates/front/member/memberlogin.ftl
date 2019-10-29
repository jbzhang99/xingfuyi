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
			</div>
		</div>
		<div class="login_content">
			<div class='nc-login-layout wp-1210' >
				<!-- S 登录页面 -->
				<div class='nc-signup nc-signup-sign'>
					<div class='signup-title signup-title-log'>
						<h3><span class="sp-l fl">用户登录</span><span class="sp-r fr">没有幸福易账号？<a href="${(domainUrlUtil.urlResources)!}/register.html" class="sign-a">立即注册</a></span></h3>
					</div>
					<div class='signup-content'>
						<form class="form-horizontal" id='formLogin'>
						  	<div class="form-group">
							    <#--<div class="form-group-user clearfix">-->
							    		<#--<div class="input_icon account_icon"></div>-->
							      	<#--<input type="text" placeholder="邮箱、手机号、账号" id="name" name='name'>-->
							      	<#--<p class='forget-tip'>用户名不能为空</p>-->
							    <#--</div>-->
                                <div class="form-group-user clearfix">
							    		<div class="input_icon account_icon"></div>
							      	<input type="text" placeholder="手机号" id="name" name='name'>
							      	<p class='forget-tip'>手机号不能为空</p>
							    </div>

						  	</div>
						  	<div class="form-group">
							    <div class="form-group-user clearfix">
							    		<div class="input_icon password_icon"></div>
							      	<input type="password" placeholder="密码" id="setPassword" name='password'>
							      	<p class='forget-tip'>密码不能为空</p>
							    </div>
						  	</div>
						  	<#--<div class="form-group form-group-yz">-->
							    <#--<div class="form-group-user clearfix" style="padding:0 22px;">-->
							    		<#--<div class="input_icon randomcode_icon"></div>-->
							      	<#--<input type="text" placeholder="验证码" id="verifyCode" name="verifyCode">-->
							      	<#--<div class='' style="float:right;">-->
								    	<#--<img style="cursor:pointer;" src="${(domainUrlUtil.urlResources)!}/verify.html" id="code_img" onclick="refreshCode();"  />-->
					    				<#--<a href='javascript:void(0);' onclick="refreshCode();">看不清，换一张</a>-->
								    <#--</div>-->
							    <#--</div>-->
						  	<#--</div>-->
						  	<div class='form-group form-group-h'>
						  		<div class=''>
						  			<a href='${(domainUrlUtil.urlResources)!}/forgetpassword.html' class='forget-password'>忘记密码？</a>
						  		</div>
						  	</div>

						  	<a href='javascript:void(0)' id="loginButton" class="ahover">
						  	<div class='form-group form-group-dl'>
						  		<div class=''>
						  			登录
						  		</div>
						  	</div>
						  	</a>
						  	<div class='form-group'>
						  	</div>
						</form>
					</div>
				</div>
				<!-- E 登录页面 -->
			</div>
		</div>

<script type="text/javascript">
function refreshCodeR(){
	jQuery("#code_img_r").attr("src",domain+"/verify.html?d"+new Date().getTime());
}
$(function(){
		//增加回车键监听
		$(document).keydown(function(e){
			var curKey = e.which;
			if (curKey == 13) {
				var jalert = $("#popup_container");
				//如果有弹出窗，响应弹出窗内容
				if(jalert && jalert.length > 0){
					$("#popup_ok").click();
				} else{
					//响应表单提交
					$("#loginButton").click();
				}
				return false;
			}
		});

		$("#loginButton").click(function() {
			var name = $("#name").val();
			if (name == '') {
				jAlert("手机号不能为空");
				return;
			}
			var setPassword = $("#setPassword").val();
			if (setPassword.trim() == ''||setPassword.trim()== null) {
				jAlert("密码不能为空");
				return;
			}
			if ($("#formLogin").valid()) {
				$(".ahover").attr("disabled", "disabled");
				$.ajax({
					type : "POST",
                    url: domain +"/dologin",
					dataType : "json",
					async : false,
                    data :{mobile:name, password:setPassword},
                    success:function(data){
						if (data.success) {
                            window.location = domain;
						} else {
							jAlert(data.message);
							//刷新验证码
//							refreshCode();
//							$(".ahover").removeAttr("disabled");
						}
					},
					error : function() {
						jAlert("异常，请重试！");
						$(".ahover").removeAttr("disabled");
					}
				});
			}

		});

	});
</script>
<#include "/front/commons/_endbig.ftl" />
