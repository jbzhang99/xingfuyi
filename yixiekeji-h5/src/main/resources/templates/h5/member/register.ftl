<#include "/h5/commons/_head.ftl" />
<body style="background:#ffffff;">
   <!-- 头部 -->
   <header>
   	  <div class="flex flex-align-center login-header">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">用户注册</div>
         <div class="flex-1 text-right"></div>
   	  </div>

   </header>
   <!-- 头部 end-->

	<!--<div class="s-container">
	  <div class="pad10">
	  	  <div class="tiperror"></div>
	  	  <form id="registerForm">
	  	    <div class="form-group flex">
	  	        <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入登录用户名" >
	  	    </div>
	  	    <div class="form-group" style="position:relative;">
	  	      <input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="请输入验证码" maxlength="6" >
	  	      <span class="captcha-img" style="right:10px; left:auto;">
	  	      	<img style="cursor:pointer;" src="${(domainUrlUtil.urlResources)!}/verify.html" id="code_img" onclick="refreshCode();" width="63" height="25" />
	  	      </span>
	  	    </div>
	  	    <div class="form-group" style="position:relative;">
	  	      <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号码" maxlength="11" >
	  	      <span class="captcha-img" style="right:10px; left:auto;">
	  	      	<div class="register-phone-verifycode"><button type="button" id="getSMSVerify">获取短信验证码</button></div>
	  	      </span>
	  	    </div>
	  	      <div class="form-group flex">
	  	        <input type="text" class="form-control" id="smsCode" name="smsCode" placeholder="请输入手机验证码" >
	  	    </div>

	  	    <div class="form-group" style="position:relative;">
	  	      <input type="password" class="form-control" id="password" name="password" placeholder="请设置6-20位登录密码"  >
	  	    </div>
	  	    <div class="form-group" style="position:relative;">
	  	      <input type="password" class="form-control" id="passwordCfm" name="passwordCfm" placeholder="请再次输入密码"  >
	  	    </div>

	  	    <div class="form-group" style="position:relative;">
	  	      <input type="text" class="form-control"  id="salecode" name='salecode' value="${salecode!''}" placeholder="请输入推广码"  >
	  	    </div>
	  	    <button type="button" class="btn btn-block btn-login" id="registerBtn">注册</button>
	  	  </form>

	  </div>
    </div>-->
    <!--modify by fanlei 2019-10-10 登录注册重构-->
	<div class="padl_r15">
			<div style="height: 2rem;"></div>
			<!--账户输入框-->
			<div class="accountInput">
				<span>账户</span>
				<input type="input" id="accountInputP" maxlength="11" placeholder="请输入手机号"/>
			</div>
			<!--下一步按钮-->
			<div class="loginBtn"  id="nextBtn">
				<div style="color: #ffffff;">下一步</div>
			</div>
   	</div>

   	<div class="maskbg" id="maskBg">
   		<div class="modelbg" id="modelBg">
   			<div class="model-head">
   				<div class="model-headL">安全验证</div>
   				<div class="model-headR">
                    <img src="${(domainUrlUtil.staticResources)!}/img/icon_shuaxin@3x.png" style="width: 18px;height: 17px;margin-top: 1.5rem;" onclick="refreshCode()">
   					<#--<span class="fa fa-refresh" id="refreshCode" onclick="refreshCode()"></span>-->
   				</div>
   			</div>
   			<div class="model-body">
   				<img src="" id="verificationCode"/>
   			</div>
            <div class="verificationInput">
                <input type="input" id="verificationInput" maxlength="4" placeholder="请输入验证码"/>
                <span style="color:#E02020;" id="confirm">确认</span>
            </div>
            <div class="errorMask" id="errorMasks">
                <img src="" id="errorMaskImg">
            </div>

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
    <#--<a href="${(domainUrlUtil.urlResources)!}" >-->

        //手机号输入框失去焦点时，按钮颜色改变
        $("#accountInputP").bind('input propertychange',function(){
            if(($("#accountInputP").val().trim()=='')){
                $("#nextBtn").removeClass('loginBtnClick');
                $("#nextBtn").addClass('loginBtn');
            }else{
                $("#nextBtn").removeClass('loginBtn');
                $("#nextBtn").addClass('loginBtnClick');
            }
        })

        // 点击下一步按钮，弹出验证码遮罩层
        $("#nextBtn").click(function(){
            var phone = $("#accountInputP").val();
            var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
            if (phone == null || phone.trim() == "") {
                layer.open({content:"手机号不得为空" ,skin: 'msg',time: 2 });
                return false;
            }
            if(!re.test(phone)){
                layer.open({content:"请输入正确手机号" ,skin: 'msg',time: 2 });
                return false;
            }


            if($('#accountInputP').val().trim()!=''){
                $("#nextBtn").attr("disabled","disabled");
                $.ajax({
                    type:"get",
                    url:domain+"/checkMobile",
                    dataType:"json",
                    async : false,
                    data : {
                        mobile:$('#accountInputP').val()
                    },
                    success:function(data){
                        if(data.success){
                            $("#maskBg").show();
                            $("#modelBg").show();
                            setData("phoneNumber",data.result);
                            refreshCode();
                        }else{
                            layer.open({content:data.message ,skin: 'msg',time: 2 });
                            $("#nextBtn").removeAttr("disabled");
                        }
                    },
                    error:function(){
                        layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                        $("#nextBtn").removeAttr("disabled");
                    }
                });
            }
		})

        // 验证图形验证码

        $("#confirm").click(function(){
            if($('#verificationInput').val().trim() == '' ||$('#verificationInput').val().trim() == null){
                //提示内容                //2秒后自动关闭
                layer.open({content: '图形验证码不得为空',skin: 'msg',time: 2 });
                return false;
            }
            $("#confirm").attr("disabled","disabled");
            $("#refreshCode").attr("disabled","disabled");

            $.ajax({
                type:"get",
                url:domain+"/checkVerifyCode",
                dataType:"json",
                async : false,
                data : {
                    verifyCode:$('#verificationInput').val()
                },
                success:function(data){
                    if(data.success){
                        $("#errorMasks").show();
                        $("#errorMaskImg").attr("src","${(domainUrlUtil.staticResources)!}/img/chenggong.png");
                        setTimeout(function () {
                            window.location=domain+"/registerVerification.html";
                        },1000)
                    }else{
                        $("#confirm").removeAttr("disabled");
                        $("#refreshCode").removeAttr("disabled");
                        $("#errorMasks").show();
                        $("#errorMaskImg").attr("src","${(domainUrlUtil.staticResources)!}/img/shibai.png");
//                        layer.open({content:data.message ,skin: 'msg',time: 2 });
                    }
                },
                error:function(){
                    $("#confirm").removeAttr("disabled");
                    $("#refreshCode").removeAttr("disabled");
                    layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                }
            });
        })



        //add by fanlei 2019-10-10 登录注册重构  end

		<#--$("#registerBtn").click(function(){-->
			<#--if(!verifysms){-->
				<#--$(".tiperror").html('请先获取手机验证码进行手机验证', '提示');-->
				<#--return;-->
			<#--}-->

			<#--var userName = $("#userName").val();-->
			<#--if (userName == null || userName == "") {-->
				<#--$(".tiperror").html("请输入用户名");-->
				<#--return;-->
			<#--}-->

			<#--var verifyCode = $("#verifyCode").val();-->
			<#--if (verifyCode == null || verifyCode == "") {-->
				<#--$(".tiperror").html("请输入验证码");-->
				<#--return;-->
			<#--}-->

			<#--var phone = $("#phone").val();-->
			<#--if (phone == null || phone == "") {-->
				<#--$(".tiperror").html("请输入手机号码");-->
				<#--return;-->
			<#--}-->

			<#--var smsCode = $("#smsCode").val();-->
			<#--if (smsCode == null || smsCode == "") {-->
				<#--$(".tiperror").html("请输入手机验证码");-->
				<#--return;-->
			<#--}-->

			<#--var password = $("#password").val();-->
			<#--if (password == null || password == "") {-->
				<#--$(".tiperror").html("请输入密码");-->
				<#--return;-->
			<#--}-->
			<#--if (password.length < 6 || password.length > 20) {-->
				<#--$(".tiperror").html("请输入6-20位密码");-->
				<#--return false;-->
			<#--}-->
			<#--var passwordCfm = $("#passwordCfm").val();-->
			<#--if (passwordCfm == null || passwordCfm == "") {-->
				<#--$(".tiperror").html("请输入确认密码");-->
				<#--return;-->
			<#--}-->
			<#--if (passwordCfm.length < 6 || passwordCfm.length > 20) {-->
				<#--$(".tiperror").html("请输入6-20位确认密码");-->
				<#--return false;-->
			<#--}-->
			<#--if (password != passwordCfm) {-->
				<#--$(".tiperror").html("确认密码不正确");-->
				<#--return;-->
			<#--}-->
			<#--$("#registerBtn").attr("disabled","disabled");-->
			<#--var params = $('#registerForm').serialize();-->
			<#--$.ajax({-->
				<#--type:"POST",-->
				<#--url:domain+"/doregister.html",-->
				<#--dataType:"json",-->
				<#--async : false,-->
				<#--data : params,-->
				<#--success:function(data){-->
					<#--if(data.success){-->
						<#--// 跳转到用户中心-->
						<#--window.location=domain+"/member/index.html";-->
					<#--}else{-->
						<#--$(".tiperror").html(data.message);-->
						<#--//刷新验证码-->
						<#--refreshCode();-->
						<#--$("#registerBtn").removeAttr("disabled");-->
					<#--}-->
				<#--},-->
				<#--error:function(){-->
					<#--$(".tiperror").html("异常，请重试！");-->
					<#--$("#registerBtn").removeAttr("disabled");-->
				<#--}-->
			<#--});-->
		<#--});-->


		<#--//获取验证码-->
		<#--$("#getSMSVerify").click(function() {-->
			<#--var sendverfiy = false;-->
			<#--var obj = $(this);-->
			<#--var mob = $("#phone").val();-->

			<#--var verifyCode = $("#verifyCode").val();-->
			<#--if (verifyCode == null || verifyCode == "") {-->
				<#--$(".tiperror").html("请输入验证码");-->
				<#--return;-->
			<#--}-->

			<#--var phone = $("#phone").val();-->
			<#--var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;-->
			<#--if (phone == null || phone == "") {-->
				<#--$(".tiperror").html("请输入手机号码");-->
				<#--return;-->
			<#--}-->
			<#--if(!re.test(phone)){-->
				<#--$(".tiperror").html("请输入正确的手机号码");-->
				<#--return;-->
			<#--}-->

			<#--$.ajax({-->
				<#--type : 'get',-->
				<#--url : domain + '/sendVerifySMS.html?mob=' + mob-->
						<#--+ '&verifycode=' + verifyCode + '&type=reg',-->
				<#--async:false,-->
				<#--success : function(e) {-->
					<#--if (!e.success) {-->
						<#--$(".tiperror").html(e.message);-->
						<#--refreshCode();-->
						<#--obj.text("获取短信验证码");-->
					<#--} else{-->
						<#--$(".tiperror").empty();-->
						<#--sendverfiy = true;-->
						<#--verifysms = true;-->
					<#--}-->
				<#--}-->
			<#--});-->

			<#--if(sendverfiy){-->
				<#--var time = 120;-->
				<#--obj.attr("disabled", true);-->
				<#--obj.text(time + "秒后重新获取");-->
				<#--time--;-->

				<#--intervalId = setInterval(function() {-->
					<#--obj.text(time + "秒后重新获取");-->
					<#--time--;-->
					<#--if (time == 0) {-->
						<#--clearInterval(intervalId);-->
						<#--obj.removeAttr("disabled");-->
						<#--obj.text("获取短信验证码");-->
					<#--}-->
				<#--}, 1000);-->

			<#--}-->

		<#--});-->
	});

	<#--//刷新验证码-->
	function refreshCode(){
       var code = $("#errorMasks").css("display");
       if(code == "block"){
           $("#errorMasks").css("display","none");
       }else {
           $.ajax({
               type:"get",
               url:domain+"/verify.html",
               dataType:"json",
               async : false,
               data : {},
               success:function(data){
                   if(data.success){
                       $("#verificationCode").attr("src","data:image/jpeg;base64,"+data.result);
                   }else{
                        layer.open({content:data.message ,skin: 'msg',time: 2 });
                   }
               },
               error:function(){
                   $("#confirm").removeAttr("disabled");
                   $("#refreshCode").removeAttr("disabled");
                   layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
               }
           });

       }
	}
    <#--function refreshCode(){-->
    <#--jQuery("#code_img").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?d"+new Date().getTime());-->
    <#--}-->
</script>



</body>
</html>
