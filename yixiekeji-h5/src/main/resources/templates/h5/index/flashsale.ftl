<#if actFlashSaleStage??>	
	<div class="box-floor">
		<div class="floor-container seckill-floor bdr-bottom">
			<div class="title-wrap clear">
				<span class="seckill-icon"></span>
				<h2 class="seckill-title">掌上秒杀</h2>
				<div class="seckill-timer">
					<span class="sm-txt hour_sw">00</span>
					<span>:</span>
					<span class="sm-txt minute_sw">00</span>
					<span>:</span>
					<span class="sm-txt second_sw">00</span>
				</div>
				<a href="${(domainUrlUtil.urlResources)!}/act-flash-sale.html" class="seckill-more-link">更多秒杀 ></a>
			</div>
			<div class="seckill-new-container swiper-container wiper-container-horizontal">
				<ul class="seckill-new-list clear swiper-wrapper">
					<#if actFlashSaleStage.productList??>
				        <#list actFlashSaleStage.productList as saleProduct>
				        	<#if saleProduct_index < 6>
					        	<#if saleProduct.product??>
									<li class="seckill-new-item swiper-slide swiper-slide-next">
										<div class="seckill-item-img bdr-r">
											<a href="${(domainUrlUtil.urlResources)!}/product/${(saleProduct.product.id)!0}.html?type=1" class="seckill-new-link">
												<img src="${(domainUrlUtil.imageResources)!}${(saleProduct.product.masterImg)!}" alt="" border="0" >
											</a>
										</div>
										<!-- 
										<div class="seckill-item-titl">
											<a href="${(domainUrlUtil.urlResources)!}/product/${(saleProduct.product.id)!0}.html?type=1">${(saleProduct.product.name1)!""}</a>
										</div>
										<div class="seckill-item-price">
											<span class="seckill-new-price">￥${(saleProduct.price)?string("0.00")!""}</span><del>￥${(saleProduct.product.marketPrice)?string("0.00")!""}</del>
										</div>-->
										<div class="seckill-item-price">
				                            <span class="seckill-new-price">￥${(saleProduct.price)?string("0.00")!""}</span><br>
				                            <del>￥${(saleProduct.product.marketPrice)?string("0.00")!""}</del>
				                        </div>
									</li>
							</#if>
			        	</#if>
			        </#list>
		        </#if>
				</ul>
			</div>
		</div>
	</div>
</#if>

<script type="text/javascript">
    function timer(intDiff){
        window.setInterval(function(){
        var day=0,
            hour=0,
            minute=0,
            second=0;//时间默认值       
        if(intDiff > 0){
            day = Math.floor(intDiff / (60 * 60 * 24));
            hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
            minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
            second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        }
        if (hour <= 9 ) hour = '0' + hour;
        if (minute <= 9) minute = '0' + minute;
        if (second <= 9) second = '0' + second;
        // $('.day_show').html(day+"天");
        $('.seckill-timer .hour_sw').html('<s id="h"></s>'+hour);
        $('.seckill-timer .minute_sw').html('<s></s>'+minute);
        $('.seckill-timer .second_sw').html('<s></s>'+second);
        intDiff--;
        }, 1000);
    } 
    var intDiff = parseInt(${(countTime)!0});//倒计时总毫秒数
    timer(intDiff);
    
    // 页面开始加载调用
		// 掌上秒杀
</script>