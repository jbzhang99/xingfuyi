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
		<div class='container'>
			<div class='level-box clearfix'>
				<div class='level-nav'>
					<div class='leve-nav-main clearfix'>
						<div class='level-nav-item fist-level'>
							<a href='#' class='level-link'>${(productCatePidPid.name)!""}</a>
							<i class='level-right'>></i>
						</div>
						<div class='level-nav-item'>
							<div class='menu-drop'>
								<div class='trigger'>
									<span class='trigger-name'>${(productCatePid.name)!""}</span>
									<i class='menu-drop-arrow'></i>
								</div>
								
								<div class='menu-drop-main'>
									<ul class='menu-drop-list'>
											<#if productCate2s??>
												<#list productCate2s as productCate2s>
												<li>
													<a href='${(domainUrlUtil.urlResources)!}/list/${(productCate2s.id)!""}.html'>${(productCate2s.name)!""}</a>
												</li>
												</#list>
											</#if>
									</ul>
								</div>
								
							</div>
							<i class='level-right'>></i>
						</div>
						<div class='level-nav-item'>
							<div class='menu-drop'>
								<div class='trigger'>
									<span class='trigger-name'>${(productCate.name)!""}</span>
									<i class='menu-drop-arrow'></i>
								</div>
								
								<div class='menu-drop-main'>
									<ul class='menu-drop-list'>
											<#if productCate3s??>
												<#list productCate3s as productCate3s>
												<li>
													<a href='${(domainUrlUtil.urlResources)!}/cate/${(productCate3s.id)!""}.html'>${(productCate3s.name)!""}</a>
												</li>
												</#list>
											</#if>
									</ul>
								</div>
								
							</div>
							<i class='level-right'>></i>
						</div>
						
						<#if brandName??>
							<div class='shop-total selector-set'>
								<a href="javascript:void(0)" onclick="filterBrand(0)" class="ss-item"><b>${(brandName)!""}</b><i></i></a>
							</div>
						</#if>
						
						<#if productTypeAttrWhereAlls??>
							<#list productTypeAttrWhereAlls?keys as key>
								<div class='shop-total selector-set'>
									<a href="javascript:void(0)" class="ss-item" onclick="delFilter('-${(key)!""}')"><b>${productTypeAttrWhereAlls[key]}</b><i></i></a>
								</div>
							</#list>
						</#if>
			
					</div>
				</div>
			</div>
		</div>
		<div class='container' id='shop-tag'>
			<!-- 商品筛选 -->
			<div id='TagContainer' class='selector'>
				<div class='select-title'>
					<h3>
						<b>${(productCate.name)!""}</b>
						<em style='font-weight:bold'>&nbsp;商品筛选</em>
					</h3>
					<div class='shop-total'>
						共 &nbsp;<span>${(productSize)!""}&nbsp;</span>个商品
					</div>
				</div>
				
				<#if brandId == 0>
				<div class='shop-brand selector-fold'>
					<div class='select-brand'>
						<div class='select-key'>
							<span>品牌：</span>
						</div>
						<div class='select-value'>
							<ul class='select-the-letter brand-letter'>
								<li data-type='0'>所有品牌</li>
								<#if productBrandNameFirsts??>
									<#list productBrandNameFirsts as productBrandNameFirst>
										<li data-type='${(productBrandNameFirst)!""}'>${(productBrandNameFirst)!""}</li>
									</#list>
								</#if>
							</ul>
							<div class='select-brand-list'>
								<ul id='BrandType'class='value-list v-fixed'>
								<#if productBrands??>
									<#list productBrands as productBrand>
										<li data-type='${(productBrand.nameFirst)!""}'><a href="javascript:void(0)" onclick="filterBrand(${(productBrand.id)!})"><i></i>${(productBrand.name)!""}</a></li>
									</#list>
								</#if>
								</ul>
							</div>
						</div>
						<div class='select-ext'>
							<a class='select-more' style='visibility: visible;' href='javascript:void(0);'>更多 <i></i></a>
						</div>
					</div>
				</div>
				</#if>
				
				
				<#if productTypeAttrVOs??&&(productTypeAttrVOs?size>0)>
					<#list productTypeAttrVOs as productTypeAttrVO>
						<div class='select-price selector-fold  <#if (productTypeAttrVO_index>1)>tag-display</#if>'>
							<div class='select-brand'>
								<div class='select-key'style='padding-top:4px;'>
									<span>${(productTypeAttrVO.name)!""}：</span>
								</div>
								<div class='select-value'>
									<div class='select-brand-list'>
										<ul class='value-list'>
											<#if productTypeAttrVO.values??>
												<#list productTypeAttrVO.values as value>
													<li><a href="javascript:void(0);" onclick='filterType("-${(productTypeAttrVO.id)!}_${(value_index)!}")'><i>${(value)!""}</i></a></li>
												</#list>
											</#if>
										</ul>
									</div>
								</div>
								<!-- <div class='select-ext'>
									<a class='select-more' style='visibility: visible;' href='javascript:void(0);'>更多 <i></i></a>
								</div> -->
							</div>
						</div>
					</#list>
					<!-- 更多选项 -->
					<div class='select-more-tag' id='SelectMore'>
						<span class='select-wrap'>更多<i></i></span>
					</div>
				</#if>
			</div>
			<!-- end -->
			<!-- shop-list -->
			<div class='select-main' id='SelectMain'>
				<div class='shop-list'>
					<div class='shop-list-wrap'>
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
							
							<!--二次筛选功能 start-->
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
						 <!--二次筛选功能 end-->
							
						</div>
						<!-- 商品展示列表页 -->
						<div class='main-item'>
							<ul class='gl-warp clearfix'>
							
						<#if producListVOs??>
							<#list producListVOs as producListVO>
								<li class='item fl'>
									<div class='gl-wrap-i'>
										<div class='wrap-pic limit'>
											<a  target="_blank"  class='pro-img fl b1' href='${(domainUrlUtil.urlResources)!}/product/${(producListVO.id)!0}.html'>
												<img class="lazy" width="200" height="200" data-original='${(domainUrlUtil.imageResources)!}${(producListVO.masterImg)!""}'>
											</a>
										</div>
										<div class='pro-name limit'>
											<a href="${(domainUrlUtil.urlResources)!}/product/${(producListVO.id)!0}.html" class='' title='${(producListVO.name1)!""}' target='_blank'>${(producListVO.name1)!""}</em></a>
										</div>
										<div class='pro-price limit'>
											<p class='price-number fl'>
												<b>¥</b>
												<strong>${(producListVO.mallPcPrice)?string("0.00")!""}</strong>
											</p>
										</div>
										<div class="list-goods-tip">
											<#if producListVO.sellerId == 1><span class="icon-group-1">自营</span></#if>
											<#if producListVO.special == 1><span class="icon-group-3">特卖</span></#if>
											<#if producListVO.full == 1><span class="icon-group-2">满减</span></#if>
											<#if producListVO.coupon == 1><span class="icon-group-4">优惠券</span></#if>
											<#if producListVO.single == 1><span class="icon-group-2">立减</span></#if>
										</div>
										<div class='shops-details-btn shoping-cart'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${(producListVO.id)!0}.html'  target="_blank" class='search-btn b-normal btn-light cart-btn fl'>查看详情</a>
											<a href="javascript:void(0);" onclick="addCart('${(producListVO.id)!''}','${(producListVO.sellerId)!''}','0')" class='search-btn b-normal btn-light cart-btn fr'>加入购物车</a>
										</div>
									</div>
								</li>
						  </#list>
				    </#if>
							</ul>
						</div>
							<!-- 分页 -->
							<#include "/front/commons/_paginationproductlist.ftl" />
					</div>
				</div>
				<div class='promote-shop'>
					<div class='promote-bar'>
						<!-- 用户最终购买了 -->
						<div class='promote-goods'>
							<div class='mt'>
								<h2 style="line-height: 26px;">推广商品</h2>
							</div>
							<div class='goods-mc'>
								<ul>
				    				 <div id="cateLeftId"></div>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end -->
		</div>

<script type="text/javascript">
window.onload=function(){
	cateTopAjax();
	cateLeftAjax();
}

function delFilter(filter) {
	var urlPath = "${(urlPath)!}";
	var url = urlPath.replace(filter,"");
	
	var urlPaths = url.split("-");
	var urls = "";
	for(var i=0; i<urlPaths.length; i++) {
		if(i == 2){
			urlPaths[i] = 1;
		}
		urls += urlPaths[i];
		if((i+1) != urlPaths.length) {
			urls += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + urls + ".html";
}

function filterBrand(brand) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
		if(i == 2){
			urlPaths[i] = 1;
		}
	    if(i == 6) {
	    	url += brand;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterStore(store) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
		if(i == 2){
			urlPaths[i] = 1;
		}
	    if(i == 5) {
	    	url += store;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterDoSelf(doSelf) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
		if(i == 2){
			urlPaths[i] = 1;
		}
	    if(i == 4) {
	    	url += doSelf;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterSort(sort) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
		if(i == 2){
			urlPaths[i] = 1;
		}
	    if(i == 3) {
	    	url += sort;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterType(type) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
		if(i == 2){
			urlPaths[i] = 1;
		}
	    url += urlPaths[i];
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	url += type;
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}


function cateTopAjax() {
	var cateTopHtml = "";						
	$.ajax({
        type:"get",
        url: "${(domainUrlUtil.urlResources)!}/cateTop.html?cateId=" + ${cateId},
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
                    cateTopHtml += "<a href='javascript:void(0);' onclick=addCart(" + product.id + "," + product.sellerId + "," +1+")" + " class='btn btn-default'>立即抢购</a>";
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

function cateLeftAjax() {
	var cateLeftHtml = "";						
	$.ajax({
        type:"get",
        url: "${(domainUrlUtil.urlResources)!}/cateLeft.html?cateId=" + ${cateId},
        dataType: "json",
        cache:false,							
        success:function(data){
            if (data.success) {
                $.each(data.data, function(i, product){
                	cateLeftHtml += "<li><div class='goods-img'>";
                    cateLeftHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' target='_blank' >";
                    cateLeftHtml += "<img width='200' height='200' data-original='${(domainUrlUtil.imageResources)!}" + product.masterImg + "'></a></div>";
                    cateLeftHtml += "<div class='goods-price'><span>特价：</span><strong>￥" + parseFloat(product.mallPcPrice).toFixed(2) + "</strong></div><div class='goods-name'>";
                    cateLeftHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' target='_blank' title='" + product.name1 + "'>" + product.name1 + "</a>";
                    cateLeftHtml += "</div></li>";
                })
                $("#cateLeftId").append(cateLeftHtml);
                
                $("#cateLeftId > li > div > a > img").lazyload({
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
	function addCart(productId,sellerId,type){
		//未登录不能添加购物车
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type : "POST",
			url :  domain+"/cart/addtocart.html?type="+type,
			data : {productId:productId,sellerId:sellerId},
			dataType : "json",
			success : function(data) {
				if(data.success){
					//跳转到添加购物车成功页面
					window.open(domain+"/cart/add.html?id=" + data.data.id);
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
<!-- 登录弹出框 -->
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />