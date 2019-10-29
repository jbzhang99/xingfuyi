<#include "/front/commons/_headbig.ftl" />
    
   <style>
		.em-errMes{
			color:red;
		}
		/*商品下架样式*/
		.m-itemover-title {
		  height: 38px;
		  line-height: 38px;
		  border: 1px solid #ddd;
		  background: #f5f5f5;
		}
		.m-itemover-title h3 {
		  padding-top: 10px;
		  padding-left: 10px;
		}
		
		.choosenorms .disabled{
			background-color: #f0ad4e;
			border-color: #eea236;
			cursor: not-allowed;
			filter: alpha(opacity=25);
			-webkit-box-shadow: none;
			box-shadow: none;
			opacity: .25;
			cursor: wait;
		}
		
		.choosenorms .disabled a{
			cursor: not-allowed;
		}
		
		.choosenorms .disabled a:hover{
			color: #ccc;
			border: 1px solid #ccc;
		}
		.seckilling {
			    height: 32px;
			    line-height: 32px;
			    padding: 0 10px;
			    font-family: "Microsoft YaHei";
			    overflow: hidden;
			    zoom: 1;
				background: url(${(domainUrlUtil.staticResources)!}/img/miaosha_ing.png) no-repeat #c208aa;
			}
			.activity-type {
				float: left;
   				padding-left: 30px;
				font-size: 16px;
			    color: #fff;
			    font-weight: 400;
			}
			.activity-message {
				margin-top: 4px;
				height: 24px;
    			line-height: 24px;
				float: right;
				color: #fbe2e2;
    			font-size: 12px;
			}
			.seckilling .activity-message span {
				display: inline-block;
				font-size: 14px;
				color: #fff;
				height: 24px;
				text-align: center;
				line-height: 24px;
				background: #443b3b;
				margin: 0 4px;
				border-radius: 2px;
				padding:0 6px;
			}
	</style>
		<div id='root-nav'>
			<div class='container'>
				<div class='subheader'>
					<strong>
						<#if productCatePP?? >
							${(productCatePP.name)!''}
						</#if>
					</strong>
					<span>
						&nbsp;>&nbsp;<a href='${(domainUrlUtil.urlResources)!}/list/${(productCateP.id)!0}.html'>${(productCateP.name)!''}</a>
						&nbsp;>&nbsp;<a href='${(domainUrlUtil.urlResources)!}/cate/${(productCate.id)!0}.html'>${(productCate.name)!''}</a>
						&nbsp;>&nbsp;
					</span>
					<span>
					<!-- 品牌的链接 TODO -->
						<a href="${(domainUrlUtil.urlResources)!}/brand/${(product.productBrandId)!0}.html">${(productBrand.name)!''}</a>
						&nbsp;>&nbsp;
						${(product.name1)!''}
					</span>
				</div>
			</div>	
		</div>
		<div id='p-box'>
			<div class='container'>
				<div class='product-intro m-item-grid'>
					<div class="right-extra">
						<div>
							<div id="preview" class="spec-preview">
								<!-- 默认第一张图 -->
								<#if productLeadPicList?? && productLeadPicList?size &gt; 0>
							        		<#list productLeadPicList as img>
							        			<#if img_index == 0>
							        				<span class="jqzoom">
														<img style="width:350px;height:350px" jqimg="${(domainUrlUtil.imageResources)!}${img}" src="${(domainUrlUtil.imageResources)!}${img}" />
													</span> 
								            		<#break>
											    </#if>
							        		</#list>
							        </#if>
							</div>
   							 <!--缩图开始-->
						    <div class="spec-scroll"> 
						    	<a class="prev">&lt;</a> 
						    	<a class="next">&gt;</a>
						      	<div class="items">
							        <ul>
							        	<#if productLeadPicList?? && productLeadPicList?size &gt; 0>
							        		<#list productLeadPicList as img>
							        			<li>
							          				<img  bimg="${(domainUrlUtil.imageResources)!}${img}" src="${(domainUrlUtil.imageResources)!}${img}" onmousemove="preview(this);">
							            		</li>
							        		</#list>
							        	</#if>
							        </ul>
						        </div>
						    </div>
   							<!--缩图结束-->
						</div>
					</div>
					
			<!-- 购物车表单 -->
			<form action="" method="GET"  name="cartForm" id="cartForm" autocomplete="off">
			<!-- 隐藏域 -->
				<!-- 产品ID -->
				<input  type="hidden" name="productId" value="${productId!''}">
				<input  type="hidden" name="sellerId" value="${(seller.id)!''}">
				<input  type="hidden" name="productGoodsId" id="productGoodsId" value="${(goods.id)!''}">
				<input  type="hidden"  id='goodsNormAttrId' value="${(goods.normAttrId)!''}">
				
					<div class='m-item-inner'>
						
							<div id='itemInfo'>
								<div id='detaile-name'>
									<h1>${(product.name1)!''}</h1>
									<div class='p-ad' id='p-ad'>${(product.name2)!''}</div> 
								</div>
								
								<#if stageType?? && stageType == 1 >
									<!-- 已经结束 -->
								<#elseif stageType?? && stageType == 2 >
									<div class="seckilling">
										<div class="activity-type">
											每日秒杀
										</div>
										<div class="activity-message">
											距离结束仅剩：<span class="hour_show">00</span>时<span class="minute_show">00</span>分<span class="second_show">00</span>秒
										</div>
									</div>
								<#else>
									<div class="seckilling">
										<div class="activity-type">
											每日秒杀
										</div>
										<div class="activity-message">
											即将开始：<span class="hour_show">00</span>时<span class="minute_show">00</span>分<span class="second_show">00</span>秒
										</div>
									</div>
								</#if>
								
								
							
							<!-- 判断是否下架 -->
							<#if product?? && product.state?? && product.state == 6 && actFlashSaleProduct?? && actFlashSaleProduct.status == 2>
								<div id='summary'>
									<div id='CommentCount'>
										<p class='cumulative-comment'>累计评价</p>
										<a id='CountNumber'>${(statisticsVO.productCommentsAllCount)!'' }</a>
									</div>
									<div id='SummaryPrice' style="padding-top: 14px;padding-bottom: 6px;font-weight: 700;font-size: 14px;">
										<div class='dt'>商 城 价：</div>
										<div class='dd'>
											<strong class='p-price'>￥${(product.mallPcPrice)!''}</strong>
										</div>
									</div>
									<div class='summary-promotion summary-top' style="padding-top: 14px;padding-bottom: 6px;font-size: 14px;font-weight: 700;">
										<div class='dt'>秒 杀 价：</div>
										<div class='dd'>
											<strong class='p-price' id="mallPcPrice" >￥${(actFlashSaleProduct.price)!''}</strong>
										</div>
									</div>
									
									<div class='summary-top'>
										<div class='dt' style="font-weight: 700;color: #000;margin-right: 2px;font-size:14px;">已秒杀：</div>
										<div class='dd'><em id="productStock" style="font-weight: 700;color: #000;margin-right: 2px;font-size:14px;">${(actFlashSaleProduct.actualSales)!'0'} 件</em></div>
										<div class='dt' style="font-weight: 700;color: #000;margin-right: 2px;font-size:14px;">仅剩：</div>
										<div class='dd'><em id="productStock" style="font-weight: 700;color: #000;margin-right: 2px;font-size:14px;">${(actFlashSaleProduct.stock)!'0'} 件</em></div>
									</div>
									
									<div class='summary-service'>
										<div class='dt'>服　　务：</div>
										<div class='dd'>由 ${seller.sellerName} 发货并提供售后服务</div>
									</div>
									
								</div>
								<div id='Choose' class='p-choose-wrap'>
									<#if norms??>
										<#list norms as norm>
											<div id='ChooseNorm${norm_index}' class='li choosenorms'>
												<div class='dt'>${norm.name}：</div>
												<div class='dd norms' >
													<#list norm.attrList as normattr>
														<div class='item' 
															data-pic-url="${(normattr.url)!''}"
															val="${normattr.id}">
															<b></b>
															<a href='javascript:void(0);' class='norm-min' title='${normattr.name}' >
															<#if (normattr.url)??>
																<img
																	width="25" height="25"
																	src="${(domainUrlUtil.imageResources)!}/${(normattr.url)!}" 
																	onerror="this.src='${(domainUrlUtil.staticResources)!}/img/nopic.png'" />
															</#if>
															${normattr.name}
															</a>
														</div>
													</#list>
													<!-- 规格值ID  -->
													<input  type="hidden" name="specId" class="attrid" >
													<!-- 规格详情， 用逗号分隔 ，例如颜色：黑色 -->
													<input  type="hidden" name="specInfo" class="attrname" >
												</div>
											</div>
										</#list>
									</#if>
									
									
									<div class='tzm-border'>
										<div class='tip'>
											<span style="color:red">请选择您要的商品信息</span>
										</div>
										<a href='javascript:void(0);' class='close internation-close'></a>
									</div>
								</div>
								<div id='MainBtns' class='li'>
									<#if stageType?? && stageType == 1 >
										<!-- 已经结束 -->
										<button type="button" class="btn btn-warning">已经结束了</button>&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-danger addcart" onclick="location.href='${(domainUrlUtil.urlResources)!}/product/${productId!0}.html'">原价购买</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<#elseif stageType?? && stageType == 2 >
										<!-- 正在进行 -->
										<#if (actFlashSaleProduct.stock)??>
											<#if actFlashSaleProduct.stock &lt; 1>
											<span style="font-size:14px;color:red">已经被抢光了</span>
											<#else>
											<button type="button" class="btn btn-warning buynow">立即秒杀</button>&nbsp;&nbsp;&nbsp;&nbsp;
											</#if>
										<#else>
											<span style="font-size:14px;color:red">已经被抢光了</span>
										</#if>
									<#else>
										<!-- 即将开始 -->
										<button type="button" class="btn btn-warning">即将开始</button>&nbsp;&nbsp;&nbsp;&nbsp;
									</#if>
										
										<#if statisticsVO??> 
											<#if statisticsVO.collectedProduct=true>
												<button id="collectProduct" onclick="disCollectProductMy('${productId!''}')" type="button" class="btn btn-default">取消收藏</button>
											<#else>
												<button id="collectProduct" onclick="collectProductMy('${productId!''}')" type="button" class="btn btn-default">收藏</button>
											</#if>
										</#if>
								</div>
							<#else> 
								<div class="m-itemover">
			                        <div class="m-itemover-title">
			                            <h3><strong>当前商品没有秒杀活动，请到<a style="color:red" href="${(domainUrlUtil.urlResources)!}/product/${(productId)!0}.html">商品详情</a>页查看商品信息！</strong></h3>
			                        </div>
			                    </div>
			                </#if>
							</div>
					</div>
					</form>
					<!-- right -->
					<div class='m-item-ext'>
						<div class='extInfo' id='extInfo'>
							<div class='seller-infor'>
								<a target="_blank" href='${(domainUrlUtil.urlResources)!}/store/${(seller.id)!0}.html' title='${(seller.sellerName)!''}' class='infor-name'>${(seller.sellerName)!''}</a>
							</div>
							<div class='seller-pop-box'>
								<div class='z-pop-desc-show'>
									<dl class='pop-score-detail'>
										<dt class='score-title'>
											<span class='rating-name'>商家满意度</span>
										</dt>
										<dd class='score-infor'>
											<div class='score-part'>
												<span class='score-desc'>商品描述：<em class='score'>${(seller.scoreDescription)!'0'}</em>分</span>
											</div>
											<div class='score-part'>
												<span class='score-desc'>服务态度：<em class='score'>${(seller.scoreService)!'0'}</em>分</span>
											</div>
											<div class='score-part'>
												<span class='score-desc'>发货速度：<em class='score'>${(seller.scoreDeliverGoods)!'0'}</em>分</span>
											</div>
										</dd>
									</dl>
									<div class='pop-shop-detail'>
										<div class='item'>
											<span class='label'>店铺名称：</span>
											<span class='text'><a target="_blank" href="${(domainUrlUtil.urlResources)!}/store/${(seller.id)!0}.html">${(seller.sellerName)!''}</a></span>
										</div>
										<div class='item'>
											<span class='label'>所&nbsp;在&nbsp;地&nbsp;：</span>
											<span class='text'> ${(sellerLocation)!''}</span>
										</div>
									</div>
								</div>
							</div>
							<dl class='customer-service clearfix'>
								<dd class='service'>
									<span class='item'>
										<b style='font-weight:700'>联 系 客 服 </b>
									</span>
									<br>
									<div class='custom-service'>
										<#if sellerQqList?? && sellerQqList?size &gt; 0>
											<#list sellerQqList as qq>
												<span class='item'>
													<b>${(qq.name)!''}：</b>
													<a href='http://wpa.qq.com/msgrd?v=3&uin=${(qq.qq)!''}&Site=${(qq.qq)!''}&Menu=yes' target='_blank'><img src='${(domainUrlUtil.staticResources)!}/img/qq.jpg' width='25' height='25'></a>
												</span>
											</#list>
										</#if>
									</div>
								</dd>
							</dl>
							<div class='pop-shop-enter'>
								<a href='${(domainUrlUtil.urlResources)!}/store/${(seller.id)!0}.html' target='_blank' class='btn btn-default'>进入店铺</a>
								&nbsp;&nbsp;
								<#if statisticsVO??> 
										<#if statisticsVO.collectedShop=true>
											<a id="collectShop" href='javascript:void(0)' onclick="disCollectShop('${(seller.id)!''}')" class='btn btn-default'>取消收藏</a>
										<#else>
											<a id="collectShop" href='javascript:void(0)' onclick="collectShop('${(seller.id)!''}')" class='btn btn-default'>收藏店铺</a>
										</#if>
								</#if> 
								
							</div>
						</div>
					</div>
					<!-- end -->
				</div>
			</div>
		</div>
		
		<div class='container'>
			<div class='left'>
				<div id='browse-browse-pop' class='m m2 related-buy'>
					<div class='mt'>
						<h2>精品推荐</h2>
					</div>
					<div class='mc'>
						<ul>
							<#if productTop?? && productTop?size &gt; 0>
								<#list productTop as top>
									<li class='fore1'>
										<div class='p-img'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${top.id!0}.html' target='_blank' title='${top.name1 }'>
												<img class="lazy" width='160' height='160' alt='${(top.name1)!""}' data-original='${(domainUrlUtil.imageResources)!}${(top.masterImg)!""}'>
											</a>
										</div>
										<div class='p-name'>
											<a href='${(domainUrlUtil.urlResources)!}/product/${top.id!0}.html' target='_blank' title=''>${(top.name1)!''}</a>
										</div>
										<div class='p-price'>
											<strong>￥${(top.mallPcPrice)!''}</strong>
										</div>
									</li>
									
								</#list>
							</#if>
							
						</ul>
					</div>
				</div>
			</div>
			<div class='right'>
				<div id='product-detail' class='m m1'>
					<div class='mt' id='pro-detail-hd'>
						<div class='mt-inner tab-trigger-wrap clearfix'>
							<ul class='m-tab-trigger'>
								<li class='li-curr curr trig-item' data-table='1'>
									<a href='javascript:void(0);'>商品详情</a>
								</li>
								<li class='li-curr trig-item' data-table='2'>
									<a href='javascript:void(0);'>商品评价（${(statisticsVO.productCommentsAllCount)!'' }）</a>
								</li>
								<li class='li-curr trig-item' data-table='3'>
									<a href='javascript:void(0);'>咨询（${(statisticsVO.productAskCount)!'' }）</a>
								</li>
								<!-- <li class='li-curr trig-item' data-table='4'>
									<a href='javascript:void(0);'>规格属性</a>
								</li> -->
							</ul>
						</div>
					</div>
					<!-- 商品介绍 -->
					<div class='b-table bcent-table' id='table1'>
						<div class='mc'>
							<div class='p-parameter'>
								<ul id='parameter2' class='p-parameter-list'>
									<li title=''>商品名称：${(product.name1)!'' }</li>
									<li title=''>店铺： <a href='${(domainUrlUtil.urlResources)!}/store/${(seller.id)!0}.html' target='_blank'>${(seller.sellerName)!''}</a></li>
									<li title='2015-04-01 20:35:24'>上架时间：${(product.upTime?string("yyyy-MM-dd HH:mm:ss"))!'' }</li>
								
									<#if productAttr?? && productAttr?size &gt; 0>
										<#list productAttr as attr>
											<li title=''>${(attr.name)!''}：${(attr.value)!''}</li>
										</#list>
									</#if>
								</ul>
							</div>
						</div>
						<div class='detail-content clearfix'>
							<div class='detail-content-wrap'>
								<div class='detail-content-item'>
									<p align='center'>
										 <#noescape> ${(product.description)!''}</#noescape>
										<!--<img src='${(domainUrlUtil.staticResources)!}/img/center.jpg' style='margin-top:10px;margin-bottom:10px;'>
										<img src='${(domainUrlUtil.staticResources)!}/img/center1.jpg' style='margin-top:10px;margin-bottom:10px;'> -->
									</p>
								</div>
							</div>
						</div>
					</div>
					<!-- 商品评价 -->
					<div class='b-table'  id='table2'>
						<div id='state'>
							<strong>权利声明：</strong>
							<br>
							齐驱科技上的所有商品信息、客户评价、商品咨询、网友讨论等内容，是齐驱科技重要的经营资源，未经许可，禁止非法转载使用。
                			<p>
                				<b>注：</b>
                				本站商品信息均来自于合作方，其真实性、准确性和合法性由信息拥有者（合作方）负责。本站不提供任何保证，并不承担任何法律责任。
                			</p>
						</div>
						<div id='comment' class='m'>
							<div class='mt'>
								<h2>商品评价</h2>
							</div>
							<div class='mc'>
								<div id='i-comment'>
									<div class='rate'>
										<strong>${(statisticsVO.productCommentsHighProportion)!'' }<span>%</span></strong><br>
										<span>好评度</span>
									</div>
									<div class='percent'>
										<dl>
											<dt>好评<span>(${(statisticsVO.productCommentsHighProportion)!'' }%)</span></dt>
											<dd><div class='progress'></div></dd>
										</dl>
										<dl class='dl'>
											<dt>中评<span>(${(statisticsVO.productCommentsMiddleProportion)!'' }%)</span></dt>
											<dd><div class='progress' style='width:0%'></div></dd>
										</dl>
										<dl class='dl'>
											<dt>差评<span>(${(statisticsVO.productCommentsLowProportion)!'' }%)</span></dt>
											<dd><div class='progress' style='width:0%'></div></dd>
										</dl>
									</div>
								</div>
							</div>
						</div>
						<div id='comments-list' class='m'>
							<div class='mt'>
								<div class='mt-inner tab-trigger-wrap clearfix'>
									<ul class='m-tab-trigger'>
										<li class='li-curr curr comment-li' data-box='1'>
											<a href='javascript:void(0);' onclick="showAllComments('all','${productId}')" >全部评价<em>(${(statisticsVO.productCommentsAllCount)!'' })</em></a>
										</li>
										<li class='li-curr comment-li' data-box='2'>
											<a href='javascript:void(0);' onclick="showAllComments('high','${productId}')">好评<em>(${(statisticsVO.productCommentsHighCount)!'' })</em></a>
										</li>
										<li class='li-curr comment-li' data-box='3'>
											<a href='javascript:void(0);' onclick="showAllComments('middle','${productId}')">中评<em>(${(statisticsVO.productCommentsMiddleCount)!'' })</em></a>
										</li>
										<li class='li-curr comment-li' data-box='4'>
											<a href='javascript:void(0);' onclick="showAllComments('low','${productId}')">差评<em>(${(statisticsVO.productCommentsLowCount)!'' })</em></a>
										</li>
									</ul>
								</div>
							</div>
							
							<!-- 评价列表（全部、好、中、差 -->
							<div id = "commentsList"></div>
							
						</div>
					</div>
					<!-- 咨询 -->
					<div class='b-table' id='table3'>
						<div class='cs-main-wrap mt10 m'>
							<div class='mt'>
								<div class='mt-inner tab-trigger-wrap clearfix'>
									<ul class='m-tab-trigger'>
										<li class="li-curr curr advice-li" data-number='1'>
											<a href='javascript:void(0);' onclick="showProductAskList('${productId!''}')">全部咨询<em>(${(statisticsVO.productAskCount)!'' })</em></a>
										</li>
									</ul>
								</div>
							</div>
							<!--  咨询  列表区域 begin-->
								<div id = "productAskList"></div>
							<!--  咨询  列表区域 end-->
							
						<div id="editConsultaiion" style="height:500px;margin-top:20px;">
							<a style="color:blue" href='javascript:void(0);' onclick="editConsultaiion('${productId!''}','${(seller.id)!''}')">我要咨询</a>
						</div>
						</div>
						
					</div>
					
					 </div>
				</div>
			</div>

