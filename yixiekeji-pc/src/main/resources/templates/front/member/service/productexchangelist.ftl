<#include "/front/commons/_headbig.ftl" />
<style>
.table_1 th {
    padding: 8px 41px;
    background: #e7e7e7;
    text-align: center;
}
</style>
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
			<a href='javascript:void(0)'>换货</a>
		</span>
	</div>
</div>
<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder'>
		<h3>换货</h3>
		<table class='table_1' id="refushtable" cellspacing="0" cellpadding="0" border='1' style="">
			<tbody>
				<tr>
					<th>订单编号</th>
					<th>商品名称</th>
					<th>换货数量</th>
					<th>申请时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<#if exchangeList??>
					<#list exchangeList as exchangeinfo>
						<tr>
							<td>
								<a href='${(domainUrlUtil.urlResources)!}/member/orderdetail.html?id=${(exchangeinfo.orderId)!''}' target='_blank' class='ftx-05'>${(exchangeinfo.orderSn)!''}</a>
							</td>
							<td>
								<a href='${(domainUrlUtil.urlResources)!}/product/${(exchangeinfo.productId)!0}.html' target='_blank' class='ftx-05'>${(exchangeinfo.productName)!''}</a>
							</td>
							<td>${(exchangeinfo.number)!0}</td>
							<td>${(exchangeinfo.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
							<td>
								<#assign canComplain = 'false'/>
								<#if  exchangeinfo.state??>
					  				<#assign state = exchangeinfo.state>
					  				<#if state==1>未处理
						  				<#elseif state==2>审核通过
						  				<#elseif state==3>用户发回退件
						  				<#elseif state==4>商家收到退件
						  				<#elseif state==5>商家发出换件
						  				<#elseif state==6>原件退还
						  					<!-- 此时可以发起投诉 -->
						  					<#assign canComplain = 'true'/>
						  				<#elseif state==7>不处理
						  					<!-- 此时可以发起投诉 -->
						  					<#assign canComplain = 'true'/>
						  				<#else>
					  				</#if>
			  		    		</#if>
							</td>
							<td>
							<!-- 1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理' -->
								<a href='${(domainUrlUtil.urlResources)!}/member/exchangedetail.html?exchangeid=${(exchangeinfo.id)!''}&&orderProductId=${(exchangeinfo.orderProductId)!''}&&orderId=${(exchangeinfo.orderId)!''}'' class='acolorblue'>查看</a>
								<#if exchangeinfo.state?? && exchangeinfo.state == 2>
									<a href='${(domainUrlUtil.urlResources)!}/member/exchangeDeliverGoods.html?exchangeid=${(exchangeinfo.id)!''}' class='acolorblue'>发货</a>
								</#if>
								<#if canComplain??>
									<#if canComplain=='true'>
										| <a href='${(domainUrlUtil.urlResources)!}/member/addexchangecomplain.html?productExchangeId=${(exchangeinfo.id)!''}&&orderProductId=${(exchangeinfo.orderProductId)!''}&&orderId=${exchangeinfo.orderId}' class='acolorblue'>投诉</a>
									</#if>
								</#if>
							</td>
						</tr>
					</#list>
				</#if>
			</tbody>
		</table>
		<!-- 分页 -->
		<#include "/front/commons/_pagination.ftl" />
	</div>
	
</div>
<script type="text/javascript">
	$(function(){
		//控制左侧菜单选中
		$("#productexchange").addClass("currnet_page");
	});
</script>
<#include "/front/commons/_endbig.ftl" />