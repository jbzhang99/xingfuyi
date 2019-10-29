package com.yixiekeji.web.controller.member;

import com.yixiekeji.core.*;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.sale.ISaleMemberService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "member")
public class AppSaleMemberController extends BaseController {

    @Resource
    private IMemberService memberService;

    @Resource
    private ISaleMemberService saleMemberService;

    @Resource
    private DomainUrlUtil domainUrlUtil;

    private final static int PAGE_NUMBER = 20;

    /**
     * 跳转到 个人资料界面
     *
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-saleIndex", method = {RequestMethod.GET})
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> saleIndex(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  ModelMap map,
                                                  @RequestParam("memberId") Integer memberId) throws Exception {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        ServiceResult<Member> serviceResult = memberService.getMemberById(memberId);

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        map.put("member", member);

        ServiceResult<SaleMember> resultSaleMember = saleMemberService
                .getSaleMemberByMemberId(member.getId());
        SaleMember saleMember = null;
        if (resultSaleMember.getSuccess()) {
            saleMember = resultSaleMember.getResult();
        }
        if (saleMember == null) {
            saleMember = new SaleMember();
            saleMember.setReferrerName("无");
            saleMember.setSaleCode("无");
            saleMember.setIsSale(ConstantsEJS.YES_NO_0);
        }

        map.put("saleMember", saleMember);

        jsonResult.setData(map);
        return jsonResult;
    }

    /**
     * 申请成为推广员
     *
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-apply.html", method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<Boolean> saleApply(HttpServletRequest request,
                                      HttpServletResponse response,
                                      ModelMap map,
                                      Integer memberId) throws Exception {
        ServiceResult<Member> serviceResult = memberService.getMemberById(memberId);

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }

        SaleMember saleMember = new SaleMember();
        saleMember.setMemberId(member.getId());
        saleMember.setMemberName(member.getName());
        saleMember.setCertificateType(SaleMember.CERTIFICATETYPE_CODE);
        saleMember.setState(SaleMember.STATE_1);
        saleMember.setGrade(SaleMember.GRADE_1);
        saleMember.setIsSale(ConstantsEJS.YES_NO_1);

        ServiceResult<Integer> resultSaleMember = saleMemberService
                .saveSaleMemberByMemberId(saleMember);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        if (!resultSaleMember.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleMember.getCode())) {
                throw new RuntimeException(resultSaleMember.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 跳转到 个人资料界面
     *
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-finance-info.html", method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<Map<String, Object>> saleFinanceInfo(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        ModelMap map,
                                                        Integer memberId) throws Exception {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        ServiceResult<Member> serviceResult = memberService.getMemberById(memberId);

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        map.put("member", member);

        String success = request.getParameter("success");
        if ("success".equals(success)) {
            map.put("message", "操作成功");
        }

        ServiceResult<SaleMember> resultSaleMember = saleMemberService
                .getSaleMemberByMemberId(member.getId());
        SaleMember saleMember = null;
        if (resultSaleMember.getSuccess()) {
            saleMember = resultSaleMember.getResult();
        }
        if (saleMember == null) { //没有创建，创建记录
            saleMember = new SaleMember();
            saleMember.setMemberId(member.getId());
            saleMember.setMemberName(member.getName());
            saleMember.setCertificateType(SaleMember.CERTIFICATETYPE_CODE);
            saleMember.setState(SaleMember.STATE_1);
            saleMember.setGrade(SaleMember.GRADE_1);
            saleMember.setIsSale(ConstantsEJS.YES_NO_0);

            ServiceResult<Integer> resultSale = saleMemberService
                    .saveSaleMemberByMemberId(saleMember);
            if (resultSale.getSuccess()) {
                saleMember = saleMemberService.getSaleMemberById(member.getId()).getResult();
            }
        }

        map.put("saleMember", saleMember);

        jsonResult.setData(map);
        return jsonResult;
    }

    /**
     * 保存财务信息
     *
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-finance.html", method = {RequestMethod.POST})
    public @ResponseBody
    HttpJsonResult<Boolean> saleFinance(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Integer memberId) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(memberId);

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        int id = ConvertUtil.toInt(request.getParameter("id"), 0);
        if (id == 0) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(id);
        if (!resultSaleMemberOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        SaleMember saleMember = resultSaleMemberOld.getResult();
        if (null == saleMember) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }
        if (member.getId().intValue() != saleMember.getMemberId().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，只能操作自己的数据，请勿越权！");
            return jsonResult;
        }

        saleMember
                .setCertificateType(ConvertUtil.toInt(request.getParameter("certificateType"), 1));
        saleMember.setCertificateCode(request.getParameter("certificateCode"));
        saleMember.setBankAdd(request.getParameter("bankAdd"));
        saleMember.setBankCode(request.getParameter("bankCode"));
        saleMember.setBankName(request.getParameter("bankName"));
        saleMember.setBankType(request.getParameter("bankType"));
        saleMember.setState(SaleMember.STATE_1);

        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);

        if (!resultSaleMember.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleMember.getCode())) {
                throw new RuntimeException(resultSaleMember.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 提交审核
     *
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-submit.html", method = {RequestMethod.POST})
    public @ResponseBody
    HttpJsonResult<Boolean> saleSubmit(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Integer memberId) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(memberId);

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        int id = ConvertUtil.toInt(request.getParameter("id"), 0);
        if (id == 0) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(id);
        if (!resultSaleMemberOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        SaleMember saleMember = resultSaleMemberOld.getResult();
        if (null == saleMember) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }
        if (member.getId().intValue() != saleMember.getMemberId().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，只能操作自己的数据，请勿越权！");
            return jsonResult;
        }

        if (SaleMember.STATE_3 == saleMember.getState().intValue()
                || SaleMember.STATE_2 == saleMember.getState().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，审核通过不能重新提交！");
            return jsonResult;
        }
        saleMember.setState(SaleMember.STATE_2);

        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);

        if (!resultSaleMember.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleMember.getCode())) {
                throw new RuntimeException(resultSaleMember.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 查询我的推广用户
     *
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-member1.html", method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<Map<String, Object>> saleMember1(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    ModelMap dataMap,
                                                    Integer memberId) throws Exception {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_referrerId", memberId.toString());
        dataMap.put("q_memberName", StringUtil.nullSafeString(queryMap.get("q_memberName")));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleMember>> resultSaleMember = saleMemberService
                .getSaleMembers(feignUtil);
        if (resultSaleMember.getSuccess()) {
            dataMap.put("saleMembers", resultSaleMember.getResult());
            //            dataMap.put("pagesize", PAGE_NUMBER);
        }

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 返回JSON
     *
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-sale-member1-json.html", method = RequestMethod.GET)
    public @ResponseBody
    HttpJsonResult<List<SaleMember>> saleMember1Json(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     Integer memberId) {
        HttpJsonResult<List<SaleMember>> jsonResult = new HttpJsonResult<List<SaleMember>>();
        Member member = memberService.getMemberById(memberId).getResult();

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_referrerId", member.getId().toString());
        queryMap.put("q_memberName", StringUtil.nullSafeString(queryMap.get("q_memberName")));

        String pageNumStr = request.getParameter("pageNum");
        int pageNum = ConvertUtil.toInt(pageNumStr, 1);
        PagerInfo pager = new PagerInfo(PAGE_NUMBER, pageNum);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleMember>> serviceResult = saleMemberService.getSaleMembers(feignUtil);
        if (!serviceResult.getSuccess()) {
            return jsonResult;
        }

        jsonResult.setData(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    /**
     * 查询我的间接推广用户
     *
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-member2.html", method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<Map<String, Object>> saleMember2(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    ModelMap dataMap,
                                                    Integer memberId) throws Exception {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_referrerPid", memberId.toString());
        dataMap.put("q_memberName", StringUtil.nullSafeString(queryMap.get("q_memberName")));
        dataMap.put("q_referrerName", StringUtil.nullSafeString(queryMap.get("q_referrerName")));

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleMember>> resultSaleMember = saleMemberService
                .getSaleMembers(feignUtil);

        if (resultSaleMember.getSuccess()) {
            dataMap.put("saleMembers", resultSaleMember.getResult());
            //            dataMap.put("pagesize", PAGE_NUMBER);
        }

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 返回JSON
     *
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-sale-member2-json.html", method = RequestMethod.GET)
    public @ResponseBody
    HttpJsonResult<List<SaleMember>> saleMember2Json(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     Integer memberId) {
        HttpJsonResult<List<SaleMember>> jsonResult = new HttpJsonResult<List<SaleMember>>();
        Member member = memberService.getMemberById(memberId).getResult();
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_referrerPid", member.getId().toString());
        queryMap.put("q_memberName",
                StringUtil.nullSafeString(request.getParameter("q_memberName")));
        queryMap.put("q_referrerName",
                StringUtil.nullSafeString(request.getParameter("q_referrerName")));

        String pageNumStr = request.getParameter("pageNum");
        int pageNum = ConvertUtil.toInt(pageNumStr, 1);
        PagerInfo pager = new PagerInfo(PAGE_NUMBER, pageNum);

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleMember>> serviceResult = saleMemberService.getSaleMembers(feignUtil);
        if (!serviceResult.getSuccess()) {
            return jsonResult;
        }

        jsonResult.setData(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    /**
     * @Description: 绑定银行卡
     * @Author: mofan
     * @Date: 2019/10/18
     */
    @PostMapping(value = "/app-saleFinance")
    @ResponseBody
    public ServiceResult saleFinance(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("smsVerifyCode") String smsVerifyCode,
                                       @RequestParam("memberId") Integer memberId,
                                       @RequestParam("bankName") String bankName,
                                       @RequestParam("bankCode") String bankCode,
                                       @RequestParam("bankType") String bankType
                                     ) {
        HttpSession session = request.getSession();
        Object reg_smsCode = session.getAttribute("person_smsCode");
        if (reg_smsCode == null || !reg_smsCode.equals(smsVerifyCode)) {
            return new ServiceResult(false, StatusCode.ERROR, "短信验证码校验失败!");
        }
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(memberId);
        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(memberId);
        if (!resultSaleMemberOld.getSuccess()) {
            return new ServiceResult(false, StatusCode.ERROR, "操作失败，请重试!");
        }
        SaleMember saleMember = resultSaleMemberOld.getResult();
        if (null == saleMember) {
            return new ServiceResult(false, StatusCode.ERROR, "操作失败，请重试!");
        }
        if (member.getId().intValue() != saleMember.getMemberId().intValue()) {
            return new ServiceResult(false, StatusCode.ERROR, "操作失败，只能操作自己的数据，请勿越权！");
        }

        if (!bankName.equals(saleMember.getBankName())) {
            return new ServiceResult(false, StatusCode.ERROR, "收款名称不匹配!");
        }
        saleMember.setBankName(bankName);
        saleMember.setBankCode(bankCode);
        saleMember.setBankType(bankType);
        saleMember.setBindingState(1);
        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);
        if (!resultSaleMember.getSuccess()) {
            return new ServiceResult(false, StatusCode.ERROR, "绑定失败!");
        }
        return new ServiceResult(true, StatusCode.OK, "绑定成功!");
    }


    /**
     * 图片上传
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/app-uploadFiles", method = {RequestMethod.POST})
    @ResponseBody
    public Object uploadImage(MultipartHttpServletRequest request,
                       HttpServletResponse response) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
            if (map != null) {
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String str = iter.next();
                    List<MultipartFile> fileList = map.get(str);
                    for (MultipartFile mpf : fileList) {
                        String originalFilename = mpf.getOriginalFilename();
                        File tmpFile = new File(buildImgPath(request) + "/" + UUID.randomUUID()
                                + originalFilename.substring(
                                originalFilename.lastIndexOf("."),
                                originalFilename.length()));
                        if (!mpf.isEmpty()) {
                            byte[] bytes = mpf.getBytes();
                            BufferedOutputStream stream = new BufferedOutputStream(
                                    new FileOutputStream(tmpFile));
                            stream.write(bytes);
                            stream.close();
                        }
                        String url = UploadUtil.getInstance().productUploaderImage(tmpFile, request,
                                domainUrlUtil.getImageResources() + "/imageUtilProduct", response);
                        //规范路径,以避免浏览器兼容问题
                        if (!StringUtil.isEmpty(url))
                            url = url.replaceAll("\\\\", "/");
                        result.put("url", domainUrlUtil.getImageResources() + url);
                        jsonResult.setData(result);
                        return jsonResult;
                    }
                }
            }
        } catch (Exception e) {
            log.error("[SellerProductController][uploadImage] exception:", e);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private String buildImgPath(HttpServletRequest request) {
        String path = "upload";
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        path = request.getRealPath(path);
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
        return path;
    }


    /**
     * @Description: 监护人/残疾人身份证认证
     * @Author: mofan
     * @Date: 2019/10/19
     */
    @PostMapping(value = "/app-guardianAuth")
    @ResponseBody
    public ServiceResult guardianAuth(
                                       @RequestParam("saleMemberId") Integer saleMemberId,
                                       @RequestParam("bankName")String bankName,
                                       @RequestParam("certificateCode")String certificateCode,
                                       @RequestParam("url")String url,
                                       @RequestParam("authType")Integer authType) {
        SaleMember saleMember = new SaleMember();
        saleMember.setId(saleMemberId);
        saleMember.setAuthType(authType);
        saleMember.setBankName(bankName);
        saleMember.setCertificateCode(certificateCode);
        saleMember.setCardPath(url);
        saleMember.setState(2);
        ServiceResult<Integer> ServiceResult = saleMemberService.updateSaleMember(saleMember);
        if(ServiceResult.getSuccess()){
            return new ServiceResult(true, StatusCode.OK, "提交审核成功!");
        }
            return new ServiceResult(false, StatusCode.ERROR, "提交审核失败!");
    }

    /**
     * @Description: 残疾证认证
     * @Author: mofan
     * @Date: 2019/10/19
     */
    @PostMapping(value = "/app-disabilityAuth")
    @ResponseBody
    public ServiceResult disabilityAuth(
                                         @RequestParam("saleMemberId")Integer saleMemberId,
                                          @RequestParam("disabilityGrade")String disabilityGrade,
                                          @RequestParam("disabilityCard")String disabilityCard,
                                          @RequestParam("url")String url,
                                          @RequestParam("authType")Integer authType,
                                         @RequestParam("detailDress")String detailDress,
                                          @RequestParam("provinceId")Integer provinceId,
                                         @RequestParam("cityId")Integer cityId,
                                          @RequestParam("areaId")Integer areaId) {
        SaleMember saleMember = new SaleMember();
        saleMember.setId(saleMemberId);
        saleMember.setAuthType(authType);
        saleMember.setDisabilityGrade(disabilityGrade);
        saleMember.setDisabilityCard(disabilityCard);
        saleMember.setCardPath(url);
        saleMember.setDetailDress(detailDress);
        saleMember.setProvinceId(provinceId);
        saleMember.setCityId(cityId);
        saleMember.setAreaId(areaId);
        saleMember.setDisabilityState(1);
        ServiceResult<Integer> ServiceResult = saleMemberService.updateSaleMember(saleMember);
        if(ServiceResult.getSuccess()){
            return new ServiceResult(true, StatusCode.OK, "提交审核成功!");
        }
        return new ServiceResult(false, StatusCode.ERROR, "提交审核失败!");
    }

    /** 
    * @Description: 根据id获取残疾人/监护人认证信息
    * @Author: mofan 
    * @Date: 2019/10/21 
    */
    @GetMapping(value = "/app-getAuthInfoById")
    @ResponseBody
    public ServiceResult getAuthInfoById( @RequestParam("saleMemberId") Integer saleMemberId,
                                          @RequestParam("state")Integer state){
        SaleMember saleMember = saleMemberService.getAuthInfoById(saleMemberId,state);
        if(saleMember == null ){
            return new ServiceResult(false, StatusCode.ERROR, "查询失败!");
        }
        return new ServiceResult(true, StatusCode.OK, saleMember,"查询成功!");
    }

}
