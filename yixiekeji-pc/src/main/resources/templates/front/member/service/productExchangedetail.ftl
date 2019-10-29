<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/productback.css">

<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'>
			<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
		</strong>
		<span>
			&nbsp;>&nbsp;
			<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技</a>
		</span>
		<span>
			&nbsp;>&nbsp;
			<a href='javascript:void(0)'>换货申请</a>
		</span>
	</div>
</div>
		
<form id ="productBackForm" action="${(domainUrlUtil.urlResources)!}/member/doproductexchange.html">
<!-- 隐藏域 -->
<input type="hidden" name="sellerId" value="${(orderProduct.sellerId)!''}">
<input type="hidden" name="seller" value="${(orderProduct.sellerId)!''}">
<input type="hidden" name="orderId" value="${(order.id)!''}"> 
<!-- 网单id -->
<input type="hidden" name="orderProductId" value="${(orderProduct.id)!''}">
<input type="hidden" name="productId" value="${(orderProduct.productId)!''}">

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder'>
		<h3>换货信息</h3>
		<table class='table_1' id="refushtable" cellspacing="0" cellpadding="0" border='1'>
			<tbody>
				<tr>
					<th>商品名称</th>
					<th>购买数量</th>
				</tr>
				<tr>
					<td>
						<ul class='list-proinfo'>
							<li>
								<#if orderProduct??>
									<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}.html' target="_blank">
										<img src='${(domainUrlUtil.imageResources)!}${orderProduct.productLeadLittle}' width='50' height='50' title='${(orderProduct.productName)!''}'>
									</a>
									<div class='p-info-name'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}.html' target='_blank'>${(orderProduct.productName)!''}</a>
									</div>
								</#if>
							</li>
						</ul>
					</td>
					<td>${(orderProduct.number)!''}</td>
				</tr>
			</tbody>
		</table>
		<div class='apply-form' id='consignee-form'>
			<div class='repair-steps'>
				<div class='repair-step repair-step-curr'>
					<div class='miaoshuDiv'>
						<!-- 换货数量 -->
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换货数量：</span> 
							</span>
							<div >
								 ${(info.number)!0 }
							</div>
						</div>
						<!-- 问题描述 -->
						<div class='sellerPrompt'>
							<span class='label'>
								<span>问题描述：</span> 
							</span>
							<div >
								 ${(info.question)!'' }
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>状&#12288;&#12288;态：</span> 
							</span>
							<div>
								 <#if  info.state??>
					  				<#assign state = info.state>
					  				<#if state==1>未处理
						  				<#elseif state==2>审核通过
						  				<#elseif state==3>用户发回退件
						  				<#elseif state==4>商家收到退件
						  				<#elseif state==5>商家发出换件
						  				<#elseif state==6>原件退还
						  				<#elseif state==7>不处理
						  				<#else>
					  				</#if>
			  		    		</#if>
							</div>
							<em class='em-errMes'></em>	
						</div>
						
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换件收货地址：</span> 
							</span>
							<div>
								${(info.addressAll)!''}${(info.addressInfo)!''}
							</div>
						</div>
						
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换件收货邮编：</span> 
							</span>
							<div>
								${(info.zipCode)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换件收货人姓名：</span> 
							</span>
							<div>
								${(info.changeName)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换件收货人电话：</span> 
							</span>
							<div>
								${(info.phone)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换件物流公司：</span> 
							</span>
							<div>
								${(info.logisticsName)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>换件物流单号：</span> 
							</span>
							<div>
								${(info.logisticsNumber)!''}
							</div>
						</div>
						<!-- 退件 -->
						<div class='sellerPrompt'>
							<span class='label'>
								<span>退件收货地址：</span> 
							</span>
							<div>
								${(info.addressAll2)!''}${(info.addressInfo2)!''}
							</div>
						</div>
						
						<div class='sellerPrompt'>
							<span class='label'>
								<span>退件收货邮编：</span> 
							</span>
							<div>
								${(info.zipCode2)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>退件收货人姓名：</span> 
							</span>
							<div>
								${(info.changeName2)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>退件收货人电话：</span> 
							</span>
							<div>
								${(info.phone2)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>退件物流公司：</span> 
							</span>
							<div>
								${(info.logisticsName2)!''}
							</div>
						</div>
						<div class='sellerPrompt'>
							<span class='label'>
								<span>退件物流单号：</span> 
							</span>
							<div>
								${(info.logisticsNumber2)!''}
							</div>
						</div>
						
						<div class='sellerPrompt'>
							<span class='label'>
								<span>处理意见：</span> 
							</span>
							<div>
								${(info.remark)!'无'}
							</div>
							<em class='em-errMes'></em>	
						</div>
					</div>
					<!-- end -->
				</div>
			</div>
		</div>
		<!-- 订单跟踪 -->
		<#if memberProductExchangeLogList?? && memberProductExchangeLogList?size &gt;0>
			<div class='mc-box'>
				<div class="payment-info">
					<h3>换货操作记录</h3>
					<table cellpadding='0' cellspacing='0' width='100%'>
						<tbody id='tbody_track'>
							<tr>
								<th class='15%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">处理时间</strong>
								</th>	
								<th class='50%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">处理信息</strong>
								</th>
								<th class='35%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">操作人</strong>
								</th>
							</tr>
						</tbody>
						<tbody>
							<#list memberProductExchangeLogList as memberProductExchangeLog> 
								<tr>
									<td>${memberProductExchangeLog.createTime?string("yyyy-MM-dd HH:mm:ss") }</td>
									<td>${memberProductExchangeLog.content}</td>
									<td>${memberProductExchangeLog.operatingName}</td>
								</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</#if>
		<#if memberProductExchangeLogBackList?? && memberProductExchangeLogBackList?size &gt;0>
			<div class='mc-box'>
				<div class="payment-info">
					<h3>换货时退件物流信息</h3>
					<table cellpadding='0' cellspacing='0' width='100%'>
						<tbody id='tbody_track'>
							<tr>
								<th class='15%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">处理时间</strong>
								</th>	
								<th class='50%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">处理信息</strong>
								</th>
								<th class='35%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">操作人</strong>
								</th>
							</tr>
						</tbody>
						<tbody>
							<#list memberProductExchangeLogBackList as memberProductExchangeLog> 
								<tr>
									<td>${memberProductExchangeLog.createTime?string("yyyy-MM-dd HH:mm:ss") }</td>
									<td>${memberProductExchangeLog.content}</td>
									<td>${memberProductExchangeLog.operatingName}</td>
								</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</#if>
		<#if memberProductExchangeLogExchangeList?? && memberProductExchangeLogExchangeList?size &gt;0>
			<div class='mc-box'>
				<div class="payment-info">
					<h3>换货时换件物流信息</h3>
					<table cellpadding='0' cellspacing='0' width='100%'>
						<tbody id='tbody_track'>
							<tr>
								<th class='15%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">处理时间</strong>
								</th>	
								<th class='50%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">处理信息</strong>
								</th>
								<th class='35%'>
									<strong style="font-size: 14px;color:#222;font-weight: 400;">操作人</strong>
								</th>
							</tr>
						</tbody>
						<tbody>
							<#list memberProductExchangeLogExchangeList as memberProductExchangeLog> 
								<tr>
									<td>${memberProductExchangeLog.createTime?string("yyyy-MM-dd HH:mm:ss") }</td>
									<td>${memberProductExchangeLog.content}</td>
									<td>${memberProductExchangeLog.operatingName}</td>
								</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</#if>
	</div>
</div>
 
</form>
<script type="text/javascript">
	
	$(function(){
		//控制左侧菜单选中
		$("#productexchange").addClass("currnet_page");
	});
	
</script>

<#include "/front/commons/_endbig.ftl" />