<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
		
<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'>
			<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
		</strong>
		<span>
			&nbsp;>&nbsp;
			<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技</a>
		</span>
		<span>
			&nbsp;>&nbsp;
			<a href='javascript:void(0)'>提现申请</a>
		</span>
	</div>
</div>

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />

	<!-- 右侧主要内容 -->
	<div class='wrapper_main'>
		<h3>提现申请</h3>
		
		<form class='myinfo' id='form'>
			<input type="hidden" id="balance" value="${balance!'0.00'}">
			<dl class='dl_col1'>
				<dt>
					<label>银行名称：</label>
				</dt>
				<dd class='p-item'>
					${(info.bank)!'' }
				</dd>
				<dt>
					<label>银行卡号：</label>
				</dt>
				<dd class='p-item'>
					${(info.bankCode)!'' }
				</dd>
				<dt>
					<label>申请提现金额：</label>
				</dt>
				<dd class='p-item'>
					￥${(info.money)?string("0.00")!'' }
				</dd>
				<dt>
					<label>状态：</label>
				</dt>
				<dd class='p-item'>
					<@cont.codetext value="${(info.state)!0}" codeDiv="MEMBER_DRAWING_STATE"/>
				</dd>
				
				<#if info.state?? && info.state==4>
					<dt>
						<label>失败原因：</label>
					</dt>
					<dd class='p-item'>
							${(info.failReason)!'' }
					</dd>
				</#if>
			</dl>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		//控制左侧菜单选中
		$("#withdrawdeposit").addClass("currnet_page");
	});
</script>
	
<#include "/front/commons/_endbig.ftl" />