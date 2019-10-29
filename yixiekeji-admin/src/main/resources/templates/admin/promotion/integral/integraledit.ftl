<#include "/admin/commons/_detailheader.ftl" />
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/admin/jslib/My97DatePicker/WdatePicker.js"></script>
<link href="${domainUrlUtil.staticResources}/resources/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>

<script language="javascript">
$(function(){
	$("#back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/admin/promotion/actintegral";
	});
	$("#add").click(function(){
		if($("#addForm").form('validate')){
	 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/admin/promotion/actintegral/update")
  				 .attr("method", "POST")
  				 .submit();
  		}
	});
	
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
})

function delMethod(obj) {
	obj.parentNode.remove();
}

function productCallBack(data){
    $('#productId').val('');
    $('#productName').html("");
    if(data && data.length > 0){
        var productName = '';
        var productId = '';
        $.each(data, function(j, n){
            productId = n.id;
            productName = n.name1;
        })
         $('#productId').val(productId);
         $('#productName').html(productName);
    }
}
    
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">修改积分商城<span class="s-poar"><a class="a-back" href="${domainUrlUtil.urlResources}/admin/promotion/actintegral">返回</a></span></h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm" name="addForm" enctype="multipart/form-data">
			
			<input type="hidden" id="id" name="id" value="${(actIntegral.id)!}"/>
			
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font><a target="_blank" href="${(domainUrlUtil.frontUrl)!}/product/${(actIntegral.productId)!}.html">商品(点击查看)：</a></label>
							<a target="_blank" href="${(domainUrlUtil.frontUrl)!}/product/${(actIntegral.productId)!}.html">${(actIntegral.productName)!}</a>
						</p>
					</div>
					<br/>
					
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
							<label class="lab-item"><font class="red">*</font>促销标题：</label>
							${(actIntegral.name)!}
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
                            <label class="lab-item"><font class="red">*</font>权重: </label>
                            <input type="text" id="sortNum" name="sortNum" value="${(actIntegral.sortNum)!''}" class="txt w200 easyui-numberbox" data-options="min:0,max:200,required:true"/>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                权重越大，排序越靠前。
                            </font>
                        </p>
                    </div>
                    <br/>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>虚拟销量: </label>
                            <input type="text" id="virtualSaleNum" name="virtualSaleNum" value="${(actIntegral.virtualSaleNum)!''}" class="txt w200 easyui-numberbox" data-options="min:0,max:9999,required:true"/>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                一旦设置之后，用户可以看到的销量是 实际销量+虚拟销量。
                            </font>
                        </p>
                    </div>
                    <br/>
                    
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>是否推荐: </label>
                            <@cont.radio id="isBest" value="${(actIntegral.isBest)!''}" codeDiv="YES_NO" />
                        </p>
                    </div>
                    <br/>
					
					<br/>
				</dd>
			</dl>

			<#--2.batch button-------------->
			<p class="p-item p-btn">
				<input type="button" id="add" class="btn" value="修改" />
				<input type="button" id="back" class="btn" value="返回"/>
			</p>
			</@form.form>
		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />