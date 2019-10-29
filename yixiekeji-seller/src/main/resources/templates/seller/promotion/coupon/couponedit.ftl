<#include "/seller/commons/_head.ftl">

<script>
	$(function() {
		initMenu('coupon');

		$("button[type='button'].back").click(function(){
	 		window.location.href="${domainUrlUtil.urlResources}/seller/promotion/coupon";
		});
		
		$('#addform').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				prefix : {
					validators : {
						regexp : {
							regexp : /^[A-Za-z]+$/,
							message : '优惠券前缀只能输入英文字母'
						},
						stringLength: {
		                  min: 4,
		                  max: 4,
		                  message: '优惠券前缀长度固定4位'
		              	}
					}
				},
				sendStartTime : {
					validators : {
						 notEmpty: true
					}
				},
				sendEndTime : {
					validators : {
						 notEmpty: true
					}
				},
				useStartTime : {
					validators : {
						 notEmpty: true
					}
				},
				useEndTime : {
					validators : {
						 notEmpty: true
					}
				},
				type : {
					validators : {
						 notEmpty: true
					}
				},
				channel : {
					validators : {
						notEmpty: true
					}
				},
				minAmount : {
					validators : {
						callback : {
							message : '适用最低订单金额必须大于优惠券面值',
							callback:function(value, validator,$field){
								var couponValue = $("#couponValue").val();
								var minAmount = $("#minAmount").val();
								return parseFloat(minAmount) >= parseFloat(couponValue);
							}
						}
					}
				}
			}
		});
		<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
	});
	
	function updateStatus(name){
		$("#addform").bootstrapValidator('updateStatus', name, 'NOT_VALIDATED').
			bootstrapValidator('validateField', name);
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
						href="${domainUrlUtil.urlResources}/seller/promotion/coupon">优惠券管理</a>
					</li>
					<li class="active">编辑优惠券</li>
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

					<form method="post" id="addform" class="form-horizontal"
							action="${domainUrlUtil.urlResources}/seller/promotion/coupon/update"
						 	data-bv-message="该项必填">
						 	
					 	<input type="hidden" id="id" name="id" value="${(coupon.id)!''}">
						<input type="hidden" id="status" name="status" value="${(coupon.status)!''}">
						<input type="hidden" id="receivedNum" name="receivedNum" value="${(coupon.receivedNum)!''}">	
						 
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>优惠券名称：</label>
							<div class="col-lg-4">
								<input type="text" id="couponName" name="couponName" required
									class="form-control" value="${(coupon.couponName)!''}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>优惠券前缀：</label>
							<div class="col-lg-4">
								<input type="text" id="prefix" name="prefix" required
									maxlength = "4"
									value="${(coupon.prefix)!''}" class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">用于生成优惠券序列号，固定4位长度，只能是A-Z的英文字母组成</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>优惠券面值：</label>
							<div class="col-lg-4">
								<input type="text" id="couponValue" name="couponValue" 
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0.01"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.01"
									value="${(coupon.couponValue)!''}" class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">优惠券抵用现金金额，取值范围0.01-999999</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>最低订单金额：</label>
							<div class="col-lg-3">
								<input type="text" id="minAmount" name="minAmount" 
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0.01"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.01"
									value="${(coupon.minAmount)!''}" class="form-control" />
							</div>
							<label class="col-lg-7 ejava-errinforight">优惠券适用的订单最低金额，该金额是在订单扣除单品立减活动、订单满减活动后的订单金额，取值范围0.01-999999</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>发放时间：</label>
							<div class="col-lg-4">
								<input type="text" id="sendStartTime" name="sendStartTime"
									onblur="updateStatus(this.name);"
									missingMessage="开始时间必填" 
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'sendEndTime\')}'});"
									value="${(coupon.sendStartTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									class="form-control" />
							</div>

							<div class="col-lg-4">
								<input type="text" id="sendEndTime" name="sendEndTime"
									onblur="updateStatus(this.name);"
									missingMessage="结束时间必填" 
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sendStartTime\')}'});"
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
							<label class="col-lg-2 control-label"><font class="red">*</font>使用时间：</label>
							<div class="col-lg-4">
								<input type="text" id="useStartTime" name="useStartTime"
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sendStartTime\')}',maxDate:'#F{$dp.$D(\'useEndTime\')}'});"
									value="${(coupon.useStartTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									class="form-control" />
							</div>
							
							<div class="col-lg-4">
								<input type="text" id="useEndTime" name="useEndTime"
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sendEndTime\')}'});"
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
							<label class="col-lg-2 control-label"><font class="red">*</font>会员限制领取数量：</label>
							<div class="col-lg-4">
								<input type="text" id="personLimitNum" name="personLimitNum"
									required
									min="0"
									max="999999"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="数量必须为整数"
                             	  	data-bv-lessthan-message="数必须小于999999"
                             	  	data-bv-greaterthan-message="数量必须大于等于0"
									value="${(coupon.personLimitNum)!''}" class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">每个会员限制领取的数量，0为不限</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>总数量：</label>
							<div class="col-lg-4">
								<input type="text" id="totalLimitNum" name="totalLimitNum"
									value="${(coupon.totalLimitNum)!''}"
									required
									min="0"
									max="999999"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="数量必须为整数"
                             	  	data-bv-lessthan-message="数必须小于999999"
                             	  	data-bv-greaterthan-message="数量必须大于等于0"
									class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">总共预计发放的总数量，至少1张</label>

						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>优惠券类型：</label>
							<div class="col-lg-4"><@cont.select id="type"
								value="${(coupon.type)!''}" codeDiv="COUPON_TYPE"
								class="form-control" mode="1"/></div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>应用渠道：</label>
							<div class="col-lg-4"><@cont.select id="channel"
								value="${(coupon.channel)!''}" codeDiv="CHANNEL"
								class="form-control" mode="1"/></div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">优惠券描述：</label>
							<div class="col-lg-8">
								<textarea name="remark" rows="3" 
									class="form-control"
									id="remark">${(coupon.remark)!''}</textarea>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="submit" class="btn btn-danger btn-primary">提交</button>
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
