<div id="normDiv" style="display: none">
<#assign normNum = 0>
<#assign hasColor = false>

<#if normList?? && normList?size &gt; 0>
<#list normList as norm>
    <#assign i = 0>
    <#assign isColor = false>
    <#assign normNum = normNum+1>
 	<#-- 记录最后一次normid和attrid-->
 	<#assign normindex = 0>
 	<#assign attrindex = 0>
 	<#--记录最大属性id-->
 	<#assign maxattrid = 0>
    <#if (norm.id)==1>
  	  <#assign isColor = true>
  	  <#assign hasColor = true>
    </#if>
    
    
    <div class="form-group normattrs <#if isColor>normtype_color<#else>normtype_others</#if>">
        <label class="col-lg-2 control-label" normname="${(norm.name)!''}" 
        	normid="${(norm.id)!''}">${(norm.name)!''}: </label>
        <div class="col-lg-10">
            <#if norm.attrList?? && norm.attrList?size &gt; 0>
            <div class="checkbox">
                <#list norm.attrList as attr>
             	<#assign normindex = norm_index>
 				<#assign attrindex = attr_index>
 				<#if maxattrid &lt; attr.id>
 					<#assign maxattrid = attr.id>
 				</#if>
                <label style="margin: 0px 8px;">
				    <!-- ======================${attr.name}属性数据 bg====================== -->
				    <input id="norm_id_${normindex}_${attrindex}" name="norm_id_${normindex}_${attrindex}" value="${(norm.id)!}" type="hidden"/>
				    <input id="norm_name_${normindex}_${attrindex}" name="norm_name_${normindex}_${attrindex}" value="${(norm.name)!}" type="hidden"/>
				    <input id="type_attr_${normindex}_${attrindex}" name="type_attr_${normindex}_${attrindex}" value="1" type="hidden"/>
				    <input id="attr_name_${normindex}_${attrindex}" name="attr_name_${normindex}_${attrindex}" value="${(attr.name)!}" type="hidden"/>
				    <input id="attr_id_${normindex}_${attrindex}"  name="attr_id_${normindex}_${attrindex}" 
				    	<#--编辑时默认属性id，否则只能通过用户点击赋值-->
				    	<#if edit??>value="${(attr.id)!}"</#if> 
				    	type="hidden"/>
				    <input id="image_${normindex}_${attrindex}" name="image_${normindex}_${attrindex}" value="${(attr.image)!}" type="hidden"/>
				    <!-- ======================${attr.name}属性数据 ed====================== -->
                    <!-- 2019年10月08日 根据需求确认 编辑状态下 规格修改为可编辑状态 -->                
                   <!-- <input name="attr_${attrindex}" type="checkbox" id="attr_${attrindex}" 
                    	colortype="default" idx="${attrindex}"
                    	attrtype="${normindex}" value="${(attr.id)!''}"  <#if edit??> disabled </#if>  
                    	<#if (attr.checked)?? && (attr.checked)?string == "true">checked</#if>>${(attr.name)!''}
                    -->   
                    <input name="attr_${attrindex}" type="checkbox" id="attr_${attrindex}" 
                    	colortype="default" idx="${attrindex}"
                    	attrtype="${normindex}" value="${(attr.id)!''}"  
                    	<#if (attr.checked)?? && (attr.checked)?string == "true">checked</#if>>${(attr.name)!''}
                </label>
                </#list>
                
                <#if !edit?? && isColor>
                <#-- 自定义颜色 -->
                 <label style="margin: 0px 8px;" data-custom-color="true">
                 	<!-- ======================自定义属性数据 bg====================== -->
				    <input id="norm_id_${normindex}_${attrindex+1}" name="norm_id_${normindex}_${attrindex+1}" value="${(norm.id)!}"type="hidden"/>
				    <input id="norm_name_${normindex}_${attrindex+1}" name="norm_name_${normindex}_${attrindex+1}" value="${(norm.name)!}" type="hidden"/>
				    <input id="type_attr_${normindex}_${attrindex+1}" name="type_attr_${normindex}_${attrindex+1}" value="2" type="hidden"/>
				    <input id="attr_name_${normindex}_${attrindex+1}" name="attr_name_${normindex}_${attrindex+1}" value="${(attr.name)!}" type="hidden"/>
				    <input id="attr_id_${normindex}_${attrindex+1}" name="attr_id_${normindex}_${attrindex+1}" value="${(attr.id)!}" type="hidden"/>
				    <input id="image_${normindex}_${attrindex+1}" name="image_${normindex}_${attrindex+1}" type="hidden"/>
				    <!-- ======================自定义属性数据 ed====================== -->
                 	 <input name="attr_${attrindex+1}" type="checkbox" 
	                	colortype="custom" idx="${attrindex+1}"
	                	id="attr_${attrindex+1}" attrindex="${attrindex+1}" 
	                	attrtype="${normindex}" value="${maxattrid+1}" />
                	<input name="cuscolr" placeholder="其它颜色" style="width: 60px;" type="text"/>
            	</label>
                </#if>
                
            </div>
            </#if>
        </div>
    </div>
