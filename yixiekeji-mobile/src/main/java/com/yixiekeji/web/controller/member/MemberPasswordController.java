package com.yixiekeji.web.controller.member;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.core.sms.bean.VerifyCodeResult;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 用户中心：资料管理
 *                       
 */
@Controller
@RequestMapping(value = "member")
public class MemberPasswordController extends BaseController {

    @Resource
    private IMemberService memberService;

    /**
     * 跳转到 修改密码界面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/editpassword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> toEditPassword(HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Member> serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        jsonResult.setData(member);
        return jsonResult;
    }

    /**
     * 修改密码提交
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/updatepassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> editPasswordSumbit(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    @RequestParam(value = "oldPwd", required = true) String oldPwd,
                                                                    @RequestParam(value = "newPwd", required = true) String newPwd,
                                                                    String confirmPwd) throws Exception {

        if (!newPwd.equals(confirmPwd)) {
            return new HttpJsonResult<Boolean>("确认密码不对");
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Member> serviceResult = memberService.editPassword(oldPwd, newPwd,
            sessionMember);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 跳转到设置支付密码页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/setbalancepassword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> balancepwdadd(HttpServletRequest request,
                                                              HttpServletResponse response) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        //查询用户信息
        ServiceResult<Member> memberResult = new ServiceResult<Member>();
        memberResult = memberService.getMemberById(sessionMember.getId());

        jsonResult.setData(memberResult.getResult());
        return jsonResult;
    }

    /**
     * 支付密码提交
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/savebalancepassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> addBalancePwdSumbit(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     @RequestParam(value = "password", required = true) String password,
                                                                     String confirmPwd) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        if (!password.equals(confirmPwd)) {
            return new HttpJsonResult<Boolean>("确认密码不对");
        }

        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.addBalancePassword(password, member);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 跳转到 修改支付密码页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/editbalancepassword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> toEditBalancePwd(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();

        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        //查询用户信息
        ServiceResult<Member> memberResult = new ServiceResult<Member>();
        memberResult = memberService.getMemberById(sessionMember.getId());

        jsonResult.setData(memberResult.getResult());
        return jsonResult;
    }

    @RequestMapping(value = "/balancepwdreset.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> balancepwdreset(HttpServletRequest request,
                                                                HttpServletResponse response) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        //查询用户信息
        ServiceResult<Member> memberResult = new ServiceResult<Member>();
        memberResult = memberService.getMemberById(sessionMember.getId());

        jsonResult.setData(memberResult.getResult());
        return jsonResult;
    }

    /**
     * 获取手机验证码
     * @param request
     * @param response
     * @param dataMap
     * @param mob
     * @param verifycode
     * @return
     */
    @RequestMapping(value = "/balancepwdreset/sendVerifySMS.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Integer> sendVerifySMS(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> dataMap,
                                                               String mob) {
        //        HttpSession session = request.getSession();
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }
        try {
            //            //获得session中的验证码
            //            String verify_number = WebFrontSession.getVerifyNumber(request);
            //            if (verify_number == null || !verify_number.equalsIgnoreCase(verifycode)) {
            //                return new HttpJsonResult<Integer>("验证码不正确");
            //            }
            //
            //            //            ServiceResult<Object> result = senderService.sendVerifyCode(mob);

            //TODO默认验证码是 888888 上线需要去掉
            //--------------------------测试代码 bg--------------------------//
            ServiceResult<Object> result = new ServiceResult<Object>();
            VerifyCodeResult vc = new VerifyCodeResult();
            vc.setVerifyCode("888888");
            result.setResult(vc);
            //--------------------------测试代码 ed--------------------------//

            if (result.getSuccess()) {
                VerifyCodeResult vcr = (VerifyCodeResult) result.getResult();
                Member memberNew = new Member();
                memberNew.setId(sessionMember.getId());
                memberNew.setSmsCode(vcr.getVerifyCode());
                ServiceResult<Boolean> updateServiceResult = memberService.updateMember(memberNew);
                if (updateServiceResult.getSuccess()) {
                    log.debug("短信发送完毕：验证码：" + vcr.getVerifyCode() + "|" + vcr.toString());
                } else {
                    return new HttpJsonResult<Integer>(updateServiceResult.getMessage());
                }
                //                //将信息放入当前会话
                //                session.setAttribute("smsCode", vcr.getVerifyCode());
                //                session.setAttribute("exp_time", new Date().getTime());
                //                session.setAttribute("vc_count", session.getAttribute("vc_count") == null ? 0
                //                    : ((Integer) session.getAttribute("vc_count")) + 1);
                //
                //                //当天只能获取5次
                //                if (session.getAttribute("exp_time") != null
                //                    && isCur((long) session.getAttribute("exp_time")) && ((Integer) session
                //                        .getAttribute("vc_count")) >= ConstantsEJS.SMS_MAX_GIVEN_NUM) {
                //                    return new HttpJsonResult<Integer>("今日获取验证码次数过多,请明日再试");
                //                }
                //                log.debug("短信发送完毕：验证码：" + vcr.getVerifyCode() + "|" + vcr.toString());
            } else {
                jsonResult.setMessage(result.getMessage());
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                jsonResult.setMessage(e.getMessage());
            } else {
                e.printStackTrace();
                jsonResult.setMessage("获取失败,请稍后重试");
            }
        }
        return jsonResult;
    }

    /**
     * 是否当天
     * @param attribute
     * @return
     */
    private boolean isCur(long time) {
        long now = new Date().getTime();
        long diff = (now - time) / (1000 * 60 * 60 * 24);
        return diff == 0;
    }

    @RequestMapping(value = "/balancepwdreset/updatepwd.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updatepwd(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           String verifycode, String newPwd,
                                                           String smsCode, String repwd) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        //        //获得session中的验证码
        //        String verify_number = WebFrontSession.getVerifyNumber(request);
        //        if (verify_number == null || !verify_number.equalsIgnoreCase(verifycode)) {
        //            return new HttpJsonResult<Boolean>("验证码不正确");
        //        }
        //
        //        HttpSession session = request.getSession();
        //        //校验验证码是否过期
        //        long time = session.getAttribute("exp_time") != null
        //            ? (long) session.getAttribute("exp_time")
        //            : 0l;
        //        long now = new Date().getTime();
        //        long diff = (((now - time) / 1000) / 60);
        //        if (diff > ConstantsEJS.SMS_MAX_WAIT_TIME) {
        //            session.removeAttribute("smsCode");
        //            return new HttpJsonResult<Boolean>("验证码已过期,请重新获取");
        //        }
        ServiceResult<Member> sr = memberService.getMemberById(sessionMember.getId());
        Member member = sr.getResult();

        if (StringUtil.isEmpty(member.getSmsCode()) || !smsCode.equals(member.getSmsCode())) {
            return new HttpJsonResult<Boolean>("手机验证码错误");
        }

        //更新支付密码
        member.setBalancePwd(Md5.getMd5String(newPwd));
        member.setSmsCode("");
        ServiceResult<Boolean> serviceResult = memberService.updateMember(member);
        //更新session用户
        WebFrontSession.putMemberSession(request, member);

        jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 修改支付密码提交
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/updatebalancepassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> editBalancePasswordSumbit(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           @RequestParam(value = "oldPwd", required = true) String oldPwd,
                                                                           @RequestParam(value = "newPwd", required = true) String newPwd,
                                                                           String confirmPwd) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!newPwd.equals(confirmPwd)) {
            return new HttpJsonResult<Boolean>("确认密码不对");
        }
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.editBalancePassword(oldPwd, newPwd, sessionMember.getId());

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }
}
