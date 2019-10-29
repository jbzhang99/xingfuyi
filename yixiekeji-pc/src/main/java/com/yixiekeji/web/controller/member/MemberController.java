package com.yixiekeji.web.controller.member;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.email.SendMailBy163;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.core.sms.util.SmsUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.entity.member.MemberGradeConfig;
import com.yixiekeji.service.member.IMemberAddressService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心：资料管理
 *                       
 */
@Controller
@RequestMapping(value = "member")
public class MemberController extends BaseController {

    @Resource
    private IMemberAddressService memberAddressService;

    @Resource
    private IMemberService        memberService;

    @Resource
    private ICouponService        couponService;

    @Resource
    private DomainUrlUtil         domainUrlUtil;

    /**
     * 跳转到 收货地址管理界面  取所有收货地址
     * @param request
     * @param response
     * @param map
     * @param id  收货地址id
     * @return
     */
    @RequestMapping(value = "/address.html", method = { RequestMethod.GET })
    public String toReciptAddress(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap map) {
        Member member = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddressByMId(member.getId());
        Object obj = null;
        if (serviceResult.getSuccess()) {
            obj = serviceResult.getResult();
        }

        map.put("addressList", obj);

        return "front/member/account/myreciptaddress";
    }

    /**
     * 收货地址添加或者编辑界面
     * @param request
     * @param response
     * @param map
     * @param id
     * @return
     */
    @RequestMapping(value = "/editaddress.html", method = { RequestMethod.GET })
    public String toAddOrEditAddress(HttpServletRequest request, HttpServletResponse response,
                                     ModelMap map, Integer id) {
        ServiceResult<MemberAddress> serviceResult = new ServiceResult<MemberAddress>();
        if (id != null && !id.equals(0)) {
            serviceResult = memberAddressService.getMemberAddressById(id);
        }

        MemberAddress memberAddress = new MemberAddress();
        if (serviceResult.getSuccess()) {
            memberAddress = serviceResult.getResult();
        }
        map.put("address", memberAddress);
        return "front/member/account/addressedit";
    }

