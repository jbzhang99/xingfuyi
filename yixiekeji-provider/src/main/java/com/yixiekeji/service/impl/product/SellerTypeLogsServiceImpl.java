package com.yixiekeji.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.SellerTypeLogs;
import com.yixiekeji.model.product.SellerTypeLogsModel;
import com.yixiekeji.service.product.ISellerTypeLogsService;

@RestController
public class SellerTypeLogsServiceImpl implements ISellerTypeLogsService {
    private static Logger       log = LoggerFactory.getLogger(SellerTypeLogsServiceImpl.class);

    @Resource
    private SellerTypeLogsModel sellerTypeLogsModel;

    @Override
    public ServiceResult<Boolean> saveSellerTypeLogs(@RequestBody SellerTypeLogs sellerTypeLogs) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerTypeLogsModel.saveSellerTypeLogs(sellerTypeLogs));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerTypeLogsServiceImpl saveSellerTypeLogs param:"
                      + JSON.toJSONString(sellerTypeLogs));
            log.error("SellerTypeLogsServiceImpl saveSellerTypeLogs exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateSellerTypeLogs(@RequestBody SellerTypeLogs sellerTypeLogs) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {

            serviceResult.setResult(sellerTypeLogsModel.updateSellerTypeLogs(sellerTypeLogs));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerTypeLogsServiceImpl updateSellerTypeLogs param:"
                      + JSON.toJSONString(sellerTypeLogs));
            log.error("SellerTypeLogsServiceImpl updateSellerTypeLogs exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delSellerTypeLogs(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerTypeLogsModel.delSellerTypeLogs(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerTypeLogsServiceImpl delSellerTypeLogs sellerTypeLogsId:" + id);
            log.error("SellerTypeLogsServiceImpl delSellerTypeLogs exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SellerTypeLogs> getSellerTypeLogsById(@RequestParam("sellerTypeLogsId") Integer sellerTypeLogsId) {
        ServiceResult<SellerTypeLogs> serviceResult = new ServiceResult<SellerTypeLogs>();
        try {
            serviceResult.setResult(sellerTypeLogsModel.getSellerTypeLogsById(sellerTypeLogsId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerTypeLogsServiceImpl getSellerTypeLogsById id:" + sellerTypeLogsId);
            log.error("SellerTypeLogsServiceImpl getSellerTypeLogsById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SellerTypeLogs>> getSellerTypeLogsByCateId(@RequestParam("cateId") Integer cateId) {
        ServiceResult<List<SellerTypeLogs>> serviceResult = new ServiceResult<List<SellerTypeLogs>>();
        try {
            serviceResult.setResult(sellerTypeLogsModel.getSellerTypeLogsByCateId(cateId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("SellerTypeLogsServiceImpl getSellerTypeLogsByCateId cateId:" + cateId);
            log.error("SellerTypeLogsServiceImpl getSellerTypeLogsByCateId exception:", e);
        }
        return serviceResult;
    }

}