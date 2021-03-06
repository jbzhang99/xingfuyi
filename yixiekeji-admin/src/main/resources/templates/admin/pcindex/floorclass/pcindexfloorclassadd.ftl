<#include "/admin/commons/_detailheader.ftl" />
<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<script type="text/javascript" src="${domainUrlUtil.staticResources}/resources/admin/jslib/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">

$(function(){


	$("#back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/admin/pcindex/floorclass";
	});
	$("#add").click(function(){
		var floorId = $("#floorId").val();
    	if (floorId == null || floorId == "") {
    		$.messager.alert('提示','请选择楼层');
    		return false;
    	}
		
		if($("#addForm").form('validate')){
	 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/admin/pcindex/floorclass/create")
  				 .attr("method", "POST")
  				 .submit();
  		}
	});

	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
})

</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">添加楼层分类<span class="s-poar"><a class="a-back" href="${domainUrlUtil.urlResources}/admin/pcindex/floorclass">返回</a></span></h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm" name="addForm" enctype="multipart/form-data">
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>楼层：</label>
							<select name="floorId" id="floorId" value="${(pcIndexFloorClass.floorId)!''}" data-options="required:true">
		                    	<option value="">--请选择--</option>
		                        <#if floors?? && floors?size &gt; 0>
		                        	<#list floors as floor>
										<option value="${(floor.id)!}" <#if pcIndexFloorClass?? && pcIndexFloorClass.floorId?? && pcIndexFloorClass.floorId == floor.id>selected="true"</#if>>${(floor.name)!}</option>
									</#list>
								</#if>
						    </select>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>名称：</label>
							<input class="easyui-validatebox txt w280" type="text" id="name" name="name" value="${(pcIndexFloorClass.name)!''}" data-options="required:true,validType:'length[0,20]'" >
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>排序号：</label>
							<input class="easyui-numberbox w280" type="text" id="orderNo" name="orderNo" value="${(pcIndexFloorClass.orderNo)!''}" data-options="required:true,max:99" >
						</p>
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label>
							<label>
								<font style="color: #808080">
								序号越小越靠前显示
								</font>
							</label>
						</p>
					</div>
					<br/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item">备注：</label>
							<textarea name="remark" rows="4" cols="60" id="remark" class="{maxlength:100}" >${(pcIndexFloorClass.remark)!''}</textarea>
						</p>
					</div>
					<br/>
				</dd>
			</dl>

			<#--2.batch button-------------->
			<p class="p-item p-btn">
				<input type="button" id="add" class="btn" value="提交" />
				<input type="button" id="back" class="btn" value="返回"/>
			</p>
			</@form.form>
		</div>
	</div>
</div>



<#include "/admin/commons/_detailfooter.ftl" />