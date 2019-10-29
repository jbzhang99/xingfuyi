<#include "/seller/commons/_head.ftl">

<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/theme.js'></script>
<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/echarts.js'></script>

<#include "/seller/commons/_echartsheader.ftl" />

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
					<li class="active">购买率统计</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->
			</div>
			<!-- 主体头部结束 -->
			<!-- Page Body -->
			<div class="page-body">
				<div id="bodyejavashop" class="easyui-layout ejava-easyui-layout"
					data-options="fit:true,split:false" style="height: 300px;">
					<div class="whtitdiv" data-options="region:'north'"
						style="padding-top: 10px; overflow-x: hidden; overflow-y: auto;">
						<!-- <table id="part1">12341234</table> -->
						<form class="from-ff"
							action="${domainUrlUtil.urlResources}/seller/report/product/phurchaseRate"
							method="get" id="queryForm" name="queryForm">

							<input type="hidden" name="productId" id="proid"
								value="${productId!}" /> <input type="hidden" name="proName"
								id="hd_proName" value="${proName!}" />

							<div class="row">
								<div class="col-lg-12 col-sm-12 col-xs-12">
									<div class="row row-mgbot">
										<div class="col-lg-4 col-sm-6 col-xs-12">
											<label>统计方式：</label> 
											<input type="radio"
												name="model" value="year"<#if
												!model??||model=='year'>checked="checked"</#if> id="r_year"/>按年份
											
											<input type="radio"
												name="model" value="month" id="r_month"<#if
												model??&&model=='month'>checked="checked"</#if>/>按月份
										</div>
										
										<div class="col-lg-4 col-sm-6 col-xs-12 year">
											<label class="lab-item-cho">年份：</label> <input
												onclick="WdatePicker({dateFmt:'yyyy'});" type="text" class="txt"
												id="year" name="year" value="${currentYear}" />
										</div>
										<div class="col-lg-4 col-sm-6 col-xs-12 month"
											style="display: none">
											<label class="lab-item-cho">月份：</label> <input
												onclick="WdatePicker({dateFmt:'MM'});" type="text" class="txt"
												id="month" name="month" value="${currentMonth}" />
										</div>
										<div class="col-lg-4 col-sm-6 col-xs-12">
											<input type="submit" id="doSearch" class="btn btn-danger btn-primary" value="提交" />
										</div>
									</div>
								</div>
							</div>

						</form>
					</div>
					<div data-options="region:'center'" border="false">
						<div id="chartdiv" style="width: 100%; height: 407px;"></div>
					</div>
					<div data-options="region:'south'" style="height:100px;">
						<dl class="dl-group" style="margin: 10px;">
							<dt class="dt-group">
								<span class="s-icon"></span>帮助
							</dt>
							<dd class="dd-group">
								<div class="fluidbox">
									<label class="lab-item help"> 购买率统计展示网店在一段时间内的有效订单数与这些订单相关的总访客数的比值；
										可以按照年度和月份为单位，分别进行查询 。 </label>
								</div>
								<div class="fluidbox">
									<label class="lab-item help"> 按月查询时，数据为当前年、所选月份的具体订单及销售情况 </label>
								</div>
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<#include "/seller/commons/_listautoheight.ftl"> 
<#include "/seller/commons/_end.ftl">