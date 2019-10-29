package com.yixiekeji.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.RandomUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.member.IMemberWxsignService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.SystemUtil;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 会员登录controller
 * 
 * @Filename: MemberLoginController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class MemberLoginController extends BaseController {

    @Resource
    private IMemberService       memberService;

    @Resource
    private IOrdersService       ordersService;

    @Resource
    private IMemberWxsignService memberWxsignService;
    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private ICouponService       couponService;
    @Resource
    private IActSingleService    actSingleService;
    @Resource
    private IActFullService      actFullService;

    /**
     * 跳转到登录页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/login.html", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> stack) {
        String toUrl = request.getParameter("toUrl");
        stack.put("toUrl", toUrl);
        return "h5/member/login";
    }

    /**
     * 登录
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "dologin.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> loginSumbit(HttpServletRequest request,
                                                            HttpServletResponse response) {

        String ip = WebUtil.getIpAddr(request);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        //        String verifyCode = request.getParameter("verifyCode");
        //获得session中的验证码
        //        String verify_number = WebFrontSession.getVerifyNumber(request);

        //        if (verify_number == null || !verify_number.equalsIgnoreCase(verifyCode)) {
        //            return new HttpJsonResult<Member>("验证码不正确！");
        //        }
        // 登录验证
        ServiceResult<Member> serviceResult = memberService.memberLogin(userName, password, ip,
            SystemUtil.getSysVersion(request));

        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Member>(serviceResult.getMessage());
                return jsonResult;
            }
        }

        Member member = serviceResult.getResult();
        String token = WebFrontSession.genToken();
        member.setToken(token);
        // 登录送经验值积分
        memberService.memberLoginSendValue(member.getId(), member.getName());

        // 保存token
        Member memberNew = new Member();
        memberNew.setId(member.getId());
        memberNew.setToken(token);
        memberService.updateMember(memberNew);

        jsonResult.setData(member);
        return jsonResult;
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "logout.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> logout(HttpServletRequest request,
                                                        HttpServletResponse response) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("memberSession");
        }
        return jsonResult;
    }

    /**
     * 判断登录
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "isuserlogin.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> isUserLogin(HttpServletRequest request,
                                                             HttpServletResponse response) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member loginedUser = WebFrontSession.getLoginedUser(request);
        if (loginedUser == null) {
            jsonResult.setData(false);
        } else {
            jsonResult.setData(true);
        }
        return jsonResult;
    }

    /**
     * 获取登录用户（如果未登录返回null对象）
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "getloginuser.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> getLoginUser(HttpServletRequest request,
                                                             HttpServletResponse response) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        Member loginedUser = WebFrontSession.getLoginedUser(request);
        if (loginedUser == null) {
            jsonResult.setData(null);
        } else {
            jsonResult.setData(loginedUser);
        }
        return jsonResult;
    }

    //    /**
    //     * 跳转到忘记密码页面
    //     * @param request
    //     * @param response
    //     * @param stack
    //     * @return
    //     */
    //    @RequestMapping(value = "/forgetpassword.html", method = { RequestMethod.GET })
    //    public String forgetPassword(HttpServletRequest request, HttpServletResponse response,
    //                                 Map<String, Object> stack) {
    //        return "h5/member/person/forgetpassword";
    //    }

    /**
     * 忘记密码发送邮件
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doforgetpassword.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> doForgetpassword(HttpServletRequest request,
                                                                  HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (StringUtil.isEmpty(name)) {
            return new HttpJsonResult<Boolean>("用户名不能为空！");
        }
        String mobile = request.getParameter("mobile");
        if (StringUtil.isEmpty(mobile)) {
            return new HttpJsonResult<Boolean>("手机号码不能为空！");
        }

        // 根据name取会员信息
        ServiceResult<List<Member>> memberResult = memberService.getMemberByName(name);
        if (!memberResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(memberResult.getMessage());
        }
        if (memberResult.getResult() == null || memberResult.getResult().size() == 0) {
            return new HttpJsonResult<Boolean>("您输入的用户名不存在，请重试！");
        }
        Member member = memberResult.getResult().get(0);

        if (member.getIsSmsVerify() == null
            || member.getIsSmsVerify().equals(Member.IS_SMS_VERIFY_0)
            || StringUtil.isEmpty(member.getMobile(), true)) {
            return new HttpJsonResult<Boolean>("对不起，您没有绑定手机！");
        }

        if (!mobile.equals(member.getMobile())) {
            return new HttpJsonResult<Boolean>("对不起，您输入的手机号码与您绑定的号码不一致，请输入正确的手机号码！");
        }

        String newPwd = RandomUtil.randomNumber(6);

        Member memberNew = new Member();
        memberNew.setId(member.getId());
        memberNew.setPassword(Md5.getMd5String(newPwd));

        ServiceResult<Boolean> updateMember = memberService.updateMember(memberNew);
        if (!updateMember.getSuccess()) {
            return new HttpJsonResult<Boolean>(updateMember.getMessage());
        }

        // 发送短信的方法，需要运营商提供请求URL，不要删除这段代码
        /*SendSms send = new SendSms();
        Boolean sendSms = send.sendSms(mobile, "感谢您使用密码找回功能，您的新密码是：" + newPwd + "，请及时更改您的密码。");
        if (!sendSms) {
            jsonResult = new HttpJsonResult<Boolean>("短信发送异常，请稍后重试！");
        }*/

        return new HttpJsonResult<Boolean>();
    }

    /**
     * 跳转到用户中心页面
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/member/index.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> memberIndex(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        if (sessionMember == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        Integer memberId = sessionMember.getId();

        // 获取member对象
        ServiceResult<Member> result = memberService.getMemberById(memberId);
        dataMap.put("member", result.getResult());

        //待支付订单数
        ServiceResult<Integer> numResult = ordersService.getOrderNumByMIdAndState(memberId,
            Orders.ORDER_STATE_1);
        dataMap.put("toBepaidOrders", numResult.getResult());
        //待收货订单数
        numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_4);
        dataMap.put("toBeReceivedOrders", numResult.getResult());
        //        //待评价订单数
        //        numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_5);
        //        dataMap.put("toBeCommentedOrders", numResult.getResult());
        //取销量前10的商品
        Map<String, Integer> mapFull = new HashMap<String, Integer>();
        Map<String, Integer> mapCoupon = new HashMap<String, Integer>();
        ServiceResult<List<Product>> serviceResultProduct = productFrontService
            .getProductMemberByTop(10);
        List<Product> products = serviceResultProduct.getResult();
        for (Product product : products) {
            actAllProduct(mapFull, mapCoupon, product);
        }
        dataMap.put("products", products);
        jsonResult.setData(dataMap);
        return jsonResult;
    }
    /**
     * 商品活动的处理
     * @param mapFull
     * @param mapCoupon
     * @param product
     */
    private void actAllProduct(Map<String, Integer> mapFull, Map<String, Integer> mapCoupon,
                               Product product) {
       
            product.setSpecial(0);
        

        //判断单品立减
        ServiceResult<ActSingle> singleResult = actSingleService
            .getEffectiveActSingle(product.getSellerId(), ConstantsEJS.CHANNEL_2, product.getId());
        ActSingle actSingle = singleResult.getResult();
        if (actSingle != null) {
            product.setSingle(1);
        } else {
            product.setSingle(0);
        }

        //判断订单满减
        Integer sellerId = mapFull.get(product.getSellerId().toString());
        if (sellerId != null) {
            product.setFull(1);
        } else {
            ServiceResult<ActFull> fullResult = actFullService
                .getEffectiveActFull(product.getSellerId(), ConstantsEJS.CHANNEL_2);
            ActFull actFull = fullResult.getResult();
            if (actFull != null) {
                product.setFull(1);
                mapFull.put(product.getSellerId().toString(), product.getSellerId());
            } else {
                product.setFull(0);
            }
        }

        //判断优惠券
        Integer sellerIdCoupon = mapCoupon.get(product.getSellerId().toString());
        if (sellerIdCoupon != null) {
            product.setCoupon(1);
        } else {
            ServiceResult<List<Coupon>> couponResult = couponService
                .getEffectiveCoupon(product.getSellerId(), ConstantsEJS.CHANNEL_2);
            List<Coupon> listCoupon = couponResult.getResult();
            if (listCoupon != null && listCoupon.size() > 0) {
                product.setCoupon(1);
                mapCoupon.put(product.getSellerId().toString(), product.getSellerId());
            } else {
                product.setCoupon(0);
            }
        }
    }
}
