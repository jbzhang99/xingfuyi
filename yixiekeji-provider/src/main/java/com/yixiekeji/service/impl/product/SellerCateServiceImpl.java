package com.yixiekeji.service.impl.product;

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
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.model.product.SellerCateModel;
import com.yixiekeji.service.product.ISellerCateService;

@RestController
public class SellerCateServiceImpl implements ISellerCateService {
    private static Logger   log = LoggerFactory.getLogger(SellerCateServiceImpl.class);

    @Resource
    private SellerCateModel sellerCateModel;

    @Override
    public ServiceResult<Boolean> saveSellerCate(@RequestBody SellerCate sellerCate) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerCateModel.saveSellerCate(sellerCate));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "SellerCateServiceImpl saveSellerCate param:" + JSON.toJSONString(sellerCate));
            log.error("SellerCateServiceImpl saveSellerCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateSellerCate(@RequestBody SellerCate sellerCate) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {

            serviceResult.setResult(sellerCateModel.updateSellerCate(sellerCate));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "SellerCateServiceImpl updateSellerCate param:" + JSON.toJSONString(sellerCate));
            log.error("SellerCateServiceImpl updateSellerCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delSellerCate(@RequestParam("id") Integer id,
                                                @RequestParam("sellerId") Integer sellerId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerCateModel.delSellerCate(id, sellerId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerCateServiceImpl delSellerCate sellerCateId:" + id);
            log.error("SellerCateServiceImpl delSellerCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SellerCate> getSellerCateById(@RequestParam("sellerCateId") Integer sellerCateId) {
        ServiceResult<SellerCate> serviceResult = new ServiceResult<SellerCate>();
        try {
            serviceResult.setResult(sellerCateModel.getSellerCateById(sellerCateId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerCateServiceImpl getSellerCateById id:" + sellerCateId);
            log.error("SellerCateServiceImpl getSellerCateById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SellerCate>> pageSellerCate(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<SellerCate>> serviceResult = new ServiceResult<List<SellerCate>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(sellerCateModel.pageSellerCateCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(sellerCateModel.pageSellerCate(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerCateServiceImpl pageSellerCate queryMap:" + JSON.toJSONString(queryMap)
                      + " pager:" + JSON.toJSONString(pager));
            log.error("SellerCateServiceImpl pageSellerCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SellerCate>> getByPid(@RequestParam("pid") Integer pid,
                                                    @RequestParam("sellerId") Integer sellerId) {
        ServiceResult<List<SellerCate>> serviceResult = new ServiceResult<List<SellerCate>>();
        try {
            serviceResult.setResult(sellerCateModel.getByPid(pid, sellerId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerCateServiceImpl pageSellerCate pid:" + pid);
            log.error("SellerCateServiceImpl pageSellerCate exception:", e);
        }
        return serviceResult;
    }

    /**
     * 获得所有商家分类（显示状态）
     * @param sellerId
     * @return
     */
    @Override
    public ServiceResult<List<SellerCate>> getOnuseSellerCate(@RequestParam("sellerId") Integer sellerId) {
        ServiceResult<List<SellerCate>> serviceResult = new ServiceResult<List<SellerCate>>();
        try {
            serviceResult.setResult(sellerCateModel.getOnuseSellerCate(sellerId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerCateServiceImpl][getOnuseSellerCate]获取商家分类列表时发生异常:", e);
        }

        return serviceResult;

    }
}