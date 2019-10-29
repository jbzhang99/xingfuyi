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
    <div class="flex flex-align-center head-bar bg-col">
      <div class="flex-1 text-left">
        <a href="javascript:ejsPageBack();">
        	<span class="fa fa-angle-left col-f"></span>
        </a>
      </div>
      <div class="flex-2 text-center col-f">积分换购详情</div>
      <div class="flex-1 text-right" id="fa-bars">
        <span class="fa fa-bars col-f"></span>
      </div>
    </div>
    <#include "/h5/commons/_hidden_menu.ftl" />
  </header>
  <!-- 头部 end-->

  <div class="s-container container-padtom" id="container">
    <!--图片 -->
    <div class="detail2">
      <img src="${(domainUrlUtil.imageResources)!}${(actIntegral.image)!''}"></div>
    <section class="goods_price">
      <div class="group_price">
        <span>换购价：</span>
        	<#if actIntegral.isWithMoney == 0>
                <span class="price">${(actIntegral.price)!}分</span>
            <#elseif actIntegral.isWithMoney == 1>	
                <span class="price">${(actIntegral.price)!}分+￥${(actIntegral.priceMoney)!}</span>
            </#if>
         <del>市场价:¥${(actIntegral.marketPrice)?string('0.00')!'99999999'}</del>
      </div>
    </section>
    <section class="num_time">
      <div class="buy_num">
        <span>${(actIntegral.virtualSaleNum + actIntegral.saleNum)!0}</span>
        人已兑换
      </div>
      <#if stageType?? && stageType == 1 >
		<!-- 即将开始 -->
		<div class="remaining_time">
	        即将开始：
	        <span></span>
      	</div>
	  <#elseif stageType?? && stageType == 2 >
		<!-- 正在进行 -->
		<div class="remaining_time">
	        距离结束：
	        <span></span>
	        
      	</div>
	  <#else>
		<!-- 已经结束 -->
		<div>兑换已结束</div>
	  </#if>
    </section>
    <section class="goods_title">
      <a>
        <h2 style="font-size:16px;">${(actIntegral.productName)!''}</h2>
      </a>
      <a style="color:red;font-size:14px;">
        <h2>${(actIntegral.name)!''}</h2>
      </a>
    </section>
    <div class="bor-btom mar-bt mar-bt-padding">
		<#if norms?? && norms?size &gt; 0>
			<div class="flex pad-bt">
		    	<div class="pad-r">已选：</div>
		    	<div class="flex-2" id="normAttr0"></div>
		    	<div class="flex-2" id="normAttr1"></div>
		    	<div class="flex-2" id="amount"></div>
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
		<input  type="hidden" id="productId" name="productId" value="${(product.id)!''}">
		<input  type="hidden" id="productGoodsId" name="productGoodsId" value="${(goods.id)!''}">
		<input  type="hidden" id="goodsNormAttrId" name="goodsNormAttrId" value="${(goods.normAttrId)!''}">

		<div class="flex pad-bt pad-bt-padding">
			<div class="pad-r">剩余库存：${(actIntegral.stock)!0}</div>
		</div>
	</div>
    <section class="attribute_buy_num">
      <span class="attribute_buy_num_tit">购买数量：</span>
      <div class="attribute_buy_num_val">
        <span class="un_minus_icon" id="minus_num"></span>
	        <input type="text" value="1" id="buyNum" autocomplete="off" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="modify();">
	        <span class="add_icon" id="add_num"></span>
	        <div class="limit_num">
	          单次限购
	          <span>${(actIntegral.purchase)!1}</span>
	          件
	        </div>
      </div>
    </section>
    
    <!-- 商品详情 -->
    <div class="mar_top pad10 bgff bort shop-info">
      <h3>商品介绍</h3>
      <#noescape> ${(actIntegral.descinfo)!''}</#noescape>
    </div>
    
    <!-- 底部固定菜单 -->
    <div class="fixed-nav">
	    <div class="flex">
	        <div class="flex-1 yellow-cart" ><a href="${(domainUrlUtil.urlResources)!}/product/${(actIntegral.productId)!}.html" class="block">对比原商品</a></div>
	        
	        <#if stageType?? && stageType == 1 >
				<!-- 即将开始 -->
				<div class="flex-1 gray-cart">敬请期待</div>
			  <#elseif stageType?? && stageType == 2 >
				<!-- 正在进行 -->
				<div class="flex-1 red-cart" id="buyNow">立即兑换</div>
			  <#else>
				<!-- 已经结束 -->
				<div class="flex-1 gray-cart">兑换结束</div>
			</#if>
	      </div>
	    </div>
    </div>
   <!-- 主体结束 -->
    

  </div>
  <!-- 主体结束 -->
