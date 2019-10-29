package com.yixiekeji.web.controller.member;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yixiekeji.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.OrderLog;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.service.order.IOrderLogService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 会员订单中心controller
 *
 * @Filename: MemberOrderController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class AppMemberOrderController extends BaseController {

    @Resource
    private IMemberService         memberService;
    @Resource
    private IOrdersService         ordersService;
    @Resource
    private IOrderLogService       orderLogService;
    @Resource
    private ICourierCompanyService courierCompanyService;

    /**
     * 跳转到我的订单列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "app-order.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toUserCenter(HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          Map<String, Object> dataMap,
                                                                          String orderState,
                                                                          Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);

        Member member = memberService.getMemberById(memberId).getResult();
        queryMap.put("q_memberId", String.valueOf(member.getId()));
        if (!StringUtil.isEmpty(orderState, true)) {
            queryMap.put("q_orderState", String.valueOf(orderState));
        }
        // 查询订单列表
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<Orders>> serviceResult = ordersService
            .getShowOrderWithOrderProduct(feignUtil);
        pager = serviceResult.getPager();
        dataMap.put("ordersList", serviceResult.getResult());
        dataMap.put("ordersCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pager.getPageIndex());
        dataMap.put("orderState", orderState);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * ajax加载更多订单
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "app-moreorder.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Orders>> moreOrder(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> dataMap,
                                                                String orderState,
                                                                Integer pageIndex,
                                                                Integer memberId) {
        HttpJsonResult<List<Orders>> jsonResult = new HttpJsonResult<List<Orders>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);

        Member member = memberService.getMemberById(memberId).getResult();
        queryMap.put("q_memberId", String.valueOf(member.getId()));
        if (!StringUtil.isEmpty(orderState, true)) {
            queryMap.put("q_orderState", String.valueOf(orderState));
        }
        // 查询订单列表
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<Orders>> serviceResult = ordersService
            .getShowOrderWithOrderProduct(feignUtil);
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 跳转到 订单详情页面 显示物流、付款信息、网单信息
     * @param request
     * @param response
     * @param dataMap
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/app-orderdetail.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toOrdersDetail(HttpServletRequest request,
                                                                            HttpServletResponse response,
                                                                            Map<String, Object> dataMap,
                                                                            Integer memberId,
                                                                            @RequestParam(value = "id", required = true) Integer id) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        String errorMsg = "";
        try {
            // 查询订单列表
            ServiceResult<Orders> serviceResult = ordersService.getOrderWithOPById(id);
            // 查询订单日志
            ServiceResult<List<OrderLog>> orderLogResult = orderLogService.getOrderLogByOrderId(id);

            Orders orders = serviceResult.getResult();
            if (orders == null) {
                jsonResult.setMessage("订单不存在");
                return jsonResult;
            } else if (memberId.intValue() != orders.getMemberId().intValue()) {
                jsonResult.setMessage("您无权查看他人信息");
                return jsonResult;
            }
            // 计算是否显示退货和换货按钮
            Date createDate = orders.getCreateTime();
            long createTime = 0;
            if (createDate != null) {
                createTime = createDate.getTime();
            }
            long newTime = new Date().getTime();
            if (newTime - createTime < 15 * 24 * 60 * 60 * 1000) {
                orders.setIsShowBackAndExchange(true);
            } else {
                orders.setIsShowBackAndExchange(false);
            }

            List<OrderLog> logList = orderLogResult.getResult();
            if (orders != null && orders.getLogisticsId() > 0) {
                // 快递100查询物流信息
                ServiceResult<CourierCompany> courierResult = courierCompanyService
                    .getCourierCompanyById(orders.getLogisticsId());
                CourierCompany courierCompany = courierResult.getResult();
                if (courierCompany != null) {
                    String url = "http://api.kuaidi100.com/api?id=" + EjavashopConfig.KUAIDI100_KEY;
                    url += "&com=" + courierCompany.getCompanyMark();
                    url += "&nu=" + orders.getLogisticsNumber();
                    url += "&show=0";
                    url += "&muti=1";
                    url += "&order=asc";

                    String sendGet = HttpClientUtil.sendGet(url);

                    Map<String, Object> fromJson = JsonUtil.fromJson(sendGet);
                    Object status = null;
                    if (fromJson != null) {
                        status = fromJson.get("status");
                    }
                    // 查询结果状态： 0：物流单暂无结果， 1：查询成功， 2：接口出现异常
                    if (status != null && "1".equals(status.toString())) {
                        List<Map<String, String>> list = (List<Map<String, String>>) fromJson
                            .get("data");
                        for (Map<String, String> map : list) {
                            OrderLog orderLog = new OrderLog();
                            SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                            try {
                                orderLog.setCreateTime(simpleDateFormat.parse(map.get("time")));
                                orderLog.setContent(map.get("context"));
                                orderLog.setOperatingName(courierCompany.getCompanyName());
                            } catch (ParseException e) {
                                log.error(e.getMessage(), e);
                            }
                            logList.add(orderLog);
                        }
                    } else {
                        log.error("物流信息查询错误：status=" + status.toString());
                        log.error("物流信息查询错误：message=" + fromJson.get("message"));
                        log.error("物流公司：" + courierCompany.getCompanyName());
                        log.error("物流单号：" + orders.getLogisticsNumber());
                    }

                    Collections.sort(logList, new Comparator<OrderLog>() {
                        public int compare(OrderLog arg0, OrderLog arg1) {
                            return arg0.getCreateTime().compareTo(arg1.getCreateTime());
                        }
                    });
                }
            }

            dataMap.put("orderLogList", logList);
            dataMap.put("order", orders);
        } catch (Exception e) {
            if (e instanceof BusinessException)
                errorMsg = e.getMessage();
            else
                e.printStackTrace();

            jsonResult.setMessage(errorMsg);
        }

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 取消订单
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-cancalorder.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> cancalOrder(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             Map<String, Object> dataMap,
                                                             Integer memberId,
                                                             @RequestParam(value = "id", required = true) Integer id) {
        Member member = memberService.getMemberById(memberId).getResult();
        //取消订单
        ServiceResult<Boolean> serviceResult = ordersService.cancelOrder(id, member.getId(),
            member.getName());

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;

    }

    /**
     * 确认收货
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/app-goodreceive.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> goodsReceipt(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              Integer memberId,
                                                              @RequestParam(value = "ordersId", required = true) Integer ordersId) {
        Member member = memberService.getMemberById(memberId).getResult();
        ServiceResult<Boolean> serviceResult = ordersService.goodsReceipt(member, ordersId);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     *  1：根据会员Id 查找当前会员的已发货订单列表。
     *  2：根据发货单查找物流信息。
     *
     * @param request
     * @param response
     * @param memberId
     * @return
     */
    @GetMapping("/app-queryLogisticByMemberId.html")
    @ResponseBody
    public ServiceResult queryLogisticByMemberId(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "memberId", required = true) Integer memberId) {


        // 根据会员ID 查找当前会员订单信息, 订单为已发货的信息。
        ServiceResult serviceResult = ordersService.getOrdersByMemberIdAndState(memberId, Orders.ORDER_STATE_4);
        List<Orders> orders = (List<Orders>)serviceResult.getResult();
        if(orders==null || orders.size()==0){
            Map dataMap = new HashMap();
            dataMap.put("info", "当前会员没有物流信息。");
            serviceResult.setResult(dataMap);
        }

        List list = new ArrayList();
        for(Orders order : orders){
            String com = order.getLogisticsName();
            String num = order.getLogisticsNumber();

            String wuliuInfo = Kuaidi100Utils.synQueryData(com, num, null, null, null, 0);

            // 查找当前订单的商品信息。
            serviceResult = ordersService.getOrderWithOPById(order.getId());
            Orders ordersWithProd = (Orders)serviceResult.getResult();
            Map dataMap = new HashMap();
            dataMap.put("logistics", wuliuInfo);
            dataMap.put("order", ordersWithProd);
            list.add(dataMap);
        }
        serviceResult.setResult(list);

        return serviceResult;
    }


    /**
     * 根据子订单（is_parent=0）ID 查找当前订单物流信息。
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @GetMapping("/app-queryLogisticByOrderId.html")
    @ResponseBody
    public ServiceResult queryLogisticByOrderId(HttpServletRequest request,
                                                HttpServletResponse response,
                                                @RequestParam(value = "orderId", required = true) Integer orderId) {

        ServiceResult serviceResult = ordersService.getOrdersById(orderId);
        Orders order = (Orders) serviceResult.getResult();
        if(order==null){
            Map dataMap = new HashMap();
            dataMap.put("info", "订单不存在。");
            serviceResult.setResult(dataMap);
        }

        String com = order.getLogisticsName();
        String num = order.getLogisticsNumber();

        String wuliuInfo = Kuaidi100Utils.synQueryData(com, num, null, null, null, 0);

        serviceResult.setResult(wuliuInfo);

        return serviceResult;
    }


}
