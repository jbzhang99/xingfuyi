<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/usersale.css"/>
	
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
					<a href='javascript:void(0)'>我的分销</a>
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
						<p>(3)申请成为分销者之后，会获得推荐码和推荐链接，可以分享给好友。</p>
						<p>(4)好友注册成功，并消费成功之后可以获得对应的佣金。</p>
						<p>(5)成为分销者之后，在商品列表页可以看到具体商品获得的佣金比例。</p>
						<p>(6)客服：QQ:43006111。</p>
					</div>
				</div>

				<div class="reminder-con reminder-conbotm">
					<h3>用户信息</h3>
						<div class="cb-item">
							<label for="">当前状态：</label>
							<div class="ipt-con">
								<span class="examineStatus">
									<#if saleMember.isSale == 0>
										未开通
									<#elseif saleMember.isSale == 1>
										开通
									</#if>
								</span>
							</div>
						</div>
						<div class="cb-item">
							<label for="">用户名：</label>
							<div class="ipt-con"><span class="examineInfo">${(member.name)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">注册时间：</label>
							<div class="ipt-con"><span class="examineInfo">${(member.registerTime)?string('yyyy.MM.dd HH:mm:ss')!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">手机：</label>
							<div class="ipt-con"><span class="examineInfo">${(member.mobile)!''}</span></div>
						</div>
						<div class="cb-item">
							<label for="">我的推荐码：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleMember.saleCode)!'无'}</span></div>
						</div>
						<div class="cb-item">
							<label for="">我的推荐链接：</label>
							<div class="ipt-con">
								<span class="examineInfo">
									<#if saleMember.isSale == 0>
										无
									<#elseif saleMember.isSale == 1>
										${(domainUrlUtil.urlResources)!}/sale_enter.html?salecode=${(saleMember.saleCode)!''}
									</#if>
								</span></div>
						</div>
						<div class="cb-item">
							<label for="">我的推荐人：</label>
							<div class="ipt-con"><span class="examineInfo">${(saleMember.referrerName)!'无'}</span></div>
						</div>
						
						<#if saleMember.isSale == 0>
							<div class="cb-item">
								<label for=""></label><div class="ipt-con textcter">
									<a href="javascript:void(0)" onclick="jsSaleMember()" class="btn btn-danger bt_submit_finance colftbtn">成为推广员</a>
								</div>
							</div>
						</#if>
						
				</div>
				

			</div>
			<!-- E 右侧主要内容 -->
			
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#saleIndex").addClass("currnet_page");
		});
		
		function jsSaleMember(id){
			  jConfirm("是否申请成为推广员？", "提示", function(r) { 
            	  	  if (r) { 
            	  	  	$.ajax({
						type:"GET",
						url:domain+"/member/sale-apply.html",
						dataType:"json",
						async : false,
						success:function(data){
							if(data.success){
								//jAlert("设置成功");
								window.location.href=domain+"/member/sale-index.html";
							}else{
								jAlert(data.message);
							}
						},
						error:function(){
							jAlert("操作失败，请重试！");
						}
					});
                   } 
               }); 
		}
	</script>
	
<#include "/front/commons/_endbig.ftl" />
