
		<#if complaintList?? && complaintList?size &gt; 0 >
		<#list  complaintList as complaint>
		    <div class="oder-list sev-list">
		    	<h2 class="flex flex-pack-justify sev_regoods">
		    		<div>
		    		  <p class="mar-bt">订单编号：${(complaint.ordersProduct.ordersSn)!0}</p>
		    		  <p>申请时间：${(complaint.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</p>
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
		    	<a href="${(domainUrlUtil.urlResources)!}/product/${(complaint.ordersProduct.productId)!0}.html?goodId=${(complaint.ordersProduct.productGoodsId)!0}" class="block">
			    	<dl class="img-ul flex">
			    	  <dt style="width:80px; height:80px;"><img src="${(domainUrlUtil.imageResources)!}${complaint.product.masterImg}"></dt>
			    	  <dd class="flex-2">
			    	    <div class="product_name">${(complaint.ordersProduct.productName)!''}&nbsp;${(complaint.ordersProduct.specInfo)!''}</div>
			    	    <div>x${(complaint.ordersProduct.number)!0}</div>
			    	  </dd>
			    	</dl>
		    	</a>
		    	<div class="order-set-btn">
		    		<a href="${(domainUrlUtil.urlResources)!}/member/complaintdetail.html?id=${(complaint.id)!0}" class="btn">查看</a>
				</div>
		    </div>
	    </#list>
		</#if>
