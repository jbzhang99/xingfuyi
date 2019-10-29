 <#include "/front/commons/_headbig.ftl" />

<link rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/article.css'>

<script>
	$(function() {
		$(".wp1200 .container").removeClass("w");
		$(".wp1200 .footer").removeClass("w");
		$(".wp1200 .wraper").removeClass("w");
	});
</script>

<div class='container'>
	<div class='subheader pb10'>
		<strong> <a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
		</strong> <span>></span> <strong> <a href='${(domainUrlUtil.urlResources)!}/news/type_${currType.id!}.html'>${currType.name!""}</a>
		</strong> <span>><span class='arrow'>${news.title!}</span>
		</span>
	</div>
</div>
<div class='container'>
	<div class='article-left'>
		<div class='nch-module nch-module-style01'>
			<div class='title'>
				<h3>文章分类</h3>
			</div>
			<div class='article-content'>
				<ul class='nch-sidebar-article-class'>
				<#if newsTypes??>
					<#list newsTypes as newstype>
						<li>
							<a href='${(domainUrlUtil.urlResources)!}/news/type_${newstype.id!}.html'>
								${newstype.name!} 
							</a>
						</li>
					</#list>
				</#if>
				</ul>
			</div>
		</div>
		<div class='nch-module'>
			<div class='title'>
				<h3>最新文章</h3>
			</div>
			<div class='article-content'>
				<ul class='nch-sidebar-article-list'>
				 <#if lastedNews??>
					<#list lastedNews as ln>
					<li><i></i> <a
						href='${(domainUrlUtil.urlResources)!}/news/${ln.id!}.html'>
							${ln.title!} </a></li> 
					</#list>
				</#if>
				</ul>
			</div>
		</div>
	</div>
	
	<div class='article-right'>
		<div class='nch-article-con'>
			<h1>${news.title!}</h1>
			<h2>${news.createTime?string("yyyy-MM-dd HH:mm:ss")}</h2>
			<div class='default' id="cont_">
				<#noescape>
				${news.content}
				</#noescape>
			</div>
		</div>
	</div>
	
</div>
<#include "/front/commons/_endbig.ftl" />
