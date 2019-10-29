package com.yixiekeji.web.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.ProductComments;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductCommentsService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 用户中心：我的评价
 *                       
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberCommentController extends BaseController {

    @Resource
    private IProductCommentsService productCommentsService;
    @Resource
    private IOrdersService          ordersService;
    @Resource
    private IMemberService          memberService;

    /**
     * 跳转到 我的评价界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/comment.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toMyEvaluation(HttpServletRequest request,
                                                                            HttpServletResponse response,
                                                                            Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        queryMap.put("q_userId", String.valueOf(member.getId()));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductComments>> serviceResult = productCommentsService
            .getProductCommentsWithInfo(feignUtil);

        if (!serviceResult.getSuccess()) {
            throw new RuntimeException(serviceResult.getMessage());
        }
        pager = serviceResult.getPager();
        dataMap.put("commentsList", serviceResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE_10);
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 添加或编辑商品评价界面
     * @param request
     * @param response
     * @param dataMap
     * @param orderSn 订单id
     * @return
     */
    @RequestMapping(value = "/addcomment.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Orders> addComment(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap,
                                                           @RequestParam(value = "id", required = true) Integer id) {
        HttpJsonResult<Orders> jsonResult = new HttpJsonResult<Orders>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        Integer userId = member.getId();
        if (id == null) {
            jsonResult.setMessage("订单id为空");
            return jsonResult;
        }

        ServiceResult<Orders> orderServiceResult = ordersService.getOrderWithOPById(id);
        if (!orderServiceResult.getSuccess()) {
            jsonResult.setMessage(orderServiceResult.getMessage());
            return jsonResult;
        }

        if (orderServiceResult.getResult() == null) {
            jsonResult.setMessage("订单不存在");
            return jsonResult;
        }
        if (!orderServiceResult.getResult().getMemberId().equals(userId)) {
            jsonResult.setMessage("您无权访问他人信息");
            return jsonResult;
        }
        dataMap.put("order", orderServiceResult.getResult());
        jsonResult.setData(orderServiceResult.getResult());
        return jsonResult;
    }

    /**
     * 添加或查看商品评价界面（子页面）
     * @param request
     * @param response
     * @param dataMap
     * @param orderSn 订单编号
     * @param productId 产品id
     * @return
     */
    @RequestMapping(value = "/addcommentdetail.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> addCommentDetail(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              Map<String, Object> dataMap,
                                                                              @RequestParam(value = "orderSn", required = true) String orderSn,
                                                                              @RequestParam(value = "productId", required = true) String productId,
                                                                              @RequestParam(value = "productGoodsId", required = true) String productGoodsId,
                                                                              @RequestParam(value = "ordersProductId", required = true) String ordersProductId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        if (StringUtil.isEmpty(orderSn)) {
            jsonResult.setMessage("订单编号为空");
            return jsonResult;
        }
        if (StringUtil.isEmpty(productId)) {
            jsonResult.setMessage("产品id为空");
            return jsonResult;
        }
        if (StringUtil.isEmpty(productGoodsId)) {
            jsonResult.setMessage("货品id为空");
            return jsonResult;
        }
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<ProductComments> serviceResult = productCommentsService
            .getProductCommentsByOrderSn(orderSn, productId, productGoodsId, member.getId());
        dataMap.put("comment", serviceResult.getResult());
        dataMap.put("orderSn", orderSn);
        dataMap.put("productId", productId);
        dataMap.put("productGoodsId", productGoodsId);
        dataMap.put("ordersProductId", ordersProductId);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 商品评价提交
     * @param request
     * @param response
     * @param productComments
     * @throws IOException 
     */
    @RequestMapping(value = "/savecomment.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<ProductComments> evaluationSubmit(HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          String orderSn,
                                                                          Integer productId,
                                                                          Integer productGoodsId,
                                                                          Integer ordersProductId,
                                                                          Integer grade,
                                                                          Integer description,
                                                                          Integer serviceAttitude,
                                                                          Integer productSpeed,
                                                                          String content) throws Exception {
        HttpJsonResult<ProductComments> jsonResult = new HttpJsonResult<ProductComments>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        if (ordersProductId == null) {
            return new HttpJsonResult<ProductComments>("网单id不能为空，请重试");
        }

        ProductComments productComments = new ProductComments();
        productComments.setUserId(member.getId());
        productComments.setUserName(member.getName());
        productComments.setOrderSn(orderSn);
        productComments.setProductId(productId);
        productComments.setProductGoodsId(productGoodsId);
        productComments.setOrdersProductId(ordersProductId);
        productComments.setGrade(grade);
        productComments.setDescription(description);
        productComments.setServiceAttitude(serviceAttitude);
        productComments.setProductSpeed(productSpeed);
        productComments.setContent(content);

        ServiceResult<Orders> ordersBySnRlt = ordersService
            .getOrdersBySn(productComments.getOrderSn());
        if (!ordersBySnRlt.getSuccess()) {
            jsonResult = new HttpJsonResult<ProductComments>(ordersBySnRlt.getMessage());
            return jsonResult;
        }
        if (ordersBySnRlt.getResult() != null) {
            productComments.setSellerId(ordersBySnRlt.getResult().getSellerId());
        }

        productComments.setState(ProductComments.STATE_1);

        ServiceResult<Boolean> serviceResult = productCommentsService
            .saveProductComments(productComments, ordersProductId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<ProductComments>(serviceResult.getMessage());
            }
        }

        // 评论送积分
        memberService.memberEvaluateSendValue(member.getId(), member.getName(),
            productComments.getProductId());
        return jsonResult;
    }

}
