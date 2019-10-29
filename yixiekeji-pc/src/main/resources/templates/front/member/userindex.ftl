<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/userindex.css"/>
	<div class='container w1200'>
		<div class='breadcrumb'>
			<strong class='business-strong'>
				<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
			</strong>
			<span>
				&nbsp;>&nbsp;
				<a href='javascript:void(0)'>我的齐驱科技</a>
			</span>
			<span>
				&nbsp;>&nbsp;
				<a href='javascript:void(0)'>用户中心</a>
			</span>
		</div>
	</div>
	
	<div class='container'>
		<#include "/front/commons/_left.ftl" />
	
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder wrapper_main-wd'>
		<div class="user_info_top">
			<div class="user_info_l fl">
				<div class="user_avatar fl" >
					<img src="${(domainUrlUtil.staticResources)!}/img/vip_face_default.jpg" class="head_img" width="110" height="110">
				</div >
				<div class="user_avatar_r fl">
					<b>${commUtil.hideMiddleStr(member.name,2,2)}</b>
					<br>
					<#if member.grade == 1>
						<img src="${(domainUrlUtil.staticResources)!}/img/level1.jpg" alt="注册会员" title="注册会员" width="30" height="30">
					<#elseif member.grade == 2>
						<img src="${(domainUrlUtil.staticResources)!}/img/level2.jpg" alt="铜牌会员" title="铜牌会员" width="30" height="30">
					<#elseif member.grade == 3>
						<img src="${(domainUrlUtil.staticResources)!}/img/level3.jpg" alt="银牌会员" title="银牌会员" width="30" height="30">
					<#elseif member.grade == 4>
						<img src="${(domainUrlUtil.staticResources)!}/img/level4.jpg" alt="金牌会员" title="金牌会员" width="30" height="30">
					<#elseif member.grade == 5>
						<img src="${(domainUrlUtil.staticResources)!}/img/level5.jpg" alt="钻石会员" title="钻石会员" width="30" height="30">
					</#if>
					
					<div class="h20"><span>经验值：</span><span class="red">${(member.gradeValue)!}</span></div>
					<#if gradeValue!=0>
						<div class="h20"><span>距离下次升级还差经验值：</span><span class="red">${(gradeValue)!}</span></div>
						
					</#if>
				</div>
			</div>
			<div class="info-rcol">
				<ul class="acco-info fl">
					<li class="acco-item">
						<div>
							<em>余额：</em>
							<a href="${(domainUrlUtil.urlResources)!}/member/balance.html">${(member.balance)!}</a>
						</div>
						<div>
							<em>积分：</em>
							<a class="bold" href="">${(member.integral)!}</a>
						</div>
						<div>
							<em>优惠券：</em>
							<a class="bold" href="${(domainUrlUtil.urlResources)!}/member/coupon-use.html">${(couponNum)!0}</a>
							<a class="pa10 a-col" target="_blank" href="${(domainUrlUtil.urlResources)!}/coupon.html">领券</a>
						</div>
					</li>
				</ul>
				<div class="verification-box fl">
					<#if member.isSmsVerify == 0>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/tel.jpg" alt="" width="50" height="72">
							<a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="red">去验证</a>
							<p>
								<#if member.mobile??>
									${commUtil.hideMiddleStr(member.mobile,3,4)}
								</#if>
							</p>
						</div>
					<#else>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/telyes.jpg" alt="" width="50" height="72">
							<a class="colgreen">已验证</a>
							<p>
								<#if member.mobile??>
									${commUtil.hideMiddleStr(member.mobile,3,4)}
								</#if>
							</p>
						</div>
					</#if>
					<#if member.isEmailVerify == 0>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/yx.jpg" alt="" width="68" height="70">
							<a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="red">去验证</a>
							<p>
								<#if member.email??>
									${commUtil.hideMiddleStr(member.email,2,5)}
								</#if>
							</p>
						</div>
					<#else>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/yxyes.jpg" alt="" width="68" height="70">
							<a class="colgreen">已验证</a>
							<p>
								<#if member.email??>
									${commUtil.hideMiddleStr(member.email,2,5)}
								</#if>
							</p>
						</div>
					</#if>
				</div>
			</div>
		</div>

		<!-- S 最近的订单 -->
		<div class="recent-orders">
			<div class="order-tit">
				<span class="t">最近的订单</span>
				<a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=1" class="zero">待付款<span class="num">${(toBepaidOrders)!0}</span></a>
				<span class="dividingLine">|</span>
				<a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=4" class="zero">待收货<span class="num">${(toBeReceivedOrders)!0}</span></a>
				<span class="dividingLine">|</span>
				<a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=5&evaluateNoState=3" class="zero">待评价<span class="num">${(toBeEvaluateOrders)!0}</span></a>
				<a href="${(domainUrlUtil.urlResources)!}/member/order.html" class="r">查看更多订单>></a>
			</div>
			<ul class="pro">
			<#if orders??>
				<#list orders as order>
					<#if order.orderState!=6>
				<li>
					<a target="_blank" href="${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=${(order.id)!}">
						<#if order.orderProductList??>
							<#list order.orderProductList as orderProduct>
								<#if orderProduct_index==0>
									<img src="${(domainUrlUtil.imageResources)!}/${orderProduct.productLeadLittle}" alt="" >
								</#if>
							</#list>
						</#if>
					</a>
					<div class="text">
						<p>订单号：<span class="num">${(order.orderSn)!}</span></p>
						<p>共<span class="b">${(order.orderProductList?size)!}</span>件商品</p>
						<p class="total">总金额：<span>￥${(order.moneyOrder)?string("0.00")!}</span></p>
					</div>
					<div class="bar fr">
						<#include "/front/member/orderprogress.ftl" />
					</div>
				</li>
					</#if>
				</#list>
				<#else>
				</li>
				<li>
				</#if>
			</ul>
		</div>
		<!-- E 最近的订单 -->

		<!-- S 推荐商品 -->
		<div class="pick-week">
			<h3>推荐商品</h3>
			<ul>
				<#if products??>
					<#list products as product>
						<li>
							<div class="small_pergood">
								<div class="small_goodimg dashed">
									<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" target='_blank'>
										<img class="lazy" data-original="${(domainUrlUtil.imageResources)!}${(product.masterImg)!}" alt="" width="140" height="140">
									</a>
								</div>
								<p class="list_good_des"><a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" target='_blank' title="${(product.name1)!}">${(product.name1)!}</a></p>
								<p class="small_good_price">￥${(product.mallPcPrice)?string("0.00")!}</p>
							</div>
						</li>
					</#list>
				</#if>
			</ul>
		</div>
	</div>
		<!-- E 推荐商品 -->	
		
<script type="text/javascript">
$(function(){
	//控制左侧菜单选中
	$("#memberIndex").addClass("currnet_page");
});
</script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/list.js'></script>
<#include "/front/commons/_endbig.ftl" />
