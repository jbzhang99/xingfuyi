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
   	  	 <div class="flex-2 text-center">订单中心</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   	
   	<div class="o-tab-block">
	    <div class="swiper-container">
	        <div class="swiper-wrapper js-o-tab-items">
	            <div class="swiper-slide <#if orderState==0>active</#if>" data-hash="slide1" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html">所有订单</a></div>
	            <div class="swiper-slide <#if orderState==1>active</#if>" data-hash="slide2" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=1">未付款</a></div>
	            <div class="swiper-slide <#if orderState==2>active</#if>" data-hash="slide3" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=2">待确认</a></div>
	            <div class="swiper-slide <#if orderState==3>active</#if>" data-hash="slide4" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=3">待发货</a></div>
	            <div class="swiper-slide <#if orderState==4>active</#if>" data-hash="slide5" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=4#slide4">已发货</a></div>
	            <div class="swiper-slide <#if orderState==5>active</#if>" data-hash="slide6" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=5#slide4">已完成</a></div>
	            <div class="swiper-slide <#if orderState==6>active</#if>" data-hash="slide7" ><a href="${(domainUrlUtil.urlResources)!}/member/order.html?orderState=6#slide4">已取消</a></div>
	        </div>
	    </div>
	</div>

   
   	<input type="hidden" id="ordersCount" value="${ordersCount!'0'}"/>
    <input type="hidden" id="pageIndex" value="1"/>
    <input type="hidden" id="pageSize" value="${pageSize!'5'}"/>
    <input type="hidden" id="orderState" value="${orderState!''}"/>
	<div id="ordersListDiv" class="order-box-content">
		<#include "/h5/member/order/orderlistmore.ftl" />
    </div>
    
    <#if ordersList?? && ordersList?size &gt; 0 >
	    <div id="addMoreOrderDiv">
	   		<a href="javaScript:;" onClick="addMoreOrder()">
				<div class="text-center font14">查看更多 <i class="fa fa-angle-double-down"></i></div>
			</a>
		</div>
		<div id="noMoreOrderDiv" style="display:none;">
			<div class="text-center font14">已展示全部记录</div>
		</div>
	<#else>
		<div class="text-center font14">已展示全部记录</div>
	</#if>
    
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/swiper/swiper.min.js"></script>
<script type="text/javascript">
	$(function(){
		var mySwiper = new Swiper('.swiper-container', {
            slidesPerView: 4,
            hashNavigation: true
        });
        $('.js-o-tab-items .swiper-slide').on('click',function(){
          $(this).addClass('active').siblings().removeClass('active');
        });
		displayMoreBtn();
	});
	
	function displayMoreBtn() {
		// 总数
		var ordersCount = parseInt($("#ordersCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (ordersCount <= (pageSize * pageIndex)) {
			$("#addMoreOrderDiv").hide();
			$("#noMoreOrderDiv").show();
			return;
		}
	}
	
	// 加载更多
	function addMoreOrder() {
		// 总数
		var ordersCount = parseInt($("#ordersCount").val());
		// 当前加载的页数
		var pageIndex = parseInt($("#pageIndex").val());
		// 每页加载数量
		var pageSize = parseInt($("#pageSize").val());
		// 如果总数量小于等于已经加载的数量，则表示没有更多，隐藏加载更多按钮，显示没有更多
		if (ordersCount <= (pageSize * pageIndex)) {
			$("#addMoreOrderDiv").hide();
			$("#noMoreOrderDiv").show();
			return;
		}
		$.ajax({
			type:"GET",
			url:"${(domainUrlUtil.urlResources)!}/member/moreorder.html",
			dataType:"html",
			data:{orderState:$("#orderState").val(),pageIndex:pageIndex+1},
			success:function(data){
				//加载数据
				$("#ordersListDiv").append(data);
				// 页码加1
				$("#pageIndex").val(pageIndex + 1)
				if (ordersCount <= (pageSize * (pageIndex+1))) {
					$("#addMoreOrderDiv").hide();
					$("#noMoreOrderDiv").show();
					return;
				}
			}
		});
	}
	
	//取消订单
	function cancalOrder(ordersId){
		$.dialog('confirm','提示','确定要取消该订单吗？',0,function(){
			$.closeDialog();
			$.ajax({
				type : "GET",
				url :  domain+"/member/cancalorder.html",
				data : {id:ordersId},
				dataType : "json",
				success : function(data) {
					if(data.success){
						// 修改显示状态
						$("#orderStateFont"+ordersId).removeClass("clr53");
						$("#orderStateFont"+ordersId).html("取消");
						
						// 修改显示按钮
						var btnDiv = "<a href='${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=" + ordersId + "' class='cla4'>查看</a>";
						$("#orderBtnDiv"+ordersId).empty();
						$("#orderBtnDiv"+ordersId).append(btnDiv);
					}else {
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		});
	}
	
	//确认收货
	function goodsReceipt(ordersId){
		$.ajax({
			type : "GET",
			url :  domain+"/member/goodreceive.html",
			data : {ordersId:ordersId},
			dataType : "json",
			success : function(data) {
				if(data.success){
					// 修改显示状态
					$("#orderStateFont"+ordersId).html("完成");
					
					// 修改显示按钮
					var btnDiv = "<a href='${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=" + ordersId + "' class='cla4'>查看</a>";
					btnDiv = btnDiv + "&nbsp;";
					btnDiv = btnDiv + "<a href='${(domainUrlUtil.urlResources)!}/member/addcomment.html?id=" + ordersId + "' class='cla4'>评价晒单</a>";
					btnDiv = btnDiv + "&nbsp;";
					btnDiv = btnDiv + "<a href='${(domainUrlUtil.urlResources)!}/member/backapply.html?id=" + ordersId + "' class='cla4'>申请退换货</a>";
					$("#orderBtnDiv"+ordersId).empty();
					$("#orderBtnDiv"+ordersId).append(btnDiv);
				}else {
					// alert(data.message);
					$.dialog('alert','提示',data.message,2000);
				}
			}
		});
	}
</script>
</body>
</html>