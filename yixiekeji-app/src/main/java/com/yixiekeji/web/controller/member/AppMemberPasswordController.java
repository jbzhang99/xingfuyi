package com.yixiekeji.web.controller.member;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.ServiceResult;
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
public class AppMemberPasswordController extends BaseController {

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
    @RequestMapping(value = "/app-editpassword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> toEditPassword(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Integer memberId) throws Exception {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();

        ServiceResult<Member> serviceResult = memberService.getMemberById(memberId);

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
    @RequestMapping(value = "/app-updatepassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> editPasswordSumbit(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    @RequestParam(value = "oldPwd", required = true) String oldPwd,
                                                                    @RequestParam(value = "newPwd", required = true) String newPwd,
                                                                    Integer memberId) throws Exception {

        Member member = memberService.getMemberById(memberId).getResult();
        ServiceResult<Member> serviceResult = memberService.editPassword(oldPwd, newPwd, member);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
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
    @RequestMapping(value = "/app-setbalancepassword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> balancepwdadd(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              Integer memberId) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();

        //查询用户信息
        ServiceResult<Member> memberResult = new ServiceResult<Member>();
        memberResult = memberService.getMemberById(memberId);

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
    @RequestMapping(value = "/app-savebalancepassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> addBalancePwdSumbit(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     @RequestParam(value = "password", required = true) String password,
                                                                     Integer memberId) throws Exception {

        ServiceResult<Member> serviceResult = new ServiceResult<Member>();

        Member member = memberService.getMemberById(memberId).getResult();

        serviceResult = memberService.addBalancePassword(password, member);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
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
    @RequestMapping(value = "/app-editbalancepassword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> toEditBalancePwd(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Integer memberId) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();

        //查询用户信息
        ServiceResult<Member> memberResult = memberService.getMemberById(memberId);

        jsonResult.setData(memberResult.getResult());
        return jsonResult;
    }

    @RequestMapping(value = "/app-balancepwdreset.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Member> balancepwdreset(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Integer memberId) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();

        //查询用户信息
        ServiceResult<Member> memberResult = new ServiceResult<Member>();
        memberResult = memberService.getMemberById(memberId);

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
    @RequestMapping(value = "/balancepwdreset/app-sendVerifySMS.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Integer> sendVerifySMS(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> dataMap,
                                                               String mob
    //                                                               , String verifycode
    ) {
        HttpSession session = request.getSession();
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
        try {
            //获得session中的验证码
            //            String verify_number = WebFrontSession.getVerifyNumber(request);
            //            if (verify_number == null || !verify_number.equalsIgnoreCase(verifycode)) {
            //                return new HttpJsonResult<Integer>("验证码不正确");
            //            }

            //            ServiceResult<Object> result = senderService.sendVerifyCode(mob);

            //TODO默认验证码是 888888 上线需要去掉
            //--------------------------测试代码 bg--------------------------//
            ServiceResult<Object> result = new ServiceResult<Object>();
            VerifyCodeResult vc = new VerifyCodeResult();
            vc.setVerifyCode("888888");
            result.setResult(vc);
            //--------------------------测试代码 ed--------------------------//

            if (result.getSuccess()) {
                VerifyCodeResult vcr = (VerifyCodeResult) result.getResult();

                //将信息放入当前会话
                session.setAttribute("smsCode", vcr.getVerifyCode());
                session.setAttribute("exp_time", new Date().getTime());
                session.setAttribute("vc_count", session.getAttribute("vc_count") == null ? 0
                    : ((Integer) session.getAttribute("vc_count")) + 1);

                //当天只能获取5次
                if (session.getAttribute("exp_time") != null
                    && isCur((long) session.getAttribute("exp_time")) && ((Integer) session
                        .getAttribute("vc_count")) >= ConstantsEJS.SMS_MAX_GIVEN_NUM) {
                    return new HttpJsonResult<Integer>("今日获取验证码次数过多,请明日再试");
                }
                log.debug("短信发送完毕：验证码：" + vcr.getVerifyCode() + "|" + vcr.toString());
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

    @RequestMapping(value = "/balancepwdreset/app-updatepwd.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updatepwd(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           String newPwd, String smsCode,
                                                           Integer memberId) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        HttpSession session = request.getSession();
        //校验验证码是否过期
        long time = session.getAttribute("exp_time") != null
            ? (long) session.getAttribute("exp_time") : 0l;
        long now = new Date().getTime();
        long diff = (((now - time) / 1000) / 60);
        if (diff > ConstantsEJS.SMS_MAX_WAIT_TIME) {
            session.removeAttribute("smsCode");
            return new HttpJsonResult<Boolean>("验证码已过期,请重新获取");
        }

        if (session.getAttribute("smsCode") == null
            || !smsCode.equals(session.getAttribute("smsCode"))) {
            return new HttpJsonResult<Boolean>("手机验证码错误");
        }

        ServiceResult<Member> sr = memberService.getMemberById(memberId);
        Member member = sr.getResult();
        //更新支付密码
        member.setBalancePwd(Md5.getMd5String(newPwd));
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
    @RequestMapping(value = "/app-updatebalancepassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> editBalancePasswordSumbit(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           @RequestParam(value = "oldPwd", required = true) String oldPwd,
                                                                           @RequestParam(value = "newPwd", required = true) String newPwd,
                                                                           Integer memberId) throws Exception {

        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.editBalancePassword(oldPwd, newPwd, memberId);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
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
