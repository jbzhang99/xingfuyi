package com.yixiekeji.web.controller.member;

import java.util.Date;
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
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberApplyDrawing;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.member.IMemberApplyDrawingService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 会员提款申请管理controller
 *
 * @Filename: AdminMemberDrawingController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/member/drawmoney", produces = "application/json;charset=UTF-8")
public class AdminMemberDrawingController extends BaseController {

    @Resource
    private IMemberApplyDrawingService memberApplyDrawingService;

    /**
     * 会员提款申请管理
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/member/member/memberdrawinglist";
    }

    /**
     * 会员提款申请管理列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MemberApplyDrawing>> list(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MemberApplyDrawing>> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawings(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<MemberApplyDrawing>> jsonResult = new HttpJsonResult<List<MemberApplyDrawing>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 会员提款申请审核通过
     * @param awardId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "auditing", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> auditing(Integer id, HttpServletRequest request,
                                                          HttpServletResponse response) {

        ServiceResult<MemberApplyDrawing> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawing(id);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        if (serviceResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("提款申请信息获取失败，请稍后再试！");
        }

        MemberApplyDrawing memberApplyDrawing = serviceResult.getResult();
        MemberApplyDrawing memberApplyDrawingNew = new MemberApplyDrawing();
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);

        memberApplyDrawingNew.setId(id);
        memberApplyDrawingNew.setState(ConstantsEJS.MEMBER_DRAWING_STATE_2);
        memberApplyDrawingNew.setAuditingTime(new Date());
        memberApplyDrawingNew.setOptId(adminUser.getId());
        memberApplyDrawingNew.setOptName(adminUser.getName());
        memberApplyDrawingNew.setMemberId(memberApplyDrawing.getMemberId());
        memberApplyDrawingNew.setMoney(memberApplyDrawing.getMoney());

        ServiceResult<Integer> updateResult = memberApplyDrawingService
            .updateMemberApplyDrawing(memberApplyDrawingNew);
        HttpJsonResult<Boolean> jsonResult = null;
        if (updateResult.getSuccess() && updateResult.getResult() != null
            && updateResult.getResult() > 0) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            String error = updateResult.getMessage();
            jsonResult = new HttpJsonResult<Boolean>(
                StringUtil.isEmpty(error, true) ? "操作失败，请重试！" : error);
        }
        return jsonResult;
    }

    /**
     * 会员提款申请审核拒绝
     * @param awardId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "reject", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> reject(Integer id, HttpServletRequest request,
                                                        HttpServletResponse response) {
        ServiceResult<MemberApplyDrawing> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawing(id);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        if (serviceResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("提款申请信息获取失败，请稍后再试！");
        }

        MemberApplyDrawing memberApplyDrawing = serviceResult.getResult();

        if (memberApplyDrawing.getState().intValue() != ConstantsEJS.MEMBER_DRAWING_STATE_1) {
            return new HttpJsonResult<Boolean>("对不起，只能操作提交审核的申请！");
        }

        MemberApplyDrawing memberApplyDrawingNew = new MemberApplyDrawing();
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);

        memberApplyDrawingNew.setId(id);
        memberApplyDrawingNew.setAuditingTime(new Date());
        memberApplyDrawingNew.setFailReason("审核不通过");
        memberApplyDrawingNew.setState(ConstantsEJS.MEMBER_DRAWING_STATE_4);
        memberApplyDrawingNew.setOptId(adminUser.getId());
        memberApplyDrawingNew.setOptName(adminUser.getName());

        ServiceResult<Integer> updateResult = memberApplyDrawingService
            .updateMemberApplyDrawing(memberApplyDrawingNew);
        HttpJsonResult<Boolean> jsonResult = null;
        if (updateResult.getSuccess() && updateResult.getResult() != null
            && updateResult.getResult() > 0) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            String error = serviceResult.getMessage();
            jsonResult = new HttpJsonResult<Boolean>(
                StringUtil.isEmpty(error, true) ? "审核失败，请重试！" : error);
        }
        return jsonResult;
    }

    /**
     * 会员提款申请已打款
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "paid", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> paid(Integer id, HttpServletRequest request,
                                                      HttpServletResponse response) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        ServiceResult<Integer> serviceResult = memberApplyDrawingService.paid(id, adminUser.getId(),
            adminUser.getName());
        HttpJsonResult<Boolean> jsonResult = null;
        if (serviceResult.getSuccess() && serviceResult.getResult() != null
            && serviceResult.getResult() > 0) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            String error = serviceResult.getMessage();
            jsonResult = new HttpJsonResult<Boolean>(
                StringUtil.isEmpty(error, true) ? "操作失败，请重试！" : error);
        }
        return jsonResult;
    }
}
