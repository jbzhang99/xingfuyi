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
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.seller.SellerManageCate;
import com.yixiekeji.model.product.ProductCateModel;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.vo.product.FrontProductCateVO;
import com.yixiekeji.vo.product.ProductCateVO;

@RestController
public class ProductCateServiceImpl implements IProductCateService {
    private static Logger    log = LoggerFactory.getLogger(ProductCateServiceImpl.class);

    @Resource
    private ProductCateModel productCateModel;

    @Override
    public ServiceResult<Boolean> saveProductCate(@RequestBody ProductCate productCate) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productCateModel.saveProductCate(productCate));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductCateServiceImpl saveProductCate param:" + JSON.toJSONString(productCate));
            log.error("ProductCateServiceImpl saveProductCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateProductCate(@RequestBody ProductCate productCate) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productCateModel.updateProductCate(productCate));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "ProductCateServiceImpl updateProductCate param:" + JSON.toJSONString(productCate));
            log.error("ProductCateServiceImpl updateProductCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delProductCate(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productCateModel.delProductCate(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl delProductCate productCateId:" + id);
            log.error("ProductCateServiceImpl delProductCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductCate> getProductCateById(@RequestParam("productCateId") Integer productCateId) {
        ServiceResult<ProductCate> serviceResult = new ServiceResult<ProductCate>();
        try {
            serviceResult.setResult(productCateModel.getProductCateById(productCateId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl getProductCateById id:" + productCateId);
            log.error("ProductCateServiceImpl getProductCateById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductCateVO>> pageProductCate(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductCateVO>> serviceResult = new ServiceResult<List<ProductCateVO>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(productCateModel.pageProductCate(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl pageProductCate queryMap:"
                      + JSON.toJSONString(queryMap) + " pager:" + JSON.toJSONString(pager));
            log.error("ProductCateServiceImpl pageProductCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductCate>> getByPid(@RequestParam("pid") Integer pid) {
        ServiceResult<List<ProductCate>> serviceResult = new ServiceResult<List<ProductCate>>();
        try {
            serviceResult.setResult(productCateModel.getByPid(pid));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl pageProductCate pid:" + pid);
            log.error("ProductCateServiceImpl pageProductCate exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductCate>> getCateBySellerId(@RequestParam("sellerId") Integer sellerId) {
        ServiceResult<List<ProductCate>> serviceResult = new ServiceResult<List<ProductCate>>();
        try {
            serviceResult.setResult(productCateModel.getCateBySellerId(sellerId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl getCateBySellerId sellerId:" + sellerId);
            log.error("ProductCateServiceImpl getCateBySellerId exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductCate>> getCateBySellerIdAndPid(@RequestParam("sellerId") Integer sellerId,
                                                                    @RequestParam("pid") Integer pid) {
        ServiceResult<List<ProductCate>> serviceResult = new ServiceResult<List<ProductCate>>();

        try {
            serviceResult.setResult(productCateModel.getCateBySellerId(sellerId, pid));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl getCateBySellerId sellerId:" + sellerId);
            log.error("ProductCateServiceImpl getCateBySellerId exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> getCatePathStrById(@RequestParam("productCateId") Integer productCateId) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            serviceResult.setResult(productCateModel.getCatePathStrById(productCateId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl getCatePathStrById productCateId:" + productCateId);
            log.error("ProductCateServiceImpl getCatePathStrById exception:", e);
        }
        return serviceResult;
    }

    @Override
    public SellerManageCate getSellerManageCate(@RequestParam("id") Integer id) {
        return productCateModel.getSellerManageCate(id);
    }

    @Override
    public Boolean updateSellerManageCate(@RequestBody SellerManageCate cate) {
        return productCateModel.updateSellerManageCate(cate);
    }

    @Override
    public ServiceResult<List<ProductCateVO>> getByPidAndSeller(@RequestParam("pid") Integer pid,
                                                                @RequestParam("seller") Integer seller) {
        ServiceResult<List<ProductCateVO>> serviceResult = new ServiceResult<List<ProductCateVO>>();
        try {

            serviceResult.setResult(productCateModel.getByPidAndSeller(pid, seller));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("ProductCateServiceImpl pageProductCate pid:" + pid);
            log.error("ProductCateServiceImpl pageProductCate exception:", e);
        }
        return serviceResult;
    }

    /**
     * 取得所有显示状态的商品分类
     * @param  map
     * @return
     */
    @Override
    public ServiceResult<List<FrontProductCateVO>> getProductCateList(@RequestBody Map<String, Object> queryMap) {
        ServiceResult<List<FrontProductCateVO>> serviceResult = new ServiceResult<List<FrontProductCateVO>>();
        try {
            serviceResult.setResult(productCateModel.getProductCateList(queryMap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductCateService][getProductCateList]获取商品分类列表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductCate>> getProductCate(@RequestBody Map<String, Object> param) {
        ServiceResult<List<ProductCate>> serviceResult = new ServiceResult<List<ProductCate>>();
        try {
            serviceResult.setResult(productCateModel.getProductCate(param));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductCateService][getProductCate]获取商品分类列表时发生异常:", e);
        }
        return serviceResult;
    }
}