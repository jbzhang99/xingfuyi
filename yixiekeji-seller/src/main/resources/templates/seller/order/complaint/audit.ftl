<#include "/seller/commons/_head.ftl">
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/order/complaint"/>
<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>

<script language="javascript">
	$(function() {
		initMenu('complaint');
		<#if obj.image?? && "" != obj.image>
		$(".boxer").boxer();
		</#if>
		
        var options = {
            type:"POST",
            url:'${currentBaseUrl}/doAudit',
            dataType:"json",
            async : false,
            success: function (data) {
                if(data.success){
                    window.location.href='${currentBaseUrl}';
                }else{
                	$.messager.progress("close");
                    $.messager.alert("提示",data.message);
                    $("#sub").attr("disabled",false);
                    return false;
                }
            },
            error:function(){
                alert("异常，请重试！");
            }
        };

		$(".back").click(function() {
			window.location.href = '${currentBaseUrl}';
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
    			$.messager.confirm('确认', '确定提交申诉么？', function(r) {
    				if (r) {
    					$("#stateType").val('agree');
    					$.messager.progress({
    						text : "提交中..."
    					});
                        $('#addform').ajaxSubmit(options);
    				}
    			});
    		}
    	});
	});

	function closeWin() {
		$('#newstypeWin').window('close');
	}
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
						href="${domainUrlUtil.urlResources}/seller/order/complaint">投诉管理</a>
					</li>
					<li class="active">商家申诉</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">投诉信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<@form.form method="post" class="form-horizontal" id="addform"
						name="addform" enctype="multipart/form-data"
						action="${currentBaseUrl}/doAudit"> 
						
						<input type="hidden" value="${(obj.id)!''}" name="id"> 
						<input type="hidden" id="stateType" name="stateType">
						
						<div class="form-group">
							<label class="col-lg-2 control-label">投诉人账号：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.userName!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">订单号：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.orderSn!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">投诉来源：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.source!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">问题描述：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.question!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">投诉商品：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.orderProductName!}</span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">投诉内容：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.content!}</span>
							</div>
						</div>
						
						<#if obj.image?? && "" != obj.image>
						<div class="form-group">
							<label class="col-lg-2 control-label">投诉图片：</label> 
							<div class="col-lg-8">
								 <a class="info-span boxer" href="${domainUrlUtil.imageResources}${obj.image!''}">查看图片</a>
							</div>
						</div>
						</#if>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">投诉时间：</label> 
							<div class="col-lg-8">
								<span class="info-span" >${obj.complaintTimeStr!}</span>
							</div>
						</div>
						
						<div style="padding-top: 10px;">申诉信息</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>申诉内容：</label> 
							<div class="col-lg-8">
								<textarea class="form-control" 
									required
									name="optContent" id="optContent"></textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">图片：</label> 
							<div class="col-lg-8">
								<input
									type="file" id="pic" name="pic"
									class="form-control"
									/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-3">
								<button id="sub" type="submit" class="btn btn-danger btn-primary">提交</button>
								<button type="button" class="btn btn-danger back btn-primary">返回</button>
							</div>
						</div>
					</@form.form>

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