<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${(domainUrlUtil.urlResources)!}/seller/member/productask"/>

<script language="javascript">
	var statusBox;

	$(function(){
		//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			statusBox = eval('(${initJSCodeContainer("PRODUCT_ASK_STATE")})');
		</#noescape>
		// 查询按钮
		$('#a-gridSearch').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});

		$("#a-gridReply").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if(!selected) {
				$.messager.alert('提示','请选择操作行。');
				return;
			}
			if (selected.state != 1) {
				$.messager.alert('提示','只能回复咨询状态的咨询。');
				return;
			}
	 		window.location.href="${currentBaseUrl}/reply?id="+selected.id;
		});
	});

	function statusFormat(value,row,index){
		return statusBox["PRODUCT_ASK_STATE"][value];
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
                    <li class="active">会员咨询管理</li>
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
	                                  	<label class="lab-item">用户名：</label>
										<input type="text" class="txt" id="q_userName" name="q_userName" value="${q_userName!''}"/>
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
				    					,url:'${currentBaseUrl}/list'
				    					,queryParams:queryParamsHandler()
				    					,onLoadSuccess:dataGridLoadSuccess
				    					,method:'get'">
						<thead>
							<tr>
								<th field="userName" width="150" align="left" halign="center">用户名</th>  
					            <th field="sellerName" width="80" align="left" halign="center" >商家</th>  
					            <th field="productName" width="100" align="left" halign="center">产品名称</th>  
					            <th field="askContent" width="150" align="left" halign="center">咨询内容</th>  
					            <th field="replyName" width="150" align="left" halign="center">回复人员</th>  
					            <th field="replyContent" width="150" align="left" halign="center">回复内容</th>  
					            <th field="createTime" width="150" align="left" halign="center">咨询时间</th>  
					            <th field="replyTime" width="150" align="left" halign="center">回复时间</th>  
					            <th field="state" width="70" align="left" halign="center" formatter="statusFormat">状态</th>  
							</tr>
						</thead>
					</table>
				
					<#--3.function button----------------->
					<div id="gridTools">
						<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						<@shiro.hasPermission name="/seller/member/productask/reply">
						<a id="a-gridReply" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">回复</a>
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