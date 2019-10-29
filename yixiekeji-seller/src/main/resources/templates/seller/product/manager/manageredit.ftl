<#include "/seller/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/manager/"/>
<script language="javascript">
    $(function () {
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
        $("#back").click(function () {
            window.location.href = backUrl;
        });
        $("#add").click(function () {
            //商家申请分类必须具体到第三级分类
            if($("select[name^='parentId_']").length < 3 || $('select[level]').last().val() == '-1'){
                $.messager.alert('提示','商家申请分类必须具体到三级分类。');
                return;
            }

            var validator = $('#addForm').validate();
            if(validator.form()){
                var parentId = $('select[level]').last().val();
                if('' == parentId){
                    parentId = $('select[level]').last().prev().val();
                }
                if('' == parentId || -1 == parentId){
                    parentId = 0;
                }
                $('#productCateId').val(parentId);
                $('#addForm').ajaxSubmit(options);
            }
        });

        $("select[name^='parentId_']").live("change", function(){
            var level = $(this).attr("level");
            //控制最多只有三级分类
            if(level == 2)
                return;
            var id = $(this).attr("id");
            var parentId = $(this).val();

            $("select[name^='parentId_']").each(function(){
                var subLevel = $(this).attr("level");
                if (parseInt(subLevel) > parseInt(level)) {
                    $(this).remove();
                }
            })
            $.ajax({
                type:"get",
                url: "${domainUrlUtil.urlResources}/admin/product/cate/getByPid?id=" + parentId,
                dataType: "json",
                cache:false,
                success:function(data){
                    if (data.length > 0) {
                        var children = "<select id='parentId_" + parseInt(level +1) + "' name='parentId_"+parseInt(level +1)+"' level="+parseInt(level +1) +" class='w200'>";
                        children += "<option value='-1'>请选择</option>";
                        $.each(data, function(i, n){
                            children += "<option value=" + n.id + ">" + n.name + "</option>";
                        })
                        children += "</select>"
                        $('#'+id).after('&nbsp;&nbsp;&nbsp;' + children);
                    }
                }
            });
        });

        <#if message??>$.messager.progress('close');
        alert('${message}');</#if>
    });

</script>

<div class="wrapper">
    <div class="formbox-a">
        <h2 class="h2-title">申请分类新增
            <span class="s-poar">
                <a class="a-back" href="${currentBaseUrl}">返回</a>
            </span>
        </h2>

    <#--1.addForm----------------->
        <div class="form-contbox">
        <@form.form method="post" class="validForm" id="addForm" name="addForm">
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>基本信息</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item"><font class="red">*</font>商品分类: </label>
                            <input type="hidden" name="productCateId" id="productCateId"/>
                            <select id="parentId_0" name="parentId_0" level="0" class="w200">
                                <option value="-1">无</option>
                                <#if productCates?? && productCates?size&gt; 0>
                                    <#list productCates as productCate>
                                        <option value="${productCate.id}">${productCate.name}</option>
                                    </#list>
                                </#if>
                            </select>
                        </p>
                    </div>
                </dd>
            </dl>

            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>帮助</dt>
                <dd class="dd-group">
                    <div class="fluidbox">
                        <label class="lab-item">帮助信息。</label>
                    </div>
                </dd>
            </dl>

        <#--2.batch button-------------->
            <p class="p-item p-btn">
                <input type="button" id="add" class="btn" value="提交"/>
                <input type="button" id="back" class="btn" value="返回"/>
            </p>
        </@form.form>
        </div>
    </div>
</div>
<#include "/seller/commons/_detailfooter.ftl" />