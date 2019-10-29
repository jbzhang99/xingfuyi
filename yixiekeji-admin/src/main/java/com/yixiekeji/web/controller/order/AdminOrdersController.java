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
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 卖家订单controller
 *                       
 * @Filename: AdminOrdersController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/order/orders", produces = "application/json;charset=UTF-8")
public class AdminOrdersController extends BaseController {
    @Resource
    private IOrdersService        orderService;
    @Resource
    private IMemberService        memberService;
    @Resource
    private IOrdersProductService ordersProductService;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/order/orders/orderslist";
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
        return "admin/order/orders/orderslist1";
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
        return "admin/order/orders/orderslist2";
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
        return "admin/order/orders/orderslist3";
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
        return "admin/order/orders/orderslist4";
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
        return "admin/order/orders/orderslist5";
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
        return "admin/order/orders/orderslist6";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Orders>> list(HttpServletRequest request,
                                                           Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        //        queryMap.put("q_paymentCode", "OFFLINE");
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
     * 确认收款
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "submitpay", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> submitPay(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Integer id) {

        ServiceResult<Orders> orderResult = orderService.getOrdersById(id);
        if (!orderResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(orderResult.getCode())) {
                throw new RuntimeException(orderResult.getMessage());
            } else {
                throw new BusinessException(orderResult.getMessage());
            }
        }
        Orders ordersDb = orderResult.getResult();

        if (ordersDb.getPaymentStatus().equals(Orders.PAYMENT_STATUS_1)) {
            return new HttpJsonResult<Boolean>("该订单已经付款，请勿重复操作！");
        }

        if (!ordersDb.getOrderState().equals(Orders.ORDER_STATE_4)
            && !ordersDb.getOrderState().equals(Orders.ORDER_STATE_5)) {
            return new HttpJsonResult<Boolean>("已发货或者已完成的订单才能确认收款。");
        }

        SystemAdmin systemAdmin = WebAdminSession.getAdminUser(request);
        Orders orders = new Orders();
        orders.setId(id);
        //已付款
        orders.setPaymentStatus(Orders.PAYMENT_STATUS_1);
        orders.setPayTime(new Date());
        orders.setCodconfirmId(systemAdmin.getId());
        orders.setCodconfirmName(systemAdmin.getName());
        orders.setCodconfirmTime(new Date());
        orders.setCodconfirmState(Orders.CODCONFIRM_STATE_2);
        orders.setMoneyPaidReality(ordersDb.getMoneyOrder().subtract(ordersDb.getMoneyPaidBalance())
            .subtract(ordersDb.getMoneyIntegral()));

        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setOrders(orders);
        feignObjectUtil.setSystemAdmin(systemAdmin);

        ServiceResult<Integer> serviceResult = orderService.updateOrdersByAdmin(feignObjectUtil,
            Orders.SUBMIT_PAY);

        HttpJsonResult<Boolean> jsonResult = null;
        if (serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>();
            // 送积分和经验值
            if (serviceResult.getResult() != null && serviceResult.getResult() > 0) {
                memberService.memberOrderSendValue(ordersDb.getMemberId(), ordersDb.getMemberName(),
                    ordersDb.getId());
            }
        } else {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }

        memberService.memberOrderSendValue(ordersDb.getMemberId(), ordersDb.getMemberName(),
            ordersDb.getId());

        return jsonResult;
    }

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
        Orders orders = orderService.getOrderWithOPById(id).getResult();
        dataMap.put("orders", orders);
        return "admin/order/orders/ordersprint";
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

        if (StringUtils.isEmpty(id) || id.equals("0")) {
            dataMap.put("info", "订单id不能为空，请重试");
            return "admin/500";
        }

        //获取订单
        ServiceResult<Orders> ordersResult = orderService.getOrdersById(Integer.parseInt(id));
        if (!ordersResult.getSuccess() || ordersResult.getResult() == null) {
            dataMap.put("info", "获取订单失败，请重试");
            return "admin/500";
        }
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("orders", ordersResult.getResult());
        return "admin/order/orders/ordersdetails";
    }

}
