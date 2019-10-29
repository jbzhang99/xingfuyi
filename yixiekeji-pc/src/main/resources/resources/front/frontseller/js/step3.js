$(function() {
	jQuery.validator.addMethod("isMobile", function() {
		var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		return re.test($('#mobile').val());
	});

	$(function() {
		var opt = {
			//省的样式（可选）
			pClass : 'valid',
			//市的样式（可选）
			cClass : 'valid',
			//地区的样式（可选）
			aClass : 'valid',
			//默认省
			defaultProvince : '2',
			//默认市
			defaultCity : '716',
			//默认地区
			defaultArea : '',
			compent : 'td_area',
			areaRequired : false,
			domain : domain,
			provinceName : 'bankProvince',
			cityName : 'bankCity'
		};
		//实例此对象，参数可选
		var area = new AreaSupport(opt);
		//初始化对象并组装DOM添加至给定的选择器对象。注意，此对象的init返回的是JQuery对象
		area.getProvince().appendTo($('#td_area'));
		//如果希望进入页面加载到市，则可以手动执行此初始化方法
		area.getCity().appendTo($('#td_area'));
		//如果希望进入页面加载到地区，则可以手动执行此初始化方法
		// area.getArea().appendTo($('#td_area'));
	});
	
	jQuery("#form_credentials_info").validate({
		rules : {
			bankUser : {
				required : true
			},
			bankName : {
				required : true
			},
			bankNameBranch : {
				required : true
			},
			brandNameCode : {
				required : true
			},
			bankCode : {
				required : true
			},
			taxLicense : {
				required : true
			},
			bankProvince : {
				required : true
			},
			bankCity : {
				required : true
			}
		},
		messages : {
			bankUser : {
				required : "开户行账号名称不能为空"
			},
			bankName : {
				required : "开户行不能为空"
			},
			bankNameBranch : {
				required : "开户行支行名称不能为空"
			},
			brandNameCode : {
				required : "开户行支行联行号不能为空"
			},
			bankCode : {
				required : "银行账号不能为空"
			},
			taxLicense : {
				required : "税务登记证号不能为空"
			},
			bankProvince : {
				required : "开户行省不能为空"
			},
			bankCity : {
				required : "开户行市不能为空"
			}
		}
	});
	
	$('#form_credentials_info').submit(function() {
		if ($(this).valid()) {
			$('#loadingDiv').show();
		}
	});
});