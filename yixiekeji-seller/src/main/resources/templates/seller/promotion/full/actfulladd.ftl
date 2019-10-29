<#include "/seller/commons/_head.ftl">

<script language="javascript">

$(function(){
	initMenu('full');
	
	$("button[type='button'].back").click(function(){
 		window.location.href= domain+"/seller/promotion/full";
	});
	
	$('#addform').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		submitHandler: function (validator, form, submitButton) {
			if(!validator.isValid()){
				return false;
			}
			var firstFull = $("#firstFull").val();
			var secondFull = $("#secondFull").val();
			var thirdFull = $("#thirdFull").val();
			var firstDiscount = $("#firstDiscount").val();
			var secondDiscount = $("#secondDiscount").val();
			var thirdDiscount = $("#thirdDiscount").val();

			// 满额减免额比较
			if (parseFloat(firstFull) <= parseFloat(firstDiscount)) {
				$.messager.alert('提示','满额必须大于减免额。');
				return;
			}
			if (secondFull != null && secondFull != "" && parseFloat(secondFull) > 0) {
				if (parseFloat(secondFull) <= parseFloat(firstFull)) {
					$.messager.alert('提示','第二档满额必须大于第一档满额。');
					return;
				}
				if (secondDiscount == null || secondDiscount == "" || parseFloat(secondDiscount) <= 0) {
					$.messager.alert('提示','设定满额必须同时设定减免额。');
					return;
				}
				if (parseFloat(secondFull) <= parseFloat(secondDiscount)) {
					$.messager.alert('提示','满额必须大于减免额。');
					return;
				}
				if (parseFloat(secondDiscount) <= parseFloat(firstDiscount)) {
					$.messager.alert('提示','第二档减免额必须大于第一档减免额。');
					return;
				}
			}
			if (thirdFull != null && thirdFull != "" && parseFloat(thirdFull) > 0) {
				if (secondFull == null || secondFull == "" || parseFloat(secondFull) <= 0) {
					$.messager.alert('提示','如果设定第三档则必须设定第二档。');
					return;
				}
				if (parseFloat(thirdFull) <= parseFloat(secondFull)) {
					$.messager.alert('提示','第三档满额必须大于第二档满额。');
					return;
				}
				if (thirdDiscount == null || thirdDiscount == "" || parseFloat(thirdDiscount) <= 0) {
					$.messager.alert('提示','设定满额必须同时设定减免额。');
					return;
				}
				if (parseFloat(thirdFull) <= parseFloat(thirdDiscount)) {
					$.messager.alert('提示','满额必须大于减免额。');
					return;
				}
				if (parseFloat(thirdDiscount) <= parseFloat(secondDiscount)) {
					$.messager.alert('提示','第三档减免额必须大于第二档减免额。');
					return;
				}
			}
			
			var channel = $("#channel").val();
			if (channel == null || channel == "") {
				$.messager.alert('提示','请选择活动应用渠道。');
				return;
			}
			
			validator.defaultSubmit();
		},
		fields : {
			actFullName : {
				validators : {
					 notEmpty: true
				}
			},
			firstFull : {
				validators : {
					 notEmpty: true
				}
			},
			firstDiscount : {
				validators : {
					 notEmpty: true
				}
			},
			startTime : {
				validators : {
					notEmpty: true
				}
			},
			endTime : {
				validators : {
					notEmpty: true
				}
			},
			channel : {
				validators : {
					notEmpty: true
				}
			}/* ,
			remark : {
				validators : {
					notEmpty: true
				}
			} */
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
						href="${domainUrlUtil.urlResources}/seller/promotion/full">订单满减</a>
					</li>
					<li class="active">添加订单满减活动</li>
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
						action="${domainUrlUtil.urlResources}/seller/promotion/full/create"
						enctype="multipart/form-data" data-bv-message="该项必填">
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>满减活动名称：</label>
							<div class="col-lg-4">
								<input class="form-control" type="text"
									id="actFullName" name="actFullName"
									value="${(actFull.actFullName)!''}"
									 data-bv-stringlength="true"
                                     data-bv-stringlength-min="1"
                                     data-bv-stringlength-max="40"
                                     data-bv-stringlength-message="名称1-40位长度"
                                     />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>第一档：</label> 
							<div class="col-lg-4">
							<input class="form-control" type="text"
								id="firstFull" name="firstFull"
								value="${(actFull.firstFull)!''}"
								placeholder="满"
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
                               	data-bv-greaterthan-message="金额必须大于0.01" /> 
							</div>
							<div class="col-lg-4">
								<input
									placeholder="减"
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
									class="form-control" type="text" id="firstDiscount"
									name="firstDiscount" value="${(actFull.firstDiscount)!''}" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-10 ejava-errinforight">满额与订单减去单品立减金额后计算出的金额相比较，如满1000减50，如果订单总额减去单品立减优惠金额后大于100，则满足规则</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">第二档：</label> 
							<div class="col-lg-4">
							<input
								placeholder="满"
								data-bv-numeric="true"
								data-bv-numeric-message="请输入正确的金额"
								min="0"
								max="999999"
								pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                            	data-bv-regexp-message="金额保留两位小数" 
								data-bv-lessthan-inclusive="true"
                                data-bv-lessthan-message="金额必须小于999999"
                                data-bv-greaterthan-inclusive="true"
                               	data-bv-greaterthan-message="金额必须大于0" 
								class="form-control" type="text" id="secondFull"
								name="secondFull" value="${(actFull.secondFull)!''}" />
							</div>	
							<div class="col-lg-4">
								<input
									placeholder="减"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
	                            	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0" 
									class="form-control" type="text"
									id="secondDiscount" name="secondDiscount"
									value="${(actFull.secondDiscount)!''}"  />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-10 ejava-errinforight">
								第二、三档可为空，为空或设定为0，表示不设定该档次，不能跨档次设定（如设定一三档次不设定二档次）<br>
								第二档满额必须比第一档满额金额大；同理第三档满额必须比第二档满额金额大
							</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">第三档：</label>
							<div class="col-lg-4">
								<input
									placeholder="满"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
	                            	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0" 
									class="form-control" type="text" id="thirdFull"
									name="thirdFull" value="${(actFull.thirdFull)!''}" /> 
							</div>
							<div class="col-lg-4">
								<input
									placeholder="减"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
	                            	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0" 
									class="form-control" type="text" id="thirdDiscount"
									name="thirdDiscount" value="${(actFull.thirdDiscount)!''}"  />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动时间：</label> 
							<div class="col-lg-4">
								<input
									type="text" id="startTime" name="startTime"
									class="form-control"
									onblur="updateStatus(this.name);"
									data-options="required:true"
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});"
									value="${(actFull.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}" /> 
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime"
								name="endTime" class="form-control"
								onblur="updateStatus(this.name);"
								data-options="required:true"
								onclick="WdatePicker({startDate:'%y-%M-{%d+1} 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});"
								value="${(actFull.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>应用渠道：</label>
							<div class="col-lg-4">
								<@cont.select id="channel" value="${(actFull.channel)!''}"
									codeDiv="CHANNEL" class="form-control" mode="1"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">活动描述：</label>
							<div class="col-lg-8">
								<textarea name="remark" id="remark"
									class="form-control">${(actFull.remark)!''}</textarea>
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