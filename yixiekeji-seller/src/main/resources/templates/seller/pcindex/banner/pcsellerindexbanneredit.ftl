<#include "/seller/commons/_head.ftl">

<script language="javascript">

$(function(){
	initMenu('pbanner');
	
	$("button[type='button'].back").click(function(){
 		window.location.href= domain+"/seller/pcindex/banner";
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
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			
			var startInt = new Date(startTime.replace("-", "/").replace("-", "/"));
			var endInt = new Date(endTime.replace("-", "/").replace("-", "/"));
			
			if(endInt <= startInt) {
				$.messager.alert('提示','结束时间不能小于开始时间');
				return;
			}
			
            validator.defaultSubmit();
		},
		fields:{
			channel:{
				validators : {
					 notEmpty: true
				}
			}
		}
	});
	
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
});

function updateStatus(name){
	$("#addform").bootstrapValidator('updateStatus', name, 'NOT_VALIDATED').
		bootstrapValidator('validateField', name);
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
					<li><a
						href="${domainUrlUtil.urlResources}/seller/pcindex/banner">首页轮播图</a>
					</li>
					<li class="active">编辑首页轮播图</li>
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
						action="${domainUrlUtil.urlResources}/seller/pcindex/banner/update"
						enctype="multipart/form-data">
						<input type="hidden" id="id" name="id" value="${(pcSellerIndexBanner.id)!''}">
						<input type="hidden" id="status" name="status" value="${(pcSellerIndexBanner.status)!''}">
						<input type="hidden" id="image" name="image" value="${(pcSellerIndexBanner.image)!''}">
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>标题：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="title"
									name="title" value="${(pcSellerIndexBanner.title)!''}"
									required
								 	data-bv-stringlength="true"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="200"
                                    data-bv-stringlength-message="标题2-200位长度"
									/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>链接地址：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="linkUrl"
									name="linkUrl" value="${(pcSellerIndexBanner.linkUrl)!''}"
									required
								 	data-bv-stringlength="true"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="255"
                                    data-bv-stringlength-message="地址长度不合法"/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								填写完整的地址，例如：http://www.xxx.com/product/1.html
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>排序号：</label>
							<div class="col-lg-4">
								<input class="form-control" type="text" id="orderNo"
									name="orderNo" value="${(pcSellerIndexBanner.orderNo)!''}"
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="请输入正确的排序号"/>
							</div>
									
							<label class="col-lg-6 ejava-errinforight">
								序号越小越靠前显示
							</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>展示时间：</label> 
							<div class="col-lg-4">
								<input
									type="text" id="startTime" name="startTime"
									class="form-control"
									required
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});"
									value="${(pcSellerIndexBanner.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}" /> 
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime"
									name="endTime" class="form-control"
									required
									onblur="updateStatus(this.name);"
									missingMessage="结束时间必填" data-options="required:true"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});"
									value="${(pcSellerIndexBanner.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>图片：</label> 
							<div class="col-lg-4">
								<input
									type="file" id="imageFile" name="imageFile"
									class="form-control"
									/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								图片最佳像素（或保持该比例）：宽1920，高550
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label> 
							<div class="col-lg-4">
								<img alt="图片" style="max-width: 700px;"
											src="${domainUrlUtil.imageResources}${pcSellerIndexBanner.image}" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
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