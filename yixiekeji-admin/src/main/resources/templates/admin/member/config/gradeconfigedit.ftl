<#include "/admin/commons/_detailheader.ftl" />
<style>
	iframe .panel-fit, .panel-fit body {
	    overflow: scroll;
	}
</style>
<script language="javascript">
$(function(){
	// 会员等级配置操作界面确定按钮
	$("#edit").click(function(){
		if($("#addForm").form('validate')){
			$.ajax({
				type:"POST",
				url: "${domainUrlUtil.urlResources}/admin/member/config/grade/update",
				dataType: "json",
				data: $('#addForm').serialize(),
				cache:false,
				success:function(data, textStatus){
					if (data.success) {
						$.messager.alert('提示','修改成功。');
						return;
					} else {
						$.messager.alert("提示",data.message);
					}
				}
			});
		}
	});

	<#if message??>$.messager.progress('close');alert('${message}');</#if>
})
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">会员等级配置</h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm" name="addForm">
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					<input type="hidden" id="id" name="id" value="${(memberGradeConfig.id)!''}" data-options="required:true"/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>注册会员经验值: </label>
							<label style="float:left" id="lbl_balance_memberId">0</label>
							<input type="hidden" id="grade1" name="grade1" value="${(memberGradeConfig.grade1)!''}"/>
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>铜牌会员经验值: </label>
							<input class="easyui-numberbox" id="grade2" name="grade2" value="${(memberGradeConfig.grade2)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>银牌会员经验值: </label>
							<input class="easyui-numberbox" id="grade3" name="grade3" value="${(memberGradeConfig.grade3)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>金牌会员经验值: </label>
							<input class="easyui-numberbox" id="grade4" name="grade4" value="${(memberGradeConfig.grade4)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>钻石会员经验值: </label>
							<input class="easyui-numberbox" id="grade5" name="grade5" value="${(memberGradeConfig.grade5)!''}" data-options="min:0,required:true">
						</p>
					</div>
				</dd>
			</dl>
			
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>帮助</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<label>会员经验值：会员通过购物等途径获取经验值，根据会员的经验值设定会员的等级。如铜牌会员经验值设定为500，则当会员的经验值满500后，等级从注册会员提升为铜牌会员。</label>
					</div>
				</dd>
			</dl>
			
			<#--2.batch button-------------->
			<p class="p-item p-btn">
				<input type="button" id="edit" class="btn" value="确定"/>
			</p>
			</@form.form>
		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />