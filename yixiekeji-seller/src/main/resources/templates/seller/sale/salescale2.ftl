<#include "/seller/commons/_head.ftl">
<script language="javascript">
$(function(){
	$('#addform').bootstrapValidator();

	initMenu('onsale');
	
	<#if message??>$.messager.progress('close');alert('${message}');</#if>
	
	$(".back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/product/onSale";
	});
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
					<li class="active">设置佣金</li>
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
						action="${domainUrlUtil.urlResources}/seller/sale/salescale/salescaleproduct"
						enctype="multipart/form-data" data-bv-message="该项必填"
						data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
                        data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
                        data-bv-feedbackicons-validating="glyphicon glyphicon-refresh"
						>
						<input type="hidden" id="productId" name="productId" value="${(product.id)!''}">
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品名称：</label>
							<div class="col-lg-4">
								<label class="control-label">${(product.name1)!}</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>一级佣金: </label> 
							<div class="col-lg-4">
							<input
								type="text" id="opt_saleScale1" name="opt_saleScale1" value="${(product.saleScale1)!''}"
								class="form-control" 
								required
								data-bv-numeric="true"
								data-bv-numeric-message="请输入正确的佣金保留小数点2位"
								min="0"
								max="1"
								pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                         	  	data-bv-regexp-message="佣金保留两位小数" 
								data-bv-lessthan-inclusive="true"
                                data-bv-lessthan-message="佣金必须小于1"
                                data-bv-greaterthan-inclusive="true"
                               	data-bv-greaterthan-message="佣金必须大于01" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>二级佣金: </label> 
							<div class="col-lg-4">
								<input
									type="text" id="opt_saleScale2" name="opt_saleScale2" value="${(product.saleScale2)!''}"
									class="form-control" 
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的佣金保留小数点2位"
									min="0"
									max="1"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
	                         	  	data-bv-regexp-message="佣金保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="佣金必须小于1"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="佣金必须大于0"  />
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