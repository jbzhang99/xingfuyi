<#include "/front/commons/_headbig.ftl" />
	
		<div class='container'>
			<div class='breadcrumb'>
				<strong class='business-strong'>
					<a href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
				</strong>
				<span>
					&nbsp;>&nbsp;
					<a href='${(domainUrlUtil.urlResources)!}/member/index.html'>我的齐驱科技
				</span>
				<span>
					&nbsp;>&nbsp;
					<a href='javascript:void(0)'>我的收货地址</a>
				</span>
			</div>
		</div>
		<div class='container'>
			<!--左侧导航 -->
			<#include "/front/commons/_left.ftl" />
			<!-- 右侧主要内容 -->
			<div class='wrapper_main myorder' id='addressList'>
				<!-- 地址列表区域 -->
				<h3>
		<a href='javascript:void(0);' class='add-address ftx-05' onclick="addOrEditAddress(0)">新增收货地址</a>
					我的收货地址
		</h3>
				<#if addressList??>
					<#list addressList as address>
						<input type="hidden" value="${address.id}" id ="addressId${address_index }" />
						<ul class="ul_col2">
							<#if (address.state)=1>
								<li class='li-default' style="display:block"><span class='default-address'>默认地址</span></li>
							<#else>
								<li class='li-default' style="display:none"><span class='default-address'>默认地址</span></li>
							</#if>
							
							<li class='li-infomation'>
								<em class='em-infotitle'>收货人：</em>
								<span class='ed_fresher'>${address.memberName}</span>
							</li>
							<li class='li-infomation'>
								<em class='em-infotitle'>手机号码：</em>
								<span class='ed_contact'>${address.mobile}</span>
							</li>
							<br>
							<li class='li-infomation'>
								<em class='em-infotitle'>邮箱：</em>
								<span class='ed_email'>${address.email}</span>
							</li>
							<li class='li-infomation'>
								<em class='em-infotitle'>邮政编码：</em>
								<span class='ed_fresh'>${(address.zipCode)!''}</span>
							</li>
							<br>
							<li class='li-infomation'>
								<em class='em-infotitle'>收货地址：</em>
								<span class='dd_col2 ed_addr'>${address.addAll}${address.addressInfo}</span>
							</li>
							<li class='li-operation'>
								<#if (address.state)=1>
									<a href='javascript:void(0);' style="display:none" class='ftx-05 defaultAddress' onclick="defaultAddress('${address.id}')">设为默认</a>
								<#else>
									<a href='javascript:void(0);' style="display:block" class='ftx-05 defaultAddress' onclick="defaultAddress('${address.id}')">设为默认</a>
								</#if>
								
								<a href='javascript:void(0);' class='ftx-05 edit-address' onclick="addOrEditAddress('${address.id}')">编辑</a>
								<a href='javascript:void(0);' class='ftx-05 delete-address'  onclick="deleteAddress('${address.id}')">删除</a>
							</li>
						</ul>
					</#list>
				</#if>
			</div>
		</div>

		<!-- 收货人信息 -->
		<div class='background-layer' id='Harvest'>
		</div>
		
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/member/myreciptaddress.js'></script>
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/areaSupport.js'></script>
	<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/common.js'></script>
<#include "/front/commons/_endbig.ftl" />