</#list>
</#if>

<#--没有颜色规格时不显示-->
<#if hasColor>
 <div class="skudiv" name="skudiv">
 	<div class="table-scrollable">
      <table class="table table-striped table-bordered">
     	 <thead>
     	 	<tr>
     	 		<th width="15%">颜色</th>
     	 		<th width="35%" colspan="2">规格图片</th>
     	 	</tr>
    	 </thead>
    	 <tbody>
	   	 	<#if edit?? && colorAttr??>
				<#list colorAttr as attr>
				<#assign colortype='custom'>
				<#if (attr.typeAttr)==1>
					<#assign colortype='default'>
				<#else>
					<#assign colortype='custom'>
				</#if>

				<#list normList as norm>
					<#list norm.attrList as normattr>
						<#if normattr.id == attr.attrId>
							<#assign attridx =normattr_index>
						</#if>
					</#list>
				</#list>
				
				
				<tr id="skuAttrTr_${attr.attrId}">
					<td style="text-align: center">${attr.name}
						<input name="skuattrid_${attr.attrId}" value="${attr.attrId}" type="hidden">
					</td>
					<td>
						<div name="skupicfile" action="" index="${waitShow_index!''}" 
							multiparam='{"url":"${currentBaseUrl}uploadSKUImage?attrid=${attr.attrId}&uploadtype=2&productId=${product.id}&colortype=${colortype}&normindex=0&attrindex=${attridx}","validate":{"fileSize":{"value":2048000,"errMsg":"上传图片不允许超过2M"}, "fileMaxNum":{"value":5,"errMsg":"上传图片最多不能超过5张"},"fileType":{"value":"img","errMsg":"上传图片后缀只支持:image、gif、jpeg、jpg、png"}},"callback":"skuuploadCallback"}'>
					    </div>
					</td>
					<td>
						<a rel='gallery'
							class='boxer' 
							<#if (attr.image)??>
							href="${domainUrlUtil.imageResources}${(attr.image)!}"
							<#else>
							href="${domainUrlUtil.urlResources}/resources/seller/images/nopic.png"
							</#if>
							>
							<img style='max-width: 40px;' title="点击查看"
								onerror="this.src='${domainUrlUtil.urlResources}/resources/seller/images/nopic.png'"
								original-url="${domainUrlUtil.imageResources}${(attr.image)!}"
								src="${domainUrlUtil.imageResources}${(attr.image)!''}"
								/>
						</a>
					</td>
				</tr>
				</#list>
			<#else>
		   	 	<tr>
		   	 		<td colspan="2" style="color: #ccc;text-align: center;">
						请选择颜色
					</td>
		   	 	</tr>
			</#if>
    	 </tbody>
     </table>
    </div>
 </div>
  <input type="hidden" name="skupics" id="skupics">
