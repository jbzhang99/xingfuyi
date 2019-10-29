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
import com.yixiekeji.entity.pcseller.PcSellerIndex;
import com.yixiekeji.model.pcseller.PcSellerIndexModel;
import com.yixiekeji.service.pcseller.IPcSellerIndexService;

@RestController
public class PcSellerIndexServiceImpl implements IPcSellerIndexService {
    private static Logger      log = LoggerFactory.getLogger(PcSellerIndexServiceImpl.class);

    @Resource
    private PcSellerIndexModel pcSellerIndexModel;

    @Override
    public ServiceResult<PcSellerIndex> getPcSellerIndexById(@RequestParam("pcSellerIndexId") Integer pcSellerIndexId) {
        ServiceResult<PcSellerIndex> result = new ServiceResult<PcSellerIndex>();
        try {
            result.setResult(pcSellerIndexModel.getPcSellerIndexById(pcSellerIndexId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerIndexService][getPcSellerIndexById]根据id[" + pcSellerIndexId
                      + "]取得PC端商家首页信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexService][getPcSellerIndexById]根据id[" + pcSellerIndexId
                      + "]取得PC端商家首页信息时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<PcSellerIndex> getPcSellerIndexBySellerId(@RequestParam("sellerId") Integer sellerId) {
        ServiceResult<PcSellerIndex> result = new ServiceResult<PcSellerIndex>();
        try {
            result.setResult(pcSellerIndexModel.getPcSellerIndexBySellerId(sellerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcSellerIndexService][getPcSellerIndexById]根据商家id[" + sellerId
                      + "]取得PC端商家首页信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexService][getPcSellerIndexById]根据商家id[" + sellerId
                      + "]取得PC端商家首页信息时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> savePcSellerIndex(@RequestBody PcSellerIndex pcSellerIndex) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerIndexModel.savePcSellerIndex(pcSellerIndex));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcSellerIndexService][savePcSellerIndex]保存PC端商家首页信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexService][savePcSellerIndex]保存PC端商家首页信息时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updatePcSellerIndex(@RequestBody PcSellerIndex pcSellerIndex) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerIndexModel.updatePcSellerIndex(pcSellerIndex));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcSellerIndexService][updatePcSellerIndex]更新PC端商家首页信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexService][updatePcSellerIndex]更新PC端商家首页信息时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deletePcSellerIndex(@RequestParam("pcSellerIndexId") Integer pcSellerIndexId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcSellerIndexModel.deletePcSellerIndex(pcSellerIndexId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcSellerIndexService][deletePcSellerIndex]删除PC端商家首页信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexService][deletePcSellerIndex]删除PC端商家首页信息时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<PcSellerIndex>> getPcSellerIndexs(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<PcSellerIndex>> serviceResult = new ServiceResult<List<PcSellerIndex>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(pcSellerIndexModel.getPcSellerIndexsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(pcSellerIndexModel.getPcSellerIndexs(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[IPcSellerIndexService][getPcSellerIndexs]根据条件取得PC端商家首页信息时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcSellerIndexService][getPcSellerIndexs]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IPcSellerIndexService][getPcSellerIndexs]根据条件取得PC端商家首页信息时发生异常:", e);
        }
        return serviceResult;
    }

}