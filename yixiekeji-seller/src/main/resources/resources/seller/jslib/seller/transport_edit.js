var no_trans_city_count = 0;
var mail_city_count = 0;
var express_city_count = 0
var ems_city_count = 0;
var mail_batch = 0;
var express_batch = 0;
var ems_batch = 0;

function deleteCity(obj){
	$.messager.confirm('确认', '确认要删除当前地区的设置么？', function(r) {
		if (r) {
			remove_trans_city(obj);
		}
	});
}

$(function() {
	initMenu('sellerTransport');
	
	$(document).on("change",".db_box_main_rdinary :text.in" ,function(obj,p){
		if(isNaN($(this).val())){
			$(this).val(1);
			$(this).focus();
		} else{
			if(transType == "" || Number(transType) < 2){
				// 按件时件数必须为整数
				if ($(this).hasClass("money")) {
					$(this).val(parseFloat($(this).val()).toFixed(2));
				} else {
					if(!($(this).val() % 1 === 0)){
						$(this).val(parseInt($(this).val()));
					}
				}
			} else if (Number(transType) == 2) {
				// 按重量时保持两位小数
				$(this).val(parseFloat($(this).val()).toFixed(2));
			}
		}
	});
	
	$("button[type='button'].back").click(function(){
 		window.location.href=currentBaseUrl;
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
			var isChecked = false;
			$(":checkbox[id^='transtype_']").each(function(idx,e){
				if($(this).is(":checked")){
					isChecked = true;
					return false;
				}
			});
			if(!isChecked){
				validator.disableSubmitButtons(false);
				$.messager.alert('提示', '请至少选择一种运送方式');
				return false;
			}
			
			var errMsg = "";
			// 校验输入是否是数字
			if($("#transtype_mail").is(":checked")){
				// 首重或首件
				$(":input[id^='mail_trans_weight']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 首重或首件费用
				$(":input[id^='mail_trans_fee']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 增重或增件
				$(":input[id^='mail_trans_add_weight']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 增重或增件费用
				$(":input[id^='mail_trans_add_fee']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
			}
			
			if($("#transtype_express").is(":checked")){
				// 首重或首件
				$(":input[id^='express_trans_weight']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 首重或首件费用
				$(":input[id^='express_trans_fee']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 增重或增件
				$(":input[id^='express_trans_add_weight']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 增重或增件费用
				$(":input[id^='express_trans_add_fee']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
			}
			
			if($("#transtype_ems").is(":checked")){
				// 首重或首件
				$(":input[id^='ems_trans_weight']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 首重或首件费用
				$(":input[id^='ems_trans_fee']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 增重或增件
				$(":input[id^='ems_trans_add_weight']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
				// 增重或增件费用
				$(":input[id^='ems_trans_add_fee']").each(function(idx,e){
					var weight = $(this).val();
					if (weight == null || weight == "") {
						errMsg = "请输入完整的运费信息。";
						return false;
					}
					if (isNaN(weight)) {
						errMsg = "运费信息只能输入数字。";
						return false;
					}
				});
				if (errMsg != "") {
					validator.disableSubmitButtons(false);
					$.messager.alert('提示', errMsg);
					return false;
				}
			}
			
			validator.defaultSubmit();
		},
		fields:{
			transTime:{
				validators : {
					 notEmpty: true
				}
			}
		}
	});
	
	//编辑页面初始化重量模板
	if (id && transType == "2") {
		$.messager.progress({
			text : "加载中..."
		});
		jQuery.ajax({
			url : currentBaseUrl+'/transportType',
			data:{
				id:id,
				type:"weight"
			},
			success : function(data) {
				jQuery("#trans_detail").empty().html(data);
				$.messager.progress('close');
			}
		});
	}
	
	//索引从0开始，长度-2去掉table表头
	mail_city_count = jQuery("#mail_trans_city_info table tr").length - 1;
	express_city_count = jQuery("#express_trans_city_info table tr").length - 1;
	ems_city_count = jQuery("#ems_trans_city_info table tr").length - 1;
	no_trans_city_count=jQuery("#no_trans_city_info table tr").length - 1;
	jQuery("#mail_city_count").val(mail_city_count);
	jQuery("#express_city_count").val(express_city_count);
	jQuery("#ems_city_count").val(ems_city_count);
        jQuery("#no_trans_city_count").val(no_trans_city_count);

	jQuery(document).on("click", ":checkbox", function() {
		if ($(this).is(":checked")) {
			var id = jQuery(this).attr("id");
			jQuery("#" + id + "_info").show();
		} else {
			var id = jQuery(this).attr("id");
			jQuery("#" + id + "_info").hide();
		}
	});
	

	jQuery(document).on("click", "a[id^=batch_set_]", function() {
		jQuery(this).parent().parent().find(":checkbox").show();
		var type = jQuery(this).attr("transType");
		jQuery("#" + type + "_trans_city_op").show();
		jQuery(this).hide();
		jQuery("#batch_cancle_" + type).show();
		if (type == "mail") {
			mail_batch = 1;
		}
		if (type == "express") {
			express_batch = 1;
		}
		if (type == "ems") {
			ems_batch = 1;
		}
	});
	
	jQuery(document).on("click", "a[id^=batch_cancle_]", function() {
		jQuery(this).parent().parent().find(":checkbox").hide();
		var type = jQuery(this).attr("transType");
		jQuery("#" + type + "_trans_city_op").hide();
		jQuery(this).hide();
		jQuery("#batch_set_" + type).show();
		if (type == "mail") {
			mail_batch = 0;
		}
		if (type == "express") {
			express_batch = 0;
		}
		if (type == "ems") {
			ems_batch = 0;
		}
	});
	
	jQuery(document).on(
			"click",
			"a[id^=batch_del_]",
			function() {
				jQuery(this).parent().parent().find(
						":checkbox[checked=true][id^=trans_ck]").each(
						function() {
							jQuery(this).parent().parent().parent().parent()
									.remove();
						});
				jQuery("#mail_trans_all").attr("checked", false);
			});
	
	jQuery("a[id^=batch_config_]").click(function() {

	});
	
	jQuery(document).on("click",":checkbox[id$=mail_trans_all]" ,function() {
		if ($(this).is(":checked")) {
			jQuery(this).parent().parent().parent().find(":checkbox").attr(
					"checked", true);
		} else {
			jQuery(this).parent().parent().parent().find(":checkbox").attr(
					"checked", false);
		}
	});
	
	jQuery(":radio[id=transType]").change(function() {
		var this_ = this;
		$.messager.confirm('确认', '正在切换计价方式，确定继续么？', function(r) {
			if (r) {
				$.messager.progress({
					text : "请等待..."
				});
				var type = $(this_).val()==2?"weight":"";
				if ($(this_).val()==2) {
					transType = 2;
				} else {
					transType =1;
				}
				jQuery.ajax({
					url : currentBaseUrl+'/transportType',
					data:{
						id:id,
						type:type
					},
					success : function(data) {
						jQuery("#trans_detail").empty().html(data);
						$.messager.progress('close');
					}
				});
			}else{
				//恢复原选中状态
				$(this_).parent().siblings("label").find(":radio").attr("checked","checked");
				$(this_).parent().siblings("label").find(":radio").prop("checked",true);
			}
		});
	});
});

function trans_city(id) {
	var the_id = "";
	var s = "";
	if (id == "express") {
		the_id = "express" + express_city_count;
		if (express_batch == 1) {
			s = '<tr index="'
					+ express_city_count
					+ '"><td><span class="width2"><i><input id="trans_ck_'
					+ express_city_count
					+ '" name="trans_ck_'
					+ express_city_count
					+ '" type="checkbox" value="" /></i><input id="express_city_ids'
					+ express_city_count
					+ '" name="express_city_ids'
					+ express_city_count
					+ '" type="hidden" value="" /><input id="express_city_names'
					+ express_city_count
					+ '" name="express_city_names'
					+ express_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><input type="text" value="1" class="in easyui-numberbox" id="express_trans_weight'
					+ express_city_count
					+ '" name="express_trans_weight'
					+ express_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="express_trans_fee'
					+ express_city_count
					+ '" name="express_trans_fee'
					+ express_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox" id="express_trans_add_weight'
					+ express_city_count
					+ '" name="express_trans_add_weight'
					+ express_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="express_trans_add_fee'
					+ express_city_count
					+ '" name="express_trans_add_fee'
					+ express_city_count
					+ '" /></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		} else {
			s = '<tr index="'
					+ express_city_count
					+ '"><td><span class="width2"><i><input id="trans_ck_'
					+ express_city_count
					+ '" name="trans_ck_'
					+ express_city_count
					+ '" type="checkbox" value="" style="display:none;" /></i><input id="express_city_ids'
					+ express_city_count
					+ '" name="express_city_ids'
					+ express_city_count
					+ '" type="hidden" value="" /><input id="express_city_names'
					+ express_city_count
					+ '" name="express_city_names'
					+ express_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><input type="text" value="1" class="in easyui-numberbox" id="express_trans_weight'
					+ express_city_count
					+ '" name="express_trans_weight'
					+ express_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="express_trans_fee'
					+ express_city_count
					+ '" name="express_trans_fee'
					+ express_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox" id="express_trans_add_weight'
					+ express_city_count
					+ '" name="express_trans_add_weight'
					+ express_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="express_trans_add_fee'
					+ express_city_count
					+ '" name="express_trans_add_fee'
					+ express_city_count
					+ '" /></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		}
		jQuery("#" + id + "_trans_city_info table tr:last").after(s);
		jQuery("#" + id + "_trans_city_info").show();
		express_city_count = express_city_count + 1;
		jQuery("#express_city_count").val(express_city_count);
	}
	if (id == "ems") {
		the_id = "ems" + ems_city_count;
		if (ems_batch == 1) {
			s = '<tr index="'
					+ ems_city_count
					+ '"><td><span class="width2"><i><input id="trans_ck_'
					+ ems_city_count
					+ '" name="trans_ck_'
					+ ems_city_count
					+ '" type="checkbox" value="" /></i><input id="ems_city_ids'
					+ ems_city_count
					+ '" name="ems_city_ids'
					+ ems_city_count
					+ '" type="hidden" value="" /><input id="ems_city_names'
					+ ems_city_count
					+ '" name="ems_city_names'
					+ ems_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><input type="text" value="1" class="in easyui-numberbox" id="ems_trans_weight'
					+ ems_city_count
					+ '" name="ems_trans_weight'
					+ ems_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="ems_trans_fee'
					+ ems_city_count
					+ '" name="ems_trans_fee'
					+ ems_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox" id="ems_trans_add_weight'
					+ ems_city_count
					+ '" name="ems_trans_add_weight'
					+ ems_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="ems_trans_add_fee'
					+ ems_city_count
					+ '" name="ems_trans_add_fee'
					+ ems_city_count
					+ '" /></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		} else {
			s = '<tr index="'
					+ ems_city_count
					+ '"><td><span class="width2"><i><input id="trans_ck_'
					+ ems_city_count
					+ '" name="trans_ck_'
					+ ems_city_count
					+ '" type="checkbox" value="" style="display:none;" /></i><input id="ems_city_ids'
					+ ems_city_count
					+ '" name="ems_city_ids'
					+ ems_city_count
					+ '" type="hidden" value="" /><input id="ems_city_names'
					+ ems_city_count
					+ '" name="ems_city_names'
					+ ems_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><input type="text" value="1" class="in easyui-numberbox" id="ems_trans_weight'
					+ ems_city_count
					+ '" name="ems_trans_weight'
					+ ems_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="ems_trans_fee'
					+ ems_city_count
					+ '" name="ems_trans_fee'
					+ ems_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox" id="ems_trans_add_weight'
					+ ems_city_count
					+ '" name="ems_trans_add_weight'
					+ ems_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="ems_trans_add_fee'
					+ ems_city_count
					+ '" name="ems_trans_add_fee'
					+ ems_city_count
					+ '" /></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		}
		jQuery("#" + id + "_trans_city_info table tr:last").after(s);
		jQuery("#" + id + "_trans_city_info").show();
		ems_city_count = ems_city_count + 1;
		jQuery("#ems_city_count").val(ems_city_count);
	}
	if (id == "mail") {
		the_id = "mail" + mail_city_count;
		if (mail_batch == 1) {
			s = '<tr index="'
					+ mail_city_count
					+ '"><td><span class="width2"><i><input id="trans_ck_'
					+ mail_city_count
					+ '" name="trans_ck_'
					+ mail_city_count
					+ '" type="checkbox" value="" /></i><input id="mail_city_ids'
					+ mail_city_count
					+ '" name="mail_city_ids'
					+ mail_city_count
					+ '" type="hidden" value="" /><input id="mail_city_names'
					+ mail_city_count
					+ '" name="mail_city_names'
					+ mail_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><input type="text" value="1" class="in easyui-numberbox" id="mail_trans_weight'
					+ mail_city_count
					+ '" name="mail_trans_weight'
					+ mail_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="mail_trans_fee'
					+ mail_city_count
					+ '" name="mail_trans_fee'
					+ mail_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox" id="mail_trans_add_weight'
					+ mail_city_count
					+ '" name="mail_trans_add_weight'
					+ mail_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="mail_trans_add_fee'
					+ mail_city_count
					+ '" name="mail_trans_add_fee'
					+ mail_city_count
					+ '" /></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		} else {
			s = '<tr index="'
					+ mail_city_count
					+ '"><td><span class="width2"><i><input id="trans_ck_'
					+ mail_city_count
					+ '" name="trans_ck_'
					+ mail_city_count
					+ '" type="checkbox" value="" style="display:none;" /></i><input id="mail_city_ids'
					+ mail_city_count
					+ '" name="mail_city_ids'
					+ mail_city_count
					+ '" type="hidden" value="" /><input id="mail_city_names'
					+ mail_city_count
					+ '" name="mail_city_names'
					+ mail_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><input type="text" value="1" class="in easyui-numberbox" id="mail_trans_weight'
					+ mail_city_count
					+ '" name="mail_trans_weight'
					+ mail_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="mail_trans_fee'
					+ mail_city_count
					+ '" name="mail_trans_fee'
					+ mail_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox" id="mail_trans_add_weight'
					+ mail_city_count
					+ '" name="mail_trans_add_weight'
					+ mail_city_count
					+ '" /></td><td><input type="text" value="1" class="in easyui-numberbox money" id="mail_trans_add_fee'
					+ mail_city_count
					+ '" name="mail_trans_add_fee'
					+ mail_city_count
					+ '" /></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		}
		jQuery("#" + id + "_trans_city_info table tr:last").after(s);
		jQuery("#" + id + "_trans_city_info").show();
		mail_city_count = mail_city_count + 1;
		jQuery("#mail_city_count").val(mail_city_count);
	}
	
}




function no_trans_city(id) {
        if(no_trans_city_count>=1)
            return;
	var the_id = the_id = "no_trans" + no_trans_city_count;
	var s = '<tr index="'
					+ no_trans_city_count
					+ '"><td><span class="width2"><i><input id="noTrans" name="noTrans" type="checkbox" checked="checked" value="1" /></i><input id="no_trans_city_ids'
					+ no_trans_city_count
					+ '" name="no_trans_city_ids'
					+ no_trans_city_count
					+ '" type="hidden" value="" /><input id="no_trans_city_names'
					+ no_trans_city_count
					+ '" name="no_trans_city_names'
					+ no_trans_city_count
					+ '" type="hidden" value="" /><a  href="javascript:void(0);" onclick="edit_trans_city(this);" trans_city_type="'
					+ id
					+ '">编辑</a></span><span class="width1" id="'
					+ the_id
					+ '">未添加地区</span></td><td><span class="width3"><a href="javascript:void(0);" onclick="if(confirm(\'确认要删除当前地区的设置么？\'))remove_trans_city(this)">删除</a></span></td></tr>';
		
		jQuery("#" + id + "_city_info table tr:last").after(s);
		jQuery("#" + id+"_city_info").show();
		no_trans_city_count = no_trans_city_count + 1;
		jQuery("#no_trans_city_count").val(no_trans_city_count);
	}


function edit_trans_city(obj) {
	
	var trans_city_type = jQuery(obj).attr("trans_city_type");
	var trans_index = $(obj).closest("tr").attr("index");
	log.i(trans_index);
	jQuery.ajax({
		type : 'POST',
		url : domain + '/seller/operate/sellerTransport/transport_area',
		data : {
			"trans_city_type" : trans_city_type,
			"trans_index" : trans_index
		},
		success : function(data) {
			$("#editbyarea").show();
			$("#editbyarea").html(data);
			var left = $(obj).offset().left;
			var top = $(obj).offset().top;
			var width_ = ($(".area_box").width())/2;
			var height_ = $(".area_box").height();
			jQuery(".area_box").animate({
				"top" : height_,
				"left" : width_
			});
			
			var bottomheight_ = $(".area_box_bottom").height();
			var areTop = $(".area_box").offset().top;
			var btnpos = top - bottomheight_ - 2;
			jQuery(".area_box_bottom").animate({
				"top" : areTop + height_,
				"margin-left" : "-1px"
			});
			
			
		}
	});
}

function remove_trans_city(obj) {
	jQuery(obj).parent().parent().parent().remove();
}

function closeWin() {
	$('#newstypeWin').window('close');
}