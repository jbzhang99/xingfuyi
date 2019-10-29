package com.yixiekeji.web.controller.seller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerApply;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.seller.ISellerApplyService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.MemberSession;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 商家入驻流程controller
 * 
 * @Filename: FrontSellerRegisterController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class SellerRegisterController extends BaseController {

    @Resource
    private ISellerApplyService sellerApplyService;
    @Resource
    private ISellerService      sellerService;
    @Resource
    private IMemberService      memberService;
    @Resource
    private DomainUrlUtil       domainUrlUtil;

    @RequestMapping(value = "/store/step1.html", method = { RequestMethod.GET })
    public String step1(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> dataMap) {
        String returnURL = "front/seller/step1";
        MemberSession ms = WebFrontSession.getMemberSession(request);
        if (ms == null) {
            returnURL = "front/seller/step5";
            dataMap.put("nologin", "登录后可申请!");
            dataMap.put("url", "login.html");
        } else {
            //是否已申请过
            ServiceResult<SellerApply> sr = sellerApplyService
                .getSellerApplyByUser(ms.getMember().getId());
            if (sr.getResult() != null) {
                returnURL = "front/seller/step5";
                dataMap.put("applyed", "您已提交过申请,请耐心等待审核!");
            } else {
                request.getSession().setAttribute("safekey", new Date());
            }
        }
        return returnURL;
    }

    /**
     * 商家入驻第二步：公司资质信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/store/step2.html", method = { RequestMethod.GET })
    public String step2(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> dataMap) {
        String returnURL = "front/seller/step2";
        if (request.getSession().getAttribute("safekey") == null) {
            dataMap.put("illegal", "true");
            returnURL = "front/seller/step5";
        } else {
            request.getSession().removeAttribute("safekey");
        }
        return returnURL;
    }

    /**
     * 商家入住申请第二步保存信息<br>
     * 保存公司资质信息
     * @param request
     * @param response
     * @param app
     * @return
     */
    @RequestMapping(value = "/store/step2save.html", method = { RequestMethod.POST })
    public String step2Save(HttpServletRequest request, HttpServletResponse response,
                            SellerApply app) {
        //营业执照扫描件
        String bli = null;
        //身份证正面
        String pcu = null;
        //身份证反面
        String pdw = null;

        Map<String, String> param = new HashMap<String, String>();
        param.put("seller", "apply");

        try {
            bli = UploadUtil.getInstance().uploadFile2ImageServer("up_bussinessLicenseImage",
                request, param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");

            pcu = UploadUtil.getInstance().uploadFile2ImageServer("up_personCardUp", request, param,
                domainUrlUtil.getImageResources() + "/commonImageUploadServlet");

            pdw = UploadUtil.getInstance().uploadFile2ImageServer("up_personCardDown", request,
                param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
        } catch (BusinessException e) {
            e.printStackTrace();
            log.error("图片上传异常>>" + e.getMessage());
        }

        if (bli != null) {
            app.setBussinessLicenseImage(bli);
        }

        if (pcu != null) {
            app.setPersonCardUp(pcu);
        }

        if (pdw != null) {
            app.setPersonCardDown(pdw);
        }

        WebFrontSession.putObjToSession(request, "frontsellerApply", app);

        request.getSession().setAttribute("safekey", new Date());
        return "redirect:/store/step3.html";
    }

    /**
     * 商家入住申请第三步<br>
     * @param request
     * @param response
     * @param app
     * @return
     */
    @RequestMapping(value = "/store/step3.html", method = { RequestMethod.GET })
    public String step3(HttpServletRequest request, HttpServletResponse response, SellerApply app,
                        Map<String, Object> dataMap) {
        String returnURL = "front/seller/step3";
        if (request.getSession().getAttribute("safekey") == null) {
            dataMap.put("illegal", "true");
            returnURL = "front/seller/step5";
        } else {
            request.getSession().removeAttribute("safekey");
        }
        return returnURL;
    }

    /**
     * 商家入驻第三步保存信息
     * @param request
     * @param response
     * @param app
     * @return
     */
    @RequestMapping(value = "/store/step3save.html", method = { RequestMethod.POST })
    public String step3Save(HttpServletRequest request, HttpServletResponse response,
                            SellerApply app) {

        SellerApply sp = (SellerApply) WebFrontSession.getObjFromSession(request,
            "frontsellerApply");

        sp.setBankUser(app.getBankUser());
        sp.setBankName(app.getBankName());
        sp.setBankNameBranch(app.getBankNameBranch());
        sp.setBrandNameCode(app.getBrandNameCode());
        sp.setBankCode(app.getBankCode());
        sp.setBankProvince(app.getBankProvince());
        sp.setBankCity(app.getBankCity());
        sp.setTaxLicense(app.getTaxLicense());

        WebFrontSession.putObjToSession(request, "frontsellerApply", sp);

        request.getSession().setAttribute("safekey", new Date());
        return "redirect:/store/step4.html";
    }

    /**
     * 商家入住申请第四步<br>
     * @param request
     * @param response
     * @param app
     * @return
     */
    @RequestMapping(value = "/store/step4.html", method = { RequestMethod.GET })
    public String step4(HttpServletRequest request, HttpServletResponse response, SellerApply app,
                        Map<String, Object> dataMap) {
        String returnURL = "front/seller/step4";
        if (request.getSession().getAttribute("safekey") == null) {
            dataMap.put("illegal", "true");
            returnURL = "front/seller/step5";
        } else {
            request.getSession().removeAttribute("safekey");
        }
        return returnURL;
    }

    /**
     * 注册第四步保存
     * @param request
     * @param response
     * @param name
     * @param seller_name
     * @return
     */
    @RequestMapping(value = "/store/step4save.html", method = { RequestMethod.POST })
    public String step4Save(HttpServletRequest request, HttpServletResponse response, String name,
                            String seller_name) {
        SellerApply sellerApp = (SellerApply) WebFrontSession.getObjFromSession(request,
            "frontsellerApply");

        sellerApp.setName(name);
        sellerApp.setCreateTime(new Date());

        Member loginedUser = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<Member> sr = memberService.getMemberById(loginedUser.getId());
        if (!sr.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                throw new RuntimeException(sr.getMessage());
            } else {
                throw new BusinessException(sr.getMessage());
            }
        }
        Member member = sr.getResult();

        sellerApp.setUserId(member.getId());
        sellerApp.setName(member.getName());
        sellerApp.setPassword(member.getPassword());
        sellerApp.setState(SellerApply.STATE_1_SEND);
        sellerApp.setType(2);

        //保存商家申请、及商家表
        ServiceResult<Boolean> serviceResult = sellerApplyService.saveSellerApplyForFront(sellerApp,
            name, seller_name, loginedUser.getId());
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        WebFrontSession.delObjFromSession(request, "frontsellerApply");

        request.getSession().setAttribute("safekey", new Date());
        return "redirect:/store/step5.html";
    }

    /**
     * 注册第五步，注册成功
     * @param request
     * @param response
     * @param app
     * @return
     */
    @RequestMapping(value = "/store/step5.html", method = { RequestMethod.GET })
    public String step5(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> dataMap) {
        if (request.getSession().getAttribute("safekey") == null)
            dataMap.put("illegal", "true");
        request.getSession().removeAttribute("safekey");
        return "front/seller/step5";
    }

    /**
     * 校验唯一
     * @param request
     * @param val
     * @param type
     */
    @RequestMapping(value = "/store/validate.html", method = { RequestMethod.POST })
    public void validUnique(HttpServletRequest request, HttpServletResponse response, String val,
                            Integer type) {
        boolean ret = true;
        Assert.notNull(type);

        switch (type) {
            case ValidType.COMPANY_NAME:

                ServiceResult<List<SellerApply>> sr = sellerApplyService
                    .getSellerApplyByCompany(val);
                if (!sr.getSuccess()) {
                    ret = false;
                    if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                        throw new RuntimeException(sr.getMessage());
                    } else {
                        throw new BusinessException(sr.getMessage());
                    }
                } else {
                    List<SellerApply> list = sr.getResult();
                    ret = list == null || (list != null && list.size() < 1);
                }

                break;
            case ValidType.SELLER_NAME:

                ServiceResult<List<Seller>> sr1 = sellerService.getSellerByName(val);
                if (!sr1.getSuccess()) {
                    ret = false;
                    if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr1.getCode())) {
                        throw new RuntimeException(sr1.getMessage());
                    } else {
                        throw new BusinessException(sr1.getMessage());
                    }
                } else {
                    List<Seller> sellerlist = sr1.getResult();
                    ret = sellerlist == null || (sellerlist != null && sellerlist.size() < 1);
                }

                break;
            case ValidType.STORE_NAME:

                ServiceResult<List<Seller>> sr2 = sellerService.getSellerBySellerName(val);
                if (!sr2.getSuccess()) {
                    ret = false;
                    if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr2.getCode())) {
                        throw new RuntimeException(sr2.getMessage());
                    } else {
                        throw new BusinessException(sr2.getMessage());
                    }
                } else {
                    List<Seller> sellerlist = sr2.getResult();
                    ret = sellerlist == null || (sellerlist != null && sellerlist.size() < 1);
                }

                break;
            default:
                break;
        }
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证类型
     */
    static class ValidType {
        public static final int COMPANY_NAME = 1;
        public static final int SELLER_NAME  = 2;
        public static final int STORE_NAME   = 3;
    }

}
