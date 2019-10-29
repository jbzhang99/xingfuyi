<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/order/productBack"/>

<style>
#newstypeTree img {
	max-width: 390px;
	max-height: 290px;
}
</style>

<script language="javascript">
	var codeBox;
	$(function() {

		<#noescape>
			codeBox = eval('(${initJSCodeContainer("MEM_PB_STATE_RETURN","MEM_PB_STATE_MONEY")})');
		</#noescape>

		$('#btn_edit').click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			location.href = '${currentBaseUrl}/edit?id=' + selected.id;
		});
		
		$('#btn_deliver').click(function() {
			var selected = $('#dataGrid').datagrid('getSelected');
			if (!selected) {
				$.messager.alert('提示', '请选择操作行。');
				return;
			}
			var state = selected.stateReturn;
			if(state != 3){
				$.messager.alert('提示', '该退货申请不是可收货状态！');
				return;
			}
			var msg = "您确认收货后，将由平台管理员退款给用户，请确认已收到退回的商品！";
			$.messager.confirm('确认', msg, function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type : "GET",
						url : "${currentBaseUrl}/takeDeliver?id="+selected.id,
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								$('#dataGrid').datagrid('reload');
							}else{
								$.messager.alert('提示', data.message);
								return false;
							}
						}
					});
				}
			});
		});

		
		// 查询按钮
		$('#btn-search').click(function() {
			$('#dataGrid').datagrid('reload', queryParamsHandler());
		});

	});

	function imageFormat(value, row, index) {
		return "<a class='newstype_view' onclick='showimg($(this).attr(\"imgpath\"));' href='javascript:;' imgpath='"
				+ value + "'>点击查看</a>";
	}

	function showimg(href) {
		$("#newstypeTree")
				.html(
						"<img src='${domainUrlUtil.urlResources}/"+href+"' alt='企业logo'>");
		$("#newstypeWin").window('open');
	}

	function proBackState(value, row, index) {
		var box = codeBox["MEM_PB_STATE_RETURN"][value];
		return box;
	}
	
	function proMonState(value, row, index) {
		var box = codeBox["MEM_PB_STATE_MONEY"][value];
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
                    <li class="active">退货管理</li>
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
	                                  <label class="lab-item">退货状态：</label>
		                               <@cont.select
											id="q_stateReturn" codeDiv="MEM_PB_STATE_RETURN"
											value="${queryMap['q_stateReturn']!''}" name="q_stateReturn" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                  <label class="lab-item">退款状态：</label>
		                              <@cont.select
											id="q_stateMoney" codeDiv="MEM_PB_STATE_MONEY"
											value="${queryMap['q_stateMoney']!''}" name="q_stateMoney" />
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
									<th field="orderMoney" hidden="hidden"></th>
									<th field="orderSn" width="150" align="center">订单号</th>
									<th field="productName" width="150" align="center">商品名称</th>
									<th field="memberName" width="120" align="center">用户名</th>
									<th field="question" width="150" align="center">问题描述</th>
									<th field="number" width="150" align="center">退货数量</th>
									<th field="backMoney" width="80" align="center">退款金额</th>
									<th field="backIntegral" width="60" align="center">退回积分</th>
									<th field="backCouponSn" width="120" align="center">退回优惠券</th>
									<th field="stateReturn" width="100" align="center" formatter="proBackState">退货状态</th>
									<th field="stateMoney" width="100" align="center" formatter="proMonState">退款状态</th>
									<th field="createTime" width="150" align="center">创建时间</th>
								</tr>
							</thead>
						</table>
				
						<div id="gridTools">
							<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
							<@shiro.hasPermission name="/seller/order/productBack/edit">
							<a id="btn_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">处理退货申请</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/order/productBack/takeDeliver">
							<a id="btn_deliver" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">确认收货</a>
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