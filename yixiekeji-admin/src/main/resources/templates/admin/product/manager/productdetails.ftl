<#include "/admin/commons/_detailheader.ftl" />
<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/datagrid-detailview.js"></script>
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script language="javascript">
    $(function () {
    	$(".boxer").boxer();
        $("#back").click(function () {
        	window.location.href="${domainUrlUtil.urlResources}/admin/product/waitSale";
        });
        
    });
    function detailFormatter(index,row){
        return '<div style="padding:2px"><table class="ddv"></table></div>';
    }
    function stateFormatter(value,row,index){
    	return value && value == 1?"启用" : "不启用";
    }
</script>

<div class="wrapper">
    <div class="formbox-a">
    <h2 class="h2-title">商品详情

        <#--1.addForm----------------->
        <div class="form-contbox">
        
        	<!-- 订单基本信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>商品基本信息</dt>
                <dd class="dd-group">
                	<div class="fluidbox">
                		<p class="p6 p-item">
                            <label class="lab-item">分类名称:</label>
                            <span>${(pro.productCateName)!''}<span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">品牌名称: </label>
                            <span>${(pro.productBrandName)!''}<span>
                        </p>
                	</div>
                	<div class="fluidbox">
                		<p class="p6 p-item">
                            <label class="lab-item">商家分类名称:</label>
                            <span>${(pro.sellerCateName)!''}<span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">商家名称:</label>
                            <span>${(pro.seller)!''}<span>
                        </p>
                	</div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">商品名称:</label>
                            <span>${(pro.name1)!''}<span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">商品促销信息: </label>
                            <span>${(pro.name2)!''}<span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">商品关键字: </label>
                            <span>${(pro.keyword)!''}</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">商品类型:</label>
                            <#if pro??>
                            	<#if pro.isSelf == 1>
	                            <span>自营</span>
	                            <#elseif pro.isSelf == 2>
	                            <span>商家</span>
	                            </#if>
                            </#if>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">商品状态: </label>
                            <#if pro??>
                            	<#if pro.state == 1>
	                            <span>新建</span>
	                            <#elseif pro.state == 2>
	                            <span>提交审核</span>
	                            <#elseif pro.state == 3>
	                            <span>审核通过</span>
	                            <#elseif pro.state == 4>
	                            <span>申请驳回</span>
	                            <#elseif pro.state == 5>
	                            <span>商品删除</span>
	                            <#elseif pro.state == 6>
	                            <span>上架</span>
	                            <#elseif pro.state == 7>
	                            <span>下架</span>
	                            </#if>
                            </#if>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">是否推荐商品:</label>
                            <#if pro??>
                            	<#if pro.isTop == 1>
	                            <span>不推荐</span>
	                            <#elseif pro.isTop == 2>
	                            <span>推荐</span>
	                            </#if>
                            </#if>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">包装清单:</label>
                            <span>${(pro.packing)!''}<span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">商品编码:</label>
                            <span>${(pro.productCode)!'' }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">运费计算类型:</label>
                            <#if pro?? && pro.transportType??>
                            	<#if pro.transportType == 1>
                            	 <span>按件<span>
                            	 <#elseif pro.transportType == 2>
                            	 <span>按重量<span>
                            	 <#elseif pro.transportType == 3>
                            	 <span>按体积<span>
                            	</#if>
                            </#if>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">运费模板名称:</label>
                            <span>${(pro.sellerTransportName)!'' }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">创建时间:</label>
                            <span>${(pro.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}<span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">上架时间:</label>
                            <span>${(pro.upTime?string('yyyy-MM-dd HH:mm:ss'))!''}<span>
                        </p>
                    </div>
                </dd>
            </dl>
            <dl class="dl-group">
            	<dt class="dt-group"><span class="s-icon"></span>商品价格信息</dt>
            	<dd class="dd-group">
            		<div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">成本价: </label>
                            <span>${(pro.costPrice?string('0.00'))!'0.00' }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">保护价: </label>
                            <span>${(pro.protectedPrice?string('0.00'))!'0.00' }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">市场价: </label>
                            <span>${(pro.marketPrice?string('0.00'))!'0.00' }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">商城价: </label>
                            <span>${(pro.mallPcPrice?string('0.00'))!'0.00' }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">商城价Mobile: </label>
                            <span>${(pro.malMobilePrice?string('0.00'))!'0.00' }</span>
                        </p>
                    </div>
            	</dd>
            </dl>
            
            <dl class="dl-group">
            	<dt class="dt-group"><span class="s-icon"></span>商品销量信息</dt>
            	<dd class="dd-group">
            		<div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">虚拟销量: </label>
                            <span>${(pro.virtualSales)!'0' }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">实际销量: </label>
                            <span>${(pro.actualSales)!'0' }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">商品库存: </label>
                            <span>${(pro.productStock)!'0' }</span>
                        </p>
                    </div>
            	</dd>
            </dl>
            
            <dl class="dl-group">
            	<dt class="dt-group"><span class="s-icon"></span>规格信息</dt>
            <dd class="dd-group">
            <div data-options="region:'center'" border="false"
						style="width: 100%; height: 400px;">
			    <table id="dataGrid" class="easyui-datagrid" 
			    					data-options="rownumbers:true
									,idField :'id'
									,singleSelect:true
									,view: detailview
									,autoRowHeight:false
									,fitColumns:false
									,toolbar:'#gridTools'
									,detailFormatter:detailFormatter
									,striped:true
									,pagination:true
									,pageSize:'${pageSize}'
									,fit:true
			    					,url:'${domainUrlUtil.urlResources}/admin/product/list_goods?productId=${(pro.id)!0 }'
			    					,onLoadSuccess:dataGridLoadSuccess
			    					,method:'get'">
					<thead>
						<tr>
							<th field="id" hidden="hidden"></th>
							<th field="normName" width="170" align="left" halign="center">规格值</th>
							<th field="sku" width="170" align="left" halign="center">SKU</th>
							<th field="mallPcPrice" width="170" align="left" halign="center">商城PC价格</th>
							<th field="mallMobilePrice" width="170" align="left" halign="center">商城Mobile价格</th>
							<th field="weight" width="70" align="left" halign="center">重量kg</th>
							<th field="length" width="70" align="left" halign="center">长度cm</th>
							<th field="width" width="70" align="left" halign="center">宽度cm</th>
							<th field="height" width="70" align="left" halign="center">高度cm</th>
							<th field="productStock" width="170" align="left" halign="center">库存</th>
							<th field="state" width="170" align="left" halign="center" formatter="stateFormatter">状态</th>
						</tr>
					</thead>
				</table>
				</div>
            	</dd>
            </dl>
            
            <dl class="dl-group">
            	<dt class="dt-group"><span class="s-icon"></span>商品图片信息</dt>
            	<dd class="dd-group">
            	<#if proPic??>
            		<div class="fluidbox">
            		<#list proPic as pic>
                        <p class="p2 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <span><a href="${(domainUrlUtil.imageResources)!}${(pic.imagePath)!}" rel='gallery' class="boxer"><img alt="" src="${(domainUrlUtil.imageResources)!}${(pic.imagePath)!}" width="100" height="100"></a></span>
                        </p>
            		</#list>
            		</div>
            	</#if>
            	</dd>
            </dl>
            
            <dl class="dl-group">
            	<dt class="dt-group"><span class="s-icon"></span>商品详情</dt>
            	<dd class="dd-group">
            		<div class="fluidbox">
                        <p class="p12 p-item">
                            	<#noescape>${(pro.description)!''}</#noescape>
                        </p>
                    </div>
            	</dd>
            </dl>
            
            
            <#--2.batch button-------------->
            <p class="p-item p-btn">
                <input type="button" id="back" class="btn" value="返回"/>
            </p>
        </div>
    </div>
</div>
<div class="wrapper" id="checkdeliver">	
</div>
<#include "/admin/commons/_detailfooter.ftl" />