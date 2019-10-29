<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/order/orders"/>

<script language="javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/lodop/LodopFuncs.js"></script>
<script language="javascript" type="text/javascript"> 
    var LODOP; //声明为全局变量       
	function myBlankDesign() {
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
// 		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='${(domainUrlUtil.imageResources)!}/${(courierCompany.imagePath)!}'>");
// 		<#noescape>
// 		${(courierCompany.content)!''}
// 		</#noescape>
// 		LODOP.PRINT_DESIGN();
	};
	
	$(function(){
		initMenu('ordersstate4');
	});
	
	function toback(){
		window.location.href="${domainUrlUtil.urlResources}/seller/order/orders/state4";
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
						href="${domainUrlUtil.urlResources}/seller/order/orders/state4">已发货订单</a>
					</li>
					<li class="active">设计打印模板</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="whtitdiv" data-options="region:'north'"
					style="padding-top: 10px; overflow-x: hidden; overflow-y: auto;">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div style="padding-top: 10px;">基本信息</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

						<@form.form method="post" class="form-horizontal" id="addform"
						name="addform" enctype="multipart/form-data"
						action="${currentBaseUrl}/doAudit"> 
						<input type="hidden" value="${(orders.id)!''}" name="id"> 
						<input type="hidden" id="stateType" name="stateType">
						<div class="form-group">
							<label class="col-lg-2 control-label">收件人姓名：</label>
							<div class="col-lg-8">
								<span class="info-span">${(consigneeName)!''}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">收件人电话：</label>
							<div class="col-lg-8">
								<span class="info-span">${(consigneePhone)!''}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">物流公司：</label>
							<div class="col-lg-8">
								<span class="info-span">${(courierCompany.companyName)!''}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">快递单号：</label>
							<div class="col-lg-8">
								<span class="info-span">${(orders.logisticsNumber)!''}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">收件人地址：</label>
							<div class="col-lg-8">
								<span class="info-span">${(consigneeAdds)}</span>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-3">
								<button type="button" class="btn btn-danger btn-primary"
									onclick="myBlankDesign()">打印</button>
								<button type="button" onclick="toback();" class="btn btn-danger back btn-primary">返回</button>
							</div>
						</div>
						</@form.form>

					</div>
				</div>
				<div data-options="region:'south'" style="height: 100px;">
					<dl class="dl-group" style="margin: 10px;">
						<div style="padding-top: 10px;">帮助信息</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
						<dd class="dd-group">
							<div class="fluidbox">
								<label class="lab-item help"> 
								<font style="color:red">打印快递单目前只支持Windows Internet Explorer 7+，请使用IE或其内核浏览器打印</font><br /> 
								Lodop 官网：http://www.lodop.net/ ，版本：6.198<br />
								第一次使用需要安装浏览器插件 <a
									href="${(domainUrlUtil.staticResources)!}/resources/seller/lodop/Lodop.zip"><b>下载</b></a>
									<br /> 也可以去Lodop下载插件，下载地址：<a href="http://www.lodop.net/download.html" target="_blank">http://www.lodop.net/download.html</a><br />
								</label>
							</div>
						</dd>
					</dl>
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
