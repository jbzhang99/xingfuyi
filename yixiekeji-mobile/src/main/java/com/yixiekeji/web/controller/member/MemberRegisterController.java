package com.yixiekeji.web.controller.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.CookieHelper;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.sms.bean.VerifyCodeResult;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberWxsign;
import com.yixiekeji.entity.member.MobileVerifyCode;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.member.IMemberWxsignService;
import com.yixiekeji.service.member.IMobileVerifyCodeService;
import com.yixiekeji.service.sale.ISaleMemberService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 会员注册controller
 * 
 * @Filename: MemberRegisterController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class MemberRegisterController extends BaseController {

    @Resource
    private IMemberService           memberService;

    @Resource
    private IMemberWxsignService     memberWxsignService;

    @Resource
    private ISaleMemberService       saleMemberService;

    @Resource
    private IMobileVerifyCodeService mobileVerifyCodeService;

    /**
     * 跳转到注册页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/register.html", method = { RequestMethod.GET })
    public String signup(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> stack) {
        Cookie cookie = CookieHelper.getCookieByName(request, SaleMember.EJS_SALECODE);
        if (cookie != null) {
            String cookieValue = cookie.getValue();
            cookieValue = StringUtil.trim(cookieValue);
            stack.put("salecode", cookieValue);
        }

        return "h5/member/register";
    }

    /**
     * 获取手机验证码
     * @param request
     * @param response
     * @param dataMap
     * @param mob 手机号码
     * @param verifycode 验证码
     * @return
     */
    @RequestMapping(value = "/sendVerifySMS.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Integer> sendVerifySMS(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> dataMap,
                                                               String type, String mob,
                                                               String uid) {
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();

        if (StringUtil.isEmpty(uid)) {
            return new HttpJsonResult<Integer>("uid为空！");
        }
        //注册时才需要校验
        if (!isNull(type) && "reg".equals(type)) {

            //判断用户名是否已存在
            ServiceResult<Boolean> serviceResult = memberService.isMobExists(mob);
            if (!serviceResult.getSuccess()) {
                return new HttpJsonResult<Integer>("手机号校验出错，请重试");
            }
            if (serviceResult.getResult() != null && serviceResult.getResult() == Boolean.TRUE) {
                return new HttpJsonResult<Integer>("手机号重复，请重新输入");
            }
        }

        //--------------------------正式代码 开始------------------------
        //            ServiceResult<Object> result = senderService.sendVerifyCode(mob);
        //--------------------------正式代码 结束------------------------

        //TODO默认验证码是 888888 上线需要去掉 
        //--------------------------测试代码 本地测试时放开本段代码 bg--------------------------//
        ServiceResult<Object> result = new ServiceResult<Object>();
        VerifyCodeResult vc = new VerifyCodeResult();
        vc.setVerifyCode("888888");
        result.setResult(vc);
        //--------------------------测试代码 ed--------------------------//

        if (result.getSuccess()) {
            VerifyCodeResult vcr = (VerifyCodeResult) result.getResult();

            MobileVerifyCode mobileVerifyCode = null;

            ServiceResult<List<MobileVerifyCode>> verifyCodeResult = mobileVerifyCodeService
                .getMobileVerifyCodeByUid(uid);

            //已有记录时，更新次数
            if (verifyCodeResult.getSuccess() && verifyCodeResult.getResult().size() == 1) {
                mobileVerifyCode = verifyCodeResult.getResult().get(0);
                if (isCur(mobileVerifyCode.getCreateTime().getTime())
                    && mobileVerifyCode.getSendNum() >= ConstantsEJS.SMS_MAX_GIVEN_NUM) {
                    return new HttpJsonResult<Integer>("今日获取验证码次数过多,请明日再试");
                }
                mobileVerifyCode.setCode(vcr.getVerifyCode());
                mobileVerifyCode.setSendNum(mobileVerifyCode.getSendNum() + 1);
                mobileVerifyCodeService.updateMobileVerifyCode(mobileVerifyCode);
            } else if (verifyCodeResult.getSuccess() && verifyCodeResult.getResult().size() > 1) {
                //发现多条数据时删除数据，提示稍后再试
                mobileVerifyCodeService.delMobileVerifyCodeByUid(uid);
                return new HttpJsonResult<Integer>("网络异常，请再试一次");
            } else if (verifyCodeResult.getResult() == null
                       || verifyCodeResult.getResult().size() < 1) {
                //记录验证码
                mobileVerifyCode = new MobileVerifyCode();
                mobileVerifyCode.setUid(uid);
                mobileVerifyCode.setCode(vcr.getVerifyCode());
                mobileVerifyCode.setPhone(mob);
                mobileVerifyCode.setSendNum(1);
                ServiceResult<Integer> saveResult = mobileVerifyCodeService
                    .saveMobileVerifyCode(mobileVerifyCode);

                if (saveResult.getSuccess()) {
                    log.debug("短信发送完毕：验证码：" + vcr.getVerifyCode() + "|" + vcr.toString());
                } else {
                    return new HttpJsonResult<Integer>(saveResult.getMessage());
                }
            }

        } else {
            jsonResult.setMessage(result.getMessage());
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

    /**
     * 注册信息提交
     * @param request
     * @param response
     * @param stack
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/doregister.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> signupSubmit(HttpServletRequest request,
                                                              HttpServletResponse response) throws Exception {
        //        String verifyCode = request.getParameter("verifyCode");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordCfm = request.getParameter("password");
        String smsCode = request.getParameter("smsCode");
        String phone = request.getParameter("phone");
        String uid = request.getParameter("uid");

        //获取短信验证码
        ServiceResult<List<MobileVerifyCode>> verifyCodeResult = mobileVerifyCodeService
            .getMobileVerifyCodeByUid(uid);
        String sms_number = "";
        if (!verifyCodeResult.getSuccess() || verifyCodeResult.getResult() == null
            || verifyCodeResult.getResult().size() < 1) {
            return new HttpJsonResult<Boolean>("短信验证码不正确");
        } else if (verifyCodeResult.getResult().size() > 1) {
            //发现多条数据时删除数据，提示稍后再试
            mobileVerifyCodeService.delMobileVerifyCodeByUid(uid);
            return new HttpJsonResult<Boolean>("短信验证码异常，请重新获取");
        }
        sms_number = verifyCodeResult.getResult().get(0).getCode();
        if (!phone.equals(verifyCodeResult.getResult().get(0).getPhone())) {
            return new HttpJsonResult<Boolean>("手机号与验证码不匹配");
        }
        //校验验证码是否过期
        long time = verifyCodeResult.getResult().get(0).getCreateTime() != null
            ? verifyCodeResult.getResult().get(0).getCreateTime().getTime()
            : 0l;
        long now = new Date().getTime();
        long diff = (((now - time) / 1000) / 60);
        if (diff > ConstantsEJS.SMS_MAX_WAIT_TIME) {
            mobileVerifyCodeService.delMobileVerifyCodeByUid(uid);
            return new HttpJsonResult<Boolean>("验证码已过期,请重新获取");
        }

        if (StringUtil.isEmpty(sms_number) || !smsCode.equals(sms_number)) {
            return new HttpJsonResult<Boolean>("手机验证码错误");
        }

        if (StringUtil.isEmpty(userName, true)) {
            return new HttpJsonResult<Boolean>("用户名不能为空");
        }

        if (StringUtil.isEmpty(password, true)) {
            return new HttpJsonResult<Boolean>("登录密码不能为空");
        }

        if (!password.equals(passwordCfm)) {
            return new HttpJsonResult<Boolean>("确认密码不正确，请重新输入");
        }

        //判断用户名是否已存在
        ServiceResult<List<Member>> serviceResult = memberService.getMemberByName(userName);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>("用户名校验出错，请重试");
        }
        if (serviceResult.getResult() != null && serviceResult.getResult().size() > 0) {
            return new HttpJsonResult<Boolean>("用户名重复，请重新输入");
        }

        // 初始化会员信息
        Member member = new Member();
        member.setName(userName);
        member.setPassword(Md5.getMd5String(password));
        member.setGrade(Member.GRADE_1);
        member.setGradeValue(0);
        member.setMobile(phone);
        member.setIntegral(0);
        member.setLastLoginIp(WebUtil.getIpAddr(request));
        member.setLoginNumber(0);
        member.setPwdErrCount(0);
        member.setSource(ConstantsEJS.SOURCE_2_H5);
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
        member.setPhone(phone);
        ServiceResult<Member> registerResult = memberService.memberRegister(member);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!registerResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(registerResult.getCode())) {
                throw new RuntimeException(registerResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(registerResult.getMessage());
            }
        }
        //删除短信验证码
        mobileVerifyCodeService.delMobileVerifyCodeByUid(uid);
        //注册成功后默认登录
        String ip = WebUtil.getIpAddr(request);
        // 登录验证
        ServiceResult<Member> serviceResultLogin = memberService.memberLogin(member.getName(),
            password, ip, ConstantsEJS.SOURCE_2_H5);
        member = serviceResultLogin.getResult();
        WebFrontSession.putMemberSession(request, member);

        //注册时赠送经验值及积分
        memberService.memberRegistSendValue(member.getId(), member.getName());

        // 登录完成后把member和openid绑定
        String openid = (String) WebFrontSession.getObjFromSession(request, "openid");
        if (!StringUtil.isEmpty(openid)) {
            MemberWxsign memberWxsign = new MemberWxsign();
            memberWxsign.setMemberId(member.getId());
            memberWxsign.setOpenid(openid);
            memberWxsignService.saveMemberWxsign(memberWxsign);
        }

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

}
