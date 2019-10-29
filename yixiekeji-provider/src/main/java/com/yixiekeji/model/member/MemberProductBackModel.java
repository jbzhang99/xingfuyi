package com.yixiekeji.model.member;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.coupon.CouponUserReadDao;
import com.yixiekeji.dao.shop.read.member.MemberGradeIntegralLogsReadDao;
import com.yixiekeji.dao.shop.read.member.MemberProductBackReadDao;
import com.yixiekeji.dao.shop.read.member.MemberProductExchangeReadDao;
import com.yixiekeji.dao.shop.read.operate.CourierCompanyReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.write.coupon.CouponOptLogWriteDao;
import com.yixiekeji.dao.shop.write.coupon.CouponUserWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberBalanceLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberGradeIntegralLogsWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberProductBackLogWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberProductBackWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberProductExchangeWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberWriteDao;
import com.yixiekeji.dao.shop.write.order.OrdersProductWriteDao;
import com.yixiekeji.dao.shop.write.order.OrdersWriteDao;
import com.yixiekeji.entity.coupon.CouponOptLog;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalanceLogs;
import com.yixiekeji.entity.member.MemberGradeIntegralLogs;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.member.MemberProductBackLog;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerUser;

/**
 * 会员退货管理
 *
 */
@Component(value = "memberProductBackModel")
public class MemberProductBackModel {
    private static org.slf4j.Logger         log = org.slf4j.LoggerFactory
        .getLogger(MemberProductBackModel.class);

    @Resource
    private MemberProductBackWriteDao       memberProductBackWriteDao;
    @Resource
    private MemberProductBackReadDao        memberProductBackReadDao;
    @Resource
    private MemberProductExchangeReadDao    memberProductExchangeReadDao;
    @Resource
    private MemberProductExchangeWriteDao   memberProductExchangeWriteDao;
    @Resource
    private OrdersReadDao                   ordersReadDao;
    @Resource
    private OrdersWriteDao                  ordersWriteDao;
    @Resource
    private ProductReadDao                  productReadDao;
    @Resource
    private OrdersProductReadDao            ordersProductReadDao;
    @Resource
    private OrdersProductWriteDao           ordersProductWriteDao;
    @Resource
    private MemberWriteDao                  memberWriteDao;
    @Resource
    private MemberBalanceLogsWriteDao       memberBalanceLogsWriteDao;
    @Resource
    private DataSourceTransactionManager    transactionManager;
    @Resource
    private MemberGradeIntegralLogsWriteDao memberGradeIntegralLogsWriteDao;
    @Resource
    private MemberGradeIntegralLogsReadDao  memberGradeIntegralLogsReadDao;
    @Resource
    private CouponUserReadDao               couponUserReadDao;
    @Resource
    private CouponUserWriteDao              couponUserWriteDao;
    @Resource
    private CouponOptLogWriteDao            couponOptLogWriteDao;

    @Resource
    private MemberProductBackLogWriteDao    memberProductBackLogWriteDao;
    @Resource
    private CourierCompanyReadDao           courierCompanyReadDao;

    /**
     * 根据id取得用户退货
     * @param memberProductBackId
     * @return
     */
    public MemberProductBack getMemberProductBackById(Integer memberProductBackId) {
        MemberProductBack memberProductBack = memberProductBackWriteDao.get(memberProductBackId);
        return memberProductBack;
    }

    /**
     * 更新用户退货
     * @param memberProductBack
     * @return
     */
    public boolean updateMemberProductBack(MemberProductBack memberProductBack) {
        return memberProductBackWriteDao.update(memberProductBack) > 0;
    }

    /**
     * 退货逻辑简略说明（详细请参考代码注释）<br>
     * 退货涉及退款主要影响字段：orders.money_paid_balance,orders.money_paid_reality,orders.money_integral,
     *                      orders_product.money_product,orders_product.money_act_single
     * <li>money_paid_balance：订单余额支付的金额
     * <li>money_paid_reality：订单现金支付的金额，退款时现金支付和余额支付部分无区别，都按照余额退到账户余额中
     * <li>money_integral：订单使用的积分转换后的金额，这部分金额只退回积分，不退余额，积分支付部分是在现金支付和余额支付部分金额退完之后再退积分；
     * <li>money_product：网单金额
     * <li>money_act_single：网单单品立减金额
     * 
     * <br>
     * <li>退款原则：
     * <li>1、退到余额的金额不能大于现金支付+余额支付之和，退回的积分不能大于integral值；<br>
     * <li>2、先退现金支付+余额支付部分，再退积分支付部分，最后退优惠券；<br>
     * <li>3、优惠券只有在退到最后一个商品的时候才会退回（因为优惠券不可拆分，所以即使优惠券的金额大于最后一个商品的金额也不会退）；<br>
     * <li>4、退款金额计算不考虑是否参加了满减活动；<br>
     * <li>5、退款金额按照最终支付金额乘以退货商品实际成交金额在整个订单的实际成交金额比例计算；
     * <li>6、运费不退；
     * <br>
     * <br>
     * 避免主从库数据同步延迟所有退货表的数据从写库读取
     * 
     * @param memberProductBack
     * @param request
     * @return
     */
    public boolean saveMemberProductBack(MemberProductBack memberProductBack, Member member) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 参数校验
            if (memberProductBack == null) {
                throw new BusinessException("退货申请不能为空，请重试！");
            } else if (memberProductBack.getProductId() == null
                       || memberProductBack.getProductId() == 0) {
                throw new BusinessException("退货申请产品id不能为空，请重试！");
            } else if (memberProductBack.getOrderProductId() == null
                       || memberProductBack.getOrderProductId() == 0) {
                throw new BusinessException("退货申请网单id不能为空，请重试！");
            } else if (memberProductBack.getOrderId() == null
                       || memberProductBack.getOrderId() == 0) {
                throw new BusinessException("退货申请订单id不能为空，请重试！");
            }
            if (memberProductBack.getNumber() < 1) {
                throw new BusinessException("退货数量必须大于0，请重新填写！");
            }

            // 已经申请过退货的退款金额和
            BigDecimal backedMoney = BigDecimal.ZERO;
            // 已经申请过退货的返回积分和
            Integer backedIntegral = 0;
            // 已经申请过退货的返回积分金额
            BigDecimal backedIntegralMoney = BigDecimal.ZERO;
            // 已经返回的优惠券
            List<Integer> couponUserList = new ArrayList<Integer>();

            // 当前网单下已经退货或者换货的数量（该数量为本次退货的数量 + 已经退货或者换货的数量，不能大于网单的总数量）
            int opServicedNum = memberProductBack.getNumber();
            // 订单下已经退货的数量（判断是否是当前订单下最后一次退货）
            int orderBackedNum = 0;
            // 订单下商品总数量
            int orderNum = 0;
            // 订单下所有网单立减金额和（用于计算退货退款金额）
            BigDecimal singleSum = BigDecimal.ZERO;

            // 获取该订单下已经申请退货的退货表数据
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.clear();
            queryMap.put("memberId", member.getId());
            queryMap.put("orderId", memberProductBack.getOrderId());
            List<MemberProductBack> backedList = memberProductBackWriteDao.queryList(queryMap);
            if (backedList.size() > 0) {
                for (MemberProductBack back : backedList) {
                    // 统计订单下已经退货的数量
                    orderBackedNum += back.getNumber();
                    // 如果网单ID等于本次退货的ID，则统计已经退货或者换货的数量
                    if (memberProductBack.getOrderProductId().equals(back.getOrderProductId())) {
                        opServicedNum += back.getNumber();
                    }
                    // 累计退款金额
                    backedMoney = backedMoney.add(back.getBackMoney());
                    // 累计退回的积分、积分金额
                    if (back.getBackIntegral() != null && back.getBackIntegral() > 0) {
                        backedIntegral = backedIntegral + back.getBackIntegral();
                        backedIntegralMoney = backedIntegralMoney.add(back.getBackIntegralMoney());
                    }
                    // 记录已退还的优惠券
                    if (back.getBackCouponUserId() != null && back.getBackCouponUserId() > 0) {
                        couponUserList.add(back.getBackCouponUserId());
                    }
                }
            }

            // 获取当前退货网单下的换货数量
            Integer exchangeNum = memberProductExchangeWriteDao
                .getExchangeNumByOpId(memberProductBack.getOrderProductId());
            exchangeNum = exchangeNum == null ? 0 : exchangeNum;
            // 当前网单下已经退货或者换货的数量加上换货的数量
            opServicedNum += exchangeNum;

