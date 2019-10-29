<#include "/seller/commons/_head.ftl">

<script language="javascript">
$(function(){
	$("#oldPassword").val("");
	$("#newPassword").val("");
	$("#newPasswordCfm").val("");
	
	initMenu('editpwd');
	$('#addform').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		}
	}).on('success.form.bv', function(e) {
		//点击提交之后
        // Prevent form submission
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
        
        $.ajax({
			type:"POST",
			url: "${domainUrlUtil.urlResources}/seller/system/sellerUser/editpwd/update",
			dataType: "json",
			data: $('#addform').serialize(),
			cache:false,
			success:function(data, textStatus){
				if (data.success) {
					$.messager.alert('提示','修改成功。');
					$("#oldPassword").val("");
					$("#newPassword").val("");
					$("#newPasswordCfm").val("");
					return;
				} else {
					 bv.disableSubmitButtons(false);
					$.messager.alert("提示",data.message);
				}
			}
		});
	});

	<#if message??>$.messager.progress('close');alert('${message}');</#if>
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
					<li class="active">修改密码</li>
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
						action="${domainUrlUtil.urlResources}/seller/system/sellerUser/editpwd/update"
						enctype="multipart/form-data" data-bv-message="该项必填">
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>旧密码: </label> 
							<div class="col-lg-4">
							<input
								type="password" id="oldPassword" name="oldPassword"
								class="form-control"
								required
							 	data-bv-stringlength="true"
                                data-bv-stringlength-min="6"
                                data-bv-stringlength-max="8"
                                data-bv-stringlength-message="请设置6-8位长度密码"  />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>新密码: </label> 
							<div class="col-lg-4">
								<input
									type="password" id="newPassword" name="newPassword"
									class="form-control"
									required
									data-bv-stringlength="true"
	                                data-bv-stringlength-min="6"
	                                data-bv-stringlength-max="8"
	                                data-bv-stringlength-message="请设置6-8位长度密码"  />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>新密码确认:
							</label> 
							<div class="col-lg-4">
							<input type="password" id="newPasswordCfm" name="newPasswordCfm"
								class="form-control"
								required
								data-bv-stringlength="true"
                                data-bv-stringlength-min="6"
                                data-bv-stringlength-max="8"
                                data-bv-identical="true"
                              	data-bv-identical-field="newPassword"
                               	data-bv-identical-message="两次密码输入不一致"
                                data-bv-stringlength-message="请设置6-8位长度密码"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-3">
								<button type="submit" class="btn btn-danger btn-primary">提交</button>
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