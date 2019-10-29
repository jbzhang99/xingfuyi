<script language="javascript">
$(function(){
		//设置佣金
		$("#a_pro_sale").click(function(){
			var selecteds = $('#dataGrid').datagrid('getSelections');
			if(selecteds.length != 1){
        			$.messager.alert('提示', '请选择一条商品数据');
    				return false;
        		}
        		$("#valuereset").click(); 
			var selected = $('#dataGrid').datagrid('getSelected');
        		$("#opt_productId").val(selected.id);
			$("#lbl_value_productName").html(selected.name1);
			//$("#opt_saleScale1").val(selected.saleScale1);
			//$("#opt_saleScale2").val(selected.saleScale2);
			$('#_salevalueopt').dialog('open');
	 	});
	 	
	 	// 确定按钮
		$("#valueOptOk").click(function(){
						
			if($("#valueOptForm").form('validate')){
				$.ajax({
					type:"POST",
					url: "${domainUrlUtil.urlResources}/admin/sale/salescale/salescaleproduct",
					dataType: "json",
					data: $('#valueOptForm').serialize(),// + "&" + getCSRFTokenParam(),
					cache:false,
					success:function(data, textStatus){
						if (data.success) {
							$.messager.alert('提示','修改成功。');
							$('#_salevalueopt').dialog('close');
							$('#dataGrid').datagrid('reload',queryParamsHandler());
							return;
						} else {
							$.messager.alert("提示",data.message);
							//refrushCSRFToken(data.csrfToken);
						}
					}
				});
	  		}
		});

		//取消按钮
		$("#valueOptCancel").click(function(){
			$('#_salevalueopt').dialog('close');
		});
		
})
</script>
<div id="_salevalueopt" class="easyui-dialog popBox" title="设置佣金" style="width:800px;height:300px;" data-options="resizable:true,closable:true,closed:true,cache: false,modal: true">
	
	<div class="form-contbox">
		<@form.form method="post" class="validForm" id="valueOptForm" name="valueOptForm">
		<input type="reset" id="valuereset" style="display:none;" /> 
		<dl class="dl-group">
			<dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
			<dd class="dd-group">
				<input type="reset" id="valuereset" style="display:none;" /> 
				<input type="hidden" id="opt_productId" name="opt_productId"/>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item">商品名称: </label>
						<label style="float:left" id="lbl_value_productName"></label>
					</p>
				</div>
				<br/>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item"><font class="red">*</font>一级分销: </label>
						<input type="text" id="opt_saleScale1" name="opt_saleScale1" 
							class="txt w200 easyui-numberbox" missingMessage="输入0到1之间的小数，精确到小数点两位" 
							data-options="min:0,max:1,precision:2,required:true"/>
					</p>
				</div>
				<br/>
				<div class="fluidbox">
					<p class="p12 p-item">
						<label class="lab-item"><font class="red">*</font>二级分销: </label>
						<input type="text" id="opt_saleScale2" name="opt_saleScale2" 
						 class="txt w200 easyui-numberbox" 
						missingMessage="输入0到1之间的小数，精确到小数点两位" data-options="min:0,max:1,precision:2,required:true"/>
					</p>
				</div>
				<br/>
					
			</dd>
		</dl>
		<#--2.batch button-------------->
		<p class="p-item p-btn">
			<input type="button" id="valueOptOk" class="btn" value="确定"/>
			<input type="button" id="valueOptCancel" class="btn" value="取消"/>
		</p>
		</@form.form>
	</div>
</div>
