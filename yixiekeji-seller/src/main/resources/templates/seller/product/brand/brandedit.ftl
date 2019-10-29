<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/brand"/>

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script language="javascript">
    $(function () {
        var backUrl = "${currentBaseUrl}";
        $(".boxer").boxer();
        initMenu('brand');
    	
    	$("button[type='button'].back").click(function(){
     		window.location.href= domain+"/seller/product/brand";
    	});
    	
    	$('#addform').bootstrapValidator({
    		feedbackIcons : {
    			valid : 'glyphicon glyphicon-ok',
    			invalid : 'glyphicon glyphicon-remove',
    			validating : 'glyphicon glyphicon-refresh'
    		},
    		submitHandler: function (validator, form, submitButton) {
    			if(!validator.isValid()){
    				return false;
    			}
    			 $('#addform').ajaxSubmit({
    				 url: '${currentBaseUrl}/update',
   		            type: 'post',
   		            success: function (data) {
   		                if (data && null != data.success && data.success == true) {
   		                    window.location.href=backUrl;
   		                }else if(data.backUrl != null){
   		                	$.messager.alert("提示",data.message);
   		                    window.location.href=data.backUrl;
   		                }else{
   		                    refrushCSRFToken(data.csrfToken);
   		                    $.messager.alert("提示",data.message);
   		                    $("#sub").attr("disabled",false);
   		                }
   		            }
   		        });
    		},
    		fields : {
    			name : {
    				validators : {
    					 notEmpty: true
    				}
    			},
    			nameFirst : {
    				validators : {
    					 notEmpty: true,
    					 regexp: {
                             regexp: /^[a-zA-Z]{1}$/,
                             message: '该品牌的拼音首字母，一个字符'
                         }
    				}
    			},
    			explainInfo : {
    				validators : {
    					 notEmpty: true
    				}
    			}
    		}
    	});
    	
    	
        <#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
    })
</script>

<div class="main-container container-fluid">
	<!-- Page Container -->
	<div class="page-container">
		<!-- 左侧菜单开始 -->
		<#include "/seller/commons/_left.ftl">
		<!-- 左侧菜单结束 -->
		<!-- Page Content -->
		<div class="page-content">
			<!-- 主体头部开始 -->
			<div class="page-breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="fa fa-home"></i> <a
						href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
					</li>
					<li><a
						href="${domainUrlUtil.urlResources}/seller/product/brand">品牌管理</a>
					</li>
					<li class="active">编辑品牌</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal"
						enctype="multipart/form-data" data-bv-message="该项必填">
						
						<input type="hidden" name="id" value="${(brand.id)!''}"/>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>品牌名称: </label>
							<div class="col-lg-4">
								<input type="text" id="name" name="name"
									value="${(brand.name)!''}" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>首字母: </label>
							<div class="col-lg-4">
								<input type="text" id="nameFirst" name="nameFirst"
									class="form-control"
									onkeyup="this.value =this.value.toUpperCase()"
									value="${(brand.nameFirst)!''}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>品牌图片: </label>
							<div class="col-lg-4">
								<input type="file" id="imageFile" name="imageFile" />
							</div>
							<#if (brand.image)??>
							<div class="col-lg-4">
								<a href="${domainUrlUtil.imageResources}${(brand.image)!''}" class='boxer'>查看图片</a>
							</div>
							</#if>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>品牌说明: </label>
							<div class="col-lg-4">
								<textarea id="explainInfo" name="explainInfo"
									class="form-control"/>${(brand.explainInfo)!''}</textarea>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-3">
								<button id="sub" type="submit" class="btn btn-danger btn-primary">提交</button>
								<button type="button" class="btn btn-danger back btn-primary">返回</button>
							</div>
						</div>
					</form>

				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">
