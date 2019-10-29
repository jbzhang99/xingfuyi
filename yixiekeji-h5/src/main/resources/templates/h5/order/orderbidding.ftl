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
		<input type="hidden" id='actBiddingId' name="actBiddingId" value="${(actBidding.id)!0}"/>
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
	              <a href="${(domainUrlUtil.urlResources)!}/bidding/${(actBidding.id)!0}.html" class="block">
	              <dl class="img-ul cart-ul flex">
	                <dt style="width:80px;height:80px;"><img src="${(domainUrlUtil.imageResources)!}${(actBidding.image)!''}"></dt>
	                <dd class="flex-2 pos_relative">
						<div class="product_name">${product.name1!''} ${productGoods.normName!''}</div>
						<div class="clr53 font12">
							￥<font>${(actBidding.firstPrice)?string("0.00")!"0.00"}</font>
						</div>
						<div>x <font>${(number)!0}</font></div>
	                </dd>
	              </dl>
	              </a>
	              <!-- S 小计 -->
	              <div class="subtotal">
	                <i>小计:${(actBidding.firstPrice * number)?string('0.00')!"0.00"}</i>
	              </div>
	              <!-- E 小计 -->
              </#if>
            </div> 
         </#if>
      </div>

      <div class="order-d-box">
      <a href="javascript:;" class="block">
         <ul class="flex flex-pack-justify">
           <li>支付配送</li>
           <li>
              <span id="paymentCodeSpan">在线支付&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
          </li>
         </ul>
      </a>
      <!-- <div class="flex-2 sel-btn pad-top text-center" id="paymentCodeDiv" style="display:none;">
		<a class="btn btn-default active" onclick="setPayment(this, 'ONLINE')">在线支付</a>
		<a class="btn btn-default" onclick="setPayment(this, 'OFFLINE')">货到付款</a>
	  </div> -->
      </div>
      
	  <!-- 支付方式名称 -->
	  <input type="hidden" id='paymentName' name="paymentName" value="${(orderCommitVO.paymentName)!''}"/>
	  <!-- 支付方式code -->
	  <input type="hidden" id='paymentCode' name="paymentCode" value="${(orderCommitVO.paymentCode)!''}"/>
	  
	  <input type="hidden" id="invoiceId" name="invoiceId" >
	  <input type="hidden" id="invoiceTaxNumber" name="invoiceTaxNumber" >

      <div class="order-d-box">
      <a href="javascript:;" class="block" onclick="setInvoice(this)">
         <ul class="flex flex-pack-justify">
           <li>发票信息</li>
           <li class="flex text-right">
             <div class="o-f-invoice">
             	<span id="invoiceTitleSpan">不开发票</span>
             	<br>
             	<span id="invoiceTypeSpan"></span>
             </div>
             <div><span id="invoiceDownSpan" class="fa fa-angle-down"></div>
          </li>
         </ul>
      </a>  
      </div>
      <div id="invoiceDiv" style="display:none;" class="o-box-layer">
      	<div class="pad-bt">
          发票类型: <font class="cl70">纸质发票</font>
        </div>
      	<div class="pad-bt">
          发票抬头:
          <a class="btn btn-default" onclick="newInvoiceTitle(this)">新抬头</a><br>
          <p class="padt_b10">
          <input type="text" id="newInvoiceTitleText" name="newInvoiceTitleText" class="form-control" style="display:none;">
          </p>
           <select class="form-control" id="invoiceTitleSlt" name="invoiceTitleSlt">
	          <option selected='true' value="0" >个人</option>
	          <#if invoiceList??>
	        	<#list invoiceList as invoice>
	                <option value="${(invoice.id)!''}">${(invoice.content)!''}</option>
	             </#list>
	           </#if>
          </select>
        </div>
        <input style="display: none;" id="invoiceTaxNumber1" name="" type="text" class="form-control" placeholder="请填写纳税人识别号">
      	<div class="cl70">
          发票内容:
          <font>明细</font>
        </div>
      	<div>
      		<lable id="incoiceErr" class="clr53"></lable>
      	</div>
      	<button type="button" class="btn btn-block" style="margin-top:20px;" onclick="saveInvoice()">保存并使用</button>
      	<button type="button" class="btn btn-block" style="margin-top:20px;" onclick="noInvoice()">不开发票</button>
      </div>
      <input type="hidden" id='invoiceStatus' name="invoiceStatus" value="${(orderCommitVO.invoiceStatus)!''}"/>
	  <!-- 发票内容 -->
	  <input type="hidden" id='invoiceType' name="invoiceType" value="${(orderCommitVO.invoiceType)!''}"/>
	  <!-- 发票抬头 -->
	  <input type="hidden" id='invoiceTitle' name="invoiceTitle" value="${(orderCommitVO.invoiceTitle)!''}"/>

	  <!-- 使用积分 -->
      <div class="order-d-box">
      <a href="javascript:;" class="block" onclick="useIntegral(this)">
         <ul class="flex flex-pack-justify">
           <li>
           	积分<span class="clrbf">&nbsp;共可用${(member.integral - ((member.integral)%(config.integralScale)))!0}积分</span>
           </li>
           <li class="flex text-right">
             <div class="o-f-invoice">
             	<span>使用</span><span id="useIntegralSpan">0</span><span>积分</span>
             	<br>
             	<span id="integralToMoney1">￥0.00</span>
             </div>
             <div><span id="integralDownSpan" class="fa fa-angle-down"></div>
          </li>
         </ul>
      </a>
      </div>
      <div id="integralDiv" style="display:none;" class="o-box-layer">
      	<span>使用规则(<i style="color:#edd28b;">积分满${config.integralScale!0}即可使用：每次使用积分为n*${config.integralScale!0}</i>)</span>
      	<div class="cho-con pad-top">
			<span>使用积分</span>
			<div style="padding-top:5px;">
				<a class="btn btn-default" onclick="plusIntegral()">+</a>
				<input type="text" id="integralText" value="0" readonly="readonly" class="form-control" style="display:inline-block; width:40%;">
				<a class="btn btn-default" onclick="minusIntegral()">-</a>
				<span id="integralToMoney2">￥0.00</span>
			</div>
		</div>
		<div>
			<label id="integralErr" class="clr53"></label>
		</div>
		<button type="button" class="btn btn-block" onclick="confirmUseIntegral()">确定</button>
      </div>
      <!-- 使用积分数 -->
      <input type="hidden" id="integral" name="integral" value="0"/>

      <div class="order-d-box">
         <#-- <!-- <ul class="flex flex-pack-justify">
           <li> 商品金额(原价)</li>
           <li class="flex text-right">
             <span class="clr53">￥${(actBidding.marketPrice * number)?string('0.00')!'0.00'}</span>
          </li>
         </ul> -->
         <ul class="flex flex-pack-justify">
           <li> 商品金额(订金)</li>
           <li class="flex text-right">
             <span class="clr53">￥${(actBidding.firstPrice * number)?string('0.00')!'0.00'}</span>
          </li>
         </ul>
         <#-- <!-- <ul class="flex flex-pack-justify">
           <li> 活动节省</li>
           <li class="flex text-right">
             <span class="clr53"> - ￥${((actBidding.marketPrice - actBidding.firstPrice) * number)?string('0.00')!'0.00'}</span>
          </li>
         </ul> -->
         <ul class="flex flex-pack-justify">
           <li> 运费</li>
           <li class="flex text-right">
             <span class="clr53"> + ￥${(transFee)?string('0.00')!'0.00'}</span>
          </li>
         </ul>
         <ul class="flex flex-pack-justify" style="display:none;" id="integralUl">
           <li> 积分</li>
           <li class="flex text-right">
             <span class="clr53" id="integralSpan">- ￥0.00</span>
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
			   <span class="font14">实付款:</span><font class="font16">&nbsp;¥</font><font class="font16" id="finalamountFont">${((actBidding.firstPrice * number) + transFee)?string('0.00')!'0.00'}</font>
			 </div>
			 <div class="go-pay padlr10 font16"><a href="javascript:;" class="block" onclick="submitOrder(this)">提交订单</a></div>
		</div>
	</div>
    
    <!-- 使用积分数量，记录一个hidden值方便计算 -->
	<input type="hidden" id="usedIntegralHidden" value="0"/>
    <!-- 应付款金额，记录一个hidden值方便计算 -->
	<input type="hidden" id="sumPayPriceHidden" value="${((actBidding.firstPrice * number) + transFee)!'0.00'}"/>
    
	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script>
	$(document).ready(function () {
		// 切换发票
		$("#invoiceTitleSlt").change(function(){
			var invoiceId = $("#invoiceTitleSlt").val();
			// 后台请求纳税人识别号
			$.ajax({
				type : "get",
				url :  domain+"/order/getinvoicebyid.html",
				data : {invoiceId:invoiceId},
				dataType : "json",
				success : function(data) {
					if(data.success){
		    			$("#invoiceTaxNumber1").val(data.data.taxNum);
		    			$("#invoiceId").val(invoiceId);
		    			$("#invoiceTaxNumber").val(data.data.taxNum);
		    			$("#invoiceTaxNumber1").attr("readonly","readonly")
					}else {
						jAlert(data.message);
					}
				},
				error : function() {
					jAlert("数据加载失败！");
				}
			});
			
			if(invoiceId == 0){
				$("#invoiceTaxNumber1").hide();
			}else{
				console.log("invoiceId="+invoiceId);
				$("#invoiceTaxNumber1").show();
			}
		})
	});
	
	function chooseAddress() {
		var actInfo = ${(product.id)!0} + "-" + ${(productGoods.id)!0} + "-" + ${(seller.id)!0} + "-" + ${(actBidding.id)!0} + "-" + ${(number)!0};
		window.location.href = domain+"/member/address.html?isFromOrder=1&orderType=4&actInfo=" + actInfo;
	}
	
	// 点击发票信息
	function setInvoice(_this) {
		if ($("#invoiceDiv").is(':hidden')) {
			$("#invoiceDiv").show();
			$(_this).find(".fa-angle-down").addClass("addangle-down");
		} else {
			$("#invoiceDiv").hide();
			$(_this).find(".fa-angle-down").removeClass("addangle-down");
		}
	}
	
	// 点击新抬头
	var invoiceTaxNumberNew = "";
	function newInvoiceTitle(obj) {
		// 新发票抬头输入框隐藏，显示输入框，隐藏下拉框
		if ($("#newInvoiceTitleText").is(':hidden')) {
			$("#newInvoiceTitleText").show();
			$("#invoiceTitleSlt").hide();
			$("#invoiceTaxNumber1").show();	
			invoiceTaxNumberNew = $('#invoiceTaxNumber1').val();
			$('#invoiceTaxNumber1').val("");	
			$('#newInvoiceTitleText').val("");	
			$("#invoiceId").val("");
			$("#invoiceTaxNumber1").removeAttr("readonly");
			$(obj).html("取消");
		} else {
			$("#newInvoiceTitleText").hide();
			$("#invoiceTitleSlt").show();
			$("#newInvoiceTitleText").removeAttr("readonly");
			var invoiceTitleSlt = $('#invoiceTitleSlt option:selected').val()
			if(invoiceTitleSlt != 0 ){
				$('#invoiceTaxNumber1').val(invoiceTaxNumberNew);
			}else{
				$("#invoiceTaxNumber1").hide();	
			}
			$(obj).html("新抬头");
		}
	}
	
	// 保存并使用发票信息
	function saveInvoice() {
		var title = "";
		// 新发票抬头输入框隐藏，说明是选择已有抬头
		if ($("#newInvoiceTitleText").is(':hidden')) {
			title = $('#invoiceTitleSlt option:selected').text();
			if (title == null || title == "") {
				$("#incoiceErr").html("请选择发票抬头");
				return;
			}
		} else {
			title = $("#newInvoiceTitleText").val();
			if (title == null || title == "") {
				$("#incoiceErr").html("请填写发票抬头");
				return;
			}
			if (title.length > 50) {
				$("#incoiceErr").html("发票抬头长度不能超过50");
				return;
			}
			var invoiceTaxNumber = $("#invoiceTaxNumber1").val();
			$("#invoiceTaxNumber").val(invoiceTaxNumber);
			if(null == invoiceTaxNumber||invoiceTaxNumber==''){
				$.dialog('alert','提示',"请输入纳税人识别号",2000);
				return false;
			}
			// 如果新填写抬头，且抬头不是个人，则保存数据库
			if (title != "个人") {
				var invoiceId = $("#invoiceId").val();
				if(invoiceId == null || invoiceId == ''){
					$.ajax({
						type : "POST",
						url :  domain+"/order/saveinvoice.html",
						data : {content:title,taxNum:invoiceTaxNumber},
						dataType : "json",
						success : function(data) {
							if(data.success){
								$("#invoiceId").val(data.data);
								$("#newInvoiceTitleText").attr("readonly","readonly");
								$("#invoiceTaxNumber1").attr("readonly","readonly");
							}else{
								$.dialog('alert','提示',data.message,2000);
							}
						}
						// 保存抬头，为增加用户体验流畅不管是否保存成功不做任何操作
					});
				}
			}
		}

		$("#invoiceTitleSpan").html("纸质发票-" + title);
		$("#invoiceTypeSpan").html("明细");
		$("#invoiceDiv").hide();
		
		// 给隐藏标签赋值
		var invoiceStatus = 0;
		if (title == "个人") {
			invoiceStatus = 2;
		} else {
			invoiceStatus = 1;
		}
		$("#invoiceStatus").val(invoiceStatus);
		$("#invoiceType").val("明细");
		$("#invoiceTitle").val(title);
		
		$("#invoiceDownSpan").removeClass("addangle-down");
	}
	
	// 不要发票
	function noInvoice() {
		$("#invoiceTitleSpan").html("不开发票");
		$("#invoiceTypeSpan").html("");
		$("#invoiceDiv").hide();
		
		$("#invoiceStatus").val(0);
		$("#invoiceType").val("");
		$("#invoiceTitle").val("");
		$("#invoiceDownSpan").removeClass("addangle-down");
	}
	
	// 点击使用积分
	function useIntegral(_this) {
		if ($("#integralDiv").is(':hidden')) {
			$("#integralDiv").show();
			$(_this).find(".fa-angle-down").addClass("addangle-down");
		} else {
			$("#integralDiv").hide();
			$(_this).find(".fa-angle-down").removeClass("addangle-down");
		}
	}
	
	// 确定使用积分
	function confirmUseIntegral() {
		// 获得使用积分数量
		var integral = parseInt($("#integralText").val());
		// 计算积分转换后金额
		var intMoney = (integral/exchangeRate).toFixed(2);
		
		// 计算实付款
		// 应付款
		var orderTotalPrice= parseFloat($("#sumPayPriceHidden").val());
		// 已使用的积分金额
		var usedIntegralHidden = parseFloat($("#usedIntegralHidden").val());
		// 减去上一次修改积分使用数量时产生的金额
		orderTotalPrice = orderTotalPrice + usedIntegralHidden;
		// 计算出本次修改积分数量后的应付款
		orderTotalPrice = orderTotalPrice - intMoney;
		
		// 使用积分后应付金额小于0报错
		if (orderTotalPrice < 0) {
			$("#integralErr").html("积分使用超过订单金额，请调整使用积分数量");
			return;
		}
		
		// 修改积分部分值
		$("#integralDiv").hide();
		$("#integral").val(integral);
		$("#useIntegralSpan").html(integral);
		$("#integralToMoney1").html("￥"+ intMoney);
		
		// 修改隐藏值
		$("#sumPayPriceHidden").val(orderTotalPrice.toFixed(2));
		$("#usedIntegralHidden").val(intMoney);
		
		// 修改显示值
		if (parseInt(intMoney) <= 0) {
			$("#integralUl").hide();
		} else {
			$("#integralUl").show();
		}
		$("#integralSpan").html("- ￥" + intMoney);
		$("#finalamountFont").html(orderTotalPrice.toFixed(2));
		
		$("#integralDownSpan").removeClass("addangle-down");
	}
	
  	var _max = ${(member.integral - ((member.integral)%(config.integralScale)))!0};
  	var exchangeRate = ${config.integralScale!0};
  	
  	// 增加使用积分
  	function plusIntegral() {
  		var orderTotalPrice = parseFloat($("#sumPayPriceHidden").val());
		if (orderTotalPrice <= 0) {
			return;
		}
		
		// 已使用的积分金额
		var usedIntegralHidden = parseFloat($("#usedIntegralHidden").val());
		// 减去上一次修改积分使用数量时产生的金额
		orderTotalPrice = orderTotalPrice + usedIntegralHidden;
		
  		var integral = $("#integralText").val();
  		// 增加后积分数
  		var plusIntegral = parseInt(integral) + exchangeRate;
  		if (plusIntegral > _max) {
  			// 增加后积分数大于最大积分数则不再增加
  			return;
  		}
  		
  		// 计算金额
		var intMoney = (plusIntegral/exchangeRate).toFixed(2);
  		// 增加后应付款小于等于0则不能再增加
  		if ((orderTotalPrice - intMoney) < 0) {
  			return;
  		}
  		
		$("#integralText").val(plusIntegral);
		$("#integral").val(plusIntegral);

		$("#integralToMoney2").html("￥"+ intMoney);
  	}
  	
 	// 减少使用积分
  	function minusIntegral() {
  		var integral = $("#integralText").val();
  		// 减少后积分数
  		var minusIntegral = parseInt(integral) - exchangeRate;
  		if (minusIntegral < 0) {
  			// 减少后积分数小于0则不再减少
  			return;
  		} else {
  			$("#integralText").val(minusIntegral);
  			$("#integral").val(minusIntegral);
  			
  			// 计算金额
  			var intMoney = (minusIntegral/exchangeRate).toFixed(2);
  			$("#integralToMoney2").html("￥"+ intMoney);
  		}
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
		
 		// 提交订单按钮屏蔽，避免重复提交
 		$(obj).attr("disabled",true);
 		// 提交loading
		$('body').append("<div id='submit_loading' class='purchase-loading'><div class='loading-cont'></div></div>");
		var param = $("#orderForm").serialize();
		var actionUrl = domain + "/order/ordercommitforbidding.html";
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