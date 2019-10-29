
		<#if (cartInfoVO.cartListVOs??) && (cartInfoVO.cartListVOs?size &gt; 0) >
		<#list cartInfoVO.cartListVOs as cartListVO>
		<#assign seller = cartListVO.seller />
            <div style="width: 94%;margin-left: 3%;margin-top: 10px;border-radius:10px;" class="one-shop">
	        <h2 class="cart-h2 pad10">
	        	<!-- <input type="checkbox"> -->
                <#--&nbsp;<span>${seller.sellerName!''}</span><input type="checkbox">-->
                <span  class="selectShopCheckboxN" style="display: none;" id="ShopCheck" onclick="checkedShop(this)"></span> <img src="${(domainUrlUtil.staticResources)!}/img/dianpu@2x.png" style="margin-top: 3px;margin-left: 3px; width: 16px; height: 16px;" alt=""> <span>${seller.sellerName!''} </span> <span class="fa fa-angle-right"></span>
	        </h2>
	        <#if cartListVO.actFull?? >
	        <#--<div class="full-box clear">-->
	            <#--<div class="full-reduction">满减</div>-->
	            <#--<div class="full-money">-->
	              <#--<span>-->
                  		<#--<#if cartListVO.actFull.firstFull?? && cartListVO.actFull.firstFull &gt; 0>-->
                  		<#--&nbsp;满${(cartListVO.actFull.firstFull)?string('0.00')!"0.00"}-${(cartListVO.actFull.firstDiscount)?string('0.00')!"0.00"}-->
                  		<#--</#if>-->
                  		<#--<#if cartListVO.actFull.secondFull?? && cartListVO.actFull.secondFull &gt; 0>-->
                  		<#--&nbsp;满${(cartListVO.actFull.secondFull)?string('0.00')!"0.00"}-${(cartListVO.actFull.secondDiscount)?string('0.00')!"0.00"}-->
                  		<#--</#if>-->
                  		<#--<#if cartListVO.actFull.thirdFull?? && cartListVO.actFull.thirdFull &gt; 0>-->
                  		<#--&nbsp;满${(cartListVO.actFull.thirdFull)?string('0.00')!"0.00"}-${(cartListVO.actFull.thirdDiscount)?string('0.00')!"0.00"}-->
                  		<#--</#if>-->
                  		<#--<#if cartListVO.orderDiscount?? && cartListVO.orderDiscount &gt; 0>-->
                  		<#--<br>-->
	              		<#--&nbsp;(已减:${(cartListVO.orderDiscount)?string('0.00')!"0.00"}元)-->
	              		<#--</#if>-->
	              <#--</span>-->
	            <#--</div>-->
	        <#--</div>-->

                <div class="full-box clear" style="background-color: #ffffff;">
                    <div class="full-reduction" style="font-size:12px;font-family:PingFangSC-Medium,PingFangSC">满减</div>
                    <div class="full-money">
                        <span style="font-size:12px;font-family:PingFangSC-Medium,PingFangSC">
                            <#if cartListVO.actFull.firstFull?? && cartListVO.actFull.firstFull &gt; 0>
                            &nbsp;满${(cartListVO.actFull.firstFull)?string('0')!"0"}-${(cartListVO.actFull.firstDiscount)?string('0')!"0"}
                            </#if>
                            <#if cartListVO.actFull.secondFull?? && cartListVO.actFull.secondFull &gt; 0>
                            &nbsp;满${(cartListVO.actFull.secondFull)?string('0')!"0"}-${(cartListVO.actFull.secondDiscount)?string('0')!"0"}
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
                        <#if cart?? && cart.checked?? && cart.checked == 1>
                            <span class="selectCheckboxY" style="margin-top: 30px;" name="checkItem" id="${(cart.id)!''}" value="${(cart.id)!''}" onclick="checkedChange(this)"></span>
                        <#else>
                            <span class="selectCheckboxN" style="margin-top: 30px;" name="checkItem" id="${(cart.id)!''}" value="${(cart.id)!''}" onclick="checkedChange(this)"></span>
                        </#if>

                    <#--<input type='checkbox' name="checkItem" id="${(cart.id)!''}" value="${(cart.id)!''}" onchange="checkedChange(this)" style="display: none" autocomplete="off" <#if cart?? && cart.checked?? && cart.checked == 1>checked="checked"</#if>>-->
            </div>
                    <dt style="width:90px;height:90px;">
                    <#--<a href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html?goodId=${(cart.productGoodsId)!0}"><img src="${(domainUrlUtil.imageResources)!}${product.masterImg!''}"></a>-->
                        <a href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html?goodId=${(cart.productGoodsId)!0}"><img src="${(domainUrlUtil.imageResources)!}${product.masterImg!''}"></a>
                    </dt>
                    <dd class="flex-2 pos_relative">
                        <div class="product_name">
                            <a href="${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html?goodId=${(cart.productGoodsId)!0}" style="font-size:13px;font-family:PingFangSC-Regular,PingFangSC;font-weight:400;color:rgba(51,51,51,1);line-height:20px;">${(product.name1)!""}&nbsp;${cart.specInfo!''}</a>
                        </div>
                        <span style="padding:0 7px;margin-left: 7px;font-size:13px;font-family:PingFangSC-Regular,PingFangSC;font-weight:400;color:rgba(153,153,153,1);line-height:18px;"></span>
                        <#--<span style="padding:0 7px;margin-left: 7px; background:linear-gradient(90deg,rgba(249,249,249,1) 0%,rgba(249,249,249,1) 100%);font-size:13px;font-family:PingFangSC-Regular,PingFangSC;font-weight:400;color:rgba(153,153,153,1);line-height:18px;">黑色;M</span>-->
                    <#--<div></div>-->
                        <div class="flex">
                            <span class="flex-3" style="height: 3rem;padding-left: 5px;line-height: 3rem;color: #E7250C;font-family: PingFangSC-Medium,PingFangSC;"> ￥<font>${(productGoods.mallMobilePrice)?string('0.00')!"0.00"}</font> </span>
                            <a class="subtractNum" onclick="cartminus(this)"><img src="${(domainUrlUtil.staticResources)!}/img/-@2x.png" style="width: 10px;margin-top:8px;margin-left: 5px;" alt=""></a>
                            <input type="text" style="width: 48px;height: 21px;margin-top: 5px;border: 1px solid rgba(224,224,224,1);text-align: center;font-family:PingFangSC-Medium,PingFangSC;color:rgba(46,45,45,1); " onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="quantity" size="4" value="${cart.count!''}" id="number" onblur="modify(this);">
                            <a class="addNum" onclick="cartplus(this)"><img src="${(domainUrlUtil.staticResources)!}/img/jia@2x.png" style="width: 10px;margin-top:4px;margin-left:6px;" alt=""></a>
                        <#--<a class="quantity-decrease" onclick="cartminus(this)">-->
                        <#--<i class="fa fa-minus-square"></i>-->
                        <#--</a>-->
                        <#--<input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" class="quantity" size="4" value="${cart.count!''}" id="number" onblur="modify(this);">-->
                        <#--<a class="quantity-increase" onclick="cartplus(this)">-->
                        <#--<i class="fa fa-plus-square"></i>-->
                        <#--</a>-->
                        <#--<div>-->
                        <#--(库存${productGoods.productStock!'0'}件)-->
                        <#--</div>-->
                            <input type="hidden" id="productStock" name="productStock" value="${productGoods.productStock!'0'}"/>
                            <input type="hidden" id="cartId" name="cartId" value="${cart.id!'0'}"/>
                        </div>
                    <#--<div class="cart_delate"><i class="fa fa-trash" onclick="deleteCart(${cart.id!'0'})"></i></div>-->
                    </dd>
                </dl>
            <div class="cartPrice">小计<font>¥${(cart.currDiscountedAmount)?string('0.00')!"0.00"}</font>
            <!-- <div class="clr53"></div> -->
            <span class='productStock'>&nbsp;&nbsp;&nbsp;已省:${(cart.currDiscounted)?string('0.00')!0}</span>
            </div>
            </div>


	        </#list>
            </div>
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
