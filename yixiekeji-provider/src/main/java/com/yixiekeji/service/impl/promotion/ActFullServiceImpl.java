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
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.model.promotion.ActFullModel;
import com.yixiekeji.service.promotion.IActFullService;

@RestController
public class ActFullServiceImpl implements IActFullService {
    private static Logger log = LoggerFactory.getLogger(ActFullServiceImpl.class);

    @Resource
    private ActFullModel  actFullModel;

    @Override
    public ServiceResult<ActFull> getActFullById(@RequestParam("actFullId") Integer actFullId) {
        ServiceResult<ActFull> result = new ServiceResult<ActFull>();
        try {
            result.setResult(actFullModel.getActFullById(actFullId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActFullService][getActFullById]根据id[" + actFullId + "]取得满减活动时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][getActFullById]根据id[" + actFullId + "]取得满减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveActFull(@RequestBody ActFull actFull) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actFullModel.saveActFull(actFull));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActFullService][saveActFull]保存满减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][saveActFull]保存满减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateActFull(@RequestBody ActFull actFull) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actFullModel.updateActFull(actFull));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActFullService][updateActFull]更新满减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][updateActFull]更新满减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateActFullStatus(@RequestBody ActFull actFull) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actFullModel.updateActFullStatus(actFull));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActFullService][updateActFullStatus]更新满减活动状态时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][updateActFullStatus]更新满减活动状态时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteActFull(@RequestParam("actFullId") Integer actFullId,
                                                @RequestParam("userId") Integer userId,
                                                @RequestParam("userName") String userName) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actFullModel.deleteActFull(actFullId, userId, userName));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActFullService][deleteActFull]删除满减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][deleteActFull]删除满减活动时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ActFull>> getActFulls(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ActFull>> serviceResult = new ServiceResult<List<ActFull>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(actFullModel.getActFullsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(actFullModel.getActFulls(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IActFullService][getActFulls]根据条件取得满减活动时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][getActFulls]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[IActFullService][getActFulls]根据条件取得满减活动时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ActFull> getEffectiveActFull(@RequestParam("sellerId") Integer sellerId,
                                                      @RequestParam("channel") Integer channel) {
        ServiceResult<ActFull> result = new ServiceResult<ActFull>();
        try {
            result.setResult(actFullModel.getEffectiveActFull(sellerId, channel));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IActFullService][getEffectiveActFull]根据商家ID和渠道取得满减活动时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActFullService][getEffectiveActFull]根据商家ID和渠道取得满减活动时发生异常:", e);
        }
        return result;
    }

}