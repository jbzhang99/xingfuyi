<#include "/front/commons/_top.ftl" />

<style>

.purchase-loading {
	width: 100%;
	height: 100%;
	min-height: 90px;
	position: fixed;
	left: 0;
	top: 0;
	_position: absolute;
	_width: expression(documentElement.clientWidth);
	_height: expression(documentElement.clientHeight);
	_top: expression(documentElement.scrollTop);
	background:
		url('${(domainUrlUtil.staticResources)!}/img/blank.gif') 0 0
		repeat;
	z-index: 3000
}

.purchase-loading .loading-cont {
	width: 100px;
	height: 100px;
	background:
		url('${(domainUrlUtil.staticResources)!}/img/loading04.gif')
		no-repeat;
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -50px 0 0 -50px
}
.order-summary .statistic em.price {
	float:right;
	width:auto;
}
</style>

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/member/myreciptaddress.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/areaSupport.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/order.css">

<div class='w1 header container'>
	<div id='logo'>
		<a href='${(domainUrlUtil.urlResources)!}/index.html' target='_blank' class='link1'>
			<img src='${(domainUrlUtil.staticResources)!}/img/yxkjlogo.png'>
		</a>
	</div>
	<div class='stepflex-box fr'>
		<ul>
			<li class="prev">
				<span class="fl">1.我的购物车</span><i class="fl"></i>
			</li>
			<li class="current">
				<span class="fl">2.确认订单信息</span><i class="fl"></i>
			</li>
			<li class="">
				<span class="fl">3.成功提交订单</span><i class="fl lasti"></i>
			</li>
		</ul>
	</div>
</div>
<!--  -->

<@form.form id = "invoiceForm"  method="POST" autocomplete="off">
	<!-- 收货地址ID  -->
	<input type="hidden" id="addressId" name="addressId" value="${(defaultAddress.id)!''}"/>
	<input type="hidden" id='invoiceStatus' name="invoiceStatus" value="${(orderCommitVO.invoiceStatus)!''}"/>
	<!-- 发票内容 -->
	<input type="hidden" id='invoiceType' name="invoiceType" value="${(orderCommitVO.invoiceType)!''}"/>
	<!-- 发票抬头 -->
	<input type="hidden" id='invoiceTitle' name="invoiceTitle" value="${(orderCommitVO.invoiceTitle)!''}"/>
	<!-- 支付方式名称 -->
	<input type="hidden" id='paymentName' name="paymentName" value="${(orderCommitVO.paymentName)!''}"/>
	<!-- 支付方式code -->
	<input type="hidden" id='paymentCode' name="paymentCode" value="${(orderCommitVO.paymentCode)!''}"/>
	<input type="hidden" id='integral' name="integral"/>
	
	<!-- 团购的商品ID、货品ID、商家ID、积分换购ID、购买数量 -->
	<input type="hidden" id='productId' name="productId" value="${(product.id)!0}"/>
	<input type="hidden" id='productGoodsId' name="productGoodsId" value="${(productGoods.id)!0}"/>
	<input type="hidden" id='sellerId' name="sellerId" value="${(seller.id)!0}"/>
	<input type="hidden" id='actIntegralId' name="actIntegralId" value="${(actIntegral.id)!0}"/>
	<input type="hidden" id='number' name="number" value="${(number)!0}"/>
</@form.form>
		<!--  -->
