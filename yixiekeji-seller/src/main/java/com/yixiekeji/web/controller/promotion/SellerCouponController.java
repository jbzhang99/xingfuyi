package com.yixiekeji.web.controller.promotion;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.excel.CellConfig;
import com.yixiekeji.core.excel.ExcelConfig;
import com.yixiekeji.core.excel.ExcelManager;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 优惠券管理controller
 *                       
 * @Filename: SellerCouponController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/promotion/coupon")
public class SellerCouponController extends BaseController {

    @Resource
    private ICouponService couponService;

    /**
     * 优惠券列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/promotion/coupon/couponlist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Coupon>> list(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Coupon>> serviceResult = couponService.getCoupons(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<Coupon> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<Coupon>> jsonResult = new HttpJsonResult<List<Coupon>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "seller/promotion/coupon/couponadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(Coupon coupon, HttpServletRequest request, Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        coupon.setCreateUserId(userId);
        coupon.setCreateUserName(sellerUser.getName());
        coupon.setUpdateUserId(sellerUser.getId());
        coupon.setUpdateUserName(sellerUser.getName());

        coupon.setStatus(Coupon.STATUS_1);
        coupon.setSellerId(sellerUser.getSellerId());

        // 已发放数量默认0
        coupon.setReceivedNum(0);
        // 前缀修改为大写
        coupon.setPrefix(coupon.getPrefix().toUpperCase());

        ServiceResult<Boolean> serviceResult = couponService.saveCoupon(coupon);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("coupon", coupon);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/promotion/coupon/couponadd";
            }
        }
        return "redirect:/seller/promotion/coupon";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, int couponId, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<Coupon> serviceResult = couponService.getCouponById(couponId);

        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }
        Coupon coupon = serviceResult.getResult();
        if (coupon == null) {
            return "seller/404";
        }
        if (!coupon.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("coupon", coupon);
        return "seller/promotion/coupon/couponedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(Coupon coupon, HttpServletRequest request, Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        coupon.setUpdateUserId(sellerUser.getId());
        coupon.setUpdateUserName(sellerUser.getName());

        coupon.setSellerId(sellerUser.getSellerId());

        // 前缀修改为大写
        coupon.setPrefix(coupon.getPrefix().toUpperCase());

        ServiceResult<Boolean> serviceResult = couponService.updateCoupon(coupon);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("coupon", coupon);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/promotion/coupon/couponedit";
            }
        }
        return "redirect:/seller/promotion/coupon";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<Coupon> couponResult = couponService.getCouponById(id);
        if (!couponResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(couponResult.getMessage());
        }
        if (couponResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("优惠券信息获取失败。");
        }

        Coupon coupon = couponResult.getResult();
        if (sellerUser.getSellerId().intValue() != coupon.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能删除自己店铺的优惠券。");
        }

        if (coupon.getStatus().intValue() != Coupon.STATUS_1
            && coupon.getStatus().intValue() != Coupon.STATUS_4) {
            return new HttpJsonResult<Boolean>("只能删除新建或审核失败的优惠券。");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = couponService.deleteCoupon(id, sellerUser.getId(),
            sellerUser.getName());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "audit", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> audit(HttpServletRequest request,
                                                       @RequestParam("id") Integer id) {

        ServiceResult<Coupon> couponRlt = couponService.getCouponById(id);

        if (!couponRlt.getSuccess()) {
            return new HttpJsonResult<Boolean>(couponRlt.getMessage());
        }
        if (couponRlt.getResult() == null) {
            return new HttpJsonResult<Boolean>("优惠券信息获取失败。");
        }
        Coupon couponDb = couponRlt.getResult();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != couponDb.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能操作自己店铺的优惠券。");
        }
        if (couponDb.getStatus().intValue() != Coupon.STATUS_1
            && couponDb.getStatus().intValue() != Coupon.STATUS_4) {
            return new HttpJsonResult<Boolean>("非新建或审核失败的优惠券不能提交审核。");
        }

        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setStatus(Coupon.STATUS_2);
        coupon.setUpdateUserId(sellerUser.getId());
        coupon.setUpdateUserName(sellerUser.getName());
        coupon.setSellerId(sellerUser.getSellerId());
        ServiceResult<Boolean> serviceResult = couponService.updateCouponStatus(coupon);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> up(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {

        ServiceResult<Coupon> couponRlt = couponService.getCouponById(id);

        if (!couponRlt.getSuccess()) {
            return new HttpJsonResult<Boolean>(couponRlt.getMessage());
        }
        if (couponRlt.getResult() == null) {
            return new HttpJsonResult<Boolean>("优惠券信息获取失败。");
        }
        Coupon couponDb = couponRlt.getResult();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != couponDb.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能操作自己店铺的优惠券。");
        }
        if (couponDb.getStatus().intValue() != Coupon.STATUS_3) {
            return new HttpJsonResult<Boolean>("非审核通过状态的优惠券不能上架。");
        }

        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setStatus(Coupon.STATUS_5);
        coupon.setUpdateUserId(sellerUser.getId());
        coupon.setUpdateUserName(sellerUser.getName());
        coupon.setSellerId(sellerUser.getSellerId());
        ServiceResult<Boolean> serviceResult = couponService.updateCouponStatus(coupon);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> down(HttpServletRequest request,
                                                      @RequestParam("id") Integer id) {

        ServiceResult<Coupon> couponRlt = couponService.getCouponById(id);

        if (!couponRlt.getSuccess()) {
            return new HttpJsonResult<Boolean>(couponRlt.getMessage());
        }
        if (couponRlt.getResult() == null) {
            return new HttpJsonResult<Boolean>("优惠券信息获取失败。");
        }
        Coupon couponDb = couponRlt.getResult();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != couponDb.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能操作自己店铺的优惠券。");
        }
        if (couponDb.getStatus().intValue() != Coupon.STATUS_5) {
            return new HttpJsonResult<Boolean>("非上架状态的优惠券不能执行下架操作。");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setStatus(Coupon.STATUS_6);
        coupon.setUpdateUserId(sellerUser.getId());
        coupon.setUpdateUserName(sellerUser.getName());
        coupon.setSellerId(sellerUser.getSellerId());
        ServiceResult<Boolean> serviceResult = couponService.updateCouponStatus(coupon);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "export", method = { RequestMethod.GET })
    public String exportExcel(HttpServletRequest request, int couponId,
                              Map<String, Object> dataMap) {

        ServiceResult<Coupon> serviceResult = couponService.getCouponById(couponId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/promotion/coupon/couponlist";
            }
        }
        Coupon coupon = serviceResult.getResult();

        dataMap.put("coupon", coupon);
        return "seller/promotion/coupon/couponexport";
    }

    @RequestMapping(value = "doexport", method = { RequestMethod.GET })
    public void doExportExcel(HttpServletRequest request, HttpServletResponse response,
                              @RequestHeader(value = "user-agent") String userAgent,
                              @RequestParam("id") Integer id,
                              @RequestParam("exportNum") Integer exportNum) {
        ServiceResult<Coupon> serviceResult = couponService.getCouponById(id);

        if (!serviceResult.getSuccess()) {
            throw new RuntimeException(serviceResult.getMessage());
        }
        Coupon coupon = serviceResult.getResult();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<List<CouponUser>> exportResult = couponService.exportCoupon(id, exportNum,
            sellerUser.getSellerId(), sellerUser);

        if (!exportResult.getSuccess() || null == exportResult.getResult()) {
            try {
                Cookie msgCookie = new Cookie("busiErrMsg",
                    URLEncoder.encode(exportResult.getMessage(), "utf-8"));
                msgCookie.setPath("/");
                response.addCookie(msgCookie);
                Cookie fileDownloadCookie = new Cookie("fileDownload", "false");
                fileDownloadCookie.setPath("/");
                response.addCookie(fileDownloadCookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            List<CouponUser> list = exportResult.getResult();
            this.export(response, userAgent, list, coupon);
        }
    }

    /**
     * 导出excel数据
     * @param response
     * @param userAgent
     * @param result
     * @return
     */
    private void export(HttpServletResponse response, String userAgent,
                        List<CouponUser> couponUserList, Coupon coupon) {
        ExcelConfig<CouponUser> config = new ExcelConfig<CouponUser>();
        config.setData(couponUserList);
        config.setExcelVersion(ExcelConfig.ExcelVersion.EXECL_VERSION_2007);
        config.setFileName(
            coupon.getCouponName() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        config.setLineConfig(getLineConfig());
        // 优惠券发放数量增加
        coupon.setReceivedNum(coupon.getReceivedNum() + couponUserList.size());
        this.setSearchCondition(config, coupon);
        config.setSheetName(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        config.setUserAgent(userAgent);
        ExcelManager.export(response, config);
    }

    /**
     * 设置搜索条件
     * @param config
     * @param queryMap
     */
    private void setSearchCondition(ExcelConfig<CouponUser> config, Coupon coupon) {
        if (coupon == null)
            return;

        config.setSearchConditionKeys(
            new String[] { "优惠券名称", "优惠券金额", "订单最低金额", "发放开始时间", "发放结束时间", "使用开始时间", "使用结束时间",
                           "用户限制领取数量", "总数量", "已发放数量", "优惠券类型", "使用渠道" });
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String type = "";
        if (coupon.getType().intValue() == 1) {
            type = "在线发放";
        } else if (coupon.getType().intValue() == 2) {
            type = "线下发放";
        }
        String channel = "";
        if (coupon.getChannel().intValue() == 1) {
            channel = "通用";
        } else if (coupon.getChannel().intValue() == 2) {
            channel = "PC";
        } else if (coupon.getChannel().intValue() == 3) {
            channel = "mobile";
        }
        config.setSearchConditionValues(
            new String[] { coupon.getCouponName(), coupon.getCouponValue().toString(),
                           coupon.getMinAmount().toString(),
                           format.format(coupon.getSendStartTime()),
                           format.format(coupon.getSendEndTime()),
                           format.format(coupon.getUseStartTime()),
                           format.format(coupon.getUseEndTime()),
                           coupon.getPersonLimitNum().toString(),
                           coupon.getTotalLimitNum().toString(), coupon.getReceivedNum().toString(),
                           type, channel });

    }

    /**
     * 取得行配置
     * @return
     */
    private LinkedHashMap<String, CellConfig> getLineConfig() {
        LinkedHashMap<String, CellConfig> config = new LinkedHashMap<String, CellConfig>();
        CellConfig couponSnConfig = new CellConfig("序列号");
        config.put("couponSn", couponSnConfig);

        CellConfig passwordConfig = new CellConfig("密码");
        config.put("password", passwordConfig);

        CellConfig canUseConfig = new CellConfig("可使用次数");
        config.put("canUse", canUseConfig);

        return config;
    }
}
