package com.yixiekeji.model.product;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.product.ProductGoodsReadDao;
import com.yixiekeji.dao.shop.write.product.ProductGoodsWriteDao;
import com.yixiekeji.dao.shop.write.product.ProductWriteDao;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;

@Component(value = "productGoodsModel")
public class ProductGoodsModel {

    @Resource
    private ProductGoodsWriteDao         productGoodsWriteDao;
    @Resource
    private ProductWriteDao              productWriteDao;
    @Resource
    private ProductGoodsReadDao          productGoodsReadDao;

    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    public List<ProductGoods> getGoodSByProductId(Integer productId) {
        return productGoodsReadDao.getByProductId(productId);
    }

    public Boolean saveProductGoods(ProductGoods productGoods) {
        return productGoodsWriteDao.insert(productGoods) > 0;
    }

    public Boolean updateProductGoods(ProductGoods productGoods) {
        return productGoodsWriteDao.update(productGoods) > 0;
    }

    public Boolean delProductGoods(Integer productGoodsId) {
        //1. business check
        if (null == productGoodsId || 0 == productGoodsId)
            throw new BusinessException("根据id删除货品表失败，id为空");

        //2. dbOper
        return productGoodsWriteDao.del(productGoodsId) > 0;
    }

    public ProductGoods getProductGoodsById(Integer productGoodsId) {
        return productGoodsWriteDao.get(productGoodsId);
    }

    public List<ProductGoods> pageProductGoods(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(productGoodsWriteDao.count(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return productGoodsWriteDao.page(queryMap, start, size);
    }

    /**
     * 根据条件（规格id组合）取得货品信息
     * @param queryMap
     * @return
     */

    public ProductGoods getProductGoodsByCondition(Map<String, String> queryMap) {
        if (queryMap == null) {
            throw new BusinessException("查询条件不能为空");
        } else {
            String productId = queryMap.get("q_productId");
            if (StringUtil.isEmpty(productId)) {
                throw new BusinessException("传入的产品ID不能为空");
            }
        }

        return productGoodsWriteDao.getByCondition(queryMap);
    }

    public Boolean updateProductGoods(List<ProductGoods> goodslist) {
        if (goodslist == null || goodslist.size() < 1)
            throw new BusinessException("没有需要更新的货品");
        return productGoodsWriteDao.batchUpdate(goodslist) > 0;
    }

    /**
     * 根据商家ID和sku判断是否已有货品存在
     * @param sellerId
     * @param sku
     * @return
     */
    public Boolean isUnique(Integer sellerId, String sku) {
        List<ProductGoods> prolist = productGoodsWriteDao.getBySellerIdAndSku(sellerId, sku);
        return prolist == null || prolist.size() < 1;
    }

    public Boolean updateProductAndGoods(Product product) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer proresult = productWriteDao.update(product);
            Integer goodsresult = productGoodsWriteDao.batchUpdate(product.getGoodsList());

            transactionManager.commit(status);

            return proresult * goodsresult > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return false;
    }
}
