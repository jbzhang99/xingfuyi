<#include "/admin/commons/_detailheader.ftl" />
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script language="javascript">
$(function(){
	$("#back").click(function(){
	 		window.location.href="${domainUrlUtil.urlResources}/admin/promotion/integraltype";
		});
	$("#add").click(function(){
			if($("#addForm").form('validate')){
		 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/admin/promotion/integraltype/update")
	  				 .attr("method", "POST")
	  				 .submit();
	  		}
		});
	<#if message??>$.messager.progress('close');alert('${message}');</#if>
})
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">积分商城分类修改<span class="s-poar"><a class="a-back" href="${domainUrlUtil.urlResources}/admin/promotion/integraltype">返回</a></span></h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm" name="addForm">
			
			<input type="hidden" name="id" value="${(actIntegralType.id)!''}" />
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>积分商城分类名称：</label>
							<input class="easyui-validatebox txt w280" type="text" id="name" name="name" value="${(actIntegralType.name)!''}" missingMessage="积分商城分类名称必须填写，输入1到4个字符" data-options="required:true,validType:'length[1,4]'" >
						</p>
					</div>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                积分商城分类名称必须填写，输入1到4个字符
                            </font>
                        </p>
                    </div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>排序：</label>
							<input class="easyui-numberbox w280" type="text" id="sort" name="sort" value="${(actIntegralType.sort)!''}" missingMessage="排序必须填写，输入0到200之间的数字，数字越大顺序越靠前" data-options="min:0,max:200,required:true"" >
						</p>
					</div>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                排序必须填写，输入0到200之间的数字，数字越大顺序越靠前
                            </font>
                        </p>
                    </div>
				</dd>
			</dl>
			
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>帮助</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<label class="lab-item">帮助信息。</label>
					</div>
				</dd>
			</dl>
			
			<#--2.batch button-------------->
			<p class="p-item p-btn">
				<input type="button" id="add" class="btn" value="修改"/>
				<input type="button" id="back" class="btn" value="返回"/>
			</p>
			</@form.form>
		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />