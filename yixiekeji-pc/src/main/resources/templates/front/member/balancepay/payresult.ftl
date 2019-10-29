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
			<li class=""><span class="fl">1.填写充值金额</span><i
				class="fl"></i></li>
			<li class=""><span class="fl">2.在线支付</span><i class="fl"></i></li>
			<li class="current"><span class="fl">3.充值完成</span><i class="fl lasti"></i></li>
		</ul>
	</div>
</div>

<div class="container">
	<div class="recharge-box">
		<div class="recharge-tit">充值结果</div>
		<div class="clearfix" style="padding: 90px 10px;font-size: 18px;">
			<div class="fl sd-fl">
				<#if success?? && success == true>
				<strong>${(info)!'充值成功！'}请到<a style="padding: 5px;color: #ff8000;"
					href='${(domainUrlUtil.urlResources)!}/member/balance.html'>会员中心</a>查看详情。
				</strong>
				<#else>
				<strong>
					${(info)!'充值失败'} ，请稍后再试
				</strong>
				</#if>
			</div>
			<span style="font-size: 12px;color: red;margin-left: 30px;"><i id="time">5</i>秒后跳转</span>
		</div>
	</div>
</div>

<script>
	function dointerval(){
		var time_ = 5;
		setInterval(function(){
			if(time_ == 0){
				location.href="${(domainUrlUtil.urlResources)!}/member/balance.html";
			}
			$("#time").html(time_);
			time_ --;
		}, 1000);
	}

	$(function(){
		dointerval();
	});
</script>
<#include "/front/commons/_endbig.ftl" />
