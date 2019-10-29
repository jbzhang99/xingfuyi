<#include "/seller/commons/_detailheader.ftl" /> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/operate/sellerTransport"/>

<style>
.dl-group p img {
	max-width: 120px;
	float: left;
}

.formbox-a .lab-item {
	float: left;
	width: 120px;
	text-align: right;
	margin-right: 3px;
	display: inline;
	padding-top: 5px;
}
</style>

<script type="text/javascript">
	$(function() {

		var tsType = $("#type").val();
		if (tsType != '1') {
			var tsModel = $("#transModel").val();
			switch (tsModel) {
			case "1":
				$("#mailInfo").show();
				break;
			case "2":
				$("#emsInfo").show();
				break;
			case "3":
				$("#expressInfo").show();
				break;
			default:
				break;
			}
		}

		$("#type").change(function() {
			var type = $(this).val();
			var model = $("#transModel").val();
			if (model) {
				switch (model) {
				case "1":
					if (type == 2) {
						$("#mailInfo").show();
						$("#mailInfo").siblings("div[id$='Info']").hide();
					} else {
						$(".dd-group div[id$='Info']").hide();
					}
					break;
				case "2":
					if (type == 2) {
						$("#emsInfo").show();
						$("#emsInfo").siblings("div[id$='Info']").hide();
					} else {
						$(".dd-group div[id$='Info']").hide();
					}
					break;
				case "3":
					if (type == 2) {
						$("#expressInfo").show();
						$("#expressInfo").siblings("div[id$='Info']").hide();
					} else {
						$(".dd-group div[id$='Info']").hide();
					}
					break;

				default:
					break;
				}
			}
		});

		$("#transModel").change(function() {
			var type = $("#type").val();
			var model = $(this).val();
			if (model) {
				switch (model) {
				case "1":
					if (type == 2) {
						$("#mailInfo").show();
						$("#mailInfo").siblings("div[id$='Info']").hide();
					} else {
						$(".dd-group div[id$='Info']").hide();
					}
					break;
				case "2":
					if (type == 2) {
						$("#emsInfo").show();
						$("#emsInfo").siblings("div[id$='Info']").hide();
					} else {
						$(".dd-group div[id$='Info']").hide();
					}
					break;
				case "3":
					if (type == 2) {
						$("#expressInfo").show();
						$("#expressInfo").siblings("div[id$='Info']").hide();
					} else {
						$(".dd-group div[id$='Info']").hide();
					}
					break;

				default:
					break;
				}
			}
		});

		$("#add").click(
				function() {
					var flag = true;
					$(".dd-group div[id$='Info']").each(
							function() {
								if ($(this).css('display') == 'block'
										&& !$(this).children().find(
												".easyui-numberbox").val()) {
									flag = false;
									$.messager.alert('提示', '请设置价格。');
									return;
								}
							});
					if (flag)
						$('#addForm').submit();
				});

	});
</script>

<div class="wrapper">
	<div class="formbox-a">
		<h2 class="h2-title">
			运费设置<span class="s-poar"> <a class="a-back"
				href="${currentBaseUrl}">返回</a>
			</span>
		</h2>

		<#--1.addForm----------------->
		<div class="form-contbox">
			<@form.form method="post" class="validForm" id="addForm"
			name="addForm" enctype="multipart/form-data"
			action="${currentBaseUrl}/save"> <input type="hidden"
				name="transMail" id="transMail" /> <input type="hidden"
				name="transEms" id="transEms" /> <input type="hidden"
				name="transExpress" id="transExpress" /> <input type="hidden"
				name="id" value="${(obj.id)!}" />

			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>基本信息
				</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<p class="p6 p-item">
							<label class="lab-item"><font class="red">*</font>运送方式: </label>
							<@cont.select id="transModel" mode="-1" codeDiv="TRANSPORT_MODEL"
							name="transModel" class="" value="${(obj.transModel)!''}"
							style="width:200px;"/>
						</p>
						<p class="p6 p-item">
							<label class="lab-item"><font class="red">*</font>类型: </label>
							<@cont.select id="type" mode="-1" codeDiv="TRANSPORT_TYPE"
							name="type" class="" value="${(obj.type)!''}"
							style="width:200px;"/>
						</p>
					</div>
					<div class="fluidbox" id="mailInfo" style="display: none;">
						<p class="p6 p-item">
							<label class="lab-item"><font class="red">*</font>平邮价格: </label>
							一件 <input type="text" id="transMailInfo" name="transMailInfo"
								value="${(obj.transMailInfo)!''}"
								class="txt w200 easyui-numberbox" data-options="min:0" /> 元
						</p>
					</div>
					<div class="fluidbox" id="emsInfo" style="display: none">
						<p class="p6 p-item">
							<label class="lab-item"><font class="red">*</font>EMS价格:
							</label> 一件 <input type="text" id="transEmsInfo" name="transEmsInfo"
								value="${(obj.transEmsInfo)!''}"
								class="txt w200 easyui-numberbox" data-options="min:0" /> 元
						</p>
					</div>
					<div class="fluidbox" id="expressInfo" style="display: none">
						<p class="p6 p-item">
							<label class="lab-item"><font class="red">*</font>快递价格: </label>
							一件 <input type="text" id="transExpressInfo"
								name="transExpressInfo" value="${(obj.transExpressInfo)!''}"
								class="txt w200 easyui-numberbox" data-options="min:0" /> 元
						</p>
					</div>
				</dd>

			</dl>

			<dl class="dl-group">
				<dt class="dt-group">
					<span class="s-icon"></span>帮助
				</dt>
				<dd class="dd-group">
					<div class="fluidbox">
						<label class="lab-item" style="width: 100%; text-align: left;">
							订单结算时会使用此计价设置 </label>
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

<#include "/seller/commons/_detailfooter.ftl" />
