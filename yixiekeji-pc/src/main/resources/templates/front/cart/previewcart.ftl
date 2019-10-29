
<#if cartInfoVO?? && (cartInfoVO.cartListVOs??) && (cartInfoVO.cartListVOs?size &gt; 0) >
<div class='incart-goods-box ps-container'>
	<div class='incart-goods'>
		<div class='sub-title'>
			<h4>最新加入的商品</h4>
		</div>
		<#list cartInfoVO.cartListVOs as cartListVO>
			<#if (cartListVO.cartList??) && (cartListVO.cartList?size>0) >
               <#list cartListVO.cartList as cart>
                   <#assign product = cart.product />
                   <#assign productGoods = cart.productGoods />
				<dl>
					<dt class='shop-googs-name'>
						<a href='${(domainUrlUtil.urlResources)!}/product/${cart.productId!0}.html' target="_blank" style="word-break: break-all;text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;">${(product.name1)!'' }</a>
						${(cart.specInfo)!'' }
					</dt>
					
					<dd class='mcart-mj' style="display:block;">
						<a href='' title="${(product.name1)!'' }">
                        <img width='80' height='80'  alt="${product.name1!''}" 
                        	src="${(domainUrlUtil.imageResources)!}${product.masterImg!''}"/>
					</dd>
					<dd class='mcart-price' style="display:block;">
						<em>¥${productGoods.mallPcPrice!0}×${(cart.count)!0}</em>
					</dd>
				</dl>
			</#list>
			</#if>
		</#list>
	</div>
</div>
<div class='checkout clearfix'>
	<span class='checkout-price fl'> 共<i>${(cartInfoVO.totalNumber)!0}</i>种商品&nbsp;&nbsp;总计金额： <em>¥${cartInfoVO.cartAmount!'0.00'}</em>
	</span>
	<span class="fr">
		<a href='${(domainUrlUtil.urlResources)!}/cart/detail.html' class='btn btn-danger' target='_blank' style='color: #fff; padding: 4px 9px;'>去购物车</a>
	</span>
</div>
<input type="hidden" id="totalNumber" name="totalNumber" value="${cartInfoVO.totalNumber!0}"/>
<#else>
	<!-- 如果没有商品的话显示这个 -->
	<div class='no-order'>
		<div class="emptycart">
	      <div class="emptycart_line"></div>
	      <div class="emptycart_txt"><img src="${(domainUrlUtil.staticResources)!}/img/settleup-nogoods.png" alt="">购物车中还没有商品，赶紧选购吧</div>
	   </div>
	</div>
	<input type="hidden" id="totalNumber" name="totalNumber" value="0"/>
</#if>

