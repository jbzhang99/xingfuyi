<#include "/front/commons/_headbig.ftl" />

<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/twoLevelCate.css">

		<div class="boxbg">
			<div class='container'>
				<div class='breadcrumb' style="margin-bottom: 0px;">
					<strong class='business-strong'>
						<a href='${(domainUrlUtil.urlResources)!}' style="letter-spacing: 2px;font-size: 14px;font-weight: 400;line-height: 30px;">首页></a>${(productCate.name)!""}
					</strong>
				</div>
				<div class="main_left">
					<div class="category">
						<div class="category_name">
							<span>
								<div>${(productCate.name)!""}</div>
							</span>
						</div>
						<div class="category_list" style="margin-top: 3px;">
							<ul>
								<#if childCates?? && childCates?size &gt; 0 >
								<#list childCates as cate >
									<li>
										<a href="${(domainUrlUtil.urlResources)!}/cate/${cate.id!0}.html">${(cate.name)!""}</a>
									</li>
								</#list>
								</#if>
							</ul>
						</div>
					</div>
				</div>
				<div class="main_right">
					<div class="choose_box_bottom clearfix">
						<!-- <div class="fl"><div class="choose_title">排序：</div></div> -->
						<div class="fl">
							<!-- 0-默认，1-价格升序，2-价格降序，3-销量降序，4-评价降序 -->
							<div class="choose_item" style="width:auto;">
								<a href="${(domainUrlUtil.urlResources)!}/list/${(productCate.id)!0}-1-0.html" <#if sort?? && sort == 0> class="selected" </#if>>综合排序</a>
								<#if sort?? && sort == 1>
								<a href="${(domainUrlUtil.urlResources)!}/list/${(productCate.id)!0}-1-2.html" class="selected" >价格 ↑</a>
								<#elseif sort?? && sort == 2>
								<a href="${(domainUrlUtil.urlResources)!}/list/${(productCate.id)!0}-1-1.html" class="selected" >价格 ↓</a>
								<#else>
								<a href="${(domainUrlUtil.urlResources)!}/list/${(productCate.id)!0}-1-1.html" >价格</a>
								</#if>
								<a href="${(domainUrlUtil.urlResources)!}/list/${(productCate.id)!0}-1-3.html" <#if sort?? && sort == 3> class="selected" </#if>>销量</a>
								<a href="${(domainUrlUtil.urlResources)!}/list/${(productCate.id)!0}-1-4.html" <#if sort?? && sort == 4> class="selected" </#if>>好评</a>
							</div>
						</div>
						<div class="fr">此类商品共<span class="colrpd">${(page.rowCount)!0}</span>个</div>
					</div>
					<!-- S 商品列表 -->
					<div class="goods_content">
						<ul class="clearfix">
							<#if productList?? && productList?size &gt; 0 >
							<#list productList as product >
							<li>
								<div class="ac_list fl">
									<div class="tab_content">
										<div class="list_good_img">
											<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" target="_blank"><img class="lazy" data-original="${(domainUrlUtil.imageResources)!}${(product.masterImg)!''}" alt=""></a>
										</div>
										<div class="list_good_des">
											<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" target="_blank" title="${(product.name1)!''}">${(product.name1)!""}</a>
										</div>
										<div class="list_good_price clearfix">
											<div class="list_good_price_new list">
												<span>￥${(product.mallPcPrice)?string("0.00")!""}</span>
												<del>￥${(product.marketPrice)?string("0.00")!""}</del>
												<em class="fr" style="color:#E13335;">${(product.commentsNumber)!0}条评论</em>
											</div>
										</div>
										<div class="list_good_addbox clearfix">
											<!-- <div class="list_good_sumbox fl">
												<input value="1" type="text" class="list_good_num ellipsis fl">
												<a href="javascript:void(0)" class="list_good_upnum fr ">+</a>
												<a href="javascript:void(0)" class="list_good_downnum fr">-</a>
											</div> -->
											<a href="javascript:void(0)" onclick="addCart('${(product.id)!''}','${(product.sellerId)!''}','0')" class="list_good_addcart fl addcar"><span>加入购物车</span></a>
											<a href="${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html" target="_blank" class="list_good_addfav fr myfav_38340">查看详情</a>
										</div>
									</div>
								</div>
							</li>
							</#list>
							</#if>
						</ul>
					</div>
					<div class='page'>
						<!-- 分页 -->
						<#include "/front/commons/_paginationcateprdlist.ftl" />
					</div>
					<!-- E 商品列表 -->
				</div>
			</div>
		</div>



<#include "/front/commons/logindialog.ftl" />		
<#include "/front/commons/_endbig.ftl" />

<script type="text/javascript">
$(function(){
	// 商品列表
	$(".goods_content ul li").on("mouseover",function(){
		$(this).find(".ac_list").css({"borderColor":"#E13335"})
	});
	$(".goods_content ul li").on("mouseleave",function(){
		$(this).find(".ac_list").css({"borderColor":"#fff"})
	});

	// 左侧筛选
	$(".category_list li").on("click",function(){
		$(this).css({"background":"#E13335"}).find("a").css({"color":"#fff"}).end().siblings("li").css({"background":"#fff"}).find("a").css({"color":"#666"});
		$(this).parent().parent().siblings(".category_list").find("li").css({"background":"#fff"}).find("a").css({"color":"#666"});
	});

})

	function addCart(productId,sellerId,type) {
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