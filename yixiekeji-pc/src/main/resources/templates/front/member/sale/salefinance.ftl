<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/usersale.css"/>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/userindex.css"/>
	
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
					<a href='javascript:void(0)'>财务中心</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

			<!-- S 右侧主要内容 -->
			<div class='wrapper_main myorder wrapper_main-wd'>

				<div class="reminder-con">
					<h3>温馨提示</h3>
					<div class="con">
						<p>(1)请上传真实用户资料。</p>
						<p>(2)我们会保护用户的隐私资料，绝不会向第三方透漏用户的个人资料。</p>
						<p>(3)为确保结算安全，该项信息不能修改，如需变更，请联系客服!银行卡的户名必须同姓名一致!</p>
						<p>(4)办理此银行卡时所用真实姓名，请与身份证保持一致!</p>
						<p>(5)开户行地址格式为：xx省,xx市/县,xx支行/分行,请认真核对为确保结算安全，该项信息不能修改，如需变更，请联系客服!</p>
						<p>(6)客服：QQ:43006111</p>
					</div>
				</div>

				<div class="reminder-con reminder-conbotm">
					<h3>用户信息</h3>
					<form action="" id="salefinanceForm">
						<input type="hidden" name="id" value="${(saleMember.id)!0}" />
						<div class="cb-item">
							<label for="">当前状态：</label>
							<div class="ipt-con"><span class="examineStatus"><@cont.codetext value="${(saleMember.state)!1}" codeDiv="SALE_MEMBER_STATE"/></span></div>
						</div>
						<div class="cb-item">
							<label for=""><i>*</i>证件类型：</label>
							<div class="ipt-con">
								<select name="certificateType" id="certificateType">
									<option value="1">居民身份证</option>
								</select>
							</div>
						</div>
						<div class="cb-item">
							<label for=""><i>*</i>证件号：</label>
							<div class="ipt-con">
								<input type="text" name="certificateCode" id="certificateCode" value="${(saleMember.certificateCode)!''}" />
								<em class='em-errMes' style="color: red;"></em>
							</div>
						</div>
						<div class="cb-item">
							<label for=""><i>*</i>开户银行：</label>
							<div class="ipt-con">
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
								<em class='em-errMes' style="color: red;"></em>
							</div>
						</div>
						<div class="cb-item">
							<label for=""><i>*</i>银行卡号：</label>
							<div class="ipt-con">
								<input type="text" name="bankCode" id="bankCode" value="${(saleMember.bankCode)!''}"/>
								<em class='em-errMes' style="color: red;"></em>
							</div>
						</div>
						<div class="cb-item">
							<label for=""><i>*</i>开户人姓名：</label>
							<div class="ipt-con">
								<input type="text" name="bankName" id="bankName" value="${(saleMember.bankName)!''}" />
								<em class='em-errMes' style="color: red;"></em>
							</div>
						</div>
						<div class="cb-item">
							<label for=""><i>*</i>开户行地址：</label>
							<div class="ipt-con">
								<input type="text" name="bankAdd" id="bankAdd" value="${(saleMember.bankAdd)!''}" />
								<em class='em-errMes' style="color: red;"></em>
							</div>
						</div>
						<p class="pTip">* (格式为: xx省，xx市/县，xx支行/分行； 用“，”隔开)</p>
						
						<div class="cb-item">
						<#if (saleMember.state)??>
							<#if saleMember.state != 3>
								<input type='button'  class='btn btn-danger bt_submit_finance' id='buttonSubmit' name='button' value='确定修改'>
							</#if>
							
							<#if saleMember.state == 1 || saleMember.state == 4>
								<input type='button'  class='btn btn-danger bt_submit_salefinance' id='buttonSaleSubmit' name='button' value='提交审核'>
							</#if>
						</#if>
						</div>
					</form>
				</div>
				

			</div>
			<!-- E 右侧主要内容 -->
			
		</div>
	<script type="text/javascript">
		$(function(){
			
			//控制左侧菜单选中
			$("#saleFinance").addClass("currnet_page");
			
			//校验
			jQuery("#salefinanceForm").validate({
				errorPlacement : function(error, element) {
					var obj = element.siblings(".em-errMes").css('display', 'block');
					error.appendTo(obj);
				},
		        rules : {
		            "certificateCode":{required:true,rangelength:[18,18]},
		            "bankCode":{required:true,digits:true,rangelength:[19,19]},
		            "bankName":{required:true,rangelength:[2,20]},
		            "bankAdd":{required:true,rangelength:[10,100]}
		        },
		        messages:{
		            "certificateCode":{required:"身份证号不能为空，请输入身份证号",rangelength:"请输入18位的身份证号码"},
		        		"bankCode":{required:"银行卡号不能为空，请输入银行卡号",digits:"银行卡号只能输入正整数",rangelength:"请输入19位的银行卡号"},
		            "bankName":{required:"开户行姓名不能为空，请输入开户行姓名",rangelength:"姓名介于2到20个字符之间"},
		            "bankAdd":{required:"开户行地址不能为空，请输入开户行地址",rangelength:"开户行地址介于10到100个字符之间"}
		        }
		    });
		
			
			$(".bt_submit_finance").click(function(){
				if($("#salefinanceForm").valid()){
					$(".bt_submit").attr("disabled","disabled");
					var params = $('#salefinanceForm').serialize();
					
					$.ajax({
						type:"POST",
						url:domain+"/member/sale-finance.html",
						dataType:"json",
						async : false,
						data : params,
						success:function(data){
							if(data.success){
								jAlert('操作成功', '提示',function(){
									window.location.href=domain+"/member/sale-finance-info.html"
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
			
			
			$(".bt_submit_salefinance").click(function(){
				jConfirm("确定提交审核吗？", "提示", function(r) {
					if (r) { 
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
									jAlert('操作成功', '提示',function(){
										window.location.href=domain+"/member/sale-finance-info.html"
									});
								}else{
									jAlert(data.message);
									$(".buttonSaleSubmit").removeAttr("disabled");
								}
							},
							error:function(){
								jAlert("异常，请重试！");
								$(".buttonSaleSubmit").removeAttr("disabled");
							}
						});
					}
				}); 
			});
			
			
		});
	</script>
	
<#include "/front/commons/_endbig.ftl" />
