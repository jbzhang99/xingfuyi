$(function() {
	$('#btn_add').click(function() {
		location.href = currentBaseUrl+'/add';
	});
	
	$('#btn_edit').click(function() {
		var selected = $('#dataGrid').datagrid('getSelected');
		if (!selected) {
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		location.href = currentBaseUrl+'/edit?id='+selected.id;
	});
	
	// 查询按钮
	$('#btn-search').click(function(){
		$('#dataGrid').datagrid('reload',queryParamsHandler());
	});
	
	$('#btn_del').click(function() {
		var selected = $('#dataGrid').datagrid('getSelected');
		if (!selected) {
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		if(selected.state == 1){
			$.messager.alert('提示', '启用状态下的模板不能删除。');
			return;
		}
		$.messager.confirm('确认', '确定删除该模板吗？此操作不可撤销', function(r) {
			if (r) {
				$.messager.progress({
					text : "提交中..."
				});
				$.ajax({
					url : currentBaseUrl+'/del',
					data:{id:selected.id},
					success : function(e) {
						$('#dataGrid').datagrid('reload');
						$.messager.progress('close');
						$.messager.show({
							title:'提示',
							msg:e,
							showType:'show'
						});
					}
				});
			}
		});

	});
	
	//启用
	$('#btn-inuse').click(function() {
		var selected = $('#dataGrid').datagrid('getSelected');
		if (!selected) {
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		var state = selected.state;
		if(state == 1){
			$.messager.alert('提示', '该模板已启用，请勿重复操作！');
			return;
		}
		$.messager.confirm('确认', '确定启用该模板吗?', function(r) {
			if (r) {
				$.messager.progress({
					text : "提交中..."
				});

				$.ajax({
					type:"GET",
				    url: currentBaseUrl+"/inuse",
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
	
	//禁用
	$('#btn-disable').click(function() {
		var selected = $('#dataGrid').datagrid('getSelected');
		if (!selected) {
			$.messager.alert('提示', '请选择操作行。');
			return;
		}
		var state = selected.state;
		if(state == 2){
			$.messager.alert('提示', '该模板已禁用，请勿重复操作！');
			return;
		}
		$.messager.confirm('确认', '确定禁用该模板吗?', function(r) {
			if (r) {
				$.messager.progress({
					text : "提交中..."
				});

				$.ajax({
					type:"GET",
				    url: currentBaseUrl+"/disable",
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

function detailFormatter(index,row){
    return '<div style="padding:2px"><table class="ddv"></table></div>';
}

function onExpandRow(index,row){
	var type = row.transType;
	var trans_weight = "";
	var trans_fee = "";
	var trans_add_weight = "";
	if(type ==1){
		trans_weight = "首件(件)";
		trans_fee = "首费(元)";
		trans_add_weight = "续件(件)";
	} else{
		trans_weight = "首重(kg)";
		trans_fee = "运费(元)";
		trans_add_weight = "续重(kg)";
	}
	var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
	ddv.datagrid({
		fitColumns:true,
		singleSelect:true,
		rowStyler:function(index,row){
			if(index%2 == 0)
				return "background-color:#FFF";
			else
				return "background-color:#FAFAFA";
		},
		method:'get',
		url:currentBaseUrl+'/getTransport?id='+row.id,
		loadMsg : '数据加载中...',
		height : 'auto',
		emptyMsg: '没有数据',
		columns : [[{
			field : 'type',
			align : 'center',
			title : '运送方式',
			width : 30
		}, {
			field : 'city_name',
			title : '运送到',
			align : 'left',
			halign: 'center',
			width : 220
		}, {
			field : 'trans_weight',
			align : 'center',
			title : trans_weight,
			width : 50
		}, {
			field : 'trans_fee',
			align : 'center',
			title : trans_fee,
			width : 50
		}, {
			field : 'trans_add_weight',
			align : 'center',
			title : trans_add_weight,
			width : 50
		}, {
			field : 'trans_add_fee',
			align : 'center',
			title : '续费(元)',
			width : 50
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

function imageFormat(value, row, index) {
	return "<a class='newstype_view' onclick='showimg($(this).attr(\"imgpath\"));' href='javascript:;' imgpath='"
			+ value + "'>点击查看</a>";
}

function getState(value, row, index) {
	var box = codeBox["TRANSPORT_STATE"][value];
	return box;
}

function transType(value, row, index) {
	var box = codeBox["TRANSPORT_TYPE"][value];
	return box;
}

function transTime(value, row, index) {
	return codeBox["TRANSPORT_TIME"][value];
}

