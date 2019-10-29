<#include "/h5/commons/_head.ftl" />
<body>
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
		 </div>
   	  	 <div class="flex-2 text-center">忘记密码</div>
   	  	 <!--<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>-->
   	  	 <div class="flex-1 text-right"></div>
   	  </div>
   	  <!--<#include "/h5/commons/_hidden_menu.ftl" />-->
   </header>
   <!-- 头部 end-->
	
	<div class="s-container">
	  <!--<div>

		  <div class="user-infor user-infor2 bgff mar-bt">
		    <form id="forgetform">
		  	 <div class="pad10 flex bor-b2 input-line">
		  	 	<div class="u-label">登录账号:</div>
                <div class="flex-2">
                	<input type="text" class="form-control" id="name" name="name" placeholder="请输入用户名">
                </div>
		  	 </div>
		  	 <div class="pad10 flex bor-b2 input-line">
		  	 	<div class="u-label">手机号码:</div>
                <div class="flex-2">
                	<input type="text" class="form-control" id="mobile" name="mobile" placeholder="请输入绑定的手机号码">
                </div>
		  	 </div>
		  	 <div class="pad10 flex bor-b2 input-line">
		  	 	<div class="u-label">验证码:</div>
                <div class="flex-2"  style="position:relative;">
                	<input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="请输入验证码" maxlength="6" style="width:50%">
			  	    <span class="captcha-img">
			  	    	<img style="cursor:pointer;" src="${(domainUrlUtil.urlResources)!}/verify.html" id="code_img" onclick="refreshCode();" width="63" height="25" />
			  	    </span>
                </div>
                
		  	 </div>
		  	 <br>
             <label id="errLabel" style="color:red"></label>
		  	 
		  	 <div class="text-center padt_b10">
		  	 	 <button class="btn btn-login" type="submit" style="padding:6px 12px;" id='forgetPasswordBtn'>重置密码</button>
		  	 </div>
		  	 
		  	</form>
		  </div>

	  </div>-->
	  <!--modify by fanlei 2019-10-10 登录注册重构-->
	  <div class="padl_r15">
			<div style="height: 2rem;"></div>
			<!--账户输入框-->
			<div class="accountInput">
				<span>账户</span>
				<input type="input" id="accountInput"  placeholder="请输入手机号"/>
			</div>
			<!--下一步按钮-->
			<div class="loginBtn" id="nextBtn">
				<div>下一步</div>
			</div>
   	</div>
   	<!--modify by fanlei 2019-10-10 登录注册重构-->
	  
	  
	  
	  
	  
  </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		$("#forgetPasswordBtn").click(function(){
			var name = $("#name").val();
			var mobile = $("#mobile").val();
			var verifyCode = $("#verifyCode").val();
			
			if (name == null || name == "") {
				$("#errLabel").html("请输入登录账号");
				return false;
			}
			if (mobile == null || mobile == "") {
				$("#errLabel").html("请输入绑定的手机号");
				return false;
			}
			if (verifyCode == null || verifyCode == "") {
				$("#errLabel").html("请输入验证码");
				return false;
			}

			$("#forgetPasswordBtn").attr("disabled","disabled");
			var params = $('#forgetform').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/doforgetpassword.html",
				dataType:"json",
				data : params,
				success:function(data){
					if(data.success){
						// alert("密码重置成功，请查收您的短信！");
						$.dialog('alert','提示','密码重置成功，请查收您的短信！',2000,function(){ window.location=domain+"/login.html"; });
					}else{
						$("#errLabel").html(data.message);
						refreshCode();//刷新验证码
						$("#forgetPasswordBtn").removeAttr("disabled");
						return false;
					}
				}
			});
		});
	
	
	});
	
	//刷新验证码
	function refreshCode(){
		jQuery("#code_img").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?d"+new Date().getTime());
	}
</script>

</body>
</html>