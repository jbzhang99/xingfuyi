package com.yixiekeji.service.impl.pcseller;

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
import com.yixiekeji.entity.pcseller.PcSellerIndexBanner;
import com.yixiekeji.model.pcseller.PcSellerIndexBannerModel;
import com.yixiekeji.service.pcseller.IPcSellerIndexBannerService;

@RestController
public class PcSellerIndexBannerServiceImpl implements IPcSellerIndexBannerService {
    private static Logger            log = LoggerFactory
        .getLogger(PcSellerIndexBannerServiceImpl.class);

    @Resource
    private PcSellerIndexBannerModel pcSellerIndexBannerModel;

    @Override
    public ServiceResult<PcSellerIndexBanner> getPcSellerIndexBannerById(@RequestParam("pcSellerIndexBannerId") Integer pcSellerIndexBannerId) {
        ServiceResult<PcSellerIndexBanner> result = new ServiceResult<PcSellerIndexBanner>();
        try {
            result.setResult(
                pcSellerIndexBannerModel.getPcSellerIndexBannerById(pcSellerIndexBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerIndexBannerService][getPcSellerIndexBannerById]根据id["
                      + pcSellerIndexBannerId + "]取得PC端商家首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexBannerService][getPcSellerIndexBannerById]根据id["
                      + pcSellerIndexBannerId + "]取得PC端商家首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> savePcSellerIndexBanner(@RequestBody PcSellerIndexBanner pcSellerIndexBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerIndexBannerModel.savePcSellerIndexBanner(pcSellerIndexBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerIndexBannerService][savePcSellerIndexBanner]保存PC端商家首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexBannerService][savePcSellerIndexBanner]保存PC端商家首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updatePcSellerIndexBanner(@RequestBody PcSellerIndexBanner pcSellerIndexBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result
                .setResult(pcSellerIndexBannerModel.updatePcSellerIndexBanner(pcSellerIndexBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerIndexBannerService][updatePcSellerIndexBanner]更新PC端商家首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexBannerService][updatePcSellerIndexBanner]更新PC端商家首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deletePcSellerIndexBanner(@RequestParam("pcSellerIndexBannerId") Integer pcSellerIndexBannerId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                pcSellerIndexBannerModel.deletePcSellerIndexBanner(pcSellerIndexBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerIndexBannerService][deletePcSellerIndexBanner]删除PC端商家首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexBannerService][deletePcSellerIndexBanner]删除PC端商家首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<PcSellerIndexBanner>> getPcSellerIndexBanners(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<PcSellerIndexBanner>> serviceResult = new ServiceResult<List<PcSellerIndexBanner>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(pcSellerIndexBannerModel.getPcSellerIndexBannersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(pcSellerIndexBannerModel.getPcSellerIndexBanners(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IPcSellerIndexBannerService][getPcSellerIndexBanners]根据条件取得PC端商家首页轮播图时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexBannerService][getPcSellerIndexBanners]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error(
                "[IPcSellerIndexBannerService][getPcSellerIndexBanners]根据条件取得PC端商家首页轮播图时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<PcSellerIndexBanner>> getPcSellerIndexBannerForView(@RequestParam("sellerId") Integer sellerId,
                                                                                  @RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<PcSellerIndexBanner>> result = new ServiceResult<List<PcSellerIndexBanner>>();
        try {
            result.setResult(
                pcSellerIndexBannerModel.getPcSellerIndexBannerForView(sellerId, isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcSellerIndexBannerService][getPcSellerIndexBannerForView]取得PC端商家首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[IPcSellerIndexBannerService][getPcSellerIndexBannerForView]取得PC端商家首页轮播图时发生异常:",
                e);
        }
        return result;
    }

}