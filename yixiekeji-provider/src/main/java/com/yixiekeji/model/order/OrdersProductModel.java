package com.yixiekeji.model.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.product.ProductPictureReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.order.OrdersProductWriteDao;
import com.yixiekeji.dto.ProductDayDto;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.util.FrontProductPictureUtil;

@Component(value = "ordersProductModel")
public class OrdersProductModel {

    @Resource
    private OrdersProductWriteDao ordersProductWriteDao;
    @Resource
    private OrdersProductReadDao  ordersProductReadDao;
    @Resource
    private SellerReadDao         sellerReadDao;

    @Resource
    private ProductPictureReadDao productPictureReadDao;

    /**
    * 根据id取得网单表
    * @param  ordersProductId
    * @return
    */
    public OrdersProduct getOrdersProductById(Integer ordersProductId) {
        return ordersProductWriteDao.get(ordersProductId);
    }

    /**
     * 保存网单表
     * @param  ordersProduct
     * @return
     */
    public Integer saveOrdersProduct(OrdersProduct ordersProduct) {
        return ordersProductWriteDao.insert(ordersProduct);
    }

    /**
    * 更新网单表
    * @param  ordersProduct
    * @return
    */
    public Integer updateOrdersProduct(OrdersProduct ordersProduct) {
        return ordersProductWriteDao.update(ordersProduct);
    }

    /**
     * 根据订单号获取网单
     * @param orderId
     * @return
     */
    public List<OrdersProduct> getOrdersProductByOId(Integer orderId) {
        return ordersProductWriteDao.getByOrderId(orderId);
    }

    /**
     * 根据id 取得网单信息
     * @param request
     * @return
     */
    public OrdersProduct getOrdersProductWithImgById(Integer orderProductId) {
        if (orderProductId == null || orderProductId == 0) {
            throw new BusinessException("网单id为空，请重试！");
        }

        OrdersProduct productvo = ordersProductReadDao.get(orderProductId);
        if (productvo != null) {

            ProductPicture productPicture = productPictureReadDao
                .getproductLead(productvo.getProductId());
            String productLeadMiddle = FrontProductPictureUtil.getproductLeadMiddle(productPicture);

            productvo.setProductLeadLittle(productLeadMiddle);
        }
        return productvo;
    }

    /**
     * 统计商品每日销量
     * @param queryMap
     * @return
     */
    public List<ProductDayDto> getProductDayDto(Map<String, String> queryMap) {
        List<ProductDayDto> list = ordersProductReadDao.getProductDayDto(queryMap);
        if (list != null && list.size() > 0) {
            Map<Integer, Seller> map = new HashMap<Integer, Seller>();
            for (ProductDayDto productDayDto : list) {
                Seller seller = map.get(productDayDto.getSellerId());
                if (seller == null) {
                    seller = sellerReadDao.get(productDayDto.getSellerId());
                    map.put(productDayDto.getSellerId(), seller);
                }
                if (seller != null) {
                    productDayDto.setSellerName(seller.getSellerName());
                }
            }
        }
        return list;
    }
}
