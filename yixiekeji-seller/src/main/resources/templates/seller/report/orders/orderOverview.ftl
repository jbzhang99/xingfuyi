<#include "/seller/commons/_head.ftl">

<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/theme.js'></script>
<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/echarts.min.js'></script>

<#include "/seller/commons/_echartsheader.ftl" />

<script>
	$(function(){
		$("#doSearch").click(function(){
			if(!$("#startTime").val()){
				$.messager.alert('提示', '请选择开始时间');
				return;
			}
			if(!$("#endTime").val()){
				$.messager.alert('提示', '请选择结束时间');
				return;
			}
			$("#queryForm").submit();
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
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
                    </li>
                    <li class="active">订单概况</li>
                </ul>
                
                <!-- 头部按钮开始 -->
                <#include "/seller/commons/_headerbuttons.ftl">
                <!-- 头部按钮结束 -->
            </div>
            <!-- 主体头部结束 -->
            <!-- Page Body -->
            <div class="page-body">
              <div id="bodyejavashop" class="easyui-layout ejava-easyui-layout" data-options="fit:true,split:false" style="height:300px; " >
                <div class="whtitdiv" data-options="region:'north'" style="padding-top: 10px;overflow-x:hidden;overflow-y:auto;">
                    <!-- <table id="part1">12341234</table> -->
                    <form class="from-ff"
						action="${domainUrlUtil.urlResources}/seller/report/orders/orderOverview"
						method="get" id="queryForm" name="queryForm">
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label class="lab-item-cho">开始时间：</label> <input
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'});"
											type="text" class="txt" id="startTime" name="startTime"
											value="${startTime}" />
                      					 <span class="space-dot">~</span>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label class="lab-item-cho">结束时间：</label> <input
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'});"
											type="text" class="txt" id="endTime" name="endTime"
											value="${endTime}" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<input type="button" id="doSearch" class="btn btn-danger btn-primary" value="提交" />
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
                </div>
               	<div data-options="region:'center'" border="false">
					 <div id="chartdiv" style="width: 100%; height: 400px;"></div>
				</div>
				<div data-options="region:'south'" style="height:100px;">
					<dl class="dl-group" style="margin: 10px;">
						<dt class="dt-group">
							<span class="s-icon"></span>帮助
						</dt>
						<dd class="dd-group">
							<div class="fluidbox">
								<label class="lab-item help">
									订单概况展示平台所有商家在一段时间内的订单状态分布情况，例如：待确认的订单，已完成的订单等。 </label>
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