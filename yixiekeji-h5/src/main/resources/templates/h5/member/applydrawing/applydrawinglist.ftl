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
   	  	 <div class="flex-2 text-center">提现申请列表</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div>
	   <div class="rest-infor">
		   <div class="text-right">
		   		<a href="${(domainUrlUtil.urlResources)!}/member/applydrawinginfo.html" class="btn btn-default">提现</a>
		   </div>
	   </div>
	   <div style="padding:0 10px;">
		<#if memberApplyDrawings??>
			<#list memberApplyDrawings as memberApplyDrawing>
		    <div class="oder-list sev-list">
		    	<h2 class="flex flex-pack-justify sev_regoods">
		    		<div>
		    		  <p>申请时间：${(memberApplyDrawing.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</p>
		    		  <p class="pad-top">银行名称：${(memberApplyDrawing.bank)!'' }</p>
		    		  <p class="pad-top">银行卡号：${(memberApplyDrawing.bankCode)!'' }</p>
		    		  <p class="pad-top">申请提现金额：${(memberApplyDrawing.money)!'' }</p>
		    		</div>
		    		<div class="text-right">
		    		   <p class="mar-bt"><font class="clr53">状态：
		    		   <#if memberApplyDrawing.state??>
			  				<#if memberApplyDrawing.state==1>待审核
				  				<#elseif memberApplyDrawing.state==2>通过
				  				<#elseif memberApplyDrawing.state==3>已打款
				  				<#elseif memberApplyDrawing.state==4>处理失败
				  				<#else>
			  				</#if>
	  		    		</#if>
		    		   </font></p>
		    		</div>
		    	</h2>
		    </div>
			</#list>
		</#if>
	    
	
	
			<div id="applydrawing_more"></div>
					
			<#if memberApplyDrawings?? && memberApplyDrawings?size==pagesize>
				<div id="applydrawing_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
				<div id="applydrawing_more_json_no" class="text-center font14 pad-top2" style="display:none;">已展示全部记录</div>
			<#else>
				<div id="applydrawing_more_json_no" class="text-center font14 pad-top2">已展示全部记录</div>
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
	$("#applydrawing_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urljson = "${(domainUrlUtil.urlResources)!}/member/applydrawingJson.html?pageNum=" + list_page;
		
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
                    	listJsonHtml += "<p>申请时间：" + info.createTime + "</p>";
                    	listJsonHtml += "<p class='pad-top'>银行名称：" + info.bank + "</p>";
                    	listJsonHtml += "<p class='pad-top'>银行卡号：" + info.bankCode + "</p>";
                    	listJsonHtml += "<p class='pad-top'>申请提现金额：" + info.money + "</p>";
                    	listJsonHtml += "</div>";
                    	listJsonHtml += "<div class='text-right'>";
                    	listJsonHtml += " <p class='mar-bt'><font class='clr53'>状态：";
                    	if(info.state == 1) {
                    		listJsonHtml += "待审核";
                    	} else if(info.state == 2) {
                    		listJsonHtml += "通过";
                    	} else if(info.state == 3) {
                    		listJsonHtml += "已打款";
                    	} else if(info.state == 4) {
                    		listJsonHtml += "处理失败";
                    	}
                		listJsonHtml += " </font></p></div></h2></div>";
                    })
                    $("#applydrawing_more").append(listJsonHtml);
                    if ((data.total) == ${(pagesize)!}) {
                        $("#applydrawing_more_json").show();
                        $("#applydrawing_more_json_no").hide();
                    } else {
                        $("#applydrawing_more_json").hide();
                        $("#applydrawing_more_json_no").show();
                    }
                }
            }
        });
	});
})
</script>
</body>
</html>