<#include "/h5/commons/_statistic.ftl" />
  <!-- footer -->
<#include "/h5/commons/_footer.ftl" />

<script src="${(domainUrlUtil.staticResources)!}/js/jquery-2.1.1.min.js"></script>
<script src="${(domainUrlUtil.staticResources)!}/js/index.js"></script>
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
<script src="${(domainUrlUtil.staticResources)!}/js/jquery.hDialog.js"></script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/normplugin.js'></script>
<script>
	// 倒計時
    var intDiff = parseInt(${countTime!0});//倒计时总秒数量
    function timer(intDiff){
        window.setInterval(function(){
        var day=0,
            hour=0,
            minute=0,
            second=0;//时间默认值       
        if(intDiff > 0){
            day = Math.floor(intDiff / (60 * 60 * 24));
            hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
            minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
            second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        }
        if(day==0){
          $(".remaining_time>span").html("<span>"+hour+"时</span><span>"+minute+"分</span><span>"+second+"秒</span>")
        }else{
          $(".remaining_time>span").html(day+"天");
        }
        }, 1000);
    }
    $(function(){
    	$('#fa-bars').on("click",function(){
	      $("#nav").toggleClass('addnav');
		});
	
    	// 倒计时
        timer(intDiff);
    	
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
			
			//未登录不能立即购买
			if (!isUserLogin()) {
				// 未登录跳转到登陆页面
				var toUrl = domain + "/jifen/${(actIntegral.id)!0}.html";
				window.location.href = domain+"/login.html?toUrl="+ encodeURIComponent(toUrl);
				return;
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
			
			var stock = ${(actIntegral.stock)!0};
			if (parseInt(stock) < 1) {
				$.dialog('alert','提示', "已经被抢光了，下次要赶早哦！",1000);
				return false;
			}
			
			var buyNum = $("#buyNum").val();
			var limitNum = ${(actIntegral.purchase)!0};
			var stock = ${(actIntegral.stock)!0};
			if (parseInt(buyNum) < 1) {
				$.dialog('alert','提示', "请输入小于限购数量及库存量的正整数。",1000);
				return false;
			} else if (parseInt(buyNum) > parseInt(limitNum) || parseInt(buyNum) > parseInt(stock)) {
				$.dialog('alert','提示', "请输入小于限购数量及库存量的正整数。",1000);
				return false;
			}
			
			var goodsId = $("#productGoodsId").val();
			if (goodsId == null || goodsId == "" || goodsId == 0) {
				$.dialog('alert','提示', "请选择购买的商品。",1000);
				return false;
			}
			window.location.href=domain+"/order/jifen-" + ${(product.id)!''}+ "-" + goodsId + "-" + ${(seller.id)!''} + "-" + ${(actIntegral.id)!''} + "-" + buyNum + ".html";
		});
		
		// 购买数量减少
		$("#minus_num").click(function() {
			var buyNum = $("#buyNum").val();
			if (parseInt(buyNum) <= 1) {
				return;
			} else {
				buyNum = parseInt(buyNum) - 1;
				$("#buyNum").val(buyNum);
				$("#amount").html(buyNum);
			}
		});
		
		// 购买数量增加
		$("#add_num").click(function() {
			var limitNum = ${(actIntegral.purchase)!0};
			var stock = ${(actIntegral.stock)!0};
			var buyNum = $("#buyNum").val();
			if (parseInt(buyNum) >= parseInt(limitNum) || parseInt(buyNum) >= parseInt(stock)) {
				return;
			} else {
				buyNum = parseInt(buyNum) + 1;
				$("#buyNum").val(buyNum);
				$("#amount").html(buyNum);
			}
		});
    });
    
	// 数量输入框失去焦点
	function modify() {
		var buyNum = $("#buyNum").val();
		var limitNum = ${(actIntegral.purchase)!0};
		var stock = ${(actIntegral.stock)!0};
		if (buyNum == null || parseInt(buyNum) < 1) {
			buyNum = 1;
			$("#buyNum").val(buyNum);
		} else if (parseInt(buyNum) > parseInt(limitNum) || parseInt(buyNum) > parseInt(stock)) {
			if (parseInt(limitNum) < parseInt(stock)) {
				buyNum = parseInt(limitNum);
			} else {
				buyNum = parseInt(stock);
			}
			$("#buyNum").val(buyNum);
		}
		$("#amount").html(buyNum);
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
</script>
</body>
</html>