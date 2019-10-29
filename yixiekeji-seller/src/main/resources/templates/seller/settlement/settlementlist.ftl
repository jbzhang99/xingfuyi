<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/settlement"/>

<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/easyui/datagrid-detailview.js"></script>

<style>
#newstypeTree img {
	max-width: 390px;
	max-height: 290px;
}
</style>

<script language="javascript">
	var codeBox;
	var codeBoxSale;
	$(function() {

		<#noescape>
			codeBox = eval('(${initJSCodeContainer("SETTLEMENT_STATUS","SETTLE_OTHER_TYPE")})');
			codeBoxSale = eval('(${initJSCodeContainer("SALE_SETTING")})');
		</#noescape>

		$("#btn-detail").click(function(){
			var selectedCode = $('#dataGrid').datagrid('getSelected');
			if(!selectedCode){
				$.messager.alert('提示','请选择操作行。');
				return;
			}	
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/settlement/detail?id="+selectedCode.id;
		});	
		
		// 查询按钮
		$('#btn-search').click(function() {
			$('#dataGrid').datagrid('reload', queryParamsHandler());
		});

		<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
	});

	function settlementStatus(value, row, index) {
		var box = codeBox["SETTLEMENT_STATUS"][value];
		return box;
	}
	
	function otherType(value, row, index) {
		var box = codeBox["SETTLE_OTHER_TYPE"][value];
		return box;
	}
	
	function saleSetting(value, row, index) {
		var box = codeBoxSale["SALE_SETTING"][value];
		return box;
	}
	
</script>

<div id="devWin"></div>
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
                    <li class="active">结算管理</li>
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
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label class="lab-item">结算周期：</label> <input type="text"
											id="q_settleCycle" name="q_settleCycle" value="${q_settleCycle!''}" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label class="lab-item">结算状态：</label> <@cont.select id="q_status"
											codeDiv="SETTLEMENT_STATUS" value="${q_status!''}" name="q_status" />
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
                </div>
               	<div data-options="region:'center'" border="false">
					<table id="dataGrid" class="easyui-datagrid"
						data-options="rownumbers:true
										,idField :'id'
										,singleSelect:true
										,autoRowHeight:false
										,fitColumns:false
										,toolbar:'#gridTools'
										,striped:true
										,pagination:true
										,pageSize:'${pageSize}'
										,fit:true
					    					,url:'${currentBaseUrl}/list'
					    					,queryParams:queryParamsHandler()
					    					,onLoadSuccess:dataGridLoadSuccess
					    					,method:'get'">
						<thead>
							<tr>
								<th field="id" hidden="hidden"></th>
								<th field="settleCycle" width="150" align="center">结算周期</th>
								<th field="sellerName" width="150" align="center">店铺</th>
								<th field="moneyOrder" width="100" align="center">订单总额</th>
								<th field="moneyPaidBalance" width="100" align="center">余额支付总额</th>
								<th field="moneyPaidReality" width="100" align="center">现金支付总额</th>
								<th field="moneyBack" width="100" align="center">退款总额</th>
								<th field="moneyIntegralBack" width="100" align="center">退会积分金额总额</th>
								<th field="moneyOther" width="100" align="center">其他金额</th>
								<th field="moneyOtherType" width="100" align="center" formatter="otherType">其他金额类型</th>
								<th field="moneyOtherReason" width="100" align="center">其他金额理由</th>
								<th field="commision" width="100" align="center">佣金总额</th>
								<th field="moneySale" width="100" align="center">三级分销佣金总额</th>
								<th field="saleSet" width="100" align="center" formatter="saleSetting">三级分销佣金承担</th>
								<th field="payable" width="100" align="center">系统计算总额</th>
								<th field="status" width="100" align="center" formatter="settlementStatus">结算状态</th>
							</tr>
						</thead>
					</table>
				
					<div id="gridTools">
						<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						<@shiro.hasPermission name="/seller/settlement/detail">
						<a id="btn-detail" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">详情</a>
						</@shiro.hasPermission>
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