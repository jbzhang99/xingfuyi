<#include "/admin/commons/_detailheader.ftl" />

<script language="javascript">
$(function(){
	$("#back").click(function(){
	 		window.location.href="${domainUrlUtil.urlResources}/admin/sale/salescale";
		});
	$("#add").click(function(){
			if($("#addForm").form('validate')){
		 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/admin/sale/salescale/update")
	  				 .attr("method", "POST")
	  				 .submit();
	  		}
		});
	<#if message??>$.messager.progress('close');alert('${message}');</#if>
})
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">设置商家三级分销<span class="s-poar"><a class="a-back" href="${domainUrlUtil.urlResources}/admin/sale/salescale">返回</a></span></h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm" name="addForm">
			<input type="hidden" name="id" value="${(seller.id)!''}" />
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red"></font>店铺名称: </label>
							${(seller.sellerName)!''}
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>一级分销比例: </label>
							<input type="text" id="saleScale1" name="saleScale1" value="${(seller.saleScale1)!''}" 
								class="txt w200 easyui-numberbox" missingMessage="输入0到1之间的小数，精确到小数点两位" 
								data-options="min:0,max:1,precision:2,required:true"/>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>二级分销比例: </label>
							<input type="text" id="saleScale2" name="saleScale2" 
							value="${(seller.saleScale2)!''}" class="txt w200 easyui-numberbox" 
							missingMessage="输入0到1之间的小数，精确到小数点两位" data-options="min:0,max:1,precision:2,required:true"/>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>是否启用: </label>
							<@cont.radio id="state" value="${(seller.state)!''}" codeDiv="USE_YN" />
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
				<input type="button" id="add" class="btn" value="提交"/>
				<input type="button" id="back" class="btn" value="返回"/>
			</p>
			</@form.form>
		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />