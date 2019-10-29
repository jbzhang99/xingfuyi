package com.yixiekeji.model.order;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.*;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.coupon.CouponReadDao;
import com.yixiekeji.dao.shop.read.coupon.CouponUserReadDao;
import com.yixiekeji.dao.shop.read.integral.ActIntegralReadDao;
import com.yixiekeji.dao.shop.read.member.MemberGradeIntegralLogsReadDao;
import com.yixiekeji.dao.shop.read.operate.ConfigReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductGoodsReadDao;
import com.yixiekeji.dao.shop.read.product.ProductPictureReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.cart.CartWriteDao;
import com.yixiekeji.dao.shop.write.coupon.CouponOptLogWriteDao;
import com.yixiekeji.dao.shop.write.coupon.CouponUserWriteDao;
import com.yixiekeji.dao.shop.write.full.ActFullLogWriteDao;
import com.yixiekeji.dao.shop.write.integral.ActIntegralWriteDao;
import com.yixiekeji.dao.shop.write.member.*;
import com.yixiekeji.dao.shop.write.order.*;
import com.yixiekeji.dao.shop.write.product.ProductGoodsWriteDao;
import com.yixiekeji.dao.shop.write.product.ProductWriteDao;
import com.yixiekeji.dao.shop.write.seller.SellerWriteDao;
import com.yixiekeji.dao.shop.write.single.ActSingleLogWriteDao;
import com.yixiekeji.dto.OrderDayDto;
import com.yixiekeji.entity.cart.Cart;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.coupon.CouponOptLog;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.full.ActFullLog;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.entity.member.MemberBalanceLogs;
import com.yixiekeji.entity.member.MemberGradeIntegralLogs;
import com.yixiekeji.entity.operate.Config;
import com.yixiekeji.entity.order.*;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.single.ActSingleLog;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.model.cart.CartModel;
import com.yixiekeji.model.member.MemberModel;
import com.yixiekeji.model.seller.SellerTransportModel;
import com.yixiekeji.util.FrontProductPictureUtil;
import com.yixiekeji.vo.cart.CartInfoVO;
import com.yixiekeji.vo.cart.CartListVO;
import com.yixiekeji.vo.order.OrderCommitVO;
import com.yixiekeji.vo.order.OrderCouponVO;
import com.yixiekeji.vo.order.OrderSuccessVO;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Component(value = "ordersModel")
public class OrdersModel {
    private static Logger                   log = LoggerFactory.getLogger(OrdersModel.class);

    @Resource
    private OrdersWriteDao                  ordersWriteDao;
    @Resource
    private OrdersReadDao                   ordersReadDao;
    @Resource
    private DataSourceTransactionManager    transactionManager;
    @Resource
    private ProductWriteDao                 productWriteDao;
    @Resource
    private SellerReadDao                   sellerReadDao;
    @Resource
    private SellerWriteDao                  sellerWriteDao;
    @Resource
    private ProductGoodsWriteDao            productGoodsWriteDao;
    @Resource
    private OrdersProductWriteDao           ordersProductWriteDao;
    @Resource
    private OrderLogWriteDao                orderLogWriteDao;
    @Resource
    private OrdersReadDao                   orderReadDao;
    @Resource
    private OrdersProductReadDao            ordersProductReadDao;
    @Resource
    private MemberWriteDao                  memberWriteDao;
    @Resource
    private MemberAddressWriteDao           memberAddressWriteDao;
    @Resource
    private CartWriteDao                    cartWriteDao;
    @Resource
    private ConfigReadDao                   configReadDao;
    @Resource
    private MemberGradeIntegralLogsWriteDao memberGradeIntegralLogsWriteDao;
    @Resource
    private MemberGradeIntegralLogsReadDao  memberGradeIntegralLogsReadDao;
    @Resource
    private CartModel                       cartModel;
    @Resource
    private MemberBalanceLogsWriteDao       memberBalanceLogsWriteDao;
    @Resource
    private MemberModel                     memberModel;
    @Resource
    private OrderPayLogWriteDao             orderPayLogWriteDao;
    @Resource
    private OrderPayCashLogWriteDao         orderPayCashLogWriteDao;
    @Resource
    private CouponUserReadDao               couponUserReadDao;
    @Resource
    private CouponUserWriteDao              couponUserWriteDao;
    @Resource
    private CouponReadDao                   couponReadDao;
    @Resource
    private CouponOptLogWriteDao            couponOptLogWriteDao;
    @Resource
    private ActFullLogWriteDao              actFullLogWriteDao;
    @Resource
    private ActSingleLogWriteDao            actSingleLogWriteDao;

    @Resource
    private SellerTransportModel            sellerTransportModel;
    @Resource
    private ProductReadDao                  productReadDao;
    @Resource
    private ProductGoodsReadDao             productGoodsReadDao;

    @Resource
    private ActIntegralReadDao              actIntegralReadDao;
    @Resource
    private ActIntegralWriteDao             actIntegralWriteDao;
    @Resource
    private InvoiceWriteDao                 invoiceWriteDao;
    @Resource
    private ProductPictureReadDao           productPictureReadDao;

    /**
     * 根据id取得订单
     * @param  ordersId
     * @return
     */
    public Orders getOrdersById(Integer ordersId) {
        return ordersWriteDao.get(ordersId);
    }

    /**
     * 根据orderSn取得订单
     * @param  orderSn
     * @return
     */
    public Orders getOrdersBySn(String orderSn) {
        return ordersWriteDao.getByOrderSn(orderSn);
    }

    /**
     * 根据订单ID获取订单号
     * @param id
     * @return
     */
    public String getOrderSnById(Integer id) {
        if (id == null) {
            throw new BusinessException("id不能为null");
        }
        Orders orders = ordersWriteDao.get(id);
        if (orders.getOrderSn() == null) {
            throw new BusinessException("没有该订单:" + orders.getId());
        }
        return orders.getOrderSn();
    }

    public Integer getOrdersCount(Map<String, String> queryMap) {
        return ordersReadDao.getOrdersCount(queryMap);
    }

    public List<Orders> getOrders(Map<String, String> queryMap, Integer start,
                                  Integer size) throws Exception {
        Map<String, Object> parammap = new HashMap<String, Object>(queryMap);
        List<Orders> list = ordersReadDao.getOrders(parammap, start, size);
        return list;
    }

    public List<Orders> getOrdersByOrderPsn(String orderPsn) throws Exception {
        return ordersWriteDao.getByOrderPsn(orderPsn);
    }

    public List<Orders> getOrdersByMemberIdAndState(Integer memberId, Integer orderState) throws Exception {
        return ordersWriteDao.getOrdersByMemberIdAndState(memberId, orderState);
    }


