<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/groupon.css">
<body>
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
	  	 		<span class="fa fa-angle-left"></span>
	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">领券中心</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

  <!--S 主体 -->
  <div class="conponBox">
    <div class="c-bantip">
		<h3><a href="${(domainUrlUtil.urlResources)!}/coupon.html" <#if sort?? && sort == 0 > class="selted" </#if> >默认</a></h3>
        <h3><a href="${(domainUrlUtil.urlResources)!}/coupon.html?s=1" <#if sort?? && sort == 1 > class="selted" </#if> >即将过期</a></h3>
        <h3><a href="${(domainUrlUtil.urlResources)!}/coupon.html?s=2" <#if sort?? && sort == 2 > class="selted" </#if> >面值最大</a></h3>
    </div>
    <br>
    
    <#if couponList?? && couponList?size &gt; 0 >
	   	<#list couponList as coupon >
	   		<#if coupon.memberReceivedNum?? && coupon.memberReceivedNum &gt; 0 >
	   		<div class="c-item m-receive">
	   		<#elseif coupon.receivedNum &gt;= coupon.totalLimitNum >
	   		<div class="c-item sold-out">
	   		<#else>
	   		<div class="c-item">
	   		</#if>
			      <!-- <div class="mt">
			        <div class="c-progress c-progress-baidu">
			          <div class="ce-p-bg ce-progress-baidu" style="width:91%"></div>
			          <p>已领取<span>91</span>%</p>
			        </div>
			      </div> -->
			      <div class="mc">
			        <div class="c-con">
			          <div class="c-con-l">
			            <span>&nbsp;</span>
			            <p><i>￥</i>${(coupon.couponValue)!'0'}</p>
			          </div>
			          <div class="c-con-r" >
			            <p class="c-con-txt01">限购[${(coupon.sellerName)!''}]商品</p>
			            <p class="c-con-txt02">满${(coupon.minAmount)!'99999999'}元可用</p>
			          </div>
			        </div>
			        <#if coupon.memberReceivedNum?? && coupon.memberReceivedNum &gt; 0 >
			        	<div class="c-ft">
				          <p class="c-hr"></p>
				          <div class="c-time">${(coupon.useStartTime)?string("yyyy.MM.dd")!''}-${(coupon.useEndTime)?string("yyyy.MM.dd")!''}</div>
				          <a href="${(domainUrlUtil.urlResources)!}/store/${(coupon.sellerId)!0}.html">
					          <span class="c-btn">
					            立即使用<i></i>
					          </span>
				          </a>
				        </div>
				    	<span class="already-receive" style="display: block;"></span>
		    		<#elseif coupon.receivedNum &gt;= coupon.totalLimitNum >
		    			<div class="c-ft">
				          <p class="c-hr"></p>
				          <div class="c-time">${(coupon.useStartTime)?string("yyyy.MM.dd")!''}-${(coupon.useEndTime)?string("yyyy.MM.dd")!''}</div>
				        </div>
		    			<span class="sold-out-signet" style="display: block;"></span>
		    		<#else>
		    			<div class="c-ft">
				          <p class="c-hr"></p>
				          <div class="c-time">${(coupon.useStartTime)?string("yyyy.MM.dd")!''}-${(coupon.useEndTime)?string("yyyy.MM.dd")!''}</div>
				          <a onclick="receiveCoupon(this, ${(coupon.id)!0}, ${(coupon.sellerId)!0})">
					          <span class="c-btn">
					            立即领取<i></i>
					          </span>
				          </a>
				        </div>
		    		</#if>
			      </div>
			    </div>
	   	</#list>
	</#if>
  </div>
  
	<#if couponList?? && couponList?size &lt; total>
		<div id="product_list_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
		<div id="product_list_more_json_no" class="text-center font14 pad-top2" style="display:none;">已展示全部优惠券</div>
	<#else>
		<div id="product_list_more_json_no" class="text-center font14 pad-top2">已展示全部优惠券</div>
	</#if>
	<input type="hidden"  name="list_page" id="list_page" value="1" />
	<input type="hidden"  name="list_total" id="list_total" value="${total!0}" />
  
  <!--E 主体 -->

