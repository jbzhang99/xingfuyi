<#include "/seller/order/orders/orderscommon.ftl">

<script language="javascript">
	
	$(function() {

		$('#btn_dev').click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			var state = selected.orderState;
			if(state != 3){
				$.messager.alert('提示', '订单不是可发货状态');
				return;
			}
			var width_ = '340';
		    if(ismobile()){
				width_ = "100%";
		    }
			$("#devWin").window({
				width :width_,
				height : 210,
				href : '${currentBaseUrl}/delivery?id='+selected.id,
				title : "发货",
				closed : true,
				shadow : false,
				modal : true,
				collapsible : false,
				minimizable : false,
				maximizable : false
			}).window('open');
		});
		
		//确认订单
		$("#a_comfirm").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			if(selected.paymentCode!='OFFLINE'){
				$.messager.alert('提示', '只有货到付款的订单才需要确认。');
				return;
			}
			if(selected.orderState!=2){
				$.messager.alert('提示', '只有待确认的订单才需要确认。');
				return;
			}
			$.messager.confirm('确认','确定要确认该订单吗？', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/confirm",
						dataType: "json",
					    data: "id=" + selected.id,
					    cache:false,
						success:function(data, textStatus){
							if (data.success) {
								$('#dataGrid').datagrid('reload');
						    } else {
						    	$.messager.alert('提示',data.message);
						    	$('#dataGrid').datagrid('reload');
						    }
							$.messager.progress('close');
						}
					});
				}
			});
		});

		//确认收款
		$("#a_submit_pay").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			if(selected.paymentCode!='OFFLINE'){
				$.messager.alert('提示', '只有货到付款的订单可确认收款。');
				return;
			}
			if(selected.orderState != 4 && selected.orderState != 5){
				$.messager.alert('提示', '已发货或者已完成的订单才能确认收款。');
				return;
			}
			$.messager.confirm('确认','确定收款吗？请在确认收到买家的付款后再进行此操作。', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/submitpay",
						dataType: "json",
					    data: "id=" + selected.id,
					    cache:false,
						success:function(data, textStatus){
							if (data.success) {
								$('#dataGrid').datagrid('reload');
						    } else {
						    	$.messager.alert('提示',data.message);
						    	$('#dataGrid').datagrid('reload');
						    }
							$.messager.progress('close');
						}
					});
				}
			});
		});
	});

	function styler(value,row,index){
		switch (row.orderState) {
		case 3:
			return  'color:red'; 
			break;
		case 6:
			return  'color:#959595'; 
			break;
		default:
			break;
		}
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
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
                    </li>
                    <li class="active">全部订单</li>
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
	                                  <label class="lab-item">订单号：</label>
		                               <input type="text" id="q_orderSn" 
		                               	name="q_orderSn" value="${q_orderSn!''}" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                      <label class="lab-item">订单状态：</label> 
                                     <@cont.select id="q_orderState"
										codeDiv="ORDERS_ORDER_STATE" value="${q_orderState!''}" 
										name="q_orderState"/>
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
											,rowStyler:styler
											,view: detailview
											,autoRowHeight:false
											,fitColumns:false
											,toolbar:'#gridTools'
											,detailFormatter:detailFormatter
											,onExpandRow:onExpandRow
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
									<th field="orderSn" width="150" align="center" >订单号</th>
									<th field="memberName" width="120" align="center" >买家用户名</th>
									<th field="sellerName" width="120" align="center" >店铺名称</th>
									<th field="moneyProduct" width="80" align="center">商品金额</th>
									<th field="moneyOrder" width="80" align="center">订单总金额</th>
									<th field="paymentStatus" width="70" align="center" formatter="paymentStatus">付款状态</th>
									<th field="orderState" width="80" align="center" formatter="getState">订单状态</th>
									<th field="invoiceStatus" width="70" align="center" formatter="invoiceStatus">发票状态</th>
									<th field="invoiceTitle" width="100" align="center">发票抬头</th>
									<th field="invoiceType" width="70" align="center">发票类型</th>
									<th field="paymentName" width="70" align="center">支付方式</th>
									<th field="logisticsName" width="80" align="center">物流名称</th>
									<th field="logisticsNumber" width="100" align="center">快递单号</th>
									<th field="deliverTime" width="150" align="center">发货时间</th>
									<th field="createTime" width="150" align="center">创建时间</th>
									<th field="updateTime" width="150" align="center">修改时间</th>
								</tr>
							</thead>
						</table>
				
					<div id="gridTools">
						<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						<a id="btn-print" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true">打印</a>
						<a id="btn-details" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">订单详情</a>
						<@shiro.hasPermission name="/seller/order/orders/confirm">
						<a id="a_comfirm" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">确认</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/order/orders/delivery">
						<a id="btn_dev" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">发货</a>
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