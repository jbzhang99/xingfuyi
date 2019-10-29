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
   	  	 <div class="flex-2 text-center">设置</div>
   	  	 <div class="flex-1 text-right"></div>
   	  </div>
   	  <#--add by fanlei 2019-10-17 我的页面改造 start-->
   	  <#--<div class="flex flex-align-center head-bar">-->
   	  	 <#--<div class="flex-1 text-left">-->
   	  	 	<#--<a href="javascript:ejsPageBack();">-->
   	  	 		<#--<span class="fa fa-angle-left"></span>-->
   	  	 	<#--</a>-->
   	  	 <#--</div>-->
   	  	 <#--<div class="flex-2 text-center">我的幸福易</div>-->
   	  	 <#--<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>-->
   	  <#--</div>-->
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

	<div class="s-containerr">
        <div class="account-list add-account-list">
	        <a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="block">
	            <ul class="a-list-ul flex flex-pack-justify">
	                <li>个人资料</li>
	                <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
	            </ul>
	        </a>
	        <a href="${(domainUrlUtil.urlResources)!}/member/editpassword.html" class="block">
	            <ul class="a-list-ul flex flex-pack-justify">
	                <li>修改密码</li>
	                <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
	            </ul>
	        </a>
            <a href="${(domainUrlUtil.urlResources)!}/member/address.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>收货地址管理</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
	    </div>

        <div class="account-list add-account-list">
            <a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>客服</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a href="${(domainUrlUtil.urlResources)!}/member/aboutXfy.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>关于幸福易</li>
                    <li><i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
        </div>
        <div style="width: 100%;height:50rem;background-color: #ffffff;">
            <div style="height: 5rem;"></div>
            <div style="width: 80%; height: 5rem;line-height:5rem; text-align: center;margin-left: 10%;background-color: #f2f2f2;border-radius:3rem;">
                <a href="${(domainUrlUtil.urlResources)!''}/logout.html" style="color: #ffffff;">
                    退出登录
                </a>
            </div>
        </div>










	    <#--<div class="account-list">-->
	        <#--<h4>管理我的账号</h4>-->
	        <#--<div class="a-user-info">-->
	            <#--<div class="flex">-->
	                <#--<div class="avatar"><img src="${(domainUrlUtil.staticResources)!}/img/avatar.png" alt=""></div>-->
	                <#--<div class="a-user-text">-->
	                    <#--<p><label>用户名：</label><span>${(member.name)!}</span></p>-->
	                    <#--<p><label>会员等级：</label><span><@cont.codetext value="${(member.grade)!0}" codeDiv="MEMBER_GRADE"/></span></p>-->
	                <#--</div>-->
	            <#--</div>-->
	            <#--<span class="a-login-status">当前登陆</span>-->
	        <#--</div>-->
	    <#--</div>-->

	    <#--<div class="account-list">-->
	        <#--<a href="${(domainUrlUtil.urlResources)!}/member/address.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>收货地址管理</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	    <#--</div>-->

	    <#--<div class="account-list add-account-list">-->
	        <#--<a href="${(domainUrlUtil.urlResources)!}/member/info.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>个人资料</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	        <#--<a href="${(domainUrlUtil.urlResources)!}/member/editpassword.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>修改密码</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	    <#--</div>-->

	    <#--<div class="account-list add-account-list">-->
	        <#--<a href="${(domainUrlUtil.urlResources)!''}/member/question.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>我的咨询</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	        <#--<a href="${(domainUrlUtil.urlResources)!''}/member/comment.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>我的评价</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	        <#--<a href="${(domainUrlUtil.urlResources)!}/member/grade.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>我的经验值</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	    <#--</div>-->

	    <#--<div class="account-list add-account-list">-->
	    	<#--<a href="${(domainUrlUtil.urlResources)!}/member/back.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>我的退货</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	    	<#--<a href="${(domainUrlUtil.urlResources)!}/member/exchange.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>我的换货</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	    	<#--<a href="${(domainUrlUtil.urlResources)!}/member/complaint.html" class="block">-->
	            <#--<ul class="a-list-ul flex flex-pack-justify">-->
	                <#--<li>我的申诉</li>-->
	                <#--<li><i class="fa fa-angle-right" aria-hidden="true"></i></li>-->
	            <#--</ul>-->
	        <#--</a>-->
	    <#--</div>-->

	</div>



	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

</body>
</html>
