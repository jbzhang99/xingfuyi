<#include "/h5/commons/_head.ftl" />
<body class="bgff">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar" style="border-bottom: 0.5px solid #cccccc;">
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

	<div class="">
	    <div class="pad10" style="padding: 0 10px;">
	    	<#if addressList?size&gt; 0>
			<#--<#list addressList as address>-->
            <#--<dl class="address-list mar-bt">-->
            	<#--<dt class="flex flex-pack-justify pad-bt">-->
            		<#--<div><span>${address.memberName}</span>&nbsp;<span class="cla4">${address.mobile}</span></div>-->
            		<#--<#if (address.state)=1>-->
            		<#--<div><span class="fa fa-map-marker"></span><font class="cla4">默认地址</font></div>-->
            		<#--</#if>-->
            	<#--</dt>-->
            	<#--<dd class="pad8">-->
            		<#--<p>${address.addAll}&nbsp;${address.addressInfo}</p>-->
            		<#--<p class="text-right">-->
            			<#--<#if isFromOrder?? && isFromOrder == "1" >-->
            			<#--<!-- 如果是从订单页跳转过来显示选择按钮 &ndash;&gt;-->
            			<#--<a href="javascript:;" class="ad-delate" onclick="choseAddress(${(address.id)!0})">选择</a>-->
            			<#--&nbsp;|&nbsp;-->
            			<#--</#if>-->
            			<#--<a href="${(domainUrlUtil.urlResources)!''}/member/editaddress.html?isFromOrder=${(isFromOrder)!0}&id=${(address.id)!0}&orderType=${(orderType)!''}&actInfo=${(actInfo)!''}">编辑</a>-->
            			<#--<#if !(isFromOrder?? && isFromOrder == "1") >-->
            			<#--<!-- 如果是从订单页跳转过来不显示删除按钮 &ndash;&gt;-->
            			<#--&nbsp;|&nbsp;-->
            			<#--<a href="javascript:;" class="ad-delate" onclick="deleteAddress(this, ${(address.id)!0})">删除</a>-->
            			<#--<#if (address.state)!=1>-->
            			<#--&nbsp;|&nbsp;-->
            			<#--<a href="javascript:;" onclick="defaultAddress(${(address.id)!0})">默认</a>-->
            			<#--</#if>-->
            			<#--</#if>-->
            		<#--</p>-->
            	<#--</dd>-->
            <#--</dl>-->

            <#--<dl class="address-list mar-bt">-->
            <#--<dt class="flex flex-pack-justify pad-bt">-->
                <#--<div><span style="width: 16px;font-family:PingFangSC-Medium,PingFang SC;">${address.memberName}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="width: 16px;font-family:PingFangSC-Medium,PingFang SC;">${address.mobile}</span></div>-->
                <#--<#if address.state = 1>-->
                <#--<div>-->
                    <#--<font class="cla4">默认地址</font>-->
                <#--</div>-->
                <#--</#if>-->
            <#--</dt>-->
            <#--<dd style="padding-left: 8px;padding-bottom: 20px;" class="flex">-->
            <#--<span style="width: 87%;margin-right: 3%;height:30px; font-size: 12px;font-family: PingFangSC-Regular,PingFang SC;font-weight: 400;color: rgba(102,102,102,1);" class="flex-5">${address.addAll}&nbsp;${address.addressInfo}</span>-->
               <#--<span class="flex-1">-->
                   <#--<a href="${(domainUrlUtil.urlResources)!''}/member/editaddress.html?isFromOrder=${(isFromOrder)!0}&id=${(address.id)!0}&orderType=${(orderType)!''}&actInfo=${(actInfo)!''}"><img src="${(domainUrlUtil.staticResources)!}/img/bianji@2x.png" style="width: 20px;height: 19px;"  alt=""></a>-->
               <#--</span>-->



            <#--<p class="text-right">-->
            <#--<#if isFromOrder?? && isFromOrder == "1" >-->
            <#--<!-- 如果是从订单页跳转过来显示选择按钮 &ndash;&gt;-->
            <#--<a href="javascript:;" class="ad-delate" onclick="choseAddress(${(address.id)!0})">选择</a>-->
            <#--&nbsp;|&nbsp;-->
            <#--</#if>-->
            <#--<a href="${(domainUrlUtil.urlResources)!''}/member/editaddress.html?isFromOrder=${(isFromOrder)!0}&id=${(address.id)!0}&orderType=${(orderType)!''}&actInfo=${(actInfo)!''}">编辑</a>-->
            <#--<#if !(isFromOrder?? && isFromOrder == "1") >-->
            <#--<!-- 如果是从订单页跳转过来不显示删除按钮 &ndash;&gt;-->
            <#--&nbsp;|&nbsp;-->
            <#--<a href="javascript:;" class="ad-delate" onclick="deleteAddress(this, ${(address.id)!0})">删除</a>-->
            <#--<#if (address.state)!=1>-->
            <#--&nbsp;|&nbsp;-->
            <#--<a href="javascript:;" onclick="defaultAddress(${(address.id)!0})">默认</a>-->
            <#--</#if>-->
            <#--</#if>-->
            <#--</p>-->


            <#--</dd>-->
            <#--</dl>-->



            <#--</#list>-->


            <#if isFromOrder?? && isFromOrder == "1">
                <div id="addressListFromOrder" style="padding: 5px 10px;">

                </div>
            <#else>

            <div id="addressList" style="padding: 5px 10px;">

            </div>

            </#if>

            <div class="loginBtnClick adresbtn">
                <div>+新建收货地址</div>
            </div>
            <#else>
            <div style="width: 100%;height:40rem;text-align: center;">
                <img src="${(domainUrlUtil.staticResources)!}/img/wuchatu@2x.png" style="width: 150px;margin-top: 80px;height: 150px;" alt="">
            </div>

            <div class="loginBtnClick adresbtn">
                <div>+新建收货地址</div>
            </div>
            </#if>
            <#--</#if>-->

            <#--<button type="button" class="btn btn-block adresbtn" style="margin-top:20px;">添加新地址</button>-->
	    </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		<#--<#if message?? && message != "">-->
			<#--// alert('${message}');-->
			<#--$.dialog('alert','提示','${message}',2000);-->
		<#--</#if>-->
        var isFromOrder = ${(isFromOrder)!0};
        var orderType   = ${(orderType)!0};
        var actInfo     = ${(actInfo)!0};
        var domainUrl   = "${(domainUrlUtil.urlResources)!''}";
        var imgUrl      = "${(domainUrlUtil.staticResources)!''}";
        var str = '';
        $.ajax({
            type : "get",
            url :  domain+"/member/getAddressByMId",
            data : {memberId:getData("memberId")},
            dataType : "json",
            success : function(data) {
                if(data.success){
                    if(isFromOrder =="1"){
                        for(var i = 0; i<data.result.length;i++){
                            str+='<dl class="address-list" style="border-bottom: 1px solid rgba(0,0,0,0.1);" onclick="choseAddress('+data.result[i].id+')">';
                            str+='<dt class="flex flex-pack-justify" style="height: 34px;line-height: 34px;">';

                            var mobile = data.result[i].mobile.substr(0, 3) + '****' + data.result[i].mobile.substring(7, 11);
                            str+='<div><span style="width: 16px;font-family:PingFangSC-Medium,PingFang SC;">'+data.result[i].memberName+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="width: 16px;font-family:PingFangSC-Medium,PingFang SC;">'+mobile+'</span></div>';
                            if(data.result[i].state==1){
                                str+='<div><font class="cla4">默认地址</font></div>'
                            }else {
                                str+='<div><font class="cla4"></font></div>'
                            }
                            str+='</dt>';
                            str+='<dd style="margin-bottom:0px;" class="flex"><span style="width: 87%;font-size: 12px;font-family: PingFangSC-Regular,PingFang SC;font-weight: 400;color: rgba(102,102,102,1);" class="flex-5">'+data.result[i].addAll+''+data.result[i].addressInfo+'</span>';
                            str+='<span class="flex-1" style="text-align: right;">';
                            str+='<a href="'+domainUrl+'/member/editaddress.html?isFromOrder='+isFromOrder+'&id='+data.result[i].id+'&orderType='+orderType+'&actInfo='+actInfo+'"><img src="'+imgUrl+'/img/bianji@2x.png" style="width: 18px;height: 15px;"  alt=""></a>';
                            str+='</span>';
                            str+='</dd>';
                            str+='<div style="height: 5px;"></div>'
                            str+='</dl>';
                        }

                        $("#addressListFromOrder").html(str)


                    }else{
                        for(var i = 0; i<data.result.length;i++){
                            str+='<dl class="address-list" style="border-bottom: 1px solid rgba(0,0,0,0.1);">';
                            str+='<dt class="flex flex-pack-justify" style="height: 34px;line-height: 34px;">';

                            var mobile = data.result[i].mobile.substr(0, 3) + '****' + data.result[i].mobile.substring(7, 11);
                            str+='<div><span style="width: 16px;font-family:PingFangSC-Medium,PingFang SC;">'+data.result[i].memberName+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="width: 16px;font-family:PingFangSC-Medium,PingFang SC;">'+mobile+'</span></div>';
                            if(data.result[i].state==1){
                                str+='<div><font class="cla4">默认地址</font></div>'
                            }else {
                                str+='<div><font class="cla4"></font></div>'
                            }
                            str+='</dt>';
                            str+='<dd style="margin-bottom:0px;" class="flex"><span style="width: 87%;font-size: 12px;font-family: PingFangSC-Regular,PingFang SC;font-weight: 400;color: rgba(102,102,102,1);" class="flex-5">'+data.result[i].addAll+''+data.result[i].addressInfo+'</span>';
                            str+='<span class="flex-1" style="text-align: right;">';
                            str+='<a href="'+domainUrl+'/member/editaddress.html?isFromOrder='+isFromOrder+'&id='+data.result[i].id+'&orderType='+orderType+'&actInfo='+actInfo+'"><img src="'+imgUrl+'/img/bianji@2x.png" style="width: 18px;height: 15px;"  alt=""></a>';
                            str+='</span>';
                            str+='</dd>';
                            str+='<div style="height: 5px;"></div>'
                            str+='</dl>';
                        }

                        $("#addressList").html(str)

                    }
                }else {
                    layer.open({content:data.message,skin: 'msg',time: 2 });
                }
            }
        });


		$(".adresbtn").click(function() {
			window.location.href=domain+"/member/newaddress.html?isFromOrder=${(isFromOrder)!0}&orderType=${(orderType)!''}&actInfo=${(actInfo)!''}";
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
