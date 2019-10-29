package com.yixiekeji.web.controller.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerApply;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.seller.ISellerApplyService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 订单管理controller
 *                       
 * @Filename: SellerOrdersController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/order/orders")
public class SellerOrdersController extends BaseController {

    @Resource
    private IOrdersService         orderService;
    @Resource
    private ICourierCompanyService courierCompanyService;
    @Resource
    private ISellerService         sellerService;
    @Resource
    private ISellerApplyService    sellerApplyService;
    @Resource
    private IRegionsService        resionsService;

    /**
     * 列表页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist";
    }

    /**
     * 未付款订单列表页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "state1", method = { RequestMethod.GET })
    public String listState1(HttpServletRequest request,
                             Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist1";
    }

    /**
     * 待确认订单列表页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "state2", method = { RequestMethod.GET })
    public String listState2(HttpServletRequest request,
                             Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist2";
    }

    /**
     * 待发货订单列表页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "state3", method = { RequestMethod.GET })
    public String listState3(HttpServletRequest request,
                             Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist3";
    }

    /**
     * 已发货订单列表页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "state4", method = { RequestMethod.GET })
    public String listState4(HttpServletRequest request,
                             Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist4";
    }

    /**
     * 已完成订单列表页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "state5", method = { RequestMethod.GET })
    public String listState5(HttpServletRequest request,
                             Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist5";
    }

    /**
     * 已取消订单列表页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "state6", method = { RequestMethod.GET })
    public String listState6(HttpServletRequest request,
                             Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/order/orders/orderslist6";
    }

    /**
     * 列表页数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Orders>> list(HttpServletRequest request,
                                                           Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        queryMap.put("q_sellerId",
            WebSellerSession.getSellerUser(request).getSellerId().toString());
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Orders>> serviceResult = orderService.getSonOrders(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<Orders>> jsonResult = new HttpJsonResult<List<Orders>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 确认订单，占库存
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "confirm", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> confirm(HttpServletRequest request,
                                                         HttpServletResponse response, Integer id) {

        ServiceResult<Boolean> serviceResult = orderService.confirmOrdersBySeller(id,
            WebSellerSession.getSellerUser(request));

        HttpJsonResult<Boolean> jsonResult = null;
        if (serviceResult.getSuccess() && serviceResult.getResult()) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }

        return jsonResult;
    }

    /**
     * 发货页面
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "delivery", method = { RequestMethod.GET })
    public String delivery(HttpServletRequest request, Map<String, Object> dataMap, Integer id,
                           Integer source) {
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<Orders> serviceResult = orderService.getOrdersById(id);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        Orders orders = serviceResult.getResult();
        if (orders == null) {
            return "/seller/404";
        }
        if (!orders.getSellerId().equals(user.getSellerId())) {
            return "/seller/unauth";
        }
        List<CourierCompany> list = courierCompanyService.list();
        dataMap.put("orders", orders);
        dataMap.put("source", source);
        dataMap.put("courierCompanylist", list);
        return "seller/order/orders/devlivery";
    }

    /**
     * 卖家发货
     * @param request
     * @param dataMap
     * @param id
     * @return
     */
    @RequestMapping(value = "doDelivery", method = { RequestMethod.POST })
    public String doDelivery(HttpServletRequest request, String ccName, String ccId,
                             Integer ordersId, String giftNum, Integer source) {
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<Orders> serviceResult = orderService.getOrdersById(ordersId);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        Orders ordersDb = serviceResult.getResult();
        if (ordersDb == null) {
            return "/seller/404";
        }
        if (!ordersDb.getSellerId().equals(user.getSellerId())) {
            return "/seller/unauth";
        }
        if (!ordersDb.getOrderState().equals(Orders.ORDER_STATE_3)) {
            if (source != null && source.equals(3)) {
                return "redirect:/seller/order/orders/state3";
            } else {
                return "redirect:/seller/order/orders";
            }
        }
        Orders orders = new Orders();
        orders.setId(ordersId);
        orders.setOrderState(Orders.ORDER_STATE_4);
        orders.setLogisticsId(ccId != null && !"".equals(ccId) ? Integer.valueOf(ccId) : -1);
        orders.setLogisticsName(ccName != null && !"".equals(ccName) ? ccName.trim() : "");
        orders.setLogisticsNumber(giftNum != null && !"".equals(giftNum) ? giftNum : "");
        orders.setDeliverTime(new Date());

        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setSellerUser(WebSellerSession.getSellerUser(request));
        feignObjectUtil.setOrders(orders);

        orderService.updateOrdersBySeller(feignObjectUtil, Orders.DELIVER);

        if (source != null && source.equals(3)) {
            return "redirect:/seller/order/orders/state3";
        }

        return "redirect:/seller/order/orders";
    }

