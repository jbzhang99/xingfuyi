//库存价格设置js
function setStock(allstocks) {
	var stock_ = 0;
	$.each(allstocks, function(i, e) {
		stock_ += Number(e);
	});
	$("#productStock").val(stock_);
}

function setPprice(allpprice) {
	$("#mallPcPrice").val(Math.min.apply(null, allpprice));
}

function setMprice(allmprice) {
	$("#malMobilePrice").val(Math.min.apply(null, allmprice));
}

function statisticsStock() {
	var allstocks = new Array();
	$("input[id^='inventory_details_stock_']").each(function(idx, e) {
		if(!isNaN($(e).val())){
			allstocks.push($(e).val());
		}
	});
	setStock(allstocks);
}

function statisticsPprice(obj) {
	if(obj && !isNaN($(obj).val())){
		var thisval_ = $(obj).val();
		if(Number(thisval_) < Number(protectedPrice)){
			return false;
		}
	}
	var allspprice = new Array();
	$("input[id^='inventory_details_pprice_']").each(function(idx, e) {
		if(!isNaN($(e).val())){
			allspprice.push($(e).val());
		}
	});
	setPprice(allspprice);
	
	return true;
}

function statisticsMprice(obj) {
	if(obj && !isNaN($(obj).val())){
		var thisval_ = $(obj).val();
		if(Number(thisval_) < Number(protectedPrice)){
			return false;
		}
	}
	var allmprice = new Array();
	$("input[id^='inventory_details_mprice_']").each(function(idx, e) {
		if(!isNaN($(e).val())){
			allmprice.push($(e).val());
		}
	});
	setMprice(allmprice);
	
	return true;
}

function illegalNumber(o, p) {
	var val = $(o).val();
	var zer_ = p ? p : 0;
	if (isNaN(val) || Number(val) < zer_) {
		var pri_ = p ? p : 1;
		$(o).val(pri_);
		if (p) {
			$.messager.show({
				title : '提示',
				msg : '价格不能低于保护价',
				showType : 'show'
			});
			return p;
		} else
			return 1;
	} else{
		if(Number(val) > 999999){
			val = 999999;
			$(o).val(val);
		}
		return Number(val);
	}
}

$(function() {
	if(from == 'onSale'){
		initMenu('onsale');
	} else{
		initMenu('waitsale');
	}
	
	$("button[type='button'].back").click(function(){
		window.location.href= domain+"/seller/product/"+from;
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
			validator.defaultSubmit();
		}
	});

	// 启用规格，初始化
	if (isNorm == 2) {
		statisticsStock();
		statisticsPprice();
		statisticsMprice();
	} else {
		$("body").on("blur","#mallPcPrice",function(){
			illegalNumber($(this), protectedPrice);
		});
		
		$("body").on("blur","#malMobilePrice",function(){
			illegalNumber($(this), protectedPrice);
		});
		
		$("body").on("blur","#productStock",function(){
			illegalNumber($(this));
		});
	}

	//动态验证
	var validator_ = $("#addform").data('bootstrapValidator');
	for(var i=0;i<goodssize;i++){
		var stock_ = "inventory_details_stock_" + i;
		var pprice_ = "inventory_details_pprice_" + i;
		var mprice_ = "inventory_details_mprice_" + i;
		
		validator_.addField(stock_,{
			validators: {  
				notEmpty: true,
				numeric: {
	        		message:'请输入数字'
	        	},
	          	regexp: {
	          		regexp: /^\d{1,6}$/,
	          		message: '库存为整数，取值1-999999'
	            },
	            callback: {
					callback: function(value, validator,field) {
						statisticsStock();
						return true;
					}
				}
			}  
		});
		
		validator_.addField(pprice_,{
			validators: {
				notEmpty: true,
				numeric: {
	        		message:'请输入数字'
	        	},
  		       	regexp: {
  		       		regexp: /^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$/,
  		       		message: '请输入正确的价格，金额保留两位小数'
  		       	},
  		       	lessThan:{
  		       		message: '价格必填小于999999',
  		       		value: 999999
  		       	},
               callback: {
					message: '价格不能低于保护价',
					callback: function(value, validator,field) {
						return statisticsPprice(field[0]);
					}
				}
	    	}
		});
		
		validator_.addField(mprice_,{
			validators: {
				notEmpty: true,
				numeric: {
					message:'请输入数字'
				},
				regexp: {
					regexp: /^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$/,
					message: '请输入正确的价格，金额保留两位小数'
				},
				lessThan:{
					message: '价格必填小于999999',
					value: 999999
				},
				callback: {
					message: '价格不能低于保护价',
					callback: function(value, validator,field) {
						return statisticsMprice(field[0]);
					}
				}
			}
		});
	}
	
});