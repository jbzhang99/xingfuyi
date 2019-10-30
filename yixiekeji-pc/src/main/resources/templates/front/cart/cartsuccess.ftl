<#include "/front/commons/_headbig.ftl" />

<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/add_car.css">

		 <!-- S 主体 -->
	<#if product?? && cart??>
		<div class="bg-main">
			<div class="container">
				<div class="success-box clearfix">
					<div class="fl success-lcol">
						<div class="success-top">
							<b class="succ-icon"></b>
							<h3>商品已成功加入购物车！</h3>
						</div>
						<div class="p-item clearfix">
							<div class="p-img">
								<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html">
									<img src="${(domainUrlUtil.imageResources)!}${(product.masterImg)!}" alt="" width="60" height="60">
								</a>
							</div>
							<div class="p-info">
								<div class="p-name">
									<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" title="${(product.name1)!}">${(product.name1)!}</a>
								</div>
								<div class="p-extra">
									<span>${(cart.specInfo)!}</span>
								</div>
							</div>
						</div>
					</div>
					<div class="fr success-btns clearfix">
						<div class="success-btns-pdt">
							<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" class="btn-tobback">详情</a>
							<a href="${(domainUrlUtil.urlResources)!}/cart/detail.html?id=${(cart.id)!0}" class="btn-addtocart"><b></b>去购物车结算</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</#if>
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
										<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" title="${(product.name1)!}">${(product.name1)!}</a>
									</div>
									<div class="p-price">
										<strong><em>￥</em><i>${(product.mallPcPrice)?string("0.00")!}</i></strong>
									</div>
									<div class="p-btn">
										<a href="javascript:void(0);" onclick="addCart('${(product.id)!}','${(product.sellerId)!}','0')" class="btn-append"><b></b>加入购物车</a>
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
	function addCart(productId,sellerId,type){
		//未登录不能添加购物车
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type : "POST",
			url :  domain+"/cart/addtocart.html?type="+type,
			data : {productId:productId,sellerId:sellerId},
			dataType : "json",
			success : function(data) {
				if(data.success){
					window.location.href=domain+"/cart/add.html?id=" + data.data.id;
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