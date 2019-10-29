package com.yixiekeji.model.settlement;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ning.http.util.DateUtil;
import com.yixiekeji.dao.shop.read.settlement.SettlementSettingReadDao;
import com.yixiekeji.entity.settlement.SettlementSetting;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.member.MemberProductBackReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductCateReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleOrderReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.read.settlement.SettlementReadDao;
import com.yixiekeji.dao.shop.write.sale.SaleSettingWriteDao;
import com.yixiekeji.dao.shop.write.settlement.SettlementOpWriteDao;
import com.yixiekeji.dao.shop.write.settlement.SettlementWriteDao;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.sale.SaleSetting;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.settlement.Settlement;
import com.yixiekeji.entity.settlement.SettlementOp;

@Component
public class SettlementModel {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
        .getLogger(SettlementModel.class);

    @Resource
    private SettlementReadDao              settlementReadDao;
    @Resource
    private SettlementWriteDao             settlementWriteDao;
    @Resource
    private SettlementOpWriteDao           settlementOpWriteDao;
    @Resource
    private OrdersReadDao                  ordersReadDao;
    @Resource
    private OrdersProductReadDao           OrdersProductReadDao;
    @Resource
    private ProductCateReadDao             productCateReadDao;
    @Resource
    private SellerReadDao                  sellerReadDao;
    @Resource
    private MemberProductBackReadDao       memberProductBackReadDao;

    @Resource
    private SaleOrderReadDao               saleOrderReadDao;
    @Resource
    private SaleSettingWriteDao            saleSettingWriteDao;
    @Resource
    private SettlementSettingReadDao settlementSettingReadDao;

    @Resource
    private DataSourceTransactionManager   transactionManager;

