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
import com.yixiekeji.entity.product.ProductAttr;
import com.yixiekeji.model.product.ProductAttrModel;
import com.yixiekeji.service.product.IProductAttrService;

@RestController
public class ProductAttrServiceImpl implements IProductAttrService {
    private static Logger    log = LoggerFactory.getLogger(ProductAttrServiceImpl.class);

    @Resource
    private ProductAttrModel productAttrModel;

    @Override
    public ServiceResult<Boolean> saveProductAttr(@RequestBody ProductAttr productAttr) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productAttrModel.saveProductAttr(productAttr));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductAttrServiceImpl saveProductAttr param:" + JSON.toJSONString(productAttr));
            log.error("ProductAttrServiceImpl saveProductAttr exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductAttr(@RequestBody ProductAttr productAttr) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productAttrModel.updateProductAttr(productAttr));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductAttrServiceImpl updateProductAttr param:" + JSON.toJSONString(productAttr));
            log.error("ProductAttrServiceImpl updateProductAttr exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delProductAttr(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productAttrModel.delProductAttr(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductAttrServiceImpl delProductAttr productAttrId:" + id);
            log.error("ProductAttrServiceImpl delProductAttr exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductAttr> getProductAttrById(@RequestParam("productAttrId") Integer productAttrId) {
        ServiceResult<ProductAttr> serviceResult = new ServiceResult<ProductAttr>();
        try {
            serviceResult.setResult(productAttrModel.getProductAttrById(productAttrId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductAttrServiceImpl getProductAttrById id:" + productAttrId);
            log.error("ProductAttrServiceImpl getProductAttrById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductAttr>> getProductAttrByProductId(@RequestParam("productId") Integer productId) {
        ServiceResult<List<ProductAttr>> serviceResult = new ServiceResult<List<ProductAttr>>();
        try {
            if (null == productId || 0 == productId)
                throw new BusinessException("根据商品id获取商品商品属性失败，商品id为空");
            serviceResult.setResult(productAttrModel.getByProductId(productId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductAttrServiceImpl getProductAttrByProductId productId:" + productId);
            log.error("ProductAttrServiceImpl getProductAttrByProductId exception:", e);
        }
        return serviceResult;
    }

}