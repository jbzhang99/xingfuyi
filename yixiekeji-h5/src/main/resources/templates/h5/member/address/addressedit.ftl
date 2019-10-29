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
                <div class="addressInput">
                    <span>收货人</span>
                    <input type="text"  placeholder="请填写收货人姓名" id="memberName" name="memberName" />
                </div>
                <div class="addressInput">
                    <span>手机号码</span>
                    <input type="text"   placeholder="请填写收货人手机号" maxlength="11" id="mobile" name="mobile"  />
                </div>
                <div class="addressInput" onclick="showM()">
                    <span>所在地区</span>
                    <input type="text"   placeholder="请选择"  id="address"  disabled="disabled" />
                </div>
                <div class="addressTextarea">
                    <span>详细地址</span>
                    <textarea placeholder="街道、楼牌号等" id="addressInfo" value=""  cols="5" rows="10"></textarea>
                </div>
                <div style="clear: both;height:1rem;background:rgba(246,246,246,1);width: 120%;margin-left: -10%;"></div>
                <div class="flex">
                    <div class="flex-3">
                        <p style="font-size:1.6rem;font-family:PingFangSC-Regular,PingFang SC;height: 2rem;line-height: 2rem;margin-top: 20px;">设置为默认地址</p>
                        <p style="font-size:1.4rem;font-family:PingFangSC-Regular,PingFang SC;height: 2rem;line-height: 2rem;margin-top: 10px;">提醒：每次下单会默认推荐使用改地址</p>
                    </div>
                    <div>
                        <label><input class="btn-switch large" id="state" type="checkbox" value="" ></label>
                    </div>
                </div>
                <div style="color: #E7250C;display: none;margin-top: 10px;" class="deleteAddress" onclick="deleteAddress()">
                    删除收货地址
                </div>
				<#--<div class="form-group">-->
					<#--<label>收货人姓名</label>-->
					<#--<input type="text" class="form-control"  placeholder="" id="memberName" name="memberName" value="${(address.memberName)!''}" >-->
				<#--</div>-->
				<#--<div class="form-group">-->
					<#--<label >手机号码</label>-->
					<#--<input type="text" class="form-control" placeholder="" id="mobile" name="mobile" value="${(address.mobile)!''}" >-->
				<#--</div>-->
				<#--<div class="mar-bt">收货地址</div>-->
				<#--<div class="form-group">-->
					<#--<label>省份</label>-->
					<#--<select class="form-control" id="provinceId" name="provinceId">-->
						<#--<option value="">-- 请选择 --</option>-->
						<#--<#if provinceList ??>-->
               			<#--<#list provinceList as region>-->
                   			<#--<option <#if address?? && address.provinceId?? && address.provinceId == region.id >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>-->
               			<#--</#list>-->
           				<#--</#if>-->
       				<#--</select>-->
         		<#--</div>-->
				<#--<div class="form-group">-->
					<#--<label>城市</label>-->
					<#--<select class="form-control" id="cityId" name="cityId">-->
						<#--<option value="">-- 请选择 --</option>-->
						<#--<#if cityList ??>-->
                 		<#--<#list cityList as region>-->
                     	<#--<option <#if address?? && address.cityId?? && address.cityId == region.id >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>-->
               			<#--</#list>-->
             			<#--</#if>-->
         			<#--</select>-->
				<#--</div>-->
				<#--<div class="form-group">-->
					<#--<label>区县</label>-->
					<#--<select class="form-control" id="areaId" name="areaId">-->
						<#--<option value="">-- 请选择 --</option>-->
						<#--<#if areaList ??>-->
                 		<#--<#list areaList as region>-->
                   		<#--<option <#if address?? && address.areaId?? && address.areaId == region.id >selected='true'</#if> value="${(region.id)!''}">${(region.regionName)!''}</option>-->
                 		<#--</#list>-->
             			<#--</#if>-->
         			<#--</select>-->
         		<#--</div>-->
         		<#--<div class="form-group">-->
           			<#--<label>街道</label>-->
           			<#--<textarea class="form-control" rows="3" id="addressInfo" name="addressInfo" >${(address.addressInfo)!''}</textarea>-->
         		<#--</div>-->
				<#--<div class="form-group">-->
					<#--<label >邮编</label>-->
					<#--<input type="text" class="form-control" placeholder="" id="zipCode" name="zipCode" value="${(address.zipCode)!''}" >-->
				<#--</div>-->
             	<#--<label id="errLabel" style="color:red"></label>-->

				<#--<input type="hidden" id="id" name="id" value="${(address.id)!''}">-->
				<#--<input type="hidden" id="addAll" name="addAll" value="">-->
				<#--<input type="hidden" id="phone" name="phone" value="${(address.phone)!''}">-->
				<#--<input type="hidden" id="email" name="email" value="${(address.email)!''}">-->

         		<#--<button type="button" class="btn btn-block adresbtn" style="margin-top:20px;">-->
         			<#--<#if isFromOrder?? && isFromOrder == "1" >-->
         				<#--保存地址并使用-->
         			<#--<#else>-->
         				<#--保存地址-->
         			<#--</#if>-->
         		<#--</button>-->
                <button  type="button" class="btn btn-block adresbtn" style="width: 50%;margin: auto;margin-top: 20px;border-radius: 30px; background-color: unset;border: 1px solid #E7250C;color: #E7250C; outline: none;box-shadow:none;">
                    <#if isFromOrder?? && isFromOrder == "1" >
                        保存地址并使用
                    <#else>
                        保存地址
                    </#if>
                </button>

			</form>
	    </div>

        <section id="mymodal">
            <div class="modal-main">
                <p class="address-title">收货地址</p>
                <p class="close" onclick="closeModal()">x</p>
                <ul class="optionwrapper">
                    <li class="option-menu option-menu-one active-option">请选择</li>
                    <li class="option-menu option-menu-two"></li>
                    <li class="option-menu option-menu-three"></li>
                </ul>
                <div class="option-content">
                    <ul class="option-group option-group-one" data-index="0" style="display: block">
                    </ul>
                    <ul class="option-group option-group-two" data-index="1">
                    </ul>
                    <ul class="option-group option-group-three" data-index="2">
                    </ul>
                </div>
            </div>
        </section>


    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
