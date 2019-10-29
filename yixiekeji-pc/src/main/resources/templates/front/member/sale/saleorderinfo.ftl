<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/usersale.css"/>
	
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
					<a href='javascript:void(0)'>我的分销</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

			<!-- S 右侧主要内容 -->
			<div class='wrapper_main myorder wrapper_main-wd'>

				<div class="reminder-con">
					<h3>温馨提示</h3>
					<div class="con">
						<p>(1)请上传真实用户资料。</p>
						<p>(2)我们会保护用户的隐私资料，绝不会向第三方透漏用户的个人资料。</p>
						<p>(3)申请成为分销者之后，会获得推荐码和推荐链接，可以分享给好友。</p>
						<p>(4)好友注册成功，并消费成功之后可以获得对应的佣金。</p>
						<p>(5)成为分销者之后，在商品列表页可以看到具体商品获得的佣金比例。</p>
						<p>(6)客服：QQ:43006111。</p>
					</div>
				</div>

				<div class="reminder-con reminder-conbotm">
					<h3>分佣信息</h3>
						<div class="cb-item">
							<label for="">当前状态：</label>
							<div class="ipt-con">
								<span class="examineStatus">
									<@cont.codetext value="${(saleOrder.saleState)!0}" codeDiv="SALE_ORDER_STATE"/>
								</span>
							</div>
						</div>
						<div class="cb-item">
							<label for="">购买人：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.buyName)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">订单号：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.orderSn)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">购买商品：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.productName)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">商品所属商家：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.sellerName)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">下单时间：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.orderTime)?string('yyyy.MM.dd HH:mm:ss')!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">商品单价：</label>
							<div class="ipt-con"><span class="examineInfo">￥${(saleOrder.money)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">购买数量：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.number)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">总金额：</label>
							<div class="ipt-con"><span class="examineInfo">￥${(saleOrder.moneyAll)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">佣金金额（扣除活动退货金额）：</label>
							<div class="ipt-con"><span class="examineInfo">￥${(saleOrder.saleMoney)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">佣金比例：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.saleScale)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">退货数量：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleOrder.backNumber)!'0'}</span></div>
						</div>
						<div class="cb-item">
							<label for="">退货金额：</label>
							<div class="ipt-con"><span class="examineInfo">￥${(saleOrder.backMoney)!'0'}</span></div>
						</div>
						
						<div class="cb-item">
							<label for=""></label>
							<div class="ipt-con">
								<a href="${(domainUrlUtil.urlResources)!}/member/sale-orders.html"  class="searchorders bt"/>返回</a>
							</div>
						</div>
				</div>
			</div>
			<!-- E 右侧主要内容 -->
			
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#saleOrders").addClass("currnet_page");
		});
		
	</script>
	
<#include "/front/commons/_endbig.ftl" />
