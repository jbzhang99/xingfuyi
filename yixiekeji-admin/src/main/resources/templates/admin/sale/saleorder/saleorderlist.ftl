<#include "/admin/commons/_detailheader.ftl" /> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/sale/saleorder" />
<script language="javascript">
	var codeBox;
	var codeBoxUseYn;
	$(function() {
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("SALE_ORDER_STATE")})');
			codeBoxUseYn = eval('(${initJSCodeContainer("USE_YN")})');
		</#noescape>
		
		// 查询按钮
		$('#btn-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
		$('#btn_freeze').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			if(selected.state != 2) {
				$.messager.alert('提示','未提交审核，不能进行操作');
				return;
			}
	 		$.messager.confirm('确认', '确定驳回此用户的财务申请？', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/freeze",
						dataType: "json",
					    data: "id=" + selected.id,
					    cache:false,
						success:function(data, textStatus){
							if (data.success) {
								$.messager.show({
									title:'提示',
									msg:'操作成功',
									showType:'show'
								});
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
		
		$('#btn_unfreeze').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			if(selected.state != 2) {
				$.messager.alert('提示','未提交审核，不能进行操作');
				return;
			}
	 		$.messager.confirm('确认', '确定审核通过？', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/unfreeze",
						dataType: "json",
					    data: "id=" + selected.id,
					    cache:false,
						success:function(data, textStatus){
							if (data.success) {
								$.messager.show({
									title:'提示',
									msg:'操作成功',
									showType:'show'
								});
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

	function getState(value, row, index) {
		var box = codeBox["SALE_ORDER_STATE"][value];
		return box;
	}
	function useYnFormat(value,row,index){
		return codeBoxUseYn["USE_YN"][value];
	}
</script>

<div id="searchbar" data-options="region:'north'" style="margin:0 auto;"
	border="false">
	<h2 class="h2-title">
		分销用户列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span>
	</h2>
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont">
			<form class="form-search" action="doForm" method="post"
				id="queryForm" name="queryForm">
				<div class="fluidbox">
					<p class="p4 p-item">
						<label class="lab-item">商家名称:</label> 
						<select name="q_sellerId" class="w120" id="q_sellerId">
							<option value="">请选择</option> 
							<#if sellers??> 
								<#list sellers as seller>
									<option value="${(seller.id)!}">${(seller.sellerName)!}</option>
								</#list>
							</#if>
						</select>
					</p>
					<p class="p4 p-item">
						<label class="lab-item">购买人:</label> 
						<input type="text" class="txt" id="q_buyName" name="q_buyName" value="${q_buyName!''}" />
					</p>
					<p class="p4 p-item">
						<label class="lab-item">佣金状态:</label>
						<@cont.select id="q_saleState" codeDiv="SALE_ORDER_STATE" name="q_saleState" style="width:100px"/>
					</p>
					
				</div>
			</form>
		</div>
	</div>
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
				<th field="buyName" width="100" align="left" halign="center">购买人</th>
				<th field="memberName" width="100" align="left" halign="center">拿分佣用户</th>
				<th field="productName" width="300" align="left" halign="center">商品名称</th>
				<th field="sellerName" width="150" align="left" halign="center">商家名称</th>
				<th field="orderSn" width="170" align="left" halign="center">订单号</th>
				<th field="orderTime" width="150" align="left" halign="center">下单日期</th>
				<th field="moneyAll" width="100" align="left" halign="center">总金额</th>
				<th field="actMoney" width="100" align="left" halign="center">优惠金额</th>
				<th field="money" width="100" align="left" halign="center">单价</th>
				<th field="number" width="100" align="left" halign="center">购买数量</th>
				<th field="saleMoney" width="100" align="left" halign="center">佣金金额</th>
				<th field="saleScale" width="100" align="left" halign="center">佣金比例</th>
				<th field="saleState" width="100" align="left" halign="center" formatter="getState">佣金状态</th>
				<th field="saleGrade" width="100" align="left" halign="center">分佣级数</th>
				<th field="backNumber" width="100" align="left" halign="center">退货数量</th>
				<th field="backMoney" width="100" align="left" halign="center">退款金额</th>
				<th field="createTime" width="150" align="left" halign="center">创建时间</th>
			</tr>
		</thead>
	</table>

	<div id="gridTools">
		<a id="btn-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
	
</div>
<#include "/admin/commons/_detailfooter.ftl" />
