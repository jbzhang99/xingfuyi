<#include "/h5/commons/_head.ftl" />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<#-- <#if productBackId?? && productBackId &gt; 0 >
	   	  	 	<a href="${(domainUrlUtil.urlResources)!}/member/back.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
   	  	 	<#elseif productExchangeId?? && productExchangeId &gt; 0 >
   	  	 		<a href="${(domainUrlUtil.urlResources)!}/member/exchange.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 <#else>
	   	  	 	<a href="${(domainUrlUtil.urlResources)!}/member/index.html">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
   	  	 	</#if> -->
	 		<a href="javascript:ejsPageBack();">
 	 			<span class="fa fa-angle-left"></span>
 	 		</a>
		 </div>
   	  	 <div class="flex-2 text-center">我要投诉</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="" id="complaintListDiv">
		<div class="oder-list sev-list">
	    	<h2 class="flex flex-pack-justify sev_regoods">
	    		<div>
	    		  <p class="mar-bt">订单编号：${(ordersProduct.ordersSn)!0}</p>
	    		  <p>下单时间：${(ordersProduct.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</p>
	    		</div>
	    	</h2>
	    	<a href="${(domainUrlUtil.urlResources)!}/product/${(ordersProduct.productId)!0}.html?goodId=${(ordersProduct.productGoodsId)!0}" class="block">
		    	<dl class="img-ul flex">
		    	  <dt style="width:80px; height:80px;"><img src="${(domainUrlUtil.imageResources)!}${product.masterImg}"></dt>
		    	  <dd class="flex-2">
		    	    <div class="product_name">${(ordersProduct.productName)!''}&nbsp;${(ordersProduct.specInfo)!''}</div>
		    	    <div>x${(ordersProduct.number)!0}</div>
		    	  </dd>
		    	</dl>
	    	</a>
	    </div>
	    
		<div class="user-infor user-infor2 bgff mar-bt">
			<@form.form id="complainForm" name="complainForm" enctype="multipart/form-data">
				<input type="hidden" name="sellerId" value="${(ordersProduct.sellerId)!''}">
				<input type="hidden" name="orderId" value="${(order.id)!''}">
				<input type="hidden" id="productBackId" name="productBackId" value="${(productBackId)!''}">
				<input type="hidden" id="productExchangeId" name="productExchangeId" value="${(productExchangeId)!''}">
				<input type="hidden" name="orderProductId" value="${(ordersProduct.id)!''}">
		    	<div class='starlist flex pad10'>
					<div class="font12 padr5">投诉内容:&nbsp;<br>(10-100字)</div>
					<div class='flex-2 expertxt'>
						<textarea class='form-control' rows='3' id='content' name='content'>${(complaint.content)!'' }</textarea>
					</div>
				</div>
		    	<div class='starlist flex pad10'>
					<div class="font12">投诉图片:&nbsp;</div>
					<div class='flex-2 expertxt'>
						<input type="file" id="pic" name="pic"/>
					</div>
				</div>
				<font id="errLabel" class="font12 clr53" style="margin-left:10px;"></font>
			</@form.form>
		</div>
		<div class="flex pad10">
		   <div class="flex-1 padr5">
			<button type="button" class="btn btn-block btn-login" id="addComplaint">提交</button>
		   </div>
		   <div class="flex-1">
			<button type="button" class="btn btn-block btn-login" id="cancel">取消</button>
		   </div>
		</div>
    </div>

	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script src="${(domainUrlUtil.staticResources)!}/js/jquery.form.js"></script>
<script type="text/javascript">
	$(function(){
        var options = {
       		type:"POST",
            url:domain+"/member/savecomplaint.html",
            dataType:"json",
            async : false,
            data : $('#complainForm').serialize(),
            success: function (data) {
                if(data.success){
                	// alert("保存成功");
                    // window.location.href=domain+'/member/complaint.html';
                    $.dialog('alert','提示','保存成功',2000,function(){ window.location.href=domain+'/member/complaint.html'; });
                }else{
                	$("#errLabel").html(data.message);
                    $("#addComplaint").removeAttr("disabled");
                }
            }
        };
		
		//点击提交按钮事件
		$("#addComplaint").click(function(){
			var content = $("#content").val();
			if (content == null || content == "") {
				$("#errLabel").html("请输入投诉内容");
				return;
			}
			if (content.length < 10) {
				$("#errLabel").html("请至少输入10个字符");
				return;
			}
			if (content.length > 100) {
				$("#errLabel").html("最多输入100个字符");
				return;
			}
			
			var pic = $("#pic").val();
			if (pic == null || pic == "") {
				$("#errLabel").html("请选择投诉图片");
				return;
			}
			
			$("#addComplaint").attr("disabled","disabled");
			$('#complainForm').ajaxSubmit(options);

		});
		
		$("#cancel").click(function() {
			var toUrl = domain+'/member/complaint.html';
			var productBackId = $("#productBackId").val();
			if (productBackId != null && productBackId.length > 0) {
				toUrl = domain + '/member/back.html';
			}
			
			var productExchangeId = $("#productExchangeId").val();
			if (productExchangeId != null && productExchangeId.length > 0) {
				toUrl = domain + '/member/exchange.html';
			}
			
			window.location.href = toUrl;
		});
	});
	

</script>
 
</body>
</html>