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
	var myChart = echarts.init($("#chartdiv")[0]);
	<#noescape>
		myChart.setOption(eval(${option}));
	</#noescape>
});
</script>

<div id="chartdiv" style="width: 100%; height: 100%;"></div>