    /**
     * 收货地址信息提交或编辑
     * @param request
     * @param response
     * @param stack
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/saveaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> reciptAddressSumbit(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     Map<String, Object> stack,
                                                                     MemberAddress memberAddress) throws Exception {
        Member member = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();

        //如果id值不为空，则更新，否则添加。前台如果为空，转成Integer时为变为0
        if (memberAddress != null && memberAddress.getId() != null && memberAddress.getId() != 0) {
            serviceResult = memberAddressService.updateMemberAddress(memberAddress);
        } else {
            memberAddress.setMemberId(member.getId());
            memberAddress.setState(MemberAddress.STATE_2);
            serviceResult = memberAddressService.saveMemberAddress(memberAddress);
        }

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
     * 收获地址设为默认
     * @param request
     * @param response
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/setdefaultaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> defaultAddress(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                @RequestParam(value = "id", required = true) Integer id) throws Exception {

        Member member = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Boolean> serviceResult = memberAddressService.defaultMemberAddress(id,
            member.getId());

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
     * 删除收货地址
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/deleteaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> deleteAddress(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               @RequestParam(value = "id", required = true) Integer id) throws Exception {
        Member member = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Boolean> serviceResult = memberAddressService.deleteMemberAddress(id,
            member.getId());

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
     * 跳转到 个人资料界面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/info.html", method = { RequestMethod.GET })
    public String toPersonalfile(HttpServletRequest request, HttpServletResponse response,
                                 ModelMap map) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        map.put("member", member);

        ServiceResult<MemberGradeConfig> gradeConfigResult = memberService
            .getMemberGradeConfig(ConstantsEJS.MEMBER_GRADE_CONFIG_ID);
        MemberGradeConfig memberGradeConfig = gradeConfigResult.getResult();
        int gradeValue = 0;
        if (member.getGrade().intValue() == Member.GRADE_1) {
            gradeValue = memberGradeConfig.getGrade2().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_2) {
            gradeValue = memberGradeConfig.getGrade3().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_3) {
            gradeValue = memberGradeConfig.getGrade4().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_4) {
            gradeValue = memberGradeConfig.getGrade5().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_5) {
        }
        map.put("gradeValue", gradeValue);

        //优惠劵
        ServiceResult<Integer> couResult = couponService.countCouponUserByMemberId(member.getId());
        map.put("couponNum", couResult.getResult());

        return "front/member/account/personalfile";
    }

    /**
     * 个人资料提交
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/saveinfo.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> personalfileSumbit(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   Member membervo) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<Member> memberResult = memberService.getMemberById(sessionMember.getId());
        if (!memberResult.getSuccess()) {
            return new HttpJsonResult<Member>(memberResult.getMessage());
        }
        Member memberDb = memberResult.getResult();
        // 资料保存
        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setBirthday(membervo.getBirthday());
        memberNew.setGender(membervo.getGender());
        memberNew.setPhone(membervo.getPhone());
        if (memberDb.getIsSmsVerify() == 0) {
            memberNew.setMobile(membervo.getMobile());
        }
        if (memberDb.getIsEmailVerify() == 0) {
            memberNew.setEmail(membervo.getEmail());
        }
        memberNew.setQq(membervo.getQq());

        ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Member>(serviceResult.getMessage());
            }
        }
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
                                                                    @RequestParam(value = "newPwd", required = true) String newPwd) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = memberService.editPassword(oldPwd, newPwd,
            sessionMember);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        } else {
            jsonResult.setData(true);
        }
        return jsonResult;
    }

    /**
     * 获取手机验证码
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sendsmsverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> sendSmsVerif(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              String mobile) throws Exception {
        if (StringUtil.isEmpty(mobile)) {
            return new HttpJsonResult<Boolean>("请输入手机号码！");
        }
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        int i = RandomUtils.nextInt(1000, 9999);

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setMobile(mobile);
        memberNew.setSmsVerifyCode(String.valueOf(i));

//        try {
//            this.isCanVerifySms(mobile);
//        } catch (BusinessException e) {
//            return new HttpJsonResult<Boolean>(e.getMessage());
//        }

        ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
             return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }

        SendSmsResponse sendSmsResponse = SmsUtil.sendSms(mobile,String.valueOf(i));
        if(sendSmsResponse.getMessage().equals("OK")){
            jsonResult.setMessage("验证码发送成功！");
        }else {
            jsonResult.setMessage("验证码发送失败！");
        }
        return jsonResult;
    }

    /**
     * 解除手机绑定
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/unsmsverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> unSmsVerif(HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setIsSmsVerify(0);
        memberNew.setMobile("");
        ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 手机绑定
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/smsverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> smsVerif(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          String verif) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<Member> serviceResult = memberService.getMemberById(sessionMember.getId());
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        Member member = serviceResult.getResult();
        if (member == null) {
            return new HttpJsonResult<Boolean>("会员信息获取失败，请重试！");
        }

        if (StringUtil.isEmpty(verif)) {
            return new HttpJsonResult<Boolean>("验证码不能为空！");
        }

        if (!verif.equals(member.getSmsVerifyCode())) {
            return new HttpJsonResult<Boolean>("验证码输入错误，请重试！");
        }

        try {
            this.isCanVerifySms(member.getMobile());
        } catch (BusinessException e) {
            return new HttpJsonResult<Boolean>(e.getMessage());
        }

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setIsSmsVerify(1);

        ServiceResult<Boolean> updateServiceResult = memberService.updateMember(memberNew);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!updateServiceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(updateServiceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 手机号是否已经被绑定
     * @param mobile
     */
    private void isCanVerifySms(String mobile) {

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_mobile", mobile);
        queryMap.put("q_isSmsVerify", Member.IS_SMS_VERIFY_1 + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<Member>> membersResult = memberService.getMembers(feignUtil);
        if (!membersResult.getSuccess()) {
            throw new BusinessException(membersResult.getMessage());
        }
        if (membersResult.getResult() != null && membersResult.getResult().size() > 0) {
            throw new BusinessException("对不起，该手机号码已经被绑定过了！");
        }
    }

    /**
     * 邮箱是否已经被绑定
     * @param mobile
     */
    private void isCanVerifyEmail(String email) {
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_email", email);
        queryMap.put("q_isEmailVerify", Member.IS_EMAIL_VERIFY_1 + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<Member>> membersResult = memberService.getMembers(feignUtil);
        if (!membersResult.getSuccess()) {
            throw new BusinessException(membersResult.getMessage());
        }
        if (membersResult.getResult() != null && membersResult.getResult().size() > 0) {
            throw new BusinessException("对不起，该邮箱已经被绑定过了！");
        }
    }

    /**
     * 邮箱绑定
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sendemailverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> sendEmailVerif(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                String email) throws Exception {
        if (StringUtil.isEmpty(email)) {
            return new HttpJsonResult<Boolean>("邮箱地址不能为空！");
        }

        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        int i = RandomUtils.nextInt(1000, 9999);

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        // 邮箱地址加密
        memberNew.setEmail(email);
        memberNew.setEmailVerifyCode(String.valueOf(i));

        try {
            this.isCanVerifyEmail(email);
        } catch (BusinessException e) {
            return new HttpJsonResult<Boolean>(e.getMessage());
        }

        ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }

        // 邮件发送服务，需要客户提供邮箱地址及密码
        String encode = URLEncoder.encode(String.valueOf(i), "GBK");
        SendMailBy163 sendMail = new SendMailBy163();
        sendMail.sendMail(email, "邮箱验证",
            "<a href="+domainUrlUtil.getUrlResources()+"/member/emailverif.html?verif="+encode+">幸福易商城点击认证邮箱</a>");
        return jsonResult;
    }

    /**
     * 邮件绑定
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/emailverif.html", method = { RequestMethod.GET })
    public String emailVerif(HttpServletRequest request, HttpServletResponse response, ModelMap map,
                             String verif) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<Member> serviceResult = memberService.getMemberById(sessionMember.getId());
        if (!serviceResult.getSuccess()) {
            map.put("sucess", "false");
            map.put("message", serviceResult.getMessage());
            return "front/member/account/emailverif";
        }
        Member member = serviceResult.getResult();
        if (member == null) {
            map.put("sucess", "false");
            map.put("message", "用户信息获取失败，请重试！");
            return "front/member/account/emailverif";
        }

        if (StringUtil.isEmpty(verif)) {
            map.put("sucess", "false");
            map.put("message", "验证码不能为空！");
            return "front/member/account/emailverif";
        }

        if (!verif.equals(member.getEmailVerifyCode())) {
            map.put("sucess", "false");
            map.put("message", "验证码输入错误，请重试！");
            return "front/member/account/emailverif";
        }

        try {
            this.isCanVerifyEmail(member.getEmail());
        } catch (BusinessException e) {
            map.put("sucess", "false");
            map.put("message", e.getMessage());
            return "front/member/account/emailverif";
        }

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setIsEmailVerify(1);

        ServiceResult<Boolean> updateServiceResult = memberService.updateMember(memberNew);
        if (!updateServiceResult.getSuccess()) {
            map.put("sucess", "false");
            map.put("message", updateServiceResult.getMessage());
            return "front/member/account/emailverif";
        }
        map.put("sucess", "true");
        return "front/member/account/emailverif";
    }

    /**
     * 解除邮箱绑定
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/unemailverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> unEmailVerif(HttpServletRequest request,
                                                              HttpServletResponse response) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setIsEmailVerify(0);
        memberNew.setEmail("");

        ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return jsonResult;
    }
}
