<#include "/h5/commons/_head.ftl" />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">收银台</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
   	<@form.form action="" id="payForm" name="payForm" method="GET">
		<input type="hidden" name="orderSn" id="orderSn" value="${orderSn!''}">
		<div>
	         <div class="p-o-infor">
	           <p>
				订单提交成功，请您尽快付款！    订单号：  
				<#if orderSn?? >
					<font>${orderSn}</font>
				</#if>
				
	            </p>
	            <p class="font12 pad-top">请您在提交订单后<font class="clr53">24小时内</font>完成支付,否则订单会自动取消</p>
	         </div>
	         
	        
	         <ul class="p-o-method">
	            <li class="flex flex-pack-justify font16">
	               <div>选择支付方式</div>
	               <div class="clr53">${(payAmount)?string('0.00')!'' }元</div>
	            </li>
	            <li class="flex flex-pack-justify" id="zhifubao" style="display:none;">
	               <div>支付宝</div>
	               <div><input name="optionsRadios" type="radio" value="alipay" checked="checked" class="p-radio" ></div>
	            </li>
	            <!-- <li class="flex flex-pack-justify" id="yinlian" style="display:none;">
	               <div>中国银联</div>
	               <div><input name="optionsRadios" type="radio" value="unionpay" class="p-radio" checked="checked"></div>
	            </li> -->
	            <li class="flex flex-pack-justify" id="wxzf" style="display:none;">
	               <div>微信支付</div>
	               <div><input name="optionsRadios" type="radio" value="wxpay" class="p-radio"></div>
	            </li>
	            <li class="flex flex-pack-justify" id="wxh5" style="display:none;">
	               <div>微信支付</div>
	               <div><input name="optionsRadios" type="radio" value="wxh5" class="p-radio"></div>
	            </li>
	         </ul>
	         
	         <div class="add-balance pad10">
	            <p>
	            	<input type='checkbox' id='selectOrderBalance' name="selectOrderBalance" autocomplete="off" >
	            	&nbsp;使用余额(账户当前余额${(member.balance)?string('0.00')!'0.00' }元)</p>
	            <p class="mar_top">
	            	支付密码：
	            	<input class="form-control add-form-control" type='password' id='balancePassword' name="balancePassword" disabled >
	            </p>
	         </div>
	         <div class="pad10"><button type="button" class="btn btn-block btn-login" id='PayButtom'>立即支付</button></div>
	    </div>
	</@form.form>
	<!-- 主体结束 -->

	<script>
		// 必须在引用_footer.ftl或_pageBackStack.ftl前定义
		var intoBackStack = false;
	</script>
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />
 <script type="text/javascript">
$(function(){
	//选中余额checkbox 
	$("#selectOrderBalance").click(function(){
		//如果余额小于等于0 那么不允许选中
		<#if member??&&member.balance??>
			<#if member.balance<=0 >
				$(this).prop("checked", false);
				return;
			</#if>
		</#if>

		if($(this).prop("checked")){
			$("#balancePassword").removeAttr("disabled");
		}else{
			$("#balancePassword").attr("disabled","disabled");
			$("#balancePassword").val("");
		}
	});
	
	$("#PayButtom").click(function(){

		// 调用APP
  		var optionsRadios = $('input[name="optionsRadios"]:checked').val();
  		var orderSn = $("#orderSn").val();
  		
  		var u = navigator.userAgent;
  		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
  		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

  		
		var balancePwd = $("#balancePassword").val();
  		if($("#selectOrderBalance").prop("checked")){
  			if(balancePwd == null || balancePwd == ""){
  				// alert("密码不能为空");
  				$.dialog('alert','提示','密码不能为空',2000);
  				$("#balancePassword").focus();
  				return false;
  			}
  			//验证支付密码
  			var checkpwd = checkBalancePwd(balancePwd);
  			if(!checkpwd){
  				return false;
  			}

  			// 支付提交
  			var param = $("#payForm").serialize();
			var actionUrl = "${(domainUrlUtil.urlResources)!}/paybalance.html";
			$.ajax({
				type : "GET",
				dataType : "json",
				url : actionUrl,
				data : param,
				async:false,
				success : function(result) {
					if (result.success ) {
						if(result.backUrl != null){
							// 跳转到支付成功页面
							var url = "${(domainUrlUtil.urlResources)!}/paybalsuc.html";
							window.location.href = url;
						}else{
							if(isAndroid){
					  			window.phoneFunc.jsCallWebView("toPay,"+optionsRadios+","+orderSn);
					  		}else{
					  			window.webkit.messageHandlers.toPay.postMessage(optionsRadios+","+orderSn);
					  	  	}
						}
					} else {
						$.dialog('alert','提示',result.message,2000);
					}
				},
				error : function(error) {
	 				$.dialog('alert','提示','亲爱的用户请不要频繁点击, 请稍后重试...',2000);
				}
			});
		
  		}else{
	  		if(isAndroid){
	  			window.phoneFunc.jsCallWebView("toPay,"+optionsRadios+","+orderSn);
	  		}else{
	  			window.webkit.messageHandlers.toPay.postMessage(optionsRadios+","+orderSn);
	  	  	}
  		}
		
	});
	
});


	//验证支付密码
	function checkBalancePwd(balancePwd){
		var correct = false;
		$.ajax({
			type : "GET",
			url :  domain+"/order/checkbalancepwd.html",
			data : {balancePwd:balancePwd},
			dataType : "json",
			async:false,
			success : function(data) {
				if(data.success){
					correct = data.data.correct;
					var errcount = parseInt(data.data.pwdErrCount);
				   	if(errcount>=6){
				   		$.dialog('alert','提示','支付密码输错超过6次,请用其他方式支付',2000);
						$(".toggle-title").click();
						return false;
				   	}
					if(!correct){
				   		$.dialog('alert','提示',"支付密码不正确，您最多还可以输入"+(6-errcount-1)+"次",2000);
						return false;
					}
				}else {
					$.dialog('alert','提示',data.message,2000);
					return false;
				}
			},
			error : function() {
				$.dialog('alert','提示','验证密码失败！',2000);
			}
		});
		return correct;
	}

	var browser = {
            versions: function () {
            var u = navigator.userAgent;
            return { //移动终端浏览器版本信息
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1,//是否iPad
                MicroMessenger: u.indexOf('MicroMessenger') > -1
            };
        }()
    };
    // 支付宝
    var alipay = $("#zhifubao"),
    // 中国银联
    //unionpay = $("#yinlian"),
    // 微信支付（微信端）
    weChat = $("#wxzf");
    // 微信支付（app端）
    appWeChat = $("#wxh5");
    function test(){
    	var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            //unionpay.show();
            weChat.show();
        }else if(browser.versions.android) {
        	alipay.show();
        	//unionpay.show();
            appWeChat.show();
        }else if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
        	alipay.show();
            //unionpay.show();
            appWeChat.show();
        }
    }
    window.onload = test;
</script>

</body>
</html>