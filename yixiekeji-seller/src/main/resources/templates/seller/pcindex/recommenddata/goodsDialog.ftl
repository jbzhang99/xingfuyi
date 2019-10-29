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
		
		$('#product_case_name')
				.click(
						function() {
						    var width_ = '350';
						    if(ismobile()){
								width_ = "100%";
						    }
							//init
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

	function gdSumit() {
		var selected = $('#gd_dataGrid').datagrid('getSelected');
		if (!selected) {
			$.messager.alert('提示', '请选择一个商品。');
			return;
		}
		$('#refId').val(selected.id);
		$('#productName').val(selected.name1);
		$('#proname').val(selected.name1);
		//     	$("#addform").data('bootstrapValidator').validateField('proname');
		$("#addform").bootstrapValidator('updateStatus', 'proname',
				'NOT_VALIDATED').bootstrapValidator('validateField', 'proname');

		$('#goodsListDiv').window('close');
	}
</script>

<div data-options="region:'north'"
	style="margin: 0 auto; min-height: 10%" border="false">
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont">
			<form class="form-search" action="doForm" method="post"
				id="gd_queryForm" name="queryForm">
				<div class="fluidbox">
					<p class="p4 p-item">
						<label class="lab-item"
							style="line-height: 25px; margin-right: 6px;">商品分类:</label> <input
							type="text" class="txt" id="product_case_name" /> <a
							onclick="$('#product_case_name').val('');$('#product_case_id').val('');"
							class="easyui-linkbutton" iconCls="icon-delete">清空</a> <input
							type="hidden" name="q_productCateId" id="product_case_id" /> <input
							type="hidden" name="q_sellerState" value="1" /> <input
							type="hidden" name="q_productCateState" value="1" /> <input
							type="hidden" name="q_state" value="6" />
						<!-- <input type="hidden" name="q_isTop" value="2"/> -->
						<input type="hidden" name="q_sellerId" value="${(sellerId)!0}" />
					</p>
				</div>
			</form>
		</div>
	</div>

	<div data-options="region:'center'" border="false"
		style="height: 370px;" buttons="#dlg-buttons-goods">
		<table id="gd_dataGrid" class="easyui-datagrid"
			data-options="
							rownumbers:true,
							autoRowHeight:false,
							striped : true,
							pagination : true,
							singleSelect : true,
							fit:true,
							fitColumns:true,
							toolbar:'#goodsDialogTools',
							url:'${domainUrlUtil.urlResources}/seller/product/list',
							queryParams:queryParamsHandler(),
							method:'get'">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th field="name1" width="190" align="left" halign="center">商品名称</th>
					<th field="productCateName" width="100" align="center">商品分类</th>
					<th field="productBrandName" width="90" align="center">商品品牌</th>
					<th field="mallPcPrice" width="70" align="center">商城价</th>
					<th field="isTop" width="90" align="center" formatter="isTopFormat">是否推荐</th>
					<th field="upTimeStr" width="90" align="center">上架时间</th>
					<th field="state" width="70" align="center" formatter="stateFormat">状态</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<div id="goodsDialogTools">
	<a id="gd_btn-search" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
</div>

<div id="dlg-buttons-good" style="text-align: right; padding: 7px;">
	<em style="font-style: normal;float: left;">
		您可以按商品分类查询，选择一个分类后，再点击[查询]按钮即可过滤列表内容
	</em>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-ok" onclick="gdSumit()">确定</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:$('#goodsListDiv').window('close')">取消</a>
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
