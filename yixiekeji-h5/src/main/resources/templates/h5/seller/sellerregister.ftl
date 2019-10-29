<#include "/h5/commons/_head.ftl" />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">商家入驻申请</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
	<!-- 头部 end-->
	<h2 class="b_title">公司资质信息</h2>
	<div class="s-container" id="container">
		<@form.form action="${(domainUrlUtil.urlResources)!}/storeregister/doregister.html" id="registerForm" name="registerForm" method="post" enctype="multipart/form-data">
		<input type="hidden" id="id" name="id" value="${(sellerApply.id)!''}">
		<input type="hidden" id="state" name="state" value="${(sellerApply.state)!''}">
		<h3 class="b_h3 mar_top">公司及联系人信息</h3>
		<div class="business-d-box">
			 <div class="form-group b_form-group">
				<label><font class="clr53">*</font>公司名称：</label>
				<input type="text" class="form-control" id="company" name="company" value="${(sellerApply.company)!''}" placeholder="公司名称">
				<p class="clr53 font12"></p>
		     </div>
		     <div class="form-group b_form-group">
				<label><font class="clr53">*</font>公司所在地</label>
				<div class="flex">
					<div class="flex-1">
						<select class="form-control" id="companyProvince" name="companyProvince">
							<option value="">-- 请选择 --</option>
							<#if provinceList ??>
	             			<#list provinceList as region>
	                 			<option <#if sellerApply?? && sellerApply.companyProvince?? && sellerApply.companyProvince == (region.id)?string >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
	             			</#list>
	         				</#if>
						</select>
					</div>
					<div class="flex-1">
						<select class="form-control" id="companyCity" name="companyCity">
							<option value="">-- 请选择 --</option>
							<#if companyCityList ??>
	                 		<#list companyCityList as region>
	                     	<option <#if sellerApply?? && sellerApply.companyCity?? && sellerApply.companyCity == (region.id)?string >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
	               			</#list>
	             			</#if>
	         			</select>
					</div>
				</div>
				<p class="clr53 font12" id="companyCityError"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>公司详细地址：</label>
				<input type="text" class="form-control" id="companyAdd" name="companyAdd" value="${(sellerApply.companyAdd)!''}" placeholder="公司详细地址">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>公司电话：</label>
				<input type="text" class="form-control" id="telephone" name="telephone" value="${(sellerApply.telephone)!''}" placeholder="公司电话">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label>传真：</label>
				<input type="text" class="form-control" id="fax" name="fax" value="${(sellerApply.fax)!''}" placeholder="传真">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>法定代表人：</label>
				<input type="text" class="form-control" id="legalPerson" name="legalPerson" value="${(sellerApply.legalPerson)!''}" placeholder="法定代表人">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>联系人电话：</label>
				<input type="text" class="form-control" id="personPhone" name="personPhone" value="${(sellerApply.personPhone)!''}" placeholder="联系人电话">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>邮箱：</label>
				<input type="text" class="form-control" id="email" name="email" value="${(sellerApply.email)!''}" placeholder="邮箱">
				<p class="clr53 font12"></p>
			</div>
		</div>

		<h3 class="b_h3 mar_top">营业执照信息（副本）</h3>
		<div class="business-d-box">
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>营业执照号：</label>
				<input type="text" class="form-control" id="bussinessLicense" name="bussinessLicense" value="${(sellerApply.bussinessLicense)!''}" placeholder="营业执照号">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>组织机构代码：</label>
				<input type="text" class="form-control" id="organization" name="organization" value="${(sellerApply.organization)!''}" placeholder="组织机构代码">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>营业开始日期：</label>
				<input type="text" class="form-control" id="companyStartTime" name="companyStartTime" value="${(sellerApply.companyStartTime?string('yyyy-MM-dd'))!''}" placeholder="营业开始日期">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>营业结束日期：</label>
				<input type="text" class="form-control" id="companyEndTime" name="companyEndTime" value="${(sellerApply.companyEndTime?string('yyyy-MM-dd'))!''}" placeholder="营业结束日期">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label>营业执照扫描件：</label>
				<input type="file" class="form-control" id="up_bussinessLicenseImage" name="up_bussinessLicenseImage" placeholder="营业执照扫描件">
				<input type="hidden" id="bussinessLicenseImage" name="bussinessLicenseImage" value="${(sellerApply.bussinessLicenseImage)!''}">
				<p class="font12">请确保图片清晰，文字可辨并有清晰的红色公章。</p>
				<p class="clr53 font12"></p>
			</div>
		</div>

		<h3 class="b_h3 mar_top">一般纳税人证明</h3>
		<div class="business-d-box">
			<div class="form-group b_form-group">
				<label>法定代表人身份证：</label>
				<input type="text" class="form-control" id="legalPersonCard" name="legalPersonCard" value="${(sellerApply.legalPersonCard)!''}" placeholder="法定代表人身份证">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label>身份证正面图片：</label>
				<input type="file" class="form-control" id="up_personCardUp" name="up_personCardUp" placeholder="身份证正面图片">
				<input type="hidden" id="personCardUp" name="personCardUp" value="${(sellerApply.personCardUp)!''}">
				<p class="font12">请确保图片清晰。</p>
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>身份证背面图片：</label>
				<input type="file" class="form-control" id="up_personCardDown" name="up_personCardDown" placeholder="身份证背面图片">
				<input type="hidden" id="personCardDown" name="personCardDown" value="${(sellerApply.personCardDown)!''}">
				<p class="font12">请确保图片清晰。</p>
				<p class="clr53 font12"></p>
			</div>
		</div>

		<h3 class="b_h3 mar_top">开户银行信息(此账号为结算账号)</h3>
		<div class="business-d-box">
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>开户行账号名称：</label>
				<input type="text" class="form-control" id="bankUser" name="bankUser" value="${(sellerApply.bankUser)!''}" placeholder="开户行账号名称">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>开户行：</label>
				<input type="text" class="form-control" id="bankName" name="bankName" value="${(sellerApply.bankName)!''}" placeholder="开户行">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>开户行支行名称：</label>
				<input type="text" class="form-control" id="bankNameBranch" name="bankNameBranch" value="${(sellerApply.bankNameBranch)!''}" placeholder="开户行支行名称">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>开户行支行联行号：</label>
				<input type="text" class="form-control" id="brandNameCode" name="brandNameCode" value="${(sellerApply.brandNameCode)!''}" placeholder="开户行支行联行号">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>银行账号：</label>
				<input type="text" class="form-control" id="bankCode" name="bankCode" value="${(sellerApply.bankCode)!''}" placeholder="银行账号">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>开户行所在地：</label>
				<div class="flex">
					<div class="flex-1">
						<select class="form-control" id="bankProvince" name="bankProvince">
							<option value="">-- 请选择 --</option>
							<#if provinceList ??>
	             			<#list provinceList as region>
	                 			<option <#if sellerApply?? && sellerApply.bankProvince?? && sellerApply.bankProvince == (region.id)?string >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
	             			</#list>
	         				</#if>
						</select>
					</div>
					<div class="flex-1">
						<select class="form-control" id="bankCity" name="bankCity">
							<option value="">-- 请选择 --</option>
							<#if bankCityList ??>
	                 		<#list bankCityList as region>
	                     	<option <#if sellerApply?? && sellerApply.bankCity?? && sellerApply.bankCity == (region.id)?string >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
	               			</#list>
	             			</#if>
	         			</select>
					</div>
				</div>
				<p class="clr53 font12" id="bankCityError"></p>
			</div>
		</div>

		<h3 class="b_h3 mar_top">其他信息</h3>
		<div class="business-d-box">
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>税务登记证号：</label>
				<input type="text" class="form-control" id="taxLicense" name="taxLicense" value="${(sellerApply.taxLicense)!''}" placeholder="税务登记证号">
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>商家账号：</label>
				<input type="text" class="form-control" id="userName" name="userName" value="${(userName)!''}" placeholder="商家账号">
				<p class="font12">此账号为日后登录并管理商家中心时使用。</p>
				<p class="clr53 font12"></p>
			</div>
			<div class="form-group b_form-group">
				<label><font class="clr53">*</font>店铺名称：</label>
				<input type="text" class="form-control" id="sellerName" name="sellerName" value="${(sellerName)!''}" placeholder="店铺名称">
				<p class="font12">店铺名称注册后不可修改，请认真填写。</p>
				<p class="clr53 font12"></p>
			</div>
		</div>

		<#if sellerApply?? >
			<#if sellerApply.state?? && sellerApply.state != 2 && sellerApply.state != 3 >
				<p class="bort mar_top pad-top"><button type="submit" onclick="subClick()" class="btn btn-block btn-login" id="btn_apply_agreement_submit">提交</button></p>
			</#if>
		<#else>
			<p class="bort mar_top pad-top"><button type="submit" onclick="subClick()" class="btn btn-block btn-login" id="btn_apply_agreement_submit">提交</button></p>
		</#if>
		</@form.form>
	</div> 
	<!-- 主体结束 -->

	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.validate.min.js"></script>
