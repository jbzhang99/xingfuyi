 <#include "/front/commons/_headbig.ftl" />

<link rel="icon" type="image/x-icon" href="${(domainUrlUtil.staticResources)!}/img/favicon.png" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/article.css">

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/frontseller/js/step1.js'></script>

<div class='container'>
	<div class='breadcrumb'>
		<strong class='business-strong'> <a href=''>首页</a>
		</strong> <span> &nbsp;>&nbsp; <a href=''>商家入住申请</a>
		</span>
	</div>
</div>
<div class='container'>
	<div class='business-layout'>
		<div class='joinin-step'>
			<ul>
				<li class='li-curr step1 current' data-form='1'><span>签订入驻协议</span>
				</li>
				<li class='li-curr' data-form='2'><span>公司资质信息</span></li>
				<li class='li-curr ' data-form='3'><span>财务资质信息</span></li>
				<li class='li-curr' data-form='4'><span>店铺经营信息</span></li>
				<!-- <li class='li-curr' data-form='5'>
							<span>合同签订及缴费</span>
						</li> -->
				<li class='li-curr step6' data-form='6'><span>店铺开通</span></li>
			</ul>
		</div>
		<div class='joinin-concrete'>
			<!-- 协议 -->
			<div class='apply_agreement' id='apply_agreement'>
				<div class='title'>
					<h3>入驻协议</h3>
				</div>
				<div>
					<p>
						使用本公司服务所须遵守的条款和条件。 <br> <br> 1.用户资格 <br>
						本公司的服务仅向适用法律下能够签订具有法律约束力的合同的个人提供并仅由其使用。在不限制前述规定的前提下，本公司的服务不向18周岁以下或被临时或无限期中止的用户提供。如您不合资格，请勿使用本公司的服务。此外，您的账户（包括信用评价）和用户名不得向其他方转让或出售。另外，本公司保留根据其意愿中止或终止您的账户的权利。
						<br> <br> 2.您的资料（包括但不限于所添加的任何商品）不得： <br>
						*具有欺诈性、虚假、不准确或具误导性； <br>
						*侵犯任何第三方著作权、专利权、商标权、商业秘密或其他专有权利或发表权或隐私权； <br>
						*违反任何适用的法律或法规（包括但不限于有关出口管制、消费者保护、不正当竞争、刑法、反歧视或贸易惯例/公平贸易法律的法律或法规）；
						<br> *有侮辱或者诽谤他人，侵害他人合法权益的内容； <br>
						*有淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的内容； <br>
						*包含可能破坏、改变、删除、不利影响、秘密截取、未经授权而接触或征用任何系统、数据或个人资料的任何病毒、特洛依木马、蠕虫、定时炸弹、删除蝇、复活节彩蛋、间谍软件或其他电脑程序；
						<br> <br> 3.违约 <br>
						如发生以下情形，本公司可能限制您的活动、立即删除您的商品、向本公司社区发出有关您的行为的警告、发出警告通知、暂时中止、无限期地中止或终止您的用户资格及拒绝向您提供服务：
						<br> (a)您违反本协议或纳入本协议的文件； <br> (b)本公司无法核证或验证您向本公司提供的任何资料；
						<br> (c)本公司相信您的行为可能对您、本公司用户或本公司造成损失或法律责任。 <br> <br>
						4.责任限制 <br>
						本公司、本公司的关联公司和相关实体或本公司的供应商在任何情况下均不就因本公司的网站、本公司的服务或本协议而产生或与之有关的利润损失或任何特别、间接或后果性的损害（无论以何种方式产生，包括疏忽）承担任何责任。您同意您就您自身行为之合法性单独承担责任。您同意，本公司和本公司的所有关联公司和相关实体对本公司用户的行为的合法性及产生的任何结果不承担责任。
						<br> <br> 5.无代理关系 <br>
						用户和本公司是独立的合同方，本协议无意建立也没有创立任何代理、合伙、合营、雇员与雇主或特许经营关系。本公司也不对任何用户及其网上交易行为做出明示或默许的推荐、承诺或担保。
						<br> <br> 6.一般规定 <br>
						本协议在所有方面均受中华人民共和国法律管辖。本协议的规定是可分割的，如本协议任何规定被裁定为无效或不可执行，该规定可被删除而其余条款应予以执行。
					</p>
				</div>
				<div class='apple-agreement'>
					<input type='checkbox' id='input_apply_agreement' checked>
					<label for='input_apply_agreement'>我已阅读并同意以上协议</label>
				</div>
				<div class='bottom'>
					<a href='javascript:void(0);' id='btn_apply_agreement_next' class='btn btn-default'>下一步，填写企业资质信息</a>
				</div>
			</div>
			<!-- end -->
		</div>
	</div>
</div>
<#include "/front/commons/_endbig.ftl" />
