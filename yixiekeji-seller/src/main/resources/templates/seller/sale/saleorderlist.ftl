<#include "/seller/commons/_head.ftl"> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/sale/saleorder"/>

<style>
#newstypeTree img {
	max-width: 390px;
	max-height: 290px;
}
</style>

<script language="javascript">
	var codeBox;
	var codeBoxUseYn;
	$(function() {
		
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("SALE_ORDER_STATE")})');
			codeBoxUseYn = eval('(${initJSCodeContainer("USE_YN")})');
		</#noescape>
		
		// 查询按钮
		$('#btn-search').click(function(){
			$('#dataGrid').datagrid('reload',queryParamsHandler());
		});
		
	});

	function getState(value, row, index) {
		var box = codeBox["SALE_ORDER_STATE"][value];
		return box;
	}
	function useYnFormat(value,row,index){
		return codeBoxUseYn["USE_YN"][value];
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
                    <li class="active">佣金流水</li>
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
                                  		<label class="lab-item">购买人：</label> 
                                  		<input type="text" id="q_buyName" name="q_buyName" value="${queryMap['q_buyName']!''}" />
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
                                  		<label class="lab-item">状态：</label> 
                                  		<@cont.select id="q_saleState" codeDiv="SALE_ORDER_STATE" value="${queryMap['q_saleState']!''}" name="q_saleState"/>
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
								<th field="buyName" width="100" align="left" halign="center">购买人</th>
								<th field="memberName" width="100" align="left" halign="center">拿分佣用户</th>
								<th field="productName" width="300" align="left" halign="center">商品名称</th>
								<th field="sellerName" width="150" align="left" halign="center">商家名称</th>
								<th field="orderSn" width="170" align="left" halign="center">订单号</th>
								<th field="orderTime" width="150" align="left" halign="center">下单日期</th>
								<th field="moneyAll" width="100" align="left" halign="center">总金额</th>
								<th field="actMoney" width="100" align="left" halign="center">优惠金额</th>
								<th field="money" width="100" align="left" halign="center">单价</th>
								<th field="number" width="100" align="left" halign="center">购买数量</th>
								<th field="saleMoney" width="100" align="left" halign="center">佣金金额</th>
								<th field="saleScale" width="100" align="left" halign="center">佣金比例</th>
								<th field="saleState" width="100" align="left" halign="center" formatter="getState">佣金状态</th>
								<th field="saleGrade" width="100" align="left" halign="center">分佣级数</th>
								<th field="backNumber" width="100" align="left" halign="center">退货数量</th>
								<th field="backMoney" width="100" align="left" halign="center">退款金额</th>
								<th field="createTime" width="150" align="left" halign="center">创建时间</th>
							</tr>
						</thead>
					</table>
				
					<div id="gridTools">
						<a id="btn-search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
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