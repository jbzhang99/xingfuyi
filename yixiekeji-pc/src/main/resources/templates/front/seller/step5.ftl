 <#include "/front/commons/_headbig.ftl" />

<link rel="icon" type="image/x-icon" href="${(domainUrlUtil.staticResources)!}/img/favicon.png" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/article.css">

<style>
#apply_store_info .alert font {
	font-size: 18px;
	color: #9C855E;
}

#count {
	font-size: 12px;
	margin-left: 20px;
}
</style>

<script>
	var count = 5;
	var url = "${url!'/index.html'}";
	//防止后退
	window.history.forward(1); 
	
	$(function(){
		$("#count").html("<i style='color:red'>"+count+"</i>秒后跳转");
	});

	var interval = window.setInterval(go, 1000);
	function go() {
		count--;
		$("#count").html("<i style='color:red'>"+count+"</i>秒后跳转");
		if(count==0) {
			window.location.href="${(domainUrlUtil.urlResources)!}/"+url;
	    	clearInterval(interval);	    	
	    }
	}
</script>

<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'> <a href=''>首页</a>
		</strong> <span> &nbsp;>&nbsp; <a href=''>商家入驻申请</a>
		</span>
	</div>
</div>
<div class='container'>
	<div class='business-layout'>
		<#if !illegal?? && !nologin?? && !applyed??>
			<div class='joinin-step'>
				<ul>
					<li class='li-curr step1 current' data-form='1'><span>签订入驻协议</span>
					</li>
					<li class='li-curr current' data-form='2'><span>公司资质信息</span></li>
					<li class='li-curr current ' data-form='3'><span>财务资质信息</span>
					</li>
					<li class='li-curr current' data-form='4'><span>店铺经营信息</span></li>
					<li class='li-curr step6 current' data-form='6'><span>店铺开通</span></li>
				</ul>
			</div>
		</#if>
		<div class='joinin-concrete'>
			<!-- 店铺经营信息 -->
			<div id='apply_store_info' class='apply_store_info'>
				<div class='alert'>
					<h4 style="text-align: center;">
						<#if illegal??>
							<font>
								非法访问 <span id="count" ></span>
							</font>
						<#else>
							<#if nologin??>
								<font>
									${nologin!}<span id="count"></span>
								</font>
							<#elseif applyed??>
								<font>
									${applyed!}<span id="count"></span>
								</font>
							<#else>
								<font>
									申请成功！请等待审核 <span id="count"></span>
								</font>
							</#if>
						</#if>
					</h4>
				</div>
			</div>
			<!-- end -->
		</div>
	</div>
</div>

<#include "/front/commons/_endbig.ftl" />