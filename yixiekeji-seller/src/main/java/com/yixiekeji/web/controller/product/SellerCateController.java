package com.yixiekeji.web.controller.product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.ISellerCateService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 店铺分类
 */
@Controller
@RequestMapping(value = "seller/product/sellerCate")
public class SellerCateController extends BaseController {
    private String             baseUrl = "seller/product/sellercate/";

    @Resource
    private ISellerCateService sellerCateService;
    @Resource
    private DomainUrlUtil      domainUrlUtil;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "catelist";
    }

    @RequestMapping(value = "getByPid", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<SellerCate> getByPid(HttpServletRequest request,
                                                   @RequestParam(value = "id", required = true) Integer pid) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService.getByPid(pid,
            sellerUser.getSellerId());
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            return serviceResult.getResult();
        }
        return null;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService.getByPid(0,
            sellerUser.getSellerId());
        if (serviceResult.getSuccess() && serviceResult.getResult() != null) {
            dataMap.put("productCates", serviceResult.getResult());
        }
        return baseUrl + "cateadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> create(SellerCate cate, HttpServletRequest request,
                                         Map<String, Object> dataMap) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>(true);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (null == sellerUser) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/seller/login.html");
            return jsonResult;
        }
        cate.setSellerId(sellerUser.getSellerId());
        cate.setCreateId(sellerUser.getId());
        cate.setStatus(SellerCate.STATUS_1);
        //设置path
        cate.setPath(buildPath(cate.getPid()));
        ServiceResult<Boolean> serviceResult = sellerCateService.saveSellerCate(cate);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
            return jsonResult;
        }
        return jsonResult;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, Integer id, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<SellerCate> serviceResult = sellerCateService.getSellerCateById(id);
        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }
        SellerCate sellerCate = serviceResult.getResult();
        if (sellerCate == null) {
            return "seller/404";
        }
        if (!sellerCate.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        /**1.商品分类*/
        dataMap.put("cate", sellerCate);

        /**2.构造上级分类*/
        String[] cateIds = sellerCate.getPath().split("/");
        if (cateIds.length == 0) {
            //只构造出一级分类
            ServiceResult<List<SellerCate>> result1 = sellerCateService.getByPid(0,
                sellerUser.getSellerId());//一级分类

            //删除当前分类
            List<SellerCate> list = result1.getResult();
            Iterator<SellerCate> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getId() == id)
                    iterator.remove();
            }
            dataMap.put("B", list);
        }
        if (cateIds.length > 0) {
            Map<String, Object> cateMap = new HashMap<String, Object>();
            for (int i = 0; i < cateIds.length - 1; i++) {
                ServiceResult<List<SellerCate>> result1 = new ServiceResult<List<SellerCate>>();
                if (StringUtil.isEmpty(cateIds[i])) {
                    result1 = sellerCateService.getByPid(0, sellerUser.getSellerId());//一级分类
                } else {
                    result1 = sellerCateService.getByPid(Integer.valueOf(cateIds[i]),
                        sellerUser.getSellerId());
                }
                if (result1.getSuccess() && result1.getResult() != null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("cateId", cateIds[i + 1]);
                    map.put("cateList", result1.getResult());
                    if (i == 0) {
                        cateMap.put("B", map);//大类
                    } else if (i == 1) {
                        cateMap.put("M", map);//中类
                    } else {
                        cateMap.put("S", map);//小类
                    }
                }
            }
            dataMap.put("parentCate", cateMap);
        }
        return baseUrl + "cateedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> update(SellerCate cate, HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>(true);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (null == sellerUser) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/seller/login.html");
            return jsonResult;
        }
        ServiceResult<SellerCate> result = sellerCateService.getSellerCateById(cate.getId());
        if (!result.getSuccess()) {
            return new HttpJsonResult<Object>(result.getMessage());
        }
        SellerCate sellerCate = result.getResult();
        if (sellerCate == null) {
            return new HttpJsonResult<Object>("获取商家分类信息失败，请重试。");
        }
        if (!sellerCate.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Object>("您无权进行该操作，请重试。");
        }

        cate.setPath(buildPath(cate.getPid()));
        ServiceResult<Boolean> serviceResult = sellerCateService.updateSellerCate(cate);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>(true);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<Boolean> serviceResult = sellerCateService.delSellerCate(id,
            sellerUser.getSellerId());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 根据pid构造上级path
     * @param pid
     * @return
     */
    private String buildPath(Integer pid) {
        String path = "";
        ServiceResult<SellerCate> result = sellerCateService.getSellerCateById(pid);
        if (result.getResult() == null) {
            path = "/";//一级分类
        } else {
            if ("/".equals(result.getResult().getPath())) {
                path = result.getResult().getPath() + pid;
            } else {
                path = result.getResult().getPath() + "/" + pid;
            }
        }
        return path;
    }
}
