<#include "/seller/commons/_head.ftl" />
<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/easyui/datagrid-detailview.js"></script>
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
	
	#previewImgBox li {
		float:left;
	}
</style>
<script language="javascript">
    $(function () {
    	$(".boxer").boxer();
        $("#back").click(function () {
        	window.location.href="${domainUrlUtil.urlResources}/seller/product/saleAll";
        });
    });
    function detailFormatter(index,row){
        return '<div style="padding:2px"><table class="ddv"></table></div>';
    }
    function stateFormatter(value,row,index){
    	return value && value == 1?"启用" : "不启用";
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
					<li><i class="fa fa-home"></i> <a
						href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
					</li>
					<li><a
						href="${domainUrlUtil.urlResources}/seller/product/saleAll">所有商品</a>
					</li>
					<li class="active">商品详情</li>
				</ul>
				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->
			</div>
			
			<!-- 主体头部结束 -->
			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
			<form class="form-horizontal">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">商品基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">分类名称:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.productCateName)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">品牌名称:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.productBrandName)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品名称:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.name1)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品促销信息:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.name2)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品关键字:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.keyword)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品类型:</label> 
						<div class="col-lg-8">
							<#if pro??>
                            	<#if pro.isSelf == 1>
	                            <span class="info-span" >自营</span>
	                            <#elseif pro.isSelf == 2>
	                            <span class="info-span" >商家</span>
	                            </#if>
                            </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品状态:</label> 
						<div class="col-lg-8">
							<#if pro??>
                            	<#if pro.state == 1>
	                            <span class="info-span" >新建</span>
	                            <#elseif pro.state == 2>
	                            <span class="info-span" >提交审核</span>
	                            <#elseif pro.state == 3>
	                            <span class="info-span" >审核通过</span>
	                            <#elseif pro.state == 4>
	                            <span class="info-span" >申请驳回</span>
	                            <#elseif pro.state == 5>
	                            <span class="info-span" >商品删除</span>
	                            <#elseif pro.state == 6>
	                            <span class="info-span" >上架</span>
	                            <#elseif pro.state == 7>
	                            <span class="info-span" >下架</span>
	                            </#if>
                            </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">是否推荐商品:</label> 
						<div class="col-lg-8">
							<#if pro??>
                            	<#if pro.isTop == 1>
	                            <span class="info-span" >不推荐</span>
	                            <#elseif pro.isTop == 2>
	                            <span class="info-span" >推荐</span>
	                            </#if>
                            </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">包装清单:</label> 
						<div class="col-lg-8">
	                        <span class="info-span" >${(pro.packing)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品编码:</label> 
						<div class="col-lg-8">
	                        <span class="info-span" >${(pro.productCode)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">运费计算类型:</label> 
						<div class="col-lg-8">
						 <#if pro?? && pro.transportType??>
                            	<#if pro.transportType == 1>
                            	 <span class="info-span" >按件</span>
                            	 <#elseif pro.transportType == 2>
                            	 <span class="info-span" >按重量</span>
                            	 <#elseif pro.transportType == 3>
                            	 <span class="info-span" >按体积</span>
                            	</#if>
                            </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">运费模板名称:</label> 
						<div class="col-lg-8">
	                        <span class="info-span" >${(pro.sellerTransportName)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">创建时间:</label> 
						<div class="col-lg-8">
	                        <span class="info-span" >${(pro.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">上架时间:</label> 
						<div class="col-lg-8">
	                        <span class="info-span" >${(pro.upTime?string('yyyy-MM-dd HH:mm:ss'))!''}</span>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">商品价格信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">成本价:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.costPrice?string('0.00'))!'0.00' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">保护价:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.protectedPrice?string('0.00'))!'0.00' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">市场价:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.marketPrice?string('0.00'))!'0.00' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商城价:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.mallPcPrice?string('0.00'))!'0.00' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商城价Mobile:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.malMobilePrice?string('0.00'))!'0.00' }</span>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">商品销量信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<#if pro?? && pro.sellerId == 1>
					<div class="form-group">
						<label class="col-lg-2 control-label">虚拟销量:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.virtualSales)!'0' }</span>
						</div>
					</div>
					</#if>
					<div class="form-group">
						<label class="col-lg-2 control-label">实际销量:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.actualSales)!'0' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品库存:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${(pro.productStock)!'0' }</span>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
				<div style="padding-top: 10px;">规格</div>
				<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
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
				    					,url:'${domainUrlUtil.urlResources}/seller/product/list_goods?productId=${(pro.id)!0 }'
				    					,onLoadSuccess:dataGridLoadSuccess
				    					,method:'get'">
						<thead>
							<tr>
								<th field="id" hidden="hidden"></th>
								<th field="normName" width="150" align="left" halign="center">规格值</th>
								<th field="sku" width="150" align="left" halign="center">SKU</th>
								<th field="mallPcPrice" width="150" align="left" halign="center">商城PC价格</th>
								<th field="mallMobilePrice" width="150" align="left" halign="center">商城Mobile价格</th>
								<th field="weight" width="70" align="left" halign="center">重量kg</th>
								<th field="length" width="70" align="left" halign="center">长度cm</th>
								<th field="width" width="70" align="left" halign="center">宽度cm</th>
								<th field="height" width="70" align="left" halign="center">高度cm</th>
								<th field="productStock" width="150" align="left" halign="center">库存</th>
								<th field="state" width="150" align="left" halign="center" formatter="stateFormatter">状态</th>
							</tr>
						</thead>
					</table>
				</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
				<div style="padding-top: 10px;">商品图片信息</div>
				<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
				<div class="form-group">
				<label class="col-lg-1 control-label"></label> 
				<div class="col-lg-10">
				    <dd id='previewImgBox'>
				         <input type="hidden" id="imageSrc" name="imageSrc"/>
				         <ul class='preview-img' id="preview-img">
				             <#if proPic?? && proPic?size &gt; 0>
				               	<#list proPic as pic>
				                    <li>
				                       <div class='img'>
				                           <a href="${(domainUrlUtil.imageResources)!}${(pic.imagePath)!}" rel='gallery' class="boxer"><img src='${(domainUrlUtil.imageResources)!}${(pic.imagePath)!}' width='150' height='150'></a>
				                       </div>
				                   </li>
				                </#list>
				            </#if>
				        </ul>
				   </dd>
			    </div>
			    </div>
			    </div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
				<div style="padding-top: 10px;">商品详情</div>
				<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
			
				<div class="form-group">
					<label class="col-lg-2 control-label">商品描述:</label> 
					<div class="col-lg-8">
						<#noescape>${(pro.description)!''}</#noescape>
					</div>
				</div>
				</div>
				
				</form>
			
				<div class="col-lg-12 col-lg-offset-4 settlementbtn">
					<input type="button" id="back" class="btn btn-danger btn-primary" value="返回" />
				</div>
			</div>
		</div>
	</div>
</div>
<#include "/seller/commons/_listautoheight.ftl">
<#include "/seller/commons/_end.ftl">