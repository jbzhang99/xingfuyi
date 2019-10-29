<#include "/admin/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/"/>
<style>
    /*预览图片*/
    #previewImgBox{
        height:150px;
        padding:10px 0px 10px 83px;
    }
    .preview-img{
        height:150px;
    }
    .preview-img li{
        list-style: none;
        width:150px;
        height:150px;
        float:left;
        padding-right:20px;
    }
    .preview-img .img{
        position: relative;
        /* float:left;*/
        width:150px;
        height:150px;
    }

    .upload-img{
        padding-left:83px;
        padding-top:10px;
    }
    .img-box{
        display: none;
        position: absolute;
        left:0;
        top:0;
        width: 100%;
        height: 100%;
        color: white;
        font-style: 16px;
        background: rgba(47, 47, 47, 0.5);
        cursor: pointer;
    }
    .del-img{
        position: relative;
        top:72px;
        left:57px;
        color:#fff;
        cursor: pointer;
    }
    /*end*/
</style>
<script language="JavaScript">
    $(function () {
        $("[name=uploadImg]").multiupload();
    })
</script>
<div class="wrapper">
    <div class="formbox-a">
        <h2 class="h2-title">发布商品
            <span class="s-poar">
                <a class="a-back" href="http://127.0.0.1:8805/seller/product/">返回</a>
            </span>
        </h2>

        <div class="form-contbox">
            <form id="addForm" name="addForm" class="validForm" action="" method="post">
                <dl class="dl-group">
                    <dt class="dt-group">
                        <span class="s-icon"></span>图片信息
                    </dt>
                    <dd class="dd-group">
                        <div class="fluidbox">
                            <p class="p12 p-item">
                                <label class="lab-item"><font class="red">*</font>商品分类: </label>
                                <input type="text" id="productCateName" name="productCateName" value="" class="txt w200">
                                <input type="hidden" id="productCateId" name="productCateId" value="">
                            </p>
                        </div>
                        <div class="fluidbox">
                            <p class="p12 p-item">
                                <label class="lab-item">&nbsp;</label>
                                <font style="color: #808080">
                                    选择当前分类的上级分类，如需添加一级分类，请选择“无”。
                                </font>
                            </p>
                        </div>
                        <div class="fluidbox">
                            <p class="p12 p-item">
                                <label class="lab-item"><font class="red">*</font>商品品牌: </label>
                                <input type="text" id="productBrandName" name="productBrandName" value="" class="txt w200">
                                <input type="hidden" id="productBrandId" name="productBrandId" value="">
                            </p>
                        </div>
                        <span class="s-showbtn">
                            <div name="uploadImg" action="" index="${waitShow_index!''}" multiparam='{"url":"${currentBaseUrl}uploadFiles","validate":{"fileSize":{"value":2048000,"errMsg":"上传图片不允许超过2M"}, "fileMaxNum":{"value":10,"errMsg":"上传图片最多不能超过10张"},"fileType":{"value":"img","errMsg":"上传图片后缀只支持:image、gif、jpeg、jpg、png"}},"callback":"callback1"}' class="upload-img">
                                <a href="#" class="txt_white">选择图片</a>
                                <input type="hidden" name="fileIndex" value="0"/>
                            </div>
                        </span>
                    </dd>
                    <!-- 预览图片 -->
                    <dd id='previewImgBox'>
                        <ul class='preview-img'>
                            <li>
                                <div class='img'>
                                    <img src='${domainUrlUtil.urlResources}/resources/seller/default.gif' width='150' height='150'>
                                    <div class='img-box'>
                                        <a class='del-img' href='javascript:void(0);'>删除</a>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class='img'>
                                    <img src='${domainUrlUtil.urlResources}/resources/seller/default.gif' width='150' height='150'>
                                    <div class='img-box'>
                                        <a class='del-img' href='javascript:void(0);'>删除</a>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class='img'>
                                    <img src='${domainUrlUtil.urlResources}/resources/seller/default.gif' width='150' height='150'>
                                    <div class='img-box'>
                                        <a class='del-img' href='javascript:void(0);'>删除</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </dd>
                    <!-- end -->
                </dl>

                <dl class="dl-group">
                    <dt class="dt-group"><span class="s-icon"></span>帮助</dt>
                    <dd class="dd-group">
                        <div class="fluidbox">
                            <label class="lab-item">帮助信息。</label>
                        </div>
                    </dd>
                </dl>

                <p class="p-item p-btn">
                    <input type="button" id="add" class="btn" value="保存">
                    <input type="button" id="back" class="btn" value="返回">
                </p>
                <input type="hidden" name="CSRFToken" value="f684b80b-147a-45ad-8b1a-f52371ecc930">
            </form>
        </div>
    </div>
</div>
<div class="layout-split-proxy-h"></div><div class="layout-split-proxy-v"></div>
<script type="text/javascript">
    // //鼠标移入移出图片
    $(".img").mouseover(function(){
        $(this).find('.img-box').show();
    }).mouseout(function() {
        $(this).find('.img-box').hide();
    });
</script>
<#include "/seller/commons/_detailfooter.ftl" />