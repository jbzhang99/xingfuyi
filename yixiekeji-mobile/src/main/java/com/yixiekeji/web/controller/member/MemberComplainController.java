package com.yixiekeji.web.controller.member;

import java.io.IOException;
import java.net.URLEncoder;
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
import org.springframework.web.multipart.MultipartFile;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.service.member.IMemberProductExchangeService;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.seller.ISellerComplaintService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.seller.FrontSellerComplaintVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 客户服务：申诉
 *                       
 * @Filename: MemberComplainController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberComplainController extends BaseController {

    @Resource
    private IOrdersService                ordersService;

    @Resource
    private IOrdersProductService         ordersProductService;

    @Resource
    private IProductFrontService          productFrontService;

    @Resource
    private IMemberProductBackService     memberProductBackService;

    @Resource
    private IMemberProductExchangeService memberProductExchangeService;

    @Resource
    private ISellerComplaintService       sellerComplaintService;

    /**
     * 跳转到 申诉列表界面
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/complaint.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toComplainApply(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<List<FrontSellerComplaintVO>> serviceResult = sellerComplaintService
            .getComplaintListWithPrdAndOp(pager, member.getId());
        pager = serviceResult.getPager();

        dataMap.put("complaintList", serviceResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 跳转到 申诉申请录入界面 
     * @param request
     * @param response
     * @param dataMap
     * @param orderProductId 网单ID
     * @param productBackId  退货申请ID
     * @param productExchangeId 换货申请ID
     * @param orderId 订单ID
     * @return
     */
    @RequestMapping(value = "/addcomplaint.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toComplainApply(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             Map<String, Object> dataMap,
                                                                             @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                                                             Integer productBackId,
                                                                             Integer productExchangeId,
                                                                             @RequestParam(value = "orderId", required = true) Integer orderId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        ServiceResult<OrdersProduct> serviceResult = ordersProductService
            .getOrdersProductWithImgById(orderProductId);

        //查询订单信息
        ServiceResult<Orders> orderResult = ordersService.getOrderWithOPById(orderId);

        OrdersProduct ordersProduct = serviceResult.getResult();
        if (ordersProduct != null) {
            ServiceResult<Product> productResult = productFrontService
                .getProductById(ordersProduct.getProductId());
            dataMap.put("product", productResult.getResult());
        }

        dataMap.put("order", orderResult.getResult());
        dataMap.put("ordersProduct", serviceResult.getResult());
        dataMap.put("productBackId", productBackId);
        dataMap.put("productExchangeId", productExchangeId);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 申诉提交
     * @param request
     * @param response
     * @param dataMap
     * @param sellerComplaint
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/savecomplaint.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<SellerComplaint> complainSubmit(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        Integer sellerId,
                                                                        Integer orderProductId,
                                                                        Integer productBackId,
                                                                        Integer productExchangeId,
                                                                        Integer orderId,
                                                                        @RequestParam("pic") MultipartFile pic) throws IOException {
        SellerComplaint sellerComplaint = new SellerComplaint();
        sellerComplaint.setSellerId(sellerId);
        sellerComplaint.setOrderProductId(orderProductId);
        sellerComplaint.setProductBackId(productBackId);
        sellerComplaint.setProductExchangeId(productExchangeId);
        sellerComplaint.setOrderId(orderId);

        HttpJsonResult<SellerComplaint> jsonResult = new HttpJsonResult<SellerComplaint>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            jsonResult.setMessage(URLEncoder.encode(jsonResult.getMessage(), "UTF-8"));
            return jsonResult;
        }

        sellerComplaint
            .setImage(UploadUtil.getInstance().uploadFile2ImageServerApp(request, pic, 0));
        //        sellerComplaint.setImage(UploadUtil.getInstance().uploadFile2ImageServer("pic", request));
        //保存申诉
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMember(member);
        feignObjectUtil.setSellerComplaint(sellerComplaint);
        ServiceResult<SellerComplaint> serviceResult = sellerComplaintService
            .saveSellerComplaint(feignObjectUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<SellerComplaint>(serviceResult.getMessage());
                jsonResult.setMessage(URLEncoder.encode(jsonResult.getMessage(), "UTF-8"));
            }
        }
        return jsonResult;
    }

    /**
     * 跳转到 申诉详情页面
     * @param request
     * @param response
     * @param dataMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/complaintdetail.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toComplainApplyDetail(HttpServletRequest request,
                                                                                   HttpServletResponse response,
                                                                                   Map<String, Object> dataMap,
                                                                                   @RequestParam(value = "id", required = true) Integer id) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        Integer userId = member.getId();
        //查询申诉信息
        ServiceResult<SellerComplaint> scResult = sellerComplaintService.getSellerComplaintById(id);
        if (!scResult.getSuccess()) {
            jsonResult.setMessage(scResult.getMessage());
            return jsonResult;
        }
        if (scResult.getResult() == null) {
            jsonResult.setMessage("获取数据失败，请重试");
            return jsonResult;
        }
        if (!scResult.getResult().getUserId().equals(userId)) {
            jsonResult.setMessage("您无权查看他人信息");
            return jsonResult;
        }

        //根据申诉信息取退换货信息
        if (scResult.getResult() != null) {
            SellerComplaint sellerComplaint = scResult.getResult();
            //            Integer backId = sellerComplaint.getProductBackId();
            //            Integer exchangeId = sellerComplaint.getProductExchangeId();
            //            if (backId != null && backId != 0) {
            //                MemberProductBack memberProductBack = (memberProductBackService
            //                    .getMemberProductBackById(backId)).getResult();
            //                dataMap.put("backInfo", memberProductBack);
            //            } else if (exchangeId != null && exchangeId != 0) {
            //                MemberProductExchange memberProductExchange = (memberProductExchangeService
            //                    .getMemberProductExchangeById(exchangeId)).getResult();
            //                dataMap.put("exchangeInfo", memberProductExchange);
            //            }

            ServiceResult<OrdersProduct> serviceResult = ordersProductService
                .getOrdersProductWithImgById(sellerComplaint.getOrderProductId());

            OrdersProduct ordersProduct = serviceResult.getResult();
            if (ordersProduct != null) {
                ServiceResult<Product> productResult = productFrontService
                    .getProductById(ordersProduct.getProductId());
                dataMap.put("product", productResult.getResult());
            }

            dataMap.put("ordersProduct", ordersProduct);
            dataMap.put("complaint", sellerComplaint);
        }

        jsonResult.setData(dataMap);
        return jsonResult;
    }

}
