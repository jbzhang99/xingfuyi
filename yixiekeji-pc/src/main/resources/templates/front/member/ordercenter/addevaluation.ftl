<#include "/front/commons/_headbig.ftl" />

	
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
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
					<a href='javascript:void(0)'>发表评价</a>
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
					<table class='tb-void p-states'>
						<thead>
							<tr>
								<th width='328'>商品信息</th>
								<th>购买时间</th>
								<th>评价状态</th>
							</tr>
						</thead>
						<tbody>
						
						<#if order.orderProductList??>
						<#list  order.orderProductList as product>
							<tr class='cli-box'>
								<td colspan="3">
									<ul class='pro-info'>
										<li class="fore1">
											<div class='p-info'>
												<div class='p-img fl'>
													<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html' target='_blank'>
														<img title="${(product.productName)!''}" src='${(domainUrlUtil.imageResources)!}${product.productLeadLittle}' width='50' height='50'>
													</a>
												</div>
												<div class='p-name fl'>
													<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html' target='_blank'>${(product.productName)!''}</a>
												</div>
											</div>
										</li>
										<li class='fore2'>
											<span class='ftx03'>${(order.createTime?string("yyyy-MM-dd"))!''}</span>
										</li>
										<li class='fore3'>
											<a href='javascript:void(0);' class='look-avalute' onclick="editEvaluat(this,'${(order.orderSn)!''}',${(product.productId)!''},${(product.productGoodsId)!''},${(product.id)!''})">发表评价</a>
										</li>
									</ul>
									
									<!-- 编辑评价 --> 
									<div id="my-evaluat${(product.productGoodsId)!''}" class='my-evaluat look-box'>
										
									</div>
									<!-- end -->
								</td>
							</tr>
							</#list>
						</#if>
							
						</tbody>
					</table>
				</div>
			</div>
		</form>	
		</div>
		
		
		
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#myevaluation").addClass("currnet_page");
		});
		function editEvaluat(obj,orderSn,productId,productGoodsId,ordersProductId){
		 	$(obj).parent().parent().siblings(".look-box").slideToggle();
		 	$(obj).parents(".cli-box").siblings().find(".look-box").slideUp();
		 	
		 	$(".my-evaluat").empty();
		 	 $.ajax({
				type:"GET",
				url:domain+"/member/editcomment.html",
				dataType:"html",
				async : false,
				data : {orderSn:orderSn,productId:productId,productGoodsId:productGoodsId,ordersProductId:ordersProductId},
				success:function(data){
						//加载数据
						$("#my-evaluat"+productGoodsId).html(data);
				},
				error:function(){
					jAlert("异常，请重试！");
				}
			});
		}
	</script>
	
<#include "/front/commons/_endbig.ftl" />
