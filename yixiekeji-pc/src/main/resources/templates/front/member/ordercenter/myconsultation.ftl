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
					<a href='javascript:void(0)'>我的咨询</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />
			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>我的咨询</h3>
				<div class='mc'>
					<table class='tb-void'>
						<thead>
							<tr>
								<th>咨询商品</th>
								<th >咨询内容</th>
								<th width="25%">咨询回复</th>
								<th width="13%">咨询时间</th>
							</tr>
						</thead>
						
						<tbody>
						<#if askList??>
							<#list askList as askinfo >
							<tr>
								<td>
								   <div class="clearfix" style="width:260px;">
										<div class='img-list zixun-img mg0 clearfix fl'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${(askinfo.productId)!0}.html' target='_blank' class='img-box'>
												<img src='${(domainUrlUtil.imageResources)!}${askinfo.productLeadLittle}' width='60' height='60' class='err-product' title='${(askinfo.productName)!''}'>
											</a>
										</div>
										<span>${(askinfo.productName)!''}</span>
									</div>
								</td>
								<td>
									${(askinfo.askContent)!''}
								</td>
								<td>
									${(askinfo.replyContent)!''}
								</td>
								<td>
									${(askinfo.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}
								</td>
							</tr>
								
							</#list>
						</#if>
						</tbody>
					</table>
				</div>
				
				<!-- 分页 -->
				<#include "/front/commons/_pagination.ftl" />
				
			</div>
		</div>
		<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#myconsultation").addClass("currnet_page");
		});
		
		</script>
<#include "/front/commons/_endbig.ftl" />