<div class='container'>
	<div class='container'>
		<div class='m-order'>
			<div class='mt'>
				<h2>填写并核对信息</h2>
			</div>
			<div class='mc'>
				<div class='checkout-steps'>
					<div class='step-tit'>
						<h3>收货人信息</h3>
						<div class='extra-r'>
							<a href='javascript:void(0);' class='ftx-05 addaddress' onclick="addOrEditAddress(0)">新增收货地址</a>
						</div>
					</div>
					<div class='step-cont'>
						<div id='consignee-addr'>
							<div class='consignee-cont consignee-off' style='position: relative;' id='consignee1'>
								<ul class="consignee-list" id='consignee-list' style='top:0px;position:relative;'>
									<#if addressList??>
										<#list addressList as address>
											<li style='display: list-item;' class='order-select' value="${(address.id)!''}" >
												<#if hasDefaultAdd??&&hasDefaultAdd='yes'&&(address.state)=1>
													<div class='consignee-item item-selected'>
														<span>默认地址</span>
														<b></b>
													</div>
													<#elseif hasDefaultAdd??&&hasDefaultAdd='no'&&address_index=0>
													<div class='consignee-item item-selected'>	
														<span>${address.memberName}</span>
														<b></b>
													</div>
													<#else>
														<div class='consignee-item'>	
															<span>${address.memberName}</span>
															<b></b>
														</div>
												</#if>
												<div class='addr-detail'>
													<span class='addr-name'>${address.memberName}</span>
													<span class='addr-info'>
														<#assign adds = address.addAll+address.addressInfo>
														${commUtil.substring(adds,30)}
													</span>
													<span class='addr-tel'>${commUtil.hideMiddleStr(address.mobile,3,4)}</span>
												</div>
												<div class='op-btns'>
													<a href='javascript:void(0)' class='ftx-05' onclick="addOrEditAddress('${address.id}')">编辑</a>
												</div>
											</li>
										</#list>
									</#if>
								</ul>
							</div>
						</div>
						<!-- 收起地址和更多地址 -->
						<div class='more-addr switch-on' id='consigneeItemAllClick' onclick='show_ConsigneeAll();'>
							<span class='ftx-05'>更多地址</span>
						</div>
						<div class='more-addr switch-off hide' id='consigneeItemHideClick' onclick='hide_ConsigneeAll()'>
							<span class='ftx-05'>收起地址</span>
						</div>
						<!-- end -->
					</div>
					<div class='hr'></div>
					<!-- 支付方式 -->
					<div id='shipAndSkuInfo'>
						<div id='payShipAndSkuInfo'>
							<!--送货清单 -->
							<div class='step-tit'>
								<h3>送货清单</h3>
							</div>
							<div class='step-cont'>
							<#if seller?? >
							<div class='shopping-lists'>
								<div class='order-common-list'>
									<div class='goods-tit'>
										<h4 id='vendor_name_h'>商家：${(seller.sellerName)!'' }</h4>
									</div>
									<#if product?? && productGoods?? && actIntegral?? >
									<!--  单品 -->
									<div class='goods-items '>
										<div class='goods-item goods-item-extra'>
											<div class='p-img'>
												<a href='${(domainUrlUtil.urlResources)!}/jifen/${(actIntegral.id)!0}.html' target="_blank">
													<img width="80" height="80" src='${(domainUrlUtil.imageResources)!}${(actIntegral.image)!""}'>
												</a>
											</div>
											<div class='goods-msg'>
												<div class='goods-msg-gel'>
													<div class='p-name'>
														<a href='${(domainUrlUtil.urlResources)!}/jifen/${(actIntegral.id)!0}.html' target='_blank'>${product.name1!''}</a>
													</div>
													<div class='p-state'>
														<strong class=''>${productGoods.normName!''}</strong>
													</div>
													<div class='p-price'>
														<strong class=''>
														${(actIntegral.price)!0} 积分
														<#if actIntegral.isWithMoney == 1>
														+ ￥${(actIntegral.priceMoney)?string("0.00")!'0.00'}
														</#if>
														</strong>
													</div>
													<div class='p-num'>
														<span> x ${(number)!0}</span>
													</div>
													<div class='p-state'>
														<span>
															<#if (actIntegral.stock)?? && (actIntegral.stock &gt; number)>
																有货
															<#else>
																<span style="color:red">无货</span>
															</#if>
														</span>
													</div>
													<div class='p-total'>
														<strong>
															${(actIntegral.price * number)!0} 积分
															<#if actIntegral.isWithMoney == 1>
															<br>￥${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}
															</#if>
														</strong>
													</div>
												</div>
											</div>
											<div class='' style='float:left;'>
												<span class='ftx-04'><!-- 7天无理由退货 --></span>
											</div>
										</div>
									</div>
									</#if>
								</div>
							</div>
							</#if>	

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='order-summary'>
			<div class='statistic' style='float:right;width:auto;'>
				<div class='list'>
					<span>
						<em class='ftx-01'>${(number)!0}</em>
						 件商品，总商品金额（原价）：
					</span>
					 <em class='price' id='warePriceId'>￥${(actIntegral.marketPrice * number)?string("0.00")!'0.00'}</em>
				</div>
				<div class='list'>
					<span>
						 总商品金额（换购）：
					</span>
					 <em class='price' id='discountAmountPriceId'>
					 	${(actIntegral.price * number)!'0'}积分
					 	<#if actIntegral.isWithMoney == 1 >
						+ ￥${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}
						</#if>
					 </em>
				</div>
				<div class='list'>
					<span>应付积分 ：</span>
					<em class='price' id='sumPayPriceId'>${(actIntegral.price * number)!'0'}</em>
				</div>
				<div class='list'>
					<span>可用积分 ：</span>
					<em class='price'>${(member.integral)!'0'}</em>
				</div>
				<#if actIntegral.isWithMoney == 1 >
				<div class='list'>
					<span>应付现金 ：</span>
					<em class='price' id='sumPayPriceId'>${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}</em>
				</div>
				</#if>
			</div>
		</div>
		<div class='clr'></div>
		<div class='trade-foot'>
			<div class='group' id='checkout-floatbar'>
				<div class='checkout-buttons'>
					<div class='sticky-wrap'>
						<div class='inner'>
							<button type='button' class='checkout-submit btn btn-danger' id='order-submit' onclick="submitOrder()"> 
								提交订单
								<b></b>
							</button>
							<span class='total'>
								应付金额：
								<strong id='payPriceId'>
									${(actIntegral.price * number)!'0'}积分
									<#if actIntegral.isWithMoney == 1 >
										+ ￥${(actIntegral.priceMoney * number)?string("0.00")!'0.00'}
									</#if>
								</strong>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class='consignee-foot'>
				<#if defaultAddress??>
         				<#assign adds = defaultAddress.addAll+defaultAddress.addressInfo>
					<p>
          				寄送至： <span id="addressDetail" title="${adds}">${commUtil.substring(adds,30)}</span>
       				</p>
       				<p>
        				收货人：<span id="consigneeName">${(defaultAddress.memberName)!'' }</span>
        					<span id="consigneeMobile">${commUtil.hideMiddleStr((defaultAddress.mobile)!'',3,4)}</span>
       				</p>
      			</#if>
			</div>
		</div>
	</div>
