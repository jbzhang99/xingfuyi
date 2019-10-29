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
   	  	 <div class="flex-2 text-center">浏览记录</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	
	<div class="s-container">
	<input type="hidden" id="logCount" value="${logCount!'0'}"/>
    <input type="hidden" id="pageIndex" value="1"/>
    <input type="hidden" id="pageSize" value="${pageSize!'5'}"/>
		<div class="looklog-box">
			<ul id="lookLogListDiv"  class="i-list-ul clear log-i-list-ul">
				<#include "/h5/member/person/productlookloglist.ftl" />
			</ul>
  		</div>
  		
  		<#if lookLogList?? && lookLogList?size &gt; 0 >
		    <div id="addMoreLogDiv">
		   		<a href="javaScript:;" onClick="addMoreLog()">
					<div class="text-center font14 pad-top2">查看更多 <i class="fa fa-angle-double-down"></i></div>
				</a>
			</div>
			<div id="noMoreLogDiv" style="display:none;">
				<div class="text-center font14 pad-top2">已展示全部记录</div>
			</div>
		<#else>
			<div class="text-center font14 pad-top2">已展示全部记录</div>
		</#if>
    </div>
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
		var logCount = parseInt($("#logCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (logCount <= (pageSize * pageIndex)) {
			$("#addMoreLogDiv").hide();
			$("#noMoreLogDiv").show();
			return;
		}
	}
	
	// 加载更多
	function addMoreLog() {
		// 总数
		var logCount = parseInt($("#logCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (logCount <= (pageSize * pageIndex)) {
			$("#addMoreLogDiv").hide();
			$("#noMoreLogDiv").show();
			return;
		}
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/moreviewed.html",
			dataType:"html",
			data:{pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				$("#lookLogListDiv").append(data);
				// 页码加1
				$("#pageIndex").val(pageIndex + 1)
				if (logCount <= (pageSize * (pageIndex+1))) {
					$("#addMoreLogDiv").hide();
					$("#noMoreLogDiv").show();
					return;
				}
			}
		});
	}
	
</script>
</body>
</html>