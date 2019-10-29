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
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductNormAttrOpt;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.model.product.ProductNormModel;
import com.yixiekeji.service.product.IProductNormService;
import com.yixiekeji.util.FeignProjectUtil;

@RestController
public class ProductNormServiceImpl implements IProductNormService {
    private static Logger    log = LoggerFactory.getLogger(ProductNormServiceImpl.class);
    @Resource
    private ProductNormModel productNormModel;

    @Override
    public ServiceResult<Boolean> saveNorm(@RequestBody ProductNorm norm) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.saveNorm(norm));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][saveNorm]保存规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][saveNorm]保存规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveNormMap(@RequestBody FeignProjectUtil feignProjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.saveNormMap(feignProjectUtil));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][saveNorm]保存规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][saveNorm]保存规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductNorm> getNormById(@RequestParam("id") Integer id) {
        ServiceResult<ProductNorm> serviceResult = new ServiceResult<ProductNorm>();
        try {
            serviceResult.setResult(productNormModel.getNormById(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][getNormById]根据id查询商品规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][getNormById]根据id查询商品规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductNorm>> getNormByIds(@RequestParam("ids") String ids) {
        ServiceResult<List<ProductNorm>> serviceResult = new ServiceResult<List<ProductNorm>>();
        try {
            serviceResult.setResult(productNormModel.getNormByIds(ids));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][getNormByIds]根据ids查询商品规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][getNormByIds]根据ids查询商品规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductNorm>> pageNorm(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductNorm>> serviceResult = new ServiceResult<List<ProductNorm>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(productNormModel.pageNorm(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][pageNorm]分页展示商品规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][pageNorm]分页展示商品规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateNorm(@RequestBody ProductNorm norm) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.updateNorm(norm));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][updateNorm]更新商品规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][updateNorm]更新商品规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateNormMap(@RequestBody FeignProjectUtil feignProjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.updateNorm(feignProjectUtil));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][updateNorm]更新商品规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][updateNorm]更新商品规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductType>> chkHasUsed(@RequestParam("id") Integer id) {
        ServiceResult<List<ProductType>> serviceResult = new ServiceResult<List<ProductType>>();
        try {
            serviceResult.setResult(productNormModel.chkHasUsed(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][chkHasUsed]根据规格id查询商品类型,检查规格是否被占用时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][chkHasUsed]根据规格id查询商品类型,检查规格是否被占用时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delNorm(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.delNorm(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][delNorm]删除规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][delNorm]删除规格时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveNormAttr(@RequestBody ProductNormAttr attr) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.saveNormAttr(attr));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][saveNormAttr]保存商品规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][saveNormAttr]保存商品规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<ProductNormAttr> getNormAttrById(@RequestParam("id") Integer id) {
        ServiceResult<ProductNormAttr> serviceResult = new ServiceResult<ProductNormAttr>();
        try {
            serviceResult.setResult(productNormModel.getNormAttrById(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][getNormAttrById]根据商品规格id查询商品规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][getNormAttrById]根据商品规格id查询商品规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductNormAttr>> getAttrByNormId(@RequestParam("id") Integer id) {
        ServiceResult<List<ProductNormAttr>> serviceResult = new ServiceResult<List<ProductNormAttr>>();
        try {
            serviceResult.setResult(productNormModel.getAttrByNormId(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][getAttrByNormId]根据normId获取attr时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][getAttrByNormId]根据normId获取attr时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductNormAttr>> getAttrByNormIds(@RequestParam("ids") String ids) {
        ServiceResult<List<ProductNormAttr>> serviceResult = new ServiceResult<List<ProductNormAttr>>();
        try {
            if (StringUtil.isEmpty(ids))
                throw new BusinessException("获取商品规格属性失败，无效的商品规格id");
            serviceResult.setResult(productNormModel.getAttrByNormIds(ids));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][getAttrByNormIds]根据normId获取attr时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][getAttrByNormIds]根据normId获取attr时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductNormAttr>> pageNormAttr(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductNormAttr>> serviceResult = new ServiceResult<List<ProductNormAttr>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(productNormModel.pageNormAttr(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][pageNormAttr]分页列表展示商品规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][pageNormAttr]分页列表展示商品规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateNormAttr(@RequestBody ProductNormAttr attr) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.updateNormAttr(attr));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][updateNormAttr]修改商品规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][updateNormAttr]修改商品规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delNormAttr(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.delNormAttr(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][delNormAttr]删除商品规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][delNormAttr]删除商品规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveNormAttrOpt(@RequestBody ProductNormAttrOpt opt) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.saveNormAttrOpt(opt));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][saveNormAttrOpt]保存商品选定的规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][saveNormAttrOpt]保存商品选定的规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateNormAttrOpt(@RequestBody ProductNormAttrOpt opt) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.updateNormAttrOpt(opt));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][updateNormAttrOpt]修改商品选定的规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][updateNormAttrOpt]修改商品选定的规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delNormAttrOpt(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(productNormModel.delNormAttrOpt(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][delNormAttrOpt]根据id删除商品选定的规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][delNormAttrOpt]根据id删除商品选定的规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    public ServiceResult<List<ProductNormAttrOpt>> pageNormAttrOpt(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductNormAttrOpt>> serviceResult = new ServiceResult<List<ProductNormAttrOpt>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(productNormModel.pageNormAttrOpt(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][pageNormAttrOpt]分页获取商品选定的规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][pageNormAttrOpt]分页获取商品选定的规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    public ServiceResult<ProductNormAttrOpt> getNormAttrOptById(@RequestParam("id") Integer id) {
        ServiceResult<ProductNormAttrOpt> serviceResult = new ServiceResult<ProductNormAttrOpt>();
        try {
            serviceResult.setResult(productNormModel.getNormAttrOptById(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][getNormAttrOptById]根据id获取商品选定的规格属性时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][getNormAttrOptById]根据id获取商品选定的规格属性时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductNorm>> listNoPage() {
        ServiceResult<List<ProductNorm>> serviceResult = new ServiceResult<List<ProductNorm>>();
        try {
            serviceResult.setResult(productNormModel.listNoPage());
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[ProductNormService][listNoPage]查询所有的规格时发生异常:", e);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductNormService][listNoPage]查询所有的规格时发生异常:", e);
        }
        return serviceResult;
    }

}
