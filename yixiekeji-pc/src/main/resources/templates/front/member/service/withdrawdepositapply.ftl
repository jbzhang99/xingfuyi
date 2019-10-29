<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
		
<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'>
			<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
		</strong>
		<span>
			&nbsp;>&nbsp;
			<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技</a>
		</span>
		<span>
			&nbsp;>&nbsp;
			<a href='javascript:void(0)'>提现申请</a>
		</span>
	</div>
</div>

<div class='container'>
	<!--左侧导航 -->
	<#include "/front/commons/_left.ftl" />

	<!-- 右侧主要内容 -->
	<div class='wrapper_main'>
		<h3>提现申请</h3>
		
		<form class='myinfo' id='form'>
			<input type="hidden" id="balance" value="${balance!'0.00'}">
			<dl class='dl_col1'>
				<dt>
					<label>可用余额：</label>
				</dt>
				
				<dd> ${balance!'0.00'}</dd>
				<dt>
					<span style='color:red'>*&nbsp;</span><label>银行名称：</label>
				</dt>
				<dd class='p-item'>
					<input id="bank" name="bank"  class="txt txt-err" style='width:40%'/>
					<em class='em-errMes' style='color:red'></em>
				</dd>
				<dt>
					<span style='color:red'>*&nbsp;</span><label>银行卡号：</label>
				</dt>
				<dd class='p-item'>
					<input id="bankCode" name="bankCode"  class="txt txt-err" style='width:40%'/>
					<em class='em-errMes' style='color:red'></em>
				</dd>
				<dt>
					<span style='color:red'>*&nbsp;</span><label>申请提现金额：</label>
				</dt>
				<dd class='p-item'>
					<input type='text'  name='money'  class='txt txt-err' style='width:40%'>
					<em class='em-errMes' style='color:red'></em>
				</dd>

				<dt></dt>
				<dd class='p-item'>
					<input type='button' value='确认提交' class='bt_submit'>
				</dd>
			</dl>
		</form>
	</div>
</div>

	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#withdrawdeposit").addClass("currnet_page");
			
			   jQuery.validator.addMethod("balance", function(value,element) {
	                var sumreward=${balance!'0.00'};//$("#balance").val();
	                value = parseFloat(value);
	                sumreward = parseFloat(sumreward);
	              return this.optional(element) || value<= sumreward;   
	            }, $.validator.format(" * 余额不足 "));
	          
	          jQuery.validator.addMethod("checkBalance", function(value,element) {
	                var  balance=0;
	                value = parseFloat(value);
	              return this.optional(element) || value>balance;   
	            }, $.validator.format(" * 转账金额必须大于0 "));
			
			//校验
			jQuery("#form").validate({
				errorPlacement : function(error, element) {
					var obj = element.siblings(".em-errMes").css('display', 'block');
					error.appendTo(obj);
				},
		        rules : {
		            "bank":{required:true},
		            "money":{required:true,number:true,balance:true,checkBalance:true},
		            "bankCode":{required:true}
		        },
		        messages:{
		            "bank":{required:"请输入银行名称"},
		            "money":{required:"请输入提现金额",number:"请输入数字"},
		            "bankCode":{required:"请输入银行卡号"}
		        }
		    }); 
		
		
			$(".bt_submit").click(function(){
				if($("#form").valid()){
					$(".bt_submit").attr("disabled","disabled");
					var params = $('#form').serialize();
					$.ajax({
						type:"POST",
						url:domain+"/member/dowithdrawapply.html",
						dataType:"json",
						async : false,
						data : params,
						success:function(data){
							if(data.success){
								//jAlert("保存成功");
								//跳转到列表页面
								//window.location.href=domain+"/member/withdraw.html";
								
								jAlert('保存成功，工作人员会在24小时之内给您联系！', '提示',function(){
									window.location.href=domain+"/member/withdraw.html"
								});
							}else{
								jAlert(data.message);
								$(".bt_submit").removeAttr("disabled");
							}
						},
						error:function(){
							jAlert("异常，请重试！");
							$(".bt_submit").removeAttr("disabled");
						}
					});
				}
				
			});
		});
	</script>
	
<#include "/front/commons/_endbig.ftl" />