<!--日历-->
<link href="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll.css" rel="stylesheet" type="text/css">
<script src="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll_002.js" type="text/javascript"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll.js" type="text/javascript"></script>   
<script src="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll_003.js" type="text/javascript"></script>
<script>
	function subClick() {
		$("p.clr53").each(function(index, element) {
			$(this).empty();
		});
	}

	$(document).ready(function () {
		<#if message??>
			// alert('${message}');
			$.dialog('alert','提示','${message}',2000);
		</#if>
		
		/*日历*/
		var currYear = (new Date()).getFullYear();
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.default = {
			theme: 'android-ics', //皮肤样式
			display: 'modal', //显示方式 
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy-mm-dd',
			lang: 'zh',
			showNow: false,
			startYear: currYear - 10, //开始年份
			endYear: currYear + 50 //结束年份
		};
		$("#companyStartTime").mobiscroll($.extend(opt['date'], opt['default']));
		$("#companyEndTime").mobiscroll($.extend(opt['date'], opt['default']));
		
		var validate = $("#registerForm").validate({
            debug: true, //调试模式取消submit的默认提交功能   
            //errorClass: "label.error", //默认为错误的样式类为：error   
            focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
            onkeyup: false,
            submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form 
            	var s1 = jQuery("#companyCity option:selected").val(); 
            	var s2 = jQuery("#bankCity option:selected").val(); 
            	if(s1 == null || s1 == "") {
            		$("#companyCityError").empty();
            		$("#companyCityError").append("<i class='fa fa-exclamation-triangle'>请选择公司所在地</i>");
            		return false;
            	} else {
            		$("#companyCityError").empty();
            	}
            	if(s2 == null || s2 == "") {
            		$("#bankCityError").empty();
            		$("#bankCityError").append("<i class='fa fa-exclamation-triangle'>请选择开户行所在地</i>");
            		return false;
            	} else {
            		$("#bankCityError").empty();
    			}
            	var up_bussinessLicenseImage = $("#up_bussinessLicenseImage").val();
            	var bussinessLicenseImage = $("#bussinessLicenseImage").val();
            	if ((up_bussinessLicenseImage == null || up_bussinessLicenseImage == "") 
            			&& (bussinessLicenseImage == null || bussinessLicenseImage == "")) {
            		var element = $("#up_bussinessLicenseImage").siblings("p.clr53");
            		element.empty();
            		element.append("<i class='fa fa-exclamation-triangle'>请选择营业执照扫描件</i>");
            		return false;
            	} else {
            		var element = $("#up_bussinessLicenseImage").siblings("p.clr53");
            		element.empty();
    			}
            	var up_personCardUp = $("#up_personCardUp").val();
            	var personCardUp = $("#personCardUp").val();
            	if ((up_personCardUp == null || up_personCardUp == "") 
            			&& (personCardUp == null || personCardUp == "")) {
            		var element = $("#up_personCardUp").siblings("p.clr53");
            		element.empty();
            		element.append("<i class='fa fa-exclamation-triangle'>请选择身份证正面图片</i>");
            		return false;
            	} else {
            		var element = $("#up_personCardUp").siblings("p.clr53");
            		element.empty();
    			}
            	var up_personCardDown = $("#up_personCardDown").val();
            	var personCardDown = $("#personCardDown").val();
            	if ((up_personCardDown == null || up_personCardDown == "") 
            			&& (personCardDown == null || personCardDown == "")) {
            		var element = $("#up_personCardDown").siblings("p.clr53");
            		element.empty();
            		element.append("<i class='fa fa-exclamation-triangle'>请选择身份证背面图片</i>");
            		return false;
            	} else {
            		var element = $("#up_personCardDown").siblings("p.clr53");
            		element.empty();
    			}
            	var telephone = $("#telephone").val();
            	if (!isMobile(telephone) && !isPhone(telephone)) {
    				var element = $("#telephone").siblings("p.clr53");
            		element.empty();
            		element.append("<i class='fa fa-exclamation-triangle'>请输入正确的公司电话</i>");
    				return false;
    			} else {
    				var element = $("#telephone").siblings("p.clr53");
            		element.empty();
    			}
            	var fax = $("#fax").val();
            	if (fax != null && fax != "" && !isPhone(fax)) {
    				var element = $("#fax").siblings("p.clr53");
            		element.empty();
            		element.append("<i class='fa fa-exclamation-triangle'>请输入正确的传真</i>");
    				return false;
    			} else {
    				var element = $("#fax").siblings("p.clr53");
            		element.empty();
    			}
            	var personPhone = $("#personPhone").val();
            	if (!isMobile(personPhone) && !isPhone(personPhone)) {
    				var element = $("#personPhone").siblings("p.clr53");
            		element.empty();
            		element.append("<i class='fa fa-exclamation-triangle'>请输入正确的联系人电话</i>");
    				return false;
    			} else {
    				var element = $("#personPhone").siblings("p.clr53");
            		element.empty();
    			}
                form.submit();   //提交表单
            },
            errorPlacement : function(error, element) {
    			var obj = element.siblings("p.clr53");
    			obj.empty();
    			obj.append("<i class='fa fa-exclamation-triangle'>" + error.text() + "</i>");
			},
            
            rules:{
            	company:{
                    required:true,
                    rangelength:[0,50]
                },
                companyAdd:{
                    required:true,
                    rangelength:[0,50]
                },
                telephone:{
                    required:true,
                    rangelength:[0,20]
                },
                fax:{
                    rangelength:[0,20]
                },
                legalPerson:{
                    required:true,
                    rangelength:[0,50]
                },
                personPhone:{
                    required:true,
                    rangelength:[0,20]
                },
                email:{
                    required:true,
                    rangelength:[0,50]
                },
                // 营业执照信息（副本）
                bussinessLicense:{
                    required:true,
                    rangelength:[0,50]
                },
                organization:{
                    required:true,
                    rangelength:[0,50]
                },
                companyStartTime:{
                    required:true
                },
                companyEndTime:{
                    required:true
                },
                /* up_bussinessLicenseImage:{
                    required:true
                }, */
                // 一般纳税人证明
                legalPersonCard:{
                    required:true,
                    rangelength:[0,50]
                },
                /* up_personCardUp:{
                    required:true
                },
                up_personCardDown:{
                    required:true
                }, */
                // 开户银行信息(此账号为结算账号)
                bankUser:{
                    required:true,
                    rangelength:[0,50]
                },
                bankName:{
                    required:true,
                    rangelength:[0,50]
                },
                bankNameBranch:{
                    required:true,
                    rangelength:[0,50]
                },
                brandNameCode:{
                    required:true,
                    rangelength:[0,50]
                },
                bankCode:{
                    required:true,
                    rangelength:[0,50]
                },
                // 其他信息
                taxLicense:{
                    required:true,
                    rangelength:[0,50]
                },
                userName:{
                    required:true,
                    rangelength:[0,50]
                },
                sellerName:{
                    required:true,
                    rangelength:[0,50]
                }
            },
            messages:{
            	company:{
                    required:"&nbsp;&nbsp;公司名称不能为空",
                    rangelength: $.format("&nbsp;&nbsp;公司名称在{0}到{1}位之间。")
                },
                companyAdd:{
                	required:"&nbsp;&nbsp;公司详细地址不能为空",
                    rangelength: $.format("&nbsp;&nbsp;公司详细地址在{0}到{1}位之间。")
                },
                telephone:{
                	required:"&nbsp;&nbsp;公司电话不能为空",
                    rangelength: $.format("&nbsp;&nbsp;公司电话在{0}到{1}位之间。")
                },
                fax:{
                    rangelength: $.format("&nbsp;&nbsp;传真在{0}到{1}位之间。")
                },
                legalPerson:{
                	required:"&nbsp;&nbsp;法定代表人不能为空",
                    rangelength: $.format("&nbsp;&nbsp;法定代表人在{0}到{1}位之间。")
                },
                personPhone:{
                	required:"&nbsp;&nbsp;联系人电话不能为空",
                    rangelength: $.format("&nbsp;&nbsp;联系人电话在{0}到{1}位之间。")
                },
                email:{
                	required:"&nbsp;&nbsp;邮箱不能为空",
                    rangelength: $.format("&nbsp;&nbsp;邮箱在{0}到{1}位之间。")
                },
             	// 营业执照信息（副本）
                bussinessLicense:{
                	required:"&nbsp;&nbsp;营业执照号不能为空",
                    rangelength: $.format("&nbsp;&nbsp;营业执照号在{0}到{1}位之间。")
                },
                organization:{
                	required:"&nbsp;&nbsp;组织机构代码不能为空",
                    rangelength: $.format("&nbsp;&nbsp;组织机构代码在{0}到{1}位之间。")
                },
                companyStartTime:{
                	required:"&nbsp;&nbsp;营业开始日期不能为空"
                },
                companyEndTime:{
                	required:"&nbsp;&nbsp;营业结束日期不能为空"
                },
                /* up_bussinessLicenseImage:{
                	required:"&nbsp;&nbsp;营业执照扫描件不能为空"
                }, */
                // 一般纳税人证明
                legalPersonCard:{
                	required:"&nbsp;&nbsp;法定代表人身份证不能为空",
                    rangelength: $.format("&nbsp;&nbsp;法定代表人身份证在{0}到{1}位之间。")
                },
                /* up_personCardUp:{
                	required:"&nbsp;&nbsp;身份证正面图片不能为空"
                },
                up_personCardDown:{
                	required:"&nbsp;&nbsp;身份证背面图片不能为空"
                }, */
                // 开户银行信息(此账号为结算账号)
                bankUser:{
                	required:"&nbsp;&nbsp;开户行账号名称不能为空",
                    rangelength: $.format("&nbsp;&nbsp;开户行账号名称在{0}到{1}位之间。")
                },
                bankName:{
                	required:"&nbsp;&nbsp;开户行不能为空",
                    rangelength: $.format("&nbsp;&nbsp;开户行在{0}到{1}位之间。")
                },
                bankNameBranch:{
                	required:"&nbsp;&nbsp;开户行支行名称不能为空",
                    rangelength: $.format("&nbsp;&nbsp;开户行支行名称在{0}到{1}位之间。")
                },
                brandNameCode:{
                	required:"&nbsp;&nbsp;开户行支行联行号不能为空",
                    rangelength: $.format("&nbsp;&nbsp;开户行支行联行号在{0}到{1}位之间。")
                },
                bankCode:{
                	required:"&nbsp;&nbsp;银行账号不能为空",
                    rangelength: $.format("&nbsp;&nbsp;银行账号在{0}到{1}位之间。")
                },
                // 其他信息
                taxLicense:{
                	required:"&nbsp;&nbsp;税务登记证号不能为空",
                    rangelength: $.format("&nbsp;&nbsp;税务登记证号在{0}到{1}位之间。")
                },
                userName:{
                	required:"&nbsp;&nbsp;商家账号不能为空",
                    rangelength: $.format("&nbsp;&nbsp;商家账号在{0}到{1}位之间。")
                },
                sellerName:{
                	required:"&nbsp;&nbsp;店铺名称不能为空",
                    rangelength: $.format("&nbsp;&nbsp;店铺名称在{0}到{1}位之间。")
                }
            }

        });
		
		$("#companyProvince").change(function(){
	        getRegion($("#companyCity"), $(this).val(), "");
	    });
		
		$("#bankProvince").change(function(){
	        getRegion($("#bankCity"), $(this).val(), "");
	    });
	});
	
	function getRegion(_select, _parentId, _selectId) {
	    _select.empty();
	    $.ajax({
	        type:"get",
	        url: domain+"/getRegionByParentId",
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

</body>
</html>