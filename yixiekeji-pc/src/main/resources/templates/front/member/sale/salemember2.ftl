<#include "/front/commons/_headbig.ftl" />
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
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
					<a href='javascript:void(0)'>间接推广用户</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />

			<!-- S 右侧主要内容 -->
			<div class='wrapper_main myorder wrapper_main-wd'>
			
				<!-- 结算明细 -->
				<div class="reminder-con reminder-Detailed">
					<h3>结算明细</h3>
					<div class="derailedCon">
						<form action="${(domainUrlUtil.urlResources)!}/member/sale-member2.html" class="mybalance-search" method="get">
							<div class="searchItm">
								<span class="search-lable">推 广 用 户：</span>
								<input type="text" name="q_memberName" class="search__odn" value="${q_memberName!''}" placeholder="请输入注册用户名">
							</div>
							
							<div class="searchItm">
								<span class="search-lable">推 荐 人：</span>
								<input type="text" name="q_referrerName" class="search__odn" value="${q_referrerName!''}" placeholder="请输入推广用户名">
							</div>
							
							<div class="btnaera">
								 <input type="submit" class="searchorders bt" value="查询"/>
								 <a href="${(domainUrlUtil.urlResources)!}/member/sale-member2.html"  class="searchorders bt"/>重置</a>
							</div>
						</form>
					</div>
					
					<table class="searchlist-result">
						<tr class="">
							<th class="th-cell">用户名</th>
							<th class="th-cell">推荐人</th>
							<th class="th-cell th-cell-with20">注册时间</th>
							<th class="th-cell th-cell-with">是否推荐人</th>
						</tr>
						
						<#if saleMembers??>
							<#list saleMembers as saleMember>
								<tr>
									<td>${(saleMember.memberName)!""}</td>
									<td>${(saleMember.referrerName)!""}</td>
									<td>${(saleMember.createTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
									<td><@cont.codetext value="${(saleMember.isSale)!0}" codeDiv="YES_NO"/></td>
								</tr>
							</#list>
						</#if>
						
					</table>
				</div>
				
				<#include "/front/commons/_pagination.ftl" />
			</div>
			<!-- E 右侧主要内容 -->
			
		</div>
	<script type="text/javascript">
		$(function(){
			//控制左侧菜单选中
			$("#saleSaleMember2").addClass("currnet_page");
		});
	</script>
	
<#include "/front/commons/_endbig.ftl" />
