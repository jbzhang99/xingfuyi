<#include "/h5/commons/_head.ftl" />
<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<body class="bgf2">

    <!-- 搜索框 -->
	<div class="fixedtop">
		<div class="search-cover" id="search-cover"></div>
		<div class="search-box flex">
			<div class="s-b-i">
				<img src="${(domainUrlUtil.staticResources)!''}/img/logo.png" width="50">
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
				<a href="${(domainUrlUtil.urlResources)!''}/member/index.html" class="block">
					<i class="fa fa-user bg3"></i><br>
					<span>会员中心</span>
				</a>
			</li>
			<li class="flex-1 flex-25p">
				<a href="${(domainUrlUtil.urlResources)!''}/temai.html" class="block">
					<i class="fa fa-file-picture-o bg1"></i><br>
					<span>特卖场</span>
				</a>
			</li>
		</ul>
		<ul class="flex">
			<li class="flex-1 flex-25p">
				<a href="${(domainUrlUtil.urlResources)!''}/coupon.html" class="block">
					<i class="fa fa-cny bg4"></i><br>	
					<span>优券集市</span>
				</a>
			</li>
			<li class="flex-1 flex-25p">
				<a href="${(domainUrlUtil.urlResources)!''}/bidding-sale.html" class="block">
					<i class="fa fa-sort-amount-desc bg5"></i><br>
					<span>拼多多</span>
				</a>
			</li>
			<li class="flex-1 flex-25p" >
				<a href="${(domainUrlUtil.urlResources)!''}/jifen.html" class="block">
					<i class="fa fa-hourglass-start bg6"></i><br>
					<span>积分商城</span>
				</a>
			</li>
			<li class="flex-1 flex-25p" >
				<a href="${(domainUrlUtil.urlResources)!''}/tuan.html" class="block">
					<i class="fa fa-shopping-bag bg7"></i><br>
					<span>团购</span>
				</a>
			</li>
		</ul>
	</div>
    
    
    <!-- 掌上秒杀 -->
	<div id="priviewMyQiangou"></div>

	<!-- 多惠专区 -->
	<#if hotList?? && hotList?size &gt; 0 >
	<div>
		<div class="floor-container bestie-floor bdr-bottom">
			<div class="title-wrap clear">
				<h2 class="seckill-title bestie-tit"><i class="fa fa-gift"></i>多惠部落</h2>
				<a href="${(domainUrlUtil.urlResources)!}/recommend.html" class="seckill-more-link">更多优惠 ></a>
			</div>
			<div class="bestie-new-container swiper-container">
				<ul class="bestie-new-list clear swiper-wrapper">
					<#list hotList as recommend>
					<li class="bestie-new-item swiper-slide">
						<div class="bestie-item-img bdr-r">
							<a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html" class="bestie-new-link">
								<img src="${(domainUrlUtil.imageResources)!}${(recommend.product.masterImg)!''}" alt="" border="0" >
								<div class="bestie-tip">
									<img src="${(domainUrlUtil.staticResources)!''}/img/pricespread.png" alt="">
									<span class="item-spread">省<span class="item-spread-money">${(recommend.discount)!""}</span></span>
								</div>
							</a>
						</div>
						<div class="bestie-item-titl">
							<a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html">${(recommend.product.name1)!""}</a>
						</div>
						<div class="bestie-item-price">
							<span class="bestie-new-price">¥${(recommend.product.malMobilePrice)?string('0.00')}</span>
						</div>
					</li>
					</#list>
				</ul>
			</div>
		</div>
	</div>
    </#if>
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
								<div class="i-index-price">￥${(data.product.malMobilePrice)?string("0.00")!"0.00"}</div>
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
		<!-- <div class="text-center font14">点击继续加载 <i class="fa fa-angle-double-down"></i></div> -->
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
	refreshMyQiangou();
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
		       slidesPerView: 2.5,
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