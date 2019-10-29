<li style="color: #fff;">商城公告： <a class="wave in dropdown-toggle"
	data-toggle="dropdown" title="Help" href="javascript:;"> <i
		class="icon fa fa-envelope"></i> <span class="badge"><#if count??>${count}<#else>0</#if></span>
</a>
	<ul class="pull-right dropdown-menu dropdown-arrow dropdown-messages">
		<#if noticelist?? && noticelist?size &gt; 0>
		<#list noticelist as notice>
		<li>
			<a href="${domainUrlUtil.urlResources}/seller/systemNotice/detail?id=${notice.id}">
					<div class="message">
						<span class="message-sender" style="max-width: 79.5%;">${notice.title}</span> 
						<span class="message-time">${(notice.createTime)?string('yyyy-MM-dd')}</span> 
						<span class="message-body">${notice.describe}</span>
					</div>
			</a>
		</li>
		</#list>
		<#if noticelist?? && count &gt; 5>
		<li>
			<a href="${domainUrlUtil.urlResources}/seller/systemNotice">
				<div class="message">
					<span class="message-time" style="position: relative;float: right;">更多&gt;&gt;</span>
				</div>
			</a>
		</li>
		</#if>
		<#else>
		<li>
			<a href="javascript:;">
				<div class="message" style="text-align: center;">
					没有未读公告
				</div>
			</a>
		</li>
		</#if>
	</ul>
</li>