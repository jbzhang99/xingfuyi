<script src="${(domainUrlUtil.staticResources)!}/resources/seller/easyui/mask.js"></script>

<script language="javascript">
	var attrMap = {};
	var skuMap = {};
// 	var box_ = false;
	
	/**
	 * 初始化选择的规格数据
	 */
	function initSelectedNorm(){
		//清空所选规格
		var checkbox_ = $("#normDiv").find("input[type='checkbox']");
		checkbox_.removeAttr("checked");
        $("#normDiv div").find("input[type='hidden']").val("");
        $(".skuinfo").find("table tbody").empty();
        removeValidator();
        
        checkbox_.each(function(){
	        if($(this).parent("label").data("custom-color") == true){
		        var customcolorLabel_ = $(this).parent("label");
		        customcolorLabel_.find("input[name='cuscolr']").val("").removeAttr("readonly");
		        
		        customcolorLabel_.nextAll().remove();
	        }
        });
        
        var skupic_ = 
        	'<tr>'+
        	'	<td colspan="2" style="color: #ccc; text-align: center;">请选择颜色</td>'+
        	'</tr>';
        $(".skudiv").find("table tbody").html(skupic_);
        
    	attrMap = {};
    	skuMap = {};
	}
	
    $(function () {
    	<#if product?? && (product.isNorm)?string == "2">
        $('#normDiv').show();
        $(".sku").hide();
        $(".price").hide();
        $(".stock").hide();
        $(".normtips").show();
        </#if>
        
        //是否启用规格
        $("input[name^='isNorm']").change(function () {
            if (2 == $(this).val()) {
                $('#normDiv').show();
                $('.sku').hide();
                $(".price").hide();
                $(".stock").hide();
                $(".normtips").show();
            } else {
                $('#normDiv').hide();
                $('.sku').show();
                $(".price").show();
                $(".stock").show();
                $(".normtips").hide();
                
                initSelectedNorm();
            }
        });
        
        //当前分类下无规格时，不可选择规格 
        <#if !normList?? || normList?size &lt;= 0>
        $('input[name="isNorm"]').eq(0).attr("checked",true);
        $('#isNormHidden').val(1);
        $('input[name="isNorm"]').attr("disabled",true);
        </#if>
        
//         $(document).on('blur','.styleStock', function () {
//             if($(this).val() == ''){
//                 $(this).val(0);
//             }
//         });

// 		var skuindex_ = 0;
        $(document).on('change','.styleSku', function () {
        	if(!$(this).val()){
        		return;
        	}
        	var normtr = $(".table-scrollable").find("tr[name='normAttrTr']");
			var allsku_ = new Array();
	    	normtr.each(function(e,idx){
	    		var skuval_ = $(this).find("input[id^='inventory_details_sku_']").val();
    			if(skuval_){
    				allsku_.push(skuval_);
    			}
	    	});
	  		log.i(allsku_);
	  		if(isRepeat(allsku_)){
// 	  			$(this).val($(this).val()+"_"+skuindex_);
	  			$(this).val("");
	  			$("#addform").bootstrapValidator('updateStatus', $(this).prop("name"), 'NOT_VALIDATED').
	  				bootstrapValidator('validateField', $(this).prop("name"));
	  			$.messager.show({
                    title:'提示',
                    msg:'sku不允许重复',
                    showType:'show'
	            });
// 	  			skuindex_ ++;
	  		}
        });

        $(document).on('blur', '.stylePrice',function () {
            if(parseFloat($(this).val()) < parseFloat($('#protectedPrice').val())){
                $.messager.show({
                	msg:"货品价格不能小于保护价",
                	showType:"show",
                	title:"提示"
                });
                $(this).val(parseFloat($('#protectedPrice').val()));
            }
			format(this);
			var index = $(".stylePrice").index(this);
			if (index == 0) {
				syncValueWithClass($(this).val(), ".stylePrice");
			}
        });

        //自定义规格事件
        <#if normList?? && normList?size == 2>
        <#assign map0 = normList[0]>
        <#assign map1 = normList[1]>
        <#assign title0 = map0.name>
        <#assign title1 = map1.name>
        <#assign list0 = map0.attrList>
        <#assign list1 = map1.attrList>
        <#list list0 as list0>
        <#assign id0 = list0.id>
        <#assign name0 = list0.name>
        <#if list0.checked?? && (list0.checked)?string=='true'>
        if (0 in attrMap) {
            attrMap[0]["names"].push('attr_${list0_index}');
        } else {
            attrMap[0] = {};
            attrMap[0]["title"] = '${title0!''}';
            attrMap[0]["names"] = new Array();
            attrMap[0]["names"].push('attr_${list0_index}');
        }
        </#if>
        </#list>

        <#list list1 as list1>
        <#assign id1 = list1.id>
        <#assign name1 = list1.name>
            <#if list1.checked?? && (list1.checked)?string=='true'>
            if (1 in attrMap) {
                attrMap[1]["names"].push('attr_${list1_index}');
            } else {
                attrMap[1] = {};
                attrMap[1]["title"] = '${title1!''}';
                attrMap[1]["names"] = new Array();
                attrMap[1]["names"].push('attr_${list1_index}');
            }
            </#if>
        </#list>

        </#if>
        
        $(document).on('change', 'input[name^="attr_"]',function () {
        	var this_ = this;
        	//颜色属性
        	if($(this).closest('.normtype_color').length>0){
        		//如果是自定义属性，必须先输入值
        		if($(this).attr('colortype')&& $(this).attr('colortype') == 'custom'){
        			var sibval_ = $(this).siblings(":visible").val();
        			if(!sibval_){
        				$(this).attr("checked",false);
        				$.messager.alert('提示','请先输入颜色名称');
        				return;
        			} else{
        				if($(this).is(":checked")){
	        				var normidx_ = Number($(this).attr('attrtype'));
	        				var attridx_ = Number($(this).attr('attrindex'))+1;
	        				var colval_ = Number($(this).val())+1;
	        				//生成下一个输入框
	        				var nextinput_ = "<label style='margin: 0px 8px;'>" +
			        				'<!-- ======================自定义属性'+attridx_+'数据 bg====================== -->'+
			        				'<input id="norm_id_'+normidx_+'_'+attridx_+'" name="norm_id_'+normidx_+'_'+attridx_+'" type="hidden"/>'+
			        				'<input id="norm_name_'+normidx_+'_'+attridx_+'" name="norm_name_'+normidx_+'_'+attridx_+'" type="hidden"/>'+
			        				'<input id="type_attr_'+normidx_+'_'+attridx_+'" name="type_attr_'+normidx_+'_'+attridx_+'" type="hidden"/>'+
			        				'<input id="attr_name_'+normidx_+'_'+attridx_+'" name="attr_name_'+normidx_+'_'+attridx_+'" type="hidden"/>'+
			        				'<input id="attr_id_'+normidx_+'_'+attridx_+'" name="attr_id_'+normidx_+'_'+attridx_+'" type="hidden"/>'+
			        				'<input id="image_'+normidx_+'_'+attridx_+'" name="image_'+normidx_+'_'+attridx_+'" type="hidden"/>'+
			        				'<!-- ======================自定义属性'+attridx_+'数据 ed====================== -->'+
	        				
	        						"<input name='attr_"+attridx_+"' idx='"+attridx_+"' type='checkbox' colortype='custom'" +
	        						"	id='attr_"+attridx_+"' attrindex='"+attridx_+"' attrtype='"+normidx_+"' value='"+colval_+"' />" +
	        						"<input name='cuscolr' placeholder='其它颜色' style='width: 60px;' type='text'/>" +
	        						"</label>";
	        				$(this).parent().after(nextinput_);
	        				//readonly
	        				$(this).siblings("input[name='cuscolr']").prop("readonly",true);
        				} else{
        					//移除
        					$(this).parent().remove();
        				}
        			}
        		}
        		//生成sku上传图片页面代码
	        	generateSKUHtml(this_);
        		generateNormHTML(this_);
        		
        		//加载图片上传控件
        	    $("[name=skupicfile]").skuupload();
        	} else{
        		generateNormHTML(this_);
        	}
        	
        	//属性值赋值
        	setValues(this);
        });


        <#--规格属性编辑,只有一列选中的时候,第二列禁用-->
        var attrType0 = $("input[attrtype='0'");
        var attrType1 = $("input[attrtype='1'");
        var isReadOnly0 = false;
        var isReadOnly1 = false;
        if(attrType0 && attrType0.length > 0){
            for(var i = 0; i < attrType0.length; i ++){
                isReadOnly0 = $(attrType0[i]).is(':checked');
                if (isReadOnly0){
                    break;
                }
            }
        }
        if(attrType1 && attrType1.length > 0){
            for(var i = 0; i < attrType1.length; i ++){
                isReadOnly1 = $(attrType1[i]).is(':checked');
                if (isReadOnly1){
                    break;
                }
            }
        }

        if(!isReadOnly0 && isReadOnly1){
            for(var i=0; i < $("input[attrtype='0'").length; i ++){
                var attrtype = $("input[attrtype='0'")[i];
                $(attrtype).attr('disabled','true');
            }
        }

        if(isReadOnly0 && !isReadOnly1){
            for(var i=0; i < $("input[attrtype='1'").length; i ++){
                var attrtype = $("input[attrtype='1'")[i];
                $(attrtype).attr('disabled','true');
            }
        }
        
        $(document).on("keyup", ".stylePrice",function() {
			var index = $(".stylePrice").index(this);
			if (index == 0) {
				syncValueWithClass($(this).val(), ".stylePrice");
			}
		});
		
    });

	/**
	 * 对相应规格属性赋值
	 */
	function setValues(obj){
		var this_ = $(obj);
		var attrindex = this_.attr("idx");
		var normindex = this_.attr('attrtype');
		
		var attrid = this_.val();
		var normid = this_.closest(".normattrs").find("label:first").attr('normid');;
		
		var normname = this_.closest(".normattrs").find("label:first").attr('normname');
		var typeAttr = this_.attr("colortype");
		
		var attrName = "";
		var colorinput = this_.siblings("input[name='cuscolr']");
	    if(colorinput.length > 0){
	    	//自定义属性
	    	attrName = colorinput.val();
	    } else{
	    	attrName = this_.parent().text().trim();
	    }
	    
	    if($(obj).is(":checked")){
			$("#norm_id_"+normindex+"_"+attrindex).val(normid);
			$("#norm_name_"+normindex+"_"+attrindex).val(normname);
			$("#type_attr_"+normindex+"_"+attrindex).val(typeAttr);
			$("#attr_name_"+normindex+"_"+attrindex).val(attrName);
			$("#attr_id_"+normindex+"_"+attrindex).val(attrid);
	    } else{
			$("#norm_id_"+normindex+"_"+attrindex).val(null);
			$("#norm_name_"+normindex+"_"+attrindex).val(null);
			$("#type_attr_"+normindex+"_"+attrindex).val(null);
			$("#attr_name_"+normindex+"_"+attrindex).val(null);
			$("#attr_id_"+normindex+"_"+attrindex).val(null);
	    }
	}
	    
	function syncValueWithClass(val, cls) {
		<#if !edit??>
		if(isNaN(val) || val == 0)
			return;
		$(cls).each(function() {
			$(this).val(val);
		});
		</#if>
	}
	    
	function format(obj) {
	    if($(obj).val() && !isNaN($(obj).val())){
		    $(obj).val(parseFloat(obj.value));
	    }
	}

    function generateSKUHtml(obj){
       	var attrType = $(obj).attr("attrtype");
        var name = $(obj).attr("name");
        if ($(obj).is(":checked")) {
        	//图片默认值
        	//移除之前的
        	var existspics_ = $("#normDiv").find("input[name='skupic_"+$(obj).val()+"']");
        	if(existspics_.length>0){
        		existspics_.remove();
        	}
        	
        	var html_ = "<input name='skupic_"+$(obj).val()+"' attrid='"+
        		$(obj).val()+"' type='hidden' value='-1' colortype='"+$(obj).attr('colortype')+"'>";
        	$("#custom-color-ul").after(html_);
            if (attrType in skuMap) {
                skuMap[attrType]["names"].push(name);
            } else {
                skuMap[attrType] = {};
                var colorinput = $(obj).siblings("input[name='cuscolr']");
                var title = "";
                if(colorinput.length > 0){
                	title = $(obj).parent().parent().parent().prev().attr("normname");
                } else{
	                title = $($(obj).parent().parent().parent().
	                		parent().children()[0]).text().trim();
	                title = title.substring(0, title.length - 1);
                }
                skuMap[attrType]["title"] = title;
                skuMap[attrType]["names"] = new Array();
                skuMap[attrType]["names"].push(name);
            }
        } else {
//         	//删除其图片
//         	$("#normDiv").find("input[name='skupic_"+$(obj).val()+"']").remove();
            if (attrType in skuMap) {
                //删除sku信息
                skuMap[attrType]["names"].remove(name);
                if (skuMap[attrType]["names"].length == 0) {
                    delete skuMap[attrType];
                }
            }
        }
        var columnName = '';//循环构造列名
        var keys = Object.keys(skuMap);
        if (keys && keys.length > 0) {
            for (var i = 0; i < keys.length; i++) {
                columnName += "<th width=\"15%\">" + skuMap[keys[i]]["title"] + "</th>";
            }
        }

        var htmlArray = new Array();
        getSKUTrArray(htmlArray, '', skuMap, 0);
      
        $('div[name="skudiv"]').remove();//删除动态表格
         var skutableHtml = "<div class=\"skudiv\" name=\"skudiv\">" +
        		"<div class=\"table-scrollable\">" +
                "<table name=\"normTable\" class=\"table table-striped table-bordered\">" +
                "<thead>" +
	                "<tr>"
	              		+ columnName +
		             	"<th width=\"35%\" colspan=\"2\">规格图片</th>" +
	             	"</tr>" +
             	"</thead>";

        skutableHtml += "<tbody>";  
        if (htmlArray && htmlArray.length > 0) {
            for (var i = 0; i < htmlArray.length; i++) {
            	skutableHtml += htmlArray[i];
            }
        }
        
        skutableHtml += "</tbody></table></div></div>";
        $('.normattrs:last').after(skutableHtml);
        //初始化所有图片隐藏域值
        $("#normDiv").find("input[name^='skupic_']").remove();
        
//         if(box_){
//         	$(".boxer").boxer();
//         }
    }
    
    function generateNormHTML(obj){
		var attrType = $(obj).attr("attrtype");
        var name = $(obj).attr("name");
        if ($(obj).is(":checked")) {
            if (attrType in attrMap) {
                attrMap[attrType]["names"].remove(name);
                attrMap[attrType]["names"].push(name);
            } else {
                attrMap[attrType] = {};
                var colorinput = $(obj).siblings("input[name='cuscolr']");
                var title = "";
                if(colorinput.length > 0){
                	title = $(obj).parent().parent().parent().prev().attr("normname");
                } else{
	                title = $($(obj).parent().parent().parent().
	                		parent().children()[0]).text().trim();
	                title = title.substring(0, title.length - 1);
                }
                attrMap[attrType]["title"] = title;
                attrMap[attrType]["names"] = new Array();
                attrMap[attrType]["names"].push(name);
            }
        } else {
            if (attrType in attrMap) {
                attrMap[attrType]["names"].remove(name);
                if (attrMap[attrType]["names"].length == 0) {
                    delete attrMap[attrType];
                }
            }
        }
        var columnName = '';//循环构造列名
        var keys = Object.keys(attrMap);
        if (keys && keys.length > 0) {
            for (var i = 0; i < keys.length; i++) {
            	 columnName += "<th>" + attrMap[keys[i]]["title"] + "</th>";
            }
        }

        var htmlArray = new Array();
        index_ = 0;
        getTrArray(htmlArray, '', attrMap, 0);
        
        $('div[name="dyTable"]').remove();//删除动态表格
        var tableHtml = "<div class=\"skuinfo\" name=\"dyTable\">" +
        		"<div class=\"table-scrollable\">" +
                "<table name=\"normTable\" class=\"table table-striped table-bordered\">" +
                "<thead>" +
	                "<tr>"
	              		+ columnName +
		                "<th>sku</th>" +
		                "<th>库存</th>" +
		                "<th>PC价格</th>" +
		             	"<th>mobile价格</th>" +
		             	"<th>重量(kg)</th>" +
		             	"<th>长度(cm)</th>" +
		             	"<th>宽度(cm)</th>" +
		             	"<th>高度(cm)</th>" +
		             	"<th>启用</td>" +
	             	"</tr>" +
             	"</thead>";

       	tableHtml += "<tbody>";  	
        if (htmlArray && htmlArray.length > 0) {
            for (var i = 0; i < htmlArray.length; i++) {
                tableHtml += htmlArray[i];
            }
        }
        
        tableHtml += "</tbody></table></div></div>";
        $('#normDiv').append(tableHtml);
       
       	var reg_ = new RegExp('norm1','gm');
        $(".skuinfo tr[name='normAttrTr']").each(function(idx_,element_){
        	var normAttrId_ = $(this).find("td:first input[name^='normAttrId_']");
        	var normName_ = $(this).find("td:first input[name^='normName_']");
        	var normValue_ = $(this).find("td:first input[name^='normValue_']");
        	
        	normAttrId_.prop("name",normAttrId_.prop("name").replace(reg_,idx_));
        	normName_.prop("name",normName_.prop("name").replace(reg_,idx_));
        	normValue_.prop("name",normValue_.prop("name").replace(reg_,idx_));
        });
        
        addValidator();
    }
    
    function removeValidator(){
    	var addform_ = $("#addform").data('bootstrapValidator');
    	var normtr = $(".table-scrollable").find("tr[name='normAttrTr']");
  		//给每行的数据加校验
    	normtr.each(function(idx){
    		addform_.removeField("inventory_details_sku_"+idx)
	    	addform_.removeField("inventory_details_stock_"+idx)
	    	addform_.removeField("inventory_details_pprice_"+idx)
	    	addform_.removeField("inventory_details_mprice_"+idx)
	    	addform_.removeField("inventory_details_weight_"+idx)
	    	addform_.removeField("inventory_details_length_"+idx)
	    	addform_.removeField("inventory_details_width_"+idx)
	    	addform_.removeField("inventory_details_height_"+idx)
    	});
    }
    
    function addValidator(){
    	var addform_ = $("#addform").data('bootstrapValidator');
    	var normtr = $(".table-scrollable").find("tr[name='normAttrTr']");
   		var delay = 200;
  		//给每行的数据加校验
    	normtr.each(function(idx){
    		//多规格情况下可能会造成系统开销，延迟处理
    		setTimeout(function(){
				addskuValid(addform_,idx);
				addStockValid(addform_,idx);
				addPriceValid(addform_,idx);
				addvolumeValiad(addform_,idx);
    		},delay);
    		delay += 800;
    	});
    }
    
    function addskuValid(){
    	var args = arguments;
    	var addform_,skusel_;
    	if(args.length == 1){
    		skusel_ = arguments[0];
    		addform_ = $("#addform").data('bootstrapValidator');
    	} else if(args.length == 2){
    		addform_ = arguments[0];
    		skusel_ = "inventory_details_sku_" + arguments[1];
    	}
    	addform_.addField(skusel_,{
	    	validators: {
				remote: {
					message: '该sku已存在',
		            type: 'post',
		            delay: 800,
		            data: $("#"+skusel_).val(),
		            url: '${currentBaseUrl}validateSKU' 
				},
	    		notEmpty: true,
  		        stringLength: {
               		min: 2,
              		max: 40,
             		message: 'sku长度2-40位'
               }
	    	}
	    }); 
    }
    
    function isRepeat(arr) {
    	if(!arr || arr.length < 1){
    		return false;
    	}
		var hash = {};
		for ( var i in arr) {
			if (hash[arr[i]]) {
				return true;
			}
			hash[arr[i]] = true;
		}
		return false;
	}
    
    function addStockValid(addform,idx){
    	addform.addField("inventory_details_stock_"+idx,{
	    	validators: {
	        	notEmpty: true,
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^\d{1,6}$/,
               	message: '库存1-6位数字'
               }
	    	}  
	    }); 
    }
    
    function addPriceValid(addform,idx){
    	addform.addField("inventory_details_pprice_"+idx,{
    		validators: {
	        	notEmpty: true,
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$/,
               	message: '请输入正确的价格，金额保留两位小数'
               },
               lessThan:{
               	message: '价格必填小于999999',
                   value: 999999
               }
	    	}  
	    }); 
    	addform.addField("inventory_details_mprice_"+idx,{
    		validators: {
	        	notEmpty: true,
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^(([1-9]+)|([0-9]+\.?[0-9]{1,2}))$/,
               	message: '请输入正确的价格，金额保留两位小数'
               },
               lessThan:{
               	message: '价格必填小于999999',
                   value: 999999
               }
	    	}  
	    }); 
    }
    
    function addvolumeValiad(addform,idx){
    	addform.addField("inventory_details_weight_"+idx,{
    		validators: {
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^(([1-9]+)|([0-9]+\.?[0-9]{1,3}))$/,
               	message: '请输入正确的重量，重量保留三位小数'
               },
               lessThan:{
               	message: '价格必填小于999999',
                   value: 999999
               }
	    	}  
	    });
    	addform.addField("inventory_details_length_"+idx,{
    		validators: {
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^\d{1,6}$/,
               	message: '请输入正确的长度，长度为整数'
               },
               lessThan:{
               	message: '长度必填小于999999',
                   value: 999999
               }
	    	}  
	    });
    	addform.addField("inventory_details_width_"+idx,{
    		validators: {
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^\d{1,6}$/,
               	message: '请输入正确的宽度，宽度为整数'
               },
               lessThan:{
               	message: '宽度必填小于999999',
                   value: 999999
               }
	    	}  
	    });
    	addform.addField("inventory_details_height_"+idx,{
    		validators: {
	        	numeric: {
	        		message:'请输入数字'
	        	},
  		        regexp: {
               	regexp: /^\d{1,6}$/,
               	message: '请输入正确的高度，高度为整数'
               },
               lessThan:{
               	message: '高度必填小于999999',
                   value: 999999
               }
	    	}  
	    });
    	
    }
    
    function getSKUTrArray(html, tds, map, level) {
//     	box_ = false;
        var keys = Object.keys(map);
        if (keys && keys.length > 0) {
            var names = map[keys[level].toString()]["names"];
            for (var i = 0; i < names.length; i++) {
                var li_ = $("[name=" + names[i] + "][attrtype=" + keys[level].toString() + "]").parent();
                var textinput_ = li_.find("input[type='text']");
                var text = textinput_.length<1?li_.text().trim():textinput_.val();
                var selected_ = $("[name=" + names[i] + "][attrtype=" + keys[level].toString() + "]").parent().find("input[type='checkbox']");
                var value = selected_.val();
                
                var colortype = selected_.attr('colortype');
                var normid = selected_.closest(".normattrs").find("label:first").attr('normid');
                var attrindex = selected_.attr('idx');
                var normindex = selected_.attr('attrtype');
                
                if (level == keys.length - 1) {
                	 var tmptds = tds + "<td style='text-align: center;'>" + text +
                     "<input name=\"skuattrid_"+value+"\" type=\"hidden\" value=\""+value+"\">" +
                     "</td>";
                     
            		 var skufilehtml_ = "<tr id=\"skuAttrTr_"+value+"\">" + tmptds +
	                    "<td>" +
	                    "<div name='skupicfile' action='' multiparam='{\"url\":\"${currentBaseUrl}uploadSKUImage?attrid="+
	                    		value+"&colortype="+colortype+"&uploadtype=1&normindex="+normindex+"&attrindex="+attrindex+"\"," +
	                    "\"validate\":{\"fileSize\":{\"value\":2048000,\"errMsg\":\"上传图片不允许超过2M\"}, " +
	                    "\"fileMaxNum\":{\"value\":1,\"errMsg\":\"只能上传一张图片\"},\"fileType\":" +
	                    "{\"value\":\"img\",\"errMsg\":\"上传图片后缀只支持:image、gif、jpeg、jpg、png\"}}," +
	                    "\"callback\":\"skuuploadCallback\"}'>"+
	                "</td>";
		                
// 		            var thiscolorpic_ = $("#normDiv").find("input[name='skupic_"+value+"']");
// 		            if(thiscolorpic_.length > 0 && thiscolorpic_.val()!=-1){
// 		            	  skufilehtml_ += "<td><a class='boxer' href='${domainUrlUtil.imageResources}"+
// 		            	  	thiscolorpic_.val()+"'><img style='max-width: 40px;' src='${domainUrlUtil.imageResources}"+
// 		            	  	thiscolorpic_.val()+"'/></a></td>";
// 		            	 box_ = true;
// 		            } else{
		           		skufilehtml_ += "<td>&nbsp;</td>";
// 		            }
		            skufilehtml_ += "</tr>";
                    html.push(skufilehtml_);
                } else {
                    tds +=  "<td style='text-align: center;'>" + text +
                            "<input name=\"skuattrid_"+value+"\" type=\"hidden\" value=\""+value+"\">" +
                            "</td>";
                   	getSKUTrArray(html, tds, map, level + 1);
                }
                if (level == 0) {
                    tds = '';
                }
            }
        }
    }
    
    function skuuploadCallback(obj){
    	//移除之前的
    	var existspics_ = $("#normDiv").find("input[name='skupic_"+obj.attrid+"']");
    	if(existspics_.length>0){
    		existspics_.remove();
    	}
    	
    	var html_ = "<input name='skupic_"+obj.attrid+"' attrid='"+obj.attrid+
    		"' value='"+obj.url+"' colortype='"+obj.colortype+"' type='hidden'>";
    	
    	log.i("设置图片路径："+"#image_"+obj.normindex+"_"+obj.attrindex);
    	$("#image_"+obj.normindex+"_"+obj.attrindex).val(obj.url);
    	$("div[name='skudiv']").after(html_);
    }
    
    var index_ = 0;
    var rows = 0;
    function getTrArray(html, tds, map, level) {
        var keys = Object.keys(map);
        
        if (keys && keys.length > 0) {
            var names = map[keys[level].toString()]["names"];
            
        	var mallPcPriceVal = $('#mallPcPrice').val();
			var malMobilePrice = $('#malMobilePrice').val();
			var weight = $('#weight').val();
			var length = $('#length').val();
			var width = $('#width').val();
			var height = $('#height').val();
            for (var i = 0; i < names.length; i++) {
  				var li_ = $("[name=" + names[i] + "][attrtype=" + keys[level].toString() + "]").parent();
                var textinput_ = li_.find("input[type='text']");
                var text = textinput_.length<1?li_.text().trim():textinput_.val();
                var value = $("[name=" + names[i] + "][attrtype=" + keys[level].toString() + "]").parent().find("input[type='checkbox']").val();
               	var curentselect_ = $("[name=" + names[i] + "][attrtype=" + 
                    	keys[level].toString() + "]")
                    	
                var currentNormname_ = curentselect_.closest(".normattrs").find("label:first").attr('normname');
// 				var curVal_ = value+"!@#"+currentNormname_+"!@#"+text;
				
                if (level == keys.length - 1) {
                    var tmptds = tds + "<td kl1=1 style='text-align: center;'>" + text +
				                            "<input name=\"normAttrId_"+index_+"_"+level+"\" type=\"hidden\" value=\""+value+"\">" +
				                            "<input name=\"normName_"+index_+"_"+level+"\" type=\"hidden\" value=\""+currentNormname_+"\">" +
				                            "<input name=\"normValue_"+index_+"_"+level+"\" type=\"hidden\" value=\""+text+"\">" +
			                            "</td>";
                    html.push(
                            "<tr name=\"normAttrTr\">" + tmptds +
                                "<td>" +
                                	"<div class=\"form-group nomargin\">" +
                                    "<input name=\"inventory_details_sku_"+index_+"\" type=\"text\" id=\"inventory_details_sku_"+index_+
                                    "\" value=\"\" class=\"styleSku form-control\" autocomplete=\"off\">" +
                                    "</div>" +
                                "</td>" +
                                "<td>" +
                               		"<div class=\"form-group nomargin\">" +
                                    "<input name=\"inventory_details_stock_"+index_+"\" type=\"text\" id=\"inventory_details_stock_"+index_+
                                    "\" value=\"\" class=\"styleStock form-control\" autocomplete=\"off\">" +
                                    "</div>" +
                                "</td>" +
                                "<td>" +
                           		    "<div class=\"form-group nomargin\">" +
                                    "<input name=\"inventory_details_pprice_"+index_+"\" type=\"text\" id=\"inventory_details_pprice_"+index_+
                                    "\" value=\"" + mallPcPriceVal + "\" class=\"stylePrice form-control\" autocomplete=\"off\">" +
                                    "</div>" +
                                "</td>" +
                                "<td>" +
                               		"<div class=\"form-group nomargin\">" +
                                    "<input name=\"inventory_details_mprice_"+index_+"\" type=\"text\" id=\"inventory_details_mprice_"+index_+
                                    "\" class=\"stylePrice form-control\" value=\""+ malMobilePrice +"\" autocomplete=\"off\">" +
                                    "</div>" +
                                "</td>" +
                                "<td>" +
	                           		"<div class=\"form-group nomargin\">" +
	                                "<input name=\"inventory_details_weight_"+index_+"\" type=\"text\" id=\"inventory_details_weight_"+index_+
	                                "\" class=\"styleWeight form-control\" value=\""+ weight +"\" autocomplete=\"off\">" +
	                                "</div>" +
	                            "</td>" +
	                            "<td>" +
	                           		"<div class=\"form-group nomargin\">" +
	                                "<input name=\"inventory_details_length_"+index_+"\" type=\"text\" id=\"inventory_details_length_"+index_+
	                                "\" class=\"styleLength form-control\" value=\""+ length +"\" autocomplete=\"off\">" +
	                                "</div>" +
	                            "</td>" +
	                            "<td>" +
	                           		"<div class=\"form-group nomargin\">" +
	                                "<input name=\"inventory_details_width_"+index_+"\" type=\"text\" id=\"inventory_details_width_"+index_+
	                                "\" class=\"styleWidth form-control\" value=\""+ width +"\" autocomplete=\"off\">" +
	                                "</div>" +
	                            "</td>" +
	                            "<td>" +
	                           		"<div class=\"form-group nomargin\">" +
	                                "<input name=\"inventory_details_height_"+index_+"\" type=\"text\" id=\"inventory_details_height_"+index_+
	                                "\" class=\"styleHeight form-control\" value=\""+ height +"\" autocomplete=\"off\">" +
	                                "</div>" +
	                            "</td>" +
                                "<td>"+
                                "	<input type=\"checkbox\" class=\"form-control\""+
                                "		name=\"goods_enable_"+index_+"\" checked style=\"margin: 0px\" value=\"1\"/>"+
                                "</td>"+
                            "</tr>");
                    
                    index_ ++;
                } else {
					//规格1
                    tds +=  "<td style='text-align: center;'>" + text +
			                    "<input name=\"normAttrId_norm1_"+level+"\" type=\"hidden\" value=\""+value+"\">" +
			                    "<input name=\"normName_norm1_"+level+"\" type=\"hidden\" value=\""+currentNormname_+"\">" +
			                    "<input name=\"normValue_norm1_"+level+"\" type=\"hidden\" value=\""+text+"\">" +
                            "</td>";
                    
                    getTrArray(html, tds, map, level + 1);
                }
                if (level == 0) {
                    tds = '';
                }
            }
        }
        
        var normDiv_ = $("#normDiv");
        normDiv_.find("input[type='hidden'][name='skunum']").remove();
       	$("<input type='hidden' name='skunum' value='" + index_ + "' />").appendTo(normDiv_);
    }
    
    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    
    function change_mobile_proce(i){
    	var price = $("#inventory_details_pprice_"+i).val();
    	if(price!=null){
    		$("#inventory_details_mprice_"+i).val(price);
    	}
    }
    
</script>