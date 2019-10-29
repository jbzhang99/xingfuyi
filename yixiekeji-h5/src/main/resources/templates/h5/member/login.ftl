<#include "/h5/commons/_head.ftl" />
<body style="background:#ffffff;">
   <!-- 头部 -->
   <header>
   		<!--	modify by fanlei 2019-10-09 登录注册页面重构  start-->
   	  <!--<div class="flex flex-align-center login-header">
   	  	 <div class="padlr10 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">登录</div>
   	  </div>-->
   	  <div class="flex flex-align-center login-header">
   	  	 <div class="padlr10 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
                 <img src="${(domainUrlUtil.staticResources)!}/img/nav_guanbi@3x.png" style="width: 19px;height: 19px;">
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center"></div>
         <div class="flex-1 text-right"></div>
   	  </div>
			<!--	modify by fanlei 2019-10-09 登录注册页面重构  end-->
   </header>
   <!-- 头部 end-->
<!--	modify by fanlei 2019-10-09 登录注册页面重构  start-->
	<div class="s-container">
		<div class="padl_r15">
			<div style="height: 3rem;"></div>
			<div style="font-family: PingFangSC-Regular;font-size: 2.7rem;color: #2E2D2D;letter-spacing: 0.32px;">
				欢迎来到幸福易
			</div>
            <input type="hidden" id="toUrl" name="toUrl" value="${(toUrl)!''}"/>
			<!--手机号码输入框-->
			<div class="phoneInput">
				<input type="input" id="phoneInput"  placeholder="请输入手机号" maxlength="11"/>
			</div>
			<!--密码输入框-->
			<div class="passwordInput">
				<input type="password" id="passwordInput" maxlength="20" placeholder="请输入密码"/>
				<span id="forgetPassword"><a href="${(domainUrlUtil.urlResources)!}/registerForgetPassword.html" style="color: #666666;">忘记密码</a></span>
			</div>
			<!--登录按钮-->
			<div class="loginBtn" id="loginBtn">
				<div>登录</div>
			</div>
			<!--新用户注册-->
			<div style="height: 2rem;"></div>
			<div class="newRegistration" id=""><a href="${(domainUrlUtil.urlResources)!}/invitation.html"  style="color: #666666;">新用户注册</a></div>
   	</div>
	  <!--<div class="pad10">
	  	  <div class="tiperror"></div>
	  	  <form id="loginForm" onSubmit="return false;">
	  	  	<input type="hidden" id="toUrl" name="toUrl" value="${(toUrl)!''}"/>
	  	    <div class="form-group">
	  	      <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入手机号">
	  	    </div>
	  	    <div class="form-group">
	  	      <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
	  	    </div>
	  	    <div class="form-group" style="position:relative;">
	  	      <input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="请输入验证码" maxlength="6" style="width:50%">
	  	      <span class="captcha-img">-->
	  	      	<!-- <img src="" width="63" height="25"> -->
	  	      	<!--<img style="cursor:pointer;" src="${(domainUrlUtil.urlResources)!}/verify.html" id="code_img" onclick="refreshCode();" width="63" height="25" />
	  	      </span>

	  	    </div>
	  	    <button type="submit" class="btn btn-block btn-login" id="loginBtn">登录</button>
	  	  </form>
	  	  <div class="flex flex-pack-justify login-option">
	  	  	 <div><a href="${(domainUrlUtil.urlResources)!}/register.html">快速注册</a></div>
	  	  	 <div><a href="${(domainUrlUtil.urlResources)!}/forgetpassword.html">找回密码</a></div>
	  	  </div>
	  </div>
    </div>-->
  <!--	modify by fanlei 2019-10-09 登录注册页面重构  end-->
	<!-- 主体结束 -->
	<!--服务条款协议-->

	<div class="terms">
		<span class="termsSpanL">注册或登录即代表您已经同意</span>
		<span class="termsSpanR"><a href="${(domainUrlUtil.urlResources)!}/privacyProtocol.html" style="color: #007AFF;">《幸福易隐私政策》</a></span>
        <span class="termsSpanR"><a href="${(domainUrlUtil.urlResources)!}/privacyProtocolMember.html" style="color: #007AFF;">《幸福易平台服务协议(会员)》</a></span>
        <span class="termsSpanR"><a href="${(domainUrlUtil.urlResources)!}/privacyProtocolPromoters.html" style="color: #007AFF;">《幸福易平台协议（促销员）》</a></span>
	</div>


	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		//add by fanlei 2019-10-10 登录注册重构   start
		//手机号输入框失去焦点时，按钮颜色改变

        $("#phoneInput,#passwordInput").bind('input propertychange', function() {
            if(($('#phoneInput').val().trim()!='' && $('#passwordInput').val().trim()!='')){
                $("#loginBtn").removeClass('loginBtn');
                $("#loginBtn").addClass('loginBtnClick');
            }else{
                $("#loginBtn").removeClass('loginBtnClick');
                $("#loginBtn").addClass('loginBtn');
            }
        })


        $("#loginBtn").click(function(){
        var phoneInput = $("#phoneInput").val();
        if (phoneInput == null || phoneInput.trim() == "") {
                                  //提示内容                //2秒后自动关闭
            layer.open({content: '手机号不得为空',skin: 'msg',time: 2 });
            return false;
        }
        var password = $("#passwordInput").val();
        if (password == null || password.trim() == "") {
            layer.open({content: '密码不得为空',skin: 'msg',time: 2
            });
            return false;
            return false;
        }
        $("#loginBtn").attr("disabled","disabled");
        var toUrl = $('#toUrl').val();
        $.ajax({
            type:"POST",
            url:domain+"/dologin",
            dataType:"json",
            async : false,
            data : {
                mobile:phoneInput,
                password:password
            },
            success:function(data){
                if(data.success){
                    // 跳转到TODO
                    if (toUrl != null && toUrl != "") {
                        window.location=toUrl;
                    } else {
                        window.location=domain+"/member/index.html";
                    }
                }else{
                    layer.open({content: data.message,skin: 'msg',time: 2 });
                    $("#loginBtn").removeAttr("disabled");
                    return false;
                }
            },
            error:function(){
                layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                $("#loginBtn").removeAttr("disabled");
                return false;
            }
        });
    });





		//add by fanlei 2019-10-10 登录注册重构  end

