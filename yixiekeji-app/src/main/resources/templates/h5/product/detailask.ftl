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
			<div class="flex-2 text-center">商品咨询</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
	<#-- <#include "/h5/product/producthead.ftl" />  -->
	<div class="flex goods-nav">
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/info/${(productId)!0}.html?type=${(type)!0}">详细介绍</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/spec/${(productId)!0}.html?type=${(type)!0}">规格参数</a></div>
		<div class="flex-1"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/comment/${(productId)!0}.html?type=${(type)!0}">商品评价</a></div>
		<div class="flex-1 active"><a class="block" href="${(domainUrlUtil.urlResources)!''}/product/ask/${(productId)!0}.html?type=${(type)!0}">商品咨询</a></div>
	</div>
	<div class="s-container bgff" id="container">
	    <div class="pad10">
	        <!-- 咨询框 -->
		    <div class="text-right"><a class="btn btn-default" href="#" role="button" id="consult-btn">我要咨询</a></div>
		    <div class="textareabox" id="textareabox">
		    	<span class="arrow-up"></span>
		    	<span class="arrow-up2"></span>
		    	<textarea class="form-control" rows="3" id="askContent"></textarea>
		    	<label style="color:red" id="errLabel"></label>
		    	<p class="pad-top text-center">
			    	<input class="btn btn-default bgf2" type="button" value="提交" id="askSubmit">
	                <input class="btn btn-default bgf2" type="button" value="取消" id="consult-cancle">	
                </p>
		    </div>
		    <!-- end -->

		    <!-- 咨询列表 -->
		    
	    	<input type="hidden" id="askNumber" value="${(askNumber)!0}"/>
    		<input type="hidden" id="askPageIndex" value="1"/>
    		<input type="hidden" id="pageSize" value="${(pageSize)!5}"/>
    		<!-- <div  class="font12"> -->
   			<#if askList?? && askList?size &gt; 0 >
   				<div  class="font12" id="askDiv">
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
	                           <p class="clrbf">${((ask.replyTime)?string("yyyy-MM-dd"))!""}</p>
			    	        </div>
			    	 	</dd>
			    	 </dl>
   				</#list>
   				</div>
   				<div id="addMoreDiv">
    				<a href="javaScript:;" onClick="addMoreAsk()">
    					<div class="text-center font16">查看更多 <i class="fa fa-angle-double-down"></i></div>
    				</a>
   				</div>
   				<div id="noMoreDiv" style="display:none;">
   					<div class="text-center font16">已展示全部记录</div>
   				</div>
   			<#else>
   				<div class="text-center font16">已展示全部记录</div>
   			</#if>
   			<!-- </div> -->
		    
		    <!-- 咨询列表 end-->
		</div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		
		// 咨询总数
		var askNumber = parseInt($("#askNumber").val());
		// 当前加载的页数
		var askPageIndex = parseInt($("#askPageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多咨询，隐藏加载更多按钮，显示没有更多咨询
		if (askNumber <= (pageSize * askPageIndex)) {
			$("#addMoreDiv").hide();
			$("#noMoreDiv").show();
		}
		
		$("#consult-btn").on("click",function(){
			if (!isUserLogin()) {
				// 未登录跳转到登陆页面
				var toUrl = domain + "/product/ask/${(productId)!0}.html?type=${(type)!0}";
				window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
				return;
			}
	       $("#textareabox").css("display","block");
		});
		$("#consult-cancle").on("click",function(){
	       $("#textareabox").css("display","none");
		});
		
		$("#askSubmit").click(function() {
			var content = $("#askContent").val();
			if (content == null || content == "") {
				$("#errLabel").html("请输入咨询内容");
				return;
			}
			if (content.length < 10) {
				$("#errLabel").html("请至少输入10个字符");
				return;
			}
			if (content.length > 100) {
				$("#errLabel").html("最多输入100个字符");
				return;
			}
			
			$("#askSubmit").attr("disabled", "disabled");
			$.ajax({
				type : "POST",
				url : domain + "/member/app-savequestion.html",
				dataType : "json",
				async : false,
				data : {productId:${(productId)!0},askContent:content},
				success : function(data) {
					if (data.success) {
						// alert('保存成功');
						$.dialog('alert','提示','保存成功',2000);
						$("#errLabel").html("");
						$("#consult-cancle").click();
					} else {
						$("#errLabel").html(data.message);
						$("#askSubmit").removeAttr("disabled");
					}
				}
			});

		});
	});
	
	// 加载更多咨询
	function addMoreAsk() {
		// 咨询总数
		var askNumber = parseInt($("#askNumber").val());
		// 当前加载的页数
		var askPageIndex = parseInt($("#askPageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多咨询，隐藏加载更多按钮，显示没有更多咨询
		if (askNumber <= (pageSize * askPageIndex)) {
			$("#addMoreDiv").hide();
			$("#noMoreDiv").show();
			return;
		}
		
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/product/moreask.html",
			dataType:"html",
			data:{productId:${(productId)!0},pageIndex:askPageIndex+1},
			success:function(data){
				//加载数据
				$("#askDiv").append(data);
				// 页码加1
				$("#askPageIndex").val(askPageIndex + 1)
				if (askNumber <= (pageSize * (askPageIndex+1))) {
					$("#addMoreDiv").hide();
					$("#noMoreDiv").show();
					return;
				}
			}
		});
	}
</script>
</body>
</html>