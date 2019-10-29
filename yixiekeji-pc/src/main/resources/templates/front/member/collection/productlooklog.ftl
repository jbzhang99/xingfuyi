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
					<a href='javascript:void(0)'>浏览记录</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />


				<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>浏览记录</h3>
				<div class='mc'>
					<div class='fav-goods-list'>
						<ul>
							<#if lookLogList??>
								<#list lookLogList as lookLog>
									<#if lookLog.product?? >
									<li>
										<div class='fav-goods-item'>
											<div class='p-img'>
												<a href='${(domainUrlUtil.urlResources)!}/product/${(lookLog.product.id)!0}.html' target="_blank">
													<img src='${(domainUrlUtil.imageResources)!}${lookLog.product.masterImg}' width='160' height='160'>
												</a>
											</div>
											<div class='p-name'>
												<a href='${(domainUrlUtil.urlResources)!}/product/${(lookLog.product.id)!0}.html' target="_blank" title="${(lookLog.product.name1)!''}">${(lookLog.product.name1)!''}</a>
											</div>
											<div class='p-price'>
												<strong>￥${(lookLog.product.mallPcPrice)?string("0.00")!''}</strong>
											</div>
											<div>
												浏览日期：${(lookLog.createTime)!}
											</div>
										</div>
									</li>
									</#if>
								</#list>
							</#if>
						</ul>
					</div>
				</div>
				<!-- 分页 -->
				<#include "/front/commons/_pagination.ftl" />
			
			</div>
			<!-- end -->
		</div>
	<script type="text/javascript">
	$(function(){
		//控制左侧菜单选中
		$("#productLookLog").addClass("currnet_page");
	});
	</script>
	
<#include "/front/commons/_endbig.ftl" />
