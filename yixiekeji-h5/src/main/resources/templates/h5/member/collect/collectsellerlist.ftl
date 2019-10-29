			<#if sellerList?? && sellerList?size &gt; 0 >
				<#list sellerList as seller >
					<dl class="flex list-dl pos_relative">
				    	<dt class="flex flex-align-center">
				    		<div>
				    		</div>
				    	</dt>
						<dd class="padl flex-2">
							<div class="pad-top">
								<a href="${(domainUrlUtil.urlResources)!}/store/${(seller.sellerId)!''}.html">
									${(seller.sellerName)!''}
								</a>
							</div>
							<div class="flex flex-pack-justify pad-top">
							   	<div>
							   		${(seller.seller.collectionNumber)!0}人关注
							    </div>
							    <div><a href="javascript:;" class="btn btn-default btn-sm" role="button" onclick="cancelCollectSeller(this, ${(seller.sellerId)!''})">取消收藏</a></div>
							</div>
						</dd>
					</dl>
	  			</#list>
				<div id="addMoreSellerDiv">
		    		<a href="javaScript:;" onClick="addMoreSellerCollect()">
						<div class="text-center font14 pad-top2">查看更多 <i class="fa fa-angle-double-down"></i></div>
					</a>
				</div>
				<div id="noMoreSellerDiv" style="display:none;">
   					<div class="text-center font14 pad-top2">已展示全部记录</div>
   				</div>
			<#else >
				<div class="text-center font14 pad-top2">已展示全部记录</div>
			</#if>