<#include "/h5/commons/_head.ftl" />
<body style="background:#ffffff;">
   <!-- 头部 -->
   <header>
   	  <div class="flex flex-align-center login-header">
   	  	 <div class="flex-1  text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">获取验证码</div>
         <div class="flex-1 text-right"></div>
   	  </div>

   </header>
   <!-- 头部 end-->
    <!--modify by fanlei 2019-10-10 登录注册重构-->
	<div class="padl_r15">
          <div style="height: 2rem;"></div>
			<!--验证码输入框-->
          <div class="verifyInput">
              <input type="input" id="verifyInput" oninput = "value=value.replace(/[^\d]/g,'')" maxlength="4" placeholder="请输入验证码"/>
              <span><a style="color: #E02020;"  id="getSMSVerify">获取验证码</a></span>
          </div>
          <!--登录按钮-->
          <div class="loginBtn" id="nextBtnToSetPassword">
              <div style="color: #ffffff;">下一步</div>
          </div>
   	</div>
   	<!--modify by fanlei 2019-10-10 登录注册重构-->

	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
<script type="text/javascript">
	var verifysms = false;
	$(function(){
        //add by fanlei 2019-10-10 登录注册重构   start
        //手机号输入框失去焦点时，按钮颜色改变
        $("#verifyInput").bind('input propertychange',function(){
            if(($('#verifyInput').val().trim()=='')){
                $("#nextBtnToSetPassword").removeClass('loginBtnClick');
                $("#nextBtnToSetPassword").addClass('loginBtn');
            }else{
                $("#nextBtnToSetPassword").removeClass('loginBtn');
                $("#nextBtnToSetPassword").addClass('loginBtnClick');
            }
        })
        //add by fanlei 2019-10-10 登录注册重构  end
		//获取手机验证码
		$("#getSMSVerify").click(function() {
			var sendverfiy = false;
			var obj = $("#getSMSVerify")
			$.ajax({
				type : 'get',
				url : domain + '/sendVerifySMS.html?mob=' + getData("phoneNumber")+ '&type=reg',
				async:false,
				success : function(e) {
					if (!e.success) {
                        layer.open({content: e.message,skin: 'msg',time: 2 });
						obj.text("获取短信验证码");
                        //   待更改
                        if(e.StatusCode == "11111"){
                            obj.attr("disabled", true);
                        }
					} else{
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

        $("#nextBtnToSetPassword").click(function () {
            if($("#verifyInput").val().trim()!=""){
                checkSmsCode($("#verifyInput").val())
            }else{
                layer.open({content: "短信验证码不得为空",skin: 'msg',time: 2 });
            }
        })


	});



	function checkSmsCode(code) {
        $.ajax({
            type:"get",
            url:domain+"/checkSmsCode",
            dataType:"json",
            async : false,
            data : {
                type:"reg",
                smsVerifyCode:code
            },
            success:function(data){
                if(data.success){
                    setData("smsVerifyCode",data.result);
                    window.location=domain+"/setPassword.html";
                }else{
                    layer.open({content:data.message ,skin: 'msg',time: 2 });
                }
            },
            error:function(){
                layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
            }
        });
    }

	//刷新验证码
	function refreshCode(){
		jQuery("#code_img").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?d"+new Date().getTime());
	}
</script>



</body>
</html>
