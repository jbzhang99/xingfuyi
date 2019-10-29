<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/order/complaint"/>

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>

<script language="javascript">
	var codeBox;
	 function loadSuccess(data){
    	dataGridLoadSuccess(data,this);
		$(".boxer").boxer();
	}
	$(function() {
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("SELLER_COMPLAINT")})');
		</#noescape>

		$('#btn_audit').click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
            var state = selected.state;
            if(state != 3){
                $.messager.alert('提示',"状态只有是[买家投诉通过]时商家才能处理");
                return;
            }
			location.href = '${currentBaseUrl}/auditPage?id=' + selected.id;
		});

		// 查询按钮
		$('#btn-search').click(function() {
			$('#dataGrid').datagrid('reload', queryParamsHandler());
		});

	});

	 function imageFormat(value,row,index){
	    	return "<a href='${domainUrlUtil.imageResources}" + 
				value + "' style='color:#276892' rel='gallery' class='boxer'>点击查看</a>";
	 }

	function getState(value, row, index) {
		var box = codeBox["SELLER_COMPLAINT"][value];
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
                    <li class="active">投诉管理</li>
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
	                                  <label class="lab-item">状态：</label>
		                               <@cont.select id="q_state"
											codeDiv="SELLER_COMPLAINT" value="${queryMap['q_state']!''}"
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
											,pageSize:'${pageSize}'
											,fit:true
					    					,url:'${currentBaseUrl}/list'
					    					,queryParams:queryParamsHandler()
					    					,onLoadSuccess:loadSuccess
					    					,method:'get'">
							<thead>
								<tr>
									<th field="id" hidden="hidden"></th>
									<th field="userName" width="50" align="center">投诉人账户名</th>
									<th field="orderSn" width="50" align="center">订单号</th>
									<th field="source" width="40" align="center">投诉来源</th>
									<th field="content" width="150" align="left" halign="center">投诉内容</th>
									<th field="sellerName" width="50" align="center">被投诉商家</th>
									<th field="image" width="40" align="center" formatter="imageFormat">举证图片</th>
									<th field="complaintTimeStr" width="50" align="center">投诉时间</th>
									<th field="state" width="50" align="center" formatter="getState">投诉状态</th>
								</tr>
							</thead>
						</table>
				
					<div id="gridTools">
						<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						<@shiro.hasPermission name="/seller/order/complaint/auditPage">
						<a id="btn_audit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">申诉</a>
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

