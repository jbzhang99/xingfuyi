<#include "/front/commons/_headbig.ftl" />

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
<div class='wrapper_main myorder'>
	<h3>提现申请</h3>
	
	<h3>
		<a  class='add-address ftx-05' href="${(domainUrlUtil.urlResources)!}/member/withdrawapply.html">申请提现</a>
	</h3>
	<table class='table_1' id="refushtable" cellspacing="0" cellpadding="0" border='1'>
		<tbody>
			<tr>
				<th>序号</th>
				<th>申请时间</th>
				<th>提现金额</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<#if infoList??>
				<#list infoList as info>
					<tr>
						<td>
							${info_index+1 }
						</td>
						<td>${(info.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
						<td>￥${(info.money)?string("0.00")!''}</td>
						<td>
							<#if  info.state??>
								<@cont.codetext value="${(info.state)!0}" codeDiv="MEMBER_DRAWING_STATE"/>
		  		    		</#if>
						</td>
						<td>
							<a href='${(domainUrlUtil.urlResources)!}/member/withdrawdetail.html?infoId=${(info.id)!''}' class='acolorblue'>查看</a>
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
		$("#withdrawdeposit").addClass("currnet_page");
		
	});
</script>
<#include "/front/commons/_endbig.ftl" />