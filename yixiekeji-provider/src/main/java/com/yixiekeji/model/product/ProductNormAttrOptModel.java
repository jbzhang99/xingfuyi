package com.yixiekeji.model.product;

import java.util.HashMap;
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
import com.yixiekeji.dao.shop.write.product.ProductNormAttrOptWriteDao;
import com.yixiekeji.entity.product.ProductNormAttrOpt;

@Component
public class ProductNormAttrOptModel {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
                                                   .getLogger(ProductNormAttrOptModel.class);

    @Resource
    private ProductNormAttrOptWriteDao     productNormAttrOptWriteDao;
    @Resource
    private DataSourceTransactionManager   transactionManager;

    /**
     * 根据id取得商品选定的规格属性(保存商品插入，暂时不用)
     * @param  productNormAttrOptId
     * @return
     */
    public ProductNormAttrOpt getProductNormAttrOptById(Integer productNormAttrOptId) {
        return productNormAttrOptWriteDao.get(productNormAttrOptId);
    }

    /**
     * 保存商品选定的规格属性(保存商品插入，暂时不用)
     * @param  productNormAttrOpt
     * @return
     */
    public Integer saveProductNormAttrOpt(ProductNormAttrOpt productNormAttrOpt) {
        this.dbConstrains(productNormAttrOpt);
        return productNormAttrOptWriteDao.save(productNormAttrOpt);
    }

    /**
    * 更新商品选定的规格属性(保存商品插入，暂时不用)
    * @param  productNormAttrOpt
    * @return
    */
    public Integer updateProductNormAttrOpt(ProductNormAttrOpt productNormAttrOpt) {
        this.dbConstrains(productNormAttrOpt);
        return productNormAttrOptWriteDao.update(productNormAttrOpt);
    }

    private void dbConstrains(ProductNormAttrOpt productNormAttrOpt) {
        productNormAttrOpt
            .setName(StringUtil.dbSafeString(productNormAttrOpt.getName(), false, 50));
        productNormAttrOpt.setImage(StringUtil.dbSafeString(productNormAttrOpt.getImage(), true,
            200));
    }

    public List<ProductNormAttrOpt> page(Map<String, String> queryMap, PagerInfo pager)
                                                                                       throws Exception {
        Map<String, Object> param = new HashMap<String, Object>(queryMap);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(productNormAttrOptWriteDao.getCount(param));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        param.put("start", start);
        param.put("size", size);
        List<ProductNormAttrOpt> list = productNormAttrOptWriteDao.page(param);
        return list;
    }

    public Integer del(Integer id) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (id == null)
                throw new BusinessException("删除商品选定的规格属性(保存商品插入，暂时不用)[" + id + "]时出错");
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("[ProductNormAttrOptModel][del] exception:" + e.getMessage());
            transactionManager.rollback(status);
            e.printStackTrace();
            throw e;
        }
        return productNormAttrOptWriteDao.del(id);
    }

}