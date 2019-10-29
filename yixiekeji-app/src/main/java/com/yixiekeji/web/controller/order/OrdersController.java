package com.yixiekeji.web.controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.HttpJsonResultForAjax;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.entity.operate.Config;
import com.yixiekeji.entity.order.Invoice;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.service.cart.ICartService;
import com.yixiekeji.service.member.IInvoiceService;
import com.yixiekeji.service.member.IMemberAddressService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.operate.IConfigService;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.promotion.IActIntegralService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.seller.ISellerTransportService;
import com.yixiekeji.util.FeignProjectUtil;
import com.yixiekeji.vo.cart.CartInfoVO;
import com.yixiekeji.vo.member.FrontCheckPwdVO;
import com.yixiekeji.vo.order.OrderCommitVO;
import com.yixiekeji.vo.order.OrderCouponVO;
import com.yixiekeji.vo.order.OrderSuccessVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.csrf.CsrfTokenManager;
import com.yixiekeji.web.util.CommUtil;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 购物流程-订单<br>
 * 本controller中得请求都需要登录才能访问
 * 
 * @Filename: FrontOrdersController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class OrdersController extends BaseController {

    @Resource
    private IOrdersService          ordersService;
    @Resource
    private IOrdersProductService   ordersProductService;
    @Resource
    private ICartService            cartService;
    @Resource
    private IMemberAddressService   memberAddressService;
    @Resource
    private IInvoiceService         invoiceService;
    @Resource
    private IMemberService          memberService;
    @Resource
    private IConfigService          configservice;
    @Resource
    private ICouponService          couponService;
    @Resource
    private ISellerTransportService sellerTransportService;
    @Resource
    private ISellerService          sellerService;
    @Resource
    private IProductFrontService    productFrontService;
    @Resource
    private IProductGoodsService    productGoodsService;
    @Resource
    private IActIntegralService     actIntegralService;

    /**
     * 跳转到提交订单页面 计算总金额,运费、货品小计，按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/info.html", method = { RequestMethod.GET })
    public String toOrderSubmit(HttpServletRequest request, ModelMap map,
                                HttpServletResponse response,Integer id) {
        Member member = WebFrontSession.getLoginedUser(request, response);

        MemberAddress memberAddress = null;

        Integer addressId = ConvertUtil.toInt(request.getParameter("addressId"), 0);
        if (addressId > 0) {
            ServiceResult<MemberAddress> memberAddressResult = memberAddressService
                .getMemberAddressById(addressId);
            memberAddress = memberAddressResult.getResult();
        }

        if (memberAddress == null) {
            // 收货地址信息
            ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
                .getMemberAddressByMId(member.getId());
            // 获取默认收货地址，如果没有取第一个
            if (serviceResult.getSuccess()) {
                List<MemberAddress> addressList = serviceResult.getResult();
                if (addressList != null && addressList.size() > 0) {
                    memberAddress = addressList.get(0);
                    for (MemberAddress address : addressList) {
                        if (address.getState() == MemberAddress.STATE_1) {
                            memberAddress = address;
                            break;
                        }
                    }
                }
            }
        }

        map.put("address", memberAddress);
        // 构建默认值 ，默认在线支付。收货地址为默认地址，发票默认为不开发票
        OrderCommitVO orderCommitVO = new OrderCommitVO();
        orderCommitVO.setInvoiceType("");
        orderCommitVO.setInvoiceTitle("");
        orderCommitVO.setPaymentName(Orders.PAYMENT_NAME_ONLINE);
        orderCommitVO.setPaymentCode(Orders.PAYMENT_CODE_ONLINE);
        map.put("orderCommitVO", orderCommitVO);
        if(id != null){
            updateStatusById(id);
        }
        // 取购物车信息  产品价格 按照商家来区分
        // 查询购物车
        ServiceResult<CartInfoVO> cartServiceResult = cartService.getCartInfoByMId(member.getId(),
            memberAddress==null?new MemberAddress():memberAddress, ConstantsEJS.SOURCE_2_H5, 2);
        map.put("cartInfoVO", cartServiceResult.getResult());

        // 获取发票信息
        ServiceResult<List<Invoice>> invoiceResult = invoiceService.getInvoiceByMId(member.getId());
        map.put("invoiceList", invoiceResult.getResult());

        //取会员余额信息
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());
        if (memberResult.getResult() == null) {
            map.put("info", "会员信息获取失败。");
            return "h5/commons/error";
        }
        map.put("member", memberResult.getResult());

        ServiceResult<Config> configById = configservice.getConfigById(ConstantsEJS.CONFIG_ID);
        if (configById.getResult() != null) {
            Config config = configById.getResult();
            if (config.getIntegralScale() > 0) {
                map.put("config", config);
            }
        }
        return "h5/order/order";
    }

    //修改购物车商品状态
    @RequestMapping(value = "order/updateStatusById", method = { RequestMethod.GET })
    public void updateStatusById(Integer id){
        ServiceResult<Boolean> bool = cartService.updateStatusById(id);
    }
    //    /**
    //     * 根据地址ID计算新的运费信息
    //     * @param request
    //     * @param response
    //     * @param map
    //     * @param addressId
    //     * @return
    //     */
    //    @RequestMapping(value = "order/calculateTransFee.html", method = { RequestMethod.POST })
    //    public @ResponseBody HttpJsonResult<CartInfoVO> orderSubmit(HttpServletRequest request,
    //                                                                HttpServletResponse response,
    //                                                                ModelMap map, Integer addressId) {
    //        Member member = WebFrontSession.getLoginedUser(request,response);
    //        ServiceResult<MemberAddress> memberAddressRlt = memberAddressService
    //            .getMemberAddressById(addressId);
    //        if (!memberAddressRlt.getSuccess() || memberAddressRlt.getResult() == null) {
    //            return new HttpJsonResult<>("收获地址信息获取失败！");
    //        }
    //        // 查询购物车
    //        ServiceResult<CartInfoVO> cartServiceResult = cartService.getCartInfoByMId(member.getId(),
    //            memberAddressRlt.getResult(), ConstantsEJS.SOURCE_2_H5);
    //        if (!cartServiceResult.getSuccess() || cartServiceResult.getResult() == null) {
    //            return new HttpJsonResult<>("计算运费信息失败！");
    //        }
    //        HttpJsonResult<CartInfoVO> jsonResult = new HttpJsonResult<CartInfoVO>();
    //        jsonResult.setData(cartServiceResult.getResult());
    //        return jsonResult;
    //    }

    /**
     * 提交订单 计算总金额 按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommit.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmit(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           OrderCommitVO orderCommitVO,
                                                                           ModelMap map) {
        // 获取优惠券使用信息
        Map<String, OrderCouponVO> sellerCouponMap = new HashMap<String, OrderCouponVO>();
        String useCouponSellerIds = request.getParameter("useCouponSellerIds");
        if (!StringUtil.isEmpty(useCouponSellerIds, true)) {
            String[] split = useCouponSellerIds.split(",");
            for (String sellerIdStr : split) {
                if (!StringUtil.isEmpty(sellerIdStr, true)) {
                    Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);
                    String couponTypeStr = request.getParameter("couponType" + sellerId);
                    Integer couponType = ConvertUtil.toInt(couponTypeStr, 0);
                    String couponSn = request.getParameter("couponSn" + sellerId);
                    String couponPassword = request.getParameter("couponPassword" + sellerId);
                    OrderCouponVO orderCouponVO = new OrderCouponVO();
                    orderCouponVO.setSellerId(sellerId);
                    orderCouponVO.setCouponType(couponType);
                    orderCouponVO.setCouponSn(couponSn);
                    orderCouponVO.setCouponPassword(couponPassword);
                    sellerCouponMap.put(String.valueOf(sellerId), orderCouponVO);
                }
            }
        }
        orderCommitVO.setSellerCouponMap(sellerCouponMap);

        return this.commonSubmit(request, response, orderCommitVO, Orders.ORDER_TYPE_1);
    }

    /**
     * 提交订单方法
     * @param request
     * @param orderCommitVO
     * @param orderType
     * @return
     */
    private HttpJsonResultForAjax<OrderSuccessVO> commonSubmit(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               OrderCommitVO orderCommitVO,
                                                               int orderType) {
        Member member = WebFrontSession.getLoginedUser(request, response);
        orderCommitVO.setMemberId(member.getId());

        if (orderCommitVO.getInvoiceStatus() == null) {
            // 默认不开发票
            orderCommitVO.setInvoiceStatus(Orders.INVOICE_STATUS_0);
        }
        // 设定IP地址
        orderCommitVO.setIp(WebUtil.getIpAddr(request));
        // 设定来源
        orderCommitVO.setSource(ConstantsEJS.SOURCE_2_H5);
        orderCommitVO.setRemark("");

        // 提交订单
        ServiceResult<OrderSuccessVO> serviceResult = null;
        if (orderType == Orders.ORDER_TYPE_1) {
            // 1、普通订单
            serviceResult = ordersService.orderCommit(orderCommitVO);
        } else if (orderType == Orders.ORDER_TYPE_6) {
            // 6、积分换购订单
            serviceResult = ordersService.orderCommitForIntegral(orderCommitVO);
        }

        HttpJsonResultForAjax<OrderSuccessVO> jsonResult = new HttpJsonResultForAjax<OrderSuccessVO>();
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResultForAjax<OrderSuccessVO>(null,
                CsrfTokenManager.getTokenForSession(CsrfTokenManager.getMemkeyFromRequest(request),
                    request.getSession()));
            jsonResult.setMessage(serviceResult.getMessage());
            return jsonResult;
        }

        //订单提交后返回结果
        OrderSuccessVO orderSuccessVO = serviceResult.getResult();
        if (orderSuccessVO.getIsPaid() && orderType != Orders.ORDER_TYPE_6) {
            // 如果已经付过款，则调用下单送积分方法，积分换购订单不再送积分
            for (Orders order : orderSuccessVO.getOrdersList()) {
                memberService.memberOrderSendValue(member.getId(), member.getName(), order.getId());
            }
        }
        //支付随机码 避免重复提交
        String order_session = CommUtil.randomString(32);
        // 存入session，支付时取出后与参数传入的对比，判断是否二次提交
        request.getSession(false).setAttribute("order_session", order_session);
        request.getSession(false).setAttribute("order_success_vo", orderSuccessVO);
        orderSuccessVO.setPaySessionstr(order_session);

        jsonResult.setData(orderSuccessVO);

        return jsonResult;
    }

    /**
     * 跳转到提交订单成功页面 （货到付款）
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "order/success.html", method = { RequestMethod.GET })
    public String toOrderSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> dataMap,
                                 @RequestParam(value = "orderSn", required = true) String orderSn) {

        Member member = WebFrontSession.getLoginedUser(request, response);
        //根据父订单号查询订单信息
        ServiceResult<List<Orders>> serviceResult = ordersService.getOrdersByOrderPsn(orderSn);
        List<Orders> list = serviceResult.getResult();
        if (list != null && list.size() > 0) {
            if (!member.getId().equals(list.get(0).getMemberId())) {
                dataMap.put("info", "您不能查看别人的订单。");
                return "h5/commons/error";
            }
        }
        dataMap.put("orderList", list);

        return "h5/order/ordersuccess";
    }

    /**
     * 跳转到支付页面 （在线支付）
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "order/pay.html", method = { RequestMethod.GET })
    public String toPayfor(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap, String orderSn) {

        if (StringUtil.isEmpty(orderSn, true)) {
            dataMap.put("info", "请选择要支付的订单，谢谢！");
            return "h5/commons/error";
        }
        Member member = WebFrontSession.getLoginedUser(request, response);
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());
        if (memberResult.getResult() == null) {
            dataMap.put("info", "会员信息获取失败。");
            return "h5/commons/error";
        }
        dataMap.put("member", memberResult.getResult());

        ServiceResult<Orders> ordersResult = ordersService.getOrdersBySn(orderSn);
        if (!ordersResult.getSuccess()) {
            dataMap.put("info", "订单信息获取出错，请稍后再试！");
            return "h5/commons/error";
        }
        Orders mainOrder = ordersResult.getResult();

        if (!member.getId().equals(mainOrder.getMemberId())) {
            dataMap.put("info", "您不能查看别人的订单。");
            return "h5/commons/error";
        }

        dataMap.put("orderSn", orderSn);
        dataMap.put("payAmount", mainOrder.getMoneyOrder().subtract(mainOrder.getMoneyPaidBalance())
            .subtract(mainOrder.getMoneyPaidReality()).subtract(mainOrder.getMoneyIntegral()));

        return "h5/order/payselect";

    }

    /**
     * 获取发票信息
     * @param request
     * @param response
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "order/getinvoicebyid.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Invoice> getinvoicebyid(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Integer invoiceId) {

        HttpJsonResult<Invoice> jsonResult = new HttpJsonResult<Invoice>();
        ServiceResult<Invoice> serviceResult = invoiceService.getInvoiceById(invoiceId);

        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Invoice>(serviceResult.getMessage());
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 保存发票抬头
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/saveinvoice.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Integer> saveInvoice(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             Invoice invoice) {

        Member member = WebFrontSession.getLoginedUser(request, response);

        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        serviceResult = invoiceService.saveInvoice(invoice, member.getId());

        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Integer>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 判断 余额支付密码是否正确
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/checkbalancepwd.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<FrontCheckPwdVO> checkcheckBalancePwd(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              @RequestParam(value = "balancePwd", required = true) String balancePwd) {

        Member member = WebFrontSession.getLoginedUser(request, response);

        ServiceResult<FrontCheckPwdVO> serviceResult = new ServiceResult<FrontCheckPwdVO>();
        serviceResult = memberService.checkcheckBalancePwd(balancePwd, member.getId());

        HttpJsonResult<FrontCheckPwdVO> jsonResult = new HttpJsonResult<FrontCheckPwdVO>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<FrontCheckPwdVO>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 获取用户当前可用的已绑定的优惠券
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "order/getsellercoupon.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<CouponUser>> getSellerCoupon(HttpServletRequest request,
                                                                          HttpServletResponse response) {

        Member member = WebFrontSession.getLoginedUser(request, response);

        Integer sellerId = ConvertUtil.toInt(request.getParameter("sellerId"), 0);

        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getEffectiveByMemberIdAndSellerId(member.getId(), sellerId);

        HttpJsonResult<List<CouponUser>> jsonResult = new HttpJsonResult<List<CouponUser>>();
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<List<CouponUser>>(serviceResult.getMessage());
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 检查优惠券的可用性
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "order/checksellercoupon.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<CouponUser> checkSellerCoupon(HttpServletRequest request,
                                                                      HttpServletResponse response) {

        Member member = WebFrontSession.getLoginedUser(request, response);

        String orderAmount = request.getParameter("orderAmount");
        String couponTypeStr = request.getParameter("couponType");
        Integer couponType = ConvertUtil.toInt(couponTypeStr, 0);
        String couponSn = request.getParameter("couponSn");
        String couponPassword = request.getParameter("couponPassword");
        Integer sellerId = ConvertUtil.toInt(request.getParameter("sellerId"), 0);

        ServiceResult<CouponUser> couponUserRlt = couponService
            .getCouponUserOnlyByCouponSn(couponSn);
        if (!couponUserRlt.getSuccess()) {
            return new HttpJsonResult<CouponUser>(couponUserRlt.getMessage());
        }
        if (couponUserRlt.getResult() == null) {
            return new HttpJsonResult<CouponUser>("优惠券不存在，请确认是否输入正确。");
        }
        CouponUser couponUser = couponUserRlt.getResult();

        Integer memberId = member.getId();

        if (!sellerId.equals(couponUser.getSellerId())) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】只能购买" + couponUser.getSellerName() + "的商品。");
        }

        if (couponType == OrderCouponVO.COUPON_TYPE_1) {
            // 检查优惠券所属用户
            if (!memberId.equals(couponUser.getMemberId())) {
                return new HttpJsonResult<CouponUser>(
                    "优惠券【" + couponUser.getCouponSn() + "】不是属于您的优惠券，不能使用。");
            }
        } else if (couponType == OrderCouponVO.COUPON_TYPE_2) {
            // 校验密码
            if (couponUser.getPassword() == null
                || !couponUser.getPassword().equals(Md5.getMd5String(couponPassword))) {
                return new HttpJsonResult<CouponUser>(
                    "优惠券【" + couponUser.getCouponSn() + "】密码不对，请重新输入。");
            }
            // 检查优惠券所属用户
            if (couponUser.getMemberId() > 0 && !couponUser.getMemberId().equals(memberId)) {
                return new HttpJsonResult<CouponUser>(
                    "优惠券【" + couponUser.getCouponSn() + "】不是属于您的优惠券，不能使用。");
            }
        }

        // 优惠券可使用次数
        if (couponUser.getCanUse() < 1) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】已使用过，不能再次使用。");
        }

        // 优惠券用户关联的优惠券信息校验
        // 适用最低金额校验
        if (couponUser.getMinAmount().compareTo(new BigDecimal(orderAmount)) > 0) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】最低适用订单金额不得小于" + couponUser.getMinAmount()
                                                  + "元。");
        }
        // 优惠券使用时间校验
        if (couponUser.getUseStartTime().after(new Date())) {
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】还没有到可使用时间。");
        }
        if (couponUser.getUseEndTime().before(new Date())) {
            return new HttpJsonResult<CouponUser>("优惠券【" + couponUser.getCouponSn() + "】已过期。");
        }

        // 使用渠道校验
        if (couponUser.getChannel().intValue() != ConstantsEJS.CHANNEL_1
            && ConstantsEJS.CHANNEL_3 != couponUser.getChannel().intValue()) {
            String channelStr = couponUser.getChannel().intValue() == ConstantsEJS.CHANNEL_2 ? "电脑端"
                : "移动端";
            return new HttpJsonResult<CouponUser>(
                "优惠券【" + couponUser.getCouponSn() + "】只能在" + channelStr + "使用。");
        }

        HttpJsonResult<CouponUser> jsonResult = new HttpJsonResult<CouponUser>();
        jsonResult.setData(couponUser);
        return jsonResult;
    }

    // -------------------------限时抢购开始---------------------------------------------------
    /**
     * 限时抢购时跳转到提交订单页面 计算总金额,运费、货品小计，按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/flashsale-{visitPath}.html", method = { RequestMethod.GET })
    public String flashSale(@PathVariable String visitPath, HttpServletRequest request,
                            ModelMap map, HttpServletResponse response) {

        // visitPath为1-1-1形式，第一个为商品ID，第二个为货品ID，第三个为商家ID
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength != 3) { //长度不等于3url错误
            return "redirect:/error.html";
        }

        String productIdStr = arrVisitPath[0];
        String productGoodsIdStr = arrVisitPath[1];
        String sellerIdStr = arrVisitPath[2];
        Integer productId = ConvertUtil.toInt(productIdStr, 0);
        Integer productGoodsId = ConvertUtil.toInt(productGoodsIdStr, 0);
        Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);

        String errorUrl = this.toOrderForActivity(request, response, map, productId, productGoodsId,
            sellerId, null, 1, Orders.ORDER_TYPE_2);
        if (!StringUtil.isEmpty(errorUrl, true)) {
            // 如果出错跳到出错页面
            return errorUrl;
        }

        return "h5/order/orderflash";
    }

    /**
     * 限时抢购提交订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommitforflash.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmitForFlash(HttpServletRequest request,
                                                                                   HttpServletResponse response,
                                                                                   OrderCommitVO orderCommitVO,
                                                                                   ModelMap map) {
        // 数量默认为1
        orderCommitVO.setNumber(1);
        return this.commonSubmit(request, response, orderCommitVO, Orders.ORDER_TYPE_2);
    }

    // -------------------------限时抢购开始---------------------------------------------------

    // -------------------------活动公共开始---------------------------------------------------

    /**
     * 活动时跳转到活动订单结算页
     * @param request
     * @param map
     * @param productId 商品ID
     * @param productGoodsId 货品ID
     * @param sellerId 商家ID
     * @param activityId 活动ID（限时抢购、团购、集合竞价、积分换购）
     * @param number 购买数量
     * @param orderType 订单类型：Orders.ORDER_TYPE_2、限时抢购订单，Orders.ORDER_TYPE_3、团购订单，Orders.ORDER_TYPE_4、竞价定金订单，Orders.ORDER_TYPE_6、积分换购订单
     * @return 返回String字符串，如果字符串长度大于0，表示出错跳转到错误提示页面，返回空表示正常执行
     */
    private String toOrderForActivity(HttpServletRequest request, HttpServletResponse response,
                                      ModelMap map, Integer productId, Integer productGoodsId,
                                      Integer sellerId, Integer activityId, Integer number,
                                      Integer orderType) {
        Member member = WebFrontSession.getLoginedUser(request, response);

        MemberAddress memberAddress = null;

        Integer addressId = ConvertUtil.toInt(request.getParameter("addressId"), 0);
        if (addressId > 0) {
            ServiceResult<MemberAddress> memberAddressResult = memberAddressService
                .getMemberAddressById(addressId);
            memberAddress = memberAddressResult.getResult();
        }

        if (memberAddress == null) {
            // 收货地址信息
            ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
                .getMemberAddressByMId(member.getId());
            // 获取默认收货地址，如果没有取第一个
            if (serviceResult.getSuccess()) {
                List<MemberAddress> addressList = serviceResult.getResult();
                if (addressList != null && addressList.size() > 0) {
                    memberAddress = addressList.get(0);
                    for (MemberAddress address : addressList) {
                        if (address.getState() == MemberAddress.STATE_1) {
                            memberAddress = address;
                            break;
                        }
                    }
                }
            }
        }

        map.put("address", memberAddress);
        // 构建默认值 ，默认在线支付。收货地址为默认地址，发票默认为不开发票
        OrderCommitVO orderCommitVO = new OrderCommitVO();
        orderCommitVO.setInvoiceType("");
        orderCommitVO.setInvoiceTitle("");
        orderCommitVO.setPaymentName(Orders.PAYMENT_NAME_ONLINE);
        orderCommitVO.setPaymentCode(Orders.PAYMENT_CODE_ONLINE);
        map.put("orderCommitVO", orderCommitVO);

        map.put("number", number);

        // 取商家信息
        ServiceResult<Seller> sellerRlt = sellerService.getSellerById(sellerId);
        if (!sellerRlt.getSuccess()) {
            map.put("info", sellerRlt.getMessage());
            return "h5/commons/error";
        }
        map.put("seller", sellerRlt.getResult());

        // 取商品信息
        ServiceResult<Product> productRlt = productFrontService.getProductById(productId);
        if (!productRlt.getSuccess()) {
            map.put("info", productRlt.getMessage());
            return "h5/commons/error";
        }
        map.put("product", productRlt.getResult());

        // 取货品信息
        ServiceResult<ProductGoods> goodRlt = productGoodsService
            .getProductGoodsById(productGoodsId);
        if (!goodRlt.getSuccess()) {
            map.put("info", goodRlt.getMessage());
            return "h5/commons/error";
        }
        map.put("productGoods", goodRlt.getResult());

       if (orderType == Orders.ORDER_TYPE_6) {
            // 6、积分换购订单
            // 获取积分换购信息
            ServiceResult<ActIntegral> actIntegralResult = actIntegralService
                .getActIntegralById(activityId);
            if (!actIntegralResult.getSuccess()) {
                map.put("info", actIntegralResult.getMessage());
                return "h5/commons/error";
            }
            ActIntegral actIntegral = actIntegralResult.getResult();
            if (actIntegral != null) {
                map.put("actIntegral", actIntegral);
            } else {
                map.put("info", "积分活动信息获取失败。");
                return "h5/commons/error";
            }
        }

        if (orderType != Orders.ORDER_TYPE_6) {
            // 计算运费，积分换购不需要计算运费、不开发票
            FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
            feignProjectUtil.setProduct(productRlt.getResult());
            feignProjectUtil.setProductGoods(goodRlt.getResult());
            ServiceResult<BigDecimal> transFeeRlt = sellerTransportService
                .calculateTransFee(feignProjectUtil, number, memberAddress.getCityId());
            if (!transFeeRlt.getSuccess()) {
                map.put("info", transFeeRlt.getMessage());
                return "h5/commons/error";
            }
            BigDecimal transFee = transFeeRlt.getResult();
            map.put("transFee",
                transFee.compareTo(BigDecimal.ZERO) < 1 ? BigDecimal.ZERO : transFee);

            // 获取发票信息
            ServiceResult<List<Invoice>> invoiceResult = invoiceService
                .getInvoiceByMId(member.getId());
            map.put("invoiceList", invoiceResult.getResult());
        }

        //取会员余额信息
        ServiceResult<Member> memberResult = memberService.getMemberById(member.getId());
        if (memberResult.getResult() == null) {
            map.put("info", "会员信息获取失败。");
            return "h5/commons/error";
        }
        map.put("member", memberResult.getResult());

        ServiceResult<Config> configById = configservice.getConfigById(ConstantsEJS.CONFIG_ID);
        if (configById.getResult() != null) {
            Config config = configById.getResult();
            if (config.getIntegralScale() > 0) {
                map.put("config", config);
            }
        }

        return "";
    }

    // -------------------------活动公共结束---------------------------------------------------

    // -------------------------团购开始------------------------------------------------------

    /**
     * 团购时跳转到提交订单页面 计算总金额,运费、货品小计，按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/tuan-{visitPath}.html", method = { RequestMethod.GET })
    public String tuan(@PathVariable String visitPath, HttpServletRequest request, ModelMap map,
                       HttpServletResponse response) {

        // visitPath为1-1-1-1-1形式，第一个为商品ID，第二个为货品ID，第三个为商家ID，第四个未团购ID，第五个为购买数量
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength != 5) { //长度不等于5url错误
            return "redirect:/error.html";
        }

        String productIdStr = arrVisitPath[0];
        String productGoodsIdStr = arrVisitPath[1];
        String sellerIdStr = arrVisitPath[2];
        String actGroupIdStr = arrVisitPath[3];
        String numberStr = arrVisitPath[4];
        Integer productId = ConvertUtil.toInt(productIdStr, 0);
        Integer productGoodsId = ConvertUtil.toInt(productGoodsIdStr, 0);
        Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);
        Integer actGroupId = ConvertUtil.toInt(actGroupIdStr, 0);
        Integer number = ConvertUtil.toInt(numberStr, 0);

        String errorUrl = this.toOrderForActivity(request, response, map, productId, productGoodsId,
            sellerId, actGroupId, number, Orders.ORDER_TYPE_3);
        if (!StringUtil.isEmpty(errorUrl, true)) {
            // 如果出错跳到出错页面
            return errorUrl;
        }

        return "h5/order/ordergroup";
    }

    /**
     * 团购提交订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommitforgroup.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmitForGroup(HttpServletRequest request,
                                                                                   HttpServletResponse response,
                                                                                   OrderCommitVO orderCommitVO,
                                                                                   ModelMap map) {
        return this.commonSubmit(request, response, orderCommitVO, Orders.ORDER_TYPE_3);
    }

    // -------------------------团购开始------------------------------------------------------

    // -------------------------集合竞价开始---------------------------------------------------

    /**
     * 集合竞价时跳转到提交订单页面 计算总金额,运费、货品小计，按店铺拆分订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/bidding-{visitPath}.html", method = { RequestMethod.GET })
    public String bidding(@PathVariable String visitPath, HttpServletRequest request, ModelMap map,
                          HttpServletResponse response) {

        // visitPath为1-1-1-1-1形式，第一个为商品ID，第二个为货品ID，第三个为商家ID，第四个为集合竞价ID，第五个为购买数量
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength != 5) { //长度不等于5url错误
            return "redirect:/error.html";
        }

        String productIdStr = arrVisitPath[0];
        String productGoodsIdStr = arrVisitPath[1];
        String sellerIdStr = arrVisitPath[2];
        String actBiddingIdStr = arrVisitPath[3];
        String numberStr = arrVisitPath[4];
        Integer productId = ConvertUtil.toInt(productIdStr, 0);
        Integer productGoodsId = ConvertUtil.toInt(productGoodsIdStr, 0);
        Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);
        Integer actBiddingId = ConvertUtil.toInt(actBiddingIdStr, 0);
        Integer number = ConvertUtil.toInt(numberStr, 0);

        String errorUrl = this.toOrderForActivity(request, response, map, productId, productGoodsId,
            sellerId, actBiddingId, number, Orders.ORDER_TYPE_4);
        if (!StringUtil.isEmpty(errorUrl, true)) {
            // 如果出错跳到出错页面
            return errorUrl;
        }

        return "h5/order/orderbidding";
    }

    /**
     * 集合竞价提交订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommitforbidding.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmitForBidding(HttpServletRequest request,
                                                                                     HttpServletResponse response,
                                                                                     OrderCommitVO orderCommitVO,
                                                                                     ModelMap map) {
        return this.commonSubmit(request, response, orderCommitVO, Orders.ORDER_TYPE_4);
    }

    // -------------------------集合竞价结束---------------------------------------------------

    // -------------------------积分换购开始------------------------------------------------------

    /**
     * 积分换购时跳转到提交订单页面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/jifen-{visitPath}.html", method = { RequestMethod.GET })
    public String jifen(@PathVariable String visitPath, HttpServletRequest request, ModelMap map,
                        HttpServletResponse response) {

        // visitPath为1-1-1-1-1形式，第一个为商品ID，第二个为货品ID，第三个为商家ID，第四个为积分活动ID，第五个为购买数量
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength != 5) { //长度不等于5url错误
            return "redirect:/error.html";
        }

        String productIdStr = arrVisitPath[0];
        String productGoodsIdStr = arrVisitPath[1];
        String sellerIdStr = arrVisitPath[2];
        String actIntegralIdStr = arrVisitPath[3];
        String numberStr = arrVisitPath[4];
        Integer productId = ConvertUtil.toInt(productIdStr, 0);
        Integer productGoodsId = ConvertUtil.toInt(productGoodsIdStr, 0);
        Integer sellerId = ConvertUtil.toInt(sellerIdStr, 0);
        Integer actIntegralId = ConvertUtil.toInt(actIntegralIdStr, 0);
        Integer number = ConvertUtil.toInt(numberStr, 0);

        String errorUrl = this.toOrderForActivity(request, response, map, productId, productGoodsId,
            sellerId, actIntegralId, number, Orders.ORDER_TYPE_6);
        if (!StringUtil.isEmpty(errorUrl, true)) {
            // 如果出错跳到出错页面
            return errorUrl;
        }

        return "h5/order/orderintegral";
    }

    /**
     * 积分换购提交订单
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/ordercommitforintegral.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResultForAjax<OrderSuccessVO> orderSubmitForIntegral(HttpServletRequest request,
                                                                                      HttpServletResponse response,
                                                                                      OrderCommitVO orderCommitVO,
                                                                                      ModelMap map) {
        return this.commonSubmit(request, response, orderCommitVO, Orders.ORDER_TYPE_6);
    }

    // -------------------------积分换购开始------------------------------------------------------
}
