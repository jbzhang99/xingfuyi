<#include "/admin/commons/_detailheader.ftl" /> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/admin/system/newsParter"/>

<style>
.dl-group p img {
	max-width: 120px;
	float: left;
}
.panel-fit body.panel-noscroll {
	overflow-y: scroll;
}
</style>

<script language="javascript">
	$(function() {

		$("#back").click(function() {
			location.href = '${currentBaseUrl}';
		});

		$("#add").click(function() {
			if ($('#addForm').form('validate')) {
				$('#addForm').submit();
			}
		});

	})

	function closeWin() {
		$('#newstypeWin').window('close');
	}
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">
			<#if obj??> 编辑合作伙伴信息 <#else> 新增合作伙伴 </#if> <span class="s-poar">
				<a class="a-back" href="${currentBaseUrl}">返回</a>
			</span>
		</h2>

		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm"
			name="addForm" enctype="multipart/form-data"
			action="${currentBaseUrl}/doAdd"> <input type="hidden"
				value="${(obj.id)!''}" name="id"> 
				<input type="hidden" value="${(obj.image)!''}" name="image">
				<input type="hidden" value="1" name="status">

			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>基本信息
				</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>名称: </label> <input
								type="text" id="name" name="name"
								value="${(obj.name)!''}"
								class="txt w200 easyui-validatebox" missingMessage="请输入合作伙伴名称"
								data-options="required:true" />
						</p>
					</div>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                合作伙伴名称，一般为公司名称
                            </font>
                        </p>
                    </div>

					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>企业logo:
							</label> <#if obj??> <img alt=""
								src="${domainUrlUtil.imageResources}/${(obj.image)!''}">
							</#if> <input type="file" id="imagepic" name="imagepic"
								class="txt w200 easyui-validatebox" />
						</p>
					</div>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                               请上传合作伙伴的企业logo，图片格式支持jpg、jpeg、png、bmp、gif，大小不超过2M
                            </font>
                        </p>
                    </div>
					<div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item">链接: </label>
							<input type="text" id="url" name="url"
								value="${(obj.url)!''}" class="txt w200 easyui-validatebox"/>
						</p>
					</div>
 					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                链接URL应以http://开头，请如实填写合作伙伴的外网地址，用于用户点击跳转
                            </font>
                        </p>
                    </div>
                    <div class="fluidbox">
						<p class="p12 p-item">
							<label class="lab-item"><font class="red">*</font>权重: </label> 
							<input type="text" id="sort" name="sort" value="${(obj.sort)!''}" 
								class="txt w200 easyui-numberbox" data-options="min:1,max:999,required:true" 
								missingMessage="权重必填，1-999" />
						</p>
					</div>
					<div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">&nbsp;</label>
                            <font style="color: #808080">
                                请填写权重，权重值越高，该项显示将越靠前
                            </font>
                        </p>
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
</div>

<#include "/admin/commons/_detailfooter.ftl" />
