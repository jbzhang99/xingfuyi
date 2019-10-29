<#import "/seller/commons/_macro_controller.ftl" as cont/>

<script>
	var codeBox1;
	var selected = {};
	$(function() {
		<#noescape>
		codeBox1 = eval('(${initJSCodeContainer("PRODUCT_STATE","PRODUCT_IS_TOP")})');
		</#noescape>

		$("#saveRuleRes").click(function(){
			if(!selected.name || !selected.id){
				$.messager.alert('提示','请选择一个分类');
				return false;
			}
			$('#product_case_name').val(selected.name);
			$('#product_case_id').val(selected.id);
			closeWin();
		});
		
		$('#product_case_name').click(function() {
			//init
		    var width_ = '350';
		    if(ismobile()){
				width_ = "100%";
		    }
			selected = new Object();
			$("#caseWin").window({
				width : width_,
				height : 480,
				title : "商品分类",
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false
			});

			$("#productCaseTree")
					.tree(
							{
								method : "get",
								cascadeCheck : false,
								onClick : function(node) {
									if(selected){
										selected.name = node.text;
										selected.id = node.id;
									}
								},
								onLoadSuccess : function() {
									$("#resourceTree").tree(
											'expandAll');
								},
								url : "${domainUrlUtil.urlResources}/seller/product/cate/productCaseTree?id=0"
							});
						});

		// 查询按钮
		$('#gd_btn-search').click(function() {
			$('#gd_dataGrid').datagrid('reload', queryParamsHandler());
		});
	});

	function isTopFormat(value, row, index) {
		return codeBox1["PRODUCT_IS_TOP"][value];
	}

	function closeWin() {
		$("#caseWin").window('close');
	}

	function stateFormat(value, row, index) {
		return codeBox1["PRODUCT_STATE"][value];
	}
	
	function loadSuccess(data){
		dataGridLoadSuccess(data,this);
		//初始化选中的数据
		for(var i=0;i<data.rows.length;i++){
			var rowsdata = data.rows[i];
			if(selectedData.length > 0){
				$.each(selectedData,function(idx_,item_){
					if(item_.id == rowsdata.id){
						log.i('加载默认数据：'+item_);
						$('#gd_dataGrid').datagrid("selectRow", i);  
						return false;
					}
				});
			}
		}
	}
	
	var selectedData = new Array();
	function selectedsubmit(){
		if(selectedData.length < 1){
			$.messager.alert('提示','请选择商品');
		}

		var num = selectedData.length;
		if (num > 20) {
			$.messager.alert('提示', '同一个活动最多选择20个商品。');
			return;
		}

		var prdHtml = "";
		$.each(selectedData,function(index, item) {
			prdHtml += 	'<div class="addItemT form-group">'+
						'	<div class="col-lg-4 pull-left">'+
						'		<label class="col-sm-3 control-label">商品名称：</label>'+
						'		<div class="col-sm-9">'+
						'			<input type="hidden" name="ids" value="'+item.id+'" /> <input'+
						'				type="text" id="productName" name="productName"'+
						'				class="input-pro form-control" readonly'+
						'				value="'+item.name1+'" />'+
						'		</div>'+
						'	</div>'+
						'	<div class="col-lg-3 pull-left">'+
						'		<label class="col-sm-4 control-label">商城价：</label>'+
						'		<div class="col-sm-8">'+
						'			<input type="text" id="mallPcPrice" name="mallPcPrice" readonly'+
						'				class="form-control" value="'+item.mallPcPrice+'" />'+
						'		</div>'+
						'	</div>'+
						'	<div class="col-lg-3 pull-left">'+
						'		<label class="col-sm-4 control-label">移动端价：</label>'+
						'		<div class="col-sm-8">'+
						'			<input type="text" id="malMobilePrice" name="malMobilePrice" readonly'+
						'				class="form-control" value="'+item.malMobilePrice+'" />'+
						'		</div>'+
						'	</div>'+
						'	<div class="col-lg-2 pull-right">'+
						'		<button type="button" class="a-del-proItem btn btn-del btn-primary">删除</button>'+
						'	</div>'+
						'</div>';
				});
		$(".ejformstyle").html(prdHtml);

		$("#productNum").val(num);
		closeProWin();
	}
	
	function closeProWin(){
    	$('#goodsListDiv').window('close');
	}
	
	function unselectAll(){
		 $.messager.confirm('提示', '确定删除所有已选择的商品吗？', function(r){
			 if(r){
				$('#gd_dataGrid').datagrid('unselectAll');
				selectedData = new Array();
			 }
		 });
	}
	
	function onClickRow(rowIndex, rowData){
		//单击某行，保存该行数据
		var checkedData = $("#gd_dataGrid").datagrid('getChecked');
		//该行被选中
		if($.inArray(rowData, checkedData) > -1){
			log.i("push..");
			selectedData.push(rowData);
		} else{
			log.i("del..");
			for(var i=0;i< selectedData.length;i++){
				if(selectedData[i].id== rowData.id){
					selectedData.splice(i,1);
				}
			}
		}
	}
