package com.yixiekeji.web.controller.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.HttpClientUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.JsonUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.member.MemberProductBackLog;
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.entity.member.MemberProductExchangeLog;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.service.member.IMemberProductBackLogService;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.service.member.IMemberProductExchangeLogService;
import com.yixiekeji.service.member.IMemberProductExchangeService;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 客户服务：退换货
 *                       
 * @Filename: MemberBackExchangeController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberBackExchangeController extends BaseController {

    @Resource
    private IOrdersService                   ordersService;

    @Resource
    private IMemberProductBackService        memberProductBackService;

    @Resource
    private IMemberProductExchangeService    memberProductExchangeService;

    @Resource
    private IOrdersProductService            ordersProductService;
    @Resource
    private ICourierCompanyService           courierCompanyService;
    @Resource
    private IMemberProductBackLogService     memberProductBackLogService;
    @Resource
    private IMemberProductExchangeLogService memberProductExchangeLogService;

    /**
     * 跳转到 退货申请页面 显示该订单的所有商品 然后可以对商品发起退货申请
     * @param request
     * @param response
     * @param dataMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/backapply.html", method = { RequestMethod.GET })
    public String toProductBackApply(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> dataMap,
                                     @RequestParam(value = "id", required = true) Integer id) {
        Integer memberId = WebFrontSession.getLoginedUser(request).getId();
        //查询订单信息
        ServiceResult<Orders> serviceResult = ordersService.getOrderWithOPById(id);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/h5/commons/error";
        }
        if (serviceResult.getResult() == null) {
            dataMap.put("info", "获取数据失败，请重试。");
            return "/h5/commons/error";
        }
        if (!serviceResult.getResult().getMemberId().equals(memberId)) {
            dataMap.put("info", "您无权访问他人信息");
            return "/h5/commons/error";
        }

        dataMap.put("order", serviceResult.getResult());

        return "h5/member/service/backexchange";
    }

    /**
     * 判断 是否可以发起退换货申请  
     * @param request
     * @param response
     * @param dataMap
     * @param orderProductId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/canbackorexchange.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Integer> canApplyProductBackOrExchange(HttpServletRequest request,
                                                                               HttpServletResponse response,
                                                                               Map<String, Object> dataMap,
                                                                               @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                                                               @RequestParam(value = "orderId", required = true) Integer orderId) {

        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        serviceResult = memberProductBackService.canApplyProductBackOrExchange(orderId,
            orderProductId, member.getId());
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();

        if (!serviceResult.getSuccess() || serviceResult.getResult() == null) {
            jsonResult = new HttpJsonResult<Integer>(serviceResult.getMessage());
        }

        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 退货申请信息提交
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/doproductback.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> productBackSubmit(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   Map<String, Object> dataMap,
                                                                   MemberProductBack memberProductBack) {
        Member member = WebFrontSession.getLoginedUser(request);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMemberProductBack(memberProductBack);
        feignObjectUtil.setMember(member);

        ServiceResult<Boolean> serviceResult = memberProductBackService
            .saveMemberProductBack(feignObjectUtil);
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
     * 换货申请信息提交
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/doproductexchange.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> productExchangeSubmit(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       Map<String, Object> dataMap,
                                                                       MemberProductExchange memberProductExchange) {
        Member member = WebFrontSession.getLoginedUser(request);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMemberProductExchange(memberProductExchange);
        feignObjectUtil.setMember(member);

        ServiceResult<Boolean> serviceResult = memberProductExchangeService
            .saveMemberProductExchange(feignObjectUtil);
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
     * 跳转到 退货列表页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/back.html", method = { RequestMethod.GET })
    public String back(HttpServletRequest request, HttpServletResponse response,
                       Map<String, Object> dataMap) {

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<List<MemberProductBack>> serviceResult = memberProductBackService
            .getBackListWithPrdAndOp(pager, member.getId());
        pager = serviceResult.getPager();

        dataMap.put("backList", serviceResult.getResult());
        dataMap.put("backCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());

        return "h5/member/service/back/back";
    }

    /**
     * ajax异步加载更多
     * @param request
     * @param response
     * @param dataMap
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/moreback.html", method = { RequestMethod.GET })
    public String moreBack(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap, Integer pageIndex) {

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<List<MemberProductBack>> serviceResult = memberProductBackService
            .getBackListWithPrdAndOp(pager, member.getId());

        dataMap.put("backList", serviceResult.getResult());

        return "h5/member/service/back/backlist";
    }

    /**
     * 跳转到 换货列表页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exchange.html", method = { RequestMethod.GET })
    public String toProductExchangeList(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> dataMap) {

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<List<MemberProductExchange>> serviceResult = memberProductExchangeService
            .getExchangeListWithPrdAndOp(pager, member.getId());
        pager = serviceResult.getPager();

        dataMap.put("exchangeList", serviceResult.getResult());
        dataMap.put("exchangeCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());

        return "h5/member/service/exchange/exchange";
    }

    /**
     * ajax异步加载更多
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/moreexchange.html", method = { RequestMethod.GET })
    public String moreExchange(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> dataMap, Integer pageIndex) {

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<List<MemberProductExchange>> serviceResult = memberProductExchangeService
            .getExchangeListWithPrdAndOp(pager, member.getId());

        dataMap.put("exchangeList", serviceResult.getResult());

        return "h5/member/service/exchange/exchangelist";
    }

    /**
     * 跳转到 退货 发货页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/backDeliverGoods.html", method = { RequestMethod.GET })
    public String backDeliverGoods(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> dataMap,
                                   @RequestParam(value = "backid", required = true) Integer backid) {

        //查询退货信息
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<MemberProductBack> serviceResult = memberProductBackService
            .getMemberProductBackById(backid);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/h5/commons/error";
        }
        MemberProductBack memberProductBack = serviceResult.getResult();
        if (memberProductBack == null) {
            dataMap.put("info", "获取退货信息失败，请重试");
            return "/front/commons/error";
        }

        if (serviceResult.getResult() == null) {
            dataMap.put("info", "获取数据失败，请重试。");
            return "/h5/commons/error";
        }
        if (!serviceResult.getResult().getMemberId().equals(member.getId())) {
            dataMap.put("info", "您无权访问他人信息");
            return "/h5/commons/error";
        }

        //根据网单id 查询网单信息
        ServiceResult<OrdersProduct> result = ordersProductService
            .getOrdersProductWithImgById(memberProductBack.getOrderProductId());

        List<CourierCompany> courierCompanyList = courierCompanyService.list();

        dataMap.put("orderProduct", result.getResult());
        dataMap.put("memberProductBack", memberProductBack);
        dataMap.put("courierCompanyList", courierCompanyList);
        return "h5/member/service/back/backdelivergoods";
    }

    /**
     * 退货 发货页面
     * @param request
     * @param response
     * @param dataMap
     * @param memberProductBack
     * @return
     */
    @RequestMapping(value = "/doBackDeliverGoods.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> doBackDeliverGoods(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    Map<String, Object> dataMap,
                                                                    MemberProductBack memberProductBack) {
        HttpJsonResult<Boolean> json = new HttpJsonResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMemberProductBack(memberProductBack);
        feignObjectUtil.setMember(member);

        ServiceResult<Boolean> serviceResult = memberProductBackService
            .doBackDeliverGoods(feignObjectUtil);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return json;
    }

    /**
     * 跳转到 换货 发货页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exchangeDeliverGoods.html", method = { RequestMethod.GET })
    public String exchangeDeliverGoods(HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> dataMap,
                                       @RequestParam(value = "exchangeid", required = true) Integer exchangeid) {

        //查询换货信息
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<MemberProductExchange> serviceResult = memberProductExchangeService
            .getMemberProductExchangeById(exchangeid);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/h5/commons/error";
        }
        MemberProductExchange memberProductExchange = serviceResult.getResult();
        if (memberProductExchange == null) {
            dataMap.put("info", "获取换货信息失败，请重试");
            return "/front/commons/error";
        }

        if (serviceResult.getResult() == null) {
            dataMap.put("info", "获取数据失败，请重试。");
            return "/h5/commons/error";
        }
        if (!serviceResult.getResult().getMemberId().equals(member.getId())) {
            dataMap.put("info", "您无权访问他人信息");
            return "/h5/commons/error";
        }
        //根据网单id 查询网单信息
        ServiceResult<OrdersProduct> result = ordersProductService
            .getOrdersProductWithImgById(memberProductExchange.getOrderProductId());

        List<CourierCompany> courierCompanyList = courierCompanyService.list();

        dataMap.put("orderProduct", result.getResult());
        dataMap.put("memberProductExchange", memberProductExchange);
        dataMap.put("courierCompanyList", courierCompanyList);
        return "h5/member/service/exchange/exchangedelivergoods";
    }

    /**
     * 退货 发货页面
     * @param request
     * @param response
     * @param dataMap
     * @param memberProductBack
     * @return
     */
    @RequestMapping(value = "/doExchangeDeliverGoods.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> doExchangeDeliverGoods(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        Map<String, Object> dataMap,
                                                                        MemberProductExchange memberProductExchange) {
        HttpJsonResult<Boolean> json = new HttpJsonResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMemberProductExchange(memberProductExchange);
        feignObjectUtil.setMember(member);

        ServiceResult<Boolean> serviceResult = memberProductExchangeService
            .doExchangeDeliverGoods(feignObjectUtil);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return json;
    }

    /**
     * 退货时退件查看物流消息
     * @param request
     * @param response
     * @param dataMap
     * @param exchangeid
     * @return
     */
    @RequestMapping(value = "/lookBackLogistics.html", method = { RequestMethod.GET })
    public String lookBackLogistics(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> dataMap,
                                    @RequestParam(value = "backid", required = true) Integer backid) {

        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<MemberProductBack> serviceResult = memberProductBackService
            .getMemberProductBackById(backid);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/h5/commons/error";
        }
        MemberProductBack memberProductBack = serviceResult.getResult();
        if (memberProductBack == null) {
            dataMap.put("info", "获取退货信息失败，请重试");
            return "/front/commons/error";
        }

        if (serviceResult.getResult() == null) {
            dataMap.put("info", "获取数据失败，请重试。");
            return "/h5/commons/error";
        }
        if (!serviceResult.getResult().getMemberId().equals(member.getId())) {
            dataMap.put("info", "您无权访问他人信息");
            return "/h5/commons/error";
        }
        // 物流信息
        ServiceResult<List<MemberProductBackLog>> memberProductBackLogListResult = memberProductBackLogService
            .getMemberProductBackLogByMemberProductBackId(backid);
        List<MemberProductBackLog> memberProductBackLogList = memberProductBackLogListResult
            .getResult();
        if (null != memberProductBackLogList && memberProductBackLogList.size() > 0) {
            CourierCompany courierCompany = courierCompanyService
                .getCourierCompanyById(memberProductBack.getLogisticsId()).getResult();
            if (courierCompany != null) {
                String url = "http://api.kuaidi100.com/api?id=" + EjavashopConfig.KUAIDI100_KEY;
                url += "&com=" + courierCompany.getCompanyMark();
                url += "&nu=" + memberProductBack.getLogisticsNumber();
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
                        MemberProductBackLog memberProductBackLog = new MemberProductBackLog();
                        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                        try {
                            memberProductBackLog
                                .setCreateTime(simpleDateFormat.parse(map.get("time")));
                            memberProductBackLog.setContent(map.get("context"));
                            memberProductBackLog.setOperatingName(courierCompany.getCompanyName());
                        } catch (ParseException e) {
                            log.error(e.getMessage(), e);
                        }
                        memberProductBackLogList.add(memberProductBackLog);
                    }
                } else {
                    log.error("物流信息查询错误：status=" + status.toString());
                    log.error("物流信息查询错误：message=" + fromJson.get("message"));
                    log.error("物流公司：" + courierCompany.getCompanyName());
                    log.error("物流单号：" + memberProductBack.getLogisticsNumber());
                }

                Collections.sort(memberProductBackLogList, new Comparator<MemberProductBackLog>() {
                    public int compare(MemberProductBackLog arg0, MemberProductBackLog arg1) {
                        return arg0.getCreateTime().compareTo(arg1.getCreateTime());
                    }
                });
            }
        }
        dataMap.put("memberProductBackLogList", memberProductBackLogList);
        dataMap.put("memberProductBack", memberProductBack);

        return "h5/member/service/back/lookbacklogistics";
    }

    @RequestMapping(value = "/lookExchangeLogistics.html", method = { RequestMethod.GET })
    public String lookExchangeLogistics(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> dataMap,
                                        @RequestParam(value = "exchangeid", required = true) Integer exchangeid) {
        //查询换货信息
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<MemberProductExchange> serviceResult = memberProductExchangeService
            .getMemberProductExchangeById(exchangeid);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/h5/commons/error";
        }
        MemberProductExchange memberProductExchange = serviceResult.getResult();
        if (memberProductExchange == null) {
            dataMap.put("info", "获取换货信息失败，请重试");
            return "/front/commons/error";
        }

        if (serviceResult.getResult() == null) {
            dataMap.put("info", "获取数据失败，请重试。");
            return "/h5/commons/error";
        }
        if (!serviceResult.getResult().getMemberId().equals(member.getId())) {
            dataMap.put("info", "您无权访问他人信息");
            return "/h5/commons/error";
        }

        // 物流信息
        // 换货单操作日志
        ServiceResult<List<MemberProductExchangeLog>> memberProductExchangeLogListResult = memberProductExchangeLogService
            .getMemberProductExchangeLogByMemberProductExchangeId(exchangeid);
        List<MemberProductExchangeLog> memberProductExchangeLogList = memberProductExchangeLogListResult
            .getResult();
        // 退件物流信息
        this.getExchangeBack(memberProductExchange, dataMap);
        // 换件物流信息
        this.getExchangeExchange(memberProductExchange, dataMap);
        dataMap.put("memberProductExchangeLogList", memberProductExchangeLogList);
        return "h5/member/service/exchange/lookexchangelogistics";
    }

    /**
     * 换货时退件物流
     * @param memberProductExchange
     * @param dataMap
     */
    private void getExchangeBack(MemberProductExchange memberProductExchange,
                                 Map<String, Object> dataMap) {
        List<MemberProductExchangeLog> memberProductExchangeLogBackList = new ArrayList<MemberProductExchangeLog>();
        CourierCompany courierCompany = null;
        if (memberProductExchange.getLogisticsId() != null) {
            courierCompany = courierCompanyService
                .getCourierCompanyById(memberProductExchange.getLogisticsId()).getResult();
        }
        if (courierCompany != null) {
            String url = "http://api.kuaidi100.com/api?id=" + EjavashopConfig.KUAIDI100_KEY;
            url += "&com=" + courierCompany.getCompanyMark();
            url += "&nu=" + memberProductExchange.getLogisticsNumber();
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
                List<Map<String, String>> list = (List<Map<String, String>>) fromJson.get("data");
                for (Map<String, String> map : list) {
                    MemberProductExchangeLog memberProductExchangeLog = new MemberProductExchangeLog();
                    SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                    try {
                        memberProductExchangeLog
                            .setCreateTime(simpleDateFormat.parse(map.get("time")));
                        memberProductExchangeLog.setContent(map.get("context"));
                        memberProductExchangeLog.setOperatingName(courierCompany.getCompanyName());
                    } catch (ParseException e) {
                        log.error(e.getMessage(), e);
                    }
                    memberProductExchangeLogBackList.add(memberProductExchangeLog);
                }
            } else {
                log.error("物流信息查询错误：status=" + status.toString());
                log.error("物流信息查询错误：message=" + fromJson.get("message"));
                log.error("物流公司：" + courierCompany.getCompanyName());
                log.error("物流单号：" + memberProductExchange.getLogisticsNumber());
            }

            Collections.sort(memberProductExchangeLogBackList,
                new Comparator<MemberProductExchangeLog>() {
                    public int compare(MemberProductExchangeLog arg0,
                                       MemberProductExchangeLog arg1) {
                        return arg0.getCreateTime().compareTo(arg1.getCreateTime());
                    }
                });
        }

        dataMap.put("memberProductExchangeLogBackList", memberProductExchangeLogBackList);

    }

    /**
     * 换货时换件物流
     * @param memberProductExchange
     * @param dataMap
     */
    private void getExchangeExchange(MemberProductExchange memberProductExchange,
                                     Map<String, Object> dataMap) {
        List<MemberProductExchangeLog> memberProductExchangeLogExchangeList = new ArrayList<MemberProductExchangeLog>();
        CourierCompany courierCompany = null;
        if (memberProductExchange.getLogisticsId2() != null) {
            courierCompany = courierCompanyService
                .getCourierCompanyById(memberProductExchange.getLogisticsId2()).getResult();
        }
        if (courierCompany != null) {
            String url = "http://api.kuaidi100.com/api?id=" + EjavashopConfig.KUAIDI100_KEY;
            url += "&com=" + courierCompany.getCompanyMark();
            url += "&nu=" + memberProductExchange.getLogisticsNumber2();
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
                List<Map<String, String>> list = (List<Map<String, String>>) fromJson.get("data");
                for (Map<String, String> map : list) {
                    MemberProductExchangeLog memberProductExchangeLog = new MemberProductExchangeLog();
                    SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                    try {
                        memberProductExchangeLog
                            .setCreateTime(simpleDateFormat.parse(map.get("time")));
                        memberProductExchangeLog.setContent(map.get("context"));
                        memberProductExchangeLog.setOperatingName(courierCompany.getCompanyName());
                    } catch (ParseException e) {
                        log.error(e.getMessage(), e);
                    }
                    memberProductExchangeLogExchangeList.add(memberProductExchangeLog);
                }
            } else {
                log.error("物流信息查询错误：status=" + status.toString());
                log.error("物流信息查询错误：message=" + fromJson.get("message"));
                log.error("物流公司：" + courierCompany.getCompanyName());
                log.error("物流单号：" + memberProductExchange.getLogisticsNumber());
            }

            Collections.sort(memberProductExchangeLogExchangeList,
                new Comparator<MemberProductExchangeLog>() {
                    public int compare(MemberProductExchangeLog arg0,
                                       MemberProductExchangeLog arg1) {
                        return arg0.getCreateTime().compareTo(arg1.getCreateTime());
                    }
                });
        }

        dataMap.put("memberProductExchangeLogExchangeList", memberProductExchangeLogExchangeList);

    }

}
