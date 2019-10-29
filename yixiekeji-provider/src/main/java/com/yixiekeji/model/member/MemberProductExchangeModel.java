package com.yixiekeji.model.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.member.MemberProductExchangeReadDao;
import com.yixiekeji.dao.shop.read.operate.CourierCompanyReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.write.member.MemberProductExchangeLogWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberProductExchangeWriteDao;
import com.yixiekeji.dao.shop.write.order.OrdersProductWriteDao;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.entity.member.MemberProductExchangeLog;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerUser;

/**
 * 会员换货管理                       
 *
 */
@Component(value = "memberProductExchangeModel")
public class MemberProductExchangeModel {
    private static org.slf4j.Logger          log = org.slf4j.LoggerFactory
        .getLogger(MemberProductExchangeModel.class);

    @Resource
    private MemberProductExchangeWriteDao    memberProductExchangeWriteDao;
    @Resource
    private MemberProductExchangeReadDao     memberProductExchangeReadDao;
    @Resource
    private OrdersReadDao                    ordersReadDao;
    @Resource
    private ProductReadDao                   productReadDao;
    @Resource
    private OrdersProductWriteDao            ordersProductWriteDao;
    @Resource
    private OrdersProductReadDao             ordersProductReadDao;
    @Resource
    private DataSourceTransactionManager     transactionManager;
    @Resource
    private MemberProductExchangeLogWriteDao memberProductExchangeLogWriteDao;
    @Resource
    private CourierCompanyReadDao            courierCompanyReadDao;

    /**
    * 根据id取得用户换货
    * @param  memberProductExchangeId
    * @return
    */
    public MemberProductExchange getMemberProductExchangeById(Integer memberProductExchangeId) {
        return memberProductExchangeWriteDao.get(memberProductExchangeId);
    }

    /**
     * 保存用户换货
     * @param memberProductExchange
     * @param member
     * @return
     */
    public boolean saveMemberProductExchange(MemberProductExchange memberProductExchange,
                                             Member member) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 参数校验
            if (memberProductExchange == null) {
                throw new BusinessException("换货申请不能为空，请重试！");
            } else if (memberProductExchange.getProductId() == null
                       || memberProductExchange.getProductId() == 0) {
                throw new BusinessException("换货申请产品id不能为空，请重试！");
            } else if (memberProductExchange.getOrderProductId() == null
                       || memberProductExchange.getOrderProductId() == 0) {
                throw new BusinessException("换货申请网单id不能为空，请重试！");
            } else if (memberProductExchange.getOrderId() == null
                       || memberProductExchange.getOrderId() == 0) {
                throw new BusinessException("换货申请订单id不能为空，请重试！");
            }

            //根据订单id取对应的订单信息 
            Orders order = ordersReadDao.get(memberProductExchange.getOrderId());
            if (order == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请联系管理员！");
            } else {
                int orderState = order.getOrderState();
                if (orderState == Orders.ORDER_STATE_1 || orderState == Orders.ORDER_STATE_2
                    || orderState == Orders.ORDER_STATE_3 || orderState == Orders.ORDER_STATE_4) {

                    log.error("订单此时所处状态不允许提交换货申请。");
                    throw new BusinessException("订单此时所处状态不允许提交换货申请！");
                }
            }

            // 用户接收换件的地址（用户申请换货的时候直接从订单表查询填写）
            memberProductExchange.setProvinceId(order.getProvinceId());
            memberProductExchange.setCityId(order.getCityId());
            memberProductExchange.setAreaId(order.getAreaId());
            memberProductExchange.setAddressAll(order.getAddressAll());
            memberProductExchange.setAddressInfo(order.getAddressInfo());
            memberProductExchange.setZipCode(order.getZipCode());
            memberProductExchange.setChangeName(order.getName());
            memberProductExchange.setPhone(order.getMobile());
            if (StringUtil.isEmpty(memberProductExchange.getName(), true)) {
                memberProductExchange.setName(order.getName());
            }

