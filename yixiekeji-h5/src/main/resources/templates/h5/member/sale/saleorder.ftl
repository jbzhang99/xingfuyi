<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/salecenter.css">
<body class="bgf2">
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
	   	  	 	<a href="javascript:ejsPageBack();">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
			</div>
			<div class="flex-2 text-center">收入流水</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
    <!-- 主体 start-->
		<div class="s-container">
	  
        <!-- 结算明细 -->
        <div class="derailedCon">
            <form action="${(domainUrlUtil.urlResources)!}/member/sale-orders.html" method="get" class="mybalance-search">
              <div class="searchItm">
                <span class="search-lable">订 单 号：</span>
                <input type="text" id="q_orderSn" name="q_orderSn" value="${q_orderSn!''}" class="search__odn" placeholder="请输入订单号">
              </div>
              <div class="searchItm">
                <span class="search-lable">购 买 用 户：</span>
                <input type="text" id="q_buyName" name="q_buyName" value="${q_buyName!''}" class="search__odn" placeholder="请输入用户名">
              </div>
              <div class="searchItm">
                <span class="search-lable">佣 金 状 态：</span>
                <@cont.select id="q_saleState" codeDiv="SALE_ORDER_STATE" value="${q_saleState!''}" class="search-option__input"/>
              </div>
              <div class="btnaera">
	                <input type="submit" class="searchorders bt" value="查询"/>
	                <a href="${(domainUrlUtil.urlResources)!}/member/sale-orders.html"  class="searchorders bt"/>重置</a>
              </div>
            </form>
        </div>
       <!-- 结算明细 -->
       
	        <div class="tabitems" id="saleapplymoney_more">
			    <#if saleOrders??>
					<#list saleOrders as saleOrder>
				          <div class="item-lis">
				            <div class="th-cell">
				              <label>购 买 人：</label><span>${(saleOrder.buyName)!""}</span>
				            </div>
				            <div class="th-cell">
				              <label>订 单 号：</label><span>${(saleOrder.orderSn)!""}</span>
				            </div>
				            <div class="th-cell">
				              <label>购买商品：</label><span>${(saleOrder.productName)!""}</span>
				            </div>
				            <div class="th-cell">
				              <label>购买时间：</label><span>${(saleOrder.orderTime)?string("yyyy-MM-dd HH:mm:ss")}</span>
				            </div>
				            <div class="th-cell">
				              <label>佣金状态：</label><span><@cont.codetext value="${(saleOrder.saleState)!0}" codeDiv="SALE_ORDER_STATE"/></span>
				            </div>
				            <div class="th-cell">
				              <label>佣金金额：</label><span>￥${(saleOrder.saleMoney)!""}</span>
				            </div>
				            <div class="th-state"><a href="${(domainUrlUtil.urlResources)!}/member/sale-orders-${(saleOrder.id)!'0'}.html"/>查看</a></div>
				          </div>
			        </#list>
				</#if>
	        </div>
	        
			<#if saleOrders?? && saleOrders?size==pagesize>
				<div id="saleapplymoney_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
		       <div id="saleapplymoney_more_json_no" style="display:none" class="text-center font14 pad-top2">已展示全部记录</div>
			<#else>
				<div id="saleapplymoney_more_json_no" class="text-center font14 pad-top2">已展示全部记录</div>
			</#if>
			<input type="hidden"  name="list_page" id="list_page" value="1" />
       </div>
  </div>
  <!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
$(function(){
	<#if message?? && message != "">
		$.dialog('alert','提示','${message}',2000);
	</#if>
		
	$("#saleapplymoney_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var q_orderSn = $("#q_orderSn").val();
		var q_buyName = $("#q_buyName").val();
		var q_saleState = $("#q_saleState").val();
		
		var urljson = "${(domainUrlUtil.urlResources)!}/member/sale-orders-json.html?pageNum=" + list_page + "&q_saleState=" + q_saleState + "&q_buyName=" + q_buyName + "&q_orderSn=" + q_orderSn;
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.data, function(i, info){
        					listJsonHtml += "<div class='item-lis'>";
        					listJsonHtml += "<div class='th-cell'><label>购 买 人：</label><span>" + info.buyName + "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>订 单 号：</label><span>" + info.orderSn + "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>购买商品：</label><span>" + info.productName + "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>购买时间：</label><span>" + info.orderTime + "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>佣金状态：</label><span>";
        					if(info.saleState == 1) {
	                    		listJsonHtml += "预计收益";
	                    	} else if(info.saleState == 2){
	                    		listJsonHtml += "可以提现";
	                    	} else if(info.saleState == 3){
	                    		listJsonHtml += "提现中";
	                    	} else if(info.saleState == 4){
	                    		listJsonHtml += "提现完成";
	                    	}
        					listJsonHtml += "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>佣金金额：</label><span>￥" + info.saleMoney + "</span></div>";
	                    	listJsonHtml += "<div class='th-state'><a href='${(domainUrlUtil.urlResources)!}/member/sale-orders-" + info.id + ".html'>查看</a></div>";
	                    	listJsonHtml += "</div>";
                     })
                    $("#saleapplymoney_more").append(listJsonHtml);
                    if ((data.total) != 0) {
                        $("#saleapplymoney_more_json").show();
                    } else {
                        $("#saleapplymoney_more_json").hide();
                        $("#saleapplymoney_more_json_no").show();
                    }
                }
            }
        });
	});
})
</script>
</body>
</html>