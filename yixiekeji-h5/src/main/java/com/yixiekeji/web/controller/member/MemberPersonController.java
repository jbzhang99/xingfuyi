package com.yixiekeji.web.controller.member;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yixiekeji.core.*;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.email.SendMailBy163;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.core.sms.util.SmsUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.seller.ISellerComplaintService;
import com.yixiekeji.service.sms.ISenderService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心：资料管理
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberPersonController extends BaseController {

    @Resource
    private IMemberService memberService;
    @Resource
    private ISenderService senderService;
    @Resource
    private ISellerComplaintService sellerComplaintService;
    @Resource
    private DomainUrlUtil  domainUrlUtil;

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
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        map.put("member", member);

        return "h5/member/person/personfile";
    }

    /**
     * 个人资料提交
     * @param request
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveinfo.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> personalfileSumbit(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   Member membervo) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request);

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
     * 跳转到关于幸福易页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/aboutXfy.html", method = { RequestMethod.GET })
    public String aboutXfy(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> stack) {

        return "h5/member/aboutXfy";
    }




    /**
     * 获取手机验证码
     * @param request
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendsmsverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> sendSmsVerif(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              String mobile) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        try {
            if (StringUtil.isEmpty(mobile)) {
                return new HttpJsonResult<Boolean>("请输入手机号码！");
            }
            Member sessionMember = WebFrontSession.getLoginedUser(request);

            int i = RandomUtils.nextInt(1000, 9999);

            Member memberNew = new Member();
            memberNew.setId(sessionMember.getId());
            memberNew.setMobile(mobile);
            memberNew.setSmsVerifyCode(String.valueOf(i));

//            try {
//                this.isCanVerifySms(mobile);
//            } catch (BusinessException e) {
//                return new HttpJsonResult<Boolean>(e.getMessage());
//            }

            ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
            if (!serviceResult.getSuccess()) {
                return new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
            SendSmsResponse sendSmsResponse = SmsUtil.sendSms(mobile,String.valueOf(i));
            if(sendSmsResponse.getMessage().equals("OK")){
                jsonResult.setMessage("验证码发送成功！");
            }else {
                jsonResult.setMessage("验证码发送失败！");
            }
//            ServletContext context = request.getSession().getServletContext();
//
//            Object exttime = isNull(context.getAttribute("exp_time")) ? 0
//                : context.getAttribute("exp_time");
//            Object count = isNull(context.getAttribute("vc_count")) ? 0
//                : context.getAttribute("vc_count");
            //当天只能获取5次
//            if (isCur((long) exttime) && ((Integer) count) >= EjavashopConfig.SMS_MAX_GIVEN_NUM) {
//                return new HttpJsonResult<Boolean>("今日获取验证码次数过多,请明日再试");
//            }

//            ServiceResult<Object> result = senderService.sendVerifyCode(mobile);
//            if (result.getSuccess()) {
//                Gson gson = new Gson();
//                VerifyCodeResult vcr = gson.fromJson((String) result.getResult(),
//                    VerifyCodeResult.class);
//
//                //将信息放入servletcontext
//                context.setAttribute("vc_count", context.getAttribute("vc_count") == null ? 0
//                    : ((Integer) context.getAttribute("vc_count")) + 1);
//
//                memberNew.setSmsVerifyCode(vcr.getVerifyCode());
//                ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
//                if (!serviceResult.getSuccess()) {
//                    jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
//                }
//            }
        } catch (Exception e) {
            if (e instanceof BusinessException)
                return new HttpJsonResult<Boolean>(e.getMessage());
            else
                e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 是否当天
     * @return
     */
    private boolean isCur(long time) {
        long now = new Date().getTime();
        long diff = (now - time) / (1000 * 60 * 60 * 24);
        return diff == 0;
    }

    /**
     * 解除手机绑定
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unsmsverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> unSmsVerif(HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request);

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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/smsverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> smsVerif(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          String verif) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request);

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
        memberNew.setMobile(member.getMobile());

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
     * @param
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

        Member sessionMember = WebFrontSession.getLoginedUser(request);

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
        Member sessionMember = WebFrontSession.getLoginedUser(request);

        ServiceResult<Member> serviceResult = memberService.getMemberById(sessionMember.getId());
        if (!serviceResult.getSuccess()) {
            map.put("sucess", "false");
            map.put("message", serviceResult.getMessage());
            return "h5/member/person/emailverif";
        }
        Member member = serviceResult.getResult();
        if (member == null) {
            map.put("sucess", "false");
            map.put("message", "用户信息获取失败，请重试！");
            return "h5/member/person/emailverif";
        }

        if (StringUtil.isEmpty(verif)) {
            map.put("sucess", "false");
            map.put("message", "验证码不能为空！");
            return "h5/member/person/emailverif";
        }

        if (!verif.equals(member.getEmailVerifyCode())) {
            map.put("sucess", "false");
            map.put("message", "验证码输入错误，请重试！");
            return "h5/member/person/emailverif";
        }

        try {
            this.isCanVerifyEmail(member.getEmail());
        } catch (BusinessException e) {
            map.put("sucess", "false");
            map.put("message", e.getMessage());
            return "h5/member/person/emailverif";
        }

        Member memberNew = new Member();
        memberNew.setId(sessionMember.getId());
        memberNew.setIsEmailVerify(1);

        ServiceResult<Boolean> updateServiceResult = memberService.updateMember(memberNew);
        if (!updateServiceResult.getSuccess()) {
            map.put("sucess", "false");
            map.put("message", updateServiceResult.getMessage());
            return "h5/member/person/emailverif";
        }
        map.put("sucess", "true");
        return "h5/member/person/emailverif";
    }

    /**
     * 解除邮箱绑定
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unemailverif.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> unEmailVerif(HttpServletRequest request,
                                                              HttpServletResponse response) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request);

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

    /**
     * @Description: 修改手机号
     * @Author: mofan
     * @Date: 2019/10/18
     */
    @PostMapping(value = "/BoundOrNotByMobile")
    @ResponseBody
    public ServiceResult BoundOrNotByMobile(HttpServletRequest request,
                                            @RequestParam("memberId") Integer memberId,
                                            @RequestParam("mobile")String mobile,
                                            @RequestParam("smsVerifyCode")String smsVerifyCode) {
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_mobile", mobile);
        queryMap.put("q_isSmsVerify", Member.IS_SMS_VERIFY_1 + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);
        ServiceResult<List<Member>> membersResult = memberService.getMembers(feignUtil);
        if (membersResult.getResult() != null && membersResult.getResult().size() > 0) {
            HttpSession session = request.getSession();
            //校验验证码
            Object forget_smsCode = session.getAttribute("_smsCode");
            if(forget_smsCode == null || !forget_smsCode.equals(smsVerifyCode)){
                return new ServiceResult(false, StatusCode.ERROR,"短信验证码不存在!");
            }
            //修改手机号
            boolean serviceResult = memberService.updateMobileById(memberId,mobile);
            if(serviceResult){
                return new ServiceResult(true,StatusCode.OK,"修改成功!");
            }
            return new ServiceResult(false,StatusCode.ERROR,"修改失败!");
        }else {
            return new ServiceResult(false,StatusCode.ERROR,"该手机号未绑定!");
        }
    }

    /**
     * @Description: 个人资料修改
     * @Author: mofan
     * @Date: 2019/10/18
     */
    @RequestMapping(value = "/updatePerson", method = {RequestMethod.POST})
    @ResponseBody
    public ServiceResult updatePerson(HttpServletRequest request,
                                      @RequestParam("birthday") Date birthday,
                                      @RequestParam("gender")Integer gender,
                                      @RequestParam("nickName")String nickName,
                                      @RequestParam("mobile")String mobile,
                                      @RequestParam("headPortrait")String headPortrait,
                                      @RequestParam("memberId")Integer memberId,
                                      @RequestParam("smsVerify")String smsVerify) throws Exception {

        ServiceResult<Member> memberResult = memberService.getMemberById(memberId);
        if (!memberResult.getSuccess()) {
            return new ServiceResult(false, StatusCode.ERROR, "用户不存在!");
        }

        HttpSession session = request.getSession();
        Object forget_smsCode = session.getAttribute("_smsCode");
        if(forget_smsCode == null || !forget_smsCode.equals(smsVerify)){
            return new ServiceResult(false,StatusCode.ERROR,"短信验证码不存在!");
        }

        Member memberDb = memberResult.getResult();
        if(!memberDb.getMobile().equals(mobile) && memberDb.getIsSmsVerify() != 1){
            return new ServiceResult(false,StatusCode.ERROR,"该手机号未绑定!");
        }

        // 资料保存
        Member memberNew = new Member();
        memberNew.setId(memberId);
        memberNew.setBirthday(birthday);
        memberNew.setGender(gender);
        memberNew.setNickName(nickName);
        memberNew.setHeadPortrait(headPortrait);
        memberNew.setMobile(mobile);
        ServiceResult<Boolean> serviceResult = memberService.updateMember(memberNew);
        if(serviceResult.getResult()){
            return new ServiceResult(true,StatusCode.OK,"修改成功!");
        }
        return  new ServiceResult(false,StatusCode.ERROR,"修改失败!");
    }

    /**
     * @Description: 修改密码
     * @Author: mofan
     * @Date: 2019/10/18
     */
    @PostMapping(value = "/updatePwdById")
    @ResponseBody
    public ServiceResult updatePwdById(  @RequestParam("memberId") Integer memberId,
                                         @RequestParam("password")String password,
                                         @RequestParam("newPassword")String newPassword){
        ServiceResult<Member> memberResult = memberService.getMemberById(memberId);
        Member member = memberResult.getResult();
        if(member != null && member.getPassword().equals(Md5.getMd5String(password))){
            member.setPassword(Md5.getMd5String(newPassword));
            ServiceResult<Boolean> booleanServiceResult = memberService.updateMember(member);
            if(booleanServiceResult.getResult()){
                return new ServiceResult(true,StatusCode.OK,"修改密码成功!");
            }
            return new ServiceResult(false,StatusCode.ERROR,"修改密码失败!");
        }
        return new ServiceResult(false,StatusCode.ERROR,"原密码不正确!");
    }

    /**
     * @Description: 客服投诉
     * @Author: mofan
     * @Date: 2019/10/21
     */
    @PostMapping(value = "/addSellerComplaint")
    @ResponseBody
    public ServiceResult addSellerComplaint(
            @RequestParam("memberId") Integer memberId,
            @RequestParam("questionType")Integer questionType,
            @RequestParam("content")String content,
            @RequestParam("image")String image){
        ServiceResult<Member> member = memberService.getMemberById(memberId);
        SellerComplaint sellerComplaint = new SellerComplaint();
        sellerComplaint.setUserId(memberId);
        sellerComplaint.setUserName(member.getResult().getName());
        sellerComplaint.setContent(content);
        sellerComplaint.setQuestionType(questionType);
        sellerComplaint.setImage(image);
        Integer serviceResult = sellerComplaintService.addSellerComplaint(sellerComplaint);
        if(serviceResult > 0){
            return new ServiceResult(true,StatusCode.OK,"保存成功!");
        }
        return new ServiceResult(false,StatusCode.ERROR,"保存失败!");
    }

}
