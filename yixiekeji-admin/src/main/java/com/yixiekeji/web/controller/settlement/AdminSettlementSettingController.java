package com.yixiekeji.web.controller.settlement;

import com.ning.http.util.DateUtil;
import com.yixiekeji.core.*;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.settlement.Settlement;
import com.yixiekeji.entity.settlement.SettlementOp;
import com.yixiekeji.entity.settlement.SettlementSetting;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.settlement.ISettlementOpService;
import com.yixiekeji.service.settlement.ISettlementService;
import com.yixiekeji.service.settlement.ISettlementSettingService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * 结算设置管理controller
 *
 * @Filename: AdminSettlementSettingController.java
 * @Version: 1.0
 * @Author: 李洪林
 * @Email: leehonglim@163.com
 *
 */
@Controller
@RequestMapping(value = "admin/settlementsetting", produces = "application/json;charset=UTF-8")
public class AdminSettlementSettingController extends BaseController {

    @Resource
    private ISettlementSettingService settlementSettingService;



    /**
     * 初始化controller接口
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/settlementsetting/settlementsettinglist";
    }

    /**
     * 管理页面查询按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SettlementSetting>> list(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SettlementSetting>> serviceResult = settlementSettingService.getList(feignUtil);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<SettlementSetting>> jsonResult = new HttpJsonResult<List<SettlementSetting>>();
        jsonResult.setRows(serviceResult.getResult());
        return jsonResult;
    }


    /**
     * 保存、编辑
     * @param request
     * @param response
     * @param settlementSetting
     */
    @RequestMapping(value = "save", method = { RequestMethod.POST })
    public void save(HttpServletRequest request, HttpServletResponse response, SettlementSetting settlementSetting) {
        ServiceResult<Integer> serviceResult = null;

        Integer currentId = WebAdminSession.getAdminUser(request).getId();
        String ip = IPUtils.getRemoteIp(request);

        if (settlementSetting.getId() != null && settlementSetting.getId() != 0) {
            //编辑
            settlementSetting.setIp(ip);
            settlementSetting.setEditor(currentId);
            settlementSetting.setEditordatetime(new Date());

            String startDateTime = DateUtil.formatDate(settlementSetting.getStartDate(), "yyyy-MM-dd") + " 00:00:00";
            String endDateTime = DateUtil.formatDate(settlementSetting.getEndDate(), "yyyy-MM-dd") + " 23:59:59";
            try {
                settlementSetting.setStartDate(DateUtil.parseDate(startDateTime));
                settlementSetting.setEndDate(DateUtil.parseDate(endDateTime));
            }catch (Exception e){e.printStackTrace();};
            serviceResult = settlementSettingService.update(settlementSetting);
        } else {
            //新增
            settlementSetting.setIp(ip);
            settlementSetting.setStatus(SettlementSetting.STATUS_0);
            settlementSetting.setDelStatus(SettlementSetting.DEL_STATUS_0);
            settlementSetting.setCreatedatetime(new Date());
            settlementSetting.setOperator(currentId);
            String startDateTime = DateUtil.formatDate(settlementSetting.getStartDate(), "yyyy-MM-dd") + " 00:00:00";
            String endDateTime = DateUtil.formatDate(settlementSetting.getEndDate(), "yyyy-MM-dd") + " 23:59:59";
            try {
                settlementSetting.setStartDate(DateUtil.parseDate(startDateTime, Arrays.asList("yyyy-MM-dd hh:mm:ss")));
                settlementSetting.setEndDate(DateUtil.parseDate(endDateTime, Arrays.asList("yyyy-MM-dd hh:mm:ss")));
            }catch (Exception e){e.printStackTrace();};
            serviceResult = settlementSettingService.save(settlementSetting);
        }

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter pw = response.getWriter();
            pw.print(serviceResult.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id) {
        SettlementSetting bean = new SettlementSetting();
        bean.setId(id);
        bean.setIp(IPUtils.getRemoteIp(request));
        bean.setEditor(WebAdminSession.getAdminUser(request).getId());
        bean.setEditordatetime(new Date());
        bean.setDelStatus(SettlementSetting.DEL_STATUS_1);

        ServiceResult<Integer> serviceResult = settlementSettingService.delete(bean);

        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter pw = response.getWriter();
            pw.print(serviceResult.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
