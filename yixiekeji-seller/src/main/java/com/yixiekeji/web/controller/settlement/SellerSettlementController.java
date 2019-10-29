package com.yixiekeji.web.controller.settlement;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
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
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.settlement.Settlement;
import com.yixiekeji.entity.settlement.SettlementOp;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.settlement.ISettlementOpService;
import com.yixiekeji.service.settlement.ISettlementService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 结算管理controller
 *
 * @Filename: SellerSettlementController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/settlement")
public class SellerSettlementController extends BaseController {

    @Resource
    private ISettlementService        settlementService;

    @Resource
    private ISettlementOpService      settlementOpService;

    @Resource
    private IOrdersService            ordersService;

    @Resource
    private IMemberProductBackService memberProductBackService;

    /**
     * 初始化controller接口
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/settlement/settlementlist";
    }

    /**
     * 管理页面查询按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Settlement>> list(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Settlement>> serviceResult = settlementService.getSettlements(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<Settlement>> jsonResult = new HttpJsonResult<List<Settlement>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 详情页
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    public String detail(HttpServletRequest request, Map<String, Object> dataMap, Integer id) {

        ServiceResult<Settlement> settlementResult = settlementService.getSettlementById(id);
        if (!settlementResult.getSuccess()) {
            dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
            dataMap.put("message", settlementResult.getMessage());
            return "seller/settlement/settlementlist";
        }
        if (settlementResult.getResult() == null) {
            dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
            dataMap.put("message", "结算账单获取失败！");
            return "seller/settlement/settlementlist";
        }

        dataMap.put("settlement", settlementResult.getResult());

        return "seller/settlement/settlementdetail";
    }

    @RequestMapping(value = "orderlist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Orders>> orderlist(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> dataMap,
                                                                Integer settlementId) {

        ServiceResult<Settlement> settlementRlt = settlementService.getSettlementById(settlementId);
        if (!settlementRlt.getSuccess()) {
            return new HttpJsonResult<List<Orders>>();
        }

        Settlement settlement = settlementRlt.getResult();

        String settleCycle = settlement.getSettleCycle();
        // 周期开始时间，1号0时0分0秒
        String startTime = this.getStartTime(settleCycle);
        // 周期结束时间，周期月最后一个天23时59分59秒
        // 周期最后一天
        String endTime = this.getEndTime(settleCycle);

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_sellerId", settlement.getSellerId() + "");
        queryMap.put("q_orderState", Orders.ORDER_STATE_5 + "");
        queryMap.put("q_finishStartTime", startTime);
        queryMap.put("q_finishEndTime", endTime);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Orders>> serviceResult = ordersService.getSonOrders(feignUtil);
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

    @RequestMapping(value = "getSettlementOp", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SettlementOp>> getSettlementOp(HttpServletRequest request,
                                                                            HttpServletResponse response,
                                                                            Integer orderId) {
        ServiceResult<List<SettlementOp>> res = settlementOpService.getSettlementOpByOId(orderId);
        HttpJsonResult<List<SettlementOp>> json = new HttpJsonResult<List<SettlementOp>>();
        json.setRows(res.getResult());
        json.setTotal(res.getResult().size());
        return json;
    }

    @RequestMapping(value = "backlist", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberProductBack>> backlist(HttpServletRequest request,
                                                                          Map<String, Object> dataMap,
                                                                          Integer settlementId) {

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);

        ServiceResult<Settlement> settlementRlt = settlementService.getSettlementById(settlementId);
        if (!settlementRlt.getSuccess()) {
            return new HttpJsonResult<List<MemberProductBack>>();
        }

        Settlement settlement = settlementRlt.getResult();

        String settleCycle = settlement.getSettleCycle();
        // 周期开始时间，1号0时0分0秒
        String startTime = this.getStartTime(settleCycle);
        // 周期结束时间，周期月最后一个天23时59分59秒
        // 周期最后一天
        String endTime = this.getEndTime(settleCycle);

        ServiceResult<List<MemberProductBack>> serviceResult = memberProductBackService
            .getSettleBacks(settlement.getSellerId(), startTime, endTime, pager);
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

    private String getStartTime(String settleCycle) {
        // 结算年
        String year = settleCycle.substring(0, 4);
        // 结算月
        String month = settleCycle.substring(4);
        // 周期开始时间，1号0时0分0秒
        String startTime = year + "-" + month + "-01 00:00:00";
        return startTime;
    }

    private String getEndTime(String settleCycle) {
        // 结算年
        String year = settleCycle.substring(0, 4);
        // 结算月
        String month = settleCycle.substring(4);
        // 周期结束时间，周期月最后一个天23时59分59秒
        // 周期最后一天
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, ConvertUtil.toInt(year, 1000));
        cal.set(Calendar.MONDAY, (ConvertUtil.toInt(month, 0) - 1));
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String endTime = year + "-" + month + "-" + lastDay + " 23:59:59";
        return endTime;
    }

    /**
     * 商家对账单质疑
     * @param settlement
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "checkDoubt", method = { RequestMethod.POST })
    public String checkDoubt(Settlement settlement, HttpServletRequest request,
                             Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        if (StringUtil.isEmpty(settlement.getSellerDoubt(), true)) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "请填写质疑理由！");
            return "seller/settlement/settlementdetail";
        }

        ServiceResult<Settlement> settlementRlt = settlementService
            .getSettlementById(settlement.getId());

        if (!settlementRlt.getSuccess()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", settlementRlt.getMessage());
            return "seller/settlement/settlementdetail";
        }
        if (settlementRlt.getResult() == null) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "结算信息获取失败，请重试！");
            return "seller/settlement/settlementdetail";
        }
        Settlement settlementDb = settlementRlt.getResult();
        // 只能对商家审核通过或者已经质疑过的账单提交质疑
        if (!settlementDb.getStatus().equals(Settlement.STATUS_2)
            && !settlementDb.getStatus().equals(Settlement.STATUS_4)) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "结算账单的状态不对，请核对结算状态！");
            return "seller/settlement/settlementdetail";
        }

        Settlement settlementNew = new Settlement();
        settlementNew.setId(settlement.getId());
        settlementNew.setStatus(Settlement.STATUS_4);
        settlementNew.setSellerDoubt(settlement.getSellerDoubt());
        settlementNew.setUpdateUserId(sellerUser.getId());
        settlementNew.setUpdateUserName(sellerUser.getName());
        settlementNew.setUpdateTime(new Date());

        ServiceResult<Boolean> updateSettlement = settlementService.updateSettlement(settlementNew);

        if (!updateSettlement.getSuccess()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", updateSettlement.getMessage());
            return "seller/settlement/settlementdetail";
        }
        if (updateSettlement.getResult() == null || !updateSettlement.getResult()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "操作失败，请重试！");
            return "seller/settlement/settlementdetail";
        }
        return "redirect:/seller/settlement";
    }

    /**
     * 商家核对通过账单
     * @param settlement
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "checkOver", method = { RequestMethod.POST })
    public String checkOver(Settlement settlement, HttpServletRequest request,
                            Map<String, Object> dataMap) {

        ServiceResult<Settlement> settlementRlt = settlementService
            .getSettlementById(settlement.getId());

        if (!settlementRlt.getSuccess()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", settlementRlt.getMessage());
            return "seller/settlement/settlementdetail";
        }
        if (settlementRlt.getResult() == null) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "结算信息获取失败，请重试！");
            return "seller/settlement/settlementdetail";
        }
        Settlement settlementDb = settlementRlt.getResult();
        // 只能对商家审核通过或者已经质疑过的账单审核通过
        if (!settlementDb.getStatus().equals(Settlement.STATUS_2)
            && !settlementDb.getStatus().equals(Settlement.STATUS_4)) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "结算账单的状态不对，请核对结算状态！");
            return "seller/settlement/settlementdetail";
        }

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Settlement settlementNew = new Settlement();
        settlementNew.setId(settlement.getId());
        settlementNew.setStatus(Settlement.STATUS_3);
        settlementNew.setUpdateUserId(sellerUser.getId());
        settlementNew.setUpdateUserName(sellerUser.getName());
        settlementNew.setUpdateTime(new Date());

        ServiceResult<Boolean> updateSettlement = settlementService.updateSettlement(settlementNew);

        if (!updateSettlement.getSuccess()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", updateSettlement.getMessage());
            return "seller/settlement/settlementdetail";
        }
        if (updateSettlement.getResult() == null || !updateSettlement.getResult()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "操作失败，请重试！");
            return "seller/settlement/settlementdetail";
        }
        return "redirect:/seller/settlement";
    }

    /**
     * 商家收到支付款后修改状态
     * @param settlement
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "payOver", method = { RequestMethod.POST })
    public String payOver(Settlement settlement, HttpServletRequest request,
                          Map<String, Object> dataMap) {

        ServiceResult<Settlement> settlementRlt = settlementService
            .getSettlementById(settlement.getId());

        if (!settlementRlt.getSuccess()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", settlementRlt.getMessage());
            return "seller/settlement/settlementdetail";
        }
        if (settlementRlt.getResult() == null) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "结算信息获取失败，请重试！");
            return "seller/settlement/settlementdetail";
        }
        Settlement settlementDb = settlementRlt.getResult();
        // 只能对对账完成的账单进行支付完成
        if (!settlementDb.getStatus().equals(Settlement.STATUS_5)) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "只能对对账完成的账单进行支付完成操作，谢谢！");
            return "seller/settlement/settlementdetail";
        }

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Settlement settlementNew = new Settlement();
        settlementNew.setId(settlement.getId());
        settlementNew.setStatus(Settlement.STATUS_6);
        settlementNew.setUpdateUserId(sellerUser.getId());
        settlementNew.setUpdateUserName(sellerUser.getName());
        settlementNew.setUpdateTime(new Date());

        ServiceResult<Boolean> updateSettlement = settlementService.updateSettlement(settlementNew);

        if (!updateSettlement.getSuccess()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", updateSettlement.getMessage());
            return "seller/settlement/settlementdetail";
        }
        if (updateSettlement.getResult() == null || !updateSettlement.getResult()) {
            dataMap.put("settlement", settlement);
            dataMap.put("message", "操作失败，请重试！");
            return "seller/settlement/settlementdetail";
        }
        return "redirect:/seller/settlement";
    }
}
