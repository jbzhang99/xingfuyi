<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/areaSupport.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
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
			<a href='javascript:void(0)'>退货申请查看</a>
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
				<h3>申请售后</h3>
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
								<!-- 退货数量 -->
								<div class='sellerPrompt'>
									<span class='label'>
										<span>退货数量：</span> 
									</span>
									<div class='fl'>
										 ${(info.number)!0 }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<!-- 问题描述 -->
								<div class='sellerPrompt'>
									<span class='label'>
										<span>问题描述：</span> 
									</span>
									<div class='fl'>
										 ${(info.question)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								
								<#if info??>
									<div class='sellerPrompt'>
										<span class='label'>
											<span>退款金额：</span> 
										</span>
										<div class='fl'>&yen;${(info.backMoney)?string("0.00")!""}</div>
									</div>
									<div class='sellerPrompt'>
										<span class='label'>
											<span>返回积分：</span> 
										</span>
										<div class='fl'>${(info.backIntegral)!""}</div>
									</div>
									<#if couponUser?? >
									<div class='sellerPrompt'>
										<span class='label'>
											<span>返回优惠券：</span> 
										</span>
										<div class='fl'>${(couponUser.couponSn)!""}</div>
									</div>
									</#if>
									
							  		<#if info.stateReturn==1>
							  			<div class='sellerPrompt'>
											<span class='label'>
												<span>状&#12288;&#12288;态：</span> 
											</span>
											<div class='fl'>未处理</div>
										</div>
							  		<#elseif info.stateReturn==2>
							  			<div class='sellerPrompt'>
											<span class='label'>
												<span>状&#12288;&#12288;态：</span> 
											</span>
											<div class='fl'>审核通过</div>
										</div>
							  		<#elseif info.stateReturn==3>
							  			<div class='sellerPrompt'>
											<span class='label'>
												<span>状&#12288;&#12288;态：</span> 
											</span>
											<div class='fl'>用户发货</div>
										</div>
							  		<#elseif info.stateReturn==4>
							  			<div class='sellerPrompt'>
											<span class='label'>
												<span>状&#12288;&#12288;态：</span> 
											</span>
											<div class='fl'>店铺收货</div>
										</div>
										<div class='sellerPrompt'>
											<span class='label'>
												<span>退款状态：</span> 
											</span>
											<div class='fl'>
												<#if info.stateMoney==1>未退款</#if>
												<#if info.stateMoney==2>退款到账户</#if>
												<#if info.stateMoney==3>退款到银行</#if>
											</div>
										</div>
										<div class='sellerPrompt'>
											<span class='label'>
												<span>退款时间：</span> 
											</span>
											<div class='fl'>
												<#if info.backMoneyTime??>
													${(info.backMoneyTime)?string("yyyy-MM-dd HH:mm:ss")}
												<#else>
													正在处理
												</#if>
											</div>
										</div>
							  		<#else>
							  			<div class='sellerPrompt'>
											<span class='label'>
												<span>状&#12288;&#12288;态：</span> 
											</span>
											<div class='fl'>不予处理</div>
											<!-- 此时可以发起投诉 -->
								  			<#assign canComplain = 'true'/>
										</div>
							  		</#if>
							  	</#if>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>商家收货地址：</span> 
									</span>
									<div class='fl'>
										 ${(info.addressAll)!'' }${(info.addressInfo)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>邮编：</span> 
									</span>
									<div class='fl'>
										 ${(info.zipCode)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>商家联系人手机：</span> 
									</span>
									<div class='fl'>
										 ${(info.contactPhone)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>商家联系人姓名：</span> 
									</span>
									<div class='fl'>
										 ${(info.contactName)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>退件物流公司：</span> 
									</span>
									<div class='fl'>
										 ${(info.logisticsName)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>退件物流单号：</span> 
									</span>
									<div class='fl'>
										 ${(info.logisticsNumber)!'' }
									</div>
									<em class='em-errMes'></em>	
								</div>
								<div class='sellerPrompt'>
									<span class='label'>
										<span>处理意见：</span> 
									</span>
									<div class='fl'>${(info.remark)!'无'}</div>
								</div>
							</div>
							<!-- end -->
						</div>
					</div>
				</div>
				<!-- 物流信息 -->
				<#if memberProductBackLogList?? && memberProductBackLogList?size &gt; 0>
					<div class='mc-box'>
						<div class="payment-info">
							<h3>退货物流信息</h3>
							<table cellpadding='0' cellspacing='0' width='100%' style="border: 0px">
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
									<#list memberProductBackLogList as memberProductBackLog> 
										<tr>
											<td>${memberProductBackLog.createTime?string("yyyy-MM-dd HH:mm:ss") }</td>
											<td>${memberProductBackLog.content}</td>
											<td>${memberProductBackLog.operatingName}</td>
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
		$("#productback").addClass("currnet_page");

	});
	
</script>

<#include "/front/commons/_endbig.ftl" />
