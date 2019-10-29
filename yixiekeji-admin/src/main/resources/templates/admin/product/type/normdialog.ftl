<div id="normDialog" class="easyui-dialog popBox" title="商品规格列表"
	style="width: 980px; height: 520px;"
	data-options="resizable:true,closable:true,closed:true,cache: false,modal: true"
	buttons="#dlg-buttons-award-act">

	<div class="easyui-layout" data-options="fit:true">
                        <div id="tb" style="margin-top:25px;">
                        规格名称:
                        <a id="standardQueryBtn1" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>  
                        </div>
			<table id="normDataGrid" class="easyui-datagrid"
				data-options="
                                                        rowStyler: function (index, row) { if (hideIndexs.indexOf(index)>=0) { return 'background:red; display:none'; }},
							rownumbers:true,
							autoRowHeight:false,
							striped : true,
							singleSelect : false,
							fit:true,
							fitColumns:true,
							onLoadSuccess:normloadSuccess,
							url:'${domainUrlUtil.urlResources}/admin/product/norm/list_no_page',
							method:'get'">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="name" width="150" align="center">规格名称</th>
						<th field="createTime" width="150" align="center">创建时间</th>
						<th field="sort" width="60" align="center">排序</th>
					</tr>
				</thead>
			</table>
	</div>
	
	<div id="dlg-buttons-award-act" style="text-align: right">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="submit()">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#normDialog').dialog('close')">取消</a>
	</div>
</div>

<script language="javascript">
	function submit() {
		var selectedRow = $("#normDataGrid").datagrid('getSelections');
		if (!selectedRow || selectedRow.length == 0) {
			$.messager.alert('友情提示', '请至少选择一个对象');
			return false;
		}
		var callbackfunc = eval('normCallBack');
		callbackfunc(selectedRow);
		$("#normDialog").dialog('close');
	}


                     //要隐藏的行的索引集合.
        var hideIndexs = new Array();
        $('#standardQueryBtn1').searchbox({
            width: 200,
            searcher: function (value) {
                hideIndexs.length = 0;
                if (value == '请输入查询内容') {
                    value = '';
                }
                //结束datagrid的编辑.
                endEdit();
                var rows = $('#normDataGrid').datagrid('getRows');

                var cols = $('#normDataGrid').datagrid('options').columns[0];

                for (var i = rows.length - 1; i >= 0; i--) {
                    var row = rows[i];
                    var isMatch = false;
                    for (var j = 0; j < cols.length; j++) {

                        var pValue = row[cols[j].field];
                        if (pValue == undefined) {
                            continue;
                        }
                        if (pValue.toString().indexOf(value) >= 0) {
                            isMatch = true;
                            break;
                        }
                    }
                    if (!isMatch)
                        hideIndexs.push(i);
                      //刷新行,否则不能看到效果.
                    $('#normDataGrid').datagrid('refreshRow', i);
                }


            },
            prompt: '请输入查询内容'
        });
        function endEdit() {
            var rows = $('#normDataGrid').datagrid('getRows');
            for (var i = 0; i < rows.length; i++) {
                $('#normDataGrid').datagrid('endEdit', i);
            }
        }
</script>