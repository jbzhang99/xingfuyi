<#include "/front/commons/_headbig.ftl" />
   <style>
		.em-errMes{
			color:red;
			line-height:46px;
			padding-right:6px;
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
		.go-flash-sale {
			display:inline-block;
			line-height:18px;
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
			color: #333333;
			border: 1px solid #ccc;
		}
		
		.zpsz {
		    padding: 10px 0 0px 12px;
		    position: relative;
		    font-family: '\5B8B\4F53';
		    line-height: 1;
		}
		.zpsz .dt {
		    float: left;
		    display: inline;
		    text-align: right;
		    font-size: 12px;
		    color: #666;
		}
		.zfktm {
		    position: absolute;
		    right: 10px;
		    top: 10px;
		    color: #999;
		}
		.zfktm i {
		    color: #d7000f;
		}
		.zpsz .dd {
		    padding-left: 48px;
		    margin-top: -2px;
		    position: relative;
		    z-index: 11;
		}
		.zselect {
		    display: inline-block;
		    position: relative;
		    margin-top: -2px;
		    z-index: 2;
		    padding: 2px 10px;
		    border: 1px solid #f7f7f7;
		    border-bottom: 0;
		    background: #f7f7f7;
		    cursor: pointer;
		    color: #333;
		    font-size: 12px;
		    vertical-align: top;
		}
		.zxdress {
		    display: inline-block;
		    margin-left: 6px;
		    vertical-align: top;
		    width: 16px;
		    height: 16px;
		    background: url(${(domainUrlUtil.staticResources)!}/img/shang@3x.png) no-repeat;
		    background-size: cover;
		    transition: all 0.2s;
		    transform: rotate(180deg);
		}
		.zselbox {
		    position: absolute;
		    top: 18px;
		    left: 56px;
		    padding: 10px 15px;
		    border: 1px solid #dcdcdc;
		    background: #fff;
		    display: none;
		}
		.zsel {
		    display: inline-block;
		    width: 80px;
		    height: 26px;
		    line-height: 26px;
		    cursor: pointer;
		    color: #999;
		}
		.selboxdown {
		    padding-top: 10px;
		    border-top: 1px solid #dcdcdc;
		}
		.ulsebox {
		    overflow: hidden;
		}
		.ulsebox li {
		    float: left;
		    width: 100px;
		    height: 26px;
		    line-height: 26px;
		    text-align: left;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		.ulsebox li>span{
			display: block;
		    width: 100%;
		    height: 26px;
		    line-height: 26px;
		    text-align: left;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		.ulsebox li span:hover {
		    color: #d7000f;
		    cursor: pointer;
		}
		.zpsz .dd.hover .zselect {
		    border-color: #dcdcdc;
		    background: #fff;
		}
		.zpsz .dd.hover .zxdress {
		    transform: rotate(0deg);
		}
		.zpsz .dd.hover .zselbox {
		    display: block;
		}
		.extInfo .customer-service .service .item {
		    display: inline-block;
		    width: 100%;
		    margin-right: 0px;
		    float: none;
		    overflow: hidden;
		}
		.guarante-mt {
		    position: relative;
		    padding: 10px;
		    _zoom: 1;
		    background-color: #f7f7f7;
		    border: 1px solid #eee;
		}
		.guarante-mt h3 {
			font: 700 14px "microsoft yahei";
		}
		.guarante-item-detail {
			padding: 10px;
		}
		.guarante-serve-agree-bd {
			padding: 20px 20px 20px 62px;
		}
		.guarante-serve-agree-bd dt {
		    display: block;
		    height: 32px;
		    line-height: 32px;
		    margin-left: -42px;
		    font-size: 16px;
		    color: #e4393c;
		    font-family: "microsoft yahei";
		}
		.guarante-serve-agree-bd dt i {
			display: inline-block;
		    width: 42px;
		    height: 32px;
		    line-height: 32px;
		    padding-right: 10px;
		    vertical-align: bottom;
		}
		.guarante-serve-agree-bd dt .goods {
			background: url(${(domainUrlUtil.staticResources)!}/img/iconZP.png) no-repeat;
		}
		.guarante-serve-agree-bd dt .no-worries {
		    background: url(${(domainUrlUtil.staticResources)!}/img/iconWY.png) no-repeat;
		}
		.guarante-serve-agree-bd dt .unprofor {
		    background: url(${(domainUrlUtil.staticResources)!}/img/iconLB.png) no-repeat;
		}
		.guarante-item-detail-copyright strong {
		    display: inline-block;
		    padding: 8px 0 3px;
		}
		.guarante-serve-agree-bd dd {
		    display: block;
		    padding: 10px 0 30px;
		    line-height: 18px;
		}
		.guarante-serve-agree-bd dd a {
		    color: #005aa0;
		}
		.seckilling {
			    height: 32px;
			    line-height: 32px;
			    padding: 0 10px;
			    font-family: "Microsoft YaHei";
			    overflow: hidden;
			    zoom: 1;
				background: url(${(domainUrlUtil.staticResources)!}/img/shangou_ing.png) no-repeat #c208aa;
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
			<form action="" method="POST"  name="cartForm" id="cartForm" autocomplete="off">
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
								
							<div class="seckilling" style="display:none">
								<div class="activity-type">
									幸福易特卖
								</div>
								<div class="activity-message">
									距离结束<span class="day_show">0</span>天<span class="hour_show">00</span>:<span class="minute_show">00</span>:<span class="second_show">00</span>
								</div>
							</div>
							
							<!-- 判断是否下架 -->
							<#if product?? && product.state?? && product.state == 6>
								<div id='summary'>
									<div  style="background:#f7f7f7;padding:0 0 10px 10px;">
										<div id='CommentCount'>
											<p class='cumulative-comment'>累计评价</p>
											<a id='CountNumber'>${(statisticsVO.productCommentsAllCount)!'' }</a>
										</div>
										
										<div class="summary-product">
											<em>商 城 价：</em>
											<strong class='p-price' id="mallPcPrice" >￥${(goods.mallPcPrice)!''}</strong>
										</div>
										<div class="old-product">
											<em>市 场 价：</em>
											<del>￥${(product.marketPrice)!''}</del>
										</div>
										<#if product.malMobilePrice &lt; product.mallPcPrice >
										<div class="iphone-offer">
											<em>手机专享价：</em>
											<span>￥${(product.malMobilePrice)!""}</span>
											<span class="span-gomail">
												<span class="hl_blue">去手机购买</span>
												<span class="icon-iph"></span>
											</span>
											<div class="summary-mbuy" style="display: none;">
													<img src="${(domainUrlUtil.urlResources)!}/mbuy.html?productId=${(product.id)!''}" alt="" width="145" height="145">
											</div>
										</div>
										<#else>
										<div>&nbsp;</div>
										</#if>
									</div>
									<div style="padding: 0 0 10px 10px;">	
										<div class='summary-top' id='flashSaleInfoDiv'>
										</div>
										<div class='summary-top' id='actInfoDiv'>
										</div>
										<div class="p-choose" id="couponInfoDiv">
										</div>
										<div class='summary-service'>
											<div class='dt'>服　　务：</div>
											<div class='dd'>由 ${seller.sellerName} 发货并提供售后服务</div>
										</div>
										
										<!-- s 配送至 -->
										<#if transpot??>
										<div class="zpsz" style="z-index:22;">
											<div class="dt" style="width: auto;">配 送 至：</div>
											<#if transpot.transTime?? && (transpot.transTime == 5 || transpot.transTime == 7)>
												<div class="zfktm">付款成功后，预计<i>${(transpot.transTime)!0 }天</i>内发货</div>
											<#else>
												<div class="zfktm">付款成功后，预计<i>${(transpot.transTime)!0 }小时</i>内发货</div>
											</#if>
											<div class="dd" id="zselectdd">
												<input type="hidden" id="citicId" value="0">
												<input type="hidden" id="areaId" name="areaId" value="0">
												<div class="zselect">
													<span id="provinceName">${(province.regionName)!'请选择' }</span>
													<span id="cityName">${(city.regionName)!'' }</span>
													<span id="areaName">${(area.regionName)!'' }</span>
													<i class="zxdress"></i>
												</div>
												<div class="zselbox">
													<div class="zsel provinceUl" onclick="openUl('provinceUl')">${(province.regionName)!'请选择' }</div>
													<div class="zsel cityUl" onclick="openUl('cityUl')" <#if cityList?? && cityList?size &gt; 0><#else>style="display:none;"</#if>>${(city.regionName)!'请选择' }</div>
													<div class="zsel areaUl" onclick="openUl('areaUl')" <#if areaList?? && areaList?size &gt; 0><#else>style="display:none;"</#if>>${(area.regionName)!'请选择' }</div>
													<div class="selboxdown">
														<ul class="ulsebox" id="provinceUl" style="">
															<#if provinceList?? && provinceList?size &gt; 0>
											        			<#list provinceList as province>
											        				<li onclick="getRegions(${(province.id)!0},this)">
												        				<span>${(province.regionName)!''}</span>
												        			</li>
											        			</#list>
											        		</#if>
														</ul>
														<ul class="ulsebox" id="cityUl" style="display: none;">
															<#if cityList?? && cityList?size &gt; 0>
											        			<#list cityList as city>
											        				<li onclick="getRegions(${(city.id)!0},this)">
												        				<span>${(city.regionName)!''}</span>
												        			</li>
											        			</#list>
											        		</#if>
														</ul>
														<ul class="ulsebox" id="areaUl" style="display: none;">
															<#if areaList?? && areaList?size &gt; 0>
											        			<#list areaList as area>
											        				<li onclick="getRegions(${(area.id)!0},this)">
												        				<span>${(area.regionName)!''}</span>
												        			</li>
											        			</#list>
											        		</#if>
														</ul>
													</div>
												</div>
											</div>
										</div>
										<!-- e 配送至 -->
										
										<!-- s 运费 -->
										<div class="zpsz" id="freeDiv" style="<#if free?? && free&gt;0><#else>display:none;</#if>">
											<div class="dt" style="width: auto;">运　　费：</div>
											<div class="dd" id="freePrice" style="padding-left:18px;margin-top:0;color: #333;">${(free)!0 }</div>
										</div>
										</#if>
										<!-- e 运费 -->
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
									
									<div id='ChooseBtns' class='li'>
										<div class='dt'>购买数量：</div>
										<div class='dd'>
											<div class='choose-amount'>
												<div class='wrap-input'>
													<input type=button class='btn-reduce' id='min' value='-'>
													<a class='btn-add' id='add'>+</a>
													<input type='text' id='buy-num' value='1' data-now="1" name="count" onkeyup="checknum(this)">
												</div>
											</div>
											<span style='line-height:46px;'>(库存<em id="productStock">${(goods.productStock)!'0'}</em>件)</span>
											<span class="em-errMes"></span>
										</div>
									</div>
									<div class='tzm-border'>
										<div class='tip'>
											<span style="color:red">请选择您要的商品信息</span>
										</div>
										<a href='javascript:void(0);' class='close internation-close'></a>
									</div>
								</div>
								<div id='MainBtns' class='li'>
										<button type="button" class="btn btn-warning buynow">立即购买</button>&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-danger addcart">加入购物车</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
			                            <h3><strong>该商品已下架，非常抱歉！</strong></h3>
			                        </div>
			                    </div>
			                </#if>
							</div>
					</div>
					</form>
					<!-- right -->
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
										<div class='item' >
											<span class='label'>所&nbsp;在&nbsp;地&nbsp：</span>
											<span class='text' style="color: #333;"> ${(sellerLocation)!''}</span>
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
													<b>${(qq.name)!''}</b>
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
											<a class="p-name-detail" href='${(domainUrlUtil.urlResources)!}/product/${top.id!0}.html' target='_blank' title=''>${(top.name1)!''}</a>
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
								<li class='li-curr trig-item' data-table='4'>
									<a href='javascript:void(0);'>规格参数</a>
								</li>
								<li class='li-curr trig-item' data-table='2'>
									<a href='javascript:void(0);'>商品评价（${(statisticsVO.productCommentsAllCount)!'' }）</a>
								</li>
								<li class='li-curr trig-item' data-table='3'>
									<a href='javascript:void(0);'>咨询（${(statisticsVO.productAskCount)!'' }）</a>
								</li>
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
											<#if attr_index < 9>
												<li title=''>${(attr.name)!''}：${(attr.value)!''}</li>
											</#if>
										</#list>
									</#if>
								</ul>
								<div class="more-par clearfix"><a href="javascript:;" class="J-more-param fr">更多参数>></a></div>
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
					
					<div class="b-table" id="table4">
						
						<table cellpadding="0" cellspacing="1" width="100%" border="0" class="Ptable">
							<tbody>
								<tr class="tdTitleContent" style="height: 40px;">
									<td class="tdTitle">包装清单：</td>
									<td>${(product.packing)!'' }
									</td>
								</tr>
							</tbody>
						</table>
						
						<table cellpadding="0" cellspacing="1" width="100%" border="0" class="Ptable">
							<tbody>
								<tr>
									<th class="tdTitle" colspan="2">主体</th>
								</tr>
									
								<tr class="tdTitleContent">
									<td class="tdTitle">商品名称：</td>
									<td>${(product.name1)!'' }</td>
								</tr>
								<tr class="tdTitleContent">
									<td class="tdTitle">店铺：</td>
									<td>${(seller.sellerName)!''}</td>
								</tr>
								<tr class="tdTitleContent">
									<td class="tdTitle">上架时间：</td>
									<td>${(product.upTime?string("yyyy-MM-dd HH:mm:ss"))!'' }</td>
								</tr>
								<#if productAttr?? && productAttr?size &gt; 0>
									<#list productAttr as attr>
										<tr class="tdTitleContent">
											<td class="tdTitle">${(attr.name)!''}：</td>
											<td>${(attr.value)!''}</td>
										</tr>
									</#list>
								</#if>
							</tbody>
						</table>
						
						<div class="guaranteeCon">
							<div class="guarante-mt">
								<h3>售后保障</h3>
							</div>
							<div class="guarante-mc">
								<div class="guarante-item-detail guarante-item-detail-copyright">
									<div class="guarante-serve-agree-bd">
										<dl>
											<dt>
												<i class="goods"></i><strong>厂家服务</strong>
											</dt>
											<dd>
												本产品全国联保，享受三包服务，质保期为：一年质保</br>
												如因质量问题或故障，凭厂商维修中心或特约维修点的质量检测证明，享受7日内退货，15日内换货，15日以上在质保期内享受免费保修等三包服务！(注:如厂家在商品介绍中有售后保障的说明,则此商品按照厂家说明执行售后保障服务。)牌在各地售后服务中心的联系方式，<a href="" target="_blank">请点击这儿查询......</a>
												</br></br>
		            							品牌官方网站：<a href="http://www.齐驱科技.com" target="_blank">www.齐驱科技.com</a>
		            							</br>
		            							售后服务电话：010-57296530
											</dd>
											<dt>
												<i class="goods"></i><strong>齐驱科技承诺</strong>
											</dt>
											<dd>
												齐驱科技平台卖家销售并发货的商品，由平台卖家提供发票和相应的售后服务。请您放心购买！</br>
		 										注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！
											</dd>
											<dt>
												<i class="goods"></i><strong>正品行货</strong>
											</dt>
											<dd>
												齐驱科技商城向您保证所售商品均为正品行货，齐驱科技自营商品开具机打发票或电子发票。
											</dd>
											<dt>
												<i class="unprofor"></i><strong>全国联保</strong>
											</dt>
											<dd>
												凭质保证书及齐驱科技商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由齐驱科技联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。齐驱科技商城还为您提供具有竞争力的商品价格和<a href="" target="_blank">运费政策</a>，请您放心购买！
												</br></br>
												注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！
											</dd>
											<dt>
												<i class="no-worries"></i><strong>无忧退换货</strong>
											</dt>
											<dd>
												客户购买齐驱科技自营商品7日内（含7日，自客户收到商品之日起计算），在保证商品完好的前提下，可无理由退货。（部分商品除外，详情请见各商品细则）
											</dd>
										</dl>
									</div>
								</div>
							</div>
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
							<a style="color:blue" href='javascript:void(0);' onclick="editConsultaiion(this)">我要咨询</a>
							<div id="editConsultaiionDiv" style="display:none">
							<@form.form id="addconsultationform">
									<input  type="hidden" name="productId" value="${productId!''}">
									<input  type="hidden" name="sellerId" value="${(seller.id)!''}">

									<!-- 编辑咨询 -->
									<div class='box-t'></div>
									<div class='evaluat-form'>
										<div class='item'>
											<em style="color:red;">*</em> 咨询内容：
											<div class='cont' style="float:right;width:923px;height:120px;margin-top:-20px;">
												<textarea class='area area01' style="width:923px;height:120px;border-top:1px solid #DDD" name='askContent'></textarea>
												<span class='msg-error-01 hide'></span>
											</div>
										</div>
										<div style="float:right;">
											<a href='javascript:void(0)' class='btn btn-danger cr btn-ask-sub'>提交</a>
										</div>
									</div>
							</@form.form>
							</div>
						</div>
						</div>
						
					</div>
					
					 </div>
				</div>
			</div>

		<!-- S优惠券隐藏页面页面 -->
		<div class="toolbar-wrap" style="display: none">
			<h3 class="tbar-panel-header">
				<span class="title"><i></i><em>优惠券</em></span>
				<span class="close-panel">×</span>
			</h3>
			<div class="coupon-box">
				<div class="coupon-wrap">
					<div class="coupon-type">可领取的券
						<span class="line-left"></span>
						<span class="line-right"></span>
					</div>
					<div id="couponListDiv"></div>
				</div>
			</div>
		</div>
		<!-- E优惠券隐藏页面页面 -->
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
		// 点击更多参数
		$(".J-more-param").on("click",function(){
			$(".trig-item").eq(1).addClass("curr").siblings().removeClass("curr");
			$(".b-table").removeClass("bcent-table");
			$("#table4").addClass("bcent-table");
		});
			
		$('.close-panel').on('click',function(){
			$('.toolbar-wrap').hide(500);
		});
		
		//异步加载评价及咨询列表
		showAllComments("all",'${productId!''}');
		showProductAskList('${productId!''}');
		showProductActInfo('${productId!0}','${(seller.id)!0}');
		showProductFlashSaleInfo('${productId!0}');
		showSpecialEndTimeInfo('${productId!0}');
		
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
							return false;
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
			$(this).siblings(".attrid").val($(this).attr("val"));
			//规格详情
			var norminfo = $(this).parent().siblings(".dt").html();
			var attrinfo = $(this).find("a").attr("title");
			$(this).siblings(".attrname").val(norminfo+attrinfo);
			$(this).addClass("selected").siblings().removeClass("selected");
			
			// 只有规格有多个时需要修改规格显示
			if (normsNum > 1) {
				NormChecker.init();
			}
			//异步加载价格及库存信息
			queryPrice();
		});
		
		//校验
		jQuery.validator.addMethod("balance", function(value,element) {
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
	    }); 
		
		//添加购物车  立即购买点击事件
		$(".addcart").click(function(){
			//未登录不能添加购物车
			if (!isUserLogin()) {
				showid('ui-dialog');
				return;
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

			var params = $("#cartForm").serialize();
			if($("#cartForm").valid()){
				 $.ajax({
					type : "POST",
					url :  domain+"/cart/addtocart.html",
					data : params,
					dataType : "json",
					success : function(data) {
						if(data.success){
                            //跳转到添加购物车成功页面
							window.location.href = domain + "/cart/add.html?id=" + data.data;
						}else{
							jAlert(data.message);
						}
					},
					error : function() {
						jAlert("数据加载失败！");
					}
				});
			}
		});

		//点击立即购买
        $(".buynow").click(function(){

            //未登录不能添加购物车
            if (!isUserLogin()) {
                showid('ui-dialog');
                return;
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

            var params = $("#cartForm").serialize();
            if($("#cartForm").valid()){
                $.ajax({
                    type : "POST",
                    url :  domain+"/cart/addtocart.html",
                    data : params,
                    dataType : "json",
                    success : function(data) {
                        if(data.success){
                            //跳转到订单页面
                            window.location.href = domain + "/order/info.html?id="+data.data;
                        }else{
                            jAlert(data.message);
                        }
                    }
                });
            }

        });
		
		$(".span-gomail").on("mouseover",function(){
			$(".summary-mbuy").show();
		});
		$(".span-gomail").on("mouseleave",function(){
			$(".summary-mbuy").hide();
		});
		
		//选择配送地址
		$('#zselectdd').on('mouseover',function(){
			$(this).addClass('hover');
		});
		$('#zselectdd').on('mouseleave',function(){
			$(this).removeClass('hover');
		});
		
	}); //页面初始加载完毕


	function openUl(id){
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		if(id!=null && id == "provinceUl"){
			$("#provinceUl").show();
			$("#cityUl").hide();
			$("#areaUl").hide();
		}else if(id!=null && id == "cityUl"){
			$("#provinceUl").hide();
			$("#cityUl").show();
			$("#areaUl").hide();
		}else if(id!=null && id == "areaUl"){
			$("#provinceUl").hide();
			$("#cityUl").hide();
			$("#areaUl").show();
		}
		
	}
	
	//运费相关
	function getRegions(pId,obj){
		var parentId = $(obj).parent().attr("id");
		var nextId = "";
		if(parentId!=null && parentId!="" && parentId !="areaUl"){
			var nextId = $(obj).parent().next().attr("id");
			$("."+nextId).addClass("active");
			$("."+nextId).show();
		}

		var regionName = $(obj).find("span").text();
		$("."+parentId).html(regionName);
		$("."+parentId).removeClass("active");
		
		//计算运费信息
		if(parentId!=null && parentId!= "" && parentId == "cityUl"){
			var productId = $("#productId").val();
			$.ajax({
	          	type:"get",
	          	url: domain+"/getTransportPrice",
	          	dataType: "json",
	          	data: {productId: productId,cityId:pId,num:1},
	          	cache:false,
	          	success:function(data){
					  console.log(data)
	              	if (data.success) {
	              		$("#freePrice").html("");
	              		$("#freePrice").html(data.data);
	              		$("#freeDiv").show();
	            	}
	        	}
			});
		}

		if(nextId!=null && nextId!=""){
			//$(obj).parent().hide();
			$("#"+nextId).empty();
			$.ajax({
	          	type:"get",
	          	url: domain+"/getRegionByParentId",
	          	dataType:"json",
	          	data: {parentId: pId},
	          	cache:false,
	          	success:function(data){
	              	if (data.success) {
	                  var selectOption = '';
	                  $.each(data.data, function(i, region){
	                      selectOption += '<li onclick="getRegions('+region.id+',this)">';
						  selectOption += '<span>'+region.regionName+'</span>';
						  selectOption += '</li>';
	                  });
	                  $("#"+nextId).append(selectOption);
	                  $("#"+parentId).hide();
	                  $("#"+nextId).show();
	            	}
	        	}
			});
			$("."+nextId).text("请选择");
		}
		if(parentId !=null && parentId!="" && parentId == "provinceUl"){
			$("#provinceName").text("");
			$("#provinceName").text(regionName);
			$(".cityUl").text("请选择");
			$(".areaUl").hide();
			$("#cityName").text("");
			$("#areaName").text("");
		}else if(parentId !=null && parentId!="" && parentId == "cityUl"){
			var cityName=$("#cityName").text();
			$("#cityName").text(regionName);
			$(".areaUl").text("请选择");
			$("#areaName").text("");
		}else{
			$("#areaId").val(pId);
			var areaName=$("#areaName").text();
			$("#areaName").text(regionName);
		}
    };
	
	/**
    * 输入购买数量后进行校验
	*/
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
	}

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
					return false;
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
// 						if(goods.state == 0){
// 							jAlert("该货品暂时无法购买，请联系商家");
// 							$(".buynow").attr("disabled","disabled");
// 							$(".addcart").attr("disabled","disabled");
// 							return;
// 						}
						//商城价格
						$("#mallPcPrice").html("￥"+goods.mallPcPrice);
						//库存
						$("#productStock").html(goods.productStock);
						//货品ID
						$("#productGoodsId").val(goods.id);
						//货品属性id
						$("#goodsNormAttrId").val(goods.normAttrId);
						
						$(".buynow").removeAttr("disabled");
						$(".addcart").removeAttr("disabled");
					}else{
						//无货品信息 则不能添加购物车和购买
						jAlert("货品信息为空，请与管理员联系");
						$(".buynow").attr("disabled","disabled");
						$(".addcart").attr("disabled","disabled");
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
	function editConsultaiion(obj){
		//未登录不能添加咨询
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
		$(obj).remove();
		$("#editConsultaiionDiv").show();
	}
	
	// 显示优惠券列表
	function showCouponList() {
		if($('.toolbar-wrap').css('display')=='none'){
			$('.toolbar-wrap').show(500);
		}else {
			$('.toolbar-wrap').hide(500);
		}
	}
	
	// 异步加载商品促销信息
	function showProductActInfo(productId, sellerId){
		var sellerName = "${(seller.sellerName)!''}";
		$.ajax({
			type : "POST",
			url :  domain+"/getproductactinfo.html",
			data : {productId:productId,sellerId:sellerId},
			dataType : "json",
			success : function(data) {
				if (data.success && data.data != null) {
					var productActVO = data.data;
					// 加载单品立减和满减
					if (productActVO.actSingle == null && productActVO.actFull == null) {
						// 都是空不作操作
					} else {
						var actHtml = '<div class="dt">促销信息：</div>';
						actHtml += '<div class="dd">';
						var actSingle = productActVO.actSingle;
						if (actSingle != null) {
							actHtml += '	<span class="fullCut">';
							if (actSingle.type == 1) {
								actHtml += '		<em>立减</em> 下单即享' + actSingle.discount + '元优惠';
							} else if (actSingle.type == 2) {
								var dis = parseInt(parseFloat(actSingle.discount)*10);
								actHtml += '		<em>立减</em> 下单即享' + dis + '折优惠';
							}
							actHtml += '	</span>';
						}
						var actFull = productActVO.actFull;
						if (actFull != null) {
							actHtml += '	<span class="fullCut">';
							actHtml += '		<em>满减</em> ';
							// 满999.00减10.00，满4999.00减100.00，满12999.00减300.00
							if (actFull.firstFull != null && actFull.firstFull > 0) {
								actHtml += '满' + actFull.firstFull + '减' + actFull.firstDiscount;
							}
							if (actFull.secondFull != null && actFull.secondFull > 0) {
								actHtml += '，满' + actFull.secondFull + '减' + actFull.secondDiscount;
							}
							if (actFull.thirdFull != null && actFull.thirdFull > 0) {
								actHtml += '，满' + actFull.thirdFull + '减' + actFull.thirdDiscount;
							}
							actHtml += '	</span>';
						}
						actHtml += '</div>';
						$("#actInfoDiv").html(actHtml);
					}
					
					// 加载优惠券信息
					var couponList = productActVO.couponList;
					if (couponList != null && couponList.length > 0) {
						var couponBtnHtml = '<span >领　　券：</span>'
										  + '<a href="javascript:;" class="J-open-tb receive" onclick="showCouponList()"><span>我要领券</span></a>';
						$("#couponInfoDiv").html(couponBtnHtml);
						var couponListHtml = "";
						for (var i=0; i < couponList.length; i++) {
							var coupon = couponList[i];
							couponListHtml += '<div class="coupon-item">';
							couponListHtml += '	<div class="coupon-price">';
							couponListHtml += '		<em>￥</em><span>'+ coupon.couponValue +'</span>';
							couponListHtml += '	</div>';
							couponListHtml += '	<div class="coupon-info">';
							couponListHtml += '		<span class="coupon-info-tit">仅限'+ sellerName +'使用</span>';
							couponListHtml += '		<span class="condition">满'+coupon.minAmount+'元可用</span>';
							couponListHtml += '	</div>';
							couponListHtml += '	<a href="javascript:;" class="btn-get" onclick=receiveCoupon(' + coupon.id + ')>';
							couponListHtml += '		立即领取';
							couponListHtml += '	</a>';
							couponListHtml += '	<p class="coupon-time">有效期:'+coupon.useStartTime.substring(0,10)+' - '+coupon.useEndTime.substring(0,10)+'</p>';
							couponListHtml += '</div>';
						}
						$("#couponListDiv").html(couponListHtml);
					}
				} else {
					
				}
			}
		});
	}
	
	// 异步加载限时抢购活动信息
	function showProductFlashSaleInfo(productId) {
		$.ajax({
			type : "POST",
			url :  domain+"/getproductflashinfo.html",
			data : {productId:productId},
			dataType : "json",
			success : function(data) {
				if (data.success && data.data != null) {
					var productActVO = data.data;
					// 加载限时抢购信息
					if (productActVO.actFlashSaleProduct != null) {
						var actFlashSaleProduct = productActVO.flashPrice;
						
						var flashHtml = '<div class="dt">整点秒杀：</div>';
						flashHtml += '<div class="dd">';
						flashHtml += '<strong class="p-price" style="font-weight:400;font-size:12px">';
						var stageType = productActVO.stageType;
						// 如果是正在进行
						if (stageType == 2) {
							flashHtml += actFlashSaleProduct + '元秒杀正在进行中&nbsp;&nbsp;';
							flashHtml += '<a href="javascript:;" class="J-open-tb receive" onclick="gotoFlashSale()"><span class="go-flash-sale">我要秒杀</span></a>';
							flashHtml += '</strong>';
							$("#flashSaleInfoDiv").html(flashHtml);
						} else if (stageType == 3) {
							// 如果是即将开始
							flashHtml += actFlashSaleProduct + '元秒杀即将开始&nbsp;&nbsp;';
							flashHtml += '<a href="javascript:;" class="J-open-tb receive" onclick="gotoFlashSale()"><span class="go-flash-sale">去看看</span></a>';
							flashHtml += '</strong>';
							$("#flashSaleInfoDiv").html(flashHtml);
						} 
						//else if (stageType == 1) {
							// 如果是已经结束
						//	flashHtml += actFlashSaleProduct.price + '元秒杀结束了~~~&nbsp;&nbsp;';
						//	flashHtml += '<a href="javascript:;" class="J-open-tb receive" onclick="gotoFlashSale()"><span class="go-flash-sale">去看看</span></a>';
						//	flashHtml += '</strong>';
						//	$("#flashSaleInfoDiv").html(flashHtml);
						//}
					}
				}
			}
		});
	}
	
	// 异步加载商品特卖信息
	function showSpecialEndTimeInfo(productId) {
		$.ajax({
			type : "GET",
			url :  domain+"/getSpecialEndTime.html",
			data : {productId:productId},
			dataType : "json",
			success : function(data) {
				if (data.success && data.data != null) {
				 	  var tim =  data.data;
				 	  if(tim != 0) {
					 	  $('.seckilling').show();
				          var prentbox = $('.activity-message');
				          timer(tim,prentbox);
							// 倒计时
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
					                prentbox.find('.day_show').text(day);
					                prentbox.find('.hour_show').text(hour);
					                prentbox.find('.minute_show').text(minute);
					                prentbox.find('.second_show').text(second);
					                time--;
					              }, 1000);
					        };
				 	  }
				}
			}
		});
	}
	
	// 领取优惠券
	function receiveCoupon(couponId) {
		//未登录不能领取
		if (!isUserLogin()) {
			showid('ui-dialog');
			return;
		}
	 	$.ajax({
			type:"POST",
			url:domain+"/member/reveivecoupon.html",
			dataType:"json",
			data:{couponId:couponId},
			success:function(data){
				if (data.success) {
					jAlert("领取成功，您可在用户中心查看您的优惠券！");
				} else {
					jAlert(data.message);
				}
			},
			error:function(){
				jAlert("领取失败，请稍后再试！");
			}
		});
	}
	
	// 跳转到限时抢购页面
	function gotoFlashSale() {
		window.location.href=domain+"/product/${(productId)!0}.html?type=1";  
	}
