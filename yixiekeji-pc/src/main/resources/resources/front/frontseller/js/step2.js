$(function() {
	jQuery.validator.addMethod("isMobile", function() {
		var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		return re.test($('#mobile').val());
	});

	var opt = {
		// 省的样式（可选）
		pClass : 'valid',
		// 市的样式（可选）
		cClass : 'valid',
		// 地区的样式（可选）
		aClass : 'valid',
		// 默认省
		defaultProvince : '',
		// 默认市
		defaultCity : '',
		// 默认地区
		defaultArea : '',
		domain : domain,
		provinceName : 'companyProvince',
		areaRequired : false,
		cityName : 'companyCity'
	};
	// 实例此对象，参数可选
	var area = new AreaSupport(opt);
	// 初始化对象并组装DOM添加至给定的选择器对象。注意，此对象的init返回的是JQuery对象
	area.getProvince().appendTo($('#td_area'));
	// 如果希望进入页面加载到市，则可以手动执行此初始化方法
	area.getCity().appendTo($('#td_area'));
	// 如果希望进入页面加载到地区，则可以手动执行此初始化方法
	// area.getArea().appendTo($('#td_area'));

	jQuery("#form_company_info").validate({
		rules : {
			company : {
				required : true,
				 remote:{
				    url: domain+"/store/validate.html",     //后台处理程序
		            type: "post",
		            dataType: "json",
		            data: {
		                  "val" : function(){return $('#company').val()},
		                  "type" : 1
				     }
				}
			},
			companyProvince : {
				required : true
			},
			companyCity : {
				required : true
			},
			companyAdd : {
				required : true
			},
			legalPerson : {
				required : true
			},
			personPhone : {
				required : true
			},
			telephone : {
				required : true
			},
			email : {
				required : true,
				email : true
			},
			organization : {
				required : true
			},
			companyStartTime : {
				required : true
			},
			companyEndTime : {
				required : true
			},
			up_bussinessLicenseImage : {
				required : true
			},
			legalPersonCard : {
				required : true
			},
			up_personCardUp : {
				required : true
			},
			up_personCardDown : {
				required : true
			},
			bussinessLicense : {
				required : true
			}

		},
		messages : {
			company : {
				required : "公司名不能为空",
			    remote : "公司名称已存在"
			},
			companyProvince : {
				required : "请填写公司所在省"
			},
			companyCity : {
				required : "请填写公司所在市"
			},
			companyAdd : {
				required : "请填写公司详细地址"
			},
			legalPerson : {
				required : "请填写法定代表人"
			},
			personPhone : {
				required : "请填写法人联系电话"
			},
			telephone : {
				required : "请填写公司电话"
			},
			email : {
				required : "邮箱不能为空",
				email : "邮箱格式不正确"
			},
			organization : {
				required : "请填写组织机构代码"
			},
			companyStartTime : {
				required : "请填写营业开始日期"
			},
			companyEndTime : {
				required : "请填写营业结束日期"
			},
			up_bussinessLicenseImage : {
				required : "请上传营业执照扫描件"
			},
			legalPersonCard : {
				required : "请填写法定代表人身份证号码"
			},
			up_personCardUp : {
				required : "请上传身份证正面扫描件"
			},
			up_personCardDown : {
				required : "请上传身份证反面扫描件"
			},
			bussinessLicense : {
				required : "请填写营业执照号"
			}
		}
	});

	$('#form_company_info').submit(function() {
		if ($(this).valid()) {
			$('#loadingDiv').show();
		}
	});
});