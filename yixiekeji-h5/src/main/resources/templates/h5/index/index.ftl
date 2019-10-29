<#include "/h5/commons/_head.ftl" />
<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<body class="bgf2">

    <!-- 搜索框 -->
	<div class="fixedtop">
		<div class="search-cover" id="search-cover"></div>
		<div class="search-box flex">
			<div class="s-b-i">
				<a href="${(domainUrlUtil.urlResources)!''}/catelist.html"><i class="fa fa-bars" aria-hidden="true"></i></a>
	        </div>
	   	  	 <div class="flex-2 pos_relative">
	   	  	    <a href="${(domainUrlUtil.urlResources)!''}/search-index.html" class="block">
	   	  	 	<span class="form-control"></span>
	   	  	 	<i class="fa fa-search"></i>
	   	  	 	</a>
	   	  	 </div>
	   	  	 <#if memberSession?? && memberSession.member??>
   	  	     <div class="s-b-t" id="loginTopDiv">
   	  	     	<a href="${(domainUrlUtil.urlResources)!''}/logout.html" class="block">
   	  	     	退出
   	  	     	</a>
   	  	     </div>
   	  	     <#else>
   	  	     <div class="s-b-t" id="loginTopDiv">
   	  	     	<a href="${(domainUrlUtil.urlResources)!''}/login.html" class="block">
   	  	     	登录
   	  	     	</a>
   	  	     </div>
   	  	     </#if>
		</div>
	</div>

	<!-- lunbo -->
	<div>
		<#if banners?? && banners?size &gt; 0 >
		<div class="swiper-container" id="i-swiper-container">
		    <div class="swiper-wrapper">
		    	<#list banners as banner>
		    	<div class="swiper-slide">
		    		<a href="${(banner.linkUrl)!''}" class="block">
		    			<img src="${(domainUrlUtil.imageResources)!''}${(banner.image)!''}">
		    		</a>
		    	</div>
		        </#list>
		    </div>
		    <div class="swiper-pagination"></div>
		</div>
		</#if>
	</div>

	<!-- 导航菜单 -->
	<div class="i-menu-box padt_b10 mar-bt">
		<ul class="flex">
			<li class="flex-1 flex-25p">
				<a href="${(domainUrlUtil.urlResources)!''}/catelist.html" class="block">
					<i class="fa fa-th-large bg1"></i><br>	
					<span>分类查询</span>
				</a>
			</li>
			<li class="flex-1 flex-25p">
				<a href="${(domainUrlUtil.urlResources)!''}/cart/detail.html" class="block">
					<i class="fa fa-shopping-cart bg2"></i><br>
					<span>购物车</span>
				</a>
			</li>
			<li class="flex-1 flex-25p">
				<a href="${(domainUrlUtil.urlResources)!''}/coupon.html" class="block">
					<i class="fa fa-cny bg4"></i><br>	
					<span>优券集市</span>
				</a>
			</li>
			
			<li class="flex-1 flex-25p" >
				<a href="${(domainUrlUtil.urlResources)!''}/jifen.html" class="block">
					<i class="fa fa-hourglass-start bg6"></i><br>
					<span>积分商城</span>
				</a>
			</li>
			
			
		</ul>
	
	</div>
    
    <!-- 掌上秒杀 -->
	<div id="priviewMyQiangou"></div>

	<!--多惠专区-->
	<div class="box-floor">
	    <div class="box-floor-in">
	       
	        <ul class="flex box-line-ul add-box-line-ul" style="display:none">
	           
	            <li class="flex-1 g-i-position">
	                <h4 class="special-bestil-title">
	                    <span class="title">领卷中心</span><br>
	                    <span class="subtitle">先领卷再购物</span>
	                </h4>
	                <#if coupon?? >
		                <a href="${(domainUrlUtil.urlResources)!}/product/${(coupon.productId)!0}.html" class="block">
		                    <img src="${(domainUrlUtil.imageResources)!}/${(coupon.productImage)!''}" alt="">
		                    <p class="img-desc">满${(coupon.minAmount)!'99999999'}减${(coupon.couponValue)!'0'}</p>
		                </a>
            		</#if>
	            </li>
	         
	
	        </ul>
	    </div>
	
	</div>
	
    <!-- 列表 -->
	<div>
		<#if floors?? && floors?size &gt; 0 >
		<#list floors as floor>
			<h2 class="home-title"><span></span><p>${(floor.name)}</p><span></span></h2>
			<#if floor.advImage??>
				<div class="container-col01">
					<a href="${(domainUrlUtil.urlResources)!''}/${(floor.advLinkUrl)!}">
						<img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!''}/${(floor.advImage)!}" alt="">
					</a>
				</div>
			</#if>
		
			<div class="i-list-box mar-bt">
				<ul class="i-list-ul clear">
					<#if floor.datas?? && floor.datas?size &gt; 0 >
					<#list floor.datas as data >
						<#if data_index &lt; 4 >
						<!-- 如果是商品 -->
						<#if data.dataType?? && data.dataType == 1 >
							<li>
								<a href="${(domainUrlUtil.urlResources)!''}/product/${(data.product.id)!0}.html" class="block">
									<div class="i-list-img"><img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!''}${(data.product.masterImg)!''}" width="144" height="144"></div>
									<div class="product_name">${(data.product.name1)!""}</div>
								</a>
								<div class="list-goods-tip">
				                    <#if data.product.sellerId == 1><span class="icon-group-1">自营</span></#if>
				                    <#if data.product.coupon == 1><span class="icon-group-4">优惠券</span></#if>
				                    <#if data.product.special == 1><span class="icon-group-3">特卖</span></#if>
				                </div>
								<div class="i-index-price">
									￥${(data.product.malMobilePrice)?string("0.00")!"0.00"}
									<#if data.product.single == 1><span class="icon-group-2">立减</span></#if>
	                				<#if data.product.full == 1><span class="icon-group-2">满减</span></#if>
								</div>
							</li>
						<#elseif data.dataType?? && data.dataType == 2>
							<li>
								<a href="${(data.linkUrl)!''}" class="block">
									<div class="i-list-img"><img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!''}${(data.image)!''}" width="144" height="144"></div>
									<div class="product_name">${(data.title)!""}</div>
								</a>
							</li>
						</#if>
						</#if>
					</#list>
					</#if>
				</ul>
			</div>
		</#list>
		</#if>
	</div>
	
	<!--猜你喜欢-->
	<div>
	    <h2 class="home-title"><span></span>
	        <p>猜你喜欢</p><span></span>
	    </h2>
	
	    <div class="i-list-box mar-bt">
	        <ul class="i-list-ul clear">
	        <#if products??>
	        	<#list products as product>
	            <li>
	                <a href="${(domainUrlUtil.urlResources)!''}/product/${(product.id)!0}.html" class="block">
	                    <div class="i-list-img">
	                    	<img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!''}${(product.masterImg)!''}" width="144" height="144">
                        </div>
	                    <div class="product_name">${(product.name1)!""}</div>
	                </a>
	                <div class="list-goods-tip">
	                    <#if product.sellerId == 1><span class="icon-group-1">自营</span></#if>
	                    <#if product.coupon == 1><span class="icon-group-4">优惠券</span></#if>
	                    <#if product.special == 1><span class="icon-group-3">特卖</span></#if>
	                </div>
	                <div class="i-index-price">
	                	￥${(product.malMobilePrice)?string("0.00")!"0.00"}
	                	<#if product.single == 1><span class="icon-group-2">立减</span></#if>
	                	<#if product.full == 1><span class="icon-group-2">满减</span></#if>
	                </div>
	            </li>
	            </#list>
            </#if>
	        </ul>
	    </div>
	</div>

