<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
    <meta name="screen-orientation" content="portrait"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="full-screen" content="yes">
    <meta name="msapplication-tap-highlight" content="no">
    <meta name="msapplication-tap-highlight" content="no">
    <title>微信支付</title>
   <#-- <!-- <link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/common/index.css"/>
    <link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/newindex.css"> -->
    <!--找回密码和注册页面公用一套css-->
    <#--<!-- <link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/zx_cashier.css"/>
    <script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/common/Mobile_adaptation.js"></script> -->
</head>
<body>
<div class="all_box flow">
    <div id="wrapper">
        <div id="scroller">
            <header id="header">
                <!-- <div class="phone_top"></div> -->
                <div class="product_top retainbb">
                    <a href="javascript:ejsPageBack();"><em class="back"></em></a>
                    <h5>微信支付 </h5>
                </div>
            </header>
            <section class="main_box" style="margin-top:0.88rem;">
                <div class="order_detail retainbb">
                    <b>订单号:</b>
                    <span>
                    <#if orderSn?? >
						${orderSn}
					</#if>
                    </span>
                    <p>请您在提交订单后<em>24小时</em>内完成支付，否则订单会自动取消</p>
                </div>
                <div class="line retainbb"></div>
                <#-- <!-- <div class="order_price retainbb of flex" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                    <span class="fl" style="width:auto;margin-right:0.14rem;">商品名称:</span>
                    <b class="flex-1" style="font-size:0.3rem;color:#666;">${(productName)!'' }</b>
                </div> -->
                 <div class="order_price retainbb of" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                    <span class="fl" style="width:auto;margin-right:0.14rem;">订单金额:</span>
                    <b class="flex-1" style="font-size:0.3rem;color:#666;">¥${(payAmount)!'' }</b>
                </div>
                <div style="padding:0 0.3rem;margin-top:0.2rem;">
                	<a href="${(mwebUrl)!''}" style="display: block;height: 42px;line-height: 42px; 
                	   color: #FFFFFF;text-align: center;border-radius: 5px;
                	   background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #43C750), color-stop(1, #31AB40)); 
                	   border: 1px solid #2E993C;box-shadow: 0 1px 0 0 #69D273 inset;">立即支付</a>
                </div>
            </section>
        </div>
    </div>
</div>
<#include "/h5/commons/_statistic.ftl" />
<script>
// 必须在引用_footer.ftl或_pageBackStack.ftl前定义
var intoBackStack = false;
</script>
<#include "/h5/commons/_footer.ftl" />
</body>
</html>