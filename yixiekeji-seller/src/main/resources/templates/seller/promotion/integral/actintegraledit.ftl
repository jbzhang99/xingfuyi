<#include "/seller/commons/_head.ftl">

<link href="${domainUrlUtil.staticResources}/resources/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>

<script language="javascript">
$(function(){
	initMenu('actintegral');
	$(".boxer").boxer();
	
	$("button[type='button'].back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/seller/promotion/actintegral";
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
			var productId = $('#productId').val();
			if(!productId) {
				validator.disableSubmitButtons(false);
				$.messager.alert('提示','请选择商品');
				return;
			}
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			
			var startInt = new Date(startTime.replace("-", "/").replace("-", "/"));
			var endInt = new Date(endTime.replace("-", "/").replace("-", "/"));
			
			if(endInt <= startInt) {
				$.messager.alert('提示','活动结束时间不能小于开始时间');
				return;
			}
			
			var descinfo = UM.getEditor('myEditor').getContent();
            $('#descinfo').val(descinfo);//商品描述信息
            
            validator.defaultSubmit();
		},
		fields:{
			type:{
				validators : {
					 notEmpty: true
				}
			},
			channel:{
				validators : {
					 notEmpty: true
				}
			},
			gradeValue:{
				validators : {
					 notEmpty: true
				}
			}
		}
	});
	
	$('#selectProduct').click(function(){
    	$('#productDlg').dialog('open');
    });
        
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
})

function delMethod(obj) {
	obj.parentNode.remove();
}

function productCallBack(data){
    $('#productId').val('');
    $('#productName').html("");
    if(data && data.length > 0){
        var productName = '';
        var productId = '';
        $.each(data, function(j, n){
            productId = n.id;
            productName = n.name1;
        })
         $('#productId').val(productId);
         $('#productName').html(productName);
    }
}
 
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
						href="${domainUrlUtil.urlResources}/seller/promotion/actintegral">积分商城管理</a>
					</li>
					<li class="active">编辑积分活动</li>
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
						action="${domainUrlUtil.urlResources}/seller/promotion/actintegral/update"
						enctype="multipart/form-data" data-bv-message="该项必填">
						 	<input type="hidden" id="id" name="id" value="${(actIntegral.id)!}" />
							<input type="hidden" id="productId" name="productId" value="${(actIntegral.productId)!}"/>
							<input type="hidden" id="descinfo" name="descinfo" value="${(actIntegral.descinfo)!}"/>
				
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>积分商城分类：</label>
							<div class="col-lg-4">
								<select name="type" class="form-control">
									<#if actIntegralTypes ??>
										<#list actIntegralTypes as actIntegralType>
								  			<option value = "${actIntegralType.id}" <#if actIntegralType.id == actIntegral.type >selected</#if> >${actIntegralType.name}</option>
								  		</#list>
								  	</#if>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>选择商品：
							</label>
							<div class="col-lg-10">
								<input type="button" value="选择商品" id="selectProduct" />
								<label id="productName">${(actIntegral.productName)!}</label>
							</div>
						</div>
							
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-10 ejava-errinforight">商品必须是上架状态</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>促销标题：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" type="text" id="title"
									name="name"
									value="${(actIntegral.name)!}"
									required />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								促销标题必须填写，输入2到200个字符
							</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>渠道：</label>
							<div class="col-lg-4">
								<@cont.select id="channel" name="channel" value="${(actIntegral.channel)!}" codeDiv="CHANNEL"
									class="form-control" mode="2"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动图片：</label> 
							<div class="col-lg-4">
								<input type="file" id="imageFile" 
									name="imageFile" class="form-control"  />
							</div>
							<#if (actIntegral.image)??> 
							<div class="col-lg-4">
								<a href="${domainUrlUtil.imageResources}${(actIntegral.image)!''}" class='boxer'>查看图片</a>
							</div>
							</#if>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>市场价: </label> 
							<div class="col-lg-4">
								<input
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
									type="text" 
									id="marketPrice" 
									name="marketPrice"
									value="${(actIntegral.marketPrice)!''}"
									class="form-control" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>消耗积分: </label> 
							<div class="col-lg-4">
								<input
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
									type="text" 
									id="price" 
									name="price"
									value="${(actIntegral.price)!''}"
									class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								积分兑换数量必须填写，输入1到99999之间数字，填写后是多少积分数量兑换此商品
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>是否有现金：</label>
							<div class="col-lg-4">
								<@cont.radio id="isWithMoney" value="${(actIntegral.isWithMoney)!''}" codeDiv="IS_WITH_MONEY" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">现金价格: </label> 
							<div class="col-lg-4">
								<input
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									type="text" 
									id="priceMoney" 
									name="priceMoney"
									value="${(actIntegral.priceMoney)!''}"
									class="form-control" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>会员限制：</label>
							<div class="col-lg-4">
								<@cont.select id="gradeValue" name="gradeValue" value="${(actIntegral.gradeValue)!}"
									class="form-control"
									codeDiv="MEMBER_GRADE" mode="2"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-6 ejava-errinforight">
								如选择铜牌，那铜牌会员以上可以参加此活动，注册会员不能参加，其他类似
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>限购数量：</label> 
							<div class="col-lg-4">
								<input
									required
									min="1"
									max="999999"
									data-bv-lessthan-message="数量必须小于999999"
                             	  	data-bv-greaterthan-message="数量必须大于1"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="数量不正确" 
									class="form-control" 
									id="purchase"
									name="purchase"
									value="${(actIntegral.purchase)!''}" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								限购数量，每人每次下单最多可以购买多少商品，最少为1
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>库存：</label> 
							<div class="col-lg-4">
								<input
									required
									min="1"
									max="999999"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="库存为整数，取值1-999999"
                             	  	data-bv-lessthan-message="库存数必须小于999999"
                             	  	data-bv-greaterthan-message="库存数必须大于1" 
									class="form-control" id="stock" name="stock"
									value="${(actIntegral.stock)!''}" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								库存表示可以卖多少产品
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动时间：</label> 
							<div class="col-lg-4">
								<input
									type="text" id="startTime" name="startTime"
									class="form-control"
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});"
									value="${(actIntegral.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
									required/> 
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime"
									name="endTime" class="form-control"
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});"
									value="${(actIntegral.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}" 
									required/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-10 ejava-errinforight">
								只有在当前时间内才能看到此积分商城商品
							</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>活动描述: </label>
							<div class="col-lg-8">
								<script type="text/plain" 
									id="myEditor"
									style="width: 100%; height: 240px;"><#noescape>${(actIntegral.descinfo)!}</#noescape></script>
								<script type="text/javascript">
							    	var um = UM.getEditor('myEditor');
								</script>
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


