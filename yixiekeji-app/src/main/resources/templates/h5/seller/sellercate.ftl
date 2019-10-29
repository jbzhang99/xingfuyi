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
   	  	 <div class="flex-2 text-center">${(seller.sellerName)!'' }</div>
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
					<#if cateList?? && cateList?size &gt; 0 >
						<#list cateList as cate>
							    <div class="pad10" <#if cate_index != 0>style="display:none"</#if>>
									<#list cate.childs as cate2>
									    	<dl class="menu-group-level pad-top2">
									    		 <dd class="level-3">
								    		 		<a href="${(domainUrlUtil.urlResources)!}/store/cate/${cate2.id!0}.html?sellerId=${(seller.id)! }" class="text_overflow ">${(cate2.name)!'' }</a>
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