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
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center"></div>
   	  </div>
			<!--	modify by fanlei 2019-10-09 登录注册页面重构  end-->
   </header>
   <!-- 头部 end-->
<!--	modify by fanlei 2019-10-09 登录注册页面重构  start-->
	<div class="s-container">
		<div class="padl_r15">
			<div style="height: 3rem;"></div>
			<div style="font-family: PingFangSC-Regular;font-size: 2.7rem;color: #2E2D2D;letter-spacing: 0.32px;">
				请输入邀请码
			</div>
			<!--手机号码输入框-->
			<div class="phoneInput">
				<input type="input" id="invitationInput"  placeholder="请输入邀请码" oninput = "value=value.replace(/[^\d]/g,'')" maxlength="6"/>
			</div>
			<!--下一步按钮-->
			<div class="loginBtn" id="nextBtnToRegister">
				<div>下一步</div>
			</div>
			<!--新用户注册-->
			<div style="height: 2rem;"></div>
			<div class="newRegistration" style="color: #007AFF;" id="promoters">促销员注册</div>
   </div>
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
        $("#invitationInput").bind('input propertychange',function(){
          if(($("#invitationInput").val().trim()=='')){
            $("#nextBtnToRegister").removeClass('loginBtnClick');
            $("#nextBtnToRegister").addClass('loginBtn');
          }else{
            $("#nextBtnToRegister").removeClass('loginBtn');
            $("#nextBtnToRegister").addClass('loginBtnClick');
          }
        })
        //普通会员注册
        $("#nextBtnToRegister").click(function(){
            if ($("#invitationInput").val() == null || $("#invitationInput").val().trim() == "") {
                layer.open({content: '邀请码不得为空',skin: 'msg',time: 2});
                return false;
            }
            $.ajax({
                type:"get",
                url:domain+"/checkSaleCode",
                dataType:"json",
                async : false,
                data : {
                    saleCode: $("#invitationInput").val()
                },
                success:function(data){
                    if(data.success){
                        setData("saleCode",data.result);
                        window.location=domain+"/register.html";
                    }else{
                        layer.open({content:data.message ,skin: 'msg',time: 2 });
                    }
                },
                error:function(){
                    layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                }
            });
        });
        $("#promoters").click(function(){
            //询问框
            layer.open({
                title: '注册提示'
                ,content: '本商城致力于解决残疾人收入问题，只有持有残疾人证的残疾人或者其监护人才可以注册为促销员。'
                ,btn: ['确认','返回']
                ,yes: function(index){
                    window.location=domain+"/register.html";
                    layer.close(index);
                }
            });
        });

    });
</script>


</body>
</html>
