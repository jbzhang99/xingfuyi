<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/settlement"/>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/easyui/datagrid-detailview.js"></script>
<script language="javascript">
var codeBox;
var paycodeBox;
var ivocodeBox;
var backCodeBox;
var monCodeBox;
$(function() {

	<#noescape>
		codeBox = eval('(${initJSCodeContainer("ORDERS_ORDER_STATE")})');
		paycodeBox = eval('(${initJSCodeContainer("ORDER_PAYMENT_STATUS")})');
		ivocodeBox = eval('(${initJSCodeContainer("ORDER_INVOICE_STATUS")})');
		backCodeBox = eval('(${initJSCodeContainer("MEM_PB_STATE_RETURN")})');
		monCodeBox = eval('(${initJSCodeContainer("MEM_PB_STATE_MONEY")})');
	</#noescape>

	$("#back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/settlement";
	});
	
	$("#checkDoubt").click(function(){
		var sellerDoubt = $("#sellerDoubt").val();
		if (sellerDoubt == null || sellerDoubt == "") {
			$.messager.alert('提示','请输入质疑理由！');
			return;
		}
		
		$.messager.confirm('确认', '确定对该结算账单提出质疑吗？', function(r) {
			if (r) {
				$.messager.progress({
					text : "提交中..."
				});
		 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/seller/settlement/checkDoubt")
	  				 .attr("method", "POST")
	  				 .submit();
				$.messager.progress('close');
			}
		});
	});
	
	$("#checkOver").click(function(){
		$.messager.confirm('确认', '确定通过该结算账单吗？', function(r) {
			if (r) {
				$.messager.progress({
					text : "提交中..."
				});
		 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/seller/settlement/checkOver")
	  				 .attr("method", "POST")
	  				 .submit();
				$.messager.progress('close');
			}
		});
	});
	
	$("#payOver").click(function(){
		$.messager.confirm('确认', '确定已经收到平台打款了吗？', function(r) {
			if (r) {
				$.messager.progress({
					text : "提交中..."
				});
		 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/seller/settlement/payOver")
	  				 .attr("method", "POST")
	  				 .submit();
				$.messager.progress('close');
			}
		});
	});
	
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
});

	function getState(value, row, index) {
		var box = codeBox["ORDERS_ORDER_STATE"][value];
		return box;
	}
	
	function paymentStatus(value, row, index) {
		var box = paycodeBox["ORDER_PAYMENT_STATUS"][value];
		return box;
	}
	
	function invoiceStatus(value, row, index) {
		var box = ivocodeBox["ORDER_INVOICE_STATUS"][value];
		return box;
	}
	
	function detailFormatter(index,row){
        return '<div style="padding:2px"><table class="ddv"></table></div>';
    }
	
	function proBackState(value, row, index) {
		var box = backCodeBox["MEM_PB_STATE_RETURN"][value];
		return box;
	}
	
	function proMonState(value, row, index) {
		var box = monCodeBox["MEM_PB_STATE_MONEY"][value];
		return box;
	}
	
	function onExpandRow(index,row){
        var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
        ddv.datagrid({
           fitColumns:true,
           singleSelect:true,
           method:'get',
           url:'${domainUrlUtil.urlResources}/seller/settlement/getSettlementOp?orderId='+row.id,
			loadMsg : '数据加载中...',
			height : 'auto',
			columns : [[{
				field : 'productName',
				title : '货品名称',
				width : 150,
				align : 'left',
				halign : 'center'
			}, {
				field : 'moneyPrice',
				title : '商品单价',
				width : 30,
				align : 'center'
			}, {
				field : 'number',
				title : '商品数量',
				width : 30,
				align : 'center'
			}, {
				field : 'moneyAmount',
				title : '网单金额',
				width : 30,
				align : 'center'
			}, {
				field : 'productCateName',
				title : '所属分类',
				width : 50,
				align : 'center'
			}, {
				field : 'scaling',
				title : '佣金比例',
				width : 30,
				align : 'center'
			}, {
				field : 'commision',
				title : '佣金',
				width : 30,
				align : 'center'
			}]],
			onResize : function() {
				$('#dataGrid').datagrid('fixDetailRowHeight',index);
			},
			onLoadSuccess : function() {
				setTimeout(function() {
					$('#dataGrid').datagrid('fixDetailRowHeight',index);
				}, 0);
			}
		});
	}
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
					<li><i class="fa fa-home"></i> <a
						href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
					</li>
					<li><a
						href="${domainUrlUtil.urlResources}/seller/settlement">结算管理</a>
					</li>
					<li class="active">详情</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="whtitdiv" data-options="region:'north'"
					style="padding-top: 10px; overflow-x: hidden; overflow-y: auto;">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div style="padding-top: 10px;">基本信息</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

						<@form.form method="post" class="form-horizontal" id="addForm" 
							name="addForm" enctype="multipart/form-data">
							<input type="hidden" id="id" name="id" value="${(settlement.id)!''}" />
							
							<div class="form-group">
								<label class="col-lg-2 control-label">结算周期：</label> 
								<div class="col-lg-4">
									<input type="text" disabled="disabled"
										value="${(settlement.settleCycle)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">商家名称：</label> 
								<div class="col-lg-4">
									<input type="text" disabled="disabled"
										value="${(settlement.sellerName)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">订单总额：</label>
								<div class="col-lg-4"> 
									<input type="text" disabled="disabled"
										value="${(settlement.moneyOrder)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">余额支付总额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
									value="${(settlement.moneyPaidBalance)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">现金支付总额：</label>
								<div class="col-lg-4"> 
									<input type="text" disabled
									value="${(settlement.moneyPaidReality)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">积分转换总额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
									value="${(settlement.moneyIntegral)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">积分总额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
									value="${(settlement.integral)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">退货总额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
									value="${(settlement.moneyBack)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label"></label> 
								<label class="col-lg-10 ejava-errinforight">
									  退货申请的退款时间在结算周期内的所有退货退款总额，退货申请表的退款金额和
	                           	</label>
							</div>
							
							<div class="form-group">
									<label class="col-lg-2 control-label">退回积分总额：</label> 
									<div class="col-lg-4">
										<input type="text" disabled
										value="${(settlement.moneyIntegralBack)!''}" class="form-control" />
									</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label"></label> 
								<label class="col-lg-10 ejava-errinforight">
									  退货申请的退款时间在结算周期内的所有退货退回积分金额总额，退货申请表的退回积分金额和
	                           	</label>
							</div>

							<div class="form-group">
								<label class="col-lg-2 control-label">其他金额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
									value="${(settlement.moneyOther)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">其他金额类型: </label> 
								<div class="col-lg-4">
									<@cont.select
										id="moneyOtherType" value="${(settlement.moneyOtherType)!''}"
										codeDiv="SETTLE_OTHER_TYPE" disabled="disabled"
										class="form-control" mode="1"/>
								</div>
							</div>
							
							<div class="form-group">
								<p class="p12 p-item">
									<label class="col-lg-2 control-label">其他金额理由：</label> 
									<div class="col-lg-4">
										<input type="text" disabled
											value="${(settlement.moneyOtherReason)!''}" class="form-control" />
									</div>
								</p>
							</div>

							<div class="form-group">
								<label class="col-lg-2 control-label">佣金总额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
											value="${(settlement.commision)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label"></label> 
								<label class="col-lg-10 ejava-errinforight">
									 佣金总额=各网单金额*佣金比例之和
	                           	</label>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">系统计算总额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
										value="${(settlement.payable)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label"></label> 
								<label class="col-lg-10 ejava-errinforight">
									  系统计算总额=订单总额-退款总额-退回积分总额-佣金总额 
	                           	</label>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">三级分销佣金总金额：</label> 
								<div class="col-lg-4">
									<input type="text" disabled
										value="${(settlement.moneySale)!''}" class="form-control" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label"></label> 
								<label class="col-lg-10 ejava-errinforight">
									  如果设置商家承担佣金，应付金额=应付金额-三级分销总金额
	                           	</label>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">承担佣金: </label> 
								<div class="col-lg-4">
									<@cont.select id="status"
										value="${(settlement.saleSet)!''}" codeDiv="SALE_SETTING"
										disabled="disabled" class="form-control" mode="1"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">应支付总额：</label> 
								<div class="col-lg-4">
									<#if
									settlement.moneyOtherType?? && settlement.moneyOtherType == 1>
									<input type="text" disabled
										value="${(settlement.payable + settlement.moneyOther)!''}" class="form-control" />
									<#elseif settlement.moneyOtherType?? && settlement.moneyOtherType == 2>
									<input type="text" disabled
										value="${(settlement.payable - settlement.moneyOther)!''}" class="form-control" />
									<#else> 
									<input type="text" disabled
										value="${(settlement.payable)!''}" class="form-control" />
									</#if>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label"></label> 
								<label class="col-lg-10 ejava-errinforight">
									 应付总额=系统计算总额+（或者-，由其他金额类型决定）其他金额
	                           	</label>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">状态: </label> 
								<div class="col-lg-4">
									<@cont.select id="status"
										value="${(settlement.status)!''}" codeDiv="SETTLEMENT_STATUS"
										disabled="disabled" class="form-control" mode="1"/>
								</div>
							</div>

							<!-- 结算状态：1、账单生成；2、平台审核通过；3、商家核对通过；4、商家核对质疑；5、对账完成；6、支付完成 -->
							<#if settlement.status == 2 || settlement.status == 4>
							<!-- 结算状态：2、平台审核通过；4、商家核对质疑，此时可以输入质疑 -->
							<div class="form-group">
								<label class="col-lg-2 control-label">商家质疑：</label>
								<div class="col-lg-4">
									<textarea name="sellerDoubt" 
										class="form-control"
										id="sellerDoubt" class="{maxlength:255}">${(settlement.sellerDoubt)!''}</textarea>
								</div>
							</div>
							</#if> 
							<#if settlement.status == 1 || settlement.status == 3 ||
							settlement.status == 5 || settlement.status == 6>
							<!-- 结算状态：1、账单生成；3、商家核对通过；5、对账完成；6、支付完成，此时不可以输入质疑 -->
							<div class="form-group">
								<label class="col-lg-2 control-label">商家质疑：</label> 
								<div class="col-lg-4">
									<input type="text" disabled="disabled"
										name="sellerDoubt"
										id="sellerDoubt"
										value="${(settlement.sellerDoubt)!''}" class="form-control" />
								</div>
							</div>
							</#if>

							<div class="form-group">
								<p class="p12 p-item">
									<label class="col-lg-2 control-label">平台解释：</label> 
									<div class="col-lg-4">
										<input type="text" disabled="disabled"
											value="${(settlement.platformExplain)!''}" class="form-control" />
									</div>
								</p>
							</div>

						</@form.form>
					</div>

					<div style="padding-top: 10px;">订单列表</div>

					<div data-options="region:'center'" border="false"
						style="width: 100%; height: 400px;">
						<table id="dataGrid" class="easyui-datagrid"
							data-options="rownumbers:true
											,idField :'id'
											,singleSelect:true
											,view: detailview
											,autoRowHeight:false
											,fitColumns:false
											,detailFormatter:detailFormatter
											,onExpandRow:onExpandRow
											,striped:true
											,pagination:true
											,pageSize:'20'
											,fit:true
						    					,url:'${currentBaseUrl}/orderlist?settlementId=${(settlement.id)!''}'
						    					,onLoadSuccess:dataGridLoadSuccess
						    					,method:'get'">
							<thead>
								<tr>
									<th field="id" hidden="hidden"></th>
									<th field="orderSn" width="180" align="left" halign="center">订单号</th>
									<th field="memberName" width="150" align="center">买家用户名</th>
									<th field="sellerName" width="150" align="center">店铺</th>
									<th field="moneyProduct" width="100" align="center">商品金额</th>
									<th field="moneyOrder" width="100" align="center">订单总金额</th>
									<th field="paymentStatus" width="100" align="center"
										formatter="paymentStatus">付款状态</th>
									<th field="orderState" width="100" align="center"
										formatter="getState">订单状态</th>
									<th field="invoiceStatus" width="100" align="center"
										formatter="invoiceStatus">发票状态</th>
									<th field="invoiceTitle" width="100" align="center">发票抬头</th>
									<th field="invoiceType" width="100" align="center">发票类型</th>
									<th field="paymentName" width="100" align="center">支付方式</th>
									<th field="createTime" width="150" align="center">创建时间</th>
									<th field="updateTime" width="150" align="center">修改时间</th>
								</tr>
							</thead>
						</table>
					</div>

					<div style="padding-top: 10px;">退货列表</div>

					<div data-options="region:'center'" border="false"
						style="width: 100%; height: 400px;">
						<table id="backDataGrid" class="easyui-datagrid"
							data-options="rownumbers:true
											,idField :'id'
											,singleSelect:true
											,autoRowHeight:false
											,fitColumns:false
											,striped:true
											,pagination:true
											,pageSize:'20'
											,fit:true
					    					,url:'${currentBaseUrl}/backlist?settlementId=${(settlement.id)!''}'
					    					,onLoadSuccess:dataGridLoadSuccess
					    					,method:'get'">
							<thead>
								<tr>
									<th field="id" hidden="hidden"></th>
									<th field="orderMoney" hidden="hidden"></th>
									<th field="orderSn" width="180" align="center">订单号</th>
									<th field="productName" width="150" align="center">商品名称</th>
									<th field="memberName" width="150" align="center">用户名</th>
									<th field="question" width="150" align="center">问题描述</th>
									<th field="backMoney" width="100" align="center">退款金额</th>
									<th field="backIntegral" width="100" align="center">退回积分</th>
									<th field="backIntegralMoney" width="100" align="center">退回积分金额</th>
									<th field="stateReturn" width="120" align="center"
										formatter="proBackState">退货状态</th>
									<th field="stateMoney" width="120" align="center"
										formatter="proMonState">退款状态</th>
									<th field="createTime" width="150" align="center">创建时间</th>
								</tr>
							</thead>
						</table>
					</div>

				</div>
				<div class="col-lg-12 col-lg-offset-4 settlementbtn">
					<!-- 结算状态：1、账单生成；2、平台审核通过；3、商家核对通过；4、商家核对质疑；5、对账完成；6、支付完成 -->
					<#-- <#if settlement.status == 1> </#if> --> <#if settlement.status
					== 2 || settlement.status == 4> <input type="button" id="checkOver"
						class="btn btn-danger btn-primary" " value="核对通过" /> <input
						type="button" id="checkDoubt" class="btn btn-danger btn-primary"
						" value="核对质疑" /> </#if> <#-- <#if settlement.status == 3> </#if>
					--> <#if settlement.status == 5> <input type="button" id="payOver"
						class="btn btn-danger btn-primary" " value="支付完成" /> </#if> <input
						type="button" id="back" class="btn btn-danger btn-primary"
						" value="返回" />

				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">
