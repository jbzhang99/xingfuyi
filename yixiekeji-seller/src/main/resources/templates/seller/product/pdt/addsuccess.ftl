<#include "/seller/commons/_detailheader.ftl" />

<script>
	var count = 5;
	$(function(){
		$('#timetip').html(count + '秒后自动跳转');
	});
	
	var interval = window.setInterval(go, 1000);
	function go() {
		count--;
		$('#timetip').html(count + '秒后自动跳转');
		if (count == 0) {
			window.location.href = "${domainUrlUtil.urlResources}/seller/product/onSale";
			clearInterval(interval);
		}
	}
</script>

<style>
	#timetip{
		color: red;
		font-size: 12px;
		margin-left: 60px;
	}
</style>

<div class="ban_mian Width" style="height: 300px;">


	<div style="height: 280px; overflow: hidden; margin: 10px auto;">

		<div style="font-size: 16px; color: #666666;">


			<table border="0" width="200" style='margin: 80px auto;'>
				<tr>
					<td width="30"><img
						src="${domainUrlUtil.urlResources}/resources/seller/image/success.jpg" /></td>
					<td>发布成功!</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="timetip"></div>
					</td>
				</tr>
			</table>

		</div>
	</div>




</div>

<#include "/seller/commons/_detailfooter.ftl" />
