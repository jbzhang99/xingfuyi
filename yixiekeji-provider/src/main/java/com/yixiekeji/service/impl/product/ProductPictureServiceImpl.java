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
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.model.product.ProductPictureModel;
import com.yixiekeji.service.product.IProductPictureService;

@RestController
public class ProductPictureServiceImpl implements IProductPictureService {
    private static Logger       log = LoggerFactory.getLogger(ProductPictureServiceImpl.class);

    @Resource
    private ProductPictureModel productPictureModel;

    @Override
    public ServiceResult<Boolean> saveProductPicture(@RequestBody ProductPicture productPicture) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productPictureModel.saveProductPicture(productPicture));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductPictureServiceImpl saveProductPicture param:"
                      + JSON.toJSONString(productPicture));
            log.error("ProductPictureServiceImpl saveProductPicture exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductPicture(@RequestBody ProductPicture productPicture) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productPictureModel.updateProductPicture(productPicture));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductPictureServiceImpl updateProductPicture param:"
                      + JSON.toJSONString(productPicture));
            log.error("ProductPictureServiceImpl updateProductPicture exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delProductPicture(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productPictureModel.delProductPicture(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductPictureServiceImpl delProductPicture productPictureId:" + id);
            log.error("ProductPictureServiceImpl delProductPicture exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductPicture> getProductPictureById(@RequestParam("productPictureId") Integer productPictureId) {
        ServiceResult<ProductPicture> serviceResult = new ServiceResult<ProductPicture>();
        try {
            serviceResult.setResult(productPictureModel.getProductPictureById(productPictureId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductPictureServiceImpl getProductPictureById id:" + productPictureId);
            log.error("ProductPictureServiceImpl getProductPictureById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductPicture>> pageProductPicture(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductPicture>> serviceResult = new ServiceResult<List<ProductPicture>>();
        serviceResult.setPager(pager);

        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(productPictureModel.pageProductPictureCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<ProductPicture> list = productPictureModel.pageProductPicture(queryMap, start,
                size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductPictureServiceImpl pageProductPicture queryMap:"
                      + JSON.toJSONString(queryMap) + " pager:" + JSON.toJSONString(pager));
            log.error("ProductPictureServiceImpl pageProductPicture exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductPicture>> getProductPictureByProductId(@RequestParam("productId") Integer productId) {
        ServiceResult<List<ProductPicture>> serviceResult = new ServiceResult<List<ProductPicture>>();
        try {
            serviceResult.setResult(productPictureModel.getProductPictureByProductId(productId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductPictureServiceImpl getProductPictureByProductId productId:" + productId);
            log.error("ProductPictureServiceImpl pageProductPicture exception:", e);
        }
        return serviceResult;
    }

}