            memberProductExchange.setMemberId(member.getId());
            memberProductExchange.setMemberName(member.getName());
            memberProductExchange.setState(ConstantsEJS.MEM_PROD_EXCHG_STATE_1);
            //1、保存信息
            Integer count = memberProductExchangeWriteDao.save(memberProductExchange);
            if (count == 0) {
                throw new BusinessException("产品换货申请保存失败，请重试！");
            }
            //获取网单信息
            OrdersProduct ordersProduct = ordersProductWriteDao
                .get(memberProductExchange.getOrderProductId());
            if (ordersProduct == null) {
                log.error("网单不存在。");
                throw new BusinessException("网单不存在！");
            } else {
                //获取当前可以退货的数量
                Integer dbNum = ordersProduct.getNumber() - ordersProduct.getBackNumber()
                                - ordersProduct.getExchangeNumber();
                if (dbNum == 0 || dbNum < memberProductExchange.getNumber()) {
                    throw new BusinessException("该网单不能进行换货申请！");
                }
                Integer pbcount = ordersProductWriteDao.updateExchangeNumber(ordersProduct.getId(),
                    memberProductExchange.getNumber());
                if (pbcount == 0) {
                    throw new BusinessException("网单换货信息更新失败，请重试！");
                }
            }
            // 保存换货日志
            MemberProductExchangeLog memberProductExchangeLog = new MemberProductExchangeLog();
            memberProductExchangeLog.setOperatingId(member.getId());
            memberProductExchangeLog.setOperatingName(member.getName());
            memberProductExchangeLog.setContent("会员申请换货");
            memberProductExchangeLog.setMemberProductExchangeId(memberProductExchange.getId());
            Integer insert = memberProductExchangeLogWriteDao.insert(memberProductExchangeLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存换货日志失败，请重试！");
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
    * 更新用户换货
    * @param  memberProductExchange
    * @return
    */
    public boolean updateMemberProductExchange(MemberProductExchange memberProductExchange) {
        return memberProductExchangeWriteDao.update(memberProductExchange) > 0;
    }

    /**
     * 根据登录用户取得用户换货列表 分页
     * @param pager
     * @param request
     * @return
     */
    public List<MemberProductExchange> getMemberProductExchangeList(Map<String, Object> queryMap,
                                                                    PagerInfo pager,
                                                                    Integer memberId) {

        //取所有的换货申请
        queryMap.put("memberId", memberId);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(memberProductExchangeReadDao.queryCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        queryMap.put("start", start);
        queryMap.put("size", size);
        List<MemberProductExchange> beanList = memberProductExchangeReadDao.queryList(queryMap);

        for (MemberProductExchange bean : beanList) {
            //取对应的订单信息 根据订单id
            Orders order = ordersReadDao.get(bean.getOrderId());
            if (order == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请联系管理员！");
            }
            //获得对应产品信息
            Product product = productReadDao.get(bean.getProductId());
            bean.setProductName(product.getName1());
            bean.setOrderSn(order.getOrderSn());
        }
        return beanList;
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return memberProductExchangeReadDao.getCount(queryMap);
    }

    public List<MemberProductExchange> page(Map<String, String> queryMap, Integer start,
                                            Integer size) {
        return memberProductExchangeReadDao.page(queryMap, start, size);
    }

    /**
     * 根据登录用户取得用户换货列表(封装商品对象和网单对象)
     * @param pager
     * @param memberId
     * @return
     */
    public List<MemberProductExchange> getExchangeListWithPrdAndOp(PagerInfo pager,
                                                                   Integer memberId) {

        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("memberId", memberId);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(memberProductExchangeReadDao.queryCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        queryMap.put("start", start);
        queryMap.put("size", size);
        List<MemberProductExchange> beanList = memberProductExchangeReadDao.queryList(queryMap);

        for (MemberProductExchange bean : beanList) {
            bean.setProduct(productReadDao.get(bean.getProductId()));
            bean.setOrdersProduct(ordersProductReadDao.get(bean.getOrderProductId()));
        }
        return beanList;
    }

    /**
     * 商家处理换货信息
     * @param memberProductExchange
     * @param user
     * @return
     */
    public Boolean audit(MemberProductExchange memberProductExchange, SellerUser user) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (memberProductExchange.getState() == MemberProductExchange.STATE_5
                || memberProductExchange.getState() == MemberProductExchange.STATE_6) {
                CourierCompany courierCompany = courierCompanyReadDao
                    .get(memberProductExchange.getLogisticsId());
                memberProductExchange.setLogisticsName(courierCompany.getCompanyName());
                memberProductExchange.setLogisticsMark(courierCompany.getCompanyMark());
            }
            memberProductExchange.setOptId(user.getId());
            memberProductExchange.setOptName(user.getName());
            Integer update = memberProductExchangeWriteDao.update(memberProductExchange);
            if (update.intValue() == 0) {
                throw new BusinessException("商家处理换货信息失败，请重试");
            }

            // 保存换货日志
            MemberProductExchangeLog memberProductExchangeLog = new MemberProductExchangeLog();
            memberProductExchangeLog.setOperatingId(user.getId());
            memberProductExchangeLog.setOperatingName(user.getName());
            if (memberProductExchange.getState().intValue() == MemberProductExchange.STATE_2) {
                memberProductExchangeLog.setContent("商家同意换货");
            } else if (memberProductExchange.getState()
                .intValue() == MemberProductExchange.STATE_4) {
                memberProductExchangeLog.setContent("商家收到退件");
            } else if (memberProductExchange.getState()
                .intValue() == MemberProductExchange.STATE_5) {
                memberProductExchangeLog.setContent("商家发出换件");
            } else if (memberProductExchange.getState()
                .intValue() == MemberProductExchange.STATE_6) {
                memberProductExchangeLog.setContent("商家原件退换");
            } else if (memberProductExchange.getState()
                .intValue() == MemberProductExchange.STATE_7) {
                memberProductExchangeLog.setContent("商家拒绝换货");
            }
            memberProductExchangeLog.setMemberProductExchangeId(memberProductExchange.getId());
            Integer insert = memberProductExchangeLogWriteDao.insert(memberProductExchangeLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存换货日志失败，请重试！");
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
     * 用户换件时退件发货
     * @param memberProductExchange
     * @param member
     * @return
     */
    public Boolean doExchangeDeliverGoods(MemberProductExchange memberProductExchange,
                                          Member member) {
        // 事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            CourierCompany courierCompany = courierCompanyReadDao
                .get(memberProductExchange.getLogisticsId2());
            memberProductExchange.setLogisticsMark2(courierCompany.getCompanyMark());
            memberProductExchange.setLogisticsName2(courierCompany.getCompanyName());
            memberProductExchange.setOptId(member.getId());
            memberProductExchange.setOptName(member.getName());
            memberProductExchange.setState(MemberProductExchange.STATE_3);
            Integer update = memberProductExchangeWriteDao.update(memberProductExchange);
            if (update.intValue() == 0) {
                throw new BusinessException("用户换货时退件发货失败，请重试！");
            }

            // 保存换货日志
            MemberProductExchangeLog memberProductExchangeLog = new MemberProductExchangeLog();
            memberProductExchangeLog.setOperatingId(member.getId());
            memberProductExchangeLog.setOperatingName(member.getName());
            memberProductExchangeLog.setContent("用户发回退件");
            memberProductExchangeLog.setMemberProductExchangeId(memberProductExchange.getId());
            Integer insert = memberProductExchangeLogWriteDao.insert(memberProductExchangeLog);
            if (insert.intValue() == 0) {
                throw new BusinessException("保存换货日志失败，请重试！");
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
