<#include "/h5/commons/_head.ftl" />
<body class="bgff">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<#-- <#if isFromOrder?? && isFromOrder == "1" >
   	  	 		<#if orderType?? && orderType == "2" >
				<a href="${(domainUrlUtil.urlResources)!''}/order/flashsale-${actInfo!''}.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 	<#elseif orderType?? && orderType == "3" >
				<a href="${(domainUrlUtil.urlResources)!''}/order/tuan-${actInfo!''}.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 	<#elseif orderType?? && orderType == "4" >
				<a href="${(domainUrlUtil.urlResources)!''}/order/bidding-${actInfo!''}.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 	<#elseif orderType?? && orderType == "6" >
				<a href="${(domainUrlUtil.urlResources)!''}/order/jifen-${actInfo!''}.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 	<#else>
	   	  	 	<a href="${(domainUrlUtil.urlResources)!''}/order/info.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 	</#if>
	   	  	 <#else>
	   	  	 	<a href="${(domainUrlUtil.urlResources)!''}/member/index.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 </#if> -->
	   	  	 <a href="javascript:ejsPageBack();">
	   	  	 	<span class="fa fa-angle-left"></span>
	   	  	 </a>
	   	  	 
   	  	 </div>
   	  	 <div class="flex-2 text-center">收货地址管理</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class=""  >
	    <div class="pad10">
	    	<#if addressList??>
			<#list addressList as address>
            <dl class="address-list mar-bt">
            	<dt class="flex flex-pack-justify pad-bt">
            		<div><span>${address.memberName}</span>&nbsp;<span class="cla4">${address.mobile}</span></div>
            		<#if (address.state)=1>
            		<div><span class="fa fa-map-marker"></span><font class="cla4">默认地址</font></div>
            		</#if>
            	</dt>
            	<dd class="pad8">
            		<p>${address.addAll}&nbsp;${address.addressInfo}</p>
            		<p class="text-right">
            			<#if isFromOrder?? && isFromOrder == "1" >
            			<!-- 如果是从订单页跳转过来显示选择按钮 -->
            			<a href="javascript:;" class="ad-delate" onclick="choseAddress(${(address.id)!0})">选择</a>
            			&nbsp;|&nbsp;
            			</#if>
            			<a href="${(domainUrlUtil.urlResources)!''}/member/app-editaddress.html?isFromOrder=${(isFromOrder)!0}&id=${(address.id)!0}&orderType=${(orderType)!''}&actInfo=${(actInfo)!''}">编辑</a>
            			<#if !(isFromOrder?? && isFromOrder == "1") >
            			<!-- 如果是从订单页跳转过来不显示删除按钮 -->
            			&nbsp;|&nbsp;
            			<a href="javascript:;" class="ad-delate" onclick="deleteAddress(this, ${(address.id)!0})">删除</a>
            			<#if (address.state)!=1>
            			&nbsp;|&nbsp;
            			<a href="javascript:;" onclick="defaultAddress(${(address.id)!0})">默认</a>
            			</#if>
            			</#if>
            		</p>
            	</dd>
            </dl>
            </#list>
            </#if>

            <button type="button" class="btn btn-block adresbtn" style="margin-top:20px;">添加新地址</button>
	    </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		<#if message?? && message != "">
			// alert('${message}');
			$.dialog('alert','提示','${message}',2000);
		</#if>
		
		$(".adresbtn").click(function() {
			window.location.href=domain+"/member/app-newaddress.html?isFromOrder=${(isFromOrder)!0}&orderType=${(orderType)!''}&actInfo=${(actInfo)!''}";
		});
	});
	
	function deleteAddress(obj, id) {
		/* if(confirm("是否确定删除!")){
			$.ajax({
				type : "POST",
				url :  domain+"/member/deleteaddress.html",
				data : {id:id},
				dataType : "json",
				success : function(data) {
					if(data.success){
						$(obj).parents(".address-list").remove();
					}else {
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		} */
		$.dialog('confirm','提示','是否确定删除!',0,function(){
			$.closeDialog();
			$.ajax({
				type : "POST",
				url :  domain+"/member/deleteaddress.html",
				data : {id:id},
				dataType : "json",
				success : function(data) {
					if(data.success){
						$(obj).parents(".address-list").remove();
					}else {
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		});
	}
	
	function defaultAddress(id) {
		/* if(confirm("是否确定设定该地址为默认？")){
			window.location.href=domain+"/member/setdefaultaddress.html?id="+id;
		} */
		$.dialog('confirm','提示','是否确定设定该地址为默认？',0,function(){
			$.closeDialog();
			window.location.href=domain+"/member/setdefaultaddress.html?id="+id;
		});
	}
	
	function choseAddress(id) {
		<#if orderType?? && orderType == "2" >
			window.location.href=domain+"/order/flashsale-${actInfo!''}.html?addressId="+id;
		<#elseif orderType?? && orderType == "3" >
			window.location.href=domain+"/order/tuan-${actInfo!''}.html?addressId="+id;
		<#elseif orderType?? && orderType == "4" >
			window.location.href=domain+"/order/bidding-${actInfo!''}.html?addressId="+id;
		<#elseif orderType?? && orderType == "6" >
			window.location.href=domain+"/order/jifen-${actInfo!''}.html?addressId="+id;
  	 	<#else>
			window.location.href=domain+"/order/info.html?addressId="+id;
  	 	</#if>
	}
</script>
</body>
</html>