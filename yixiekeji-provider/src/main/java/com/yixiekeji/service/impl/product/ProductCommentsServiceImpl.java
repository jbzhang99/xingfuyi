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
import com.yixiekeji.entity.product.ProductComments;
import com.yixiekeji.model.product.ProductCommentsModel;
import com.yixiekeji.service.product.IProductCommentsService;

@RestController
public class ProductCommentsServiceImpl implements IProductCommentsService {
    private static Logger        log = LoggerFactory.getLogger(ProductCommentsServiceImpl.class);

    @Resource
    private ProductCommentsModel productCommentsModel;

    @Override
    public ServiceResult<ProductComments> getProductCommentsById(@RequestParam("productCommentsId") Integer productCommentsId) {
        ServiceResult<ProductComments> result = new ServiceResult<ProductComments>();
        try {
            result.setResult(productCommentsModel.getProductCommentsById(productCommentsId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ProductCommentsService][getProductCommentsById]根据id[" + productCommentsId
                      + "]取得商品评论时出现异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductCommentsService][getProductCommentsById]根据id[" + productCommentsId
                      + "]取得商品评论时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> auditProductComments(@RequestParam("id") Integer id,
                                                       @RequestParam("state") Integer state) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(productCommentsModel.auditProductComments(id, state));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ProductCommentsService][auditProductComments]根据id[" + id + "]审核商品评论时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductCommentsService][auditProductComments]审核商品评论时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductComments>> getProductComments(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductComments>> serviceResult = new ServiceResult<List<ProductComments>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(productCommentsModel.getProductCommentsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<ProductComments> list = productCommentsModel.getProductComments(queryMap, start,
                size);
            serviceResult.setResult(list);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[ProductCommentsService][getProductComments]获取商品评论列表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductCommentsService][getProductComments]获取商品评论列表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductComments>> getProductCommentsWithInfo(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductComments>> serviceResult = new ServiceResult<List<ProductComments>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(productCommentsModel.getProductCommentsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<ProductComments> list = productCommentsModel.getProductCommentsWithInfo(queryMap,
                start, size);
            serviceResult.setResult(list);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[ProductCommentsService][getProductCommentsWithName]获取商品评论列表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ProductCommentsService][getProductCommentsWithName]获取商品评论列表时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 保存商品评论
     * @param productComments
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Boolean> saveProductComments(@RequestBody ProductComments productComments,
                                                      @RequestParam("ordersProductId") Integer ordersProductId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                productCommentsModel.saveProductComments(productComments, ordersProductId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[productCommentsService][saveProductComments]保存商品评论时发生异常:", be);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[productCommentsService][saveProductComments]保存商品评论时发生异常:", e);
        }
        return serviceResult;
    }

    /**
    * 更新商品评论管理
    * @param  productComments
    * @return
    */
    @Override
    public ServiceResult<Integer> updateProductComments(@RequestBody ProductComments productComments) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(productCommentsModel.updateProductComments(productComments));
        } catch (Exception e) {
            log.error("更新商品评论管理时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新商品评论管理时出现未知异常");
        }
        return result;
    }

    /**
     * 根据订单编号及产品id,货品ID   取得商品评论  1个订单可以有多个产品评论
     * @param orderSn
     * @param productId
     * @param request
     * @return
     */
    @Override
    public ServiceResult<ProductComments> getProductCommentsByOrderSn(@RequestParam("orderSn") String orderSn,
                                                                      @RequestParam("productId") String productId,
                                                                      @RequestParam("productGoodsId") String productGoodsId,
                                                                      @RequestParam("memberId") Integer memberId) {
        ServiceResult<ProductComments> serviceResult = new ServiceResult<ProductComments>();
        try {
            serviceResult.setResult(productCommentsModel.getProductCommentsByOrderSn(orderSn,
                productId, productGoodsId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[productCommentsService][getProductCommentsByOrderSn]获取订单评论时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据查询条件取所有的评论 单品页使用 
     * @param request
     * @param pager
     * @return
     */
    @Override
    public ServiceResult<List<ProductComments>> getCommentsByCondition(@RequestBody FeignUtil feignUtil) {
        Map<String, Object> queryMap = feignUtil.getQueryMapObject();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<ProductComments>> serviceResult = new ServiceResult<List<ProductComments>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(productCommentsModel.getCommentsByCondition(queryMap, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[productCommentsService][getCommentsByCondition]获取商品评论列表时发生异常:", e);
        }
        return serviceResult;
    }
}