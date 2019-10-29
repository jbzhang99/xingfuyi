<!-- 单品页咨询列表 全部咨询  -->
<div class='cs-main-target advice-none advice-play' id='number1'>
	<div class='cs-target-item'>
		<#if askList??> <#list askList as askinfo>
		<div class='item clearfix'>
			<div class='pro-left l'>
				<div class='question clearfix'>
					<div class='user l'>
						<i class='cs-spring'></i>
						<p class='nickname'>${(askinfo.userName)!''}</p>
					</div>
					<div class='content l'>
						<p>${(askinfo.askContent)!''}</p>
					</div>
				</div>
				<div class='answer clearfix'>
					<div class='user l'>
						<i class='cs-spring'></i>
						<p class='nickname'>${(askinfo.replyName)!''}</p>
					</div>
					<div class='content l'>
						<p>${(askinfo.replyContent)!''}</p>
					</div>
				</div>
			</div>
			<div class='answer-right r'>
				<div class='answer-time'>
					<span>${(askinfo.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</span>
				</div>
			</div>
		</div>
		</#list> </#if>
	</div>
	<!-- 如果没有内容现在这个提示 -->
	<p class='cs-hasNoConsultation' style='display: none;'>暂无咨询</p>
	<!--  -->
</div>
<div class='com-table-footer'>
	<!-- 分页区域 begin -->
	<div class="pafrtext"><#noescape> ${ pm.pageNavigation} </#noescape></div>
	<!-- 分页区域 end -->
</div>