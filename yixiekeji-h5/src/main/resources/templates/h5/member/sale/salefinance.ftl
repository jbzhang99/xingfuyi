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
			<div class="flex-2 text-center">我的财务信息</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
    <!-- 主体 start-->
	<div class="s-container">
        <div class="formboxcon">
            <form action="" id="salefinanceForm">
            		<input type="hidden" name="id" value="${(saleMember.id)!0}" />
                <div class="foemitems">
                    <div class="u-label">当前状态：</div>
                    <div class="colw red">
                    		<@cont.codetext value="${(saleMember.state)!1}" codeDiv="SALE_MEMBER_STATE"/>
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i>*</i>证件类型：</div>
                    <div class="colw">
                        <select name="certificateType" id="certificateType">
                            <option value="1">居民身份证</option>
                        </select>
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i>*</i>证件号：</div>
                    <div class="colw">
                        <input type="text" name="certificateCode" id="certificateCode" value="${(saleMember.certificateCode)!''}"  placeholder="请输入证件号">
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i>*</i>开户银行：</div>
                    <div class="flex-2">
                        <select name="bankType" id="bankType">
                            <option value="中国工商银行" <#if (saleMember.bankType)?? && saleMember.bankType == '中国工商银行'>selected</#if> >中国工商银行</option>
							<option value="中国农业银行" <#if (saleMember.bankType)?? && saleMember.bankType == '中国农业银行'>selected</#if> >中国农业银行</option>
							<option value="中国建设银行" <#if (saleMember.bankType)?? && saleMember.bankType == '中国建设银行'>selected</#if> >中国建设银行</option>
							<option value="招商银行" <#if (saleMember.bankType)?? && saleMember.bankType == '招商银行'>selected</#if> >招商银行</option>
							<option value="中国银行" <#if (saleMember.bankType)?? && saleMember.bankType == '中国银行'>selected</#if> >中国银行</option>
							<option value="交通银行" <#if (saleMember.bankType)?? && saleMember.bankType == '交通银行'>selected</#if> >交通银行</option>
							<option value="民生银行" <#if (saleMember.bankType)?? && saleMember.bankType == '民生银行'>selected</#if> >民生银行</option>
							<option value="光大银行" <#if (saleMember.bankType)?? && saleMember.bankType == '光大银行'>selected</#if> >光大银行</option>
							<option value="中信银行" <#if (saleMember.bankType)?? && saleMember.bankType == '中信银行'>selected</#if> >中信银行</option>
                        </select>
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i>*</i>银行卡号：</div>
                    <div class="colw">
                        <input type="text" name="bankCode" id="bankCode" value="${(saleMember.bankCode)!''}" placeholder="请输入银行卡号">
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i>*</i>开户人姓名：</div>
                    <div class="colw">
                        <input type="text" name="bankName" id="bankName" value="${(saleMember.bankName)!''}" placeholder="请输入开户人姓名">
                    </div>
                </div>
                <div class="foemitems">
                    <div class="u-label"><i>*</i>开户行地址：</div>
                    <div class="colw">
                        <input type="text" name="bankAdd" id="bankAdd" value="${(saleMember.bankAdd)!''}" placeholder="请输入开户行地址">
                    </div>
                </div>
                <div class="formcol">* (格式为: xx省，xx市/县，xx支行/分行； 用“，”隔开)</div>
                
                <#if (saleMember.state)??>
                	<div class="txtcetr">
					<#if saleMember.state != 3>
						<input type='button'  class='btn btn-danger bt_submit_finance' id='buttonSubmit' name='button' value='确定修改'>
					</#if>
					
					<#if saleMember.state == 1 || saleMember.state == 4>
						<input type='button'  class='btn btn-danger bt_submit_salefinance' id='buttonSaleSubmit' name='button' value='提交审核'>
					</#if>
				</div>
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
	$(function(){
		<#if message?? && message != "">
			$.dialog('alert','提示','${message}',2000);
		</#if>
		
		$(".bt_submit_finance").click(function(){
			$(".bt_submit").attr("disabled","disabled");
			var certificateCode = $("#certificateCode").val();
			if(certificateCode == "") {
				$.dialog('alert','提示','身份证号码不能为空',2000);
				return false;
			}
			if(certificateCode.length != 18){
				$.dialog('alert','提示','身份证号码长度为18位',2000);
				return false;
			}
			
			var bankCode = $("#bankCode").val();
			if(bankCode == "") {
				$.dialog('alert','提示','银行卡号不能为空',2000);
				return false;
			}
			if(bankCode.length > 20 || bankCode.length < 9){
				$.dialog('alert','提示','请输入大于9位的银行卡号',2000);
				return false;
			}
			
			var bankName = $("#bankName").val();
			if(bankName == "") {
				$.dialog('alert','提示','开户人姓名不能为空',2000);
				return false;
			}
			if(bankName.length > 20 || bankName.length < 2){
				$.dialog('alert','提示','开户人姓名至少2位字符',2000);
				return false;
			}
			
			var bankAdd = $("#bankAdd").val();
			if(bankAdd == "") {
				$.dialog('alert','提示','开户地址不能为空',2000);
				return false;
			}
			if(bankAdd.length > 200 || bankAdd.length < 10){
				$.dialog('alert','提示','开户地址至少10位字符',2000);
				return false;
			}
			
			var params = $('#salefinanceForm').serialize();			
			$.ajax({
				type:"POST",
				url:domain+"/member/sale-finance.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						window.location.href=domain+"/member/sale-finance-info.html?success=success"
					}else{
						$.dialog('alert','提示',data.message,2000);
						$(".bt_submit").removeAttr("disabled");
					}
				},
			});
		});
		
		
		$(".bt_submit_salefinance").click(function(){
			 $.dialog('confirm','提示','确定提交审核吗？',0,function(){
				$(".buttonSaleSubmit").attr("disabled","disabled");
				var params = $('#salefinanceForm').serialize();
				$.ajax({
					type:"POST",
					url:domain+"/member/sale-submit.html",
					dataType:"json",
					async : false,
					data : params,
					success:function(data){
						if(data.success){
							window.location.href=domain+"/member/sale-finance-info.html?success=success"
						}else{
							$.dialog('alert','提示',data.message,2000);
							$(".buttonSaleSubmit").removeAttr("disabled");
						}
					},
				});
			}); 
		});
			
			
	});
</script>
</body>
</html>