<#if cartInfoVO?? && (cartInfoVO.cartListVOs??) && (cartInfoVO.cartListVOs?size &gt; 0) >
<div class='yy-cart'>
	<div class='cart-main'>
		<div class='cart-thead'>
			<div class='column t-checkbox'>
				<div class='cart-checkbox'>
					<input type='checkbox' id="checkAllHead" class='yycheckbox' onchange="checkedChangeAll(this)" autocomplete="off" <#if cartInfoVO.totalNumber == cartInfoVO.totalCheckedNumber>checked="checked"</#if>>
				</div>
				全选
			</div>
			<!-- <div class='column t-checkbox'>&nbsp;</div> -->
			<div class='column t-goods'>商品</div>
			<div class='column t-props'></div>
			<div class='column t-price'>单价(元)</div>
			<div class='column t-quantity'>数量</div>
			<div class='column t-sum'>小计(元)</div>
			<div class='column t-action'>操作</div>
		</div>
		<div id='cartList'>
			<#assign isCheckAll = 1 />
			<#list cartInfoVO.cartListVOs as cartListVO>
			    <#assign seller = cartListVO.seller />
			    <div class='cart-item-list'>
			        <div class='cart-tbody'>
			            <div class='shop'>
			                <a target="_blank" href="${(domainUrlUtil.urlResources)!}/store/${(seller.id)!0}.html" class='shop-name self-shop-name'>${seller.sellerName!''}</a>
			            </div>
			            <div class='item-list'> 
			                <div class='item-full'> 
			                    <div class='item-header'> 
			                        <div class='f-txt'>
			                        	<!-- 满减活动信息 -->
			                        	<#if cartListVO.actFull?? >
			                        	<span class='full-icon full-gray-icon'>
											满减
											<b></b>
										</span>
			                        		<#if cartListVO.actFull.firstFull?? && cartListVO.actFull.firstFull &gt; 0>
			                        		&nbsp;满${(cartListVO.actFull.firstFull)?string('0.00')!"0.00"}-${(cartListVO.actFull.firstDiscount)?string('0.00')!"0.00"}
			                        		</#if>
			                        		<#if cartListVO.actFull.secondFull?? && cartListVO.actFull.secondFull &gt; 0>
			                        		&nbsp;满${(cartListVO.actFull.secondFull)?string('0.00')!"0.00"}-${(cartListVO.actFull.secondDiscount)?string('0.00')!"0.00"}
			                        		</#if>
			                        		<#if cartListVO.actFull.thirdFull?? && cartListVO.actFull.thirdFull &gt; 0>
			                        		&nbsp;满${(cartListVO.actFull.thirdFull)?string('0.00')!"0.00"}-${(cartListVO.actFull.thirdDiscount)?string('0.00')!"0.00"}
			                        		</#if>
			                        		
			                        		<#if cartListVO.orderDiscount?? && cartListVO.orderDiscount &gt; 0>
			                        		&nbsp;(已减:${(cartListVO.orderDiscount)?string('0.00')!"0.00"})
			                        		</#if>
			                        	<#else>
			                        		&nbsp;
			                        	</#if>
			                        </div>
			                        <div class='f-price'> 
			                            <strong>${(cartListVO.sellerCheckedDiscountedAmount)?string('0.00')!'0.00'}</strong> 
			                            <span>已减：<em>${(cartListVO.sellerCheckedDiscounted)?string('0.00')!'0.00'}</em></span>
			                        </div>
			                    </div>
			                    <#if (cartListVO.cartList??) && (cartListVO.cartList?size>0) >
			                    <#list cartListVO.cartList as cart>
			                    	<#if cart?? && cart.checked?? && cart.checked == 0>
			                    		<#assign isCheckAll = 0 />
			                    	</#if>
			                        <#assign product = cart.product />
			                        <#assign productGoods = cart.productGoods />
			                        <div class='item-last item-item item-selected'>
			                            <div class='item-form'>
			                            	<div class='cell p-checkbox'>
			                            		<input type='checkbox' name="checkItem" id="${(cart.id)!''}" value="${(cart.id)!''}" onchange="checkedChange(this)" class='yycheckbox' autocomplete="off" <#if cart?? && cart.checked?? && cart.checked == 1>checked="checked"</#if>>
			                            	</div>
			                                <div class='cell p-goods'>
			                                    <div class='goods-item'> 
			                                        <div class='p-img'>
			                                            <a target='_blank' href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html">
				                                            <img width='80' height='80'  alt="${product.name1!''}" src="${(domainUrlUtil.imageResources)!}${product.masterImg!''}"/>
			                                            </a> 
			                                        </div> 
			                                        <div class='item-msg'>
			                                            <div class='p-name'> 
			                                                <a href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html" target='_blank'>${product.name1!''}</a> 
			                                            </div> 
			                                        </div>
			                                    </div> 
			                                </div> 
			                                <div class='cell p-props'>${cart.specInfo!''}</div> 
			                                <div class='cell p-price'> 
			                                    <strong class='unit-price'>${(productGoods.mallPcPrice)?string('0.00')!0}</strong> 
			                                </div> 
			                                <div class='cell p-quantity'> 
			                                    <div class='quantity-form'>
			                                        <input type=button  class='btn-reduce decrement' value='-' onclick='decrement(this)'>
			                                        <input type='text' class='itxt buy-num' data-now="${cart.count!''}" value="${cart.count!''}" onkeyup='checknum(this)'>
			                                        <a href='javascript:void(0);' class='increment'  onclick='increment(this)'>+</a>
			                                         <!-- 购物车id -->
			                                        <input class='cartId' type='hidden' value="${cart.id!0}" name='id' > 
			                                    </div>
			                                    <div class='ac ftx-03 quantity-txt'>
			                                        <#if productGoods.productStock?? && productGoods.productStock &gt; 0>
			                                            库存量<span class='productStock'>${productGoods.productStock!0}</span>
			                                        <#else>
			                                            <span style='color:red'>无货</span>
			                                        </#if>
			                                    </div>
			                                </div> 
			                                <div class='cell p-sum'>
			                                    <strong class='subtotal'>
			                                        ${(cart.currDiscountedAmount)?string('0.00')!0}
			                                    </strong>
			                                    <br>
			                                    <span class='productStock'>已省:${(cart.currDiscounted)?string('0.00')!0}</span>
			                                </div>
			                                <div class='cell p-ops'>
			                                    <a href='javascript:void(0);' class='cart-remove' onclick="deleteSingle(this,'${(cart.id)!0}')">删除</a>
			                                    <!-- <a href='javascript:void(0);'>收藏</a> -->
			                                </div>
			                            </div> 
			                        </div>
			                    </#list>
			                    </#if>
			                </div>
			            </div>
			        </div>
			    </div>
			</#list>
			
		</div>
	</div>
</div>
<div id='cart-floatbar'>
	<div class='clearing'>
		<div class='cart-toolbar'>
			<div class='toolbar-wrap'>
				<div class='options-box'>
					<div class='select-all'>
						
						<div class='cart-checkbox'>
							<input type='checkbox' id="checkAllFoot" class='yycheckbox' onchange="checkedChangeAll(this)" autocomplete="off" <#if cartInfoVO.totalNumber == cartInfoVO.totalCheckedNumber>checked="checked"</#if>>
						</div>
							全选 
						
					</div>
					<div class='operation'>
						<!--
						<a href='' class='remove-batch'>删除选中的商品</a>
						<a href='' class='follow-batch'>收藏</a>
						-->
					</div>
					<div class='toolbar-right'>
						<div class='normal'>
							<div class='comm-right'>
								<div class='btn-area'>
									<a href='javascript:;' class='submit-btn' onclick="toOrder()">
										去结算
										<b></b>
									</a>
								</div>
								<div class='price-sum'>
									<span class='txt'>总价（不含运费）：</span>
									<span class='price sumPrice'>
										<em id="sumPrice">￥${(cartInfoVO.checkedDiscountedCartAmount)?string("0.00")!'0.00'}</em>
									</span>
									<br>
									<span class="txt">已节省：</span>
									<span class="price totalRePrice">-￥${(cartInfoVO.checkedDiscountedAmount)?string("0.00")!'0.00'}</span>
								</div>
								<div class='amount-sum'>
									已选择
									<em id="selectedCount">${cartInfoVO.totalCheckedNumber!0}</em>
									件商品
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<#else>
	<div class="cart-empty">
		<div class="message">
			<ul>
				<li class="txt">购物车空空的哦~，去看看心仪的商品吧~</li>
				<li>
					<a href="${(domainUrlUtil.urlResources)!}/index.html" class="ftx-05"> 去购物&gt;</a>
				</li>
			</ul>
		</div>
	</div>
</#if>
<input type="hidden" id="totalNumber" name="totalNumber" value="${(cartInfoVO.totalNumber)!0}"/>