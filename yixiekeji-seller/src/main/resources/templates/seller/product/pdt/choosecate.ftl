<#include "/seller/commons/_head.ftl">

<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/product"/>
<script language="javascript">
    $(function () {
//         $("[name=uploadImg]").multiupload();
        var backUrl = "${currentBaseUrl}";
        var options = {
            url: '${currentBaseUrl}/add',
            type: 'GET',
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
        
        
        $("#add").click(function () {
            var parentId = $('select[level]').last().val();
            if(!parentId){
                parentId = $('select[level]').last().prev().val();
            }
            if('' == parentId || -1 == parentId){
                parentId = 0;
            }
            if(parentId == 0){
                $.messager.alert('提示','请选择具体的分类。');
                return;
            }
            $('#cateId').val(parentId);
            $("#addform").submit();
        });

        $(document).on("change","select[name^='parentId_']",function(){
            var level = parseInt($(this).attr("level"));
            
        	//到三级分类时，可下一步
        	if(level == 2){
        		var optlength_ = $(this).find("option:selected").length;
        		if(optlength_ > 0)
        			$("#add").removeClass("disabled");
        	} else{
        		$("#add").addClass("disabled");
	            var id = $(this).attr("id");
	            var parentId = $(this).val();
	
	            $("select[name^='parentId_']").each(function(){
	                var subLevel = $(this).attr("level");
	                if (parseInt(subLevel) > level) {
	                    $(this).parent().remove();
	                }
	            })
	            $.ajax({
	                type:"get",
	                url: "${currentBaseUrl}/getCateById?id=" + parentId,
	                dataType: "json",
	                cache:false,
	                success:function(data){
	                    if (data && data.success && data.rows && data.rows.length > 0) {
	                        var children = "<div class='col-lg-3'><select id='parentId_" + parseInt(level +1) + "' name='parentId_"+
	                        	parseInt(level +1)+"' level="+parseInt(level +1) +" class='form-control' size='18'>";
	                        //children += "<option value=''>请选择</option>";
	                        $.each(data.rows, function(i, n){
	                            children += "<option value=" + n.id + ">" + n.name + "</option>";
	                        })
	                        children += "</select></div>"
	                        $('#'+id).parent().after('&nbsp;&nbsp;&nbsp;' +children);
	                    }
	                }
	            });
        	}
            
        });

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
					<li class="active">
						发布商品
					</li>
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

					<form method="get" id="addform" class="form-horizontal"
							action="${currentBaseUrl}/add">
						<input type="hidden" id="cateId" name="cateId" />
						<div class="form-group">
							<div class="col-lg-3">
								 <select id="parentId_0" name="parentId_0" level="0" class="form-control" size="18">
	                                <#list cate as cc>
	                                    <option value="${(cc.id)!''}"> ${(cc.name)!''}</option>
	                                </#list>
	                            </select>
							</div>
							
						</div>
						<div class="col-lg-14">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-8 ejava-errinforight">选择分类没有分类信息,请先申请可以经营的商品分类,具体路径:[商品管理]-[商品分类申请]。</label>
						</div>

						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
								<button type="button" id="add" class="btn btn-danger btn-primary disabled">下一步</button>
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

