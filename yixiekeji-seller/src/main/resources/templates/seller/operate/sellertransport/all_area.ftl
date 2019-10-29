<script  type="text/javascript">
var trans_index = "${trans_index}";
var trans_city_type = "${trans_city_type}";

           //全选/全不选操作 省市
         function setRegionAllNo(regionBox,regionName){
               //alert('点击事件');
               //alert(regionName);
            //var box = document.getElementById("boxid");
            //var loves = document.getElementsByName(regionName);
            
            var loves = document.querySelectorAll("input[province_name='"+regionName+"']");
            if(regionBox.checked == false){
                for (var i = 0; i < loves.length; i++) {
                    loves[i].checked = false;
                }
            }else{
                for (var i = 0; i < loves.length; i++) {
                    loves[i].checked = true;
                }
            }
        }

          //全选/全不选操作 全部
         function setCheckboxAllNo(checkboxAllNo){
            var loves = document.querySelectorAll(".area_box input[type='checkbox']");
            if(checkboxAllNo.checked == false){
                for (var i = 0; i < loves.length; i++) {
                    loves[i].checked = false;
                }
            }else{
                for (var i = 0; i < loves.length; i++) {
                    loves[i].checked = true;
                }
            }
        }

</script>
<script src="${domainUrlUtil.urlResources}/resources/seller/jslib/seller/allarea.js"></script>

<div class="area_box">
	<div class="area_box_title">
		<span class="area_box_title_left">选择区域<font style="color:red">(选择地区后请点击"确定"以返回)</font></span> <span
			class="area_box_title_right"><a href="javascript:void(0);"
			onclick="javascript:jQuery('.area_box').remove();jQuery('#editbyarea').hide();">×</a></span>
	</div>
	<!--蓝色背景:area_bg_blue 白色背景:area_bg_white-->
        <#--选中所有区域全选-->
        <div  style="border:1px solid #ccc;height:40px">
            <label style="padding-left: 0px;">全选</label>
            <input onclick="setCheckboxAllNo(this)" tyle="margin-left: 10px;" id="checkboxAllNo" name="checkboxAllNo" type="checkbox"  checked />
           
        </div>             
	<#list allArea as pros>
	<div class="area_bg_white">
		<div>
			<#-- <input name="province_${pros.id}" id="province_${pros.id}" type="checkbox"
				value="${pros.id}" /> -->

			<label style="padding-left: 0px;"
				for="province_${pros.id}">${(pros.regionName)!''}</label>
                        <#--省市全选-->
                        <input onclick="setRegionAllNo(this,'${(pros.regionName)!''}')" tyle="margin-left: 10px;" id="${(pros.regionName)!''}" name="${(pros.regionName)!''}" type="checkbox"  checked />
                          
              
		</div>
		<div class="area_box_main">
			<ul>
				<#list pros.children as citys>
				<li><input name="city_${citys.id}" province_name="${(pros.regionName)!''}"
					id="city_${citys.id}" city_name="${citys.regionName}" type="checkbox"  checked value="${citys.id}" /> <span><label
						for="city_${citys.id}" style="float: left;">${(citys.regionName)!''}</label></span> 
					
					</li> 
				</#list>
			</ul>
		</div>
	</div>
	</#list>
	<div style="position: relative;">
		<div class="area_box_bottom">
			<input type="button" value="取消"
				onclick="jQuery('.area_box').remove();jQuery('#editbyarea').hide();" /> <input type="button"
				value="确定" onclick="generic_area();" /> <input name="trans_index"
				type="hidden" id="trans_index" value="${trans_index}" /> <input
				name="trans_city_type" type="hidden" id="trans_city_type"
				value="${trans_city_type}" />
		</div>
	</div>
</div>