<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/salecenter.css">
<body class="bgf2">
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
	   	  	 	<a href="javascript:ejsPageBack();">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
			</div>
			<div class="flex-2 text-center">收入流水</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
    <!-- 主体 start-->
		<div class="s-container">
	        <div class="tabitems" id="saleapplymoney_more" style="margin-top:10px;">
		          <div class="item-lis">
		          	<div class="th-cell">
		              <label>佣金状态：</label><span><@cont.codetext value="${(saleOrder.saleState)!0}" codeDiv="SALE_ORDER_STATE"/></span>
		            </div>
		            <div class="th-cell">
		              <label>购 买 人：</label><span>${(saleOrder.buyName)!""}</span>
		            </div>
		            <div class="th-cell">
		              <label>订 单 号：</label><span>${(saleOrder.orderSn)!""}</span>
		            </div>
		            <div class="th-cell">
		              <label>购买商品：</label><span>${(saleOrder.productName)!""}</span>
		            </div>
		            <div class="th-cell">
		              <label>所属商家：</label><span>${(saleOrder.sellerName)!''}</span>
		            </div>
		            <div class="th-cell">
		              <label>购买时间：</label><span>${(saleOrder.orderTime)?string("yyyy-MM-dd HH:mm:ss")}</span>
		            </div>
		            <div class="th-cell">
		              <label>商品单价：</label><span>￥${(saleOrder.money)!''}</span>
		            </div>
		            <div class="th-cell">
		              <label>购买数量：</label><span>${(saleOrder.number)!''}</span>
		            </div>
		            <div class="th-cell">
		              <label>总 金 额：</label><span>￥${(saleOrder.moneyAll)!''}</span>
		            </div>
		            <div class="th-cell">
		              <label>佣金金额：</label><span>￥${(saleOrder.saleMoney)!''}（扣除活动退货金额）</span>
		            </div>
		            <div class="th-cell">
		              <label>佣金比例：</label><span>${(saleOrder.saleScale)!''}</span>
		            </div>
		            <div class="th-cell">
		              <label>退货数量：</label><span>${(saleOrder.backNumber)!'0'}</span>
		            </div>
		            <div class="th-cell">
		              <label>退货金额：</label><span>￥${(saleOrder.backMoney)!'0'}</span>
		            </div>
		            <div class="th-state"><a href="${(domainUrlUtil.urlResources)!}/member/sale-orders.html"/>返回</a></div>
		          </div>
	        </div>
       </div>
       <div class="reminder-con">
            <h3>温馨提示：</h3>
            <div class="cons">
                <p>(1)请上传真实用户资料。</p>
                <p>(2)我们会保护用户的隐私资料，绝不会向第三方透漏用户的个人资料。</p>
                <p>(3)为确保结算安全，该项信息不能修改，如需变更，请联系客服!银行卡的户名必须同姓名一致!</p>
                <p>(4)办理此银行卡时所用真实姓名，请与身份证保持一致!</p>
                <p>(5)开户行地址格式为：xx省,xx市/县,xx支行/分行,请认真核对为确保结算安全，该项信息不能修改，如需变更，请联系客服!</p>
                <p>(6)客服：QQ:43006111。</p>
            </div>
        </div>
  </div>
  <!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
</body>
</html>