</script>
<!-- 登录弹出框 -->
<#include "/front/commons/logindialog.ftl" />
<#include "/front/commons/_endbig.ftl" />
<script type="text/javascript">
document.write('<img width="1" height="1" style="position:absolute;" src="${(domainUrlUtil.urlResources)!}/product_look_log.html?memberId='+ memberId + '&productId='+ ${productId!0} + '" />');
</script>

<script type="text/javascript">
	$(function() {
		//校验
		jQuery("#addconsultationform").validate(
				{
					errorPlacement : function(error, element) {
						var obj = element.nextAll(".msg-error-01")
								.removeClass('hide');
						error.appendTo(obj);
					},
					rules : {
						"askContent" : {
							required : true,
							minlength : 10,
							maxlength : 500
						}
					},
					messages : {
						"askContent" : {
							required : "请输入内容",
							minlength : "不能小于10个字呦",
							maxlength : "不能超过500个字呦"
						}
					}
				});

		$(".btn-ask-sub").click(function() {

			if ($("#addconsultationform").valid()) {
				$(".btn-ask-sub").attr("disabled", "disabled");
				var params = $('#addconsultationform').serialize();
				$.ajax({
					type : "POST",
					url : domain + "/member/savequestion.html",
					dataType:'json',
					async : false,
					data : params,
					success : function(data) {
						if (data.success) {
							jAlert('保存成功', '提示',function(){
								location.replace(location.href)
							});
						} else {
							jAlert(data.message);
							$(".btn-ask-sub").removeAttr("disabled");
						}
					},
					error : function() {
						jAlert("异常，请重试！");
						$(".btn-ask-sub").removeAttr("disabled");
					}
				});
			}

		});
	});
</script>