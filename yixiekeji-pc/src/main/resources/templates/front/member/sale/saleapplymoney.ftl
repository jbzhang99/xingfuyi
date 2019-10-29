<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
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
					<a href='javascript:void(0)'>结算中心</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

			<!-- S 右侧主要内容 -->
			<div class='wrapper_main myorder wrapper_main-wd'>
			
				<!-- 结算中心 -->
				<div class="reminder-con">
					<h3>结算中心</h3>
					<div class="mybalance-mainreview clearfix">
						<div class="mybalance-accrued">
							<div class="accrued__upper bottomline-dot clearfix">
								<h3 class="mybalance-title mybalance-title__accrued">预计收入</h3>
								<ul class="mybalance-info">
									<li class="balancecash"><em class="pricetag">￥</em>${sumState1!"0"}</li>
									<li class="balancedinfo"><em class="iconadd icon-clock"></em>${countState1!"0"}单已完成购买</li>
								</ul>
							</div>
							<div class="accrued__below">
								<dl class="accrued-rules">
									<dt>满足以下条件即可提现佣金：</dt>
									<dd style="margin-top: -15px;">
										<em class="iconadd icon-check"></em>客户满意
									</dd>
									<dd>
										<em class="iconadd icon-check"></em>订单完成
									</dd>
									<dd>
										<em class="iconadd icon-check"></em>无退换货问题且过期限
									</dd>
									<dd>
										<br>
									</dd>
									<dt>注：预计收入中的奖励明细在符合提现时，系统自动生成。</dt>
								</dl>
							</div>
						</div>
						<div class="mybalance-withdraw">
							<div class="withdraw__upper bottomline-dot clearfix">
								<h3 class="mybalance-title mybalance-title__cash">可提现</h3>
								<ul class="mybalance-info">
									<li class="balancecash">
										<em class="pricetag">￥</em>${sumState2!"0"}
									</li>
									<li class="balancedinfo">
										<em class="iconadd icon-clock"></em>${countState2!"0"}单可提现
									</li>
								</ul>
								<a href="javascript:void(0)" onclick="jsSaleApplyMoney()" class="btn-getcash">提现</a>
							</div>
							<div class="withdraw__middle bottomline-dot">
								<ul>
									<li class="balancecash-money">
										<em class="pricetag">￥</em>${sumState3!"0"}
									</li>
									<li class="balancecash-status">提现中…</li>
									<li class="balancecash-info">
										<em class="iconadd icon-clock"></em>${countState3!"0"}单正在审核结算中
									</li>
								</ul>
							</div>
							<div class="withdraw__below">
								<ul>
									<li class="balancecash-money">
										<em class="pricetag">￥</em>${sumState4!"0"}
									</li>
									<li class="balancecash-status">已提现</li>
									<li class="balancecash-info">
										<em class="iconadd icon-clock"></em>${countState4!"0"}单已提现完毕
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 结算明细 -->
				<div class="reminder-con" style="margin-top: 20px;">
					<h3>结算明细</h3>
					<table class="searchlist-result">
						<tr class="">
							<th class="th-cell">姓名</th>
							<th class="th-cell th-cell-with20">身份证号</th>
							<th class="th-cell th-cell-with">金额</th>
							<th class="th-cell th-cell-with">取款时间</th>
							<th class="th-cell th-cell-with">汇款时间</th>
							<th class="th-cell">是否打款</th>
						</tr>
						
						<#if saleApplyMoneys??>
							<#list saleApplyMoneys as saleApplyMoney>
								<tr>
									<td>${(saleApplyMoney.bankName)!""}</td>
									<td>${(saleApplyMoney.certificateCode)!""}</td>
									<td>${(saleApplyMoney.money)!""}</td>
									<td>${(saleApplyMoney.createTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
									<td><#if saleApplyMoney.updateTime??>${(saleApplyMoney.updateTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
									<td><@cont.codetext value="${(saleApplyMoney.state)!0}" codeDiv="YES_NO"/></td>
								</tr>
							</#list>
						</#if>
					</table>
				</div>
				<#include "/front/commons/_pagination.ftl" />
			</div>
			<!-- E 右侧主要内容 -->
			
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#saleApplyMoneys").addClass("currnet_page");
		});
		
		function jsSaleApplyMoney(){
			 var countState2 = ${countState2!0};
			 if(countState2 == 0) {
			 	jAlert("没有订单可以提款。");
			 	return;
			 }
			 var countState3 = ${countState3!0};
			 if(countState3 != 0) {
			 	jAlert("有未处理的提款申请，处理完成之后，在进行提款操作。");
			 	return;
			 }
			 
			  jConfirm("确定提现吗？", "提示", function(r) { 
            	  	  if (r) { 
            	  	  	$.ajax({
						type:"POST",
						url:domain+"/member/sale-apply-money-save.html",
						dataType:"json",
						async : false,
						success:function(data){
							if(data.success){
								jAlert('提现成功', '提示',function() {
									window.location.href=domain+"/member/sale-apply-money.html"
								});
							}else{
								jAlert(data.message);
							}
						},
						error:function(){
							jAlert("操作失败，请重试！");
						}
					});
                   } 
               }); 
		}
	</script>
	
<#include "/front/commons/_endbig.ftl" />
