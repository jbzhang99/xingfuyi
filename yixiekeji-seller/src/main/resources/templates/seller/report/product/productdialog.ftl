<script type="text/javascript">
function proTitle(value,row,index){
    return "<font style='color:blue;cursor:pointer' title='"+
            value+"' onclick='openwin("+row.id+")'>"+value+"</font>";
}

function stateFormat(value,row,index){
    return codeBox["PRODUCT_STATE"][value];
}

function isTopFormat(value,row,index){
    return codeBox["PRODUCT_IS_TOP"][value];
}

<#noescape>
var codeBox = eval('(${initJSCodeContainer("PRODUCT_STATE","PRODUCT_IS_TOP")})');
</#noescape>

function submitProForm(){
	$('#productDataGrid').datagrid('reload',queryParamsHandler());
}
</script>

<div id="productDialog" class="easyui-dialog popBox" title="商品列表"
	style="width: 980px; height: 381px;"
	data-options="resizable:true,closable:true,closed:true,cache: false"
	buttons="#dlg-buttons-award-act">

	<input id="sel_seller" type="hidden" name="q_sellerId" value="${seller}">

	<div  data-options="fit:true,region:'center'" border="false" style="height:100%">
			<table id="productDataGrid" class="easyui-datagrid"
				data-options="
							rownumbers:true,
							singleSelect : true,
							pagination:true,
							fit:true,
							fitColumns:true,
							url:'${domainUrlUtil.urlResources}/seller/product/list?q_state=6',
							onLoadSuccess:dataGridLoadSuccess,
							method:'get'">
				<thead>
					 <tr>
			            <th field="name1" width="400" align="left" halign="center">商品名称</th>
			            <th field="seller" width="120" align="center">商家</th>
			            <th field="productCateName" width="100" align="center">商品分类</th>
			            <th field="productBrandName" width="90" align="center">商品品牌</th>
			            <th field="actualSales" width="70" align="center">销量</th>
			            <th field="state" width="60" align="center" formatter="stateFormat">状态</th>
			        </tr>
				</thead>
			</table>
	</div>
	
	<div id="dlg-buttons-award-act" style="text-align: right">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="submit()">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#productDialog').dialog('close')">取消</a>
	</div>
</div>

<script language="javascript">
	function submit() {
		var selectedRow = $("#productDataGrid").datagrid('getSelected');
		if (!selectedRow) {
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		var callbackfunc = eval('productCallBack');
		callbackfunc(selectedRow);
		$("#productDialog").dialog('close');
		$("#proid").change();
	}
</script>