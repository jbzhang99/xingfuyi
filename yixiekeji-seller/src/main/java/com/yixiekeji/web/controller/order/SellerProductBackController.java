package com.yixiekeji.web.controller.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.member.MemberProductBackLog;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.member.IMemberProductBackLogService;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 用户退货商家管理controller
 *
 * @Filename: SellerProductBackController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/order/productBack")
public class SellerProductBackController extends BaseController {

    @Resource
    private IMemberProductBackService    memberProductBackService;
    @Resource
    private ICouponService               couponService;
    @Resource
    private IRegionsService              regionsService;
    @Resource
    private ICourierCompanyService       courierCompanyService;
    @Resource
    private IMemberProductBackLogService memberProductBackLogService;

    private static Logger                log = LoggerFactory
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
        return "seller/order/productback/list";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberProductBack>> list(HttpServletRequest request,
                                                                      Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        queryMap.put("sellerId", WebSellerSession.getSellerUser(request).getSellerId().toString());
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MemberProductBack>> serviceResult = memberProductBackService
            .page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<MemberProductBack>> jsonResult = new HttpJsonResult<List<MemberProductBack>>();
        jsonResult.setRows((List<MemberProductBack>) serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 进入处理退货申请页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, Map<String, Object> dataMap, Integer id) {
        SellerUser user = WebSellerSession.getSellerUser(request);
        MemberProductBack memberProductBack = this.memberProductBackService
            .getMemberProductBackById(id).getResult();
        if (memberProductBack == null) {
            return "seller/404";
        }
        if (!memberProductBack.getSellerId().equals(user.getSellerId())) {
            return "/seller/unauth";
        }
        dataMap.put("obj", memberProductBack);

        if (memberProductBack != null && memberProductBack.getBackCouponUserId() != null
            && memberProductBack.getBackCouponUserId() > 0) {
            ServiceResult<CouponUser> couponResult = couponService
                .getCouponUserById(memberProductBack.getBackCouponUserId());
            dataMap.put("couponUser", couponResult.getResult());
        }
        // 地址信息
        ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
        dataMap.put("provinceList", provinceResult.getResult());

        ServiceResult<List<Regions>> cityListResult = regionsService
            .getRegionsByParentId(ConvertUtil.toInt(memberProductBack.getProvinceId(), -1));
        dataMap.put("cityList", cityListResult.getResult());
        ServiceResult<List<Regions>> areaListResult = regionsService
            .getRegionsByParentId(ConvertUtil.toInt(memberProductBack.getCityId(), -1));
        dataMap.put("areaList", areaListResult.getResult());

        // 快递公司信息
        List<CourierCompany> courierCompanyList = courierCompanyService.list();
        dataMap.put("courierCompanyList", courierCompanyList);
        // 物流信息
        ServiceResult<List<MemberProductBackLog>> memberProductBackLogListResult = memberProductBackLogService
            .getMemberProductBackLogByMemberProductBackId(id);
        List<MemberProductBackLog> memberProductBackLogList = memberProductBackLogListResult
            .getResult();
        if (null != memberProductBackLogList && memberProductBackLogList.size() > 0) {
            CourierCompany courierCompany = null;
            if (memberProductBack.getLogisticsId() != null) {
                courierCompany = courierCompanyService
                    .getCourierCompanyById(memberProductBack.getLogisticsId()).getResult();
            }
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
        return "seller/order/productback/edit";
    }

    /**
     * 处理退货申请页面
     * @param request
     * @param response
     * @param id
     * @param type
     */
    @RequestMapping(value = "audit", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> audit(HttpServletRequest request,
                                                       HttpServletResponse response, Integer type,
                                                       MemberProductBack memberProductBack) {
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        SellerUser user = WebSellerSession.getSellerUser(request);
        MemberProductBack memberProductBackDb = this.memberProductBackService
            .getMemberProductBackById(memberProductBack.getId()).getResult();
        if (memberProductBackDb == null
            || !memberProductBackDb.getSellerId().equals(user.getSellerId())) {
            return new HttpJsonResult<Boolean>("处理退货操作失败,请重试。");
        }

        memberProductBack.setStateReturn(type);
        memberProductBack.setOptId(user.getId());
        memberProductBack.setOptName(user.getName());
        ServiceResult<Boolean> upResult = memberProductBackService.audit(memberProductBack);
        if (!upResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(upResult.getMessage());
        }
        result.setData(upResult.getResult());
        return result;
    }

    /**
     * 确认收货操作
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "takeDeliver", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> takeDeliver(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             Integer id) {

        SellerUser user = WebSellerSession.getSellerUser(request);
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        ServiceResult<MemberProductBack> serviceResult = memberProductBackService
            .getMemberProductBackById(id);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        MemberProductBack backDB = serviceResult.getResult();
        if (backDB == null || !backDB.getSellerId().equals(user.getSellerId())) {
            return new HttpJsonResult<Boolean>("获取退货信息失败，请重试！");
        }
        if (backDB.getStateReturn().intValue() != MemberProductBack.STATE_RETURN_3) {
            return new HttpJsonResult<Boolean>("该退货申请不是可收货状态！");
        }
        MemberProductBack back = new MemberProductBack();
        back.setId(id);
        // 退货状态-已收货
        back.setStateReturn(MemberProductBack.STATE_RETURN_4);

        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setSellerUser(user);
        feignObjectUtil.setMemberProductBack(back);

        ServiceResult<Boolean> upResult = memberProductBackService.takeDeliver(feignObjectUtil);
        if (!upResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(upResult.getMessage());
        }
        result.setData(upResult.getResult());

        return result;
    }

}