    //    /**
    //     * 取消订单
    //     * @param request
    //     * @param id
    //     */
    //    @RequestMapping(value = "cancelorder", method = { RequestMethod.GET })
    //    public @ResponseBody HttpJsonResult<Boolean> cancelOrder(HttpServletRequest request,
    //                                                             HttpServletResponse response,
    //                                                             Integer id) {
    //
    //        SellerUser user = WebSellerSession.getSellerUser(request);
    //        ServiceResult<Orders> orderResult = orderService.getOrdersById(id);
    //        if (!orderResult.getSuccess()) {
    //            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(orderResult.getCode())) {
    //                throw new RuntimeException(orderResult.getMessage());
    //            } else {
    //                throw new BusinessException(orderResult.getMessage());
    //            }
    //        }
    //        Orders ordersDb = orderResult.getResult();
    //        if (ordersDb == null) {
    //            return new HttpJsonResult<Boolean>("获取订单信息失败，请重试");
    //        }
    //        if (!ordersDb.getSellerId().equals(user.getSellerId())) {
    //            return new HttpJsonResult<Boolean>("您无权处理该操作，请重试");
    //        }
    //
    //        if (!ordersDb.getOrderState().equals(Orders.ORDER_STATE_1)
    //            && !ordersDb.getOrderState().equals(Orders.ORDER_STATE_2)) {
    //            return new HttpJsonResult<Boolean>("只有待付款或待确认的订单可以执行取消操作！");
    //        }
    //
    //        ServiceResult<Boolean> serviceResult = orderService.cancelOrderBySeller(id,
    //            WebSellerSession.getSellerUser(request));
    //
    //        HttpJsonResult<Boolean> jsonResult = null;
    //        if (serviceResult.getSuccess()) {
    //            jsonResult = new HttpJsonResult<Boolean>();
    //        } else {
    //            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
    //        }
    //
    //        return jsonResult;
    //    }

    /**
     * 订单打印
     * 
     * @param request
     * @param dataMap
     * @param id
     * @return
     */
    @RequestMapping(value = "print", method = { RequestMethod.GET })
    public String print(HttpServletRequest request, Map<String, Object> dataMap, Integer id) {
        SellerUser user = WebSellerSession.getSellerUser(request);
        Orders orders = orderService.getOrderWithOPById(id).getResult();
        if (orders == null) {
            return "seller/404";
        }
        if (!orders.getSellerId().equals(user.getSellerId())) {
            return "seller/unauth";
        }
        dataMap.put("orders", orders);
        return "seller/order/orders/ordersprint";
    }

