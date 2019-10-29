<#include "/h5/commons/_head.ftl" />
<body>
    <!-- 头部 -->
    <header id="header">
   	   <div class="flex flex-align-center head-bar">
   	   	  <div class="flex-1 text-left">
	   	   	  <a href="javascript:ejsPageBack();">
	   	   	  	<span class="fa fa-angle-left"></span>
	   	   	  </a>
   	   	  </div>
   	   	  <div class="flex-2 text-center">商品评价</div>
   	  	  <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	   </div>
   	   <#include "/h5/commons/_hidden_menu.ftl" />
    </header>
    <!-- 头部 end-->
	<#-- <#include "/h5/product/producthead.ftl" />  -->
	<div class="flex goods-nav">
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/info/${(productId)!0}.html?type=${(type)!0}">详细介绍</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/spec/${(productId)!0}.html?type=${(type)!0}">规格参数</a></div>
		<div class="flex-1 active"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/comment/${(productId)!0}.html?type=${(type)!0}">商品评价</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/ask/${(productId)!0}.html?type=${(type)!0}">商品咨询</a></div>
	</div>
	<div class="s-container bgff" id="container">
	    <div class="pad10">
		    <div class="flex" style="margin:20px 0;">
		    	<div class="evaluation pad-r"><font>${(statisticsVO.productCommentsHighProportion)!"100"}%</font><br><span>好评度</span></div>
		    	<div class="flex-2" style="font-size:14px;">
		    		<div class="flex">
		    			<div class="">好评</div>
		    			<div class="flex-2 perecnt"><span>&nbsp;</span></div>
		    			<div  class="rate">${(statisticsVO.productCommentsHighProportion)!"0"}%</div>
		    		</div>
		    		<div class="flex">
		    			<div class="">中评</div>
		    			<div class="flex-2 perecnt"><span>&nbsp;</span></div>
		    			<div class="rate">${(statisticsVO.productCommentsMiddleProportion)!"0"}%</div>
		    		</div>
		    		<div class="flex">
		    			<div class="">差评</div>
		    			<div class="flex-2 perecnt"><span>&nbsp;</span></div>
		    			<div class="rate">${(statisticsVO.productCommentsLowProportion)!"0"}%</div>
		    		</div>
		    	</div>
		    </div>

		    <div class="flex evaluat-nav" id="evaluat-nav">
		    	<div class="flex-1 clr53">全部<br>(${(statisticsVO.productCommentsAllCount)!"0"})</div>
		    	<div class="flex-1">好评<br>(${(statisticsVO.productCommentsHighCount)!"0"})</div>
		    	<div class="flex-1">中评<br>(${(statisticsVO.productCommentsMiddleCount)!"0"})</div>
		    	<div class="flex-1">差评<br>(${(statisticsVO.productCommentsLowCount)!"0"})</div>
		    </div>
		    <!-- 评价内容 -->
		    <div class="content" id="content">
		    	<input type="hidden" id="pageSize" value="${(pageSize)!5}"/>
		    	<div class="content-list" style="display:block;">
		    		<input type="hidden" id="allNumber" value="${(allNumber)!0}"/>
		    		<input type="hidden" id="allPageIndex" value="1"/>
	    			<#if allCommentList?? && allCommentList?size &gt; 0 >
	    				<div id="allCommentDiv">
	    				<#list allCommentList as comment>
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
	    					<div id="allAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('all')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				</div>
	    				<div id="allNoMoreDiv" style="display:none;">
	    					<div class="text-center font16">已展示全部记录</div>
	    				</div>
	    			<#else>
	    				<div class="text-center font16">已展示全部记录</div>
	    			</#if>
		    	</div>
		    	<div class="content-list">
					<input type="hidden" id="highNumber" value="${(highNumber)!0}"/>
		    		<input type="hidden" id="highPageIndex" value="1"/>
	    			<#if highCommentList?? && highCommentList?size &gt; 0 >
	    				<div id="highCommentDiv">
	    				<#list highCommentList as comment>
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
	    					<div id="highAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('high')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				</div>
	    				<div id="highNoMoreDiv" style="display:none;">
	    					<div class="text-center font16">已展示全部记录</div>
	    				</div>
	    			<#else>
	    				<div class="text-center font16">已展示全部记录</div>
	    			</#if>
		    	</div>
		    	<div class="content-list">
		    		<input type="hidden" id="middleNumber" value="${(middleNumber)!0}"/>
		    		<input type="hidden" id="middlePageIndex" value="1"/>
	    			<#if middleCommentList?? && middleCommentList?size &gt; 0 >
	    				<div id="middleCommentDiv">
	    				<#list middleCommentList as comment>
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
	    					<div id="middleAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('middle')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				</div>
	    				<div id="middleNoMoreDiv" style="display:none;">
	    					<div class="text-center font16">已展示全部记录</div>
	    				</div>
	    			<#else>
	    				<div class="text-center font16">已展示全部记录</div>
	    			</#if>
		    	</div>
		    	<div class="content-list">
		    		<input type="hidden" id="lowNumber" value="${(lowNumber)!0}"/>
		    		<input type="hidden" id="lowPageIndex" value="1"/>
	    			<#if lowCommentList?? && lowCommentList?size &gt; 0 >
	    				<div id="lowCommentDiv">
	    				<#list lowCommentList as comment>
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
	    					<div id="lowAddMoreDiv">
			    				<a href="javaScript:;" onClick="addMoreComment('low')">
			    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
			    				</a>
		    				</div>
	    				</div>
	    				<div id="lowNoMoreDiv" style="display:none;">
	    					<div class="text-center font16">已展示全部记录</div>
	    				</div>
	    			<#else>
	    				<div class="text-center font16">已展示全部记录</div>
	    			</#if>
		    	</div>
		    </div> 
		    <!-- 评价内容结束 -->
		</div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		var allNumber = parseInt($("#allNumber").val());
		if (allNumber <= pageSize) {
			$("#allAddMoreDiv").hide();
			$("#allNoMoreDiv").show();
		}
		
		var highNumber = parseInt($("#highNumber").val());
		if (highNumber <= pageSize) {
			$("#highAddMoreDiv").hide();
			$("#highNoMoreDiv").show();
		}
		
		var middleNumber = parseInt($("#middleNumber").val());
		if (middleNumber <= pageSize) {
			$("#middleAddMoreDiv").hide();
			$("#middleNoMoreDiv").show();
		}
		
		var lowNumber = parseInt($("#lowNumber").val());
		if (lowNumber <= pageSize) {
			$("#lowAddMoreDiv").hide();
			$("#lowNoMoreDiv").show();
		}
		
	});
	
	// 加载更多评论
	function addMoreComment(gradeType) {
		// 评论总数
		var number = 0;
		// 当前加载的页数
		var pageIndex = 1;
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		if (gradeType == "all") {
			number = parseInt($("#allNumber").val());
			pageIndex = parseInt($("#allPageIndex").val());
			// 如果总数量小于等于已经加载的数量，则表示没有更多评论，隐藏加载更多按钮，显示没有更多评论
			if (number <= (pageSize * pageIndex)) {
				$("#allAddMoreDiv").hide();
				$("#allNoMoreDiv").show();
				return;
			}
			// 删除加载更多按钮（在ajax调用后会再加载一个新的）
			$("#allAddMoreDiv").remove();
		} else if (gradeType == "high") {
			number = parseInt($("#highNumber").val());
			pageIndex = parseInt($("#highPageIndex").val());
			// 如果总数量小于等于已经加载的数量，则表示没有更多评论，隐藏加载更多按钮，显示没有更多评论
			if (number <= (pageSize * pageIndex)) {
				$("#highAddMoreDiv").hide();
				$("#highNoMoreDiv").show();
				return;
			}
			// 删除加载更多按钮（在ajax调用后会再加载一个新的）
			$("#highAddMoreDiv").remove();
		} else if (gradeType == "middle") {
			number = parseInt($("#middleNumber").val());
			pageIndex = parseInt($("#middlePageIndex").val());
			// 如果总数量小于等于已经加载的数量，则表示没有更多评论，隐藏加载更多按钮，显示没有更多评论
			if (number <= (pageSize * pageIndex)) {
				$("#middleAddMoreDiv").hide();
				$("#middleNoMoreDiv").show();
				return;
			}
			// 删除加载更多按钮（在ajax调用后会再加载一个新的）
			$("#middleAddMoreDiv").remove();
		}  else if (gradeType == "low") {
			number = parseInt($("#lowNumber").val());
			pageIndex = parseInt($("#lowPageIndex").val());
			// 如果总数量小于等于已经加载的数量，则表示没有更多评论，隐藏加载更多按钮，显示没有更多评论
			if (number <= (pageSize * pageIndex)) {
				$("#lowAddMoreDiv").hide();
				$("#lowNoMoreDiv").show();
				return;
			}
			// 删除加载更多按钮（在ajax调用后会再加载一个新的）
			$("#lowAddMoreDiv").remove();
		}
		
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/product/morecomment.html",
			dataType:"html",
			data:{productId:${(productId)!0},type:gradeType,pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				if (gradeType == "all") {
					$("#allCommentDiv").append(data);
					// 页码加1
					$("#allPageIndex").val(pageIndex + 1)
				} else if (gradeType == "high") {
					$("#highCommentDiv").append(data);
					// 页码加1
					$("#highPageIndex").val(pageIndex + 1)
				} else if (gradeType == "middle") {
					$("#middleCommentDiv").append(data);
					// 页码加1
					$("#middlePageIndex").val(pageIndex + 1)
				}  else if (gradeType == "low") {
					$("#lowCommentDiv").append(data);
					// 页码加1
					$("#lowPageIndex").val(pageIndex + 1)
				}
			}
		});
	}
</script>
</body>
</html>