
<form id="addevaluationform">
	<input type="hidden" id="commentId" name="id"
		value="${(comment.id)!''}" /> <input type="hidden" name="productId"
		value="${productId}" /> <input type="hidden" name="sellerId"
		value="${(comment.sellerId)!''}" /> <input type="hidden"
		name="orderSn" value="${orderSn}" /> <input type="hidden"
		name="productGoodsId" value="${(productGoodsId)!''}" />
		<input type="hidden" name="ordersProductId" value="${(ordersProductId)!''}" />
	<!-- 评分 动态变化 -->
	<input type="hidden" id="grade" name="grade"
		value="${(comment.grade)!''}">
	<!-- 描述相符 动态变化 -->
	<input type="hidden" id="description" name="description"
		value="${(comment.description)!''}">
	<!-- 服务态度(1到5星) 动态变化 -->
	<input type="hidden" id="serviceAttitude" name="serviceAttitude"
		value="${(comment.serviceAttitude)!''}">
	<!-- 发货速度(1到5星) 动态变化 -->
	<input type="hidden" id="productSpeed" name="productSpeed"
		value="${(comment.productSpeed)!''}">


	<!-- 编辑评价 -->
	<div class='box-t'></div>
	<div class='evaluat-form'>
		<div class='item'>
			<span class="label"> <em>*</em> 评分：
			</span>
			<div class='fl'>
				<span class='commstar' attrname="grade"
					value="${(comment.grade)!''}"> <a href='javascript:void(0);'
					class='star1' val="1"></a> <a href='javascript:void(0);'
					class='star2' val="2"></a> <a href='javascript:void(0);'
					class='star3' val="3"></a> <a href='javascript:void(0);'
					class='star4' val="4"></a> <a href='javascript:void(0);'
					class='star5' val="5"></a>
				</span>
			</div>
			<span class='msg-error-01 hide'></span>
		</div>

		<div class='item'>
			<span class="label"> <em>*</em> 描述相符：
			</span>
			<div class='fl'>
				<span class='commstar' attrname="description"
					value="${(comment.description)!''}"> <a
					href='javascript:void(0);' class='star1' val="1"></a> <a
					href='javascript:void(0);' class='star2' val="2"></a> <a
					href='javascript:void(0);' class='star3' val="3"></a> <a
					href='javascript:void(0);' class='star4' val="4"></a> <a
					href='javascript:void(0);' class='star5' val="5"></a>

				</span>
			</div>
			<span class='msg-error-01 hide'></span>
		</div>
		<div class='item'>
			<span class="label"> <em>*</em> 服务态度：
			</span>
			<div class='fl'>
				<span class='commstar' attrname="serviceAttitude"
					value="${(comment.serviceAttitude)!''}"> <a
					href='javascript:void(0);' class='star1' val="1"></a> <a
					href='javascript:void(0);' class='star2' val="2"></a> <a
					href='javascript:void(0);' class='star3' val="3"></a> <a
					href='javascript:void(0);' class='star4' val="4"></a> <a
					href='javascript:void(0);' class='star5' val="5"></a>
				</span>
			</div>
			<span class='msg-error-01 hide'></span>
		</div>

		<div class='item'>
			<span class="label"> <em>*</em> 发货速度：
			</span>
			<div class='fl'>
				<span class='commstar' attrname="productSpeed"
					value="${(comment.productSpeed)!''}"> <a
					href='javascript:void(0);' class='star1' val="1"></a> <a
					href='javascript:void(0);' class='star2' val="2"></a> <a
					href='javascript:void(0);' class='star3' val="3"></a> <a
					href='javascript:void(0);' class='star4' val="4"></a> <a
					href='javascript:void(0);' class='star5' val="5"></a>
				</span>
			</div>
			<span class='msg-error-01 hide'></span>
		</div>


		<div class='item'>
			<span class="label"> <em>*</em> 心得：
			</span>
			<div class='cont'>
				<textarea class='area area01' name='content'>${(comment.content)!''}</textarea>
				<span class='msg-error-01 hide'></span>
			</div>
		</div>
		<div class='item'>
			<a href='javascript:void(0)' class='btn btn-danger cr '>评价</a>
		</div>
	</div>
