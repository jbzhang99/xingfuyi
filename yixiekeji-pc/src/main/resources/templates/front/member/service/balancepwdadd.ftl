<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
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
			<a href='javascript:void(0)'>设置支付密码</a>
		</span>
	</div>
</div>

<div class='container'>
	<#include "/front/commons/_left.ftl" />

	<div class='wrapper_main myorder'>
		<h3>设置支付密码</h3>
		<div class=''id='pageTips'></div>
		<form class='pwform' id='editPasswordform'>
			<dl class='dl-setpw'>
				<dt class='dt_item'>
					<span style='color:red'>*&nbsp;</span><label>密码：</label>
				</dt>
				<dd class='dd_item p-item'>
					<input type='password' class='txt' id='txt-newpw' name='password' style='width:40%'>
					<em class='em-errMes' style='color:red'></em>
				</dd>
			</dl>
			<dl class='dl-setpw'>
				<dt class='dt_item'>
					<span style='color:red'>*&nbsp;</span><label>确认密码：</label>
				</dt>
				<dd class='dd_item p-item'>
					<input type='password' class='txt' id='txt-repw' name='confirmPwd' style='width:40%'>
					<em class='em-errMes' style='color:red'></em>
				</dd>
			</dl> 
			<input type='button'  class='btn btn-danger bt_submit' id='buttonSubmit' name='button' value='确认提交'>
		</form>
	</div>
	<!-- end -->
</div>

	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#editPassword").addClass("currnet_page");
			
			//校验
			jQuery("#editPasswordform").validate({
				errorPlacement : function(error, element) {
					var obj = element.siblings(".em-errMes").css('display', 'block');
					error.appendTo(obj);
				},
		        rules : {
		            "password":{required:true,passwordLength:true},
		            "confirmPwd":{required:true,equalTo:"#txt-newpw"}
		        },
		        messages:{
		        	"password":{required:"请输入密码"},
		            "confirmPwd":{required:"请输入确认密码",equalTo:"密码输入不一致"}
		        }
		    }); 
		
		
			$(".bt_submit").click(function(){
				if($("#editPasswordform").valid()){
					$(".bt_submit").attr("disabled","disabled");
					var params = $('#editPasswordform').serialize();
					$.ajax({
						type:"POST",
						url:domain+"/member/savebalancepassword.html",
						dataType:"json",
						async : false,
						data : params,
						success:function(data){
							if(data.success){
								//jAlert("密码修改成功");
								//重新加载数据
								//window.location.href=domain+"/member/balance.html";
								
								jAlert('密码修改成功', '提示',function(){
									window.location.href=domain+"/member/balance.html"
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
