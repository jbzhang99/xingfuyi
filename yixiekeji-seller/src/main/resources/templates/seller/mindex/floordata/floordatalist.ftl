<#include "/seller/commons/_head.ftl">

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script language="javascript">
	var statusBox;
	function loadSuccess(data){
    	dataGridLoadSuccess(data,this);
		$(".boxer").boxer();
	}
	
	$(function(){
	//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			statusBox = eval('(${initJSCodeContainer("DATA_TYPE")})');
		</#noescape>

		$("#a-gridAdd").click(function(){
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/mindex/floordata/add";
		});
		$("#a-gridSearch").click(function(){
	 		$('#dataGrid').datagrid('load',queryParamsHandler());
		});
		$("#a-gridEdit").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if(!selected) {
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/mindex/floordata/edit?mSellerIndexFloorDataId="+selected.id;
		});
		
		$("#a-gridDel").click(function(){
            var selectedCode = $('#dataGrid').datagrid('getSelected');
            if(!selectedCode){
                $.messager.alert('提示','请选择操作行。');
                return;
            }

            $.messager.confirm('提示', '确定删除吗？', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"POST",
                        url: "${domainUrlUtil.urlResources}/seller/mindex/floordata/delete",
                        dataType: "json",
                        data: "id="+selectedCode.id,
                        cache:false,
                        success:function(data, textStatus){
                            if (data.success) {
                                $('#dataGrid').datagrid('reload',queryParamsHandler());
                            }else{
                                $.messager.alert('提示', data.message);
                            }
                            $.messager.progress('close');
                        }
                    });
                }
            });
        });
        
		<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
	})

	function statusFormat(value,row,index){
		return statusBox["DATA_TYPE"][value];
	}

	function imageFormat(value,row,index){
		if(row.dataType == 1){
			return "-";
		} else{
	    	return "<a href='${domainUrlUtil.imageResources}" + 
				value + "' style='color:#276892' rel='gallery' class='boxer'>点击查看</a>";
		}
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
                    <li class="active">楼层数据</li>
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
                                  		<label class="lab-item">楼层：</label>
										<select name="q_indexFloorId" id="q_indexFloorId" value="${q_indexFloorId!''}">
					                    	<option value="">--全部--</option>
					                        <#if floors?? && floors?size &gt; 0>
					                        	<#list floors as floor>
													<option value="${(floor.id)!}">${(floor.name)!}</option>
												</#list>
											</#if>
									    </select>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                               		 	  <label class="lab-item">状态：</label>
                                  		<@cont.select id="q_dataType" value="${(q_dataType)!''}" codeDiv="DATA_TYPE"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
                </div>
               	<div data-options="region:'center'" border="false">
					<table id="dataGrid" class="easyui-datagrid"
							data-options="rownumbers:true
										,singleSelect:true
										,autoRowHeight:false
										,fitColumns:false
										,collapsible:true
										,toolbar:'#gridTools'
										,striped:true
										,pagination:true
										,pageSize:'${pageSize}'
										,fit:true
				    					,url:'${(domainUrlUtil.urlResources)!}/seller/mindex/floordata/list'
				    					,queryParams:queryParamsHandler()
				    					,onLoadSuccess:loadSuccess
				    					,method:'get'">
						<thead>
							<tr>
								<th field="mSellerIndexFloor.name" width="100" align="left" halign="center">楼层</th>
					            <th field="dataType" width="70" align="center" halign="center" formatter="statusFormat">数据类型</th>
								<th field="productName" width="200" align="left" halign="center">商品名称</th>
								<th field="title" width="150" align="center" halign="center">图片标题</th>
								<th field="linkUrl" width="150" align="center" halign="center">链接地址</th>
					            <th field="image" width="80" align="center" halign="center" formatter="imageFormat">查看图片</th>
					            <th field="orderNo" width="50" align="center" halign="center">排序号</th>
					            <th field="remark" width="150" align="left" halign="center">备注</th>
					            <th field="updateUserName" width="100" align="center">最后修改人</th>
					            <th field="updateTime" width="150" align="center">最后修改时间</th>
							</tr>
						</thead>
					</table>
					<div id="gridTools">
						<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						
						<@shiro.hasPermission name="/seller/mindex/floordata/add">
						<a id="a-gridAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/mindex/floordata/edit">
						<a id="a-gridEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/mindex/floordata/delete">
						<a id="a-gridDel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
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

<#include "/seller/commons/_listautoheight.ftl">
<#include "/seller/commons/_end.ftl">