            //根据订单id取对应的订单信息 
            Orders order = ordersReadDao.get(memberProductBack.getOrderId());
            if (order == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请联系管理员！");
            } else {
                int orderState = order.getOrderState();
                if (orderState == Orders.ORDER_STATE_1 || orderState == Orders.ORDER_STATE_2
                    || orderState == Orders.ORDER_STATE_3 || orderState == Orders.ORDER_STATE_4) {
                    log.error("订单此时所处状态不允许提交退货申请。");
                    throw new BusinessException("订单此时所处状态不允许提交退货申请！");
                }
            }
            //获取网单信息
            List<OrdersProduct> opList = ordersProductWriteDao.getByOrderId(order.getId());
            if (opList == null || opList.size() == 0) {
                log.error("根据订单号（" + order.getId() + "）获取网单为空。");
                throw new BusinessException("网单不存在！");
            }
            OrdersProduct ordersProduct = null;
            for (OrdersProduct op : opList) {
                // 统计订单下商品总数量
                orderNum += op.getNumber();
                // 统计所有网单的立减金额和
                singleSum = singleSum.add(op.getMoneyActSingle());
                // 获得当前退货的订单
                if (op.getId().equals(memberProductBack.getOrderProductId())) {
                    ordersProduct = op;
                }
            }
            if (ordersProduct == null) {
                log.error("获取网单(网单号：" + memberProductBack.getOrderProductId() + ")为空。");
                throw new BusinessException("网单不存在！");
            }
            // 网单中已退货或换货数量之和+本次退货的数量
            int opInNum = ordersProduct.getBackNumber() + ordersProduct.getExchangeNumber()
                          + memberProductBack.getNumber();
            // 两个条件如果结果不一致，则表示代码处理退换货数量的地方有BUG
            if (opServicedNum > ordersProduct.getNumber() || opInNum > ordersProduct.getNumber()) {
                log.error(
                    "网单(网单号：" + memberProductBack.getOrderProductId() + ")的退货和换货数量之和超过网单数量了。");
                throw new BusinessException("退货数量超过了可退货数量，请重新填写数量！");
            }

            /*
             * 计算退款金额
             */
            // 订单总退款限制金额（现金+余额-运费），运费不退
            BigDecimal backLimit = order.getMoneyPaidBalance().add(order.getMoneyPaidReality())
                .subtract(order.getMoneyLogistics());
            backLimit = backLimit.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : backLimit;
            // 订单总返回积分限制数量
            Integer integralLimit = order.getIntegral();
            // 订单返回积分金额限制金额
            BigDecimal integralMoneyLimit = order.getMoneyIntegral();

            // 整个订单还能退回的金额：总退款限制金额-已经退款的金额和
            BigDecimal backBalance = backLimit.subtract(backedMoney);
            // 整个订单还能退回的积分：总返回积分限制数量-已经返回积分和
            Integer backIntBalance = integralLimit - backedIntegral;
            // 整个订单还能退回的积分金额：总返回积分金额限制金额-已经退回的积分金额和
            BigDecimal backIntMoneyBalance = integralMoneyLimit.subtract(backedIntegralMoney);

