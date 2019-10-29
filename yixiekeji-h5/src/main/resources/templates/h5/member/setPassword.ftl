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
   	  	 <div class="flex-2 text-center">设置密码</div>
         <div class="flex-1 text-right"></div>
   	  </div>

   </header>
   <!-- 头部 end-->
    <!--modify by fanlei 2019-10-10 登录注册重构-->
	<div class="padl_r15">
          <div style="height: 2rem;"></div>
          <div class="setPasswordInput">
              <span>设置密码</span>
              <input type="password" id="passwordOne" maxlength="20" placeholder="请输入密码"/>
          </div>
          <div class="setPasswordInput">
              <span>再次确认密码</span>
              <input type="password" id="passwordAgain" maxlength="20"  placeholder="请再次确认密码"/>
          </div>
          <div class="tipsPassword">
              必须是6-20个英文字母、数字或符号(除空格)，且字母、数字和标点符号至少包含两种
          </div>
          <!--完成按钮-->
          <div class="loginBtn" id="nextBtnComplete">
              <div>完成</div>
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
        $("#passwordOne,#passwordAgain").bind('input propertychange',function(){
            if(($('#passwordOne').val().trim()!='' && $('#passwordAgain').val().trim()!='' )){
                $("#nextBtnComplete").removeClass('loginBtn');
                $("#nextBtnComplete").addClass('loginBtnClick');
            }else{
                $("#nextBtnComplete").removeClass('loginBtnClick');
                $("#nextBtnComplete").addClass('loginBtn');
            }
        })
        $("#nextBtnComplete").click(function () {
            if($("#passwordOne").val().trim()=="" || $("#passwordOne").val().trim()==null ){
                layer.open({content: '密码不得为空',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordOne").val().length < 6){
                layer.open({content: '密码长度不得小于6位',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordAgain").val().trim()=="" || $("#passwordAgain").val().trim()==null ){
                layer.open({content: '再次确认密码不得为空',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordAgain").val().length < 6){
                layer.open({content: '再次确认密码长度不得小于6位',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordOne").val() !=  $("#passwordAgain").val()){
                layer.open({content: '两次密码不一样',skin: 'msg',time: 2 });
                return false;
            }
            var passwordOne = $("#passwordOne").val();
            var passwordAgain = $("#passwordAgain").val();
            if (passwordOne.indexOf(" ") > 0 || passwordAgain.indexOf(" ") >0) {
                layer.open({content: '密码中不得包含空格',skin: 'msg',time: 2 });
                return false;
            }
            $("#nextBtnComplete").attr("disabled","disabled");
            $.ajax({
                type : 'post',
                url : domain + "/doregister",
                async:false,
                data:{
                    saleCode:getData("saleCode"),
                    mobile:getData("phoneNumber"),
                    smsVerifyCode:getData("smsVerifyCode"),
                    password:passwordAgain
                },
                success : function(e) {
                    if (e.success) {
                        window.location=domain+"/member/index.html";
                    } else{
                        layer.open({content:e.message ,skin: 'msg',time: 2 });
                        $("#nextBtnComplete").removeAttrs("disabled");
                    }
                }
            });

        })
    })
        //add by fanlei 2019-10-10 登录注册重构  end

</script>



</body>
</html>
