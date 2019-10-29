<!--
<footer class="text-center">
	<#if memberSession?? && memberSession.member??>
		<div class="ft_top mar-bt flex">
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/member/index.html" class="block">${(memberSession.member.name)!''}</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="block">返回首页</a></div>
			<div class="flex-1"><a href="#" class="block">返回顶部</a></div>
		</div>
	<#else>
		<div class="ft_top mar-bt flex">
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/login.html" class="block">登录</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/register.html" class="block">注册</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="block">返回首页</a></div>
			<div class="flex-1"><a href="#" class="block">返回顶部</a></div>
		</div>
	</#if>
	<div>
	    Copyright©2019 北京齐驱科技有限公司<br />
	    京ICP备19032652号-3
	</div>
</footer>
-->
<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/index.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/common.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.hDialog.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/wallet.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/echo.min.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/xback.js"></script>
<script>
	echo.init();
</script>
<script src="${(domainUrlUtil.staticResources)!}/js/_pageBackStack.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/layer_mobile/layer.js"></script>