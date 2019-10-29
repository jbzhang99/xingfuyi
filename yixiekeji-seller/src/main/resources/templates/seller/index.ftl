<#include "/seller/commons/_head.ftl">

<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/theme.js'></script>
<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/echarts/echarts.js'></script>

<script>
	$(function(){
		//去掉loading遮罩
		$(".loading-container").remove();
		
		$("#ovediv").html('<iframe src="${domainUrlUtil.urlResources}/seller/orderOverview"'+
				'	runat="server" width="100%" height="100%" frameborder="no" border="0"'+
				'	marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>');
		
		
	});
</script>

<div class="main-container container-fluid">
	<!-- Page Container -->
	<div class="page-container">
		<!-- 左侧菜单开始 -->
		<#include "/seller/commons/_left.ftl">
		<!-- 左侧菜单结束 -->
		<!-- Page Content -->
		<div class="page-content">
			<!-- 主体头部开始 -->
			<div class="page-breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="fa fa-home"></i> <a href="javascript:;">首页</a></li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div class="page-body" id="ejpage-body"
				style="overflow-y: auto; overflow-x: hidden;">
				<div class="row" style="background: #fff; margin-bottom: 20px;">
					<div class="ejinfo-rightbox clearfix"
						style="margin-top: 10px; margin-bottom: 6px;">
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
							<div class="col-lg-9 use-info">
								<span class="tit">${(user.seller.sellerName)!}</span><span class="stylecur">（用户：${(user.name)!}）</span>
								<p class="stylecur">企业名称：${(user.seller.company)!}</p>
							</div>
						</div>
						<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
							<div class="mgb4" style="margin-bottom: 4px;">店铺动态评分：</div>
							<ul class="dyscore-ul">
								<li class="col-lg-3">
									<div>
										<span>描述</span> <span class="colr">${(seller.scoreDescription)!0}分</span>
									</div>
								</li>
								<li class="col-lg-3">
									<div>
										<span>服务</span> <span class="colr">${(seller.scoreService)!0}分</span>
									</div>
								</li>
								<li class="col-lg-3">
									<div>
										<span>发货</span> <span class="colr">${(seller.scoreDeliverGoods)!0}分</span>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
						<div
							class="databox databox-xxlg databox-vertical databox-inverted ejdatabox-xxlg">
							<div
								class="databox-top bg-whitesmoke no-padding clearfix ejdatabox-top">
								<div class="databox-row row-2 ejbg-orangebt no-padding ">
									<div
										class="databox-cell cell-1 text-align-center no-padding padding-top-5">
										<span class="databox-number colorange"><i
											class="fa fa-calendar"></i></span>
									</div>
									<div
										class="databox-cell no-padding padding-top-5 text-align-left ejcell-9">
										<span class="databox-number colorange" style="float:left">商城公告</span>
										<span class="idxnews-more" onclick="location.href='${domainUrlUtil.urlResources}/seller/systemNotice'">
											更多&gt;&gt;
										</span>
									</div>
								</div>
								<div class="databox-row50" style="width: 100%;">
									<ul>
										<#list noticelist as notice>
											<li><span></span>
												<a href="${domainUrlUtil.urlResources}/seller/systemNotice/detail?id=${notice.id}">${notice.title}</a>
											</li>
										</#list>
									</ul>
								</div>
							</div>

						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
						<div
							class="databox databox-xxlg databox-vertical databox-inverted ejdatabox-xxlg">
							<div class="databox-top bg-whitesmoke no-padding ejdatabox-top">
								<div class="databox-row row-2 ejbg-orangebt no-padding">
									<div
										class="databox-cell cell-1 text-align-center no-padding padding-top-5">
										<span class="databox-number colorange"><i
											class="fa fa-list-alt"></i></span>
									</div>
									<div
										class="databox-cell no-padding padding-top-5 text-align-left ejcell-9">
										<span class="databox-number colorange">平台联系方式</span>
									</div>
								</div>
								<div class="collx-box" style="float:left">
									<div class="collx"><label>电话：</label>18688888888</div>
									<div class="collx"><label>Q Q：</label>43006111</div>
									<div class="collx"><label>座机：</label>010-43006111</div>
								</div>
							</div>

						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
						<div id="ovediv"
							class="databox databox-xxlg databox-vertical databox-shadowed bg-white radius-bordered padding-5">
							<div class="nodata-loading">数据加载中...</div>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
						<div
							class="databox databox-xxlg databox-vertical databox-inverted ejdatabox-xxlg">
							<div style="height: 300px;"
								class="databox-top bg-whitesmoke no-padding clearfix ejdatabox-top">
								<div class="databox-row row-2 ejbg-orangebt no-padding ">
									<div
										class="databox-cell cell-1 text-align-center no-padding padding-top-5">
										<span class="databox-number colorange"><i
											class="fa fa-calendar"></i></span>
									</div>
									<div
										class="databox-cell no-padding padding-top-5 text-align-left ejcell-9">
										<span class="databox-number colorange" style="float:left">待办事宜</span>
									</div>
								</div>
								<div class="databox-row50" style="width: 100%;">
									<ul>
										<li title="待确认订单"><span></span>
											<label>待确认订单<a href="${domainUrlUtil.urlResources}/seller/order/orders/state2">${uncnf!0}</a>个</label>
										</li>
										<li><span></span>
											<label>待发货订单<a href="${domainUrlUtil.urlResources}/seller/order/orders/state3">${undev!0}</a>个</label>
										</li>
										<li><span></span>
											<label>退货申请<a href="${domainUrlUtil.urlResources}/seller/order/productBack">${backcount!0}</a>个</label>
										</li>
										<li><span></span>
											<label>换货申请<a href="${domainUrlUtil.urlResources}/seller/order/productExchange">${expcount!0}</a>个</label>
										</li>
										<li><span></span>
											<label>用户投诉<a href="${domainUrlUtil.urlResources}/seller/order/complaint">${compcount!0}</a>个</label>
										</li>
										<li><span></span>
											<label>待售商品<a href="${domainUrlUtil.urlResources}/seller/product/waitSale">${unsalecount!0}</a>个</label>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<!-- /Page Body -->

		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<script type="text/javascript">
	var _height = $(window).height() - 85;
	$('#ejpage-body').css('height', _height + 'px');
	window.onresize = function() {
		$('#ejpage-body').css('height', _height + 'px');
	}
</script>
<#include "/seller/commons/_end.ftl">
