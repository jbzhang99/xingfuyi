<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>幸福易商城</title>
		<meta name="Keywords" content="幸福易商城">
		<meta name="Description" content="幸福易商城">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"></link>
		<link  rel="stylesheet" type="text/css" href='${(domainUrlUtil.staticResources)!}/css/bootstrap.min.css'></link>
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/base.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/index.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/list.css">
		<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/jquery.alerts.css"/>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/jquery-1.9.1.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/bootstrap.min.js'></script>
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/jquery.validate.min.js'></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/func.js"></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/checkvalue.js"></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.alerts.js"></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/slider.js"></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.SuperSlide.2.1.1.js"></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.lazyload.js"></script>
		<style type='text/css' rel="stylesheet">
		</style>
		<script type="text/javascript">
			var domain = '${(domainUrlUtil.urlResources)!}';
			var resource_path_ = '${(domainUrlUtil.staticResources)!}';
		</script>
	</head>
	<body class='wp1200' style="background-color: #f2f2f2;">
			<div class='wrapper'>
			<div class='container'>
				<ul class='collect lh'>
					<li class='fore1'>
						<a href='javascript:void(0)' onclick="AddFavorite(window.location,document.title);return false;">收藏</a>
					</li>
				</ul>
				<ul class='shortcut-right lh'>
					<#if Session.memberSession??>
				   		<#assign user = Session.memberSession.member>
				   </#if>
				   <#if user??>
				   		<li class='fore1' id='loginbar'>
							<a href='${(domainUrlUtil.urlResources)!}/member/order.html' target="_blank" class='login'>${(user.name)!''}</a>&nbsp;&nbsp;
							<a href='${(domainUrlUtil.urlResources)!}/logout.html'  onclick="logout()" class='regist'>退出</a>
						</li>
						<li class='fore2 ld'>
							<span></span>
							<a href="${(domainUrlUtil.urlResources)!}/member/order.html" target="_blank">我的订单</a>
						</li>
				   	 
				   	<#else>
						<li class='fore1' id='loginbar'>
							<a href='${(domainUrlUtil.urlResources)!}/login.html' class='login'>你好，请登录</a>&nbsp;&nbsp;
							<a href='${(domainUrlUtil.urlResources)!}/register.html' class='regist'>免费注册</a>
						</li>
				   </#if>
					
					<li class='fore2-1 ld ff-vip'stle='padding-left:12px;'>
						<span></span>
						<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>会员中心</a>
					</li>
					
					<li class='fore3 ld app-ff menu' style="text-align:center;width: 126px;padding: 0 5px;">
						<div class="menubox">
							<div class="mu-line">
								<span></span>
								关注幸福易<i class="ci-t"></i>
							</div>
							<div class="imgwx-ej" style="display: none;border:1px solid #e0e0e0;border-top:0;">
								<img src="${(domainUrlUtil.staticResources)!}/img/yxkjwx.jpg" width="80" height="80">
								<div style="padding: 0 6px; line-height: 20px;">关注幸福易微信公众号</div>
							</div>
						</div>
					</li>
					
					<li class='fore3 ld app-ff menu' style="text-align:center;width: 78px;padding: 0 5px;">
						<div class="menubox">
							<div class="mu-line">
								<span></span>
								客户服务<i class="ci-t"></i>
							</div>
							<div class="imgwx-ej khfw clearfix " style="display: none">
								<div class="" style="text-align: left;">客户</div>
								<div class="item">
									<a target="_blank" href="${(domainUrlUtil.urlResources)!}/news/type_1.html">帮助中心</a>
								</div>
								<div class="item">
									<a target="_blank" href="${(domainUrlUtil.urlResources)!}/news/type_2.html">店主之家</a>
								</div>
								<div class="item">
									<a target="_blank" href="${(domainUrlUtil.urlResources)!}/news/type_3.html">支付方式</a>
								</div>
								<div class="item">
									<a target="_blank" href="${(domainUrlUtil.urlResources)!}/news/type_4.html">售后服务</a>
								</div>
								<div class="item">
									<a target="_blank" href="${(domainUrlUtil.urlResources)!}/news/type_5.html">客服中心</a>
								</div>
								<div class="item">
									<a target="_blank" href="${(domainUrlUtil.urlResources)!}/news/type_6.html">关于我们</a>
								</div>
							</div>
						</div>
					</li>
					<li class='fore5 ld menu' id='site-nav'>
						<span></span>
						<a target="_blank" href='${(domainUrlUtil.urlResources)!}/store/step1.html'>商家入驻</a>
					</li>
					<li class='fore5 ld menu' id='site-nav'>
						<span></span>
						<a target="_blank" href='#'>联系我们</a>
					</li>
				</ul>
			</div>
		</div>
		
	<#if pcIndexImageTop??>
		<div class="top-banner" style="background: #F65382">
			<div class="container" style="position: relative;">
				<a href="javascript:;" class="a-close"></a>
				<a href="${(domainUrlUtil.urlResources)!}/${(pcIndexImageTop.linkUrl)!}">
					<img width="100%" src="${domainUrlUtil.imageResources!}/${(pcIndexImageTop.image)!}" alt="" />
				</a>
			</div>
		</div>
	</#if>
		
		<div class="head-part">
			<div class='container' id='HeardTop'>
				<div class='ld' id='logo-img'>
					<a href="${(domainUrlUtil.urlResources)!}/index.html">
						<img alt="" src="${domainUrlUtil.staticResources!}/img/yxkjlogo.png" >
					</a>
				</div>
				<div class='seach-box index-saeach-box'>
					<div class='i-search ld'>
						<ul class="hide-box" style="display: none">
						</ul>
						<div class='form'>
							<form action="${(domainUrlUtil.urlResources)!}/search.html" method="get">
								<input type='text' id='keyword' name="keyword" value="${(keyword)!''}" class='text' autocomplete="off" style='color:rgb(153,153,153);'>
								<input type='submit' value='搜索' class='button'>
							</form>
						</div>
					</div>
					<div id='Hotwords'>
						<strong>热门搜索：</strong>
						<div id="keywordIDs"></div>
					</div>
				</div>
				
				
				<div class='settleup'>
					<dl class>
						<!-- 如果没有商品这里显示0 -->
						<div class='addcart-goods-num'>0</div>
						<!--  -->
						<dt class='ld first-dt'>
							<s></s> <a href='${(domainUrlUtil.urlResources)!}/cart/detail.html'
								target="_blank">我的购物车</a> <b></b>
						</dt>
						<dd  id="priviewMycart">
						</dd>
					</dl>
				</div>
			</div>
		</div>
		<!--商品分类 -->
		<div id='NavSort'>
			<div class='container'>
				<div class='all-category'>
					<div class='dts'>
						<a href='' target='_blank'>全部商品分类</a>
					</div>
					<div class='sec_attr' style="overflow: hidden;">
						<ul class='dd-inner '>
						<#if cateList??> 
							<#list cateList as cate1>
								<li class='odd' data-index='${cate1_index+1}'>
									<h3>
										<a href='' target='_blank'>${cate1.name!''}</a>
									</h3>
									<i>&gt;</i>
								</li>
							</#list> 
						</#if>
						</ul>
						<div class='dorpdown-layer'>
							<!-- 一级分类 -->
							<#list cateList as cate1>
							<div class='item-sub' id='index${cate1_index+1}'>
								<div class='subitems'>
									<!-- 二级分类  -->
									<#list cate1.childs as cate2>
									<dl class='fore-dl'>
										<dt>
											<a href='${(domainUrlUtil.urlResources)!}/list/${cate2.id!0}.html' 
												target='_blank'>${cate2.name!'' }</a>
											<i>&gt;</i>
										</dt>
										<dd>
											<!-- 三级分类 -->
											<#list cate2.childs as cate3> 
											<a href='${(domainUrlUtil.urlResources)!}/cate/${cate3.id!0}.html' 
												target='_blank'>${cate3.name!'' }</a>
											</#list>
											<!-- 三级分类 end -->
										</dd>
									</dl>
									</#list>
									<!-- 二级分类  end -->
								</div>
							</div>
							</#list> 
							<!-- 一级分类 end -->
							
						</div>
					</div>
				</div>
				<ul class='site-menu'>
					<li class='fore1'>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/recommend.html#item'>多惠部落</a>
						</li>
						
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/brand.html'>品牌馆</a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/coupon.html'>优券集市</a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/jifen.html'>积分商城</a>
						</li>
				</ul>
				<!--轮播图上面的右侧小图 -->
				<#if pcIndexImageFloat??>
					<div class="banner-smimg">
						<a href="${(domainUrlUtil.urlResources)!}/${(pcIndexImageFloat.linkUrl)!}">
						<img src="${(domainUrlUtil.imageResources)!}/${(pcIndexImageFloat.image)!}" alt="" /></a>
						
						<#if pcIndexImageFloat2??>
							<a href="${(domainUrlUtil.urlResources)!}/${(pcIndexImageFloat2.linkUrl)!}">
							<img src="${(domainUrlUtil.imageResources)!}/${(pcIndexImageFloat2.image)!}" alt="" /></a>
						</#if>
					</div>
				</#if>
			</div>
		</div>
 
	<!-- end -->
    <#if bannerList?? && bannerList?size &gt; 0 >
    <div class="main-lbbox">
		<div class="index-lunbo-container clearfix">
	    <div class="hd">
	        <ul class="">
	        	<#list bannerList as banner>
	            	<li class="bullet">${banner_index+1}</li>
	            </#list>
	        </ul>
	        <div class="bullet-bg"></div>
	    </div>
	    <ul class="bd">
	    	<#list bannerList as banner>
		        <li>
		        	<a target="_blank" href="${(banner.linkUrl)!''}">
		        		<img src="${(domainUrlUtil.imageResources)!}${(banner.image)!''}" alt=""/>
		        	</a>
		        </li>
	        </#list>
	    </ul>
		</div>
	</div>
	</#if>
	<!-- end -->
	
	<!-- S add -->
	<#if pcIndexImageDowns ??>
		<div class="container">
			<div class="index15_ad">
				<ul>
				<#list pcIndexImageDowns as pcIndexImageDown>
					<#if pcIndexImageDown_index < 4>
						<li class="mg5">
							<a href="${(domainUrlUtil.urlResources)!}/${(pcIndexImageDown.linkUrl)!}">
								<img src="${(domainUrlUtil.imageResources)!}/${(pcIndexImageDown.image)!}" alt="" width="100%" height="100%" />
							</a>
						</li>
					</#if>
				</#list>
				</ul>
			</div>
		</div>
	</#if>
	<!-- E add-->
	
	<!-- S 限时抢购 -->
	<div id="priviewMyQiangou"></div>
	<!-- E 限时抢购 -->
	
	<!-- 多惠部落 -->
	<div class='container'>
		 <div class="gusess-like">
	        <div id="guessyou" class="clearfix">
	
	            <!--修改-->
	            <!--领券中心-->
	            <div class="gusess-like-r g-add-block">
	                <h2 class="title">
	                    <span class="border"></span>
	                    <span class="text">领券中心</span>
	                    <a href="${(domainUrlUtil.urlResources)!}/coupon.html" class="more">更多</a>
	                </h2>
	                <ul class="coupon-content">
	                	<#if couponList?? && couponList?size &gt; 0 >
            				<#list couponList as coupon >
			                    <li class="clearfix">
			                        <div class="fl slide-area">
			                            <img src="${(domainUrlUtil.imageResources)!}/${(coupon.productImage)!''}"
			                                 alt="">
			                            <div class="c-goods-info">
			                                <p>
			                                    <span class="price"><i>¥</i>${(coupon.couponValue)!'0'}</span> <span class="rule">满${(coupon.minAmount)!'99999999'}元可用</span>
			                                </p>
			                                <p>限购[${(coupon.sellerName)!''}]商品</p>
			                            </div>
			                        </div>
			                        <a  href="${(domainUrlUtil.urlResources)!}/product/${(coupon.productId)!0}.html" class="get-coupon">
			                            <b class="semi-circle"></b>
			                            <span class="">立即领取</span>
			                        </a>
			                        <div class="coupon-icon-used"></div>
			                    </li>
	                   		</#list>
            			</#if>
	                </ul>
	            </div>
	            <!--领券中心 end-->
	            
	            <!--多惠部落-->
	            <#if hotList?? && hotList?size &gt; 0 >
	            <div class="gusess-like-l g-add-block">
	                <h2 class="title">
	                    <span class="border"></span>
	                    <span class="text">必抢好货</span>
	                    <a href="${(domainUrlUtil.urlResources)!}/recommend.html" class="more">更多</a>
	                </h2>
	                <div class="clearfix g-block-part">
	                    <ul class="clearfix gusess-like-list">
	                    	<#list hotList as recommend>
	                        <li>
	                            <div class="p-img">
	                                <a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html" target="_blank">
	                                    <img class="lazy" data-original='${(domainUrlUtil.imageResources)!}${(recommend.product.masterImg)!""}'>
	                                </a>
	                            </div>
	                            <div class="p-info">
	                                <div class="p-name">
	                                    <a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html" target="_blank">${(recommend.product.name1)!""}'>${(recommend.product.name1)!""}</a>
	                                </div>
	                                <div class="p-price">
	                                    <i>¥</i>${(recommend.product.mallPcPrice)!"0.00"}
	                                    <del>¥${(recommend.product.marketPrice)!"0.00"}</del>
	                                </div>
	                            </div>
	                        </li>
	                        </#list>
	                    </ul>
	                </div>
	                <div class="vertical-line"></div>
	            </div>
				</#if>
				
	            <!--拼多多-->
	            
	
	            <!--拼多多 end-->
	        </div>
	    </div>
	</div>
	

	<!-- 楼层 -->
	<!-- 广告 -->
	<#if floorList?? && floorList?size &gt; 0>
	<#list floorList as floor>
		<#if floor?? && floor.advImage?? && floor.advImage != "">
			<div class='container'>
				<div class='floor-banner'>
				  <a href='${(floor.advLinkUrl)!"#"}' target='_blank'>
						<img src='${(domainUrlUtil.imageResources)!}${(floor.advImage)!""}' width='1210' height='100'/>
				  </a>
				</div>
			</div>
			<br />
		</#if>
		<div class='container'>
			<div class='lazy-fn'>
				<div class='floor-box guess-box-berd'>
					<div class='lazy-clothes'>
						<h2 class="f-part-title  <#if floor_index==1>pink</#if><#if floor_index==2>yellow</#if>">
							<div class="fl f-title-lab">
		                        <span class="title  <#if floor_index==1>pink</#if><#if floor_index==2>yellow</#if>">${floor.name!""}</span>
		                        <span class="link">
		                           <#if floor.patchList?? && floor.patchList?size &gt; 0>
										<#list floor.patchList as patch>
											<#if patch_index lt 3>
											<a href='${(patch.linkUrl)!""}' target="_blank">
												${(patch.title)!""}
											</a>
											</#if>
										</#list>
									</#if>
		                       </span>
		                    </div>
		                    <a href="${(floor.advLinkUrl)!"#"}" class="more">更多</a>
						</h2>
					</div>
					<div class='lazy-mc clearfix'>
						<div class="lazy-left">
	                        <img src="${(domainUrlUtil.imageResources)!}${(floor.masterImage)!""}" class="img-position">
	                        <p class="slide-items">
	                            <#if floor.patchList?? && floor.patchList?size &gt; 0>
									<#list floor.patchList as patch>
										<#if patch_index gt 5>
											<a href='${(patch.linkUrl)!""}' target="_blank">
												${(patch.title)!""}
											</a>
										</#if>
									</#list>
								</#if>
	                        </p>
	                    </div>
						
						<#if floor.classList?? && floor.classList?size &gt; 0>
						<#list floor.classList as fc>
							<div class='lazy-main <#if fc_index &gt; 0>hide</#if>'>
								<ul class='p-list'>
									<#if fc.dataList?? && fc.dataList?size &gt; 0>
									<#list fc.dataList as data >
										<#if data.dataType == 1 >
											<li>
												<div class='p-img'>
													<a href='${(domainUrlUtil.urlResources)!}/product/${(data.product.id)!0}.html' 
														target='_blank' title='${(data.product.name1)!""}'>
														<img class="lazy" data-original='${(domainUrlUtil.imageResources)!}${(data.product.masterImg)!""}' width="185" height="185">
													</a>
												</div>
												<div class='p-name'>
													<a href='${(domainUrlUtil.urlResources)!}/product/${(data.product.id)!0}.html' 
														target='_blank'  title='${(data.product.name1)!""}'>
														${(data.product.name1)!""}
													</a>
												</div>
												<div class='p-price'>
													<span>￥</span><span>${(data.product.mallPcPrice)!''}</span>
												</div>
											</li>
										<#elseif data.dataType == 2 >
											<li>
												<div class='add-p-img'>
													<a href='${(data.linkUrl)!""}' target='_blank' title='${(data.title)!""}'>
														<img class="lazy" data-original='${(domainUrlUtil.imageResources)!}${(data.image)!""}' >
													</a>
												</div>
											</li>
										</#if>
									</#list>
									</#if>
								</ul>
							</div>
						</#list>
						</#if>
					</div>
				</div>
				<!--  end-->
			</div>
		</div>
	</#list>
	</#if>
	<!-- end -->
	
	<!-- 为你推荐-->
	<div class="container">
        <div class="recommend-content">
            <h3>
                <img src="${(domainUrlUtil.staticResources)!}/img/sprite.png" width="20">
                为你推荐
                <img src="${(domainUrlUtil.staticResources)!}/img/sprite.png" width="20">
            </h3>
            <ul class="p-list clearfix">
            
    	<#if products??>
			<#list products as product>
                <li>
                    <div class="p-img">
                        <a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" target="_blank">
                            <img class="lazy"
                                 data-original="${(domainUrlUtil.imageResources)!}${(product.masterImg)!""}">
                        </a>
                    </div>
                    <div class="p-name">
                        <a href="${(domainUrlUtil.urlResources)!}/product/${(produc.id)!0}.html" target="_blank" title="${(product.name1)!}">
                            ${(product.name1)!}
                        </a>
                    </div>
                    <div class="p-price">
                        <span>￥</span><span>${(product.mallPcPrice)?string("0.00")!""}</span>
                    </div>
                    <div class="list-goods-tip">
                        <#if product.sellerId == 1><span class="icon-group-1">自营</span></#if>
						<#if product.special == 1><span class="icon-group-3">特卖</span></#if>
						<#if product.full == 1><span class="icon-group-2">满减</span></#if>
						<#if product.coupon == 1><span class="icon-group-4">优惠券</span></#if>
						<#if product.single == 1><span class="icon-group-2">立减</span></#if>
                    </div>
                </li>
               </#list>
			</#if>  
            </ul>
        </div>
    </div>
    <!-- end -->

	<!-- footer -->
		<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/index.js'></script>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/common.js"></script>
		
		<script>
			$(function(){
				// 初始化登录状态
				loginInfoInit();
				// 刷新购物车
				refreshMycart();
				
			});
			
			// 异步加载用户登录信息
			function loginInfoInit() {
				$.ajax({
					type:"POST",
					url:domain+"/getloginuser.html",
					success:function(data){
						if(data.success){
							if (data.data != null && data.data.name != null) {
								// 移除未登录时显示的链接
								$("#loginbar").remove();
								// 构造登录信息
								var loginInfoHtml = "";
								loginInfoHtml += ("<li class='fore1' id='loginbar'>");
								loginInfoHtml += ("	<a href='${(domainUrlUtil.urlResources)!}/member/order.html' target='_blank' class='login'>" + data.data.name + "</a>&nbsp;&nbsp;");
								loginInfoHtml += ("	<a href='${(domainUrlUtil.urlResources)!}/logout.html'  onclick='logout()' class='regist'>退出</a>");
								loginInfoHtml += ("</li>");
								loginInfoHtml += ("<li class='fore2 ld'>");
								loginInfoHtml += ("	<span></span>");
								loginInfoHtml += ("	<a href='${(domainUrlUtil.urlResources)!}/member/order.html' target='_blank'>我的订单</a>");
								loginInfoHtml += ("</li>");
								// 显示登录信息
								$(".shortcut-right").prepend(loginInfoHtml);
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
		<#include "/front/commons/_endbig.ftl" />
