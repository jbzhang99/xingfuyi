<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/groupon.css">
<body>
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar  bg-color">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
	  	 		<span class="fa fa-angle-left"></span>
	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">多惠部落</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

  <!--S 主体 -->
  <#if recommendList?? && recommendList?size &gt; 0 >
    <div class="mainBox">
      <div class="l_sortbox">
        <img src="${(domainUrlUtil.staticResources)!''}/img/icon_cabbage_header.png" alt="" width="100%">
      </div>
      <div class="i-list-box">
        <ul class="i-list-ul clear">
	        <#list recommendList as recommend>
	          <li>
	            <a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html" class="block">
	              <div class="i-list-img">
	              	<div class="item-price-spread">
	                  <img src="${(domainUrlUtil.staticResources)!''}/img/pricespread.png" alt="" width="60" height="60">
	                  <span class="item-spread">
	                    省<span class="item-spread-money">${(recommend.discount)!""}</span>
	                  </span>
	                </div>	
	              	<img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!}${(recommend.product.masterImg)!''}" width="144" height="144">
	              </div>
	              <div class="product_name"><a href="${(domainUrlUtil.urlResources)!}/product/${(recommend.product.id)!0}.html">${(recommend.product.name1)!""}</a></div>
	              <div class="on-product">
	                <del>市场价￥${(recommend.product.marketPrice)?string("0.00")!"0.00"}</del>
	                <div class="l_np">特惠价￥${(recommend.product.malMobilePrice)?string("0.00")!"0.00"}</div>
	              </div>
	            </a>
	          </li>
	         </#list>
         
         	<div id="recommend_list_more"></div>
        </ul>
      </div>
      
     <#if recommendList?? && recommendList?size==pagesize>
		<div id="recommend_list_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
		<div id="recommend_list_more_json_no" class="text-center font14 pad-top2" style="display:none;">已展示全部商品</div>
	 <#else>
		<div id="recommend_list_more_json_no" class="text-center font14 pad-top2">已展示全部商品</div>
	 </#if>
	 <input type="hidden"  name="list_page" id="list_page" value="1" />
	
    </div>
   </#if>
  <!--E 主体 -->
<!-- footer -->
<#include "/h5/commons/_footer.ftl" />
<#include "/h5/commons/_statistic.ftl" />
<script type="text/javascript">
$(function(){
	$("#recommend_list_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urljson = "${(domainUrlUtil.urlResources)!}/recommenJson.html?page=" + list_page;
		
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.rows, function(i, recommend){
                    	listJsonHtml += "<li>";
                    	listJsonHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + recommend.product.id + ".html' class='block'>";
                    	listJsonHtml += "<div class='i-list-img'>";
                    	listJsonHtml += "<div class='item-price-spread'>";
                    	listJsonHtml += "<img src='${(domainUrlUtil.staticResources)!''}/img/pricespread.png' alt='' width='60' height='60'>";
                    	listJsonHtml += "<span class='item-spread'>省<span class='item-spread-money'>" + recommend.discount + "</span></span></div>";
                    	listJsonHtml += "<img src='${(domainUrlUtil.imageResources)!}" + recommend.product.masterImg+"' width='144' height='144'></div>";
                    	listJsonHtml += "<div class='product_name'><a href='${(domainUrlUtil.urlResources)!}/product/" + recommend.product.id + ".html'>" + recommend.product.name1 + "</a></div>";
                    	listJsonHtml += "<div class='on-product'>";
                    	listJsonHtml += "<del>市场价￥" + parseFloat(recommend.product.marketPrice).toFixed(2) + "</del>";
                    	listJsonHtml += "<div class='l_np'>特惠价￥" + parseFloat(recommend.product.malMobilePrice).toFixed(2) + "</div>";
                    	listJsonHtml += "</div></a></li>";
                    })
                    $("#recommend_list_more").append(listJsonHtml);
                    if ((data.total) == ${(pagesize)!}) {
                        $("#recommend_list_more_json").show();
                        $("#recommend_list_more_json_no").hide();
                    } else {
                        $("#recommend_list_more_json").hide();
                        $("#recommend_list_more_json_no").show();
                    }
                }
            }
        });
	});
})
</script>