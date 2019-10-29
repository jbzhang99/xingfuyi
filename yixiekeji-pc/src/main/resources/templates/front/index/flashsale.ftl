<#if actFlashSaleStage??>
	<div class="container">
		<div class="index15_time">
			<dl>
				<dt>
					<div class="tit"><i></i>每日秒杀</div>
					<div class="content  clearfix">
						<div class="time clearfix">
							<p class="word01">距离结束</p>
							<p class="word02">
								<span class="word-hour">0</span>
								时
								<span class="word-minute">0</span>
								分
							</p>
						</div>
						<div class="more-a">
							<a target="_blank" href="${(domainUrlUtil.urlResources)!}/qianggou.html">更多<s></s></a>
						</div>
					</div>
				</dt>
				<dd>
					<ul class="index15_time-ul">
					 <#if actFlashSaleStage.productList??>
				        <#list actFlashSaleStage.productList as saleProduct>
				        	<#if saleProduct_index < 5>
					        	<#if saleProduct.product??>
									<li>
										<div class="box">
											<div class="pic">
												<a href="${(domainUrlUtil.urlResources)!}/product/${(saleProduct.product.id)!0}.html?type=1">
												<img src="${(domainUrlUtil.imageResources)!}${(saleProduct.product.masterImg)!}" alt="" /></a>
											</div>
											<div class="name">
													<a href="${(domainUrlUtil.urlResources)!}/product/${(saleProduct.product.id)!0}.html?type=1" title="${(saleProduct.product.name1)!''}">
														${(saleProduct.product.name1)!""}
													</a>
											</div>
											<div class="price">
												￥${(saleProduct.price)?string("0.00")!""}<span>￥${(saleProduct.product.marketPrice)?string("0.00")!""}</span>
											</div>
											<div class="btncar">
												<a href="${(domainUrlUtil.urlResources)!}/product/${(saleProduct.product.id)!0}.html?type=1">立即秒杀</a>
											</div>
										</div>
									</li>
						 		</#if>
				        	</#if>
				        </#list>
			        </#if>
					</ul>
				</dd>
			</dl>
		</div>
	</div>
</#if>
<script type="text/javascript">
$(function(){
	$(".index15_time-ul li").on("mouseover",function(){
		$(this).find(".btncar").css({"bottom":"0px"}).end().find(".name").css({"marginTop":"0px"}).end().find(".price").css({"marginTop":"0px"});
		$(this).find(".box").css({"borderColor":"#f5f5f5"}).end().siblings("li").find(".box").css({"borderColor":"#fff"});
	});
	$(".index15_time-ul li").on("mouseleave",function(){
		$(this).find(".btncar").css({"bottom":"-40px"}).end().find(".name").css({"marginTop":"15px"}).end().find(".price").css({"marginTop":"10px"});
		$(this).find(".box").css({"borderColor":"#fff"});
	});

	// 倒计时
	var intDiff = parseInt(${(countTime)!0});//倒计时总秒数量(毫秒)
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
        if (hour <= 9) hour = '0' + hour;
        if (minute <= 9) minute = '0' + minute;
        $('.word-hour').html(hour);
        $('.word-minute').html(minute);
        intDiff--;
        }, 1000);
    } 
    $(function(){
        timer(intDiff);
    });

    $(".top-banner .a-close").on("click",function(){
    		$(".top-banner").hide();
    });
});
</script>