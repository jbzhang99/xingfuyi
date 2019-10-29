<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/member/productask"/>

<script language="javascript">
$(function() {
	initMenu('productask');
	$("button[type='button'].back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/member/productask";
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
			var replyContent = $("#replyContent").val();
			if (replyContent == null || replyContent == "") {
				$.messager.alert('提示','请输入回复内容！');
				return;
			}
            validator.defaultSubmit();
		}
	});
	
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
})

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
						href="${domainUrlUtil.urlResources}/seller/member/productask">会员咨询管理</a>
					</li>
					<li class="active">商家回复</li>
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
						action="${domainUrlUtil.urlResources}/seller/member/productask/doreply"
						enctype="multipart/form-data">
						
						<input type="hidden" id="id" name="id" value="${(productAsk.id)!''}" />
					
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>用户名称：</label>
							<label class="col-lg-10 ejava-errinforight">${(productAsk.userName)!''}</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商家名称：</label> 
							<label class="col-lg-10 ejava-errinforight">${(productAsk.sellerName)!''}</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">内容：</label> 
							<label class="col-lg-10 ejava-errinforight">${(productAsk.askContent)!''}</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">商家回复：</label>
							<div class="col-lg-8">
								<textarea 
									id="replyContent"
									name="replyContent"
									class="form-control">${(productAsk.replyContent)!''}</textarea>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="submit" class="btn btn-danger btn-primary">提交</button>
								<button type="button" class="btn btn-danger btn-primary back">返回</button>
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