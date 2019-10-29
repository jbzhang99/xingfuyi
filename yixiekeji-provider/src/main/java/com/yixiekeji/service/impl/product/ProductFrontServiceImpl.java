package com.yixiekeji.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.model.product.ProductFrontModel;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.util.FeignObjectUtil;

@RestController
public class ProductFrontServiceImpl implements IProductFrontService {
    private static Logger     log = LoggerFactory.getLogger(ProductFrontServiceImpl.class);

    @Resource
    private ProductFrontModel productFrontModel;

    /**
     * 根据分类列表页查询商品
     * @param cateId
     * @param mapCondition
     * @param stack
     */
    @Override
    public void getProducts(@RequestParam("cateId") Integer cateId,
                            @RequestBody FeignObjectUtil feignObjectUtil) {
        try {
            productFrontModel.getProducts(cateId, feignObjectUtil.getQueryMapObject1(),
                feignObjectUtil.getQueryMapObject2());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[ProductFrontService][getProducts]根据商品分类查询商品时发生异常:", e);
        }
    }

    /**
     * 根据分类查询列表页推荐的头部信息
     * @param cateId
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductListByCateIdTop(int)
     */
    @Override
    public ServiceResult<List<Product>> getProductListByCateIdTop(@RequestParam("cateId") int cateId) {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductListByCateIdTop(cateId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[ProductFrontService][getProductListByCateIdTop]根据商户id取得列表页头部推荐商品 及最新商品时发生异常:", e);
        }
        return result;
    }

    /**
     * 根据分类查询列表页推荐的左边信息
     * @param cateId
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductListByCateIdLeft(int)
     */
    @Override
    public ServiceResult<List<Product>> getProductListByCateIdLeft(@RequestParam("cateId") int cateId) {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductListByCateIdLeft(cateId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[ProductFrontService][getProductListByCateIdTop]根据商户id取得列表页左侧推荐商品 及最新商品时发生异常:", e);
        }
        return result;
    }