    /**
     * 订单打印
     * 
     * @param request
     * @param dataMap
     * @param id
     * @return
     */
    @RequestMapping(value = "printcourier", method = { RequestMethod.GET })
    public String printCourier(HttpServletRequest request, Map<String, Object> dataMap,
                               Integer id) {
        SellerUser user = WebSellerSession.getSellerUser(request);

        Orders orders = orderService.getOrderWithOPById(id).getResult();

        Seller seller = new Seller();
        SellerApply sellerApply = null;
        if (orders == null) {
            return "/seller/404";
        }
        if (!orders.getSellerId().equals(user.getSellerId())) {
            return "/seller/unauth";
        }
        if (orders != null) {
            seller = sellerService.getSellerById(orders.getSellerId()).getResult();
            if (seller != null) {
                sellerApply = sellerApplyService.getSellerApplyByUser(seller.getMemberId())
                    .getResult();
            }
        }

        CourierCompany courierCompany = courierCompanyService
            .getCourierCompanyById(orders.getLogisticsId()).getResult();
        dataMap.put("courierCompany", courierCompany);

        dataMap.put("sendName", seller.getSellerName());
        courierCompany.setContent(
            printString(courierCompany.getContent(), "sendName", seller.getSellerName()));
        dataMap.put("sendUnit", "");
        courierCompany.setContent(printString(courierCompany.getContent(), "sendUnit", ""));

        String province = "";
        String city = "";
        if (!StringUtil.isEmpty(sellerApply.getCompanyProvince(), true)) {
            Regions provinceRegion = resionsService
                .getRegionsById(ConvertUtil.toInt(sellerApply.getCompanyProvince(), 0)).getResult();
            province = provinceRegion == null ? "" : provinceRegion.getRegionName();

            Regions cityRegion = resionsService
                .getRegionsById(ConvertUtil.toInt(sellerApply.getCompanyCity(), 0)).getResult();
            city = cityRegion == null ? "" : cityRegion.getRegionName();
        }
        dataMap.put("sendProvince", province);
        courierCompany
            .setContent(printString(courierCompany.getContent(), "sendProvince", province));

        dataMap.put("sendCity", city);
        courierCompany.setContent(printString(courierCompany.getContent(), "sendCity", city));

        dataMap.put("sendAdds", sellerApply.getCompanyAdd());
        courierCompany.setContent(
            printString(courierCompany.getContent(), "sendAdds", sellerApply.getCompanyAdd()));

        dataMap.put("consigneeName", orders.getName());
        courierCompany.setContent(
            printString(courierCompany.getContent(), "consigneeName", orders.getName()));

        dataMap.put("sendPhone", "");
        courierCompany.setContent(printString(courierCompany.getContent(), "sendPhone", ""));

        dataMap.put("orders", orders);

        province = "";
        city = "";
        String area = "";
        Regions provinceRegion = resionsService
            .getRegionsById(ConvertUtil.toInt(orders.getProvinceId(), 0)).getResult();
        province = provinceRegion == null ? "" : provinceRegion.getRegionName();

        Regions cityRegion = resionsService.getRegionsById(ConvertUtil.toInt(orders.getCityId(), 0))
            .getResult();
        city = cityRegion == null ? "" : cityRegion.getRegionName();

        Regions areaRegion = resionsService.getRegionsById(ConvertUtil.toInt(orders.getAreaId(), 0))
            .getResult();
        area = areaRegion == null ? "" : areaRegion.getRegionName();

        dataMap.put("consigneeProvince", province);
        dataMap.put("consigneeCity", city);
        dataMap.put("consigneeArea", area);
        dataMap.put("consigneeAdds", orders.getAddressAll() + "" + orders.getAddressInfo());
        dataMap.put("sendGoods", "商品");

        courierCompany
            .setContent(printString(courierCompany.getContent(), "consigneeProvince", province));
        courierCompany.setContent(printString(courierCompany.getContent(), "consigneeCity", city));
        courierCompany.setContent(printString(courierCompany.getContent(), "consigneeArea", area));
        courierCompany.setContent(printString(courierCompany.getContent(), "consigneeAdds",
            orders.getAddressAll() + "" + orders.getAddressInfo()));
        courierCompany.setContent(printString(courierCompany.getContent(), "sendGoods", "商品"));

        List<OrdersProduct> opList = orders.getOrderProductList();

        int number = 0;
        if (opList != null && opList.size() > 0) {
            for (OrdersProduct op : opList) {
                number += op.getNumber();
            }
        }

        dataMap.put("sendNumber", number);
        dataMap.put("consigneePhone", orders.getMobile());

        courierCompany
            .setContent(printString(courierCompany.getContent(), "sendNumber", number + ""));
        courierCompany.setContent(
            printString(courierCompany.getContent(), "consigneePhone", orders.getMobile()));

        return "seller/order/orders/ordersprintorder";
    }

    /**
     * 
     * @param content 打印的文档
     * @param str1 要替换的值
     * @param str2 替换的值
     */
    private String printString(String content, String str1, String str2) {
        String string = "\\$\\{" + str1 + "\\}";
        if (content == null || str1 == null || str1 == null) {
            return "";
        }
        return content.replaceAll(string, str2);
    }

    /**
     * 订单详情
     * 
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "details", method = { RequestMethod.GET })
    public String details(HttpServletRequest request, HttpServletResponse response,
                          Map<String, Object> dataMap, String id) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        if (StringUtils.isEmpty(id) || id.equals("0")) {
            dataMap.put("info", "订单id不能为空，请重试");
            return "seller/500";
        }

        //获取订单
        ServiceResult<Orders> ordersResult = orderService.getOrdersById(Integer.parseInt(id));
        if (!ordersResult.getSuccess() || ordersResult.getResult() == null) {
            dataMap.put("info", "获取订单失败，请重试");
            return "seller/500";
        }
        if (!ordersResult.getResult().getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("orders", ordersResult.getResult());
        return "seller/order/orders/ordersdetails";
    }
}
