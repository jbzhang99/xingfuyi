<#include "/h5/commons/_head.ftl" />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<style>
.starbox {
    border-top: solid 1px #f2f2f2;
    /* position: relative; */
    border: solid 1px #edf9e5;
    border-radius: 4px;
    background: #edf9e5;
}
select.cselct {
	appearance:none;
	-moz-appearance:none;
	-webkit-appearance:none;
	background:url("${(domainUrlUtil.staticResources)!}/img/arrow.png") no-repeat scroll right center transparent;
	background-color: #fff;
	padding-right: 14px;
}
</style>
<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
  	  	 	<a href="javascript:ejsPageBack();">
  	  	 		<span class="fa fa-angle-left"></span>
  	  	 	</a>
	 	 </div>
   	  	 <div class="flex-2 text-center">退件发货</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   	<@form.form  id ="productBackForm" action="${(domainUrlUtil.urlResources)!}/member/doBackDeliverGoods.html">
	<!-- 隐藏域 -->
	<input type="hidden" id="id" name="id" value="${(memberProductBack.id)!0}">
	<div class=""  >
	    <div class="mar-bt pos_relative">
	    	<dl class="flex list-dl">
	    		<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}.html?goodId=${(orderProduct.productGoodsId)!0}' class="block">
	    			<dt style="width:80px; height:80px;"><img src="${(domainUrlUtil.imageResources)!}${orderProduct.productLeadLittle}"></dt>
	    		</a>
	    		<dd class="padl flex-2">
	    			<a href='${(domainUrlUtil.urlResources)!}/product/${(orderProduct.productId)!0}.html?goodId=${(orderProduct.productGoodsId)!0}' class="block">
	    				<div class="product_name mar_top">${(orderProduct.productName)!''}&nbsp;${(orderProduct.specInfo)!''}</div>
	    			</a>
	    			<div class="flex flex-pack-justify goods-sun">
	    			<div>购买时间：<font>${(orderProduct.createTime?string("yyyy-MM-dd"))!''}</font></div>
	    			<div></div>
	    			</div>
	    		</dd>
	    	</dl>

	    	<div class="applyDetailDiv">
	    		<div class='bgff pad10 evalute-list2'>
					<div class='starbox'>
					      <div class='stararrow-up'></div>
					      <div class='starlist flex pad10'>
							  <i>快递公司：</i>
							  <div class='' style='margin-left:8px;'>
								  	<select  class="form-control cselct" id="logisticsId" name="logisticsId" style="width:16rem;"> 
										<#if courierCompanyList?? && courierCompanyList?size &gt; 0>
											<#list courierCompanyList as courierCompany>
												<option value="${(courierCompany.id)!''}">${(courierCompany.companyName)!''}</option>
											</#list>
										</#if>
									</select>
							  </div>
						  </div>
					      <div class='stararrow-up'></div>
					      <div class='starlist flex pad10'>
							  <i>快递单号：</i>
							  <div class='s-exchange' style='margin-left:8px;'>
							  		<input type="text" name="logisticsNumber" id="logisticsNumber" class="form-control" style="width:16rem;">
							  </div>
						</div>
						<div class='text-center mar-bt'>
							<button type='button' class='btn o-d-btn1 o-d-btn2' id='applySubmitId' onclick='applySubmit()'>提交</button>
						</div>
					</div>
				</div>
	    	</div>
	    </div>
    </div>
    </@form.form>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
	var number = 0;
	$(function(){
		
	});


	
	function applySubmit() {
		// logisticsId 
		var logisticsId = $("#logisticsId").val();
		if (logisticsId == null || logisticsId == "") {
			$.dialog('alert','提示','请选择物流公司',2000);
			return;
		}
		
		var logisticsNumber = $("#logisticsNumber").val();
		if (logisticsNumber == null || logisticsNumber == "") {
			$.dialog('alert','提示','请输入物流单号',2000);
			return;
		}
		
		$("#applySubmitId").attr("disabled","disabled");
		var params = $('#productBackForm').serialize();
		$.ajax({
			type:"POST",
			url:$('#productBackForm').attr("action"),
			dataType:"json",
			data : params,
			success:function(data){
				if(data.success){
					$.dialog('alert','提示','保存成功',2000);
					window.location.href= "${(domainUrlUtil.urlResources)!}/member/back.html";
				}else{
					$.dialog('alert','提示',data.message,2000,function(){ $("#applySubmitId").removeAttr("disabled"); });
				}
			}
		}); 
	}
</script>
 
</body>
</html>