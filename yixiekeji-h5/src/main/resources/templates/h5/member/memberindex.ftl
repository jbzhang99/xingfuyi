<#include "/h5/commons/_head.ftl" />
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">我的幸福易</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

	 <!-- 主体开始 -->
	<div class="s-containerr cl70">

		<#--<div class="user-perinfo">-->
			<#--<div class="user-flex-grid"><a href="${(domainUrlUtil.urlResources)!''}/member/account.html" class="block"><span>账号管理 &nbsp;<i class="fa fa-cog" aria-hidden="true"></i></span></a></div>-->
	        <#--<div class="perinfor-1 flex">-->
	            <#--<div class="avatar"><img src="${(domainUrlUtil.staticResources)!}/img/avatar.png" alt=""></div>-->
	            <#--<div class="user-text flex-2">-->
	                <#--<p><label>用户名：</label><span>${(member.name)!}</span></p>-->
	                <#--<p><label>会员等级：</label><span><@cont.codetext value="${(member.grade)!0}" codeDiv="MEMBER_GRADE"/></span></p>-->
	                <#--<p><a href="${(domainUrlUtil.urlResources)!}/member/integral.html" class="badge-txt">我的积分${((member.integral))!}</a>-->
	                	<#--<a href="${(domainUrlUtil.urlResources)!}/member/grade.html" class="badge-txt">经验值${((member.gradeValue))!}</a></p>-->
	            <#--</div>-->
	        <#--</div>-->
	    <#--</div>-->

        <#--add by fanlei 2019-10-14 个人主页改造  start-->

        <div class="indexBg">
            <div style="width: 100%;height: 1rem;"></div>
            <div class="settingAndNews">
                <div class="settingAndNewsL">
                    <a href="${(domainUrlUtil.urlResources)!''}/member/account.html" class="block"><img src="${(domainUrlUtil.staticResources)!}/img/icon_shezhibaise@3x.png"></a>

                </div>
                <div class="settingAndNewsR">
                    <img src="${(domainUrlUtil.staticResources)!}/img/icon_xiaoxibaise@3x.png"">
                </div>
            </div>
            <div style="width: 100%;height: 1rem;clear: both;"></div>
            <#--头像 昵称 推广码 -->
            <div class="personalInformation">
                <div class="personalInformationL">
                    <img src="${(domainUrlUtil.staticResources)!}/img/touxiang@2x.png" alt="">
                </div>
                <div class="personalInformationR" style="line-height: 6.4rem;">
                    <div class="personalInformationRName">
                        <span class="personalInformationRNameL">
                            ${(member.nickName)!}
                        </span>
                        <#--<span class="personalInformationRNameR">-->
                            <#--<span class="realName">-->
                                <#--未实名认证-->
                            <#--</span>-->
                        <#--</span>-->
                    </div>
                    <#--<div class="personalInformationRExtension">-->
                        <#--<span class="personalInformationRExtensionL">-->
                            <#--TLa123-->
                        <#--</span>-->
                        <#--<span class="personalInformationRExtensionR">-->
                            <#--<span class="copy">-->
                                <#--复制推广码-->
                            <#--</span>-->
                        <#--</span>-->
                    <#--</div>-->
                </div>
            </div>
            <div style="width: 100%;height: 1rem;clear: both;"></div>
            <#--头像 昵称 推广码 -->
            <#--促销员-->
            <#--<div class="flex userbar" style="box-shadow: 0 0 0px #dedede;">-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!}/member/balance.html" class="block" style="color: #ffffff;">-->
                        <#--<span style="font-size: 2rem;">18</span><br><span>我的余额</span>-->
                    <#--</a>-->
                <#--</div>-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!''}/member/coupon/list.html" class="block" style="color: #ffffff;">-->
                        <#--<span style="font-size: 2rem;">27</span><br>优惠券-->
                    <#--</a>-->
                <#--</div>-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!}/member/integral.html" class="block" style="color: #ffffff;">-->
                        <#--<span style="font-size: 2rem;">12</span><br>积分-->
                    <#--</a>-->
                <#--</div>-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!}/member/applydrawing.html" class="block" style="color: #ffffff;">-->
                        <#--<span style="font-size: 2rem;">23</span><br>申请提现-->
                    <#--</a>-->
                <#--</div>-->

            <#--</div>-->
            <#--会员-->
            <div class="flex userbar" style="box-shadow: 0 0 0px #dedede;">
                <div class="flex-1">
                    <a href="${(domainUrlUtil.urlResources)!}/member/collect.html" class="block" style="color: #ffffff;">
                        <span style="font-size: 2rem;">${productCount}</span><br><span>商品收藏</span>
                    </a>
                </div>
                <div class="flex-1">
                    <a href="${(domainUrlUtil.urlResources)!}/member/collect.html?type=seller" class="block" style="color: #ffffff;">
                        <span style="font-size: 2rem;">${sellerCount}</span><br>店铺关注
                    </a>
                </div>
                <div class="flex-1">
                    <a href="${(domainUrlUtil.urlResources)!}/member/viewed.html" class="block" style="color: #ffffff;">
                        <span style="font-size: 2rem;">${browseCount}</span><br>浏览记录
                    </a>
                </div>
                <div class="flex-1">
                    <a href="${(domainUrlUtil.urlResources)!}/member/grade.html" class="block" style="color: #ffffff;">
                        <span style="font-size: 2rem;">${((member.gradeValue))!}</span><br>经验值
                    </a>
                </div>

            </div>


        </div>
        <#--佣金账户-->
        <#--<div class="commission">-->
            <#--<div class="commissionOne">-->
                <#--<div class="commissionOneL">佣金账户总览</div> <div class="commissionOneR"><span class="fa fa-angle-right"></span></div>-->
            <#--</div>-->
            <#--<div class="commissionTwo">-->
                <#--<div class="commissionTwoL">预计收入</div> <div class="commissionTwoR">可提现</div>-->
            <#--</div>-->
            <#--<div class="commissionThree">-->
                <#--<div class="commissionThreeL">￥7788</div> <div class="commissionThreeR">+ 6666</div>-->
            <#--</div>-->
        <#--</div>-->


        <#--add by fanlei 2019-10-14 个人主页改造  end-->
        <div class="user-items" style="margin-top: -44px;">
	    <#--<div class="user-items">-->
        <h4><a href="${(domainUrlUtil.urlResources)!''}/member/order.html" class="block">我的订单 <span class="item-title-tip">查看全部订单 ></span></a></h4>
        <div class="flex userbar">
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!''}/member/order.html?orderState=1" class="block">-->
                    <#--<i class="fa fa-jpy" aria-hidden="true"></i><br>待付款-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!''}/member/order.html?orderState=4" class="block">-->
                    <#--<i class="fa fa-folder" aria-hidden="true"></i><br>待收货-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!''}/member/order.html?orderState=6" class="block">-->
                    <#--<i class="fa fa-bullseye" aria-hidden="true"></i><br>已完成-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/back.html" class="block">-->
                    <#--<i class="fa fa-truck" aria-hidden="true"></i><br>退货／换货-->
                <#--</a>-->
            <#--</div>-->
            <#--add by fanlei 2019-10-17 我的页面改造  start-->
            <div class="flex-1">
                <a href="${(domainUrlUtil.urlResources)!''}/member/order.html?orderState=1"  style="position: relative" class="block">
                    <img src="${(domainUrlUtil.staticResources)!}/img/icon_daifukuan@2x.png" style="width: 2.3rem;height: 2.1rem;"> <br>待付款
                    <#if toBepaidOrders gt 0 && toBepaidOrders  lte 99>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">${toBepaidOrders}</span>
                    <#elseif toBepaidOrders gt 99>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">99+</span>
                    <#else>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;display: none;"></span>
                    </#if>
                </a>
            </div>
            <div class="flex-1">
                <a href="${(domainUrlUtil.urlResources)!''}/member/order.html?orderState=4"  style="position: relative" class="block">
                    <img src="${(domainUrlUtil.staticResources)!}/img/icon_daifahuo@2x.png" style="width: 2.2rem;height: 2.2rem;"> <br>待发货
                    <#if toBeSubstituteOrders gt 0 && toBeSubstituteOrders  lte 99>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">${toBeSubstituteOrders}</span>
                    <#elseif toBeSubstituteOrders gt 99>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">99+</span>
                    <#else>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;display: none"></span>
                    </#if>
                </a>
            </div>
            <div class="flex-1">
                <a href="${(domainUrlUtil.urlResources)!''}/member/order.html?orderState=6"  style="position: relative" class="block">
                    <img src="${(domainUrlUtil.staticResources)!}/img/icon_daishouhuo@2x.png" style="width: 2.5rem;height: 2.3rem;"> <br>待收货
                    <#if toBeReceivedOrders gt 0 && toBeReceivedOrders  lte 99>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">${toBeReceivedOrders}</span>
                    <#elseif toBeReceivedOrders gt 99>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">99+</span>
                    <#else>
                        <span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 45px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;display: none"></span>
                    </#if>
                </a>
            </div>
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/back.html"  style="position: relative" class="block">-->
                    <#--<img src="${(domainUrlUtil.staticResources)!}/img/icon_daipingjia@2x.png" style="width: 2.3rem;height: 2.1rem;"> <br>待评价-->
                    <#--<span style="position: absolute; border: 1px solid rgba(241,92,95,1); top: -10px;left: 40px; border-radius: 30px;color: #F15C5F; padding: 1px 2px; height: 18px;text-align: center; width: 22px; font-family: PingFangSC-Medium,PingFangSC; font-weight: 500; font-size: 10px;">9</span>-->
                <#--</a>-->
            <#--</div>-->
            <div class="flex-1">
                <a href="${(domainUrlUtil.urlResources)!}/member/back.html"  style="position: relative" class="block">
                    <img src="${(domainUrlUtil.staticResources)!}/img/icon_daihuanhuo@2x.png" style="width: 2.6rem;height: 2.3rem;"> <br>退换货
                    <#--<span style="position: absolute;border: 1px solid rgba(241,92,95,1);top: -10px;left: 42px;border-radius: 30px;color: #F15C5F;padding: 1px;font-family:PingFangSC-Medium,PingFangSC;font-weight:500;font-size:10px;">99+</span>-->
                </a>
            </div>
            <#--add by fanlei 2019-10-17 我的页面改造  end-->
        </div>

        <div class="">

        </div>

    </div>

    <#--add by fanlei 2019-10-17 个人页面改造  start-->
            <#--促销员-->
        <#--<div class="user-items">-->
            <#--<h4>我的钱包</h4>-->
            <#--<div class="flex userbar">-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!}/member/balance.html" class="block" style="font-family:PingFangSC-Medium,PingFangSC;font-weight:500;color:rgba(46,45,45,1);">-->
                        <#--<span style="font-size: 2rem;">0.00</span><br><span>我的钱包</span>-->
                    <#--</a>-->
                <#--</div>-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!''}/member/balance.html" class="block" style="font-family:PingFangSC-Medium,PingFangSC;font-weight:500;color:rgba(46,45,45,1);">-->
                        <#--<span style="font-size: 2rem;">125</span><br>余额-->
                    <#--</a>-->
                <#--</div>-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!}/member/integral.html" class="block" style="font-family:PingFangSC-Medium,PingFangSC;font-weight:500;color:rgba(46,45,45,1);">-->
                        <#--<span style="font-size: 2rem;">109</span><br>积分-->
                    <#--</a>-->
                <#--</div>-->
                <#--<div class="flex-1">-->
                    <#--<a href="${(domainUrlUtil.urlResources)!}/member/applydrawing.html" class="block" style="font-family:PingFangSC-Medium,PingFangSC;font-weight:500;color:rgba(46,45,45,1);">-->
                        <#--<span style="font-size: 2rem;">6049</span><br>佣金-->
                    <#--</a>-->
                <#--</div>-->

            <#--</div>-->
        <#--</div>-->
         <#--会员-->
        <div class="user-items">
            <h4>我的钱包</h4>
            <div class="flex userbar">
                <div class="flex-1">
                    <a href="${(domainUrlUtil.urlResources)!''}/member/coupon/list.html" class="block" style="font-family:PingFangSC-Medium,PingFangSC;font-weight:500;color:rgba(46,45,45,1);">
                        <span style="font-size: 2rem;">${couponCount}</span><br>优惠券
                    </a>
                </div>
                <div class="flex-1">
                    <a href="${(domainUrlUtil.urlResources)!}/member/integral.html" class="block" style="font-family:PingFangSC-Medium,PingFangSC;font-weight:500;color:rgba(46,45,45,1);">
                        <span style="font-size: 2rem;">${(member.integral)!}</span><br>积分
                    </a>
                </div>
                <div class="flex-2">
                    &nbsp;
                </div>
            </div>
        </div>

    <#--<div class="user-items">-->
        <#--<h4>我的服务 </h4>-->
        <#--<div class="flex userbar">-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/collect.html" class="block">-->
                    <#--<i class="fa fa-gratipay" aria-hidden="true" style="color: #FD857A;"></i><br>商品收藏-->

                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/collect.html?type=seller" class="block">-->
                    <#--<i class="fa fa-university" aria-hidden="true" style="color: #FFBB76;"></i><br>店铺收藏-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/viewed.html" class="block">-->
                    <#--<i class="fa fa-skyatlas" aria-hidden="true" style="color: #88B5F5;"></i></i><br>浏览记录-->
                <#--</a>-->
            <#--</div>-->
        <#--</div>-->
    <#--</div>-->

    <#--<div class="user-items">-->
        <#--<h4>我的资产 </h4>-->
        <#--<div class="flex userbar">-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/balance.html" class="block">-->
                    <#--<i class="fa fa-envelope-o" aria-hidden="true" style="color: #e4393c;"></i><br>我的余额-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!''}/member/coupon/list.html" class="block">-->
                    <#--<i class="fa fa-folder" aria-hidden="true" style="color: #5cb85c;"></i><br>优惠券-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/integral.html" class="block">-->
                    <#--<i class="fa fa-database" aria-hidden="true" style="color:#FFCF00;"></i><br>积分-->
                <#--</a>-->
            <#--</div>-->
            <#--<div class="flex-1">-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/applydrawing.html" class="block">-->
                    <#--<i class="fa fa-money" aria-hidden="true" style="color: #E83B3D;"></i><br>申请提现-->
                <#--</a>-->
            <#--</div>-->

        <#--</div>-->
    <#--</div>-->

    <#--<div class="user-items">-->
        <#--<h4>分销中心 </h4>-->
        <#--<div class="flex userbar add-flex-sty">-->
            <#--<div>-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/sale-index.html" class="block">-->
                    <#--<i class="fa fa-external-link-square" aria-hidden="true"></i><br>我的分销-->
                <#--</a>-->
            <#--</div>-->

            <#--<div>-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/sale-finance-info.html" class="block">-->
                    <#--<i class="fa fa-cubes" aria-hidden="true"></i><br>财务信息-->
                <#--</a>-->
            <#--</div>-->
            <#--<div>-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/sale-apply-money.html" class="block">-->
                    <#--<i class="fa fa-credit-card" aria-hidden="true"></i><br>结算中心-->
                <#--</a>-->
            <#--</div>-->
            <#--<div>-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/sale-orders.html" class="block">-->
                    <#--<i class="fa fa-line-chart" aria-hidden="true" style="color: #bb4b86;"></i><br>收入流水-->
                <#--</a>-->
            <#--</div>-->
            <#--<div>-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/sale-member1.html" class="block">-->
                    <#--<i class="fa fa-odnoklassniki" aria-hidden="true"></i><br>推广用户-->
                <#--</a>-->
            <#--</div>-->
            <#--<div>-->
                <#--<a href="${(domainUrlUtil.urlResources)!}/member/sale-member2.html" class="block">-->
                    <#--<i class="fa fa-odnoklassniki-square" aria-hidden="true"></i><br>间接推广用户-->
                <#--</a>-->
            <#--</div>-->
        <#--</div>-->
    <#--</div>-->
        <#--add by fanlei 2019-10-17 个人页面改造  end-->
	    <div>
	        <h2 class="home-title"><span></span>
	            <p>为你推荐</p><span></span>
	        </h2>

	        <div class="i-list-box mar-bt">
	            <ul class="i-list-ul clear">
		         <#if products??>
		        	<#list products as product>
		            <li>
		                <a href="${(domainUrlUtil.urlResources)!''}/product/${(product.id)!0}.html" class="block">
		                    <div class="i-list-img">
		                    	<img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!''}${(product.masterImg)!''}" width="144" height="144">
	                        </div>
		                    <div class="product_name">${(product.name1)!""}</div>
		                </a>
		                <div class="list-goods-tip">
		                    <#if product.sellerId == 1><span class="icon-group-1">自营</span></#if>
		                    <#if product.coupon == 1><span class="icon-group-4">优惠券</span></#if>
		                    <#if product.special == 1><span class="icon-group-3">特卖</span></#if>
		                </div>
		                <div class="i-index-price">
		                	￥${(product.malMobilePrice)?string("0.00")!"0.00"}
		                	<#if product.single == 1><span class="icon-group-2">立减</span></#if>
		                	<#if product.full == 1><span class="icon-group-2">满减</span></#if>
		                </div>
		            </li>
		            </#list>
	            </#if>
	            </ul>
	        </div>
	    </div>

	</div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
   <script type="text/javascript">
       setData("memberId",(${(member.id)!0}))
   </script>
</body>
</html>
