package com.yixiekeji.model.promotion;

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
import com.yixiekeji.dao.shop.read.integral.ActIntegralReadDao;
import com.yixiekeji.dao.shop.read.integral.ActIntegralTypeReadDao;
import com.yixiekeji.dao.shop.write.integral.ActIntegralTypeWriteDao;
import com.yixiekeji.dao.shop.write.integral.ActIntegralWriteDao;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralType;

@Component(value = "actIntegralModel")
public class ActIntegralModel {

    @Resource
    private ActIntegralWriteDao          actIntegralWriteDao;

    @Resource
    private ActIntegralReadDao           actIntegralReadDao;

    @Resource
    private ActIntegralTypeWriteDao      actIntegralTypeWriteDao;

    @Resource
    private ActIntegralTypeReadDao       actIntegralTypeReadDao;

    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得积分商城分类
     * @param  actIntegralTypeId
     * @return
     */
    public ActIntegralType getActIntegralTypeById(Integer actIntegralTypeId) {
        return actIntegralTypeReadDao.get(actIntegralTypeId);
    }

    /**
     * 保存积分商城分类
     * @param  actIntegralType
     * @return
     */
    public Integer saveActIntegralType(ActIntegralType actIntegralType) {
        this.dbActIntegralTypeConstrains(actIntegralType);
        return actIntegralTypeWriteDao.insert(actIntegralType);
    }

    /**
    * 更新积分商城分类
    * @param  actIntegralType
    * @return
    */
    public Integer updateActIntegralType(ActIntegralType actIntegralType) {
        this.dbActIntegralTypeConstrains(actIntegralType);
        return actIntegralTypeWriteDao.update(actIntegralType);
    }

    /**
     * 根据id取得积分商城
     * @param  actIntegralId
     * @return
     */
    public ActIntegral getActIntegralById(Integer actIntegralId) {
        return actIntegralReadDao.get(actIntegralId);
    }

    /**
     * 保存积分商城
     * @param  actIntegral
     * @return
     */
    public Integer saveActIntegral(ActIntegral actIntegral) {
        this.dbActIntegralConstrains(actIntegral);
        return actIntegralWriteDao.insert(actIntegral);
    }

    /**
    * 更新积分商城
    * @param  actIntegral
    * @return
    */
    public Integer updateActIntegral(ActIntegral actIntegral) {
        this.dbActIntegralConstrains(actIntegral);
        return actIntegralWriteDao.update(actIntegral);
    }

    private void dbActIntegralConstrains(ActIntegral actIntegral) {
        actIntegral.setName(StringUtil.dbSafeString(actIntegral.getName(), false, 65535));
        actIntegral.setDescinfo(StringUtil.dbSafeString(actIntegral.getDescinfo(), false, 65535));
        actIntegral.setAuditName(StringUtil.dbSafeString(actIntegral.getAuditName(), true, 50));
        actIntegral.setReason(StringUtil.dbSafeString(actIntegral.getReason(), true, 200));
        actIntegral
            .setAuditOpinion(StringUtil.dbSafeString(actIntegral.getAuditOpinion(), false, 255));
    }

    private void dbActIntegralTypeConstrains(ActIntegralType actIntegralType) {
        actIntegralType.setName(StringUtil.dbSafeString(actIntegralType.getName(), false, 20));
        actIntegralType
            .setCreateName(StringUtil.dbSafeString(actIntegralType.getCreateName(), true, 50));
        actIntegralType
            .setUpdateName(StringUtil.dbSafeString(actIntegralType.getUpdateName(), true, 50));
    }

