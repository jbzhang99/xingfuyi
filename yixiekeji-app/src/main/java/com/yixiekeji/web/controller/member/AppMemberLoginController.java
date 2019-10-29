package com.yixiekeji.web.controller.member;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yixiekeji.core.*;
import com.yixiekeji.core.sms.util.SmsUtil;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberWxsign;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.entity.sale.SaleOrder;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.service.member.IMemberCollectionProductService;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.member.IMemberWxsignService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.sale.ISaleMemberService;
import com.yixiekeji.service.sale.ISaleOrderService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class AppMemberLoginController extends BaseController {

    @Resource
    private IMemberService       memberService;
    @Resource
    private IOrdersService       ordersService;
    @Resource
    private IMemberWxsignService memberWxsignService;
    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private ICouponService couponService;
    @Resource
    private IActSingleService actSingleService;
    @Resource
    private IActFullService actFullService;
    @Resource
    private IMemberCollectionProductService memberCollectionProductService;
    @Resource
    private IMemberCollectionSellerService memberCollectionSellerService;
    @Resource
    private IAnalysisService analysisService;
    @Resource
    private ISaleOrderService saleOrderService;
    @Resource
    private ISaleMemberService saleMemberService;

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
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "app-dologin.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> loginSumbit(HttpServletRequest request,
                                                            HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String ip = request.getParameter("ip");
        // 登录验证
        ServiceResult<Member> serviceResult = memberService.memberLogin(userName, password, ip,
                ConstantsEJS.SOURCE_3_ANDROID);

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
        member.setLoginSource(ConstantsEJS.YES_NO_1);
        // 登录送经验值积分
        memberService.memberLoginSendValue(member.getId(), member.getName());
        // 存入session
        WebFrontSession.putMemberSession(request, member);

        jsonResult.setData(member);

        // 登录完成后把member和openid绑定
        String openid = (String) WebFrontSession.getObjFromSession(request, "openid");
        if (!StringUtil.isEmpty(openid)) {
            MemberWxsign memberWxsign = new MemberWxsign();
            memberWxsign.setMemberId(member.getId());
            memberWxsign.setOpenid(openid);
            memberWxsignService.saveMemberWxsign(memberWxsign);
        }

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
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "isuserlogin.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> isUserLogin(HttpServletRequest request,
                                                             HttpServletResponse response) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member loginedUser = WebFrontSession.getLoginedUser(request,response);
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
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getloginuser.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Member> getLoginUser(HttpServletRequest request,
                                                             HttpServletResponse response) {
        HttpJsonResult<Member> jsonResult = new HttpJsonResult<Member>();
        Member loginedUser = WebFrontSession.getLoginedUser(request,response);
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
     * 忘记密码发送短信
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-doforgetpassword.html", method = { RequestMethod.POST })
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

        // 发送短信的方法
        SendSmsResponse sendSmsResponse = SmsUtil.sendSms(mobile,newPwd);
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
    @RequestMapping(value = "/member/app-index.html", method = { RequestMethod.GET })
    @ResponseBody
    public  HttpJsonResult<Map<String, Object>> memberIndex(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> dataMap,
                                                             Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        // 获取member对象
        ServiceResult<Member> result = memberService.getMemberById(memberId);
        dataMap.put("member", result.getResult());

        //待支付订单数
//        ServiceResult<Integer> numResult = ordersService.getOrderNumByMIdAndState(memberId,
//                Orders.ORDER_STATE_1);
//        dataMap.put("toBepaidOrders", numResult.getResult());
//        //待收货订单数
//        numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_4);
//        dataMap.put("toBeReceivedOrders", numResult.getResult());
        //        //待评价订单数
        //        numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_5);
        //        dataMap.put("toBeCommentedOrders", numResult.getResult());

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * @Description: 用户登录
     * @Author: mofan
     * @Date: 2019/10/10
     */
    @PostMapping(value = "/app-dologin")
    public @ResponseBody HttpJsonResult<Member> dologin(HttpServletRequest request,
                                                        HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        String ip = WebUtil.getIpAddr(request);
        // 登录验证
        ServiceResult<Member> serviceResult = memberService.memberLogins(mobile, password, ip,
                ConstantsEJS.SOURCE_3_ANDROID);

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
        member.setLoginSource(ConstantsEJS.YES_NO_1);
        // 登录送经验值积分
        memberService.memberLoginSendValue(member.getId(), member.getName());
        // 存入session
        WebFrontSession.putMemberSession(request, member);

        jsonResult.setData(member);

        // 登录完成后把member和openid绑定
        String openid = (String) WebFrontSession.getObjFromSession(request, "openid");
        if (!StringUtil.isEmpty(openid)) {
            MemberWxsign memberWxsign = new MemberWxsign();
            memberWxsign.setMemberId(member.getId());
            memberWxsign.setOpenid(openid);
            memberWxsignService.saveMemberWxsign(memberWxsign);
        }

        return jsonResult;
    }

    /**
     * @Description: 根据手机号是否存在
     * @Author: mofan
     * @Date: 2019/10/9
     */
    @GetMapping(value = "/app-checkMobileByForgetPassWord")
    @ResponseBody
    public ServiceResult checkMobileByForgetPassWord(@RequestParam(value = "mobile",required = true) String mobile){
        if(mobile == null || mobile == ""){
            return new ServiceResult(false,StatusCode.ERROR,"手机号不能为空");
        }
        Member bool = memberService.checkMobile(mobile);
        if(bool != null){
            return new ServiceResult(true,StatusCode.OK,mobile,"检验通过!");
        }
        return new ServiceResult(false,StatusCode.ERROR,"手机号不存在!");
    }

    /**
     * @Description: 修改密码
     * @Author: mofan
     * @Date: 2019/10/10
     */
    @GetMapping(value = "/app-updatePassword")
    @ResponseBody
    public ServiceResult updatePassword(@RequestParam(value = "mobile") String mobile,
                                        @RequestParam(value = "password") String password,
                                        @RequestParam(value = "smsVerifyCode") String smsVerifyCode,HttpServletRequest request){
        if(mobile == null || mobile == ""){
            return new ServiceResult(false,StatusCode.ERROR,"手机号不能为空");
        }

        HttpSession session = request.getSession();
        //校验验证码是否过期
        long time = session.getAttribute("forget_exp_time") != null
                ? (long) session.getAttribute("forget_exp_time") : 0l;
        long now = new Date().getTime();
        long diff = (((now - time) / 1000) / 60);
        if (diff > ConstantsEJS.SMS_MAX_WAIT_TIME) {
            session.removeAttribute("forget_smsCode");
            return new ServiceResult(false,StatusCode.ERROR,"验证码已过期,请重新获取!");
        }

        Object forget_smsCode = session.getAttribute("forget_smsCode");
        if (forget_smsCode == null || !forget_smsCode.equals(smsVerifyCode)) {
            return new ServiceResult(false,StatusCode.ERROR,"短信验证码校验失败!");
        }else {
            Member member = memberService.checkMobile(mobile);
            if(member != null){
                int i = memberService.updatePassword(member.getId(),Md5.getMd5String(password));
                if(i>0){
                    return new ServiceResult(true,StatusCode.OK,"密码修改成功!");
                }else{
                    return new ServiceResult(false,StatusCode.ERROR,"密码修改失败!");
                }
            }
            return new ServiceResult(false,StatusCode.ERROR,"手机号不存在!");
        }
    }

    /**
    * @Description: 获取我的页面信息
    * @Author: mofan
    * @Date: 2019/10/12
    */
    @GetMapping(value = "/app-getMemberData")
    @ResponseBody
    public ServiceResult getMemberData(@RequestParam("memberId") Integer memberId){
        Map<String, Object> dataMap = new HashMap<>();
        if( memberId != null && memberId != 0){
            //获取会员信息
            ServiceResult<Member> member = memberService.getMemberById(memberId);
            ServiceResult<SaleMember> saleMemberByMemberId = saleMemberService.getSaleMemberByMemberId(memberId);
            if(saleMemberByMemberId.getResult() != null){
                SaleMember saleMember = saleMemberByMemberId.getResult();
                dataMap.put("authState",saleMember.getState());
            }
            if(member.getResult() != null){
                member.getResult().setPassword("");
            }
            dataMap.put("member",member.getResult());
            //获取商品收藏数量
            Integer productCount = memberCollectionProductService.getCollectionProductCount(memberId);
            dataMap.put("productCount",productCount);
            //获取店铺收藏数量
            Integer sellerCount = memberCollectionSellerService.getCollectionSeller(memberId);
            dataMap.put("sellerCount",sellerCount);
            //浏览记录数
            Integer browseCount = analysisService.getProductBrowseCount(memberId);
            dataMap.put("browseCount",browseCount);
            //预计收入
            ServiceResult<BigDecimal> resultSum1 = saleOrderService.sumSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_1, memberId);
            if (resultSum1.getSuccess()) {
                dataMap.put("sumState1", resultSum1.getResult());
            }
            //待付款订单数
            ServiceResult<Integer> numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_1);
            dataMap.put("toBepaidOrders", numResult.getResult());
            //待发货订单数
            numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_3);
            dataMap.put("toBeSubstituteOrders", numResult.getResult());
            //待收货订单数
            numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_4);
            dataMap.put("toBeReceivedOrders", numResult.getResult());
            //待评价订单数
            numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_5);
            dataMap.put("toBeCommentedOrders", numResult.getResult());
            //获取优惠卷
            ServiceResult<Integer> couponServiceResult = couponService.countCouponUserByMemberId(memberId);
            dataMap.put("couponCount", couponServiceResult.getResult());
            //取销量前10的商品
            Map<String, Integer> mapFull = new HashMap<>();
            Map<String, Integer> mapCoupon = new HashMap<>();
            ServiceResult<List<Product>> serviceResultProduct = productFrontService.getProductMemberByTop(10);
            List<Product> products = serviceResultProduct.getResult();
            for (Product product : products) {
                actAllProduct(mapFull, mapCoupon, product);
            }
            dataMap.put("products", products);
            return new ServiceResult(true,StatusCode.OK,dataMap,"查询成功!");
        }else {
            return new ServiceResult(false,StatusCode.ERROR,"未登录,请先登录!");
        }
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

    /** 
    * @Description: 根据推广码获取推广用户列表
    * @Author: mofan 
    * @Date: 2019/10/21 
    */ 
    @GetMapping(value = "/app-getPromotionUser")
    @ResponseBody
    public ServiceResult getPromotionUser( @RequestParam("saleCode") String saleCode){
        List<Member> memberList = memberService.getPromotionUser(saleCode);
        if(memberList.isEmpty() || memberList == null){
            return new ServiceResult(false,StatusCode.ERROR,"获取推广用户列表失败！");
        }
        return new ServiceResult(true,StatusCode.OK,memberList,"获取推广用户列表成功！");
    }
    
    /** 
    * @Description: 根据id获取会员信息
    * @Author: mofan 
    * @Date: 2019/10/21 
    */ 
    @GetMapping(value = "/app-getUserInfo")
    @ResponseBody
    public ServiceResult getUserInfo(@RequestParam("memberId") Integer memberId){
        ServiceResult<Member> member = memberService.getMemberById(memberId);
        if(member.getResult() != null){
            return new ServiceResult(true,StatusCode.OK,member.getResult(),"获取用户信息成功！");
        }
        return new ServiceResult(false,StatusCode.ERROR,"获取用户信息失败！");
    }

}
