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
        <div class="flex-2 text-center">忘记密码</div>
        <div class="flex-1 text-right"></div>
    </div>

</header>
<!-- 头部 end-->
<!--modify by fanlei 2019-10-10 登录注册重构-->
<div class="padl_r15">
    <div style="height: 2rem;"></div>
    <!--账户输入框-->
    <div class="accountInput">
        <span>账户</span>
        <input type="input" id="forgetAccountInputP" maxlength="11" placeholder="请输入手机号"/>
    </div>
    <!--下一步按钮-->
    <div class="loginBtn"  id="nextBtnForget">
        <div style="color: #ffffff;">下一步</div>
    </div>
</div>

<div class="maskbg" id="maskBgForget">
    <div class="modelbg" id="modelBgForget">
        <div class="model-head">
            <div class="model-headL">安全验证</div>
            <div class="model-headR">
                <img src="${(domainUrlUtil.staticResources)!}/img/icon_shuaxin@3x.png" style="width: 18px;height: 17px;margin-top: 1.5rem;" onclick="refreshCodeForget()">
            </div>
        </div>
        <div class="model-body">
            <img src="" id="verificationCodeForget"/>
        </div>
        <div class="verificationInput">
            <input type="input" id="verificationInputForget" maxlength="4" placeholder="请输入验证码"/>
            <span style="color:#E02020;" id="confirmForget">确认</span>
        </div>
        <div class="errorMask" id="errorMasksForget">
            <img src="" id="errorMaskImgForget">
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
        //手机号输入框失去焦点时，按钮颜色改变
        $("#forgetAccountInputP").bind('input propertychange',function(){
            if(($("#forgetAccountInputP").val().trim()=='')){
                $("#nextBtnForget").removeClass('loginBtnClick');
                $("#nextBtnForget").addClass('loginBtn');
            }else{
                $("#nextBtnForget").removeClass('loginBtn');
                $("#nextBtnForget").addClass('loginBtnClick');
            }
        })
        // 点击下一步按钮，弹出验证码遮罩层
        $("#nextBtnForget").click(function(){
            var phone = $("#forgetAccountInputP").val();
            var re = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
            if (phone == null || phone.trim() == "") {
                layer.open({content:"手机号不得为空" ,skin: 'msg',time: 2 });
                return false;
            }
            if(!re.test(phone)){
                layer.open({content:"请输入正确手机号" ,skin: 'msg',time: 2 });
                return false;
            }


            if($('#forgetAccountInputP').val().trim()!=''){
                $("#nextBtnForget").attr("disabled","disabled");
                $.ajax({
                    type:"get",
                    url:domain+"/checkMobileByForgetPassWord",
                    dataType:"json",
                    async : false,
                    data : {
                        mobile:$('#forgetAccountInputP').val()
                    },
                    success:function(data){
                        console.log(data);
                        if(data.success){
                            $("#maskBgForget").show();
                            $("#modelBgForget").show();
                            setData("phoneNumberForget",data.result);
                            refreshCodeForget();

                        }else{
                            layer.open({content:data.message ,skin: 'msg',time: 2 });
                            $("#nextBtnForget").removeAttr("disabled");
                        }
                    },
                    error:function(){
                        layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                        $("#nextBtnForget").removeAttr("disabled");
                    }
                });
            }
        })

        // 验证图形验证码

        $("#confirmForget").click(function(){
            if($('#verificationInputForget').val().trim() == '' ||$('#verificationInputForget').val().trim() == null){
                //提示内容                //2秒后自动关闭
                layer.open({content: '图形验证码不得为空',skin: 'msg',time: 2 });
                return false;
            }
            $("#confirmForget").attr("disabled","disabled");
            $("#refreshCodeForget").attr("disabled","disabled");

            $.ajax({
                type:"get",
                url:domain+"/checkVerifyCode",
                dataType:"json",
                async : false,
                data : {
                    verifyCode:$('#verificationInputForget').val()
                },
                success:function(data){
                    if(data.success){
                        $("#errorMasksForget").show();
                        $("#errorMaskImgForget").attr("src","${(domainUrlUtil.staticResources)!}/img/chenggong.png");
                        setTimeout(function () {
                            window.location=domain+"/registerVerificationForgetPassword.html";
                        },1000)
                    }else{
                        $("#confirmForget").removeAttr("disabled");
                        $("#refreshCodeForget").removeAttr("disabled");
                        $("#errorMasksForget").show();
                        $("#errorMaskImgForget").attr("src","${(domainUrlUtil.staticResources)!}/img/shibai.png");
                    }
                },
                error:function(){
                    $("#confirmForget").removeAttr("disabled");
                    $("#refreshCodeForget").removeAttr("disabled");
                    layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                }
            });
        })

    })

        //add by fanlei 2019-10-10 登录注册重构  end

    <#--//刷新验证码-->
    function refreshCodeForget(){
        var code = $("#errorMasksForget").css("display");
        if(code == "block"){
            $("#errorMasksForget").css("display","none");
        }else {
            $.ajax({
                type:"get",
                url:domain+"/verify.html",
                dataType:"json",
                async : false,
                data : {},
                success:function(data){
                    if(data.success){
                        $("#verificationCodeForget").attr("src","data:image/jpeg;base64,"+data.result);
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
        <#--if($("#errorMasksForget").show()){-->
            <#--$("#errorMasksForget").hide();-->
        <#--}else if ($("#errorMasksForget").hide()){-->
            <#--$("#verificationCodeForget").attr("src","${(domainUrlUtil.urlResources)!}/verify.html?name=verifyCode&type=2");-->
            <#--alert("111111")-->
        <#--}-->
    }
</script>



</body>
</html>
