<#include "/front/commons/_top.ftl" />
<link  rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/order.css'>
		
		<!--  成功加入购物车 -->
		<div class='container'>
			<div class='cart-sucess'>
				<div id='CartSucess'>
					<div class='success-b'>
						<h3>支付成功  </h3>
						
						<#if orderSn??>
						订单号为 : ${orderSn}
						</#if>
					</div>
				</div>
			</div>
		</div>
		<!-- end -->
 <#include "/front/commons/_endbig.ftl" />