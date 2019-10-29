package com.yixiekeji.web.controller.sale;

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
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.sale.SaleApplyMoney;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.sale.ISaleApplyMoneyService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

@Controller
@RequestMapping(value = "admin/sale/saleapplymoney1", produces = "application/json;charset=UTF-8")
public class AdminSaleApplyMoneyController extends BaseController {

    @Resource
    private ISaleApplyMoneyService saleApplyMoneyService;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/sale/saleapplymoney/saleapplymoney";
    }

    /**
     * 用户列表列表
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SaleApplyMoney>> list(HttpServletRequest request,
                                                                   Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SaleApplyMoney>> serviceResult = saleApplyMoneyService
            .getSaleApplyMoneys(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<SaleApplyMoney>> jsonResult = new HttpJsonResult<List<SaleApplyMoney>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 打款
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "updatemoneystate", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> updatemoneystate(Integer awardId,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Integer id) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        HttpJsonResult<Boolean> jsonResult = null;

        ServiceResult<SaleApplyMoney> resultSaleApplyMoneyOld = saleApplyMoneyService
            .getSaleApplyMoneyById(id);
        if (!resultSaleApplyMoneyOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
        }

        SaleApplyMoney saleApplyMoney = resultSaleApplyMoneyOld.getResult();

        if (ConstantsEJS.YES_NO_0 != saleApplyMoney.getState().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("不是未打款状态，不能进行重复操作！");
            return jsonResult;
        }

        saleApplyMoney.setUpdateId(adminUser.getId());
        saleApplyMoney.setUpdateName(adminUser.getName());
        saleApplyMoney.setState(ConstantsEJS.YES_NO_1);

        ServiceResult<Integer> resultSaleApplyMoney = saleApplyMoneyService
            .updateSaleApplyMoney(saleApplyMoney);

        if (!resultSaleApplyMoney.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(resultSaleApplyMoney.getMessage());
            return jsonResult;
        } else {
            jsonResult = new HttpJsonResult<Boolean>();
        }

        return jsonResult;
    }

}