</script>

<div data-options="region:'north'"
	style="margin: 0 auto; min-height: 10%" border="false">
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont">
			<form class="form-search" action="doForm" method="post"
				id="gd_queryForm" name="queryForm">
				<div class="querycond">
					<label>商品分类:</label> <input type="text" class="txt"
						id="product_case_name" /> <a
						onclick="$('#product_case_name').val('');$('#product_case_id').val('');"
						class="easyui-linkbutton" iconCls="icon-delete">清空</a> <input
						type="hidden" name="q_productCateId" id="product_case_id" /> <input
						type="hidden" name="q_sellerState" value="1" /> <input
						type="hidden" name="q_productCateState" value="1" /> <input
						type="hidden" name="q_state" value="6" />
					<!-- <input type="hidden" name="q_isTop" value="2"/> -->
					<input type="hidden" name="q_sellerId" value="${(sellerId)!0}" />
				</div>
			</form>
		</div>
	</div>
</div>

<div data-options="region:'center'" border="false"
	style="height: 370px;" buttons="#dlg-buttons-goods">
	<table id="gd_dataGrid" class="easyui-datagrid"
		data-options="rownumbers:true,
							autoRowHeight:false,
							striped : true,
							pagination : true,
							singleSelect : false,
							fit:true,
							pageSize: 10,  
              				pageList: [10, 20, 30],  
							fitColumns:false,
							toolbar:'#goodsDialogTools',
							onClickRow:onClickRow,
							url:'${domainUrlUtil.urlResources}/seller/product/list',
							queryParams:queryParamsHandler(),
							onLoadSuccess:loadSuccess,
							method:'get'">
		<thead>
			<tr>
				<th field="name1" width="260" align="left" halign="center">商品名称</th>
				<th field="productCateName" width="170" align="center">商品分类</th>
				<th field="productBrandName" width="90" align="center">商品品牌</th>
				<th field="mallPcPrice" width="70" align="center">商城价</th>
				<th field="malMobilePrice" width="90" align="center">商城价(移动端)</th>
				<th field="isTop" width="90" align="center" formatter="isTopFormat">是否推荐</th>
				<th field="upTime" width="180" align="center">上架时间</th>
			</tr>
		</thead>
	</table>

</div>

<div id="goodsDialogTools">
	<a id="gd_btn-search" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	<a id="unselectAll" href="javascript:void(0)" onclick="unselectAll();"
		class="easyui-linkbutton" iconCls="icon-fail" plain="true">清除所有选择商品</a>
</div>

<div id="dlg-buttons-good" style="text-align: right;padding:7px;">
	<em style="font-style: normal;float: left;">
		您可以选择多个商品（再次点击取消选择），选择结束后，请点击[选择]按钮以返回；点击[取消]按钮以退出选择并关闭此对话框
	</em>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
		onclick="selectedsubmit()" style="margin-right:10px;">选择</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" 
		iconCls="icon-cancel" onclick="closeProWin()">取消</a>
</div>

<div id="caseWin" style="overflow: hidden;display: none">
	<form id="productCaseForm" method="post">
		<div style="margin-top: 5px;">
			<label style="font-weight: bold; margin-left: 15px;">商品分类 </label> <input
				id="resIds" type="hidden" name="resIds"> <input id="roleId"
				type="hidden" name="roleId" value="${id!}">
		</div>

		<ul id="productCaseTree"
			style="margin-top: 10px; margin-left: 10px; max-height: 370px; overflow: auto; border: 1px solid #86a3c4;">
			<div style="padding: 12px 140px; text-align: center;">数据加载中...</div>
		</ul>
		<div>
			<p class="p-item p-btn p-btn-save">
				<a id="saveRuleRes" class="easyui-linkbutton" iconCls="icon-save">选择</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-delete" onclick="closeWin();">关闭</a>
			</p>
		</div>
	</form>
</div>
