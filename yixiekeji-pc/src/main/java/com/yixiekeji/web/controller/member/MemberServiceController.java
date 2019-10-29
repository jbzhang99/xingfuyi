package com.yixiekeji.web.controller.member;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.HttpClientUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.JsonUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.RandomUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberApplyDrawing;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.member.MemberProductBackLog;
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.entity.member.MemberProductExchangeLog;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.service.member.IMemberApplyDrawingService;
import com.yixiekeji.service.member.IMemberProductBackLogService;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.service.member.IMemberProductExchangeLogService;
import com.yixiekeji.service.member.IMemberProductExchangeService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerComplaintService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.seller.FrontSellerComplaintVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 客户服务：退换货、提款申请、申诉
 *                       
 * @Filename: MemberServiceController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberServiceController extends BaseController {

    @Resource
    private IOrdersService                   ordersService;

    @Resource
    private IOrdersProductService            ordersProductService;

    @Resource
    private IMemberProductBackService        memberProductBackService;

    @Resource
    private IMemberProductExchangeService    memberProductExchangeService;

    @Resource
    private ISellerComplaintService          sellerComplaintService;

    @Resource
    private IMemberApplyDrawingService       memberApplyDrawingService;

    @Resource
    private IMemberService                   memberService;

    @Resource
    private ICouponService                   couponService;

    @Resource
    private ICourierCompanyService           courierCompanyService;

    @Resource
    private IMemberProductBackLogService     memberProductBackLogService;

    @Resource
    private IMemberProductExchangeLogService memberProductExchangeLogService;

    @Resource
    private DomainUrlUtil                    domainUrlUtil;

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
        Integer memberId = WebFrontSession.getLoginedUser(request, response).getId();
        //查询订单信息
        ServiceResult<Orders> serviceResult = ordersService.getOrderWithOPById(id);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/front/commons/error";
        }
        if (serviceResult.getResult() == null) {
            dataMap.put("info", "获取数据失败，请重试。");
            return "/front/commons/error";
        }
        if (!serviceResult.getResult().getMemberId().equals(memberId)) {
            dataMap.put("info", "您无权访问他人信息");
            return "/front/commons/error";
        }

        dataMap.put("order", serviceResult.getResult());

        return "front/member/service/productbackapply";
    }

    /**
     * 跳转到 退货申请录入界面
     * @param request
     * @param response
     * @param dataMap
     * @param orderProductId  网单id
     * @return
     */
    @RequestMapping(value = "/productbackapply.html", method = { RequestMethod.GET })
    public String productBackApply(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> dataMap,
                                   @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                   @RequestParam(value = "orderId", required = true) Integer orderId) {

        Member member = WebFrontSession.getLoginedUser(request, response);
        //查询订单信息
        ServiceResult<Orders> orderResult = new ServiceResult<Orders>();
        orderResult = ordersService.getOrderWithOPById(orderId);
        if (!orderResult.getSuccess()) {
            dataMap.put("info", orderResult.getMessage());
            return "front/commons/error";
        }
        if (orderResult.getResult() == null) {
            dataMap.put("info", "获取订单信息失败，请重试");
            return "front/commons/error";
        }
        if (!orderResult.getResult().getMemberId().equals(member.getId())) {
            dataMap.put("info", "您无权操作该数据，请重试");
            return "front/commons/error";
        }

        ServiceResult<OrdersProduct> serviceResult = ordersProductService
            .getOrdersProductWithImgById(orderProductId);

        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "front/commons/error";
        }
        OrdersProduct ordersProduct = serviceResult.getResult();
        if (ordersProduct == null) {
            dataMap.put("info", "获取网单失败，请重试");
            return "front/commons/error";
        }

        //计算可以退换货的数量
        Integer number = ordersProduct.getNumber() - ordersProduct.getBackNumber()
                         - ordersProduct.getExchangeNumber();
        if (number < 0) {
            dataMap.put("info", "网单信息有误，请联系管理人员");
            log.error("网单[" + ordersProduct.getProductName() + "]:退换货数量有误。");
            return "front/commons/error";
        }

        dataMap.put("order", orderResult.getResult());
        dataMap.put("orderProduct", ordersProduct);
        dataMap.put("number", number);

        return "front/member/service/productbackapplyinput";
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

        Member member = WebFrontSession.getLoginedUser(request, response);
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
        Member member = WebFrontSession.getLoginedUser(request, response);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMember(member);
        feignObjectUtil.setMemberProductBack(memberProductBack);

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
     * 跳转到 退货列表页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/back.html", method = { RequestMethod.GET })
    public String toProductBackList(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> dataMap) {

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        Member member = WebFrontSession.getLoginedUser(request, response);
        ServiceResult<List<MemberProductBack>> serviceResult = new ServiceResult<List<MemberProductBack>>();
        serviceResult = memberProductBackService.getMemberProductBackList(pager, member.getId());
        pager = serviceResult.getPager();
        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
            request.getRequestURI() + "");

        dataMap.put("backList", serviceResult.getResult());
        dataMap.put("page", pm);

        return "front/member/service/productbacklist";
    }

    /**
     * 跳转到 退货 查看页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/backdetail.html", method = { RequestMethod.GET })
    public String toProductBackDetail(HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> dataMap,
                                      @RequestParam(value = "backid", required = true) Integer backid,
                                      @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                      @RequestParam(value = "orderId", required = true) Integer orderId) {

        //查询退货信息
        ServiceResult<MemberProductBack> serviceResult = memberProductBackService
            .getMemberProductBackById(backid);

        if (!serviceResult.getSuccess()) {
            dataMap.put("info", "退货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }
        MemberProductBack memberProductBack = serviceResult.getResult();
        if (memberProductBack == null) {
            dataMap.put("info", "获取退货信息失败，请重试");
            return "/front/commons/error";
        }
        Boolean checkResult = this.checkBackOrExchange(request, response, memberProductBack, null,
            orderId, orderProductId, dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }
        //根据网单id 查询网单信息
        ServiceResult<OrdersProduct> result = ordersProductService
            .getOrdersProductWithImgById(orderProductId);

        //查询订单信息
        ServiceResult<Orders> orderResult = ordersService.getOrderWithOPById(orderId);

        if (memberProductBack != null && memberProductBack.getBackCouponUserId() != null
            && memberProductBack.getBackCouponUserId() > 0) {
            ServiceResult<CouponUser> couponUserResult = couponService
                .getCouponUserById(memberProductBack.getBackCouponUserId());
            dataMap.put("couponUser", couponUserResult.getResult());
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
        dataMap.put("orderProduct", result.getResult());
        dataMap.put("info", memberProductBack);
        dataMap.put("order", orderResult.getResult());
        return "front/member/service/productbackdetail";

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
        Member member = WebFrontSession.getLoginedUser(request, response);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMember(member);
        feignObjectUtil.setMemberProductExchange(memberProductExchange);

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
     * 跳转到 换货列表页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exchange.html", method = { RequestMethod.GET })
    public String toProductExchangeList(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> dataMap) {

        Map<String, Object> queryMap = WebUtil.handlerQueryMapNoQ(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        Member member = WebFrontSession.getLoginedUser(request, response);
        FeignUtil feignUtil = FeignUtil.getFeignUtilObject(queryMap, pager);

        ServiceResult<List<MemberProductExchange>> serviceResult = memberProductExchangeService
            .getMemberProductExchangeList(feignUtil, member.getId());
        pager = serviceResult.getPager();

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
            request.getRequestURI() + "");

        dataMap.put("exchangeList", serviceResult.getResult());
        dataMap.put("page", pm);
        return "front/member/service/productexchangelist";
    }

    /**
     * 跳转到 换货 查看页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/exchangedetail.html", method = { RequestMethod.GET })
    public String toProductExchangeDetail(HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> dataMap,
                                          @RequestParam(value = "exchangeid", required = true) Integer exchangeid,
                                          @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                          @RequestParam(value = "orderId", required = true) Integer orderId) {
        //查询换货信息
        ServiceResult<MemberProductExchange> serviceResult = memberProductExchangeService
            .getMemberProductExchangeById(exchangeid);

        if (!serviceResult.getSuccess()) {
            dataMap.put("info", "换货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }
        MemberProductExchange memberProductExchange = serviceResult.getResult();

        if (memberProductExchange == null) {
            dataMap.put("info", "换货信息获取失败，请重试。");
            return "/front/commons/error";
        }
        //校验信息
        Boolean checkResult = this.checkBackOrExchange(request, response, null,
            memberProductExchange, orderId, orderProductId, dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }

        //根据网单id 查询网单信息
        ServiceResult<OrdersProduct> result = ordersProductService
            .getOrdersProductWithImgById(orderProductId);

        //查询订单信息
        ServiceResult<Orders> orderResult = ordersService.getOrderWithOPById(orderId);
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
        dataMap.put("orderProduct", result.getResult());
        dataMap.put("info", memberProductExchange);
        dataMap.put("order", orderResult.getResult());
        dataMap.put("memberProductExchangeLogList", memberProductExchangeLogList);
        return "front/member/service/productExchangedetail";
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

    /**
     * 跳转到 申诉申请录入界面(退货)
     * @param request
     * @param response
     * @param dataMap
     * @param orderProductId
     * @param productBackId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/addbackcomplain.html", method = { RequestMethod.GET })
    public String toBackComplainApply(HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> dataMap,
                                      @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                      @RequestParam(value = "productBackId", required = true) Integer productBackId,
                                      @RequestParam(value = "orderId", required = true) Integer orderId) {

        //查询退货信息
        ServiceResult<MemberProductBack> productBackResult = memberProductBackService
            .getMemberProductBackById(productBackId);

        if (!productBackResult.getSuccess()) {
            dataMap.put("info", "退货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }

        MemberProductBack memberProductBack = productBackResult.getResult();

        if (memberProductBack == null) {
            dataMap.put("info", "退货信息获取失败，请重试。");
            return "/front/commons/error";
        }
        //校验信息
        Boolean checkResult = this.checkBackOrExchange(request, response, memberProductBack, null,
            orderId, orderProductId, dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }

        ServiceResult<OrdersProduct> serviceResult = ordersProductService
            .getOrdersProductWithImgById(orderProductId);

        ServiceResult<Orders> orderResult = new ServiceResult<Orders>();
        //查询订单信息
        orderResult = ordersService.getOrderWithOPById(orderId);

        dataMap.put("order", orderResult.getResult());
        dataMap.put("orderProduct", serviceResult.getResult());
        dataMap.put("productBackId", productBackId);

        dataMap.put("message", request.getParameter("message"));

        return "front/member/service/complainapply";
    }

    /**
     * 跳转到 申诉申请录入界面(换货)
     * @param request
     * @param response
     * @param dataMap
     * @param orderProductId 网单ID
     * @param productBackId  退货申请ID
     * @param productExchangeId 换货申请ID
     * @param orderId 订单ID
     * @return
     */
    @RequestMapping(value = "/addexchangecomplain.html", method = { RequestMethod.GET })
    public String toComplainApply(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> dataMap,
                                  @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                  @RequestParam(value = "productExchangeId", required = true) Integer productExchangeId,
                                  @RequestParam(value = "orderId", required = true) Integer orderId) {

        //查询换货信息
        ServiceResult<MemberProductExchange> productExchangeResult = memberProductExchangeService
            .getMemberProductExchangeById(productExchangeId);

        if (!productExchangeResult.getSuccess()) {
            dataMap.put("info", "换货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }
        MemberProductExchange memberProductExchange = productExchangeResult.getResult();

        if (memberProductExchange == null) {
            dataMap.put("info", "换货信息获取失败，请重试。");
            return "/front/commons/error";
        }
        //校验信息
        Boolean checkResult = this.checkBackOrExchange(request, response, null,
            memberProductExchange, orderId, orderProductId, dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }

        ServiceResult<OrdersProduct> serviceResult = ordersProductService
            .getOrdersProductWithImgById(orderProductId);

        ServiceResult<Orders> orderResult = new ServiceResult<Orders>();
        //查询订单信息
        orderResult = ordersService.getOrderWithOPById(orderId);

        dataMap.put("order", orderResult.getResult());
        dataMap.put("orderProduct", serviceResult.getResult());
        dataMap.put("productExchangeId", productExchangeId);

        dataMap.put("message", request.getParameter("message"));

        return "front/member/service/complainapply";
    }

    /**
     * 申诉提交(退货)
     * @param request
     * @param response
     * @param dataMap
     * @param sellerComplaint
     * @return
     */
    @RequestMapping(value = "/savebackcomplain.html", method = { RequestMethod.POST })
    public String backComplainSubmit(MultipartHttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> dataMap,
                                     SellerComplaint sellerComplaint) {
        Member member = WebFrontSession.getLoginedUser(request, response);
        //查询退货信息
        ServiceResult<MemberProductBack> productBackResult = memberProductBackService
            .getMemberProductBackById(sellerComplaint.getProductBackId());

        if (!productBackResult.getSuccess()) {
            dataMap.put("info", "退货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }

        MemberProductBack memberProductBack = productBackResult.getResult();

        if (memberProductBack == null) {
            dataMap.put("info", "退货信息获取失败，请重试。");
            return "/front/commons/error";
        }
        //校验信息
        Boolean checkResult = this.checkBackOrExchange(request, response, memberProductBack, null,
            sellerComplaint.getOrderId(), sellerComplaint.getOrderProductId(), dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("member", "complain");
            String imageName = UploadUtil.getInstance().uploadFile2ImageServer("pic", request,
                param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
            if (imageName == null || imageName == "") {
                dataMap.put("info", "上传申诉文件失败，请联系管理员。");
                return "/front/commons/error";
            }
            sellerComplaint.setImage(imageName);
            sellerComplaint.setProductExchangeId(0);
            FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
            feignObjectUtil.setMember(member);
            feignObjectUtil.setSellerComplaint(sellerComplaint);
            //保存申诉
            ServiceResult<SellerComplaint> serviceResult = sellerComplaintService
                .saveSellerComplaint(feignObjectUtil);
            if (!serviceResult.getSuccess()) {
                dataMap.put("info", serviceResult.getMessage());
                return "/front/commons/error";
            }
        } catch (Exception e) {
            log.error(
                "[shoppingmall-front-web][MemberServiceController][backComplainSubmit] exception:",
                e);
        }
        return "redirect:/member/complain.html";
    }

    /**
     * 申诉提交(换货)
     * @param request
     * @param response
     * @param dataMap
     * @param sellerComplaint
     * @return
     */
    @RequestMapping(value = "/saveexchangecomplain.html", method = { RequestMethod.POST })
    public String exchangeComplainSubmit(MultipartHttpServletRequest request,
                                         HttpServletResponse response, Map<String, Object> dataMap,
                                         SellerComplaint sellerComplaint) {
        Member member = WebFrontSession.getLoginedUser(request, response);
        //查询换货信息
        ServiceResult<MemberProductExchange> productExchangeResult = memberProductExchangeService
            .getMemberProductExchangeById(sellerComplaint.getProductExchangeId());

        if (!productExchangeResult.getSuccess()) {
            dataMap.put("info", "换货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }
        MemberProductExchange memberProductExchange = productExchangeResult.getResult();
        if (memberProductExchange == null) {
            dataMap.put("info", "换货信息获取失败，请重试。");
            return "/front/commons/error";
        }
        //校验信息
        Boolean checkResult = this.checkBackOrExchange(request, response, null,
            memberProductExchange, sellerComplaint.getOrderId(),
            sellerComplaint.getOrderProductId(), dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("member", "complain");
            String imageName = UploadUtil.getInstance().uploadFile2ImageServer("pic", request,
                param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
            if (imageName == null || imageName == "") {
                dataMap.put("info", "上传申诉文件失败，请联系管理员。");
                return "/front/commons/error";
            }
            sellerComplaint.setImage(imageName);
            sellerComplaint.setProductBackId(0);
            FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
            feignObjectUtil.setMember(member);
            feignObjectUtil.setSellerComplaint(sellerComplaint);
            //保存申诉
            ServiceResult<SellerComplaint> serviceResult = sellerComplaintService
                .saveSellerComplaint(feignObjectUtil);
            if (!serviceResult.getSuccess()) {
                dataMap.put("info", serviceResult.getMessage());
                return "/front/commons/error";
            }
        } catch (Exception e) {
            log.error(
                "[shoppingmall-front-web][MemberServiceController][exchangeComplainSubmit] exception:",
                e);
        }
        return "redirect:/member/complain.html";
    }

    /**
     * 跳转到 申诉申请 查看  查看是针对哪个网单进行的申请，并且查出其退换货信息
     * @param request
     * @param response
     * @param dataMap
     * @param orderProductId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/complaindetail.html", method = { RequestMethod.GET })
    public String toComplainApplyDetail(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> dataMap,
                                        @RequestParam(value = "orderProductId", required = true) Integer orderProductId,
                                        @RequestParam(value = "orderId", required = true) Integer orderId,
                                        @RequestParam(value = "infoId", required = true) Integer infoId) {

        ServiceResult<OrdersProduct> serviceResult = ordersProductService
            .getOrdersProductWithImgById(orderProductId);
        Integer userid = WebFrontSession.getLoginedUser(request, response).getId();
        //查询申诉信息
        ServiceResult<SellerComplaint> scResult = sellerComplaintService
            .getSellerComplaintById(infoId);
        if (!scResult.getSuccess()) {
            dataMap.put("info", "获取投诉信息失败，请联系管理员。");
            return "/front/commons/error";
        }
        SellerComplaint sellerComplaint = scResult.getResult();
        if (sellerComplaint == null) {
            dataMap.put("info", "获取投诉信息失败，请重试。");
            return "/front/commons/error";
        }
        if (!sellerComplaint.getUserId().equals(userid)) {
            dataMap.put("info", "您无权查看他人信息");
            return "/front/commons/error";
        }
        //根据申诉信息取退换货信息
        Integer backId = sellerComplaint.getProductBackId();
        Integer exchangeId = sellerComplaint.getProductExchangeId();
        if (backId != null && backId != 0) {
            MemberProductBack memberProductBack = (memberProductBackService
                .getMemberProductBackById(backId)).getResult();
            dataMap.put("backInfo", memberProductBack);
        } else if (exchangeId != null && exchangeId != 0) {
            MemberProductExchange memberProductExchange = (memberProductExchangeService
                .getMemberProductExchangeById(exchangeId)).getResult();
            dataMap.put("exchangeInfo", memberProductExchange);
        }

        dataMap.put("orderProduct", serviceResult.getResult());
        dataMap.put("info", scResult.getResult());

        return "front/member/service/complainapplydetail";
    }

    /**
     * 跳转到 申诉列表界面 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/complain.html", method = { RequestMethod.GET })
    public String toComplainApply(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> dataMap) {

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        Member member = WebFrontSession.getLoginedUser(request, response);
        ServiceResult<List<FrontSellerComplaintVO>> serviceResult = sellerComplaintService
            .getSellerComplaintList(pager, member.getId());
        pager = serviceResult.getPager();

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
            request.getRequestURI() + "");

        dataMap.put("complaintList", serviceResult.getResult());
        dataMap.put("page", pm);

        return "front/member/service/complainlist";
    }

    /**
     * 跳转到 提现申请列表界面 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/withdraw.html", method = { RequestMethod.GET })
    public String toWithdrawDepositList(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> dataMap) {

        Member member = WebFrontSession.getLoginedUser(request, response);

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);

        ServiceResult<List<MemberApplyDrawing>> serviceResult = new ServiceResult<List<MemberApplyDrawing>>();
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        serviceResult = memberApplyDrawingService.getMemberApplyDrawings(feignUtil);
        pager = serviceResult.getPager();
        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
            request.getRequestURI() + "");

        dataMap.put("infoList", serviceResult.getResult());
        dataMap.put("page", pm);

        return "front/member/service/withdrawdepositlist";
    }

    /**
     * 跳转到 提现申请 查看界面
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/withdrawdetail.html", method = { RequestMethod.GET })
    public String toWithdrawDepositDetail(HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> dataMap,
                                          @RequestParam(value = "infoId", required = true) Integer infoId) {

        Member member = WebFrontSession.getLoginedUser(request, response);
        ServiceResult<MemberApplyDrawing> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawing(infoId);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", serviceResult.getMessage());
            return "/front/commons/error";
        }
        MemberApplyDrawing memberApplyDrawing = serviceResult.getResult();
        if (memberApplyDrawing == null) {
            dataMap.put("info", "获取信息失败，请重试。");
            return "/front/commons/error";
        }
        if (!memberApplyDrawing.getMemberId().equals(member.getId())) {
            dataMap.put("info", "您无权访问他人数据");
            return "/front/commons/error";
        }

        dataMap.put("info", memberApplyDrawing);

        return "front/member/service/withdrawdepositdetail";
    }

    /**
     * 跳转到 提现申请录入界面 取账户余额 判断是否可以提现
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/withdrawapply.html", method = { RequestMethod.GET })
    public String toWithdrawDepositApply(HttpServletRequest request, HttpServletResponse response,
                                         Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request, response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(member.getId());

        //账户余额 默认为0
        BigDecimal balance = new BigDecimal(0);
        if (serviceResult.getResult() != null) {
            Member memberDb = serviceResult.getResult();
            balance = memberDb.getBalance();

        }
        dataMap.put("balance", balance);

        return "front/member/service/withdrawdepositapply";
    }

    /**
     * 跳转到 提现申请提交 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dowithdrawapply.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Integer> withdrawDepositSubmit(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       MemberApplyDrawing memberApplyDrawing) {

        Member member = WebFrontSession.getLoginedUser(request, response);
        memberApplyDrawing.setMemberId(member.getId());
        memberApplyDrawing.setMemberName(member.getName());
        //设置提现编号
        memberApplyDrawing.setCode(RandomUtil.getOrderSn());
        memberApplyDrawing.setState(ConstantsEJS.MEMBER_DRAWING_STATE_1);
        ServiceResult<Integer> serviceResult = memberApplyDrawingService
            .saveMemberApplyDrawing(memberApplyDrawing);
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Integer>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    public Boolean checkBackOrExchange(HttpServletRequest request, HttpServletResponse response,
                                       MemberProductBack memberProductBack,
                                       MemberProductExchange memberProductExchange, Integer orderId,
                                       Integer orderProductId, Map<String, Object> dataMap) {
        Integer userid = WebFrontSession.getLoginedUser(request, response).getId();
        if (memberProductBack != null) {//退货校验
            //校验退货信息是否正确
            if (!memberProductBack.getOrderProductId().equals(orderProductId)
                || !memberProductBack.getOrderId().equals(orderId)) {
                dataMap.put("info", "信息获取错误，请重试。");
                return false;
            } else if (!memberProductBack.getMemberId().equals(userid)) {
                dataMap.put("info", "您无权查看他人信息。");
                return false;
            }
        } else {//换货校验
            if (!memberProductExchange.getOrderProductId().equals(orderProductId)
                || !memberProductExchange.getOrderId().equals(orderId)) {
                dataMap.put("info", "换货信息获取失败，请重试");
                return false;
            }
            if (!memberProductExchange.getMemberId().equals(userid)) {
                dataMap.put("info", "您无权查看他人信息");
                return false;
            }
        }
        return true;
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
        ServiceResult<MemberProductBack> serviceResult = memberProductBackService
            .getMemberProductBackById(backid);

        if (!serviceResult.getSuccess()) {
            dataMap.put("info", "退货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }
        MemberProductBack memberProductBack = serviceResult.getResult();
        if (memberProductBack == null) {
            dataMap.put("info", "获取退货信息失败，请重试");
            return "/front/commons/error";
        }
        Boolean checkResult = this.checkBackOrExchange(request, response, memberProductBack, null,
            memberProductBack.getOrderId(), memberProductBack.getOrderProductId(), dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }
        //根据网单id 查询网单信息
        ServiceResult<OrdersProduct> result = ordersProductService
            .getOrdersProductWithImgById(memberProductBack.getOrderProductId());

        List<CourierCompany> courierCompanyList = courierCompanyService.list();

        dataMap.put("orderProduct", result.getResult());
        dataMap.put("memberProductBack", memberProductBack);
        dataMap.put("courierCompanyList", courierCompanyList);
        return "front/member/service/backdelivergoods";
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
        Member member = WebFrontSession.getLoginedUser(request, response);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMember(member);
        feignObjectUtil.setMemberProductBack(memberProductBack);

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
        ServiceResult<MemberProductExchange> serviceResult = memberProductExchangeService
            .getMemberProductExchangeById(exchangeid);
        if (!serviceResult.getSuccess()) {
            dataMap.put("info", "退货信息获取失败，请联系管理员。");
            return "/front/commons/error";
        }
        MemberProductExchange memberProductExchange = serviceResult.getResult();
        if (memberProductExchange == null) {
            dataMap.put("info", "获取换货信息失败，请重试");
            return "/front/commons/error";
        }
        Boolean checkResult = this.checkBackOrExchange(request, response, null,
            memberProductExchange, memberProductExchange.getOrderId(),
            memberProductExchange.getOrderProductId(), dataMap);
        if (!checkResult) {
            return "/front/commons/error";
        }
        //根据网单id 查询网单信息
        ServiceResult<OrdersProduct> result = ordersProductService
            .getOrdersProductWithImgById(memberProductExchange.getOrderProductId());

        List<CourierCompany> courierCompanyList = courierCompanyService.list();

        dataMap.put("orderProduct", result.getResult());
        dataMap.put("memberProductExchange", memberProductExchange);
        dataMap.put("courierCompanyList", courierCompanyList);
        return "front/member/service/exchangedelivergoods";
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
        Member member = WebFrontSession.getLoginedUser(request, response);

        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setMember(member);
        feignObjectUtil.setMemberProductExchange(memberProductExchange);
        ServiceResult<Boolean> serviceResult = memberProductExchangeService
            .doExchangeDeliverGoods(feignObjectUtil);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return json;
    }

}
