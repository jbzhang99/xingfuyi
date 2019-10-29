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
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.sale.ISaleMemberService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

@Controller
@RequestMapping(value = "admin/sale/salemember", produces = "application/json;charset=UTF-8")
public class AdminSaleMemberController extends BaseController {

    @Resource
    private ISaleMemberService saleMemberService;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/sale/salemember/salememberlist";
    }

    /**
     * 用户列表列表
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SaleMember>> list(HttpServletRequest request,
                                                               Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SaleMember>> serviceResult = saleMemberService.getSaleMembers(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<SaleMember>> jsonResult = new HttpJsonResult<List<SaleMember>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 审核失败
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "freeze", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> freeze(Integer awardId, HttpServletRequest request,
                                                        HttpServletResponse response, Integer id) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        HttpJsonResult<Boolean> jsonResult = null;

        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(id);
        if (!resultSaleMemberOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
        }

        SaleMember saleMember = resultSaleMemberOld.getResult();

        if (SaleMember.STATE_2 != saleMember.getState().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("不是提交审核状态，不能提交！");
            return jsonResult;
        }

        saleMember.setAuditId(adminUser.getId());
        saleMember.setAuditName(adminUser.getName());
        saleMember.setState(SaleMember.STATE_4);

        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);

        if (!resultSaleMember.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            return jsonResult;
        } else {
            jsonResult = new HttpJsonResult<Boolean>();
        }

        return jsonResult;
    }

    /**
     * 审核通过
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "unfreeze", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> unfreeze(Integer awardId,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          Integer id) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        HttpJsonResult<Boolean> jsonResult = null;

        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(id);
        if (!resultSaleMemberOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        SaleMember saleMember = resultSaleMemberOld.getResult();

        if (SaleMember.STATE_2 != saleMember.getState().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("不是提交审核状态，不能提交！");
            return jsonResult;
        }

        saleMember.setAuditId(adminUser.getId());
        saleMember.setAuditName(adminUser.getName());
        saleMember.setState(SaleMember.STATE_3);

        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);

        if (!resultSaleMember.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            return jsonResult;
        } else {
            jsonResult = new HttpJsonResult<Boolean>();
        }

        return jsonResult;
    }

}
