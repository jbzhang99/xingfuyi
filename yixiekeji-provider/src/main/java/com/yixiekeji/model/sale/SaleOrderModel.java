package com.yixiekeji.model.sale;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.dao.shop.read.member.MemberProductBackReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleMemberReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleOrderReadDao;
import com.yixiekeji.dao.shop.read.sale.SaleScaleReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.sale.SaleOrderWriteDao;
import com.yixiekeji.dao.shop.write.sale.SaleSettingWriteDao;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.entity.sale.SaleOrder;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.sale.SaleSetting;

@Component
public class SaleOrderModel {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
        .getLogger(SaleOrderModel.class);

    @Resource
    private SaleOrderWriteDao              saleOrderWriteDao;

    @Resource
    private SaleOrderReadDao               saleOrderReadDao;

    @Resource
    private SaleMemberReadDao              saleMemberReadDao;

    @Resource
    private SaleScaleReadDao               saleScaleReadDao;

    @Resource
    private SaleSettingWriteDao            saleSettingWriteDao;

    @Resource
    private OrdersReadDao                  ordersReadDao;

    @Resource
    private OrdersProductReadDao           ordersProductReadDao;

    @Resource
    private ProductReadDao                 productReadDao;

    @Resource
    private MemberProductBackReadDao       memberProductBackReadDao;

    @Resource
    private SellerReadDao                  sellerReadDao;

    /**
     * 根据id取得收入流水表
     * @param  saleOrderId
     * @return
     */
    public SaleOrder getSaleOrderById(Integer saleOrderId) {
        return saleOrderReadDao.get(saleOrderId);
    }

    /**
     * 保存收入流水表
     * @param  saleOrder
     * @return
     */
    public Integer saveSaleOrder(SaleOrder saleOrder) {
        this.dbConstrains(saleOrder);
        return saleOrderWriteDao.insert(saleOrder);
    }

    /**
    * 更新收入流水表
    * @param  saleOrder
    * @return
    */
    public Integer updateSaleOrder(SaleOrder saleOrder) {
        this.dbConstrains(saleOrder);
        return saleOrderWriteDao.update(saleOrder);
    }

    private void dbConstrains(SaleOrder saleOrder) {
        saleOrder.setBuyName(StringUtil.dbSafeString(saleOrder.getBuyName(), false, 50));
        saleOrder.setMemberName(StringUtil.dbSafeString(saleOrder.getMemberName(), false, 50));
        saleOrder.setProductName(StringUtil.dbSafeString(saleOrder.getProductName(), false, 255));
        saleOrder.setOrderSn(StringUtil.dbSafeString(saleOrder.getOrderSn(), false, 50));
        saleOrder.setBackIds(StringUtil.dbSafeString(saleOrder.getBackIds(), false, 200));
    }

