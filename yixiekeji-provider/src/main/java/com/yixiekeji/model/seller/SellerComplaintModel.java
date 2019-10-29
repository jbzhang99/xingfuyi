package com.yixiekeji.model.seller;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.order.OrdersProductReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerComplaintReadDao;
import com.yixiekeji.dao.shop.write.member.MemberProductBackLogWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberProductExchangeLogWriteDao;
import com.yixiekeji.dao.shop.write.order.OrdersWriteDao;
import com.yixiekeji.dao.shop.write.seller.SellerComplaintWriteDao;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberProductBackLog;
import com.yixiekeji.entity.member.MemberProductExchangeLog;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.vo.seller.FrontSellerComplaintVO;
import net.sf.cglib.beans.BeanCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投诉管理
 *
 */
@Component(value = "sellerComplaintModel")
public class SellerComplaintModel {
    private static Logger                    log = LoggerFactory.getLogger(SellerComplaintModel.class);

    @Resource
    private SellerComplaintWriteDao          sellerComplaintWriteDao;

    @Resource
    private SellerComplaintReadDao           sellerComplaintReadDao;

    @Resource
    private OrdersWriteDao                   ordersWriteDao;

    @Resource
    private OrdersReadDao                    ordersReadDao;

    @Resource
    private ProductReadDao                   productReadDao;

    @Resource
    private OrdersProductReadDao             ordersProductReadDao;

    @Resource
    private MemberProductBackLogWriteDao     memberProductBackLogWriteDao;
    @Resource
    private MemberProductExchangeLogWriteDao memberProductExchangeLogWriteDao;

    @Resource
    private DataSourceTransactionManager     transactionManager;

    /**
    * 根据id取得商家投诉管理
    * @param  sellerComplaintId
    * @return
    */
    public SellerComplaint getSellerComplaintById(Integer sellerComplaintId) {
        return sellerComplaintWriteDao.get(sellerComplaintId);
    }

    /**
     * 保存商家投诉管理
     * @param sellerComplaint
     * @return
     */
    public SellerComplaint saveSellerComplaint(Member member, SellerComplaint sellerComplaint) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 参数校验
            if (sellerComplaint == null) {
                throw new BusinessException("申诉申请不能为空，请重试！");
            } else if (sellerComplaint.getOrderProductId() == null
                       || sellerComplaint.getOrderProductId() == 0) {
                throw new BusinessException("申诉申请网单id不能为空，请重试！");
            } else if (sellerComplaint.getOrderId() == null || sellerComplaint.getOrderId() == 0) {
                throw new BusinessException("申诉申请订单id不能为空，请重试！");
            } else if ((sellerComplaint.getProductBackId() == null
                        || sellerComplaint.getProductBackId() == 0)
                       && (sellerComplaint.getProductExchangeId() == null
                           || sellerComplaint.getProductExchangeId() == 0)) {
                throw new BusinessException("申诉申请必须关联退换货申请中的一个，请重试！");
            } else if ((sellerComplaint.getProductBackId() != null
                        && sellerComplaint.getProductBackId() != 0)
                       && (sellerComplaint.getProductExchangeId() != null
                           && sellerComplaint.getProductExchangeId() != 0)) {
                throw new BusinessException("申诉申请只能关联退换货申请中的一个，请重试！");
            }

            //根据订单id取对应的订单信息 
            Orders order = ordersWriteDao.get(sellerComplaint.getOrderId());
            if (order == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请联系管理员！");
            } else {
                int orderState = order.getOrderState();
                if (orderState == Orders.ORDER_STATE_1 || orderState == Orders.ORDER_STATE_2
                    || orderState == Orders.ORDER_STATE_3 || orderState == Orders.ORDER_STATE_4) {

                    log.error("订单此时所处状态不允许提交申诉申请。");
                    throw new BusinessException("订单此时所处状态不允许提交申诉申请！");
                }
            }

            //根据条件取申诉信息 判断是否已申请过申诉
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("userId", member.getId());
            queryMap.put("orderProductId", sellerComplaint.getOrderProductId());
            List<SellerComplaint> beanList = sellerComplaintWriteDao.queryList(queryMap);
            if (beanList.size() > 0) {
                log.error("该产品已经提交过申诉了。");
                throw new BusinessException("该产品已经提交过申诉了！");
            }

            sellerComplaint.setUserId(member.getId());
            sellerComplaint.setUserName(member.getName());
            sellerComplaint.setState(ConstantsEJS.SELLER_COMPLAINT_1);

            //1、保存信息
            dbConstrains(sellerComplaint);
            Integer count = sellerComplaintWriteDao.save(sellerComplaint);
            if (count == 0) {
                throw new BusinessException("产品申诉申请保存失败，请重试！");
            }
            if (null != sellerComplaint.getProductBackId()
                && sellerComplaint.getProductBackId().intValue() != 0) {
                MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
                memberProductBackLog.setMemberProductBackId(sellerComplaint.getProductBackId());
                memberProductBackLog.setOperatingId(member.getId());
                memberProductBackLog.setOperatingName(member.getName());
                memberProductBackLog.setContent("用户投诉商家拒绝退货");
                Integer insert = memberProductBackLogWriteDao.insert(memberProductBackLog);
                if (insert.intValue() == 0) {
                    throw new BusinessException("保存退货信息日志失败。");
                }
            }

