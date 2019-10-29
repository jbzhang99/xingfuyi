<#include "/h5/commons/_head.ftl" />
<body class="bgf2" style="overflow: hidden">
   <!-- 头部 -->
   <header id="header">
       <#--add by fanlei 2019-10-17 我的页面改造 start-->
      <div class="flex flex-align-center head-bar" style="border-bottom: 1px solid rgba(0,0,0,0.1)">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">关于幸福易</div>
   	  	 <div class="flex-1 text-right"></div>
   	  </div>
   	  <#--add by fanlei 2019-10-17 我的页面改造 start-->
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

	<div class="s-containerr">
        <div class="account-list add-account-list">
	        <#--<a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>给我评分</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	        <a href="${(domainUrlUtil.urlResources)!}/privacyProtocol.html" class="block">
	            <ul class="a-list-ul flex flex-pack-justify">
	                <li>幸福易隐私权政策</li>
	                <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
	            </ul>
	        </a>
            <a href="${(domainUrlUtil.urlResources)!}/privacyProtocolMember.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>幸福易平台服务协议（会员）</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a href="${(domainUrlUtil.urlResources)!}/privacyProtocolPromoters.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>幸福易平台协议（促销员)</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a href="${(domainUrlUtil.urlResources)!}/privacyProtocolChildren.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>儿童个人信息安全事件应急预案</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a href="${(domainUrlUtil.urlResources)!}/importantClause.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>特别声明</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>

	    </div>

        <div style="width: 100%;height:50rem;background-color: #ffffff;text-align: center;">
            <div style="height: 5rem;line-height: 5rem;padding: 0 10px;text-align: center;font-family: PingFangSC-Regular,PingFang SC;font-weight: 400;">扫描二维码，您的朋友也可以下载幸福易客户端</div>
            <div >
                <img src="https://www.baidu.com/img/bd_logo1.png" style="width: 125px;height: 125px;" alt="">
            </div>
        </div>


	</div>

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

</body>
</html>
