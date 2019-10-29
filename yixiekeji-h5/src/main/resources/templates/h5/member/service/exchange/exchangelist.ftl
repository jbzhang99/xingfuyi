
		<#if exchangeList?? && exchangeList?size &gt; 0 >
		<#list  exchangeList as exchange>
		    <div class="oder-list sev-list">
		    	<h2 class="flex flex-pack-justify sev_regoods">
		    		<div>
		    		  <p class="mar-bt">订单编号：${(exchange.ordersProduct.ordersSn)!0}</p>
		    		  <p>申请时间：${(exchange.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</p>
		    		</div>
		    		<div>
		    			<font class="clr53">
		    				<#assign canComplain = 'false'/>
		  		    		<#if  exchange.state??>
				  				<#assign state = exchange.state>
				  				<#if state==1>未处理
					  				<#elseif state==2>审核通过
					  				<#elseif state==3>用户发回退件
					  				<#elseif state==4>商家收到退件
					  				<#elseif state==5>商家发出换件
					  				<#elseif state==6>原件退还
					  					<!-- 此时可以发起投诉 -->
					  					<#assign canComplain = 'true'/>
					  				<#elseif state==7>不处理
					  					<!-- 此时可以发起投诉 -->
					  					<#assign canComplain = 'true'/>
					  				<#else>
				  				</#if>
		  		    		</#if>
		    			</font>
		    		</div>
		    	</h2>
		    	<a href="${(domainUrlUtil.urlResources)!}/product/${(exchange.ordersProduct.productId)!0}.html?goodId=${(exchange.ordersProduct.productGoodsId)!0}" class="block">
			    	<dl class="img-ul flex">
			    	  <dt style="width:80px; height:80px;"><img src="${(domainUrlUtil.imageResources)!}${exchange.product.masterImg}"></dt>
			    	  <dd class="flex-2">
			    	    <div class="product_name">${(exchange.ordersProduct.productName)!''}&nbsp;${(exchange.ordersProduct.specInfo)!''}</div>
			    	    <div>x${(exchange.ordersProduct.number)!0}</div>
			    	  </dd>
			    	</dl>
		    	</a>
		    	<div class="order-set-btn">
		    		<#if canComplain??>
						&nbsp;
						<#if canComplain=='true'>
							<a href="${(domainUrlUtil.urlResources)!}/member/addcomplaint.html?productExchangeId=${(exchange.id)!''}&orderProductId=${(exchange.orderProductId)!''}&orderId=${exchange.orderId}" class="btn">申诉</a>
						</#if>
					</#if>
					<#if exchange.state?? && exchange.state == 2>
						&nbsp;
						<a href='${(domainUrlUtil.urlResources)!}/member/exchangeDeliverGoods.html?exchangeid=${(exchange.id)!''}' class='btn'>发货</a>
					</#if>
					<#if exchange.state?? && (exchange.state == 3 || exchange.state == 4 || exchange.state == 5 || exchange.state == 6)>
						&nbsp;
						<a href='${(domainUrlUtil.urlResources)!}/member/lookExchangeLogistics.html?exchangeid=${(exchange.id)!''}' class='btn'>查看物流信息</a>
					</#if>
					<a href="javascript:;" class="btn" onclick="viewDetail(this)">查看</a>
				</div>
				
				<div class='bgff pad10 evalute-list' style="margin-top:0;">
					<div class='starbox'>
					    <div class='stararrow-up'></div>
					    <div class='starlist flex pad10'>
						  <div class="pad-t6">换货数量：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	  <i class='form-control' disabled="disabled">${(exchange.number)!0}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">问题描述：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	  <textarea class='form-control' rows='3' id='question' name='question' disabled="disabled">${(exchange.question)!""}</textarea>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">换件收货地址：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.addressAll)!''}${(exchange.addressInfo)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">换件收货邮编：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.zipCode)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">换件收货人姓名：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.changeName)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">换件收货人电话：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.phone)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">换件物流公司：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.logisticsName)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">换件物流单号：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.logisticsNumber)!''}</i>
						  </div>
						</div>
						<!-- 退件 -->
						<div class='starlist flex pad10'>
						  <div class="pad-t6">退件收货地址：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.addressAll2)!''}${(exchange.addressInfo2)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">退件收货邮编：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.zipCode2)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">退件收货人姓名：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.changeName2)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">退件收货人电话：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.phone2)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">退件物流公司：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.logisticsName2)!''}</i>
						  </div>
						</div>
						<div class='starlist flex pad10'>
						  <div class="pad-t6">退件物流单号：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	    <i class='form-control' disabled="disabled">${(exchange.logisticsNumber2)!''}</i>
						  </div>
						</div>
						
						<div class='starlist flex pad10'>
						  <div class="pad-t6">处理意见：&nbsp;</div>
						  <div class='flex-2 expertxt'>
						  	  <textarea class='form-control' rows='3' id='remark' name='remark' disabled="disabled">${(exchange.remark)!'无'}</textarea>
						  </div>
						</div>
					</div>
				</div>
		    </div>
	    </#list>
		</#if>
