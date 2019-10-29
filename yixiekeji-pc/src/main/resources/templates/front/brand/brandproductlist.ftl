<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/list.css">

		<div class='container'>
			<div class='hot-salse'>
				<div id='HotSalse' class='hot-salse-main'>
					<div class='hot-recommend'>热卖推荐</div>
					<div class='recommend-details mc'>
						<ul class='mc clearfix list-ul'>
							 <div id="cateTopId"></div>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class='container' id='shop-tag'>

			<!-- shop-list -->
			<div class='select-main' id='SelectMain'>
				<div class='shop-list'>
					<div class='shop-list-wrap shop-list-wrap-wt'>
						<div class='filter' id='shop-filter'>
							<div class='filter-line top'>
								<div class='composite-sort'>
									<a href="javascript:void(0)" onclick="filterSort(0)" <#if sort?? && sort==0>class='btn-sort'</#if>>综合排序<i></i></a>
									<a href="javascript:void(0)" onclick="filterSort(1)" <#if sort?? && sort==1>class='btn-sort'</#if>>销量<i></i></a>
									
									<#if sort??>
									<#if sort == 0 || sort == 1 || sort == 2>
										<a href="javascript:void(0)" onclick="filterSort(3)">价格<i></i></a>
									<#else>
										<#if sort == 3>
											<a href="javascript:void(0)" onclick="filterSort(4)" class='btn-sort up'>价格<i></i></a>
										</#if>
										<#if sort == 4>
											<a href="javascript:void(0)" onclick="filterSort(3)" class='btn-sort down'>价格<i></i></a>
										</#if>
									</#if>
									</#if>
									<a href="javascript:void(0)" onclick="filterSort(2)" <#if sort?? && sort==2>class='btn-sort'</#if>>评论数<i></i></a>
								</div>
							</div>
							<div class='filter-line clearfix'>
								<div class='f-store'>
									<div class='f-harvested instock'>
										<#if store?? && store==0>
										    <a href="javascript:void(0)" onclick="filterStore(1)"><i></i>仅显示有货</a>
										<#else>
											<a class="selected" href="javascript:void(0)" onclick="filterStore(0)"><i></i>仅显示有货</a>
										</#if>
									</div>
								</div>
								<div class='f-feature'>
									<ul>
										<#if doSelf?? && doSelf==0>
											<li><a href="javascript:void(0)" onclick="filterDoSelf(1)"><i></i>商城自营</a></li>
										<#else>
											<li><a class="selected" href="javascript:void(0)" onclick="filterDoSelf(0)"><i></i>商城自营</a></li>
										</#if>
									</ul>
								</div>
							</div>
						</div>
						<!-- 商品展示列表页 -->
						<div class='main-item'>
							<ul class='gl-warp clearfix gl-warp-wt'>
								<#if productList?? && productList?size &gt; 0 >
									<#list productList as product>
										<li class='item fl item-li'>
											<div class='gl-wrap-i'>
												<div class='wrap-pic limit'>
													<a  target="_blank"  class='pro-img fl b1' href='${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html'>
														<img width="200" height="200" 
															class="lazy"
															data-original='${(domainUrlUtil.imageResources)!}${(product.masterImg)!""}'>
													</a>
												</div>
												<div class='pro-name limit'>
													<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" class='' title='${(product.name1)!""}' target='_blank'>${(product.name1)!""}</em></a>
												</div>
												<div class='pro-price limit'>
													<p class='price-number fl'>
														<b>¥</b>
														<strong>${(product.mallPcPrice)?string("0.00")!""}</strong>
													</p>
												</div>
												<div class='look-goods limit'>
												<#if product.productStock gt 0>
													<span class='font-pale fl'>有货</span>
												<#else>
													<span class='font-pale fl'>无货</span>
												</#if>
												<span class='shop-comment fr fl'>${(product.commentsNumber)!"0"} 条评价</span>
												</div>
												<div class='shops-details-btn shoping-cart'>
													<a href='${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html'  target="_blank" class='search-btn b-normal btn-light cart-btn fl'>查看详情</a>
													<a href="javascript:void(0);" onclick="addCart('${(product.id)!''}','${(product.sellerId)!''}','1')" class='search-btn b-normal btn-light cart-btn fr'>加入购物车</a>
												</div>
											</div>
										</li>
									</#list>
								<#else>
									没有发现您需要的商品。。。
								</#if>
							</ul>
						</div>
						<div class='page'>
							<!-- 分页 -->
							<#include "/front/commons/_paginationbrandprdlist.ftl" />
						</div>
					</div>
				</div>
				
			</div>
			<!-- end -->
		</div>

<!-- 登录弹出框 -->
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />


<script type="text/javascript">
	window.onload=function(){
		brandTopAjax();
	}
	
	function filterStore(store) {
		// 品牌ID-分页-排序-自营非自营－有货无货
		var urlPath = "${(urlPath)!}";
		var urlPaths = urlPath.split("-");
		var url = "";
		for(var i=0; i<urlPaths.length; i++) {
		    if(i == 4) {
		    	url += store;
		    } else {
		    	url += urlPaths[i];
		    }
			if((i+1) != urlPaths.length) {
				url += "-";
			}
		}
		self.location="${(domainUrlUtil.urlResources)!}/brand/" + url + ".html";
	}

	function filterDoSelf(doSelf) {
		// 品牌ID-分页-排序-自营非自营－有货无货
		var urlPath = "${(urlPath)!}";
		var urlPaths = urlPath.split("-");
		var url = "";
		for(var i=0; i<urlPaths.length; i++) {
		    if(i == 3) {
		    	url += doSelf;
		    } else {
		    	url += urlPaths[i];
		    }
			if((i+1) != urlPaths.length) {
				url += "-";
			}
		}
		self.location="${(domainUrlUtil.urlResources)!}/brand/" + url + ".html";
	}

	function filterSort(sort) {
		// 品牌ID-分页-排序-自营非自营－有货无货
		var urlPath = "${(urlPath)!}";
		var urlPaths = urlPath.split("-");
		var url = "";
		for(var i=0; i<urlPaths.length; i++) {
		    if(i == 2) {
		    	url += sort;
		    } else {
		    	url += urlPaths[i];
		    }
			if((i+1) != urlPaths.length) {
				url += "-";
			}
		}
		self.location="${(domainUrlUtil.urlResources)!}/brand/" + url + ".html";
	}

	function brandTopAjax() {
		var cateTopHtml = "";						
		$.ajax({
	        type:"get",
	        url: "${(domainUrlUtil.urlResources)!}/brandtop.html?brandId=" + ${brandId!0},
	        dataType: "json",
	        cache:false,
	        success:function(data){
	            if (data.success) {
	                $.each(data.data, function(i, product){
	                	cateTopHtml += "<li><a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' class='recommend-img' target='_blank'>";
	                    cateTopHtml += "<img width='100 height='100' data-original='${(domainUrlUtil.imageResources)!}" + product.masterImg + "'></a>";
	                    cateTopHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' target='_blank' class='shop-name' title='" + product.name1 + "'>";
	                    cateTopHtml += "<em>" + product.name1 + "</em></a><div class='shop-price'><span>特价：</span><strong>";
	                    cateTopHtml += "<em class='shop-number'>￥" + parseFloat(product.mallPcPrice).toFixed(2) + "</em></strong></div><div class='shop-snapped'>";
	                    cateTopHtml += "<a href='javascript:void(0);' onclick=addCart(" + product.id + "," + product.sellerId + ")" + " class='btn btn-default'>立即抢购</a>";
	                    cateTopHtml += "</div></li>";
	                })
	                $("#cateTopId").append(cateTopHtml);
	                
	                $("#cateTopId > li > a > img").lazyload({
	    		 		effect : "fadeIn",
	    		 		placeholder : "${(domainUrlUtil.staticResources)!}/img/NOTICE.jpg"
	    	    	});
	            }
	        }
	    });
	}

	/**
		添加购物车
	*/
	function addCart(productId,sellerId){
		//未登录不能添加购物车
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type : "POST",
			url :  domain+"/cart/addtocart.html",
			data : {productId:productId,sellerId:sellerId},
			dataType : "json",
			success : function(data) {
				if(data.success){
					//跳转到添加购物车成功页面
					window.open(domain+"/cart/add.html?id=" + data.data);  
				}else{
					jAlert(data.message);
				}
			},
			error : function() {
				jAlert("数据加载失败！");
			}
		});
	}
</script>