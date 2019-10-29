<#include "/seller/commons/_head.ftl">
<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>
<script language="javascript">

$(function(){
	$(".boxer").boxer();

	$('#pro').click(function(){
// 		$('#goodsDialog').dialog('open');
	    var width_ = '1000';
	    if(ismobile()){
			width_ = "100%";
	    }
		$('#gd_dataGrid').datagrid('unselectAll');
		$("#goodsListDiv").window({
			top:50,
			width : width_,
			height : 505,
			title : "选择商品",
			modal : true,
			shadow : false,
			collapsible : false,
			minimizable : false,
			maximizable : false
		});
	});
	
	$("#dataType").change(function(){
        var dataType = $(this).val();
        if (dataType == 1) {
        	byPro();
        } else if (dataType == 2) {
        	byLink();
        }
        
    });
	
	initMenu('floordata');
	$("button[type='button'].back").click(function(){
		window.location.href="${domainUrlUtil.urlResources}/seller/mindex/floordata";
	});
	
	$('#addform').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields:{
			dataType:{
				validators : {
					 notEmpty: true
				}
			}
		}
	});
	
	<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
});

function byLink(){
	$(".dataPrdDiv").hide();
	$(".dataLinkDiv").show();
	//增加必填校验
  	 $("#addform").data('bootstrapValidator').addField('title',{  
          validators: {  
              notEmpty: true 
          }  
      }); 
  	 $("#addform").data('bootstrapValidator').addField('linkUrl',{  
          validators: {  
              notEmpty: true,
              stringLength: {
                  min: 2,
                  max: 255,
                  message: '地址长度不合法'
              }
          }  
      }); 
  	 $("#addform").data('bootstrapValidator').addField('imageFile',{  
         validators: {  
             notEmpty: {
            	 message:'请上传图片'
             } 
         }  
     }); 
  	 $("#addform").data('bootstrapValidator').removeField('proname'); 
}

function byPro(){
	$(".dataPrdDiv").show();
	$(".dataLinkDiv").hide();
 	//商品必选
	$("#addform").data('bootstrapValidator').addField('proname',{  
        validators: {  
            notEmpty: {
            	message:'请选择商品'
            } 
        }  
    }); 
	$("#addform").data('bootstrapValidator').removeField('title'); 
	$("#addform").data('bootstrapValidator').removeField('linkUrl'); 
	$("#addform").data('bootstrapValidator').removeField('imageFile'); 
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
						href="${domainUrlUtil.urlResources}/seller/mindex/floordata">楼层数据</a>
					</li>
					<li class="active">编辑楼层数据</li>
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
						action="${domainUrlUtil.urlResources}/seller/mindex/floordata/update"
						enctype="multipart/form-data">
						<input type="hidden" id="image" name="image" value="${(mSellerIndexFloorData.image)!''}">
						<input type="hidden" id="id" name="id" value="${(mSellerIndexFloorData.id)!''}">
											
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>楼层：</label>
							<div class="col-lg-4">
								<select name="indexFloorId" id="indexFloorId" 
									value="${(mSellerIndexFloorData.indexFloorId)!''}" 
									class="form-control" required>
			                    	<option value="">--请选择--</option>
			                        <#if floors?? && floors?size &gt; 0>
			                        	<#list floors as floor>
											<option value="${(floor.id)!}" <#if mSellerIndexFloorData?? && mSellerIndexFloorData.indexFloorId?? && mSellerIndexFloorData.indexFloorId == floor.id>selected="true"</#if>>${(floor.name)!}</option>
										</#list>
									</#if>
							    </select>
						    </div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>排序号：</label> 
							<div class="col-lg-4">
								<input
									class="form-control" id="orderNo" name="orderNo"
									value="${(mSellerIndexFloorData.orderNo)!''}"
									required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="请输入正确的排序号" />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								 序号越小越靠前显示
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>数据类型: </label>
							<div class="col-lg-4">
								<@cont.select id="dataType" class="form-control"
								value="${(mSellerIndexFloorData.dataType)!''}"
								codeDiv="DATA_TYPE" mode="1"/>
							</div>
						</div>
						
						<div class="form-group dataPrdDiv" <#if mSellerIndexFloorData?? && mSellerIndexFloorData.dataType?? && mSellerIndexFloorData.dataType != 1>style="display:none;"</#if>>
							<label class="col-lg-2 control-label"><font class="red">*</font>商品: </label> 
							<div class="col-lg-1">	
								<input style="margin-top: 2px;padding: 3px;"
									type="button" value="选择商品" id="pro" /> 
									<input type="hidden" id="productName" name="productName"
										value="${(mSellerIndexFloorData.product.name1)!''}" > 
									<input type="hidden" id="refId" name="refId" value="${(mSellerIndexFloorData.refId)!''}">
							</div>
							<div class="col-lg-8">	
								<input id="proname" name="proname" 
										<#if mSellerIndexFloorData?? && mSellerIndexFloorData.dataType?? && mSellerIndexFloorData.dataType != 2>
										required
										</#if>
										class="form-control"
										readonly="readonly"
										value="${(mSellerIndexFloorData.product.name1)!''}"
										style="background-color: rgb(255, 255, 255);border: none;box-shadow: none;"
										/>
							</div>
						</div>
						
						<div class="form-group dataLinkDiv" <#if mSellerIndexFloorData?? && mSellerIndexFloorData.dataType?? && mSellerIndexFloorData.dataType != 2>style="display:none;"</#if>>
							<label class="col-lg-2 control-label"><font class="red">*</font>图片标题：</label>
							<div class="col-lg-4">
								<input class="form-control" type="text"
									id="title" name="title"
									value="${(mSellerIndexFloorData.title)!''}" />
							</div>	
						</div>

						<div class="form-group dataLinkDiv" <#if mSellerIndexFloorData?? && mSellerIndexFloorData.dataType?? && mSellerIndexFloorData.dataType != 2>style="display:none;"</#if>>
							<label class="col-lg-2 control-label"><font class="red">*</font>链接地址：</label>
							<div class="col-lg-4">
								<input type="text"
									id="linkUrl" name="linkUrl"
									value="${(mSellerIndexFloorData.linkUrl)!''}"
									class="form-control" 
									 />
							</div>
							<label class="col-lg-6 ejava-errinforight">
								 请填写完整的地址,例如：http://www.xxx.com/product/1.html
							</label>
						</div>
						
						<div class="form-group dataLinkDiv" <#if mSellerIndexFloorData?? && mSellerIndexFloorData.dataType?? && mSellerIndexFloorData.dataType != 2>style="display:none;"</#if>>
							<label class="col-lg-2 control-label"><font class="red">*</font>图片：</label> 
							<div class="col-lg-4">
								<input type="file" id="imageFile" 
									name="imageFile" class="form-control"  />
							</div>
							<#if (mSellerIndexFloorData.image)??> 
							<div class="col-lg-4">
								<a href="${domainUrlUtil.imageResources}${(mSellerIndexFloorData.image)!''}" class='boxer'>查看图片</a>
							</div>
							</#if>
						</div>
						
						<div class="form-group dataLinkDiv" style="display: none;">
							<label class="col-lg-2 control-label"></label>
							<label class="col-lg-10 ejava-errinforight">
								图片建议像素（或保持该比例）：宽400，高400
							</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">备注：</label> 
							<div class="col-lg-4">
								<textarea name="remark" id="remark"
									class="form-control">${(mSellerIndexFloorData.remark)!''}</textarea>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-6 col-lg-offset-3">
								<button type="submit" class="btn btn-danger btn-primary">提交</button>
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

<div style="display: none" id="goodsListDiv">
<#include "goodsDialog.ftl"/>
</div>

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">