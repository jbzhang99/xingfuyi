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
import com.yixiekeji.entity.integral.ActIntegralBanner;
import com.yixiekeji.model.promotion.ActIntegralBannerModel;
import com.yixiekeji.service.promotion.IActIntegralBannerService;

@RestController
public class ActIntegralBannerServiceImpl implements IActIntegralBannerService {
    private static Logger          log = LoggerFactory
        .getLogger(ActIntegralBannerServiceImpl.class);

    @Resource
    private ActIntegralBannerModel actIntegralBannerModel;

    @Override
    public ServiceResult<ActIntegralBanner> getActIntegralBannerById(@RequestParam("pcIndexBannerId") Integer actIntegralBannerId) {
        ServiceResult<ActIntegralBanner> result = new ServiceResult<ActIntegralBanner>();
        try {
            result.setResult(actIntegralBannerModel.getActIntegralBannerById(actIntegralBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActIntegralBannerService][getActIntegralBannerById]根据id["
                      + actIntegralBannerId + "]取得积分商城首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActIntegralBannerService][getActIntegralBannerById]根据id["
                      + actIntegralBannerId + "]取得积分商城首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveActIntegralBanner(@RequestBody ActIntegralBanner actIntegralBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralBannerModel.saveActIntegralBanner(actIntegralBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActIntegralBannerService][saveActIntegralBanner]保存积分商城首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActIntegralBannerService][saveActIntegralBanner]保存积分商城首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateActIntegralBanner(@RequestBody ActIntegralBanner actIntegralBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralBannerModel.updateActIntegralBanner(actIntegralBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActIntegralBannerService][updateActIntegralBanner]更新积分商城首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActIntegralBannerService][updateActIntegralBanner]更新积分商城首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteActIntegralBanner(@RequestParam("pcIndexBannerId") Integer actIntegralBannerId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(actIntegralBannerModel.deleteActIntegralBanner(actIntegralBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IActIntegralBannerService][deleteActIntegralBanner]删除积分商城首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActIntegralBannerService][deleteActIntegralBanner]删除积分商城首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ActIntegralBanner>> getActIntegralBanners(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ActIntegralBanner>> serviceResult = new ServiceResult<List<ActIntegralBanner>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(actIntegralBannerModel.getActIntegralBannersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(actIntegralBannerModel.getActIntegralBanners(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IActIntegralBannerService][getActIntegralBanners]根据条件取得积分商城首页轮播图时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IActIntegralBannerService][getActIntegralBanners]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IActIntegralBannerService][getActIntegralBanners]根据条件取得积分商城首页轮播图时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 查询积分商城首页轮播图
     * @param pcMobile
     * @return
     * @see com.yixiekeji.service.promotion.IActIntegralBannerService#getActIntegralBannersPcMobile(int)
     */
    @Override
    public ServiceResult<List<ActIntegralBanner>> getActIntegralBannersPcMobile(@RequestParam("pcMobile") int pcMobile) {
        ServiceResult<List<ActIntegralBanner>> serviceResult = new ServiceResult<List<ActIntegralBanner>>();
        try {
            serviceResult.setResult(actIntegralBannerModel.getActIntegralBannersPcMobile(pcMobile));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[IActIntegralBannerService][getActIntegralBannersPcMobile]根据条件取得积分商城首页轮播图时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[IActIntegralBannerService][getActIntegralBannersPcMobile]根据条件取得积分商城首页轮播图时发生异常:",
                e);
        }
        return serviceResult;
    }

}