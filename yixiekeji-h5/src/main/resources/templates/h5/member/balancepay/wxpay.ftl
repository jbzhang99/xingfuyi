<#include "/h5/commons/_head.ftl" /> 
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<body>
	<#include "/h5/commons/_footer.ftl" /> 
	<#include "/h5/commons/_statistic.ftl" />

	<script type="text/javascript">
		$(function() {
			//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
			if (typeof WeixinJSBridge == "undefined") {
				if (document.addEventListener) {
					document.addEventListener('WeixinJSBridgeReady', jsApiCall,
							false);
				} else if (document.attachEvent) {
					document.attachEvent('WeixinJSBridgeReady', jsApiCall);
					document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
				}
			} else {
				jsApiCall();
			}
		});

		//调用微信JS api 支付
		function jsApiCall() {
			WeixinJSBridge.invoke('getBrandWCPayRequest', {
				"appId" : "${appid}",
				"timeStamp" : "${timeStamp}",
				"nonceStr" : "${nonceStr}",
				"package" : "${package}",
				"signType" : "MD5",
				"paySign" : "${sign}"
			}, function(res) {
				if (res.err_msg == "get_brand_wcpay_request:ok") {
					var uri = domain + "/member/balance/pay/payresult?state=1&res=支付成功";
					location.href = encodeURI(uri);
				} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
					var uri = domain + "/member/balance/pay/payresult?res=用户取消支付";
					location.href = encodeURI(uri);
				} else {
					if (res.err_desc)
						location.href = encodeURI(domain + "/member/balance/pay/payresult?res="+res.err_desc);
					else
						location.href = encodeURI(domain + "/member/balance/pay/payresult?res=支付失败");
				}
			});
		}
	</script>
</body>
</html>