<footer class="text-center" style="margin-bottom: 57px;">
	<#if memberSession?? && memberSession.member??>
		<div class="ft_top mar-bt flex" id="loginFootDiv">
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/member/index.html" class="block">${(memberSession.member.name)!''}</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="block">返回首页</a></div>
			<div class="flex-1"><a href="#" class="block">返回顶部</a></div>
		</div>
	<#else>
		<div class="ft_top mar-bt flex" id="loginFootDiv">
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/login.html" class="block">登录</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/register.html" class="block">注册</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="block">返回首页</a></div>
			<div class="flex-1"><a href="#" class="block">返回顶部</a></div>
		</div>
	</#if>
	<div>
	    Copyright©2019 北京齐驱科技有限公司<br />
	    京ICP备19032652号-3
	</div>
</footer>
	
<div class="" style="position: fixed;bottom:0px;left:0px;width:100%;z-index: 1;">
	 <nav class="nav addnav" id="nav">
  	   <div class="flex flex-align-center navbar">
  	   	   <div class="flex-1" ><a href="${(domainUrlUtil.urlResources)!''}/index.html" class="block"><span class="fa fa-home"></span>首页</a></div>
	   	   <div class="flex-1"><a href="${(domainUrlUtil.urlResources)!''}/catelist.html" class="block"><span class="fa fa-glass"></span>分类</a></div>
	   	   <div class="flex-1"><a href="${(domainUrlUtil.urlResources)!''}/cart/detail.html" class="block"><span class="fa fa-cart-plus"></span>购物车</a></div>
	   	   <div class="flex-1"><a href="${(domainUrlUtil.urlResources)!''}/member/index.html" class="block"><span class="fa fa-user"></span>我的</a></div>
  	   </div>
  </nav>
</div>

<!-- footer -->
<#include "/h5/commons/_footer.ftl" />
<#include "/h5/commons/_statistic.ftl" />
<script src="${(domainUrlUtil.staticResources)!}/swiper/swiper-3.2.7.min.js"></script>
<script type="text/javascript">
$(function(){
	var mySwiper = new Swiper ('#i-swiper-container', {
		autoplay: 4000,
		loop: true,
		// 如果需要分页器
		pagination: '.swiper-pagination',
	});
    //多惠专区
  	 new Swiper ('.bestie-new-container', {
        slidesPerView: 2.5,
        paginationClickable:true,
        spaceBetween:0
      });
         //初始化登录状态
	loginInfoInit();
	
})

function refreshMyQiangou(){
 	 $.ajax({
		type:"GET",
		url:domain+"/indexqianggou.html",
		dataType:"html",
		async : true,
		success:function(data){
			//加载数据
			$("#priviewMyQiangou").html(data);
			new Swiper ('.seckill-new-container', {
		       slidesPerView: 4.5,
		       paginationClickable:true,
		       spaceBetween:0
		    });
		}
	});
}
	
//异步加载用户登录信息
function loginInfoInit() {
	$.ajax({
		type:"POST",
		url:domain+"/getloginuser.html",
		success:function(data){
			if(data.success){
				if (data.data != null && data.data.name != null) {
					// 移除未登录时显示的链接
					$("#loginTopDiv").empty();
					// 构造顶部登录信息
					var loginInfoTopHtml = "";
					loginInfoTopHtml += ("<a href='${(domainUrlUtil.urlResources)!''}/logout.html' class='block'>退出</a>");
					$("#loginTopDiv").html(loginInfoTopHtml);
					
					// 移除未登录时显示的链接
					$("#loginFootDiv").empty();
					// 构造底部登录信息
					var loginInfoFootHtml = "";
					loginInfoFootHtml += ("<div class='flex-1'><a href='${(domainUrlUtil.urlResources)!}/member/index.html' class='block'>" + data.data.name + "</a></div>");
					loginInfoFootHtml += ("<div class='flex-1'><a href='${(domainUrlUtil.urlResources)!}/index.html' class='block'>返回首页</a></div>");
					loginInfoFootHtml += ("<div class='flex-1'><a href='#' class='block'>返回顶部</a></div>");
					$("#loginFootDiv").html(loginInfoFootHtml);
				} else {
				}
			}else{
			}
		},
		error:function(){
		}
	});
}
</script>
</body>
</html>