    /**
     * 定时器定时根据已经完成的订单，生成对应的佣金
     * @return
     */
    public Boolean jobSaveSaleOrder() {
        SaleSetting saleSetting = saleSettingWriteDao.getSaleSettingDesc();
        if (saleSetting == null) {
            return true;
        }
        int page = 500;//每次取500数据
        //根据上次处理时间，查询订单表，普通订单生成佣金，活动订单不生成佣金
        int count = ordersReadDao.countByFinishTimeAndOrderState(saleSetting.getSaleOrderTime(),
            Orders.ORDER_STATE_5);
        if (count != 0) {
            //查询正在上架的分销的所有商家放入到Map中key是商家ID，值是分佣对象
            Map<Integer, SaleScale> mapSaleScale = new HashMap<Integer, SaleScale>();
            List<SaleScale> saleScales = saleScaleReadDao.getSaleScalesByState();
            if (null != saleScales) {
                for (SaleScale saleScale : saleScales) {
                    mapSaleScale.put(saleScale.getSellerId(), saleScale);
                }
            }

            int start = 0, size = 0;
            int number = count / page;
            for (int i = 0; i <= number; i++) {
                start = page * i;
                if (i == number) {
                    size = count % page;
                    List<Orders> listOrders = ordersReadDao.getByFinishTimeAndOrderState(
                        saleSetting.getSaleOrderTime(), Orders.ORDER_STATE_5, start, size);
                    //根据订单列表保存分销信息
                    saveSaleOrderAll(mapSaleScale, listOrders);
                } else {
                    size = page;
                    List<Orders> listOrders = ordersReadDao.getByFinishTimeAndOrderState(
                        saleSetting.getSaleOrderTime(), Orders.ORDER_STATE_5, start, size);
                    //根据订单列表保存分销信息
                    saveSaleOrderAll(mapSaleScale, listOrders);
                }
            }
        }

        //查询新创建的退货，然后判断分销订单中是否存在记录，存在更新，不存在不做处理
        int countBack = memberProductBackReadDao
            .countByBackMoneyTime(saleSetting.getSaleOrderTime());
        if (countBack != 0) {
            int start = 0, size = 0;
            int number = count / page;
            for (int i = 0; i <= number; i++) {
                start = page * i;
                if (i == number) {
                    size = count % page;
                    List<MemberProductBack> listOrders = memberProductBackReadDao
                        .getByBackMoneyTime(saleSetting.getSaleOrderTime(), start, size);
                    if (listOrders != null && listOrders.size() > 0) {
                        //根据订单列表保存分销信息
                        updateSaleOrderByBackProduct(listOrders);
                    }
                    //根据订单列表保存分销信息
                } else {
                    size = page;
                    List<MemberProductBack> listOrders = memberProductBackReadDao
                        .getByBackMoneyTime(saleSetting.getSaleOrderTime(), start, size);
                    if (listOrders != null && listOrders.size() > 0) {
                        //根据订单列表保存分销信息
                        updateSaleOrderByBackProduct(listOrders);
                    }
                }
            }

        }

        //查询比当前时间小15天的订单，假如没有退货把预计收益修改为可以体现状态
        try {
            Date date2 = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            calendar.add(Calendar.DAY_OF_MONTH, -15);
            date2 = calendar.getTime();
            saleOrderWriteDao.updateBySaleStateAndCreateTime(date2);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询比当前时间小15天的订单，假如没有退货把预计收益修改为可以体现状态,出现异常");
        }

        // 更新分销设置表
        try {
            saleSetting.setCreateId(0);
            saleSettingWriteDao.insertSaleOrderTime(saleSetting);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新分销设置表,出现异常");
        }

        return true;
    }

