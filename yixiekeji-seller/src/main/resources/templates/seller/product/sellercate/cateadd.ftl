<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/sellerCate/"/>

<script language="javascript">
    $(function () {
    	initMenu('sellercate');
    	
        var backUrl = "${currentBaseUrl}";
        var options = {
            url: '${currentBaseUrl}create',
            type: 'post',
            success: function (data) {
                if (data && null != data.success && data.success == true) {
                    window.location.href=backUrl;
                }else if(data.backUrl != null){
                    alert(data.message);
                    window.location.href=data.backUrl;
                }else{
                    refrushCSRFToken(data.csrfToken);
                    alert(data.message);
                }
            }
        };

        $("button[type='button'].back").click(function(){
	 		window.location.href="${currentBaseUrl}";
		});
        
//         $("select[name^='parentId_']").change(function(){
//             var level = $(this).attr("level");
//             //控制最多只有三级分类
//             if(level == 1)
//                 return;
//             var id = $(this).attr("id");
//             var parentId = $(this).val();

//             $("select[name^='parentId_']").each(function(){
//                 var subLevel = $(this).attr("level");
//                 if (parseInt(subLevel) > parseInt(level)) {
//                     $(this).remove();
//                 }
//             })
//             $.ajax({
//                 type:"get",
//                 url: "${currentBaseUrl}getByPid?id=" + parentId,
//                 dataType: "json",
//                 cache:false,
//                 success:function(data){
//                     if (data && data.length > 0) {
//                     	var children = "<div class='col-lg-4'>";
//                         children += "<select id='parentId_" + parseInt(level +1) + "' name='parentId_"+
//                         	parseInt(level +1)+"' level="+parseInt(level +1) +" class='form-control'>";
//                         children += "<option value=''>请选择</option>";
//                         $.each(data, function(i, n){
//                             children += "<option value=" + n.id + ">" + n.name + "</option>";
//                         })
//                         children += "</select></div>"
//                         $('#'+id).parent().after('&nbsp;&nbsp;&nbsp;' +children);
                        
//                     }
//                 }
//             });
//         });
		
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
				 //上级分类赋值
	             var parentId = $('select[level]').last().val();
	             if('' == parentId){
	                 parentId = $('select[level]').last().prev().val();
	             }
	             if('' == parentId || -1 == parentId){
	                 parentId = 0;
	             }
	             $('#pid').val(parentId);
	             $('#addform').ajaxSubmit(options);
            },
			fields : {
				name : {
					validators : {
						 notEmpty: true
					}
				},
				parentId_0 : {
					validators : {
						 notEmpty: true
					}
				}
			}
		});
        
        <#if message??>$.messager.progress('close');alert('${message}');</#if>
    });
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
						href="${domainUrlUtil.urlResources}/seller/product/sellerCate">店铺分类管理</a>
					</li>
					<li class="active">新增店铺分类</li>
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
						data-bv-message="该项必填">
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>分类名称:
							</label>
							<div class="col-lg-4">
								<input type="text" id="name" name="name"
									value="${(cate.name)!''}" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>上级分类:
							</label>
							<div class="col-lg-4">
								<input type="hidden" name="pid" id="pid" /> <select
									id="parentId_0" name="parentId_0" level="0"  class="form-control" >
									<option value="-1">无</option> 
									<#if productCates?? && productCates?size&gt; 0> 
										<#list productCates as productCate>
										<option value="${productCate.id}">${productCate.name}</option>
										</#list> 
									</#if>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2"></label>
							<label class="col-lg-6 ejava-errinforight">选择当前分类的上级分类，如需添加一级分类，请选择“无”</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>权重:
							</label>
							<div class="col-lg-4">
								<input type="text" id="sort" name="sort"
									required
									min="1"
									max="999999"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查"
                             	  	data-bv-lessthan-message="权重必须小于999999"
                             	  	data-bv-greaterthan-message="权重数必须大于0"
									value="${(cate.sort)!''}" class="form-control"  />
							</div>
							<label class="col-lg-6 ejava-errinforight">权重值越高，显示将越靠前</label>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="submit" class="btn btn-danger btn-primary sub">提交</button>
								<button type="button" class="btn btn-danger btn-primary back">返回</button>
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