            if (opList.size() == 1 && orderNum == memberProductBack.getNumber().intValue()) {
                /*
                 * 如果整个订单只有一个网单，且商品数量与退货数量相等，则不需要计算比例等
                 */
                // 退款金额就是总退款限制金额
                memberProductBack.setBackMoney(backLimit);
                // 返回积分就是积分限制数量
                memberProductBack.setBackIntegral(integralLimit);
                // 返回积分金额就是积分金额限制金额
                memberProductBack.setBackIntegralMoney(integralMoneyLimit);
                // 返回优惠券为订单记录的优惠券
                memberProductBack.setBackCouponUserId(order.getCouponUserId());
            } else {
                /*
                 * 如果是多个商品且不是全退，计算退货退款金额（解决用户下单后由商家修改订单金额、满减等活动发生的情况）
                 * 退货退款金额 =（订单总退款限制金额 + 积分限制金额）*（（实际成交单价* 退货数量）/实际成交商品总金额））
                 * 实际成交单价 = 原单价 - （单品立减金额总额/数量）
                 * 实际成交商品总金额 = order.moneyProduct - 订单下所有网单的立减金额和(因为order.moneyProduct是没有减去立减金额的钱，所以计算时需要减去立减的优惠)
                 * 因为实际金额只是用来计算比例，所以此处定义的实际金额均为不考虑满减等发生在整个订单上的价格变化（如果后续在网单上有金额变化则需要加上该部分的考虑）
                 */

                // 单个商品的立减金额
                BigDecimal singleInProduct = ordersProduct.getMoneyActSingle().divide(
                    BigDecimal.valueOf(ordersProduct.getNumber()), 2, BigDecimal.ROUND_HALF_UP);
                // 实际成交单价
                BigDecimal priceTrue = ordersProduct.getMoneyPrice().subtract(singleInProduct);
                // 实际成交商品总金额
                BigDecimal moneyProductTrue = order.getMoneyProduct().subtract(singleSum);
                // 比例 = 实际成交单价/实际成交商品总金额（百分百，保留4位小数，为了减小误差，乘以数量的步骤放在这一步）
                BigDecimal scale = (priceTrue
                    .multiply(BigDecimal.valueOf(memberProductBack.getNumber())))
                        .divide(moneyProductTrue, 4, BigDecimal.ROUND_HALF_UP);
                // 所有退货商品的退款金额和
                BigDecimal backMoney = (backLimit.add(integralMoneyLimit)).multiply(scale);
                backMoney = backMoney.setScale(2, BigDecimal.ROUND_HALF_UP);

                if (backMoney.compareTo(backBalance) < 1) {
                    /*
                     * 如果退货退款金额 <= 【整个订单还能退回的金额】，则直接使用退货退款金额作为退款金额
                     */
                    // 此时直接使用退货退款金额作为退款金额
                    memberProductBack.setBackMoney(backMoney);
                    // 返回积分设定0
                    memberProductBack.setBackIntegral(0);
                    // 返回积分金额设定0
                    memberProductBack.setBackIntegralMoney(BigDecimal.ZERO);
                    // 返回优惠券ID设定0
                    memberProductBack.setBackCouponUserId(0);
                } else {
                    /*
                     * 如果退货退款金额 >  【整个订单还能退回的金额】，则使用积分补充，如果是最后一个网单，则优惠券也退回
                     */
                    // 退款金额直接设定成【整个订单还能退回的金额】
                    memberProductBack.setBackMoney(backBalance);
                    // 计算需要积分补充的金额
                    if (orderNum == (orderBackedNum + memberProductBack.getNumber())) {
                        /*
                         * 如果是最后一次退货，直接退回所有积分，积分金额，优惠券
                         */
                        // 返回积分设定【整个订单还能退回的积分】
                        memberProductBack.setBackIntegral(backIntBalance);
                        // 返回积分金额设定【整个订单还能退回的积分金额】
                        memberProductBack.setBackIntegralMoney(backIntMoneyBalance);
                        // 返回优惠券ID设定为订单的优惠券ID
                        if (!couponUserList.contains(order.getCouponUserId())) {
                            // 当前代码版本的逻辑couponUserList中不会出现有数据的情况
                            // 此判断是为防止客户修改优惠券使用逻辑但是没有修改退货逻辑导致一个优惠券多次退还的风险
                            memberProductBack.setBackCouponUserId(order.getCouponUserId());
                        }
                    } else {
                        // 不是最后一次退货，返回优惠券ID设定0
                        memberProductBack.setBackCouponUserId(0);
                        /*
                         * 如果不是最后一次退货则计算返回的积分数，返回优惠券设定成0
                         */
                        // 如果使用了积分则计算，如果没有使用积分则设定0
                        if (order.getIntegral() > 0) {
                            // 倒推下单时积分和金额之间的换算比例
                            int integralScale = order.getIntegral()
                                                / (order.getMoneyIntegral().intValue());
                            // 计算需要补充的积分数量
                            int supplement = ((backMoney.subtract(backBalance))
                                .multiply(new BigDecimal(integralScale))).intValue();
                            // 如果需要补充的数量小于等于【整个订单还能退回的积分】，则积分数量设定成需要补充的数量
                            // 否则设定成【整个订单还能退回的积分】
                            if (supplement <= backIntBalance) {
                                // 返回积分设定成【需要补充的数量】
                                memberProductBack.setBackIntegral(supplement);
                                // 返回积分金额设定设定：【需要补充的数量】/换算比例
                                BigDecimal supplementMoney = (new BigDecimal(supplement)).divide(
                                    new BigDecimal(integralScale), 2, BigDecimal.ROUND_HALF_UP);
                                memberProductBack.setBackIntegralMoney(supplementMoney);
                            } else {
                                // 返回积分设定成【整个订单还能退回的积分】
                                memberProductBack.setBackIntegral(backIntBalance);
                                // 返回积分金额设定【整个订单还能退回的积分金额】
                                memberProductBack.setBackIntegralMoney(backIntMoneyBalance);
                            }
                        } else {
                            // 返回积分设定0
                            memberProductBack.setBackIntegral(0);
                            // 返回积分金额设定0
                            memberProductBack.setBackIntegralMoney(BigDecimal.ZERO);
                        }
                    }
                }
            }

            memberProductBack.setMemberId(member.getId());
            memberProductBack.setMemberName(member.getName());
            memberProductBack.setStateReturn(MemberProductBack.STATE_RETURN_1);
            memberProductBack.setStateMoney(MemberProductBack.STATE_MONEY_1);

