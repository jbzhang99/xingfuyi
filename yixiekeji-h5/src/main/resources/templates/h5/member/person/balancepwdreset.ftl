<#include "/h5/commons/_head.ftl" />
<style>
.user-infor2 .input-line .u-label {
	width: 90px;
}
</style>

<body class="bgf2">
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
				<a href="javascript:ejsPageBack();">
					<span class="fa fa-angle-left"></span>
				</a>
			</div>
			<div class="flex-2 text-center">重置支付密码</div>
			<div class="flex-1 text-right" id="fa-bars">
				<span class="fa fa-bars"></span>
			</div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->

	<div class="s-container">
		<div>

			<div class="user-infor user-infor2 bgff mar-bt">
				<form id="editPasswordform">
					<input type="hidden" id="phone" name="phone" value="${(member.mobile)!}"/>
					<div class="pad10 flex bor-b2 input-line">
						<div class="u-label text-right">已验证手机:</div>
						<div class="flex-2">${commUtil.sensitiveInfo(member.mobile,'tel')}</div>
					</div>

					<div class="form-group" style="position: relative;">
						<input type="text" class="form-control" id="verifyCode"
							name="verifycode" placeholder="请输入验证码" maxlength="6"> <span
							class="captcha-img" style="right: 10px; left: auto;"> <img
							style="cursor: pointer;"
							src="${(domainUrlUtil.urlResources)!}/verify.html"
							id="code_img" onclick="refreshCode();" width="63" height="25" />
						</span>
					</div>
					
					<div class="form-group" style="position: relative;">
						<input type="text" class="form-control" id="smsCode" name="smsCode"
							placeholder="请输入手机验证码" maxlength="11"> <span
							class="captcha-img" style="right: 10px; left: auto;">
							<div class="register-phone-verifycode">
								<button type="button" id="getSMSVerify">获取短信验证码</button>
							</div>
						</span>
					</div>

					<div class="form-group" style="position: relative;">
						<input type="password" class="form-control" id="newPwd"
							name="newPwd" placeholder="请设置6-20位支付密码">
					</div>
					
					<div class="form-group" style="position: relative;">
						<input type="password" class="form-control" id="repwd"
							name="repwd" placeholder="请再次输入密码">
					</div>
					
					<div class="form-group" style="position: relative;">
						<font id="errLabel" class="font12 clr53"></font>
					</div>
					
					<button type="button" class="btn btn-block btn-login"
						id="buttonSubmit">确认重置</button>

				</form>
			</div>

		</div>
	</div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" /> 
	<#include "/h5/commons/_statistic.ftl" />

	<script type="text/javascript">
	//刷新验证码
	function refreshCode(){
		jQuery("#code_img").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?d"+new Date().getTime());
	}
	$(function(){
		$("#buttonSubmit").click(function(){
			var txtnewpw = $("#newPwd").val();
			var txtrepw = $("#repwd").val();

			if (txtnewpw == null || txtnewpw == "") {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入密码");
				return false;
			}
			if (txtnewpw.length < 6 || txtnewpw.length > 20) {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入6-20位密码");
				return false;
			}
			if (txtrepw == null || txtrepw == "") {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入确认密码");
				return false;
			}
			if (txtrepw.length < 6 || txtrepw.length > 20) {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入6-20位确认密码");
				return false;
			}
			if (txtnewpw != txtrepw) {
				$("#errLabel").html("<i class='fa fa-warning'></i> 两次密码输入不一致");
				return false;
			}
			
			$("#buttonSubmit").attr("disabled","disabled");
			var params = $('#editPasswordform').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/member/balancepwdreset/updatepwd.html",
				dataType:"json",
				data : params,
				success:function(data){
					if(data.success){
						// alert("密码修改成功");
						//重新加载数据
						$.dialog('alert','提示','密码重置成功',2000,function(){ 
							window.location.href=domain+"/member/balance.html"; 
						});
					}else{
						$("#errLabel").html(data.message);
						$("#buttonSubmit").removeAttr("disabled");
					}
				}
			});
		});
		
		//获取验证码
		$("#getSMSVerify").click(function() {
			var sendverfiy = false;
			var obj = $(this);

			var verifyCode = $("#verifyCode").val();
			if (verifyCode == null || verifyCode == "") {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入验证码");
				return;
			}
			var phone = $("#phone").val();
			
			$.ajax({
				type : 'get',
				url : domain + '/member/balancepwdreset/sendVerifySMS.html?mob=' + phone
						+ '&verifycode=' + verifyCode,
				async:false,
				success : function(e) {
					if (!e.success) {
						$("#errLabel").html("<i class='fa fa-warning'></i> "+e.message);
						refreshCode();
						obj.text("获取短信验证码");
					} else{
						$("#errLabel").empty();
						sendverfiy = true;
						verifysms = true;
					}
				}
			});
	
			if(sendverfiy){
				var time = 120;
				obj.attr("disabled", true);
				obj.text(time + "秒后重新获取");
				time--;
		
				intervalId = setInterval(function() {
					obj.text(time + "秒后重新获取");
					time--;
					if (time == 0) {
						clearInterval(intervalId);
						obj.removeAttr("disabled");
						obj.text("获取短信验证码");
					}
				}, 1000);
				
			}

		});
		
	});
</script>

</body>
</html>