<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/productback.css">

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/js/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/js/jquery.boxer/js/jquery.fs.boxer.js"></script>

<script>
	$(function(){
		$(".boxer").boxer({
    		fixed:true
    	});
	});
</script>
<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'>
			<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
		</strong>
		<span>
			&nbsp;>&nbsp;
			<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技</a>
		</span>
		<span>
			&nbsp;>&nbsp;
			<a href='javascript:void(0)'>投诉信息查看</a>
		</span>
	</div>
</div>

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder'>
		<h3>投诉</h3>
		<table class='table_1' id="refushtable" cellspacing="0" cellpadding="0" border='1'>
			<tbody>
				<tr>
					<th>商品名称</th>
					<th>购买数量</th>
				</tr>
				<tr>
					<td>
						<ul class='list-proinfo'>
							<li>
								<#if orderProduct??>
									<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}.html' target="_blank">
										<img src='${(domainUrlUtil.imageResources)!}${orderProduct.productLeadLittle}' width='50' height='50' title='${(orderProduct.productName)!''}'>
									</a>
									<div class='p-info-name'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}.html' target='_blank'>${(orderProduct.productName)!''}</a>
									</div>
								</#if>
							</li>
						</ul>
					</td>
					<td>${(orderProduct.number)!''}</td>
				</tr>
			</tbody>
		</table>
		<div class='apply-form' id='consignee-form'>
			<div class='repair-steps'>
					<ul>
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>投诉时间：</span>
					  	<span >${(info.complaintTime?string("yyyy-MM-dd HH:mm:ss"))!'' }</span>
					  	
					  </li>
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>状态：</span>
					  	<span>
					  		<@cont.codetext value="${(info.state)!0}" codeDiv="SELLER_COMPLAINT"/>
					  	</span>
					  </li>
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>投诉内容：</span>
					  	<span>${(info.content)!'' }</span>
					  </li>
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>投诉图片：</span>
					  	<span>
					  	<#if info.image?? && info.image != ''>
					  		<a href="${(domainUrlUtil.imageResources)!}${(info.image)!''}" class='boxer'>查看</a>
					  	<#else>
					  		无
					  	</#if>
					  	</span>
					  </li>
					  
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>商家申诉时间：</span>
					  	<span>
					  		<#if info.sellerComplaintTime??>
					  			${(info.sellerComplaintTime?string("yyyy-MM-dd HH:mm:ss"))!'' }
					  		</#if>
					  	</span>
					  </li>
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>商家申诉内容：</span>
					  	<span>${(info.sellerCompContent)!''}</span>
					  </li>
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title'>商家申诉图片：</span>
					  	<span>
					  	<#if info.sellerCompImage?? && info.sellerCompImage != ''>
					  		<a href="${(domainUrlUtil.imageResources)!}${(info.sellerCompImage)!''}" class='boxer'>查看</a>
					  	<#else>
					  		无
					  	</#if>
					  	</span>
					  </li>
					  
					  <li class="list-group-item2">
					  	<span class='s-infotitle complain-title fl'>平台意见：</span>
					  	<span>${(info.optContent)!'无' }</span>
					  </li>
					</ul>
						<br/><br/>
					<!-- end -->
					
			</div>
		</div>
	</div>
 </div>
<script type="text/javascript">
	$(function(){
		//控制左侧菜单选中
		$("#complainlist").addClass("currnet_page");
		
	});
</script>
<#include "/front/commons/_endbig.ftl" />
