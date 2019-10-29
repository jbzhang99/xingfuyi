<#include "/h5/commons/_head.ftl" />

<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/scroll.css">

<body class="bgf2">
	<!-- S 头部 -->
	<header id="header" class="headerbox">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
				<a href="javascript:ejsPageBack();">
					<span class="fa fa-angle-left"></span>
				</a>
			</div>
			<div class="flex-2 text-center">优惠券</div>
			<div class="flex-1 text-right" id="fa-bars">
				<span class="fa fa-bars"></span>
			</div>
		</div>
		 <#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- E 头部 -->
	
	<input type="hidden" id="couponCount" value="${couponCount!'0'}"/>
    <input type="hidden" id="pageIndex" value="1"/>
    <input type="hidden" id="pageSize" value="${pageSize!'5'}"/>
    
	<div class="pepper-con">
		<#if couponUsers?? && couponUsers?size &gt; 0>
			<#include "/h5/member/coupon/membercouponmore.ftl" />
		<#else>
			<div class="nocoupon">
				没有优惠券
			</div>
		</#if>	
		<div id="thelist"></div>
		<#if couponUsers?? && couponUsers?size &gt; 0 >
		    <div id="addMoreCouponDiv">
		   		<a href="javaScript:;" onClick="addMoreAsk()">
					<div class="text-center font14">查看更多 <i class="fa fa-angle-double-down"></i></div>
				</a>
			</div>
			<div id="noMoreCouponDiv" style="display:none;">
				<div class="text-center font14">已展示全部记录</div>
			</div>
		<#else>
			<div class="text-center font14">已展示全部记录</div>
		</#if>
	</div>

<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
<script type="text/javascript">
	$(function(){
		displayMoreBtn();
	});
	
	function displayMoreBtn() {
		// 总数
		var couponCount = parseInt($("#couponCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (couponCount <= (pageSize * pageIndex)) {
			$("#addMoreCouponDiv").hide();
			$("#noMoreCouponDiv").show();
			return;
		}
	}
	
	// 加载更多
	function addMoreAsk() {
		// 总数
		var couponCount = parseInt($("#couponCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (couponCount <= (pageSize * pageIndex)) {
			$("#addMoreCouponDiv").hide();
			$("#noMoreCouponDiv").show();
			return;
		}
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/coupon/listmore.html",
			dataType:"html",
			data:{pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				$("#thelist").append(data);
				// 页码加1
				$("#pageIndex").val(pageIndex + 1);
				if (couponCount <= (pageSize * (pageIndex+1))) {
					$("#addMoreCouponDiv").hide();
					$("#noMoreCouponDiv").show();
					return;
				}
			}
		});
	}
</script>
	
</body>
</html>