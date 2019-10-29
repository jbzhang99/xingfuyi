 <#include "/front/commons/_headbig.ftl" />

<link rel="icon" type="image/x-icon" href="${(domainUrlUtil.staticResources)!}/img/favicon.png" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/article.css">
<link rel="stylesheet" href='${(domainUrlUtil.staticResources)!}/frontseller/css/errorStyle.css'>

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/My97DatePicker/WdatePicker.js'></script>
<!-- 需要依赖JQuery库执行！ -->
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/areaSupport.js'></script>
<script>
	var domain = "${(domainUrlUtil.urlResources)!}";
</script>

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/frontseller/js/step2.js'></script>

<div id="loadingDiv" style="position: fixed; display: none; z-index: 2000; top: 0px; left: 0px; width: 100%; height: 100%; background-color: rgba(255, 255, 255, 0.5)"></div>

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
				<li class='li-curr ' data-form='3'><span>财务资质信息</span></li>
				<li class='li-curr' data-form='4'><span>店铺经营信息</span></li>
				<!-- <li class='li-curr' data-form='5'>
							<span>合同签订及缴费</span>
						</li> -->
				<li class='li-curr step6' data-form='6'><span>店铺开通</span></li>
			</ul>
		</div>
		<div class='joinin-concrete'>
			<!-- 公司资质信息 -->
			<div id='apply_company_info' class='apply-company-info'>
				<div class='alert'>
					<h4>注意事项：</h4>以下所需要上传的电子版资质文件仅支持JPG\GIF\PNG格式图片，大小请控制在1M之内。
				</div>
				<form
					action='${(domainUrlUtil.urlResources)!}/store/step2save.html'
					id='form_company_info' method='post' enctype="multipart/form-data">
					<table border='0' cellpadding="0" cellspacing="0" class='all'
						id='table'>
						<thead>
							<tr>
								<th colspan='20'>公司及联系人信息</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th><i>*</i>
									公&nbsp;&nbsp;司&nbsp;&nbsp;名&nbsp;&nbsp;称&nbsp;：</th>
								<td><input name='company' type='text' id="company"
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 公&nbsp;司&nbsp;所&nbsp;在&nbsp;地：</th>
								<td id="td_area"></td>
							</tr>
							<tr>
								<th><i>*</i> 公司详细地址：</th>
								<td><input name='companyAdd' type='text'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i>
									公&nbsp;&nbsp;司&nbsp;&nbsp;电&nbsp;&nbsp;话&nbsp;&nbsp;：</th>
								<td><input name='telephone' type='text'
									class='form-control w200 w100'> <span></span></td>
							</tr>
							<tr>
								<th>传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真&nbsp;&nbsp;：</th>
								<td><input name='fax' type='text'
									class='form-control w200 w100'><span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 法&nbsp;定&nbsp;代&nbsp;表&nbsp;人&nbsp;：</th>
								<td><input name='legalPerson' type='text'
									class='form-control w200 w100'> &nbsp; <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 联&nbsp;系&nbsp;人&nbsp;电&nbsp;话&nbsp;：</th>
								<td><input name='personPhone' type='text'
									class='form-control w200 w100'> &nbsp; <span></span></td>
							</tr>
							<tr>
								<th><i>*</i>
									电&nbsp;&nbsp;子&nbsp;&nbsp;邮&nbsp;&nbsp;箱&nbsp;&nbsp;：</th>
								<td><input name='email' type='text'
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
								<th colspan='20'>营业执照信息（副本）</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th><i>*</i>
									营&nbsp;业&nbsp;执&nbsp;照&nbsp;号&nbsp;&nbsp;&nbsp;：</th>
								<td><input type='text' name='bussinessLicense'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 组织机构代码&nbsp;&nbsp;&nbsp;：</th>
								<td><input type='text' name='organization'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 营业开始日期&nbsp;&nbsp;&nbsp;：</th>
								<td><input type='text' name='companyStartTime' id="startdate"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate\')}'});"
									class='form-control w200' pattern="yyyy-MM-dd" /> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 营业结束日期&nbsp;&nbsp;&nbsp;：</th>
								<td><input type='text' name='companyEndTime' id="enddate"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate\')}'});"
									class='form-control w200' pattern="yyyy-MM-dd"> <span></span></td>
							</tr>
							<tr>
								<th><i>*</i> 营业执照扫描件：</th>
								<td><input type='file' name='up_bussinessLicenseImage'>
									<span class='block'>请确保图片清晰，文字可辨并有清晰的红色公章。</span> <input
									type='hidden' name='hd_bussinessLicenseImage' class='error'>
									<span></span></td>
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
								<th colspan="20">一般纳税人证明 <em>注：所属企业具有一般纳税人证明时，此项为必填。</em>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th><i>*</i> 法定代表人身份证：</th>
								<td><input type='text' name='legalPersonCard'
									class='form-control w200'> <span></span></td>
							</tr>
							<tr>
								<th class="w150"><i>*</i> 身份证正面扫描件：</th>
								<td><input type='file' name='up_personCardUp'> <span
									class='block'>请确保图片清晰，文字可辨并有清晰的红色公章。</span> <input
									type='hidden' name='hd_personCardUp'> <span></span></td>
							</tr>
							<tr>
								<th class="w150"><i>*</i> 身份证背面扫描件：</th>
								<td><input type='file' name='up_personCardDown'> <span
									class='block'>请确保图片清晰，文字可辨并有清晰的红色公章。</span> <input
									type='hidden' name='hd_personCardDown'> <span></span></td>
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
					<a href='javascript:void(0);' id='btn_apply_company_next'
						class='btn btn-default'
						onclick="$('#form_company_info').submit();">下一步，提交财务资质信息</a>
				</div>
			</div>
			<!-- end -->

		</div>
	</div>
</div>
<#include "/front/commons/_endbig.ftl" />
