<style>
#devForm {
	margin: 10px 20px;
}

#devForm table td {
	padding: 8px 0;
}

form input[type="button"] {
	margin: 0 100px;
}
</style>

<script>
	$(function() {
		$('#ccName').val($("#ccId").find("option:selected").text());
		$('#submitBtn').click(function() {
			if (!$('#giftNum').val()) {
				$.messager.confirm('确认',
						'确定不填写运单号码吗?为避免不必要的纠纷,请及时补充运单号码', function(r) {
					if (r) {
						$('#devForm').submit();
					}
				});
			} else {
				$('#devForm').submit();
			}
		});
	});
</script>

<form id="devForm"
	action="${domainUrlUtil.urlResources}/seller/order/orders/doDelivery"
	method="post">
	<input id="ccName" type="hidden" name="ccName" />
	<input id="ordersId" type="hidden" name="ordersId" value="${orders.id!}" />
	<input id="source" type="hidden" name="source" value="${source!}" />
	<table cellpadding="20" cellspacing="10">
		<tr>
			<td><label>物流公司</label></td>
			<td><select class="form-control" id="ccId" name="ccId"
				onchange="$('#ccName').val(this.options[this.selectedIndex].text);">
					<#list courierCompanylist as cc>
						<option value="${cc.id!}" 
							<#if orders.logisticsId==cc.id>selected='selected'</#if> >
							${cc.companyName!}
						</option> 
					</#list>
			</select></td>
		</tr>
		<tr>
			<td><label>运单号码</label></td>
			<td><input class="form-control" id="giftNum" name="giftNum" 
				value="${orders.logisticsNumber}"/></td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" id="submitBtn" class="btn btn-primary btn-danger"
				value="确定发货" /></td>
		</tr>
	</table>
</form>