<!-- footer -->
<#include "/h5/commons/_footer.ftl" />
<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
$(function(){
	$("#product_list_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var pageSize = ${(pageSize)!0};
		var list_total = $("#list_total").val();
		
		var urljson = "${(domainUrlUtil.urlResources)!}/couponjson.html?s=${sort!0}&page=" + list_page;
		
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.rows, function(i, coupon){
                    	if (coupon.memberReceivedNum != null && coupon.memberReceivedNum > 0) {
                    		listJsonHtml += '<div class="c-item m-receive">';
                    	} else if (coupon.receivedNum >= coupon.totalLimitNum) {
                    		listJsonHtml += '<div class="c-item sold-out">';
                    	} else {
                    		listJsonHtml += '<div class="c-item">';
                    	}
                    	listJsonHtml += '  <div class="mc">';
                    	listJsonHtml += '    <div class="c-con">';
                    	listJsonHtml += '      <div class="c-con-l">';
                    	listJsonHtml += '        <span>&nbsp;</span>';
                    	listJsonHtml += '        <p><i>￥</i>' + coupon.couponValue + '</p>';
                    	listJsonHtml += '      </div>';
                    	listJsonHtml += '      <div class="c-con-r" >';
                    	listJsonHtml += '        <p class="c-con-txt01">限购[' + coupon.sellerName + ']商品</p>';
                    	listJsonHtml += '        <p class="c-con-txt02">满' + coupon.minAmount + '元可用</p>';
                    	listJsonHtml += '      </div>';
                    	listJsonHtml += '    </div>';
                    	var startTime = coupon.useStartTime;
                    	startTime = startTime.substring(0,10);
                    	var endTime = coupon.useEndTime;
                    	endTime = endTime.substring(0,10);
                    	
                    	if (coupon.memberReceivedNum != null && coupon.memberReceivedNum > 0) {
                    		listJsonHtml += '    	<div class="c-ft">';
                        	listJsonHtml += '          <p class="c-hr"></p>';
                        	listJsonHtml += '          <div class="c-time">' + startTime + '-' + endTime + '</div>';
                        	listJsonHtml += '          <a href="' + domain + '/store/' + coupon.sellerId + '.html">';
                        	listJsonHtml += '	          <span class="c-btn">';
                        	listJsonHtml += '	            立即使用<i></i>';
                        	listJsonHtml += '	          </span>';
                        	listJsonHtml += '          </a>';
                        	listJsonHtml += '        </div>';
                        	listJsonHtml += '    	<span class="already-receive" style="display: block;"></span>';
                    	} else if (coupon.receivedNum >= coupon.totalLimitNum) {
                    		listJsonHtml += '		<div class="c-ft">';
                        	listJsonHtml += '          <p class="c-hr"></p>';
                        	listJsonHtml += '          <div class="c-time">' + startTime + '-' + endTime + '</div>';
                        	listJsonHtml += '        </div>';
                        	listJsonHtml += '		<span class="sold-out-signet" style="display: block;"></span>';
                    	} else {
                    		listJsonHtml += '		<div class="c-ft">';
                        	listJsonHtml += '          <p class="c-hr"></p>';
                        	listJsonHtml += '          <div class="c-time">' + startTime + '-' + endTime + '</div>';
                        	listJsonHtml += '          <a onclick="receiveCoupon(this, ' + coupon.id + ', ' + coupon.sellerId + ')">';
                        	listJsonHtml += '	          <span class="c-btn">';
                        	listJsonHtml += '	            立即领取<i></i>';
                        	listJsonHtml += '	          </span>';
                        	listJsonHtml += '          </a>';
                        	listJsonHtml += '        </div>';
                    	}
                    	listJsonHtml += '  </div>';
                    	listJsonHtml += '</div>';
                    })
                    $(".conponBox").append(listJsonHtml);
                    if ((parseInt(list_page) * parseInt(pageSize)) < parseInt(list_total)) {
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
	
})
	//领取优惠券
	function receiveCoupon(obj, couponId, sellerId) {
		// 未登录不能领取
		if (!isUserLogin()) {
			// 未登录跳转到登陆页面
			var toUrl = domain + "/coupon.html";
			window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
			return;
		}
	 	$.ajax({
			type:"POST",
			url:domain+"/member/coupon/reveivecoupon.html",
			dataType:"json",
			data:{couponId:couponId},
			success:function(data){
				if (data.success) {
					$.dialog('alert','提示',"领取成功，您可在用户中心查看您的优惠券！",2000);
					
		            // 修改显示内容
		            $(obj).removeAttr("onclick");
		            $(obj).children("span").html("立即使用<i></i>");
		            $(obj).attr("href", domain + "/store/" + sellerId + ".html");
		            var rcvdHtml = '<span class="already-receive" style="display: block;"></span>';
					$(obj).parent(".c-ft").after(rcvdHtml);
				} else {
					$.dialog('alert','提示',data.message,2000);
				}
			}
		});
	}

</script>
</body>
</html>