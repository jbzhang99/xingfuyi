<#include "/admin/commons/_detailheader.ftl" /> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/sale/salemember"/>
<script language="javascript">
	var codeBox;
	var codeBoxUseYn;
	$(function() {
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("SALE_MEMBER_STATE")})');
			codeBoxUseYn = eval('(${initJSCodeContainer("USE_YN")})');
		</#noescape>
		
		// 查询按钮
		$('#btn-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
		// 冻结商家
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
		
		// 解冻商家
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
		var box = codeBox["SALE_MEMBER_STATE"][value];
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
						<label class="lab-item">用户名:</label> 
						<input type="text" class="txt" id="q_memberName" name="q_memberName" value="${q_memberName!''}" />
					</p>
					<p class="p4 p-item">
						<label class="lab-item">财务信息状态:</label>
						<@cont.select id="q_state" codeDiv="SALE_MEMBER_STATE" name="q_state" style="width:100px"/>
					</p>
					<p class="p4 p-item">
						<label class="lab-item">是否推广员:</label> 
						<@cont.select id="q_isSale" codeDiv="YES_NO" name="q_isSale" style="width:100px"/>
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
				<th field="memberName" width="100" align="left" halign="center">用户姓名</th>
				<th field="certificateCode" width="150" align="left" halign="center">身份证号码</th>
				<th field="bankType" width="150" align="left" halign="center">开户行</th>
				<th field="bankCode" width="150" align="left" halign="center">银行卡号</th>
				<th field="bankName" width="100" align="left" halign="center">开户人姓名</th>
				<th field="bankAdd" width="150" align="left" halign="center">开户行地址</th>
				<th field="state" width="100" align="left" halign="center" formatter="getState">财务审核状态</th>
				<th field="isSale" width="100" align="left" halign="center" formatter="useYnFormat">是否为推广员</th>
				<th field="referrerName" width="100" align="left" halign="center">推荐人姓名</th>
				<th field="referrerCode" width="100" align="left" halign="center">推荐人的推荐码</th>
				<th field="referrerPname" width="150" align="left" halign="center">推荐人的推荐人的姓名</th>
				<th field="saleCode" width="100" align="left" halign="center">我的推广码</th>
				<th field="auditName" width="100" align="left" halign="center">审核人姓名</th>
				<th field="createTime" width="150" align="left" halign="center">创建时间</th>
			</tr>
		</thead>
	</table>

	<div id="gridTools">
		<a id="btn-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<@shiro.hasPermission name="/admin/sale/salemember/freeze">
			<a id="btn_freeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-fail" plain="true">审核驳回</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/sale/salemember/unfreeze">
			<a id="btn_unfreeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">审核通过</a>
		</@shiro.hasPermission>
	</div>
	
</div>
<#include "/admin/commons/_detailfooter.ftl" />
