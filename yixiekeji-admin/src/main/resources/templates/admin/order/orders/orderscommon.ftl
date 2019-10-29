<#include "/admin/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/order/orders"/>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/datagrid-detailview.js"></script>
<!-- 订单列表公共js方法 -->
<script>
var codeBox;
$(function (){
	<#noescape>
		codeBox = eval('(${initJSCodeContainer("ORDERS_ORDER_STATE","ORDER_PAYMENT_STATUS","ORDER_INVOICE_STATUS")})');
	</#noescape>
	
	// 查询按钮
	$('#btn-search').click(function() {
		$('#dataGrid').datagrid('reload', queryParamsHandler());
	});
	
	// 订单打印
	$('#btn-print').click(function() {
		var selected = $('#dataGrid').datagrid('getSelected');
		if (!selected) {
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		window.open("${currentBaseUrl}/print?id="+selected.id);
	});
	
	//订单详情
	$("#btn-details").click(function (){
		var selected = $('#dataGrid').datagrid('getSelected');
		if(!selected){
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		location.href="${domainUrlUtil.urlResources}/admin/order/orders/details?id="+selected.id;
	});
	
});

function getState(value, row, index) {
	var box = codeBox["ORDERS_ORDER_STATE"][value];
	return box;
}

function paymentStatus(value, row, index) {
	var box = codeBox["ORDER_PAYMENT_STATUS"][value];
	return box;
}

function invoiceStatus(value, row, index) {
	var box = codeBox["ORDER_INVOICE_STATUS"][value];
	return box;
}

function detailFormatter(index,row){
    return '<div style="padding:2px"><table class="ddv"></table></div>';
}

function onExpandRow(index,row){
    var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
    ddv.datagrid({
       fitColumns:true,
       singleSelect:true,
       method:'get',
       url:'${domainUrlUtil.urlResources}/admin/order/ordersProduct/getOrdersProduct?orderId='+row.id,
		loadMsg : '数据加载中...',
		height : 'auto',
		columns : [[{
			field : 'productName',
			title : '货品名称',
			width : 120,
			align : 'left',
			halign : 'center'
		}, {
			field : 'specInfo',
			title : '规格',
			width : 70,
			align : 'left',
			halign : 'center'
		}, {
			field : 'productSku',
			title : '商品SKU',
			width : 80,
			align : 'left',
			halign : 'center'
		}, {
			field : 'moneyPrice',
			title : '商品单价',
			width : 50,
			align : 'center'
		}, {
			field : 'number',
			title : '商品数量',
			width : 50,
			align : 'center'
		}, {
			field : 'moneyAmount',
			title : '网单金额',
			width : 50,
			align : 'center'
		}]],
		onResize : function() {
			$('#dataGrid').datagrid('fixDetailRowHeight',index);
		},
		onLoadSuccess : function() {
			setTimeout(function() {
				$('#dataGrid').datagrid('fixDetailRowHeight',index);
			}, 0);
		}
	});
}
</script>
<!-- 打印加载窗口 -->
<div id="devWin"></div>