</form>



<script type="text/javascript">
	$(function() {
		if ($("#commentId").val() != '') {
			$(".btn-danger").hide();
			$("textarea").attr('readonly', 'readonly');
		} else {
			//评分点击事件
			$(".commstar a").click(function() {
				$(this).addClass("active").siblings().removeClass("active");
				var attrname = $(this).parent('span').attr('attrname');
				$("#" + attrname).val($(this).attr('val'));
			});
		}

		//初始化星星 
		$(".commstar").each(function() {
			var index = $(this).attr('value');
			$(this).children(".star" + index).addClass("active");
		});

		//校验
		jQuery("#addevaluationform").validate(
				{
					errorPlacement : function(error, element) {
						var obj;
						if (element.context.type == 'hidden') {
							var id = element.context.id;
							obj = $("span[attrname=" + id + "]").parent()
									.siblings(".msg-error-01").removeClass(
											'hide');
						} else {
							var obj = element.nextAll(".msg-error-01")
									.removeClass('hide');
						}
						error.appendTo(obj);
					},
					rules : {
						"grade" : {
							biggerThanZero : true
						},
						"description" : {
							biggerThanZero : true
						},
						"serviceAttitude" : {
							biggerThanZero : true
						},
						"productSpeed" : {
							biggerThanZero : true
						},
						"content" : {
							required : true,
							minlength : 10,
							maxlength : 500
						}
					},
					messages : {
						"grade" : {
							biggerThanZero : "请评分"
						},
						"description" : {
							biggerThanZero : "请评分"
						},
						"serviceAttitude" : {
							biggerThanZero : "请评分"
						},
						"productSpeed" : {
							biggerThanZero : "请评分"
						},
						"content" : {
							required : "请输入评价",
							minlength : "不能小于10个字呦",
							maxlength : "不能超过500个字呦"
						}
					}
				});

		$(".btn-danger").click(function() {
			if ($("#addevaluationform").valid()) {
				$(".btn-danger").attr("disabled", "disabled");
				var params = $('#addevaluationform').serialize();

				var f1_ = $(".evaluat-form").find("span[attrname='grade']").find("a.active");
				var f2_ = $(".evaluat-form").find("span[attrname='description']").find("a.active");
				var f3_ = $(".evaluat-form").find("span[attrname='serviceAttitude']").find("a.active");
				var f4_ = $(".evaluat-form").find("span[attrname='productSpeed']").find("a.active");
				
				if(f1_.length < 1){
					jAlert('请为商家评分');
					$(".btn-danger").removeAttr("disabled");
					return false;
				}
				if(f2_.length < 1){
					jAlert('请为商家描述相符打分');
					$(".btn-danger").removeAttr("disabled");
					return false;
				}
				if(f3_.length < 1){
					jAlert('请为商家服务态度打分');
					$(".btn-danger").removeAttr("disabled");
					return false;
				}
				if(f4_.length < 1){
					jAlert('请为商家发货速度评分');
					$(".btn-danger").removeAttr("disabled");
					return false;
				}
				
				$.ajax({
					type : "POST",
					url : domain + "/member/savecomment.html",
					dataType : "json",
					async : false,
					data : params,
					success : function(data) {
						if (data.success) {
							//jAlert("保存成功");
							//location.replace(location.href);

							jAlert('保存成功', '提示', function() {
								location.replace(location.href)
							});
						} else {
							jAlert(data.message);
							$(".btn-danger").removeAttr("disabled");
						}
					},
					error : function() {
						jAlert("异常，请重试！");
						$(".btn-danger").removeAttr("disabled");
					}
				});
			}

		});
	});
</script>
