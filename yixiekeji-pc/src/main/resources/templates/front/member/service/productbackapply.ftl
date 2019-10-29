<#include "/front/commons/_headbig.ftl" />

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
					<a href='javascript:void(0)'>售后申请</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />
			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder'>
				<h3>售后申请</h3>
					<table class='table_1' id="refushtable" cellspacing="0" cellpadding="0" border='1'>
						<tbody>
							<tr>
								<th width='100'>订单编号</th>
								<th>订单商品</th>
								<th width='100'>下单时间</th>
							</tr>
						
						<#if order??>
							<tr>
								<td>
									<a href='' target='_blank' class='ftx-05'>${(order.orderSn)!''}</a>
								</td>
								<td>
									<div class='list-h'>
											<ul>
										<#if (order.orderProductList)??>
											<#list (order.orderProductList) as product>
												<li>
													<div class='p-img'>
														<img src='${(domainUrlUtil.imageResources)!}${product.productLeadLittle}' width='50' height='50' title=''>
													</div>
													<div class='p-info-name'>
														<a href='${(domainUrlUtil.urlResources)!}/product/${(product.productId)!0}.html' target='_blank' title="${(product.productName)!''}">${(product.productName)!''}</a>
													</div>
													
													<a id="applyButton"  href="javascript:void(0)"  onclick='applyBack(${(product.id)!''},${order.id})' class='btn btn-default apply'>申请</a>
												</li>
											</#list>
										</#if>
											</ul>
									</div>
								</td>
								<td>
									<div class='u-name'>${(order.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</div>
								</td>
							</tr>
						</tbody>
						</#if>
						
					</table>
			</div>
		</div>
		
<script type="text/javascript">
	//控制左侧菜单选中
	$("#myorder").addClass("currnet_page");
	
	function applyBack(orderProductId,orderId){
		$("#applyButton").attr("disabled","disabled");
		  $.ajax({
			type:"GET",
			url: domain+"/member/canbackorexchange.html",
			dataType:"json",
			async : false,
			data : {orderProductId:orderProductId,orderId:orderId},
			success:function(data){
				if(data.success){
					window.location.href=domain+'/member/productbackapply.html?orderProductId='+orderProductId+'&&orderId='+orderId;
				}else{
					jAlert(data.message);
					$("#applyButton").removeAttr("disabled");
				}
			},
			error:function(){
				jAlert("异常，请重试！");
				$("#applyButton").removeAttr("disabled");
			}
		}); 
	}
</script>

<#include "/front/commons/_endbig.ftl" />
