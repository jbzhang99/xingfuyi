<#include "/seller/commons/_head.ftl" />
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/seller/easyui/datagrid-detailview.js"></script>
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script language="javascript">
    $(function () {
        $("#back").click(function () {
        	window.location.href="${domainUrlUtil.urlResources}/seller/order/orders";
        });
    });
    function detailFormatter(index,row){
        return '<div style="padding:2px"><table class="ddv"></table></div>';
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
						href="${domainUrlUtil.urlResources}/seller/order/orders">全部订单</a>
					</li>
					<li class="active">订单详情</li>
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
					
					<div style="padding-top: 10px;">订单基本信息</div>
					
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					
					<div class="form-group">
						<label class="col-lg-2 control-label">订单号:</label> 
						<div class="col-lg-8">
							<span class="info-span" >${orders.orderSn!}</span>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-lg-2 control-label">订单类型:</label> 
						<div class="col-lg-8">
							<#if orders??>
		                        <#if orders.orderType == 1>
		                        <span class="info-span" >普通订单</span>
		                        <#elseif orders.orderType == 2>
		                        <span class="info-span" >限时抢购订单</span>
		                        <#elseif orders.orderType == 3>
		                        <span class="info-span" >团购订单</span>
		                        <#elseif orders.orderType == 4>
		                        <span class="info-span" >竞价定金订单</span>
		                        <#elseif orders.orderType == 5>
		                        <span class="info-span" >竞价尾款订单</span>
		                        <#elseif orders.orderType == 6>
		                        <span class="info-span" >积分商城订单</span>
		                        </#if>
	                        </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">订单状态:</label> 
						<div class="col-lg-8">
							<#if orders??>
	                            <#if orders.orderState == 1>
		                           <span class="info-span" >未付款的订单</span>
		                        <#elseif orders.orderState == 2>
		                           <span class="info-span" >待确认的订单</span>
		                        <#elseif orders.orderState == 3>
		                           <span class="info-span" >待发货的订单</span>
		                        <#elseif orders.orderState == 4>
		                           <span class="info-span" >已发货的订单</span>
		                        <#elseif orders.orderState == 5>
		                           <span class="info-span" >已完成的订单</span>
		                        <#elseif orders.orderState == 6>
		                           <span class="info-span" >取消的订单</span>
		                        </#if>
	                        </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">下单时间:</label> 
						<div class="col-lg-8">
		                    <span class="info-span" >${(orders.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">订单完成时间:</label> 
						<div class="col-lg-8">
		                    <span class="info-span" >${(orders.finishTime?string('yyyy-MM-dd HH:mm:ss'))!''}</span>
						</div>
					</div>
					
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">订单金额信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">订单总金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyOrder?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyProduct?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">物流费用: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyLogistics?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">优惠金额总额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyDiscount?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">余额账户支付总金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyPaidBalance?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">现金支付金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyPaidReality?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">优惠券优惠金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyCoupon?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">订单满减金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyActFull?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">积分换算金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyIntegral?string('0.00'))!0.00 }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">退款的金额: </label> 
						<div class="col-lg-8">
							<span class="info-span" >${(orders.moneyBack?string('0.00'))!0.00 }</span>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">订单发票信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">发票状态: </label> 
						<div class="col-lg-8">
							<#if orders??>
                            	<#if orders.invoiceStatus == 0>
                            		<span class="info-span" >不要发票</span>
                            	<#elseif orders.invoiceStatus == 1>
                            		<span class="info-span" >单位</span>
                            	<#elseif orders.invoiceStatus == 2>
                            		<span class="info-span" >个人</span>
                            	</#if>
                            </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">发票抬头: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.invoiceTitle)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">发票内容: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.invoiceType)!''}</span>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">订单付款信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">付款状态: </label> 
						<div class="col-lg-8">
							<#if orders??>
                            	<#if orders.paymentStatus == 0>
                            		<span class="info-span" >买家未付款</span>
                            	<#elseif orders.paymentStatus == 1>
                            		<span class="info-span" >买家已付款</span>
                            	</#if>
                            </#if>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">支付方式: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.paymentName)!''}</span>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">订单收货人信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">收货人姓名: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.name)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">收货人邮编: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.zipCode)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">收货人手机: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.mobile)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">收货人邮箱: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.email)!''}</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">收货人地址: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.addressAll)!''}${(orders.addressInfo)!''}</span>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">订单发货信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					<div class="form-group">
						<label class="col-lg-2 control-label">物流公司: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.logisticsName)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">发票快递单号: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.logisticsNumber)!'' }</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">发货时间: </label> 
						<div class="col-lg-8">
                            <span class="info-span" >${(orders.deliverTime?string('yyyy-MM-dd HH:mm:ss'))!'' }</span>
						</div>
					</div>
				</div>
				
				</form>
				
				<div style="padding-top: 10px;">网单信息</div>
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
				    					,url:'${domainUrlUtil.urlResources}/seller/order/ordersProduct/getOrdersProduct?orderId=${(orders.id)!0 }'
				    					,onLoadSuccess:dataGridLoadSuccess
				    					,method:'get'">
						<thead>
							<tr>
								<th field="id" hidden="hidden"></th>
								<th field="productName" width="150" align="left" halign="center">货品名称</th>
								<th field="specInfo" width="120" align="left">规格</th>
								<th field="productSku" width="120" align="left">商品SKU</th>
								<th field="moneyPrice" width="100" align="center">商品单价</th>
								<th field="number" width="100" align="center">商品数量</th>
								<th field="moneyAmount" width="100" align="center">网单金额</th>
								<th field="moneyActSingle" width="100" align="center">立减优惠金额</th>
								<th field="createTime" width="120" align="center">创建时间</th>
								<th field="updateTime" width="120" align="center">修改时间</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="col-lg-12 col-lg-offset-4 settlementbtn">
					<input type="button" id="back" class="btn btn-danger btn-primary" value="返回" />
				</div>
			</div>
		</div>
	</div>
</div>

<#include "/seller/commons/_listautoheight.ftl">
<#include "/seller/commons/_end.ftl">