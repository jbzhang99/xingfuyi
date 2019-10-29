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
   	  	 <div class="flex-2 text-center">投诉</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
   	<!-- 隐藏域 -->
	<input type="hidden" id="complaintCount" name="complaintCount" value="${(complaintCount)!0}">
	<input type="hidden" id="pageSize" name="pageSize" value="${(pageSize)!0}">
	<input type="hidden" id="pageIndex" name="pageIndex" value="1">
	<div class="order-box-content" id="complaintListDiv">
		<#include "/h5/member/service/complaint/complaintlist.ftl" />
    </div>

    <#if complaintList?? && complaintList?size &gt; 0 >
	    <div id="addMoreBackDiv">
	   		<a href="javaScript:;" onClick="addMoreComplaint()">
				<div class="text-center font14">查看更多 <i class="fa fa-angle-double-down"></i></div>
			</a>
		</div>
		<div id="noMoreBackDiv" style="display:none;">
			<div class="text-center font14">已展示全部记录</div>
		</div>
	<#else>
		<div class="text-center font14">已展示全部记录</div>
	</#if>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		displayMoreBtn();
	});
	
	function displayMoreBtn() {
		// 总数
		var complaintCount = parseInt($("#complaintCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (complaintCount <= (pageSize * pageIndex)) {
			$("#addMoreBackDiv").hide();
			$("#noMoreBackDiv").show();
			return;
		}
	}
	
	// 加载更多
	function addMoreComplaint() {
		// 总数
		var complaintCount = parseInt($("#complaintCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (complaintCount <= (pageSize * pageIndex)) {
			$("#addMoreBackDiv").hide();
			$("#noMoreBackDiv").show();
			return;
		}
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/morecomplaint.html",
			dataType:"html",
			data:{pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				$("#complaintListDiv").append(data);
				// 页码加1
				$("#pageIndex").val(pageIndex + 1)
				if (complaintCount <= (pageSize * (pageIndex+1))) {
					$("#addMoreBackDiv").hide();
					$("#noMoreBackDiv").show();
					return;
				}
			}
		});
	}

	function viewDetail(obj) {
		$(obj).parents(".order-set-btn").siblings('.evalute-list').toggleClass('evalute-list2');
	}

</script>
 
</body>
</html>