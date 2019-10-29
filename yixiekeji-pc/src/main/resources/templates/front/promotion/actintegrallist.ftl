<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/integral.css">

<!-- S 主体 -->
<div class="main-bg">   
    <div class="container">
        <div class="pd6-box clearfix">
            <div class="vip-countlf fl">
                <div class="vip-info fl">
                    <div class="fl bradiu" >
                        <a href="javascript:void(0);">
                            <img src="${(domainUrlUtil.staticResources)!}/img/vip_face_default.jpg" alt="" width="110" height="110">
                        </a>
                    </div>
                    <div class="vip-lvdetail fl">
                    	<#if member??>
	                        <div>
	                            <div class="vip-name" title="${(member.name)!}">${(member.name)!}</div>
	                            <div class="yellow">
	                            	<@cont.codetext value="${(member.grade)!0}" codeDiv="MEMBER_GRADE"/>
	                            </div>
	                            <div class="yellow">
	                            	<#if gradeValue?? && gradeValue != 0>
		                                距离下次升级还差&nbsp;<b>${gradeValue!}</b>&nbsp;个经验值
	                            	<#else>
	                            		您现在已经是最高等级
	                                </#if>
	                            </div> 
	                        </div>
	                    <#else>
                        	<a href="javascript:;" onclick="login();" class="vip_loginbtn">登　录</a>
                        </#if>
                    </div>
                </div>
                <div class="vip-signbox fl">
                    <span>签到送积分</span>
                    <span>每天限领一次</span>
                    <#if isSign?? && isSign == 0>
	                    <!-- 签到前 -->
                   		<a href="javascript:;" id="memberSignBefore" class="vip-signbtn">今日可领${(memberRule.sign)!0}分</a>
	                    <span class="vip_signedbtn" id="memberSignAfter" style="display:none;">今日已领取${(memberRule.sign)!0}积分</span>
               		<#else>
	                    <!-- 签到后 -->
	                    <span class="vip_signedbtn">今日已领取${(memberRule.sign)!0}积分</span>
                    </#if>
                </div>
                <div class="vip-pointbox fl">
                    <span class="mrg15 yellow">可用积分：<#if member??>${(member.integral)!0}<#else>0</#if></span>
                    <div class="mrg15">
                        <a href="${(domainUrlUtil.urlResources)!}/member/integral.html" target="_blank" class="a_blue a-jl">积分中心 > </a>
                        <a href="${(domainUrlUtil.urlResources)!}/news/2.html" target="_blank" class="a_blue">如何获取 ？</a>
                    </div>
                </div>
            </div>
            <div class="vip-slide fl">
                <!-- 轮播图区域 start -->
                <div class="lunbo-container">
                    <div class="hd"><!-- 底部小圆点 -->
                        <ul class="">
                    	<#if actIntegralBanners??>
	        				<#list actIntegralBanners as actIntegralBanner>
                            <li class="bullet"></li>
                            </#list>
                		</#if>
                        </ul>
                        <div class="bullet-bg"></div>
                    </div>
                    <ul class="bd"><!-- 轮播图片 -->
                    <#if actIntegralBanners??>
	        			<#list actIntegralBanners as actIntegralBanner>
                        <li><a href="${(domainUrlUtil.urlResources)!}/${(actIntegralBanner.linkUrl)!}" target="_blank"><img src="${(domainUrlUtil.imageResources)!}${(actIntegralBanner.image)!}" class="img100"/></a></li>
                      </#list>
	                </#if>
                    </ul>
                </div>
                <!-- 轮播图区域 end -->
            </div>
        </div>
        <!-- 筛选 -->
        <div class="sea-box">
            <div class="tabsContent">
                <div class="choose-box-top">
                    <div class="choose-libox clearfix">
                        <div class="choose-label ellipsis fl">商品分类　<em>></em></div>
                        <div class="choose_tagbox fl">
                            <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=0&grade=${(grade)!1}&sort=${(sort)!0}" class="line_cell floatleft"><span  <#if type=0>class="chsall"</#if>>全部</span></a>
                            <#if actIntegralTypes??>
        						<#list actIntegralTypes as actIntegralType>
                            		<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(actIntegralType.id)!0}&grade=${(grade)!1}&sort=${(sort)!0}" class="line_cell floatleft"><span <#if actIntegralType.id=type>class="chsall"</#if>>${(actIntegralType.name)!""}</span></a>
                            	</#list>
                			</#if>
                        </div>
                    </div>
                    <div class="choose-libox clearfix">
                        <div class="choose-label ellipsis fl">会员等级　<em>></em></div>
                        <div class="choose_tagbox fl">
                            <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=1&sort=${(sort)!0}" class="line_cell floatleft"><span <#if grade=1>class="chsall"</#if>>普通会员</span></a>
                            <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=2&sort=${(sort)!0}" class="line_cell floatleft"><span <#if grade=2>class="chsall"</#if>>铜牌会员</span></a>
                            <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=3&sort=${(sort)!0}" class="line_cell floatleft"><span <#if grade=3>class="chsall"</#if>>银牌会员</span></a>
                            <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=4&sort=${(sort)!0}" class="line_cell floatleft"><span <#if grade=4>class="chsall"</#if>>金牌会员</span></a>
                            <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=5&sort=${(sort)!0}" class="line_cell floatleft"><span <#if grade=5>class="chsall"</#if>>钻石会员</span></a>
                        </div>
                    </div>
                </div>
                <div class="choose_box_bottom clearfix">
                    <div class="fl fl-bop">
                        <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=0" class="choose_b_btn fl <#if sort=0>myselect</#if>">人　气</a>
                        <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=1" class="choose_b_btn fl <#if sort=1>myselect</#if>">最　新</a>
                        <a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=2" class="choose_b_btn fl <#if sort=2>myselect</#if>">销　量</a>
                        <#if sort=3>
                        	<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=4" class="choose_b_btn fl myselect btn-price">价　格
                        	<img src="${(domainUrlUtil.staticResources)!}/img/jt.png" width="12" height="16">
                            </a>
                        <#elseif sort = 4>
                        	<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=3" class="choose_b_btn fl myselect btn-price">价　格
                        	<img src="${(domainUrlUtil.staticResources)!}/img/jtx.png" width="12" height="16">
                        	</a>
                        <#else>
                        	<a href="${(domainUrlUtil.urlResources)!}/jifen.html?type=${(type)!0}&grade=${(grade)!1}&sort=3" class="choose_b_btn fl">价　格</a>
                        </#if>
                    </div>
                    <div class="fr lh30">
                        <div class="fl bd">共
                            <span class="bd-span">${(page.rowCount)}</span>个商品
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 商品列表 -->
        <div class="good_box vip_goodbox">
            <ul class="clearfix">
            <#if actIntegrals??>
      			<#list actIntegrals as actIntegral>
                <li class="list_pergood">
                    <div class="list_item_border">
                    	<a href="${(domainUrlUtil.urlResources)!}/jifen/${(actIntegral.id)!0}.html" target="_blank">
	                        <div class="list_img"><img class="lazy" data-original="${(domainUrlUtil.imageResources)!}${(actIntegral.image)!}" width="260" height="260">
	                        </div>
                        </a>
                        <div class="list_acinfo ellipsis">
	                        <span class="em-sty"><@cont.codetext value="${(actIntegral.gradeValue)!0}" codeDiv="MEMBER_GRADE"/></span>
	                        <#if actIntegral.sellerId==1><em class="em-sty-pd fr">商城自营</em></#if>
                        </div>
                        <div class="list_atit ellipsis">
                            <a href="${(domainUrlUtil.urlResources)!}/jifen/${(actIntegral.id)!0}.html" target="_blank"  title="${(actIntegral.productName)!''}">
                            	${(actIntegral.productName)!}
                            </a>
                        </div>
                        <div class="list_price">
                        	<#if actIntegral.isWithMoney == 0>
                        		<span>${(actIntegral.price)!}分</span>
                        	<#elseif actIntegral.isWithMoney == 1>	
                           		<span>${(actIntegral.price)!}分+￥${(actIntegral.priceMoney)!}</span>
                           	</#if>
                            <del>原价：￥${(actIntegral.marketPrice)?string('0.00')!}</del>
                        </div>
                    </div>
                </li>
                </#list>
      		</#if> 
            </ul>
        </div>
         <#if page?? && page.pageCount gt 1>
		      	<#include "/front/commons/_pagination.ftl" />
	     </#if>
    </div>
