<#include "/seller/commons/_head.ftl">

<script language="javascript">
$(function(){
	$('#addform').bootstrapValidator();
	initMenu('saleScale');
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
						action="${domainUrlUtil.urlResources}/seller/sale/salescale/update"
						enctype="multipart/form-data" data-bv-message="该项必填"
						data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
                        data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
                        data-bv-feedbackicons-validating="glyphicon glyphicon-refresh"
						>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>一级佣金: </label> 
							<div class="col-lg-4">
							<input
								type="text" id="saleScale1" name="saleScale1" value="${(seller.saleScale1)!''}"
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
									type="text" id="saleScale2" name="saleScale2" value="${(seller.saleScale2)!''}"
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
							<label class="col-lg-2 control-label"><font class="red">*</font>是否启用：</label> 
							<div class="col-lg-4">
								<@cont.select id="state" mode="-1" codeDiv="USE_YN"
									name="state" class="txt" value="${(seller.state)!''}" class="form-control"/>
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