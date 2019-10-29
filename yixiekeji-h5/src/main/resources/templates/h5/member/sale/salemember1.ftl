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
			<div class="flex-2 text-center">我的推广用户</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
    <!-- 主体 start-->
		<div class="s-container">
	  
        <!-- 结算明细 -->
        <div class="derailedCon">
            <form action="${(domainUrlUtil.urlResources)!}/member/sale-member1.html" method="get" class="mybalance-search">
              <div class="searchItm">
                <span class="search-lable">推 广 用 户：</span>
                <input type="text" id="q_memberName" name="q_memberName" value="${q_memberName!''}" class="search__odn" placeholder="请输入用户名">
              </div>
              <div class="btnaera">
                <input type="submit" class="searchorders bt" value="查询"/>
                <a href="${(domainUrlUtil.urlResources)!}/member/sale-member1.html"  class="searchorders bt"/>重置</a>
              </div>
            </form>
        </div>
       <!-- 结算明细 -->
       
	        <div class="tabitems" id="saleapplymoney_more">
			    <#if saleMembers??>
					<#list saleMembers as saleMember>
				          <div class="item-lis">
				            <div class="th-cell">
				              <label>用 户 名：</label><span>${(saleMember.memberName)!""}</span>
				            </div>
				            <div class="th-cell">
				              <label>注册时间：</label><span>${(saleMember.createTime)?string("yyyy-MM-dd HH:mm:ss")}</span>
				            </div>
				            <div class="th-state">
				              <label>是否推荐人：</label><span><@cont.codetext value="${(saleMember.isSale)!0}" codeDiv="YES_NO"/></span>
				            </div>
				          </div>
			        </#list>
				</#if>
	        </div>
	        
			<#if saleMembers?? && saleMembers?size==pagesize>
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
		
		var urljson = "${(domainUrlUtil.urlResources)!}/member/sale-member1-json.html?pageNum=" + list_page;
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
        					listJsonHtml += "<div class='th-cell'><label>用 户 名：</label><span>" + info.memberName + "</span></div>";
        					listJsonHtml += "<div class='th-cell'><label>注册时间：</label><span>" + info.createTime + "</span></div>";
        					listJsonHtml += "<div class='th-state'><label>是否推荐人：</label><span>";
        					if(info.isSale == 0) {
	                    		listJsonHtml += "否";
	                    	} else if(info.isSale == 1){
	                    		listJsonHtml += "是";
	                    	} 
        					listJsonHtml += "</span></div>";
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