<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>幸福易商家管理平台</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache,must-revalidate">
<meta http-equiv="expires" content="0">

<meta name="description" content="form validation" />
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable = 0">
<link rel="shortcut icon" href="${(domainUrlUtil.staticResources)!}/resources/seller/img/favicon.png" type="image/x-icon" />

<link href="${(domainUrlUtil.staticResources)!}/resources/seller/css/bootstrap.min.css" rel="stylesheet" />
<link href="${(domainUrlUtil.staticResources)!}/resources/seller/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/resources/seller/css/beyond.css" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/resources/seller/css/index.css" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/resources/seller/css/ext.css" />
<link rel="stylesheet" type="text/css" href="${domainUrlUtil.staticResources}/resources/seller/easyui/themes/metro/easyui.css" />
<link rel="stylesheet" type="text/css" href="${domainUrlUtil.staticResources}/resources/seller/easyui/themes/mobile.css" />
<link rel="stylesheet" type="text/css" href="${domainUrlUtil.staticResources}/resources/seller/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/azure.min.css" />

<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/seller/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/jquery.filedownload.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/seller/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/func.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/seller/easyui/jquery.easyui.mobile.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/seller/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/jquery.form.js"></script>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/My97DatePicker/WdatePicker.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/resources/seller/js/skins.js"></script>
<script>
	var domain = "${domainUrlUtil.urlResources}";
	var imgdomain = "${(domainUrlUtil.imageResources)!}";
</script>

<#import "/seller/commons/_macro_controller.ftl" as cont/> 
<#assign form = JspTaglibs["http://www.springframework.org/tags/form"]>
	
</head>
<body style="overflow-y: hidden;">
	
	<!-- 加载动画开始 -->
	<#include "_loading.ftl">
	<!-- 加载动画结束 -->
	<!-- 头部导航栏开始 -->
	<#include "_navbar.ftl">
	<!-- 头部导航栏结束 -->