    /**
     * 商家结算账单生成
     * @return
     */
    public boolean jobSettlement() {

        // add by honglim   #   start
        /*
         新增结算设置。 首先当前执行日期与结算设置中运行日期配置。
         当前执行日期与结算运行日期相等时（状态为未运行且没有删除的）， 结算统计当前运行日期的开始时间与结束时间内的订单统计并生成结算单。
         生成每个商家的结算单，
          */
        SettlementSetting ss = settlementSettingReadDao.getBeanByRuntime(new Date());
        if(ss==null){
            return true;
        }

        Date startDatetime = ss.getStartDate();
        Date endDatetime = ss.getEndDate();
        String strStartDatetime = DateUtil.formatDate(startDatetime, "yyyy-MM-dd HH:mm:ss");
        String strEndDatetime = DateUtil.formatDate(endDatetime, "yyyy-MM-dd HH:mm:ss");


        // add by honglim   #   end


        List<Seller> sellerList = sellerReadDao.getSettlementSeller();
        // 记录商品分类
        Map<Integer, ProductCate> cateMap = new HashMap<Integer, ProductCate>();

        /*
        // 计算当前周期
        // 周期开始时间
        // 取当前时间
        Calendar cal = Calendar.getInstance();
        // 当前时间的上一个月
        cal.add(Calendar.MONTH, -1);
        // 结算年
        int year = cal.get(Calendar.YEAR);
        // 结算月(Calendar的月从0开始，所以加1)
        int month = cal.get(Calendar.MONTH) + 1;
        // 结算周期yyyyMM
        String monthStr = String.valueOf(month);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        String settleCycle = String.valueOf(year) + monthStr;
        // 周期开始时间，1号0时0分0秒
        String startTime = year + "-" + month + "-01 00:00:00";
        // 周期结束时间，周期月最后一个天23时59分59秒
        // 周期最后一天
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String endTime = year + "-" + month + "-" + lastDay + " 23:59:59";
        */
        String startTime = strStartDatetime;
        String endTime = strEndDatetime;
        String settleCycle = DateUtil.formatDate(ss.getRunTime(), "yyyy-MM-dd");

        if (sellerList != null && sellerList.size() > 0) {
            for (Seller seller : sellerList) {
                // 单个商家结算作为一个事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    // 结算前先删除已存在的当前周期的结算数据
                    settlementWriteDao.deleteByCysleAndSellerId(settleCycle, seller.getId());
                    settlementOpWriteDao.deleteByCysleAndSellerId(settleCycle, seller.getId());

                    // 生成新的结算
                    Settlement settlement = new Settlement();
                    settlement.setSettleCycle(settleCycle);
                    settlement.setSellerId(seller.getId());
                    settlement.setSellerName(seller.getSellerName());
                    settlement.setMoneyProduct(BigDecimal.ZERO);
                    settlement.setMoneyLogistics(BigDecimal.ZERO);
                    settlement.setMoneyOrder(BigDecimal.ZERO);
                    settlement.setMoneyPaidBalance(BigDecimal.ZERO);
                    settlement.setMoneyPaidReality(BigDecimal.ZERO);
                    settlement.setMoneyCoupon(BigDecimal.ZERO);
                    settlement.setMoneyActFull(BigDecimal.ZERO);
                    settlement.setMoneyDiscount(BigDecimal.ZERO);
                    settlement.setMoneyIntegral(BigDecimal.ZERO);
                    settlement.setIntegral(0);
                    settlement.setMoneyBack(BigDecimal.ZERO);
                    settlement.setMoneyIntegralBack(BigDecimal.ZERO);
                    settlement.setMoneyOther(BigDecimal.ZERO);
                    settlement.setMoneyOtherType(0);
                    settlement.setMoneyOtherReason("");
                    settlement.setCommision(BigDecimal.ZERO);
                    settlement.setMoneySale(BigDecimal.ZERO);
                    settlement.setSaleSet(SaleSetting.SALESET_SELLER);
                    settlement.setPayable(BigDecimal.ZERO);
                    settlement.setStatus(Settlement.STATUS_1);
                    settlement.setSellerDoubt("");
                    settlement.setPlatformExplain("");
                    settlement.setCreateTime(new Date());
                    settlement.setUpdateTime(new Date());
                    settlement.setUpdateUserId(0);
                    settlement.setUpdateUserName("system");
                    Integer insert = settlementWriteDao.insert(settlement);
                    if (insert == 0) {
                        throw new BusinessException("结算账单保存失败！");
                    }

                    // 商品总金额，等于订单中所有的商品的单价乘以数量之和：orders.money_product总和
                    BigDecimal moneyProduct = BigDecimal.ZERO;
                    // 物流费用之和：orders.money_logistics总和
                    BigDecimal moneyLogistics = BigDecimal.ZERO;
                    // 实际应收金额总计：orders.money_order总和
                    BigDecimal moneyOrder = BigDecimal.ZERO;
                    // 用户余额支付总和
                    BigDecimal moneyPaidBalance = BigDecimal.ZERO;
                    // 用户现金支付和
                    BigDecimal moneyPaidReality = BigDecimal.ZERO;
                    // 优惠码优惠金额：money_coupon总和
                    BigDecimal moneyCoupon = BigDecimal.ZERO;
                    // 订单满减总金额：money_act_full总和
                    BigDecimal moneyActFull = BigDecimal.ZERO;
                    // 优惠金额总额（满减、立减、优惠券等所有优惠额的和）：money_discount总和
                    BigDecimal moneyDiscount = BigDecimal.ZERO;
                    // 积分转换金额总和
                    BigDecimal moneyIntegral = BigDecimal.ZERO;
                    // 使用积分总和
                    Integer integral = 0;
                    // 当月退货退款和(现金+余额支付部分)，不是orders表的money_back和，而是退货表back_money和
                    BigDecimal moneyBack = BigDecimal.ZERO;
                    // 当月退货积分退款和(积分支付部分)，退货表back_integral_money和
                    BigDecimal integralMoneyBack = BigDecimal.ZERO;
                    // 佣金
                    BigDecimal commision = BigDecimal.ZERO;
                    // 应支付金额
                    BigDecimal payable = BigDecimal.ZERO;
                    //三级分销佣金
                    BigDecimal saleMoney = BigDecimal.ZERO;

                    /*
                     * 订单网单相关金额计算
                     */
                    List<Orders> ordersList = ordersReadDao.getSettleOrders(seller.getId(),
                        startTime, endTime);
                    if (ordersList != null && ordersList.size() > 0) {
                        for (Orders orders : ordersList) {

                            // 商品总金额，等于订单中所有的商品的单价乘以数量之和：orders.money_product总和
                            moneyProduct = moneyProduct.add(orders.getMoneyProduct());
                            // 物流费用之和：orders.money_logistics总和
                            moneyLogistics = moneyLogistics.add(orders.getMoneyLogistics());
                            // 实际应收金额总计：orders.money_order总和
                            moneyOrder = moneyOrder.add(orders.getMoneyOrder());
                            // 用户余额支付总和
                            moneyPaidBalance = moneyPaidBalance.add(orders.getMoneyPaidBalance());
                            // 用户现金支付和
                            moneyPaidReality = moneyPaidReality.add(orders.getMoneyPaidReality());
                            // 优惠码优惠金额：money_coupon总和
                            moneyCoupon = moneyCoupon.add(orders.getMoneyCoupon());
                            // 订单满减总金额：money_act_full总和
                            moneyActFull = moneyActFull.add(orders.getMoneyActFull());
                            // 优惠金额总额（满减、立减、优惠券等所有优惠额的和）：money_discount总和
                            moneyDiscount = moneyDiscount.add(orders.getMoneyDiscount());
                            // 积分转换金额总和
                            moneyIntegral = moneyIntegral.add(orders.getMoneyIntegral());
                            integral += orders.getIntegral();

                            /*
                             * 获取订单下网单，根据网单商品所属分类计算网单佣金
                             */
                            List<OrdersProduct> opList = OrdersProductReadDao
                                .getByOrderId(orders.getId());
                            if (opList != null && opList.size() > 0) {
                                for (OrdersProduct op : opList) {
                                    ProductCate cate = cateMap.get(op.getProductCateId());
                                    if (cate == null) {
                                        cate = productCateReadDao.get(op.getProductCateId());
                                        cateMap.put(op.getProductCateId(), cate);
                                    }
                                    SettlementOp settlementOp = new SettlementOp();
                                    PropertyUtils.copyProperties(settlementOp, op);
                                    settlementOp.setSettlementId(settlement.getId());
                                    settlementOp.setSettleCycle(settleCycle);
                                    settlementOp.setProductCateName(cate.getName());
                                    settlementOp.setScaling(cate.getScaling());
                                    // 网单佣金金额，四舍五入保留2位小数
                                    BigDecimal opCommision = op.getMoneyAmount()
                                        .multiply(cate.getScaling());
                                    opCommision = opCommision.setScale(2, BigDecimal.ROUND_HALF_UP);
                                    settlementOp.setCommision(opCommision);
                                    settlementOp.setSettleTime(new Date());

                                    // 佣金累加
                                    commision = commision.add(opCommision);

                                    // 网单三级分销佣金
                                    BigDecimal sumSale = saleOrderReadDao
                                        .sumOrdersProductId(op.getId());
                                    if (sumSale != null) {
                                        settlementOp.setSaleMoney(sumSale);
                                    } else {
                                        settlementOp.setSaleMoney(BigDecimal.ZERO);
                                    }

                                    insert = settlementOpWriteDao.insert(settlementOp);
                                    if (insert == 0) {
                                        throw new BusinessException("结算网单保存错误！");
                                    }
                                }
                            }

                        }
                    }

                    /*
                     * 退货金额计算
                     */
                    List<MemberProductBack> backList = memberProductBackReadDao
                        .getSettleBacks(seller.getId(), startTime, endTime, 0, 0);
                    if (backList != null && backList.size() > 0) {
                        for (MemberProductBack back : backList) {
                            // 退货金额(现金+余额支付部分)
                            moneyBack = moneyBack.add(back.getBackMoney());
                            // 退货积分金额（积分退款部分）
                            integralMoneyBack = integralMoneyBack.add(back.getBackIntegralMoney());
                        }
                    }

                    /**
                     * 计算三级分销佣金
                     */
                    SaleSetting saleSetting = saleSettingWriteDao.getSaleSettingDesc();
                    if (saleSetting != null) {
                        saleMoney = saleOrderReadDao.sumSaleOrderBySellerId(seller.getId(),
                            startTime, endTime);
                        settlement.setMoneySale(saleMoney);
                        settlement.setSaleSet(saleSetting.getSaleSet());
                    }

                    /*
                     * 修改结算总账单表
                     */
                    settlement.setMoneyProduct(moneyProduct);
                    settlement.setMoneyLogistics(moneyLogistics);
                    settlement.setMoneyOrder(moneyOrder);
                    settlement.setMoneyPaidBalance(moneyPaidBalance);
                    settlement.setMoneyPaidReality(moneyPaidReality);
                    settlement.setMoneyCoupon(moneyCoupon);
                    settlement.setMoneyActFull(moneyActFull);
                    settlement.setMoneyDiscount(moneyDiscount);
                    settlement.setMoneyIntegral(moneyIntegral);
                    settlement.setIntegral(integral);
                    settlement.setMoneyBack(moneyBack);
                    settlement.setMoneyIntegralBack(integralMoneyBack);
                    settlement.setCommision(commision);
                    // 应支付金额(订单金额-退款金额-佣金-积分退款金额)
                    payable = ((moneyOrder.subtract(moneyBack)).subtract(commision))
                        .subtract(integralMoneyBack);
                    if (saleSetting.getSaleSet().intValue() == SaleSetting.SALESET_SELLER) {
                        payable = payable.subtract(settlement.getMoneySale());
                    }
                    settlement.setPayable(payable);
                    Integer update = settlementWriteDao.update(settlement);
                    if (update == 0) {
                        throw new BusinessException("修改结算账单时失败！");
                    }

                    transactionManager.commit(status);
                } catch (BusinessException be) {
                    transactionManager.rollback(status);
                    log.error(be.getMessage(), be);
                } catch (Exception e) {
                    transactionManager.rollback(status);
                    log.error(e.getMessage(), e);
                }
            }
        }

        return true;
    }

    /**
     * 根据id取得结算表
     * @param  settlementId
     * @return
     */
    public Settlement getSettlementById(Integer settlementId) {
        return settlementWriteDao.get(settlementId);
    }

    /**
     * 保存结算表
     * @param  settlement
     * @return
     */
    public Integer saveSettlement(Settlement settlement) {
        //        this.dbConstrains(settlement);
        return settlementWriteDao.insert(settlement);
    }

    /**
    * 更新结算表
    * @param  settlement
    * @return
    */
    public boolean updateSettlement(Settlement settlement) {
        //        this.dbConstrains(settlement);
        return settlementWriteDao.update(settlement) > 0;
    }

    public Integer getSettlementsCount(Map<String, String> queryMap) {
        return settlementReadDao.getSettlementsCount(queryMap);
    }

    public List<Settlement> getSettlements(Map<String, String> queryMap, Integer start,
                                           Integer size) {
        return settlementReadDao.getSettlements(queryMap, start, size);
    }

}