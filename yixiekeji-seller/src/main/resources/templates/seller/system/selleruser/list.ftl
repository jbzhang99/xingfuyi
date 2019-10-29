<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/system/sellerUser"/>
<script language="javascript">
	var codeBox;
	$(function() {
		<#noescape>
		codeBox = eval('(${initJSCodeContainer("ADMIN_STATUS")})');
		</#noescape>
		
		// 查询按钮
		$('#btn-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
		$('#btn_add').click(function(){
		    var width_ = '600';
		    if(ismobile()){
				width_ = "100%";
		    }
	 		$("#editWin").window({
				width : width_,
				height : 500,
				href : "${currentBaseUrl}/add",
				title : "新增管理员",
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false
			});
		});
		
		$('#btn_edit').click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		var width_ = '600';
		    if(ismobile()){
				width_ = "100%";
		    }
	 		$("#editWin").window({
				width : width_,
				height : 540,
				href : "${currentBaseUrl}/edit?id="+selected.id,
				title : "修改管理员信息",
				modal : true,
				shadow : false,
				collapsible : false,
				minimizable : false,
				maximizable : false
			});
		});
		
		$('#btn_freeze').click(function(){
	 		var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		if(selected.status==2){
	 			$.messager.alert('提示','此账号已被冻结');
				return;
	 		}
	 		if(selected.status==3){
	 			$.messager.alert('提示','此账号已被删除');
				return;
	 		}
			$.messager.confirm('确认', '确定冻结该管理员账号吗？冻结后，该管理员将无法登录', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/freeze?id="+selected.id,
						success:function(e){
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
		
		$('#btn_unfreeze').click(function(){
	 		var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		if(selected.status==1){
	 			$.messager.alert('提示','此账号没有被冻结');
				return;
	 		}
	 		if(selected.status==3){
	 			$.messager.alert('提示','此账号已被删除');
				return;
	 		}
			$.messager.confirm('确认', '确定解冻该管理员账号吗？', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/unfreeze?id="+selected.id,
						success:function(e){
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
		
		$('#btn_del').click(function(){
	 		var selected = $('#dataGrid').datagrid('getSelected');
	 		if(!selected){
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		if(selected.status==3){
	 			$.messager.alert('提示','此账号已被删除');
				return;
	 		}
			$.messager.confirm('确认', '确定删除该管理员账号吗？删除后，该管理员将无法登录,但所拥有的角色资源仍将保留', function(r){
				if (r){
					$.messager.progress({text:"提交中..."});
					$.ajax({
						type:"GET",
					    url: "${currentBaseUrl}/del?id="+selected.id,
						success:function(e){
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
		
	});

	function getState(value, row, index) {
		var box = codeBox["ADMIN_STATUS"][value];
		return box;
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
                    <li class="active">管理员管理</li>
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
                                  		<label class="lab-item">账号：</label> <input type="text" placeholder="账号查找..."
											id="q_name" name="q_name" value="${q_name!''}" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label>状态：</label>
	                       		       <@cont.select id="q_status" value="${(q_status)!''}" codeDiv="USE_YN" style="width:80px" />
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
								<th field="name" width="120" align="center">账号</th>
								<th field="code" width="120" align="center">员工号</th>
								<th field="realName" width="120" align="center">真实姓名</th>
								<th field="phone" width="120" align="center">电话</th>
								<th field="job" width="120" align="center">职务</th>
								<th field="roleName" width="120" align="center">角色</th>
								<th field="state" width="70" align="center" formatter="getState">状态</th>
								<th field="updateTime" width="120" align="center">更新时间</th>
							</tr>
						</thead>
					</table>
				
					<div id="gridTools">
						<a id="btn-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						
						<@shiro.hasPermission name="/seller/system/sellerUser/add">
						<a id="btn_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/sellerUser/edit">
						<a id="btn_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/sellerUser/freeze">
						<a id="btn_freeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-freeze" plain="true">冻结</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/sellerUser/unfreeze">
						<a id="btn_unfreeze" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-activate" plain="true">解冻</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/system/sellerUser/del">
						<a id="btn_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
						</@shiro.hasPermission>
						
					</div>
					
					<div class="wrapper" id="editWin">
						
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

<#include "/seller/commons/_listautoheight.ftl">
<#include "/seller/commons/_end.ftl">