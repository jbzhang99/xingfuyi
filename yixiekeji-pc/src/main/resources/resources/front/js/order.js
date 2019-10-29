
/**
 * 验证收货地址消息提示
 * 
 * @param divId
 * @param value
 */

/** *********************************************验证收获地址信息****************************************************** */
function check_Consignee(divId){
	var errorFlag = false;
	var errorMessage = null;
	var value = null;
	var consignee_id = $("#consignee_form_id").val();
	var flowType = parent.$("#flowType").val();
	// 验证收货人姓名
	if(divId =="name_div"){
		value = $("#consignee_name").val();
		if(isEmpty(value)){
			errorFlag = true;
			errorMessage = "请您填写收获人姓名";
		}
		if(flowType ==10){
			if(!/^([\u4e00-\u9fa5])([\u4e00-\u9fa5\\.]){0,23}([\u4e00-\u9fa5])$/i.test(value)){
				errorFlag = true;
			}else{
				if(value.search(/\.{2,}/) > -1){
					errorFlag = true;
				}
			}
			if(errorFlag){
				errorMessage = "收货人姓名只支持中文简体和单字节字符圆点，长度不超过25个字符";
			}
		}else{
			if (value.length > 25) {
				errorFlag = true;
				errorMessage = "收货人姓名不能大于25位";
			}
			if (!is_forbid(value)) {
				errorFlag = true;
				errorMessage = "收货人姓名中含有非法字符";
			}
		}
	}
	//验证地区是否是完整


	else if(divId == "area_div"){
		var provinceId = $("#consignee_province").find("option:selected").val();
		var cityId = $("#consignee_city").find("option:selected").val();
		var countyId = $("#consignee_county").find("option:selected").val();
		var townId = $("#consignee_town").find("option:selected").val();
		// 验证地区是否正确
		if (isEmpty(provinceId) || isEmpty(cityId) || isEmpty(countyId)
				|| ($("#span_town").html() != null && $("#span_town").html() != "" && !$("#span_town").is(":hidden") && isEmpty(townId))) {
			errorFlag = true;
			errorMessage = "请您填写完整的地区信息";
		}
	}
	//验证邮箱格式

	else if(divId == "email_div"){
		value = $("#consignee_email").val();
		if(!isEmpty(value)){
			if (value.length > 50) {
				errorFlag = true;
				errorMessage = "邮箱长度不能大于50位";
			}
			if (!check_email_new(value)) {
				errorFlag = true;
				errorMessage = "邮箱格式不正确";
			}
			if(consignee_id == ""){
				if(value.indexOf("*") > -1){
					errorFlag = true;
					errorMessage = "邮箱格式不正确";
				}
			}
		}

	}

	//验证收货人地址
	else if (divId == "address_div") {
		value = $("#consignee_address").val();
		if (isEmpty(value)) {
			errorFlag = true;
			errorMessage = "请您填写收货人详细地址";
		}
		if (!is_forbid(value)) {
			errorFlag = true;
			errorMessage = "收货人详细地址中含有非法字符";
		}
		if (value.length > 50) {
			errorFlag = true;
			errorMessage = "收货人详细地址过长";
		}
	}

	//验证手机号码

	else if(divId == 'call_div'){
		value = $("#onsignee_mobile").val();
		if(isEmpty(value)){
			errorFlag = true;
			errorMessage = "请您填写收货人手机号码"
		}else{
			if (!check_mobile_new(value)) {
				errorFlag = true;
				errorMessage = "手机号码格式不正确";
			}
			if(consignee_id == "" && value.indexOf("*") > -1){
				errorFlag = true;
				errorMessage = "手机号码格式不正确";
			}
		}
	}
	if (errorFlag) {
		$("#" + divId + "_error").html(errorMessage);
		$("#" + divId + "_error").addClass("message");
		return false;
	} else {
		$("#" + divId + "_error").removeClass("message");
		$("#" + divId + "_error").html("");
	}
	return true;
}

 /** *********************************************检查收获信息格式****************************************************** */

/**
 * 检查邮箱格式
 * @param email
 * @returns {Boolean}
 */
function check_email_new(email){  
   if(email){
	   var reg=/^[0-9a-zA-Z_\-\.]{1}\**@\w+([-.]\w+)*\.\w+([-.]\w+)*(\s*$)/;
	   if(!reg.test(email) && !check_email(email)){
		   return false;
	   }
   		return true;
   }
   return false;
}

/**
 * 检查邮箱格式
 * @param email
 * @returns {Boolean}
 */
function check_email(email){  
   if(email){
   var myReg=/(^\s*)\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\s*$)/;
   if(!myReg.test(email)){return false;}
   return true;
   }
   return false;
}
  


/**
 * 判断是否是空
 * @param value
 */
function isEmpty(value){
	if(value == null || value == "" || value == "undefined" || value == undefined || value == "null"){
		return true;
	}
	else{
		value = value.replace(/\s/g,"");
		if(value == ""){
			return true;
		}
		return false;
	}
}


// 正则
function trimTxt(txt){
 return txt.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
function is_forbid(temp_str){
    temp_str=trimTxt(temp_str);
	temp_str = temp_str.replace('*',"@");
	temp_str = temp_str.replace('--',"@");
	temp_str = temp_str.replace('/',"@");
	temp_str = temp_str.replace('+',"@");
	temp_str = temp_str.replace('\'',"@");
	temp_str = temp_str.replace('\\',"@");
	temp_str = temp_str.replace('$',"@");
	temp_str = temp_str.replace('^',"@");
	temp_str = temp_str.replace('.',"@");
	temp_str = temp_str.replace(';',"@");
	temp_str = temp_str.replace('<',"@");
	temp_str = temp_str.replace('>',"@");
	temp_str = temp_str.replace('"',"@");
	temp_str = temp_str.replace('=',"@");
	temp_str = temp_str.replace('{',"@");
	temp_str = temp_str.replace('}',"@");
	var forbid_str=new String('@,%,~,&');
	var forbid_array=new Array();
	forbid_array=forbid_str.split(',');
	for(i=0;i<forbid_array.length;i++){
		if(temp_str.search(new RegExp(forbid_array[i])) != -1)
		return false;
	}
	return true;
}

/**
 * 检查手机号码
 * 
 * @param mobile
 * @returns {Boolean}
 */
function check_mobile(mobile) {
	var regu = /^\d{11}$/;
	var re = new RegExp(regu);
	if (!re.test(mobile)) {
		return false;
	}
	return true;
}

/**
 * 检查手机号码(手机号4-7位为*)
 * 
 * @param mobile
 * @returns {Boolean}
 */
function check_mobile_new(mobile) {
	var regu = /^\d{3}\*\*\*\*\d{4}$/;
	var re = new RegExp(regu);
	if (!(re.test(mobile) || check_mobile(mobile))) {
		return false;
	}
	return true;
}

/** *********************************************所在地区****************************************************** */
/**
 * 获取省份列表
 */

 // /

/**
 * 保存收货地址（包含保存常用人收货地址，根据id区分）
 */
