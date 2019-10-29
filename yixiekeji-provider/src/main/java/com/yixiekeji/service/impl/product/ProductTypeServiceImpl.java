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
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.model.product.ProductTypeModel;
import com.yixiekeji.service.product.IProductTypeService;
import com.yixiekeji.util.FeignProjectUtil;

@RestController
public class ProductTypeServiceImpl implements IProductTypeService {
    private static Logger    log = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    @Resource
    private ProductTypeModel productTypeModel;

    @Override
    public ServiceResult<Boolean> saveProductType(@RequestBody ProductType productType) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeModel.saveProductType(productType));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductTypeServiceImpl saveProductType param:" + JSON.toJSONString(productType));
            log.error("ProductTypeServiceImpl saveProductType exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveProductTypeMap(@RequestBody FeignProjectUtil feignProjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeModel.saveProductType(feignProjectUtil));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeServiceImpl saveProductType exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductType(@RequestBody ProductType productType) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeModel.updateProductType(productType));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductTypeServiceImpl updateProductType param:" + JSON.toJSONString(productType));
            log.error("ProductTypeServiceImpl updateProductType exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductTypeMap(@RequestBody FeignProjectUtil feignProjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeModel.updateProductType(feignProjectUtil));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeServiceImpl updateProductType exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delProductType(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeModel.delProductType(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeServiceImpl delProductType id:" + id);
            log.error("ProductTypeServiceImpl delProductType exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductType> getProductTypeById(@RequestParam("productTypeId") Integer productTypeId) {
        ServiceResult<ProductType> serviceResult = new ServiceResult<ProductType>();
        try {

            serviceResult.setResult(productTypeModel.getProductTypeById(productTypeId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeServiceImpl getProductTypeById id:" + productTypeId);
            log.error("ProductTypeServiceImpl getProductTypeById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductTypeAttr>> getAttrByTypeId(@RequestParam("productTypeId") Integer productTypeId) {
        ServiceResult<List<ProductTypeAttr>> serviceResult = new ServiceResult<List<ProductTypeAttr>>();
        try {
            serviceResult.setResult(productTypeModel.getAttrByTypeId(productTypeId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeServiceImpl getProductTypeById id:" + productTypeId);
            log.error("ProductTypeServiceImpl getProductTypeById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductType>> pageProductType(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductType>> serviceResult = new ServiceResult<List<ProductType>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(productTypeModel.pageProductType(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeServiceImpl pageProductType queryMap:"
                      + JSON.toJSONString(queryMap) + " pager:" + JSON.toJSONString(pager));
            log.error("ProductTypeServiceImpl pageProductType exception:", e);
        }
        return serviceResult;
    }
}
