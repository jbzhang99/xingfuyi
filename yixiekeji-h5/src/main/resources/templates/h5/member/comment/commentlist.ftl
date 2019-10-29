
	    	<#if commentsList?? && commentsList?size &gt; 0 >
	    	<#list commentsList as comment >
		    <div class="mar-bt pos_relative">
	    		<dl class="flex list-dl">
					<dt style="width:80px; height:80px;"><a href="${(domainUrlUtil.urlResources)!}/product/${(comment.productId)!0}.html" class="block"><img src="${(domainUrlUtil.imageResources)!}${comment.productLeadLittle!''}" width="80" height="80"></a></dt>
					<dd class="padl flex-2">
						<div class="product_name mar_top"><a href="${(domainUrlUtil.urlResources)!}/product/${(comment.productId)!0}.html" class="block">${(comment.productName)!''}</a></div>
						<!-- <div class="product-desript">
								<p class="clr53">￥<font>1405.00</font></p>
						</div> -->
						<div class="flex flex-pack-justify goods-sun">
							<div>购买时间：<font>${(comment.createTime?string("yyyy-MM-dd"))!''}</font></div>
							<div class="btn-box-sty"><a href="javascript:;" class="btn" onclick="viewDetail(this)">查看评价</a></div>
						</div>
						
					</dd>
				</dl>
				<div class="bgff pad10 evalute-list">
							<div class="starbox">
							    <div class="stararrow-up"></div>
								<div class="starlist clear">
								  <span>评分:</span>
								  <div class="star1" val="${(comment.grade)!3}"></div>
								</div>
								<div class="starlist clear">
								  <span>描述相符:</span>
								  <div class="star2" val="${(comment.description)!3}"></div>
								</div>
								<div class="starlist clear">
								  <span>服务态度:</span>
								  <div class="star3" val="${(comment.serviceAttitude)!3}"></div>
								</div>
								<div class="starlist clear">
								  <span>发货速度:</span>
								  <div class="star4" val="${(comment.productSpeed)!3}"></div>
								</div>
								<div class="starlist flex pad10">
								  <div >心得:</div>
								  <div class="flex-2 expertxt">${comment.content!''}</div>
								</div>
							</div>
						</div>
		    </div>
		    </#list>
		    </#if>