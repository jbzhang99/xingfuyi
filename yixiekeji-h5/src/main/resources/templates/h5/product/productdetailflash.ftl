<#include "/h5/commons/_head.ftl" />
<style>
	.sel-btn .disabled{
		color: #ccc;
	    border: 1px solid #ccc;
	    box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0);
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
   	  	 <div class="flex-2 text-center">详细介绍</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="s-container" id="container" style="padding-bottom:60px;">
		<!-- 轮播 -->
		<div style="width:100%; overflow:hidden; padding:10px 0; border-bottom:solid 1px #dedede; text-align:center;">
			<div class="swiper-container bgff" >
			    <div class="swiper-wrapper">
			    	<#if productLeadPicList?? && productLeadPicList?size &gt; 0 >
			    		<#list productLeadPicList as pic >
					        <div class="swiper-slide"><img src="${(domainUrlUtil.imageResources)!}${pic!''}" height="320"></div>
					    </#list>
			        </#if>
			    </div>
			    <div class="swiper-pagination"></div>
			</div>
		</div>
		
		<div class="seckil-time">
	      <div class="seckil-price-wrap">
	        <div class="seckill-price">
	          ¥<span class="seckill-big-price"  id="productPriceSeckil">${(actFlashSaleProduct.price)?string("0.00")!'999999'}</span>
	        </div>
	        <div class="seckill-btm-div">
	          <span class="skf-icon-pos">
	            <i class="label-icon"></i>
	            <span class="label-text">掌上秒杀</span>
	          </span>
	          <span class="skf-jdPrice">¥${(goods.mallMobilePrice)?string("0.00")!'999999'}</span>
	        </div>
	      </div>
	      <div class="seckil-time-wrap">
	      	<#if stageType?? && stageType == 2>
		        <span class="seckil-time-title">距结束还剩:</span>
		        <div class="seckill-time-div">
		          <span class="seckill-time-num hour_show">00</span>
		          <span class="seckill-time-colon">:</span>
		          <span class="seckill-time-num minute_show">00</span>
		          <span class="seckill-time-colon">:</span>
		          <span class="seckill-time-num second_show">00</span>
		        </div>
	        <#elseif stageType?? && stageType == 3>
	        		<span class="seckil-time-title">距开始还剩:</span>
		        <div class="seckill-time-div">
		          <span class="seckill-time-num hour_show">00</span>
		          <span class="seckill-time-colon">:</span>
		          <span class="seckill-time-num minute_show">00</span>
		          <span class="seckill-time-colon">:</span>
		          <span class="seckill-time-num second_show">00</span>
		        </div>
	        <#else>
	        		<span class="seckil-time-title">掌上秒杀已结束:</span>
		        <div class="seckill-time-div">
		          <span class="seckill-time-num hour_show">00</span>
		          <span class="seckill-time-colon">:</span>
		          <span class="seckill-time-num minute_show">00</span>
		          <span class="seckill-time-colon">:</span>
		          <span class="seckill-time-num second_show">00</span>
		        </div>
	        </#if>
	      </div>
	    </div>

		<!-- 详情列表 -->
		<#if product?? && product.state?? && product.state == 6 && actFlashSaleProduct?? && actFlashSaleProduct.status == 2>
			<div class="goods-infor">
				<div class="bor-btom mar-bt">
					<div class="flex">
						<div style="padding-right:10px;">${(product.name1)!''}</div>
					</div>
					<div class="salesty  pad-bt">${(product.name2)!''}</div>
				</div>
				
	
				<div class="bor-btom mar-bt">
					
					<#if norms?? && norms?size &gt; 0>
						<div class="flex pad-bt">
					    	<div class="pad-r">已选</div>
					    	<div class="flex-2" id="normAttr0"></div>
					    	<div class="flex-2" id="normAttr1"></div>
					    	<div class="flex-2" id="amount"></div>
					    	<!-- <div class="flex-2">小新</div> -->
					    </div>
						<#list norms as norm>
							<div class="flex pad-bt choosenorms">
								<div class="pad-r">${norm.name}：</div>
								<div class="flex-2 sel-btn" id="normsDiv${norm_index}">
									<#list norm.attrList as normattr>
										<a class="btn btn-default norm-min" id="${(normattr.id)!0}" 
										val="${normattr.id}"
										onclick="chooseNorm(this, ${norm_index}, ${(normattr.id)!0}, '${(normattr.name)!}')">${(normattr.name)!""}</a>
									</#list>
								</div>
							</div>
						</#list>
					</#if>
					<!-- 隐藏域 -->
					<!-- 规格值ID  -->
					<input  type="hidden" id="specId0" name="specId0" >
					<input  type="hidden" id="specId1" name="specId1" >
					<!-- 产品ID -->
					<input  type="hidden" id="productId" name="productId" value="${productId!''}">
					<input  type="hidden" id="productGoodsId" name="productGoodsId" value="${(goods.id)!''}">
					<input  type="hidden" id="goodsNormAttrId" name="goodsNormAttrId" value="${(goods.normAttrId)!''}">

					<div class="flex pad-bt">
						<div class="pad-r">库存：${(actFlashSaleProduct.stock)!0}件</div>
					</div>
				</div>

				<a href="${(domainUrlUtil.urlResources)!''}/product/info/${(product.id)!0}.html?type=1" class="block">
					<div class="flex flex-pack-justify bor-btom pad-bt mar-bt">
						<div >详细介绍</div>
						<div ><span class="fa fa-angle-right"></span></div>
					</div>
				</a>
				<a href="${(domainUrlUtil.urlResources)!''}/product/spec/${(product.id)!0}.html?type=1" class="block">
					<div class="flex flex-pack-justify bor-btom pad-bt mar-bt">
						<div >规格参数</div>
						<div ><span class="fa fa-angle-right"></span></div>
					</div>
				</a>
				<a href="${(domainUrlUtil.urlResources)!''}/product/comment/${(product.id)!0}.html?type=1" class="block">
					<div class="flex flex-pack-justify bor-btom pad-bt mar-bt">
						<div >商品评价&nbsp;<font class="clr53">${(statisticsVO.productCommentsHighProportion)!"100"}%</font>好评</div>
						<div >${(statisticsVO.productCommentsAllCount)!"0"}人评价&nbsp;<span class="fa fa-angle-right"></span></div>
					</div>
				</a>
				<a href="${(domainUrlUtil.urlResources)!''}/product/ask/${(product.id)!0}.html?type=1" class="block">
					<div class="flex flex-pack-justify bor-btom pad-bt mar-bt">
						<div >商品咨询&nbsp;<font class="clr53">${(statisticsVO.productAskCount)!"0"}</font>条</div>
						<div ><span class="fa fa-angle-right"></span></div>
					</div>
				</a>
	
			</div>
	        <!--  -详情列表end-->
	        
	        <!-- 店铺详情 -->
	        <div class="pad10 bgff" >
		    	<h2>
		    		<div><i class="fa fa-institution"></i>${(seller.sellerName)!''}</div>
		    	</h2>
		    	<div class="flex flex-pack-justify s-score">
		    		<div>商品评分：<font class="clr53">${(seller.scoreDescription)!'0'}</font></div>
		    		<div>服务态度：<font class="clr53">${(seller.scoreService)!'0'}</font></div>
		    	</div>
		    	<div class="flex flex-pack-justify s-score">
		    		<div>物流速度：<font class="clr53">${(seller.scoreDeliverGoods)!'0'}</font></div>
		    		<div>关注：<font class="clr53">${(seller.collectionNumber)!'0'}</font></div>
		    	</div>
		    	<div class="text-center pad-top">
		    	   <a href="${(domainUrlUtil.urlResources)!}/store/${(seller.id)!0}.html" class="btn btn-default s-btn" role="button">进入店铺</a>
		    	   <#if statisticsVO??> 
						<#if statisticsVO.collectedShop=true>
							<a id="collectShop" href="javascript:;" class="btn btn-default s-btn" role="button" onclick="disCollectShop('${(seller.id)!''}')">取消收藏</a>
						<#else>
							<a id="collectShop" href="javascript:;" class="btn btn-default s-btn" role="button" onclick="collectShop('${(seller.id)!''}')">收藏店铺</a>
						</#if>
				   </#if> 
		    	</div>
		    </div>
	        <!-- 底部固定菜单 -->
	        <div class="fixed-nav">
	        	<div class="flex">
		        	<div class="flex-1">
		        		<a href="javascript:;" onclick="collectProduct()" class="block">
		        			<#if statisticsVO?? && statisticsVO.collectedProduct>
		        				<span class="fa fa-heart current"></span><font id="collectSpan">取消</font>
		        			<#else>
		        				<span class="fa fa-heart"></span><font id="collectSpan">关注</font>
		        			</#if>
		        		</a>
		        	</div>
	        		
	        		<div class="flex-2 red-cart" >
	        			<#if stageType?? && stageType == 2>
	        			<a id="buyNow" class="block" href="javascript:;">
	        				立即秒杀
	        			</a>
	        			<#elseif stageType?? && stageType == 3>
	        			<a class="block" href="javascript:;">
	        				秒杀即将开始
	        			</a>
	        			<#else>
	        			<a class="block" href="javascript:;">
	        				秒杀已结束
	        			</a>
	        			</#if>
	        		</div>
	        		
	        	</div>
	        </div>
        <#else>
			<div class=" mar-bt">
		        <h3 style="padding:20px 0; background:#fdf5f5; text-align:center;">
		        	<strong>当前商品没有秒杀活动，请到<a style="color:red" href="${(domainUrlUtil.urlResources)!}/product/${(productId)!0}.html">商品详情</a>页查看商品信息！</strong>
		        </h3>
		    </div>
		</#if>
		<!-- footer -->
	    <#include "/h5/commons/_footer.ftl" />
    </div>
    <input type="hidden" id="isCollectProduct" value="${(statisticsVO.collectedProduct)?string('true','false')!'false'}"/>
	<!-- 主体结束 -->

	
	<#include "/h5/commons/_statistic.ftl" />
    
	
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.hDialog.js"></script>
<!-- 轮播 -->
<script src="${(domainUrlUtil.staticResources)!}/swiper/swiper-3.2.7.min.js"></script>
<script>
// 启用的属性
var effectAttr = new Array();
<#noescape>
<#if effectAttr??>
effectAttr = eval('${effectAttr}');
</#if>
</#noescape>
var normsNum = Number("${(normsNum)!'0'}");
</script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/normplugin.js'></script>
<script>
	$(document).ready(function () {
		var mySwiper = new Swiper ('.swiper-container', {
			autoplay: 4000,
			loop: true,
			// 如果需要分页器
			pagination: '.swiper-pagination',
		});
		
		// 默认将规格选中
		var norms = $("#goodsNormAttrId").val();
		if(norms != null && norms != ""){
			$("#amount").html($("#number").val());
			var strs= new Array(); //定义数组 
			strs=norms.split(","); //字符分割 
			for(i=0;i<strs.length;i++){
				$("#normsDiv"+i).find("a").each(function(){
					var attrid = $(this).attr("id");
					if(attrid==strs[i]){
						$("#normAttr"+i).html($(this).html());
						$("#specId"+i).val(attrid);
						$(this).addClass("active").siblings().removeClass("active");
						return;
					}
				});
			}
		}
		
		// 只有规格有多个时需要初始化规格显示
		if (normsNum > 1) {
			NormChecker.init();
		}
		
		$("#buyNow").click(function(){
			
			var stock = ${(actFlashSaleProduct.stock)!0};
			if (parseInt(stock) < 1) {
				$.dialog('alert','提示', "已经被抢光了，下次要赶早哦！",1000);
				return false;
			}
			
			var norms_ = $(".choosenorms");
			if(norms_.length > 1){
				//多规格，都选才能提交
				if(norms_.find("a.active").length < 2){
					$.dialog('alert','提示','请选择规格',2000);
					return;
				}
			} else if(norms_.length == 1){
				if(norms_.find("a.active").length < 1){
					$.dialog('alert','提示','请选择规格',2000);
					return;
				}
			}
			
			//未登录不能立即购买
			if (!isUserLogin()) {
				// 未登录跳转到登陆页面
				var toUrl = domain + "/product/${(productId)!0}.html?type=1&goodId=" + $("#productGoodsId").val();
				window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
				return;
			}
			
			var goodsId = $("#productGoodsId").val();
			window.location.href=domain+"/order/flashsale-" + ${productId!''}+ "-" + goodsId + "-" + ${(seller.id)!''} + ".html";
		});
		
		timer(intDiff);
	});
	
	// 倒计时
	var intDiff = parseInt('${(countTime)!0}');//倒计时总秒数量
	function timer(intDiff) {
		window.setInterval(function() {
			var day = 0, hour = 0, minute = 0, second = 0;//时间默认值       
			if (intDiff > 0) {
				day = Math.floor(intDiff / (60 * 60 * 24));
				hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				minute = Math.floor(intDiff / 60) - (day * 24 * 60)
						- (hour * 60);
				second = Math.floor(intDiff) - (day * 24 * 60 * 60)
						- (hour * 60 * 60) - (minute * 60);
			}
			if (hour <= 9)
				hour = '0' + hour;
			if (minute <= 9)
				minute = '0' + minute;
			if (second <= 9)
				second = '0' + second;
			$('.hour_show').html('<s id="h"></s>' + hour);
			$('.minute_show').html('<s></s>' + minute);
			$('.second_show').html('<s></s>' + second);
			intDiff--;
		}, 1000);
	}
	
	function chooseNorm(obj, normIndex, normAttrId, normAttrName) {
		if($(this).hasClass("disabled")){
			return;
		}
		$("#normAttr"+normIndex).html(normAttrName);
		$("#specId"+normIndex).val(normAttrId);
		$(obj).addClass("active").siblings().removeClass("active");
		
		var normAttrId0 = $("#specId0").val();
		var normAttrId1 = $("#specId1").val();
		var normAttrIds = "";
		if (normAttrId0 != null && normAttrId0 != "") {
			normAttrIds = normAttrId0;
		}
		if (normAttrId1 != null && normAttrId1 != "") {
			normAttrIds = normAttrIds + "," + normAttrId1;
		}
		
		// 只有规格有多个时需要修改规格显示
		if (normsNum > 1) {
			NormChecker.init();
		}
		$.ajax({
			type : "POST",
			url :  domain+"/getGoodsInfo.html",
			data : {productId:$("#productId").val(), normAttrId:normAttrIds},
			dataType : "json",
			success : function(data) {
				var productGood = data.data;
				if(productGood.id!=null){
					//货品ID
					$("#productGoodsId").val(productGood.id);
					$("#goodsNormAttrId").val(productGood.normAttrId);
				}else{
					//无货品信息 则不能添加购物车和购买
					$.dialog('alert','提示','货品信息为空，请与管理员联系',2000);
					$("#buyNow").attr("disabled","disabled");
					$("#addToCart").attr("disabled","disabled");
				}
			},
			error : function() {
				$.dialog('alert','提示','数据加载失败！',2000);
			}
		});
	}
	
	function collectProduct() {
		//未登录不能添加收藏
		if (!isUserLogin()) {
			// 未登录跳转到登陆页面
			var toUrl = domain + "/product/${(productId)!0}.html?goodId=" + $("#productGoodsId").val();
			window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
			return;
		}
		var isCollectProduct = $("#isCollectProduct").val();
		if (isCollectProduct == "true") {
			$.ajax({
				type:'GET',
				dataType:'json',
				async:false,
				data:{productId:${(productId)!0}},
				url:domain+'/member/cancelcollectproduct.html',
				success:function(data){
					if(data.success){
						$("#collectSpan").html("关注");
						$("#isCollectProduct").val("false");
						$("#collectSpan").siblings("span.fa-heart").removeClass("current");
					}else{
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		} else {
			$.ajax({
				type:'GET',
				dataType:'json',
				async:false,
				data:{productId:${(productId)!0}},
				url:domain+'/member/docollectproduct.html',
				success:function(data){
					if(data.success){
						$("#collectSpan").html("取消");
						$("#isCollectProduct").val("true");
						$("#collectSpan").siblings("span.fa-heart").addClass("current");
					}else{
						$.dialog('alert','提示',data.message,2000);
					}
				}
			});
		}
	}
	
	// 关注店铺
	function collectShop(id){
		// 未登录不能关注店铺
		if (!isUserLogin()) {
			// 未登录跳转到登陆页面
			var toUrl = domain + "/product/${(productId)!0}.html?goodId=" + $("#productGoodsId").val();
			window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
			return;
		}
		$.ajax({
			type:'GET',
			dataType:'json',
			async:false,
			data:{sellerId:id},
			url:domain+'/member/docollectshop.html',
			success:function(data){
				if(data.success){
					$.dialog('alert','提示','收藏成功',2000);
					$("#collectShop").html("取消收藏");
					$("#collectShop").attr("onclick","disCollectShop(" + id + ")");
				}else{
					$.dialog('alert','提示',data.message,2000);
				}
			}
		});
	}
	
	// 取消关注店铺
	function disCollectShop(id){
		// 未登录不能取消关注店铺
		if (!isUserLogin()) {
			// 未登录跳转到登陆页面
			var toUrl = domain + "/product/${(productId)!0}.html?goodId=" + $("#productGoodsId").val();
			window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
			return;
		}
		$.ajax({
			type:'GET',
			dataType:'json',
			async:false,
			data:{sellerId:id},
			url:domain+'/member/cancelcollectshop.html',
			success:function(data){
				if(data.success){
					$.dialog('alert','提示','取消收藏成功',2000);
					$("#collectShop").html("收藏店铺");
					$("#collectShop").attr("onclick","collectShop(" + id + ")");
				}else{
					$.dialog('alert','提示',data.message,2000);
				}
			}
		});
	}
</script>
</body>
</html>

<script type="text/javascript">
document.write('<img width="1" height="1" style="position:absolute;" src="${(domainUrlUtil.urlResources)!}/product_look_log.html?memberId='+ memberId + '&productId='+ ${productId!0} + '" />');
</script>