<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/details.js'></script>
<script>
//启用的属性
var effectAttr = new Array();
<#noescape>
<#if effectAttr??>
effectAttr = eval('${effectAttr}');
</#if>
</#noescape>
var normsNum = Number("${(normsNum)!'0'}");
</script>
<script type="text/javascript" src='${(domainUrlUtil.staticResources)!}/js/normplugin.js'></script>
<script type="text/javascript">
	$(function(){
		//异步加载评价及咨询列表
		showAllComments("all",'${productId!''}');
		showProductAskList('${productId!''}');
		
		//默认将规格选中
		var norms = $("#goodsNormAttrId").val();
		if(!isEmpty(norms)){
			var strs= new Array(); //定义数组 
			strs=norms.split(","); //字符分割 
			for(i=0;i<strs.length;i++){
				 $("#ChooseNorm"+i).find(".item").each(function(){
						var attrid = $(this).attr("val");
						if(attrid==strs[i]){
							 //规格详情
							var norminfo = $(this).parent().siblings(".dt").html();
							var attrinfo = $(this).find("a").attr("title");
							$(this).siblings(".attrname").val("").val(norminfo+attrinfo);
							$(this).siblings(".attrid").val("").val($(this).attr("val"));
							$(this).addClass("selected").siblings().removeClass("selected");
							return;
						}
					});
			}
		}
		
		// 只有规格有多个时需要初始化规格显示
		if (normsNum > 1) {
			NormChecker.init();
		}
		
		//选择规格
		$(".choosenorms .item").click(function(){
			if($(this).hasClass("disabled")){
				return;
			}
			//加载图片
			var pic_ = $(this).data('pic-url');
			var url_ = ""
			if(pic_ != null && pic_ != ""){
				url_ = "${(domainUrlUtil.imageResources)!}"+pic_;
				$(".jqzoom img").attr("src",url_).attr("jqimg",url_);
			}
			
			//为隐藏域赋值
			$(this).siblings(".attrid").val("").val($(this).attr("val"));
			//规格详情
			var norminfo = $(this).parent().siblings(".dt").html();
			var attrinfo = $(this).find("a").attr("title");
			$(this).siblings(".attrname").val("").val(norminfo+attrinfo);
			$(this).addClass("selected").siblings().removeClass("selected");
			
			// 只有规格有多个时需要修改规格显示
			if (normsNum > 1) {
				NormChecker.init();
			}
			//异步加载价格及库存信息
			queryPrice();
		});
		
		//校验
		/* jQuery.validator.addMethod("balance", function(value,element) {
			var productStock = "${(goods.productStock)!'0'}";
	           value = parseInt(value);
	           productStock = parseInt(productStock);
	         return this.optional(element) || value<= productStock;   
	    }, $.validator.format(" 不能大于库存 "));
	          
	    jQuery.validator.addMethod("checkBalance", function(value,element) {
	         var  balance=0;
	         value = parseInt(value);
	         return this.optional(element) || value>balance;   
	    }, $.validator.format(" 购买数量必须大于0 "));
	    
		jQuery("#cartForm").validate({
			errorPlacement : function(error, element) {
				var obj = element.parent().parent().siblings(".em-errMes").css('display', 'block');
				error.appendTo(obj);
			},
	        rules : {
	            "count":{required:true,number:true,balance:true,checkBalance:true}
	        },
	        messages:{
	            "count":{required:"请输入数量",number:"请输入数字"}
	        }
	    });  */
		
		//立即秒杀
		$(".buynow").click(function(){
			//未登录不能秒杀
			if (!isUserLogin()) {
				showid('ui-dialog');
				return;
			}
			
			var stock = ${(actFlashSaleProduct.stock)!0};
			if (parseInt(stock) < 1) {
				jAlert("已经被抢光了，下次要赶早哦！");
				return false;
			}
			
			//如果有规格，判断是否选择了规格，如果没有规格则判断是否有货品ID
			//默认只有两个规格
			var Selectcolor = $(this).parents("#itemInfo").find("#ChooseNorm0 .item");
			var SelectVersion = $(this).parents("#itemInfo").find("#ChooseNorm1 .item");
			if(Selectcolor.hasClass("selected") && SelectVersion.hasClass("selected")){
				$(this).parents("#itemInfo").find(".tzm-border").css("display",'none');
			}else{
				// 如果是没有规格的商品，则判断隐藏的货品ID，如果有则通过，没有则报错
				var goodId = $("#productGoodsId").val();
				if (goodId == null || goodId == "") {
					$(this).parents("#itemInfo").find(".tzm-border").css("display",'block');
					return false;
				}
			}
			
			var goodsId = $("#productGoodsId").val();
			window.location.href=domain+"/order/flashsale-" + ${productId!''}+ "-" + goodsId + "-" + ${(seller.id)!''} + ".html";
		});
	    
        var tim = parseInt('${(countTime)!0}'); //传秒
        var prentbox = $('.activity-message');
        timer(tim,prentbox);
	}); //页面初始加载完毕
	
    function timer(time,prentbox){
          window.setInterval(function(){
            var day=0,
                hour=0,
                minute=0,
                second=0;//时间默认值       
            if(time > 0){
                day = Math.floor(time / (60 * 60 * 24));
                hour = Math.floor(time / (60 * 60)) - (day * 24);
                minute = Math.floor(time / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(time) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            prentbox.find('.hour_show').text(hour);
            prentbox.find('.minute_show').text(minute);
            prentbox.find('.second_show').text(second);
            time--;
          }, 1000);
    };
	/**
    * 输入购买数量后进行校验
	*/
	/*
	function checknum(obj){
		var val = $(obj).val();
		var datanow = $(obj).attr("data-now");
		//判断是否为正整数
		if(!isIntege1(val)){
			$(obj).val(datanow);
			return false;
		}
		//如果值为1 不能点-
		var decrement = $(obj).siblings(".btn-reduce");
	    if (parseInt(val)==1){
	    	$(decrement).attr('disabled',true);
	    }else{
	    	$(decrement).removeAttr("disabled");
	    }
		$(obj).attr("data-now",val);
	} */

	//显示所有评价列表
	function showAllComments(type,productId){
		$.ajax({
			type : "POST",
			url :  domain+"/commentsList.html",
			data : {type:type,productId:productId,targetDiv:"commentsList"},
			dataType : "html",
			success : function(data) {
				$('#commentsList').html(data);
			},
			error : function() {
				jAlert("数据加载失败！");
			}
		});
	}
	
	//点击规格信息查询
	function queryPrice(){
		var flag = true;
		$("input[name='specId']").each(function(){
				if($(this).val().length<1){
					flag = false;
					return;
				}
			}
		);
		
		var params = $("#cartForm").serialize();
		if(flag){
			$.ajax({
				type : "POST",
				url :  domain+"/getGoodsInfo.html",
				data : params,
				dataType : "json",
				success : function(data) {
					var goods = data.data;
					if(goods.id!=null){
						/* //商城价格
						$("#mallPcPrice").html("￥"+goods.mallPcPrice);
						//库存
						$("#productStock").html(goods.productStock); */
						//货品ID
						$("#productGoodsId").val(goods.id);
					}else{
						//无货品信息 则不能添加购物车和购买
						jAlert("货品信息为空，请与管理员联系");
						$(".buynow").attr("disabled","disabled");
					}
				},
				error : function() {
					jAlert("数据加载失败！");
				}
			});
		}
	}
	
	
	//显示咨询列表
	function showProductAskList(productId){
		$.ajax({
			type : "POST",
			url :  domain+"/productAskList.html",
			data : {productId:productId,targetDiv:"productAskList"},
			dataType : "html",
			success : function(data) {
				$('#productAskList').html(data);
			},
			error : function() {
				jAlert("数据加载失败！");
			}
		});
	}
	/**
	 * 关注产品
	 */
	function collectProductMy(id){
		//未登录不能关注产品
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type:'GET',
			dataType:'json',
			async:false,
			data:{productId:id},
			url:domain+'/member/docollectproduct.html',
			success:function(data){
				if(data.success){
					$("#collectProduct").html("取消收藏");
					$("#collectProduct").attr("onclick", "disCollectProductMy(" + id + ")");
				}else{
					jAlert(data.message);
				}
			}
		});
	}
	
	/**
	 * 取消关注产品
	 */
	function disCollectProductMy(id){
		//未登录不能取消关注产品
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$.ajax({
			type:'GET',
			dataType:'json',
			async:false,
			data:{productId:id},
			url:domain+'/member/cancelcollectproduct.html',
			success:function(data){
				if(data.success){
					$("#collectProduct").html("收藏");
					$("#collectProduct").attr("onclick", "collectProductMy(" + id + ")");
				}else{
					jAlert(data.message);
				}
			}
		});
	}
	
	/**
	 * 关注店铺
	 */
	function collectShop(id){
		//未登录不能关注店铺
		if (!isUserLogin()) {
			showid('ui-dialog');
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
					$("#collectShop").html("取消收藏");
					$("#collectShop").attr("onclick","disCollectShop(" + id + ")");
				}else{
					jAlert(data.message);
				}
			}
		});
	}
	
	/**
	 * 取消关注店铺
	 */
	function disCollectShop(id){
		//未登录不能取消关注店铺
		if (!isUserLogin()) {
			showid('ui-dialog');
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
					$("#collectShop").html("收藏店铺");
					$("#collectShop").attr("onclick","collectShop(" + id + ")");
				}else{
					jAlert(data.message);
				}
			}
		});
	}
	
	
	/**
	 * 添加咨询
	 */
	function editConsultaiion(productId,sellerId){
		//未登录不能添加咨询
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
	 	 $.ajax({
			type:"GET",
			url:domain+"/member/addquestion.html",
			dataType:"html",
			async : false,
			data:{productId:productId,sellerId:sellerId},
			success:function(data){
				//加载数据
				$("#editConsultaiion").html(data);
			},
			error:function(){
				jAlert("异常，请重试！");
			}
		});
	}
</script>
<!-- 登录弹出框 -->
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />
<script type="text/javascript">
document.write('<img width="1" height="1" style="position:absolute;" src="${(domainUrlUtil.urlResources)!}/product_look_log.html?memberId='+ memberId + '&productId='+ ${productId!0} + '" />');
</script>