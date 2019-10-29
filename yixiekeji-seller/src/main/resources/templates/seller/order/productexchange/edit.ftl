<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/order/productExchange"/>

<script language="javascript">
	$(function() {
		initMenu('productExchange');
		
		$("button[type='button'].back").click(function(){
	 		window.location.href= '${currentBaseUrl}';
		});

		$("#agree").click(function() {
			var provinceId = $("#provinceId2").val();
			var cityId = $("#cityId2").val();
			var areaId = $("#areaId2").val();
			if (provinceId == null || provinceId == "" || cityId == null || cityId == "" || areaId == null || areaId == "") {
				$.messager.alert('提示','请选择收货地址！');
				return false;
			}
			
			var addressAll = $('#provinceId2 option:selected').text() + $('#cityId2 option:selected').text() + $('#areaId2 option:selected').text();
			$("#addressAll2").val(addressAll);
			var addressInfo2 = $("#addressInfo2").val();
			if(null == addressInfo2 || addressInfo2 == ''){
				$.messager.alert('提示','请填写详细地址！');
				return false;
			}
			
			var changeName2 = $("#changeName2").val();
			if(null == changeName2 || changeName2 == ''){
				$.messager.alert('提示','请填写商家收货人姓名！');
				return false;
			}
			
			var phone2 = $("#phone2").val();
			if(null == phone2 || phone2 == ''){
				$.messager.alert('提示','请填写商家收货人手机！');
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
								location.href = '${currentBaseUrl}';
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
						url : "${currentBaseUrl}/audit?type=7&id=${obj.id}&remark=" + $("#remarkStr").val(),
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								location.href = '${currentBaseUrl}';
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

		$("#received").click(function() {
			$.messager.confirm('确认', '确定已收到买家发回的原件吗？', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type : "GET",
						url : "${currentBaseUrl}/audit?type=4&id=${obj.id}&remark=" + $("#remarkStr").val(),
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								location.href = '${currentBaseUrl}';
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
		
		$("#delivered").click(function() {
			var logisticsId = $("#logisticsId").val();
			if(null == logisticsId || logisticsId == ''){
				$.messager.alert("提示","请选择物流公司！");
				return false;
			}
			var logisticsNumber = $("#logisticsNumber").val();
			if(null == logisticsNumber || logisticsNumber == ''){
				$.messager.alert("提示","请填写快递单号！");
				return false;
			}
			$.messager.confirm('确认', '确定已给买家发货了吗？', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type : "GET",
						url : "${currentBaseUrl}/audit?type=5&id=${obj.id}&remark=" + $("#remarkStr").val()+"&logisticsId="+logisticsId+"&logisticsNumber="+logisticsNumber,
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								location.href = '${currentBaseUrl}';
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
		
		$("#back").click(function() {
			var remarkStr = $("#remarkStr").val();
			if (remarkStr = null || remarkStr == "") {
				$.messager.alert("提示", "请描述不予换货的原因。");
				return;
			}
			var logisticsId = $("#logisticsId").val();
			if(null == logisticsId || logisticsId == ''){
				$.messager.alert("提示","请选择物流公司！");
				return false;
			}
			
			var logisticsNumber = $("#logisticsNumber").val();
			if(null == logisticsNumber || logisticsNumber == ''){
				$.messager.alert("提示","请填写快递单号！");
				return false;
			}
			
			$.messager.confirm('确认', '确定对该申请不予处理原件退还吗？', function(r) {
				if (r) {
					$.messager.progress({
						text : "提交中..."
					});
					$.ajax({
						type : "GET",
						url : "${currentBaseUrl}/audit?type=6&id=${obj.id}&remark=" + $("#remarkStr").val()+"&logisticsId="+logisticsId+"&logisticsNumber="+logisticsNumber,
						success : function(data) {
							if(data.success){
								$.messager.progress('close');
								location.href = '${currentBaseUrl}';
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

		$("#provinceId2").change(function(){
	        getRegion($("#cityId2"), $(this).val(), "");
	    });
		$("#cityId2").change(function(){
	        getRegion($("#areaId2"), $(this).val(), "");
	    });
		
	});
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
						href="${domainUrlUtil.urlResources}/seller/order/productExchange">换货管理</a>
					</li>
					<li class="active">处理换货申请</li>
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
						<label>如果您同意买家的换货申请,将进入换货流程</label>
				    </div>
					<div class="warning" style="margin-bottom: 0px;">
						<img src="${domainUrlUtil.staticResources}/resources/seller/images/warning.jpg" />
						<label>如果您不同意该换货申请,买家可选择向平台投诉</label>
					</div>
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal"
						action="${domainUrlUtil.urlResources}/seller/pcindex/banner/create"
						enctype="multipart/form-data">
						<input type="hidden" id="id" name="id" value="${obj.id!}"/>
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
							<label class="col-lg-2 control-label">换货人收货地址：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.addressAll!}${obj.addressInfo!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">换货人手机：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.phone!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">联系人姓名：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.changeName!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">邮编：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.zipCode!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">换货数量：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.number!}</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">问题描述：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.question!}</span>
							</div>
						</div>
						
						<!-- 商家收货地址 -->
						<input type="hidden" id="addressAll2" name="addressAll2" value="${(obj.addressAll2)!'' }">
						<div class="form-group">
							<label class="col-lg-2 control-label">收货地址：</label>
							<div class="col-lg-8">
								<select class="" id="provinceId2" name="provinceId2" <#if obj.state?? && obj.state !=1>disabled="disabled"</#if> >
									<option value="">-- 请选择 --</option>
									<#if provinceList ??>
				               			<#list provinceList as region>
				                   			<option <#if obj?? && obj.provinceId2?? && obj.provinceId2 == region.id >selected="selected"</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
				               			</#list>
			           				</#if>
			       				</select>
			       				
			       				<select class="" id="cityId2" name="cityId2" <#if obj.state?? && obj.state !=1>disabled="disabled"</#if> >
									<option value="">-- 请选择 --</option>
									<#if cityList ??>
				                 		<#list cityList as region>
				                     		<option <#if obj?? && obj.cityId2?? && obj.cityId2 == region.id >selected="selected"</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
				               			</#list>
			             			</#if>
			         			</select>
			       				<select class="" id="areaId2" name="areaId2" <#if obj.state?? && obj.state !=1>disabled="disabled"</#if> >
									<option value="">-- 请选择 --</option>
									<#if areaList ??>
				                 		<#list areaList as region>
				                     		<option <#if obj?? && obj.areaId2?? && obj.areaId2 == region.id >selected="selected"</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
				               			</#list>
			             			</#if>
			         			</select>
		         			</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>详细地址：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="addressInfo2"
									name="addressInfo2" value="${(obj.addressInfo2)!''}"
                                    <#if obj.state?? && obj.state !=1>disabled="disabled"</#if> />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商家收货人姓名：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="changeName2"
									name="changeName2" value="${(obj.changeName2)!''}"
                                    <#if obj.state?? && obj.state !=1>disabled="disabled"</#if>/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商家收货人手机：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="phone2"
									name="phone2" value="${(obj.phone2)!''}"
	                            	<#if obj.state?? && obj.state !=1>disabled="disabled"</#if> />
                            </div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>邮编：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="zipCode2"
									name="zipCode2" value="${(obj.zipCode2)!''}"
	                            	<#if obj.state?? && obj.state !=1>disabled="disabled"</#if> />
                            </div>
						</div>
						<!-- 商家发货信息 -->
						<#if obj.state?? && (obj.state == 4 || obj.state == 5 || obj.state == 6 )>
			         		<div class="form-group">
								<label class="col-lg-2 control-label"><font class="red">*</font>物流公司：</label> 
								<div class="col-lg-4">
									<select class="" id="logisticsId" name="logisticsId" <#if obj.state?? && obj.state !=4>disabled="disabled"</#if> >
										<option value="">-- 请选择 --</option>
										<#if courierCompanyList ??>
					                 		<#list courierCompanyList as courierCompany>
					                     		<option <#if obj?? && obj.logisticsId?? && obj.logisticsId == courierCompany.id >selected="selected"</#if> value="${(courierCompany.id)!''}">${(courierCompany.companyName)!''}</option>
					               			</#list>
				             			</#if>
				         			</select>
	                            </div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label"><font class="red">*</font>快递单号：</label> 
								<div class="col-lg-4">
									<input class="form-control" type="text" id="logisticsNumber" name="logisticsNumber" value="${(obj.logisticsNumber)!''}"  <#if obj.state?? && obj.state !=4>disabled="disabled"</#if>/>
	                            </div>   
                            </div>
						</#if>
						<!-- 物流信息 -->
						<#if memberProductExchangeLogList?? && memberProductExchangeLogList?size &gt; 0>
							<div class="form-group">
								<label class="col-lg-2 control-label"><font class="red">*</font>换货操作记录：</label> 
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
							<#list memberProductExchangeLogList as memberProductBackLog>
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
						<#if memberProductExchangeLogBackList?? && memberProductExchangeLogBackList?size &gt; 0>
							<div class="form-group">
								<label class="col-lg-2 control-label"><font class="red">*</font>退件物流信息：</label> 
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
							<#list memberProductExchangeLogBackList as memberProductBackLog>
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
						<#if memberProductExchangeLogExchangeList?? && memberProductExchangeLogExchangeList?size &gt; 0>
							<div class="form-group">
								<label class="col-lg-2 control-label"><font class="red">*</font>换件物流信息：</label> 
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
							<#list memberProductExchangeLogExchangeList as memberProductBackLog>
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
									<!-- 如果状态是1、未处理 -->
									<!-- 1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理 -->
								<#if obj.state == 1>
									<button type="button" id="agree" class="btn btn-danger btn-full-screen btn-primary">同意换货</button>
									<button type="button" id="disagree" class="btn btn-danger btn-full-screen btn-primary">不同意</button>
								</#if>
								<#if obj.state == 3>
									<button type="button" id="received" class="btn btn-danger btn-full-screen btn-primary">已收原件</button>
								</#if>
								<#if obj.state == 4>
									<button type="button" id="delivered" class="btn btn-danger btn-full-screen btn-primary">已派换件</button>
									<button type="button" id="back" 
										style="width: 130px;"
										class="btn btn-danger btn-full-screen btn-primary">不予处理原件退还</button>
								</#if>
									<button type="button" class="btn btn-danger back btn-full-screen btn-primary">返回</button>
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