<div style="display: none">
	<div id="productDlg" class="easyui-dialog popBox" title="商品列表"
		style="width: 980px; height: 520px;"
		data-options="resizable:true,closable:true,closed:true,cache: false,modal: true"
		buttons="#dlg-buttons-brand">

		<div class="easyui-layout" data-options="fit:true">
			<table id="brandDataGrid" class="easyui-datagrid"
				data-options="
							rownumbers:true,
							autoRowHeight:false,
							striped : true,
							singleSelect : true,
							fit:true,
							fitColumns:true,
							pagination:true,
							pageSize:'20',
							url:'${domainUrlUtil.urlResources}/seller/product/list?q_state=6',
							method:'get'">
				<thead>
					<tr>
						<th field="name1" width="300" align="left">商品名称</th>
						<th field="productStock" width="150" align="center">商品库存</th>
						<th field="malMobilePrice" width="150" align="center">商品价格</th>
					</tr>
				</thead>
			</table>
		</div>

		<div id="dlg-buttons-brand" style="text-align: right">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="productSubmit()">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#productDlg').dialog('close')">取消</a>
		</div>
	</div>
</div>

<div style="display: none">
	<div id="productDlg" class="easyui-dialog popBox" title="商品列表"
		style="width: 980px; height: 520px;"
		data-options="resizable:true,closable:true,closed:true,cache: false,modal: true"
		buttons="#dlg-buttons-brand">
	
		<div class="easyui-layout" data-options="fit:true">
			<table id="brandDataGrid" class="easyui-datagrid"
				data-options="
							rownumbers:true,
							autoRowHeight:false,
							striped : true,
							singleSelect : true,
							fit:true,
							fitColumns:true,
							pagination:true,
							pageSize:'20',
							url:'${domainUrlUtil.urlResources}/seller/product/list?q_state=6',
							method:'get'">
				<thead>
					<tr>
						<th field="name1" width="300" align="left">商品名称</th>
						<th field="productStock" width="150" align="center">商品库存</th>
						<th field="malMobilePrice" width="150" align="center">商品价格</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div id="dlg-buttons-brand" style="text-align: right">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="productSubmit()">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#productDlg').dialog('close')">取消</a>
		</div>
	</div>
	
	<script language="javascript">
		function productSubmit() {
			var selectedRow = $("#brandDataGrid").datagrid('getSelections');
			if (selectedRow == null) {
				$.messager.alert('友情提示', '请至少选择一个对象');
				return false;
			}
			var callbackfunc = eval('productCallBack');
			callbackfunc(selectedRow);
			$("#productDlg").dialog('close');
		}

		$("#isWithMoney").change(function(){
			var isWithMoney = $("#isWithMoney").val();
			if(isWithMoney==0){
				$("#priceMoney").val('');
			}
		})
	</script>
</div>
<#include "/seller/commons/_addcommonfooter.ftl"> 
<#include "/seller/commons/_end.ftl">