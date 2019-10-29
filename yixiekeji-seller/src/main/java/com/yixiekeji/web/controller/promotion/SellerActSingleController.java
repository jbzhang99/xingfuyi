package com.yixiekeji.web.controller.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 单品立减管理controller
 *                       
 * @Filename: SellerActSingleController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/promotion/single")
public class SellerActSingleController extends BaseController {

    @Resource
    private IActSingleService actSingleService;
    @Resource
    private IProductService   productService;

    /**
     * 单品立减列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/promotion/single/actsinglelist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ActSingle>> list(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ActSingle>> serviceResult = actSingleService.getActSingles(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<ActSingle> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<ActSingle>> jsonResult = new HttpJsonResult<List<ActSingle>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        dataMap.put("sellerId", sellerUser.getSellerId().toString());
        return "seller/promotion/single/actsingleadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(ActSingle actSingle, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        actSingle.setCreateUserId(userId);
        actSingle.setCreateUserName(sellerUser.getName());
        actSingle.setUpdateUserId(sellerUser.getId());
        actSingle.setUpdateUserName(sellerUser.getName());

        actSingle.setStatus(ActSingle.STATUS_1);
        actSingle.setSellerId(sellerUser.getSellerId());

        // 组装活动商品
        String[] productIds = request.getParameterValues("ids");
        if (productIds == null || productIds.length == 0) {
            dataMap.put("sellerId", sellerUser.getSellerId().toString());
            dataMap.put("actSingle", actSingle);
            dataMap.put("message", "请选择参加活动的商品。");
            return "seller/promotion/single/actsingleadd";
        }
        actSingle.setProductIds(this.getIds(productIds));

        ServiceResult<Boolean> serviceResult = actSingleService.saveActSingle(actSingle);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("actSingle", actSingle);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/promotion/single/actsingleadd";
            }
        }
        return "redirect:/seller/promotion/single";
    }

    private String getIds(String[] productIds) {
        Set<String> idSet = new HashSet<String>();
        String ids = ",";
        for (String id : productIds) {
            if (!StringUtil.isEmpty(id, true) && idSet.add(id)) {
                ids += id + ",";
            }
        }
        return ids;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, int actSingleId, Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        dataMap.put("sellerId", sellerUser.getSellerId().toString());

        ServiceResult<ActSingle> serviceResult = actSingleService.getActSingleById(actSingleId);

        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }
        ActSingle actSingle = serviceResult.getResult();
        if (actSingle == null) {
            return "seller/404";
        }
        if (!actSingle.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        String ids = actSingle.getProductIds().substring(1, actSingle.getProductIds().length() - 1);
        ServiceResult<List<Product>> productsByIds = productService.getProductsByIds(getIds(ids));

        dataMap.put("actSingle", actSingle);
        dataMap.put("productList", productsByIds.getResult());
        dataMap.put("productNum",
            productsByIds.getResult() == null ? 0 : productsByIds.getResult().size());
        return "seller/promotion/single/actsingleedit";
    }

    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    public String detail(HttpServletRequest request, int actSingleId, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<ActSingle> serviceResult = actSingleService.getActSingleById(actSingleId);

        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }
        ActSingle actSingle = serviceResult.getResult();
        if (actSingle == null) {
            return "seller/404";
        }
        if (!actSingle.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        String ids = actSingle.getProductIds().substring(1, actSingle.getProductIds().length() - 1);
        ServiceResult<List<Product>> productsByIds = productService.getProductsByIds(getIds(ids));

        dataMap.put("actSingle", actSingle);
        dataMap.put("productList", productsByIds.getResult());
        return "seller/promotion/single/actsingledetail";
    }

    private List<Integer> getIds(String ids) {
        List<Integer> list = new ArrayList<Integer>();
        if (ids != null) {
            String[] split = ids.split(",");
            for (String id : split) {
                if (!StringUtil.isEmpty(id, true)) {
                    list.add(ConvertUtil.toInt(id, 0));
                }
            }
        }
        return list;
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(ActSingle actSingle, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        actSingle.setUpdateUserId(sellerUser.getId());
        actSingle.setUpdateUserName(sellerUser.getName());

        actSingle.setSellerId(sellerUser.getSellerId());

        // 组装活动商品
        String[] productIds = request.getParameterValues("ids");
        if (productIds == null || productIds.length == 0) {
            dataMap.put("sellerId", sellerUser.getSellerId().toString());
            dataMap.put("actSingle", actSingle);
            dataMap.put("message", "请选择参加活动的商品。");
            return "seller/promotion/single/actsingleedit";
        }
        actSingle.setProductIds(this.getIds(productIds));

        ServiceResult<Boolean> serviceResult = actSingleService.updateActSingle(actSingle);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("actSingle", actSingle);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/promotion/single/actsingleedit";
            }
        }
        return "redirect:/seller/promotion/single";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        ServiceResult<ActSingle> actSingleResult = actSingleService.getActSingleById(id);
        if (!actSingleResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(actSingleResult.getMessage());
        }
        if (actSingleResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("单品立减信息获取失败。");
        }
        ActSingle actSingle = actSingleResult.getResult();
        if (actSingle.getStatus().intValue() != ActSingle.STATUS_1
            && actSingle.getStatus().intValue() != ActSingle.STATUS_4) {
            return new HttpJsonResult<Boolean>("只能删除新建或审核失败的活动。");
        }

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != actSingle.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能删除自己店铺的活动。");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = actSingleService.deleteActSingle(id,
            sellerUser.getId(), sellerUser.getName());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "audit", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> audit(HttpServletRequest request,
                                                       @RequestParam("id") Integer id) {

        ServiceResult<ActSingle> actSingleRlt = actSingleService.getActSingleById(id);

        if (!actSingleRlt.getSuccess()) {
            return new HttpJsonResult<Boolean>(actSingleRlt.getMessage());
        }
        if (actSingleRlt.getResult() == null) {
            return new HttpJsonResult<Boolean>("活动信息获取失败。");
        }
        ActSingle actSingleDb = actSingleRlt.getResult();
        if (actSingleDb.getStatus().intValue() != ActSingle.STATUS_1
            && actSingleDb.getStatus().intValue() != ActSingle.STATUS_4) {
            return new HttpJsonResult<Boolean>("非新建或审核失败的活动不能提交审核。");
        }

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != actSingleDb.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能操作自己店铺的活动。");
        }

        ActSingle actSingle = new ActSingle();
        actSingle.setId(id);
        actSingle.setStatus(ActSingle.STATUS_2);
        actSingle.setUpdateUserId(sellerUser.getId());
        actSingle.setUpdateUserName(sellerUser.getName());
        actSingle.setUpdateTime(new Date());
        actSingle.setSellerId(sellerUser.getSellerId());
        ServiceResult<Boolean> serviceResult = actSingleService.updateActSingleStatus(actSingle);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> up(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {

        ServiceResult<ActSingle> actSingleRlt = actSingleService.getActSingleById(id);

        if (!actSingleRlt.getSuccess()) {
            return new HttpJsonResult<Boolean>(actSingleRlt.getMessage());
        }
        if (actSingleRlt.getResult() == null) {
            return new HttpJsonResult<Boolean>("活动信息获取失败。");
        }
        ActSingle actSingleDb = actSingleRlt.getResult();
        if (actSingleDb.getStatus().intValue() != ActSingle.STATUS_3) {
            return new HttpJsonResult<Boolean>("非审核通过状态的活动不能上架。");
        }

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != actSingleDb.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能操作自己店铺的活动。");
        }

        ActSingle actSingle = new ActSingle();
        actSingle.setId(id);
        actSingle.setStatus(ActSingle.STATUS_5);
        actSingle.setUpdateUserId(sellerUser.getId());
        actSingle.setUpdateUserName(sellerUser.getName());
        actSingle.setUpdateTime(new Date());
        actSingle.setSellerId(sellerUser.getSellerId());
        ServiceResult<Boolean> serviceResult = actSingleService.updateActSingleStatus(actSingle);
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> down(HttpServletRequest request,
                                                      @RequestParam("id") Integer id) {

        ServiceResult<ActSingle> actSingleRlt = actSingleService.getActSingleById(id);

        if (!actSingleRlt.getSuccess()) {
            return new HttpJsonResult<Boolean>(actSingleRlt.getMessage());
        }
        if (actSingleRlt.getResult() == null) {
            return new HttpJsonResult<Boolean>("活动信息获取失败。");
        }
        ActSingle actSingleDb = actSingleRlt.getResult();
        if (actSingleDb.getStatus().intValue() != ActSingle.STATUS_5) {
            return new HttpJsonResult<Boolean>("非上架状态的活动不能执行下架操作。");
        }

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (sellerUser.getSellerId().intValue() != actSingleDb.getSellerId().intValue()) {
            return new HttpJsonResult<Boolean>("只能操作自己店铺的活动。");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        ActSingle actSingle = new ActSingle();
        actSingle.setId(id);
        actSingle.setStatus(ActSingle.STATUS_6);
        actSingle.setUpdateUserId(sellerUser.getId());
        actSingle.setUpdateUserName(sellerUser.getName());
        actSingle.setUpdateTime(new Date());
        actSingle.setSellerId(sellerUser.getSellerId());
        ServiceResult<Boolean> serviceResult = actSingleService.updateActSingleStatus(actSingle);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
