
		<#if (cartInfoVO.cartListVOs??) && (cartInfoVO.cartListVOs?size &gt; 0) >
		<#list cartInfoVO.cartListVOs as cartListVO>
		<#assign seller = cartListVO.seller />
	        <h2 class="cart-h2 pad10">
	        	<!-- <input type="checkbox"> -->&nbsp;<span>${seller.sellerName!''}</span>
	        </h2>
	        <#if cartListVO.actFull?? >
	        <div class="full-box clear">
	            <div class="full-reduction">满减</div>
	            <div class="full-money">
	              <span>
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
                  		<br>
	              		&nbsp;(已减:${(cartListVO.orderDiscount)?string('0.00')!"0.00"}元)
	              		</#if>
	              </span>
	            </div>
	        </div>
	        </#if>
	        <#if (cartListVO.cartList??) && (cartListVO.cartList?size>0) >
            <#list cartListVO.cartList as cart>
            <#assign product = cart.product />
            <#assign productGoods = cart.productGoods />
	        <div class="oder-list">
				<dl class="img-ul cart-ul flex">
					<div class="checksty">
						<input type='checkbox' name="checkItem" id="${(cart.id)!''}" value="${(cart.id)!''}" onchange="checkedChange(this)" autocomplete="off" <#if cart?? && cart.checked?? && cart.checked == 1>checked="checked"</#if>>
					</div>
					<dt style="width:80px;height:80px;">
						<a href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html?goodId=${(cart.productGoodsId)!0}"><img src="${(domainUrlUtil.imageResources)!}${product.masterImg!''}"></a>
					</dt>
				<dd class="flex-2 pos_relative">
					<div class="product_name">
						<a href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html?goodId=${(cart.productGoodsId)!0}">${(product.name1)!""}&nbsp;${cart.specInfo!''}</a>
					</div>
					<div>￥<font>${(productGoods.mallMobilePrice)?string('0.00')!"0.00"}</font></div>
					<div>
						<a class="quantity-decrease" onclick="cartminus(this)">
					    	<i class="fa fa-minus-square"></i>
						</a>
						<input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="quantity" size="4" value="${cart.count!''}" id="number" onblur="modify(this);">
						<a class="quantity-increase" onclick="cartplus(this)">
					    	<i class="fa fa-plus-square"></i>
						</a>
						<div>
							(库存${productGoods.productStock!'0'}件)
						</div>
						<input type="hidden" id="productStock" name="productStock" value="${productGoods.productStock!'0'}"/>
						<input type="hidden" id="cartId" name="cartId" value="${cart.id!'0'}"/>
					</div>
					<div class="cart_delate"><i class="fa fa-trash" onclick="deleteCart(${cart.id!'0'})"></i></div>
				</dd>
				</dl>
				<div class="cartPrice">小计<font>¥${(cart.currDiscountedAmount)?string('0.00')!"0.00"}</font>
					<!-- <div class="clr53"></div> -->
					<span class='productStock'>&nbsp;&nbsp;&nbsp;已省:${(cart.currDiscounted)?string('0.00')!0}</span>
				</div>
	        </div>
	        </#list>
	        </#if>
	    </#list>
	    <#else>
	    <div style="height:70%;" class="flex flex-pack-center flex-align-center">
			<div>
				<p class="text-center"><i class="fa fa-shopping-cart"></i><br>购物车是空的，去挑一件中意的商品吧！</p>
		        <p class="mar_top text-center"><a href="${(domainUrlUtil.urlResources)!}/index.html" class="a_btn">去逛逛</a></p>
			</div>
		</div>
	    </#if>

	    <input type="hidden" id="cartAmount" name="cartAmount" value="${cartInfoVO.checkedDiscountedCartAmount!'0.00'}"/>
	    <input type="hidden" id="totalNumber" name="totalNumber" value="${cartInfoVO.totalNumber!0}"/>
	    <input type="hidden" id="totalCheckedNumber" name="totalCheckedNumber" value="${cartInfoVO.totalCheckedNumber!0}"/>
