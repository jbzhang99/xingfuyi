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
					<a href='javascript:void(0)'>我的评价</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />
			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>我的评价</h3>
				<div class='mc'>
					<table class='tb-void'>
						<thead>
							<tr>
								<th width='328'>商品信息</th>
								<th>购买时间</th>
								<th>评价状态</th>
							</tr>
						</thead>
						<tbody>
						<#if commentsList??>
								<#list commentsList as comment>
							<tr class='cli-box'>
								<td colspan="3">
									<ul class='pro-info'>
										<li class="fore1">
											<div class='p-info'>
												<div class='p-img fl'>
													<a href="${(domainUrlUtil.urlResources)!}/product/${(comment.productId)!0}.html" target='_blank'>
														<img src="${(domainUrlUtil.imageResources)!}${comment.productLeadLittle!''}" width='50' height='50'>
													</a>
												</div>
												<div class='p-name fl'>
													<a href="${(domainUrlUtil.urlResources)!}/product/${(comment.productId)!0}.html" target='_blank'>${(comment.productName)!''}</a>
												</div>
											</div>
										</li>
										<li class='fore2'>
											<span class='ftx03'>${(comment.createTime?string("yyyy-MM-dd"))!''}</span>
										</li>
										<li class='fore3'>
											<a href='javascript:void(0)' class="look-avalute" onclick="editEvaluat(this)">查看评价</a>
										</li>
									</ul>
									<!-- 查看评价 --> 
									<div class='my-evaluat look-box'>
										<div class='box-t'></div>
										<div class='evaluat-form'>
											<div class='item'>
												<span class="label">
													评分：
												</span>
												<div class='fl'>
													<span class='commstar look-commstart' value="${(comment.grade)!''}">
														<a href='javascript:void(0);' class='star1'></a>
														<a href='javascript:void(0);' class='star2'></a>
														<a href='javascript:void(0);' class='star3'></a>
														<a href='javascript:void(0);' class='star4'></a>
														<a href='javascript:void(0);' class='star5 '></a>
													</span>
												</div>
												<span class='msg-error-01 hide'>你的评分是偶们前进的动力</span>
											</div>
											<div class='item'>
												<span class="label">
													<em>*</em>
													描述相符：
												</span>
												<div class='fl'>
													<span class='commstar' attrname="description" value="${(comment.description)!''}">
														<a href='javascript:void(0);' class='star1' val="1"></a>
														<a href='javascript:void(0);' class='star2' val="2"></a>
														<a href='javascript:void(0);' class='star3' val="3"></a>
														<a href='javascript:void(0);' class='star4' val="4"></a>
														<a href='javascript:void(0);' class='star5' val="5"></a>
														
													</span>
												</div>
												<span class='msg-error-01 hide'></span>
											</div>
											<div class='item'>
												<span class="label">
													<em>*</em>
													服务态度：
												</span>
												<div class='fl'>
													<span class='commstar'  attrname="serviceAttitude" value="${(comment.serviceAttitude)!''}">
														<a href='javascript:void(0);' class='star1' val="1"></a>
														<a href='javascript:void(0);' class='star2' val="2"></a>
														<a href='javascript:void(0);' class='star3' val="3"></a>
														<a href='javascript:void(0);' class='star4' val="4"></a>
														<a href='javascript:void(0);' class='star5' val="5"></a>
													</span>
												</div>
												<span class='msg-error-01 hide'></span>
											</div>
											
											<div class='item'>
												<span class="label">
													<em>*</em>
													发货速度：
												</span>
												<div class='fl' >
													<span class='commstar' attrname="productSpeed" value="${(comment.productSpeed)!''}">
														<a href='javascript:void(0);' class='star1' val="1"></a>
														<a href='javascript:void(0);' class='star2' val="2"></a>
														<a href='javascript:void(0);' class='star3' val="3"></a>
														<a href='javascript:void(0);' class='star4' val="4"></a>
														<a href='javascript:void(0);' class='star5' val="5"></a>
													</span>
												</div>
												<span class='msg-error-01 hide'></span>
											</div>
											<div class='item'>
												<span class="label">
													心得：
												</span>
												<div class='cont fl'>${comment.content!''}</div>
											</div>
										</div>
									</div>
									<!-- end -->
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
				$("#myevaluation").addClass("currnet_page");
			});
			
			function editEvaluat(obj){
				$(obj).parent().parent().siblings(".look-box").slideToggle();
			 	$(obj).parents(".cli-box").siblings().find(".look-box").slideUp();
			}
			
			//初始化星星 
			$(".commstar").each(function(){
				var index = $(this).attr('value');
				$(this).children(".star"+index).addClass("active");
			});

		</script>
		<!-- footer -->
		<!-- 页脚 -->
		<#include "/front/commons/_endbig.ftl" />