<#include "/seller/commons/_head.ftl">

<script>
	$(function() {
		initMenu('coupon');

		$("button[type='button'].back").click(function(){
	 		window.location.href="${domainUrlUtil.urlResources}/seller/promotion/coupon";
		});
		
		$('#addform').bootstrapValidator({
			submitHandler: function (validator, form, submitButton) {
				if(!validator.isValid()){
					return false;
				}
				var exportNum = $("#exportNum").val();
				$.messager.confirm('提示', '确定导出' + exportNum + '张优惠券吗？', function(r){
		            if (r){
		            	$.fileDownload(
		            		'${domainUrlUtil.urlResources}/seller/promotion/coupon/doexport',
		            		{
		            			data:"id=${(coupon.id)!''}&exportNum=" + exportNum
		            		});
		            	submitButton.removeAttr("disabled");
		            } else{
		            	submitButton.removeAttr("disabled");
		            }
		        });
			}
		});
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
					<li><i class="fa fa-home"></i> <a
						href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
					</li>
					<li><a
						href="${domainUrlUtil.urlResources}/seller/promotion/coupon">优惠券管理</a>
					</li>
					<li class="active">导出优惠券</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal">
						<div class="form-group">
							<label class="col-lg-2 control-label">优惠券名称：</label>
							<div class="col-lg-4">
								<input type="text" id="couponName" name="couponName" disabled
									class="form-control" value="${(coupon.couponName)!''}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">优惠券前缀：</label>
							<div class="col-lg-4">
								<input type="text" id="prefix" name="prefix" disabled
									value="${(coupon.prefix)!''}" class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">用于生成优惠券序列号，固定4位长度，只能是A-Z的英文字母组成</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">优惠券面值：</label>
							<div class="col-lg-4">
								<input type="text" id="couponValue" name="couponValue"
									disabled
									value="${(coupon.couponValue)!''}" class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">优惠券抵用现金金额</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">最低订单金额：</label>
							<div class="col-lg-3">
								<input type="text" id="minAmount" name="minAmount" disabled
									value="${(coupon.minAmount)!''}" class="form-control" />
							</div>
							<label class="col-lg-7 ejava-errinforight">优惠券适用的订单最低金额，该金额是在订单扣除单品立减活动、订单满减活动后的订单金额</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">发放时间：</label>
							<div class="col-lg-4">
								<input type="text" id="sendStartTime" name="sendStartTime"
									disabled
									value="${(coupon.sendStartTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									class="form-control" />
							</div>

							<div class="col-lg-4">
								<input type="text" id="sendEndTime" name="sendEndTime"
									disabled
									value="${(coupon.sendEndTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									class="form-control" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<div class="col-lg-16">
								<label class="col-lg-6 ejava-errinforight">如果优惠券是线下发放类型，则该时间为店铺可以导出优惠券号码的时间</label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">使用时间：</label>
							<div class="col-lg-4">
								<input type="text" id="useStartTime" name="useStartTime"
									disabled
									value="${(coupon.useStartTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									class="form-control" />
							</div>
							
							<div class="col-lg-4">
								<input type="text" id="useEndTime" name="useEndTime"
									disabled
									value="${(coupon.useEndTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<div class="col-lg-16">
								<label class="col-lg-6 ejava-errinforight">使用开始时间开始必须比发放开始时间晚，使用结束时间必须比发放结束时间晚</label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">会员限制领取数量：</label>
							<div class="col-lg-4">
								<input type="text" id="personLimitNum" name="personLimitNum"
									disabled
									value="${(coupon.personLimitNum)!''}" class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">每个会员限制领取的数量，0为不限</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">总数量：</label>
							<div class="col-lg-4">
								<input type="text" id="totalLimitNum" name="totalLimitNum"
									value="${(coupon.totalLimitNum)!''}"
									disabled
									class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">总共预计发放的总数量，至少1张</label>

						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">优惠券类型：</label>
							<div class="col-lg-4">
							<@cont.select id="type" disabled="disabled" 
								value="${(coupon.type)!''}" codeDiv="COUPON_TYPE"
								class="form-control" mode="1"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">应用渠道：</label>
							<div class="col-lg-4"><@cont.select id="channel" disabled="disabled" 
								value="${(coupon.channel)!''}" codeDiv="CHANNEL"
								class="form-control" mode="1"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">优惠券描述：</label>
							<div class="col-lg-8">
								<textarea name="remark" rows="3" 
									disabled
									class="form-control"
									id="remark">${(coupon.remark)!''}</textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">导出数量：</label>
							<div class="col-lg-3">
								<input type="text" id="exportNum" name="exportNum"
									required
									data-bv-message="请输入导出数量"
									min="0"
									max="1001"
									data-bv-lessthan-inclusive="true"
                                    data-bv-lessthan-message="导出数量必须小于等于1000"
                                    data-bv-greaterthan-inclusive="true"
                                   	data-bv-greaterthan-message="导出数量必须大于0"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数量"
									class="form-control" />
							</div>
							<label class="col-lg-7 ejava-errinforight">导出数量，一次至少导出1张，最多导出1000张，总导出数量不能超过优惠券设定的总数量</label>
						</div>
						
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="submit" class="btn btn-danger export btn-primary">导出</button>
								<button type="button" class="btn btn-danger back btn-primary">返回</button>
							</div>
						</div>
					</form>

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