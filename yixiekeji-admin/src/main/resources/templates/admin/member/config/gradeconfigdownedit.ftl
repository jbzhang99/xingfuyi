<#include "/admin/commons/_detailheader.ftl" />

<script language="javascript">
$(function(){
	// 会员等级配置操作界面确定按钮
	$("#edit").click(function(){
		if($("#addForm").form('validate')){
			$.ajax({
				type:"POST",
				url: "${domainUrlUtil.urlResources}/admin/member/config/gradedown/update",
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
		<h2 class="h2-title">会员年度经验值递减配置</h2>
		
		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm" name="addForm">
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
				<dd class="dd-group">
					<input type="hidden" id="id" name="id" value="${(memberGradeConfig.id)!''}" data-options="required:true"/>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>注册会员年度减少值: </label>
							<input class="easyui-numberbox" id="grade1" name="grade1" value="${(memberGradeConfig.grade1)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>铜牌会员年度减少值: </label>
							<input class="easyui-numberbox" id="grade2" name="grade2" value="${(memberGradeConfig.grade2)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>银牌会员年度减少值: </label>
							<input class="easyui-numberbox" id="grade3" name="grade3" value="${(memberGradeConfig.grade3)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>金牌会员年度减少值: </label>
							<input class="easyui-numberbox" id="grade4" name="grade4" value="${(memberGradeConfig.grade4)!''}" data-options="min:0,required:true">
						</p>
					</div>
					</br>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>钻石会员年度减少值: </label>
							<input class="easyui-numberbox" id="grade5" name="grade5" value="${(memberGradeConfig.grade5)!''}" data-options="min:0,required:true">
						</p>
					</div>
				</dd>
			</dl>
			
			<dl class="dl-group">
				<dt class="dt-group"><span class="s-icon"></span>帮助</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<label>年度减少经验值：会员注册日起，每年会减少会员的累计经验值，从而影响会员的等级。</label>
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