    /**
     * 根据退货信息更新分佣表
     * @param listOrders
     */
    private void updateSaleOrderByBackProduct(List<MemberProductBack> listOrders) {
        for (MemberProductBack memberProductBack : listOrders) {
            //异常不过，不会因为某一条记录运行失败，导致整体运行失败
            try {
                List<SaleOrder> saleOrders = saleOrderWriteDao
                    .getByOrdersProductId(memberProductBack.getOrderProductId());
                if (saleOrders == null || saleOrders.size() == 0) {
                    continue;
                }
                for (SaleOrder saleOrder : saleOrders) {
                    //判断退款ID是否已经处理
                    if ((saleOrder.getBackIds() + ",")
                        .contains(memberProductBack.getId().toString() + ",")) {
                        continue;
                    }

                    saleOrder.setBackNumber(memberProductBack.getNumber().intValue()
                                            + saleOrder.getBackNumber().intValue());
                    if ("".equals(saleOrder.getBackIds())) {
                        saleOrder.setBackIds(memberProductBack.getId().toString());
                    } else {
                        saleOrder.setBackIds(
                            saleOrder.getBackIds() + "," + memberProductBack.getId().toString());
                    }
                    saleOrder
                        .setBackMoney(saleOrder.getBackMoney().add(memberProductBack.getBackMoney())
                            .add(memberProductBack.getBackIntegralMoney()));

                    //重新计算佣金
                    saleOrder.setSaleMoney((saleOrder.getMoneyAll()
                        .subtract(saleOrder.getActMoney()).subtract(saleOrder.getBackMoney()))
                            .multiply(saleOrder.getSaleScale()));
                    //更新数据
                    saleOrderWriteDao.update(saleOrder);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("根据退货信息更新分佣表出现异常，异常memberProductBackID：" + memberProductBack.getId());
            }
        }
    }

    /**
     * 根据订单列表保存分销信息
     * @param mapSaleScale
     * @param listOrders
     */
    private void saveSaleOrderAll(Map<Integer, SaleScale> mapSaleScale, List<Orders> listOrders) {
        if (listOrders != null) {
            SaleOrder saleOrder1 = null;
            SaleOrder saleOrder2 = null;
            for (Orders orders : listOrders) {
                //异常不过，不会因为某一条记录运行失败，导致整体运行失败
                try {
                    //判断此用户是否有推荐人
                    SaleMember saleMember = saleMemberReadDao
                        .getSaleMemberByMemberId(orders.getMemberId());
                    //无推荐人，执行下一条记录
                    if (saleMember == null || saleMember.getReferrerId() == null
                        || saleMember.getReferrerId().intValue() == 0) {
                        continue;
                    }

                    //商家没有分佣执行下一条记录
                    SaleScale saleScale = mapSaleScale.get(orders.getSellerId());
                    if (saleScale == null
                        || saleScale.getState().intValue() == ConstantsEJS.YES_NO_0) {
                        continue;
                    }

                    //创建订单的时间比商家启用的时间小，不生成佣金
                    if (saleScale.getCreateTime().getTime() > orders.getCreateTime().getTime()) {
                        continue;
                    }

                    List<OrdersProduct> listOrdersProducts = ordersProductReadDao
                        .getByOrderId(orders.getId());
                    for (OrdersProduct ordersProduct : listOrdersProducts) {
                        //判断是否已经生成佣金
                        int countordersProduct = saleOrderWriteDao
                            .countByOrdersProductId(ordersProduct.getId());
                        if (countordersProduct > 0) {
                            continue;
                        }

                        Product product = productReadDao.get(ordersProduct.getProductId());
                        BigDecimal saleScale1 = getSaleScaleByGrade(product, saleScale, 1);
                        //处理一级分销
                        if (saleScale1 != null && !"0".equals(saleScale1.toString())) {
                            saleOrder1 = new SaleOrder();
                            saleOrder1.setBuyId(orders.getMemberId());
                            saleOrder1.setBuyName(orders.getMemberName());
                            saleOrder1.setMemberId(saleMember.getReferrerId());
                            saleOrder1.setMemberName(saleMember.getReferrerName());
                            saleOrder1.setProductId(ordersProduct.getProductId());
                            saleOrder1.setProductName(ordersProduct.getProductName());
                            saleOrder1.setSellerId(orders.getSellerId());
                            saleOrder1.setSellerName(orders.getSellerName());
                            saleOrder1.setOrderId(orders.getId());
                            saleOrder1.setOrderSn(orders.getOrderSn());
                            saleOrder1.setOrdersProductId(ordersProduct.getId());
                            saleOrder1.setOrderTime(orders.getCreateTime());
                            saleOrder1.setMoneyAll(ordersProduct.getMoneyPrice()
                                .multiply(new BigDecimal(ordersProduct.getNumber())));
                            saleOrder1.setMoney(ordersProduct.getMoneyPrice());
                            saleOrder1.setNumber(ordersProduct.getNumber());
                            saleOrder1.setSaleScale(saleScale1);
                            saleOrder1.setSaleState(SaleOrder.SALE_STATE_1);
                            saleOrder1.setSaleType(SaleOrder.SALE_TYPE);
                            saleOrder1.setSaleGrade(SaleOrder.SALE_GRADE_1);
                            saleOrder1.setBackNumber(0);
                            saleOrder1.setBackMoney(new BigDecimal(0));
                            saleOrder1.setBackIds("");
                            saleOrder1.setSaleApplyMoneyId(0);

                            //网单对应的金额/（订单商品总金额-积分优惠金额）*订单优惠金总额=网单优惠金额
                            //(saleOrder1.getMoneyAll().subtract(orders.getMoneyIntegral()))
                            //.divide(orders.getMoneyProduct(), 2, BigDecimal.ROUND_DOWN)
                            //.multiply(orders.getMoneyDiscount())
                            //保留2位小数，后续舍去
                            saleOrder1.setActMoney(
                                (saleOrder1.getMoneyAll().subtract(orders.getMoneyIntegral()))
                                    .divide(orders.getMoneyProduct(), 2, BigDecimal.ROUND_DOWN)
                                    .multiply(orders.getMoneyDiscount()));
                            //(网单总金额-优惠金额)*佣金比例
                            saleOrder1.setSaleMoney(
                                (saleOrder1.getMoneyAll().subtract(saleOrder1.getActMoney()))
                                    .multiply(saleScale1));

                            saleOrderWriteDao.insert(saleOrder1);
                        }

                        //处理二级分销
                        BigDecimal saleScale2 = getSaleScaleByGrade(product, saleScale, 2);
                        if (saleScale2 != null && !"0".equals(saleScale2.toString())
                            && saleMember.getReferrerPid() != null
                            && saleMember.getReferrerPid().intValue() != 0) {
                            saleOrder2 = new SaleOrder();
                            saleOrder2 = new SaleOrder();
                            saleOrder2.setBuyId(orders.getMemberId());
                            saleOrder2.setBuyName(orders.getMemberName());
                            saleOrder2.setMemberId(saleMember.getReferrerPid());
                            saleOrder2.setMemberName(saleMember.getReferrerPname());
                            saleOrder2.setProductId(ordersProduct.getProductId());
                            saleOrder2.setProductName(ordersProduct.getProductName());
                            saleOrder2.setSellerId(orders.getSellerId());
                            saleOrder2.setSellerName(orders.getSellerName());
                            saleOrder2.setOrderId(orders.getId());
                            saleOrder2.setOrderSn(orders.getOrderSn());
                            saleOrder2.setOrdersProductId(ordersProduct.getId());
                            saleOrder2.setOrderTime(orders.getCreateTime());
                            saleOrder2.setMoneyAll(ordersProduct.getMoneyPrice()
                                .multiply(new BigDecimal(ordersProduct.getNumber())));
                            saleOrder2.setMoney(ordersProduct.getMoneyPrice());
                            saleOrder2.setNumber(ordersProduct.getNumber());
                            saleOrder2.setSaleScale(saleScale2);
                            saleOrder2.setSaleState(SaleOrder.SALE_STATE_1);
                            saleOrder2.setSaleType(SaleOrder.SALE_TYPE);
                            saleOrder2.setSaleGrade(SaleOrder.SALE_GRADE_2);
                            saleOrder2.setBackNumber(0);
                            saleOrder2.setBackMoney(new BigDecimal(0));
                            saleOrder2.setBackIds("");
                            saleOrder2.setSaleApplyMoneyId(0);

                            //网单对应的金额/（订单商品总金额-积分优惠金额）*订单优惠金总额=网单优惠金额
                            //(saleOrder2.getMoneyAll().subtract(orders.getMoneyIntegral()))
                            //.divide(orders.getMoneyProduct(), 2, BigDecimal.ROUND_DOWN)
                            //.multiply(orders.getMoneyDiscount())
                            //保留2位小数，后续舍去
                            saleOrder2.setActMoney(
                                (saleOrder2.getMoneyAll().subtract(orders.getMoneyIntegral()))
                                    .divide(orders.getMoneyProduct(), 2, BigDecimal.ROUND_DOWN)
                                    .multiply(orders.getMoneyDiscount()));
                            //(网单总金额-优惠金额)*佣金比例
                            saleOrder2.setSaleMoney(
                                (saleOrder2.getMoneyAll().subtract(saleOrder2.getActMoney()))
                                    .multiply(saleScale2));

                            saleOrderWriteDao.insert(saleOrder2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("根据订单列表保存分销信息，订单ID：" + orders.getId());
                }
            }
        }
    }

    /**
     * 获取商品的佣金比例
     * @param product
     * @param saleScale
     * @param grade 1、一级佣金；2、二级佣金
     * @return
     */
    private BigDecimal getSaleScaleByGrade(Product product, SaleScale saleScale, int grade) {
        if (grade == 1) {
            if (null == product.getSaleScale1()) {
                return saleScale.getSaleScale1();
            } else {
                return product.getSaleScale1();
            }
        } else if (grade == 2) {
            if (null == product.getSaleScale2()) {
                return saleScale.getSaleScale2();
            } else {
                return product.getSaleScale2();
            }
        }

        return new BigDecimal(0);
    }

    /**
     * 分页查询佣金流水
     * @param queryMap
     * @param pager
     * @return
     */
    public List<SaleOrder> getSaleOrders(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;

        if (pager != null) {
            pager.setRowsCount(saleOrderReadDao.getSaleOrdersCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return saleOrderReadDao.getSaleOrders(queryMap, start, size);
    }

    /**
     * 根据用户ID和状态统计数量
     * @param saleState
     * @param memberId
     * @return
     */
    public Integer countSaleOrderBySaleStateAndMemberId(int saleState, Integer memberId) {
        return saleOrderReadDao.countSaleOrderBySaleStateAndMemberId(saleState, memberId);
    }

    /**
     * 根据用户ID和状态统计金额
     * @param saleState
     * @param memberId
     * @return
     */
    public BigDecimal sumSaleOrderBySaleStateAndMemberId(int saleState, Integer memberId) {
        return saleOrderReadDao.sumSaleOrderBySaleStateAndMemberId(saleState, memberId);
    }
}