<#include "/seller/commons/_head.ftl">

<script language="javascript">

$(function(){
	initMenu('single');
	
	$("button[type='button'].back").click(function(){
 		window.location.href= domain+"/seller/promotion/single";
	});
	
	//初始化已选择的商品
	var proids = $(":input[name='ids']");
	if(proids.length > 0){
		if(!selectedData || selectedData.length < 1)
			selectedData = new Array();
		$.each(proids,function(idx_,ele_){
			var data_ = {};
			var this_ = $(this);
			var id = this_.val();
			var name1 = this_.siblings(":input").val();
			var pprice = this_.parent().parent().next().find("div > input[name='mallPcPrice']").val();
			var mprice = this_.parent().parent().next().next().find("div > input[name='malMobilePrice']").val();
			
			data_.id = id;
			data_.name1 = name1;
			data_.mallPcPrice = pprice;
			data_.malMobilePrice = mprice;
			
			selectedData.push(data_);
		});
		log.i("初始化已有商品："+selectedData.length+"个");
	}
	
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
			var type = $("#type").val();
			if (type == null || type == "") {
				$.messager.alert('提示','请选择活动类型。');
				return;
			}
			
			var channel = $("#channel").val();
			if (channel == null || channel == "") {
				$.messager.alert('提示','请选择活动应用渠道。');
				return;
			}
			
			var num = parseInt($("#productNum").val());
	    	if (num <= 0) {
	    		$.messager.alert('提示', '请选择参加活动的商品。');
				return;
	    	}
	    	
	    	if(num>20){
	    		$.messager.alert('提示', '同一个活动最多选择20个商品。');
				return;
	    	}
	    	
	    	// 减免固定金额时检查价格
	    	var discount = parseFloat($("#discount").val());
	    	if (type == 1) {
		    	var name1 = "";
		    	$.each($("input[id='mallPcPrice']"), function(index, item){
		    		var mobprice = $(item).closest("div.pull-left").prev().find("input[name='malMobilePrice']").val();
		    		// 通用渠道检查商家价和移动端价
		    		if (channel == 1) {
		    			if (parseFloat($(item).val()) <= discount) {
				    		name1 = $(item).closest("div.pull-left").prev().find("input[name='productName']").val();
		    				console.info("pc:"+$(item).val()+" | "+discount);
			    			return false;
			    		}
			    		if (parseFloat(mobprice) <= discount) {
			    			name1 = $(item).closest("div.pull-left").prev().find("input[name='productName']").val();
			    			console.info("mob:"+mobprice+" | "+discount);
			    			return false;
			    		}
		    		} else if (channel == 2) {
		    			// PC渠道只检查商城价
		    			if (parseFloat($(item).val()) <= discount) {
		    				name1 = $(item).closest("div.pull-left").prev().find("input[name='productName']").val();
			    			return false;
			    		}
		    		} else if (channel == 3) {
		    			// 移动端渠道只检查移动端价
			    		if (parseFloat(mobprice) <= discount) {
			    			name1 = $(item).closest("div.pull-left").prev().find("input[name='productName']").val();
			    			return false;
			    		}
		    		}
		    	});
		    	
		    	if (name1 != "") {
		    		$.messager.alert('提示', '商品【' + name1 + '】的价格已低于优惠金额，请重新选择商品（参加活动的商品价格必须大于优惠金额）。');
					return;
		    	}
	    	} else {
	    		// 折扣时检查折扣值，在0到1之间
	    		if (discount <= 0 || discount >= 1) {
	    			$.messager.alert('提示', '折扣值必须在0到1之间。');
					return;
	    		}
	    	}
			validator.defaultSubmit();
		},
		fields : {
			actSingleName : {
				validators : {
					 notEmpty: true
				}
			},
			type : {
				validators : {
					 notEmpty: true
				}
			},
			discount : {
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
			}
		}
	});
	
	$('#pro').click(function(){
		$('#gd_dataGrid').datagrid('reload');
		
		var width_ = '1000';
	    if(ismobile()){
			width_ = "100%";
	    }
		$("#goodsListDiv").window({
			top:50,
			width : width_,
			height : 505,
			title : "选择商品",
			modal : true,
			shadow : false,
			collapsible : false,
			minimizable : false,
			maximizable : false
		});
	});
	
	$(document).on('click','.a-del-proItem',function () {
		var prolist = $(".addItemT");
		if(prolist.length < 2){
			$.messager.show({
				"title":"提示",
				"msg":"至少添加一个商品",
				"showType":"show"
			});
			return false;
		}
		var parent_ = $(this).closest('.addItemT');
		var num = parseInt($("#productNum").val()) - 1;
		$("#productNum").val(num);
		
		var delid = parent_.find("div:first > div input[type='hidden'][name='ids']").val();
		//删除选中的数据
		if(selectedData && selectedData.length > 0){
			$.each(selectedData,function(idx,ele){
				if(ele.id == delid){
					log.i("删除："+ele.id);
					selectedData.splice(idx,1);
					return false;
				}
			});
		}
		parent_.remove();
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
						href="${domainUrlUtil.urlResources}/seller/promotion/single">单品立减</a>
					</li>
					<li class="active">编辑单品立减</li>
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
						action="${domainUrlUtil.urlResources}/seller/promotion/single/update"
						enctype="multipart/form-data" data-bv-message="该项必填">
						<!-- 用于计算数量 -->
						<input type="hidden" id="productNum" name="productNum"
							value="${(productNum)!0}"> <input type="hidden" id="id"
							name="id" value="${(actSingle.id)!''}"> <input
							type="hidden" id="status" name="status"
							value="${(actSingle.status)!''}">

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动名称：</label>
							<div class="col-lg-4">
								<input class="form-control" type="text" id="actSingleName"
									name="actSingleName" value="${(actSingle.actSingleName)!''}"
									data-bv-stringlength="true" data-bv-stringlength-min="1"
									data-bv-stringlength-max="40"
									data-bv-stringlength-message="活动名称1-40位长度" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动类型：</label>
							<div class="col-lg-4"><@cont.select id="type"
								value="${(actSingle.type)!''}" class="form-control"
								codeDiv="ACT_SINGLE_TYPE" mode="1"/></div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">优惠额/折扣：</label>
							<div class="col-lg-3">
								<input class="form-control" type="text" id="discount"
									name="discount" value="${(actSingle.discount)!''}" min="0"
									max="999999" pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
									data-bv-regexp-message="金额保留两位小数"
									data-bv-lessthan-inclusive="true"
									data-bv-lessthan-message="金额必须小于999999"
									data-bv-greaterthan-inclusive="true"
									data-bv-greaterthan-message="金额必须大于0" data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字" />
							</div>
							<label class="col-lg-7 ejava-errinforight">活动类型为减免金额时为金额（如10为减免10元），折扣类型时为折扣（如0.90为打九折）</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">活动商品：</label>
							<div class="col-lg-1">
								<input type="button" value="选择商品" id="pro" />
							</div>
							<label class="col-lg-8 ejava-errinforight">一个订单满减活动最多添加20个商品</label>
						</div>

						<div class="ejformstyle">
							<#if productList??> 
							<#list productList as product>
							<div class="addItemT form-group">
								<div class="col-lg-4 pull-left">
									<label class="col-sm-3 control-label">商品名称：</label>
									<div class="col-sm-9">
										<input type="hidden" name="ids" value="${(product.id)!''}" />
										<input type="text" id="productName" name="productName"
											class="input-pro form-control" readonly value="${(product.name1)!''}" />
									</div>
								</div>
								<div class="col-lg-3 pull-left">
									<label class="col-sm-4 control-label">商城价：</label>
									<div class="col-sm-8">
										<input type="text" id="mallPcPrice" name="mallPcPrice"
											readonly class="form-control" value="${(product.mallPcPrice)!''}" />
									</div>
								</div>
								<div class="col-lg-3 pull-left">
									<label class="col-sm-4 control-label">移动端价：</label>
									<div class="col-sm-8">
										<input type="text" id="malMobilePrice" name="malMobilePrice"
											readonly class="form-control" value="${(product.malMobilePrice)!''}" />
									</div>
								</div>
								<div class="col-lg-2 pull-right">
									<button type="button"
										class="a-del-proItem btn btn-del btn-primary">删除</button>
								</div>
							</div>
							</#list> 
							</#if>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>活动时间：</label>
							<div class="col-lg-4">
								<input type="text" id="startTime" name="startTime"
									class="form-control"
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});"
									value="${(actSingle.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}" />
							</div>
							<div class="col-lg-4">
								<input type="text" id="endTime" name="endTime"
									class="form-control"
									onblur="updateStatus(this.name);"
									onclick="WdatePicker({startDate:'%y-%M-{%d+1} 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});"
									value="${(actSingle.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>应用渠道：</label>
							<div class="col-lg-4"><@cont.select id="channel"
								value="${(actSingle.channel)!''}" codeDiv="CHANNEL"
								class="form-control"/></div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">活动描述：</label>
							<div class="col-lg-8">
								<textarea name="remark" id="remark" class="form-control">${(actSingle.remark)!''}</textarea>
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

<div style="display: none" id="goodsListDiv"><#include
	"goodsDialog.ftl"/></div>
<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">
