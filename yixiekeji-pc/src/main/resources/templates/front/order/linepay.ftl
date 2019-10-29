<#include "/front/commons/_headbig.ftl" />

<link  rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/order.css'>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/add_car.css">

<div class='success-oder-box'>
	<div class="container clearfix"style="padding-bottom: 10px;padding-left: 50px;">
		<div class="fl sd-fl">
			<strong>感谢您，订单支付成功！请到<a href='${(domainUrlUtil.urlResources)!}/member/order.html'>订单中心</a>查看详情。</strong>
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