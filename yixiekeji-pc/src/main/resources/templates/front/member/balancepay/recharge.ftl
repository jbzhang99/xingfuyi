<#include "/front/commons/_top.ftl" />
<link rel="stylesheet" type="text/css"
	href="${(domainUrlUtil.staticResources)!}/css/recharge.css" />
<link rel="stylesheet" type="text/css"
	href="${(domainUrlUtil.staticResources)!}/css/order.css">

<div class='w1 header container'>
	<div id='logo'>
		<a href='${(domainUrlUtil.urlResources)!}/index.html'
			target='_blank' class='link1'> <img
			src='${(domainUrlUtil.staticResources)!}/img/logo.jpg'>
		</a>
	</div>
	<div class='stepflex-box fr'>
		<ul>
			<li class="current"><span class="fl">1.填写充值金额</span><i
				class="fl"></i></li>
			<li class=""><span class="fl">2.在线支付</span><i class="fl"></i>
			</li>
			<li class=""><span class="fl">3.充值完成</span><i class="fl"></i></li>
		</ul>
	</div>
</div>

<div class="container">
	<div class="recharge-box">
		<div class="recharge-tit">填写充值金额</div>
		<div class="recharge-content">
			<form method="post"
				action="${(domainUrlUtil.urlResources)!}/member/balance/pay/selectpay.html">
				<div class="grounp-div">
					<span>充值账户：</span><i>${(Session.memberSession.member.name)!}</i>
				</div>
				<div class="grounp-div">
					<span>充值金额：</span><input type="number" class="mony-txt" min="10"
						name="amount"
						oninvalid="setCustomValidity('充值金额不能为空，最小充值10元，最大充值50000元')"
						oninput="setCustomValidity('')" required max="50000" />元
				</div>
				<div class="careful-info">
					<p>请注意：支持银联支付、支付宝支付、微信支付，在线支付成功后，充值金额会在1到5分钟内到账；如果需要提现，请至会员中心申请</p>
					<button type="submit" class="careful-btn">下一步</button>
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
</div>

<#include "/front/commons/_endbig.ftl" />
