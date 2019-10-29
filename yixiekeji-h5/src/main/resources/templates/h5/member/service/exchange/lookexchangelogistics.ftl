<#include "/h5/commons/_head.ftl" />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<style>
.starbox {
    border-top: solid 1px #f2f2f2;
    /* position: relative; */
    border: solid 1px #edf9e5;
    border-radius: 4px;
    background: #edf9e5;
}
select.cselct {
	appearance:none;
	-moz-appearance:none;
	-webkit-appearance:none;
	background:url("${(domainUrlUtil.staticResources)!}/img/arrow.png") no-repeat scroll right center transparent;
	background-color: #fff;
	padding-right: 14px;
}
</style>
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
  	  	 	<a href="javascript:ejsPageBack();">
  	  	 		<span class="fa fa-angle-left"></span>
  	  	 	</a>
	 	 </div>
   	  	 <div class="flex-2 text-center">退货物流信息</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	<!-- 隐藏域 -->
	<div class=""  >
	    <div class="order-d-box">
	         <h2 class="pad-bt cl0">
	            换货操作记录
	         </h2>
	         <#if memberProductExchangeLogList?? && memberProductExchangeLogList?size &gt; 0 >
				 <#list memberProductExchangeLogList as memberProductExchangeLog >
					 <div class="logisbox mar_top">
						${memberProductExchangeLog.createTime?string("yyyy-MM-dd HH:mm:ss") }
					  	<br>
					  	${memberProductExchangeLog.content}
					 </div>
				 </#list>
			 </#if>
		 <#if memberProductExchangeLogBackList?? && memberProductExchangeLogBackList?size &gt; 0 >
		    <div class="order-d-box">
		         <h2 class="pad-bt cl0">
		            退件物流信息
		         </h2>
		         <p>配送方式：<font>${(memberProductBack.logisticsName2)!""}</font></p>
		         <p>快递单号：<font>${(memberProductBack.logisticsNumber2)!""}</font></p>
				 <#list memberProductExchangeLogBackList as memberProductBackLog >
					 <div class="logisbox mar_top">
						${memberProductBackLog.createTime?string("yyyy-MM-dd HH:mm:ss") }
					  	<br>
					  	${memberProductBackLog.content}
					 </div>
				 </#list>
		     </div>
		 </#if>
		 <#if memberProductExchangeLogExchangeList?? && memberProductExchangeLogExchangeList?size &gt; 0 >
		    <div class="order-d-box">
		         <h2 class="pad-bt cl0">
		            换件物流信息
		         </h2>
		         <p>配送方式：<font>${(memberProductBack.logisticsName)!""}</font></p>
		         <p>快递单号：<font>${(memberProductBack.logisticsNumber)!""}</font></p>
				 <#list memberProductExchangeLogExchangeList as memberProductBackLog >
					 <div class="logisbox mar_top">
						${memberProductBackLog.createTime?string("yyyy-MM-dd HH:mm:ss") }
					  	<br>
					  	${memberProductBackLog.content}
					 </div>
				 </#list>
		     </div>
		 </#if>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	
	
</script>
 
</body>
</html>