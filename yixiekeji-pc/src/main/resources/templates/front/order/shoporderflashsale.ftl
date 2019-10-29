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

.trade-foot .act-info{
	color: red;
	font-style: italic;
	display: block;
	text-align: right;
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
	<input type="hidden" id="invoiceId" name="invoiceId" >
	<input type="hidden" id="invoiceTaxNumber" name="invoiceTaxNumber" >
	<!-- 限时抢购的商品ID、货品ID、商家ID -->
	<input type="hidden" id='productId' name="productId" value="${(product.id)!0}"/>
	<input type="hidden" id='productGoodsId' name="productGoodsId" value="${(productGoods.id)!0}"/>
	<input type="hidden" id='sellerId' name="sellerId" value="${(seller.id)!0}"/>
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
													<!-- <a href='' class='ftx-05'>设为默认地址</a> -->
													<a href='javascript:void(0)' class='ftx-05' onclick="addOrEditAddress('${address.id}')">编辑</a>
													<!-- <a href='' class='ftx-05'>删除</a> -->
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
							<div class='step-tit'>
								<h3>支付方式</h3>
							</div>
							<div class='step-cont'>
								<div class='payment-list'>
									<div class='list-cont'>
										<ul id='payment-list'>
											<!-- <li class='payment-li'>
												<div class='payment-item  offline-payment' value="OFFLINE" pname="货到付款"><b></b>货到付款</div>
											</li> -->
											<li class='payment-li'>
												<div class='payment-item  item-selected online-payment' value="ONLINE" pname="在线支付"><b></b>在线支付</div>
											</li>
										</ul>
									</div>
								</div>
							</div>
							<div class='hr'></div>
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
									<#if product?? && productGoods?? && actFlashSaleProduct?? >
									<!--  单品 -->
									<div class='goods-items '>
										<div class='goods-item goods-item-extra'>
											<div class='p-img'>
												<a href='${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html?type=1' target="_blank">
													<img width="80" height="80" src='${(domainUrlUtil.imageResources)!}${product.masterImg!''}'>
												</a>
											</div>
											<div class='goods-msg'>
												<div class='goods-msg-gel'>
													<div class='p-name'>
														<a href='${(domainUrlUtil.urlResources)!}/product/${(product.id)!0}.html?type=1' target='_blank'>${product.name1!''}</a>
													</div>
													<div class='p-state'>
														<strong class=''>${productGoods.normName!''}</strong>
													</div>
													<div class='p-price'>
														<strong class=''>￥${(actFlashSaleProduct.price)?string('0.00')!0}</strong>
													</div>
													<div class='p-num'>
														<span> x 1</span>
													</div>
													<div class='p-state'>
														<span>
															<#if (actFlashSaleProduct.stock)?? && (actFlashSaleProduct.stock &gt; 0)>
																有货
															<#else>
																<span style="color:red">无货</span>
															</#if>
														</span>
													</div>
													<div class='p-total'>
														<strong>
															${(actFlashSaleProduct.price)?string('0.00')!0}
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
							<div class='hr'></div>
							<!-- 发票 -->
							<div class='step-tit'>
								<h3>发票信息</h3>
							</div>
							<div class='step-content'>
									<div class='invoice-cont'>
										<span class='mr10 invoice_title' > 不开发票</span>&nbsp;
										<span class='mr10 invoice_title_show' ></span>&nbsp;
										<span class='mr10 invoice_content_show'></span>&nbsp;
										<a href='javascript:void(0);' class='ftx-05 invoice-edit'>修改</a>

									</div>
							</div>
							<div class='hr'></div>
							<!--  -->
							<div class='order-coupon'>
								
								<!--使用积分 -->
								<#if config?? && config.integralScale?? && config.integralScale &gt; 0>
								<div class='item' id='balance-div'>
									<div class='toggle-title'>
										<a href='javascript:void(0);' class='toggler'>
											<b></b>
											使用积分
										</a>
									</div>
									<div class='toggle-wrap' id='balance-div'>
										<div class='cbox'>
											
											<div id="orderBeanItem" class="inner">
										        <div class="beans-2015">
										           	<div class="cho-con">
										 					<label for="xxx"><input type="checkbox" name="" id="integralUse">
										 					使用积分</label>
															<div class="cho-bar disabled" id="useBean">
																<input type="text" id="jdBeanVal" value="0" readonly="readonly"> 
																<span class="plus" id="plus">+</span>
																<span class="minus" id="reduction">-</span>
															</div>
															<div class="cho-r">
																<span>个</span> <span class="bean-exchange">￥0.00</span>
															</div>
													</div>
													<div class="fake-hr"></div>
													<div class="cho-result">
															共<span class="total">${(member.integral!0)}</span>个积分，本次可用 <span class="available">${(member.integral - ((member.integral)%(config.integralScale)))!0}</span>个积分 
															<span class="beans-cho-tip">使用规则(<i style="color:#edd28b;">积分满${config.integralScale!0}即可使用：每次使用积分为n*${config.integralScale!0}</i>)</span>
															<span class="clr"></span>
													</div>
										        </div>
										    </div>
										
										</div>

									</div>
								</div>
								</#if>
								<!--  结束-->
								<!--  -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='order-summary'>
			<div class='statistic' style='float:right'>
				<div class='list'>
					<span>
						<em class='ftx-01'>1</em>
						 件商品，总商品金额（原价）：
					</span>
					 <em class='price' id='warePriceId'>￥${(productGoods.mallPcPrice)?string("0.00")!'0.00'}</em>
				</div>
				<div class='list'>
					<span>
						 总商品金额（秒杀）：
					</span>
					 <em class='price' id='discountAmountPriceId'>￥${(actFlashSaleProduct.price)?string("0.00")!'0.00'}</em>
				</div>
				<div class='list'>
					<span>活动节省 ：</span>
					<em class='price' id='discountPriceId'>
						<font style="color:#FF6600"> - ￥${(productGoods.mallPcPrice - actFlashSaleProduct.price)?string("0.00")!'0.00'} </font>
					</em>
				</div>
				<div class='list'>
					<span>运费 ：</span>
					<em class='price' id='freightPriceId'>
						<font style="color:#FF6600"> + ￥${(transFee)?string("0.00")!'0.00'} </font>
					</em>
				</div>
				<div class='list' id="integralPriceListDiv" style="display:none;">
					<span>积分 ：</span>
					<em class='price' >
						<font style="color:#FF6600"> -￥<em id='integralPay'></em></font>
					</em>
				</div>
				<div class='list'>
					<span>应付总额 ：</span>
					<em class='price' id='sumPayPriceId'>￥${(actFlashSaleProduct.price + transFee)?string("0.00")!'0.00'}</em>
					<!-- 记录一个hidden值方便计算 -->
					<input type="hidden" id="sumPayPriceHidden" value="${(actFlashSaleProduct.price + transFee)?string('0.00')!'0.00'}" autocomplete="off"/>
				</div>
			</div>
		</div>
		<div class='clr'></div>
		<div class='trade-foot'>
			<div class='group' id='checkout-floatbar'>
				<div class='checkout-buttons'>
					<div class='sticky-wrap'>
						<div class='inner'>
							<#if actFlashSale??>
							<i class="act-info">秒杀订单必须在当前时间段活动结束前支付，请及时支付您的订单，谢谢！</i>
							</#if>
							<button type='button' class='checkout-submit btn btn-danger' id='order-submit' onclick="submitOrder()"> 
								提交订单
								<b></b>
							</button>
							<span class='total'>
								应付总额：
								<strong id='payPriceId'>￥${(actFlashSaleProduct.price + transFee)?string("0.00")!'0.00'}</strong>
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
<!-- 修改发票 -->
<div class='background-layer' id='mainId'>
	<div class='internation'>
		<div class='internation-title'>
			<span>发票信息</span>
		</div>
		<div class='internation-content'  style="height: 350px;">
			<div id='dialogIframe'>
				<div class='invoice-thickbox' id='invoice-tab'>	
					<div class='tab-nav'>
						<ul>
							<li id='click_1' class='tab-nav-item tab-item-selected' value='1'>普通发票<b></b></li>
							<!-- <li id='click_2' class='tab-nav-item  disabled' value='2'>电子发票<b></b></li>
							<li id='click_3' class='tab-nav-item  disabled' value='3'>增值税发票<b></b></li> -->
						</ul>
					</div>
					<div class='form' id='Invoice'>
						<div class='item'>
							<span class='label'>发票抬头：</span>
							<div class='fl'>
								<div class='invoice-list invoice-tit-list' id='invoice-tit-list'>
									<div class='invoice-item invoice-item-selected chose-invoice-item'>
										<div id='invoice-1'>
											<span class='fore2'>
												<input  type='text' class='itxt' readonly="readonly" value='个人'>
												<input  type="hidden" class='invoiceId'>
												<b></b>
											</span>
										</div>
									</div>
									<#if invoiceList??>
										<#list invoiceList as invoice>
											<div class='invoice-item chose-invoice-item'>
												<div id='invoice-1'>
													<span class='fore2'>
														<input  type='text' class='itxt' readonly="readonly" value='${invoice.content}'>
														<input  type="hidden" class='invoiceId' value='${invoice.id}'>
														<b></b>
													</span>
												</div>
												<div class='btns'>
													<a href='javascript:void(0)' class='ftx-05 edit-tit'>编辑</a>
													<a href='javascript:void(0)' class='ftx-05 update-tit hide' onclick="updateInvoice(this,'${invoice.id}')">保存</a>
													<a href='javascript:void(0)' class='ftx-05 ml10 del-tit' onclick="delInvoice(this,'${invoice.id}')">删除</a>
												</div>
											</div>
										</#list>
									</#if>
									
									<div class='invoice-item invoice-item-selected' id='save-invoice' style='display:none'>
										<div class='add-invoice-tit'>
											<input name="content" type='text' class='itxt itxt04'>
											<div class='btns'>
												<a href='javascript:void(0)' class='ftx-05 save-tit' onclick="saveInvoce(this)">保存</a>
											</div>
										</div>
									</div>
								</div>
								<div class='add-invoice' id='add-invoice'>
									<a href='javascript:void(0);' class='ftx-05' onclick='add_save();'>新增单位发票</a>
								</div>
							</div>
						</div>
					</div>
					<div class='form' id='invoiceNumber' style="display: none;">
						<div class='item'>
							<span class='label'>纳税人识别号：</span>
							<div class='fl'>
								<div class='invoice-list invoice-tit-list'>
										<div id='invoice-2'>
											<input  onkeyup="this.value=this.value.replace(/[ _`~!@#￥$%^&*……！]|\n|\r|\t/g,'')" style="width: 325px;" size="18" id="invoiceTaxNumber1" name="invoiceTaxNumber1" type='text' >
										</div>
										<div id="">
											
										</div>
								</div>
							</div>
						</div>
					</div>
					<div class='tab-box'>
						<div class='tab-con'>
							<div class='form'>
								<div class='item'>
									<span class='label'>发票内容：</span>
									<div class='fl'>
										<div class='invoice-list' id='InvoiceInformation'>
											<ul id='electro_book_content_radio'>
												<li class='invoice-item' val="noinvoice">
													不开发票
													<b></b>
												</li>
												<li class='invoice-item invoice-item-selected' val="明细">
													明细
													<b></b>
												</li>
												<!-- <li class='invoice-item' val="办公用品">办公用品<b></b></li>
												<li class='invoice-item' val="电脑配件">电脑配件<b></b></li>
												<li class='invoice-item' val="耗材">耗材<b></b></li> -->
											</ul>
										</div>
									</div>
								</div>
								<div class='item' style='margin-top:30px'>
									<span class='label'>&nbsp;</span>
									<div class='fl'>
										<div class='op-obt'>
											<a href='javascript:void(0)' class='btn btn-default save-invoice'>保存发票信息</a>
											<a href='javascript:void(0)' class='btn btn-default cancel-invoice' style='margin-left:10px'>取消</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 右上角退出按钮 -->
		<a href='javascript:void(0);' class='internation-close harvest-close-invoice'></a>
	</div>
	<!--  -->
</div>

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/order.js'></script>
<script type="text/javascript">
	// 判断是否显示纳税人识别号
	$(".chose-invoice-item").click(function(){
		var context = $(this).find(".itxt").val();
		if(context == "个人"){
			$("#invoiceNumber").hide();
		}else{
			$("#invoiceNumber").show();
		} 
	});
	// 使用积分
	$(".toggle-title").click(function(){
		var btn = $(this).parent();
		if(btn.hasClass("toggle-active")){
			btn.removeClass("toggle-active");
			$(this).siblings().css("display","none");
		}else{
			btn.addClass("toggle-active");
			$(this).siblings().css("display","block");
		}
	});
	
	//支付方式选中
	$(".payment-item").click(function(){
		$(this).addClass("item-selected").parent().siblings().children().removeClass("item-selected");
		//赋值
		$("#paymentCode").val($(this).attr("value"));
		$("#paymentName").val($(this).attr("pname"));
		
	})
	//鼠标移入
	$('.online-payment').hover(function(){	
			$(this).addClass('payment-item-hover');
	},function(){
			$(this).removeClass('payment-item-hover');
	}); 

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
  	// 修改发票窗口弹出
	$(".invoice-edit").click(function(){
		orderCenter();
		$("#mainId").addClass("lay-display");
	});
  	// 发票窗口退出
  	$(".cancel-invoice").click(function(){
		$("#mainId").removeClass("lay-display");
	});
  	// 点右上角关闭发票编辑窗口 
	$(".harvest-close-invoice").click(function(){
		$("#mainId").removeClass("lay-display");
	});
	// 发票内容点击 选中样式切换
	$("#InvoiceInformation li").click(function(){
		$(this).addClass("invoice-item-selected").siblings().removeClass("invoice-item-selected");
	});
  	function add_save(){
		$('#invoice-tit-list .invoice-item-selected').removeClass('invoice-item-selected');
		$('#save-invoice').show().addClass('invoice-item-selected').removeClass('hide').find('input').removeAttr('readonly').val('').focus();
		$('#invoice-tit-list').scrollTop($('#invoice-tit-list')[0].scrollHeight);
		$('#add-invoice').hide();
		$("#invoiceNumber").show();
		$('#invoiceTaxNumber1').val("");
	}
	//鼠标移入移出（发票抬头）
	$(".invoice-item").mouseover(function(){
		$(this).addClass("hover");
	}).mouseout(function(){
		$(this).removeClass("hover");
	});
	
	//新增发票效果 
	$(".invoice-item").click(function(){
		$(this).addClass("invoice-item-selected").siblings().removeClass("invoice-item-selected");
		var _$save = $(this).attr('id');
		if(_$save){
			return;
		}else{
			var len = $('#invoice-tit-list').find('.invoice-item').length;
			if (len < 11) {			
				if($('#add-invoice').is(":hidden")){
					$('#save-invoice').hide();
					$('#add-invoice').show();
				}
			} else {
				$('#add-invoice').hide();
			}
		}
		//将其余组件的保存按钮隐藏，输入框只读
		$(this).siblings().find('.fore2 .itxt').attr('readonly','readonly');
		$(this).siblings().find(".edit-tit").removeClass('hide').next().addClass('hide');
		var invoiceId = $(this).find('.invoiceId').val();
		$("#invoiceId").val(invoiceId);
		if(null == invoiceId || invoiceId== ''){
			return false;
		}
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
				}else {
					jAlert(data.message);
				}
			},
			error : function() {
				jAlert("数据加载失败！");
			}
		});
	});
	
	//编辑发票
	$(".edit-tit").click(function(){
		$(this).addClass('hide').next().removeClass('hide');	
		$(this).parent().prev().find('.fore2 .itxt').removeAttr('readonly').focus();
	});

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
			var oldAddressId = $("#addressId").val();
			var newAddressId = $(obj).val();
			var productId = ${(product.id)!0};
			var productGoodsId = ${(productGoods.id)!0};
			var price = ${(actFlashSaleProduct.price)?string("0.00")!'0.00'}
			if (oldAddressId != newAddressId) {
				// 如果旧的地址和新的地址不相等，则ajax调用方法计算新的运费
				$.ajax({
					type : "POST",
					url :  domain+"/order/calculateTransFeeForActivity.html",
					data : {productId:productId,productGoodsId:productGoodsId,addressId:newAddressId,number:1},
					dataType : "json",
					success : function(data) {
						if(data.success){
							// 新的总金额
							var newAmount = parseFloat(price) + parseFloat(data.data);
							newAmount = newAmount.toFixed(2);
							var newTransFee = (parseFloat(data.data)).toFixed(2);
							$("#sumPayPriceId").html("￥"+newAmount);
							$("#payPriceId").html("￥"+newAmount);
							$("#sumPayPriceHidden").val(newAmount);
							$("#freightPriceId").children("font").html(" + ￥"+newTransFee);
							
							// 清除使用积分
							$("#integralUse").prop("checked", false);
							$("#integralPriceListDiv").hide();
				  			$("#integralPay").html(0);
				  			$("#integral").val(0);
							$("#useBean").addClass("disabled");
							$("#jdBeanVal").val(0);
							$(".bean-exchange").html("￥"+ "0.00");
						}
					}
				});
			}
			
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
  		
	//保存发票抬头
	function saveInvoce(obj){
		var content = $(obj).parent().siblings("input").val();
		if(content.length==0){
			jAlert("请输入内容");
			$(obj).parent().siblings("input").focus();
			return false;
		}else if(content.length<2){
			jAlert("请输入完整公司名称");
			$(obj).parent().siblings("input").focus();
			return false;
		}
		var taxNum = $("#invoiceTaxNumber1").val();
 		if(taxNum==null || taxNum ==''){
 			jAlert("请填写纳税人识别号");
 	 	}
		$("#invoiceTaxNumber").val(taxNum);
 			//异步提交
		$.ajax({
		type : "POST",
		url :  domain+"/order/saveinvoice.html",
		data : {content:content,taxNum:taxNum},
		dataType : "json",
		success : function(data) {
			if(data.success){
				//先隐藏保存部分
    			$(obj).parent().parent().parent().hide();
    			$('#add-invoice').show();
    			//拷贝一个抬头对象，并进行操作
    			var firstObj = $(obj).parent().parent().parent().siblings(":first");
    			var newtitle = $(firstObj).clone(true);
    			$(newtitle).addClass("invoice-item-selected").siblings().removeClass("invoice-item-selected");
    			$(newtitle).find("input").val(content);
    			$(firstObj).after($(newtitle));
    			$(newtitle).find(".invoiceId").val(data.data);
    			$("#invoiceId").val(data.data);
			}else {
				jAlert(data.message);
			}
		},
		error : function() {
			jAlert("数据加载失败！");
		}
		});
  	}
  		
 	//更新发票抬头
	function updateInvoice(obj,id){
		var content = $(obj).parent().siblings().find("input").val();
		if(content.length==0){
			jAlert("请输入内容");
			$(obj).parent().siblings("input").focus();
			return false;
		}else if(content.length<2){
			jAlert("请输入完整公司名称");
			$(obj).parent().siblings("input").focus();
			return false;
		}
 			
		var invoiceTaxNumber1 = $("#invoiceTaxNumber1").val();
 		if(null == invoiceTaxNumber1 ||invoiceTaxNumber1 == ''){
 			jAlert("请输入纳税人识别号");
 			return false;
 	 	}
		$.ajax({
		type : "POST",
		url :  domain+"/order/updateinvoice.html",
		data : {content:content,id:id,taxNum:invoiceTaxNumber1},
		dataType : "json",
		success : function(data) {
			if(data.success){
				//隐藏编辑按钮 输入框不可编辑
				$(obj).addClass('hide').prev().removeClass('hide');	
				$(obj).parent().siblings().find("input").attr("readonly","readonly")
				$("#invoiceId").val(data.data);
				$("#invoiceTaxNumber1").val(invoiceTaxNumber1);
			}else {
				jAlert(data.message);
			}
		},
		error : function() {
			jAlert("数据加载失败！");
		}
		});
  	}

  		//点击  "保存发票信息" 将选定的值赋给隐藏域，并展示
  		$(".save-invoice").click(function(){
  			
  			//赋值
  			var title = $(".invoice-tit-list").find(".invoice-item.invoice-item-selected").find("input").val();
  			//发票内容
  			var content = $("#InvoiceInformation").find("li.invoice-item-selected").attr("val");
  			if(content=='noinvoice'){
  				$("#invoiceStatus").val('0');
  				$("#invoiceTitle").val('');
      			$("#invoiceType").val('');
      			$(".invoice_title").html("不开发票");
      			$(".invoice_title_show").html('');
      			$(".invoice_content_show").html('');
  			}else {
  				$("#invoiceTitle").val(title);
  				if (title == "个人") {
  					// 如果发票选择个人
  					$("#invoiceStatus").val('2');
  				} else {
  					// 纳税人识别号必填
  	  				var invoiceTaxNumber1 = $("#invoiceTaxNumber1").val();
  	  				if(invoiceTaxNumber1 == "" || invoiceTaxNumber1 == null){
  	  					jAlert("请填写纳税人识别号！");
  	  	  				return false;
  	  	  			}
  					$("#invoiceStatus").val('1');
  					$("#invoiceTaxNumber").val(invoiceTaxNumber1);
  					var invoiceId = $(".invoice-tit-list").find(".invoice-item.invoice-item-selected").find(".invoiceId").val();
					if(null == invoiceId || invoiceId == ''){
						jAlert("请先保存发票信息！");
	  	  				return false;
	  				}
  				}
      			$("#invoiceType").val(content);
      			$(".invoice_title").html("普通发票（纸质）");
      			$(".invoice_title_show").html(title);
      			$(".invoice_content_show").html(content);
  			}
  			//窗口关闭
  			$("#mainId").removeClass("lay-display");
  		});
  		
  		//删除发票
  		function delInvoice(obj,id){
  			if(confirm("确定要删除该发票信息吗？")){
   			$.ajax({
				type : "GET",
				url :  domain+"/order/deleteinvoice.html",
				data : {invoiceId:id},
				dataType : "json",
				success : function(data) {
					if(data.success){
						//移除该行发票内容
		    			$(obj).parent().parent('.invoice-item').remove();
					}else {
						jAlert(data.message);
					}
				},
				error : function() {
					jAlert("数据加载失败！");
				}
			});
  			}
  		}
  		
  		//提交订单 
  		function submitOrder(){
  			var actionUrl = domain + "/order/ordercommitforflash.html";
  			var param = "";

  			//判断收货地址是否存在
  			if(isEmpty($("#addressId").val())){
  				jAlert("请添加或选择收货地址");
  				$(".ftx-05.addaddress").focus();
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
  		
  	var usedJdBeanCount = 0;
  	var _max = ${(member.integral - ((member.integral)%(config.integralScale)))!0};
  	var exchangeRate = ${config.integralScale!0};
  	function beanExchange(){
  		var operate = $(this).text();
  		if($("#integralUse").is(":checked")){
  			var sumPayPrice = $("#sumPayPriceHidden").val();
  			var jdBeanVal = $("#jdBeanVal").val();
  			if("+" == operate && jdBeanVal < _max){
				// 如果积分使用后应付金额小于0则不能使用积分
				if (parseFloat(sumPayPrice) < 1) {
					return;
				}
  				$("#jdBeanVal").val(parseInt(jdBeanVal)+exchangeRate);
  				if(($("#jdBeanVal").val()) == (_max)){
  					$(this).addClass("disabled");
  				}
  				$("#reduction").removeClass("disabled");
  				beanUse();
  				
  				sumPayPrice = parseFloat(sumPayPrice) - 1;
  			}else if("-" == operate && jdBeanVal > 0){
  				$("#jdBeanVal").val(parseInt(jdBeanVal)-exchangeRate);
  				if(($("#jdBeanVal").val()) == 0){
  					$(this).addClass("disabled");
  				}
  				$("#plus").removeClass("disabled");
  				beanUse();
  				
  				sumPayPrice = parseFloat(sumPayPrice) + 1;
  			}else{
  				$(this).addClass("disabled");
  			}
  			$("#integralPriceListDiv").show();
  			var integralPayVal = (($("#jdBeanVal").val())/exchangeRate).toFixed(2);
  			$("#integralPay").html(integralPayVal);
  			
  			sumPayPrice = sumPayPrice.toFixed(2);
  			
  			$("#sumPayPriceId").html("￥"+sumPayPrice);
			$("#payPriceId").html("￥"+sumPayPrice);
			$("#sumPayPriceHidden").val(sumPayPrice);
  		} else {
  			$("#integralPriceListDiv").hide();
  			$("#integralPay").html(0.00);
  			$("#integral").val(0);
  			
  			var sumPayPrice = $("#sumPayPriceHidden").val();
  			sumPayPrice = sumPayPrice - $("#jdBeanVal").val();
  			$("#sumPayPriceId").html("￥"+sumPayPrice.toFixed(2));
			$("#payPriceId").html("￥"+sumPayPrice.toFixed(2));
			$("#sumPayPriceHidden").val(sumPayPrice);
  		}
  	}

  	function beanUse(){
  		var beanCount = $("#jdBeanVal").val();
  		$(".bean-exchange").html("￥"+ (beanCount/exchangeRate).toFixed(2));
  		//useCancelEditJdBean(beanCount,exchangeRate,beanCount>0?false:true);
  		$("#integral").val(beanCount);
  	}
  	
  	$(function(){
		// 把选中的地址显示出来
		$(".consignee-item").parents("li").css("display","none");
		$(".consignee-item.item-selected").parent("li").css("display","list-item");
		$("#addressId").val("${(defaultAddress.id)!''}")
		$("#sumPayPriceHidden").val(${(actFlashSaleProduct.price + transFee)?string("0.00")!'0.00'});
		if(usedJdBeanCount > 0){
			$("#integralUse").attr("checked",true);
			$("#jdBeanVal").val(usedJdBeanCount);
			$(".bean-exchange").html("￥"+ (usedJdBeanCount/exchangeRate).toFixed(2));
			$("#useBean").removeClass("disabled");	
			if(usedJdBeanCount == _max && usedJdBeanCount > 0){
				$("#plus").addClass("disabled");
			}else if(_max > 0 && usedJdBeanCount == 0){
				$("#reduction").addClass("disabled");
			}
		} else {
			$("#integralUse").attr("checked",false);
			$("#jdBeanVal").val(0);
		}
		if (${(member.integral)!0} < exchangeRate) {
			$("#integralUse").attr("checked",false);
			$("#integralUse").attr("disabled",true);
		}
		
		$("#integralUse").click(function(){
			if (${(member.integral)!0} < exchangeRate) {
				$("#integralUse").attr("checked",false);
				$("#integralUse").attr("disabled",true);
				return;
			}
			if($(this).is(":checked")){
				var sumPayPrice = $("#sumPayPriceHidden").val();
				// 如果积分使用后应付金额小于0则不能使用积分
				if (parseFloat(sumPayPrice) < 1) {
					$("#integralUse").attr("checked",false);
					return;
				}
				
				$("#useBean").removeClass("disabled");
				$("#jdBeanVal").val(exchangeRate);
				$(".bean-exchange").html("￥"+ (($("#jdBeanVal").val())/exchangeRate).toFixed(2));
				
				$("#integralPriceListDiv").show();
	  			var integralPayVal = (($("#jdBeanVal").val())/exchangeRate).toFixed(2);
	  			$("#integralPay").html(integralPayVal);
	  			
	  			sumPayPrice = parseFloat(sumPayPrice) - 1;
	  			$("#sumPayPriceId").html("￥"+sumPayPrice.toFixed(2));
				$("#payPriceId").html("￥"+sumPayPrice.toFixed(2));
				$("#sumPayPriceHidden").val(sumPayPrice);
			}else{
				
				var integralVal = $("#jdBeanVal").val();
				var sumPayPrice = $("#sumPayPriceHidden").val();
	  			sumPayPrice = parseFloat(sumPayPrice) + (integralVal/exchangeRate);
	  			
				$("#sumPayPriceId").html("￥"+sumPayPrice.toFixed(2));
				$("#payPriceId").html("￥"+sumPayPrice.toFixed(2));
				$("#sumPayPriceHidden").val(sumPayPrice);
				
				$("#integralPriceListDiv").hide();
	  			$("#integralPay").html(0.00);
	  			$("#integral").val(0);
				
				$("#useBean").addClass("disabled");
				$("#jdBeanVal").val(0);
				$(".bean-exchange").html("￥"+ "0.00");
			}
			var beanCount = $("#jdBeanVal").val();
			$("#integral").val(beanCount);
			//useCancelEditJdBean(beanCount,exchangeRate,beanCount>0?false:true);
		});
		$("#plus").click(beanExchange);
		$("#reduction").click(beanExchange);
	});
</script>
