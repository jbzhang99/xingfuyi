package com.yixiekeji.service.impl.product;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductNormAttrOpt;
import com.yixiekeji.model.product.ProductNormAttrOptModel;
import com.yixiekeji.service.product.IProductNormAttrOptService;

@RestController
public class ProductNormAttrOptServiceImpl implements IProductNormAttrOptService {
    private static Logger           log = LoggerFactory
        .getLogger(ProductNormAttrOptServiceImpl.class);

    @Resource
    private ProductNormAttrOptModel productNormAttrOptModel;

    /**
    * 根据id取得商品选定的规格属性(保存商品插入，暂时不用)
    * @param  productNormAttrOptId
    * @return
    */
    @Override
    public ServiceResult<ProductNormAttrOpt> getProductNormAttrOptById(@RequestParam("productNormAttrOptId") Integer productNormAttrOptId) {
        ServiceResult<ProductNormAttrOpt> result = new ServiceResult<ProductNormAttrOpt>();
        try {
            result
                .setResult(productNormAttrOptModel.getProductNormAttrOptById(productNormAttrOptId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IProductNormAttrOptService][getProductNormAttrOptById]根据id["
                      + productNormAttrOptId + "]取得商品选定的规格属性(保存商品插入，暂时不用)时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IProductNormAttrOptService][getProductNormAttrOptById]根据id["
                      + productNormAttrOptId + "]取得商品选定的规格属性(保存商品插入，暂时不用)时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存商品选定的规格属性(保存商品插入，暂时不用)
     * @param  productNormAttrOpt
     * @return
     */
    @Override
    public ServiceResult<Integer> saveProductNormAttrOpt(@RequestBody ProductNormAttrOpt productNormAttrOpt) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(productNormAttrOptModel.saveProductNormAttrOpt(productNormAttrOpt));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IProductNormAttrOptService][saveProductNormAttrOpt]保存商品选定的规格属性(保存商品插入，暂时不用)时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IProductNormAttrOptService][saveProductNormAttrOpt]保存商品选定的规格属性(保存商品插入，暂时不用)时出现未知异常：",
                e);
        }
        return result;
    }

    /**
    * 更新商品选定的规格属性(保存商品插入，暂时不用)
    * @param  productNormAttrOpt
    * @return
    * @see com.yixiekeji.service.ProductNormAttrOptService#updateProductNormAttrOpt(ProductNormAttrOpt)
    */
    @Override
    public ServiceResult<Integer> updateProductNormAttrOpt(@RequestBody ProductNormAttrOpt productNormAttrOpt) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(productNormAttrOptModel.updateProductNormAttrOpt(productNormAttrOpt));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IProductNormAttrOptService][updateProductNormAttrOpt]更新商品选定的规格属性(保存商品插入，暂时不用)时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[IProductNormAttrOptService][updateProductNormAttrOpt]更新商品选定的规格属性(保存商品插入，暂时不用)时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductNormAttrOpt>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductNormAttrOpt>> serviceResult = new ServiceResult<List<ProductNormAttrOpt>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(productNormAttrOptModel.page(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[ProductNormAttrOptServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormAttrOptModel.del(id) > 0);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[ProductNormAttrOptServiceImpl][del] exception:" + e.getMessage());
        }
        return serviceResult;
    }
}