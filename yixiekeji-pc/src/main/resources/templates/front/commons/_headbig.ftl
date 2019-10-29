<#import "/front/commons/_macro_controller.ftl" as cont/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<title>幸福易商城</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<link  rel="stylesheet" type="text/css" href='${(domainUrlUtil.staticResources)!}/css/bootstrap.min.css'/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/details.css"/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/user.css"/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/groupSingPage.css">
	<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/jquery.alerts.css"/>
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/jquery-1.9.1.min.js'></script>
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/bootstrap.min.js'></script>
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/jquery.validate.min.js'></script>
	<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/func.js"></script>
	<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/checkvalue.js"></script>
	<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.lazyload.js"></script>
	<#assign form = JspTaglibs["http://www.springframework.org/tags/form"]>
	<script type="text/javascript">
		var domain = '${(domainUrlUtil.urlResources)!}';
	</script>
  </head>
	<body class='wp1200'>
			<div class='wrapper'>
			<div class='container'>
				<ul class='collect lh'>
					<li class='fore1'>
						<a href='javascript:void(0)' onclick="AddFavorite(window.location,document.title);return false;">收藏幸福易</a>
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
							<a target="_blank" href="${(domainUrlUtil.urlResources)!}/member/order.html" target="_blank">我的订单</a>
						</li>
				   	<#else>
						<li class='fore1' id='loginbar'>
							<a href='${(domainUrlUtil.urlResources)!}/login.html' class='login'>你好，请登录</a>&nbsp;&nbsp;
							<a href='${(domainUrlUtil.urlResources)!}/register.html' class='regist'>免费注册</a>
						</li>
				   </#if>
					
					<li class='fore2-1 ld ff-vip' style='padding-left:12px;'>
						<span></span>
						<a target="_blank" href='${(domainUrlUtil.urlResources)!}/member/index.html'>会员中心</a>
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
			<div class='top-head'>
			<div class='container' id='HeardTop'>
				<div class='ld' id='logo-img'>
					<a href="${(domainUrlUtil.urlResources)!}/index.html" target="_blank">
						<img alt="" src="${domainUrlUtil.staticResources!}/img/yxkjlogo.png" >
					</a>
				</div>
				<div class='seach-box'>
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
						<dd id="priviewMycart">
							<!-- <div class="emptycart">
						      <div class="emptycart_line"></div>
						      <div class="emptycart_txt"><img src="imgttleup-nogoods.png" alt="">购物车中还没有商品，赶紧选购吧</div>
						   </div>-->
						</dd>
					</dl>
				</div>
			</div>
			<div class='container' id="allcate">
				<div class='nav'>
					<div id='CateGorys'>
						<div class='total-shop ld'>
							<h2>
								<a href=''>全部商品分类</a>
								<b></b>
							</h2>
						</div>
						<div class='yy-sortall' id="yy-sortall" style="height:auto;min-height:402px;">
						</div>
					</div>
					<ul class='navitems'>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/recommend.html#item'>多惠部落</a>
						</li>
						<#--
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/qianggou.html'>每日秒杀
							<i class="nav-icon hot-icon"></i></a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/tuan.html'>团购</a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/bidding-sale.html'>幸福易拼购
							<i class="nav-icon hot-icon"></i></a>
						</li>
						<li class='fore1'>
							<a href='${(domainUrlUtil.urlResources)!}/temai.html'>特卖场
							<i class="nav-icon new-icon"></i></a>
						</li>
						-->
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
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/common.js"></script>
	
	<script type="text/javascript">
		$(function(){
			// 图片延迟加载
			$("img.lazy").lazyload({
		 		effect : "fadeIn",
		 		threshold : 400,
		 		placeholder : "${(domainUrlUtil.staticResources)!}/img/NOTICE.jpg"
	    	});
			
			//加载购物车数据
			refreshMycart();
			
			//加载导航数据
			$.ajax({
				type:"GET",
				url:domain+"/cateList.html",
				dataType:"html",
				async : false,
				success:function(data){
						//加载数据
						$("#yy-sortall").html(data);
				}
			});
			
		})
	</script>
	
