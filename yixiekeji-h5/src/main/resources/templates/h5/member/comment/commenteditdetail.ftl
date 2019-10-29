<form id="commentForm">
	    		<div class="bgff pad10 evalute-list2">
			    	<div class="starbox">
			    	    <div class="stararrow-up"></div>
			    		<div class="starlist clear">
			    		  <span>评分:</span>
			    		  <div class="star1"></div>
			    		</div>
			    		<div class="starlist clear">
			    		  <span>描述相符:</span>
			    		  <div class="star2"></div>
			    		</div>
			    		<div class="starlist clear">
			    		  <span>服务态度:</span>
			    		  <div class="star3"></div>
			    		</div>
			    		<div class="starlist clear">
			    		  <span>发货速度:</span>
			    		  <div class="star4"></div>
			    		</div>
			    		<div class="starlist flex padt0">
			    		  <div class="pad-top">心得:&nbsp;</div>
			    		  <div class="flex-2 expertxt" style="margin-top:-13px;">
			    		      <p class="text-right font12 cl70">10~100字</p>
			    		  	  <textarea class="form-control" rows="3" id="content" name="content" <#if comment?? && (comment.id)?? >disabled="disabled"</#if> >${(comment.content)!''}</textarea>
			    		  </div>
			    		</div>
			    		<div style="padding:0 0 10px 45px;">
			    		  	  <font id="errLabel" class="font12 clr53"></font>
			    		</div>
			    		<#if !(comment??) >
			    		<div class="text-center mar-bt">
			    			<button type="button" class="btn o-d-btn1 o-d-btn2" id="commentSubmit">提交</button>
			    		</div>
			    		</#if>
			    	</div>
			    </div>

<input type="hidden" id="orderSn" name="orderSn" value="${orderSn!''}"/>
<input type="hidden" id="productId" name="productId" value="${productId!'0'}"/>
<input type="hidden" id="productGoodsId" name="productGoodsId" value="${productGoodsId!'0'}"/>
<input type="hidden" id="ordersProductId" name="ordersProductId" value="${ordersProductId!'0'}"/>

<input type="hidden" id="grade" name="grade" value="${(comment.grade)!''}"/>
<input type="hidden" id="description" name="description" value="${(comment.description)!''}"/>
<input type="hidden" id="serviceAttitude" name="serviceAttitude" value="${(comment.serviceAttitude)!''}"/>
<input type="hidden" id="productSpeed" name="productSpeed" value="${(comment.productSpeed)!''}"/>

<input type="hidden" id="commentId"  name="id" value="${(comment.id)!''}"/>

</form>

<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.hDialog.js"></script>
<!-- 星级评分 -->
<script type="text/javascript" src="${(domainUrlUtil.staticResources)!}/js/jquery.raty.min.js"></script>

<script type="text/javascript">
	$(function(){
		$.fn.raty.defaults.path = '${(domainUrlUtil.staticResources)!}/img';
		var commentId = $("#commentId").val();
		if (commentId != null && parseInt(commentId) > 0) {
			// 说明已经评论过了
			$('.star1').raty({ readOnly: true, score: ${(comment.grade)!0} });
			$('.star2').raty({ readOnly: true, score: ${(comment.description)!0} });
			$('.star3').raty({ readOnly: true, score: ${(comment.serviceAttitude)!0} });
			$('.star4').raty({ readOnly: true, score: ${(comment.productSpeed)!0} });
		} else {
			$('.star1').raty({ readOnly: false, score: 0 });
			$('.star2').raty({ readOnly: false, score: 0 });
			$('.star3').raty({ readOnly: false, score: 0 });
			$('.star4').raty({ readOnly: false, score: 0 });
			
			$('.star1').raty({ click: function (score, evt) {$("#grade").val(score);} });
			$('.star2').raty({ click: function (score, evt) {$("#description").val(score);} });
			$('.star3').raty({ click: function (score, evt) {$("#serviceAttitude").val(score);} });
			$('.star4').raty({ click: function (score, evt) {$("#productSpeed").val(score);} });
		}
		
		$("#commentSubmit").click(function() {
			var grade = $("#grade").val();
			var description = $("#description").val();
			var serviceAttitude = $("#serviceAttitude").val();
			var productSpeed = $("#productSpeed").val();
			
			if (grade == 0 || description == 0 || serviceAttitude == 0 || productSpeed == 0) {
				$("#errLabel").html("请选择评论分数");
				return;
			}
			
			var content = $("#content").val();
			if (content == null || content == "") {
				$("#errLabel").html("请输入评论内容");
				return;
			}
			if (content.length < 10) {
				$("#errLabel").html("请至少输入10个字符");
				return;
			}
			if (content.length > 100) {
				$("#errLabel").html("最多输入100个字符");
				return;
			}
			
			$("#commentSubmit").attr("disabled","disabled");
			var params = $('#commentForm').serialize();
			$.ajax({
				type:"POST",
				url:domain+"/member/savecomment.html",
				dataType:"json",
				async : false,
				data : params,
				success:function(data){
					if(data.success){
						// alert('保存成功');
						$.dialog('alert','提示',"评价成功",2000);
						$(".commentDetailDiv").each(function(){
							$(this).empty();
							$(this).hide();
						});
					}else{
						// alert(data.message);
						$.dialog('alert','提示',data.message,2000);
						$("#commentSubmit").removeAttr("disabled");
					}
				}
			}); 
		});
	});

</script>
