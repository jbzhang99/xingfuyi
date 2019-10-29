<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/areaSupport.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/productback.css">

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
			<a href='javascript:void(0)'>用户换货</a>
		</span>
	</div>
</div>
		
<form id ="productExchangeForm" action="${(domainUrlUtil.urlResources)!}/member/doExchangeDeliverGoods.html">
<!-- 隐藏域 -->
<input type="hidden" name="id" value="${(memberProductExchange.id)!''}">

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder'>
		<h3>用户发货</h3>
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
									<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}' target="_blank">
										<img src='${(domainUrlUtil.imageResources)!}${orderProduct.productLeadLittle}' width='50' height='50' title='${(orderProduct.productName)!''}'>
									</a>
									<div class='p-info-name'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}' target='_blank'>${(orderProduct.productName)!''}</a>
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
				<div class='repair-step repair-step-curr'>
					<!-- 服务类型 -->
					<div class='sellerPrompt'>
						<span class='label'>
							<em>*</em>
							 <span>服务类型：</span> 
						</span>
						<div class='fl'>
							<ul class='list-type list-type-new'>
								<li class='selected' >
									<a href='javascript:void(0);'>换货<b></b></a>
								</li>
							</ul>
						</div>
					</div>
					<div class='sellerPrompt'>
						<span class='label'>
							<em>*</em>
							 <span>快递公司：</span> 
						</span>
						<div class='fl'>
							<select  id="logisticsId2" name="logisticsId2">
								<#if courierCompanyList?? && courierCompanyList?size &gt; 0>
									<#list courierCompanyList as courierCompany>
										<option value="${(courierCompany.id)!''}">${(courierCompany.companyName)!''}</option>
									</#list>
								</#if>
							</select>
						</div>
					</div>
					<div class='sellerPrompt'>
						<span class='label'>
							<em>*</em>
							 <span>快递单号：</span> 
						</span>
						<div class='fl'>
							<input type="text" name="logisticsNumber2" id="logisticsNumber2">
						</div>
						<em class='em-errMes'></em>	
					</div>
					<!-- end -->
					<div class='miaoshuDiv'>
						<div class='sellerPrompt'>
							<div class="button-center">
								<a href='javascript:void(0)' class='btn btn-default btn-7'>提交</a>
							</div>
						</div>
						<!-- end -->
					</div>
					<!-- end -->		
				</div>
			</div>
		</div>
	</div>
</div>
 
</form>
<script type="text/javascript">
	

	$(function(){
		//控制左侧菜单选中
		$("#myorder").addClass("currnet_page");
		
		
		//校验
		jQuery("#productExchangeForm").validate({
			errorPlacement : function(error, element) {
				var obj = element.parent().siblings(".em-errMes").css('display', 'block');
				error.appendTo(obj);
			},  
	        rules : {
	            "logisticsNumber2":{required:true,minlength:2,maxlength:255}
	        },
	        messages:{
	            "logisticsNumber2":{required:"请输入快递单号"}
	        }
	    });
		
		//点击提交按钮事件
		$(".btn-default").click(function(){
			var logisticsId2 = $("#logisticsId2").val();
			if(logisticsId2 == null || logisticsId2 == ''){
				jAlert("请选择物流公司");
				return false;
			}
			if($("#productExchangeForm").valid()){
				$(".btn-default").attr("disabled","disabled");
				var params = $('#productExchangeForm').serialize();
				  $.ajax({
					type:"POST",
					url:$('#productExchangeForm').attr("action"),
					dataType:"json",
					async : false,
					data : params,
					success:function(data){
						if(data.success){
							jAlert('保存成功', '提示',function(){
								window.location.href=domain+'/member/exchange.html'
							});
						}else{
							jAlert(data.message);
							$(".btn-default").removeAttr("disabled");
						}
					},
					error:function(){
						jAlert("异常，请重试！");
						$(".btn-default").removeAttr("disabled");
					}
				}); 
			}
			
		});
	});
	
</script>

<#include "/front/commons/_endbig.ftl" />
