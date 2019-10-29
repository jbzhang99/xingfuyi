<#include "/front/commons/_headbig.ftl" />

<link  rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/order.css'>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/add_car.css">

<div class='success-oder-box'>
	<div class="container clearfix"style="padding-bottom: 10px;padding-left: 50px;">
		<div class="fl sd-fl">
				<strong>感谢您，订单提交成功！请到<a href='${(domainUrlUtil.urlResources)!}/member/order.html'>订单中心</a>查看详情。</strong>
				<strong>
					<#if orderList??>
						<#if (orderList?size>1)>
						<h2>亲爱的用户，由于您购买的商品分属不同的商家，此订单被拆分为${(orderList?size) }个订单进行结算及配送。</h2>
						</#if>
				    </#if>
				</strong>
				
				<#if orderList??>
					<#list orderList as order>
						<div style="margin-top: 10px;">
							<span class="or_allpay"><@cont.codetext value="${(order.orderType)!0}" codeDiv="ORDER_TYPE"/>，
							订单号：<a href='${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=${(order.id)!''}'>${(order.orderSn)!'' }</a>,
							<#if order.orderType == 6>
								消耗积分<i>${(order.integral)!'0' }分</i>
							<#else>
								应付金额<i>￥${(order.moneyOrder)?string('0.00')!'' }</i>
							</#if>
							</span>
						</div>
					</#list>
				</#if>	
		</div>
		<div class="fr sd-fr">
			<div class='stepflex-box fr'>
				<ul>
					<li class="">
						<span class="fl">1.我的购物车</span><i class="fl"></i>
					</li>
					<li class="prev">
						<span class="fl">2.确认订单信息</span><i class="fl"></i>
					</li>
					<li class="current">
						<span class="fl">3.成功提交订单</span><i class="fl lasti"></i>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
		
		
<div class="container">
	<div class="adc-m">
		<div class="adc-mt">
			<h3>购买了该商品的用户还购买了</h3>
		</div>
		<div class="adc-mc s-panel-main">
			<div class="adc-goods-list s-panel">
				<ul>
				<#if products??>
					<#list products as product>
						<li>
							<div class="adc-item">
								<div class="p-img">
									<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html">
									<img src="${(domainUrlUtil.imageResources)!}${(product.masterImg)!}" width="160" height="160">
								</a>
								</div>
								<div class="p-name">
									<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html">${(product.name1)!}</a>
								</div>
								<div class="p-price">
									<strong><em>￥</em><i>${(product.mallPcPrice)?string("0.00")!}</i></strong>
								</div>
								<div class="p-btn">
									<a href="javascript:void(0);" onclick="addCart('${(product.id)!}','${(product.sellerId)!}')" class="btn-append"><b></b>加入购物车</a>
								</div>
							</div>
						</li>
					  </#list>	
				  </#if>
				</ul>
			</div>
		</div>
	</div>
</div>		
<script type="text/javascript">
	function addCart(productId,sellerId){
		//未登录不能添加购物车
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type : "POST",
			url :  domain+"/cart/addtocart.html",
			data : {productId:productId,sellerId:sellerId},
			dataType : "json",
			success : function(data) {
				if(data.success){
					//跳转到添加购物车成功页面
					window.location.href=domain+"/cart/add.html?id=" + data.data;  
				}else{
					jAlert(data.message);
				}
			},
			error : function() {
				jAlert("数据加载失败！");
			}
		});
	}
</script>	
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />