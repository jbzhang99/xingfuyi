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
							<a href='#' class='level-link'>搜索结果</a>
							<i class='level-right'>></i>
						</div>
						<div class='level-nav-item'>
						${(keyword)!''}
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='container' id='shop-tag'>
			<!-- 商品筛选 -->
			<div id='TagContainer' class='selector'>
				<div class='select-title'>
					<h3>
						<b></b>
						<em style='font-weight:bold'>&nbsp;商品筛选</em>
					</h3>
					<div class='shop-total'>
						共 &nbsp;<span>${(count)!""}&nbsp;</span>个商品
					</div>
				</div>
			</div>
			<!-- end -->
			<!-- shop-list -->
			<div class='select-main' id='SelectMain'>
				<div class='shop-list'>
					<div class='shop-list-wrap'>
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
											<a href="${(domainUrlUtil.urlResources)!}/product/${(producListVO.id)!0}.html" class='' target='_blank'>
											<#noescape>${(producListVO.name1)!""}</#noescape>
											</em></a>
										</div>
										<div class='pro-price limit'>
											<p class='price-number fl'>
												<b>¥</b>
												<strong>${(producListVO.mallPcPrice)!""}</strong>
											</p>
										</div>
										<div class='look-goods limit'>
										<#if producListVO.productStock gt 0>
											<span class='font-pale fl'>有货</span>
										<#else>
											<span class='font-pale fl'>无货</span>
										</#if>
										<span class='shop-comment fr fl'>${(producListVO.commentsNumber)!"0"} 条评价</span>
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
						<#if page?? && page.pageCount gt 1>
					      <div class="pagin-box">
					      	<#include "/front/commons/_pagination.ftl" />
					      </div>
				      </#if>
					</div>
				</div>
				<div class='promote-shop'>
					<div class='promote-bar'>
						<!-- 用户最终购买了 -->
						<div class='promote-goods'>
							<div class='mt'>
								<h3>推广商品</h3>
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


function cateTopAjax() {
	var cateTopHtml = "";						
	$.ajax({
        type:"get",
        url: "${(domainUrlUtil.urlResources)!}/searchTop.html",
        dataType: "json",
        cache:false,
        success:function(data){
            if (data.success) {
                $.each(data.data, function(i, product){
                	cateTopHtml += "<li><a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' class='recommend-img' target='_blank'>";
                    cateTopHtml += "<img width='100 height='100' data-original='${(domainUrlUtil.imageResources)!}" + product.masterImg + "'></a>";
                    cateTopHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' target='_blank' class='shop-name'>";
                    cateTopHtml += "<em>" + product.name1 + "</em></a><div class='shop-price'><span>特价：</span><strong>";
                    cateTopHtml += "<em class='shop-number'>￥" + product.mallPcPrice + "</em></strong></div><div class='shop-snapped'>";
                    cateTopHtml += "<a href='javascript:void(0);' onclick=addCart(" + product.id + "," + product.sellerId + ","+1+")" + " class='btn btn-default'>立即抢购</a>";
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
        url: "${(domainUrlUtil.urlResources)!}/searchLeft.html",
        dataType: "json",
        cache:false,							
        success:function(data){
            if (data.success) {
                $.each(data.data, function(i, product){
                	cateLeftHtml += "<li><div class='goods-img'>";
                    cateLeftHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' target='_blank' title='" + "product.name1'>";
                    cateLeftHtml += "<img width='200' height='200' data-original='${(domainUrlUtil.imageResources)!}" + product.masterImg + "'></a></div>";
                    cateLeftHtml += "<div class='goods-price'><span>特价：</span><strong>￥" + product.mallPcPrice + "</strong></div><div class='goods-name'>";
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
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />