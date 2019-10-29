


<form id='consignee-form'>

<!-- 隐藏域  -->
<input type='hidden' name="addAll" id='addAll'> <!--省市区组合-->
<input type='hidden' name="id" id='newAddressId' value="${(address.id)!''}"><!-- 地址id-->
<input type='hidden'  id='provinceId' value="${(address.provinceId)!''}"><!-- 省id -->
<input type='hidden'  id='cityId' value="${(address.cityId)!''}"><!-- 市id -->
<input type='hidden'  id='areaId' value="${(address.areaId)!''}"><!-- 区id -->

			<div class='internation'>
				<div class='internation-title'>
					<span>新增收货人信息</span>
				</div>
				<div class='internation-content'>
					<div id='dialogIframe'>
						<div class='form' >
							<div class='item' id='name_div'>
								<span class='label'>
									<span style='color:red'>*</span>
									&nbsp;收货人：
								</span>
								<div class='fl news-address'>
									<input type='text' class='itxt' id='consignee_name' name='memberName' value="${(address.memberName)!''}" maxlength='20' tabindex='1'>
									<span class='error-msg' id='name_div_error'></span>
								</div>
							</div>
							<div class='item' id='area_div'>
								<span class='label'>
									<span style='color:red'>*</span>
									&nbsp;所在地区：
								</span>
								<div class='fl news-address' >
									<span id='td_area'>
									</span>
									<span class='error-msg' id='province_div_error'></span>
								</div>
								
							</div>
							<div class='item'>
								<span class='label' id='address_div'>
									<span style='color:red'>*</span>
									&nbsp;详细地址：
								</span>
								<div class='fl news-address'>
									<input type='text' class='itxt itxt02' value="${(address.addressInfo)!'' }" name="addressInfo" id='consignee_address' tabindex='6' maxlength='50' >
									<span class='error-msg' id='address_div_error'></span>
								</div>
							</div>
							<div class='item' id='call_div'>
								<span class='label' id='address_div'>
									<span style='color:red'>*</span>
									&nbsp;手机号码：
								</span>
								<div class='fl news-address'>
									<input type='text' id='consignee_mobile' name="mobile" value="${(address.mobile)!''}" class='itxt' tabindex='7' maxlength="20" onblur="">
									<span class='error-msg' id='call_div_error'></span>
								</div>
							</div>
							<div class='item' id='zipcode_div'>
								<span class='label'>邮编：</span>
								<div class='fl news-address'>
									<input type='text' id='consignee_zipcode' name="zipCode" value="${(address.zipCode)!''}" class='itxt' tabindex='8' maxlength="20">
									<span class='error-msg' id='zipcode_div_error'></span>
								</div>
							</div>
							<div class='item' id='email_div'>
								<span class='label'>邮箱：</span>
								<div class='fl news-address'>
									<input type='text' id='consignee_email' name="email" value="${(address.email)!''}" class='itxt' tabindex='8' maxlength="20">
									<span class='error-msg' id='email_div_error'></span>
								</div>
							</div>
							<div class='item'>
								<div class='save-btn'>
									<a href='javascript:void(0);' class='btn btn-default' id="saveConsigneeButton">
										<span id='saveConsigneeTitleDiv'>保存收货人信息</span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<a href='javascript:void(0);' class='internation-close harvest-close'></a>
			</div>
	</form>

    <script type="text/javascript">

			$(function(){
				
				//控制弹出框居中
				var v_top=($(window).height()-375)/2;
			    var v_left=($(window).width()-690)/2;
				$(".internation").css({"left":v_left+"px","top":v_top+"px"});
				
					//加载省市区
					var opt = {
							//省的样式（可选）`
							pClass : 'selt',
							//市的样式（可选）
							cClass : 'selt',
							//地区的样式（可选）
							aClass : 'selt',
							//默认省
							defaultProvince : $("#provinceId").val(),
							//默认市
							defaultCity : $("#cityId").val(),
							//默认地区
							defaultArea : $("#areaId").val(),
							areaRequired : true,
							domain : domain,
							provinceName : 'provinceId',
							cityName : 'cityId',
							areaName:'areaId'
						};
						//实例此对象，参数可选
						var area = new AreaSupport(opt);
						//初始化对象并组装DOM添加至给定的选择器对象。注意，此对象的init返回的是JQuery对象
						area.getProvince().appendTo($('#td_area'));
						//如果希望进入页面加载到市，则可以手动执行此初始化方法
						area.getCity().appendTo($('#td_area'));
						//如果希望进入页面加载到地区，则可以手动执行此初始化方法
						area.getArea().appendTo($('#td_area'));

						
						//关闭收货信息层
						var bt = null;
						$(".harvest-close").click(function(){
							bt = $(this);
							$("#Harvest").removeClass("lay-display");
						});
						// 设置弹出曾的高
						$(".background-layer").css("height",$(window).height());
						
						//关闭收货信息层
						function closeHarvest(){
							bt = $(".harvest-close");
							$("#Harvest").removeClass("lay-display");
						}


					
					jQuery("#consignee-form").validate({
						errorPlacement : function(error, element) {
							var obj = element.siblings(".error-msg").css('display', 'block');
							error.appendTo(obj);
						},
				        rules : {
				            "memberName":{required:true},
				            "addressInfo":{required:true},
				            "email":{isEmail:true},
				            "mobile":{required:true,isMobile:true}
				        },
				        messages:{
				        	"memberName":{required:"请输入收货人姓名"},
				            "addressInfo":{required:"请输入详细地址"},
				            "mobile":{required:"请输入手机号"}
				        }
				    }); 
					
					/**
					 * 提交信息
					 */
					$("#saveConsigneeButton").click(function(){
						var province = $('#province_').val();
						var city = $('#city_').val();
						var area = $('#area_').val();
						
						
						//获得省市区中文并拼接
						var addall= $("#province_  option:selected").text()+$("#city_  option:selected").text()+$("#area_  option:selected").text();
						$("#addAll").val(addall);
						
						
						if ($("#consignee-form").valid()) {
							//校验地址
							if(province==''||city=='-1'||area=='-1'){
								$("#province_div_error").css('display', 'block');
								$("#province_div_error").html("请输入完整地址");
								return false;
							}else{
								$("#province_div_error").css('display', 'none');
							}
							
							$("#saveConsigneeButton").attr("disabled","disabled");
							var params = $('#consignee-form').serialize();
							$.ajax({
								type:"POST",
								url:domain+"/member/saveaddress.html",
								dataType:"json",
								data : params,
								success:function(data){
									if(data.success){
										// jAlert("保存成功");
										window.location.reload();
										//关闭弹出框
										//closeHarvest();
										//重新加载列表数据
											
									}else{
										jAlert(data.message);
										$("#saveConsigneeButton").removeAttr("disabled");
									}
								},
								error:function(){
									jAlert("异常，请重试！");
									$("#saveConsigneeButton").removeAttr("disabled");
								}
							});
						}
						
					});
				
			});	
			
	</script>

	