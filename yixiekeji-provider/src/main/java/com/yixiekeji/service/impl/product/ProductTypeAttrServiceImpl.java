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
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.model.product.ProductTypeAttrModel;
import com.yixiekeji.service.product.IProductTypeAttrService;
import com.yixiekeji.vo.product.ProductTypeAttrVO;

@RestController
public class ProductTypeAttrServiceImpl implements IProductTypeAttrService {
    private static Logger        log = LoggerFactory.getLogger(ProductTypeAttrServiceImpl.class);

    @Resource
    private ProductTypeAttrModel productTypeAttrModel;

    @Override
    public ServiceResult<Boolean> saveProductTypeAttr(@RequestBody ProductTypeAttr productTypeAttr) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeAttrModel.saveProductTypeAttr(productTypeAttr));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeAttrServiceImpl saveProductTypeAttr param:"
                      + JSON.toJSONString(productTypeAttr));
            log.error("ProductTypeAttrServiceImpl saveProductTypeAttr exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductTypeAttr(@RequestBody ProductTypeAttr productTypeAttr) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeAttrModel.updateProductTypeAttr(productTypeAttr));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeAttrServiceImpl updateProductTypeAttr param:"
                      + JSON.toJSONString(productTypeAttr));
            log.error("ProductTypeAttrServiceImpl updateProductTypeAttr exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delProductTypeAttr(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productTypeAttrModel.delProductTypeAttr(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeAttrServiceImpl delProductTypeAttr productTypeAttrId:" + id);
            log.error("ProductTypeAttrServiceImpl delProductTypeAttr exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductTypeAttr> getProductTypeAttrById(@RequestParam("productTypeAttrId") Integer productTypeAttrId) {
        ServiceResult<ProductTypeAttr> serviceResult = new ServiceResult<ProductTypeAttr>();
        try {
            serviceResult.setResult(productTypeAttrModel.getProductTypeAttrById(productTypeAttrId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeAttrServiceImpl getProductTypeAttrById id:" + productTypeAttrId);
            log.error("ProductTypeAttrServiceImpl getProductTypeAttrById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductTypeAttr>> getProductTypeAttrByTypeId(@RequestParam("productTypeId") Integer productTypeId) {
        ServiceResult<List<ProductTypeAttr>> serviceResult = new ServiceResult<List<ProductTypeAttr>>();
        try {
            serviceResult.setResult(productTypeAttrModel.getProductTypeAttrByTypeId(productTypeId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductTypeAttrServiceImpl getProductTypeAttrById productTypeId:" + productTypeId);
            log.error("ProductTypeAttrServiceImpl getProductTypeAttrById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductTypeAttrVO>> pageProductTypeAttr(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<ProductTypeAttrVO>> serviceResult = new ServiceResult<List<ProductTypeAttrVO>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(productTypeAttrModel.pageProductTypeAttr(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductTypeAttrServiceImpl pageProductTypeAttr queryMap:"
                      + JSON.toJSONString(queryMap) + " pager:" + JSON.toJSONString(pager));
            log.error("ProductTypeAttrServiceImpl pageProductTypeAttr exception:", e);
        }
        return serviceResult;
    }
}
