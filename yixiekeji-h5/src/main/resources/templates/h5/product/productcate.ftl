<#include "/h5/commons/_head.ftl" />

<body >
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">商品分类</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

	<div class="s-container" style="height:100%;">
	    <div class="clear">
	        <!-- 左侧菜单 -->
			<div class="menunav" style="float:left;">
				<div class="swiper-container" id="swiper-container">
				    <div class="swiper-wrapper" id="swiper-wrapper">
			    	<#if cateList??>
						<#list cateList as cate1>
				        	<div class="swiper-slide text_overflow <#if cate1_index=0>slide-active</#if>">
				        		<#if cate1.name?replace("、","")?length gt 4>
				        			${cate1.name?replace("、","")?substring(0,4)}
				        		<#else>
				        			${cate1.name}
				        		</#if>
				        	</div>
						</#list>
					</#if>
				    </div>
				</div>
			</div>
			<!-- 左侧菜单结束 -->

			<div class=" menu-group" id="menu-group" style="overflow:hidden;">
				<div style="height:100%; position:absolute; padding-bottom:40px; width:76%;" class="overtouch">
					<#if cateList??>
						<#list cateList as cate1>
							    <div class="pad10" <#if cate1_index != 0>style="display:none"</#if>>
									<#list cate1.childs as cate2>
								    	<dl class="menu-group-level mar_top2 menu-group-level-box-shadow">
								    		 <#--<dt>${cate2.name }<hgroup class="menu-title-line"></hgroup></dt>-->
                                             <dt>${cate2.name }</dt>
								    		 <dd class="level-3">
								    		 	<ul class="level-group clearfix">
									    		 	<#list cate2.childs as cate3>
								    		 		<li>
									    		 		<a href="${(domainUrlUtil.urlResources)!}/cate/${cate3.id!0}.html" class="text_overflow ">
									    		 			<#--<img src="${(domainUrlUtil.imageResources)!}/${(cate3.image)!'' }" alt="" />-->
                                                            <img src="https://www.baidu.com/img/bd_logo1.png" style="width: 66px;height: 66px"/>
									    		 			<p class="good-name">${(cate3.name)!'' }</p>
									    		 		</a>
								    		 		</li>
								    		 		</#list>
							    		 		</ul>
								    		 </dd>
								    	</dl>
							    	</#list>
							    </div>
						</#list>
					</#if>
				</div>
			</div>
		</div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
    <script src="${(domainUrlUtil.staticResources)!}/js/index.js"></script>
    <script src="${(domainUrlUtil.staticResources)!}/js/common.js"></script>
	<#include "/h5/commons/_statistic.ftl" />

 <!-- 分类菜单滑动 -->
 <script src="${(domainUrlUtil.staticResources)!}/swiper/swiper-3.2.7.min.js"></script>
 <script>
 $(document).ready(function () {
        $("html,body").css("overflow","hidden");
 		var h = $('body').height()-40;
 		$(".menunav").css('height',h);
 		var n = parseInt(h / 50);
	    var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'vertical',
	    height: h,
	    preventClicks : true,
        slidesPerView : n,
	    paginationClickable: true,
	    loop: false
	   });

	   $("#swiper-wrapper").on("click",".swiper-slide",function(){
          var _this=$(this);
          var index=_this.index();
          _this.addClass('slide-active').siblings().removeClass("slide-active");

          $("#menu-group .pad10").eq(index).css("display","block").siblings().css("display","none");
	   });
  });
 </script>
<script src="${(domainUrlUtil.staticResources)!}/js/xback.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/_pageBackStack.js"></script>
</body>
</html>
