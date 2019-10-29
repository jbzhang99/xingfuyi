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
   	  	 <div class="flex-2 text-center">设置支付密码</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
	
	<div class="s-container">
	  <div>

		  <div class="user-infor user-infor2 bgff mar-bt">
		    <form id="editPasswordform">
		  	 <div class="pad10 flex bor-b2 input-line">
		  	 	<div class="u-label text-right">用户名:</div>
                <div class="flex-2">${(member.name)!""}</div>
		  	 </div>
		  	 <div class="pad10 flex bor-b2 input-line">
		  	 	<div class="u-label">新密码:</div>
                <div class="flex-2">
                	<input type="password" id='txt-newpw' name='password' placeholder="6-20位数字、字母或符号组合">
                	<br>
                	<em class='em-errMes'></em>
                </div>
		  	 </div>
		  	 <div class="pad10 flex bor-b2 input-line">
		  	 	<div class="u-label">确认密码:</div>
                <div class="flex-2">
                	<input type="password" id='txt-repw' name='confirmPwd' placeholder="6-20位数字、字母或符号组合">
                	<br>
                	<em class='em-errMes'></em>
                </div>
		  	 </div>
		  	 <div style="padding:10px 0 0 10px;"><font id="errLabel" class="font12 clr53"></font></div>
		  	 
		  	 <div class="text-center padt_b10">
		  	 	 <button class="btn btn-login" type="submit" style="padding:6px 12px;" id='buttonSubmit'>确认提交</button>
		  	 </div>
		  	 
		  	</form>
		  </div>

	  </div>
    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	$(function(){
	
	
		$("#buttonSubmit").click(function(){
			var txtnewpw = $("#txt-newpw").val();
			var txtrepw = $("#txt-repw").val();
			
			if (txtnewpw == null || txtnewpw == "") {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入密码");
				return false;
			}
			if (txtnewpw.length < 6 || txtnewpw.length > 20) {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入6-20位密码");
				return false;
			}
			if (txtrepw == null || txtrepw == "") {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入确认密码");
				return false;
			}
			if (txtrepw.length < 6 || txtrepw.length > 20) {
				$("#errLabel").html("<i class='fa fa-warning'></i> 请输入6-20位确认密码");
				return false;
			}
			if (txtnewpw != txtrepw) {
				$("#errLabel").html("<i class='fa fa-warning'></i> 确认密码不对");
				return false;
			}
		
			$("#buttonSubmit").attr("disabled","disabled");
			var params = $('#editPasswordform').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/member/savebalancepassword.html",
				dataType:"json",
				data : params,
				success:function(data){
					if(data.success){
						// alert("密码设定成功");
						$.dialog('alert','提示','密码设定成功',2000,function(){ window.location.href=domain+"/member/balance.html"; });
					}else{
						$("#errLabel").html(data.message);
						$("#buttonSubmit").removeAttr("disabled");
					}
				}
			});
		});
	});
</script>

</body>
</html>