
		<#if productList?? && productList?size &gt; 0 >
			<#list productList as product >
				<dl class="flex list-dl pos_relative">
				    <dt><a href="${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html"><img src="${(domainUrlUtil.imageResources)!}${product.productLeadLittle}"></a></dt>
					<dd class="padl flex-2">
						<div class="product_name">
							<a href="${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html">
							${(product.productName)!''}
							</a>
						</div>
						<div class="product-desript">
						   <p class="clr53">￥<font>${(product.mallMobilePrice)?string('0.00')!'0.00'}</font></p>
						</div>
						<a href="javascript:;" class="btn btn-default btn-sm col_cancelbtn" role="button" onclick="cancelCollectProduct(this, ${(product.productId)!0})">取消收藏</a>
					</dd>
				</dl>
			</#list>
				<div id="addMoreProductDiv">
		    		<a href="javaScript:;" onClick="addMoreProductCollect()">
						<div class="text-center font14 pad-top2">查看更多 <i class="fa fa-angle-double-down"></i></div>
					</a>
				</div>
				<div id="noMoreProductDiv" style="display:none;">
   					<div class="text-center font14 pad-top2">已展示全部记录</div>
   				</div>
			<#else >
				<div class="text-center font14 pad-top2">已展示全部记录</div>
		</#if>
		