<#include "/front/commons/_headbig.ftl" />

<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/list.css">
		<!-- 主体 -->
		<div>
			<div class="banner"></div>
			<div class="list-shop-box">
				<div class="items-goods clearfix" id="item">
					<ul>
						<#if recommendList?? && recommendList?size &gt; 0 >
						<#list recommendList as recommend >
							<li>
								<a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html" target="_blank">
									<div class="item-gdbox">
										<div class="item-gdimg">
											<img class="lazy" data-original="${(domainUrlUtil.imageResources)!}${(recommend.product.masterImg)!''}" alt="">
											<#if (recommend.product)?? && recommend.product.productStock &lt;= 0 >
											<em class="item-empty"></em>
											</#if>
											<div class="item-price-spread">
												<img src="${(domainUrlUtil.staticResources)!}/img/pricespread.png" alt="">
												<span class="item-spread">省<span class="item-spread-money"><i>${(recommend.discount)!""}</i></span>
												</span>
											</div>
										</div>
										<div class="item-gdes" title="${(recommend.product.name1)!''}">
											${(recommend.product.name1)!""}
										</div>
										<div class="item-gdprice">
											<span class="old-price">市场价￥${(recommend.product.marketPrice)!"0.00"}</span><br>
											<span class="new-price">特惠价：￥<i>${(recommend.product.mallPcPrice)!"0.00"}</i></span>
										</div>
									</div>
								</a>
							</li>
						</#list>
						</#if>
					</ul>
				</div>
				<!-- 分页 -->
				<#if page?? && page.pageCount gt 1>
					<div class="pagin-box">
						<#include "/front/commons/_pagination.ftl" />
					</div>
				</#if>
			</div>
		</div>

<script>
$(function(){

	// 鼠标移入标题
	$(".items-goods li").on("mouseenter",function(){
		$(this).find(".item-gdes").animate({marginTop:"0px"});
	});
	$(".items-goods li").on("mouseleave",function(){
		$(this).find(".item-gdes").animate({marginTop:"20px"});
	});

	window.location.hash = "#item";

});

</script>
<#include "/front/commons/_endbig.ftl" />
 