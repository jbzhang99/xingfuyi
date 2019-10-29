<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/system/role"/>

<style>
#newstypeTree img {
	max-width: 390px;
	max-height: 290px;
}
</style>

<script language="javascript">
	$(function() {
		$("#btn_add").click(function() {
			$("#roleid").val('');
			$("#rolesName").val('');
			$("#roleCode").val('');
			$("#rolesName").attr('disabled',false);
			$("#roleCode").attr('disabled',false);
			$("#content").val('');
			var width_ = '580';
		    if(ismobile()){
				width_ = "100%";
		    }
			$("#addRole").window({
				width : width_,
				height : 355,
				title : "角色添加",
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false
			});
			
			$('#addform').bootstrapValidator({
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				submitHandler: function (validator, form, submitButton) {
					if(validator.isValid())
						submitform();
				},
				fields :{
					roleCode : {
						validators : {
							remote: {
					            message: '角色编码已存在',
					            type: 'post',
					            delay: 800,
					            data: $("#roleCode").val(),
					            url: '${currentBaseUrl}/validateRole'
					        }
	    				}
					}
				}
			});
			
		});
		
		//分配权限
		$("#btn_res").click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			var width_ = '400';
		    if(ismobile()){
				width_ = "100%";
		    }
			$("#allotResourceWin").window({
				width : width_,
				height : 510,
				title : "分配权限",
				href : "${currentBaseUrl}/role2Res?id="+selected.id+"&rolesName="+selected.rolesName,
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false
			});
			
		});
		
		$('#btn_edit').click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			$("#roleid").val(selected.id);
			$("#rolesName").val(selected.rolesName);
			$("#roleCode").val(selected.roleCode);
			$("#rolesName").attr('disabled',true);
			$("#roleCode").attr('disabled',true);
			$("#rolesName").validatebox({});
			$("#roleCode").validatebox({});
			$("#content").val(selected.content);
			
			var width_ = '580';
		    if(ismobile()){
				width_ = "100%";
		    }
			$("#addRole").window({
				width : width_,
				height : 355,
				title : "角色编辑",
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false
			});
			
			$('#addform').bootstrapValidator({
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				submitHandler: function (validator, form, submitButton) {
					submitform();
				}
			});
		});

		// 查询按钮
		$('#btn-search').click(function() {
			$('#dataGrid').datagrid('reload', queryParamsHandler());
		});

		$('#btn_del').click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			$.messager.confirm('确认', '确定删除该角色吗?此操作不可撤销', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						url : '${currentBaseUrl}/del?id=' + selected.id,
						success : function(e) {
						  	$.messager.show({
								title:'提示',
								msg:e,
								showType:'show'
							});
							$('#dataGrid').datagrid('reload');
							$.messager.progress('close');
						}
					});
				}
			});
		});
	});

	function closeW(){
	    $("#addRole").window("close");
	} 
	
	function submitform(){
		 $("#addform").form('submit',{
			 url:"${currentBaseUrl}/save",
		     success:function(e){
	    		closeW();
	    		$('#addform').form('clear');
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
</script>

<div class="main-container container-fluid">
    <!-- Page Container -->
    <div class="page-container">
        <!-- 左侧菜单开始 -->
        <#include "/seller/commons/_left.ftl">
        <!-- 左侧菜单结束 -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- 主体头部开始 -->
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
                    </li>
                    <li class="active">角色管理</li>
                </ul>
                
                <!-- 头部按钮开始 -->
                <#include "/seller/commons/_headerbuttons.ftl">
                <!-- 头部按钮结束 -->
            </div>
            <!-- 主体头部结束 -->
            <!-- Page Body -->
            <div class="page-body">
              <div id="bodyejavashop" class="easyui-layout ejava-easyui-layout" data-options="fit:true,split:false" style="height:300px; " >
                <div class="whtitdiv" data-options="region:'north'" style="padding-top: 10px;overflow-x:hidden;overflow-y:auto;">
                    <!-- <table id="part1">12341234</table> -->
                    <form class="from-ff">
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label class="lab-item">角色名称：</label> <input type="text"
											id="q_rolesName" name="q_rolesName"
											value="${q_rolesName!''}" />
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
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
								<th field="rolesName" width="120" align="center">角色名称</th>
								<th field="content" width="120" align="center">角色描述</th>
								<th field="roleCode" width="90" align="center">角色编码</th>
								<th field="createTime" width="90" align="center">创建时间</th>
							</tr>
						</thead>
					</table>
				
					<div id="gridTools">
						<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						<@shiro.hasPermission name="/seller/system/role/add">
						<a id="btn_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/role/edit">
						<a id="btn_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/role/del">
						<a id="btn_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/role/role2Res">
						<a id="btn_res" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">分配权限</a>
						</@shiro.hasPermission>
						
					</div>
				</div>
              </div>
            </div>
            <!-- /Page Body -->
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Page Container -->
</div>

<div id="addRole" class="wrapper" style="overflow-y: auto; overflow-x: hidden;">
	<div class="col-lg-12 col-sm-12 col-xs-12" style="margin: 30px 0px;">
		<form method="post" id="addform" class="form-horizontal"
			action="${currentBaseUrl}/doAdd"
			enctype="multipart/form-data">
			<input type="hidden" id="roleid" name="id" value="0">
			
			<div class="form-group">
				<label class="col-lg-3 control-label"><font class="red">*</font>角色名称：</label> 
				<div class="col-lg-8">
					<input
						class="form-control" type="text" 
						id="rolesName"
						name="rolesName"
						required
					 	data-bv-stringlength="true"
                        data-bv-stringlength-min="2"
                        data-bv-stringlength-max="11"
                        data-bv-stringlength-message="角色名称2-11位长度"/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-3 control-label"><font class="red">*</font>角色编码：</label> 
				<div class="col-lg-8">
					<input
						class="form-control" type="text" 
						id="roleCode"
						name="roleCode"
						required
						data-bv-stringlength="true"
                        data-bv-stringlength-min="2"
                        data-bv-stringlength-max="11"
                        data-bv-stringlength-message="角色编码2-11位长度" />
	            </div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-3 control-label"><font class="red">*</font>角色描述：</label> 
				<div class="col-lg-8">
					<textarea class="form-control" 
						required
						id="content" name="content"></textarea>
			</div>
			
			<div class="form-group">
				<div class="col-lg-8 col-lg-offset-5" style="margin-top: 20px;">
					<button type="submit" class="btn btn-danger btn-primary">提交</button>
					<button type="button" class="btn btn-danger back btn-primary" onclick="closeW()">取消</button>
				</div>
			</div>
		</form>
	</div>
</div>
					
<div id="allotResourceWin">
</div>

<#include "/seller/commons/_listautoheight.ftl">
<#include "/seller/commons/_end.ftl">