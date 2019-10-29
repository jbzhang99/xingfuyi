jQuery(function() {
	//disable已经选择的区域信息
	var count = jQuery("#" + trans_city_type+"_trans_city_info table tr").not(".trtitle").length;
	log.i("count:"+count);
	for (var i = 0; i < count; i++) {
		var ids = jQuery("#" + trans_city_type+"_city_ids" + i).val().split(",");
		log.i("选择器："+"#" + trans_city_type+"_city_ids" + i);
		
		//不是当前地区列的数据不可编辑
		log.i("trans_index:"+trans_index);
		if (i != Number(trans_index)) {
			jQuery(".area_box :checkbox").each(function() {
				var id = jQuery(this).val();
				for (var i = 0; i < ids.length; i++) {
					if (ids[i] == id) {
						$(this).attr("disabled", "disabled");
						$(this).siblings("span").css("color","gray");
					}
				}
			});
		} else {
			//是当前地区的数据默认选中
			jQuery(".area_box :checkbox").each(function() {
				var id = jQuery(this).val();
				for (var i = 0; i < ids.length; i++) {
					if (ids[i] == id) {
						jQuery(this).attr("checked", true);
					}
				}
			});

		}
	}
	jQuery(".area_box_main>ul>li").each(function() {
		var count = jQuery(this).find(".area_level :checkbox[checked='checked']").length;
		if (count > 0) {
			jQuery(this).find("b").html("(" + count + ")");
		}
	});
	jQuery(".area_box_main li span").click(function() {
//		jQuery(".area_box_main li").removeClass("this");
		jQuery(".area_level").hide();
//		jQuery(this).parent().addClass("this");
		jQuery(this).parent().find(".area_level").show();
	});
	jQuery(".area_before>:checkbox").click(function() {
		if (jQuery(this).attr("checked") == "checked") {
			jQuery(this).parent().parent().find(":checkbox[disabled!=true]").attr("checked", true);
			jQuery(this).parent().parent().find(":checkbox[id^=province_]").each(function() {
				var count = jQuery(this).parent().find(":checkbox[id^=city_][disabled!=true]").length;
				if (count > 0) {
					jQuery(this).parent().find("b").html("(" + count + ")");
				}
			})
		} else {
			jQuery(this).parent().parent().find(":checkbox[disabled!=true]").attr("checked", false);
			jQuery(this).parent().parent().find(":checkbox[id^=province_]").each(function() {
				jQuery(this).parent().find("b").html("");
			})
		}
	});
	jQuery(":checkbox[id^=province_]").click(function() {
		if (jQuery(this).attr("checked") == "checked") {
			jQuery(this).parent().find(":checkbox[id^=city_][disabled!=true]").attr("checked", true);
			var count = jQuery(this).parent().find(":checkbox[id^=city_][disabled!=true]").length;
			if (count > 0) {
				jQuery(this).parent().find("b").html("(" + count + ")");
			}
		} else {
			jQuery(this).parent().find(":checkbox[id^=city_][disabled!=true]").attr("checked", false);
			jQuery(this).parent().find("b").html("");
		}
		var count = jQuery(this).parent().parent().parent().parent().find(":checkbox[id^=province_][checked='checked']").length;
		var total_count = jQuery(this).parent().parent().parent().parent().find(":checkbox[id^=province_]").length;
		if (count == total_count) {
			jQuery(this).parent().parent().parent().parent().find(":checkbox[id^=group_]").attr("checked", true);
		} else {
			jQuery(this).parent().parent().parent().parent().find(":checkbox[id^=group_]").attr("checked", false);
		}
	});
	jQuery(":checkbox[id^=city_]").click(function() {
		var count = jQuery(this).parents(".area_box_main").children().find(":checkbox[id^=city_][checked='checked']").length;
		var total_count = jQuery(this).parents(".area_box_main").children().find(":checkbox[id^=city_]").length;
		if (count == total_count) {
			jQuery(this).parents(".area_box_main").siblings(".area_before").find(":checkbox[id^=province_]").attr("checked", true);
		} else {
			jQuery(this).parents(".area_box_main").siblings(".area_before").find(":checkbox[id^=province_]").attr("checked", false);
		}
		var p_count = jQuery(this).parents().find(":checkbox[id^=province_][checked='checked']").length;
		var p_total_count = jQuery(this).parents().find(":checkbox[id^=province_]").length;
		if (p_count == p_total_count) {
			jQuery(this).parents().find(":checkbox[id^=group_]").attr("checked", true);
		} else {
			jQuery(this).parents().find(":checkbox[id^=group_]").attr("checked", false);
		}
	});

});
function generic_area() {
	var trans_city_type = jQuery("#trans_city_type").val(); //no_trans
	var trans_index = jQuery("#trans_index").val(); //0
	var citys = "";
	var city_ids = "";
	jQuery(".area_box :checkbox[id^=city_]:checked").each(function() {
		citys += jQuery(this).attr("city_name") + "、";
		city_ids = jQuery(this).val() + "," + city_ids;
	});
	if(citys.length>0)
		citys = citys.substring(0,citys.length-1);
	var the_id = trans_city_type + trans_index;
	var city_id = trans_city_type + "_city_ids" + trans_index;
	var city_names = trans_city_type + "_city_names" + trans_index;
	
	log.i(the_id+"|"+citys);
	
	jQuery("#" + the_id).html(citys);
	jQuery("#" + city_id).val(city_ids);
	jQuery("#" + city_names).val(citys);
	
	log.i("city_id:"+city_id+"|"+"city_names:"+city_names);
	jQuery(".area_box").remove();
	jQuery("#editbyarea").hide();
}