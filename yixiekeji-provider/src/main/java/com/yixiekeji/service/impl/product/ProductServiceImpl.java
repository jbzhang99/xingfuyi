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
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.model.product.ProductModel;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.util.FeignProjectUtil;

@RestController
public class ProductServiceImpl implements IProductService {
    private static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductModel  productModel;

    @Override
    public ServiceResult<List<Product>> getByCateIdTop(@RequestParam("cateId") Integer cateId,
                                                       @RequestParam("limit") Integer limit) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            serviceResult.setResult(productModel.getByCateIdTop(cateId, limit));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl getByCateIdTop cateId:" + cateId);
            log.error("ProductServiceImpl getByCateIdTop exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getSellerRecommendProducts(@RequestParam("sellerId") Integer sellerId,
                                                                   @RequestParam("size") Integer size) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            serviceResult.setResult(productModel.getSellerRecommendProducts(sellerId, size));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductService][getSellerRecommendProducts]获取商家推荐商品时出现异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getSellerNewProducts(@RequestParam("sellerId") Integer sellerId,
                                                             @RequestParam("size") Integer size) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            serviceResult.setResult(productModel.getSellerNewProducts(sellerId, size));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductService][getSellerNewProducts]获取商家新品时出现异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getProductForSellerList(@RequestParam("sellerId") Integer sellerId,
                                                                @RequestParam("size") Integer sort,
                                                                @RequestParam("sellerCateId") Integer sellerCateId,
                                                                @RequestBody PagerInfo pager) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(
                    productModel.getProductForSellerListCount(sellerId, sort, sellerCateId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(
                productModel.getProductForSellerList(sellerId, sort, sellerCateId, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ProductService][getProductForSellerList]查询店铺商品时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductService][getProductForSellerList]查询店铺商品时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getRecommendProducts(@RequestParam("size") Integer size) {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productModel.getRecommendProducts(size));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ProductService][getIndexRecProduct]获取首页推荐商品时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductService][getIndexRecProduct]获取首页推荐商品时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveProduct(@RequestBody FeignProjectUtil feignProjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productModel.saveProduct(feignProjectUtil.getParammap(),
                feignProjectUtil.getProduct(), feignProjectUtil.getProductPictureList(),
                feignProjectUtil.getProductAttrList()));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            throw e;
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl saveProduct param:"
                      + JSON.toJSONString(feignProjectUtil.getProduct()));
            log.error("ProductServiceImpl saveProduct exception:", e);
            throw e;
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProduct(@RequestBody FeignProjectUtil feignProjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productModel.updateProduct(feignProjectUtil.getParammap(),
                feignProjectUtil.getProduct(), feignProjectUtil.getProductPictureList(),
                feignProjectUtil.getProductAttrList()));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl updateProduct param:"
                      + JSON.toJSONString(feignProjectUtil.getProduct()));
            log.error("ProductServiceImpl updateProduct exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delProduct(@RequestParam("id") Integer id,
                                             @RequestParam("sellerId") Integer sellerId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productModel.delProduct(id, sellerId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl delProduct productId:" + id);
            log.error("ProductServiceImpl delProduct exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Product> getProductById(@RequestParam("productId") Integer productId) {
        ServiceResult<Product> serviceResult = new ServiceResult<Product>();
        try {
            serviceResult.setResult(productModel.getProductById(productId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl getProductById id:" + productId);
            log.error("ProductServiceImpl getProductById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> pageProduct(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(productModel.pageProduct(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(false);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl pageProduct queryMap:" + JSON.toJSONString(queryMap)
                      + " pager:" + JSON.toJSONString(pager));
            log.error("ProductServiceImpl pageProduct exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getProductsBySellerId(@RequestParam("sellerid") Integer sellerid) {
        ServiceResult<List<Product>> sr = new ServiceResult<List<Product>>();
        try {
            sr.setResult(productModel.getProductsBySellerId(sellerid));
        } catch (BusinessException e) {
            sr.setMessage(e.getMessage());
            sr.setSuccess(false);
        }
        return sr;
    }

    @Override
    public ServiceResult<Integer> updateByIds(@RequestBody FeignProjectUtil feignProjectUtil) {

        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(productModel.updateByIds(feignProjectUtil.getMap(),
                feignProjectUtil.getIntegerList()));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl updateByIds param1:");
            log.error("ProductServiceImpl updateByIds exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductState(@RequestParam("id") Integer id,
                                                     @RequestParam("state") Integer state) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productModel.updateProductState(id, state));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl updateProductState productId:" + id);
            log.error("ProductServiceImpl updateProductState exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductRecommend(@RequestParam("id") Integer id,
                                                         @RequestParam("id") Integer isTop) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productModel.updateProductRecommend(id, isTop));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl updateProductRecommend productId:" + id);
            log.error("ProductServiceImpl updateProductRecommend exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getProductsByIds(@RequestBody List<Integer> ids) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            serviceResult.setResult(productModel.getProductsByIds(ids));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductServiceImpl][getProductsByIds] exception:", e);
        }
        return serviceResult;
    }

    /**
     * 查询最大的商品
     * @return
     * @see com.yixiekeji.service.product.IProductService#getProductByMax()
     */
    @Override
    public ServiceResult<Product> getProductByMax() {
        ServiceResult<Product> serviceResult = new ServiceResult<Product>();
        try {
            serviceResult.setResult(productModel.getProductByMax());
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl getProductByMax");
            log.error("ProductServiceImpl getProductByMax exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> isUnique(@RequestParam("sellerId") Integer sellerId,
                                           @RequestParam("productCode") String productCode,
                                           @RequestParam("productId") Integer productId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productModel.isUnique(sellerId, productCode, productId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl uniqueValid exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getSalesProducts(@RequestParam("size") Integer size) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            serviceResult.setResult(productModel.getSalesProducts(size));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductServiceImpl uniqueValid exception:", e);
        }
        return serviceResult;
    }
}