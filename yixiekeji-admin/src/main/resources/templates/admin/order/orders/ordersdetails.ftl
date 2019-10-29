<#include "/admin/commons/_detailheader.ftl" />
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/easyui/datagrid-detailview.js"></script>
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script language="javascript">
    $(function () {
        $("#back").click(function () {
        	window.location.href="${domainUrlUtil.urlResources}/admin/order/orders";
        });
        
        
    });
    function detailFormatter(index,row){
        return '<div style="padding:2px"><table class="ddv"></table></div>';
    }
</script>

<div class="wrapper">
    <div class="formbox-a">
    <h2 class="h2-title">订单详情

        <#--1.addForm----------------->
        <div class="form-contbox">
        
        	<!-- 订单基本信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>订单基本信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">订单号: </label>
                            <span>${(orders.orderSn)!''}<span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">订单类型: </label>
                            <#if orders??>
	                            <#if orders.orderType == 1>
	                            <span>普通订单</span>
	                            <#elseif orders.orderType == 2>
	                            <span>限时抢购订单</span>
	                            <#elseif orders.orderType == 3>
	                            <span>团购订单</span>
	                            <#elseif orders.orderType == 4>
	                            <span>竞价定金订单</span>
	                            <#elseif orders.orderType == 5>
	                            <span>竞价尾款订单</span>
	                            <#elseif orders.orderType == 6>
	                            <span>积分商城订单</span>
	                            </#if>
                            </#if>
                        </p>
                    </div>
                    
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">订单状态: </label>
                            <#if orders??>
                            	<#if orders.orderState == 1>
	                            <span>未付款的订单</span>
	                            <#elseif orders.orderState == 2>
	                            <span>待确认的订单</span>
	                            <#elseif orders.orderState == 3>
	                            <span>待发货的订单</span>
	                            <#elseif orders.orderState == 4>
	                            <span>已发货的订单</span>
	                            <#elseif orders.orderState == 5>
	                            <span>已完成的订单</span>
	                            <#elseif orders.orderState == 6>
	                            <span>取消的订单</span>
	                            </#if>
                            </#if>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">下单时间: </label>
                            <span>${(orders.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}<span>
                        </p>
                    </div>
                    
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">订单完成时间: </label>
                            <span>${(orders.finishTime?string('yyyy-MM-dd HH:mm:ss'))!''}<span>
                        </p>
                    </div>
                </dd>
            </dl>
            
            <!-- 订单金额信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>订单金额信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">订单总金额: </label>
                            <span>${(orders.moneyOrder?string('0.00'))!0.00 }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">商品金额: </label>
                            <span>${(orders.moneyProduct?string('0.00'))!0.00 }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">物流费用: </label>
                            <span>${(orders.moneyLogistics?string('0.00'))!0.00 }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">优惠金额总额: </label>
                            <span>${(orders.moneyDiscount?string('0.00'))!0.00 }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">余额账户支付总金额: </label>
                            <span>${(orders.moneyPaidBalance?string('0.00'))!0.00 }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">现金支付金额: </label>
                            <span>${(orders.moneyPaidReality?string('0.00'))!0.00 }</span>
                        </p>
                    </div>
                    
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">优惠券优惠金额: </label>
                            <span>${(orders.moneyCoupon?string('0.00'))!0.00 }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">订单满减金额: </label>
                            <span>${(orders.moneyActFull?string('0.00'))!0.00 }</span>
                        </p>
                    </div>
                    
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">积分换算金额: </label>
                            <span>${(orders.moneyIntegral?string('0.00'))!0.00 }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">退款的金额: </label>
                            <span>${(orders.moneyBack?string('0.00'))!0.00 }</span>
                        </p>
                    </div>
                </dd>
            </dl>
            
            <!-- 订单发票信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>订单发票信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">发票状态: </label>
                            <#if orders??>
                            	<#if orders.invoiceStatus == 0>
                            		<span>不要发票</span>
                            	<#elseif orders.invoiceStatus == 1>
                            		<span>单位</span>
                            	<#elseif orders.invoiceStatus == 2>
                            		<span>个人</span>
                            	</#if>
                            </#if>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">发票抬头: </label>
                            <span>${(orders.invoiceTitle)!''}<span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">发票内容: </label>
                            <span>${(orders.invoiceType)!''}</span>
                        </p>
                    </div>
                </dd>
            </dl>
            
            <!-- 订单支付信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>订单付款信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">付款状态: </label>
                            <#if orders??>
                            	<#if orders.paymentStatus == 0>
                            		<span>买家未付款</span>
                            	<#elseif orders.paymentStatus == 1>
                            		<span>买家已付款</span>
                            	</#if>
                            </#if>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">支付方式: </label>
                            <span>${(orders.paymentName)!''}<span>
                        </p>
                    </div>
                </dd>
            </dl>
            
            <!-- 订单收货人信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>订单收货人信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">收货人姓名: </label>
                            <span>${(orders.name)!'' }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">收货人邮编: </label>
                            <span>${(orders.zipCode)!'' }</span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">收货人手机: </label>
                            <span>${(orders.mobile)!'' }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">收货人邮箱: </label>
                            <span>${(orders.email)!''}<span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">收货人地址: </label>
                            <span>${(orders.addressAll)!''}${(orders.addressInfo)!''}<span>
                        </p>
                    </div>
                </dd>
            </dl>
            
            <!-- 订单发货信息 -->
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>订单发货信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">物流公司: </label>
                            <span>${(orders.logisticsName)!'' }</span>
                        </p>
                        <p class="p6 p-item">
                            <label class="lab-item">发票快递单号: </label>
                            <span>${(orders.logisticsNumber)!''}<span>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p6 p-item">
                            <label class="lab-item">发货时间: </label>
                            <span>${(orders.deliverTime?string('yyyy-MM-dd HH:mm:ss'))!'' }</span>
                        </p>
                    </div>
                </dd>
            </dl>
            
            <dl class="dl-group">
            	<dt class="dt-group"><span class="s-icon"></span>网单基本信息</dt>
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
			    					,url:'${domainUrlUtil.urlResources}/admin/order/ordersProduct/getOrdersProduct?orderId=${(orders.id)!0}'
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