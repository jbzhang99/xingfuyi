package com.yixiekeji.web.controller.member;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 咨询管理
 * 
 * @Filename: MemberAskController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class AppMemberAskController extends BaseController {

    @Resource
    private IProductAskService   productAskService;

    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private IMemberService       memberService;

    /**
     * 跳转到 我的咨询界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-question.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> question(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      Map<String, Object> dataMap,
                                                                      Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        queryMap.put("q_userId", memberId.toString());
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductAsk>> serviceResult = productAskService
            .getProductAsksWithInfo(feignUtil);
        pager = serviceResult.getPager();

        dataMap.put("askList", serviceResult.getResult());
        dataMap.put("askCount", pager.getRowsCount());
        dataMap.put("pageIndex", pager.getPageIndex());
        dataMap.put("pageSize", pager.getPageSize());

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * ajax异步加载更多咨询
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-morequestion.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductAsk>> moreQuestion(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       Map<String, Object> dataMap,
                                                                       Integer pageIndex,
                                                                       Integer memberId) {
        HttpJsonResult<List<ProductAsk>> jsonResult = new HttpJsonResult<List<ProductAsk>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        queryMap.put("q_userId", memberId + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductAsk>> serviceResult = productAskService
            .getProductAsksWithInfo(feignUtil);

        jsonResult.setData(serviceResult.getResult());

        return jsonResult;
    }

    /**
     * 商品咨询提交
     * @param request
     * @param response
     * @param dataMap
     * @param
     * @return
     */
    @RequestMapping(value = "/app-savequestion.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<ProductAsk> saveQuestion(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Map<String, Object> dataMap,
                                                                 Integer productId,
                                                                 String askContent) {
        Member members = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Product> productResult = productFrontService.getProductById(productId);
        if (!productResult.getSuccess()) {
            return new HttpJsonResult<ProductAsk>(productResult.getMessage());
        }

        Product product = productResult.getResult();
        if (product == null) {
            return new HttpJsonResult<ProductAsk>("商品信息获取失败");
        }

        Member member = memberService.getMemberById(members.getId()).getResult();

        ProductAsk productAsk = new ProductAsk();
        productAsk.setSellerId(product.getSellerId());
        productAsk.setProductId(productId);
        productAsk.setUserId(member.getId());
        productAsk.setUserName(member.getName());
        productAsk.setAskContent(askContent);
        productAsk.setState(ProductAsk.STATE_1);
        FeignObjectUtil feignObjectUtil = FeignObjectUtil.getFeignUtil();
        feignObjectUtil.setProductAsk(productAsk);
        feignObjectUtil.setMember(member);
        ServiceResult<ProductAsk> serviceResult = productAskService.saveProductAsk(feignObjectUtil);
        HttpJsonResult<ProductAsk> jsonResult = new HttpJsonResult<ProductAsk>();

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<ProductAsk>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

}
