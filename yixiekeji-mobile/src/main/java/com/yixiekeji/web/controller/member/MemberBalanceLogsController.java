package com.yixiekeji.web.controller.member;

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
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalanceLogs;
import com.yixiekeji.service.member.IMemberBalanceLogsService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 余额日志
 *                       
 * @Filename: MemberBalanceLogsController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberBalanceLogsController extends BaseController {

    @Resource
    private IMemberService            memberService;

    @Resource
    private IMemberBalanceLogsService memberBalanceLogsService;

    /**
     * 跳转到余额列表页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/balance.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> balancelist(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        //查询用户信息
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());

        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageIndex);

        ServiceResult<List<MemberBalanceLogs>> serviceResult = memberBalanceLogsService
            .getMemberBalanceLogs(member.getId(), pager);
        pager = serviceResult.getPager();

        dataMap.put("memberBalanceLogss", serviceResult.getResult());
        dataMap.put("member", memberResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

}
