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
			<div class="flex-2 text-center">我的结算明细</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
    <!-- 主体 start-->
		<div class="s-container">
	  
        <div class="expewrapcon">
            <div class="expected-income-con">
              <h3 class="mybalance-title mybalance-title__accrued">预计收入</h3>
              <ul class="mybalance-info">
                <li class="balancecash"><em class="pricetag">￥</em>${sumState1!"0"}</li>
                <li class="balancedinfo"><em class="iconadd icon-clock"></em>${countState1!"0"}单已完成购买</li>
              </ul>
              <div class="accrued__below">
                <dl class="accrued-rules">
                  <dt>满足以下条件即可提现佣金：</dt>
                  <dd>
                    <em class="iconadd icon-check"></em>客户满意
                  </dd>
                  <dd>
                    <em class="iconadd icon-check"></em>订单完成
                  </dd>
                  <dd>
                    <em class="iconadd icon-check"></em>无退换货问题且过期限
                  </dd>
                </dl>
                <p>注：预计收入中的奖励明细在符合提现时，系统自动生成。</p>
              </div>
            </div>
            <div class="expected-income-con mybalance-withdraw">
              <div class="withdraw__upper">
                <h3 class="mybalance-title mybalance-title__cash">可提现</h3>
                <ul class="mybalance-info">
                  <li class="balancecash">
                    <em class="pricetag">￥</em>${sumState2!"0"}
                  </li>
                  <li class="balancedinfo">
                    <em class="iconadd icon-clock"></em>${countState2!"0"}单可提现
                  </li>
                </ul>
                <a href="javascript:void(0)" onclick="jsSaleApplyMoney()" class="btn-getcash">提现</a>
              </div>
              <div class="withdraw__middle bottomline-dot">
                <ul>
                  <li class="balancecash-money">
                    <em class="pricetag">￥</em>${sumState3!"0"}
                  </li>
                  <li class="balancecash-status">提现中…</li>
                  <li class="balancecash-info">
                    <em class="iconadd icon-clock"></em>${countState3!"0"}单正在审核结算中
                  </li>
                </ul>
              </div>
              <div class="withdraw__below">
                <ul>
                  <li class="balancecash-money">
                    <em class="pricetag">￥</em>${sumState4!"0"}
                  </li>
                  <li class="balancecash-status">已提现</li>
                  <li class="balancecash-info">
                    <em class="iconadd icon-clock"></em>${countState4!"0"}单已提现完毕
                  </li>
                </ul>
              </div>
            </div>
        </div>

       <!-- 结算明细 -->
       
	  <div class="tabitems" id="saleapplymoney_more">
	    <#if saleApplyMoneys??>
			<#list saleApplyMoneys as saleApplyMoney>
		          <div class="item-lis">
		            <div class="th-cell">
		              <label>姓　　名：</label><span>${(saleApplyMoney.bankName)!""}</span>
		            </div>
		            <div class="th-cell">
		              <label>身份证号：</label><span>${(saleApplyMoney.certificateCode)!""}</span>
		            </div>
		            <div class="th-cell">
		              <label>金　　额：</label><span>￥${(saleApplyMoney.money)!""}</span>
		            </div>
		            <div class="th-cell">
		              <label>取款时间：</label><span>${(saleApplyMoney.createTime)?string("yyyy-MM-dd HH:mm:ss")}</span>
		            </div>
		            <div class="th-cell">
		              <label>汇款时间：</label><span><#if saleApplyMoney.updateTime??>${(saleApplyMoney.updateTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></span>
		            </div>
		            <!-- 状态 -->
		            <div class="th-state">是否打款：<@cont.codetext value="${(saleApplyMoney.state)!0}" codeDiv="YES_NO"/></div>
		          </div>
	        </#list>
		</#if>
	  </div>
	  
			<#if saleApplyMoneys?? && saleApplyMoneys?size==pagesize>
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
function jsSaleApplyMoney(){
	 var countState2 = ${countState2!0};
	 if(countState2 == 0) {
	 	$.dialog('alert','提示','没有订单可以提款。',2000);
	 	return;
	 }
	 var countState3 = ${countState3!0};
	 if(countState3 != 0) {
	 	$.dialog('alert','提示','有未处理的提款申请，处理完成之后，在进行提款操作。',2000);
	 	return;
	 }
	 
	  $.dialog('confirm','提示','确定提现吗？',0,function(){
    	  	  	$.ajax({
				type:"POST",
				url:domain+"/member/sale-apply-money-save.html",
				dataType:"json",
				async : false,
				success:function(data){
					if(data.success){
						window.location.href=domain+"/member/sale-apply-money.html?success=success"
					}else{
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
       }); 
}
		
$(function(){
	<#if message?? && message != "">
		$.dialog('alert','提示','${message}',2000);
	</#if>
		
	$("#saleapplymoney_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urljson = "${(domainUrlUtil.urlResources)!}/member/sale-apply-money-json.html?pageNum=" + list_page;
					
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
        					listJsonHtml += "<div class='th-cell'><label>姓　　名：</label><span>" + info.bankName + "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>身份证号：</label><span>" + info.certificateCode + "</span></div>";
        					listJsonHtml += " <div class='th-cell'><label>金　　额：</label><span>" + info.money + "</span></div>";
        					listJsonHtml += " <div class='th-cell'><label>取款时间：</label><span>" + info.createTime + "</span></div>";
        					listJsonHtml += " <div class='th-cell'><label>汇款时间：</label><span>" + info.updateTime + "</span></div>";
	                    	listJsonHtml += "<div class='th-state'>是否打款：";
	                    	if(info.state == 0) {
	                    		listJsonHtml += "否";
	                    	} else {
	                    		listJsonHtml += "是";
	                    	}
	                    	listJsonHtml += "</div>";
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