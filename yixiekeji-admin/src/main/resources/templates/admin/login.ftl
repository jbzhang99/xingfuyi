<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>幸福易多商家管理系统平台端-登录</title>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet"
	href="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/themes/default/easyui.css"
	type="text/css"></link>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/js/func.js"></script>
<link rel="stylesheet" type="text/css"
	href="${(domainUrlUtil.staticResources)!}/resources/admin/css/login.css"></link>
<script>
	//刷新验证码
	function refreshCode() {
		$("#code_img").attr(
				"src",
				"${(domainUrlUtil.urlResources)!}/admin/system/verifyCode?d"
						+ new Date().getTime());
	}
</script>
</head>
<body style="background: #e0e0e0;">
	<div class="nav-tit">
		<div class="w">
			<img
				src="${(domainUrlUtil.staticResources)!}/resources/admin/images/logo0.png"
				alt="">幸福易运营管理平台
		</div>
	</div>
	<div class="login-form">
		<form class="form_login"
			action="${domainUrlUtil.urlResources}/admin/doLogin"
			method="post">
			<div class="tit">管理登录</div>
			<div class="form-group">
				<i class="i-username"></i> <input type="text" name="name" required
					oninvalid="setCustomValidity('请输入用户名')"
					oninput="setCustomValidity('')" placeholder="用户名" />
				<!-- <div class="erro">请输入用户名</div> -->
			</div>
			<div class="form-group">
				<i class="i-password"></i> <input type="password" name="password"
					oninvalid="setCustomValidity('请输入密码')"
					oninput="setCustomValidity('')" required placeholder="密码" />
				<!-- <div class="erro">请输入密码</div> -->
			</div>
			<div class="form-group">
				<input type="text" name="verifyCode"
					oninvalid="setCustomValidity('请输入验证码')"
					maxLength=4
					autocomplete="off"
					oninput="setCustomValidity('')" required placeholder="验证码"
					style="width: 100px;">
				<div class='' style="float: right;margin-right: 15px;">
					<img onclick="refreshCode();" id="code_img"
						src='${(domainUrlUtil.staticResources)!}/admin/system/verifyCode'
						width="100" height="34">
				</div>
				<div class="erro">${(message)!}</div>
			</div>
			<div class="form-group">
				<button type="submit" class="btn-login">登录</button>
			</div>
		</form>
	</div>
	<div class="botm-copy">版权所有 2019
		北京齐驱科技有限公司.保留一切权利.京ICP备19032652号-3</div>
</body>
</html>