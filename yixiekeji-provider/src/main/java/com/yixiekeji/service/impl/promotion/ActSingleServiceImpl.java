package com.yixiekeji.service.impl.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.model.promotion.ActSingleModel;
import com.yixiekeji.service.promotion.IActSingleService;

@RestController
public class ActSingleServiceImpl implements IActSingleService {
    private static Logger  log = LoggerFactory.getLogger(ActSingleServiceImpl.class);

    @Resource
    private ActSingleModel actSingleModel;

    @Override
    public ServiceResult<ActSingle> getActSingleById(@RequestParam("actSingleId") Integer actSingleId) {
        ServiceResult<ActSingle> result = new ServiceResult<ActSingle>();
        try {
            result.setResult(actSingleModel.getActSingleById(actSingleId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActSingleService][getActSingleById]根据id[" + actSingleId + "]取得单品立减活动时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[IActSingleService][getActSingleById]根据id[" + actSingleId + "]取得单品立减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveActSingle(@RequestBody ActSingle actSingle) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actSingleModel.saveActSingle(actSingle));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActSingleService][saveActSingle]保存单品立减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActSingleService][saveActSingle]保存单品立减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateActSingle(@RequestBody ActSingle actSingle) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actSingleModel.updateActSingle(actSingle));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActSingleService][updateActSingle]更新单品立减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActSingleService][updateActSingle]更新单品立减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateActSingleStatus(@RequestBody ActSingle actSingle) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actSingleModel.updateActSingleStatus(actSingle));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IActSingleService][updateActSingleStatus]更新单品立减活动状态时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActSingleService][updateActSingleStatus]更新单品立减活动状态时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteActSingle(@RequestParam("actSingleId") Integer actSingleId,
                                                  @RequestParam("userId") Integer userId,
                                                  @RequestParam("userName") String userName) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actSingleModel.deleteActSingle(actSingleId, userId, userName));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActSingleService][deleteActSingle]删除单品立减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActSingleService][deleteActSingle]删除单品立减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ActSingle>> getActSingles(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ActSingle>> serviceResult = new ServiceResult<List<ActSingle>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(actSingleModel.getActSinglesCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(actSingleModel.getActSingles(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IActSingleService][getActSingles]根据条件取得单品立减活动时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActSingleService][getActSingles]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[IActSingleService][getActSingles]根据条件取得单品立减活动时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ActSingle> getEffectiveActSingle(@RequestParam("sellerId") Integer sellerId,
                                                          @RequestParam("channel") Integer channel,
                                                          @RequestParam("productId") Integer productId) {
        ServiceResult<ActSingle> result = new ServiceResult<ActSingle>();
        try {
            result.setResult(actSingleModel.getEffectiveActSingle(sellerId, channel, productId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActSingleService][getEffectiveActSingle]根据商家ID、渠道、商品ID取得单品立减活动时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActSingleService][getEffectiveActSingle]根据商家ID、渠道、商品ID取得单品立减活动时发生异常:", e);
        }
        return result;
    }

}