<script type="text/javascript">
    var addressId  =  getParam("id");
	$(function(){
		<#--<#if message?? && message != "">-->
			<#--// alert('${message}');-->
			<#--$.dialog('alert','提示','${message}',2000);-->
		<#--</#if>-->

//		$("#provinceId").change(function(){
//	        getRegion($("#cityId"), $(this).val(), "");
//	    });
//
//	    $("#cityId").change(function(){
//	        getRegion($("#areaId"), $(this).val(), "");
//	    });
	    $(".adresbtn").click(function() {
	    	// 校验
			var memberName = $("#memberName").val();
			if (memberName == null || memberName.trim() == "") {
                layer.open({content: '请输入收货人姓名',skin: 'msg',time: 2 });
				return false;
			}
			if (memberName.length > 20) {
                layer.open({content: '收货人姓名不能超过20字符',skin: 'msg',time: 2 });
				return false;
			}

			var mobile = $("#mobile").val();
			if (mobile == null || mobile == "") {
                layer.open({content: '请输入收货人手机号码',skin: 'msg',time: 2 });

				return false;
			}
			if (mobile != null && mobile.length > 0) {
				if (!isMobile(mobile)) {
                    layer.open({content: '请输入正确的手机号码',skin: 'msg',time: 2 });
					return false;
				}
			}
	    	var province = getData("provinceId");
			var city = getData("cityId");
			var area = getData("areaId");

			if (province == null || province == "" || city == null || city == "" || area == null || area == "") {
                layer.open({content: '请选择省市区地址',skin: 'msg',time: 2 });
				return false;
			}

			var addressInfo = $("#addressInfo").val();
			if (addressInfo == null || addressInfo == "") {
				$("#errLabel").html("请输入详细地址");
				return false;
			}
			if (addressInfo.length > 50) {
				$("#errLabel").html("详细地址不能超过50字符");
				return false;
			}
            <#--var memberId = ${(member.id)}-->
			$(".adresbtn").attr("disabled","disabled");
			$.ajax({
				type:"POST",
				url:domain+"/member/saveAddress",
				dataType:"json",
//				data : $('#addresform').serialize(),
                data:{
                    memberId:getData("memberId"),
                    memberName:memberName,
                    mobile:mobile,
                    provinceId:getData("provinceId"),
                    cityId:getData("cityId"),
                    areaId:getData("areaId"),
                    addAll:$("#address").val(),
                    addressInfo:addressInfo,
                    state:$("#state").is(':checked') ? 1 : 2,
                    id:addressId==null ? "" : addressId
                },
				success:function(data){
				    console.log(data);
					if(data.success){
						if (${(isFromOrder)!0} == "1") {
							if (${(orderType)!"0"} == "2") {
								window.location.href=domain+"/order/flashsale-${actInfo!''}.html?addressId="+data.result.id;
							} else if (${(orderType)!"0"} == "3") {
								window.location.href=domain+"/order/tuan-${actInfo!''}.html?addressId="+data.result.id;
							} else if (${(orderType)!"0"} == "4") {
								window.location.href=domain+"/order/bidding-${actInfo!''}.html?addressId="+data.result.id;
							} else if (${(orderType)!"0"} == "6") {
								window.location.href=domain+"/order/jifen-${actInfo!''}.html?addressId="+data.result.id;
							} else {
								window.location.href=domain+"/order/info.html?addressId="+data.result.id;
							}
						} else {
							window.location.href=domain+"/member/address.html";
						}
					}else{
						$.dialog('alert','提示',data.message,2000);
						$(".adresbtn").removeAttr("disabled");
					}
				}
			});
	    });

//        add by fanlei 2019-10-21 地址优化改造  start

        init();
        initAddress();
//        add by fanlei 2019-10-21 地址优化改造  end




	});
    //根据addressid回显数据
    function  initAddress() {
        if(addressId==null){
            return;
        }else{
            $.ajax({
                type:"get",
                url:domain+"/member/getAddressById",
                dataType:"json",
                data:{
                    id:addressId
                },
                success:function(data){
                    if(data.success){
                        var address = data.result;
                        $("#memberName").val(address.memberName);
                        $("#mobile").val(address.mobile);
                        $("#address").val(address.addAll);
                        $("#addressInfo").text(address.addressInfo);
                        setData("provinceId",address.provinceId);
                        setData("cityId",address.cityId);
                        setData("areaId",address.areaId);
                        if(address.state==1){
                            $("#state").prop('checked','true');
                        }else{
                            $("#state").removeAttr('checked');
                        }
                    }else{
                        layer.open({content: data.message ,skin: 'msg',time: 2 });
                    }
                },
                error:function(){
                    layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                    $("#loginBtn").removeAttr("disabled");
                    return false;
                }
            });

            $(".deleteAddress").show()
        }
    }