            if (null != sellerComplaint.getProductExchangeId()
                && sellerComplaint.getProductExchangeId().intValue() != 0) {

                // 保存换货日志
                MemberProductExchangeLog memberProductExchangeLog = new MemberProductExchangeLog();
                memberProductExchangeLog.setOperatingId(member.getId());
                memberProductExchangeLog.setOperatingName(member.getName());
                memberProductExchangeLog.setContent("用户投诉商家拒绝换货");
                memberProductExchangeLog
                    .setMemberProductExchangeId(sellerComplaint.getProductExchangeId());
                Integer insert = memberProductExchangeLogWriteDao.insert(memberProductExchangeLog);
                if (insert.intValue() == 0) {
                    throw new BusinessException("保存换货日志失败，请重试！");
                }

            }

            transactionManager.commit(status);
            return sellerComplaint;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    /**
    * 更新商家投诉管理
    * @param  sellerComplaint
    * @return
    */
    public Integer updateSellerComplaint(SellerComplaint sellerComplaint) {
        if (null == sellerComplaint)
            throw new BusinessException("", "1000");
        if (null == sellerComplaint.getId())
            throw new BusinessException("", "1001");

        dbConstrains(sellerComplaint);
        return sellerComplaintWriteDao.update(sellerComplaint);
    }

    /**
     * 根据登录用户获得申诉列表
     * @param pager
     * @return
     */
    public List<FrontSellerComplaintVO> getSellerComplaintList(PagerInfo pager, Integer memberId) {

        //根据用户id取所有的申诉
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("userId", memberId);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(sellerComplaintReadDao.queryCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        queryMap.put("start", start);
        queryMap.put("size", size);
        List<SellerComplaint> beanList = sellerComplaintReadDao.queryList(queryMap);

        List<FrontSellerComplaintVO> returnList = new ArrayList<FrontSellerComplaintVO>();
        for (SellerComplaint bean : beanList) {
            FrontSellerComplaintVO vo = new FrontSellerComplaintVO();

            //取对应的订单信息 根据订单id
            Orders order = ordersReadDao.get(bean.getOrderId());
            if (order == null) {
                log.error("订单信息获取失败。");
                throw new BusinessException("订单信息获取失败，请联系管理员！");
            }

            //获得对应网单信息
            OrdersProduct product = ordersProductReadDao.get(bean.getOrderProductId());

            final BeanCopier copier = BeanCopier.create(SellerComplaint.class,
                FrontSellerComplaintVO.class, false);
            copier.copy(bean, vo, null);
            vo.setProductName(product.getProductName());
            vo.setOrderSn(order.getOrderSn());
            vo.setProductId(product.getProductId());
            returnList.add(vo);
        }
        return returnList;
    }

    /**
     * 根据登录用户获得申诉列表（封装商品对象和网单对象）
     * @param pager
     * @param memberId
     * @return
     */
    public List<FrontSellerComplaintVO> getComplaintListWithPrdAndOp(PagerInfo pager,
                                                                     Integer memberId) {

        //根据用户id取所有的申诉
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("userId", memberId);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(sellerComplaintReadDao.queryCount(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        queryMap.put("start", start);
        queryMap.put("size", size);
        List<SellerComplaint> beanList = sellerComplaintReadDao.queryList(queryMap);

        List<FrontSellerComplaintVO> returnList = new ArrayList<FrontSellerComplaintVO>();
        for (SellerComplaint bean : beanList) {
            FrontSellerComplaintVO vo = new FrontSellerComplaintVO();
            //获得对应网单信息
            OrdersProduct ordersProduct = ordersProductReadDao.get(bean.getOrderProductId());
            final BeanCopier copier = BeanCopier.create(SellerComplaint.class,
                FrontSellerComplaintVO.class, false);
            copier.copy(bean, vo, null);
            vo.setProduct(productReadDao.get(ordersProduct.getProductId()));
            vo.setOrdersProduct(ordersProduct);
            returnList.add(vo);
        }
        return returnList;
    }

    private void dbConstrains(SellerComplaint sellerComplaint) {
        sellerComplaint.setImage(StringUtil.dbSafeString(sellerComplaint.getImage(), false, 255));
        sellerComplaint
            .setContent(StringUtil.dbSafeString(sellerComplaint.getContent(), false, 1000));
        sellerComplaint
            .setOptContent(StringUtil.dbSafeString(sellerComplaint.getOptContent(), false, 255));
        sellerComplaint.setSellerCompContent(
            StringUtil.dbSafeString(sellerComplaint.getSellerCompContent(), false, 1000));
        sellerComplaint.setSellerCompImage(
            StringUtil.dbSafeString(sellerComplaint.getSellerCompImage(), false, 255));
        sellerComplaint
            .setUserContent(StringUtil.dbSafeString(sellerComplaint.getUserContent(), false, 255));
        sellerComplaint
            .setUserName(StringUtil.dbSafeString(sellerComplaint.getUserName(), false, 50));

    }

    public Integer addSellerComplaint(SellerComplaint sellerComplaint) {
        return  sellerComplaintWriteDao.save(sellerComplaint);
    }
}
