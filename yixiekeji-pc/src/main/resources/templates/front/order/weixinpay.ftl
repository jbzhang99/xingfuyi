<#include "/front/commons/_top.ftl" />
<link  rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/order.css'>
<link  rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/css/payfor.css'>
<div class='w1 header container'>
	<div id='logo'>
		<a href='${(domainUrlUtil.urlResources)!}/index.html' target='_blank' class='link1'>
			<img src='${(domainUrlUtil.staticResources)!}/img/yxkjlogo.png'>
		</a>
	</div>
	<div class='stepflex-box fr'>
		<ul>
			<li class="">
				<span class="fl">1.我的购物车</span><i class="fl"></i>
			</li>
			<li class="prev">
				<span class="fl">2.确认订单信息</span><i class="fl"></i>
			</li>
			<li class="current">
				<span class="fl">3.成功提交订单</span><i class="fl lasti"></i>
			</li>
		</ul>
	</div>
</div>
	
	<form id="payForm" method="GET" >
		<!-- 支付 -->
		<div id='PayforBox'>
			<div class='container'>
				<!-- 订单详情 -->
				<div class='order-information'>
					<div class='p-left'>
						<h3 class='p-title'>
								订单号：${(showWeiXinorderNo)!}
						</h3>
						<p class='p-tips'>请您在提交订单后<span class='font-red'>24小时内</span>完成支付，否则订单会自动取消</p>
					</div>
					<div class='p-right'>
						<div class='pay-price'>
							<em>应付金额</em>
							<strong>${(showWeiXinMoney)!'' }</strong>
							<em>元</em>
						</div>
					</div>
					
					<br>
					
					<div class='clr'></div>
					
				</div>
				
				<!-- end -->
				<!-- 支付方式 -->
				<div class='payment'>
					<div>
						<span>
							<span style="font-size: 20px">微信支付</span>
						     &nbsp;&nbsp;&nbsp;&nbsp;  如二维码过期，<a href="javascript:window.location.reload();" style="color: #ff5d5b;"class='font-red'>刷新</a>页面重新获取二维码。</div>
						
						</span>
					</div>
					<div style="margin-left: 100px">
						<img src="${(domainUrlUtil.urlResources)!}/wx/createTDCode.html?codeUrl=${(codeUrl)!}" width="298px">
					</div>
					<div style="margin-left: 450px;margin-top: -300px;">
						<img src='${(domainUrlUtil.staticResources)!}/img/phone-bg.png' >
					</div>
					<div style="margin-left: 100px;margin-top: -120px;background-color:#ff5d5b; width:300px">
                         <img src='${(domainUrlUtil.staticResources)!}/img/saoma.png' width="298px" height="80px" >
                    </div>	
				</div>
			</div>
		</div>
		
	</form>
		<!-- end -->
		
<script type="text/javascript">
		$(document).ready(function () {
		    setInterval("ajaxstatus()", 3000);    
		});
		var orderNo = "${showWeiXinorderNo}";
		var url = "${(domainUrlUtil.urlResources)!}";
		function ajaxstatus() {
	        $.ajax({
	            url: url+"/wx/returnUrl.html",
	            type: "GET",
	            dataType:"json",
	            async : false,
	            cache:false,
	            data: {orderNo:orderNo},
	            success: function (data, textStatus) {
	                if (data.data) {
	                	window.location.href = url+"/wx/returnUrlSuccess.html"; 
	                }
	            }
	        });
		  }
</script>
	
<#include "/front/commons/_endbig.ftl" />