<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/usersale.css"/>
	
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
					<a href='javascript:void(0)'>收入流水</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

			<!-- S 右侧主要内容 -->
			<div class='wrapper_main myorder wrapper_main-wd'>
			
				<!-- 结算明细 -->
				<div class="reminder-con reminder-Detailed">
					<h3>结算明细</h3>
					<div class="derailedCon">
						<form action="${(domainUrlUtil.urlResources)!}/member/sale-orders.html" class="mybalance-search" method="get">
							<div class="searchItm">
								<span class="search-lable">订 单 号：</span>
								<input type="text" name="q_orderSn" class="search__odn" value="${q_orderSn!''}" placeholder="请输入订单号">
							</div>
							<div class="searchItm">
								<span class="search-lable">佣 金 状 态：</span>
								<@cont.select id="q_saleState" codeDiv="SALE_ORDER_STATE" value="${q_saleState!''}" class="search-option__input"/>
							</div>
							<div class="searchItm">
								<span class="search-lable">购 买 用 户：</span>
								<input type="text" name="q_buyName" value="${q_buyName!''}" class="search__odn" placeholder="请输入用户名">
							</div>
							
							<div class="btnaera">
								 <input type="submit" class="searchorders bt" value="查询"/>
								 <a href="${(domainUrlUtil.urlResources)!}/member/sale-orders.html"  class="searchorders bt"/>重置</a>
							</div>
						</form>
					</div>
					
					<table class="searchlist-result">
						<tr class="">
							<th class="th-cell">购买人</th>
							<th class="th-cell th-cell-with20">订单号</th>
							<th class="th-cell th-cell-with">购买商品</th>
							<th class="th-cell th-cell-with">购买时间</th>
							<th class="th-cell th-cell-with">佣金状态</th>
							<th class="th-cell th-cell-with">获取佣金金额</th>
							<th class="th-cell">操作</th>
						</tr>
						
						<#if saleOrders??>
							<#list saleOrders as saleOrder>
								<tr>
									<td>${(saleOrder.buyName)!""}</td>
									<td>${(saleOrder.orderSn)!""}</td>
									<td>${(saleOrder.productName)!""}</td>
									<td>${(saleOrder.orderTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
									<td><@cont.codetext value="${(saleOrder.saleState)!0}" codeDiv="SALE_ORDER_STATE"/></td>
									<td>${(saleOrder.saleMoney)!""}</td>
									<td><a href="${(domainUrlUtil.urlResources)!}/member/sale-orders-${(saleOrder.id)!'0'}.html"/>查看</a></td>
								</tr>
							</#list>
						</#if>
						
					</table>
				</div>
				
				<#include "/front/commons/_pagination.ftl" />
			</div>
			<!-- E 右侧主要内容 -->
			
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#saleOrders").addClass("currnet_page");
		});
	</script>
	
<#include "/front/commons/_endbig.ftl" />
