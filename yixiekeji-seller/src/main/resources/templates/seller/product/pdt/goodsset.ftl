<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/"/>
<style>
	.table-responsive .table td .form-group{
		margin:0;
	}
	
	.table-responsive .form-group .help-block{
		float: left;
	}
	
	.table-responsive .form-group i.form-control-feedback{
		right:0;
	}
</style>
<script language="javascript">
    var protectedPrice = "${(product.protectedPrice)!0}";
    var isNorm = Number("${(product.isNorm)!1}");
    var domain = "${domainUrlUtil.urlResources}";
    var from = "${from}";
    var goodssize = 0;
    <#if goodslist??>
    	goodssize = Number("${goodslist?size}");
    </#if>
</script>
<script src="${domainUrlUtil.urlResources}/resources/seller/js/product/pdt/goodsset.js"></script>

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
					<li>
						<#if from == 'onSale'>
						<a href="${domainUrlUtil.urlResources}/seller/product/onSale">在售商品</a>
						<#else>
						<a href="${domainUrlUtil.urlResources}/seller/product/waitSale">待售商品</a>
						</#if>
					</li>
					<li class="active">修改库存价格 </li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal"
							action="${currentBaseUrl}goodssetSumit"
						 	data-bv-message="该项必填">
						 	
						<input type="hidden" id="goodsnum" name="goodsnum" />
						<input type="hidden" name="id" value="#{(product.id)!''}"/>
			            <input type="hidden" name="goodsinfo" id="goodsinfo"/>
			            <input type="hidden" name="from" value="${from}"/>
					
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品名称：</label>
							<div class="col-lg-4">
								<label class="control-label">${(product.name1)!}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品保护价：</label>
							<div class="col-lg-4">
								<input type="text" readonly="readonly"
									value="${(product.protectedPrice)!0}" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商城价：</label>
							<div class="col-lg-4">
								<input type="text" id="mallPcPrice" name="mallPcPrice"
									<#if product.isNorm != 2>
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的价格"
									min="0.01"
									max="999999"
									required
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.01"
									<#else>
									readonly="readonly"
									</#if>
									value="${(product.mallPcPrice)!''}"  class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">价格必须是0.01~999999之间的数字，且不能高于市场价。
                                此价格为商品实际销售价格，如果商品存在规格，该价格将取最低价格</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商城价(mobile)：</label>
							<div class="col-lg-4">
								<input type="text" id="malMobilePrice" name="malMobilePrice"
                                  		value="${(product.malMobilePrice)!''}" 
                                  		<#if product.isNorm != 2>
										data-bv-numeric="true"
										data-bv-numeric-message="请输入正确的价格"
										min="0.01"
										max="999999"
										required
										pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
	                             	  	data-bv-regexp-message="金额保留两位小数" 
										data-bv-lessthan-inclusive="true"
		                                data-bv-lessthan-message="金额必须小于999999"
		                                data-bv-greaterthan-inclusive="true"
		                               	data-bv-greaterthan-message="金额必须大于0.01"
										<#else>
										readonly="readonly"
										</#if>
										class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight"> 价格必须是0.01~999999之间的数字，且不能高于市场价。
                                此价格为手机商城商品实际销售价格，如果商品存在规格，该价格将取最低价格</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品库存：</label>
							<div class="col-lg-4">
								<input type="text" id="productStock" name="productStock"
                                   	value="${(product.productStock)!''}"
                                   	<#if product.isNorm != 2>
									required
									min="0"
									max="999999"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查"
                             	  	data-bv-lessthan-message="库存数必须小于999999"
                             	  	data-bv-greaterthan-message="库存数必须大于0"
									<#else>
									readonly="readonly"
									</#if>
									class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight"> 0~999999之间的整数，如发生交易，系统会自动计算库存</label>
						
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>促销信息：</label>
							<div class="col-lg-8">
								<textarea id="name2" name="name2"
                                   class="form-control"
                                   cols="90" rows="5">${(product.name2)!''}</textarea>
							</div>
						</div>
						
						<#if product.isNorm==2>
						<!-- sku -->
						<div class="table-responsive">
		                    <table class="table table-bordered table-condensed ejtablestyle">
		                        <tr>
		                          <th>规格值</th>
		                          <th>sku</th>
		                          <th>库存</th>
		                          <th>PC价格</th>
		                          <th>mobile价格</th>
		                        </tr>
		                        <#if goodslist?? && goodslist?size &gt; 0>
                                <#list goodslist as good>
		                        <tr style="border:1px solid #e2e1e1" name="goodstr">
                                      <td style="display: none"><input name="goodsid_${good_index}" type="hidden" value="${(good.id)!''}"></td>
                                      <td>
                                          <label>${(good.normName)!''}</label>
                                      </td>
                                      <td style="padding:6px;">
                                          <label>${(good.sku)!''}</label>
                                      </td>
                                      <td>
                                      	<div class="form-group">
                                          <input name="inventory_details_stock_${good_index}" type="text" 
                                          		id="inventory_details_stock_${good_index}" 
	                                          	class="form-control"
	                                            value="${(good.productStock)!''}" class="styleStock">
	                                     </div>
                                      </td>
                                      <td>
                                      	<div class="form-group">
                                          <input name="inventory_details_pprice_${good_index}" type="text" id="inventory_details_pprice_${good_index}" 
	                                         	class="form-control"
	                                           	value="${(good.mallPcPrice)!''}" class="stylePrice">
	                                    </div>
                                      </td>
                                      <td>
                                      	<div class="form-group">
                                          <input name="inventory_details_mprice_${good_index}" type="text" id="inventory_details_mprice_${good_index}" 
                                          		class="form-control"
                                          		value="${(good.mallMobilePrice)!''}" class="stylePrice">
                                         </div>
                                      </td>
                                 </tr>
                                 <#if good_index == goodslist?size -1>
                                 <script>
                                 	$("#goodsnum").val('${goodslist?size}');
                                 </script>
                                 </#if>
                                </#list>
                                </#if>
		                    </table>
		                  </div>
						<!-- sku end-->
						</#if>
						
						<div class="form-group goodsset-btn-div">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="submit" class="btn btn-danger btn-primary">提交</button>
								<button type="button" class="btn btn-danger back btn-primary">返回</button>
							</div>
						</div>
					</form>

				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<#include "/seller/commons/_addcommonfooter.ftl"> 
<#include "/seller/commons/_end.ftl">