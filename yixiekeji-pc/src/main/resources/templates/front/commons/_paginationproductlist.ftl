<#if page??>
	<div class="ejpagination-pages fr">
		<#if page.num != 1>
			<a href="javascript:void(0)" onclick="filterPage(${page.first})"><span class="page-first">首页</span></a> 
			<a href="javascript:void(0)" onclick="filterPage(${page.prev})"><span class="page_next">前一页</span></a>
		<#else>
			<span class="page-first">首页</span>
		</#if>
		
		<#list page.pageNumbers as pageNumber>
			<#if page.num != pageNumber?number>
				<a href="javascript:void(0)" onclick="filterPage(${pageNumber})"><span class="page-wrap">${pageNumber!}</span></a> 
			<#else>
				<span class="current disabled">${pageNumber!}</span>
			</#if>
		</#list>
		
		<#if page.num != 0 && page.pageCount != 0 && page.num != page.pageCount>
			<a href="javascript:void(0)" onclick="filterPage(${page.next})"><span class="page_next">后一页</span></a> 
			<a href="javascript:void(0)" onclick="filterPage(${page.last})"><span class="page_next">末页</span></a>  
		<#else>
			<span class="page_next">末页</span>
		</#if>
		<span class="page_next">共${page.pageCount}页</span>
	</div>
</#if>

<script type="text/javascript">
	function filterPage(page) {
		var urlPath = "${(urlPath)!}";
		var urlPaths = urlPath.split("-");
		var url = "";
		for(var i=0; i<urlPaths.length; i++) {
		    if(i == 2) {
		    	url += page;
		    } else {
		    	url += urlPaths[i];
		    }
			if((i+1) != urlPaths.length) {
				url += "-";
			}
		}
		self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
	}
</script>