package com.yixiekeji.model.settlement;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.member.MemberProductBackReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductCateReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleOrderReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.read.settlement.SettlementReadDao;
import com.yixiekeji.dao.shop.read.settlement.SettlementSettingReadDao;
import com.yixiekeji.dao.shop.write.sale.SaleSettingWriteDao;
import com.yixiekeji.dao.shop.write.settlement.SettlementOpWriteDao;
import com.yixiekeji.dao.shop.write.settlement.SettlementSettingWriteDao;
import com.yixiekeji.dao.shop.write.settlement.SettlementWriteDao;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.sale.SaleSetting;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.settlement.Settlement;
import com.yixiekeji.entity.settlement.SettlementOp;
import com.yixiekeji.entity.settlement.SettlementSetting;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Component
public class SettlementSettingModel {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
        .getLogger(SettlementSettingModel.class);

    @Resource
    private SettlementSettingReadDao settlementSettingReadDao;
    @Resource
    private SettlementSettingWriteDao settlementSettingWriteDao;


    /**
     * 保存结算表
     * @param  settlementSetting
     * @return
     */
    public Integer save(SettlementSetting settlementSetting) {
        //        this.dbConstrains(settlement);
        return settlementSettingWriteDao.insert(settlementSetting);
    }

    /**
    * 更新结算表
    * @param  settlementSetting
    * @return
    */
    public Integer update(SettlementSetting settlementSetting) {
        return settlementSettingWriteDao.update(settlementSetting);
    }



    public List<SettlementSetting> getList(Map<String, String> queryMap, Integer start,
                                           Integer size) {
        return settlementSettingReadDao.getList(queryMap, start, size);
    }


    public Integer delete(SettlementSetting settlementSetting) {
        return settlementSettingWriteDao.delete(settlementSetting);
    }
}