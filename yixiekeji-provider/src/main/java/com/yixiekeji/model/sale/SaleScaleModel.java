package com.yixiekeji.model.sale;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.dao.shop.read.sale.SaleScaleReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleSettingReadDao;
import com.yixiekeji.dao.shop.write.product.ProductWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleScaleLogWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleScaleProductLogWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleScaleWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleSettingWriteDao;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.sale.SaleScaleLog;
import com.yixiekeji.entity.sale.SaleScaleProductLog;
import com.yixiekeji.entity.sale.SaleSetting;

@Component
public class SaleScaleModel {

    @Resource
    private SaleScaleWriteDao            saleScaleWriteDao;

    @Resource
    private SaleScaleLogWriteDao         saleScaleLogWriteDao;

    @Resource
    private SaleScaleReadDao             saleScaleReadDao;

    @Resource
    private SaleScaleProductLogWriteDao  saleScaleProductLogWriteDao;

    @Resource
    private SaleSettingWriteDao          saleSettingWriteDao;

    @Resource
    private SaleSettingReadDao           saleSettingReadDao;

    @Resource
    private ProductWriteDao              productWriteDao;

    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得商家总体分佣表
     * @param  saleScaleId
     * @return
     */
    public SaleScale getSaleScaleById(Integer saleScaleId) {
        return saleScaleReadDao.get(saleScaleId);
    }

    /**
     * 根据商家id取得商家总体分佣表
     * @param  sellerId
     * @return
     */
    public SaleScale getSaleScaleBySellerId(Integer sellerId) {
        return saleScaleReadDao.getSaleScaleBySellerId(sellerId);
    }

    /**
     * 保存商家总体分佣表
     * @param  saleScale
     * @return
     */
    public Integer saveSaleScale(SaleScale saleScale) {
        return saleScaleWriteDao.insert(saleScale);
    }

    /**
     * 根据商家id取得商家总体分佣表
     * @param  saleScale
     * @return
     */
    public Integer saveSaleScaleOrUpdateBySellerId(SaleScale saleScale) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        int success = 0;
        String opt = "";
        try {
            if (null == saleScale.getId()) {
                success = saleScaleWriteDao.insert(saleScale);
                opt = ConstantsEJS.DATA_OPT_TYPE_C;
            } else {
                success = saleScaleWriteDao.update(saleScale);
                saleScale = saleScaleWriteDao.get(saleScale.getId());
                opt = ConstantsEJS.DATA_OPT_TYPE_U;
            }
            //记录日志
            final net.sf.cglib.beans.BeanCopier bc = net.sf.cglib.beans.BeanCopier
                .create(SaleScale.class, SaleScaleLog.class, false);
            SaleScaleLog saleScaleLog = new SaleScaleLog();
            bc.copy(saleScale, saleScaleLog, null);
            saleScaleLog.setId(null);
            saleScaleLog.setOptType(opt);
            saleScaleLogWriteDao.insert(saleScaleLog);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return success;
    }

    /**
    * 更新商家总体分佣表
    * @param  saleScale
    * @return
    */
    public Integer updateSaleScale(SaleScale saleScale) {
        return saleScaleWriteDao.update(saleScale);
    }

    /**
     * 保存商品分佣日志表
     * @param  saleScaleProductLog
     * @return
     */
    public Integer saveSaleScaleProductLog(SaleScaleProductLog saleScaleProductLog) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        int success = 0;
        try {
            productWriteDao.updateSaleScale(saleScaleProductLog.getProductId(),
                saleScaleProductLog.getSaleScale1(), saleScaleProductLog.getSaleScale2());

            success = saleScaleProductLogWriteDao.insert(saleScaleProductLog);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return success;
    }

    /**
     * 查询正在上架的商家的分佣
     * @return
     */
    public List<SaleScale> getSaleScales() {
        return saleScaleReadDao.getSaleScalesByState();
    }

    /**
     * 查询最新的分销设置的一条记录
     * @return
     */
    public SaleSetting getSaleSettingDesc() {
        return saleSettingReadDao.getSaleSettingDesc();
    }

    /**
     * 保存分销设置表
     * @param  saleSetting
     * @return
     */
    public Integer saveSaleSetting(SaleSetting saleSetting) {
        SaleSetting old = saleSettingWriteDao.getSaleSettingDesc();
        if (old != null) {
            saleSetting.setSaleOrderTime(old.getSaleOrderTime());
        }
        return saleSettingWriteDao.insert(saleSetting);
    }

}