
<div class=''>
	<#if cateList??> 
		<#list cateList as cate1>
			<div class='list-cashcade forel'>
				<span>
					<h3>
						${cate1.name}
					</h3> <s></s>
				</span>
				<div class='i-mc' style='top: 4px;'>
					<div class='subitem'>
						<#list cate1.childs as cate2>
						<dl class='fore1'>
							<dt>
								<a href='${(domainUrlUtil.urlResources)!}/list/${cate2.id!0}.html' 
									target="_blank">${cate2.name }</a>
							</dt>
							<dd>
								<#list cate2.childs as cate3> 
									<em>
										<a href='${(domainUrlUtil.urlResources)!}/cate/${cate3.id!0}.html' 
											target="_blank">${cate3.name }</a>
									</em>
								</#list>
							</dd>
						</dl>
						</#list>
					</div>
				</div>
			</div>
		</#list> 
	</#if>
</div>


<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/list.js'></script>