            //1、保存信息
            Integer count = memberProductBackWriteDao.save(memberProductBack);
            if (count == 0) {
                throw new BusinessException("产品退货申请保存失败，请重试！");
            }

            Integer pbcount = ordersProductWriteDao.updateBackNumber(ordersProduct.getId(),
                memberProductBack.getNumber());
            if (pbcount == 0) {
                throw new BusinessException("网单退货信息更新失败，请重试！");
            }

            // 保存退货日志信息
            MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
            memberProductBackLog.setOperatingId(member.getId());
            memberProductBackLog.setOperatingName(member.getName());
            memberProductBackLog.setMemberProductBackId(memberProductBack.getId());
            memberProductBackLog.setContent("您已申请退货单");
            Integer insert = memberProductBackLogWriteDao.insert(memberProductBackLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存退货日志失败，请重试！");
            }
            transactionManager.commit(status);
            return count > 0;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    /**
     * 根据登录用户取得用户退货列表
     * @param request
     * @return
     */
    public List<MemberProductBack> getMemberProductBackList(PagerInfo pager, Integer memberId) {

        //取所有的退货申请
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("memberId", memberId);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(memberProductBackReadDao.queryCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        queryMap.put("start", start);
        queryMap.put("size", size);
        List<MemberProductBack> beanList = memberProductBackReadDao.queryList(queryMap);

        return beanList;
    }

    /**
     * 判断是否可以发起退换货申请
     * @param orderId
     * @param orderProductId
     * @param request
     * @return
     * @throws ParseException 
     */
    public Integer canApplyProductBackOrExchange(Integer orderId, Integer orderProductId,
                                                 Integer memberId) throws ParseException {

        // 参数校验
        if (orderProductId == null || orderProductId == 0) {
            throw new BusinessException("网单id不能为空，请重试！");
        } else if (orderId == null || orderId == 0) {
            throw new BusinessException("订单id不能为空，请重试！");
        }

        //根据订单id取对应的订单信息 
        Orders order = ordersReadDao.get(orderId);
        if (order == null) {
            log.error("订单信息获取失败。");
            throw new BusinessException("订单信息获取失败，请联系管理员！");
        } else {
            int orderState = order.getOrderState();
            if (orderState == Orders.ORDER_STATE_1 || orderState == Orders.ORDER_STATE_2
                || orderState == Orders.ORDER_STATE_3 || orderState == Orders.ORDER_STATE_4) {
                throw new BusinessException("订单此时所处状态不允许提交退换货申请。");
            } else {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                // 订单完成时间
                Date finishTime = df.parse(df.format(order.getFinishTime()));
                // 获取当前时间15天之前的时间
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -15);
                String time = TimeUtil.getDateTimeString(calendar.getTime());
                Date before = df.parse(time);

                if (before.getTime() > finishTime.getTime()) {
                    throw new BusinessException("订单完成已超过15天，无法申请退换货。");
                }

            }
        }
        //获取网单
        OrdersProduct ordersProduct = ordersProductReadDao.get(orderProductId);
        if (ordersProduct == null) {
            log.error("网单信息获取失败。");
            throw new BusinessException("网单信息获取失败，请联系管理员！");
        }
        Integer number = ordersProduct.getNumber() - ordersProduct.getBackNumber()
                         - ordersProduct.getExchangeNumber();
        if (number < 0) {
            log.error("网单数量有误。");
            throw new BusinessException("网单信息有误，请联系管理员！");
        }

        return number;
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return memberProductBackReadDao.getCount(queryMap);
    }

    public List<MemberProductBack> page(Map<String, String> queryMap, Integer start, Integer size) {
        List<MemberProductBack> list = memberProductBackReadDao.page(queryMap, start, size);
        for (MemberProductBack back : list) {
            if (back.getBackCouponUserId() != null && back.getBackCouponUserId() > 0) {
                back.setCouponUser(couponUserReadDao.get(back.getBackCouponUserId()));
            }
        }
        return list;
    }

