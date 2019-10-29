<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/groupon.css">
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/list.css">

<body class="bgf2">
  <!-- 头部 -->
  <header id="header">
    <div class="flex flex-align-center head-bar bg-color">
      <div class="flex-1 text-left">
        <a href="javascript:ejsPageBack();">
  	 		<span class="fa fa-angle-left"></span>
  	 	</a>
      </div>
      <div class="flex-2 text-center fa-lg">积分商城</div>
      <div class="flex-1 text-right" id="fa-bars">
        <span class="fa fa-bars col-f"></span>
      </div>
    </div>
    <#include "/h5/commons/_hidden_menu.ftl" />
  </header>
  <!-- 头部 end-->

  <!-- S 轮播 -->
  <div>
      <div class="swiper-container" id="i-swiper-container">
          <div class="swiper-wrapper">
          	  <#if actIntegralBanners??>
	      		  <#list actIntegralBanners as actIntegralBanner>
	              	  <div class="swiper-slide"><a href="${(domainUrlUtil.urlResources)!}/${(actIntegralBanner.linkUrl)!}" class="block">
	              	  	<img src="${(domainUrlUtil.imageResources)!}${(actIntegralBanner.image)!}"></a>
              	  	  </div>
	              </#list>
      		  </#if>
          </div>
          <div class="swiper-pagination"></div>
      </div>
  </div>
  <!-- E 轮播 -->

  <!-- S 会员中心 -->
  <div class="member-center-box">
    <div class="member-center-info clearfix">
      <div class="fl-box">
        <#if member??>
        	<div class="yes-loginvip">
	          <div class="vip-name" title="123****@qq.com">${(member.name)!}</div>
	          <div class="mem-grade"><@cont.codetext value="${(member.grade)!0}" codeDiv="MEMBER_GRADE"/></div>
	          <div class="mem-grade">
	            可用积分：${(member.integral)!0} 分
	          </div>
	        </div>
        <#else>
        	<a href="${(domainUrlUtil.urlResources)!}/login.html" class="no-loginvip">登录</a>
        </#if>
        
      </div>
      <div class="fr-box">
        <p class="p-integral">签到送积分</p>
        <p class="p-one">每天限领一次</p>
        <#if isSign?? && isSign == 0>
        	<a href="javascript:;" id="memberSignBefore" class="vip-signbtn">今日可领取<span>${(memberRule.sign)!0}</span>积分</a>
        	<a class="vip-signbtn" id="memberSignAfter" style="display:none;background-color:gray;border-color:gray;" >今日已领取<span>${(memberRule.sign)!0}</span>积分</a>
        <#else>
        	<a class="vip-signbtn" style="background-color:gray;border-color:gray;">今日已领取<span>${(memberRule.sign)!0}</span>积分</a>
        </#if>
      </div>
    </div>
  </div>
  <!-- E 会员中心 -->
  
  <!-- S 商品 -->
  <div  style="padding:0 5px;" class="group_section">
  		<nav>
		  <div class="flex flex-align-center nav-2-bar" id="nav-2-bar">
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=0" <#if sort?? && sort == 0>class="btn-sort"</#if>>人气</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=1" <#if sort?? && sort == 1>class="btn-sort"</#if>>最新</a></div>
			<div class="flex-1"><a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=2" <#if sort?? && sort == 2>class="btn-sort"</#if>>销量</a></div>
			<div class="flex-1">
				<#if sort??>
					<#if sort == 0 || sort == 1 || sort == 2>
						<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=3">价格</a>
					<#else>
						<#if sort == 3>
							<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=4" class="btn-sort">价格<span class="fa fa-long-arrow-up"></span></a>
						</#if>
						<#if sort == 4>
							<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=3" class="btn-sort">价格<span class="fa fa-long-arrow-down"></span></a>
						</#if>
					</#if>
				</#if>
			</div>
			<div class="flex-1"><a href="javascript:;" id="a-list-filter">筛选&nbsp;<span class="fa fa-caret-right"></span></a></div>
		  </div>
		</nav>
		
    <div class="i-list-box mar-bt">
      <ul class="i-list-ul clear">
	      <#if actIntegrals??>
				<#list actIntegrals as actIntegral>
					<dl class="flex list-dl">
						<dt class="grabdt"><a><img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!}${(actIntegral.image)!}"></a></dt>
						<dd class="padl flex-2  pos_relative">
							<div class="product_name">${(actIntegral.productName)!}</div>
							<div class="product-desript">
							   <p class="clr53">限<#if actIntegral.gradeValue == 1>普通会员<#elseif actIntegral.gradeValue == 2>铜牌会员<#elseif actIntegral.gradeValue == 3>银牌会员<#elseif actIntegral.gradeValue == 4>金牌会员<#elseif actIntegral.gradeValue == 5>钻石会员</#if></p>
							   <p class="clr53">
							   <#if actIntegral.isWithMoney == 0>
		                           <font>${(actIntegral.price)!}分</font>
		                       <#elseif actIntegral.isWithMoney == 1>	
		                           <font>${(actIntegral.price)!}分+￥${(actIntegral.priceMoney)!}</font>
		                       </#if>
							   </p>
							</div>
							<div class="flex flex-pack-justify mar_top">
								<div class="integral_buy_originally">原价:￥${(actIntegral.marketPrice)!}</div>
								<div>已兑换:<font class="clr53">${actIntegral.virtualSaleNum + actIntegral.saleNum}</font>人</div>
							</div>
							<a class="Grab_btn" href="${(domainUrlUtil.urlResources)!}/jifen/${(actIntegral.id)}.html">
								兑换
							</a>
						</dd>
					</dl>
				</#list>
			</#if>
	      
	      <div id="product_list_more"></div>
      </ul>
    </div>

	<#if actIntegrals?? && actIntegrals?size==pagesize>
		<div id="product_list_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
		<div id="product_list_more_json_no" class="text-center font14 pad-top2" style="display:none;">已展示全部商品</div>
	<#else>
		<div id="product_list_more_json_no" class="text-center font14 pad-top2">已展示全部商品</div>
	</#if>
	<input type="hidden"  name="list_page" id="list_page" value="1" />
  </div>
  <!-- E 商品 -->
  
 <!-- 筛选层 -->
  <div class="slidebar slidebar-width" id="slidebar" style="display: none;">
    <div class="flex flex-align-center head-bar">
           <div class="flex-1 text-left" id="sliderarrow-m"><span class="fa fa-angle-left"></span></div>
           <div class="flex-2 text-center">筛选</div>
           <div class="flex-1 text-right"><a href="${(domainUrlUtil.urlResources)!}/jifen.html">清除</a></div>
       </div>
       <div class="s-container">
           <div class="menubox menubox-m" >
           	   <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=0&grade=${(grade)!0}&sort=${(sort)!0}">
	               <h2 class="flex flex-pack-justify">
	                  <div>全部分类</div>
	               </h2>
               </a>
               <ul class="menu-2-ul menu-2-ul-m">
               <#if actIntegralTypes??>
          		<#list actIntegralTypes as actIntegralType>
          			<a class="a-ali" href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(actIntegralType.id)}">
	                  <li class="flex flex-pack-justify">
	                      <div>${(actIntegralType.name)!}</div>
	                      <div><span class="fa fa-check" <#if type==actIntegralType.id>style="display: inline-block;"</#if>></span></div>
	                  </li>
                  	</a>
          		</#list>
	          </#if>
               </ul>
           </div>
           <div class="menubox menubox-m">
           		<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=0&sort=${(sort)!0}">
               <h2 class="flex flex-pack-justify">
                   <div>会员等级</div>
               </h2>
               </a>
               <ul class="menu-2-ul menu-2-ul-m">
	           	  <a class="a-ali" href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=1&sort=${(sort)!0}">
                  <li class="flex flex-pack-justify">
                      <div>普通会员</div>
                      <div><span class="fa fa-check" <#if grade==1>style="display: inline-block;"</#if>></span></div>
                  </li>
                  </a>
                  	<a class="a-ali" href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=2&sort=${(sort)!0}">
                  <li class="flex flex-pack-justify">
                      <div>铜牌会员</div>
                      <div><span class="fa fa-check" <#if grade==2>style="display: inline-block;"</#if>></span></div>
                  </li>
                     </a>
                  	<a class="a-ali" href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=3&sort=${(sort)!0}">
                  <li class="flex flex-pack-justify">
                      <div>银牌会员</div>
                      <div><span class="fa fa-check" <#if grade==3>style="display: inline-block;"</#if>></span></div>
                  </li>
                  	</a>
              		<a class="a-ali" href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=4&sort=${(sort)!0}">
                  <li class="flex flex-pack-justify">
                      <div>金牌会员</div>
                      <div><span class="fa fa-check" <#if grade==4>style="display: inline-block;"</#if>></span></div>
                  </li>
                    </a>
                  	<a class="a-ali" href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=5&sort=${(sort)!0}">
                  <li class="flex flex-pack-justify">
                      <div>钻石会员</div>
                      <div><span class="fa fa-check" <#if grade==5>style="display: inline-block;"</#if>></span></div>
                  </li>
                    </a>
               </ul>
           </div>
       </div>
  </div>
  <!-- 筛选层  end -->
  
