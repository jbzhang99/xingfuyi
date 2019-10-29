$(function() {
	jQuery.validator.addMethod("isMobile", function() {
		var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		return re.test($('#mobile').val());
	});

	jQuery("#form_store_info").validate({
		rules : {
			name : {
				required : true,
				 remote:{
				    url: domain+"/store/validate.html",     //后台处理程序
		            type: "post",
		            dataType: "json",
		            data: {
		                  "val" : function(){return $('#username').val()},
		                  "type" : 2
				     }
				}
			},
			seller_name : {
				required : true,
				 remote:{
				    url: domain+"/store/validate.html",     //后台处理程序
		            type: "post",
		            dataType: "json",
		            data: {
		                  "val" : function(){return $('#seller_name').val()},
		                  "type" : 3
				     }
				}
			}
		},
		messages : {
			name : {
				required : "用户名不能为空",
				remote : "该账号已存在"
			},
			seller_name : {
				required : "店铺名称不能为空",
				remote : "该店铺名称已存在"
			}
		}
	});
	
	$('#form_store_info').submit(function() {
		if ($(this).valid()) {
			$('#loadingDiv').show();
		}
	});
});