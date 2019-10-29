<#include "/admin/commons/_detailheader.ftl" />

<script>
	function removejscssfile(filename) {
		var allsuspects = $(document).find("link");
		allsuspects.each(function() {
			if ($(this).prop("href")
					&& $(this).prop("href").indexOf(filename) != -1) {
				log.i("移除冲突css：" + $(this)[0].outerHTML);
				$(this).remove();
			}
		});
	}
	$(function() {
		removejscssfile("style.css");
	});
</script>

<link rel="stylesheet" type="text/css"
	href="${(domainUrlUtil.staticResources)!}/resources/admin/css/index.css"></link>


<div class="page-body" id="ejpage-body"
	style="overflow-y: auto; overflow-x: hidden; height: 588px;">
	<div class="clearfix mb30">
		<div class="fl w46">
			<div class="w100 bgcol">
				<div class="w100 clearfix lh42 borbot pd20">
					<span class="fl colorange ftb">今日新增订单</span>
				</div>
				<div class="w100 whbox">
					<ul>
						<li><span></span> <em>订单数：${count!0}</em></li>
						<li><span></span> <em>订单总金额：&yen;${moneyOrder!0}</em></li>
						<li><span></span> <em>已支付金额：&yen;${moneyPaidReality!0}</em></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="fr w46">
			<div class="w100 bgcol">
				<div class="w100 clearfix lh42 borbot pd20">
					<span class="fl colorange ftb">待处理事宜</span>
				</div>
				<div class="w100 whbox undo">
					<ul>
						<li><span></span> 新增商家申请：<em>${apply!0}</em>个</li>
						<li><span></span> 待审核品牌：<em>${brand!0}</em>个</li>
						<li><span></span> 待确认订单：<em>${orders!0}</em>个</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix mb30">
		<div class="fl w46">
			<div class="w100 bgcol">
				<div class="w100 clearfix lh42 borbot pd20">
					<span class="fl colorange ftb">本月订单概况</span> 
					<a class="mored-a fr" href="${domainUrlUtil.urlResources}/admin/report/orders/orderOverview">更多&gt;&gt;</a>
				</div>
				<div class="w100 whbox txcent idxpot">
					<iframe
						src="${domainUrlUtil.urlResources}/admin/index/ordersOverview"
						runat="server" width="100%" height="100%" frameborder="no"
						border="0" marginwidth="0" marginheight="0" scrolling="no"
						allowtransparency="yes"></iframe>
				</div>
			</div>
		</div>
		<div class="fr w46">
			<div class="w100 bgcol">
				<div class="w100 clearfix lh42 borbot pd20">
					<span class="fl colorange ftb">本月商品PV曲线</span> 
					<a href="${domainUrlUtil.urlResources}/admin/report/product/pvStatistics"
						class="mored-a fr">更多&gt;&gt; </a>
				</div>
				<div class="w100 whbox txcent idxpot">
					<iframe
						src="${domainUrlUtil.urlResources}/admin/index/pvStatistics"
						runat="server" width="100%" height="100%" frameborder="no"
						border="0" marginwidth="0" marginheight="0" scrolling="no"
						allowtransparency="yes"></iframe>
				</div>
			</div>
		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />
