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
   	  	 <div class="flex-2 text-center">我的余额</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="">
	    <div class="rest-infor">
	    <div style="float: left;margin-top: 8px;">
			余额：&yen; ${(member.balance)!0} 元
	    </div>
		<#if member?? && member.balancePwd?? && member.balancePwd != ''>
			<a href="${(domainUrlUtil.urlResources)!}/member/editbalancepassword.html" class="btn btn-default">修改支付密码</a>
		<#else>
			<a href="${(domainUrlUtil.urlResources)!}/member/setbalancepassword.html" class="btn btn-default">设置支付密码</a>
		</#if>
			<a href="${(domainUrlUtil.urlResources)!}/member/balance/pay/recharge.html" class="btn btn-default">充值</a>
		</div>
		<div class="balance-content">
	<#if memberBalanceLogss??>
		<#list memberBalanceLogss as memberBalanceLogs>
	    <div class="oder-list sev-list">
	    	<h2 class="flex flex-pack-justify sev_regoods">
	    		<div>
	    		  <p>时间：${(memberBalanceLogs.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</p>
	    		  <p class="pad-top">描述：${(memberBalanceLogs.remark)!'' }</p>
	    		   <p class="pad-top">金额：${(memberBalanceLogs.money)!''}</p>
	    		</div>
	    		<div class="text-right">
	    		   <p class="mar-bt"><font class="clr53">状态：
		  				<#if memberBalanceLogs.state==1||memberBalanceLogs.state==2||memberBalanceLogs.state==5>增加</#if>
		  				<#if memberBalanceLogs.state==3||memberBalanceLogs.state==4||memberBalanceLogs.state==6>减少</#if>
	    		   </font></p>
	    		</div>
	    	</h2>
	    </div>
		</#list>
	</#if>
	
	
		<div id="balance_more"></div>
				
		<#if memberBalanceLogss?? && memberBalanceLogss?size==pagesize>
			<div id="balance_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
			<div id="balance_more_json_no" class="text-center font14 pad-top2" style="display:none;">已展示全部记录</div>
		<#else>
			<div id="balance_more_json_no" class="text-center font14 pad-top2">已展示全部记录</div>
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
	$("#balance_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urljson = "${(domainUrlUtil.urlResources)!}/member/balanceJson.html?pageNum=" + list_page;
		
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.data, function(i, info){
                    	listJsonHtml += "<div class='oder-list sev-list'>";
                    	listJsonHtml += "<h2 class='flex flex-pack-justify sev_regoods'>";
                    	listJsonHtml += "<div>";
                    	listJsonHtml += "<p>时间：" + info.createTime + "</p>";
                    	listJsonHtml += "<p class='pad-top'>描述：" + info.remark + "</p>";
                    	listJsonHtml += "<p class='pad-top'>金额：" + info.money + "</p>";
                    	listJsonHtml += "</div>";
                    	listJsonHtml += "<div class='text-right'>";
                    	listJsonHtml += " <p class='mar-bt'><font class='clr53'>状态：";
                    	if(info.state == 1 || info.state == 2 || info.state == 5) {
                    		listJsonHtml += "增加";
                    	} else {
                    		listJsonHtml += "减少";
                    	}
                		listJsonHtml += " </font></p></div></h2></div>";
                    })
                    $("#balance_more").append(listJsonHtml);
                    
                    if ((data.total) == ${(pagesize)!}) {
                        $("#balance_more_json").show();
                        $("#balance_more_json_no").hide();
                    } else {
                        $("#balance_more_json").hide();
                        $("#balance_more_json_no").show();
                    }
                }
            }
        });
	});
})
</script>
</body>
</html>