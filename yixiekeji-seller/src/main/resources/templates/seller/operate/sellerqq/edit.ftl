<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/operate/sellerqq"/>

<style>
.dl-group p img {
	max-width: 120px;
	float: left;
}

.formbox-a .lab-item {
	float: left;
	width: 120px;
	text-align: right;
	margin-right: 3px;
	display: inline;
	padding-top: 5px;
}
</style>

<script language="javascript">
	$(function() {
		initMenu('sellerqq');
		
		$("button[type='button'].back").click(function(){
	 		window.location.href="${currentBaseUrl}";
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
	            validator.defaultSubmit();
			}
		});

	});

	
	function closeWin() {
		$('#newstypeWin').window('close');
	}
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
						href="${domainUrlUtil.urlResources}/seller/operate/sellerqq">客服QQ设置</a>
					</li>
					<li class="active">添加客服QQ</li>
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
						action="${currentBaseUrl}/doAdd"
						enctype="multipart/form-data" data-bv-message="该项必填">
						<input type="hidden" value="${(obj.id)!''}" name="id"> 
						<input type="hidden" value="${(obj.image)!''}" name="image">
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>QQ：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" 
									id="qq" 
									name="qq"
									value="${(obj.qq)!''}"
									required
									pattern="^\d{4,11}$"
                             	  	data-bv-regexp-message="QQ号请输入数字，4-11位长度"
								 	data-bv-stringlength="true" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>客服名称：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" 
									id="name" 
									name="name" 
									value="${(obj.name)!''}"
									required
									data-bv-stringlength="true"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="50"
                                    data-bv-stringlength-message="名称2-50位长度" />
                            </div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>状态：</label> 
							<div class="col-lg-4">
								<@cont.select id="state" mode="-1" codeDiv="SELLER_QQ_STATE"
									name="state" class="txt" value="${(obj.state)!''}" class="form-control"/>
                            </div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-3">
								<button type="submit" class="btn btn-danger btn-primary">提交</button>
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

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">