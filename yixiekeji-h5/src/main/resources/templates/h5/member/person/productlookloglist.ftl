
			<#if lookLogList??>
			<#list lookLogList as lookLog>
				<#if lookLog.product?? >
			
				
				<li>
	                <a href="${(domainUrlUtil.urlResources)!}/product/${(lookLog.product.id)!0}.htmll" class="block">
	                    <div class="i-list-img"><img src="${(domainUrlUtil.imageResources)!}${lookLog.product.masterImg}" width="144"
	                                                 height="144"></div>
	                    <div class="product_name">${(lookLog.product.name1)!''}</div>
	                </a>
	                <!--
	                <div class="list-goods-tip">
	                    <span class="icon-group-1">自营</span>
	                    <span class="icon-group-2">满减</span>
	                    <span class="icon-group-3">放心购</span>
	                </div>-->
	                <div class="i-index-price">${(lookLog.product.malMobilePrice)?string("0.00")!''}</div>
	            </li>
            
				</#if>
  			</#list>
  			</#if>
