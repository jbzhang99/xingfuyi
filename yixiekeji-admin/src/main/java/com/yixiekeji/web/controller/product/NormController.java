package com.yixiekeji.web.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.system.Code;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.product.IProductNormService;
import com.yixiekeji.util.FeignProjectUtil;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 商品规格
 */
@Controller
@RequestMapping(value = "admin/product/norm", produces = "application/json;charset=UTF-8")
public class NormController extends BaseController {
    private String              baseUrl = "admin/product/norm/";

    @Resource
    private IProductNormService productNormService;

    @Resource
    private DomainUrlUtil       domainUrlUtil;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_state", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "normlist";
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductNorm>> list(HttpServletRequest request,
                                                                Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductNorm>> serviceResult = productNormService.pageNorm(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ProductNorm>> jsonResult = new HttpJsonResult<List<ProductNorm>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "list_no_page", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductNorm>> listNoPage(HttpServletRequest request,
                                                                      Map<String, Object> dataMap) {
        ServiceResult<List<ProductNorm>> serviceResult = productNormService.listNoPage();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<ProductNorm>> jsonResult = new HttpJsonResult<List<ProductNorm>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    /**
     * 商品类型管理，添加或者编辑商品类型的时候，选择的商品规格状态为正常的
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list1", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductNorm>> list1(HttpServletRequest request,
                                                                 Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_state", String.valueOf(ProductNorm.Status.DEFAULT.getValue()));
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductNorm>> serviceResult = productNormService.pageNorm(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ProductNorm>> jsonResult = new HttpJsonResult<List<ProductNorm>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) {
        Code code = new Code();
        //        code.setUseYn(ConstantsJM.USE_YN_Y);
        dataMap.put("code", code);
        return baseUrl + "normadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> create(ProductNorm norm, HttpServletRequest request,
                                         Map<String, Object> dataMap) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }
        //norm
        norm.setCreateId(user.getId());
        norm.setUpdateId(user.getId());
        norm.setState(ProductNorm.Status.DEFAULT.getValue());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("norm", norm);
        List<ProductNormAttr> attrs = processAttrForAdd(request.getParameter("attrVal"),
            user.getId());
        if (null != attrs) {
            map.put("attr", attrs);
        }

        FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
        feignProjectUtil.setProductNormAttrList(attrs);
        feignProjectUtil.setProductNorm(norm);

        ServiceResult<Boolean> serviceResult = productNormService.saveNormMap(feignProjectUtil);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    private List<ProductNormAttr> processAttrForAdd(String str, Integer userId) {
        if (!StringUtil.isEmpty(str) && null != userId) {
            String attrs[] = str.split(";");
            List<ProductNormAttr> productNormAttrss = new ArrayList<ProductNormAttr>();
            for (int i = 0; i < attrs.length; i++) {
                ProductNormAttr productNormAttr = new ProductNormAttr();
                String attr[] = attrs[i].split(",");
                productNormAttr.setName(attr[0]);
                productNormAttr.setSort(Integer.valueOf(attr[1]));
                productNormAttr.setCreateId(userId);
                productNormAttrss.add(productNormAttr);
            }
            return productNormAttrss;
        }
        return null;
    }

    private List<ProductNormAttr> processAttr(String str, Integer userId) {
        if (!StringUtil.isEmpty(str) && null != userId) {
            String attrs[] = str.split(";");
            List<ProductNormAttr> productNormAttrss = new ArrayList<ProductNormAttr>();
            for (int i = 0; i < attrs.length; i++) {
                ProductNormAttr productNormAttr = new ProductNormAttr();
                String attr[] = attrs[i].split(",");
                Integer attrId = ConvertUtil.toInt(attr[0], -1);
                if (-1 != attrId) {
                    productNormAttr.setId(attrId);
                }
                productNormAttr.setName(attr[1]);
                productNormAttr.setSort(Integer.valueOf(attr[2]));
                productNormAttr.setCreateId(userId);
                productNormAttrss.add(productNormAttr);
            }
            return productNormAttrss;
        }
        return null;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(Integer id, Map<String, Object> dataMap) {
        ServiceResult<ProductNorm> serviceResult = productNormService.getNormById(id);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }
        dataMap.put("norm", serviceResult.getResult());

        ServiceResult<List<ProductNormAttr>> attr = productNormService.getAttrByNormId(id);
        if (!attr.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }
        dataMap.put("attr", attr.getResult());

        //判断是否可以添加商品规格值
        ServiceResult<List<ProductType>> serviceResult1 = productNormService.chkHasUsed(id);
        if (serviceResult1.getSuccess() && serviceResult1.getResult() != null
            && serviceResult1.getResult().size() > 0) {
            dataMap.put("tmp", "tmp");
        }
        return baseUrl + "normedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> update(ProductNorm norm, HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }
        norm.setUpdateId(user.getId());

        FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
        feignProjectUtil
            .setProductNormAttrList(processAttr(request.getParameter("attrVal"), user.getId()));
        feignProjectUtil.setProductNorm(norm);

        ServiceResult<Boolean> serviceResult = productNormService.updateNormMap(feignProjectUtil);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<Boolean> serviceResult = productNormService.delNorm(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<Object> getById(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<ProductNorm> serviceResult = productNormService.getNormById(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        jsonResult.setRows(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 商家商品类型编辑页面初始化ajax调用
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping(value = "getByIds", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<Object> getByIds(HttpServletRequest request,
                                                         @RequestParam("ids") String ids) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<List<ProductNorm>> serviceResult = productNormService.getNormByIds(ids);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        String str = "";
        for (ProductNorm norm : serviceResult.getResult()) {
            str += norm.getName() + ",";
        }
        if (str.length() > 0)
            str = str.substring(0, str.lastIndexOf(","));
        jsonResult.setRows(str);
        return jsonResult;
    }
}
