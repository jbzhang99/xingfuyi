<#include "/admin/commons/_detailheader.ftl" />
<style>
.panel-fit body.panel-noscroll {
	overflow-y: scroll;
}
</style>
<link rel="stylesheet"
	href="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/jquery.boxer/css/jquery.fs.boxer.css"
	type="text/css">
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script type="text/javascript"
	src="${domainUrlUtil.staticResources}/resources/admin/jslib/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
$(function(){
	$(".colorbox").boxer({
		fixed:true
	});
    
    $("#back").click(function(){
 		window.location.href="${domainUrlUtil.urlResources}/admin/actintegralbanner/banner";
	});
	$("#add").click(function(){
		if($("#addForm").form('validate')){
	 		$("#addForm").attr("action", "${domainUrlUtil.urlResources}/admin/actintegralbanner/banner/update")
  				 .attr("method", "POST")
  				 .submit();
  		}
	});
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
})
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">
			修改积分商城首页轮播图<span class="s-poar"><a class="a-back"
				href="${domainUrlUtil.urlResources}/admin/actintegralbanner/banner">返回</a></span>
		</h2>

		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm"
			name="addForm" enctype="multipart/form-data"> <input type="hidden"
				name="id" value="${(actIntegralBanner.id)!}" />
			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>基本信息
				</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>标题：</label> <input
								class="easyui-validatebox txt w280" type="text" id="title"
								name="title" value="${(actIntegralBanner.title)!''}"
								data-options="required:true,validType:'length[1,50]'">
						</p>
					</div>
					<br />
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>链接地址：</label> <input
								class="easyui-validatebox txt w280" type="text" id="linkUrl"
								name="linkUrl" value="${(actIntegralBanner.linkUrl)!''}"
								data-options="required:true,validType:'length[5,100]'">
						</p>
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label> <label> <font
								style="color: #808080">
									请填写完整的地址,以“/”开头，例如跳转到单品页：“/product/2.html” </font>
							</label>
						</p>
					</div>
					<br />
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>应用场景：</label>
							<@cont.radio id="type" name="pcMobile"
							value="${(actIntegralBanner.pcMobile)!''}" codeDiv="PC_MOBILE"/>
						</p>
					</div>
					<br />
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>排序号：</label> <input
								class="easyui-numberbox w280" type="text" id="orderNo"
								name="orderNo" value="${(actIntegralBanner.orderNo)!''}"
								data-options="required:true,min:1,max:99">
						</p>
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label> <label> <font
								style="color: #808080"> 序号越大越靠前显示，请输入1到99直接的数字 </font>
							</label>
						</p>
					</div>
					<br />
					<div class="fluidbox">
						<label class="lab-item"><font class="red">*</font>展示时间：</label> <input
							type="text" id="startTime" name="startTime"
							class="txt w200 easyui-validatebox" missingMessage="开始时间必填"
							data-options="required:true"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});"
							value="${(actIntegralBanner.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
							readonly="readonly"> ~ <input type="text" id="endTime"
							name="endTime" class="txt w200 easyui-validatebox"
							missingMessage="结束时间必填" data-options="required:true"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});"
							value="${(actIntegralBanner.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}"
							readonly="readonly">
					</div>
					<br />
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>图片：</label> <input
								type="file" id="imageFile" name="imageFile"
								style="height: 21px; float: left; line-height: 30px; vertical-align: middle;"
								missingMessage="请选择图片" class="txt w200 easyui-validatebox" />
							<#if (actIntegralBanner.image)??> &nbsp;&nbsp;&nbsp;&nbsp;<a
								href="${domainUrlUtil.imageResources}${(actIntegralBanner.image)!''}"
								class="colorbox">查看图片</a> </#if>
						</p>
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label> <label> <font
								style="color: #808080">
									PC积分商城图片最佳像素（或保持该比例）：宽740，高240；移动端团购图片最佳像素（或保持该比例）：宽720，高350； </font>
							</label>
						</p>
					</div>
					<br />
				</dd>
			</dl>

			<#--2.batch button-------------->
			<p class="p-item p-btn">
				<input type="button" id="add" class="btn" value="修改" /> <input
					type="button" id="back" class="btn" value="返回" />
			</p>
			</@form.form>
		</div>
	</div>
</div>

<#include "/admin/commons/_detailfooter.ftl" />
