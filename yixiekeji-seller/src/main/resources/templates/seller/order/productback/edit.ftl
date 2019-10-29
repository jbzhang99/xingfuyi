<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/order/productBack"/>

<script language="javascript">
	$(function() {
		initMenu('productBack');
		
		$("#back").click(function() {
			location.href = '${currentBaseUrl}';
		});

		$("#agree").click(function() {
			var provinceId = $("#provinceId").val();
			
			var cityId = $("#cityId").val();
			var areaId = $("#areaId").val();
			if (provinceId == null || provinceId == "" || cityId == null || cityId == "" || areaId == null || areaId == "") {
				$.messager.alert('提示','请选择收货地址！');
				return false;
			}
			var addressAll = $('#provinceId option:selected').text() + $('#cityId option:selected').text() + $('#areaId option:selected').text();
			$("#addressAll").val(addressAll);

			var addressInfo = $("#addressInfo").val();
			if(null == addressInfo || addressInfo == ''){
				$.messager.alert('提示','请填写详细地址！');
				return false;
			}
			
			var contactName = $("#contactName").val();
			if(null == contactName || contactName == ''){
				$.messager.alert('提示','请填写商家联系人姓名！');
				return false;
			}
			
			var contactPhone = $("#contactPhone").val();
			if(null == contactPhone || contactPhone == ''){
				$.messager.alert('提示','请填写商家联系人手机号！');
				return false;
			}
			
			var params = $("#addform").serialize();
			
			$.messager.confirm('确认', '确定同意该买家的退货申请吗？', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type : "GET",
						url : "${currentBaseUrl}/audit?type=2&"+params,
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								location.href="${currentBaseUrl}";
							}else{
								$.messager.alert("提示",data.message);
								$.messager.progress('close');
								return false;
							}
							
						}
					});
				}
			});
		});

		$("#disagree").click(function() {
			var remarkStr = $("#remarkStr").val();
			if (remarkStr = null || remarkStr == "") {
				$.messager.alert("提示", "请描述不予换货的原因。");
				return;
			}
			
			$.messager.confirm('确认', '确定对该买家的退货申请不予处理吗？', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type : "GET",
						url : "${currentBaseUrl}/audit?type=5&id=${obj.id}&remark=" + $("#remarkStr").val(),
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								location.href="${currentBaseUrl}";
							}else{
								$.messager.alert("提示",data.message);
								$.messager.progress('close');
								return false;
							}
						}
					});
				}
			});
		});
		$("#provinceId").change(function(){
	        getRegion($("#cityId"), $(this).val(), "");
	    });
		$("#cityId").change(function(){
	        getRegion($("#areaId"), $(this).val(), "");
	    });
	})
	
	function closeWin() {
		$('#newstypeWin').window('close');
	}
	
	function getRegion(_select, _parentId, _selectId) {
	    _select.empty();
	    $.ajax({
	        type:"get",
	        url: "${domainUrlUtil.urlResources}/seller/system/regions/getRegionByParentId",
	        dataType: "json",
	        data: {parentId: _parentId},
	        cache:false,
	        success:function(data){
	            if (data.success) {
	                _select.empty();
	                var selectOption = '<option value ="">-- 请选择 --</option>'
	                $.each(data.data, function(i, region){
	                    if (_selectId == region.id) {
	                        selectOption += "<option selected='true' value=" + region.id + ">" + region.regionName + "</option>";
	                    } else {
	                        selectOption += "<option value=" + region.id + ">" + region.regionName + "</option>";
	                    }
	                })
	                _select.append(selectOption);
	            } else {

	            }
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
						href="${domainUrlUtil.urlResources}/seller/order/productBack">退货管理</a>
					</li>
					<li class="active">处理退货申请</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="alert alert-warning">
					<a href="#" class="close" data-dismiss="alert">
				        &times;
				    </a>
				    <div class="warning">
				    	<img src="${domainUrlUtil.staticResources}/resources/seller/images/warning.jpg" />
						<label>如果您同意买家的退货申请,将进入退货流程</label>
				    </div>
					<div class="warning" style="margin-bottom: 0px;">
						<img src="${domainUrlUtil.staticResources}/resources/seller/images/warning.jpg" />
						<label>如果您不同意该退货申请,买家可选择向平台投诉</label>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal"
						action="${domainUrlUtil.urlResources}/seller/pcindex/banner/create"
						enctype="multipart/form-data">
						<input type="hidden" id="id" name="id" value="${obj.id!}"> 
						<div class="form-group">
							<label class="col-lg-2 control-label">订单号：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.orderSn!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">商品名称：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.productName!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">用户名：</label>
							<div class="col-lg-8">
								<span class="info-span" >${obj.memberName!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">问题描述：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.question!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">退货数量：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.number!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">退款金额：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.backMoney!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">退回积分：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.backIntegral!}</span>
							</div>
						</div>

						<#if couponUser?? >
						<div class="form-group">
							<label class="col-lg-2 control-label">退回优惠券：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${couponUser.couponSn!}</span>
							</div>
						</div>
						</#if>
						<input type="hidden" id="addressAll" name="addressAll" value="${(obj.addressAll)!'' }">
						<div class="form-group">
							<label class="col-lg-2 control-label">收货地址：</label>
							<div class="col-lg-8">
								<select class="" id="provinceId" name="provinceId" <#if obj.stateReturn != 1>disabled="disabled"</#if>>
									<option value="">-- 请选择 --</option>
									<#if provinceList ??>
				               			<#list provinceList as region>
				                   			<option <#if obj?? && obj.provinceId?? && obj.provinceId == region.id >selected="selected"</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
				               			</#list>
			           				</#if>
			       				</select>
			       				
			       				<select class="" id="cityId" name="cityId" <#if obj.stateReturn != 1>disabled="disabled"</#if>>
									<option value="">-- 请选择 --</option>
									<#if cityList ??>
				                 		<#list cityList as region>
				                     		<option <#if obj?? && obj.cityId?? && obj.cityId == region.id >selected="selected"</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
				               			</#list>
			             			</#if>
			         			</select>
			       				<select class="" id="areaId" name="areaId" <#if obj.stateReturn != 1>disabled="disabled"</#if> >
									<option value="">-- 请选择 --</option>
									<#if areaList ??>
				                 		<#list areaList as region>
				                     		<option <#if obj?? && obj.areaId?? && obj.areaId == region.id >selected="selected"</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
				               			</#list>
			             			</#if>
			         			</select>
		         			</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>详细地址：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="addressInfo"
									name="addressInfo" value="${(obj.addressInfo)!''}"
									<#if obj.stateReturn != 1>readonly="readonly"</#if>
									/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商家联系人姓名：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="contactName"
									name="contactName" value="${(obj.contactName)!''}"
									<#if obj.stateReturn != 1>readonly="readonly"</#if>
									/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商家联系人手机：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="contactPhone"
									name="contactPhone" value="${(obj.contactPhone)!''}"
									<#if obj.stateReturn != 1>readonly="readonly"</#if>
									 />
                            </div>
						</div>
						<!-- 物流信息 -->
						<#if memberProductBackLogList?? && memberProductBackLogList?size &gt; 0>
							<div class="form-group">
								<label class="col-lg-2 control-label"><font class="red">*</font>退货物流信息：</label> 
								<div class="col-lg-4">
	                            </div>
							</div>
							<div class="form-group prom-items">
								<div class="col-lg-4 pull-left">
									<label class="prom-label control-label"></label>
									<div class="prom-div">
										处理时间
									</div>
								</div>
								<div class="col-lg-4 pull-left">
									<label class="prom-label control-label"></label>
									<div class="prom-div">
										处理信息
									</div>
								</div>
								<div class="col-lg-4 pull-left">
									<label class="prom-label control-label"></label>
									<div class="prom-div">
										操作人
									</div>
								</div>
							</div>
							<#list memberProductBackLogList as memberProductBackLog>
								<div class="form-group prom-items">
									<div class="col-lg-4 pull-left">
										<label class="prom-label control-label"></label>
										<div class="prom-div">
										 	${memberProductBackLog.createTime?string("yyyy-MM-dd HH:mm:ss") }
										</div>
									</div>
									<div class="col-lg-4 pull-left">
										<label class="prom-label control-label"></label>
										<div class="prom-div">
											${memberProductBackLog.content}
										</div>
									</div>
									<div class="col-lg-4 pull-left">
										<label class="prom-label control-label"></label>
										<div class="prom-div">
											${memberProductBackLog.operatingName}
										</div>
									</div>
								</div>
							</#list>
						</#if>
						<div class="form-group">
							<label class="col-lg-2 control-label">备注：</label> 
							<div class="col-lg-8">
								<textarea name="remark" id="remarkStr" 
								class="form-control">${(obj.remark)!''}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-12 textcenter">
								<!-- 退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理 -->
								<#if obj.stateReturn == 1>
									<button type="button" id="agree" class="btn btn-danger btn-full-screen btn-primary">同意退货</button>
									<button type="button" id="disagree" class="btn btn-danger btn-full-screen btn-primary">不同意</button>
								</#if>
								<button type="button" id="back" class="btn btn-danger btn-full-screen btn-primary" >返回</button>
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
