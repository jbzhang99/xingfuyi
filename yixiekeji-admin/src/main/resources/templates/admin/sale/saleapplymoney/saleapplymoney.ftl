<#include "/admin/commons/_detailheader.ftl" /> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/sale/saleapplymoney1"/>
<script language="javascript">
	var codeBoxUseYn;
	$(function() {
		<#noescape>
			codeBoxUseYn = eval('(${initJSCodeContainer("YES_NO")})');
		</#noescape>
		
		// 查询按钮
		$('#btn-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
		// 打款
		$('#btn_freeze').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			if(selected.state != 0) {
				$.messager.alert('提示','已经打款，请勿重复操作');
				return;
			}
	 		$.messager.confirm('确认', '确定打款吗？', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/updatemoneystate",
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

	function useYnFormat(value,row,index){
		return codeBoxUseYn["YES_NO"][value];
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
					<p class="p6 p-item">
						<label class="lab-item">用户名:</label> 
						<input type="text" class="txt" id="q_memberName" name="q_memberName" value="${q_memberName!''}" />
					</p>
					<p class="p6 p-item">
						<label class="lab-item">是否打款:</label> 
						<@cont.select id="q_state" codeDiv="YES_NO" name="q_state" style="width:100px"/>
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
				<th field="memberName" width="100" align="left" halign="center">用户名</th>
				<th field="bankName" width="200" align="left" halign="center">收款人姓名</th>
				<th field="certificateCode" width="200" align="left" halign="center">证件号码</th>
				<th field="bankType" width="200" align="left" halign="center">开户行</th>
				<th field="bankCode" width="200" align="left" halign="center">银行卡号</th>
				<th field="bankAdd" width="200" align="left" halign="center">开户行地址</th>
				<th field="money" width="100" align="left" halign="center">金额</th>
				<th field="state" width="100" align="left" halign="center" formatter="useYnFormat">是否打款</th>
				<th field="createTime" width="150" align="left" halign="center">取款时间</th>
				<th field="updateName" width="100" align="left" halign="center">汇款人姓名</th>
				<th field="updateTime" width="150" align="left" halign="center">汇款时间</th>
			</tr>
		</thead>
	</table>

	<div id="gridTools">
		<a id="btn-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<@shiro.hasPermission name="/admin/sale/saleapplymoney1/updatemoneystate">
			<a id="btn_freeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">打款</a>
		</@shiro.hasPermission>
	</div>
	
</div>
<#include "/admin/commons/_detailfooter.ftl" />
