package com.yixiekeji.service.impl.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralType;
import com.yixiekeji.model.promotion.ActIntegralModel;
import com.yixiekeji.service.promotion.IActIntegralService;

@RestController
public class ActIntegralServiceImpl implements IActIntegralService {
    private static Logger    log = LoggerFactory.getLogger(ActIntegralServiceImpl.class);

    @Resource
    private ActIntegralModel actIntegralModel;

    /**
    * 根据id取得积分商城
    * @param  actIntegralId
    * @return
    */
    @Override
    public ServiceResult<ActIntegral> getActIntegralById(@RequestParam("actIntegralId") Integer actIntegralId) {
        ServiceResult<ActIntegral> result = new ServiceResult<ActIntegral>();
        try {
            result.setResult(actIntegralModel.getActIntegralById(actIntegralId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralService][getActIntegralById]根据id[" + actIntegralId
                      + "]取得积分商城时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存积分商城
     * @param  actIntegral
     * @return
     */
    @Override
    public ServiceResult<Integer> saveActIntegral(@RequestBody ActIntegral actIntegral) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(actIntegralModel.saveActIntegral(actIntegral));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralService][saveActIntegral]保存积分商城时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新积分商城
    * @param  actIntegral
    * @return
    * @see com.yixiekeji.service.ActIntegralService#updateActIntegral(ActIntegral)
    */
    @Override
    public ServiceResult<Integer> updateActIntegral(@RequestBody ActIntegral actIntegral) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(actIntegralModel.updateActIntegral(actIntegral));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralService][updateActIntegral]更新积分商城时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 根据id取得积分商城分类
     * @param  actIntegralTypeId
     * @return
     */
    @Override
    public ServiceResult<ActIntegralType> getActIntegralTypeById(@RequestParam("actIntegralTypeId") Integer actIntegralTypeId) {
        ServiceResult<ActIntegralType> result = new ServiceResult<ActIntegralType>();
        try {
            result.setResult(actIntegralModel.getActIntegralTypeById(actIntegralTypeId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegralTypeById]根据id[" + actIntegralTypeId
                      + "]取得积分商城分类时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存积分商城分类
     * @param  actIntegralType
     * @return
     */
    @Override
    public ServiceResult<Integer> saveActIntegralType(@RequestBody ActIntegralType actIntegralType) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(actIntegralModel.saveActIntegralType(actIntegralType));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][saveActIntegralType]保存积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新积分商城分类
    * @param  actIntegralType
    * @return
    * @see com.yixiekeji.service.ActIntegralTypeService#updateActIntegralType(ActIntegralType)
    */
    @Override
    public ServiceResult<Integer> updateActIntegralType(@RequestBody ActIntegralType actIntegralType) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(actIntegralModel.updateActIntegralType(actIntegralType));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][updateActIntegralType]更新积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询积分商城分类
     * @param queryMap
     * @param pager
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#getActIntegralTypes(java.util.Map, com.yixiekeji.core.PagerInfo)
     */
    @Override
    public ServiceResult<List<ActIntegralType>> getActIntegralTypes(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ActIntegralType>> result = new ServiceResult<List<ActIntegralType>>();
        result.setPager(pager);
        try {
            result.setResult(actIntegralModel.getActIntegralTypes(queryMap, pager));
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegralTypes]查询积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 删除积分商城分类
     * @param id
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#delActIntegralType(java.lang.Integer)
     */
    @Override
    public ServiceResult<Boolean> delActIntegralType(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralModel.delActIntegralType(id));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][delActIntegralType]删除积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 启用积分商城分类
     * @param id
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#auditYesActIntegralType(java.lang.Integer)
     */
    @Override
    public ServiceResult<Boolean> auditYesActIntegralType(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralModel.auditYesActIntegralType(id));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][auditYesActIntegralType]启用积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 停用积分商城分类
     * @param id
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#auditNoActIntegralType(java.lang.Integer)
     */
    @Override
    public ServiceResult<Boolean> auditNoActIntegralType(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralModel.auditNoActIntegralType(id));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][auditNoActIntegralType]停用积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询所有积分商城信息
     * @param queryMap
     * @param pager
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#getActIntegrals(java.util.Map, com.yixiekeji.core.PagerInfo)
     */
    @Override
    public ServiceResult<List<ActIntegral>> getActIntegrals(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ActIntegral>> result = new ServiceResult<List<ActIntegral>>();
        result.setPager(pager);
        try {
            result.setResult(actIntegralModel.getActIntegrals(queryMap, pager));
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegrals]查询积分商城信息时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询所有可用的积分商城分类
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#getActIntegralTypesAll()
     */
    @Override
    public ServiceResult<List<ActIntegralType>> getActIntegralTypesAll() {
        ServiceResult<List<ActIntegralType>> result = new ServiceResult<List<ActIntegralType>>();
        try {
            result.setResult(actIntegralModel.getActIntegralTypesAll());
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegralTypesAll]查询所有可用的积分商城分类时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 更改积分商城的状态
     * @param id
     * @param state
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#updateActIntegralState(java.lang.Integer, int)
     */
    @Override
    public ServiceResult<Boolean> updateActIntegralState(@RequestParam("id") Integer id,
                                                         @RequestParam("state") int state) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralModel.updateActIntegralState(id, state));
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][updateActIntegralState]更改积分商城的状态时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 更改积分商城的活动状态
     * @param id
     * @param activityState
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#updateActIntegralActivityState(java.lang.Integer, int)
     */
    @Override
    public ServiceResult<Boolean> updateActIntegralActivityState(@RequestParam("id") Integer id,
                                                                 @RequestParam("activityState") int activityState) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralModel.updateActIntegralActivityState(id, activityState));
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IActIntegralTypeService][updateActIntegralActivityState]更改积分商城的活动状态时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 平台管理员审核，审核通过之后更改状态，并填写审核意见
     * @param id
     * @param state
     * @param auditOpinion
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#updateActIntegralStateAndAuditOpinion(java.lang.Integer, int, java.lang.String)
     */
    @Override
    public ServiceResult<Boolean> updateActIntegralStateAndAuditOpinion(@RequestParam("id") Integer id,
                                                                        @RequestParam("state") int state,
                                                                        @RequestParam("auditOpinion") String auditOpinion) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                actIntegralModel.updateActIntegralStateAndAuditOpinion(id, state, auditOpinion));
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IActIntegralTypeService][updateActIntegralStateAndAuditOpinion]平台管理员审核，审核通过之后更改状态，并填写审核意见出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 积分商城前台查看列表页
     * @param page
     * @param type
     * @param channel
     * @param grade
     * @param sort
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#getActIntegralsFront(com.yixiekeji.core.PagerInfo, int, int, int, int)
     */
    @Override
    public ServiceResult<List<ActIntegral>> getActIntegralsFront(@RequestBody PagerInfo page,
                                                                 @RequestParam("type") int type,
                                                                 @RequestParam("channel") int channel,
                                                                 @RequestParam("grade") int grade,
                                                                 @RequestParam("sort") int sort) {
        ServiceResult<List<ActIntegral>> result = new ServiceResult<List<ActIntegral>>();
        result.setPager(page);
        try {
            result
                .setResult(actIntegralModel.getActIntegralsFront(page, type, channel, grade, sort));
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegrals]查询积分商城信息时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询所有可用的积分商城分类
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralService#getActIntegralTypesFront()
     */
    @Override
    public ServiceResult<List<ActIntegralType>> getActIntegralTypesFront() {
        ServiceResult<List<ActIntegralType>> result = new ServiceResult<List<ActIntegralType>>();
        try {
            result.setResult(actIntegralModel.getActIntegralTypesFront());
        } catch (BusinessException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegralTypesFront]查询所有可用的积分商城分类时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ActIntegral>> getActIntegralsByType(@RequestParam("type") Integer type,
                                                                  @RequestParam("topNum") Integer topNum) {
        ServiceResult<List<ActIntegral>> result = new ServiceResult<List<ActIntegral>>();
        try {
            result.setResult(actIntegralModel.getActIntegralsByType(type, topNum));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IActIntegralTypeService][getActIntegralsByType]根据类型查询topNum条积分商城时出现未知异常：",
                e);
        }
        return result;
    }
}