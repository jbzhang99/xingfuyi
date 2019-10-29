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
    public String balancelist(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> dataMap) {
        Member sessionMember = WebFrontSession.getLoginedUser(request);

        //查询用户信息
        ServiceResult<Member> memberResult = memberService.getMemberById(sessionMember.getId());

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, 1);
        ServiceResult<List<MemberBalanceLogs>> serviceResult = memberBalanceLogsService
            .getMemberBalanceLogs(sessionMember.getId(), pager);

        dataMap.put("pagesize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        dataMap.put("memberBalanceLogss", serviceResult.getResult());
        dataMap.put("member", memberResult.getResult());

        return "h5/member/balance/balancelist";
    }

    /**
     * 返回余额 json 数据
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/balanceJson.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<MemberBalanceLogs>> balanceJson(HttpServletRequest request,
                                                                             HttpServletResponse response) {
        HttpJsonResult<List<MemberBalanceLogs>> jsonResult = new HttpJsonResult<List<MemberBalanceLogs>>();
        Member member = WebFrontSession.getLoginedUser(request);

        String pageNumStr = request.getParameter("pageNum");
        int pageNum = ConvertUtil.toInt(pageNumStr, 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageNum);

        ServiceResult<List<MemberBalanceLogs>> serviceResult = memberBalanceLogsService
            .getMemberBalanceLogs(member.getId(), pager);
        if (!serviceResult.getSuccess()) {
            return jsonResult;
        }

        jsonResult.setData(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

}
