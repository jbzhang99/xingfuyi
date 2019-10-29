<#include "/admin/commons/_detailheader.ftl" />
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script language="javascript">
$(function(){
	$("#back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/admin/promotion/actintegral";
	});
})
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">积分商城查看<span class="s-poar"><a class="a-back" href="${domainUrlUtil.urlResources}/admin/promotion/actintegral">返回</a></span></h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>积分商城分类：</label>
							<select name="type" disabled="disabled">
								<#if actIntegralTypes ??>
									<#list actIntegralTypes as actIntegralType>
							  			<option value="${actIntegralType.id}" <#if actIntegralType.id==actIntegral.type>selected</#if> >${actIntegralType.name}</option>
							  		</#list>
							  	</#if>
							</select>
						</p>
					</div>
					<br/>
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>所属商家：</label>
							${(actIntegral.sellerName)!}
						</p>
					</div>
					<br/>
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font><a target="_blank" href="${(domainUrlUtil.frontUrl)!}/product/${(actIntegral.productId)!}.html">商品(点击查看)：</a></label>
							<a target="_blank" href="${(domainUrlUtil.frontUrl)!}/product/${(actIntegral.productId)!}.html">${(actIntegral.productName)!}</a>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>促销标题：</label>
							${(actIntegral.name)!}
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>渠道：</label>
							<@cont.select id="channel" name="channel" codeDiv="CHANNEL" value="${(actIntegral.channel)!}" mode="2" disabled="disabled"/>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>活动图片：</label>
							<#if (actIntegral.image)??> 
								&nbsp;&nbsp;&nbsp;&nbsp;<a href="${domainUrlUtil.imageResources}${(actIntegral.image)!''}" target="_blank">查看图片</a>
							</#if>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>市场价: </label>
                           ${(actIntegral.marketPrice)!''}
                        </p>
                    </div>
                    <br/>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>消耗积分: </label>
                            ${(actIntegral.price)!''}
                        </p>
                    </div>
                     <br/>
                     <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>是否有现金: </label>
                           <@cont.select id="isWithMoney" name="isWithMoney" codeDiv="IS_WITH_MONEY" value="${(actIntegral.isWithMoney)!}" mode="2" disabled="disabled"/>
                        </p>
                    </div>
                     <br/>
                     <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>现金价格: </label>
                            ${(actIntegral.priceMoney)!''}
                        </p>
                    </div>
                     <br/>
                    <div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>会员等级：</label>
							<@cont.select id="gradeValue" name="gradeValue" codeDiv="MEMBER_GRADE" value="${(actIntegral.gradeValue)!}" mode="2" disabled="disabled"/>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>库存：</label>
							${(actIntegral.stock)!''}
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<label class="lab-item"><font class="red">*</font>活动时间：</label>
						${(actIntegral.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}
						~
						${(actIntegral.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}
					</div>
					<br/>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>活动描述: </label>
                        <div style="padding-left: 140px;padding-top: 2px;">
                        	<#noescape>
                        	${(actIntegral.descinfo)!}
                        	</#noescape>
                        </div>
                        </p>
                    </div>
					<br/>
				</dd>
			</dl>

		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />