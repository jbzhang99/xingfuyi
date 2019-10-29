<#include "/h5/commons/_head.ftl" />
<body class="bgff">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">下单成功</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="errorbox">
	     <div class="arrow-fl"></div>
         <h3 class='ftc-02 text-center'><i class="fa fa-smile-o"></i>&nbsp;感谢您，订单提交成功！</h3>
         <div class="padt_b10 borbt text-center">请到&nbsp;<a href='${(domainUrlUtil.urlResources)!}/member/order.html' class="btn s-btn">订单中心</a>&nbsp;查看详情。</div>
			<#if orderList??>
				<#if (orderList?size>1)>
				<h2 class="mar_top">亲爱的用户，由于您购买的商品分属不同的商家，此订单被拆分为${(orderList?size) }个订单进行结算及配送。</h2>
				</#if>
		   </#if>
		<#if orderList?? && orderList?size &gt; 0 >
		<#list orderList as order>
			<div class='mcs mar_top' id='msop_detail'>
				<ul class='list-order'>
					<li class='li-st'>
						<div class='li-for1'>
							订单号：
							<a href='${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=${(order.id)!''}'>${(order.orderSn)!'' }</a>
						</div>
						<div class='li-for2'>
							${(order.paymentName)!'' }：
							<!-- 如果是积分换购订单 -->
							<#if order.orderType?? && order.orderType == 6 >
								<strong class='ftc-01'>${(order.integral)?string('0')!'' }分</strong>
								<#if (order.moneyOrder - order.moneyIntegral) &gt; 0 >
								<strong class='ftc-01'>${(order.moneyOrder - order.moneyIntegral)?string('0.00')!'' }元</strong>
								</#if>
							<#else>
							<strong class='ftc-01'>${(order.moneyOrder - order.moneyIntegral)?string('0.00')!'' }元</strong>
							</#if>
						</div>
					</li>
				</ul>
			</div>
		  </#list>
		 </#if>	
    </div>
	<!-- 主体结束 -->

	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
 <script type="text/javascript">
$(function(){
	
});

</script>

</body>
</html>