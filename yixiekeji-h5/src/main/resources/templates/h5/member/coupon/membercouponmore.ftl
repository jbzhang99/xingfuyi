
	<#if couponUsers?? && couponUsers?size &gt; 0>
		<#list couponUsers as couponUser>
	        <div class="pepper-w">
	            <div class="pepper">
	                <div class="pepper-l">
	                    <p class="pepper-l-num" <#if couponUser.timeout || couponUser.isuse>style="color:gray"</#if> >
	                        <i>¥</i><span>${(couponUser.couponValue)!0}</span><i>.00</i><br>
	                        (满${(couponUser.minAmount)!}元可用)
	                    </p>
	                </div>
	                <div class="pepper-r">
	                    <span>满${(couponUser.minAmount)!}减${(couponUser.couponValue)!0}</span>
	                    <div class="pepper-l-con">适用商家：${(couponUser.sellerName)!}</div>
	                    <div>
	                        <#if couponUser.useStartTime??>${couponUser.useStartTime?string("yyyy-MM-dd")}</#if> ~ 
	                        <#if couponUser.useEndTime??>${couponUser.useEndTime?string("yyyy-MM-dd")}</#if>
	                    </div>
	                </div>
	            </div>
	            <div class="pepper-bottom"><a href="${(domainUrlUtil.urlResources)!}/store/${(couponUser.sellerId)!0}.html">去使用</a></div>
	            <#if couponUser.timeout || couponUser.isuse>
	            	<div class="used-icon"></div>
	            </#if>
	        </div>
		</#list>
	</#if>	
		