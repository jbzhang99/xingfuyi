<#import "/h5/commons/_macro_controller.ftl" as cont/>
<#include "/h5/commons/_head.ftl" />
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
		 </div>
   	  	 <div class="flex-2 text-center">个人资料</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->

	<div class="s-container">
        <div class="account-list add-account-list">
            <a class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>头像</li>
                    <li><span> <img src="https://www.baidu.com/img/bd_logo1.png" style="width: 35px;height: 35px;border-radius:50%;" alt=""> </span> <i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a href="${(domainUrlUtil.urlResources)!}/member/editpassword.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>昵称</li>
                    <li><span style="color:rgba(153,153,153,1);">${(member.name)!''}</span> <i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>性别</li>
                    <li>
                        <span style="color:rgba(153,153,153,1);">
                            <#if member??>
                                <#if  member.gender??>
                                    <#assign gender = member.gender>
                                        <#if gender==0>保密
                                        <#elseif gender==1>男
                                        <#elseif gender==2>女
                                        <#elseif gender=='null'>请选择
                                        <#else>
                                    </#if>
                                </#if>
                            </#if>
                        </span>
                        <i class="fa fa-angle-right" aria-hidden="true"></i>
                    </li>
                </ul>
            </a>
            <a class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>出生日期</li>
                    <li><span style="color:rgba(153,153,153,1);">${(member.birthday?string('yyyy-MM-dd'))!'请选择'}</span> <i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
            <a href="${(domainUrlUtil.urlResources)!}/member/address.html" class="block">
                <ul class="a-list-ul flex flex-pack-justify">
                    <li>已绑定手机号</li>
                    <li><span style="color:rgba(153,153,153,1);">1531314944</span> <i class="fa fa-angle-right" aria-hidden="true"></i></li>
                </ul>
            </a>
        </div>

	  <#--<div>-->

		  <#--<div class="user-infor">-->
		  	<#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>用户名:</div>-->
                <#--<div>${(member.name)!''}</div>-->
		  	 <#--</div>-->
		  	 <#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>生日</div>-->
		  	 	<#--<div>${(member.birthday?string("yyyy-MM-dd"))!''}</div>-->
		  	 <#--</div>-->
		  	 <#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>性别</div>-->
		  	 	<#--<div>-->
		  	 		<#--<#if member??>-->
		  			<#--<#if  member.gender??>-->
		  				<#--<#assign gender = member.gender>-->
			  				<#--<#if gender==0>保密-->
			  				<#--<#elseif gender==1>男-->
			  				<#--<#elseif gender==2>女-->
			  				<#--<#else>-->
		  				<#--</#if>-->
		  		    <#--</#if>-->
		  		  	<#--</#if>-->
				<#--</div>-->
		  	 <#--</div>-->
		  	 <#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>邮箱</div>-->
		  	 	<#--<div>${(member.email)!''}</div>-->
		  	 <#--</div>-->
		  	 <#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>QQ</div>-->
		  	 	<#--<div>${(member.qq)!''}</div>-->
		  	 <#--</div>-->
		  	 <#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>电话</div>-->
		  	 	<#--<div>${(member.phone)!''}</div>-->
		  	 <#--</div>-->
		  	 <#--<div class="flex flex-pack-justify u-i-item">-->
		  	 	<#--<div>手机</div>-->
		  	 	<#--<div>${(member.mobile)!''}</div>-->
		  	 <#--</div>-->
		  <#--</div>-->

		  <#--<div class="user-infor" style="margin-top:30px;">-->
		    <#--<form id='personalfileForm'>-->
		  	 <#---->
		  	 <#--<div class="form-group">-->
		  	 	<#--<label class="birthday">生日:</label>-->
                <#--<input type="text" class="form-control" id="birthday" name="birthday" value="${(member.birthday?string('yyyy-MM-dd'))!''}" placeholder="1990-1-1">-->
		  	 <#--</div>-->
		  	 <#---->
		  	 <#--<div class="form-group">-->
		  	 	<#--<label for="phone">性别:</label> -->
		  	 	<#--&nbsp;&nbsp;&nbsp;&nbsp;<@cont.radio id="q_gender" class="radio-inline" name="gender" value="${(member.gender)!''}" codeDiv="GENDER" />-->
		  	 <#--</div>-->
		  	 <#---->
		  	 <#--<div class="form-group">-->
		  	 	<#--<label class="phone">电话:</label>-->
		  	 	<#--<input type="tel" id='phone' name='phone' value="${(member.phone)!''}" placeholder="" class="form-control">-->
		  	 <#--</div>-->
		  	 <#---->
		  	 <#--<div class="form-group">-->
		  	 	<#--<label for="curMobile">手机-->
		  	 			<#--<#if member??>-->
							<#--<#if (member.isSmsVerify)=1>(已绑定):-->
							<#--<#elseif (member.isSmsVerify)=0>(未绑定):-->
							<#--</#if>-->
						<#--<#else>(未绑定):-->
						<#--</#if> -->
				<#--</label>-->
		  	 	<#--<input type="tel" class="form-control" id="curMobile" name="mobile" value="${(member.mobile)!''}"  placeholder="请输入手机号码">-->
		 	    <#--<#if member??>-->
				  <#--<#if (member.isSmsVerify)=1>-->
					  <#--<button class="btn" type="button" id="unSmsVerif">解除绑定</button>-->
				  <#--<#elseif (member.isSmsVerify)=0>-->
					  <#--<button class="btn btn-primary" type="button" id="sendSmsVerif">获取短信验证码</button>-->
				  <#--</#if>-->
			    <#--</#if> -->
		  	 <#--</div>-->
		  	 <#--<#if member??>-->
				<#--<#if (member.isSmsVerify)=0>-->
					<#--<div class="form-group">-->
				  	 	<#--<label for="curMobile">验证码:</label>-->
				  	 	<#--<input type="text" name='smsVerifyCode' maxlength="6" id='smsVerifyCode'  placeholder="请输入验证码">-->
				  	 	<#--<button class="btn btn-primary" type="button" id='bindMobileButton'>绑定手机</button>-->
				  	<#--</div>-->
				<#--</#if>-->
			 <#--</#if> -->
		  	 <#---->
		  	 <#--<div class="form-group">-->
		  	 	<#--<label for="curEmail1">邮箱-->
		  	 		<#--<#if member??>-->
						<#--<#if (member.isEmailVerify)=1>(已验证):-->
						<#--<#elseif (member.isEmailVerify)=0>(未验证):-->
						<#--</#if>-->
					<#--<#else>(未验证):-->
					<#--</#if>-->
		  	 	<#--</label>-->
		  	 	<#--<input type="tel" class="form-control" id='curEmail1' name="email" value="${(member.email)!''}" placeholder="请输入邮箱">-->
		  	 	<#--<#if member??>-->
					<#--<#if (member.isEmailVerify)=0>-->
						<#--<button class="btn btn-primary" type="button" id='sendEmailVerify'>发送验证邮件</button>-->
					<#--<#elseif (member.isEmailVerify)=1>-->
						<#--<button class="btn" type="button" id='unEmailVerify'>解除绑定</button>-->
					<#--</#if>-->
				<#--</#if>-->
		  	 <#--</div>-->
		  	 <#--<div class="form-group">-->
		  	 	<#--<label for="curEmail1">QQ:</label>-->
		  	 	<#--<input type="number" class="form-control" id='qq' name='qq' value="${(member.qq)!''}" placeholder="请输入QQ">-->
		  	 <#--</div>-->
		  	 <#---->
             <#--<div style="padding:10px 0 0 10px;"><font id="errLabel" class="font12 clr53"></font></div>-->
		  	 <#--<div class="text-center padt_b10">-->
		  	 	 <#--<button class="btn btn-login" type="submit" style="padding:6px 12px;" id="personSubmit">确认提交</button>-->
		  	 <#--</div>-->
		  	 <#---->
		  	<#--</form>-->
		  <#--</div>-->
		<#---->
	  <#--</div>-->
    </div>

   <div class="maskbg" style="display: block;">
       <div style="position: absolute;width: 100%;bottom: 70px;">
           <div style="width: 90%;height: 5rem;line-height: 5rem;margin-left: 5%;text-align: center;border-radius: 10px 10px 0 0;border-bottom: 1px solid #cccccc;color: #007AFF;background-color: #fff;font-size: 20px;">
               <label style="width: 100%;height: 5rem;">
                   拍照<input type="file" id='image' style="display: none;"  accept="image/*" capture='camera' onchange="fileSelected(this)">
               </label>
           </div>
           <div style="width: 90%;height: 5rem;line-height: 5rem;margin-left: 5%;text-align: center;border-radius: 0 0 10px 10px;color: #007AFF;background-color: #fff;font-size: 20px;">
               <label style="width: 100%;height: 5rem;">
                   相册<input type="file" id='image' style="display: none;"  accept="image/*" capture='camera' onchange="fileSelected(this)">
               </label>
           </div>
           <div style="width: 90%;height: 5rem;line-height: 5rem;margin-left: 5%;margin-top: 15px; text-align: center;border-radius: 10px;color: #007AFF;background-color: #fff;font-size: 20px;">取消</div>

       </div>
   </div>

	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />


 <!--日历-->
