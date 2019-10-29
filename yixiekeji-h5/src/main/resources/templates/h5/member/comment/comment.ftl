<#include "/h5/commons/_head.ftl" />
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
		 </div>
   	  	 <div class="flex-2 text-center">我的评价</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="">
	    <!-- 评价列表 -->
	    <div id="commentListDiv">
	    	<input type="hidden" id="commentsCount" value="${commentsCount!'0'}"/>
    		<input type="hidden" id="pageIndex" value="1"/>
    		<input type="hidden" id="pageSize" value="${pageSize!'5'}"/>
    		<#include "/h5/member/comment/commentlist.ftl" />
	    </div>
	    <!-- 评价列表 -->
	    <#if commentsList?? && commentsList?size &gt; 0 >
	    <div id="addMoreCommentDiv">
	   		<a href="javaScript:;" onClick="addMoreComment()">
				<div class="text-center font14">查看更多 <i class="fa fa-angle-double-down"></i></div>
			</a>
		</div>
		<div id="noMoreCommentDiv" style="display:none;">
			<div class="text-center font14">已展示全部记录</div>
		</div>
	    <#else>
	    <div class="text-center font14">已展示全部记录</div>
	    </#if>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<!-- 星级评分 -->
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.raty.min.js"></script>

<script type="text/javascript">
	$(function(){
		$.fn.raty.defaults.path = '${(domainUrlUtil.staticResources)!}/img';
		initStar();
		
		displayMoreBtn();
	});
	
	function displayMoreBtn() {
		// 总数
		var commentsCount = parseInt($("#commentsCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (commentsCount <= (pageSize * pageIndex)) {
			$("#addMoreCommentDiv").hide();
			$("#noMoreCommentDiv").show();
		}
	}
	
	function initStar() {
		$("#commentListDiv").find(".star1").each(function(){
			var starNumber = $(this).attr("val");
			$(this).raty({ readOnly: true, score: starNumber });
		});
		
		$("#commentListDiv").find(".star2").each(function(){
			var starNumber = $(this).attr("val");
			$(this).raty({ readOnly: true, score: starNumber });
		});
		
		$("#commentListDiv").find(".star3").each(function(){
			var starNumber = $(this).attr("val");
			$(this).raty({ readOnly: true, score: starNumber });
		});
		
		$("#commentListDiv").find(".star4").each(function(){
			var starNumber = $(this).attr("val");
			$(this).raty({ readOnly: true, score: starNumber });
		});
	}
	
	// 加载更多收藏商品
	function addMoreComment() {
		// 总数
		var commentsCount = parseInt($("#commentsCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (commentsCount <= (pageSize * pageIndex)) {
			$("#addMoreCommentDiv").hide();
			$("#noMoreCommentDiv").show();
			return;
		}
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/morecomment.html",
			dataType:"html",
			data:{pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				$("#commentListDiv").append(data);
				// 页码加1
				$("#pageIndex").val(pageIndex + 1)
				initStar();
				if (commentsCount <= (pageSize * (pageIndex+1))) {
					$("#addMoreCommentDiv").hide();
					$("#noMoreCommentDiv").show();
					return;
				}
			}
		});
	}
	
	function viewDetail(obj) {
		$(obj).parents(".list-dl").siblings('.evalute-list').toggleClass('evalute-list2');
	}

</script>
 
</body>
</html>