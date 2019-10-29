<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript"
	src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
<script type="text/javascript"
	src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/user_retrieve_password.css">
		
<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'> <a
			href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
		</strong> <span> &nbsp;>&nbsp; <a
			href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技</a>
		</span> <span> &nbsp;>&nbsp; <a href='javascript:void(0)'>重置支付密码</a>
		</span>
	</div>
</div>

<div class='container'>
	<#include "/front/commons/_left.ftl" />

	<div class="wrapper_main myorder wrapper_main-wd">
		<div class="mc">
			<div class="stepflex clearfix">
				
				<dl class="first doing">
					<dt class="s-num">1</dt>
					<dd class="s-text">
						验证身份<s></s>
					</dd>
				</dl>
				<dl class="normal doing">
					<dt class="s-num">2</dt>
					<dd class="s-text">
						修改支付密码<s></s><b></b>
					</dd>
				</dl>
				<dl class="last ">
					<dt class="s-num"></dt>
					<dd class="s-text">
						完成<s></s><b></b>
					</dd>
				</dl>
			</div>
			<div class="form-boxzh">
				<form id='editPasswordform'>
					<div class="item-user">
							<span class="w108"> 已验证手机：</span>
							<label class="tel-yz">${commUtil.sensitiveInfo(member.mobile,'tel')}</label>
							<span style="color:#2722F4">通过支付密码验证
							</span>
						</div>
						<div class="item-user">
							<span class="w108"> 新密码：</span>
							<input type="password" class="user-zh" name="newPwd" id="newPwd"/>
							<span class="colr err-box"></span>
						</div>
						<div class="item-user">
							<span class="w108"> 确认密码：</span>
							<input type="password" class="user-zh" name="repwd" id="repwd"/>
							<span class="colr err-box"></span>
						</div>
						<div class="item-user">
							<input type="button" value="提交" class="btn-ip" id="bt_submit">
						</div>
				</form>
			</div>
		</div>
	</div>
	<!-- end -->
</div>
<script type="text/javascript">
	$(function() {
		//控制左侧菜单选中
		$("#balance").addClass("currnet_page");

		//校验
		$("#editPasswordform").validate({
			errorPlacement : function(error, element) {
				var obj = element.siblings("span.err-box").show();
				error.appendTo(obj);
			},
			rules : {
				"newPwd" : {
					minlength : 6,
					required : true
				},
				"repwd" : {
					equalTo : "#newPwd",
					required : true
				}
			},
			messages : {
				"newPwd" : {
					minlength : "请至少输入6位长度的密码",
					required : "请输入密码"
				},
				"repwd" : {
					equalTo : "两次密码不一致",
					required : "请输入确认密码"
				}
			}
		});

		$("#bt_submit").click(function() {
			if ($("#editPasswordform").valid()) {
				$("#bt_submit").attr("disabled", "disabled");
				var params = $('#editPasswordform').serialize();
				$.ajax({
					type : "POST",
					url : domain
							+ "/member/balancepwdreset/updatepwd.html",
					dataType : "json",
					async : false,
					data : params,
					success : function(data) {
						if (data.success) {
							//下一步
							window.location.href = domain+"/member/balance.html";
						} else {
							jAlert(data.message);
							$("#bt_submit").removeAttr(
									"disabled");
						}
					},
					error : function() {
						jAlert("系统异常，请稍后重试！");
						$("#bt_submit").removeAttr(
								"disabled");
					}
				});
			}

		});
	});
</script>

<#include "/front/commons/_endbig.ftl" />
