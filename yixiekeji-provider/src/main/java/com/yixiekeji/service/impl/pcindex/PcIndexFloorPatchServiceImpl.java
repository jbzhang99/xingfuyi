package com.yixiekeji.service.impl.pcindex;

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
import com.yixiekeji.entity.pcindex.PcIndexFloorPatch;
import com.yixiekeji.model.pcindex.PcIndexFloorPatchModel;
import com.yixiekeji.service.pcindex.IPcIndexFloorPatchService;

@RestController
public class PcIndexFloorPatchServiceImpl implements IPcIndexFloorPatchService {
    private static Logger          log = LoggerFactory
        .getLogger(PcIndexFloorPatchServiceImpl.class);

    @Resource
    private PcIndexFloorPatchModel pcIndexFloorPatchModel;

    @Override
    public ServiceResult<PcIndexFloorPatch> getPcIndexFloorPatchById(@RequestParam("pcIndexFloorPatchId") Integer pcIndexFloorPatchId) {
        ServiceResult<PcIndexFloorPatch> result = new ServiceResult<PcIndexFloorPatch>();
        try {
            result.setResult(pcIndexFloorPatchModel.getPcIndexFloorPatchById(pcIndexFloorPatchId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchById]根据id["
                      + pcIndexFloorPatchId + "]取得PC端首页楼层碎屑时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchById]根据id["
                      + pcIndexFloorPatchId + "]取得PC端首页楼层碎屑时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> savePcIndexFloorPatch(@RequestBody PcIndexFloorPatch pcIndexFloorPatch) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcIndexFloorPatchModel.savePcIndexFloorPatch(pcIndexFloorPatch));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexFloorPatchService][savePcIndexFloorPatch]保存PC端首页楼层碎屑时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexFloorPatchService][savePcIndexFloorPatch]保存PC端首页楼层碎屑时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updatePcIndexFloorPatch(@RequestBody PcIndexFloorPatch pcIndexFloorPatch) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcIndexFloorPatchModel.updatePcIndexFloorPatch(pcIndexFloorPatch));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexFloorPatchService][updatePcIndexFloorPatch]更新PC端首页楼层碎屑时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexFloorPatchService][updatePcIndexFloorPatch]更新PC端首页楼层碎屑时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deletePcIndexFloorPatch(@RequestParam("pcIndexFloorPatchId") Integer pcIndexFloorPatchId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcIndexFloorPatchModel.deletePcIndexFloorPatch(pcIndexFloorPatchId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexFloorPatchService][deletePcIndexFloorPatch]删除PC端首页楼层碎屑时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexFloorPatchService][deletePcIndexFloorPatch]删除PC端首页楼层碎屑时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<PcIndexFloorPatch>> getPcIndexFloorPatchs(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<PcIndexFloorPatch>> serviceResult = new ServiceResult<List<PcIndexFloorPatch>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(pcIndexFloorPatchModel.getPcIndexFloorPatchsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(pcIndexFloorPatchModel.getPcIndexFloorPatchs(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchs]根据条件取得PC端首页楼层碎屑时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchs]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchs]根据条件取得PC端首页楼层碎屑时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<PcIndexFloorPatch>> getPcIndexFloorPatchForView(@RequestParam("floorId") Integer floorId,
                                                                              @RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<PcIndexFloorPatch>> result = new ServiceResult<List<PcIndexFloorPatch>>();
        try {
            result
                .setResult(pcIndexFloorPatchModel.getPcIndexFloorPatchForView(floorId, isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchForView]取得PC端首页楼层碎屑时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexFloorPatchService][getPcIndexFloorPatchForView]取得PC端首页楼层碎屑时发生异常:",
                e);
        }
        return result;
    }

}