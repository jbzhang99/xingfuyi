<#include "/seller/commons/_head.ftl">

<script language="javascript">
	$(function() {
		initMenu('changethtme');
	});
</script>

<div class="main-container container-fluid">
	<!-- Page Container -->
	<div class="page-container">
		<!-- 左侧菜单开始 -->
		<#include "/seller/commons/_left.ftl">
		<!-- 左侧菜单结束 -->
		<!-- Page Content -->
		<div class="page-content">
			<!-- 主体头部开始 -->
			<div class="page-breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="fa fa-home"></i> <a
						href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
					</li>
					<li class="active">主题设置</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">点击可更换主题</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<ul class="colorpicker ejavashop-colorpicker" id="skin-changer">
						<div class="row">
							<li class="col-md-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #5DB2FF;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/blue.min.css"></a></li>
							<li class="col-md-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #2dc3e8;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/azure.min.css"></a></li>
							<li class="col-md-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #03B3B2;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/teal.min.css"></a></li>
							<li class="col-md-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #53a93f;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/green.min.css"></a></li>
						</div>
						<div class="row">
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #FF8F32;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/orange.min.css"></a></li>
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #cc324b;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/pink.min.css"></a></li>
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #AC193D;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/darkred.min.css"></a></li>
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #8C0095;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/purple.min.css"></a></li>
						</div>
						<div class="row"> 
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #0072C6;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/darkblue.min.css"></a></li>
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #585858;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/gray.min.css"></a></li>
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #474544;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/black.min.css"></a></li>
							<li class="col-lg-2 col-sm-2 col-xs-4"><a
								class="colorpick-btn" href="#"
								style="background-color: #001940;"
								rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/deepblue.min.css"></a></li>
						</div>
					</ul>

				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">