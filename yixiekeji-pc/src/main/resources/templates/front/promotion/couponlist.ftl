<#include "/front/commons/_headbig.ftl" />

<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/coupons.css">

<br>
<!-- S 主体 -->
<div class="container">
  <div class="voucher-filter">
    <div class="v-sort">
        <a href="${(domainUrlUtil.urlResources)!}/coupon.html" <#if sort?? && sort == 0 > class="selted" </#if> >默认</a>
        <a href="${(domainUrlUtil.urlResources)!}/coupon.html?s=1" <#if sort?? && sort == 1 > class="selted" </#if> >即将过期</a>
        <a href="${(domainUrlUtil.urlResources)!}/coupon.html?s=2" <#if sort?? && sort == 2 > class="selted" </#if> >面值最大</a>
    </div>
  </div>
</div>
<div class="container">
    <div class="cm">
        <div class="voucher-list clearfix">
            <#if couponList?? && couponList?size &gt; 0 >
            	<#list couponList as coupon >
            		<#if coupon.memberReceivedNum?? && coupon.memberReceivedNum &gt; 0 >
            		<div class="voucher-item">
            		<#elseif coupon.receivedNum &gt;= coupon.totalLimitNum >
            		<div class="voucher-item voucher-gray-item">
            		<#else>
            		<div class="voucher-item voucher-op-item">
            		</#if>
		                <div class="v-type">
		                    <div class="v-price clearfix">
		                        <em>￥</em>
		                        <strong class="num">${(coupon.couponValue)!'0'}</strong>
		                        <div class="txt">
		                            <div class="typ-txt">&nbsp;</div>
		                            <div class="limit"><span class="ftx-06">满${(coupon.minAmount)!'99999999'}元可用</span></div>
		                        </div>
		                    </div>
		                    <div class="v-range">
		                        <div class="range-item">
		                            <p title="限购[${(coupon.sellerName)!''}]商品">限购[${(coupon.sellerName)!''}]商品</p>
		                        </div>
		                        <div class="range-item">
		                            
		                        </div>
		                        <div class="range-item">
		                             ${(coupon.useStartTime)?string("yyyy.MM.dd")!''}-${(coupon.useEndTime)?string("yyyy.MM.dd")!''}
		                        </div>
		                    </div>
		                </div>
		                <#if coupon.memberReceivedNum?? && coupon.memberReceivedNum &gt; 0 >
	            		<div class="v-opbtns">
		                    <a href="${(domainUrlUtil.urlResources)!}/store/${(coupon.sellerId)!0}.html" title="立即使用" target="_blank">
		                        <b class="semi-circle"></b>
		                        <span>立即使用</span>
		                    </a>
		                </div>
		                <div class="v-state">
		                    <div class="btn-state btn-overdued"></div>
		                </div>
	            		<#elseif coupon.receivedNum &gt;= coupon.totalLimitNum >
	            		<div class="v-opbtns">
		                    <a href="javascript:void(0);">
		                        <b class="semi-circle"></b>
		                        <span>已领完</span>
		                    </a>
		                </div>
		                <div class="v-state">
		                    <div class="btn-state btn-getend"></div>
		                </div>
	            		<#else>
	            		<div class="v-opbtns">
		                    <a onclick="receiveCoupon(this, ${(coupon.id)!0}, ${(coupon.sellerId)!0})" title="免费领券">
		                        <b class="semi-circle"></b>
		                        <span>立即领取</span>
		                    </a>
		                </div>
	            		</#if>
		            </div>
            	</#list>
            </#if>
        </div>
    </div>

	<#if page?? && page.pageCount gt 1>
		<div class="pagin-box">
			<#include "/front/commons/_pagination.ftl" />
		</div>
	</#if>
	      
    <!-- 弹出框 -->
    <div class="popup-box">
        <div class="voucher-popup">
            <div class="voucher-popup-one"><h3>免费抢券</h3><span class="close">×</span></div>
            <div class="voucher-popup-two">
                <div class="voucher-popup-two-icon">
                    <p class="p-success">领取成功！祝您购物愉快~</p>
                    <p class="p-random">您可在会员中心查看您领取的优惠券</p>
                    <div class="v-btn">
                        <button class="btn-use">立即使用</button>
                        <input type="hidden" id="currSellerId" value="0">
                        <button class="btn-close">关闭</button>
                    </div>
                    
                </div>
            </div>
            <div class="voucher-popup-time">
                <span>6</span>秒后自动关闭
            </div>
        </div>
    </div>
    
</div>
<!-- E 主体 -->

<script type="text/javascript">
    function delayURL() {
		var delay = $(".voucher-popup-time span").html();
		if (delay > 1) {
		    delay--;
		    $(".voucher-popup-time span").html(delay);
		} else {
		    $(".popup-box").hide();
		}
		setTimeout("delayURL()", 1000);
	}
	$(function(){
        // 关闭弹出框
        function cls(btn){
            $(btn).on("click",function(){
                $(".popup-box").hide();
            })
        }
        cls(".voucher-popup-one .close");
        cls(".btn-close");
 
        $(".btn-use").on("click",function(){
        	$(".popup-box").hide();
            var sellerId = $("#currSellerId").val();
            window.open(domain + "/store/" + sellerId + ".html");
        });

	});
	
	// 领取优惠券
	function receiveCoupon(obj, couponId, sellerId) {
		//未登录不能领取
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
	 	$.ajax({
			type:"POST",
			url:domain+"/member/reveivecoupon.html",
			dataType:"json",
			data:{couponId:couponId},
			success:function(data){
				if (data.success) {
					// 当前商家ID
					$("#currSellerId").val(sellerId);
					$(".voucher-popup-time span").html(6);
					$(".popup-box").show();
		            delayURL();
		            
		            // 修改显示内容
		            $(obj).removeAttr("onclick");
		            $(obj).attr("title", "立即使用");
		            $(obj).attr("target", "_blank");
		            $(obj).children("span").html("立即使用");
		            $(obj).attr("href", domain + "/store/" + sellerId + ".html");
		            var rcvdHtml = '<div class="v-state"><div class="btn-state btn-overdued"></div></div>';
					$(obj).parent(".v-opbtns").after(rcvdHtml);
				} else {
					jAlert(data.message);
				}
			}
		});
	}
</script>

<!-- 登录弹出框 -->
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />
