<style>
.ra_div a {
	display: block;
	border-bottom: dotted 1px #dedede;
	padding-bottom: 3px;
	text-decoration: none;
	color: #000;
	font-weight: normal;
	padding-top: 5px;
	padding-left: 15px;
}
</style>
<div id="aa" class="easyui-accordion" data-options="fit:true"
	style="height: auto;">
	<@shiro.hasPermission name="/seller_menu_order">
	<div title="交易管理" class="ra_div">
		<@shiro.hasPermission name="/seller/order/orders">
		<a id='14' href="javascript:void(0);" onclick="addTab('全部订单', '${domainUrlUtil.urlResources}/seller/order/orders')">全部订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/orders/state1">
		<a id='321' href="javascript:void(0);" onclick="addTab('未付款订单', '${domainUrlUtil.urlResources}/seller/order/orders/state1')">未付款订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/orders/state2">
		<a id='322' href="javascript:void(0);" onclick="addTab('待确认订单', '${domainUrlUtil.urlResources}/seller/order/orders/state2')">待确认订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/orders/state3">
		<a id='323' href="javascript:void(0);" onclick="addTab('待发货订单', '${domainUrlUtil.urlResources}/seller/order/orders/state3')">待发货订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/orders/state4">
		<a id='324' href="javascript:void(0);" onclick="addTab('已发货订单', '${domainUrlUtil.urlResources}/seller/order/orders/state4')">已发货订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/orders/state5">
		<a id='325' href="javascript:void(0);" onclick="addTab('已完成订单', '${domainUrlUtil.urlResources}/seller/order/orders/state5')">已完成订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/orders/state6">
		<a id='326' href="javascript:void(0);" onclick="addTab('已取消订单', '${domainUrlUtil.urlResources}/seller/order/orders/state6')">已取消订单</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/productBack">
		<a id='15' href="javascript:void(0);" onclick="addTab('退货管理', '${domainUrlUtil.urlResources}/seller/order/productBack')">退货管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/productExchange">
		<a id='16' href="javascript:void(0);" onclick="addTab('换货管理', '${domainUrlUtil.urlResources}/seller/order/productExchange')">换货管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/order/complaint">
		<a id='388' href="javascript:void(0);" onclick="addTab('投诉管理', '${domainUrlUtil.urlResources}/seller/order/complaint')">投诉管理</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_product">
	<div title="商品管理" class="ra_div">
		<@shiro.hasPermission name="/seller/product/sellerCate">
		<a id='20' href="javascript:void(0);" onclick="addTab('店铺分类', '${domainUrlUtil.urlResources}/seller/product/sellerCate')">店铺分类</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/cate">
		<a id='22' href="javascript:void(0);" onclick="addTab('商品分类申请', '${domainUrlUtil.urlResources}/seller/product/cate')">商品分类申请</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/manager">
		<a id='311' href="javascript:void(0);" onclick="addTab('已申请分类', '${domainUrlUtil.urlResources}/seller/product/manager')">已申请分类</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/chooseCate">
		<a id='21' href="javascript:void(0);" onclick="addTab('发布商品', '${domainUrlUtil.urlResources}/seller/product/chooseCate')">发布商品</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/saleAll">
		<a id='18' href="javascript:void(0);" onclick="addTab('商品管理', '${domainUrlUtil.urlResources}/seller/product/saleAll')">全部商品</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/waitSale">
		<a id='167' href="javascript:void(0);" onclick="addTab('待售商品', '${domainUrlUtil.urlResources}/seller/product/waitSale')">待售商品</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/onSale">
		<a id='168' href="javascript:void(0);" onclick="addTab('在售商品', '${domainUrlUtil.urlResources}/seller/product/onSale')">在售商品</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/delSale">
		<a id='169' href="javascript:void(0);" onclick="addTab('已删除商品', '${domainUrlUtil.urlResources}/seller/product/delSale')">已删除商品</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/product/brand">
		<a id='19' href="javascript:void(0);" onclick="addTab('品牌管理', '${domainUrlUtil.urlResources}/seller/product/brand')">品牌管理</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_promotion">
	<div title="促销管理" class="ra_div">
		<@shiro.hasPermission name="/seller/promotion/coupon">
		<a href="javascript:void(0);" onclick="addTab('优惠券管理', '${domainUrlUtil.urlResources}/seller/promotion/coupon')">优惠券管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/promotion/full">
		<a href="javascript:void(0);" onclick="addTab('订单满减', '${domainUrlUtil.urlResources}/seller/promotion/full')">订单满减</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/promotion/single">
		<a href="javascript:void(0);" onclick="addTab('单品立减', '${domainUrlUtil.urlResources}/seller/promotion/single')">单品立减</a>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="/seller/promotion/actintegral">
		<a href="javascript:void(0);" onclick="addTab('积分商城管理', '${domainUrlUtil.urlResources}/seller/promotion/actintegral')">积分商城管理</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_member">
	<div title="会员管理" class="ra_div">
		<@shiro.hasPermission name="/seller/member/productask">
		<a id='361' href="javascript:void(0);" onclick="addTab('会员咨询管理', '${domainUrlUtil.urlResources}/seller/member/productask')">会员咨询管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/member/productcomments">
		<a id='362' href="javascript:void(0);" onclick="addTab('会员评价管理', '${domainUrlUtil.urlResources}/seller/member/productcomments')">会员评价管理</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_mobile">
	<div title="移动端管理" class="ra_div">
		<@shiro.hasPermission name="/seller/mindex/banner">
		<a id='421' href="javascript:void(0);" onclick="addTab('首页轮播图', '${domainUrlUtil.urlResources}/seller/mindex/banner')">首页轮播图</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/mindex/floor">
		<a id='425' href="javascript:void(0);" onclick="addTab('移动端首页楼层', '${domainUrlUtil.urlResources}/seller/mindex/floor')">移动端首页楼层</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/mindex/floordata">
		<a id='429' href="javascript:void(0);" onclick="addTab('楼层数据', '${domainUrlUtil.urlResources}/seller/mindex/floordata')">楼层数据</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_pcindex">
	<div title="PC端首页管理" class="ra_div">
		<@shiro.hasPermission name="/seller/pcindex/sellerinfo">
		<a id='486' href="javascript:void(0);" onclick="addTab('PC端首页信息', '${domainUrlUtil.urlResources}/seller/pcindex/sellerinfo')">PC端首页信息</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/pcindex/banner">
		<a id='487' href="javascript:void(0);" onclick="addTab('PC首页轮播图', '${domainUrlUtil.urlResources}/seller/pcindex/banner')">PC首页轮播图</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/pcindex/recommend">
		<a id='494' href="javascript:void(0);" onclick="addTab('PC推荐类型', '${domainUrlUtil.urlResources}/seller/pcindex/recommend')">PC推荐类型</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/pcindex/recommenddata">
		<a id='501' href="javascript:void(0);" onclick="addTab('PC推荐类型数据', '${domainUrlUtil.urlResources}/seller/pcindex/recommenddata')">PC推荐类型数据</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_operate">
	<div title="运营管理" class="ra_div">
		<@shiro.hasPermission name="/seller/operate/sellerqq">
		<a id='24' href="javascript:void(0);" onclick="addTab('客服QQ设置', '${domainUrlUtil.urlResources}/seller/operate/sellerqq')">客服QQ设置</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/operate/sellerTransport">
		<a id='26' href="javascript:void(0);" onclick="addTab('运费设置', '${domainUrlUtil.urlResources}/seller/operate/sellerTransport')">运费设置</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_system">
	<div title="系统管理" class="ra_div">
		<@shiro.hasPermission name="/seller/system/role">
		<a id='372' href="javascript:void(0);" onclick="addTab('角色管理', '${domainUrlUtil.urlResources}/seller/system/role')">角色管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/system/sellerUser">
		<a id='377' href="javascript:void(0);" onclick="addTab('管理员管理', '${domainUrlUtil.urlResources}/seller/system/sellerUser')">管理员管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/system/sellerUser/editpwd">
		<a id='400' href="javascript:void(0);" onclick="addTab('修改密码', '${domainUrlUtil.urlResources}/seller/system/sellerUser/editpwd')">修改密码</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
	<@shiro.hasPermission name="/seller_menu_settlement">
	<div title="统计结算" class="ra_div">
		<@shiro.hasPermission name="/seller/settlement">
		<a id='349' href="javascript:void(0);" onclick="addTab('结算管理', '${domainUrlUtil.urlResources}/seller/settlement')">结算管理</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/orderday">
		<a id='366' href="javascript:void(0);" onclick="addTab('每日订单统计', '${domainUrlUtil.urlResources}/seller/report/orderday')">每日订单统计</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/productday">
		<a id='367' href="javascript:void(0);" onclick="addTab('每日商品统计', '${domainUrlUtil.urlResources}/seller/report/productday')">每日商品统计</a>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="/seller/report/orders/orderOverview">
		<a href="javascript:void(0);" onclick="addTab('订单概况', '${domainUrlUtil.urlResources}/seller/report/orders/orderOverview')">订单概况</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/product/productSale">
		<a href="javascript:void(0);" onclick="addTab('商品销量统计', '${domainUrlUtil.urlResources}/seller/report/product/productSale')">商品销量统计</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/orders/saleOverview">
		<a href="javascript:void(0);" onclick="addTab('订单销量统计', '${domainUrlUtil.urlResources}/seller/report/orders/saleOverview')">订单销量统计</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/product/phurchaseRate">
		<a href="javascript:void(0);" onclick="addTab('购买率统计', '${domainUrlUtil.urlResources}/seller/report/product/phurchaseRate')">购买率统计</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/orders/goodsReturnRate">
		<a href="javascript:void(0);" onclick="addTab('退货率统计', '${domainUrlUtil.urlResources}/seller/report/orders/goodsReturnRate')">退货率统计</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/seller/report/orders/CPIstatistics">
		<a href="javascript:void(0);" onclick="addTab('人均消费统计', '${domainUrlUtil.urlResources}/seller/report/orders/CPIstatistics')">人均消费统计</a>
		</@shiro.hasPermission>
	</div>
	</@shiro.hasPermission>
</div>