</div>
<!-- E 主体 -->
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/list.js'></script>
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.SuperSlide.2.1.1.js"></script>

<script type="text/javascript">
function login() {
	showid('ui-dialog');
}
	
$(function(){
    // 轮播图
    if( $('.vip-slide .lunbo-container').length>0 ){
        $(".vip-slide .lunbo-container").slide({mainCell:".bd",autoPlay:true,pnLoop:true,effect:"left",delayTime:"400"});
    }  
    
    // 商品列表
    $(".list_item_border").on("mouseenter",function(){
        $(this).css({borderColor:"#BB232A"});
    });
    $(".list_item_border").on("mouseleave",function(){
        $(this).css({borderColor:"#fff"});
    });

    // 筛选
    $(".choose_tagbox a span").on("click",function(){
        $(this).addClass("chsall").parent().siblings().find("span").removeClass("chsall");
    });

    $(".choose_box_bottom .fl-bop a").on("click",function(){
        $(this).addClass("myselect").siblings().removeClass("myselect");
    });
    
    $("#memberSignBefore").click(function(){
    	// 未登录不能领取积分
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
   		$.ajax({
			type:"POST",
			url:domain+"/member/sign.html",
			dataType:"json",
			async : false,
			success:function(data){
				if(data.success){
					jAlert("领取成功，明天记得来签到哟！");
					$("#memberSignBefore").css("display","none");
   					$("#memberSignAfter").css("display","block");
				}else{
					jAlert(data.message);
				}
			},
			error:function(){
				jAlert("异常，请重试！");
				$(".ahover").removeAttr("disabled");
			}
		});
    });

 });
</script>
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />