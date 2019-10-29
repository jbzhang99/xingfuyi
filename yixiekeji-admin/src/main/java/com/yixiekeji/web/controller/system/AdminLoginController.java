package com.yixiekeji.web.controller.system;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dto.OrderDayDto;
import com.yixiekeji.echarts.component.SeriesData;
import com.yixiekeji.echarts.util.EchartsDataProvider;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductBrandService;
import com.yixiekeji.service.report.IStatisticsService;
import com.yixiekeji.service.seller.ISellerApplyService;
import com.yixiekeji.service.system.ISystemAdminService;
import com.yixiekeji.service.system.ISystemResourcesRolesService;
import com.yixiekeji.service.system.ISystemRolesService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * admin管理controller
 *
 * @Filename: AdminUserController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin", produces = "application/json;charset=UTF-8")
public class AdminLoginController extends BaseController {
    @Resource
    private ISystemAdminService          systemAdminService;
    @Resource
    private ISystemRolesService          rolesService;
    @Resource
    private ISystemResourcesRolesService systemResourcesRolesService;
    @Resource
    private IStatisticsService           statisticsService;
    @Resource
    private IOrdersService               ordersService;
    @Resource
    private IProductBrandService         productBrandService;
    @Resource
    private ISellerApplyService          sellerApplyService;
    @Resource
    private IOrdersService               orderService;

    @RequestMapping(value = "/login.html", method = { RequestMethod.GET })
    public String login() throws Exception {
        return "admin/login";
    }

    @RequestMapping(value = "/doLogin", method = { RequestMethod.POST })
    public String doLogin(HttpServletRequest request, HttpServletResponse response,
                          Map<String, Object> dataMap) throws Exception {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("verifyCode");
        String verify_number = WebAdminSession.getVerifyNumber(request);

        if (name == null) {
            dataMap.put("message", "用户名不能为空");
            return "admin/login";
        }

        if (password == null) {
            dataMap.put("message", "用户名不能为空");
            return "admin/login";
        }

        if (verify_number == null || !verify_number.equalsIgnoreCase(verifyCode)) {
            dataMap.put("message", "验证码不正确");
            return "admin/login";
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,
            Md5.getMd5String(password).toCharArray());
        // token.setRememberMe(true);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            dataMap.put("message", "账号不存在");
            return "admin/login";
        } catch (DisabledAccountException e) {
            dataMap.put("message", "账号未启用");
            return "admin/login";
        } catch (IncorrectCredentialsException e) {
            dataMap.put("message", "账号或密码错误");
            return "admin/login";
        } catch (RuntimeException e) {
            dataMap.put("message", "未知错误,请联系管理员");
            return "admin/login";
        }

        SystemAdmin adminUser = (SystemAdmin) subject.getPrincipal();
        if (adminUser.getStatus().intValue() != ConstantsEJS.SYSTEM_ADMIN_STATUS_NORM) {
            dataMap.put("message", "账号停用不能登录");
            return "admin/login";
        }

        WebAdminSession.putAdminUser(request, adminUser);

        return "redirect:/admin/index.html";
    }

    @RequestMapping(value = "/index.html", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        // 如果没有登录，去登录页
        if (!subject.isAuthenticated()) {
            response.getWriter().print("<script>top.window.location.href='"
                                       + request.getContextPath() + "/admin/login.html'</script>");
            return null;
        }
        return "admin/index";
    }

    @RequestMapping(value = "indexPage", method = { RequestMethod.GET })
    public String indexPage(HttpServletRequest request, HttpServletResponse response,
                            ModelMap dataMap) throws Exception {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        String day = TimeUtil.getToday();
        queryMap.put("q_startTime", day + " 00:00:00");
        queryMap.put("q_endTime", day + " 23:59:59");

        BigDecimal moneyProduct = new BigDecimal(0);
        BigDecimal moneyLogistics = new BigDecimal(0);
        BigDecimal moneyOrder = new BigDecimal(0);
        BigDecimal moneyPaidBalance = new BigDecimal(0);
        BigDecimal moneyPaidReality = new BigDecimal(0);
        BigDecimal moneyBack = new BigDecimal(0);
        Integer count = 0;

        ServiceResult<List<OrderDayDto>> serviceResult = ordersService.getOrderDayDto(queryMap);
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            List<OrderDayDto> orderDayDtos = serviceResult.getResult();

            for (OrderDayDto orderDayDto : orderDayDtos) {
                moneyProduct = moneyProduct.add(orderDayDto.getMoneyProduct());
                moneyLogistics = moneyLogistics.add(orderDayDto.getMoneyLogistics());
                moneyOrder = moneyOrder.add(orderDayDto.getMoneyOrder());
                moneyPaidBalance = moneyPaidBalance.add(orderDayDto.getMoneyPaidBalance());
                moneyPaidReality = moneyPaidReality.add(orderDayDto.getMoneyPaidReality());
                moneyBack = moneyBack.add(orderDayDto.getMoneyBack());
                count += orderDayDto.getCount();
            }

        }
        dataMap.put("count", count);
        dataMap.put("moneyOrder", moneyOrder);
        dataMap.put("moneyPaidReality", moneyPaidReality);

        ServiceResult<Integer> apply = sellerApplyService.getSellerApplyCount();
        ServiceResult<Integer> brand = productBrandService.getUndoBrand();
        ServiceResult<Integer> orders = orderService.getReconfOrdersCount();

        dataMap.put("apply", apply.getResult());
        dataMap.put("brand", brand.getResult());
        dataMap.put("orders", orders.getResult());

        return "admin/indexPage";
    }

    @RequestMapping(value = "index/ordersOverview", method = { RequestMethod.GET })
    public String ordersOverview(HttpServletRequest request, ModelMap dataMap) throws Exception {
        //默认当前年
        Calendar cal = Calendar.getInstance();
        Calendar cur = Calendar.getInstance();
        cur.clear();
        cur.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cur.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //本月第一天
        String startTime = sdf.format(cur.getTime());
        //当前时间
        String endTime = sdf.format(cal.getTime());

        Map<String, String> map = new HashMap<String, String>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        ServiceResult<List<SeriesData>> serviceResult = statisticsService.getOrderOverviewData(map);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        dataMap.put("option", EchartsDataProvider.getOrderOverviewData(serviceResult.getResult(),
            startTime, endTime, true));
        return "admin/orderoverview";
    }

    @RequestMapping(value = "index/pvStatistics", method = { RequestMethod.GET })
    public String pvStatistics(HttpServletRequest request, HttpServletResponse response,
                               ModelMap dataMap) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        //默认当前时间
        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH) + 1;
        String model = "month";

        Map<String, String> map = new HashMap<String, String>();
        year = cal.get(Calendar.YEAR);
        map.put("year", year + "");
        map.put("month", month + "");
        map.put("model", model);
        ServiceResult<List<Object>> dtolist = statisticsService.pvStatistics(map);

        dataMap.put("model", model);
        dataMap.put("currentYear", year);
        dataMap.put("currentMonth", month < 10 ? "0" + month : month);
        dataMap.put("option",
            EchartsDataProvider.pvStatistics(dtolist.getResult(), model, year, month, true));
        return "admin/pvstatistics";
    }

    @RequestMapping(value = "/exit", method = { RequestMethod.GET })
    public String exit(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        HttpSession session = request.getSession();
        Enumeration<?> em = session.getAttributeNames();
        //清空session
        while (em.hasMoreElements()) {
            session.removeAttribute(em.nextElement().toString());
        }
        //清除cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
        }
        //重定向
        return "redirect:/admin/login";
    }

    /**
     * 访问无权限URL时跳转路径
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unauth.html", method = { RequestMethod.GET })
    public String unAuth() throws Exception {
        return "admin/unauth";
    }

}
