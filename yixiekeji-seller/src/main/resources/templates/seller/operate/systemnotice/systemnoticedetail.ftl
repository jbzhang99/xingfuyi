<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/systemNotice"/>

<script language="javascript">
	var currentBaseUrl = "${currentBaseUrl}";
	var domainURL = "${domainUrlUtil.urlResources}";

	$(function() {
		initMenu('systemNotice');

		$("#back").click(function() {
			location.href = currentBaseUrl;
		});
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
					<li>
					<a
						href="${domainUrlUtil.urlResources}/seller/systemNotice">商城公告</a>
					</li>
					<li class="active">公告详情</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="container" style="width: 100%;">
					<h3 class="blog-title">${obj.title}</h3>
					<div class="center">
						<span class="blog-time">${(obj.createTime)?string("yyyy-MM-dd")}</span> 
						<span>${(obj.createUserName)!}</span>
					</div>
					<div class="blog-main">
						<#noescape>
							${obj.content}
						</#noescape>
					</div>
					<div style="position: relative;margin-bottom: 20px;" class="textcenter">
						<button type="button" id="back" class="btn btn-default btn-danger btn-primary btn-full-screen">返回列表</button>
					</div>
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
