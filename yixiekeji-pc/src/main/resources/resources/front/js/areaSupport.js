/**
 * 省市级联js插件<br>
 * 在引用此js前需要引用JQuery库
 * @author zhaihl
 * @param {} options
 */
function AreaSupport(options) {
	var this_ = this;
	if(!options.domain)
		throw new Error('请配置域名');
	var config = {
		//省默认class
		pClass : null,
		//市默认class
		cClass : null,
		//地区默认class
		aClass : null,
		compent : 'td_area',
		domain : '',
		//默认省
		defaultProvince : null,
		//默认市
		defaultCity : null,
		//默认地区
		defaultArea : null,
		//是否需要加载到市
		cityRequired : true,
		//是否需要加载到地区
		areaRequired : true,
		//要提交的省的name属性
		provinceName : '',
		//要提交的市的name属性
		cityName : '',
		//要提交的地区的name属性
		areaName : '',
		// 省获取数据URL
		provinceURL : options.domain + '/system/getProvince.html',
		// 市获取数据URL
		cityURL : options.domain + '/system/getChildrenArea.html',
		// 地区获取数据URL
		areaURL : options.domain + '/system/getChildrenArea.html'
	};

	$.extend(config, options);
	this.cfg = config;

	this.province = {
		init : function() {
			var select = $("<select id='province_' class='" + config.pClass
					+ "' value='" + config.defaultProvince
					+ "' name='"+config.provinceName+"'></select>")
					.appendTo($("body"));
			$.ajax({
				url : config.provinceURL,
				async : false,
				success : function(e) {
					var data = (eval(e));
					$(data).each(function(index, ele) {
						if (config.defaultProvince
								&& (Number(ele.id) == Number(config.defaultProvince))) {
							$("<option id='p_"
									+ index
									+ "' value='"
									+ ele.id
									+ "' selected='selected'>"
									+ ele.regionName
									+ "</option>")
							.appendTo(select);
						} else {
							$("<option id='p_"
									+ index
									+ "' value='"
									+ ele.id
									+ "'>"
									+ ele.regionName
									+ "</option>")
							.appendTo(select);
						}
					});
				}
			});
			
			if(config.cityRequired){
				$("#province_").change(function(){
					doLoading('province_',config);
					this_.city.init();
					
					//清空地区选择
					if($("#area_").length > 0){
						if($("#area_").get(0).options.length > 1){
							var opts = $("#area_").get(0).options;
							$(opts).each(function(){
								if($(this).attr('id')!='area_opt_0')
									$(this).remove();
							});
						}
					}
				});
				
			}
			return select;
		}
	};
	
	this.city = {
		init : function(def) {
			if(!def)
				def = config.defaultCity;
			var parentid = $("#province_").val();
			var select = $("<select id='city_' class='" + config.cClass
					+ "' value='" + config.defaultCity + "' name='"
					+ config.cityName
					+ "'><option value='-1' id='city_opt_0'>请选择...</option></select>");
			$.ajax({
				url : config.cityURL+'?p='+parentid+'&type=2',
				async : false,
				success : function(e) {
					var data = (eval(e));
					$(data).each(function(index, ele) {
						if(def && def == ele.id){
							$("<option id='c_" + index + "' value='"+ele.id+"' selected='selected'>" + 
									ele.regionName + "</option>")
									.appendTo(select);
						} else{
							$("<option id='c_" + index + "' value='"+ele.id+"'>" + 
									ele.regionName + "</option>")
									.appendTo(select);
						}
					});
					//加载完成后去掉loading
					if($('#loadingImg_').length > 0){
						$('#loadingImg_').remove();
					}
				}
			});
			
			$("#"+config.compent).children("select[id='city_']").remove();
			$("#province_").after(select);
			
			if(config.areaRequired){
				$("#city_").change(function(){
					doLoading('city_',config);
					this_.area.init($(this).val());
				});
			}
			
			return select;
		}
	};
	
	this.area = {
		init : function(def) {
			if(!def)
				def = config.defaultArea;
			var parentid = $("#city_").val();
			var select = $("<select id='area_' class='" + config.aClass
					+ "' value='" + config.defaultArea + "' name='"
					+ config.areaName
					+ "'><option value='-1' id='area_opt_0'>请选择...</option></select>");
			$.ajax({
				url : config.areaURL+'?p='+parentid+'&type=3',
				async : false,
				success : function(e) {
					var data = (eval(e));
					$(data).each(function(index, ele) {
						if (def && def == ele.id) {
							$("<option id='a_" + index
									+ "' value='" + ele.id
									+ "' selected='selected'>"
									+ ele.regionName
									+ "</option>").appendTo(
							select);
						} else {
							$("<option id='a_" + index
									+ "' value='" + ele.id
									+ "'>" + ele.regionName
									+ "</option>").appendTo(
							select);
						}
					});
					//加载完成后去掉loading
					if($('#loadingImg_').length > 0){
						$('#loadingImg_').remove();
					}
				}
			});
			$("#"+config.compent).children("select[id='area_']").remove();
			$("#city_").after(select);
			
			return select;
		}
	};
}

function doLoading(o,cfg){
	if(!cfg)
		return;
	var html_ = "<img src='"+cfg.domain
			+"/resources/front/img/loading.gif' style='position: " +
					"absolute;margin-left: 12px;margin-top: 5px;' id='loadingImg_'/>";
	if($('#loadingImg_').length == 0){
		$('#'+o).after(html_);
	}
}

AreaSupport.prototype.getProvince = function() {
	return this.province.init();
}

AreaSupport.prototype.getCity = function(def) {
	return this.city.init(def);
}

AreaSupport.prototype.getArea = function(def) {
	return this.area.init(def);
}
