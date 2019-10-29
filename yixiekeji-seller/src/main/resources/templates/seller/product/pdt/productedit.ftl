<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/product/"/>

<#include "productimgcss.ftl"/>

<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script type="text/javascript"
	src="${domainUrlUtil.staticResources}/resources/seller/jslib/My97DatePicker/WdatePicker.js"></script>
<link
	href="${domainUrlUtil.staticResources}/resources/umeditor/themes/default/css/umeditor.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${domainUrlUtil.staticResources}/resources/umeditor/umeditor.min.js"></script>
<script type="text/javascript"
	src="${domainUrlUtil.staticResources}/resources/umeditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/jquery.iframe-transport.js"></script>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/jquery.fileupload.js"></script>
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/jquery.multifile.upload.js"></script>
	
<script type="text/javascript"
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/js/skupicupload.js"></script>



 <script src="http://demo.vcxiaohan.com/jquery-plugins/dragMove/js/dragMove.js"></script>  
 
<script language="javascript">
    $(function () {
        <#if pic?? && pic?size &gt; 0>
		$('#previewImgBox').show();
	</#if>
    	
    	//图片数量
    	$('[name=fileIndex]').val(parseInt($("#preview-img > li:visible").length));
    	
    	initMenu('waitsale');
    	<#if (product.description)??>
         UM.getEditor('myEditor').setContent(<#noescape>"${(product.description)}"</#noescape>);
    	</#if>
    	
     	$('#addform').bootstrapValidator({
    		feedbackIcons : {
    			valid : 'glyphicon glyphicon-ok',
    			invalid : 'glyphicon glyphicon-remove',
    			validating : 'glyphicon glyphicon-refresh'
    		},
    		submitHandler: function (validator, form, submitButton) {
    			if(!validator.isValid()){
    				var invalidtip = $(document).find("small.help-block");
    				if(invalidtip){
	    				invalidtip.each(function(){
	    					log.i($(this).data("bv-result"));
		    				if($(this).data("bv-result") == "INVALID"){
		    					$.messager.show({
		                            title:'提示',
		                            msg:'请检查所有输入是否正确',
		                            showType:'show'
		        	            });
		    					$("#sub").attr("disabled",false);
		    					return false;
		    				}
	    				});
    					
    				}
    				$("#sub").attr("disabled",false);
    				return false;
    			}
    			var normAttrNum = 0;
				$("#normDiv").find("input[name^='attr_']").each(function(){
					normAttrNum ++;
				});
				$("#normAttrNum").val(normAttrNum);

    	       	 //货品信息
                var isNorm = $('input[name="isNorm"]').filter(':checked').val();
                if (isNorm == 2){
                    var normAttrTr = $('tr[name="normAttrTr"]');
                    var prostock_ = 0;
                    var pcprice_ = new Array();
                    var mobprice_ = new Array();
                    var weight_ = new Array();
                    var length_ = new Array();
                    var width_ = new Array();
                    var height_ = new Array();
                    
                    if(normAttrTr && normAttrTr.length > 0){
                        var productGoods = '';
                        for(var i = 0; i < normAttrTr.length; i ++){
                        	var normtr_ = $(normAttrTr[i]);
                        	var stockinput_ = normtr_.find("input[id^='inventory_details_stock_']");
                        	if(stockinput_.length > 0){
                        		 prostock_ += Number(stockinput_.val());
                        	}
                        	
                        	var ppriceinput_ = normtr_.find("input[id^='inventory_details_pprice_']");
                        	if(ppriceinput_.length > 0){
                        		pcprice_.push(Number(ppriceinput_.val()));
                        	}
                        	
                        	var mobpriceinput_ = normtr_.find("input[id^='inventory_details_mprice_']");
                        	if(mobpriceinput_.length > 0){
                        		mobprice_.push(Number(mobpriceinput_.val()));
                        	}
                        	
                        	var weightinput_ = normtr_.find("input[name^='inventory_details_weight_']");
                        	if(weightinput_.length > 0){
                        		weight_.push(Number(weightinput_.val()));
                        	}
                        	
                        	var lengthinput_ = normtr_.find("input[name^='inventory_details_length_']");
                        	if(lengthinput_.length > 0){
                        		length_.push(Number(lengthinput_.val()));
                        	}
                        	
                        	var widthinput_ = normtr_.find("input[name^='inventory_details_width_']");
                        	if(widthinput_.length > 0){
                        		width_.push(Number(widthinput_.val()));
                        	}
                        	
                        	var heightinput_ = normtr_.find("input[name^='inventory_details_height_']");
                        	if(heightinput_.length > 0){
                        		height_.push(Number(heightinput_.val()));
                        	}
                        	
//                         	//===============货品信息===============//
//                             var normAttrTrInput = $(normAttrTr[i]).find('input');
//                             if(normAttrTrInput && normAttrTrInput.length > 0){
//                                 var normAttrTrInputVal = '';
//                                 for(var j = 0; j < normAttrTrInput.length; j++){
//                                     normAttrTrInputVal += $(normAttrTrInput[j]).val() + ',';
//                                 }
//                                 normAttrTrInputVal = normAttrTrInputVal.substr(0, normAttrTrInputVal.length -1);
//                                 productGoods += normAttrTrInputVal + ';';
//                             }
//                           //===============货品信息===============//
							//至少启用一个sku
							if($(".skuinfo input[type='checkbox'][name^='goods_enable_']:checked").length < 1){
							<#-- $.messager.alert('提示',"请至少启用一个sku","",function(){
									 $(".skuinfo tr[name='normAttrTr']:first")[0].scrollIntoView();
								 });
							-->
								 $("#sub").attr("disabled",false);
								 return true;
							}
                        }
//                         productGoods = productGoods.substr(0, productGoods.length -1);
//                         $('#productGoods').val(productGoods);
                        
                    	//写入价格库存
		               	$("#productStock").val(prostock_);
		               	$("#mallPcPrice").val(Math.min.apply(null, pcprice_));
		               	$("#malMobilePrice").val(Math.min.apply(null, mobprice_));
		               	$("#weight").val(Math.min.apply(null, weight_));
		               	$("#length").val(Math.min.apply(null, length_));
		               	$("#width").val(Math.min.apply(null, width_));
		               	$("#height").val(Math.min.apply(null, height_));
		               	
		               	log.i("商品库存："+$("#productStock").val());
		               	log.i("mallPcPrice："+$("#mallPcPrice").val());
		               	log.i("malMobilePrice："+$("#malMobilePrice").val());
                    }
	               	log.i('productGoods:'+productGoods);
// 	               	return true;
                }else{
                    var sku = $('#sku').val();
                    if(sku == ''){
                        $.messager.alert('提示',"请填写sku");
                        $("#sub").attr("disabled",false);
                        return true;
                    }
                }
                
              	//组装sku图片信息
                var skupics_ = $("#normDiv").find("input[name^='skupic_']");
                var skupicData = new Array();
                var normp_ = $(".normtype_color > label");
                var normname = normp_.attr('normname');
                var normid = normp_.attr('normid');
                var sep_ = "_@_";
                var skuuploaded = true;
               	$.each(skupics_,function(idx1_,pic_){
               		var checkedcolr_ = $(".normtype_color").
               			find("input[type='checkbox']:checked");
               		var attrid = $(this).attr('attrid');
               		var attrname = "";
               		var colortype = $(this).attr("colortype");
               		$.each(checkedcolr_,function(idx2_,obj2_){
               			if(attrid == $(this).val()){
               				var parentli_ = $(this).parent();
               				var textinput_ = parentli_.find("input[type='text']");
               				//自定义属性
               				if(textinput_.length > 0){
               					attrname = textinput_.val();
               				} else{
               					attrname = $(this).parent().text().trim();
               				}
               			}
               		});
               		
               		skupicData.push(normid+sep_+normname+sep_+
               			attrid+sep_+attrname+sep_+$(this).val()+sep_+colortype);
               	});
               	if(skupics_.length>0&&!skuuploaded){
               		$("#sub").attr("disabled",false);
               		return;
               	}
               	$("#skupics").val(skupicData);
               	log.i('skupicData:'+skupicData);
//                	return;
                
                $('#productBrandId').val($('#brandId').val());//品牌id
                var costPrice = $('#costPrice').val();//成本价
                var protectedPrice = $('#protectedPrice').val();//保护价
                var marketPrice = $('#marketPrice').val();//市场价
                var mallPcPrice = $('#mallPcPrice').val();//pc商城价
                var malMobilePrice = $('#malMobilePrice').val();//pc商城价
                var virtualSales = $('#virtualSales').val();//虚拟销量
                var actualSales = $('#actualSales').val();//实际销量
                var todayLimitNum = $('#todayLimitNum').val();//商品库存
                var upTime = $('#upTime').val();//上架时间
                var transportId = $("#transportId").val();//运费模板
                var isInventedProduct = $("input[name='isInventedProduct'][checked]").val();//是否虚拟商品
                var description = UM.getEditor('myEditor').getContent();
                if(description == ''){
                    $.messager.alert('提示',"请填写商品描述");
                    $("#sub").attr("disabled",false);
                    return;
                }
                $('#description').val(description);//商品描述信息
                <#--var packing = $('#packing').val();//包装清单-->
                <#--if(packing == ''){-->
                  <#--&lt;#&ndash;  $.messager.alert('提示',"请填写包装清单"); &ndash;&gt;-->
                    <#--$("#sub").attr("disabled",false);-->
                    <#--return true;-->
                <#--}-->

                var sellerCateId = $("select[name^='sellerCate_']").last().val();//商家分类

                //商品图片
                var image = $('img[name^=prev_]');
                if (image && image.length > 0) {
                    var imageSrc = '';
                    for (var i = 0; i < image.length; i++) {
                        var imgSrc = $(image[i]).attr('src');
                        if (imgSrc.indexOf("resources") != -1)
                            continue;
                        imageSrc += imgSrc + ';';
                    }
                    if ('' != imageSrc) {
                        $('#imageSrc').val(imageSrc);//商品图片
                    }
                }else{
                    $.messager.alert('提示',"商品图片,至少传一张");
                    $("#sub").attr("disabled",false);
                    return true;
                }

                <#--商品属性-->
                var queryType = $('select[name=queryType]');
                if (queryType && queryType.length > 0) {
                    var queryTypeVal = '';
                    for (var i = 0; i < queryType.length; i++) {
                        queryTypeVal += $(queryType[i]).val() + ';';
                    }
                    $('#queryType').val(queryTypeVal);//商品属性
                }
                <#--自定义属性-->
                var custType = $('input[name=custType]');
                if (custType && custType.length > 0) {
                    var custTypeVal = '';
                    for (var i = 0; i < custType.length; i++) {
                        custTypeVal += $(custType[i]).attr('data') + ',' + $(custType[i]).val() + ';';
                    }
                    $('#custAttr').val(custTypeVal);//自定义属性
                }

                if (upTime == '') {
                    $.messager.alert('提示', '上架时间必填。');
                    $("#sub").attr("disabled",false);
                    return;
                }
                if(transportId == null || transportId == -1){
                	$.messager.alert('提示',"请选择运费模板，如果没有请先维护运费模板信息");
                	$("#sub").attr("disabled",false);
                    return;
                }
                if (Number(mallPcPrice) < Number(protectedPrice)) {
                    $.messager.alert('提示', '商城价不能低于保护价','',function(){
                    	var isNorm_ = Number("${(product.isNorm)!''}");
                    	if(isNorm_ == 1){
	                    	$('#protectedPrice')[0].scrollIntoView();
                    	} else{
                    		$('.skuinfo')[0].scrollIntoView();
                    	}
                    });
                    $("#sub").attr("disabled",false);
                    return;
                }
                if (Number(malMobilePrice) < Number(protectedPrice)) {
                    $.messager.alert('提示', '商城价(mobile)不能低于保护价','',function(){
                    	var isNorm_ = Number("${(product.isNorm)!''}");
                    	if(isNorm_ == 1){
	                    	$('#protectedPrice')[0].scrollIntoView();
                    	} else{
                    		$('.skuinfo')[0].scrollIntoView();
                    	}
                    });
                    $("#sub").attr("disabled",false);
                    return;
                }
                if(sellerCateId == null || sellerCateId == "-1" || sellerCateId == ""){
                    $.messager.alert('提示', '请选择商家分类');
                    $("#sub").attr("disabled",false);
                    return;
                }else{
                    $('#sellerCateId').val(sellerCateId);
                }

               	$('#addform').ajaxSubmit({
                    url: '${currentBaseUrl}update',
                    type: 'post',
                    success: function (data) {
                        if (data && null != data.success && data.success == true) {
                        	  $.messager.alert("提示","商品发布成功","info",function(){
  	                            window.location.href='${domainUrlUtil.urlResources}/seller/product/waitSale';
                              });
                        }else if(data.backUrl != null){
                        	$.messager.alert("提示",data.message);
                            window.location.href=data.backUrl;
                        }else{
                            refrushCSRFToken(data.csrfToken);
                            $.messager.alert('提示',data.message);
                            $("#sub").attr("disabled",false);
                        }
                        refrushCSRFToken(data.csrfToken);
                    }
                });
    		},
    		fields : {
    			imageFile : {
    				validators : {
    					 notEmpty: {
    						 message:'请上传图片'
    					 }
    				}
    			}
    		}
    	});
    	
        //加载图片上传控件
        $("[name=uploadImg]").multiupload();
        $("[name=skupicfile]").skuupload();
        $(".boxer").boxer();
        
        var backUrl = "${currentBaseUrl}";
        
        $("button[type='button'].back").click(function(){
            window.location.href = '${domainUrlUtil.urlResources}/seller/product/waitSale';
        });

        $('#mallPcPrice').blur(function () {
       	 	 var mallPcPrice = $(this).val();//pc商城价
             var protectedPrice = $('#protectedPrice').val();//保护价
             if (mallPcPrice && protectedPrice && Number(mallPcPrice) < Number(protectedPrice)) {
            	 $(this).val(protectedPrice);
                 $.messager.alert('提示', '商城价不能低于保护价');
                 return;
             }
        });
        
        $('#malMobilePrice').blur(function () {
             var malMobilePrice = $(this).val();
             var protectedPrice = $('#protectedPrice').val();//保护价
             if (malMobilePrice && protectedPrice && Number(malMobilePrice) < Number(protectedPrice)) {
            	 $(this).val(protectedPrice);
                 $.messager.alert('提示', '商城价不能低于保护价');
                 return;
             }
        });
        
        $('#protectedPrice').blur(function () {
            var mallPcPrice = $('#mallPcPrice').val('');
            var malMobilePrice = $("#malMobilePrice").val('');
        });

		$(document).on('mouseover', '.img',function() {
			$(this).find('.img-box').show();
		}).on('mouseout', function() {
			$(this).find('.img-box').hide();
		});

		//删除图片
		$(document).on('click', '.del-img',function() {
			$(this).closest("div").remove();
			var idx_ = Number($('[name=fileIndex]').val());
			var picNum_ = $("#preview-img > div:visible").length;
			$('[name=fileIndex]').val(idx_ ==0?0:idx_ - 1);
			if (picNum_ == 0) {
				$('#previewImgBox').hide();
			}
		});

		$(document).on("change","select[name^='sellerCate_']",function() {
			var level = $(this).attr("level");
			var id = $(this).attr("id");
			var parentId = $(this).val();

			if (parentId == '') {
				return;
			}

			$("select[name^='sellerCate_']").each(function() {
				var subLevel = $(this).attr("level");
				if (parseInt(subLevel) > parseInt(level)) {
					$(this).remove();
				}
			});
			
			$.ajax({
				type : "get",
				url : "${currentBaseUrl}sellerCate/getByPid?id="
						+ parentId,
				dataType : "json",
				cache : false,
				success : function(data) {
					if (data && data.length > 0) {
						var children = "<div class='col-lg-4'>";
						children += "<select id='sellerCate_"
								+ parseInt(level + 1)
								+ "' name='sellerCate_"
								+ parseInt(level + 1) + "' level="
								+ parseInt(level + 1)
								+ " class='form-control'>";
						children += "<option value=''>请选择</option>";
						$.each(data, function(i, n) {
							children += "<option value=" + n.id + ">"
									+ n.name + "</option>";
						})
						children += "</select>"
						children += "</div>";
						$('#' + id).parent().after(children);
					}
				}
			});
		});
		
		//加载运费模板
		$("input[name=transportType]").change(function (){
			var transportType = $("input[name=transportType]:checked").val();
				$.ajax({
	                type:"get",
	                url: "${domainUrlUtil.urlResources}/seller/operate/sellerTransport/getByType?type=" + transportType,
	                dataType: "json",
	                cache:false,
	                success:function(data){
	                    if (data.success) {
	                        var children = "<option value='-1'>--请选择--</option>";
	                        $.each(data.data, function(i, n){
	                            children += "<option value=" + n.id + " noTransInfo="+n.noTransInfo+">" + n.transName + "</option>";
	                        })
	                        $("#transportId").html("");
	                        $("#transportId").append(children);
	                    }
	                }
	            });
		});
		
	});
    
	function callback1(result) {
		//console.log("1"+result.names[0]);
	}
	
