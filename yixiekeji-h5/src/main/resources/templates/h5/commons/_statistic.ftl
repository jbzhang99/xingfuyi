<script type="text/javascript">
	var memberId = 0;
	<#if Session.memberSession??>
		<#assign user = Session.memberSession.member>
		<#if user??>
			memberId = ${(user.id)!0};
   		</#if>
   </#if>
var browser={
	versions:function(){
       var u = navigator.userAgent, app = navigator.appVersion;
       return {//移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
	}(),
	language:(navigator.browserLanguage || navigator.language).toLowerCase()
}
var ebi = "";
if(browser.versions.ios == true) {
	ebi = "ios";
}
if(browser.versions.android == true) {
	ebi = "android";
}

function getBrowserInfo() {
	var agent = navigator.userAgent.toLowerCase() ;
	var regStr_ie = /msie [\d.]+;/gi;
	var regStr_ff = /firefox\/[\d.]+/gi;
	var regStr_chrome = /chrome\/[\d.]+/gi;
	var regStr_saf = /safari\/[\d.]+/gi;
	
	if(agent.indexOf("msie") > 0) {
		return agent.match(regStr_ie) ;
	}

	if(agent.indexOf("firefox") > 0) {
		return agent.match(regStr_ff) ;
	}

	if(agent.indexOf("chrome") > 0) {
		return agent.match(regStr_chrome) ;
	}

	if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
		return agent.match(regStr_saf) ;
	}
}
var browser = getBrowserInfo() ;
var verinfo = (browser+"").replace(/[^0-9.]/ig,"");

var ref = document.referrer;
var hrf = window.location.href;
document.write('<img width="1" height="1" style="position:absolute;display: none;" src="${(domainUrlUtil.urlResources)!}/browse_Logs.html?ref='+
		ref+'&hrf='+ hrf +'&ebi=' + ebi + '&memberId='+ memberId + '&browser='+ browser + '&verinfo=' + verinfo + '" />');
</script>