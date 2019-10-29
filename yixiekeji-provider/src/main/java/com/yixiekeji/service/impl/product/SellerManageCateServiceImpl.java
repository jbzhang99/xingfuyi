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
import com.yixiekeji.entity.seller.SellerManageCate;
import com.yixiekeji.model.product.SellerManageCateModel;
import com.yixiekeji.service.product.ISellerManageCateService;

@RestController
public class SellerManageCateServiceImpl implements ISellerManageCateService {
    private static Logger         log = LoggerFactory.getLogger(SellerManageCateServiceImpl.class);

    @Resource
    private SellerManageCateModel sellerManageCateModel;

    @Override
    public ServiceResult<Boolean> saveSellerManageCate(@RequestBody SellerManageCate sellerManageCate) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerManageCateModel.saveSellerManageCate(sellerManageCate));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl saveSellerManageCate param:"
                      + JSON.toJSONString(sellerManageCate));
            log.error("SellerManageCateServiceImpl saveSellerManageCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateSellerManageCate(@RequestBody SellerManageCate sellerManageCate) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerManageCateModel.updateSellerManageCate(sellerManageCate));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl updateSellerManageCate param:"
                      + JSON.toJSONString(sellerManageCate));
            log.error("SellerManageCateServiceImpl updateSellerManageCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delSellerManageCate(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerManageCateModel.delSellerManageCate(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl delSellerManageCate sellerManageCateId:" + id);
            log.error("SellerManageCateServiceImpl delSellerManageCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> auditing(@RequestBody Map<String, String> map) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerManageCateModel.auditing(map));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl auditing map:" + JSON.toJSONString(map));
            log.error("SellerManageCateServiceImpl auditing exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> stop(@RequestBody Map<String, String> map) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerManageCateModel.stop(map));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl commit map:" + JSON.toJSONString(map));
            log.error("SellerManageCateServiceImpl commit exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> commit(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerManageCateModel.commit(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl commit id:" + id);
            log.error("SellerManageCateServiceImpl commit exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SellerManageCate> getSellerManageCateById(@RequestParam("sellerManageCateId") Integer sellerManageCateId) {
        ServiceResult<SellerManageCate> serviceResult = new ServiceResult<SellerManageCate>();
        try {
            serviceResult
                .setResult(sellerManageCateModel.getSellerManageCateById(sellerManageCateId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "SellerManageCateServiceImpl getSellerManageCateById id:" + sellerManageCateId);
            log.error("SellerManageCateServiceImpl getSellerManageCateById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SellerManageCate>> pageSellerManageCate(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SellerManageCate>> serviceResult = new ServiceResult<List<SellerManageCate>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(sellerManageCateModel.pageSellerManageCateCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<SellerManageCate> list = sellerManageCateModel.pageSellerManageCate(queryMap,
                start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerManageCateServiceImpl pageSellerManageCate queryMap:"
                      + JSON.toJSONString(queryMap) + " pager:" + JSON.toJSONString(pager));
            log.error("SellerManageCateServiceImpl pageSellerManageCate exception:", e);
        }
        return serviceResult;
    }

}