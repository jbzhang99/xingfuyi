<link rel="stylesheet"
	href="${(domainUrlUtil.staticResources)!}/resources/seller/css/index.css">
<link rel="stylesheet"
	href="${(domainUrlUtil.staticResources)!}/resources/seller/css/ext.css">
<link rel="stylesheet" type="text/css"
	href="${domainUrlUtil.urlResources}/resources/seller/easyui/themes/metro/easyui.css">
<script type="text/javascript"
	src="${domainUrlUtil.urlResources}/resources/seller/easyui/jquery.min.js"></script>

<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/theme.js'></script>
<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/echarts.js'></script>
<script>
	$(function() {
		var phurchaseRate = echarts.init($("#phurchaseRate")[0]);
		<#noescape>
		phurchaseRate.setOption(eval(${option}));
		</#noescape>
	});
</script>


<div id="phurchaseRate" style="width: 100%; height: 100%;"></div>
