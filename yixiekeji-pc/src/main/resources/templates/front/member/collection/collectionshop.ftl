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
					<a href='javascript:void(0)'>收藏的店铺</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

				<!-- 右侧主要内容 -->
				<div class='wrapper_main myorder'>
				<h3>关注的店铺</h3>
				<div class='mc'>
					<div class='fav-lists'>
						<div class='fav-list fav-list-box clearfix'>
						<#if sellerList??>
							<#list sellerList as seller>
						
							<!-- 店铺信息 -->
							<div class='i-store i-store-box'>
								<div class='i-logo'>
										<a href='' target="_blank">
											<img src='${(domainUrlUtil.imageResources)!}/${(seller.sellerLogo)!''}' width='180' height='60'> 
										</a>
								</div>
								<ul class='list-info'>
									<li>
										<span class='label'>店铺名称：</span>
										${(seller.sellerName)!''}
									</li>
									<li>
										<span class='label'>关注人气：</span>
										${(seller.seller.collectionNumber)!0}人
									</li>
									<li>
										<span class='label'>服务评价：</span>
										<a href='javascript:void(0);'>9.81</a>
									</li>
									<li>
										<span class='label'>关注时间：</span>
										${(seller.createTime?string("yyyy-MM-dd"))!''}
									</li>
								</ul>
								<div class='btns'>
									<a href="${(domainUrlUtil.urlResources)!}/store/${(seller.sellerId)!''}.html" onclick="" class='btn btn-default'>进入店铺</a>
								</div>
								<div class='btns'>
									<a href='javascript:void(0)' onclick="unfollow(${(seller.sellerId)!''})" class='btn btn-default'>取消关注</a>
								</div>
							</div>
							</#list>
						</#if>
							
						</div>
					</div>
				</div>
				<!-- 分页 -->
				<#include "/front/commons/_pagination.ftl" />
			</div>
		</div>

			<!-- end -->
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#collectionShop").addClass("currnet_page");
		});
		
		//取消关注
		function unfollow(id){
			if(confirm("是否取消关注")){
				$.ajax({
					type:"GET",
					url:domain+"/member/cancelcollectshop.html",
					dataType:"json",
					async : false,
					data : {sellerId:id},
					success:function(data){
						if(data.success){
							//重新加载数据
							window.location.href=domain+"/member/collectshop.html";
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