</div>
<!--页脚 -->
<#include "/front/commons/_endbig.ftl" />

<!-- 收货地址显示区 -->
<div class='background-layer' id='Harvest'>
</div>
<!-- end -->

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/order.js'></script>
<script type="text/javascript">

	// 新增地址
	$(".addaddress").click(function(){
		orderCenter();
		$("#Harvest").addClass("lay-display");
	});
	// 关闭收货信息层
	$(".harvest-close").click(function(){
		$("#Harvest").removeClass("lay-display");
	});

	// 居中
	function orderCenter(){
    	var v_top=($(window).height()-375)/2;
    	var v_left=($(window).width()-690)/2;
   		$(".internation").css({"left":v_left+"px","top":v_top+"px"});
	}
	// 设置弹出曾的高
	$(".background-layer").css("height",$(window).height());

	// 收货人信息鼠标移入移出
 	$('#consignee-addr').delegate('li','mouseenter',function(){
    	$(this).addClass('li-hover');
    }).delegate('li','mouseleave',function(){
		$(this).removeClass('li-hover');
	});

	//显示更多地址
	function show_ConsigneeAll(){
		$("#consigneeItemAllClick").addClass("hide");
		$("#consigneeItemHideClick").removeClass("hide");
		$("#consignee1").removeClass("consignee-off");
		if($('#consignee-list li').length>4){
			$('#consignee-addr .consignee-cont').css({
			    'height':162,
			    'position':'relative',
			    'overflow-y': 'auto'
 					 });
		}else{
			 $('#consignee-addr .consignee-cont').css({
		      'height':'auto'
		    });
		    $('#consignee-addr ul').css({
		    'position':'relative'
		    });
		}
		$(".consignee-item").parents("li").css("display","list-item");
		//设置默认地址
		addressSelect();
	}
	
	// 点击收货地址 ，样式切换，并且赋值
	function addressSelect(){
		$(".consignee-item").click(function(){
			$(this).addClass("item-selected").parent().siblings().children().removeClass("item-selected");
			var obj = $(this).addClass("item-selected").parent();
			
			//为隐藏域的收货地址ID赋值
			$("#addressId").val($(obj).val());
			
			//为结算按钮下的收货地址信息赋值
			$("#addressDetail").html($(this).siblings('.addr-detail').find(".addr-info").html());
			$("#consigneeName").html($(this).siblings('.addr-detail').find(".addr-name").html());
			$("#consigneeMobile").html($(this).siblings('.addr-detail').find(".addr-tel").html());
		});
	}
	
	function hide_ConsigneeAll() {
		//设置默认地址
		addressSelect();
		
		$("#consigneeItemAllClick").removeClass("hide");
		$("#consigneeItemHideClick").addClass("hide");
		$("#consignee1").addClass("consignee-off");
		$('#consignee-addr .addr-ctrl').hide();
		$('#consignee-addr .consignee-cont').css({
			'height':'40px',
			'overflow-y': 'hidden'
			});
		$('#consignee-addr ul').css({
		    'top': '0px',
		    'position':'absolute'
		});

		var li_selected = $(".consignee-item.item-selected").parent("li");//当前选中li
		var first_li = $(".consignee-item").parents("li").last();//当前列表第一项
		var _tempstr = first_li.find("div span").first().html();
		if(_tempstr && _tempstr.indexOf("默认地址") > -1) {
		    // 1.插入在默认地址之后
		    li_selected.clone().insertAfter(first_li);
		} else {
		    // 2.插入在地址列表第一位
		    li_selected.clone().insertBefore(first_li);
		}
		  li_selected.remove();
		  // 收起并定位第一页功能
		  $(".consignee-item").parents("li").css("display","none");
		  $(".consignee-item.item-selected").parent("li").css("display","list-item");
		  // 初始化地址组件的绑定事件，否则移动dom会导致绑定失效，因此改动组件采用delegate绑定
	}
  		
  		//提交订单 
  		function submitOrder(){
  			var actionUrl = domain + "/order/ordercommitforintegral.html";
  			var param = "";

  			//判断收货地址是否存在
  			if(isEmpty($("#addressId").val())){
  				jAlert("请添加或选择收货地址");
  				$(".ftx-05.addaddress").focus();
  				return false;
  			}
  			
  			var totalPrice = ${(actIntegral.price * number)!0};
  			var memberIntegral = ${(member.integral)!0};
  			if (parseInt(memberIntegral) < parseInt(totalPrice)) {
  				jAlert("积分不够了，请重新选择商品。");
  				return false;
  			}
      		
      		//提交订单按钮屏蔽，避免重复提交
      		$("#order-submit").attr("disabled",true);
      		// 提交loading
  			$('body').append("<div id='submit_loading' class='purchase-loading'><div class='loading-cont'></div></div>");
  			param = $("#invoiceForm").serialize();
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
							jAlert(result.message);
							return;
						} else {
							$("#submit_loading").remove();
							showSubmitErrorMessage("系统出错了~~~, 请稍后重试...");
							return;
						}
					}
				},
				error : function(error) {
					$("#order-submit").removeAttr("disabled");
					$("#submit_loading").remove();
					jAlert("亲爱的用户请不要频繁点击, 请稍后重试...");
				}
  			});
      		
  		}
  	
  	$(function(){
		// 把选中的地址显示出来
		$(".consignee-item").parents("li").css("display","none");
		$(".consignee-item.item-selected").parent("li").css("display","list-item");
		$("#addressId").val("${(defaultAddress.id)!''}")
	});
</script>
