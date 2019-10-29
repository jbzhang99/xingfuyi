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
import com.yixiekeji.entity.pcseller.PcSellerRecommend;
import com.yixiekeji.model.pcseller.PcSellerRecommendModel;
import com.yixiekeji.service.pcseller.IPcSellerRecommendService;

@RestController
public class PcSellerRecommendServiceImpl implements IPcSellerRecommendService {
    private static Logger          log = LoggerFactory
        .getLogger(PcSellerRecommendServiceImpl.class);

    @Resource
    private PcSellerRecommendModel pcSellerRecommendModel;

    @Override
    public ServiceResult<PcSellerRecommend> getPcSellerRecommendById(@RequestParam("pcSellerRecommendId") Integer pcSellerRecommendId) {
        ServiceResult<PcSellerRecommend> result = new ServiceResult<PcSellerRecommend>();
        try {
            result.setResult(pcSellerRecommendModel.getPcSellerRecommendById(pcSellerRecommendId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerRecommendService][getPcSellerRecommendById]根据id["
                      + pcSellerRecommendId + "]取得PC端商家推荐类型时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerRecommendService][getPcSellerRecommendById]根据id["
                      + pcSellerRecommendId + "]取得PC端商家推荐类型时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> savePcSellerRecommend(@RequestBody PcSellerRecommend pcSellerRecommend) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerRecommendModel.savePcSellerRecommend(pcSellerRecommend));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerRecommendService][savePcSellerRecommend]保存PC端商家推荐类型时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerRecommendService][savePcSellerRecommend]保存PC端商家推荐类型时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updatePcSellerRecommend(@RequestBody PcSellerRecommend pcSellerRecommend) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerRecommendModel.updatePcSellerRecommend(pcSellerRecommend));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerRecommendService][updatePcSellerRecommend]更新PC端商家推荐类型时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerRecommendService][updatePcSellerRecommend]更新PC端商家推荐类型时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deletePcSellerRecommend(@RequestParam("pcSellerRecommendId") Integer pcSellerRecommendId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerRecommendModel.deletePcSellerRecommend(pcSellerRecommendId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerRecommendService][deletePcSellerRecommend]删除PC端商家推荐类型时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerRecommendService][deletePcSellerRecommend]删除PC端商家推荐类型时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<PcSellerRecommend>> getPcSellerRecommends(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<PcSellerRecommend>> serviceResult = new ServiceResult<List<PcSellerRecommend>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(pcSellerRecommendModel.getPcSellerRecommendsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(pcSellerRecommendModel.getPcSellerRecommends(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IPcSellerRecommendService][getPcSellerRecommends]根据条件取得PC端商家推荐类型时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerRecommendService][getPcSellerRecommends]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IPcSellerRecommendService][getPcSellerRecommends]根据条件取得PC端商家推荐类型时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<PcSellerRecommend>> getPcSellerRecommendForView(@RequestParam("sellerId") Integer sellerId,
                                                                              @RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<PcSellerRecommend>> result = new ServiceResult<List<PcSellerRecommend>>();
        try {
            result
                .setResult(pcSellerRecommendModel.getPcSellerRecommendForView(sellerId, isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerRecommendService][getPcSellerRecommendForView]取得PC端商家推荐类型时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerRecommendService][getPcSellerRecommendForView]取得PC端商家推荐类型时发生异常:",
                e);
        }
        return result;
    }

}