//    根据id删除地址
    function deleteAddress() {
        $.dialog('confirm','提示','是否确定删除!',0,function(){
            $.closeDialog();
            $.ajax({
                type : "POST",
                url :  domain+"/member/deleteaddress.html",
                data : {id:addressId},
                dataType : "json",
                success : function(data) {
                    if(data.success){
                        window.location.href=domain+"/member/address.html";
                    }else {
                        layer.open({content: data.message ,skin: 'msg',time: 2 });
                    }
                },
                error:function(){
                    layer.open({content: '异常，请重试！',skin: 'msg',time: 2 });
                    return false;
                }
            });
        });
    }

//	function getRegion(_select, _parentId, _selectId) {
//	    _select.empty();
//	    $.ajax({
//	        type:"get",
//	        url: domain+"/getRegionByParentId",
//	        dataType: "json",
//	        data: {parentId: _parentId},
//	        cache:false,
//	        success:function(data){
//	            if (data.success) {
//	                _select.empty();
//	                var selectOption = '<option value ="">-- 请选择 --</option>'
//	                $.each(data.data, function(i, region){
//	                    if (_selectId == region.id) {
//	                        selectOption += "<option selected='true' value=" + region.id + ">" + region.regionName + "</option>";
//	                    } else {
//	                        selectOption += "<option value=" + region.id + ">" + region.regionName + "</option>";
//	                    }
//	                })
//	                _select.append(selectOption);
//	            } else {
//
//	            }
//	        }
//	    });
//	}
    //	收货地址弹窗显示
    function  showM() {
        $("#mymodal").show();
        $(".modal-main").animate({"bottom":"0"}, 400)
        if(addressId==null){
            return;
        }else{
            province();
        }

    }

    function province() {
        if(getData("provinceId")!=""||getData("provinceId")!=null){
            $.ajax({
                type:"get",
                url: domain+"/getRegionByParentId",
                dataType: "json",
                data: {parentId: 0},
                cache:false,
                async : false,
                success:function(data){
                    if (data.success) {
                        // 初始化省份
                        var optionGroupOne = "";
                        $.each(data.data,function(index, el) {
                            if(data.data[index]["id"]==getData("provinceId")){
                                optionGroupOne += '<li class="option-list option-list-one" data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked" style="display: block;"></div></li>'
                                $(".option-menu").eq(0).text(data.data[index]["regionName"])
                            }else{
                                optionGroupOne += '<li class="option-list option-list-one" data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked" style="display: none;"></div></li>'
                            }
                        });
                        $(".optionwrapper").find(".option-menu").removeClass('active-option');
                        $(".option-menu-two").html("请选择").addClass('active-option');
                        $(".option-group-one").html(optionGroupOne)
                        city();
                    } else {
                        layer.open({content:data.message,skin: 'msg',time: 2 });
                    }
                },
                error:function (err) {
                    layer.open({content: '异常，请重试',skin: 'msg',time: 2 });
                }
            });
        }
    }


    //城市回显
    function city() {
        if(getData("cityId")!=""||getData("cityId")!=null){
            $.ajax({
                type:"get",
                url: domain+"/getRegionByParentId",
                dataType: "json",
                data: {parentId: getData("provinceId")},
                cache:false,
                success:function(data){
                    if (data.success) {
                        // 初始化省份
                        var cityStr = "";
                        $.each(data.data,function(index, el) {
                            if(data.data[index]["id"]==getData("cityId")){
                                cityStr += '<li class="option-list option-list-two"  data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked" style="display: block;"></div></li>'
                                console.log(data.data[index]["id"])
                                $(".option-menu").eq(1).text(data.data[index]["regionName"])
                            }else{
                                cityStr += '<li class="option-list option-list-two"  data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked" style="display: none;"></div></li>'

                            }
                        });
                        $(".option-group-one").hide();
                        $(".optionwrapper").find(".option-menu").removeClass('active-option');
                        $(".option-menu-three").html("请选择").addClass('active-option');
                        $(".option-group-two").html(cityStr).show();
                        area();
                    } else {
                        layer.open({content:data.message,skin: 'msg',time: 2 });
                    }
                },
                error:function (err) {
                    layer.open({content: '异常，请重试',skin: 'msg',time: 2 });
                }
            });
        }
    }

    //县级回显
    function  area() {
        if(getData("areaId")!=""||getData("areaId")!=null){
            $.ajax({
                type:"get",
                url: domain+"/getRegionByParentId",
                dataType: "json",
                data: {parentId: getData("cityId")},
                cache:false,
                success:function(data){
                    if (data.success) {
                        var areaStr = "";
                        $.each(data.data,function(index, el) {
                            if(data.data[index]["id"]==getData("areaId")){
                                areaStr += '<li class="option-list option-list-three" data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked" style="display: block;"></div></li>'
                                $(".option-menu").eq(2).text(data.data[index]["regionName"])
                            }else{
                                areaStr += '<li class="option-list option-list-three" data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked" style="display: none;"></div></li>'
                            }
                        });
                        $(".option-group-two").hide();
                        $(".option-group-three").html(areaStr).show();
                    } else {
                        layer.open({content:data.message,skin: 'msg',time: 2 });
                    }
                },
                error:function (err) {
                    layer.open({content: '异常，请重试',skin: 'msg',time: 2 });
                }
            });
        }
    }


    //	收货地址弹窗隐藏
    function closeModal() {
        $(".modal-main").animate({"bottom":"-900px"}, 400);
        setTimeout(function () {
            $("#mymodal").fadeOut();
        },350)
    }
    //    地址初始化显示所有省的名字
    function init(){
        $.ajax({
            type:"get",
            url: domain+"/getRegionByParentId",
            dataType: "json",
            data: {parentId: 0},
            cache:false,
            success:function(data){
                if (data.success) {
                    // 初始化省份
                    var optionGroupOne = "";
                    $.each(data.data,function(index, el) {
                        optionGroupOne += '<li class="option-list option-list-one" data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked"></div></li>'
                    });

                    $(".option-group-one").html(optionGroupOne)
                } else {
                    layer.open({content:data.message,skin: 'msg',time: 2 });
                }
            },
            error:function (err) {
                layer.open({content: '异常，请重试',skin: 'msg',time: 2 });
            }
        });
    }
    //    点击省份显示城市
    $("#mymodal").on("click",".option-list-one",function(){
        var parentIndex = $(this).attr("data-id");
        setData("provinceId",parentIndex);
        var provinceName = $(this).text().trim();
        var provinceIndex = $(this).index();
        $(this).find('.checked').show();
        $(this).siblings().find('.checked').hide();
        $(".option-menu").eq(0).text(provinceName)
        var cityStr = "";
        $.ajax({
            type:"get",
            url: domain+"/getRegionByParentId",
            dataType: "json",
            data: {parentId: parentIndex},
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.data,function(index, el) {
                        cityStr += '<li class="option-list option-list-two"  data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked"></div></li>'
                    });
                    $(".option-group-one").hide();
                    $(".option-group-two").html(cityStr).show();
                    $(".optionwrapper").find(".option-menu").removeClass('active-option')
                    $(".option-menu-two").html("请选择").addClass('active-option')
                    $(".option-group-three").html("");
                    $(".option-menu-three").html("")
                } else {
                    layer.open({content:data.message,skin: 'msg',time: 2 });
                }
            },
            error:function () {
                layer.open({content: '异常，请重试',skin: 'msg',time: 2 });
            }
        });

    })
    //    点击城市显示县区
    $("#mymodal").on("click",".option-list-two",function(){
        var parentIndex = $(this).attr("data-id");
        setData("cityId",parentIndex);
        var provinceName = $(this).text().trim();
        var provinceIndex = $(this).index();
        $(this).find('.checked').show();
        $(this).siblings().find('.checked').hide();
        $(".option-menu").eq(1).text(provinceName)
        var areaStr = "";
        $.ajax({
            type:"get",
            url: domain+"/getRegionByParentId",
            dataType: "json",
            data: {parentId: parentIndex},
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.data,function(index, el) {
                        areaStr  += '<li class="option-list option-list-three"  data-id="'+data.data[index]["id"]+'"><span>'+data.data[index]["regionName"]+'</span><div class="checked"></div></li>'
                    });
                    $(".option-group-two").hide();
                    $(".option-group-three").html(areaStr).show();
                    $(".optionwrapper").find(".option-menu").removeClass('active-option');
                    $(".option-menu-three").html("请选择").addClass('active-option');
                } else {
                    layer.open({content:data.message,skin: 'msg',time: 2 });
                }
            }
        });

    })
    //    选择县区
    $("#mymodal").on("click",".option-list-three",function(){
        var parentIndex = $(this).attr("data-id");
        setData("areaId",parentIndex);
        var provinceName = $(this).text().trim();
        var provinceIndex = $(this).index();
        $(this).find('.checked').show();
        $(this).siblings().find('.checked').hide();
        $(".option-menu").eq(2).text(provinceName);

        var menuOne = $(".option-menu").eq(0).text();
        var menuTwo = $(".option-menu").eq(1).text();
        var addressVal = menuOne+ menuTwo + provinceName;
        $(".modal-main").animate({"bottom":"-900px"}, 400);
        setTimeout(function () {
            $("#mymodal").fadeOut()
        },350)
        $("#address").val(addressVal)

    })

    $("#mymodal").on("click",".option-menu",function(){
        var i = $(this).index();
        $(this).addClass('active-option').siblings().removeClass('active-option');
        $(".option-group").eq(i).show().siblings().hide()
    })


</script>

</body>
</html>
