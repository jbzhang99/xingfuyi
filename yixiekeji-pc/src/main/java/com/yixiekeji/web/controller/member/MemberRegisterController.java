package com.yixiekeji.web.controller.member;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yixiekeji.core.*;
import com.yixiekeji.core.sms.util.SmsUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.sale.ISaleMemberService;
import com.yixiekeji.service.sms.ISenderService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员注册controller
 *
 * @Filename: MemberRegisterController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 */
@Controller
public class MemberRegisterController extends BaseController {

    @Resource
    private IMemberService memberService;
    @Resource
    private ISenderService senderService;

    @Resource
    private ISaleMemberService saleMemberService;

    /**
     * 查看用户协议
     *
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/service_protocol.html", method = {RequestMethod.GET})
    public String protocol(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> stack) {
        return "front/member/serviceprotocol";
    }

    /**
     * 跳转到注册页面
     *
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/register.html", method = {RequestMethod.GET})
    public String signup(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> stack) {
        Cookie cookie = CookieHelper.getCookieByName(request, SaleMember.EJS_SALECODE);
        if (cookie != null) {
            String cookieValue = cookie.getValue();
            cookieValue = StringUtil.trim(cookieValue);
            stack.put("salecode", cookieValue);
        }

        return "front/member/membersignup";
    }

    /**
     * 判断用户名是否已存在
     *
     * @param request
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/nameIsExist.html", method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<Boolean> verify_membername(HttpServletRequest request,
                                              HttpServletResponse response,
                                              @RequestParam(value = "name", required = true) String name) throws Exception {
        //判断用户名是否已存在
        ServiceResult<List<Member>> serviceResult = memberService.getMemberByName(name);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>("用户名校验出错，请重试！");
        }

        if (serviceResult.getResult() != null && serviceResult.getResult().size() > 0) {
            return new HttpJsonResult<Boolean>("用户名重复，请重新输入！");
        }

        return new HttpJsonResult<Boolean>();
    }

    /**
     * 获取手机验证码
     *
     * @param request
     * @param response
     * @param dataMap
     * @param mob        手机号码
     * @param verifycode 验证码
     * @return
     */
    @RequestMapping(value = "/sendVerifySMS.html", method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<Integer> sendVerifySMS(HttpServletRequest request,
                                          HttpServletResponse response,
                                          Map<String, Object> dataMap,
                                          String verifycode,
                                          String type, String mob) throws Exception {
        HttpSession session = request.getSession();
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
        String smsCode = type + "_smsCode";
        String exp_time = type + "_exp_time";
        String vc_count = type + "_vc_count";

        //注册时才需要校验
        if (!isNull(type) && "reg".equals(type)) {
            //获得session中的验证码
            // String verify_number = WebFrontSession.getVerifyNumber(request);
            Object verifycode1 = session.getAttribute("verifycode");
            if (verifycode1 == null || !verifycode1.equals(verifycode)) {
                return new HttpJsonResult<Integer>("验证码不正确");
            }
            //判断用户名是否已存在
            ServiceResult<Boolean> serviceResult = memberService.isMobExists(mob);
            if (!serviceResult.getSuccess()) {
                return new HttpJsonResult<Integer>("手机号校验出错，请重试");
            }
            if (serviceResult.getResult() != null && serviceResult.getResult() == Boolean.TRUE) {
                return new HttpJsonResult<Integer>("手机号重复，请重新输入");
            }
        }
        int i = RandomUtils.nextInt(1000, 9999);
        SendSmsResponse sendSmsResponse = SmsUtil.sendSms(mob, String.valueOf(i));
        if (sendSmsResponse.getMessage().equals("OK")) {
            jsonResult.setSuccess(true);
            jsonResult.setMessage("发送成功");
            session.setAttribute(smsCode, String.valueOf(i));
            //将信息放入当前会话
            session.setAttribute(exp_time, new Date().getTime());
            session.setAttribute(vc_count, session.getAttribute(vc_count) == null ? 0
                    : ((Integer) session.getAttribute(vc_count)) + 1);

            //当天只能获取5次
            if (session.getAttribute(exp_time) != null
                    && isCur((long) session.getAttribute(exp_time))
                    && ((Integer) session.getAttribute(vc_count)) >= ConstantsEJS.SMS_MAX_GIVEN_NUM) {
                return new HttpJsonResult<Integer>("今日获取验证码次数过多,请明日再试");
            }
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("发送失败");
        }
        return jsonResult;
    }

    /**
     * 是否当天
     *
     * @param
     * @return
     */
    private boolean isCur(long time) {
        long now = new Date().getTime();
        long diff = (now - time) / (1000 * 60 * 60 * 24);
        return diff == 0;
    }

    /**
     * 注册信息提交
     *
     * @param request
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/doregister.html", method = {RequestMethod.POST})
    public @ResponseBody
    HttpJsonResult<Member> signupSubmit(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Member member, String verifyCode,
                                        String smsCode) throws Exception {
        //获得session中的验证码
        String verify_number = WebFrontSession.getVerifyNumber(request);
        if (verify_number == null || !verify_number.equalsIgnoreCase(verifyCode)) {
            return new HttpJsonResult<Member>("验证码不正确！");
        }

        HttpSession session = request.getSession();
        //校验验证码是否过期
        long time = session.getAttribute("reg_exp_time") != null
                ? (long) session.getAttribute("reg_exp_time")
                : 0l;
        long now = new Date().getTime();
        long diff = (((now - time) / 1000) / 60);
        if (diff > ConstantsEJS.SMS_MAX_WAIT_TIME) {
            session.removeAttribute("reg_smsCode");
            return new HttpJsonResult<Member>("验证码已过期,请重新获取");
        }

        if (session.getAttribute("reg_smsCode") == null
                || !smsCode.equals(session.getAttribute("reg_smsCode"))) {
            return new HttpJsonResult<Member>("手机验证码错误");
        }

        // 初始化会员信息
        String pwd = member.getPassword();
        member.setPassword(Md5.getMd5String(member.getPassword()));
        member.setGrade(Member.GRADE_1);
        member.setGradeValue(0);
        member.setMobile(member.getPhone());
        member.setIntegral(0);
        member.setLastLoginIp(WebUtil.getIpAddr(request));
        member.setLoginNumber(0);
        member.setPwdErrCount(0);
        member.setSource(ConstantsEJS.SOURCE_1_PC);
        member.setBalance(new BigDecimal(0.00));
        member.setBalancePwd("");
        member.setIsEmailVerify(ConstantsEJS.YES_NO_0);
        member.setIsSmsVerify(ConstantsEJS.YES_NO_1);
        member.setSmsVerifyCode("");
        member.setEmailVerifyCode("");
        member.setCanReceiveSms(1);
        member.setCanReceiveEmail(1);
        member.setStatus(Member.STATUS_1);
        member.setEmail("");
        ServiceResult<Member> serviceResult = memberService.memberRegister(member);
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Member>(serviceResult.getMessage());
            }
        }

        //注册成功后默认登录
        String ip = WebUtil.getIpAddr(request);
        // 登录验证
        ServiceResult<Member> serviceResultLogin = memberService.memberLogin(member.getName(), pwd,
                ip, ConstantsEJS.SOURCE_1_PC);

        member = serviceResultLogin.getResult();
        WebFrontSession.putMemberSession(request, member);

        //注册时赠送经验值及积分
        memberService.memberRegistSendValue(member.getId(), member.getName());

        //注册成功后三级分销录入成功
        sale(member, request);

        return jsonResult;
    }

    private boolean sale(Member member, HttpServletRequest request) {
        Cookie cookie = CookieHelper.getCookieByName(request, SaleMember.EJS_SALECODE);
        String cookieValue = null;
        if (cookie != null) {
            cookieValue = cookie.getValue();
            cookieValue = StringUtil.trim(cookieValue);
        }

        String salecode = request.getParameter("salecode");
        salecode = StringUtil.trim(salecode);

        if ((null == salecode || "".equals(salecode))
                && (null == cookieValue || "".equals(cookieValue))) {
            return true;
        }

        SaleMember saleMember = new SaleMember();
        saleMember.setMemberId(member.getId());
        saleMember.setMemberName(member.getName());
        saleMember.setCertificateType(SaleMember.CERTIFICATETYPE_CODE);
        saleMember.setState(SaleMember.STATE_1);
        saleMember.setIsSale(ConstantsEJS.YES_NO_0);

        //输入推广码不为空，按照推广码来计算
        String saleCodeStr = "";
        if (null != salecode && !"".equals(salecode)) {
            saleCodeStr = salecode;
        } else {
            saleCodeStr = cookieValue;
        }
        ServiceResult<SaleMember> resultMember = saleMemberService
                .getSaleMemberBySaleCode(saleCodeStr);
        if (!resultMember.getSuccess()) {
            return false;
        }
        SaleMember saleMemberMember = resultMember.getResult();
        if (saleMemberMember == null) {
            return false;
        }
        if (saleMemberMember.getReferrerPid() != null
                && saleMemberMember.getReferrerPid().intValue() != 0) {
            saleMember.setGrade(SaleMember.GRADE_3);
        } else if (saleMemberMember.getReferrerId() != null
                && saleMemberMember.getReferrerId().intValue() != 0) {
            saleMember.setGrade(SaleMember.GRADE_2);
        } else {
            saleMember.setGrade(SaleMember.GRADE_1);
        }
        saleMember.setReferrerId(saleMemberMember.getMemberId());
        saleMember.setReferrerName(saleMemberMember.getMemberName());
        saleMember.setReferrerPid(saleMemberMember.getReferrerId());
        saleMember.setReferrerPname(saleMemberMember.getReferrerName());
        saleMember.setReferrerCode(saleCodeStr);

        ServiceResult<Integer> serviceResult = saleMemberService
                .saveSaleMemberByMemberId(saleMember);
        if (!serviceResult.getSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * @Description: 发送短信
     * @Author: mofan
     * @Date: 2019/10/23
     */
    @GetMapping(value = "/sendSmsVerify")
    @ResponseBody
    public ServiceResult<Integer> sendSmsVerify(HttpServletRequest request, String type, String mobile) throws Exception {
        HttpSession session = request.getSession();
        String smsCode = type + "_smsCode";
        String exp_time = type + "_exp_time";
        String vc_count = type + "_vc_count";

        //注册时才需要校验
        if (!isNull(type) && "reg".equals(type)) {
            //判断用户名是否已存在
            ServiceResult<Boolean> serviceResult = memberService.isMobExists(mobile);
            if (serviceResult.getResult()) {
                return new ServiceResult<>(false, StatusCode.ERROR, "手机号已存在!");
            }
        }
        String number = RandomUtil.randomNumber(4);
        SendSmsResponse sendSmsResponse = SmsUtil.sendSms(mobile, number);
        if (sendSmsResponse.getMessage().equals("OK")) {
            session.setAttribute(smsCode, number);
            //将信息放入当前会话
            session.setAttribute(exp_time, new Date().getTime());
            session.setAttribute(vc_count, session.getAttribute(vc_count) == null ? 0
                    : ((Integer) session.getAttribute(vc_count)) + 1);
            //当天只能获取5次
            if (session.getAttribute(exp_time) != null
                    && isCur((long) session.getAttribute(exp_time))
                    && ((Integer) session.getAttribute(vc_count)) >= ConstantsEJS.SMS_MAX_GIVEN_NUM) {
                return new ServiceResult<Integer>(false, StatusCode.ERROR, "今日获取验证码次数过多,请明日再试");
            }
            return new ServiceResult<>(true, StatusCode.OK, "发送成功!");
        } else {
            return new ServiceResult<>(false, StatusCode.ERROR, "发送失败!");
        }
    }

    /**
     * @Description: 用户注册
     * @Author: mofan
     * @Date: 2019/10/9
     */
    @PostMapping(value = "/doregister")
    @ResponseBody
    public ServiceResult doregister(HttpServletRequest request,
                                    @RequestParam(value = "saleCode",required = false) String saleCode,
                                    @RequestParam("mobile") String mobile,
                                    @RequestParam("smsVerifyCode") String smsVerifyCode,
                                    @RequestParam("password") String password){
        // 初始化会员信息
        Member member = new Member();
        if(saleCode != null && saleCode != ""){
            member.setUserType(0);
            member.setSaleCode(saleCode);
            member.setName(mobile);
        }else {
            member.setUserType(1);
            member.setName(mobile);
        }
        member.setNickName(mobile);
        member.setPassword(Md5.getMd5String(password));
        member.setGrade(Member.GRADE_1);
        member.setGradeValue(0);
        member.setMobile(mobile);
        member.setIntegral(0);
        member.setLastLoginIp(WebUtil.getIpAddr(request));
        member.setLoginNumber(0);
        member.setPwdErrCount(0);
        member.setSource(ConstantsEJS.SOURCE_1_PC);
        member.setBalance(new BigDecimal(0.00));
        member.setBalancePwd("");
        member.setIsEmailVerify(ConstantsEJS.YES_NO_0);
        member.setIsSmsVerify(ConstantsEJS.YES_NO_1);
        member.setSmsVerifyCode(smsVerifyCode);
        member.setEmailVerifyCode("");
        member.setCanReceiveSms(1);
        member.setCanReceiveEmail(1);
        member.setStatus(Member.STATUS_1);
        member.setEmail("");
        member.setLoginSource(ConstantsEJS.YES_NO_1);
        member.setPhone(mobile);

        HttpSession session = request.getSession();

        //校验验证码是否过期
        long time = session.getAttribute("reg_exp_time") != null
            ? (long) session.getAttribute("reg_exp_time")
            : 0l;
        long now = new Date().getTime();
        long diff = (((now - time) / 1000) / 60);
        if (diff > ConstantsEJS.SMS_MAX_WAIT_TIME) {
            session.removeAttribute("reg_smsCode");
            return new ServiceResult(false,StatusCode.ERROR,"验证码已过期,请重新获取!");
        }

        Object reg_smsCode = session.getAttribute("reg_smsCode");
        if (reg_smsCode == null || !reg_smsCode.equals(smsVerifyCode)) {
            return new ServiceResult(false,StatusCode.ERROR,"短信验证码校验失败!");
        }else {
            ServiceResult<Member> registerResult = memberService.memberRegister(member);
            if (!registerResult.getSuccess()) {
                return new ServiceResult(false,StatusCode.ERROR,"注册失败!");
            }
            //        //注册成功后默认登录
            String ip = WebUtil.getIpAddr(request);
//        // 登录验证
            ServiceResult<Member> serviceResultLogin = memberService.memberLogin(member.getMobile(), password,
                ip, ConstantsEJS.SOURCE_1_PC);

//        //注册时赠送经验值及积分
            member = serviceResultLogin.getResult();

            //将促销员的信息放入推广表
            if(member.getUserType() == 1){
                SaleMember saleMember = new SaleMember();
                saleMember.setMemberId(member.getId());
                saleMember.setMemberName(member.getName());
                saleMember.setCertificateType(SaleMember.CERTIFICATETYPE_CODE);
                saleMember.setState(SaleMember.STATE_1);
                saleMember.setGrade(SaleMember.GRADE_1);
                saleMember.setIsSale(ConstantsEJS.YES_NO_1);
                ServiceResult<Integer> resultSaleMember = saleMemberService
                    .saveSaleMemberByMemberId(saleMember);
                if (!resultSaleMember.getSuccess()) {
                    return new ServiceResult(false,StatusCode.ERROR,"操作失败!");
                }
            }

            WebFrontSession.putMemberSession(request, member);
            //注册时赠送经验值及积分
            memberService.memberRegistSendValue(member.getId(), member.getName());
            //注册成功后三级分销录入成功
            sale(member, request);
            member.setPassword("");
            return new ServiceResult(true,StatusCode.OK,member,"注册成功!");
        }
    }

}
