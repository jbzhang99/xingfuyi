<#include "/front/commons/_headbig.ftl" />
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
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
					<a href='javascript:void(0)'>收藏的商品</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />


				<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>收藏的商品</h3>
				<div class='mc'>
					<div class='fav-goods-list'>
						<ul>
							<#if productList??>
								<#list productList as product>
									<li>
										<div class='fav-goods-item'>
											<div class='p-img'>
												<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html' target="_blank">
													<img src='${(domainUrlUtil.imageResources)!}${product.productLeadLittle}' width='160' height='160'>
												</a>
											</div>
											<div class='p-name'>
												<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html' target="_blank" title="${(product.productName)!''}">${(product.productName)!''}</a>
											</div>
											<div class='p-price'>
												<strong>￥${(product.mallPcPrice)?string("0.00")!''}</strong>
											</div>
											<div class='p-opbtns'>
												<a href='javascript:void(0);' onclick="unfollowProduct('${(product.productId)!''}')" class='btn btn-default'>取消收藏</a>
											</div>
										</div>
									</li>
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
		$("#collectionGoods").addClass("currnet_page");
	});
	
	//取消关注
	function unfollowProduct(id){
		if(confirm("是否取消关注")){
			$.ajax({
				type:"GET",
				url:domain+"/member/cancelcollectproduct.html",
				dataType:"json",
				async : false,
				data : {productId:id},
				success:function(data){
					if(data.success){
						//重新加载数据
						window.location.href=domain+"/member/collectproduct.html";
					}else{
						jAlert(data.message);
					}
				},
				error:function(){
					jAlert("异常，请重试！");
				}
			});
		}
	}
		
	</script>
	
<#include "/front/commons/_endbig.ftl" />