    /**
     * 修改订单
     * @param orders 订单
     * @param type 商家操作订单类型
     * @param sellerUser 商家管理员对象
     * @param updateStore 是否需要修改库存
     * @return
     * @throws Exception
     */
    public Integer updateOrdersBySeller(Orders orders, int type,
                                        SellerUser sellerUser) throws Exception {
        Integer result = 0;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //1.更新订单
            result = ordersWriteDao.update(orders);
            Orders ordersDB = ordersWriteDao.get(orders.getId());
            //2.写订单日志
            writeOrderInfo(ordersDB, sellerUser, type);

            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("更新订单时出现未知异常：" + e);
            transactionManager.rollback(status);
            throw e;
        }
        return result;
    }

    /**
     * 商家确认订单
     * @param orderId
     * @param seller
     * @return
     * @throws Exception
     */
    public boolean confirmOrdersBySeller(Integer orderId, SellerUser sellerUser) throws Exception {
        Integer result = 0;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            Orders ordersDb = ordersReadDao.get(orderId);
            if (ordersDb == null) {
                throw new BusinessException("订单信息获取失败，请稍后再试。");
            }
            if (!ordersDb.getOrderState().equals(Orders.ORDER_STATE_2)) {
                throw new BusinessException("只有待确认状态的订单可以执行此操作！");
            }
            if (!ordersDb.getSellerId().equals(sellerUser.getSellerId())) {
                throw new BusinessException("您无权操作该条数据。");
            }

            Orders orders = new Orders();
            orders.setId(orderId);
            //待发货
            orders.setOrderState(Orders.ORDER_STATE_3);

            // 更新订单
            result = ordersWriteDao.update(orders);

            // 写订单日志
            writeOrderInfo(ordersDb, sellerUser, Orders.CONFIRM);

            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("商家确认订单时出现未知异常：" + e);
            transactionManager.rollback(status);
            throw e;
        }
        return result > 0;
    }

    /**
     * 写订单日志
     * @param orders
     * @throws Exception
     */
    private void writeOrderInfo(Orders orders, SellerUser sellerUser, int type) throws Exception {
        OrderLog log = new OrderLog(sellerUser.getId(), sellerUser.getName(), orders.getId(),
                orders.getOrderSn(), "", new Date());
        switch (type) {
            case Orders.DELIVER:
                log.setContent("商家已发货");
                break;
            case Orders.CANCEL:
                log.setContent("商家取消了此订单");
                break;
            case Orders.UPDATE_AMOUNT:
                log.setContent("商家修改订单金额为" + orders.getMoneyOrder());
                break;
            case Orders.SUBMIT_PAY:
                log.setContent("商家已确认收款");
                break;
            case Orders.CONFIRM:
                log.setContent("商家确认订单");
                break;
            default:
                break;
        }
        orderLogWriteDao.save(log);
    }

    /**
     * 修改订单
     * @param orders 订单
     * @param type 商家操作订单类型
     * @param systemAdmin 平台管理员对象
     * @return
     * @throws Exception
     */
    public Integer updateOrdersByAdmin(Orders orders, int type,
                                       SystemAdmin systemAdmin) throws Exception {
        Integer result = 0;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //1.更新订单
            result = ordersWriteDao.update(orders);
            Orders ordersDB = ordersWriteDao.get(orders.getId());
            //2.写订单日志
            writeOrderInfo(ordersDB, systemAdmin, type);
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("更新订单时出现未知异常：" + e);
            transactionManager.rollback(status);
            throw e;
        }
        return result;
    }

    /**
     * 写订单日志
     * @param orders
     * @throws Exception
     */
    private void writeOrderInfo(Orders orders, SystemAdmin systemAdmin, int type) throws Exception {
        OrderLog log = new OrderLog(systemAdmin.getId(), systemAdmin.getName(), orders.getId(),
                orders.getOrderSn(), "", new Date());
        switch (type) {
            case Orders.DELIVER:
                log.setContent("已由平台管理员发货");
                break;
            case Orders.CANCEL:
                log.setContent("平台管理员取消了此订单");
                break;
            case Orders.UPDATE_AMOUNT:
                log.setContent("平台管理员修改订单金额为" + orders.getMoneyOrder());
                break;
            case Orders.SUBMIT_PAY:
                log.setContent("平台管理员已确认收款");
                break;
            case Orders.CONFIRM:
                log.setContent("平台管理员确认订单");
                break;
            default:
                break;
        }
        orderLogWriteDao.save(log);
    }

    /**
     * 根据会员ID，订单状态获取订单数量
     * @param memberId
     * @param orderState
     * @return
     */
    public Integer getOrderNumByMIdAndState(Integer memberId, Integer orderState) {
        return ordersReadDao.getOrderNumByMIdAndState(memberId, orderState);
    }

    /**
     * 根据条件取得订单并封装网单
     * @param queryMap
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    public List<Orders> getOrderWithOrderProduct(Map<String, String> queryMap, Integer start,
                                                 Integer size) throws Exception {
        Map<String, Object> parammap = new HashMap<String, Object>(queryMap);
        List<Orders> list = ordersReadDao.getOrders(parammap, start, size);
        for (Orders orders : list) {

            List<OrdersProduct> orderProductList = null;
            if (orders.getIsParent().intValue() == Orders.IS_PARENT_1) {
                orderProductList = ordersProductWriteDao.getByOrdersPsn(orders.getOrderSn());
            } else {
                orderProductList = ordersProductWriteDao.getByOrderId(orders.getId());
            }

            //记录订单下所有网单可以退换货数量
            int backOrExchangeNum = 0;
            if (orderProductList.size() == 0) {
                log.error("网单信息获取失败。");
                throw new BusinessException("网单信息获取失败，请联系管理员！");
            } else {
                //根据产品id查小图路径
                for (OrdersProduct op : orderProductList) {
                    ProductPicture productPicture = productPictureReadDao
                            .getproductLead(op.getProductId());
                    String productLeadLittle = FrontProductPictureUtil
                            .getproductLeadLittle(productPicture);

                    op.setProductLeadLittle(productLeadLittle);
                    backOrExchangeNum += (op.getNumber() - op.getBackNumber()
                            - op.getExchangeNumber());
                }
                if (backOrExchangeNum < 0) {
                    log.error("订单【" + orders.getOrderSn() + "】下有网单退换货数量有误");
                    throw new BusinessException("订单信息有误，请联系管理员！");
                } else {
                    orders.setBackOrExchangeNum(backOrExchangeNum);
                }
                orders.setOrderProductList(orderProductList);
            }
            // 计算是否显示退货和换货按钮
            Date createDate = orders.getCreateTime();
            long createTime = 0;
            if (createDate != null) {
                createTime = createDate.getTime();
            }
            long newTime = (new Date()).getTime();
            if (newTime - createTime < 15 * 24 * 60 * 60 * 1000) {
                orders.setIsShowBackAndExchange(true);
            } else {
                orders.setIsShowBackAndExchange(false);
            }

        }
        return list;
    }

    /**
     * 根据订单id 取订单、网单及产品图片信息
     * @param request
     * @return
     */
    public Orders getOrderWithOPById(Integer orderId) {
        Orders orders = ordersWriteDao.get(orderId);
        if (orders == null) {
            throw new BusinessException("订单信息获取失败，请重试！");
        }

        //根据订单id查询网单
        List<OrdersProduct> orderProductList = null;

        if (orders.getIsParent().intValue() == Orders.IS_PARENT_1) {
            orderProductList = ordersProductWriteDao.getByOrdersPsn(orders.getOrderSn());
        } else {
            orderProductList = ordersProductWriteDao.getByOrderId(orders.getId());
        }

        if (orderProductList.size() == 0) {
            throw new BusinessException("网单信息获取失败，请联系管理员！");
        }
        //根据产品id查小图路径
        for (OrdersProduct op : orderProductList) {
            ProductPicture productPicture = productPictureReadDao.getproductLead(op.getProductId());
            String productLeadLittle = FrontProductPictureUtil.getproductLeadLittle(productPicture);
            op.setProductLeadLittle(productLeadLittle);
        }
        orders.setOrderProductList(orderProductList);

        return orders;
    }

    /**
     * 用户提交订单<br>
     * 1、判断是否使用余额、判断支付密码<br>
     * 2、按商家拆分订单<br>
     * 3、保存网单<br>
     * 4、清除购物车<br>
     * 5、如果使用余额，并且余额足够支付所有订单，修改支付状态、修改库存<br>
     * @param orderCommitVO
     * @return
     * @throws Exception
     */
    public OrderSuccessVO orderCommit(OrderCommitVO orderCommitVO) throws Exception {

        //参数校验
        if (orderCommitVO == null) {
            log.error("订单提交信息为空。");
            throw new BusinessException("订单提交信息为空，请重试！");
        } else if (orderCommitVO.getAddressId() == null || orderCommitVO.getAddressId() == 0) {
            log.error("订单提交信息中收货地址ID为空。");
            throw new BusinessException("订单提交信息中收货地址ID为空，请重试！");
        } else if (StringUtil.isEmpty(orderCommitVO.getPaymentName())) {
            log.error("订单提交信息中支付方式为空。");
            throw new BusinessException("订单提交信息中支付方式为空，请重试！");
        }

        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // 初始化返回的参数
            orderCommitVO.setSellerCouponMapInfo();
            OrderSuccessVO orderSuccVO = new OrderSuccessVO();
            // 初始默认为订单没有支付，如果余额支付全款则重设为true
            orderSuccVO.setIsPaid(false);
            // 初始默认为跳往支付页面，如果订单是货到付款或者余额全额支付了，设定为false
            orderSuccVO.setGoJumpPayfor(true);
            // 支付方式默认与页面选择的一致，如果余额全额支付后，修改为Orders.PAYMENT_CODE_BALANCE，余额支付
            orderSuccVO.setPaymentCode(orderCommitVO.getPaymentCode());
            orderSuccVO.setPaymentName(orderCommitVO.getPaymentName());

            // 获取地址
            MemberAddress address = memberAddressWriteDao.get(orderCommitVO.getAddressId());
            //新加方法

            Cart cartById = new Cart();
            if(orderCommitVO.getCartId() != 0 && orderCommitVO.getCartId() != null){
                cartById = cartModel.getCartById(orderCommitVO.getCartId());
                boolean b = cartModel.updateCartNumber(orderCommitVO.getCartId(), orderCommitVO.getCounts());
            }

            // 根据登录用户取得购物车信息，数据都从写库获取，数据已经根据商家封装好
            CartInfoVO cartInfoVO = cartModel.getCartInfoByMId(orderCommitVO.getMemberId(), address,
                    orderCommitVO.getSource(), 2);
            // 获取提交订单的用户
            Member member = memberWriteDao.get(orderCommitVO.getMemberId());

            // 如果订单使用积分，判断用户积分是否够填入的积分，是否够转换的最低金额
            Integer integral = orderCommitVO.getIntegral();
            Config config = null;
            if (integral != null && integral > 0) {
                config = configReadDao.get(ConstantsEJS.CONFIG_ID);
                this.checkIntegral(member, config, integral);
            }

            //新加方法
            if(orderCommitVO.getCartId() != 0 && orderCommitVO.getCartId() != null){
                boolean bool = cartModel.updateCartById(cartById.getId(), cartById.getCount());
            }

            // 根据来源判断渠道，默认渠道为PC
            int channel = ConstantsEJS.CHANNEL_2;
            if (orderCommitVO.getSource() != null
                    && (orderCommitVO.getSource().equals(ConstantsEJS.SOURCE_2_H5)
                    || orderCommitVO.getSource().equals(ConstantsEJS.SOURCE_3_ANDROID)
                    || orderCommitVO.getSource().equals(ConstantsEJS.SOURCE_4_IOS))) {
                channel = ConstantsEJS.CHANNEL_3;
            }

            if (cartInfoVO != null && cartInfoVO.getCartListVOs().size() > 0) {

                // 使用商家优惠券信息
                Map<String, OrderCouponVO> sellerCouponMap = orderCommitVO.getSellerCouponMap();
                // 优惠券使用日志信息（用于记录优惠券用户表记录的操作日志）
                List<CouponOptLog> couponOptLogList = new ArrayList<CouponOptLog>();
                // 满减活动参加日志表
                List<ActFullLog> actFullLogList = new ArrayList<ActFullLog>();
                // 单品立减活动参加日志表
                List<ActSingleLog> actSingleLogList = new ArrayList<ActSingleLog>();

                List<Orders> orderList = new ArrayList<Orders>();
                // 获取父订单号
                String orderPsn = RandomUtil.getOrderSn();
                // 循环各个商家，每个商家是一个订单
                for (CartListVO cartListVO : cartInfoVO.getCartListVOs()) {

                    List<Cart> cartList = cartListVO.getCartList();
                    if (cartList != null && cartList.size() > 0) {
                        Seller seller = cartListVO.getSeller();
                        if (seller.getAuditStatus().intValue() != Seller.AUDIT_STATE_2_DONE) {
                            throw new BusinessException(
                                    "商家[" + seller.getSellerName() + "]已被冻结，请把该商家的商品移出购物车后再下单，谢谢！");
                        }
                        // 如果使用了当前商家的优惠券，校验优惠券信息
                        OrderCouponVO orderCouponVO = sellerCouponMap.get(seller.getId());
                        if (orderCouponVO != null) {
                            this.checkSellerCoupon(orderCouponVO, member.getId(), cartListVO,
                                    channel);
                        }
                        // 保存订单及日志信息
                        Orders order = this.saveOrderInfo(orderPsn, cartListVO, member,
                                orderCommitVO, address, orderCouponVO, couponOptLogList,
                                actFullLogList);
                        orderList.add(order);

                        // 保存网单信息
                        this.saveOrderProductInfo(order, cartList, member, actSingleLogList);

                    }
                }

                // 保存总订单
                Orders mainOrder = this.saveMainOrder(orderPsn, orderList, orderCommitVO, member,
                        address);

                //删除购物车数据，不判断删除的成功与否，购物车的数据不影响，只打日志，不抛异常
                int count = cartWriteDao.deleteByMemberId(member.getId());
                if (count == 0) {
                    log.error("删除购物车失败！");
                }

                // 分摊使用的积分
                assignedIntegral(integral, mainOrder, orderList, config, member);

                if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
                    // 只要是货到付款，则不跳转到支付页面
                    orderSuccVO.setGoJumpPayfor(false);
                }

                // 如果订单还没有支付
                // 循环判断订单应支付的金额，如果应支付金额小于等于0则修改订单的状态为支付状态
                if (!orderSuccVO.getIsPaid()) {
                    // 返回true说明修改了支付状态
                    if (this.checkNeedPay(mainOrder, orderList, member)) {
                        orderSuccVO.setIsPaid(true);
                        orderSuccVO.setGoJumpPayfor(false);
                    }
                }

                // 订单操作完毕，操作优惠券、满减、立减的使用和参与日志
                if (couponOptLogList.size() > 0) {
                    // 记录用户优惠券操作日志
                    couponOptLogWriteDao.batchInsertCouponOptLog(couponOptLogList);
                    // 修改用户优惠券可使用次数
                    for (CouponOptLog couponOptLog : couponOptLogList) {
                        couponUserWriteDao.updateCanUse(couponOptLog.getMemberId(),
                                couponOptLog.getCouponUserId(), couponOptLog.getOrderId());
                    }
                }
                // 记录满减活动参与日志
                if (actFullLogList.size() > 0) {
                    actFullLogWriteDao.batchInsertActFullLog(actFullLogList);
                }
                // 记录单品立减参与日志
                if (actSingleLogList.size() > 0) {
                    actSingleLogWriteDao.batchInsertActSingleLog(actSingleLogList);
                }

                //封装返回对象
                orderSuccVO.setMainOrder(mainOrder);
                orderSuccVO.setOrdersList(orderList);
                orderSuccVO.setPayAmount(this.orderMoneyAlls(mainOrder));
            } else {
                throw new BusinessException("购物车中无商品，请选择购买的商品。");
            }
            transactionManager.commit(status);
            return orderSuccVO;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
     * 使用积分校验
     * @param member
     * @param config 系统配置
     * @param integral 使用的积分数量
     */
    private void checkIntegral(Member member, Config config, Integer integral) {
        // 判断用户的积分是否够填入的积分数
        if (integral.compareTo(member.getIntegral()) > 0) {
            throw new BusinessException("积分不够了，请重新填写积分数量！");
        }
        // 至少消耗积分换算比例的积分数量
        if (config == null) {
            throw new BusinessException("积分转换金额失败，请联系系统管理员！");
        }
        if (integral.compareTo(config.getIntegralScale()) < 0) {
            throw new BusinessException("对不起，请至少使用" + config.getIntegralScale() + "个积分！");
        }
    }

    /**
     * 分摊积分到各个订单
     * @param integral 使用的积分
     * @param mainOrder
     * @param orderList
     * @param config
     * @param member
     */
    private void assignedIntegral(Integer integral, Orders mainOrder, List<Orders> orderList,
                                  Config config, Member member) {
        // 计算订单使用积分金额（都计算成整数）
        if (integral != null && integral > 0) {
            // 计算转换总金额
            int moneyIntegral = integral / config.getIntegralScale();
            // 已经分摊的金额
            int moneyShared = 0;
            // 已经分摊的积分
            int integralShared = 0;

            for (int i = 0; i < orderList.size(); i++) {
                Orders order = orderList.get(i);
                // 订单金额为0则不分摊
                if (order.getMoneyOrder().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                if (integralShared >= integral) {
                    // 如果已经分摊的积分大于等于使用的积分则跳出循环
                    break;
                }

                // 用一个新对象来更新数据库
                Orders orderNew = new Orders();
                orderNew.setId(order.getId());

                // 当前订单分摊金额、积分数
                int orderMoneyIntegral = 0;
                int orderIntegral = 0;
                if ((i + 1) == orderList.size()) {
                    // 如果是最后一个订单，按总的数量减去已经分摊的数量
                    orderMoneyIntegral = moneyIntegral - moneyShared;
                    orderIntegral = integral - integralShared;
                    orderNew.setMoneyIntegral(new BigDecimal(orderMoneyIntegral));
                    orderNew.setIntegral(orderIntegral);
                    order.setMoneyIntegral(new BigDecimal(orderMoneyIntegral));
                    order.setIntegral(orderIntegral);
                } else {
                    // 计算分摊比例
                    BigDecimal scale = order.getMoneyOrder().divide(mainOrder.getMoneyOrder(), 2,
                            BigDecimal.ROUND_HALF_UP);
                    // 计算分摊金额
                    BigDecimal decimal = (new BigDecimal(moneyIntegral)).multiply(scale);
                    // 不足一块时按一块计算
                    if (decimal.compareTo(new BigDecimal(1)) < 0) {
                        orderMoneyIntegral = 1;
                    } else {
                        orderMoneyIntegral = decimal.intValue();
                    }
                    if (orderMoneyIntegral > (moneyIntegral - moneyShared)) {
                        // 如果计算出的金额比剩余金额大，则分摊金额=总换算金额-已分摊金额
                        // 防止小金额订单占用1块钱出现后续比例计算后超出的情况
                        orderMoneyIntegral = moneyIntegral - moneyShared;
                    }
                    // 计算分摊积分数
                    orderIntegral = orderMoneyIntegral * config.getIntegralScale();
                    orderNew.setMoneyIntegral(new BigDecimal(orderMoneyIntegral));
                    orderNew.setIntegral(orderIntegral);
                    order.setMoneyIntegral(new BigDecimal(orderMoneyIntegral));
                    order.setIntegral(orderIntegral);
                }
                // 记录已经分摊的金额和积分
                moneyShared += orderMoneyIntegral;
                integralShared += orderIntegral;

                // 当前订单分摊金额、积分数大于0则更改订单的积分转换金额和积分数
                if (orderMoneyIntegral > 0 || orderIntegral > 0) {
                    Integer update = ordersWriteDao.update(orderNew);
                    if (update == 0) {
                        throw new BusinessException("设置订单积分金额时失败，请重试！");
                    }
                    // 记录积分消耗日志
                    MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
                    memberGradeIntegralLogs.setMemberId(member.getId());
                    memberGradeIntegralLogs.setMemberName(member.getName());
                    memberGradeIntegralLogs.setValue(orderIntegral);
                    memberGradeIntegralLogs
                            .setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_7);
                    memberGradeIntegralLogs.setOptDes("积分支付消费(订单号:" + order.getOrderSn() + ")");
                    memberGradeIntegralLogs.setRefCode(order.getOrderSn());
                    memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
                    memberGradeIntegralLogs.setCreateTime(new Date());
                    Integer save = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
                    if (save == 0) {
                        throw new BusinessException("记录用户积分消费日志失败，请重试！");
                    }

                    // 记录积分支付日志
                    OrderPayLog payLog = new OrderPayLog();
                    payLog.setOrdersId(order.getId());
                    payLog.setOrdersSn(order.getOrderSn());
                    payLog.setPaymentCode(Orders.PAYMENT_CODE_INTEGRAL);
                    payLog.setPaymentName(Orders.PAYMENT_NAME_INTEGRAL);
                    payLog.setPayMoney(new BigDecimal(orderMoneyIntegral));
                    payLog.setPayIntegral(orderIntegral);
                    payLog.setMemberId(member.getId());
                    payLog.setMemberName(member.getName());
                    payLog.setCreateTime(new Date());
                    orderPayLogWriteDao.insert(payLog);
                }
            }

            // 用一个新对象来更新数据库（父订单）
            Orders orderNewMain = new Orders();
            orderNewMain.setId(mainOrder.getId());
            orderNewMain.setMoneyIntegral(new BigDecimal(moneyShared));
            orderNewMain.setIntegral(integralShared);
            Integer update = ordersWriteDao.update(orderNewMain);
            if (update == 0) {
                throw new BusinessException("设置订单积分支付金额时失败，请重试！");
            }
            mainOrder.setMoneyIntegral(new BigDecimal(moneyShared));
            mainOrder.setIntegral(integralShared);

            // 记录父订单积分支付日志
            OrderPayLog payLog = new OrderPayLog();
            payLog.setOrdersId(mainOrder.getId());
            payLog.setOrdersSn(mainOrder.getOrderSn());
            payLog.setPaymentCode(Orders.PAYMENT_CODE_INTEGRAL);
            payLog.setPaymentName(Orders.PAYMENT_NAME_INTEGRAL);
            payLog.setPayMoney(orderNewMain.getMoneyIntegral());
            payLog.setPayIntegral(orderNewMain.getIntegral());
            payLog.setMemberId(member.getId());
            payLog.setMemberName(member.getName());
            payLog.setCreateTime(new Date());
            orderPayLogWriteDao.insert(payLog);

            // 修改用户积分数
            Member memberNew = new Member();
            memberNew.setId(member.getId());
            memberNew.setIntegral(0 - integralShared);
            Integer updateIntegral = memberWriteDao.updateIntegral(memberNew);
            if (updateIntegral == 0) {
                throw new BusinessException("扣除用户积分时失败，请重试！");
            }

        }
    }

    /**
     * 订单使用优惠券校验
     * @param orderCouponVO 使用店铺优惠券信息
     * @param memberId 下单用户ID
     * @param cartListVO 订单购物车信息
     * @param channel 渠道
     */
    private void checkSellerCoupon(OrderCouponVO orderCouponVO, Integer memberId,
                                   CartListVO cartListVO, Integer channel) {
        Seller seller = cartListVO.getSeller();
        Integer couponType = orderCouponVO.getCouponType();

        if (couponType != null && couponType > 0) {

            List<CouponUser> couponUserList = couponUserReadDao
                    .getCouponUserByCouponSn(orderCouponVO.getCouponSn(), seller.getId());
            if (couponUserList == null || couponUserList.size() == 0) {
                log.error("获取优惠券【" + orderCouponVO.getCouponSn() + "】信息错误，请检查优惠券序列号、所属店铺是否正确。");
                throw new BusinessException(
                        "获取优惠券【" + orderCouponVO.getCouponSn() + "】信息错误，请检查优惠券序列号、所属店铺是否正确。");
            }
            CouponUser couponUser = couponUserList.get(0);

            if (couponType == OrderCouponVO.COUPON_TYPE_1) {
                // 检查优惠券所属用户
                if (!memberId.equals(couponUser.getMemberId())) {
                    throw new BusinessException(
                            "优惠券【" + orderCouponVO.getCouponSn() + "】不是属于您的优惠券，不能使用。");
                }
            } else if (couponType == OrderCouponVO.COUPON_TYPE_2) {
                // 校验密码
                if (couponUser.getPassword() == null || !couponUser.getPassword()
                        .equals(Md5.getMd5String(orderCouponVO.getCouponPassword()))) {
                    throw new BusinessException(
                            "优惠券【" + orderCouponVO.getCouponSn() + "】密码不对，请重新输入。");
                }
                // 检查优惠券所属用户
                if (couponUser.getMemberId() > 0 && !couponUser.getMemberId().equals(memberId)) {
                    throw new BusinessException(
                            "优惠券【" + orderCouponVO.getCouponSn() + "】不是属于您的优惠券，不能使用。");
                }
            } else {
                throw new BusinessException("优惠券信息错误，请重新输入。");
            }

            // 优惠券可使用次数
            if (couponUser.getCanUse() < 1) {
                throw new BusinessException("优惠券【" + orderCouponVO.getCouponSn() + "】已使用过，不能再次使用。");
            }

            // 优惠券用户关联的优惠券信息校验
            Coupon coupon = couponReadDao.get(couponUser.getCouponId());
            if (coupon == null) {
                log.error("获取优惠券【" + orderCouponVO.getCouponSn() + "】信息错误（couponId="
                        + couponUser.getCouponId() + " ）。");
                throw new BusinessException(
                        "获取优惠券【" + orderCouponVO.getCouponSn() + "】信息错误，请检查优惠券序列号、所属店铺是否正确。");
            }

            // 适用最低金额校验
            if (coupon.getMinAmount()
                    .compareTo(cartListVO.getSellerCheckedDiscountedAmount()) > 0) {
                throw new BusinessException("优惠券【" + orderCouponVO.getCouponSn() + "】最低适用订单金额不得小于"
                        + coupon.getMinAmount() + "元。");
            }
            // 优惠券使用时间校验
            if (coupon.getUseStartTime().after(new Date())) {
                throw new BusinessException("优惠券【" + orderCouponVO.getCouponSn() + "】还没有到可使用时间。");
            }
            if (coupon.getUseEndTime().before(new Date())) {
                throw new BusinessException("优惠券【" + orderCouponVO.getCouponSn() + "】已过期。");
            }

            // 使用渠道校验
            if (coupon.getChannel().intValue() != ConstantsEJS.CHANNEL_1
                    && coupon.getChannel().intValue() != channel.intValue()) {
                String channelStr = channel.intValue() == ConstantsEJS.CHANNEL_2 ? "电脑端" : "移动端";
                throw new BusinessException(
                        "优惠券【" + orderCouponVO.getCouponSn() + "】只能在" + channelStr + "使用。");
            }

            orderCouponVO.setCoupon(coupon);
            orderCouponVO.setCouponUser(couponUser);
        }

    }

    /**
     * 判断订单应支付的金额，如果应支付金额小于等于0则修改订单的状态为支付状态<br>
     * 处理例如使用积分足够支付整个订单金额等的特殊情况
     * @param mainOrder
     * @param orderList
     * @param member
     * @return 不满足修改状态的条件则返回false，否则返回true
     */
    private boolean checkNeedPay(Orders mainOrder, List<Orders> orderList, Member member) {
        // 获取父订单的支付金额
        BigDecimal payMoney = mainOrder.getMoneyOrder().subtract(mainOrder.getMoneyIntegral());
        if (payMoney.compareTo(BigDecimal.ZERO) <= 0) {
            // 如果支付金额小于等于0，则说明订单已经被支付完了

            // 隐藏父订单显示子订单
            // 更新订单付款状态
            Orders newMainOrder = new Orders();
            newMainOrder.setId(mainOrder.getId());
            newMainOrder.setIsShow(Orders.IS_SHOW_0);
            newMainOrder.setOrderState(Orders.ORDER_STATE_3);
            newMainOrder.setPayTime(new Date());
            newMainOrder.setPaymentStatus(Orders.PAYMENT_STATUS_1);
            int updateRow = ordersWriteDao.update(newMainOrder);
            if (updateRow == 0) {
                log.error("修改订单支付状态时失败。");
                throw new BusinessException("下单时发生异常，请重试！");
            }

            for (Orders order : orderList) {

                // 更新订单付款状态
                Orders newOrder = new Orders();
                newOrder.setId(order.getId());
                newMainOrder.setIsShow(Orders.IS_SHOW_1);
                newOrder.setOrderState(Orders.ORDER_STATE_3);
                newOrder.setPayTime(new Date());
                newOrder.setPaymentStatus(Orders.PAYMENT_STATUS_1);

                updateRow = ordersWriteDao.update(newOrder);
                if (updateRow == 0) {
                    log.error("修改订单支付状态时失败。");
                    throw new BusinessException("下单时发生异常，请重试！");
                }

                OrderLog orderLog = new OrderLog();
                orderLog.setContent("您的订单已支付");
                orderLog.setOperatingId(member.getId());
                orderLog.setOrdersId(order.getId());
                orderLog.setOrdersSn(order.getOrderSn());
                orderLog.setOperatingName(member.getName());
                orderLogWriteDao.save(orderLog);
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * 保存商家的订单，以及订单日志<br>
     *
     * @param orderPsn
     * @param cartListVO
     * @param member
     * @param orderCommitVO
     * @param address
     * @param orderCouponVO 使用的优惠券信息
     * @param actFullLogList
     * @param actSingleLogList
     * @return
     */
    private Orders saveOrderInfo(String orderPsn, CartListVO cartListVO, Member member,
                                 OrderCommitVO orderCommitVO, MemberAddress address,
                                 OrderCouponVO orderCouponVO, List<CouponOptLog> couponOptLogList,
                                 List<ActFullLog> actFullLogList) {
        Seller seller = cartListVO.getSeller();
        // 生成订单编号
        String orderSn = RandomUtil.getOrderSn();
        // 生成订单
        Orders order = new Orders();
        order.setOrderSn(orderSn);
        // 设定父订单号
        order.setOrderPsn(orderPsn);
        order.setIsParent(Orders.IS_PARENT_0);
        // 如果是货到付款，则直接显示子订单，父订单不显示
        if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
            order.setIsShow(Orders.IS_SHOW_1);
        } else {
            order.setIsShow(Orders.IS_SHOW_0);
        }

        // 设为普通订单
        order.setOrderType(Orders.ORDER_TYPE_1);
        // 关联订单编号
        order.setRelationOrderSn("");
        order.setSellerId(seller.getId());
        order.setSellerName(seller.getSellerName());
        order.setMemberId(member.getId());
        order.setMemberName(member.getName());
        // 判断发票状态，记录发票信息
        order.setInvoiceStatus(orderCommitVO.getInvoiceStatus());
        if (Orders.INVOICE_STATUS_0 != orderCommitVO.getInvoiceStatus().intValue()) {
            order.setInvoiceTitle(orderCommitVO.getInvoiceTitle());
            order.setInvoiceType(orderCommitVO.getInvoiceType());
            if (Orders.INVOICE_STATUS_1 == orderCommitVO.getInvoiceStatus().intValue()) {
                order.setTaxNum(orderCommitVO.getInvoiceTaxNumber());
            } else {
                order.setTaxNum("");
            }
        }

        order.setIp(orderCommitVO.getIp());
        // 支付信息
        order.setPaymentName(orderCommitVO.getPaymentName());
        order.setPaymentCode(orderCommitVO.getPaymentCode());

        // 收货地址信息设置
        order.setName(address.getMemberName());
        order.setProvinceId(address.getProvinceId());
        order.setCityId(address.getCityId());
        order.setAreaId(address.getAreaId());
        order.setAddressAll(address.getAddAll());
        order.setAddressInfo(address.getAddressInfo());
        order.setMobile(address.getMobile());
        order.setEmail(address.getEmail());
        order.setZipCode(address.getZipCode());

        // 设置订单备注
        order.setRemark(orderCommitVO.getRemark());
        // 在线交易支付流水号
        order.setTradeSn("");
        // 订单来源：1、pc；2、H5；3、Android；4、IOS
        order.setSource(orderCommitVO.getSource());
        // 物流信息
        order.setLogisticsId(0);
        order.setLogisticsName("");
        order.setLogisticsNumber("");

        // 是否货到付款订单，根据payMentCode判断
        if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
            // 是否货到付款订单0、不是；1、是
            order.setIsCodconfim(Orders.IS_CODCONFIM_1);
            // 货到付款状态 0、非货到付款；1、待确认；2、确认通过可以发货；3、订单取消
            order.setCodconfirmState(Orders.CODCONFIRM_STATE_1);
        } else {
            order.setIsCodconfim(Orders.IS_CODCONFIM_0);
            order.setCodconfirmState(Orders.CODCONFIRM_STATE_0);
        }
        order.setCodconfirmId(0);
        order.setCodconfirmName("");
        order.setCodconfirmRemark("");

        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        /**
         * 金额的计算
         */

        // 金额信息

        // 商品总额（只是商品价格*数量的金额之和）
        order.setMoneyProduct(cartListVO.getSellerCheckedAmount());
        // 判断物流费用
        order.setMoneyLogistics(cartListVO.getSellerLogisticsFee());
        // 余额支付金额（此处暂时设定为0，支付之后再修改）
        order.setMoneyPaidBalance(BigDecimal.ZERO);
        // 现金支付金额
        order.setMoneyPaidReality(new BigDecimal(0));

        // 优惠券优惠金额、优惠券ID（coupon_user的ID）
        // 如果使用了优惠券
        if (orderCouponVO != null && orderCouponVO.getCoupon() != null) {
            order.setMoneyCoupon(orderCouponVO.getCoupon().getCouponValue());
            order.setCouponUserId(orderCouponVO.getCouponUser().getId());
        } else {
            order.setMoneyCoupon(new BigDecimal(0));
            order.setCouponUserId(0);
        }
        // 订单满减金额
        if (cartListVO.getOrderDiscount() != null
                && cartListVO.getOrderDiscount().compareTo(BigDecimal.ZERO) > 0) {
            order.setMoneyActFull(cartListVO.getOrderDiscount());
            order.setActFullId(cartListVO.getActFull().getId());
        } else {
            order.setMoneyActFull(BigDecimal.ZERO);
            order.setActFullId(0);
        }
        // 活动id（与订单类型对应），比如团购时存团购的ID，此处为普通订单，存0
        order.setActivityId(0);
        // 优惠金额总额（满减、立减、优惠券和）
        // 如果从购物车中计算出的优惠总额为空，则赋0，然后加上商家优惠券的额度，计算出所有的优惠总额
        BigDecimal moneyDiscount = cartListVO.getSellerCheckedDiscounted() == null ? BigDecimal.ZERO
                : cartListVO.getSellerCheckedDiscounted();
        // 现在是单品立减、订单满减、商家优惠券三个优惠，以后如果加其他优惠政策则加上新优惠的金额
        order.setMoneyDiscount(moneyDiscount.add(order.getMoneyCoupon()));
        // 新订单退款的金额为0
        order.setMoneyBack(new BigDecimal(0));
        // 订单使用积分金额，所有订单入库后再计算该金额
        order.setMoneyIntegral(BigDecimal.ZERO);
        order.setIntegral(0);
        order.setEvaluateState(Orders.EVALUATE_STATE_1);

        // 订单总金额，等于商品总金额＋运费-优惠金额总额（这个金额是最后结算给商家的金额）
        order.setMoneyOrder(((order.getMoneyProduct().add(order.getMoneyLogistics()))
                .subtract(order.getMoneyDiscount())));

        if (order.getMoneyOrder().compareTo(new BigDecimal(0)) <= 0) {
            // 如果订单金额为0 直接认为已付款
            order.setOrderState(Orders.ORDER_STATE_3);
            order.setPayTime(new Date());
            order.setPaymentStatus(Orders.PAYMENT_STATUS_1);
            order.setIsShow(Orders.IS_SHOW_1);
        } else {
            // 其他情况
            if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
                // 如果是货到付款
                order.setOrderState(Orders.ORDER_STATE_2);
                order.setPaymentStatus(Orders.PAYMENT_STATUS_0);
            } else {
                // 如果不是货到付款
                order.setOrderState(Orders.ORDER_STATE_1);
                order.setPaymentStatus(Orders.PAYMENT_STATUS_0);
            }
        }

        // 1、保存订单
        int count = ordersWriteDao.insert(order);
        if (count == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }
        //保存订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setContent("您提交了订单");
        orderLog.setOperatingId(member.getId());
        orderLog.setOrdersId(order.getId());
        orderLog.setOrdersSn(order.getOrderSn());
        orderLog.setOperatingName(member.getName());

        int orderlogCount = orderLogWriteDao.save(orderLog);
        if (orderlogCount == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }

        if (orderCouponVO != null && orderCouponVO.getCoupon() != null) {
            CouponUser couponUser = orderCouponVO.getCouponUser();
            // 设定优惠券使用日志
            CouponOptLog couponOptLog = new CouponOptLog();
            couponOptLog.setCouponUserId(couponUser.getId());
            couponOptLog.setMemberId(member.getId());
            couponOptLog.setSellerId(seller.getId());
            couponOptLog.setCouponId(orderCouponVO.getCoupon().getId());
            couponOptLog.setOptType(CouponOptLog.OPT_TYPE_2);
            couponOptLog.setOrderId(order.getId());
            couponOptLog.setCreateUserId(member.getId());
            couponOptLog.setCreateUserName(member.getName());
            couponOptLog.setCreateTime(new Date());
            couponOptLogList.add(couponOptLog);
        }

        // 订单满减金额
        if (cartListVO.getOrderDiscount() != null
                && cartListVO.getOrderDiscount().compareTo(BigDecimal.ZERO) > 0) {
            // 设定订单满减参与日志
            ActFullLog actFullLog = new ActFullLog();
            actFullLog.setActFullId(cartListVO.getActFull().getId());
            actFullLog.setMemberId(member.getId());
            actFullLog.setSellerId(seller.getId());
            actFullLog.setOrderId(order.getId());
            actFullLog.setCreateUserId(member.getId());
            actFullLog.setCreateUserName(member.getName());
            actFullLog.setCreateTime(new Date());
            actFullLogList.add(actFullLog);
        }

        return order;
    }

    /**
     * 保存总订单，以及订单日志
     * @param orderSn
     * @param childrenOrders
     * @param orderCommitVO
     * @param member
     * @param address
     * @param hasOverseas 是否有海外商品
     * @param couponPlatformOptLog 红包操作日志
     * @return
     */
    private Orders saveMainOrder(String orderSn, List<Orders> childrenOrders,
                                 OrderCommitVO orderCommitVO, Member member,
                                 MemberAddress address) {

        // 生成订单
        Orders order = new Orders();
        // 设定订单号
        order.setOrderSn(orderSn);
        order.setOrderPsn("");
        order.setIsParent(Orders.IS_PARENT_1);
        // 如果是货到付款，则直接显示子订单，父订单不显示
        if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
            order.setIsShow(Orders.IS_SHOW_0);
        } else {
            order.setIsShow(Orders.IS_SHOW_1);
        }

        // 设为普通订单
        order.setOrderType(Orders.ORDER_TYPE_1);
        // 关联订单编号
        order.setRelationOrderSn("");
        order.setSellerId(0);
        order.setSellerName("");
        order.setMemberId(member.getId());
        order.setMemberName(member.getName());
        // 判断发票状态，记录发票信息
        order.setInvoiceStatus(orderCommitVO.getInvoiceStatus());
        if (Orders.INVOICE_STATUS_0 != orderCommitVO.getInvoiceStatus().intValue()) {
            order.setInvoiceTitle(orderCommitVO.getInvoiceTitle());
            order.setInvoiceType(orderCommitVO.getInvoiceType());
            if (Orders.INVOICE_STATUS_1 == orderCommitVO.getInvoiceStatus().intValue()) {
                order.setTaxNum(orderCommitVO.getInvoiceTaxNumber());
            } else {
                order.setTaxNum("");
            }
        }

        order.setIp(orderCommitVO.getIp());
        // 支付信息
        order.setPaymentName(orderCommitVO.getPaymentName());
        order.setPaymentCode(orderCommitVO.getPaymentCode());

        // 收货地址信息设置
        order.setName(address.getMemberName());
        order.setProvinceId(address.getProvinceId());
        order.setCityId(address.getCityId());
        order.setAreaId(address.getAreaId());
        order.setAddressAll(address.getAddAll());
        order.setAddressInfo(address.getAddressInfo());
        order.setMobile(address.getMobile());
        order.setEmail(address.getEmail());
        order.setZipCode(address.getZipCode());

        // 设置订单备注
        order.setRemark(orderCommitVO.getRemark());
        // 在线交易支付流水号
        order.setTradeSn("");
        // 订单来源：1、pc；2、H5；3、Android；4、IOS
        order.setSource(orderCommitVO.getSource());
        // 物流信息
        order.setLogisticsId(0);
        order.setLogisticsName("");
        order.setLogisticsNumber("");

        // 是否货到付款订单，根据payMentCode判断
        if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
            // 是否货到付款订单0、不是；1、是
            order.setIsCodconfim(Orders.IS_CODCONFIM_1);
            // 货到付款状态 0、非货到付款；1、待确认；2、确认通过可以发货；3、订单取消
            order.setCodconfirmState(Orders.CODCONFIRM_STATE_1);
        } else {
            order.setIsCodconfim(Orders.IS_CODCONFIM_0);
            order.setCodconfirmState(Orders.CODCONFIRM_STATE_0);
        }
        order.setCodconfirmId(0);
        order.setCodconfirmName("");
        order.setCodconfirmRemark("");

        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        /**
         * 金额的计算
         */
        BigDecimal MoneyProduct = BigDecimal.ZERO;
        BigDecimal MoneyLogistics = BigDecimal.ZERO;
        BigDecimal MoneyPaidBalance = BigDecimal.ZERO;
        BigDecimal MoneyPaidReality = BigDecimal.ZERO;
        BigDecimal MoneyCoupon = BigDecimal.ZERO;
        BigDecimal MoneyActFull = BigDecimal.ZERO;
        BigDecimal MoneyDiscount = BigDecimal.ZERO;
        BigDecimal MoneyBack = BigDecimal.ZERO;
        BigDecimal MoneyIntegral = BigDecimal.ZERO;
        BigDecimal MoneyOrder = BigDecimal.ZERO;
        Integer integral = 0;
        for (Orders child : childrenOrders) {
            MoneyProduct = MoneyProduct.add(child.getMoneyProduct());
            MoneyLogistics = MoneyLogistics.add(child.getMoneyLogistics());
            MoneyPaidBalance = MoneyPaidBalance.add(child.getMoneyPaidBalance());
            MoneyPaidReality = MoneyPaidReality.add(child.getMoneyPaidReality());
            MoneyCoupon = MoneyCoupon.add(child.getMoneyCoupon());
            MoneyActFull = MoneyActFull.add(child.getMoneyActFull());
            MoneyDiscount = MoneyDiscount.add(child.getMoneyDiscount());
            MoneyBack = MoneyBack.add(child.getMoneyBack());
            MoneyIntegral = MoneyIntegral.add(child.getMoneyIntegral());
            MoneyOrder = MoneyOrder.add(child.getMoneyOrder());
            integral = integral + child.getIntegral();
        }

        // 订单金额＝各个sku总和
        order.setMoneyProduct(MoneyProduct);
        order.setMoneyLogistics(MoneyLogistics);
        order.setMoneyPaidBalance(MoneyPaidBalance);
        order.setMoneyPaidReality(MoneyPaidReality);
        order.setMoneyOrder(MoneyOrder);

        order.setMoneyCoupon(MoneyCoupon);
        order.setCouponUserId(0);

        order.setMoneyActFull(MoneyActFull);
        order.setActFullId(0);

        order.setMoneyDiscount(MoneyDiscount);
        order.setMoneyBack(MoneyBack);
        order.setMoneyIntegral(MoneyIntegral);
        order.setIntegral(integral);

        // 活动id（与订单类型对应），比如团购时存团购的ID，此处为普通订单，存0
        order.setActivityId(0);
        order.setEvaluateState(Orders.EVALUATE_STATE_1);

        if (order.getMoneyOrder().compareTo(new BigDecimal(0)) <= 0) {
            // 如果订单金额为0 直接认为已付款
            order.setOrderState(Orders.ORDER_STATE_3);
            order.setPayTime(new Date());
            order.setPaymentStatus(Orders.PAYMENT_STATUS_1);
        } else {
            // 其他情况
            if (Orders.PAYMENT_CODE_OFFLINE.equals(orderCommitVO.getPaymentCode())) {
                // 如果是货到付款
                order.setOrderState(Orders.ORDER_STATE_2);
                order.setPaymentStatus(Orders.PAYMENT_STATUS_0);
            } else {
                // 如果不是货到付款
                order.setOrderState(Orders.ORDER_STATE_1);
                order.setPaymentStatus(Orders.PAYMENT_STATUS_0);
            }
        }

        // 1、保存订单
        int count = ordersWriteDao.insert(order);
        if (count == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }
        //保存订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setContent("您提交了订单");
        orderLog.setOperatingId(member.getId());
        orderLog.setOrdersId(order.getId());
        orderLog.setOrdersSn(order.getOrderSn());
        orderLog.setOperatingName(member.getName());

        int orderlogCount = orderLogWriteDao.save(orderLog);
        if (orderlogCount == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }

        return order;
    }

    /**
     * 保存网单信息
     * @param order
     * @param cartList
     */
    private void saveOrderProductInfo(Orders order, List<Cart> cartList, Member member,
                                      List<ActSingleLog> actSingleLogList) {
        for (Cart cart : cartList) {
            ProductGoods goods = cart.getProductGoods();
            Product product = cart.getProduct();
            // 判断商品状态
            if (product.getState().intValue() != Product.STATE_6) {
                throw new BusinessException("商品[" + product.getName1() + "]已下架，请重新选择商品！");
            }
            // 判断分类状态
            if (product.getProductCateState().intValue() != Product.PRODUCT_CATE_STATE_1) {
                throw new BusinessException("商品[" + product.getName1() + "]已下架，请重新选择商品！");
            }
            // 判断库存
            if (goods.getProductStock() <= 0 || goods.getProductStock() < cart.getCount()) {
                throw new BusinessException(
                        "商品[" + product.getName1() + " " + cart.getSpecInfo() + "]的库存不足，请重新选择商品！");
            }
            // 货品状态异常或未启用的，不能下单
            if (goods.getState() == null || goods.getState().intValue() == ProductGoods.DISABLE) {
                String productName = product.getName1();
                if (!StringUtil.isEmpty(goods.getNormName(), true)) {
                    productName += " " + goods.getNormName();
                }
                throw new BusinessException("商品[" + productName + "]已下架，不能下单！");
            }

            //保存网单信息
            OrdersProduct op = new OrdersProduct();
            op.setOrdersId(order.getId());
            op.setOrdersSn(order.getOrderSn());
            op.setOrdersPsn(order.getOrderPsn());
            op.setSellerId(cart.getSellerId());
            op.setSellerName(order.getSellerName());
            op.setProductCateId(product.getProductCateId());
            op.setProductId(product.getId());
            op.setProductGoodsId(cart.getProductGoodsId());
            op.setSpecInfo(cart.getSpecInfo());
            op.setProductName(product.getName1());
            op.setProductSku(goods.getSku());
            op.setPackageGroupsId(0);
            op.setMallGroupsId(0);
            op.setGiftId(0);
            op.setIsGift(OrdersProduct.IS_GIFT_0);
            // 根据来源确定使用PC价格或者移动端价格，默认使用PC端价格
            BigDecimal price = goods.getMallPcPrice();
            if (order.getSource() != null
                    && (order.getSource().equals(ConstantsEJS.SOURCE_2_H5)
                    || order.getSource().equals(ConstantsEJS.SOURCE_3_ANDROID)
                    || order.getSource().equals(ConstantsEJS.SOURCE_4_IOS))) {
                price = goods.getMallMobilePrice();
            }
            op.setMoneyPrice(price);
            op.setNumber(cart.getCount());
            // 网单金额（减去立减优惠后的金额和）
            op.setMoneyAmount(cart.getCurrDiscountedAmount());
            // 立减优惠金额和
            if (cart.getCurrDiscounted() != null
                    && cart.getCurrDiscounted().compareTo(BigDecimal.ZERO) > 0) {
                op.setMoneyActSingle(cart.getCurrDiscounted());
                op.setActSingleId(cart.getActSingle().getId());
            } else {
                op.setMoneyActSingle(BigDecimal.ZERO);
                op.setActSingleId(0);
            }
            op.setActGroupId(0);
            op.setActFlashSaleId(0);
            op.setActFlashSaleProductId(0);
            op.setActBiddingId(0);
            op.setActIntegralId(0);
            op.setActIntegralNum(0);
            op.setActIntegralMoney(BigDecimal.ZERO);
            op.setBackNumber(0);
            op.setExchangeNumber(0);
            op.setIsEvaluate(OrdersProduct.IS_EVALUATE_0);

            // 1、保存网单
            int count = ordersProductWriteDao.insert(op);
            if (count == 0) {
                throw new BusinessException("网单保存失败，请重试！");
            }
            // 修改库存和销量
            this.updateProductActualSalesAndStock(cart.getProductId(), cart.getProductGoodsId(),
                    cart.getCount(), false);

            // 设定网单的立减活动参与日志
            if (cart.getCurrDiscounted() != null
                    && cart.getCurrDiscounted().compareTo(BigDecimal.ZERO) > 0) {
                ActSingleLog actSingleLog = new ActSingleLog();
                actSingleLog.setActSingleId(cart.getActSingle().getId());
                actSingleLog.setMemberId(member.getId());
                actSingleLog.setSellerId(cart.getSellerId());
                actSingleLog.setOrderId(order.getId());
                actSingleLog.setOrderProductId(op.getId());
                actSingleLog.setProductId(product.getId());
                actSingleLog.setCreateUserId(member.getId());
                actSingleLog.setCreateUserName(member.getName());
                actSingleLog.setCreateTime(new Date());
                actSingleLogList.add(actSingleLog);
            }
        }
    }

    /**
     * 确认收货
     * @param Member member
     * @param ordersId
     * @return
     * @throws Exception
     */
    public boolean goodsReceipt(Member member, Integer ordersId) throws Exception {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            //参数校验
            if (ordersId == null || ordersId == 0) {
                log.error("订单ID为空。");
                throw new BusinessException("订单ID为空，请重试！");
            }

            //获取订单
            Orders orders = ordersWriteDao.get(ordersId);
            if (orders == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请重试！");
            } else if (!orders.getOrderState().equals(Orders.ORDER_STATE_4)) {
                log.error("订单不处于已发货状态，不能确认收货。");
                throw new BusinessException("订单不处于已发货状态，不能确认收货！");
            }

            orders.setOrderState(Orders.ORDER_STATE_5);
            orders.setFinishTime(new Date());
            int count = ordersWriteDao.update(orders);
            if (count == 0) {
                log.error("订单更新失败。");
                throw new BusinessException("订单更新失败！");
            }

            OrderLog orderLog = new OrderLog(member.getId(), member.getName(), orders.getId(),
                    orders.getOrderSn(), "您已签收订单", new Date());

            int logCount = orderLogWriteDao.save(orderLog);
            if (logCount == 0) {
                throw new BusinessException("订单日志保存失败，请重试！");
            }

            transactionManager.commit(status);

            return true;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("[OrderService][goodsReceipt]订单确认收货时发生异常:", e);
            throw e;
        }
    }

    /**
     * 统计每天订单量
     * @param queryMap
     * @return
     */
    public List<OrderDayDto> getOrderDayDto(Map<String, String> queryMap) {
        return ordersReadDao.getOrderDayDto(queryMap);
    }

    public boolean jobSystemFinishOrder() {

        // 获取当前时间15天之前的时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -15);

        String deliverTime = TimeUtil.getDateTimeString(calendar.getTime());

        //获取发货时间超过15天的订单
        List<Orders> ordersList = ordersReadDao.getUnfinishedOrders(deliverTime);

        if (ordersList != null && ordersList.size() > 0) {
            // 单条数据处理异常不影响其他数据执行
            for (Orders orders : ordersList) {
                // 事务管理
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    Orders orderNew = new Orders();
                    orderNew.setId(orders.getId());
                    orderNew.setOrderState(Orders.ORDER_STATE_5);
                    orderNew.setFinishTime(new Date());

                    Integer update = ordersWriteDao.update(orderNew);
                    if (update == 0) {
                        throw new BusinessException("系统自动完成订单时失败。");
                    }

                    OrderLog log = new OrderLog(0, "system", orders.getId(), orders.getOrderSn(),
                            "系统自动完成订单", new Date());

                    int orderlogCount = orderLogWriteDao.save(log);
                    if (orderlogCount == 0) {
                        throw new BusinessException("系统自动完成订单，订单日志保存失败，请重试！");
                    }

                    transactionManager.commit(status);
                } catch (Exception e) {
                    transactionManager.rollback(status);
                    log.error("[OrderModel][jobSystemFinishOrder]系统自动完成订单时发生异常:", e);
                    log.error(
                            "[OrderModel][jobSystemFinishOrder]发生异常的订单：" + JSON.toJSONString(orders));
                }
            }
        }

        return true;
    }

    /**
     * 订单支付前处理<br>
     * <li>支付状态校验等
     * @param mainOrder
     * @param ordersList
     */
    private void payBefore(Orders mainOrder, List<Orders> ordersList) {

        // 校验
        //只有订单状态为 1（未付款的订单）   买家付款状态 payment_status 为未付款，才能支付
        Integer orderState = mainOrder.getOrderState();//订单状态
        Integer paymentStatus = mainOrder.getPaymentStatus();//买家支付状态
        String paymentCode = mainOrder.getPaymentCode();//支付类型

        if (!paymentCode.equals(Orders.PAYMENT_CODE_ONLINE)) {
            log.error("订单" + mainOrder.getOrderSn() + "不是在线支付订单，不能进行在线支付！");
            throw new BusinessException("订单" + mainOrder.getOrderSn() + "不是在线支付订单，不能进行在线支付！");
        }

        if (orderState.intValue() != Orders.ORDER_STATE_1) {
            log.error("订单" + mainOrder.getOrderSn() + "不是未付款状态，请选择未支付的订单！");
            throw new BusinessException("订单" + mainOrder.getOrderSn() + "不是未付款状态，请选择未支付的订单！");
        }

        if (paymentStatus.intValue() != Orders.PAYMENT_STATUS_0) {
            log.error("订单" + mainOrder.getOrderSn() + "已经支付过了，请选择未支付的订单！");
            throw new BusinessException("订单" + mainOrder.getOrderSn() + "已经支付过了，请选择未支付的订单！");
        }

        for (Orders orders : ordersList) {

            // 支付前校验库存，从写库读网单防止由于网络原因导致的延迟
            List<OrdersProduct> opList = ordersProductWriteDao.getByOrderId(orders.getId());

            if (orders.getOrderType().intValue() == Orders.ORDER_TYPE_6) {
                // 积分换购订单校验
            } else {
                // 其他订单
            }
        }

    }

    /**
     * 订单支付后处理<br>
     * <li>修改订单支付状态
     * <li>记录订单日志
     * <li>记录订单支付日志
     *
     * @param mainOrder
     * @param ordersList
     * @param paidMoney 现金支付的金额
     * @param member
     * @param tradeSn 支付流水号
     * @param tradeContent 支付返回信息
     * @param paymentCode 支付方式code
     * @param paymentName 支付方式名称
     * @param isPaidBefore 是否是支付前调用（余额支付所有订单时会调用）
     */
    private void payAfter(Orders mainOrder, List<Orders> ordersList, BigDecimal paidMoney,
                          Member member, String tradeSn, String tradeContent, String paymentCode,
                          String paymentName, boolean isPaidBefore) {

        // 订单号集合
        String payOrderSn = "";
        BigDecimal allPaidMoney = paidMoney;

        // 用父订单的金额判断是否够支付整个订单
        BigDecimal mainNeedPay = mainOrder.getMoneyOrder().subtract(mainOrder.getMoneyIntegral())
                .subtract(mainOrder.getMoneyPaidBalance()).subtract(mainOrder.getMoneyPaidReality());
        // 需要支付的金额减去本次支付的金额
        BigDecimal subtracted = mainNeedPay.subtract(paidMoney);
        // 如果本次支付后父订单需要支付的金额小于等于0，则说明整个订单已经支付完成，父订单隐藏，子订单显示
        int allPaid = 0;
        if (subtracted.compareTo(BigDecimal.ZERO) <= 0) {
            allPaid = 1;
        }

        for (int i = 0; i < ordersList.size(); i++) {
            Orders ordersDb = ordersList.get(i);

            if (i > 0) {
                // 从第二个订单开始先加逗号再加订单号
                payOrderSn += ",";
            }
            payOrderSn += ordersDb.getOrderSn();

            // 修改订单支付状态
            Orders orders = new Orders();
            orders.setId(ordersDb.getId());

            // 计算余额账户支付总金额、现金支付金额
            // 订单应该支付的金额：订单金额-积分换算金额-余额支付金额-现金支付金额
            BigDecimal payMoney = ordersDb.getMoneyOrder().subtract(ordersDb.getMoneyIntegral())
                    .subtract(ordersDb.getMoneyPaidBalance()).subtract(ordersDb.getMoneyPaidReality());
            // 订单实际分配的支付金额
            BigDecimal actualPaid = BigDecimal.ZERO;
            if (payMoney.compareTo(BigDecimal.ZERO) <= 0) {
                // 如果应付金额小于等于0
                // 不计算金额
            } else {
                // 订单循环分配现金支付的金额
                if ((i + 1) == ordersList.size()) {
                    // 如果是最后一个订单，则记录剩下的金额
                    orders.setMoneyPaidReality(ordersDb.getMoneyPaidReality().add(paidMoney));
                    actualPaid = paidMoney;
                } else {
                    if (paidMoney.compareTo(BigDecimal.ZERO) > 0) {
                        if (paidMoney.compareTo(payMoney) >= 0) {
                            // 支付的金额大于等于订单待支付金额
                            orders
                                    .setMoneyPaidReality(ordersDb.getMoneyPaidReality().add(payMoney));
                            actualPaid = payMoney;
                        } else {
                            orders
                                    .setMoneyPaidReality(ordersDb.getMoneyPaidReality().add(paidMoney));
                            actualPaid = paidMoney;
                        }
                    }
                }
                // 现金支付金额减少
                paidMoney = paidMoney.subtract(actualPaid);
            }

            // 修改金额后应支付金额，如果该金额小于等于0，则支付完成，修改状态，记录支付日志，占库存
            // DB订单金额-DB积分支付金额-DB余额支付金额-NEW现金支付金额
            BigDecimal needPay = ordersDb.getMoneyOrder().subtract(ordersDb.getMoneyIntegral())
                    .subtract(ordersDb.getMoneyPaidBalance())
                    .subtract(ordersDb.getMoneyPaidReality().add(actualPaid));
            if (needPay.compareTo(BigDecimal.ZERO) <= 0) {
                // 支付完成
                if (ordersDb.getOrderType().intValue() == Orders.ORDER_TYPE_4) {
                    // 如果是竞价定金订单，支付完成即设置订单状态为已完成（因为不需要发货）
                    orders.setOrderState(Orders.ORDER_STATE_5);
                } else {
                    orders.setOrderState(Orders.ORDER_STATE_3);
                }
                if (allPaid == 1) {
                    // 订单全部支付，则所有子订单都改为显示，否则子订单都不显示
                    orders.setIsShow(Orders.IS_SHOW_1);
                }
                orders.setPayTime(new Date());
                orders.setPaymentStatus(Orders.PAYMENT_STATUS_1);
                orders.setPaymentCode(paymentCode);
                orders.setPaymentName(paymentName);
                orders.setTradeSn(tradeSn);

                Integer update = ordersWriteDao.update(orders);
                if (update == 0) {
                    throw new BusinessException("修改订单状态时失败！");
                }

                // 记录订单日志
                OrderLog orderLog = new OrderLog(member.getId(), member.getName(), ordersDb.getId(),
                        ordersDb.getOrderSn(), "订单完成支付", new Date());
                Integer save = orderLogWriteDao.save(orderLog);
                if (save == 0) {
                    throw new BusinessException("记录订单日志时失败！");
                }

                // orderPayBefore中调用时不记录日志，因为在扣除余额时已经记录过了
                // 只有在payAfter中调用且实际支付金额大于0时才记录此日志
                if (!isPaidBefore && (actualPaid.compareTo(BigDecimal.ZERO) > 0)) {
                    // 记录订单支付日志
                    OrderPayLog payLog = new OrderPayLog();
                    payLog.setOrdersId(ordersDb.getId());
                    payLog.setOrdersSn(ordersDb.getOrderSn());
                    payLog.setPaymentCode(paymentCode);
                    payLog.setPaymentName(paymentName);
                    payLog.setPayMoney(actualPaid);
                    payLog.setPayIntegral(0);
                    payLog.setPaySn(mainOrder.getOrderSn());
                    payLog.setTradeSn(tradeSn);
                    payLog.setMemberId(member.getId());
                    payLog.setMemberName(member.getName());
                    payLog.setCreateTime(new Date());
                    orderPayLogWriteDao.insert(payLog);
                }

                // 购物送积分，出错时吞掉异常不影响支付
                if (allPaid == 1) {
                    // 只有当所有子订单都支付之后才送积分，否则不送
                    try {
                        memberModel.memberOrderSendValueNoTrans(member.getId(), member.getName(),
                                ordersDb.getId());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            } else {
                // 订单支付的总金额不够订单金额，表示订单未支付完成，商城现有逻辑正常不会进入此逻辑
                // 记录部分支付信息
                orders.setTradeSn(tradeSn);
                Integer update = ordersWriteDao.update(orders);
                if (update == 0) {
                    throw new BusinessException("修改订单时失败！");
                }

                // 记录订单日志
                OrderLog orderLog = new OrderLog(member.getId(), member.getName(), ordersDb.getId(),
                        ordersDb.getOrderSn(), "订单部分支付", new Date());
                Integer save = orderLogWriteDao.save(orderLog);
                if (save == 0) {
                    throw new BusinessException("记录订单日志时失败！");
                }

                // orderPayBefore中调用时不记录日志，因为在扣除余额时已经记录过了
                // 只有在payAfter中调用且实际支付金额大于0时才记录此日志
                if (!isPaidBefore && (actualPaid.compareTo(BigDecimal.ZERO) > 0)) {
                    // 记录订单支付日志
                    OrderPayLog payLog = new OrderPayLog();
                    payLog.setOrdersId(ordersDb.getId());
                    payLog.setOrdersSn(ordersDb.getOrderSn());
                    payLog.setPaymentCode(paymentCode);
                    payLog.setPaymentName(paymentName);
                    payLog.setPayMoney(actualPaid);
                    payLog.setPayIntegral(0);
                    payLog.setPaySn(mainOrder.getOrderSn());
                    payLog.setTradeSn(tradeSn);
                    payLog.setMemberId(member.getId());
                    payLog.setMemberName(member.getName());
                    payLog.setCreateTime(new Date());
                    orderPayLogWriteDao.insert(payLog);
                }
            }
        }

        // 修改总订单的状态
        if (allPaid == 1) {
            Orders newMainOrder = new Orders();
            newMainOrder.setId(mainOrder.getId());
            newMainOrder.setMoneyPaidReality(mainOrder.getMoneyPaidReality().add(allPaidMoney));
            // 支付完成
            if (mainOrder.getOrderType().intValue() == Orders.ORDER_TYPE_4) {
                // 如果是竞价定金订单，支付完成即设置订单状态为已完成（因为不需要发货）
                newMainOrder.setOrderState(Orders.ORDER_STATE_5);
            } else {
                newMainOrder.setOrderState(Orders.ORDER_STATE_3);
            }
            newMainOrder.setIsShow(Orders.IS_SHOW_0);
            newMainOrder.setPayTime(new Date());
            newMainOrder.setPaymentStatus(Orders.PAYMENT_STATUS_1);
            newMainOrder.setPaymentCode(paymentCode);
            newMainOrder.setPaymentName(paymentName);
            newMainOrder.setTradeSn(tradeSn);

            Integer update = ordersWriteDao.update(newMainOrder);
            if (update == 0) {
                throw new BusinessException("修改订单状态时失败！");
            }

            // 记录订单日志
            OrderLog orderLog = new OrderLog(member.getId(), member.getName(), mainOrder.getId(),
                    mainOrder.getOrderSn(), "订单完成支付", new Date());
            Integer save = orderLogWriteDao.save(orderLog);
            if (save == 0) {
                throw new BusinessException("记录订单日志时失败！");
            }

            // orderPayBefore中调用时不记录日志，因为在扣除余额时已经记录过了
            // 只有在payAfter中调用且实际支付金额大于0时才记录此日志
            if (!isPaidBefore && (allPaidMoney.compareTo(BigDecimal.ZERO) > 0)) {
                // 记录订单支付日志
                OrderPayLog payLog = new OrderPayLog();
                payLog.setOrdersId(mainOrder.getId());
                payLog.setOrdersSn(mainOrder.getOrderSn());
                payLog.setPaymentCode(paymentCode);
                payLog.setPaymentName(paymentName);
                payLog.setPayMoney(allPaidMoney);
                payLog.setPayIntegral(0);
                payLog.setPaySn(mainOrder.getOrderSn());
                payLog.setTradeSn(tradeSn);
                payLog.setMemberId(member.getId());
                payLog.setMemberName(member.getName());
                payLog.setCreateTime(new Date());
                orderPayLogWriteDao.insert(payLog);
            }

        } else {
            // 订单支付的总金额不够订单金额，表示订单未支付完成，商城现有逻辑正常不会进入此逻辑
            // 记录部分支付信息
            Orders newMainOrder = new Orders();
            newMainOrder.setId(mainOrder.getId());
            newMainOrder.setMoneyPaidReality(mainOrder.getMoneyPaidReality().add(allPaidMoney));
            newMainOrder.setTradeSn(tradeSn);
            Integer update = ordersWriteDao.update(newMainOrder);
            if (update == 0) {
                throw new BusinessException("修改订单时失败！");
            }

            // 记录订单日志
            OrderLog orderLog = new OrderLog(member.getId(), member.getName(), mainOrder.getId(),
                    mainOrder.getOrderSn(), "订单部分支付", new Date());
            Integer save = orderLogWriteDao.save(orderLog);
            if (save == 0) {
                throw new BusinessException("记录订单日志时失败！");
            }

            // orderPayBefore中调用时不记录日志，因为在扣除余额时已经记录过了
            // 只有在payAfter中调用且实际支付金额大于0时才记录此日志
            if (!isPaidBefore && (allPaidMoney.compareTo(BigDecimal.ZERO) > 0)) {
                // 记录订单支付日志
                OrderPayLog payLog = new OrderPayLog();
                payLog.setOrdersId(mainOrder.getId());
                payLog.setOrdersSn(mainOrder.getOrderSn());
                payLog.setPaymentCode(paymentCode);
                payLog.setPaymentName(paymentName);
                payLog.setPayMoney(allPaidMoney);
                payLog.setPayIntegral(0);
                payLog.setPaySn(mainOrder.getOrderSn());
                payLog.setTradeSn(tradeSn);
                payLog.setMemberId(member.getId());
                payLog.setMemberName(member.getName());
                payLog.setCreateTime(new Date());
                orderPayLogWriteDao.insert(payLog);
            }
        }

        // 只在payAfter中调用时才记录此日志，因为payBefore中调用此方法时不涉及第三方支付
        if (!isPaidBefore) {
            // 记录第三方支付的支付日志
            OrderPayCashLog cashLog = new OrderPayCashLog();
            cashLog.setPaySn(mainOrder.getOrderSn());
            cashLog.setTradeSn(tradeSn);
            cashLog.setPaymentCode(paymentCode);
            cashLog.setPaymentName(paymentName);
            cashLog.setPayMoney(allPaidMoney);
            cashLog.setPayOrderSn(payOrderSn);
            cashLog.setPayContent(tradeContent);
            cashLog.setMemberId(member.getId());
            cashLog.setMemberName(member.getName());
            cashLog.setCreateTime(new Date());
            orderPayCashLogWriteDao.insert(cashLog);
        }
    }

    /**
     * 支付之前的调用，获取订单列表，以及用余额支付等逻辑<br/>
     * 假如余额够支付，那么直接更改订单的状态，返回支付成功页面
     * @param orderSn 父订单的订单号
     * @param isBalancePay 是否用余额支付
     * @param balancePassword 余额密码，未加密
     * @param member
     * @return
     */
    public OrderSuccessVO orderPayBefore(String orderSn, boolean isBalancePay,
                                         String balancePassword, Member member) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //参数校验
            if (StringUtil.isEmpty(orderSn, true)) {
                log.error("订单号为空。");
                throw new BusinessException("订单号不能为空，请重试！");
            }

            OrderSuccessVO orderSuccVO = new OrderSuccessVO();

            Orders mainOrder = ordersWriteDao.getByOrderSn(orderSn);
            if (mainOrder != null) {
                if (!mainOrder.getMemberId().equals(member.getId())) {
                    throw new BusinessException("该订单不是您的订单，您不能操作，谢谢！");
                }
            } else {
                throw new BusinessException("订单信息获取失败，请重试！");
            }

            List<Orders> orderList = ordersWriteDao.getByOrderPsn(orderSn);
            if (orderList == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请重试！");
            }
            //计算订单总金额
            BigDecimal orderMoneyAlls = this.orderMoneyAlls(mainOrder);

            orderSuccVO.setMainOrder(mainOrder);
            orderSuccVO.setOrdersList(orderList);
            orderSuccVO.setPayAmount(orderMoneyAlls);

            // 支付前验证操作
            this.payBefore(mainOrder, orderList);

            orderSuccVO.setPayOrderAllsVO(orderMoneyAlls);
            Member memberNew = memberWriteDao.get(member.getId());
            if (orderMoneyAlls.compareTo(BigDecimal.ZERO) <= 0) {
                // 如果支付金额为0，则直接更改订单状态（主要针对使用余额后支付中断的情况）
                this.payAfter(mainOrder, orderList, BigDecimal.ZERO, memberNew, "", "",
                        Orders.PAYMENT_CODE_BALANCE, Orders.PAYMENT_NAME_BALANCE, true);
                orderSuccVO.setBanlancePayMoneyVO(orderMoneyAlls);
                orderSuccVO.setBanlancePayVO(OrderSuccessVO.BANLANCEPAYVO_2);
            } else {
                if (isBalancePay) {//余额支付
                    if (!memberNew.getBalancePwd().equals(Md5.getMd5String(balancePassword))) {
                        throw new BusinessException("支付密码错误，请重新输入");
                    }
                    // 使用余额，先扣除余额，防止用户并发支付时余额被多次使用
                    // 用户的账户余额
                    BigDecimal balance = memberNew.getBalance();
                    // 订单使用的余额总计（用于修改用户账户余额）
                    BigDecimal paidMoney = BigDecimal.ZERO;
                    for (Orders orders : orderList) {
                        // 如果余额大于0，继续扣减
                        if (balance.compareTo(BigDecimal.ZERO) > 0) {
                            // 订单应支付金额
                            BigDecimal needPay = orders.getMoneyOrder()
                                    .subtract(orders.getMoneyIntegral())
                                    .subtract(orders.getMoneyPaidBalance())
                                    .subtract(orders.getMoneyPaidReality());
                            needPay = needPay.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO
                                    : needPay;
                            // 本次余额实际能支付的金额
                            BigDecimal actualPaid = BigDecimal.ZERO;

                            // 修改订单余额支付金额
                            Orders newOrder = new Orders();
                            newOrder.setId(orders.getId());
                            if (balance.compareTo(needPay) >= 0) {
                                // 如果余额大于等于应付金额
                                actualPaid = needPay;
                            } else {
                                // 如果余额小于应付金额
                                actualPaid = balance;
                            }
                            // 余额支付金额累加
                            newOrder
                                    .setMoneyPaidBalance(orders.getMoneyPaidBalance().add(actualPaid));
                            // 把新的余额支付金额赋值给list中的订单对象，因为有可能调用payAfter方法，需要使用该值
                            orders
                                    .setMoneyPaidBalance(orders.getMoneyPaidBalance().add(actualPaid));
                            // 余额累减
                            balance = balance.subtract(actualPaid);
                            // 已支付金额累加
                            paidMoney = paidMoney.add(actualPaid);

                            // 实际支付金额大于0才记录日志及修改订单
                            if (actualPaid.compareTo(BigDecimal.ZERO) > 0) {
                                Integer update = ordersWriteDao.update(newOrder);
                                if (update == 0) {
                                    throw new BusinessException("修改订单余额支付金额时失败！");
                                }

                                // 记录余额日志
                                MemberBalanceLogs logs = new MemberBalanceLogs();
                                logs.setMemberId(member.getId());
                                logs.setMemberName(member.getName());
                                logs.setMoneyBefore(balance.add(actualPaid));
                                logs.setMoneyAfter(balance);
                                logs.setMoney(actualPaid);
                                logs.setCreateTime(new Date());
                                logs.setState(MemberBalanceLogs.STATE_3);
                                logs.setRemark("消费，订单号" + orders.getOrderSn());
                                logs.setOptId(member.getId());
                                logs.setOptName(member.getName());
                                Integer save = memberBalanceLogsWriteDao.save(logs);
                                if (save == 0) {
                                    throw new BusinessException("记录余额日志时失败！");
                                }

                                // 记录订单支付日志
                                OrderPayLog payLog = new OrderPayLog();
                                payLog.setOrdersId(orders.getId());
                                payLog.setOrdersSn(orders.getOrderSn());
                                payLog.setPaymentCode(Orders.PAYMENT_CODE_BALANCE);
                                payLog.setPaymentName(Orders.PAYMENT_NAME_BALANCE);
                                payLog.setPayMoney(actualPaid);
                                payLog.setPayIntegral(0);
                                payLog.setMemberId(member.getId());
                                payLog.setMemberName(member.getName());
                                payLog.setCreateTime(new Date());
                                save = orderPayLogWriteDao.insert(payLog);
                                if (save == 0) {
                                    throw new BusinessException("记录订单支付日志时失败！");
                                }
                            }
                        } else {
                            // 如果小于等于0则跳出循环
                            break;
                        }
                    }

                    // 如果使用的余额大于0，则修改父订单的支付信息
                    if (paidMoney.compareTo(BigDecimal.ZERO) > 0) {
                        // 修改订单余额支付金额
                        Orders newMainOrder = new Orders();
                        newMainOrder.setId(mainOrder.getId());
                        // 余额支付金额累加
                        newMainOrder
                                .setMoneyPaidBalance(mainOrder.getMoneyPaidBalance().add(paidMoney));
                        // 把新的余额支付金额赋值给父订单对象，因为有可能调用payAfter方法，需要使用该值
                        mainOrder
                                .setMoneyPaidBalance(mainOrder.getMoneyPaidBalance().add(paidMoney));

                        Integer update = ordersWriteDao.update(newMainOrder);
                        if (update == 0) {
                            throw new BusinessException("修改订单余额支付金额时失败！");
                        }

                        // 记录订单支付日志
                        OrderPayLog payLog = new OrderPayLog();
                        payLog.setOrdersId(mainOrder.getId());
                        payLog.setOrdersSn(mainOrder.getOrderSn());
                        payLog.setPaymentCode(Orders.PAYMENT_CODE_BALANCE);
                        payLog.setPaymentName(Orders.PAYMENT_NAME_BALANCE);
                        payLog.setPayMoney(paidMoney);
                        payLog.setPayIntegral(0);
                        payLog.setMemberId(member.getId());
                        payLog.setMemberName(member.getName());
                        payLog.setCreateTime(new Date());
                        int save = orderPayLogWriteDao.insert(payLog);
                        if (save == 0) {
                            throw new BusinessException("记录订单支付日志时失败！");
                        }
                    }

                    //更改余额
                    Member memberBalance = new Member();
                    memberBalance.setId(member.getId());
                    memberBalance.setBalance(paidMoney.multiply(new BigDecimal(-1)));
                    Integer updateBalance = memberWriteDao.updateBalance(memberBalance);
                    if (updateBalance == 0) {
                        throw new BusinessException("修改会员余额金额时失败！");
                    }
                    if (orderMoneyAlls.compareTo(memberNew.getBalance()) <= 0) {//余额够支付
                        orderSuccVO.setBanlancePayMoneyVO(orderMoneyAlls);
                        orderSuccVO.setBanlancePayVO(OrderSuccessVO.BANLANCEPAYVO_2);
                        // 更改订单状态
                        this.payAfter(mainOrder, orderList, BigDecimal.ZERO, memberNew, "", "",
                                Orders.PAYMENT_CODE_BALANCE, Orders.PAYMENT_NAME_BALANCE, true);
                    } else {//余额不够支付
                        orderSuccVO.setBanlancePayVO(OrderSuccessVO.BANLANCEPAYVO_3);
                        orderSuccVO.setBanlancePayMoneyVO(memberNew.getBalance());
                    }
                } else {
                    orderSuccVO.setBanlancePayVO(OrderSuccessVO.BANLANCEPAYVO_1);
                }
            }

            transactionManager.commit(status);
            return orderSuccVO;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("[OrderService][orderPayBefore]获取订单数据发生异常:", e);
            throw e;
        }

    }

    /**
     * 计算订单实际需要支付的金额
     * @param ordersList
     * @return
     */
    private BigDecimal orderMoneyAlls(Orders mainOrder) {
        // 订单应支付金额：money_order - money_integral - money_paid_balance - money_paid_reality
        // 如果订单勾选余额支付则先扣除余额，所以存在用户第一次支付未成功或者放弃支付时余额已经扣除，订单的余额支付字段已有值，所以应支付金额应减去余额支付的部分
        BigDecimal needPay = mainOrder.getMoneyOrder().subtract(mainOrder.getMoneyIntegral())
                .subtract(mainOrder.getMoneyPaidBalance()).subtract(mainOrder.getMoneyPaidReality());
        needPay = needPay.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : needPay;
        return needPay;
    }

    /**
     * 支付成功之后更改订单的状态
     * @param trade_no 订单
     * @param total_fee 金额
     * @param paycode 支付方式
     * @param payname 支付方式
     * @param tradeSn 交易流水号
     * @param tradeContent 交易返回信息
     * @return
     */
    public Boolean orderPayAfter(String trade_no, String total_fee, String paycode, String payname,
                                 String tradeSn, String tradeContent) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {

            Orders mainOrder = ordersWriteDao.getByOrderSn(trade_no);
            if (mainOrder == null) {
                throw new BusinessException("订单信息获取失败，请重试！");
            }

            List<Orders> ordersList = ordersWriteDao.getByOrderPsn(trade_no);
            if (ordersList == null || ordersList.size() == 0) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请重试！");
            }

            int memberID = mainOrder.getMemberId();
            Member member = memberWriteDao.get(memberID);

            // 更改订单状态、记录余额日志、支付日志

            this.payAfter(mainOrder, ordersList, new BigDecimal(total_fee), member, tradeSn,
                    tradeContent, paycode, payname, false);

            transactionManager.commit(status);
            return true;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("[OrderService][orderPayAfter]订单修改付款状态时发生异常:", e);
            throw e;
        }
    }

    // ------------------------限时抢购开始----------------------------------------------------------------------------------



    // ------------------------限时抢购结束----------------------------------------------------------------------------------

    // ------------------------活动公用方法开始-------------------------------------------------------------------------------
    /**
     * 保存商家的限时抢购、团购、集合竞价定金订单，以及订单日志
     * @param seller 商家信息
     * @param orderType 订单类型：Orders.ORDER_TYPE_2为限时抢购订单，Orders.ORDER_TYPE_3为团购订单，Orders.ORDER_TYPE_4为竞价定金订单
     * @param actFlashSaleProduct 抢购活动商品
     * @param actGroup 团购活动
     * @param actBidding 集合竞价活动
     * @param transFee 运费
     * @param member 会员
     * @param orderCommitVO 订单提交信息
     * @param address 地址
     * @param integral 积分
     * @param config 积分配置
     * @param ordersParent 父订单
     * @return
     */
    private Orders saveOrderInfoForActivity(Seller seller, Integer orderType,
                                            ActIntegral actIntegral, BigDecimal transFee,
                                            Member member, OrderCommitVO orderCommitVO,
                                            MemberAddress address, Integer integral, Config config,
                                            Orders ordersParent) {
        // 生成订单编号
        String orderSn = RandomUtil.getOrderSn();
        // 生成订单
        Orders order = new Orders();
        // 设为限时抢购、团购订单、竞价定金订单
        order.setOrderType(orderType);
        order.setOrderSn(orderSn);
        order.setIsParent(Orders.IS_PARENT_0);
        // 关联订单编号
        order.setRelationOrderSn("");
        order.setSellerId(seller.getId());
        order.setSellerName(seller.getSellerName());
        order.setMemberId(member.getId());
        order.setMemberName(member.getName());
        // 判断发票状态，记录发票信息
        order.setInvoiceStatus(orderCommitVO.getInvoiceStatus());
        if (Orders.INVOICE_STATUS_0 != orderCommitVO.getInvoiceStatus().intValue()) {
            order.setInvoiceTitle(orderCommitVO.getInvoiceTitle());
            order.setInvoiceType(orderCommitVO.getInvoiceType());
            if (Orders.INVOICE_STATUS_1 == orderCommitVO.getInvoiceStatus().intValue()) {
                order.setTaxNum(orderCommitVO.getInvoiceTaxNumber());
            } else {
                order.setTaxNum("");
            }
        }

        order.setIp(orderCommitVO.getIp());
        // 支付信息
        order.setPaymentName(orderCommitVO.getPaymentName());
        order.setPaymentCode(orderCommitVO.getPaymentCode());

        // 收货地址信息设置
        order.setName(address.getMemberName());
        order.setProvinceId(address.getProvinceId());
        order.setCityId(address.getCityId());
        order.setAreaId(address.getAreaId());
        order.setAddressAll(address.getAddAll());
        order.setAddressInfo(address.getAddressInfo());
        order.setMobile(address.getMobile());
        order.setEmail(address.getEmail());
        order.setZipCode(address.getZipCode());

        // 设置订单备注
        order.setRemark(orderCommitVO.getRemark());
        // 在线交易支付流水号
        order.setTradeSn("");
        // 订单来源：1、pc；2、H5；3、Android；4、IOS
        order.setSource(orderCommitVO.getSource());
        // 物流信息
        order.setLogisticsId(0);
        order.setLogisticsName("");
        order.setLogisticsNumber("");

        // 是否货到付款订单0、不是；1、是
        order.setIsCodconfim(Orders.IS_CODCONFIM_0);
        // 货到付款状态 0、非货到付款；1、待确认；2、确认通过可以发货；3、订单取消
        order.setCodconfirmState(Orders.CODCONFIRM_STATE_0);
        order.setCodconfirmId(0);
        order.setCodconfirmName("");
        order.setCodconfirmRemark("");

        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        /**
         * 金额的计算
         */

        // 金额信息

        // 商品总额（只是商品价格*数量的金额之和）
        if (orderType.intValue() == Orders.ORDER_TYPE_6) {
            // 积分价格的金额
            Integer priceMulNum = actIntegral.getPrice() * orderCommitVO.getNumber();
            BigDecimal moneyProduct = new BigDecimal(priceMulNum)
                    .divide(new BigDecimal(config.getIntegralScale()), 2, BigDecimal.ROUND_HALF_UP);

            if (actIntegral.getIsWithMoney().intValue() == ActIntegral.IS_WITH_MONEY_1) {
                // 如果是积分加金额的换购，则加上金额
                moneyProduct = moneyProduct.add(actIntegral.getPriceMoney()
                        .multiply(new BigDecimal(orderCommitVO.getNumber())));
            }

            order.setMoneyProduct(moneyProduct);
            // 活动id（与订单类型对应），积分换购订单存积分换购活动表ID
            order.setActivityId(actIntegral.getId());
        }

        // 判断物流费用
        order.setMoneyLogistics(transFee);
        // 余额支付金额（此处暂时设定为0，支付之后再修改）
        order.setMoneyPaidBalance(BigDecimal.ZERO);
        // 现金支付金额
        order.setMoneyPaidReality(BigDecimal.ZERO);

        // 优惠券优惠金额、优惠券ID（coupon_user的ID），大活动不能使用优惠券
        order.setMoneyCoupon(BigDecimal.ZERO);
        order.setCouponUserId(0);
        // 订单满减金额，大活动不参加满减
        order.setMoneyActFull(BigDecimal.ZERO);
        order.setActFullId(0);
        // 优惠金额总额（满减、立减、优惠券和），大活动不参加活动、不使用优惠券
        order.setMoneyDiscount(BigDecimal.ZERO);
        // 新订单退款的金额为0
        order.setMoneyBack(BigDecimal.ZERO);
        // 订单使用积分金额
        // 积分支付日志
        OrderPayLog orderPayLogInt = null;
        if (integral > 0) {
            // 计算转换总金额
            int moneyIntegral = integral / config.getIntegralScale();
            order.setMoneyIntegral(new BigDecimal(moneyIntegral));
            order.setIntegral(integral);

            // 修改用户积分数，记录积分消耗日志
            Member memberNew = new Member();
            memberNew.setId(member.getId());
            memberNew.setIntegral(0 - integral);
            Integer updateIntegral = memberWriteDao.updateIntegral(memberNew);
            if (updateIntegral == 0) {
                throw new BusinessException("扣除用户积分时失败，请重试！");
            }
            MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
            memberGradeIntegralLogs.setMemberId(member.getId());
            memberGradeIntegralLogs.setMemberName(member.getName());
            memberGradeIntegralLogs.setValue(integral);
            memberGradeIntegralLogs.setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_7);
            memberGradeIntegralLogs.setOptDes("积分支付消费(订单号:" + order.getOrderSn() + ")");
            memberGradeIntegralLogs.setRefCode(order.getOrderSn());
            memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
            memberGradeIntegralLogs.setCreateTime(new Date());
            Integer save = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
            if (save == 0) {
                throw new BusinessException("记录用户积分消费日志失败，请重试！");
            }
            // 记录订单积分支付日志
            orderPayLogInt = new OrderPayLog();
            // orderPayLogInt.setOrdersId(order.getId());
            orderPayLogInt.setOrdersSn(order.getOrderSn());
            orderPayLogInt.setPaymentCode(Orders.PAYMENT_CODE_INTEGRAL);
            orderPayLogInt.setPaymentName(Orders.PAYMENT_NAME_INTEGRAL);
            orderPayLogInt.setPayMoney(new BigDecimal(moneyIntegral));
            orderPayLogInt.setPayIntegral(integral);
            orderPayLogInt.setMemberId(member.getId());
            orderPayLogInt.setMemberName(member.getName());
            orderPayLogInt.setCreateTime(new Date());
        } else {
            order.setMoneyIntegral(BigDecimal.ZERO);
            order.setIntegral(0);
        }

        // 订单总金额，等于商品总金额＋运费-优惠金额总额（这个金额是最后结算给商家的金额）
        order.setMoneyOrder(((order.getMoneyProduct().add(order.getMoneyLogistics()))
                .subtract(order.getMoneyDiscount())));

        OrderLog orderLogForPaid = null;
        if ((order.getMoneyOrder().subtract(order.getMoneyIntegral()))
                .compareTo(new BigDecimal(0)) <= 0) {
            // 如果订单金额减去积分支付金额小于等于0，则直接设定为已付款
            if (orderType.intValue() == Orders.ORDER_TYPE_4) {
                // 竞价定金订单付款之后直接设定状态为已完成（定金订单不需要发货）
                order.setOrderState(Orders.ORDER_STATE_5);
            } else {
                order.setOrderState(Orders.ORDER_STATE_3);
            }
            // 已支付直接显示
            order.setIsShow(Orders.IS_SHOW_1);
            order.setPayTime(new Date());
            order.setPaymentStatus(Orders.PAYMENT_STATUS_1);

            orderLogForPaid = new OrderLog();
            orderLogForPaid.setContent("您使用积分支付了订单");
            orderLogForPaid.setOperatingId(member.getId());
            // 订单保存后设定订单ID
            // orderLogForPaid.setOrdersId(order.getId());
            orderLogForPaid.setOrdersSn(order.getOrderSn());
            orderLogForPaid.setOperatingName(member.getName());
        } else {
            // 其他情况

            // 未支付不显示
            order.setIsShow(Orders.IS_SHOW_0);

            // 付款状态
            order.setOrderState(Orders.ORDER_STATE_1);
            order.setPaymentStatus(Orders.PAYMENT_STATUS_0);

        }
        order.setEvaluateState(Orders.EVALUATE_STATE_1);

        // 生成总订单编号
        String orderPsn = RandomUtil.getOrderSn();
        // 设定总订单编号
        order.setOrderPsn(orderPsn);

        // 1、保存订单
        int count = ordersWriteDao.insert(order);
        if (count == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }
        //保存订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setContent("您提交了订单");
        orderLog.setOperatingId(member.getId());
        orderLog.setOrdersId(order.getId());
        orderLog.setOrdersSn(order.getOrderSn());
        orderLog.setOperatingName(member.getName());

        int orderlogCount = orderLogWriteDao.save(orderLog);
        if (orderlogCount == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }

        // 占库存，增加销量
        Integer stockRow = 0;
        if (orderType.intValue() == Orders.ORDER_TYPE_6) {
            stockRow = this.updateIntegralStockAndActualSales(actIntegral.getId(),
                    orderCommitVO.getNumber(), false);
        }
        if (stockRow == 0) {
            throw new BusinessException("修改商品库存时失败，请重试！");
        }
        if (order.getPaymentStatus().intValue() == Orders.PAYMENT_STATUS_1) {
            // 订单支付时记录订单日志
            if (orderLogForPaid != null) {
                orderLogForPaid.setOrdersId(order.getId());
                orderLogWriteDao.save(orderLogForPaid);
            }
        }

        // 订单支付日志
        // 如果积分支付日志不为空则记录
        if (orderPayLogInt != null) {
            orderPayLogInt.setOrdersId(order.getId());
            orderPayLogWriteDao.insert(orderPayLogInt);
        }

        // 复制一个总订单，虽然活动订单都是一个订单，但是为维护共通化同样生成一个总订单一个子订单
        try {
            PropertyUtils.copyProperties(ordersParent, order);
        } catch (Exception e) {
            log.error("复制订单对象失败。", e);
            throw new BusinessException("保存订单失败，请重试！");
        }
        // 设置总订单的属性
        ordersParent.setId(null);
        ordersParent.setOrderPsn("");
        ordersParent.setOrderSn(orderPsn);
        ordersParent.setIsParent(Orders.IS_PARENT_1);
        if ((ordersParent.getMoneyOrder().subtract(ordersParent.getMoneyIntegral()))
                .compareTo(new BigDecimal(0)) <= 0) {
            ordersParent.setIsShow(Orders.IS_SHOW_0);
        } else {
            ordersParent.setIsShow(Orders.IS_SHOW_1);
        }
        count = ordersWriteDao.insert(ordersParent);
        if (count == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }
        // 保存总订单日志
        OrderLog mainOrderLog = new OrderLog();
        mainOrderLog.setContent("您提交了订单");
        mainOrderLog.setOperatingId(member.getId());
        mainOrderLog.setOrdersId(ordersParent.getId());
        mainOrderLog.setOrdersSn(ordersParent.getOrderSn());
        mainOrderLog.setOperatingName(member.getName());

        orderlogCount = orderLogWriteDao.save(mainOrderLog);
        if (orderlogCount == 0) {
            throw new BusinessException("订单保存失败，请重试！");
        }

        if (integral > 0) {
            // 记录父订单积分支付日志
            OrderPayLog orderPayLogIntForm = new OrderPayLog();
            orderPayLogIntForm.setOrdersId(ordersParent.getId());
            orderPayLogIntForm.setOrdersSn(ordersParent.getOrderSn());
            orderPayLogIntForm.setPaymentCode(Orders.PAYMENT_CODE_INTEGRAL);
            orderPayLogIntForm.setPaymentName(Orders.PAYMENT_NAME_INTEGRAL);
            orderPayLogIntForm.setPayMoney(ordersParent.getMoneyIntegral());
            orderPayLogIntForm.setPayIntegral(ordersParent.getIntegral());
            orderPayLogIntForm.setMemberId(member.getId());
            orderPayLogIntForm.setMemberName(member.getName());
            orderPayLogIntForm.setCreateTime(new Date());
            orderPayLogWriteDao.insert(orderPayLogIntForm);
        }

        if ((ordersParent.getMoneyOrder().subtract(ordersParent.getMoneyIntegral()))
                .compareTo(new BigDecimal(0)) <= 0) {
            // 记录订单支付日志
            OrderLog orderLogForPaidForm = new OrderLog();
            orderLogForPaidForm.setContent("您使用积分支付了订单");
            orderLogForPaidForm.setOperatingId(member.getId());
            orderLogForPaidForm.setOrdersId(ordersParent.getId());
            orderLogForPaidForm.setOrdersSn(ordersParent.getOrderSn());
            orderLogForPaidForm.setOperatingName(member.getName());
            orderLogWriteDao.save(orderLogForPaidForm);
        }

        return order;
    }

    /**
     * 保存限时抢购、团购、集合竞价定金网单信息
     * @param order 订单
     * @param product 商品
     * @param goods 货品
     * @param orderType 订单类型
     * @param actFlashSaleProduct 限时抢购活动
     * @param actGroup 团购活动
     * @param actBidding 集合竞价活动
     * @param member 会员
     * @param seller 商家
     * @param number 数量
     */
    private void saveOrderProductInfoForActivity(Orders order, Product product, ProductGoods goods,
                                                 Integer orderType,
                                                 ActIntegral actIntegral, Member member,
                                                 Seller seller, Integer number, Config config) {

        //保存网单信息
        OrdersProduct op = new OrdersProduct();
        op.setOrdersId(order.getId());
        op.setOrdersSn(order.getOrderSn());
        op.setOrdersPsn(order.getOrderPsn());
        op.setSellerId(seller.getId());
        op.setSellerName(seller.getSellerName());
        op.setProductCateId(product.getProductCateId());
        op.setProductId(product.getId());
        op.setProductGoodsId(goods.getId());
        op.setSpecInfo(goods.getNormName());
        op.setProductName(product.getName1());
        op.setProductSku(goods.getSku());
        op.setPackageGroupsId(0);
        op.setMallGroupsId(0);
        op.setGiftId(0);
        op.setIsGift(OrdersProduct.IS_GIFT_0);
        if (orderType.intValue() == Orders.ORDER_TYPE_6) {
            // 单价 = 积分价格 + 现金价格
            BigDecimal price = new BigDecimal(actIntegral.getPrice())
                    .divide(new BigDecimal(config.getIntegralScale()), 2, BigDecimal.ROUND_HALF_UP);
            if (actIntegral.getIsWithMoney().intValue() == ActIntegral.IS_WITH_MONEY_1) {
                // 如果是积分加金额的换购，则加上金额
                price = price.add(actIntegral.getPriceMoney());
            }

            op.setMoneyPrice(price);
            // 网单金额
            BigDecimal moneyAmount = price.multiply(new BigDecimal(number));
            moneyAmount = moneyAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
            op.setMoneyAmount(moneyAmount);

            op.setActGroupId(0);
            op.setActFlashSaleId(0);
            op.setActFlashSaleProductId(0);
            op.setActBiddingId(0);
            op.setActIntegralId(actIntegral.getId());
            op.setActIntegralNum(actIntegral.getPrice() * number);
            op.setActIntegralMoney(
                    actIntegral.getPriceMoney().multiply(BigDecimal.valueOf(number)));
        }
        op.setNumber(number);

        // 立减优惠金额和，不参加活动
        op.setMoneyActSingle(BigDecimal.ZERO);
        op.setActSingleId(0);
        op.setBackNumber(0);
        op.setExchangeNumber(0);
        op.setIsEvaluate(OrdersProduct.IS_EVALUATE_0);

        // 1、保存网单
        int count = ordersProductWriteDao.insert(op);
        if (count == 0) {
            throw new BusinessException("网单保存失败，请重试！");
        }


    }

    /**
     * 检查商品和商家信息
     * @param product
     * @param productGoods
     * @param seller
     */
    private void checkProductAndSellerForActivity(Product product, ProductGoods productGoods,
                                                  Seller seller) {
        if (product == null) {
            throw new BusinessException("商品信息获取失败！");
        }
        if (productGoods == null) {
            throw new BusinessException("货品信息获取失败！");
        }
        if (seller == null) {
            throw new BusinessException("商家信息获取失败！");
        }
        if (!productGoods.getProductId().equals(product.getId())) {
            throw new BusinessException("货品信息和商品信息不匹配！");
        }
        if (!seller.getId().equals(product.getSellerId())) {
            throw new BusinessException("商品信息和商家信息不匹配！");
        }
        if (seller.getAuditStatus().intValue() != Seller.AUDIT_STATE_2_DONE) {
            throw new BusinessException(
                    "商家[" + seller.getSellerName() + "]已被冻结，请把该商家的商品移出购物车后再下单，谢谢！");
        }
        if (product.getState().intValue() != Product.STATE_6) {
            throw new BusinessException("该商品已下架，不能下单！");
        }
        // 判断分类状态
        if (product.getProductCateState().intValue() != Product.PRODUCT_CATE_STATE_1) {
            throw new BusinessException("该商品已下架，请重新选择商品！");
        }
        // 货品状态异常或未启用的，不能下单
        if (productGoods.getState() == null
                || productGoods.getState().intValue() == ProductGoods.DISABLE) {
            String productName = product.getName1();
            if (!StringUtil.isEmpty(productGoods.getNormName(), true)) {
                productName += " " + productGoods.getNormName();
            }
            throw new BusinessException("商品[" + productName + "]已下架，不能下单！");
        }
    }
    // ------------------------活动公用方法结束-------------------------------------------------------------------------------

    // ------------------------团购开始-------------------------------------------------------------------------------------



    // ------------------------团购结束-------------------------------------------------------------------------------------

    // ------------------------集合竞价开始----------------------------------------------------------------------------------



    // ------------------------集合竞价结束----------------------------------------------------------------------------------

    // ------------------------积分商城开始-------------------------------------------------------------------------------------

    /**
     * 用户积分换购提交订单<br>
     * @param orderCommitVO
     * @return
     * @throws Exception
     */
    public OrderSuccessVO orderCommitForIntegral(OrderCommitVO orderCommitVO) throws Exception {

        //参数校验
        if (orderCommitVO == null) {
            log.error("订单提交信息为空。");
            throw new BusinessException("订单提交信息为空，请重试！");
        } else if (orderCommitVO.getAddressId() == null || orderCommitVO.getAddressId() == 0) {
            log.error("订单提交信息中收货地址ID为空。");
            throw new BusinessException("订单提交信息中收货地址ID为空，请重试！");
        }

        // 根据来源判断渠道，默认渠道为PC
        int channel = ConstantsEJS.CHANNEL_2;
        if (orderCommitVO.getSource() != null
                && (orderCommitVO.getSource().equals(ConstantsEJS.SOURCE_2_H5)
                || orderCommitVO.getSource().equals(ConstantsEJS.SOURCE_3_ANDROID)
                || orderCommitVO.getSource().equals(ConstantsEJS.SOURCE_4_IOS))) {
            channel = ConstantsEJS.CHANNEL_3;
        }

        // 获取积分换购活动
        ActIntegral actIntegral = actIntegralReadDao.get(orderCommitVO.getActIntegralId());
        this.checkActIntegralEffect(actIntegral);
        // 校验渠道
        if (actIntegral.getChannel().intValue() != ConstantsEJS.CHANNEL_1) {
            if (actIntegral.getChannel().intValue() != channel) {
                String channelName = channel == ConstantsEJS.CHANNEL_2 ? "电脑端" : "移动端";
                throw new BusinessException("该积分换购活动不能在" + channelName + "下单。");
            }
        }
        // 校验库存
        if (actIntegral.getStock() < orderCommitVO.getNumber()) {
            throw new BusinessException("对不起，商品库存不足了，请修改购买数量后再下单！");
        }
        // 单次购买限制
        if (orderCommitVO.getNumber() > actIntegral.getPurchase()) {
            throw new BusinessException("对不起，单次最多购买" + actIntegral.getPurchase() + "个该商品！");
        }
        // 会员等级限制
        // 获取提交订单的用户
        Member member = memberWriteDao.get(orderCommitVO.getMemberId());
        if (member.getGrade() < actIntegral.getGradeValue()) {
            throw new BusinessException("对不起，您的会员等级不能换购该商品，谢谢。");
        }

        // 取得商品
        Product product = productReadDao.get(orderCommitVO.getProductId());
        // 获取货品
        ProductGoods productGoods = productGoodsReadDao.get(orderCommitVO.getProductGoodsId());
        // 获取商家
        Seller seller = sellerReadDao.get(orderCommitVO.getSellerId());

        this.checkProductAndSellerForActivity(product, productGoods, seller);

        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // 初始化返回的参数
            OrderSuccessVO orderSuccVO = new OrderSuccessVO();
            // 初始默认为订单没有支付，如果余额支付全款则重设为true
            orderSuccVO.setIsPaid(false);
            // 初始默认为跳往支付页面，如果订单是货到付款或者余额全额支付了，设定为false
            orderSuccVO.setGoJumpPayfor(true);
            // 支付方式默认与页面选择的一致，如果余额全额支付后，修改为Orders.PAYMENT_CODE_BALANCE，余额支付
            orderSuccVO.setPaymentCode(orderCommitVO.getPaymentCode());
            orderSuccVO.setPaymentName(orderCommitVO.getPaymentName());

            // 获取地址
            MemberAddress address = memberAddressWriteDao.get(orderCommitVO.getAddressId());

            //            BigDecimal calculateTransFee = sellerTransportModel.calculateTransFee(
            //                orderCommitVO.getSellerId(), orderCommitVO.getNumber(), address.getCityId());
            //            calculateTransFee = calculateTransFee.compareTo(BigDecimal.ZERO) < 1 ? BigDecimal.ZERO
            //                : calculateTransFee;

            // 积分换购必须使用积分支付，所以使用积分数量根据积分换购活动计算
            Integer integral = actIntegral.getPrice() * orderCommitVO.getNumber();
            Config config = configReadDao.get(ConstantsEJS.CONFIG_ID);
            // 判断用户的积分是否够换购商品的积分数量
            if (member.getIntegral() == null || member.getIntegral() < integral) {
                throw new BusinessException("积分不够了，请重新选择商品！");
            }
            if (config == null) {
                throw new BusinessException("积分转换金额失败，请联系系统管理员！");
            }

            List<Orders> orderList = new ArrayList<Orders>();

            Orders mainOrder = new Orders();
            // 保存订单及日志信息
            Orders order = this.saveOrderInfoForActivity(seller, Orders.ORDER_TYPE_6,  actIntegral, BigDecimal.ZERO, member, orderCommitVO, address, integral,
                    config, mainOrder);
            orderList.add(order);

            // 保存网单信息
            this.saveOrderProductInfoForActivity(order, product, productGoods, Orders.ORDER_TYPE_6,
                    actIntegral, member, seller, orderCommitVO.getNumber(), config);

            if (order.getPaymentStatus().intValue() == Orders.PAYMENT_STATUS_1) {
                // 记录是否已支付，返回判断跳往什么页面
                orderSuccVO.setIsPaid(true);
                orderSuccVO.setPaymentCode(Orders.PAYMENT_NAME_BALANCE);
                orderSuccVO.setPaymentName(Orders.PAYMENT_CODE_BALANCE);
                orderSuccVO.setGoJumpPayfor(false);
            }

            //封装返回对象
            orderSuccVO.setMainOrder(mainOrder);
            orderSuccVO.setOrdersList(orderList);
            orderSuccVO.setPayAmount(order.getMoneyOrder().subtract(order.getMoneyIntegral()));
            transactionManager.commit(status);

            return orderSuccVO;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    /**
     * 积分换购活动有效性校验
     * @param actIntegral
     */
    private void checkActIntegralEffect(ActIntegral actIntegral) {
        if (actIntegral.getTypeState() == null
                || actIntegral.getTypeState() != ActIntegral.TYPE_STATE_1) {
            log.error("积分换购活动" + actIntegral.getName() + "的分类状态为不显示，下单失败。");
            throw new BusinessException("对不起，该积分换购活动已下线！");
        }
        if (actIntegral.getState() == null || actIntegral.getState() != ActIntegral.STATE_3) {
            throw new BusinessException("对不起，积分换购活动不存在！");
        }
        if (actIntegral.getActivityState() == null
                || actIntegral.getActivityState() != ActIntegral.ACTIVITY_STATE_2) {
            throw new BusinessException("对不起，该积分换购活动还没有发布！");
        }
        Date date = new Date();
        if (date.before(actIntegral.getStartTime())) {
            throw new BusinessException("对不起，该积分换购活动还没有开始！");
        }
        if (date.after(actIntegral.getEndTime())) {
            throw new BusinessException("对不起，该积分换购活动已结束！");
        }
        if (actIntegral.getStock() < 1) {
            throw new BusinessException("对不起，该商品已经被抢光了！");
        }
    }

    // ------------------------积分商城结束-------------------------------------------------------------------------------------

    /**
     * 待确认订单数量
     * @return
     */
    public Integer getReconfOrdersCount() {
        Map<String, String> param = new HashMap<>();
        param.put("q_orderState", "2");
        param.put("q_isParent", "0");
        Integer res = ordersReadDao.getOrdersCount(param);
        return res;
    }

    /**
     * 查看待评价订单数量
     * @param memberId
     * @return
     */
    public Integer getOrderNumByMIdAndEvaluateState(Integer memberId) {
        return ordersReadDao.getNumByMIdAndEvaluateState(memberId);
    }

    // ----------------------取消订单开始--------------------------------------------

    /**
     * 系统自动取消24小时没有付款订单
     * @return
     */
    public boolean jobSystemCancelOrder() {

        // 获取当前时间1天之前的时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        String cancelTime = TimeUtil.getDateTimeString(calendar.getTime());

        // 获取下单24小时还未付款的订单，此处查询的都是待付款状态的父订单
        List<Orders> ordersList = ordersReadDao.getUnPaiedOrders(cancelTime);

        if (ordersList != null && ordersList.size() > 0) {
            // 单条数据处理异常不影响其他数据执行
            for (Orders orders : ordersList) {
                if (orders.getOrderType().intValue() == Orders.ORDER_TYPE_4
                        || orders.getOrderType().intValue() == Orders.ORDER_TYPE_5) {
                    // 竞价定金订单和竞价尾款订单都不自动取消
                    continue;
                }
                // 事务管理
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {

                    // 取消订单
                    this.doCancelOrder(orders, 0, "system", "系统自动取消订单");

                    transactionManager.commit(status);
                } catch (Exception e) {
                    transactionManager.rollback(status);
                    log.error("[OrderModel][jobSystemCancelOrder]系统自动取消订单时发生异常:", e);
                    log.error(
                            "[OrderModel][jobSystemCancelOrder]发生异常的订单：" + JSON.toJSONString(orders));
                }
            }
        }

        return true;
    }

    /**
     * 取消订单 目前只有订单状态为 1、2、3的可以取消
     * @param ordersId 订单ID
     * @param optId 操作人ID
     * @param optName 操作人名称
     * @return
     */
    public boolean cancelOrder(Integer ordersId, Integer optId, String optName) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //参数校验
            if (ordersId == null || ordersId == 0) {
                throw new BusinessException("订单ID为空，请重试！");
            }
            //获取订单
            Orders ordersDb = ordersWriteDao.get(ordersId);

            if (!ordersDb.getMemberId().equals(optId)) {
                throw new BusinessException("您不能操作别人的订单，谢谢！");
            }

            // 取消订单
            this.doCancelOrder(ordersDb, optId, optName, "您取消了订单");

            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void doCancelOrder(Orders ordersDb, Integer optId, String optName, String logContent) {
        if (ordersDb == null) {
            throw new BusinessException("获取订单信息失败，请重试！");
        } else if (!(ordersDb.getOrderState().equals(Orders.ORDER_STATE_1)
                || (ordersDb.getOrderState().equals(Orders.ORDER_STATE_2))
                || (ordersDb.getOrderState().equals(Orders.ORDER_STATE_3)))) {
            throw new BusinessException("订单已发货不能取消！");
        }
        if (ordersDb.getOrderType().intValue() == Orders.ORDER_TYPE_5) {
            // 竞价尾款订单不能取消
            throw new BusinessException("集合竞价尾款订单不能取消！");
        }

        //设置订单状态
        Orders orders = new Orders();
        orders.setId(ordersDb.getId());
        orders.setFinishTime(new Date());
        orders.setOrderState(Orders.ORDER_STATE_6);
        int count = ordersWriteDao.update(orders);
        if (count == 0) {
            throw new BusinessException("订单更新失败，请重试！");
        }
        //记录订单日志
        OrderLog orderLog = new OrderLog();
        orderLog.setContent(logContent);
        orderLog.setOperatingId(optId);
        orderLog.setOrdersId(ordersDb.getId());
        orderLog.setOrdersSn(ordersDb.getOrderSn());
        orderLog.setOperatingName(optName);

        int logCount = orderLogWriteDao.save(orderLog);
        if (logCount == 0) {
            throw new BusinessException("订单日志保存失败，请重试！");
        }

        // 如果是父订单，则修改子订单的状态
        if (ordersDb.getIsParent().intValue() == Orders.IS_PARENT_1) {
            ordersWriteDao.cancelByPsn(ordersDb.getOrderSn());
        }

        // 返还积分
        this.cancelOrderBackIntegral(ordersDb);

        // 返回优惠券
        this.cancelOrderBackCoupon(ordersDb, optId, optName);

        // 退回付款金额
        this.cancelOrderBackMoney(ordersDb, optId, optName);

        // 还原库存和实际销量
        this.cancelOrderBackStockAndActualSales(ordersDb);
    }

    /**
     * 取消订单时，把现金支付和余额支付的钱退到用户余额中
     * @param order
     * @param optId
     * @param optName
     */
    private void cancelOrderBackMoney(Orders order, Integer optId, String optName) {

        Member memberDB = memberWriteDao.get(order.getMemberId());

        // 付款的金额（现金支付+余额支付）
        BigDecimal money = order.getMoneyPaidReality().add(order.getMoneyPaidBalance());
        // 如果付款金额大于0，则退回并记录日志
        if (money.compareTo(BigDecimal.ZERO) > 0) {
            // 修改用户的余额
            Member updateBalanceObj = new Member();
            updateBalanceObj.setId(order.getMemberId());
            updateBalanceObj.setBalance(money);
            Integer updateBalance = memberWriteDao.updateBalance(updateBalanceObj);
            if (updateBalance == 0) {
                log.error("退回余额时失败。");
                throw new BusinessException("退回余额时失败，请重试！");
            }

            // 记录【会员账户余额变化日志表】
            MemberBalanceLogs logs = new MemberBalanceLogs();
            logs.setMemberId(memberDB.getId());
            logs.setMemberName(memberDB.getName());
            logs.setMoneyBefore(memberDB.getBalance());
            logs.setMoneyAfter(memberDB.getBalance().add(money));
            logs.setMoney(money);
            logs.setCreateTime(new Date());
            logs.setState(MemberBalanceLogs.STATE_2);
            logs.setRemark("取消订单，订单号" + order.getOrderSn());
            logs.setOptId(optId);
            logs.setOptName(optName);

            Integer balanceLog = memberBalanceLogsWriteDao.save(logs);
            if (balanceLog == 0) {
                log.error("记录会员余额变化日志时出错。");
                throw new BusinessException("退回余额时失败，请重试！");
            }
        }
    }

    /**
     * 取消订单时退回用户优惠券
     * @param order
     * @param optId
     * @param optName
     */
    private void cancelOrderBackCoupon(Orders orderDb, Integer optId, String optName) {

        List<Orders> list = null;
        // 如果是子订单则只退这个订单的优惠券，如果是父订单，则退回该父订单下所有子订单的优惠券
        if (orderDb.getIsParent().intValue() == Orders.IS_PARENT_1) {
            list = ordersReadDao.getByOrderPsn(orderDb.getOrderSn());
        } else {
            list = new ArrayList<>();
            list.add(orderDb);
        }
        for (Orders order : list) {
            if (order.getCouponUserId() != null && order.getCouponUserId() > 0) {
                CouponUser couponUser = couponUserReadDao.get(order.getCouponUserId());
                if (couponUser == null) {
                    log.error("用户优惠券获取失败。");
                    throw new BusinessException("返还用户优惠券时失败，请重试！");
                }
                Integer backCouponUser = couponUserWriteDao.backCouponUser(order.getMemberId(),
                        couponUser.getId());
                if (backCouponUser < 1) {
                    log.error("修改用户优惠券使用次数失败。");
                    throw new BusinessException("返还用户优惠券时失败，请重试！");
                }
                // 设定优惠券使用日志
                CouponOptLog couponOptLog = new CouponOptLog();
                couponOptLog.setCouponUserId(couponUser.getId());
                couponOptLog.setMemberId(couponUser.getMemberId());
                couponOptLog.setSellerId(couponUser.getSellerId());
                couponOptLog.setCouponId(couponUser.getCouponId());
                couponOptLog.setOptType(CouponOptLog.OPT_TYPE_3);
                couponOptLog.setOrderId(order.getId());
                couponOptLog.setCreateUserId(optId);
                couponOptLog.setCreateUserName(optName);
                couponOptLog.setCreateTime(new Date());
                couponOptLogWriteDao.insert(couponOptLog);
            }
        }
    }

    /**
     * 订单取消时，1、返还该订单消耗的积分；2、追回付款时送给用户的积分
     * @param order
     */
    private void cancelOrderBackIntegral(Orders order) {
        // 最终需要修改的积分数量
        int backValue = 0;

        // 1、返还该订单消耗的积分
        // 消耗了积分才返还
        if (order.getIntegral() > 0) {
            // 返还积分是增加
            backValue = order.getIntegral();

            MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
            memberGradeIntegralLogs.setMemberId(order.getMemberId());
            memberGradeIntegralLogs.setMemberName(order.getMemberName());
            memberGradeIntegralLogs.setValue(order.getIntegral());
            memberGradeIntegralLogs.setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_11);
            memberGradeIntegralLogs.setOptDes("取消订单返还积分(订单号:" + order.getOrderSn() + ")");
            memberGradeIntegralLogs.setRefCode(order.getOrderSn());
            memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
            memberGradeIntegralLogs.setCreateTime(new Date());
            Integer save = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
            if (save == 0) {
                throw new BusinessException("记录用户积分消费日志失败（取消订单返还积分），请重试！");
            }
        }

        // 2、追回付款时送给用户的积分，只有当订单是子订单时才需要追回
        if (order.getPaymentStatus().intValue() == Orders.PAYMENT_STATUS_1
                && order.getIsParent().intValue() == Orders.IS_PARENT_0) {
            // 订单已付款才会发生此类型积分追回

            // 计算购物时平台送出的积分追回为减少用户的积分
            MemberGradeIntegralLogs sendIntLog = memberGradeIntegralLogsReadDao
                    .getIntLogByMIdAndOrderSnAndOptType(order.getMemberId(),
                            MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_3, order.getOrderSn(),
                            MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
            if (sendIntLog != null && sendIntLog.getValue() > 0) {
                //  追回积分是减少
                backValue = backValue - sendIntLog.getValue();

                MemberGradeIntegralLogs memberGradeIntegralLogs = new MemberGradeIntegralLogs();
                memberGradeIntegralLogs.setMemberId(order.getMemberId());
                memberGradeIntegralLogs.setMemberName(order.getMemberName());
                memberGradeIntegralLogs.setValue(sendIntLog.getValue());
                memberGradeIntegralLogs
                        .setOptType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_OPT_T_13);
                memberGradeIntegralLogs.setOptDes("取消订单追回积分(订单号:" + order.getOrderSn() + ")");
                memberGradeIntegralLogs.setRefCode(order.getOrderSn());
                memberGradeIntegralLogs.setType(MemberGradeIntegralLogs.MEMBER_GRD_INT_LOG_T_2);
                memberGradeIntegralLogs.setCreateTime(new Date());
                Integer saveLog = memberGradeIntegralLogsWriteDao.save(memberGradeIntegralLogs);
                if (saveLog == 0) {
                    throw new BusinessException("记录用户积分消费日志失败（取消订单追回积分），请重试！");
                }
            }
        }

        // 3、修改用户积分数量，记录积分消耗日志
        Member memberNew = new Member();
        memberNew.setId(order.getMemberId());
        memberNew.setIntegral(backValue);
        Integer updateIntegral = memberWriteDao.updateIntegral(memberNew);
        if (updateIntegral == 0) {
            throw new BusinessException("取消订单修改用户积分时失败，请重试！");
        }
    }

    /**
     * 取消订单还原库存和实际销量公共方法
     * @param orderType
     * @param ordersId
     */
    private void cancelOrderBackStockAndActualSales(Orders orderDb) {
        List<OrdersProduct> opList = null;
        if (orderDb.getIsParent().intValue() == Orders.IS_PARENT_1) {
            opList = ordersProductReadDao.getByOrdersPsn(orderDb.getOrderSn());
        } else {
            opList = ordersProductReadDao.getByOrderId(orderDb.getId());
        }
        Integer orderType = orderDb.getOrderType();
        //还原库存和实际销量
        if (orderType.intValue() == Orders.ORDER_TYPE_6) {
            // 积分换购订单时还原活动商品的库存
            // 积分换购只有一个网单
            this.updateIntegralStockAndActualSales(opList.get(0).getActIntegralId(),
                    opList.get(0).getNumber(), true);
        } else {
            // 普通订单
            // 更新货品和商品的库存
            if (opList != null && opList.size() > 0) {
                for (OrdersProduct op : opList) {
                    this.updateProductActualSalesAndStock(op.getProductId(), op.getProductGoodsId(),
                            op.getNumber(), true);
                }
            }
        }
    }

    // ----------------------取消订单结束--------------------------------------------

    /**
     * 根据商品ID和货品ID更新商品货品的实际销量和库存
     * @param productId
     * @param productGoodsId
     * @param number
     * @param isCancelOrders 是否是取消订单操作
     */
    private void updateProductActualSalesAndStock(Integer productId, Integer productGoodsId,
                                                  Integer number, boolean isCancelOrders) {

        // 如果是取消订单，则将数量变为负数
        if (isCancelOrders) {
            number = 0 - number;
        }
        // 修改商品库存和销量 累加或着累减
        int pcount = productWriteDao.updateActualSalesAndStock(productId, number);
        if (pcount == 0) {
            log.error("商品库存和实际销量更新失败。");
            throw new BusinessException("商品库存和销量更新失败！");
        }

        // 修改货品库存和销量 累加或者累减
        pcount = productGoodsWriteDao.updateActualSalesAndStock(productGoodsId, number);
        if (pcount == 0) {
            log.error("修改货品库存和销量失败。");
            throw new BusinessException("货品库存和销量更新失败！");
        }
    }






    /**
     * 修改积分换购活动商品的库存和销量
     * @param actIntegralId
     * @param number
     * @param isCancelOrders 是否是取消订单操作
     */
    private Integer updateIntegralStockAndActualSales(Integer actIntegralId, Integer number,
                                                      Boolean isCancelOrders) {
        //如果不是取消订单，则将数量改为负
        if (isCancelOrders) {
            number = 0 - number;
        }
        return actIntegralWriteDao.updateStockAndActualSales(actIntegralId, number);
    }
}
