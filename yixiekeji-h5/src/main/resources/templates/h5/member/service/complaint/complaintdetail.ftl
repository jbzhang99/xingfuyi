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
   	  	 <div class="flex-2 text-center">投诉详情</div>
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
	    		  <p>投诉时间：${(complaint.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</p>
	    		</div>
	    		<div>
	    			<font class="clr53">
	  		    		<#if  complaint.state??>
			  				<#assign state = complaint.state/>
			  				<#if state==1>待审核
				  				<#elseif state==2>投诉不通过
				  				<#elseif state==3>投诉通过
				  				<#elseif state==4>商家申诉待审核
				  				<#elseif state==5>商家申诉不通过
				  				<#elseif state==6>商家申诉通过
				  				<#else>审核中
			  				</#if>
	  		    		</#if>
	    			</font>
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
	    
		<div class="user-infor user-infor2 bgff mar-bt font12">
	    	<div class='starlist pad10'>
				<div class="font12">投诉内容:&nbsp;</div>
				<div class='pad-t5 expertxt'>
					<textarea class='form-control' rows='3' disabled="disabled">${(complaint.content)!'' }</textarea>
				</div>
			</div>
	    	<div class='starlist padt0'>
				<div class="font12">投诉图片：&nbsp;</div>
				
					<#if complaint.image?? && complaint.image != ''>
					<div class='pad-t5 expertxt' style="height:200px; overflow:hidden;">
				  		<img  src="${(domainUrlUtil.imageResources)!}${(complaint.image)!''}">
				  	</div>
				  	<#else>
				  		无
				  	</#if>
			</div>
	    	<div class='starlist flex padt0'>
				<div class="font12">商家申诉时间：&nbsp;</div>
				<div class='flex-2 expertxt'>
					<#if complaint.sellerComplaintTime??>
			  			${(complaint.sellerComplaintTime?string("yyyy-MM-dd HH:mm:ss"))!'' }
			  		</#if>
				</div>
			</div>
	    	<div class='starlist padt0'>
				<div class="font12">商家申诉内容：&nbsp;</div>
				<div class='pad-t5 expertxt'>
					<textarea class='form-control' rows='3' disabled="disabled">${(complaint.sellerCompContent)!''}</textarea>
				</div>
			</div>
	    	<div class='starlist flex padt0'>
				<div class="font12">商家申诉图片：&nbsp;</div>
				<#if complaint.sellerCompImage?? && complaint.sellerCompImage != ''>
				    <div class='pad-t5 expertxt'style="height:200px; overflow:hidden;">
			  		<img  src="${(domainUrlUtil.imageResources)!}${(complaint.sellerCompImage)!''}">
			  		</div>
			  	<#else>
			  		无
			  	</#if>
				
			</div>
	    	<div class='starlist padt0'>
				<div class="font12">平台意见：&nbsp;</div>
				<div class='pad-t5 expertxt'>
					<textarea class='form-control' rows='3' disabled="disabled">${(complaint.optContent)!'无' }</textarea>
				</div>
			</div>
		</div>
		<div class="text-center pad10">
			<button type="button" class="btn btn-block btn-login" onclick="backToList()">返回</button>
		</div>
    </div>

	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		
	});
	
	function backToList() {
		window.location.href=domain+"/member/complaint.html";
	}

</script>
 
</body>
</html>