<#include "/seller/commons/_head.ftl">

<link href="${domainUrlUtil.staticResources}/resources/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>

<script language="javascript">
$(function(){
	initMenu('actintegral');
	$(".boxer").boxer();
	
	$("button[type='button'].back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/promotion/actintegral";
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
					<li><a
						href="${domainUrlUtil.urlResources}/seller/promotion/actintegral">积分商城管理</a>
					</li>
					<li class="active">积分商城查看</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal">
						 	<input type="hidden" id="id" name="id" value="${(actIntegral.id)!}" />
							<input type="hidden" id="productId" name="productId" value="${(actIntegral.productId)!}"/>
							<input type="hidden" id="descinfo" name="descinfo" value="${(actIntegral.descinfo)!}"/>
				
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>积分商城分类：</label>
							<div class="col-lg-4">
								<select name="type" class="form-control" disabled="disabled">
									<#if actIntegralTypes ??>
										<#list actIntegralTypes as actIntegralType>
								  			<option value = "${actIntegralType.id}">${actIntegralType.name}</option>
								  		</#list>
								  	</#if>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品：
							</label>
							<div class="col-lg-10">
								<label id="productName">${(actIntegral.productName)!}</label>
							</div>
						</div>
							
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>促销标题：</label> 
							<div class="col-lg-4">
								<input disabled="disabled"
									class="form-control" type="text" id="title"
									name="name"
									value="${(actIntegral.name)!}"
									/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>渠道：</label>
							<div class="col-lg-4">
								<@cont.select id="channel" name="channel" codeDiv="CHANNEL"
									disabled="disabled"
									class="form-control" mode="2"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动图片：</label> 
							<#if (actIntegral.image)??> 
							<div class="col-lg-4">
								<a href="${domainUrlUtil.imageResources}${(actIntegral.image)!''}" class='boxer'>查看图片</a>
							</div>
							</#if>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>市场价: </label> 
							<div class="col-lg-4">
								<input
									disabled="disabled"
									type="text" 
									id="marketPrice" 
									name="marketPrice"
									value="${(actIntegral.marketPrice)!''}"
									class="form-control" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>团购价: </label> 
							<div class="col-lg-4">
								<input
									disabled="disabled"
									type="text" 
									id="price" 
									name="price"
									value="${(actIntegral.price)!''}"
									class="form-control" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>会员限制：</label>
							<div class="col-lg-4">
								<@cont.select id="gradeValue" name="gradeValue"
									class="form-control"
									disabled="disabled"
									codeDiv="MEMBER_GRADE" mode="2"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>限购数量：</label> 
							<div class="col-lg-4">
								<input
									disabled="disabled"
									class="form-control" 
									id="purchase"
									name="purchase"
									value="${(actIntegral.purchase)!''}" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>库存：</label> 
							<div class="col-lg-4">
								<input
									disabled="disabled"
									class="form-control" id="stock" name="stock"
									value="${(actIntegral.stock)!''}" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动时间：</label> 
							<div class="col-lg-4">
								<input
									type="text" id="startTime" name="startTime"
									class="form-control"
									value="${(actIntegral.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									disabled="disabled"/> 
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime"
									name="endTime" class="form-control"
									value="${(actIntegral.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}" 
									disabled="disabled"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>活动描述: </label>
							<div class="col-lg-8">
								<script type="text/plain" 
									id="myEditor"
									style="width: 100%; height: 240px;"><#noescape>${(actIntegral.descinfo)!}</#noescape></script>
								<script type="text/javascript">
							    	UM.getEditor('myEditor').setDisabled();
								</script>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="button" class="btn btn-danger back btn-primary">返回</button>
							</div>
						</div>
					</form>

				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>


<#include "/seller/commons/_addcommonfooter.ftl"> 
<#include "/seller/commons/_end.ftl">