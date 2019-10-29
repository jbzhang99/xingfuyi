<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/userindex.css"/>
	
		<div class='container'>
			<div class='breadcrumb'>
				<strong class='business-strong'>
					<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
				</strong>
				<span>
					&nbsp;>&nbsp;
					<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技
				</span>
				<span>
					&nbsp;>&nbsp;
					<a href='javascript:void(0)'>我的个人资料</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder wrapper_main-wd'>
				<div class="user_info_top">
			<div class="user_info_l fl">
				<div class="user_avatar fl" >
					<img src="${(domainUrlUtil.staticResources)!}/img/vip_face_default.jpg" class="head_img" width="110" height="110">
				</div >
				<div class="user_avatar_r fl">
					<b>${commUtil.hideMiddleStr(member.name,2,2)}</b>
					<br>
					<#if member.grade == 1>
						<img src="${(domainUrlUtil.staticResources)!}/img/level1.jpg" alt="注册会员" title="注册会员" width="30" height="30">
					<#elseif member.grade == 2>
						<img src="${(domainUrlUtil.staticResources)!}/img/level2.jpg" alt="铜牌会员" title="铜牌会员" width="30" height="30">
					<#elseif member.grade == 3>
						<img src="${(domainUrlUtil.staticResources)!}/img/level3.jpg" alt="银牌会员" title="银牌会员" width="30" height="30">
					<#elseif member.grade == 4>
						<img src="${(domainUrlUtil.staticResources)!}/img/level4.jpg" alt="金牌会员" title="金牌会员" width="30" height="30">
					<#elseif member.grade == 5>
						<img src="${(domainUrlUtil.staticResources)!}/img/level5.jpg" alt="钻石会员" title="钻石会员" width="30" height="30">
					</#if>
					
					<div class="h20"><span>经验值：</span><span class="red">${(member.gradeValue)!}</span></div>
					<#if gradeValue!=0>
						<div class="h20"><span>距离下次升级还差经验值：</span><span class="red">${(gradeValue)!}</span></div>
						
					</#if>
				</div>
			</div>
			<div class="info-rcol">
				<ul class="acco-info fl">
					<li class="acco-item">
						<div>
							<em>余额：</em>
							<a href="${(domainUrlUtil.urlResources)!}/member/balance.html">${(member.balance)!}</a>
						</div>
						<div>
							<em>积分：</em>
							<a class="bold" href="">${(member.integral)!}</a>
						</div>
						<div>
							<em>优惠券：</em>
							<a class="bold" href="${(domainUrlUtil.urlResources)!}/member/coupon-use.html">${(couponNum)!0}</a>
							<a class="pa10 a-col" target="_blank" href="${(domainUrlUtil.urlResources)!}/coupon.html">领券</a>
						</div>
					</li>
				</ul>
				<div class="verification-box fl">
					<#if member.isSmsVerify == 0>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/tel.jpg" alt="" width="50" height="72">
							<a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="red">去验证</a>
							<p>
								<#if member.mobile??>
									${commUtil.hideMiddleStr(member.mobile,3,4)}
								</#if>
							</p>
						</div>
					<#else>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/telyes.jpg" alt="" width="50" height="72">
							<a class="colgreen">已验证</a>
							<p>
								<#if member.mobile??>
									${commUtil.hideMiddleStr(member.mobile,3,4)}
								</#if>
							</p>
						</div>
					</#if>
					<#if member.isEmailVerify == 0>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/yx.jpg" alt="" width="68" height="70">
							<a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="red">去验证</a>
							<p>
								<#if member.email??>
									${commUtil.hideMiddleStr(member.email,3,8)}
								</#if>
							</p>
						</div>
					<#else>
						<div class="telphone-vf fl">
							<img src="${(domainUrlUtil.staticResources)!}/img/yxyes.jpg" alt="" width="68" height="70">
							<a class="colgreen">已验证</a>
							<p>
								<#if member.email??>
									${commUtil.hideMiddleStr(member.email,3,8)}
								</#if>
							</p>
						</div>
					</#if>
				</div>
			</div>
		</div>

				<!-- S 个人资料 -->
				<div class="main_fram">
					<div class="content_tit">
						<ul class="tabsTitle fl">
							<li class="current"><a href="javascript:;">个人信息</a></li>
							<li><a href="javascript:;">登录密码</a></li>
						</ul>
					</div>
					<div class="user-box">
						<form class='myinfo' id='personalfileForm'>
					<dl class='dl_col1'>
						<dt>
							<label>用户名：</label>
						</dt>
						<dd>${(member.name)!''} </dd>
						<dt>			
							<label>生日：</label>
						</dt>
						<dd class='p-item'>
							 <input id="birthday" name="birthday"  maxlength="10" class="input-medium Wdate"   
              							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});" value="${(member.birthday?string("yyyy-MM-dd"))!''}"/>
						</dd>
						<dt>
							<label>性别：</label>
						</dt>
						<dd class='p-item'>
							<@cont.radio id="q_gender" name="gender" value="${(member.gender)!''}" codeDiv="GENDER" />
						</dd>
						<dt>
							<label>电话：</label>
						</dt>
						<dd class='p-item'>
							<input type='text' value="${(member.phone)!''}" name='phone' maxlength="15" class='txt txt-err'>
							<em class='em-errMes'></em>
						</dd>
						<!-- mobile -->
						<dt class='p-item'>
							<label for='verifyPhone'>手机 
									<#if member??>
										<#if (member.isSmsVerify)=1>（已绑定）：
										<#elseif (member.isSmsVerify)=0>（未绑定）：
										</#if>
									<#else>未绑定：
									</#if> 
							</label>
						</dt>
						<dd class='p-item'>
							<input type='text' name="mobile" value="${(member.mobile)!''}" class='txt txt-err' maxlength="11" id='curMobile'>
							<#if member??>
								<#if (member.isSmsVerify)=1>
									<input type='button' class='veributton btn btn-default bt_red' id="unSmsVerif" value='解除绑定'>
								<#elseif (member.isSmsVerify)=0>
									<input type='button' class='veributton btn btn-default bt_red' id="sendSmsVerif" value='获取验证码'>
								</#if>
							</#if> 
							<em class='em-errMes' id="mobile-error"></em>
						</dd>
						<!-- end -->
						<#if member??>
							<#if (member.isSmsVerify)=0>
								<dt>
									<label>请输入验证码：</label>
								</dt>
								<dd class='p-item'>
									<input type='text' name='smsVerifyCode' maxlength="6" id='smsVerifyCode' class='txt txt-err verifyCode'>
									<input type='button' class='btn btn-primary bt_blue' id='bindMobileButton' value='绑定手机'>
									<em class='em-errMes'></em>
								</dd>
							</#if>
						</#if> 
						<dt>
							<label>邮箱
									<#if member??>
										<#if (member.isEmailVerify)=1>（已验证）：
										<#elseif (member.isEmailVerify)=0>（未验证）：
										</#if>
									<#else>未验证：
									</#if> 
									
							</label>
						</dt>
						<dd>
							<p class='p-item'>
									
								<input id='curEmail1' type='text' maxlength="60" name="email" class='txt txt-err' value='${(member.email)!''}' >
								<#if member??>
									<#if (member.isEmailVerify)=0>
										<input id='sendEmailVerify'  class='veributton btn btn-default bt_red' type='button' value='发送验证邮件'>
									<#elseif (member.isEmailVerify)=1>
										<input id='unEmailVerify'  class='veributton btn btn-default bt_red' type='button' value='解除绑定'>
									</#if>
								</#if> 
								
								<em class='em-errMes'></em>                                      
							</p>
						</dd>
						<dt>
							<label>QQ：</label>
						</dt>
						<dd class='p-item'>
							<input type='text' value='${(member.qq)!''}' name='qq' class='txt' maxlength="15">
						</dd>
						<dt></dt>
						<dd class='p-item'>
							<input type='button' value='确认提交' class='bt_submit'>
						</dd>
					</dl>
				</form>
					</div>
					<div class="user-box" style="display: none;" >
						<form class='myinfo' id='editPasswordform'>
							<dl class='dl_col1'>
								<dt>
									<label>当前密码：</label>
								</dt>
								<dd class="p-item">
									<input type='password' class='txt' id='txt-oldpw' name='oldPwd' placeholder="请输入当前的登录密码">
									<em class='em-errMes' style="color: red;"></em>
								</dd>
								<dt>
									<label>新密码：</label>
								</dt>
								<dd class="p-item">
									<input type='password' class='txt' id='txt-newpw' name='newPwd' placeholder="请输入新的密码">
									<em class='em-errMes' style="color: red;"></em>
								</dd>
								<dt>
									<label>确认密码：</label>
								</dt>
								<dd class="p-item">
									<input type='password' class='txt' id='txt-repw' name='confirmPwd' placeholder="请再次输入新的密码">
									<em class='em-errMes' style="color: red;"></em>
								</dd>
								<dd class='p-item'>
									<input type='button'  class='btn btn-danger bt_submit_pwd' id='buttonSubmit' name='button' value='确认提交'>
								</dd>
							</dl>
						</form>
					</div>
				</div>
				<!-- E 个人资料 -->
			</div>
		</div>

			<!-- end -->
		</div>
	<script type="text/javascript">
		$(function(){
			// 个人信息/登录密码切换
			$(".tabsTitle li").on("click",function(){
				$(this).addClass("current").siblings().removeClass("current");
				var liindex = $(this).index();
				$(".user-box").eq(liindex).show().siblings(".user-box").hide();
			})
			
			//校验
			jQuery("#editPasswordform").validate({
				errorPlacement : function(error, element) {
					var obj = element.siblings(".em-errMes").css('display', 'block');
					error.appendTo(obj);
				},
		        rules : {
		            "oldPwd":{required:true},
		            "newPwd":{required:true,passwordLength:true},
		            "confirmPwd":{required:true,equalTo:"#txt-newpw"}
		        },
		        messages:{
		        	"oldPwd":{required:"请输入原密码"},
		        	"newPwd":{required:"请输入新密码"},
		            "confirmPwd":{required:"请输入确认密码",equalTo:"密码输入不一致"}
		        }
		    }); 
		
		
			$(".bt_submit_pwd").click(function(){
				if($("#editPasswordform").valid()){
					$(".bt_submit").attr("disabled","disabled");
					var params = $('#editPasswordform').serialize();
					$.ajax({
						type:"POST",
						url:domain+"/member/updatepassword.html",
						dataType:"json",
						async : false,
						data : params,
						success:function(data){
							if(data.data){
								jAlert('密码修改成功', '提示',function(){
									window.location.href=domain+"/member/info.html"
								});
								
							}else{
								jAlert(data.message);
								$(".bt_submit").removeAttr("disabled");
							}
						},
						error:function(){
							jAlert("异常，请重试！");
							$(".bt_submit").removeAttr("disabled");
						}
					});
				}
			});
			
				
			//控制左侧菜单选中
			$("#personalfile").addClass("currnet_page");
			
			//校验
			jQuery("#personalfileForm").validate({
				errorPlacement : function(error, element) {
					var obj = element.siblings(".em-errMes").css('display', 'block');
					error.appendTo(obj);
				},
		        rules : {
		            "phone":{required:false},
		            "mobile":{required:false,isMobile:true},
		            "smsVerifyCode":{required:false},
		            "email":{required:false,isEmail:true}
		        },
		        messages:{
		        	"phone":{required:"请输入电话"},
		            "mobile":{required:"请输入手机号"},
		            "smsVerifyCode":{required:"请输入验证码"},
		            "email":{required:"请输入邮箱"}
		        }
		    }); 
		
		
			$(".bt_submit").click(function(){
				if($("#personalfileForm").valid()){
					$(".bt_submit").attr("disabled","disabled");
					var params = $('#personalfileForm').serialize();
					$.ajax({
						type:"POST",
						url:domain+"/member/saveinfo.html",
						dataType:"json",
						async : false,
						data : params,
						success:function(data){
							if(data.success){
								//jAlert("保存成功");
								//重新加载数据
								//window.location.href=domain+"/member/info.html";
								jAlert('保存成功', '提示',function(){
									window.location.href=domain+"/member/info.html"
								});
							}else{
								jAlert(data.message);
								$(".bt_submit").removeAttr("disabled");
							}
						},
						error:function(){
							jAlert("异常，请重试！");
							$(".bt_submit").removeAttr("disabled");
						}
					});
				}
				
			});
			
			$("#sendSmsVerif").click(function(){
				var curMobile = $("#curMobile").val();
				if (curMobile == null || curMobile == "") {
					// $("#mobile-error").html("请输入手机号码！");
					// $("#mobile-error").css('display', 'block');
					jAlert("请输入手机号码！");
					return;
				}
				$("#sendSmsVerif").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/sendsmsverif.html",
					dataType:"json",
					async : false,
					data : {mobile:curMobile},
					success:function(data){
						if(data.success){
							jAlert("验证码发送成功，请查收短信！");
						}else{
							jAlert(data.message);
							$("#sendSmsVerif").removeAttr("disabled");
						}
					},
					error:function(){
						$("#sendSmsVerif").removeAttr("disabled");
					}
				});
			});
			
			$("#bindMobileButton").click(function(){
				var smsVerifyCode = $("#smsVerifyCode").val();
				if (smsVerifyCode == null || smsVerifyCode == "") {
					jAlert("请输入验证码！");
					return;
				}
				$("#bindMobileButton").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/smsverif.html",
					dataType:"json",
					async : false,
					data : {verif:smsVerifyCode},
					success:function(data){
						if(data.success){
							//jAlert("绑定成功！");
							//重新加载数据
							//window.location.href=domain+"/member/info.html";
							
							jAlert('绑定成功！', '提示',function(){
								window.location.href=domain+"/member/info.html"
							});
						}else{
							jAlert(data.message);
							$("#bindMobileButton").removeAttr("disabled");
						}
					},
					error:function(){
						$("#bindMobileButton").removeAttr("disabled");
					}
				});
			});
			
			$("#unSmsVerif").click(function(){
				if(confirm("确定要解除绑定吗？")){
					$("#unSmsVerif").attr("disabled","disabled");
					$.ajax({
						type:"POST",
						url:domain+"/member/unsmsverif.html",
						dataType:"json",
						async : false,
						data : {},
						success:function(data){
							if(data.success){
								jAlert('解除绑定成功！', '提示',function(){
									window.location.href=domain+"/member/info.html"
								});
							}else{
								jAlert(data.message);
								$("#unSmsVerif").removeAttr("disabled");
							}
						},
						error:function(){
							$("#unSmsVerif").removeAttr("disabled");
						}
					});
				}
			});
			
			$("#sendEmailVerify").click(function(){
				var curEmail1 = $("#curEmail1").val();
				if (curEmail1 == null || curEmail1 == "") {
					jAlert("请输入邮箱地址！");
					return;
				}
				$("#sendEmailVerify").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/sendemailverif.html",
					dataType:"json",
					async : false,
					data : {email:curEmail1},
					success:function(data){
						if(data.success){
							jAlert("验证码发送成功，请查收邮件！");
						}else{
							jAlert(data.message);
							$("#sendEmailVerify").removeAttr("disabled");
						}
					},
					error:function(){
						$("#sendEmailVerify").removeAttr("disabled");
					}
				});
			});
			
			$("#unEmailVerify").click(function(){
				if(confirm("确定要解除绑定吗？")){
					$("#unEmailVerify").attr("disabled","disabled");
					$.ajax({
						type:"POST",
						url:domain+"/member/unemailverif.html",
						dataType:"json",
						async : false,
						data : {},
						success:function(data){
							if(data.success){
								//jAlert("解除绑定成功！");
								//重新加载数据
								//window.location.href=domain+"/member/info.html";
								
								jAlert('解除绑定成功！', '提示',function(){
									window.location.href=domain+"/member/info.html"
								});
							}else{
								jAlert(data.message);
								$("#unEmailVerify").removeAttr("disabled");
							}
						},
						error:function(){
							$("#unEmailVerify").removeAttr("disabled");
						}
					});
				}
			});
			
			
		});
	</script>
	
<#include "/front/commons/_endbig.ftl" />
