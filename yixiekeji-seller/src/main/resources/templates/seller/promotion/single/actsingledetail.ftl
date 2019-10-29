<#include "/seller/commons/_head.ftl">

<script language="javascript">

$(function(){
	initMenu('single');
	
	$("button[type='button'].back").click(function(){
 		window.location.href= domain+"/seller/promotion/single";
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
						href="${domainUrlUtil.urlResources}/seller/promotion/single">单品立减</a>
					</li>
					<li class="active">查看详情</li>
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
						action="${domainUrlUtil.urlResources}/seller/promotion/single/update"
						enctype="multipart/form-data" data-bv-message="该项必填">
						<!-- 用于计算数量 -->
						<input type="hidden" id="productNum" name="productNum" value="${(productNum)!0}">
						<input type="hidden" id="id" name="id" value="${(actSingle.id)!''}">
						<input type="hidden" id="status" name="status" value="${(actSingle.status)!''}">
					
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动名称：</label>
							<div class="col-lg-4">
								<input class="form-control" type="text"
									id="actSingleName" name="actSingleName"
									value="${(actSingle.actSingleName)!''}"
									disabled="disabled"
                                     />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动类型：</label> 
							<div class="col-lg-4">
								<@cont.select id="type" value="${(actSingle.type)!''}"
									class="form-control"
									disabled="disabled"
									codeDiv="ACT_SINGLE_TYPE" mode="1"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">优惠额/折扣：</label> 
							<div class="col-lg-3">
								<input class="form-control" type="text"
									id="discount" name="discount" value="${(actSingle.discount)!''}"
									disabled="disabled"/>
							</div>	
							<label class="col-lg-7 ejava-errinforight">活动类型为减免金额时为金额（如10为减免10元），折扣类型时为折扣（如0.90为打九折）</label>
						</div>

						<div style="padding-top: 10px;">活动商品</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
						
						<div class="form-group ejformstyle">
						<#if productList??> <#list productList as product>
							<div class="addItemT">
								<div class="col-lg-4 pull-left">
									<label class="col-sm-3 control-label">商品名称：</label>
									<div class="col-sm-9">
									
									<input type="hidden" name="ids"
											value="${(product.id)!''}" /> 
									<input type="text"
											id="productName" name="productName" class="input-pro"
											disabled value="${(product.name1)!''}" />
									</div>
								</div>
								<div class="col-lg-3 pull-left">
									<label class="col-sm-4 control-label">商城价：</label>
									<div class="col-sm-8">
										<input type="text" id="mallPcPrice" name="mallPcPrice"
											disabled value="${(product.mallPcPrice)!''}" />
									</div>
								</div>
								<div class="col-lg-3 pull-left">
									<label class="col-sm-4 control-label">移动端价：</label>
									<div class="col-sm-8">
										<input type="text" id="malMobilePrice" name="malMobilePrice"
											disabled value="${(product.malMobilePrice)!''}" />
									</div>
								</div>
							</div>
						</#list> </#if>
						</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动时间：</label>
							<div class="col-lg-4">
								<input
									type="text" id="startTime" name="startTime"
									class="form-control" 
									disabled
									value="${(actSingle.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}"/> 
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime"
									name="endTime" class="form-control"
									disabled
									value="${(actSingle.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>应用渠道：</label>
							<div class="col-lg-4">
								<@cont.select id="channel" value="${(actSingle.channel)!''}" disabled="disabled"
									codeDiv="CHANNEL" class="form-control"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">活动描述：</label>
							<div class="col-lg-8">
								<textarea name="remark" id="remark" disabled
									class="form-control">${(actSingle.remark)!''}</textarea>
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


<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">