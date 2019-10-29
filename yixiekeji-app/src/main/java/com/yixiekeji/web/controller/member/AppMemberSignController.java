package com.yixiekeji.web.controller.member;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberSignLogs;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.member.IMemberSignLogsService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 会员签到controller
 * 
 * @Filename: MemberSignController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class AppMemberSignController extends BaseController {

    @Resource
    private IMemberSignLogsService memberSignLogsService;
    @Resource
    private IMemberService         memberService;

    /**
     * 签到
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "member/app-sign.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> memberSign(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            Integer memberId) throws Exception {
        HttpJsonResult<Boolean> httpJsonResult = new HttpJsonResult<Boolean>();
        Member members = WebFrontSession.getLoginedUser(request, response);
        Member member = memberService.getMemberById(members.getId()).getResult();
        MemberSignLogs memberSignLogs = new MemberSignLogs();
        memberSignLogs.setLoginIp(WebUtil.getIpAddr(request));
        memberSignLogs.setMemberId(memberId);
        memberSignLogs.setMemberName(member.getName());
        memberSignLogs.setSource(ConstantsEJS.SOURCE_3_ANDROID);
        memberSignLogs.setCreateTime(new Date());

        ServiceResult<Boolean> saveRlt = memberSignLogsService.saveMemberSignLogs(memberSignLogs);
        if (!saveRlt.getSuccess()) {
            httpJsonResult.setMessage(saveRlt.getMessage());
            return httpJsonResult;
        }

        httpJsonResult.setData(saveRlt.getResult());
        return httpJsonResult;
    }
}