<#include "/h5/commons/_footer.ftl" />
<#include "/h5/commons/_statistic.ftl" />
<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.hDialog.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/groupon.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/index.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/echo.min.js"></script>

<script type="text/javascript">
echo.init();

$(function(){
	$("#product_list_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urljson = "${(domainUrlUtil.urlResources)!}/jifenJson.html?type=${(type)!0}&sort=${(sort)!0}&grade=${(grade)!0}&page=" + list_page;
		
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.rows, function(i, actIntegral){
                    	listJsonHtml += "<dl class='flex list-dl'>";
                    	listJsonHtml += "<dt class='grabdt'><a><img src='${(domainUrlUtil.imageResources)!}" + actIntegral.image +"'></a></dt>";
                    	listJsonHtml += "<dd class='padl flex-2  pos_relative'>";
                    	listJsonHtml += "<div class='product_name'>" + actIntegral.productName + "</div>";
                    	listJsonHtml += "<div class='product-desript'>";
                    	listJsonHtml += "<p class='clr53'>限";
                    	if(actIntegral.gradeValue == 1) {
                    		listJsonHtml += "普通会员";
                    	} else if(actIntegral.gradeValue == 2) {
                    		listJsonHtml += "铜牌会员";
                    	} else if(actIntegral.gradeValue == 3) {
                    		listJsonHtml += "银牌会员";
                    	} else if(actIntegral.gradeValue == 4) {
                    		listJsonHtml += "金牌会员";
                    	} else if(actIntegral.gradeValue == 5) {
                    		listJsonHtml += "钻石会员";
                    	}
                    	listJsonHtml += "</p></div><p class='clr53'><font>" + actIntegral.price + "分</font></p></div>";
                    	listJsonHtml += "<div class='flex flex-pack-justify mar_top'>";
                    	listJsonHtml += "<div class='integral_buy_originally'>原价:" + actIntegral.price + "</div>";
                    	listJsonHtml += "<div>已兑换:<font class='clr53'>" + (actIntegral.virtualSaleNum + actIntegral.saleNum) + "</font>人</div>";
                    	listJsonHtml += "</div><a class='Grab_btn' href='${(domainUrlUtil.urlResources)!}/jifen/" + actIntegral.id + ".html'>";
                    	listJsonHtml += "兑换";
                    	listJsonHtml += "</a></dd></dl>";
                    })
                    $("#product_list_more").append(listJsonHtml);
                    if ((data.total) == ${(pagesize)!}) {
                        $("#product_list_more_json").show();
                        $("#product_list_more_json_no").hide();
                    } else {
                        $("#product_list_more_json").hide();
                        $("#product_list_more_json_no").show();
                    }
                }
            }
        });
	});
	
	$("#memberSignBefore").click(function(){
		//未登录不能领取积分
		if (!isUserLogin()) {
			// 未登录跳转到登陆页面
			var toUrl = domain + "/jifen.html";
			window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
			return;
		}
		
   		$.ajax({
			type:"POST",
			url:domain+"/member/app-sign.html",
			dataType:"json",
			async : false,
			success:function(data){
				if(data.success){
					$.dialog('alert','提示','领取成功，明天记得来签到哟！',2000);
					$("#memberSignBefore").css("display","none");
   					$("#memberSignAfter").css("display","block");
				}else{
					$.dialog('alert','提示',data.message,2000);
				}
			},
			error:function(){
				$.dialog('alert','提示','异常，请重试！',2000);
			}
		});
    });
    
	// 点击筛选
    $("#a-list-filter").on("click",function(){
          if($("#slidebar").hasClass("show")==false){
              $("#header").addClass('pullwidth');
              $("#container").addClass('pullwidth');
              $("#push_msk").css("display","block");
              $("#slidebar").addClass("show")
              $("html,body").css({"overflow":"hidden"});
                $(window).bind('touchmove', function(e) {
              e.preventDefault();
              e.stopImmediatePropagation();
            });
           }else{
                $("html,body").css({"overflow":"visible"});
                $(window).unbind('touchmove');
           }
        });
        $("#slidercomfirm-m").on("click",function(){
              $("#header").removeClass('pullwidth');
              $("#container").removeClass('pullwidth');
              $("#push_msk").css("display","none");
              $("#slidebar").removeClass("show")
              $("html,body").css({"overflow":"visible"});
              $(window).unbind('touchmove');
        });
        $("#sliderarrow-m").on("click",function(){
              $("#header").removeClass('pullwidth');
              $("#container").removeClass('pullwidth');
              $("#push_msk").css("display","none");
              $("#slidebar").removeClass("show")
              $("html,body").css({"overflow":"visible"});
              $(window).unbind('touchmove');
        });
        $(".menu-2-ul-m li").on("click",function(){
         var _this=$(this);
           _this.addClass("clr53").find(".fa-check").css("display","inline-block");
           _this.siblings().removeClass("clr53").find(".fa-check").css("display","none");
      });
})
 </script>
  <!-- 轮播 -->
  <script src="${(domainUrlUtil.staticResources)!}/swiper/swiper-3.2.7.min.js"></script>
  <script>
      $(document).ready(function () {
         var mySwiper = new Swiper ('.swiper-container', {
           autoplay: 3000,
           loop: true,
           pagination: '.swiper-pagination'
        });
        
         $('#fa-bars').on("click",function(){
	      $("#nav").toggleClass('addnav');
		});
        
      });
  </script>

</body>
</html>