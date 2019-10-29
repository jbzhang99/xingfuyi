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
			<a href='javascript:void(0)'>售后申请</a>
		</span>
	</div>
</div>
		
<form id ="productBackForm" action="${(domainUrlUtil.urlResources)!}/member/doproductexchange.html">
<!-- 隐藏域 -->
<input type="hidden" name="sellerId" value="${(orderProduct.sellerId)!''}">
<input type="hidden" name="seller" value="${(orderProduct.sellerId)!''}">
<input type="hidden" name="orderId" value="${(order.id)!''}"> 
<input type="hidden" name="orderSn" value="${(order.orderSn)!''}"> 

<!-- 网单id -->
<input type="hidden" name="orderProductId" value="${(orderProduct.id)!''}">
<input type="hidden" name="productId" value="${(orderProduct.productId)!''}">
<input type="hidden" name="productName" value="${(orderProduct.productName)!''}"> 
<input type="hidden" name="sellerName" value="${(orderProduct.sellerName)!''}"> 

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />
	<!-- 右侧主要内容 -->
	<div class='wrapper_main myorder'>
		<h3>售后申请</h3>
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
				<div class='repair-step repair-step-curr'>
					<!-- 服务类型 -->
					<div class='sellerPrompt'>
						<span class='label'>
							<em>*</em>
							 <span>服务类型：</span> 
						</span>
						<div class='fl'>
							<ul class='list-type list-type-new'>
								<li class='selected' urlv="member/doproductexchange.html">
									<a href='javascript:void(0);'>换货<b></b></a>
								</li>
								<li class='' urlv="member/doproductback.html">
									<a href='javascript:void(0);'>退货<b></b></a>
								</li>
							</ul>
						</div>
					</div>
					<div class='sellerPrompt'>
						<span class='label'>
							<em>*</em>
							 <span>数量：</span> 
						</span>
						<div class='fl'>
							<input type="text" name="number" id="number"><span style="color:#ccc;">可以退换货数量为:${(number)!0}</span>
						</div>
					</div>
					<!-- end -->
					<!-- 问题描述 -->
					<div class='miaoshuDiv'>
						<!-- 问题描述 -->
						<div class='sellerPrompt' style='height:132px'>
							<span class='label'>
								<em>*</em>
								<span>问题描述：</span> 
							</span>
							<div class='fl'>
								<textarea name="question" class='miaoshu-text'></textarea>
								<div style='text-align:right;'>10-500字</div>
							</div>
							<em class='em-errMes'></em>	
						</div>
						
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
		
        //服务类型
		$(".list-type li").click(function(){
			
			$(this).addClass("selected").siblings().removeClass("selected");
			$("#productBackForm").attr("action",domain+"/"+$(this).attr("urlv"));
		});
		
		//校验
		jQuery("#productBackForm").validate({
			errorPlacement : function(error, element) {
				var obj = element.parent().siblings(".em-errMes").css('display', 'block');
				error.appendTo(obj);
			},  
	        rules : {
	            "question":{required:true,minlength:10,maxlength:500},
	            "number":{required:true}
	        },
	        messages:{
	            "question":{required:"请输入内容",minlength:"不能小于10个字呦",maxlength:"不能超过500个字呦"},
	            "number":{required:"请输入数量"}
	        }
	    });
		
		//点击提交按钮事件
		$(".btn-default").click(function(){
			
			if($("#productBackForm").valid()){
				
				var number = $("#number").val();
				var num = ${(number)!0};
				if(isNaN(number) || number <= 0 || number>num){
					jAlert("请输入正确的数量！");
					return ;
				}
				$(".btn-default").attr("disabled","disabled");
				var params = $('#productBackForm').serialize();
				  $.ajax({
					type:"POST",
					url:$('#productBackForm').attr("action"),
					dataType:"json",
					async : false,
					data : params,
					success:function(data){
						if(data.success){
							//jAlert("保存成功");
							//window.location.href=domain+'/member/order.html';
							
							jAlert('保存成功', '提示',function(){
								window.location.href=domain+'/member/order.html'
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
