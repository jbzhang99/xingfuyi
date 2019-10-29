<#include "/admin/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/settlementsetting"/>
<script type="text/javascript" src="${domainUrlUtil.urlResources}/resources/admin/jslib/My97DatePicker/WdatePicker.js"></script>

<style>
	#newstypeTree img {
		max-width: 390px;
		max-height: 290px;
	}
</style>


<link rel="stylesheet"
	  href="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/colorbox/colorbox.css"
	  type="text/css"></link>
<script type="text/javascript"
		src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/colorbox/jquery.colorbox-min.js"></script>

<script language="javascript">
	function getState(value, row, index) {
		var box = value==0?'未运行':'已运行';
		return box;
	}
	function getDelState(value, row, index) {
		var box = value==0?'':'已删除';
		return box;
	}



	function closeW(){
		$("#addRole").window("close");
	}

	$(function() {

		$("#btn_add").click(function () {
			$("#runTime").val('');
			$("#startDate").val('');
			$("#endDate").val('');
			$("#addRole").window({
				width: 600,
				height: 323,
				title: "结算设置添加",
				modal: true,
				shadow: false,
				collapsible: false,
				minimizable: false,
				maximizable: false
			});
		});

		$('#btn_edit').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			$("#id").val(selected.id);
			$("#runTime").val(selected.runTime);
			$("#startDate").val(selected.startDate);
			$("#endDate").val(selected.endDate);
			$("#addRole").window({
				width: 600,
				height: 323,
				title: "结算设置编辑",
				modal: true,
				shadow: false,
				collapsible: false,
				minimizable: false,
				maximizable: false
			});
		});

		$('#btn_del').click(function () {
			var selected = $('#dataGrid').datagrid('getSelected');
			var idddd = selected.id;
			alert(idddd);
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			$.messager.confirm('确认', '确定删除该设置吗?此操作不可撤销', function (r) {
				if (r) {
					$.messager.progress({
						text: "提交中..."
					});
					$.ajax({
						url: '${currentBaseUrl}/delete?id=' + selected.id,
						success: function (e) {
							$.messager.show({
								title: '提示',
								msg: e,
								showType: 'show'
							});
							$('#dataGrid').datagrid('reload');
							$.messager.progress('close');
						}
					});
				}
			});
		});

		//==============事件==============

        $("#addBtn").click(function(){
            var isValid = $("#addRoleForm").form('validate');
            if(isValid){
                $.messager.progress({
                    text : "提交中..."
                });
                $("#addRoleForm").form('submit',{
                    url:"${currentBaseUrl}/save",
                    success:function(e){
                        closeW();
                        $('#addRoleForm').form('clear');
                        $.messager.progress('close');
                        $("#dataGrid").datagrid('reload');
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
</script>

<div data-options="region:'center'" border="false">
	<table id="dataGrid" class="easyui-datagrid"
		   data-options="rownumbers:true
						,idField :'id'
						,singleSelect:true
						,autoRowHeight:false
						,fitColumns:true
						,toolbar:'#gridTools'
						,striped:true
						,fit:true
    					,url:'${currentBaseUrl}/list'
    					,onLoadSuccess:dataGridLoadSuccess
    					,method:'get'">
		<thead>
		<tr>
			<th field="id" hidden="hidden"></th>
			<th field="runTime" width="20" align="center" formatDate("yyyy-MM-dd")>运行时间</th>
			<th field="startDate" width="20" align="center">开始时间</th>
			<th field="endDate" width="20" align="center">结束时间</th>
			<th field="status" width="20" align="center" formatter="getState">运行状态</th>
			<th field="delStatus" width="20" align="center" formatter="getDelState">删除状态</th>
		</tr>
		</thead>
	</table>

	<div id="gridTools">
		<@shiro.hasPermission name="/admin/settlementsetting/add">
			<a id="btn_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/settlementsetting/edit">
			<a id="btn_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/settlementsetting/delete">
			<a id="btn_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
		</@shiro.hasPermission>
	</div>


	<div id="addRole" class="wrapper">
		<div class="formbox-a">
			<form id="addRoleForm" method="post">
				<input type="hidden" id="id" name="id" value="0">
				<div class="form-contbox">
					<dl class="dl-group">

						<dd class="dd-group">
							<div class="fluidbox">
								<p class="p12 p-item">
									<label class="lab-item"><font class="red">*</font>运行时间:
									</label>
									<input autocomplete="off" readonly type="text" id="runTime" name="runTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt w180"/>
									<span class="title_span">1-30个字符</span>
								</p>
							</div>
							<br />
							<div class="fluidbox">
								<p class="p12 p-item">
									<label class="lab-item"><font class="red">*</font>开始时间:
									</label>
									<input autocomplete="off" readonly type="text" id="startDate" name="startDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt w180"/>
									<span class="title_span">1-20个字符,唯一</span>
								</p>
							</div>
							<br/>
							<div class="fluidbox">
								<p class="p12 p-item">
									<label class="lab-item"><font class="red">*</font>结束时间: </label>
									<input autocomplete="off" readonly type="text" id="endDate" name="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="txt w180"/>
									<span class="title_span">1-20个字符,唯一</span>
								</p>
							</div>
							<br />

						</dd>
					</dl>

					<p class="p-item p-btn">
						<a class="easyui-linkbutton" iconCls="icon-save" id="addBtn">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
						   iconCls="icon-delete" onclick="closeW()">关闭</a>
					</p>
				</div>
			</form>
		</div>
	</div>

</div>


<#include "/admin/commons/_detailfooter.ftl" />
