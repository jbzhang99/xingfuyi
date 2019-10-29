<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet"
	href="${(domainUrlUtil.staticResources)!}/css/recharge.css">
<body>
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
				<a href="javascript:ejsPageBack();">
					<span class="fa fa-angle-left"></span>
				</a>
			</div>
			<div class="flex-2 text-center">余额充值</div>
			<div class="flex-1 text-right" id="fa-bars">
				<span class="fa fa-bars"></span>
			</div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->

	<!--S 主体 -->
	<div class="recharge-box">
		<div class="recharge-content">
			<form method="post"
				action="${(domainUrlUtil.urlResources)!}/member/balance/pay/selectpay.html">
				<div class="grounp-div">
					<span>充值账户：</span><span>${(Session.memberSession.member.name)!}</span>
				</div>
				<div class="grounp-div">
					<span>充值金额：</span><input type="number" class="mony-txt" min="10"
						name="amount"
						oninvalid="setCustomValidity('充值金额不能为空，最小充值10元，最大充值50000元')"
						oninput="setCustomValidity('')" required max="50000">元
				</div>
				<div class="careful-info clearfix">
					<p>请注意：支持银联支付、支付宝支付、微信支付。</p>
					<p>在线支付成功后，充值金额会在1到5分钟内到账；如果需要提现，请至会员中心申请</p>
					<button type="submit" class="btn btn-block btn-login">下一步</button>
				</div>
				<div class="review-tips">
					<p class="fir-p">温馨提示：</p>
					<p>1.充值成功后，余额可能存在延迟现象，一般1到5分钟内到账，如有问题，请咨询客服；</p>
					<p>2.充值金额输入值必须是不小于10且不大于50000的正整数；</p>
					<p>3.充值完成后，您可至会员中心查看充值记录。</p>
				</div>
			</form>
		</div>
	</div>
	<#include "/h5/commons/_footer.ftl" /> <#include
	"/h5/commons/_statistic.ftl" />
</body>
</html>