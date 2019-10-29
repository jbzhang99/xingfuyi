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
import com.yixiekeji.web.util.WebFrontSession;

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
public class MemberApplyDrawingController extends BaseController {

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
    @RequestMapping(value = "/applydrawing.html", method = { RequestMethod.GET })
    public String applydrawing(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request);
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MemberApplyDrawing>> serviceResult = memberApplyDrawingService
            .getMemberApplyDrawings(feignUtil);

        dataMap.put("pagesize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("memberApplyDrawings", serviceResult.getResult());

        return "h5/member/applydrawing/applydrawinglist";
    }

    /**
     * 返回商品列表页 json 数据
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/applydrawingJson.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<MemberApplyDrawing>> applydrawingJson(HttpServletRequest request,
                                                                                   HttpServletResponse response) {
        HttpJsonResult<List<MemberApplyDrawing>> jsonResult = new HttpJsonResult<List<MemberApplyDrawing>>();
        Member member = WebFrontSession.getLoginedUser(request);

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId().toString());

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
    @RequestMapping(value = "/applydrawinginfo.html", method = { RequestMethod.GET })
    public String applydrawinginfo(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Member> serviceResult = memberService.getMemberById(member.getId());

        //账户余额 默认为0
        BigDecimal balance = new BigDecimal(0);
        if (serviceResult.getResult() != null) {
            Member memberDb = serviceResult.getResult();
            balance = memberDb.getBalance();

        }
        dataMap.put("balance", balance);
        return "h5/member/applydrawing/applydrawinginfo";
    }

    /**
     * 提现申请提交 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/doapplydrawing.html", method = { RequestMethod.POST })
    public String withdrawDepositSubmit(HttpServletRequest request, HttpServletResponse response,
                                        MemberApplyDrawing memberApplyDrawing,
                                        Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request);
        memberApplyDrawing.setMemberId(member.getId());
        memberApplyDrawing.setMemberName(member.getName());
        //设置提现编号
        memberApplyDrawing.setCode(RandomUtil.getOrderSn());
        memberApplyDrawing.setState(ConstantsEJS.MEMBER_DRAWING_STATE_1);
        ServiceResult<Integer> serviceResult = memberApplyDrawingService
            .saveMemberApplyDrawing(memberApplyDrawing);

        if (!serviceResult.getSuccess()) {
            dataMap.put("memberApplyDrawing", memberApplyDrawing);
            dataMap.put("message", "已经申请过提现了，请耐心等待！");

            ServiceResult<Member> serviceResultMember = memberService.getMemberById(member.getId());

            //账户余额 默认为0
            BigDecimal balance = new BigDecimal(0);
            if (serviceResultMember.getResult() != null) {
                Member memberDb = serviceResultMember.getResult();
                balance = memberDb.getBalance();
            }
            dataMap.put("balance", balance);

            return "h5/member/applydrawing/applydrawinginfo";
        }
        return "redirect:applydrawing.html";
    }
}
