package com.yixiekeji.web.controller.member;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.RandomUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberApplyDrawing;
import com.yixiekeji.service.member.IMemberApplyDrawingService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 提款管理
 *                       
 * @Filename: MemberApplyDrawingController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class AppMemberApplyDrawingController extends BaseController {

    @Resource
    private IMemberApplyDrawingService memberApplyDrawingService;

    @Resource
    private IMemberService             memberService;

    /**
     * 跳转到 提现申请列表界面 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-applydrawing.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> applydrawing(HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          Map<String, Object> dataMap,
                                                                          Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", memberId.toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MemberApplyDrawing>> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawings(feignUtil);

        dataMap.put("memberApplyDrawings", serviceResult.getResult());

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 返回商品列表页 json 数据
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-applydrawingJson.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<MemberApplyDrawing>> applydrawingJson(HttpServletRequest request,
                                                                                   HttpServletResponse response,
                                                                                   Integer memberId) {
        HttpJsonResult<List<MemberApplyDrawing>> jsonResult = new HttpJsonResult<List<MemberApplyDrawing>>();

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", memberId.toString());

        String pageNumStr = request.getParameter("pageNum");
        int pageNum = ConvertUtil.toInt(pageNumStr, 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageNum);

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<MemberApplyDrawing>> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawings(feignUtil);
        if (!serviceResult.getSuccess()) {
            return jsonResult;
        }

        jsonResult.setData(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    /**
     * 跳转到申请提款页面
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-applydrawinginfo.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<BigDecimal> applydrawinginfo(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     Map<String, Object> dataMap,
                                                                     Integer memberId) {
        HttpJsonResult<BigDecimal> jsonResult = new HttpJsonResult<BigDecimal>();

        ServiceResult<Member> serviceResult = memberService.getMemberById(memberId);

        //账户余额 默认为0
        BigDecimal balance = new BigDecimal(0);
        if (serviceResult.getResult() != null) {
            Member memberDb = serviceResult.getResult();
            balance = memberDb.getBalance();

        }
        jsonResult.setData(balance);
        return jsonResult;
    }

    /**
     * 提现申请提交 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/app-doapplydrawing.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Map<String, Object>> withdrawDepositSubmit(HttpServletRequest request,
                                                                                   HttpServletResponse response,
                                                                                   String bank,
                                                                                   String bankCode,
                                                                                   BigDecimal money,
                                                                                   Integer memberId,
                                                                                   Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member member = memberService.getMemberById(memberId).getResult();

        MemberApplyDrawing memberApplyDrawing = new MemberApplyDrawing();
        memberApplyDrawing.setMemberId(member.getId());
        memberApplyDrawing.setMemberName(member.getName());
        //设置提现编号
        memberApplyDrawing.setCode(RandomUtil.getOrderSn());
        memberApplyDrawing.setState(ConstantsEJS.MEMBER_DRAWING_STATE_1);
        memberApplyDrawing.setBank(bank);
        memberApplyDrawing.setBankCode(bankCode);
        memberApplyDrawing.setMoney(money);

        ServiceResult<Integer> serviceResult = memberApplyDrawingService
            .saveMemberApplyDrawing(memberApplyDrawing);

        String backUrl = "app-applydrawing.html";

        if (!serviceResult.getSuccess()) {
            dataMap.put("memberApplyDrawing", memberApplyDrawing);
            jsonResult.setMessage("已经申请过提现了，请耐心等待！");

            ServiceResult<Member> serviceResultMember = memberService.getMemberById(member.getId());

            //账户余额 默认为0
            BigDecimal balance = new BigDecimal(0);
            if (serviceResultMember.getResult() != null) {
                Member memberDb = serviceResultMember.getResult();
                balance = memberDb.getBalance();
            }
            dataMap.put("balance", balance);
        }

        jsonResult.setData(dataMap);
        jsonResult.setBackUrl(backUrl);
        return jsonResult;
    }
}
