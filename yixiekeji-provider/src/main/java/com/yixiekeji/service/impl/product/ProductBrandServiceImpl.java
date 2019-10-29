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
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.model.product.ProductBrandModel;
import com.yixiekeji.service.product.IProductBrandService;

@RestController
public class ProductBrandServiceImpl implements IProductBrandService {
    private static Logger     log = LoggerFactory.getLogger(ProductBrandServiceImpl.class);

    @Resource
    private ProductBrandModel productBrandModel;

    @Override
    public ServiceResult<Boolean> save(@RequestBody ProductBrand brand) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productBrandModel.save(brand));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param:" + JSON.toJSONString(brand));
            log.error("[BrandServiceImpl][save] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductBrand> getById(@RequestParam("id") Integer id) {
        ServiceResult<ProductBrand> serviceResult = new ServiceResult<ProductBrand>();
        try {
            serviceResult.setResult(productBrandModel.getById(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param:" + id);
            log.error("[BrandServiceImpl][getById] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductBrand>> getByIds(@RequestParam("id") String id) {
        ServiceResult<List<ProductBrand>> serviceResult = new ServiceResult<List<ProductBrand>>();
        try {
            serviceResult.setResult(productBrandModel.getByIds(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param:" + id);
            log.error("[BrandServiceImpl][getById] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> update(@RequestBody ProductBrand brand) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productBrandModel.update(brand));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param:" + JSON.toJSONString(brand));
            log.error("[BrandServiceImpl][audit] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductBrand>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductBrand>> serviceResult = new ServiceResult<List<ProductBrand>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(productBrandModel.page(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param1:" + JSON.toJSONString(queryMap) + " &param2:"
                      + JSON.toJSONString(pager));
            log.error("[BrandServiceImpl][page] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductBrand>> getBrandByTypeId(@RequestParam("typeId") Integer typeId) {
        ServiceResult<List<ProductBrand>> serviceResult = new ServiceResult<List<ProductBrand>>();
        try {
            serviceResult.setResult(productBrandModel.getBrandByTypeId(typeId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][getBrandByTypeId] typeId:" + typeId);
            log.error("[BrandServiceImpl][getBrandByTypeId] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> audit(@RequestParam("id") Integer id,
                                        @RequestParam("state") Integer state,
                                        @RequestParam("userId") Integer userId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productBrandModel.audit(id, state, userId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param:" + id);
            log.error("[BrandServiceImpl][audit] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productBrandModel.del(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][save] param:" + id);
            log.error("[BrandServiceImpl][del] exception:", e);
        }
        return serviceResult;
    }

    /**
     * 取出所有可用的品牌
     * @return
     * @see com.yixiekeji.service.product.IProductBrandService#listNoPage()
     */
    @Override
    public ServiceResult<List<ProductBrand>> listNoPage() {
        ServiceResult<List<ProductBrand>> serviceResult = new ServiceResult<List<ProductBrand>>();
        try {
            serviceResult.setResult(productBrandModel.listNoPage());
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[BrandServiceImpl][getBrandByTypeId] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductBrand>> getHotBrands() {
        ServiceResult<List<ProductBrand>> serviceResult = new ServiceResult<List<ProductBrand>>();
        try {
            serviceResult.setResult(productBrandModel.getHotBrands());
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IProductBrandService][getHotBrands]获取推荐的品牌时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IProductBrandService][getHotBrands]获取推荐的品牌时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, List<ProductBrand>>> getBrandsLetterGrouping() {
        ServiceResult<Map<String, List<ProductBrand>>> serviceResult = new ServiceResult<Map<String, List<ProductBrand>>>();
        try {
            serviceResult.setResult(productBrandModel.getBrandsLetterGrouping());
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IProductBrandService][getBrandsLetterGrouping]获取所有品牌，按首字母分组时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IProductBrandService][getBrandsLetterGrouping]获取所有品牌，按首字母分组时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getUndoBrand() {
        ServiceResult<Integer> serviceResult = new ServiceResult<>();
        try {
            Integer res = productBrandModel.getUndoBrand();
            serviceResult.setResult(res);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
        }
        return serviceResult;
    }
}
