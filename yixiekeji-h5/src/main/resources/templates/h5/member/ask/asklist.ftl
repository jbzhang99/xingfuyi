
	    <#if askList?? && askList?size &gt; 0 >
		<#list askList as askinfo >
	    <div class="mar-bt pos_relative">
	    	<dl class="flex list-dl">
	    		<a href='${(domainUrlUtil.urlResources)!}/product/${(askinfo.productId)!0}.html' class='block'>
	    			<dt style="width:80px; height:80px;"><img src="${(domainUrlUtil.imageResources)!}${askinfo.productLeadLittle}"></dt>
	    		</a>
	    		<dd class="padl flex-2">
	    			<a href='${(domainUrlUtil.urlResources)!}/product/${(askinfo.productId)!0}.html' class='block'>
	    				<div class="product_name mar_top">${(askinfo.productName)!''}</div>
	    			</a>
	    		    <div class="flex flex-pack-justify goods-Consu">
		    			<div>咨询时间：<font>${(askinfo.createTime?string("yyyy-MM-dd"))!''}</font></div>
		    			<div><a href="javascript:;" class="cla4" onclick="viewDetail(this)">我的咨询</a></div>
	    	        </div>
	    		</dd>
	    	</dl>

	    	<!-- 咨询框 -->
    		<div class="bgff pad10 evalute-list font12" style="display:;">
    		   <div class="starbox starbox2">
    		     <div class="stararrow-up"></div>
		    	 <dl class="consult-dl mar-bt">
		    	 	<dt><img src="${(domainUrlUtil.staticResources)!}/img/d_img.jpg" width="60"><span>${(askinfo.userName)!""}</span></dt>
		    	 	<dd>
		    	 		<span class="arrow-up addlef"></span>
		    	        <span class="arrow-up2 addlef2"></span>
		    	        <div class="form-control consult_text">
                          咨询内容：${(askinfo.askContent)!''}
                           <p class="clrbf">${(askinfo.createTime?string("yyyy-MM-dd"))!''}</p>
		    	        </div>
		    	 	</dd>
		    	 </dl>

		    	 <dl class="consult-dl mar-bt">
		    	 	<dt class="text-right"><span>齐驱科技</span><img src="${(domainUrlUtil.staticResources)!}/img/d_img_1.jpg" width="60"></dt>
		    	 	<dd>
		    	 		<span class="arrow-up"></span>
		    	        <span class="arrow-up2"></span>
		    	        <div class="form-control consult_text">
                          回复内容：${(askinfo.replyContent)!''}
                           <p class="clrbf"><#if askinfo.replyTime??>${(askinfo.replyTime?string("yyyy-MM-dd"))!''}</#if></p>
		    	        </div>
		    	 	</dd>
		    	 </dl>
		    	</div>
		    </div>
	    </div>
	    </#list>
	    </#if>