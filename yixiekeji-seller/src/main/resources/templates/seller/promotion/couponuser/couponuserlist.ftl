<#include "/seller/commons/_head.ftl">

<script language="javascript">

	$(function(){
		initMenu('coupon');

		$("#a-gridSearch").click(function(){
	 		$('#dataGrid').datagrid('load',queryParamsHandler());
		});
		
		$("#a-gridBack").click(function(){
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/promotion/coupon";
		});

		$("#a-gridDoExport").click(function(){
			$.messager.confirm('提示', '确定导出该优惠券的发放详情吗？', function(r){
	            if (r){
	            	$.fileDownload('${domainUrlUtil.urlResources}/seller/promotion/couponuser/doexport',{data:queryParamsHandler()});
	            }
	        });
		});
        
		<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
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
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
                    </li>
                    <li>
                    	<a href="${domainUrlUtil.urlResources}/seller/promotion/coupon">优惠券管理</a>
                    </li>
                    <li class="active">发放详情</li>
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
                    <form class="from-ff">
                    	<input type="hidden" id="q_couponId" name="q_couponId" value="${(coupon.id)!''}"/>
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                 	<label>领取日期：</label>
				                    	<input type="text" id="q_receiveTimeStart" name="q_receiveTimeStart"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'q_receiveTimeEnd\')}'});"/>
                                  		<span class="space-dot">~</span>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
										<input type="text" id="q_receiveTimeEnd" name="q_receiveTimeEnd"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'q_receiveTimeStart\')}'});"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                	<label>使用日期：</label>
					                    <input type="text" id="q_useTimeStart" name="q_useTimeStart"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'q_useTimeEnd\')}'});"/>
                                 		<span class="space-dot">~</span>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
										<input type="text" id="q_useTimeEnd" name="q_useTimeEnd"
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'q_useTimeStart\')}'});"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
                </div>
               	<div data-options="region:'center'" border="false">
						<table id="dataGrid" class="easyui-datagrid"
							data-options="rownumbers:true
										,singleSelect:true
										,autoRowHeight:false
										,fitColumns:true
										,collapsible:true
										,toolbar:'#gridTools'
										,striped:true
										,pagination:true
										,pageSize:'${pageSize}'
										,fit:true
				    					,url:'${(domainUrlUtil.urlResources)!}/seller/promotion/couponuser/list'
				    					,queryParams:queryParamsHandler()
				    					,onLoadSuccess:dataGridLoadSuccess
				    					,method:'get'">
						<thead>
							<tr>
								<th field="memberName" width="100" align="left" halign="center">会员名称</th>
								<th field="couponSn" width="150" align="left" halign="center">序列号</th>
								<th field="canUse" width="100" align="center" halign="center">可使用次数</th>
								<th field="receiveTime" width="100" align="center" halign="center">领取时间</th>
								<th field="orderSn" width="100" align="left" halign="center">订单Sn</th>
								<th field="useTime" width="100" align="center" halign="center">使用时间</th>
							</tr>
						</thead>
					</table>
					<div id="gridTools">
						<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						<a id="a-gridBack" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">返回优惠券列表</a>
						<a id="a-gridDoExport" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true">导出</a>
					</div>
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