</script>
<#include "normjs.ftl"/>
 <style>  
        * {  
            margin: 0;   
            padding: 0;  
        }  
        body, html {  
            width: 100%;  
            height: 100%;  
        }  
        .div1 {  
            position: relative;  
             
            padding: 5px;
            display: inline-block;
        }  
        .div1 div {  
            position: relative;  
            display: inline-block;  
            width: 100px;  
            height:100px;  
            border: 2px solid #c1c1c1;  
            border-radius: 3px;
        }  
        .div1 div img {  
            width: 95px;
            height:95px;
        }  
        .div1 div i {  
            top: 0;  
            left: 0;  
            display: inline-block;  
            position: absolute;  
            width: 100%;  
            height: 100%;  
            cursor: pointer;  
        }  
        .div1 div:hover i {  
            outline: 1px dashed #ccc;  
        }  
</style>  
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
						href="${domainUrlUtil.urlResources}/seller/product/waitSale">待售商品</a>
					</li>
					<li class="active">编辑商品信息</li>
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

					 <@form.form method="post" id="addform" class="form-horizontal"
						enctype="multipart/form-data" >
						 <input type="hidden" name="id" value="#{product.id}"/>
				 	
						<div class="form-group">
							 <label class="col-lg-2 control-label"><font class="red">*</font>商品分类: </label>
	                         <label class="col-lg-10 ejava-errinforight">
	                            <#if catePath??>
	                         	   ${catePath!''}
	                            </#if>
							</label>
	                         <input type="hidden" id="productCateId" name="productCateId" value="${(product.productCateId)!''}"/>
                            <input type="hidden" id="productCatePath" name="productCatePath" value="${(product.productCatePath)!''}"/>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>商家自有商品分类: </label>
							<input type="hidden" name="sellerId" id="sellerId"/>
								
                            <#if sellerCate?? && sellerCate?size&gt; 0>
                            <div class="col-lg-4">
                                <select id="sellerCate_0" name="sellerCate_0" 
                                	level="0" class="form-control" disabled>
                                    <option value="-1">请选择</option>
                                    <#list sellerCate as sellerCate>
                                        <option value="${sellerCate.id}" <#if cateId?? && (sellerCate.id) == cateId>selected</#if>>
                                        ${sellerCate.name}
                                        </option>
                                    </#list>
                                </select>
                               </div>
                            </#if>
                            
                            <#if secondSellerCate?? && secondSellerCate?size &gt; 0>
                           	<div class="col-lg-4">
                                <select id="sellerCate_1" name="sellerCate_1" level="0" class="form-control" disabled>
                                    <option value="-1">请选择</option>
                                    <#list secondSellerCate as sellerCate>
                                        <option value="${sellerCate.id}" <#if secondCateId?? && (sellerCate.id) == secondCateId>selected</#if>>
                                        ${sellerCate.name}
                                        </option>
                                    </#list>
                                </select>
                               </div>
                            </#if>
                            <input type="hidden" id="sellerCateId" name="sellerCateId" value="${(product.sellerCateId)!''}"/>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-8 ejava-errinforight">
							 	商品可以从属于店铺的多个分类之下，店铺分类可以由 "商家管理后台系统 -> 商品管理 -> 店铺分类" 中自定义
							 </label>
						</div>
						
						<div class="form-group">
							 <label class="col-lg-2 control-label"><font class="red"></font>商品品牌: </label>
                            <#if brand?? && brand?size &gt; 0>
                            <div class="col-lg-4">
                                <select id="brandId" name="brandId" level="0" 
                                	class="form-control">
                                    <#list brand as brand>
                                        <option value="${(brand.id)!''}" <#if brand.id == product.productBrandId >selected</#if> >${(brand.nameFirst)!''} ${(brand.name)!''}</option>
                                    </#list>
                                </select>
                            </div>
                            </#if>
                            <input type="hidden" id="productBrandId" name="productBrandId" value="${(product.productBrandId)!''}"/>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品名称: </label> 
							<div class="col-lg-4">
								<input type="text" id="name1" name="name1"
									value="${(product.name1)!''}"
									class="form-control"
									required
									data-bv-stringlength="true"
                                    data-bv-stringlength-min="3"
                                    data-bv-stringlength-max="100"
                                    data-bv-stringlength-message="商品名称3-100位长度"
									/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
                               商品标题名称长度至少3个字符，最长100个字符
                            </label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>SPU: </label>
							<div class="col-lg-4">
								<input type="text" id="productCode" 
									name="productCode"
									value="${(product.productCode)!''}" 
									class="form-control"
									data-bv-stringlength="false"
                                    data-bv-stringlength-min="3"
                                    data-bv-stringlength-max="20"
                                    data-bv-stringlength-message="SPU3-20位长度"/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
                           		 商品编码名称长度至少3个字符，最长20个字符
                           	</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>关键字: </label>
							<div class="col-lg-4">
								<input
									type="text" id="keyword1" name="keyword1"
									value="${(product.keyword)!''}"
									class="form-control"
									data-bv-stringlength="false"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="50"
                                    data-bv-stringlength-message="关键字2-50位长度" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								商品关键字，用户检索检索商品，多个用逗号分割
                           	</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>促销信息: </label>
							<div class="col-lg-4">
								<input type="text" id="name2" name="name2"
									value="${(product.name2)!''}"
									class="form-control"
									data-bv-stringlength="false"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="100"
                                    data-bv-stringlength-message="促销信息2-100位长度" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>成本价: </label> 
							<div class="col-lg-4">
								<input
									type="text" id="costPrice" name="costPrice"
									value="${(product.costPrice)!''}"
									class="form-control "
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									min="0.1"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.1"
									/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label> 
							<label class="col-lg-10 ejava-errinforight">
								  价格必须是0.1~9999999之间的数字，此价格为商户对所销售的商品实际成本价格进行备注记录，非必填选项，不会在前台销售页面中显示
                           	</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>保护价: </label>
							<div class="col-lg-4">
								<input
									type="text" id="protectedPrice" name="protectedPrice"
									value="${(product.protectedPrice)!''}"
									class="form-control "
									data-bv-numeric="false"
									data-bv-numeric-message="请输入正确的数字"
									min="0.1"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.1" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								 价格必须是0.1~999999之间的数字，商品销售价不会低于此价格
                           	</label>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>市场价: </label> 
							<div class="col-lg-4">
								<input
									type="text" id="marketPrice" name="marketPrice"
									value="${(product.marketPrice)!''}"
									class="form-control "
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									min="0.1"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.1" />
							</div>	
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"></label> 
							<label class="col-lg-10 ejava-errinforight">
								  价格必须是0.1~9999999之间的数字，此价格仅为市场参考售价，请根据该实际情况认真填写
                           	</label>
						</div>
	
						 <#if seller??>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>虚拟销量: </label>
							<div class="col-lg-4">
								<input type="text" id="virtualSales" name="virtualSales"
									value="${(product.virtualSales)!''}"
									class="form-control"
									data-bv-numeric="false"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查"  />
							</div>
						</div>
						</#if>

						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>上架时间: </label>
							<div class="col-lg-4">
								<input type="text" id="upTime" name="upTime"
									value="${(product.upTime)?string('yyy-MM-dd HH:mm:ss')!''}"
									required
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									class="form-control"/>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								商品预计上线销售时间
                           	</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>运费计算类型: </label>
							<div class="col-lg-4">
								<@cont.radio id="transportType" name="transportType" value="${(product.transportType)!''}" codeDiv="PRO_TRANSPORT_TYPE" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								商品运费计算类型
                           	</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-6 ejava-errinforight">
								如果按重量计算运费，重量为0则运费计算为0。<br>
								如果按体积计算运费，长度、宽度、高度有一个为0则运费计算为0。
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>运费模板: </label>
							<div class="col-lg-4">
                                                                        <input type="hidden" name="noTransInfo" id="noTransInfo" value="<#if product?? && product.noTransInfo??>${(product.noTransInfo)!'' }</#if>"/>
									<select class="form-control" id="transportId" name="transportId">
									<#if tansports??>
										<option value="-1">--请选择--</option>
										<#list tansports as tansport>
											<option value="${(tansport.id)!'-1' }"  noTransInfo ="${(tansport.noTransInfo)!'' }" <#if product?? && product.transportId?? &&  product.transportId== tansport.id>selected="selected"</#if>>${(tansport.transName)!'' }</option>
										</#list>
									<#else>
										<option value="-1">--请选择--</option>
									</#if>
									</select>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								商品运费计算模板，如果没有该类型模板，请先去维护运费模板
                           	</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>是否商家推荐:
							</label> 
							<div class="col-lg-4">
								<@cont.radio id="sellerIsTop" value="${(product.sellerIsTop)!''}" codeDiv="PRODUCT_IS_TOP" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-6 ejava-errinforight">
								店铺分类页综合排序时推荐商品靠前排序
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>是否启用规格: </label>
                            <div class="col-lg-4">
	                            <input type="hidden" name="isNormHidden" id="isNormHidden" value="${(product.isNorm)!''}"/>
                                    <!-- 2019年10月08日 根据需求确认 编辑状态下 规格修改为可编辑状态 -->
                                    <!-- 
	                            <#if edit??>
		                            <@cont.radio1 id="isNorm" value="${(product.isNorm)!''}" codeDiv="PRODUCT_IS_NORM"/>
		                        <#else>
		                            <@cont.radio id="isNorm" value="${(product.isNorm)!''}" codeDiv="PRODUCT_IS_NORM"/>
	                            </#if>
                                     -->

                                     <@cont.radio id="isNorm" value="${(product.isNorm)!''}" codeDiv="PRODUCT_IS_NORM"/>

                            </div>
                            <input type="hidden" name="productGoods" id="productGoods" />
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-6 ejava-errinforight">
                                                                <!-- 2019年10月08日 根据需求确认 编辑状态下 规格修改为可编辑状态 -->
                                                                <!-- 
								<#if normList?? && normList?size &gt; 0>
									商品新建之后规格永久固定，不能编辑规格
								<#else>
									该分类下无规格
								</#if>
                                                                 -->
							</label>
						</div>

						<#include "incnorm.ftl"/>
                   		
                   		<div class="form-group sku">
							<label class="col-lg-2 control-label"><font class="red">*</font>sku:
							</label> 
							<div class="col-lg-4">
								<input type="text" id="sku" name="sku" value="${(product.sku)!''}" class="form-control"/>
							</div>
						</div>
						
						<div class="form-group price">
							<label class="col-lg-2 control-label"><font class="red">*</font>商城价: </label> 
							<div class="col-lg-4">
								<input
									type="text" id="mallPcPrice" name="mallPcPrice"
									value="${(product.mallPcPrice)!''}"
									class="form-control "
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									min="0.1"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.1"/>
							</div>
						</div>
						
						<div class="form-group price">
							<label class="col-lg-2 control-label"></label> 
							<label class="col-lg-10 ejava-errinforight">
								价格必须是0.1~999999之间的数字，且不能高于保护价。
                                此价格为商品实际销售价格，如果商品存在规格，该价格显示最低价格
                           	</label>
						</div>

						<div class="form-group price">
							<label class="col-lg-2 control-label"><font class="red">*</font>商城价(mobile):
							</label> 
							<div class="col-lg-4">
								<input type="text" id="malMobilePrice" name="malMobilePrice"
									value="${(product.malMobilePrice)!''}"
									class="form-control"
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									min="0.1"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.1" />
							</div>
						</div>
						
						<div class="form-group price">
							<label class="col-lg-2 control-label"></label> 
							<label class="col-lg-10 ejava-errinforight">
								价格必须是0.1~999999之间的数字，且不能高于保护价。
                                此价格为手机商城商品实际销售价格，如果商品存在规格，该价格显示最低价格
                           	</label>
						</div>
						
						<div class="form-group stock">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品库存: </label>
							<div class="col-lg-4">
	                            <input type="text" 
	                            	id="productStock" 
	                            	name="productStock"
	                                value="${(product.productStock)!''}" 
	                                class="form-control"
	                                required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="请输入0~999999之间的整数" />
                            </div>
							<label class="col-lg-6 ejava-errinforight">
								0~999999之间的整数，用户显示在单品页下，发生交易，系统会自动计算库存
                           	</label>
						</div>
						
						<div class="form-group stock">
							<label class="col-lg-2 control-label">重量(kg): </label>
							<div class="col-lg-4">
	                            <input type="text" 
	                            	id="weight" 
	                            	name="weight"
	                                value="${(product.weight)!'0.000' }" 
	                                class="form-control"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,3}))$"
                             	  	data-bv-regexp-message="输入非法字符，请检查" />
                            </div>
							<label class="col-lg-6 ejava-errinforight">
                           	</label>
						</div>
						
						<div class="form-group stock">
							<label class="col-lg-2 control-label">长度(cm): </label>
							<div class="col-lg-4">
	                            <input type="text" 
	                            	id="length" 
	                            	name="length"
	                                value="${(product.length)!'0' }" 
	                                class="form-control"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查" />
                            </div>
							<label class="col-lg-6 ejava-errinforight">
                           	</label>
						</div>
						
						<div class="form-group stock">
							<label class="col-lg-2 control-label">宽度(cm): </label>
							<div class="col-lg-4">
	                            <input type="text" 
	                            	id="width" 
	                            	name="width"
	                                value="${(product.width)!'0' }" 
	                                class="form-control"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查" />
                            </div>
							<label class="col-lg-6 ejava-errinforight">
                           	</label>
						</div>
						
						<div class="form-group stock">
							<label class="col-lg-2 control-label">高度(cm): </label>
							<div class="col-lg-4">
	                            <input type="text" 
	                            	id="height"
	                            	name="height"
	                                value="${(product.height)!'0' }"
	                                class="form-control"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查" />
                            </div>
							<label class="col-lg-6 ejava-errinforight">
                           	</label>
						</div>

						<div style="padding-top: 10px;">查询属性</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
					
						<div class="form-group">
							<div class="col-lg-10">
				           		<#if queryTypeAttr?? && queryTypeAttr?size &gt; 0>
				                <input type="hidden" id="queryType" name="queryType"/>
				                <#list queryTypeAttr as queryTypeAttr>
                                <div class="col-xs-6 pronorm">
                                	<label class="col-lg-4 control-label">${(queryTypeAttr.attrName)!''}: </label>
                                	<div class="col-lg-8">
		                                <select name="queryType" level="0" class="form-control">
		                                    <option value="${(queryTypeAttr.attrId)!''},${(queryTypeAttr.attrName)!''},-1">请选择</option>
		                                    <#list queryTypeAttr.attrValList as attr>
		                                        <option value="${(queryTypeAttr.attrId)!''},${(queryTypeAttr.attrName)!''},${(attr)!''}" <#if (queryTypeAttr.dbAttr)?? && attr == (queryTypeAttr.dbAttr)>selected</#if>>${(attr)!''}</option>
		                                    </#list>
		                                </select>
	                                </div>
                                </div>
				                </#list>
				            </#if>
				          </div>
		            	</div>
            
		           		<#if custTypeAttr?? && custTypeAttr?size &gt; 0>
		           		<div style="padding-top: 10px;">自定义属性</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
		           		
						<div class="form-group">	
							<div class="col-lg-10">
		                    	<input type="hidden" id="custAttr" name="custAttr"/>
		                    	<#list custTypeAttr as custTypeAttr>
		                    	<div class="col-xs-6 pronorm">
		                    	    <label class="col-lg-4 control-label">${(custTypeAttr.attrName)!''}:</label>
		                    	    <div class="col-lg-8">
			                         	<input type="text" name="custType" data="${(custTypeAttr.attrId)!''},${(custTypeAttr.attrName)!''}" 
			                         		value="${(custTypeAttr.dbAttr)!''}" class="form-control"/>
		                   			</div>
		                   		</div>
		                    </#list>
		                    </div>
		                </div>
		           		</#if>

						<div style="padding-top: 10px;">商品详情</div>
						<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />
			
						<div class="form-group">
							 <input type="hidden" id="description" name="description"/>
							<label class="col-lg-2 control-label"><font class="red">*</font>商品描述:
							</label> 
							<div class="col-lg-8">
								<script type="text/plain" id="myEditor"
									style="width: 100%; height: 240px;">
									</script>
								<script type="text/javascript">
							    	var um = UM.getEditor('myEditor');
								</script>
							</div>
						</div>
			
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red"></font>包装清单:
							</label> 
							<div class="col-lg-8">
								 <textarea id="packing" name="packing" 
								 	class="form-control"
								 	value="${(product.packing)!''}">${(product.packing)!''}</textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>商品图片:
							</label> 
							<div class="col-lg-4">
								 <div name="uploadImg" action="" index="${waitShow_index!''}" 
								 		multiparam='{"url":"${currentBaseUrl}uploadFiles","validate":{"fileSize":{"value":2048000,"errMsg":"上传图片不允许超过2M"}, "fileMaxNum":{"value":5,"errMsg":"上传图片最多不能超过5张"},"fileType":{"value":"img","errMsg":"上传图片后缀只支持:image、gif、jpeg、jpg、png"}},"callback":"callback1"}' class="upload-img">
			                         <input type="hidden" name="fileIndex" value="0"/>
			                     </div>
							</div>
						</div>
			
						<div class="form-group">
							<label class="col-lg-2 control-label"></label> 
							<div class="col-lg-8">
								<label class="col-lg-6 ejava-errinforight">
									图片建议像素（或保持该比例）：宽800，高800
					            </label>
				            </div>
						</div>
			
					<!-- 预览图片 -->
                                        <div id="divdrag">
                                            <input type="hidden" id="imageSrc" name="imageSrc"/>
                                            <div class="a div1" id="preview-img">  
                                                <#if pic?? && pic?size &gt; 0>
                                                    <#list pic as pic>
                                                        
                                                        <div class="aa" DR_drag="1" DR_replace="1" id="previewImgBox"> 
                                                            
                                                            <!--删除图片-->
                                                            <a  style=" position: absolute;left: 1px;top: 1px; z-index:9999;width: 20px; height:20px;" class='del-img icon-cancel'  href='javascript:void(0);'></a> 
                                                         
                                                            <img id="prev_${pic_index}"  name="prev_${pic_index}" src='${(pic.imagePath)!''}'>  
                                                            <i class="drag"></i>  
                                                        </div>  
                                                    </#list>
                                                </#if> 
                                            </div>  
                                       </div>
			             <!-- end -->
                
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-4">
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
<script>  
    $(function() {  
        // 调用插件
        $('#divdrag').dragMove({  
            
            limit: true,// 限制在窗口内  
            callback: function($move, $replace) {
                console.log('拖动了'+ $('p', $move).text() +'跟'+ $('p', $replace).text() +'进行交换');
            }  
        });  
    });  
</script> 


<script language="javascript" type="text/javascript">  
　　$(document).ready(function(){  
            $('#transportId').change(function(){  
                //alert($(this).children('option:selected').val());  
                //alert($(this).children('option:selected').attr('notransinfo'));  
                $("#noTransInfo").val($(this).children('option:selected').attr('notransinfo'));
            })  
    })  
</script>
<#include "/seller/commons/_addcommonfooter.ftl"> 
<#include "/seller/commons/_end.ftl">