<link href="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll.css" rel="stylesheet" type="text/css">
<script src="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll_002.js" type="text/javascript"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll.js" type="text/javascript"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/selectdate/mobiscroll_003.js" type="text/javascript"></script>
<script type="text/javascript">
    function fileSelected(obj){
        var type = obj.files[0].type;
        if(type=="image/jpg"||type=="image/jpeg"||type=="image/png"){
            var oFReader = new FileReader();
            oFReader.readAsDataURL(obj.files[0]);
            oFReader.onload = function (oFREvent) {
                compress(oFREvent.target.result,obj.files[0].name, function(imgBase64,name){
                    $.ajax({
                        url: domain+ "/member/uploadFiles",
                        data: dataURLtoFile(imgBase64,"image/png"),
                        type: "POST",
                        contentType: false, //这里
                        processData: false, //这两个一定设置为false
                        success: function(info) {
                            console.log(info)
                        }
                });

                });
            };
        }else{
            alert("只能上传图片");
        }

    }
        //    * 将base64转换成blob，再转换成form数据
        //    * @param {*} dataURI base64数据
        //    * @param {*} type 图片类型，如：image/png
        //    */

    function dataURLtoFile(dataURI, type) {
        // btoa和atob是window对象的两个函数，其中btoa是binary to ascii，用于将binary的数据用ascii码表示，即Base64的编码过程，而atob则是ascii to binary，用于将ascii码解析成binary数据
        var binary = atob(dataURI.split(',')[1]);
        var array = [];
        for (var i = 0; i < binary.length; i++) {
            array.push(binary.charCodeAt(i));

        }
        var fileData = new Blob([new Uint8Array(array)], {
            type: type
        });
        var form = new FormData();
        form.append("bizType", "9");
        var fileOfBlob = new File([fileData], new Date() + '.png'); // 重命名了
        form.append("file", fileOfBlob);
        return form;
    }


    //将base64转换为文件
    //    function dataURLtoFile(dataurl, filename) {
    //        var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
    //            bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    //        while(n--){
    //            u8arr[n] = bstr.charCodeAt(n);
    //        }
    //        return new File([u8arr], filename, {type:mime});
    //    }

    //    压缩图片成base64编码
    function compress(path, name, callback){
        var img = new Image();
        img.src = path;
        img.onload = function(){
            var that = this;
            // 默认按比例压缩
            var w = that.width,
                h = that.height,
                scale = w / h;
            if (w > 800) {
                w = 800;
            }
            h = w/scale;
            var quality = 0.8;  // 默认图片质量为0.7
            //生成canvas
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            // 创建属性节点
            var anw = document.createAttribute("width");
            anw.nodeValue = w;
            var anh = document.createAttribute("height");
            anh.nodeValue = h;
            canvas.setAttributeNode(anw);
            canvas.setAttributeNode(anh);
            ctx.drawImage(that, 0, 0, w, h);
            // quality值越小，所绘制出的图像越模糊
            var base64 = canvas.toDataURL('image/jpeg', quality );
            // 回调函数返回base64的值
            callback(base64,name);
        }
    }

	$(function(){
		/*日历*/
		var currYear = (new Date()).getFullYear();
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.default = {
			theme: 'android-ics', //皮肤样式
			display: 'modal', //显示方式
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy-mm-dd',
			lang: 'zh',
			showNow: false,
			startYear: currYear - 50, //开始年份
			endYear: currYear + 10 //结束年份
		};
		$("#birthday").mobiscroll($.extend(opt['date'], opt['default']));

		$("#personSubmit").click(function(){
			// 校验
			var phone = $("#phone").val();
			if (phone != null && phone.length > 0) {
				if (!isPhone(phone)) {
					$("#errLabel").html("<i class='fa fa-warning'></i> 请输入正确的电话号码，如：010-88888888");
					return false;
				}
			}

			var curMobile = $("#curMobile").val();
			if (curMobile != null && curMobile.length > 0) {
				if (!isMobile(curMobile)) {
					$("#errLabel").html("<i class='fa fa-warning'></i> 请输入正确的手机号码");
					return false;
				}
			}

			var curEmail1 = $("#curEmail1").val();
			if (curEmail1 != null && curEmail1.length > 0) {
				if (!isEmail(curEmail1)) {
					$("#errLabel").html("<i class='fa fa-warning'></i> 请输入正确的邮箱地址");
					return false;
				}
			}

			var phone = $("#phone").val();
			if (phone != null && phone.length > 0) {
				if (!isPhone(phone)) {
					$("#errLabel").html("<i class='fa fa-warning'></i> 请输入正确的电话号码");
					return false;
				}
			}

			$("#personSubmit").attr("disabled","disabled");
			var params = $('#personalfileForm').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/member/saveinfo.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						// alert("保存成功");
						//重新加载数据
						// window.location.href=domain+"/member/info.html";
						$.dialog('alert','提示','保存成功',2000,function(){ window.location.href=domain+"/member/info.html"; });
					}else{
						// alert(data.message);
						// $("#personSubmit").removeAttr("disabled");
						$.dialog('alert','提示',data.message,2000,function(){ $("#personSubmit").removeAttr("disabled"); });
					}
				}
			});
		});

		$("#sendSmsVerif").click(function(){
			var curMobile = $("#curMobile").val();
			if (curMobile == null || curMobile == "") {
				// alert("请输入手机号码！");
				$.dialog('alert','提示','请输入手机号码！',2000);
				return;
			}
			if (!isMobile(curMobile)) {
				$("#errLabel").html("请输入正确的手机号码");
				return false;
			}
			$("#sendSmsVerif").attr("disabled","disabled");
			$.ajax({
				type:"POST",
				url:domain+"/member/sendsmsverif.html",
				dataType:"json",
				async : false,
				data : {mobile:curMobile},
				success:function(data){
					if(data.success){
						// alert("验证码发送成功，请查收短信！");
						$.dialog('alert','提示','验证码发送成功，请查收短信！',2000);
					}else{
						// alert(data.message);
						// $("#sendSmsVerif").removeAttr("disabled");
						$.dialog('alert','提示',data.message,2000,function(){ $("#sendSmsVerif").removeAttr("disabled"); });
					}
				}
			});
		});

		$("#bindMobileButton").click(function(){
			var smsVerifyCode = $("#smsVerifyCode").val();
			if (smsVerifyCode == null || smsVerifyCode == "") {
				// alert("请输入验证码！");
				$.dialog('alert','提示','请输入验证码！',2000);
				return;
			}
			$("#bindMobileButton").attr("disabled","disabled");
			$.ajax({
				type:"POST",
				url:domain+"/member/smsverif.html",
				dataType:"json",
				async : false,
				data : {verif:smsVerifyCode},
				success:function(data){
					if(data.success){
						// alert("绑定成功！");
						// 重新加载数据
						// window.location.href=domain+"/member/info.html";
						$.dialog('alert','提示','绑定成功！',2000,function(){ window.location.href=domain+"/member/info.html"; });
					}else{
						// alert(data.message);
						// $("#bindMobileButton").removeAttr("disabled");
						$.dialog('alert','提示',data.message,2000,function(){ $("#bindMobileButton").removeAttr("disabled"); });
					}
				}
			});
		});

		$("#unSmsVerif").click(function(){
			/* if(confirm("确定要解除绑定吗？")){
				$("#unSmsVerif").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/unsmsverif.html",
					dataType:"json",
					async : false,
					data : {},
					success:function(data){
						if(data.success){
							// alert("解除绑定成功！");
							// 重新加载数据
							// window.location.href=domain+"/member/info.html";
							$.dialog('alert','提示','解除绑定成功！',2000,function(){ window.location.href=domain+"/member/info.html"; });
						}else{
							// alert(data.message);
							// $("#unSmsVerif").removeAttr("disabled");
							$.dialog('alert','提示',data.message,2000,function(){ $("#unSmsVerif").removeAttr("disabled"); });
						}
					}
				});
			} */
			$.dialog('confirm','提示','确定要解除绑定吗？',0,function(){
				$("#unSmsVerif").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/unsmsverif.html",
					dataType:"json",
					async : false,
					data : {},
					success:function(data){
						if(data.success){
							// alert("解除绑定成功！");
							// 重新加载数据
							$.dialog('alert','提示','解除绑定成功！',2000,function(){ window.location.href=domain+"/member/info.html"; });
						}else{
							// alert(data.message);
							$.dialog('alert','提示',data.message,2000,function(){ $("#unSmsVerif").removeAttr("disabled"); });
						}
					}
				});
			});
		});

		$("#sendEmailVerify").click(function(){
			var curEmail1 = $("#curEmail1").val();
			if (curEmail1 == null || curEmail1 == "") {
				// alert("请输入邮箱地址！");
				$.dialog('alert','提示','请输入邮箱地址！',2000);
				return;
			}
			if (!isEmail(curEmail1)) {
				$("#errLabel").html("请输入正确的邮箱地址");
				return false;
			}
			$("#sendEmailVerify").attr("disabled","disabled");
			$.ajax({
				type:"POST",
				url:domain+"/member/sendemailverif.html",
				dataType:"json",
				async : false,
				data : {email:curEmail1},
				success:function(data){
					if(data.success){
						// alert("验证码发送成功，请查收邮件！");
						$.dialog('alert','提示','验证码发送成功，请查收邮件！',2000);
					}else{
						// alert(data.message);
						// $("#sendEmailVerify").removeAttr("disabled");
						$.dialog('alert','提示',data.message,2000,function(){ $("#sendEmailVerify").removeAttr("disabled"); });
					}
				},
				error:function(){
					$("#sendEmailVerify").removeAttr("disabled");
				}
			});
		});

		$("#unEmailVerify").click(function(){
			/* if(confirm("确定要解除绑定吗？")){
				$("#unEmailVerify").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/unemailverif.html",
					dataType:"json",
					async : false,
					data : {},
					success:function(data){
						if(data.success){
							// alert("解除绑定成功！");
							//重新加载数据
							// window.location.href=domain+"/member/info.html";
							$.dialog('alert','提示','解除绑定成功！',2000,function(){ window.location.href=domain+"/member/info.html"; });
						}else{
							// alert(data.message);
							// $("#unEmailVerify").removeAttr("disabled");
							$.dialog('alert','提示',data.message,2000,function(){ $("#unEmailVerify").removeAttr("disabled"); });
						}
					},
					error:function(){
						$("#unEmailVerify").removeAttr("disabled");
					}
				});
			} */
			$.dialog('confirm','提示','确定要解除绑定吗？',0,function(){
				$("#unEmailVerify").attr("disabled","disabled");
				$.ajax({
					type:"POST",
					url:domain+"/member/unemailverif.html",
					dataType:"json",
					async : false,
					data : {},
					success:function(data){
						if(data.success){
							// alert("解除绑定成功！");
							//重新加载数据
							// window.location.href=domain+"/member/info.html";
							$.dialog('alert','提示','解除绑定成功！',2000,function(){ window.location.href=domain+"/member/info.html"; });
						}else{
							// alert(data.message);
							// $("#unEmailVerify").removeAttr("disabled");
							$.dialog('alert','提示',data.message,2000,function(){ $("#unEmailVerify").removeAttr("disabled"); });
						}
					},
					error:function(){
						$("#unEmailVerify").removeAttr("disabled");
					}
				});
			});
		});
	});

</script>


</body>
</html>
