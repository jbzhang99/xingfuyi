<#include "/admin/commons/_detailheader.ftl" /> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/sale/salescale"/>
<script language="javascript">
	var codeBox;
	var codeBoxUseYn;
	$(function() {
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("SELLER_AUDIT_STATE")})');
			codeBoxUseYn = eval('(${initJSCodeContainer("USE_YN")})');
		</#noescape>
		
		// 查询按钮
		$('#btn-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
		$("#btn_edit").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if(!selected) {
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		window.location.href="${(domainUrlUtil.urlResources)!}/admin/sale/salescale/edit?id="+selected.id;
		});
		
		
		// 冻结商家
		$('#btn_freeze').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			if(selected.state == 0) {
				$.messager.alert('提示','已经处于未启用状态。');
				return;
			}
	 		$.messager.confirm('确认', '确定停用该商家吗？停用后，将不再有分销商品', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/freeze",
						dataType: "json",
					    data: "sellerId=" + selected.id,
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
		
		// 解冻商家
		$('#btn_unfreeze').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			if(selected.state == 1) {
				$.messager.alert('提示','已经处于启用状态。');
				return;
			}
	 		$.messager.confirm('确认', '确定启用该商家吗？', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/unfreeze",
						dataType: "json",
					    data: "sellerId=" + selected.id,
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
		var box = codeBox["SELLER_AUDIT_STATE"][value];
		return box;
	}
	function useYnFormat(value,row,index){
		return codeBoxUseYn["USE_YN"][value];
	}
</script>

<div id="searchbar" data-options="region:'north'" style="margin:0 auto;"
	border="false">
	<h2 class="h2-title">
		商家列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span>
	</h2>
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont">
			<form class="form-search" action="doForm" method="post"
				id="queryForm" name="queryForm">
				<div class="fluidbox">
					<p class="p4 p-item">
						<label class="lab-item">用户名 :</label> <input type="text"
							class="txt" id="q_name" name="q_name" value="${q_name!''}" />
					</p>
					<p class="p4 p-item">
						<label class="lab-item">店铺名称 :</label> <input type="text"
							class="txt" id="q_sellerName" name="q_sellerName" value="${q_sellerName!''}" />
					</p>
					<p class="p4 p-item">
						<label class="lab-item">状态 :</label> <@cont.select id="q_auditStatus"
						codeDiv="SELLER_AUDIT_STATE" name="q_auditStatus" style="width:100px"/>
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
						,fitColumns:true
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
				<th field="name" width="120" align="center">申请注册账号名</th>
				<th field="memberName" width="120" align="center">申请人账号</th>
				<th field="sellerName" width="200" align="center">申请店铺名</th>
				<th field="productNumber" width="100" align="center">商品数量</th>
				<th field="saleMoney" width="100" align="center">店铺总销售金额</th>
				<th field="auditStatus" width="100" align="center" formatter="getState">商家状态</th>
				<th field="saleScale1" width="100" align="center">一级分销比例</th>
				<th field="saleScale2" width="100" align="center">二级分销比例</th>
				<th field="updateName" width="100" align="center">最后修改人</th>
				<th field="updateTime" width="150" align="center">修改时间</th>
				<th field="state" width="100" align="center"  formatter="useYnFormat">分销状态</th>
			</tr>
		</thead>
	</table>

	<div id="gridTools">
		<a id="btn-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<@shiro.hasPermission name="/admin/sale/salescale/edit">
			<a id="btn_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">设置</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/sale/salescale/freeze">
			<a id="btn_freeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-fail" plain="true">停用</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/sale/salescale/unfreeze">
			<a id="btn_unfreeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">启用</a>
		</@shiro.hasPermission>
	</div>
	
</div>
<#include "/admin/commons/_detailfooter.ftl" />
