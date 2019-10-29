<#include "/admin/commons/_detailheader.ftl" /> 
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/systemNotice"/>

<style>
	.panel-fit body.panel-noscroll {
		overflow-y: scroll;
	}
</style>
<link
	href="${(domainUrlUtil.staticResources)!}/resources/admin/umeditor/themes/default/css/umeditor.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/umeditor/umeditor.min.js"></script>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/admin/umeditor/lang/zh-cn/zh-cn.js"></script>

<script language="javascript">
	var currentBaseUrl = "${currentBaseUrl}";
	var domainURL = "${domainUrlUtil.urlResources}";
	
	$(function(){
	   $("#back").click(function(){
		   location.href = currentBaseUrl;
	   });
	   
	 	<#if (obj.content)??>
        UM.getEditor('myEditor').setContent(<#noescape>"${(obj.content)}"</#noescape>);
   		</#if>
	   
	   $("#add").click(function() {
			var this_ = $(this);
			if($("#addForm").form('validate')){
				var cont_ = UM.getEditor('myEditor').getContent();
                if(!cont_){
                    $.messager.alert('提示',"请填写公告内容");
                    return;
                }
                $('#content').val(cont_);
                
				this_.attr("disabled",true);
				$.messager.progress({
					text : "提交中..."
				});
				$.ajax({
					type:"POST",
					url: currentBaseUrl + "/doAdd",
					dataType: "json",
					data: $('#addForm').serialize(),
					cache:false,
					success:function(data){
						$.messager.progress('close')
						if (data.success) {
							$.messager.show({
    							title : '提示',
    							msg : <#if obj??>"编辑成功"<#else>"新增成功"</#if>,
    							showType : 'show'
    						});
							setTimeout(function(){
								location.href="${currentBaseUrl}";
							},2000);
						} else {
							this_.removeAttr("disabled");
							$.messager.alert("提示",data.message);
						}
					}
				});
			}
		});
	});
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">
			<#if obj??> 编辑商城公告 <#else> 新增商城公告</#if><span class="s-poar"> <a
				class="a-back" href="${currentBaseUrl}">返回</a>
			</span>
		</h2>

		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm"
			name="addForm"
			action="${currentBaseUrl}/doAdd"> <input type="hidden"
				value="${(obj.id)!}" name="id">

			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>基本信息
				</dt>
				<dd class="dd-group">
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>标题： </label>
							<input type="text" id="title" name="title"
								class="txt w280 easyui-validatebox" missingMessage="请输入标题"
								data-options="required:true,validType:'length[1,32]'"
								value="${(obj.title)!''}" />
						</p>
					</div>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label>
							<font style="color: #808080">
							公告标题，32字以内
							</font>
						</p>
					</div>
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>是否置顶： </label>
							<@cont.radio id="isTop" name="isTop" value="${(obj.isTop)!''}" codeDiv="YES_NO" />
						</p>
					</div>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label>
							<font style="color: #808080">
							置顶的公告将会以最高优先级显示
							</font>
						</p>
					</div>
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>排序号： </label>
							<input type="text" id="sort" name="sort"
								class="txt w280 easyui-numberbox" missingMessage="请输入排序号"
								data-options="required:true,min:1,precision:0,max:999999"
								value="${(obj.sort)!''}" />
						</p>
					</div>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label>
							<font style="color: #808080">
							排序号为数字，最大999999，越小显示将越靠前
							</font>
						</p>
					</div>
					
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>简介： </label>
							<textarea id="describe" name="describe"
								rows="3"
 								class="txt w480 easyui-validatebox" 
 								missingMessage="请输入公告简介"
								data-options="required:true,validType:'length[1,60]'"
								>${(obj.describe)!''}</textarea>
						</p>
					</div>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item">&nbsp;</label>
							<font style="color: #808080">
							公告简要描述，60字以内
							</font>
						</p>
					</div>
					
				</dd>
			</dl>
			
			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>内容编辑
				</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<input type="hidden" id="content" name="content"/>
						<script type="text/plain" id="myEditor"
							style="width: 99%; height: 280px;">
						</script>
						<script type="text/javascript">
						    	var um = UM.getEditor('myEditor');
						</script>
					</div>
				</dd>
			</dl>

			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>帮助
				</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<ul style="margin-left: 30px;">
							<li>设置商城公告，所有商家登录后将看到此公告，被置顶的公告将优先显示</li>
						</ul>
					</div>
				</dd>
			</dl>

			<p class="p-item p-btn">
				<input type="button" id="add" class="btn" value="提交" /> <input
					type="button" id="back" class="btn" value="返回" />
			</p>
			</@form.form>
		</div>
	</div>

	<#include "/admin/commons/_detailfooter.ftl" />