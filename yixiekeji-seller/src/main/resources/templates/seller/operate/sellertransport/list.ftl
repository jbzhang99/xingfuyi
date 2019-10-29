<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/operate/sellerTransport"/>

<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/easyui/datagrid-detailview.js"></script>

<style>
#newstypeTree img {
	max-width: 390px;
	max-height: 290px;
}
</style>

<script language="javascript">
	var codeBox;
	$(function(){
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("TRANSPORT_STATE","TRANSPORT_TYPE","TRANSPORT_TIME")})');
		</#noescape>
	});
	var currentBaseUrl = "${currentBaseUrl}";
	var url_res = "${domainUrlUtil.urlResources}";
</script>
<script
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/seller/transport_list.js"></script>

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
                    <li class="active">运费模板</li>
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
                                  		<label class="lab-item">模板名称：</label> <input type="text"
											id="q_qq" name="q_qq"
											value="${queryMap['q_qq']!''}" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  	<label class="lab-item">状态：</label> <@cont.select id="q_state"
										codeDiv="TRANSPORT_STATE" value="${queryMap['q_state']!''}" 
										name="q_state"/>
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
										,view: detailview
										,detailFormatter:detailFormatter
										,onExpandRow:onExpandRow
										,pageSize:'${pageSize}'
										,fit:true
				    					,url:'${currentBaseUrl}/list'
				    					,queryParams:queryParamsHandler()
				    					,onLoadSuccess:dataGridLoadSuccess
				    					,method:'get'">
						<thead>
							<tr>
								<th field="id" hidden="hidden"></th>
								<th field="transName" width="120" align="center">模板名称</th>
								<th field="transType" width="120" align="center" formatter="transType">计价方式</th>
								<th field="transTime" width="120" align="center" formatter="transTime">发货时间</th>
								<th field="createtime" width="90" align="center">创建时间</th>
								<th field="state" width="50" align="center" formatter="getState">状态</th>
							</tr>
						</thead>
					</table>
				
					<div id="gridTools">
						<!-- <a id="btn_add" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true">新增</a> <a id="btn_edit"
							href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-edit" plain="true">编辑</a> <a id="btn_del"
							href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-delete" plain="true">删除</a> -->
						<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						
						<@shiro.hasPermission name="/seller/operate/sellerTransport/add">
						<a id="btn_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/operate/sellerTransport/edit">
						<a id="btn_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/operate/sellerTransport/inuse">
						<a id="btn-inuse" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-accept" plain="true">启用</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/operate/sellerTransport/disable">
						<a id="btn-disable" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-accept" plain="true">禁用</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/seller/operate/sellerTransport/del">
						<a id="btn_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
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