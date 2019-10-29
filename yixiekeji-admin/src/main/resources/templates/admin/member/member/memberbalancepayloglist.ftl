<#include "/admin/commons/_detailheader.ftl" /> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/memberBalancePayLog"/>

<script language="javascript">
	var currentBaseUrl = "${currentBaseUrl}";
	var domainURL = "${domainUrlUtil.urlResources}";
	var codeBox;
	$(function() {
		<#noescape>
			//初始化需要的字典数据
	        codeBox = eval('(${initJSCodeContainer("MATERISAL_TYPE")})');
	    </#noescape>
	    
		$("#btn_add").click(function() {
			location.href = currentBaseUrl + "/edit";
		});

		$("#btn_edit").click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行');
				return;
			}
			location.href = currentBaseUrl + "/edit?id="+selected.id;
		});

		$("#back").click(function(){
			location.href = currentBaseUrl;
		});
		
		$("#btn_del").click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行');
				return;
			}
			$.messager.confirm('确认','确定删除该会员充值记录吗？', function(r) {
				if (r) {
					$.ajax({
						url:currentBaseUrl+'/del?id='+selected.id,
						success:function(e){
							$.messager.show({
								title : '提示',
								msg : e,
								showType : 'show'
							});
							$('#dataGrid').datagrid('reload');
						}
					});
				}
			});
		});
		
		$('#btn-search').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
	    });
		   
		$("#add").click(function() {
			var isValid = $("#addForm").form('validate');
			if (isValid) {
				$.messager.progress({
					text : "提交中..."
				});
				$("#addForm").form('submit', {
					url : currentBaseUrl + "/doAdd",
					success : function(e) {
						$.messager.progress('close');
						$.messager.show({
							title : '提示',
							msg : e,
							showType : 'show'
						});
						setTimeout(function() {
							location.href = currentBaseUrl;
						}, 1000)
					}
				});
			}
		});
	});
	
	function statefmt(value, row, index) {
		return value == 1 ? "未支付":"已支付";
	}
</script>

<div id="searchbar" data-options="region:'north'"
	style="margin: 0 auto;" border="false">
	<h2 class="h2-title">
		会员充值记录列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span>
	</h2>
	<div id="searchbox" class="head-seachbox">
		<div class="w-p99 marauto searchCont">
			<form class="form-search" action="doForm" method="post"
				id="queryForm" name="queryForm">
				<div class="fluidbox">
					<p class="p4 p-item">
						<label class="lab-item">用户名 :</label> <input type="text"
							class="txt" id="q_memberName" name="q_memberName" value="${queryMap['q_name']!}" />
					</p>
				</div>
			</form>
		</div>
	</div>
</div>

<div data-options="region:'center'" border="false">
	<table id="dataGrid" class="easyui-datagrid"
		data-options="rownumbers:false
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
				<th field="memberName" width="90" align="center">会员账号</th>
				<th field="paymentName" width="90" align="center">支付方式名称</th>
				<th field="payMoney" width="90" align="center">支付金额</th>
				<th field="payState" width="80" align="center" formatter="statefmt">支付状态</th>
				<th field="paySn" width="170" align="center">支付订单号</th>
				<th field="tradeSn" width="220" align="left" halign="center">支付交易流水号</th>
				<th field="createTime" width="140" align="center">创建时间</th>
				<th field="payFinishTime" width="140" align="center">支付完成时间</th>
			</tr>
		</thead>
	</table>

	<div id="gridTools">
		<a id="btn-search"
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" plain="true">查询</a>
	</div>
	
</div>

<#include "/admin/commons/_detailfooter.ftl" />
