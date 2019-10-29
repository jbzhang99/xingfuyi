<#include "/front/commons/_headbig.ftl" />
	<div class='container'>
			<div class='breadcrumb'>
				<strong class='business-strong'>
					<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
				</strong>
				<span>
					&nbsp;>&nbsp;
					<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技</a>
				</span>
				<span>
					&nbsp;>&nbsp;
					<a href='javascript:void(0)'>我的优惠劵</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />
			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>我的优惠劵</h3>
				
				<div class='mc'>
					<div class="coupon-toolbar">
						<ul>
							<li><a href="${(domainUrlUtil.urlResources)!}/member/coupon-use.html">未使用</a></li>
							<li class="active"><a href="${(domainUrlUtil.urlResources)!}/member/coupon-use-yes.html">已使用</a></li>
						</ul>
					</div>

					<div class="voucher">
						<!-- 未使用 -->
						<div class="unused-box-use main-use selected">
						<#if couponUsers??>
							<#list couponUsers as couponUser>
								<div class="unused-box">
									<div class="unused-left">
											<strong class="unused-price">￥${(couponUser.couponValue)!}</strong>
											<p class="unused-limit">【消费满 ${(couponUser.minAmount)!}元 可用】</p>
											<div class="unused-time"><#if couponUser.useStartTime??>${couponUser.useStartTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
												--<#if couponUser.useEndTime??>${couponUser.useEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
											<div class="unused-icon"></div>
											<div class="used-icon"></div>
									</div>
									<div class="unused-right">
											<div class="unused-range-item">
												<span class="">券&nbsp;&nbsp;编&nbsp;&nbsp;号：</span>
												<span class="txt">${(couponUser.couponSn)!}</span>
											</div>
											<div class="unused-range-item">
												<span class="">优惠价名称：</span>
												<span class="txt">${(couponUser.couponName)!}</span>
											</div>
											<div class="unused-range-item">
												<span class="">适用商家：</span>
												<span class="txt">${(couponUser.sellerName)!}</span>
											</div>
									</div>
								</div>
							</#list>
						</#if>	
						</div>
					</div>

					<div class='mt10'>
						<!-- 分页 -->
						<#include "/front/commons/_pagination.ftl" />
					</div>
			  </div>
				
			</div>
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#myecouponuser").addClass("currnet_page");
		});
	</script>						
<#include "/front/commons/_endbig.ftl" />