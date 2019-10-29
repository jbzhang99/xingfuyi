 <#include "/front/commons/_headbig.ftl" />

<link rel="icon" type="image/x-icon" href="${(domainUrlUtil.staticResources)!}/img/favicon.png" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/article.css">
<link rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/frontseller/css/errorStyle.css'>

<!-- 需要依赖JQuery库执行！ -->
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/areaSupport.js'></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/frontseller/js/step3.js'></script>

<div id="loadingDiv"
	style="position: fixed; display: none; z-index: 2000; top: 0px; left: 0px; width: 100%; height: 100%; background-color: rgba(255, 255, 255, 0.5)"></div>

<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'> <a href=''>首页</a>
		</strong> <span> &nbsp;>&nbsp; <a href=''>商家入驻申请</a>
		</span>
	</div>
</div>
<div class='container'>
	<div class='business-layout'>
		<div class='joinin-step'>
			<ul>
				<li class='li-curr step1 current' data-form='1'><span>签订入驻协议</span>
				</li>
				<li class='li-curr current' data-form='2'><span>公司资质信息</span></li>
				<li class='li-curr  current' data-form='3'><span>财务资质信息</span>
				</li>
				<li class='li-curr' data-form='4'><span>店铺经营信息</span></li>
				<!-- <li class='li-curr' data-form='5'>
							<span>合同签订及缴费</span>
						</li> -->
				<li class='li-curr step6' data-form='6'><span>店铺开通</span></li>
			</ul>
		</div>
		<div class='joinin-concrete'>
			<!-- 公司财务资质信息 -->
			<div id='apply_credentials_info' class='apply_credentials_info'>
				<form
					action='${(domainUrlUtil.urlResources)!}/store/step3save.html'
					id='form_credentials_info' method='post'
					enctype="multipart/form-data">
					<table border='0' cellpadding="0" cellspacing="0" class='all'
						id='table'>
						<thead>
							<tr>
								<th colspan='20'>开户银行信息(此账号为结算账号)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th class="w150"><i>*</i> 开户行账号名称&nbsp;&nbsp;&nbsp;：</th>
								<td><input name='bankUser' type='text'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i>
									开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行&nbsp;&nbsp;&nbsp;&nbsp;：</th>
								<td><input name='bankName' type='text'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 开户行支行名称&nbsp;&nbsp;&nbsp;&nbsp;：</th>
								<td><input name='bankNameBranch' type='text'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 开户行支行联行号&nbsp;：</th>
								<td><input name='brandNameCode' type='text'
									class='form-control w200'> &nbsp; <span></span></td>
							</tr>

						</tbody>
						<tfoot>
							<tr>
								<td colspan="20">&nbsp;</td>
							</tr>
						</tfoot>
					</table>
					<table border='0' cellpadding="0" cellspacing="0" class='all'
						id='table'>
						<thead>
							<tr>
								<th colspan='20'>结算账号信息</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th class="w150"><i>*</i>
									银&nbsp;&nbsp;行&nbsp;&nbsp;账&nbsp;&nbsp;号&nbsp;&nbsp;：</th>
								<td><input id='bankCode' type='text' name='bankCode'
									class='form-control w200 '> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 开户行所在地&nbsp;：</th>
								<td id="td_area"></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="20">&nbsp;</td>
							</tr>
						</tfoot>
					</table>
					<table border="0" cellpadding="0" class="all" cellspacing="0"
						id='table'>
						<thead>
							<tr>
								<th colspan="20">税务登记证</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th class="w150"><i>*</i> 税务登记证号：</th>
								<td><input type='text' name='taxLicense'
									class='form-control w200'> <span></span></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="20">&nbsp;</td>
							</tr>
						</tfoot>
					</table>
				</form>
				<div class='bottom'>
					<a href='javascript:void(0);' id='btn_apply_credentials_next'
						class='btn btn-default'
						onclick="$('#form_credentials_info').submit();">下一步，提交店铺经营信息</a>
				</div>
			</div>
			<!-- end -->

		</div>
	</div>
</div>

<#include "/front/commons/_endbig.ftl" />