<script>
	var widthtitdiv = $(".whtitdiv").outerHeight(true) + 10;
	var windowH = $(window).height();
	//导航信息
	var pdbs = $(".page-breadcrumbs").outerHeight(true);
	//页头
	var navbar = $(".navbar").outerHeight(true);
	
	var _height = windowH - pdbs - navbar;
	$('#bodyejavashop').css('height', _height + 'px');
	window.onresize = function() {
		var _height = windowH - widthtitdiv;
		$('#bodyejavashop').css('height', _height + 'px');
	}
</script>