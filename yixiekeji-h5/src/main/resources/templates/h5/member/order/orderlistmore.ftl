<#import "/h5/commons/_macro_controller.ftl" as cont/>
		<#if ordersList?? && ordersList?size &gt; 0 >
		<#list ordersList as order>
		    <div class="oder-list">
		    	<h2 class="flex flex-pack-justify">
		    		<div>订单号：${(order.orderSn)!''}</div>
		    		<!-- 订单状态：1、未付款的订单；2、待确认的订单；3、待发货的订单；4、已发货的订单；5、已完成的订单；6、取消的订单 -->
		    		<#if order.orderState??>
		  				<#assign state = order.orderState>
		  				<#if state==1>
		  					<div><font id="orderStateFont${(order.id)!0}" style="color: #e93b3d;">等待付款</font></div>
		  				<#elseif state==2>
		  					<div><font id="orderStateFont${(order.id)!0}" style="color: #e93b3d;">等待确认</font></div>
		  				<#elseif state==3>
		  					<div><font id="orderStateFont${(order.id)!0}" style="color:#999;">备货中</font></div>
		  				<#elseif state==4>
		  					<div><font id="orderStateFont${(order.id)!0}" style="color:#999;">已发货</font></div>
		  				<#elseif state==5>
		  					<div><font id="orderStateFont${(order.id)!0}" style="color:#999;">完成</font></div>
		  				<#elseif state==6>
		  					<div><font id="orderStateFont${(order.id)!0}" style="color:#999;">取消</font></div>
		  				</#if>
		  		    </#if>
		    	</h2>
		    	<#if (order.orderProductList)?? && (order.orderProductList)?size &gt; 1 >
			    	<!-- <a href="" class="block"> -->
				    	<ul class="img-ul clear">
							<#list (order.orderProductList) as product>
								<a href="${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html?goodId=${(product.productGoodsId)!0}" class="block">
					    			<li><img src="${(domainUrlUtil.imageResources)!}${product.productLeadLittle}"></li>
					    		</a>
				    		</#list>
				    	</ul>
			    	<!-- </a> -->
		    	<#elseif (order.orderProductList)?? && (order.orderProductList)?size == 1 >
		    		<#list (order.orderProductList) as product>
		    		<a href="${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html?goodId=${(product.productGoodsId)!0}" class="block">
				    	<dl class="img-ul flex">
				    	  <dt><img src="${(domainUrlUtil.imageResources)!}${product.productLeadLittle!''}"></dt>
				    	  <dd class="product_name flex-2">${(product.productName)!''}</dd>
				    	</dl>
			    	</a>
			    	</#list>
		    	</#if>
		    	<div class="flex flex-pack-justify order-status">
		    		<div><@cont.codetext value="${(order.orderType)!0}" codeDiv="ORDER_TYPE"/>，
		    			实付款
		    			<#if order.orderType == 6>
		    				<#if (order.orderProductList)??>
								<#list (order.orderProductList) as product>
									<#if product.actIntegralMoney == 0>
		                        		<font class="o-price">${(order.integral)!''}分</font>
		                        	<#else>	
		                           		<font class="o-price">${(order.integral)!''}分+￥${(product.actIntegralMoney)!}</font>
		                           	</#if>
		                        </#list>
							</#if>
		    			<#else>
		    				<font class="o-price">¥${(order.moneyOrder)!''}</font>
		    			</#if>
	    			</div>
		    	</div>
		    	<div class="order-set-btn" id="orderBtnDiv${(order.id)!0}">
		  				<a href="${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=${(order.id)!0}" class="btn">查看</a>
			    		<#if order.orderState??>
			  				<#assign state = order.orderState>
			  				<!-- 订单状态：1、未付款的订单；2、待确认的订单；3、待发货的订单；4、已发货的订单；5、已完成的订单；6、取消的订单 -->
			  				<#if state==1>
			  					<#if order.orderType != 5>
		  						&nbsp;
		  						<a href="javascript:;" class="btn bg_gray_btn" onclick="cancalOrder('${order.id}')">取消订单</a>
		  						</#if>
		  						&nbsp;
		  						<a class="btn bg_red_btn" href="${(domainUrlUtil.urlResources)!}/order/pay.html?orderSn=${(order.orderSn)!''}&rid=${commUtil.randomString(20)}">去付款</a>
			  				<#elseif state==2>
			  					<#if order.orderType != 5>
			  					&nbsp;
		  						<a href="javascript:;" class="btn bg_gray_btn" onclick="cancalOrder('${order.id}')">取消订单</a>
		  						</#if>
			  				<#elseif state==3>
			  					<#if order.orderType != 5>
			  					&nbsp;
		  						<a href="javascript:;" class="btn bg_gray_btn" onclick="cancalOrder('${order.id}')">取消订单</a>
		  						</#if>
			  				<#elseif state==4>
			  					&nbsp;
		  						<a href="javascript:;" class="btn bg_red_btn" onclick="goodsReceipt('${(order.id)!''}')">确认收货</a>
			  				<#elseif state==5>
		  						<#if order.orderType != 4>
		  						<#if order?? && order.evaluateState != 3>
			  					<a href="${(domainUrlUtil.urlResources)!}/member/addcomment.html?id=${(order.id)!0}" class="btn bg_red_btn">评价晒单</a>
			  					</#if>
			  					<#if order?? && order.backOrExchangeNum?? &&order.backOrExchangeNum != 0 && order.isShowBackAndExchange?string('true','false') == 'true'>
		  						&nbsp;
		  							<a href="${(domainUrlUtil.urlResources)!}/member/backapply.html?id=${(order.id)!0}" class="btn">申请退换货</a>
		  						</#if>
		  						</#if>
			  				<#elseif state==6>
			  					
			  				</#if>
			  		    </#if>
		  		    </div>
		    </div>
	    </#list>
	    </#if>