//		$("#loginBtn").click(function(){
//			var userName = $("#userName").val();
//			if (userName == null || userName == "") {
//				$(".tiperror").html("请输入用户名");
//				return false;
//			}
//			var password = $("#password").val();
//			if (password == null || password == "") {
//				$(".tiperror").html("请输入密码");
//				return false;
//			}
//			var verifyCode = $("#verifyCode").val();
//			if (verifyCode == null || verifyCode == "") {
//				$(".tiperror").html("请输入验证码");
//				return false;
//			}
//			$("#loginBtn").attr("disabled","disabled");
//			var params = $('#loginForm').serialize();
//			var toUrl = $('#toUrl').val();
//			$.ajax({
//				type:"POST",
//				url:domain+"/dologin.html",
//				dataType:"json",
//				async : false,
//				data : params,
//				success:function(data){
//					if(data.success){
//						// 跳转到TODO
//						if (toUrl != null && toUrl != "") {
//							window.location=toUrl;
//						} else {
//							window.location=domain+"/member/index.html";
//						}
//					}else{
//						$(".tiperror").html(data.message);
//						//刷新验证码
//						refreshCode();
//						$("#loginBtn").removeAttr("disabled");
//						return false;
//					}
//				},
//				error:function(){
//					$(".tiperror").html("异常，请重试！");
//					$("#loginBtn").removeAttr("disabled");
//					return false;
//				}
//			});
//		});

	});


	//刷新验证码
	function refreshCode(){
		jQuery("#code_img").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?d"+new Date().getTime());
	}
</script>


</body>
</html>