    public boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除用户退货[" + id + "]时出错");
        return memberProductBackWriteDao.del(id) > 0;
    }

    /**
     * 退货退款<br>
     * <li>修改退款状态
     * <li>退款到账户，退积分到账户
     * <li>修改订单退款金额
     * <li>修改用户余额
     * <li>追回送出的积分
     * <li>退回优惠券
     * 
     * @param memberProductBackId
     * @param optId
     * @param optName
     * @return
     */
    public boolean backMoney(Integer memberProductBackId, Integer optId, String optName) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            MemberProductBack backDB = memberProductBackReadDao.get(memberProductBackId);
            if (backDB == null) {
                throw new BusinessException("退款申请信息获取失败，请重试！");
            }
            if (backDB.getStateReturn().intValue() != MemberProductBack.STATE_RETURN_4) {
                throw new BusinessException("只有申请状态是店铺收货的申请才能退款！");
            }
            if (backDB.getStateMoney().intValue() != MemberProductBack.STATE_MONEY_1) {
                throw new BusinessException("该申请已经退款，请勿重复操作！");
            }

            MemberProductBack back = new MemberProductBack();
            back.setId(memberProductBackId);
            // 退货状态-已收货
            back.setStateMoney(MemberProductBack.STATE_MONEY_2);
            back.setBackMoneyTime(new Date());
            Integer updateResult = memberProductBackWriteDao.update(back);
            if (updateResult == 0) {
                throw new BusinessException("退款失败，请重试！");
            }

            Member member = memberWriteDao.get(backDB.getMemberId());

            // 获取订单
            Orders order = ordersReadDao.get(backDB.getOrderId());
            if (order == null) {
                throw new BusinessException("订单信息获取失败，请重试！");
            }

            // 获取网单
            OrdersProduct ordersProduct = ordersProductReadDao.get(backDB.getOrderProductId());
            if (ordersProduct == null) {
                throw new BusinessException("网单信息获取失败，请重试！");
            }

            // 修改订单退款金额
            Integer updateMoneyBack = ordersWriteDao.updateMoneyBack(order.getId(),
                backDB.getBackMoney().toString());
            if (updateMoneyBack == 0) {
                throw new BusinessException("修改订单退款金额时失败，请重试！");
            }

            // 修改用户余额
            Member memberNew = new Member();
            memberNew.setId(member.getId());
            memberNew.setBalance(backDB.getBackMoney());
            Integer updateBalance = memberWriteDao.updateBalance(memberNew);
            if (updateBalance == 0) {
                throw new BusinessException("修改用户余额时失败，请重试！");
            }

            // 变动日志
            MemberBalanceLogs logs = new MemberBalanceLogs();
            logs.setMoneyBefore(member.getBalance());
            logs.setMemberId(member.getId());
            logs.setMemberName(member.getName());
            logs.setMoneyAfter(member.getBalance().add(backDB.getBackMoney()));
            logs.setMoney(backDB.getBackMoney());
            logs.setCreateTime(new Date());
            logs.setState(MemberBalanceLogs.STATE_2);
            logs.setRemark("退货退款，订单号" + order.getOrderSn());
            logs.setOptId(optId);
            logs.setOptName(optName);

            Integer save = memberBalanceLogsWriteDao.save(logs);
            if (save == 0) {
                throw new BusinessException("记录用户余额变更日志时失败，请重试！");
            }

            // 如果有返回积分，则修改用户积分，记录积分变更日志
            // 返回积分分两部分：1、积分支付返回为退回给用户积分（增加）；
            //               2、购物时平台送出的积分追回，为减少用户的积分
            if (backDB.getBackIntegral() != null && backDB.getBackIntegral() > 0) {
                MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
                memberGradeIntegralLogs.setMemberId(backDB.getMemberId());
                memberGradeIntegralLogs.setMemberName(backDB.getMemberName());
                memberGradeIntegralLogs.setValue(backDB.getBackIntegral());
                memberGradeIntegralLogs
                    .setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_8);
                memberGradeIntegralLogs.setOptDes("退货返还积分(订单号:" + order.getOrderSn() + ")");
                memberGradeIntegralLogs.setRefCode(backDB.getOrderProductId().toString());
                memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
                memberGradeIntegralLogs.setCreateTime(new Date());
                Integer saveLog = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
                if (saveLog == 0) {
                    throw new BusinessException("记录用户积分日志失败，请重试！");
                }
            }
            // 计算购物时平台送出的积分追回为减少用户的积分
            MemberGradeIntegralLogs sendIntLog = memberGradeIntegralLogsReadDao
                .getIntLogByMIdAndOrderSnAndOptType(member.getId(),
                    MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_3, order.getOrderSn(),
                    MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
            int subIntegral = 0;
            if (sendIntLog != null && sendIntLog.getValue() > 0) {
                // 追回积分 = 送出积分 *（退款金额/（订单表订单金额 - 订单表积分支付金额））

                // 订单表订单金额 - 订单表积分支付金额
                BigDecimal amount = order.getMoneyOrder().subtract(order.getMoneyIntegral());
                // 比例
                BigDecimal scale = backDB.getBackMoney().divide(amount, 4,
                    BigDecimal.ROUND_HALF_UP);
                // 追回积分，四舍五入保留0位小数
                BigDecimal backIntDec = (new BigDecimal(sendIntLog.getValue())).multiply(scale)
                    .setScale(0, BigDecimal.ROUND_HALF_UP);
                // 追回积分，int数字
                subIntegral = backIntDec.intValue();
                // 不超上限
                subIntegral = subIntegral < sendIntLog.getValue() ? subIntegral
                    : sendIntLog.getValue();

                if (subIntegral > 0) {
                    MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
                    memberGradeIntegralLogs.setMemberId(backDB.getMemberId());
                    memberGradeIntegralLogs.setMemberName(backDB.getMemberName());
                    memberGradeIntegralLogs.setValue(subIntegral);
                    memberGradeIntegralLogs
                        .setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_12);
                    memberGradeIntegralLogs.setOptDes("退货追回积分(订单号:" + order.getOrderSn() + ")");
                    memberGradeIntegralLogs.setRefCode(backDB.getOrderProductId().toString());
                    memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
                    memberGradeIntegralLogs.setCreateTime(new Date());
                    Integer saveLog = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
                    if (saveLog == 0) {
                        throw new BusinessException("记录用户积分日志失败，请重试！");
                    }
                }
            }

            // 计算会员积分变化值
            int backIntegral = backDB.getBackIntegral() == null ? 0 : backDB.getBackIntegral();
            int backFinal = backIntegral - subIntegral;
            if (backFinal != 0) {
                // 积分变化不为0则修改
                memberNew = new Member();
                memberNew.setId(member.getId());
                // 用户的积分改变量=增加的积分 - 减少的积分，如果是负数则表示要减少的积分多，则用户总积分会减少
                memberNew.setIntegral(backFinal);
                Integer updateIntegral = memberWriteDao.updateIntegral(memberNew);
                if (updateIntegral == 0) {
                    throw new BusinessException("修改用户积分时失败，请重试！");
                }
            }

            // 如果有返回优惠券，则修改用户优惠券使用次数，并记录操作日志
            if (backDB.getBackCouponUserId() != null && backDB.getBackCouponUserId() > 0) {
                CouponUser couponUser = couponUserWriteDao.get(backDB.getBackCouponUserId());
                if (couponUser == null) {
                    throw new BusinessException("获取用户优惠券信息时失败，请重试！");
                }
                // 返回优惠券（使用次数加1）
                Integer backCouponUser = couponUserWriteDao.backCouponUser(backDB.getMemberId(),
                    backDB.getBackCouponUserId());
                if (backCouponUser == 0) {
                    throw new BusinessException("返回用户优惠券时失败，请重试！");
                }

                // 记录优惠券操作日志
                CouponOptLog couponOptLog = new CouponOptLog();
                couponOptLog.setCouponUserId(couponUser.getId());
                couponOptLog.setMemberId(couponUser.getMemberId());
                couponOptLog.setSellerId(couponUser.getSellerId());
                couponOptLog.setCouponId(couponUser.getCouponId());
                couponOptLog.setOptType(CouponOptLog.OPT_TYPE_4);
                couponOptLog.setOrderId(order.getId());
                couponOptLog.setCreateUserId(optId);
                couponOptLog.setCreateUserName(optName);
                couponOptLog.setCreateTime(new Date());
                couponOptLogWriteDao.insert(couponOptLog);
            }

            MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
            memberProductBackLog.setMemberProductBackId(memberProductBackId);
            memberProductBackLog.setOperatingId(optId);
            memberProductBackLog.setOperatingName(optName);
            memberProductBackLog.setContent("网站管理员退款");
            Integer insert = memberProductBackLogWriteDao.insert(memberProductBackLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存退货信息日志失败。");
            }
            transactionManager.commit(status);
            return true;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public Integer getSettleBacksCount(Integer sellerId, String startTime, String endTime) {
        return memberProductBackReadDao.getSettleBacksCount(sellerId, startTime, endTime);
    }

    public List<MemberProductBack> getSettleBacks(Integer sellerId, String startTime,
                                                  String endTime, Integer start, Integer size) {
        List<MemberProductBack> settleBacks = memberProductBackReadDao.getSettleBacks(sellerId,
            startTime, endTime, start, size);
        if (settleBacks != null && settleBacks.size() > 0) {
            for (MemberProductBack back : settleBacks) {
                // 设定orderSn和商品名称
                Orders orders = ordersReadDao.get(back.getOrderId());
                back.setOrderSn(orders == null ? "" : orders.getOrderSn());
                Product product = productReadDao.get(back.getProductId());
                back.setProductName(product == null ? "" : product.getName1());
            }
        }
        return settleBacks;
    }

    /**
     * 根据登录用户取得用户退货列表
     * @param pager
     * @param memberId
     * @return
     */
    public List<MemberProductBack> getBackListWithPrdAndOp(PagerInfo pager, Integer memberId) {

        //取所有的退货申请
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("memberId", memberId);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(memberProductBackReadDao.queryCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        queryMap.put("start", start);
        queryMap.put("size", size);
        List<MemberProductBack> beanList = memberProductBackReadDao.queryList(queryMap);

        for (MemberProductBack bean : beanList) {
            //获得对应产品信息
            bean.setProduct(productReadDao.get(bean.getProductId()));
            bean.setOrdersProduct(ordersProductReadDao.get(bean.getOrderProductId()));
            // 如果有返回优惠券则查询
            if (bean.getBackCouponUserId() != null && bean.getBackCouponUserId() > 0) {
                bean.setCouponUser(couponUserReadDao.get(bean.getBackCouponUserId()));
            }
        }
        return beanList;
    }

    /**
     * 审核退货信息
     * @param memberProductBack
     * @return
     */
    public Boolean audit(MemberProductBack memberProductBack) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer updateMemberProductBack = memberProductBackWriteDao.update(memberProductBack);
            if (updateMemberProductBack.intValue() == 0) {
                throw new BusinessException("审核退货信息失败，请重试！");
            }
            MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
            memberProductBackLog.setOperatingId(memberProductBack.getOptId());
            memberProductBackLog.setOperatingName(memberProductBack.getOptName());
            memberProductBackLog.setMemberProductBackId(memberProductBack.getId());
            if (memberProductBack.getStateReturn().intValue() == MemberProductBack.STATE_RETURN_2) {
                memberProductBackLog.setContent("审核退货信息通过");
            } else if (memberProductBack.getStateReturn()
                .intValue() == MemberProductBack.STATE_RETURN_5) {
                memberProductBackLog.setContent("审核退货信息拒绝");
            }
            Integer insert = memberProductBackLogWriteDao.insert(memberProductBackLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存退货信息日志失败，请重试！");
            }
            transactionManager.commit(status);
            return true;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
     * 用户发货
     * @param memberProductBack
     * @param member
     * @return
     */
    public Boolean doBackDeliverGoods(MemberProductBack memberProductBack, Member member) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            MemberProductBack memberProductBackDb = memberProductBackWriteDao
                .get(memberProductBack.getId());
            if (null == memberProductBackDb) {
                throw new BusinessException("获取退货信息失败。");
            }
            if (!memberProductBackDb.getMemberId().equals(member.getId())) {
                throw new BusinessException("您无权操作其他人信息。");
            }
            memberProductBack.setStateReturn(MemberProductBack.STATE_RETURN_3);
            memberProductBack.setOptId(member.getId());
            memberProductBack.setOptName(member.getName());
            CourierCompany courierCompany = courierCompanyReadDao
                .get(memberProductBack.getLogisticsId());
            memberProductBack.setLogisticsName(courierCompany.getCompanyName());
            memberProductBack.setLogisticsMark(courierCompany.getCompanyMark());
            Integer updateMemberProductBack = memberProductBackWriteDao.update(memberProductBack);
            if (updateMemberProductBack.intValue() == 0) {
                throw new BusinessException("用户发货操作失败，请重试");
            }
            MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
            memberProductBackLog.setMemberProductBackId(memberProductBack.getId());
            memberProductBackLog.setOperatingId(member.getId());
            memberProductBackLog.setOperatingName(member.getName());
            memberProductBackLog.setContent("用户发货");
            Integer insert = memberProductBackLogWriteDao.insert(memberProductBackLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存退货信息日志失败。");
            }
            transactionManager.commit(status);
            return true;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
     * 商家收货
     * @param memberProductBack
     * @param user 
     * @return
     */
    public Boolean takeDeliver(MemberProductBack memberProductBack, SellerUser user) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer update = memberProductBackWriteDao.update(memberProductBack);
            if (update.intValue() == 0) {
                throw new BusinessException("店铺收货失败，请重试！");
            }

            MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
            memberProductBackLog.setMemberProductBackId(memberProductBack.getId());
            memberProductBackLog.setOperatingId(user.getId());
            memberProductBackLog.setOperatingName(user.getName());
            memberProductBackLog.setContent("商家收货");
            Integer insert = memberProductBackLogWriteDao.insert(memberProductBackLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存退货信息日志失败。");
            }
            transactionManager.commit(status);
            return true;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
