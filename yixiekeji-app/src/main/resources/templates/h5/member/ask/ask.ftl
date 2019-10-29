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
   	  	 <div class="flex-2 text-center">我的咨询</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
   	<input type="hidden" id="askCount" value="${askCount!'0'}"/>
    <input type="hidden" id="pageIndex" value="1"/>
    <input type="hidden" id="pageSize" value="${pageSize!'5'}"/>
	<div id="askListDiv">
		<!-- zixun列表 1-->
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
		    	 	<dt class="text-right"><span>商城回复</span><img src="${(domainUrlUtil.staticResources)!}/img/d_img_1.jpg" width="60"></dt>
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
	    <!-- zixun列表 1 end-->
    </div>
    
    <#if askList?? && askList?size &gt; 0 >
	    <div id="addMoreAskDiv">
	   		<a href="javaScript:;" onClick="addMoreAsk()">
				<div class="text-center font14">查看更多 <i class="fa fa-angle-double-down"></i></div>
			</a>
		</div>
		<div id="noMoreAskDiv" style="display:none;">
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
		var askCount = parseInt($("#askCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (askCount <= (pageSize * pageIndex)) {
			$("#addMoreAskDiv").hide();
			$("#noMoreAskDiv").show();
			return;
		}
	}
	
	// 加载更多
	function addMoreAsk() {
		// 总数
		var askCount = parseInt($("#askCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (askCount <= (pageSize * pageIndex)) {
			$("#addMoreAskDiv").hide();
			$("#noMoreAskDiv").show();
			return;
		}
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/morequestion.html",
			dataType:"html",
			data:{pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				$("#askListDiv").append(data);
				// 页码加1
				$("#pageIndex").val(pageIndex + 1)
				if (askCount <= (pageSize * (pageIndex+1))) {
					$("#addMoreAskDiv").hide();
					$("#noMoreAskDiv").show();
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