</#if>

    <div class="skuinfo" name="dyTable">
    	<div class="table-scrollable">
          <table class="table table-striped table-bordered">
         	 <thead>
                <tr>
                    <#if column?? && column?size &gt; 0>
                    <#list column as col>
                        <th>${col!''}</th>
                    </#list>
                    </#if>
                    <th>sku</th>
                    <th>库存</th>
                    <th>PC价格</th>
                    <th>mobile价格</td>
                    <th>重量(kg)</td>
                    <th>长度(cm)</td>
                    <th>宽度(cm)</td>
                    <th>高度(cm)</td>
                    <th>启用</td>
                </tr>
              </thead>
            <tbody>
            	<#if !edit??>
            	<tr>
            		<td colspan="9" style="color: #ccc;text-align: center;">
						请选择规格
					</td>
            	</tr>
            	<#else>
		            <#if goods?? && goods?size &gt; 0>
		            <#list goods as good>
		            	<#if good_index == goods?size -1>
		           		 <script>
		        			$("<input type='hidden' name='skunum' value='${goods?size}' />").appendTo($("#normDiv"));
		           		 </script>
		        		</#if>
		            <tr name="normAttrTr">
		                <#if good.normId1??>
			                <td style='text-align: center;'>
				                ${(good.normName1)!''}
				            	<input name="normAttrId_${good_index}_0" type="hidden" value="${(good.normId1)!''}">
				            	<input name="normName_${good_index}_0" type="hidden" value="${(column[0])!''}">
				            	<input name="normValue_${good_index}_0" type="hidden" value="${(good.normName1)!''}">
			                </td>
		                </#if>
		               	 <#if good.normId2??>
			                <td style='text-align: center;'>
								${(good.normName2)!''}
				            	<input name="normAttrId_${good_index}_1" type="hidden" value="${(good.normId2)!''}">
				            	<input name="normName_${good_index}_1" type="hidden" value="${(column[1])!''}">
				            	<input name="normValue_${good_index}_1" type="hidden" value="${(good.normName2)!''}">
			                </td>
		                </#if>
		                <td>
		                	<div class="form-group nomargin">
		                   		<input type="text" 
		                   			required
	                   			 	data-bv-stringlength="true"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="40"
                                    data-bv-stringlength-message="sku 2-40位长度"
		                   			id="inventory_details_sku_${good_index}" 
		                   			name="inventory_details_sku_${good_index}" 
		                   			value="${(good.sku)!''}" class="styleSku form-control">
		                	</div>
		                </td>
		                <td>
		                    <div class="form-group nomargin">
		                   		<input type="text" 
		                   			required
									min="1"
									max="999999"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="库存为整数，取值1-999999"
                             	  	data-bv-lessthan-message="库存数必须小于999999"
                             	  	data-bv-greaterthan-message="库存不能为负" 
                             	  	
		                   			id="inventory_details_stock_${good_index}" 
		                   			name="inventory_details_stock_${good_index}" 
		                   			value="${(good.stock)!''}" class="styleStock form-control">
		                	</div>
		                </td>
		                <td>
							<div class="form-group nomargin">
		                   		<input type="text" 
		                   			required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0.01"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.01"
	                               	
		                   			id="inventory_details_pprice_${good_index}" 
		                   			name="inventory_details_pprice_${good_index}" 
		                   			value="${(good.pcPrice)!''}" class="stylePrice form-control">
		                	</div>                
		                </td>
		                <td>
		                    <div class="form-group nomargin">
		                   		<input type="text" 
		                   			required
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的金额"
									min="0.01"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$"
                             	  	data-bv-regexp-message="金额保留两位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="金额必须小于999999"
	                                data-bv-greaterthan-inclusive="true"
	                               	data-bv-greaterthan-message="金额必须大于0.01"
	                               	
		                   			id="inventory_details_mprice_${good_index}" 
		                   			name="inventory_details_mprice_${good_index}" 
		                   			value="${(good.mobilePrice)!''}" class="stylePrice form-control">
		                	</div>
		                </td>
		                <td>
		                    <div class="form-group nomargin">
		                   		<input type="text" 
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的重量"
									min="0.000"
									max="999999"
									pattern="^(([1-9]+)|([0-9]+\.?[0-9]{1,3}))$"
                             	  	data-bv-regexp-message="重量保留三位小数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="重量必须小于999999"
	                               	
		                   			id="inventory_details_weight_${good_index}" 
		                   			name="inventory_details_weight_${good_index}" 
		                   			value="${(good.weight)!'0.000'}" class="styleWeight form-control">
		                	</div>
		                </td>
		                <td>
		                    <div class="form-group nomargin">
		                   		<input type="text" 
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的长度"
									min="0"
									max="999999"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="长度必须为整数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="长度必须小于999999"
		                   			id="inventory_details_length_${good_index}" 
		                   			name="inventory_details_length_${good_index}" 
		                   			value="${(good.length)!0}" class="styleLength form-control">
		                	</div>
		                </td>
		                <td>
		                    <div class="form-group nomargin">
		                   		<input type="text" 
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的宽度"
									min="0"
									max="999999"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="宽度必须为整数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="宽度必须小于999999"
		                   			id="inventory_details_width_${good_index}" 
		                   			name="inventory_details_width_${good_index}" 
		                   			value="${(good.width)!0}" class="styleWidth form-control">
		                	</div>
		                </td>
		                <td>
		                    <div class="form-group nomargin">
		                   		<input type="text" 
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的高度"
									min="0"
									max="999999"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="高度必须为整数" 
									data-bv-lessthan-inclusive="true"
	                                data-bv-lessthan-message="高度必须小于999999"
		                   			id="inventory_details_height_${good_index}" 
		                   			name="inventory_details_height_${good_index}" 
		                   			value="${(good.height)!0}" class="styleHeight form-control">
		                	</div>
		                </td>
		                
		                <td>
		                	<input type="checkbox" class="form-control" name="goods_enable_${good_index}" 
		                		value="1" style="margin:0px"
		                		<#if (good.state)?? && (good.state)==1>checked</#if> />
		                </td>
		            </tr>
		            </#list>
		            </#if>
            	</#if>
            </tbody>
        </table>
        </div>
    </div>
    
    <input name="normNum" id="normNum" value="${normNum}" type="hidden"/>
    <input name="normAttrNum" id="normAttrNum" type="hidden"/>
</div>