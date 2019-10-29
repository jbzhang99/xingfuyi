<!-- 店铺公共头 -->
<#include "/front/seller/_storestop.ftl" />

<!-- 主体 -->
<div class=''>
	<div class="s-logo">
	    <div class="w">
	       <img src="${(domainUrlUtil.imageResources)!}${(sellerIndexInfo.logo)!''}" width="160" height="80">
	       <span>${(sellerInfo.sellerName)!''}</span>
	    </div> 
	</div>
	<div class="s-nav">
		<div class="w clear">
			<ul>
				<li><a href="${(domainUrlUtil.urlResources)!}/store/${(sellerInfo.id)!0}.html">首页</a></li>
				<li><a href="${(domainUrlUtil.urlResources)!}/store/cate-0.html?sellerId=${(sellerInfo.id)!0}">所有商品</a></li>
				<li><a href="${(domainUrlUtil.urlResources)!}/store/info-${(sellerInfo.id)!0}.html">商家介绍</a></li>
			</ul>
			<div class="fr clear s-brand">
				<div class="fl" id="infos-hd">
				  <a href="${(domainUrlUtil.urlResources)!}/store/${(sellerInfo.id)!0}.html" class="s-a-sty">${(sellerInfo.sellerName)!''}</a>
				  <div class="level-shop">
				    <em class="level-cur"></em>
				  </div>
				  <div class="fl clrc81 s_score"><font>${(sellerScoreAvg)!0}</font>分</div>
				  <!-- 滑层 -->
				  <div class="infoslayer">
				  	 <ul class="clear">
				  	 	<li>评分明细</li>
				  	 	<li class="clear">
				  	 		<label class="fl">商品描述：</label>
				  	 		<div class="level-shop">
							   <em class="level-cur"></em>
							</div>
							<div class="fl clrc81 s_score"><font>${(sellerInfo.scoreDescription)!0}</font></div>
				  	 	</li>
				  	 	<li class="clear">
				  	 		<label class="fl">发货速度：</label>
				  	 		<div class="level-shop">
							   <em class="level-cur"></em>
							</div>
							<div class="fl clrc81 s_score"><font>${(sellerInfo.scoreDeliverGoods)!0}</font></div>
				  	 	</li>
				  	 	<li class="clear">
				  	 		<label class="fl">服务态度：</label>
				  	 		<div class="level-shop">
							   <em class="level-cur"></em>
							</div>
							<div class="fl clrc81 s_score"><font>${(sellerInfo.scoreService)!0}</font></div>
				  	 	</li>
				  	 </ul>
				  	 <div class="shopDetail">
				  	 	<p>店铺名称：<font>${(sellerInfo.sellerName)!''}</font></p>
				  	 	<!-- <p>公司名称：<font></font></p> -->
				  	 </div>
				  </div>
				  <!-- 滑层 end-->
				</div>
				<#if collected?? && collected == "true" >
					<a id="collectShop" href="javaScript:;" onclick="disCollectShop(${(sellerInfo.id)!0})" class="fl s_cloct">+&nbsp;取消收藏</a>
				<#else>
					<a id="collectShop" href="javaScript:;" onclick="collectShop(${(sellerInfo.id)!0})" class="fl s_cloct">+&nbsp;收藏店铺</a>
				</#if>
			</div>
		</div>
	</div>
	
	
	<!-- 广告 -->
	<#if bannerList?? && bannerList?size &gt; 0 >
	<div id="banner_tabs" class="flexslider">
        <ul class="slides">
        	<#list bannerList as banner >
	        	<li>
	                <a title="" target="_blank" href="${(banner.linkUrl)!''}">
	                    <img width="1920" height="550" alt="" style="background: url(${(domainUrlUtil.imageResources)!}${(banner.image)!''}) no-repeat center;" src="${(domainUrlUtil.imageResources)!}${(banner.image)!''}">
	                </a>
	            </li>
        	</#list>
        </ul>
        <ul class="flex-direction-nav">
            <li><a class="flex-prev" href="javascript:;">Previous</a></li>
            <li><a class="flex-next" href="javascript:;">Next</a></li>
        </ul>
        <ol id="bannerCtrl" class="flex-control-nav flex-control-paging">
            <#list bannerList as banner >
	        	<li><a>${(banner_index + 1)!1}</a></li>
        	</#list>
        </ol>
    </div>
    </#if>
	<!-- 广告 end -->
	
	<div class='w container'>
		<!--s-->
		<h1 style="text-align:center;">${(sellerInfo.sellerName)!''}</h1>
		<div style="text-align:center;">店铺开通日期：${(sellerInfo.createTime)?string("yyyy-MM-dd HH:mm:ss")!}</div>
		<hr>
		<#noescape>
		${(sellerIndexInfo.detail)!""}
		</#noescape>
		<!--e-->
	</div>
	
	</div>

