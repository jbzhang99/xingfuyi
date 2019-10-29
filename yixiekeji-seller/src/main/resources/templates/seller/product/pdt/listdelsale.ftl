<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/"/>
<#include "inclistjs.ftl"/>

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
                    <li class="active">已删除商品</li>
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
	                                   	<label class="lab-item">商品名称：</label>
                       					 <input type="text" class="txt" id="q_name1" name="q_name1" value="${q_name!''}"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
                </div>
               	<div data-options="region:'center'" border="false">
				    <table id="dataGrid" class="easyui-datagrid"
				           data-options="rownumbers:true
										,singleSelect:false
										,autoRowHeight:false
										,fitColumns:false
										,toolbar:'#gridTools'
										,striped:true
										,pagination:true
										,pageSize:'${pageSize}'
										,fit:true
										,view: detailview
										,detailFormatter:detailFormatter
										,onExpandRow:onExpandRow
				    					,url:'${currentBaseUrl}list?q_state=${q_state!''}'
				    					,queryParams:queryParamsHandler()
				    					,onLoadSuccess:dataGridLoadSuccess
				    					,method:'get'">
				        <thead>
				        <tr>
				            <th field="ck" checkbox="true"></th>
				            <th field="name1" width="400" align="left" halign="center" formatter="proTitle">商品名称</th>
				            <th field="productCateName" width="100" align="center">商品分类</th>
				            <th field="name2" width="150" align="center">促销信息</th>
				            <th field="productBrandName" width="90" align="center">商品品牌</th>
				            <th field="costPrice" width="70" align="center">成本价</th>
				            <th field="protectedPrice" width="70" align="center">保护价</th>
				            <th field="productStock" width="70" align="center">库存</th>
				            <th field="actualSales" width="70" align="center">销量</th>
				            <th field="createTime" width="150" align="center">创建时间</th>
				            <th field="upTime" width="150" align="center">上架时间</th>
				            <th field="sellerCateName" width="70" align="center">店铺分类</th>
				            <th field="sellerIsTop" width="70" align="center" formatter="sellerIsTopFormat">店铺推荐</th>
				            <th field="state" width="90" align="center" formatter="stateFormat">状态</th>
				        </tr>
				        </thead>
				    </table>
				
				<#--3.function button----------------->
				    <div id="gridTools">
				        <a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
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