    /**
     * 分页查询积分商城分类
     * @param queryMap
     * @param pager
     * @return
     */
    public List<ActIntegralType> getActIntegralTypes(Map<String, String> queryMap,
                                                     PagerInfo pager) {
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(actIntegralTypeReadDao.count(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return actIntegralTypeReadDao.getActIntegralTypes(queryMap, start, size);
    }

    /**
     * 分页查询积分商城
     * @param queryMap
     * @param pager
     * @return
     */
    public List<ActIntegral> getActIntegrals(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(actIntegralReadDao.count(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return actIntegralReadDao.getActIntegrals(queryMap, start, size);
    }

    /**
     * 删除积分商城分类
     * @param id
     * @return
     */
    public Boolean delActIntegralType(Integer id) {
        int count = actIntegralWriteDao.countByType(id);
        if (count > 0) {
            throw new BusinessException("此分类下面有商品，不能进行删除操作。");
        }
        return actIntegralTypeWriteDao.del(id) > 0;
    }

    /**
     * 积分商城分类的启用
     * @param id
     * @return
     */
    public Boolean auditYesActIntegralType(Integer id) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        boolean mark = false;
        try {
            ActIntegralType actIntegralType = actIntegralTypeWriteDao.get(id);
            if (actIntegralType.getState().intValue() != ActIntegralType.ACTGROUPTYPE_STATE_0) {
                throw new BusinessException("状态只有停用才能启用");
            }
            mark = actIntegralTypeWriteDao.audit(id, ActIntegralType.ACTGROUPTYPE_STATE_1) > 0;

            actIntegralWriteDao.updateByTypeState(id, ActIntegral.TYPE_STATE_1);

            transactionManager.commit(status);
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return mark;
    }

    /**
     * 积分商城分类的停用
     * @param id
     * @return
     */
    public Boolean auditNoActIntegralType(Integer id) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        boolean mark = false;
        try {
            ActIntegralType actIntegralType = actIntegralTypeWriteDao.get(id);
            if (actIntegralType.getState().intValue() != ActIntegralType.ACTGROUPTYPE_STATE_1) {
                throw new BusinessException("状态只有停用才能启用");
            }
            mark = actIntegralTypeWriteDao.audit(id, ActIntegralType.ACTGROUPTYPE_STATE_0) > 0;

            actIntegralWriteDao.updateByTypeState(id, ActIntegral.TYPE_STATE_0);

            transactionManager.commit(status);
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return mark;
    }

    /**
     * 查询所有可用的积分商城分类
     * @return
     */
    public List<ActIntegralType> getActIntegralTypesAll() {
        return actIntegralTypeReadDao.getAll();
    }

    /**
     * 更改积分商城的审核状态
     * @param id
     * @param state
     * @return
     */
    public Boolean updateActIntegralState(Integer id, int state) {
        return actIntegralWriteDao.updateState(id, state) > 0;
    }

    /**
     * 更改积分商城的活动状态
     * @param id
     * @param activityState
     * @return
     */
    public Boolean updateActIntegralActivityState(Integer id, int activityState) {
        return actIntegralWriteDao.updateActivityState(id, activityState) > 0;
    }

    /**
     * 平台管理员审核，审核通过之后更改状态，并填写审核意见
     * @param id
     * @param state
     * @param auditOpinion
     * @return
     */
    public Boolean updateActIntegralStateAndAuditOpinion(Integer id, int state,
                                                         String auditOpinion) {
        return actIntegralWriteDao.updateStateAndAuditOpinion(id, state, auditOpinion) > 0;
    }

    /**
     * 积分商城前台查看列表页
     * @param pager
     * @param type
     * @param channel
     * @param grade
     * @param sort
     * @return
     */
    public List<ActIntegral> getActIntegralsFront(PagerInfo pager, int type, int channel, int grade,
                                                  int sort) {
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(actIntegralReadDao.countFront(type, channel, grade));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        return actIntegralReadDao.getActIntegralsFront(type, channel, start, size, grade, sort);
    }

    /**
     * 查询所有可用的积分商城分类
     * @return
     */
    public List<ActIntegralType> getActIntegralTypesFront() {
        return actIntegralTypeReadDao.getAll();
    }

    /**
     * 根据类型查询topNum条积分商城
     * @param type
     * @param topNum
     * @return
     */
    public List<ActIntegral> getActIntegralsByType(Integer type, Integer topNum) {
        return actIntegralReadDao.getActIntegralsByType(type, topNum);
    }

}
