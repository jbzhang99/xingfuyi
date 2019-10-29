<#include "/h5/commons/_head.ftl" />
<body class="bgf2">
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">填写订单</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div style="padding-bottom:60px;" id="orderMainDiv">
	  <@form.form action="" id="orderForm" name="orderForm" method="post">
	  <!-- 限时抢购的商品ID、货品ID、商家ID -->
		<input type="hidden" id='productId' name="productId" value="${(product.id)!0}"/>
		<input type="hidden" id='productGoodsId' name="productGoodsId" value="${(productGoods.id)!0}"/>
		<input type="hidden" id='sellerId' name="sellerId" value="${(seller.id)!0}"/>
		<input type="hidden" id='actIntegralId' name="actIntegralId" value="${(actIntegral.id)!0}"/>
		<input type="hidden" id='number' name="number" value="${(number)!0}"/>
      <div class="order-d-box bgfaf3">
      <a href="javascript:;" class="block" onclick="chooseAddress()">
         <ul class="flex flex-pack-justify">
           <#if address?? >
           <li class="clear">
             <span class="o-u-infor"><i class="fa fa-user"></i>&nbsp;<font>${(address.memberName)!""}</font></span>
             <span class="o-u-infor"><i class="fa fa-phone"></i>&nbsp;<font>${(commUtil.hideMiddleStr(address.mobile,3,4))!""}</font></span><br>
             <p>${(address.addAll)!""}&nbsp;${(address.addressInfo)!""}</p>
           </li>
           <#else>
           <li class="clear">
           	  去选择地址
           </li>
           </#if>
           <li><span class="fa fa-angle-right"></span></li>
         </ul>
      </a>
      </div>
      <input type="hidden" id="addressId" name="addressId" value="${(address.id)!0}">

      <div class="order-d-box">
      	<#if seller?? >
            <h2 class="cart-h2 pad10">
                <span>${(seller.sellerName)!'' }</span>
            </h2>
            <div>
           	  <#if product?? && productGoods?? >
	              <a href="${(domainUrlUtil.urlResources)!}/jifen/${(actIntegral.id)!0}.html" class="block">
	              <dl class="img-ul cart-ul flex">
	                <dt style="width:80px;height:80px;"><img src="${(domainUrlUtil.imageResources)!}${(actIntegral.image)!''}"></dt>
	                <dd class="flex-2 pos_relative">
						<div class="product_name">${product.name1!''} ${productGoods.normName!''}</div>
						<div class="clr53 font12">
							<font>${(actIntegral.price)!"0"}</font>积分
							<#if actIntegral.isWithMoney == 1 >
							+ ￥${(actIntegral.priceMoney)?string("0.00")!'0.00'}
							</#if>
						</div>
						<div>x <font>${(number)!0}</font></div>
	                </dd>
	              </dl>
	              </a>
	              <!-- S 小计 -->
	              <div class="subtotal">
	                <i>
	                	小计：${(actIntegral.price * number)!"0"}积分
	                	<#if actIntegral.isWithMoney == 1 >
						+ ￥${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}
						</#if>
	                </i>
	              </div>
	              <!-- E 小计 -->
              </#if>
            </div> 
         </#if>
      </div>

	  <!-- 支付方式名称 -->
	  <input type="hidden" id='paymentName' name="paymentName" value="${(orderCommitVO.paymentName)!''}"/>
	  <!-- 支付方式code -->
	  <input type="hidden" id='paymentCode' name="paymentCode" value="${(orderCommitVO.paymentCode)!''}"/>

      <input type="hidden" id='invoiceStatus' name="invoiceStatus" value="${(orderCommitVO.invoiceStatus)!''}"/>
	  <!-- 发票内容 -->
	  <input type="hidden" id='invoiceType' name="invoiceType" value="${(orderCommitVO.invoiceType)!''}"/>
	  <!-- 发票抬头 -->
	  <input type="hidden" id='invoiceTitle' name="invoiceTitle" value="${(orderCommitVO.invoiceTitle)!''}"/>


      <div class="order-d-box">
         <ul class="flex flex-pack-justify">
           <li> 商品金额(原价)</li>
           <li class="flex text-right">
             <span class="clr53">￥${(actIntegral.marketPrice * number)?string('0.00')!'0.00'}</span>
          </li>
         </ul>
         <ul class="flex flex-pack-justify">
           <li> 商品金额(换购)</li>
           <li class="flex text-right">
             <span class="clr53">
             	${(actIntegral.price * number)!'0'}积分
             	<#if actIntegral.isWithMoney == 1 >
				+ ￥${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}
				</#if>
             </span>
          </li>
         </ul>
         <ul class="flex flex-pack-justify">
           <li> 可用积分</li>
           <li class="flex text-right">
             <span class="clr53">${(member.integral)!'0'}</span>
          </li>
         </ul>
      </div>
	  </@form.form>
    </div>
	<!-- 主体结束 -->

	<!-- 合计层 -->
	<div class="totallayer">
		<div class="flex flex-align-center" style="height:100%; position:absolute; bottom:0; left:0; width:100%;">
			<div class="flex-2">
			   <span class="font14">积分:</span><font class="font16">&nbsp;</font>
			   <font class="font16" id="finalamountFont">
			   	${(actIntegral.price * number)!'0.00'}积分
			   	<#if actIntegral.isWithMoney == 1 >
				+ ￥${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}
				</#if>
			   </font>
			 </div>
			 <div class="go-pay padlr10 font16"><a href="javascript:;" class="block" onclick="submitOrder(this)">提交订单</a></div>
		</div>
	</div>
    
	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script>
	
	function chooseAddress() {
		var actInfo = ${(product.id)!0} + "-" + ${(productGoods.id)!0} + "-" + ${(seller.id)!0} + "-" + ${(actIntegral.id)!0} + "-" + ${(number)!0};
		window.location.href = domain+"/member/address.html?isFromOrder=1&orderType=6&actInfo=" + actInfo;
	}
	
 	// 提交订单 
	function submitOrder(obj){
		// 判断收货地址是否存在
		var addressId = $("#addressId").val();
		if(addressId == null || addressId == "" || addressId == 0){
			// alert("请添加或选择收货地址");
			$.dialog('alert','提示','请添加或选择收货地址',2000);
			return false;
		}
		
		var totalPrice = ${(actIntegral.price * number)!0};
		var memberIntegral = ${(member.integral)!0};
		if (parseInt(memberIntegral) < parseInt(totalPrice)) {
			$.dialog('alert','提示','积分不够了，请重新选择商品。',2000);
			return false;
		}
 		
 		// 提交订单按钮屏蔽，避免重复提交
 		$(obj).attr("disabled",true);
 		// 提交loading
		$('body').append("<div id='submit_loading' class='purchase-loading'><div class='loading-cont'></div></div>");
		var param = $("#orderForm").serialize();
		var actionUrl = domain + "/order/ordercommitforintegral.html";
		$.ajax({
			type : "POST",
			dataType : "json",
			url : actionUrl,
			data : param,
			async:false,
			success : function(result) {
				if (result.success) {
					var data = result.data;
					var goJumpPayfor = data.goJumpPayfor;
					var orderSn = data.mainOrder.orderSn;
					
					 //跳转到成功页面
					 if (goJumpPayfor) {
						successUrl = domain+"/order/pay.html";
						newurl = successUrl + "?orderSn=" + orderSn + "&rid=" + Math.random();
						window.setTimeout('window.location.href=newurl;', 450);
						return;
					} else {
						successUrl = domain+"/order/success.html";
						window.location.href = successUrl+"?orderSn="+orderSn+"&rd="+Math.random();
						return;
					}
	
				} else {
					// 更新token值
					$("input[name='CSRFToken']").val(result.csrfToken);
					$("#order-submit").removeAttr("disabled");
					if (result.message != null) {
						$("#submit_loading").remove();
						// alert(result.message);
		 				$.dialog('alert','提示',result.message,2000);
						return;
					} else {
						$("#submit_loading").remove();
						showSubmitErrorMessage("亲爱的用户请不要频繁点击, 请稍后重试...");
						return;
					}
				}
			},
			error : function(error) {
				$("#order-submit").removeAttr("disabled");
				$("#submit_loading").remove();
				// alert("亲爱的用户请不要频繁点击, 请稍后重试...");
 				$.dialog('alert','提示','亲爱的用户请不要频繁点击, 请稍后重试...',2000);
			}
		});
	}

</script>
</body>
</html>