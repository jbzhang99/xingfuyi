package com.yixiekeji.web.controller.order;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.HttpClientUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.JsonUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.entity.member.MemberProductExchangeLog;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.member.IMemberProductExchangeLogService;
import com.yixiekeji.service.member.IMemberProductExchangeService;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 卖家换货controller
 * 
 * @Filename: SellerProductExchangeController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/order/productExchange")
public class SellerProductExchangeController extends BaseController {

    @Resource
    private IMemberProductExchangeService    memberProductExchangeService;
    @Resource
    private IProductService                  productservice;
    @Resource
    private IOrdersService                   ordersService;
    @Resource
    private IRegionsService                  regionsService;
    @Resource
    private ICourierCompanyService           courierCompanyService;
    @Resource
    private IMemberProductExchangeLogService memberProductExchangeLogService;

    private static Logger                    log = LoggerFactory
        .getLogger(SellerProductBackController.class);

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        return "seller/order/productexchange/list";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberProductExchange>> list(HttpServletRequest request,
                                                                          Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        queryMap.put("sellerId", WebSellerSession.getSellerUser(request).getSellerId().toString());
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MemberProductExchange>> serviceResult = memberProductExchangeService
            .getMemberProductExchanges(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<MemberProductExchange>> jsonResult = new HttpJsonResult<List<MemberProductExchange>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 进入处理换货申请页面
     * @param request
     * @param dataMap
     * @param id
     * @return
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, Map<String, Object> dataMap, Integer id) {
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<MemberProductExchange> result = memberProductExchangeService
            .getMemberProductExchangeById(id);
        if (!result.getSuccess()) {
            return "seller/order/productexchange/list";
        }
        MemberProductExchange exchange = result.getResult();
        if (exchange == null) {
            return "seller/404";
        }
        if (!exchange.getSellerId().equals(user.getSellerId())) {
            return "seller/unauth";
        }
        ServiceResult<Product> prdResult = productservice.getProductById(exchange.getProductId());
        if (prdResult.getSuccess() && prdResult.getResult() != null) {
            exchange.setProductName(prdResult.getResult().getName1());
        }

        ServiceResult<Orders> orderResult = ordersService.getOrdersById(exchange.getOrderId());
        if (orderResult.getSuccess() && orderResult.getResult() != null) {
            exchange.setOrderSn(orderResult.getResult().getOrderSn());
        }
        // 地址信息
        ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
        dataMap.put("provinceList", provinceResult.getResult());

        ServiceResult<List<Regions>> cityListResult = regionsService
            .getRegionsByParentId(ConvertUtil.toInt(exchange.getProvinceId2(), -1));
        dataMap.put("cityList", cityListResult.getResult());
        ServiceResult<List<Regions>> areaListResult = regionsService
            .getRegionsByParentId(ConvertUtil.toInt(exchange.getCityId2(), -1));
        dataMap.put("areaList", areaListResult.getResult());

        dataMap.put("obj", exchange);
        // 退货日志信息
        ServiceResult<List<MemberProductExchangeLog>> memberProductExchangeLogListResult = memberProductExchangeLogService
            .getMemberProductExchangeLogByMemberProductExchangeId(id);
        dataMap.put("memberProductExchangeLogList", memberProductExchangeLogListResult.getResult());
        this.getExchangeBack(exchange, dataMap);
        this.getExchangeExchange(exchange, dataMap);
        // 物流公司
        List<CourierCompany> courierCompanyList = courierCompanyService.list();
        dataMap.put("courierCompanyList", courierCompanyList);
        return "seller/order/productexchange/edit";
    }

    /**
     * 处理换货申请
     * @param request
     * @param response
     * @param id
     * @param type
     * @param remark
     * @return
     */
    @RequestMapping(value = "audit", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> audit(HttpServletRequest request,
                                                       HttpServletResponse response, Integer id,
                                                       Integer type, String remark,
                                                       MemberProductExchange memberProductExchange) {
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<MemberProductExchange> serviceResult = memberProductExchangeService
            .getMemberProductExchangeById(id);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        MemberProductExchange exchange = serviceResult.getResult();
        if (exchange == null || !exchange.getSellerId().equals(user.getSellerId())) {
            return new HttpJsonResult<Boolean>("修改失败，请重试");
        }
        memberProductExchange.setState(type);
        if (type.intValue() != MemberProductExchange.STATE_2) {
            memberProductExchange.setId(id);
            memberProductExchange.setRemark(remark);
        }
        String logisticsId = request.getParameter("logisticsId");
        if (!StringUtil.isEmpty(logisticsId, true)) {
            memberProductExchange.setLogisticsId(ConvertUtil.toInt(logisticsId, 0));
            String logisticsNumber = request.getParameter("logisticsNumber");
            memberProductExchange.setLogisticsNumber(logisticsNumber);
        }

        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setSellerUser(user);
        feignObjectUtil.setMemberProductExchange(memberProductExchange);

        ServiceResult<Boolean> auditResult = memberProductExchangeService.audit(feignObjectUtil);
        if (!auditResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(auditResult.getMessage());
        }
        return result;
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
