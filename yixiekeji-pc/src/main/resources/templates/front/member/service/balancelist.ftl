<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/userindex.css"/>
<style>
.wrapper_main strong {
    font-size: 16px;
    font-family: '\5fae\8f6f\96c5\9ed1';
}
</style>

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
			<a href='javascript:void(0)'>余额</a>
		</span>
	</div>
</div>

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder'>
		<h3>收支明细</h3>
		<div style="margin-bottom:10px;">可用余额：<strong class="ftx01 num">￥${(member.balance)!'0.00'}</strong>  
			&nbsp;&nbsp;
			<#if member?? && member.balancePwd?? && member.balancePwd != ''>
				&nbsp;&nbsp;&nbsp;<a class="btn-5" href="${(domainUrlUtil.urlResources)!}/member/editbalancepassword.html">修改支付密码</a>
			<#else>
				&nbsp;&nbsp;&nbsp;<a class="btn-5" href="${(domainUrlUtil.urlResources)!}/member/setbalancepassword.html">设置支付密码</a>
			</#if>
				&nbsp;&nbsp;&nbsp;<a class="btn-5" href="${(domainUrlUtil.urlResources)!}/member/balance/pay/recharge.html">充值</a>
		</div>
		
		<table class='table_1' id="refushtable" cellspacing="0" cellpadding="0" border='1'>
			<tbody>
				<tr>
					<th>序号</th>
					<th>时间</th>
					<th>存入</th>
					<th>支出</th>
					<th>备注</th>
				</tr>
				<#if infoList??>
					<#list infoList as info>
						<tr>
							<td>
								<a href='' target='_blank' class='ftx-05'>${info_index+1 }</a>
							</td>
							<td>${(info.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
							<td>
							<!-- 1、充值；2、退款；3、消费；4、提款；5、系统添加；6、系统减少 -->
								<#if  info.state??>
					  				<#assign state = info.state/>
					  				<#if state==1||state==2||state==5> ￥${info.money}
					  				</#if>
			  		    		</#if>
							</td>
							<td>
								<#if  info.state??>
					  				<#assign state = info.state/>
					  				<#if state==3||state==4||state==6> ￥${info.money}
					  				</#if>
			  		    		</#if>
							</td>
							<td>
								${info.remark}
							</td>
						</tr>
					</#list>
				</#if>
			</tbody>
		</table>
		
		<!-- 分页 -->
		<#include "/front/commons/_pagination.ftl" />
	</div>
	
</div>

<script type="text/javascript">
	$(function(){
		//控制左侧菜单选中
		$("#balance").addClass("currnet_page");
	});
</script>

<#include "/front/commons/_endbig.ftl" />