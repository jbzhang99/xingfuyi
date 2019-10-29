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
        <div class="flex-2 text-center">重置密码</div>
        <div class="flex-1 text-right"></div>
    </div>

</header>
<!-- 头部 end-->
<!--modify by fanlei 2019-10-10 登录注册重构-->
<div class="padl_r15">
    <div style="height: 2rem;"></div>
    <div class="setPasswordInput">
        <span>设置密码</span>
        <input type="password" id="passwordOneForget" maxlength="20" placeholder="请输入密码"/>
    </div>
    <div class="setPasswordInput">
        <span>再次确认密码</span>
        <input type="password" id="passwordAgainForget" maxlength="20"  placeholder="请再次确认密码"/>
    </div>
    <div class="tipsPassword">
        必须是6-20个英文字母、数字或符号(除空格)，且字母、数字和标点符号至少包含两种
    </div>
    <!--完成按钮-->
    <div class="loginBtn" id="nextBtnCompleteForget">
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
        $("#passwordOneForget,#passwordAgainForget").bind('input propertychange',function(){
            if(($('#passwordOneForget').val().trim()!='' && $('#passwordAgainForget').val().trim()!='')){
                $("#nextBtnCompleteForget").removeClass('loginBtn');
                $("#nextBtnCompleteForget").addClass('loginBtnClick');
            }else{
                $("#nextBtnCompleteForget").removeClass('loginBtnClick');
                $("#nextBtnCompleteForget").addClass('loginBtn');
            }
        })
        $("#nextBtnCompleteForget").click(function () {
            if($("#passwordOneForget").val().trim()=="" || $("#passwordOneForget").val().trim()==null ){
                layer.open({content: '密码不得为空',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordOneForget").val().length < 6){
                layer.open({content: '密码长度不得小于6位',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordAgainForget").val().trim()=="" || $("#passwordAgainForget").val().trim()==null ){
                layer.open({content: '再次确认密码不得为空',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordAgainForget").val().length < 6){
                layer.open({content: '再次确认密码长度不得小于6位',skin: 'msg',time: 2 });
                return false;
            }
            if($("#passwordOneForget").val() !=  $("#passwordAgainForget").val()){
                layer.open({content: '两次密码不一样',skin: 'msg',time: 2 });
                return false;
            }
            var passwordOneForget = $("#passwordOneForget").val();
            var passwordAgainForget = $("#passwordAgainForget").val();
            if (passwordOneForget.indexOf(" ") > 0 || passwordAgainForget.indexOf(" ") >0) {
                layer.open({content: '密码中不得包含空格',skin: 'msg',time: 2 });
                return false;
            }
            $("#nextBtnCompleteForget").attr("disabled","disabled");
            $.ajax({
                type : 'get',
                url : domain + "/updatePassword",
                async:false,
                data:{
                    mobile:getData("phoneNumberForget"),
                    smsVerifyCode:getData("smsVerifyCodeForget"),
                    password:passwordAgainForget
                },
                success : function(e) {
                    if (e.success) {
                        window.location=domain+"/member/index.html";
                    } else{
                        layer.open({content:e.message ,skin: 'msg',time: 2 });
                        $("#nextBtnCompleteForget").removeAttrs("disabled");
                    }
                }
            });

        })
    })
    //add by fanlei 2019-10-10 登录注册重构  end

</script>



</body>
</html>