</div>

<!-- 主体 end -->
<!-- 轮播js -->
<script src="${(domainUrlUtil.staticResources)!}/js/slider.js"></script>
<!-- 星级评分 -->
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.raty.min.js"></script>
<script type="text/javascript">
    $(function() {
    	// 轮播
        var bannerSlider = new Slider($('#banner_tabs'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl'),
            activeControllerCls: 'active'
        });
        $('#banner_tabs .flex-prev').click(function() {
            bannerSlider.prev();
        });
        $('#banner_tabs .flex-next').click(function() {
            bannerSlider.next();
        });
	  	
	  	 // 星级评分
        $(".s_score font").each(function(){
            var n=$(this).text();
            var w=n/5*100;
            $(this).parent().siblings('.level-shop').find(".level-cur").css("width",w+"%");
        });

	  	// 滑层
	  	var timer=null;
	  	$("#infos-hd").hover(function(){
	  		clearTimeout(timer);
	  		timer=setTimeout(function(){
               $(".infoslayer").css("display","block");
	  		},200)
	  	},function(){
	  	   clearTimeout(timer);
           setTimeout(function(){
               $(".infoslayer").css("display","none");
	  		},200)
	  	});
        
        $(".s-dt").click(function(){
        	var i=$(this).find("i");
        	if(i.hasClass('addclass')){
        	  i.removeClass('addclass');
        	  i.text("+").end().siblings().slideUp();
        	}else{
        	  i.addClass('addclass');
        	  i.text("-").end().siblings().slideDown();
        	}
        
        });

        $(".filter-ul li").hover(function(){
          $(this).css("border","solid 1px #ccc");
        },function(){
          $(this).css("border","solid 1px #fff");
        });

    });
    
    /**
	 * 关注店铺
	 */
	function collectShop(id){
		//未登录不能关注店铺
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type:'GET',
			dataType:'json',
			async:false,
			data:{sellerId:id},
			url:domain+'/member/docollectshop.html',
			success:function(data){
				if(data.success){
					$("#collectShop").html("+ 取消收藏");
					$("#collectShop").attr("onclick", "disCollectShop(" + id + ")");
				}else{
					jAlert(data.message);
				}
			}
		});
	}
    
    /**
	 * 取消关注店铺
	 */
	function disCollectShop(id){
		//未登录不能取消关注店铺
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type:'GET',
			dataType:'json',
			async:false,
			data:{sellerId:id},
			url:domain+'/member/cancelcollectshop.html',
			success:function(data){
				if(data.success){
					$("#collectShop").html("+ 收藏店铺");
					$("#collectShop").attr("onclick", "collectShop(" + id + ")");
				}else{
					jAlert(data.message);
				}
			}
		});
	}
</script>
<!-- 登录弹出框 -->
<#include "/front/commons/logindialog.ftl" />
<!--尾部  -->
<#include "/front/commons/_endbig.ftl" />