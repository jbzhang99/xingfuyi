<#include "/seller/commons/_head.ftl">
<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>

<script language="javascript">

$(function(){
	initMenu('mbanner');
	$(".boxer").boxer();
	
	$("button[type='button'].back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/mindex/banner";
	});
	
	$('#addform').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
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
						href="${domainUrlUtil.urlResources}/seller/mindex/banner">移动端首页轮播图</a>
					</li>
					<li class="active">编辑轮播图</li>
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
						action="${domainUrlUtil.urlResources}/seller/mindex/banner/update"
						enctype="multipart/form-data" data-bv-message="该项必填">
							<input type="hidden" id="id" name="id" value="${(mSellerIndexBanner.id)!''}">
							<input type="hidden" id="status" name="status" value="${(mSellerIndexBanner.status)!''}">
							<input type="hidden" id="image" name="image" value="${(mSellerIndexBanner.image)!''}">
					
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>标题：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="title"
									name="title" value="${(mSellerIndexBanner.title)!''}"
									 data-bv-stringlength="true"
                                     data-bv-stringlength-min="2"
                                     data-bv-stringlength-max="200"
                                     required
                                     data-bv-stringlength-message="名称2-20位长度" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>链接地址：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="linkUrl"
									required
									 data-bv-stringlength="true"
                                     data-bv-stringlength-min="2"
                                     data-bv-stringlength-max="255"
                                     data-bv-stringlength-message="地址长度不合法"
									name="linkUrl" value="${(mSellerIndexBanner.linkUrl)!''}"
									/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								 请填写完整的地址,例如：http://www.xxx.com/product/1.html
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>排序号：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" id="orderNo" name="orderNo"
									value="${(mSellerIndexBanner.orderNo)!''}"
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="请输入正确的排序号" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								 序号越小越靠前显示
							</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>展示时间：</label>
							<div class="col-lg-4">
								<input type="text"
									id="startTime" name="startTime"
									class="form-control"
									required
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});"
									value="${(mSellerIndexBanner.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									/>
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime"
									name="endTime" class="form-control"
									required
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});"
									value="${(mSellerIndexBanner.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
								/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>图片：</label> 
							<div class="col-lg-4">
								<input type="file" id="imageFile" 
									name="imageFile" class="form-control"  />
							</div>
							<#if (mSellerIndexBanner.image)??> 
							<div class="col-lg-4">
								<a href="${domainUrlUtil.imageResources}${(mSellerIndexBanner.image)!''}" class='boxer'>查看图片</a>
							</div>
							</#if>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-10 ejava-errinforight">
								图片建议像素（或保持该比例）：宽808，高300
							</label>
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