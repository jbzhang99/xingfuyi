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
import com.yixiekeji.entity.pcindex.PcIndexImage;
import com.yixiekeji.model.pcindex.PcIndexImageModel;
import com.yixiekeji.service.pcindex.IPcIndexImageService;

@RestController
public class PcIndexImageServiceImpl implements IPcIndexImageService {
    private static Logger     log = LoggerFactory.getLogger(PcIndexImageServiceImpl.class);

    @Resource
    private PcIndexImageModel pcIndexImageModel;

    @Override
    public ServiceResult<PcIndexImage> getPcIndexImageById(@RequestParam("pcIndexImageId") Integer pcIndexImageId) {
        ServiceResult<PcIndexImage> result = new ServiceResult<PcIndexImage>();
        try {
            result.setResult(pcIndexImageModel.getPcIndexImageById(pcIndexImageId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexImageService][getPcIndexImageById]根据id[" + pcIndexImageId
                      + "]取得PC端首页的一些图片时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexImageService][getPcIndexImageById]根据id[" + pcIndexImageId
                      + "]取得PC端首页的一些图片时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> savePcIndexImage(@RequestBody PcIndexImage pcIndexImage) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcIndexImageModel.savePcIndexImage(pcIndexImage));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcIndexImageService][savePcIndexImage]保存PC端首页的一些图片时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexImageService][savePcIndexImage]保存PC端首页的一些图片时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updatePcIndexImage(@RequestBody PcIndexImage pcIndexImage) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcIndexImageModel.updatePcIndexImage(pcIndexImage));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcIndexImageService][updatePcIndexImage]更新PC端首页的一些图片时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexImageService][updatePcIndexImage]更新PC端首页的一些图片时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deletePcIndexImage(@RequestParam("pcIndexImageId") Integer pcIndexImageId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(pcIndexImageModel.deletePcIndexImage(pcIndexImageId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IPcIndexImageService][deletePcIndexImage]删除PC端首页的一些图片时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexImageService][deletePcIndexImage]删除PC端首页的一些图片时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<PcIndexImage>> getPcIndexImages(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<PcIndexImage>> serviceResult = new ServiceResult<List<PcIndexImage>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(pcIndexImageModel.getPcIndexImagesCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(pcIndexImageModel.getPcIndexImages(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[IPcIndexImageService][getPcIndexImages]根据条件取得PC端首页的一些图片时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexImageService][getPcIndexImages]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IPcIndexImageService][getPcIndexImages]根据条件取得PC端首页的一些图片时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<PcIndexImage>> getPcIndexImageForView(@RequestParam("isPreview") Boolean isPreview,
                                                                    @RequestParam("type") int type) {
        ServiceResult<List<PcIndexImage>> result = new ServiceResult<List<PcIndexImage>>();
        try {
            result.setResult(pcIndexImageModel.getPcIndexImageForView(isPreview, type));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IPcIndexImageService][getPcIndexImageForView]取得PC端首页的一些图片时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IPcIndexImageService][getPcIndexImageForView]取得PC端首页的一些图片时发生异常:", e);
        }
        return result;
    }

}