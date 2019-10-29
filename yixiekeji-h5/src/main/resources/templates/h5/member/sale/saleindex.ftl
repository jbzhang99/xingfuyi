<#include "/h5/commons/_head.ftl" />
<link rel="stylesheet" href="${(domainUrlUtil.staticResources)!}/css/salecenter.css">
<body class="bgf2">
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
	   	  	 	<a href="javascript:ejsPageBack();">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
			</div>
			<div class="flex-2 text-center">我的分销</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
    <!-- 主体 start-->
	<div class="s-container">
        <div class="formboxcon">
            <!-- <p class="ptit">填写个人信息</p> -->
            <form action="">
                <div class="foemitems">
                    <div class="u-label">当前状态：</div>
                    <div class="colw red">
                    		<#if saleMember.isSale == 0>
							未开通
						<#elseif saleMember.isSale == 1>
							开通
						</#if>
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i></i>用户名：</div>
                    <div class="colw">
                       ${(member.name)!''}
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i></i>注册时间：</div>
                    <div class="colw">
                        ${(member.registerTime)?string('yyyy.MM.dd HH:mm:ss')!''}
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i></i>手机：</div>
                    <div class="colw">
                        ${(member.mobile)!''}
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i></i>我的推荐码：</div>
                    <div class="flex-2">
                        ${(saleMember.saleCode)!'无'}
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i></i>我的推荐链接：</div>
                    <div class="colw">
                        <#if saleMember.isSale == 0>
							无
						<#elseif saleMember.isSale == 1>
							${(domainUrlUtil.urlResources)!}/sale_enter.html?salecode=${(saleMember.saleCode)!''}
						</#if>
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i></i>我的推荐人：</div>
                    <div class="colw">
                        ${(saleMember.referrerName)!'无'}
                    </div>
                </div>
                
                
                <#if saleMember.isSale == 0>
                <div class="txtcetr">
					<a href="javascript:void(0)" onclick="jsSaleMember()" class="abtn btn btn-danger">成为推广员</a>
				<div>
				</#if>
				
            </form>
        </div>

        <div class="reminder-con">
            <h3>温馨提示：</h3>
            <div class="cons">
                <p>(1)请上传真实用户资料。</p>
                <p>(2)我们会保护用户的隐私资料，绝不会向第三方透漏用户的个人资料。</p>
                <p>(3)为确保结算安全，该项信息不能修改，如需变更，请联系客服!银行卡的户名必须同姓名一致!</p>
                <p>(4)办理此银行卡时所用真实姓名，请与身份证保持一致!</p>
                <p>(5)开户行地址格式为：xx省,xx市/县,xx支行/分行,请认真核对为确保结算安全，该项信息不能修改，如需变更，请联系客服!</p>
                <p>(6)客服：QQ:43006111。</p>
            </div>
        </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	function jsSaleMember(){
        $.dialog('confirm','提示','是否申请成为推广员!',0,function(){
			$.ajax({
				type:"GET",
				url:domain+"/member/sale-apply.html",
				dataType:"json",
				async : false,
				success:function(data){
					if(data.success){
						window.location.href=domain+"/member/sale-index.html";
					}else{
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		});
	}
</script>
</body>
</html>