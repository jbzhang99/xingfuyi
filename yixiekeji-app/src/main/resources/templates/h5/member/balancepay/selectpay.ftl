<#include "/h5/commons/_head.ftl" /> 
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<body class="bgf2">
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
				<a href="javascript:ejsPageBack();">
					<span class="fa fa-angle-left"></span>
				</a>
			</div>
			<div class="flex-2 text-center">收银台</div>
			<div class="flex-1 text-right" id="fa-bars">
				<span class="fa fa-bars"></span>
			</div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->

	<@form.form
	action="${(domainUrlUtil.urlResources)!}/member/balance/pay/pay.html"
	id="payForm" name="payForm" method="POST">
	<input type="hidden" name="amount" value="${amount!}" />

	<div>
		<div class="p-o-infor">
			<p class="font12 pad-top">您正在充值余额，请尽快支付</p>
		</div>


		<ul class="p-o-method">
			<li class="flex flex-pack-justify font16">
				<div>选择支付方式</div>
				<div class="clr53">${amount!}元</div>
			</li>
			<li class="flex flex-pack-justify">
				<div>支付宝</div>
				<div>
					<input id="optionsRadios" name="optionsRadios" type="radio"
						value="h5alipay" class="p-radio" checked="checked">
				</div>
			</li>
			<li class="flex flex-pack-justify">
				<div>中国银联</div>
				<div>
					<input id="optionsRadios" name="optionsRadios" type="radio"
						value="h5unionpay" class="p-radio">
				</div>
			</li>
			<li class="flex flex-pack-justify">
				<div>微信支付</div>
				<div>
					<input id="wxpay" name="optionsRadios" type="radio" value="h5weixin"
						class="p-radio">
				</div>
			</li>

		</ul>
		<div class="pad10">
			<button type="submit" class="btn btn-block btn-login" id='PayButtom'>立即支付</button>
		</div>
	</div>
	</@form.form>
	<!-- 主体结束 -->

	<#include "/h5/commons/_footer.ftl" /> <#include
	"/h5/commons/_statistic.ftl" />

</body>
</html>