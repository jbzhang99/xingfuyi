<!--Basic Scripts-->
<script
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/bootstrapvalidator/vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- Beyond Scripts -->
<script
	src="${(domainUrlUtil.staticResources)!}/resources/seller/js/beyond.js"></script>
<script
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/bootstrapvalidator/dist/js/bootstrapValidator.min.js"></script>
<script
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/bootstrapvalidator/dist/js/language/zh_CN.js"></script>

<script>
	$(".ejava-sidebar-menu li").each(function(){
		var _thisindex = $(this).index();
		var sidebarmeu_height = $(window).height()
				- (125 + (_thisindex * 40));
		if ($(this).find(".ejava-submenu").height() > sidebarmeu_height) {
			$(this).find(".ejava-submenu").css('height',
					sidebarmeu_height + 'px');
		}
	});
</script>
</body>
<!--  /Body -->
</html>