    /**
     * 查询二级分类下三级分类的商品
     * @param cateId
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductListByCateId2(int)
     */
    @Override
    public ServiceResult<List<Product>> getProductListByCateId2(@RequestParam("cateId") int cateId) {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductListByCateId2(cateId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductListByCateId2]查询二级分类下三级分类的商品发生异常:", e);
        }
        return result;
    }

    /**
     * 搜索页面推荐商品信息头部
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductSearchByTop()
     */
    @Override
    public ServiceResult<List<Product>> getProductSearchByTop() {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductSearchByTop());
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductSearchByTop]查询搜索页面推荐商品信息头部发生异常:", e);
        }
        return result;
    }

    /**
     * 用户中心首页推荐商品
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductMemberByTop()
     */
    @Override
    public ServiceResult<List<Product>> getProductMemberByTop(@RequestParam("number") int number) {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductMemberByTop(number));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductMemberByTop]用户中心首页推荐商品信息左部发生异常:", e);
        }
        return result;
    }

    /**
     * 搜索页面推荐商品信息左部
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductSearchByLeft()
     */
    @Override
    public ServiceResult<List<Product>> getProductSearchByLeft() {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductSearchByLeft());
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductSearchByLeft]查询搜搜索页面推荐商品信息左部发生异常:", e);
        }
        return result;
    }

    /**
     * 查询商家列表页 商品信息
     * @param start 
     * @param size
     * @param cateId 商家分类
     * @param sellerId 商家ID
     * @param sort 排序
    * @return
    * @see com.yixiekeji.service.product.IProductFrontService#getProductListBySellerCateId(com.yixiekeji.core.PaginationUtil, java.lang.Integer, java.lang.Integer)
    */
    @Override
    public ServiceResult<List<Product>> getProductListBySellerCateId(@RequestParam("start") int start,
                                                                     @RequestParam("cateId") int size,
                                                                     @RequestParam("cateId") int cateId,
                                                                     @RequestParam("sellerId") int sellerId,
                                                                     @RequestParam("sort") int sort) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            List<Product> returnList = productFrontModel.getProductListBySellerCateId(start, size,
                cateId, sellerId, sort);
            serviceResult.setResult(returnList);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[ProductFrontService][getProductListBySellerCateId]查询商家列表页 商品信息时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductListBySellerCateId]查询商家列表页 商品信息时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据商家商品分类统计商家商品
     * @param cateId 商家分类
     * @param sellerId 商家ID
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductListBySellerCateIdCount(int, int)
     */
    @Override
    public ServiceResult<Integer> getProductListBySellerCateIdCount(@RequestParam("cateId") int cateId,
                                                                    @RequestParam("sellerId") int sellerId) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            Integer count = productFrontModel.getProductListBySellerCateIdCount(cateId, sellerId);
            serviceResult.setResult(count);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[ProductFrontService][getProductListBySellerCateIdCount]根据商家商品分类统计商家商品时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[ProductFrontService][getProductListBySellerCateIdCount]根据商家商品分类统计商家商品时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getProductsByBrandId(@RequestParam("brandId") Integer brandId,
                                                             @RequestParam("sort") Integer sort,
                                                             @RequestParam("doself") Integer doself,
                                                             @RequestParam("store") Integer store,
                                                             @RequestBody PagerInfo pager) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(
                    productFrontModel.getProductsByBrandIdCount(brandId, doself, store));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(
                productFrontModel.getProductsByBrandId(brandId, sort, doself, store, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[IProductFrontService][getProductsByBrandId]根据品牌查询商品时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IProductFrontService][getProductsByBrandId]根据品牌查询商品时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getProductsByBrandIdTop(@RequestParam("brandId") Integer brandId) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        try {
            serviceResult.setResult(productFrontModel.getProductsByBrandIdTop(brandId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IProductFrontService][getProductsByBrandIdTop]根据品牌查询销量最好的商品时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IProductFrontService][getProductsByBrandIdTop]根据品牌查询销量最好的商品时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Product>> getProductByPath(@RequestParam("productCatePath") String productCatePath,
                                                         @RequestParam("sort") int sort,
                                                         @RequestBody PagerInfo pager) {
        ServiceResult<List<Product>> serviceResult = new ServiceResult<List<Product>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(productFrontModel.getProductByPathCount(productCatePath));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<Product> returnList = productFrontModel.getProductByPath(productCatePath, sort,
                start, size);
            serviceResult.setResult(returnList);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[ProductFrontService][getProductByPath]根据商品分类路径查询商品信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductByPath]根据商品分类路径查询商品信息时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 购物车推荐商品（购买了该商品的用户还购买了）
     * @param cateId
     * @param number
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductsListCart(int, int)
     */
    @Override
    public ServiceResult<List<Product>> getProductsListCart(@RequestParam("cateId") int cateId,
                                                            @RequestParam("number") int number) {
        ServiceResult<List<Product>> result = new ServiceResult<List<Product>>();
        try {
            result.setResult(productFrontModel.getProductsListCart(cateId, number));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductsListCart]购物车推荐商品（购买了该商品的用户还购买了）发生异常:", e);
        }
        return result;
    }

    /**
     * 根据分类的名称查询分类
     * @param name
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductCateByName(java.lang.String)
     */
    @Override
    public ServiceResult<ProductCate> getProductCateByName(@RequestParam("name") String name) {
        ServiceResult<ProductCate> result = new ServiceResult<ProductCate>();
        try {
            result.setResult(productFrontModel.getProductCateByName(name));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductCateByName]根据分类的名称查询分类发生异常:", e);
        }
        return result;
    }

    /**
     * 根据品牌的名称查询
     * @param name
     * @return
     * @see com.yixiekeji.service.product.IProductFrontService#getProductBrandByName(java.lang.String)
     */
    @Override
    public ServiceResult<ProductBrand> getProductBrandByName(@RequestParam("name") String name) {
        ServiceResult<ProductBrand> result = new ServiceResult<ProductBrand>();
        try {
            result.setResult(productFrontModel.getProductBrandByName(name));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductBrandByName]根据品牌的名称查询分类发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Product> getProductById(@RequestParam("productId") Integer productId) {
        ServiceResult<Product> serviceResult = new ServiceResult<Product>();
        try {
            serviceResult.setResult(productFrontModel.getProductById(productId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ProductFrontService][getProductById] id:" + productId);
            log.error("[ProductFrontService][getProductById] exception:", e);
        }
        return serviceResult;
    }

    /**
     * 根据分类ID查询分类信息
     */
    @Override
    public ServiceResult<ProductCate> getProductCateById(@RequestParam("cateId") Integer cateId) {
        ServiceResult<ProductCate> result = new ServiceResult<ProductCate>();
        try {
            result.setResult(productFrontModel.getProductCateById(cateId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductCateById]根据分类ID查询分类信息发生异常:", e);
        }
        return result;
    }

    /**
     * 取得id的分类下面的子节点信息
     */
    @Override
    public ServiceResult<List<ProductCate>> getProductCateByPid(@RequestParam("pid") Integer pid) {
        ServiceResult<List<ProductCate>> result = new ServiceResult<List<ProductCate>>();
        try {
            result.setResult(productFrontModel.getProductCateByPid(pid));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductCateByPid]取得id的分类下面的子节点信息发生异常:", e);
        }
        return result;
    }

    /**
     * 根据类型ID查询类型信息
     */
    @Override
    public ServiceResult<ProductType> getProductTypeById(@RequestParam("id") Integer id) {
        ServiceResult<ProductType> result = new ServiceResult<ProductType>();
        try {
            result.setResult(productFrontModel.getProductTypeById(id));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductTypeById]根据类型ID查询类型信息发生异常:", e);
        }
        return result;
    }

    /**
     * 根据品牌的ID集合查询出品牌
     */
    @Override
    public ServiceResult<List<ProductBrand>> getProductBrandByIds(@RequestParam("brandIds") String brandIds) {
        ServiceResult<List<ProductBrand>> result = new ServiceResult<List<ProductBrand>>();
        try {
            result.setResult(productFrontModel.getProductBrandByIds(brandIds));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductBrandByIds]根据品牌的ID集合查询出品牌信息发生异常:", e);
        }
        return result;
    }

    /**
     * 根据类型ID查询类型下关联的查询属性
     */
    @Override
    public ServiceResult<List<ProductTypeAttr>> getByTypeIdAndQuery(@RequestParam("productTypeId") Integer productTypeId) {
        ServiceResult<List<ProductTypeAttr>> result = new ServiceResult<List<ProductTypeAttr>>();
        try {
            result.setResult(productFrontModel.getByTypeIdAndQuery(productTypeId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getByTypeIdAndQuery]根据类型ID查询类型下关联的查询属性信息发生异常:", e);
        }
        return result;
    }

    /**
     * 根据品牌ID查询品牌信息
     */
    @Override
    public ServiceResult<ProductBrand> getProductBrandById(@RequestParam("brandId") int brandId) {
        ServiceResult<ProductBrand> result = new ServiceResult<ProductBrand>();
        try {
            result.setResult(productFrontModel.getProductBrandById(brandId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductFrontService][getProductBrandById]根据品牌ID查询品牌信息发生异常:", e);
        }
        return result;
    }

}
