package com.yixiekeji.web.controller.pcindex;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.pcseller.PcSellerRecommend;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.pcseller.IPcSellerRecommendService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * PC端商家推荐类型管理controller
 *                       
 * @Filename: PcSellerRecommendController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/pcindex/recommend")
public class SellerPcRecommendController extends BaseController {

    @Resource
    private IPcSellerRecommendService pcSellerRecommendService;

    /**
     * PC端商家推荐类型列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/pcindex/recommend/pcsellerrecommendlist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<PcSellerRecommend>> list(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<PcSellerRecommend>> serviceResult = pcSellerRecommendService
            .getPcSellerRecommends(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<PcSellerRecommend> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<PcSellerRecommend>> jsonResult = new HttpJsonResult<List<PcSellerRecommend>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "seller/pcindex/recommend/pcsellerrecommendadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(PcSellerRecommend pcSellerRecommend, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        pcSellerRecommend.setCreateUserId(userId);
        pcSellerRecommend.setCreateUserName(sellerUser.getName());
        pcSellerRecommend.setUpdateUserId(sellerUser.getId());
        pcSellerRecommend.setUpdateUserName(sellerUser.getName());

        pcSellerRecommend.setStatus(PcSellerRecommend.STATUS_2);
        pcSellerRecommend.setSellerId(sellerUser.getSellerId());

        ServiceResult<Boolean> serviceResult = pcSellerRecommendService
            .savePcSellerRecommend(pcSellerRecommend);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcSellerRecommend", pcSellerRecommend);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/recommend/pcsellerrecommendadd";
            }
        }
        return "redirect:/seller/pcindex/recommend";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, int pcSellerRecommendId,
                       Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<PcSellerRecommend> serviceResult = pcSellerRecommendService
            .getPcSellerRecommendById(pcSellerRecommendId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/recommend/pcsellerrecommendlist";
            }
        }
        PcSellerRecommend pcSellerRecommend = serviceResult.getResult();
        if (pcSellerRecommend == null) {
            return "seller/404";
        }
        if (!pcSellerRecommend.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("pcSellerRecommend", pcSellerRecommend);
        return "seller/pcindex/recommend/pcsellerrecommendedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(PcSellerRecommend pcSellerRecommend, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<PcSellerRecommend> pcSellerRecommendResult = pcSellerRecommendService
            .getPcSellerRecommendById(pcSellerRecommend.getId());

        if (!pcSellerRecommendResult.getSuccess()) {
            return "seller/500";
        }
        PcSellerRecommend dbPcSellerRecommend = pcSellerRecommendResult.getResult();
        if (dbPcSellerRecommend == null) {
            return "seller/404";
        }
        if (!dbPcSellerRecommend.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        pcSellerRecommend.setUpdateUserId(sellerUser.getId());
        pcSellerRecommend.setUpdateUserName(sellerUser.getName());

        ServiceResult<Boolean> serviceResult = pcSellerRecommendService
            .updatePcSellerRecommend(pcSellerRecommend);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcSellerRecommend", pcSellerRecommend);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/recommend/pcsellerrecommendedit";
            }
        }
        return "redirect:/seller/pcindex/recommend";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<PcSellerRecommend> pcSellerRecommendResult = pcSellerRecommendService
            .getPcSellerRecommendById(id);
        if (!pcSellerRecommendResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(pcSellerRecommendResult.getMessage());
        }
        if (pcSellerRecommendResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("轮播图信息获取失败");
        }
        if (!pcSellerRecommendResult.getResult().getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Boolean>("您无权操作该数据");
        }
        if (pcSellerRecommendResult.getResult().getStatus().equals(PcSellerRecommend.STATUS_1)) {
            return new HttpJsonResult<Boolean>("正在使用的轮播图不能删除");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = pcSellerRecommendService.deletePcSellerRecommend(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> up(HttpServletRequest request,
                                                   @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        PcSellerRecommend pcSellerRecommend = new PcSellerRecommend();
        pcSellerRecommend.setId(id);
        pcSellerRecommend.setStatus(PcSellerRecommend.STATUS_1);
        pcSellerRecommend.setUpdateUserId(sellerUser.getId());
        pcSellerRecommend.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = pcSellerRecommendService
            .updatePcSellerRecommend(pcSellerRecommend);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "pre", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> pre(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        PcSellerRecommend pcSellerRecommend = new PcSellerRecommend();
        pcSellerRecommend.setId(id);
        pcSellerRecommend.setStatus(PcSellerRecommend.STATUS_2);
        pcSellerRecommend.setUpdateUserId(sellerUser.getId());
        pcSellerRecommend.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = pcSellerRecommendService
            .updatePcSellerRecommend(pcSellerRecommend);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> down(HttpServletRequest request,
                                                     @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        PcSellerRecommend pcSellerRecommend = new PcSellerRecommend();
        pcSellerRecommend.setId(id);
        pcSellerRecommend.setStatus(PcSellerRecommend.STATUS_3);
        pcSellerRecommend.setUpdateUserId(sellerUser.getId());
        pcSellerRecommend.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = pcSellerRecommendService
            .updatePcSellerRecommend(pcSellerRecommend);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
