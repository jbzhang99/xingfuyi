<#include "/h5/commons/_head.ftl" />
<body class="bgff">
	<!-- 头部 -->
	<header id="header">
		<div class="flex flex-align-center head-bar">
			<div class="flex-1 text-left">
	   	  	 	<a href="javascript:ejsPageBack();">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
			</div>
			<div class="flex-2 text-center">添加收货人信息</div>
			<div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
		</div>
		<#include "/h5/commons/_hidden_menu.ftl" />
	</header>
	<!-- 头部 end-->
   
	<div class=""  >
	    <div class="pad10">
            <form id="addresform">
				<div class="form-group">
					<label>收货人姓名</label>
					<input type="text" class="form-control"  placeholder="" id="memberName" name="memberName" value="${(address.memberName)!''}" >
				</div>
				<div class="form-group">
					<label >手机号码</label>
					<input type="text" class="form-control" placeholder="" id="mobile" name="mobile" value="${(address.mobile)!''}" >
				</div>
				<div class="mar-bt">收货地址</div>
				<div class="form-group">
					<label>省份</label>
					<select class="form-control" id="provinceId" name="provinceId">
						<option value="">-- 请选择 --</option>
						<#if provinceList ??>
               			<#list provinceList as region>
                   			<option <#if address?? && address.provinceId?? && address.provinceId == region.id >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
               			</#list>
           				</#if>
       				</select>
         		</div>
				<div class="form-group">
					<label>城市</label>
					<select class="form-control" id="cityId" name="cityId">
						<option value="">-- 请选择 --</option>
						<#if cityList ??>
                 		<#list cityList as region>
                     	<option <#if address?? && address.cityId?? && address.cityId == region.id >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
               			</#list>
             			</#if>
         			</select>
				</div>
				<div class="form-group">
					<label>区县</label>
					<select class="form-control" id="areaId" name="areaId">
						<option value="">-- 请选择 --</option>
						<#if areaList ??>
                 		<#list areaList as region>
                   		<option <#if address?? && address.areaId?? && address.areaId == region.id >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>
                 		</#list>
             			</#if>
         			</select>
         		</div>
         		<div class="form-group">
           			<label>街道</label>
           			<textarea class="form-control" rows="3" id="addressInfo" name="addressInfo" >${(address.addressInfo)!''}</textarea>
         		</div>
				<div class="form-group">
					<label >邮编</label>
					<input type="text" class="form-control" placeholder="" id="zipCode" name="zipCode" value="${(address.zipCode)!''}" >
				</div>
             	<label id="errLabel" style="color:red"></label>

				<input type="hidden" id="id" name="id" value="${(address.id)!''}">
				<input type="hidden" id="addAll" name="addAll" value="">
				<input type="hidden" id="phone" name="phone" value="${(address.phone)!''}">
				<input type="hidden" id="email" name="email" value="${(address.email)!''}">
				
         		<button type="button" class="btn btn-block adresbtn" style="margin-top:20px;">
         			<#if isFromOrder?? && isFromOrder == "1" >
         				保存地址并使用
         			<#else>
         				保存地址
         			</#if>
         		</button>
			</form>
	    </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
		<#if message?? && message != "">
			// alert('${message}');
			$.dialog('alert','提示','${message}',2000);
		</#if>
		
		$("#provinceId").change(function(){
	        getRegion($("#cityId"), $(this).val(), "");
	    });
		
	    $("#cityId").change(function(){
	        getRegion($("#areaId"), $(this).val(), "");
	    });
		
	    $(".adresbtn").click(function() {
	    	
	    	// 校验
			var memberName = $("#memberName").val();
			if (memberName == null || memberName == "") {
				$("#errLabel").html("请输入收货人姓名");
				return false;
			}
			if (memberName.length > 20) {
				$("#errLabel").html("收货人姓名不能超过20字符");
				return false;
			}
			
			var mobile = $("#mobile").val();
			if (mobile == null || mobile == "") {
				$("#errLabel").html("请输入收货人手机号码");
				return false;
			}
			if (mobile != null && mobile.length > 0) {
				if (!isMobile(mobile)) {
					$("#errLabel").html("请输入正确的手机号码");
					return false;
				}
			}
			
	    	var province = $('#provinceId').val();
			var city = $('#cityId').val();
			var area = $('#areaId').val();
			
			if (province == null || province == "" || city == null || city == "" || area == null || area == "") {
				$("#errLabel").html("请选择省市区地址");
				return false;
			}
			
			//获得省市区中文并拼接
			var addall= $("#provinceId option:selected").text()+$("#cityId option:selected").text()+$("#areaId option:selected").text();
			$("#addAll").val(addall);
			
			var addressInfo = $("#addressInfo").val();
			if (addressInfo == null || addressInfo == "") {
				$("#errLabel").html("请输入详细地址");
				return false;
			}
			if (addressInfo.length > 50) {
				$("#errLabel").html("详细地址不能超过50字符");
				return false;
			}

			$(".adresbtn").attr("disabled","disabled");
			$.ajax({
				type:"POST",
				url:domain+"/member/app-saveaddress.html?memberId=",
				dataType:"json",
				data : $('#addresform').serialize(),
				success:function(data){
					if(data.success){
						if (${(isFromOrder)!0} == "1") {
							if (${(orderType)!"0"} == "2") {
								window.location.href=domain+"/order/flashsale-${actInfo!''}.html?addressId="+data.data;
							} else if (${(orderType)!"0"} == "3") {
								window.location.href=domain+"/order/tuan-${actInfo!''}.html?addressId="+data.data;
							} else if (${(orderType)!"0"} == "4") {
								window.location.href=domain+"/order/bidding-${actInfo!''}.html?addressId="+data.data;
							} else if (${(orderType)!"0"} == "6") {
								window.location.href=domain+"/order/jifen-${actInfo!''}.html?addressId="+data.data;
							} else {
								window.location.href=domain+"/order/info.html?addressId="+data.data;
							}
						} else {
							window.location.href=domain+"/member/address.html";  
						}
					}else{
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
						$(".adresbtn").removeAttr("disabled");
					}
				}
			});
	    });
	});
	
	function getRegion(_select, _parentId, _selectId) {
	    _select.empty();
	    $.ajax({
	        type:"get",
	        url: domain+"/getRegionByParentId",
	        dataType: "json",
	        data: {parentId: _parentId},
	        cache:false,
	        success:function(data){
	            if (data.success) {
	                _select.empty();
	                var selectOption = '<option value ="">-- 请选择 --</option>'
	                $.each(data.data, function(i, region){
	                    if (_selectId == region.id) {
	                        selectOption += "<option selected='true' value=" + region.id + ">" + region.regionName + "</option>";
	                    } else {
	                        selectOption += "<option value=" + region.id + ">" + region.regionName + "</option>";
	                    }
	                })
	                _select.append(selectOption);
	            } else {

	            }
	        }
	    });
	}
</script>
</body>
</html>