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
   	  	 <div class="flex-2 text-center">提现申请</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="s-container">
	  <div class="pad10">
	  	  <div class="tiperror"></div>
	  	  <form id="loginForm" method="post" action="${(domainUrlUtil.urlResources)!}/member/doapplydrawing.html" onsubmit="return check()">
	  	    <div class="form-group">
	  	    可用余额：${(balance)!'0'}
	  	    </div>
	  	    <div class="form-group">
	  	      <input type="text" class="form-control" id="bank" name="bank" placeholder="银行名称">
	  	    </div>
	  	    <div class="form-group">
	  	      <input type="text" class="form-control" id="bankCode" name="bankCode" placeholder="银行卡号">
	  	    </div>
	  	    <div class="form-group">
	  	      <input type="text" class="form-control" id="money" name="money" placeholder="申请提现金额">
	  	    </div>
	  	    <button type="submit" class="btn btn-block" id="loginBtn">提交申请</button>
	  	  </form>
	  </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
var message = "${(message)!''}";
if(message != "") {
	// alert(message);
	$.dialog('alert','提示',message,2000);
}
function check(){
	var balance = ${(balance)!0};
	if(balance == 0) {
		// alert("您的账户没有余额可供提取");
		$.dialog('alert','提示','您的账户没有余额可供提取',2000);
		return false;
	}
	var bank = $("#bank").val();
	if(bank == "") {
		// alert("银行名称不能为空");
		$.dialog('alert','提示','银行名称不能为空',2000);
		return false;
	}
	var bankCode = $("#bankCode").val();
	if(bankCode == "") {
		// alert("银行卡号不能为空");
		$.dialog('alert','提示','银行卡号不能为空',2000);
		return false;
	}
	var money = $("#money").val();
	if(money == "") {
		// alert("提款金额不能为空");
		$.dialog('alert','提示','提款金额不能为空',2000);
		return false;
	}
	
	var reg = new RegExp("^([1-9][0-9]*)+(.[0-9]{1,2})?$");  
    if(!reg.test(money)){  
        // alert("提款金额必须是数字");
		$.dialog('alert','提示','提款金额必须是数字',2000);
        return false; 
    }  
    if(money > balance) {
    	// alert("提款金额不能大于实际的金额");
    	$.dialog('alert','提示','提款金额不能大于实际的金额',2000);
        return false;
    }
	return true;
}
</script>	

</body>
</html>