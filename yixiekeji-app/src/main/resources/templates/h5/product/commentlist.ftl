
		    <!-- 评价内容 -->
	    			<#if commentList?? && commentList?size &gt; 0 >
	    				<#list commentList as comment>
	    					<div class="flex bor-btom mar-bt">
				    			<div class="text-center pad-r">
				    				<img src="${(domainUrlUtil.staticResources)!}/img/d_img.jpg" alt="" width="80" height="80"><br>
				    				<span>${(comment.userName)!""}</span>
				    			</div>
				    			<div class="flex-2">
				    				<div class="mar-bt">
				    					<span>
				    						<#if comment?? && comment.grade?? && comment.grade &gt; 0 >
				    							<#list 1..comment.grade as i >
				    								<i class="fa fa-star-o"></i>
				    							</#list>
				    						</#if>
				    					</span>
				    					<!-- <font>5</font> -->
				    				</div>
				    				<div class="mar-bt">${(comment.content)!""}</div>
				    				<div class="mar-bt clrbf">${(comment.createTime)?string("yyyy-MM-dd")!""}</div>
				    			</div>
				    		</div>
	    				</#list>
	    				
	    				<#if type?? && type == "all" >
	    					<div id="allAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('all')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				<#elseif type?? && type == "high" >
	    					<div id="highAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('high')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				<#elseif type?? && type == "middle" >
	    					<div id="middleAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('middle')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				<#elseif type?? && type == "low" >
	    					<div id="lowAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('low')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				</#if>
	    			<#else>
	    				<div class="text-center font16">已展示全部记录</div>
	    			</#if>
