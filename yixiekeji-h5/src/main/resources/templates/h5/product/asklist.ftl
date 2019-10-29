
    			<#if askList?? && askList?size &gt; 0 >
    				<#list askList as ask>
    					<dl class="consult-dl mar-bt">
				    	 	<dt><img src="${(domainUrlUtil.staticResources)!}/img/d_img.jpg" width="60"><span>${(ask.userName)!""}</span></dt>
				    	 	<dd>
				    	 		<span class="arrow-up addlef"></span>
				    	        <span class="arrow-up2 addlef2"></span>
				    	        <div class="form-control consult_text">
		                          咨询内容：${(ask.askContent)!""}
		                           <p class="clrbf">${(ask.createTime)?string("yyyy-MM-dd")!""}</p>
				    	        </div>
				    	 	</dd>
				    	 </dl>
		
				    	 <dl class="consult-dl mar-bt">
				    	 	<dt class="text-right"><span>齐驱科技</span><img src="${(domainUrlUtil.staticResources)!}/img/d_img_1.jpg" width="60"></dt>
				    	 	<dd>
				    	 		<span class="arrow-up"></span>
				    	        <span class="arrow-up2"></span>
				    	        <div class="form-control consult_text">
		                          回复内容：${(ask.replyContent)!""}
		                           <p class="clrbf">${(ask.replyTime)?string("yyyy-MM-dd")!""}</p>
				    	        </div>
				    	 	</dd>
				    	 </dl>
    				</#list>
    			</#if>