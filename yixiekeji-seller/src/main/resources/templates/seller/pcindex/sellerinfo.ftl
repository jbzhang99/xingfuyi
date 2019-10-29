<#include "/seller/commons/_head.ftl">

<link href="${domainUrlUtil.staticResources}/resources/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>

<script language="javascript">
$(function(){
	initMenu('sellerinfo');
	
	$("button[type='button'].back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/mindex/banner";
	});
	
	$('#addform').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		submitHandler: function (validator, form, submitButton) {
			if(!validator.isValid()){
				return false;
			}
			var detail = UM.getEditor('myEditor').getContent();
	        $('#detail').val(detail);//商品描述信息
	        
            validator.defaultSubmit();
		}
	});
	
	<#if pcSellerIndex?? && (pcSellerIndex.detail)??>
	    UM.getEditor('myEditor').setContent(<#noescape>'${(pcSellerIndex.detail)}'</#noescape>, true);
	</#if>

	<#if message??>$.messager.progress('close');alert('${message}');</#if>
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
					<li class="active">PC首页信息</li>
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

					<form method="post" id="addform" class="form-horizontal"
						action="${domainUrlUtil.urlResources}/seller/pcindex/sellerinfo/update"
						enctype="multipart/form-data" data-bv-message="该项必填">
						<input type="hidden" id="id" name="id" value="${(pcSellerIndex.id)!''}">
						<input type="hidden" id="logo" name="logo" value="${(pcSellerIndex.logo)!''}">
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>logo图片：</label>
							<div class="col-lg-4">
								<input type="file" id="imageFile" name="imageFile" 
									class="form-control"/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								图片最佳像素：宽160，高80
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label> 
							<div class="col-lg-4">
								<img alt="图片" style="max-width: 700px;"
											src="${domainUrlUtil.imageResources}${(pcSellerIndex.logo)!''}">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>店铺公告：</label> 
							<div class="col-lg-8">
								<textarea name="notice" id="notice"
                                   	required
									class="form-control">${(pcSellerIndex.notice)!''}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>店铺介绍：</label>
							<div class="col-lg-8">
								<input type="hidden" id="detail" name="detail"
									value="${(pcSellerIndex.detail)!''}" />
								<script type="text/javascript" id="myEditor" style="width:100%;height:240px;"></script>
								<script>
									UM.getEditor('myEditor');
								</script>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-5">
								<button type="submit" class="btn btn-danger btn-primary">确定</button>
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

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">