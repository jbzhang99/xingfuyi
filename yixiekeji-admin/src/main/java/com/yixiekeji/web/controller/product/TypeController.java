package com.yixiekeji.web.controller.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.entity.system.Code;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.product.IProductTypeService;
import com.yixiekeji.util.FeignProjectUtil;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 商品类型
 */
@Controller
@RequestMapping(value = "admin/product/type", produces = "application/json;charset=UTF-8")
public class TypeController extends BaseController {
    private String              baseUrl = "admin/product/type/";

    @Resource
    private IProductTypeService productTypeService;

    @Resource
    private DomainUrlUtil       domainUrlUtil;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "typelist";
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductType>> list(HttpServletRequest request,
                                                                Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductType>> serviceResult = productTypeService
            .pageProductType(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ProductType>> jsonResult = new HttpJsonResult<List<ProductType>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) {
        Code code = new Code();
        //        code.setUseYn(AdminConstants.USE_YN_Y);
        dataMap.put("code", code);
        return baseUrl + "typeadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> create(ProductType type, HttpServletRequest request,
                                         Map<String, Object> dataMap) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }
        type.setCreateId(user.getId());

        //规格
        if (!StringUtil.isEmpty(type.getProductNormIds())) {
            String[] normIds = type.getProductNormIds().split(",");
            int[] array = new int[normIds.length];
            for (int i = 0; i < normIds.length; i++) {
                if (!StringUtil.isEmpty(normIds[i])) {
                    array[i] = Integer.valueOf(normIds[i]).intValue();
                }
            }
            //排序
            int length = array.length - 1;
            for (int out = length; out > 0; out--) {
                for (int in = 0; in < out; in++) {
                    if (array[in] > array[in + 1]) {
                        int s = array[in];
                        array[in] = array[in + 1];
                        array[in + 1] = s;
                    }
                }
            }
            //放入原数据集
            String newstr = "";
            for (int i : array) {
                newstr += i + ",";
            }
            type.setProductNormIds(newstr.substring(0, newstr.length() - 1));
        }

        FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
        feignProjectUtil.setProductType(type);
        feignProjectUtil
            .setProductTypeAttrList(processAttr(request.getParameter("attrVal"), user.getId()));

        ServiceResult<Boolean> serviceResult = productTypeService
            .saveProductTypeMap(feignProjectUtil);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(Integer id, Map<String, Object> dataMap) {
        ServiceResult<ProductType> serviceResult = productTypeService.getProductTypeById(id);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }

        ServiceResult<List<ProductTypeAttr>> result = productTypeService.getAttrByTypeId(id);
        if (!result.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(result.getCode())) {
                throw new RuntimeException(result.getMessage());
            }
        }

        List<ProductTypeAttr> attrList = new ArrayList<ProductTypeAttr>();
        List<ProductTypeAttr> custAttrList = new ArrayList<ProductTypeAttr>();
        dataMap.put("type", serviceResult.getResult());//商品类型
        dataMap.put("attr", attrList);//检索属性
        dataMap.put("custAttr", custAttrList);//自定义属性

        if (null != result.getResult()) {
            List<ProductTypeAttr> list = result.getResult();
            for (int i = 0; i < list.size(); i++) {
                ProductTypeAttr attr = list.get(i);
                if (attr.getType() == 1) {
                    attrList.add(attr);
                } else {
                    custAttrList.add(attr);
                }
            }
        }

        return baseUrl + "typeedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> update(ProductType type, HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;

        }

        //规格
        if (!StringUtil.isEmpty(type.getProductNormIds())) {
            String[] normIds = type.getProductNormIds().split(",");
            int[] array = new int[normIds.length];
            for (int i = 0; i < normIds.length; i++) {
                if (!StringUtil.isEmpty(normIds[i])) {
                    array[i] = Integer.valueOf(normIds[i]).intValue();
                }
            }
            //排序
            int length = array.length - 1;
            for (int out = length; out > 0; out--) {
                for (int in = 0; in < out; in++) {
                    if (array[in] > array[in + 1]) {
                        int s = array[in];
                        array[in] = array[in + 1];
                        array[in + 1] = s;
                    }
                }
            }
            //放入原数据集
            String newstr = "";
            for (int i : array) {
                newstr += i + ",";
            }
            type.setProductNormIds(newstr.substring(0, newstr.length() - 1));
        }

        FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
        feignProjectUtil.setProductType(type);
        feignProjectUtil
            .setProductTypeAttrList(processAttr(request.getParameter("attrVal"), user.getId()));

        ServiceResult<Boolean> serviceResult = productTypeService
            .updateProductTypeMap(feignProjectUtil);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<Boolean> serviceResult = productTypeService.delProductType(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "getById", method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Object> getById(Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<ProductType> serviceResult = productTypeService.getProductTypeById(id);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }
        jsonResult.setRows(serviceResult.getResult());
        return jsonResult;
    }

    private List<ProductTypeAttr> processAttr(String str, Integer createId) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        JSONArray jsonArray = JSONArray.parseArray(str);
        if (null != jsonArray && jsonArray.size() > 0) {
            List<ProductTypeAttr> attrs = new ArrayList<ProductTypeAttr>();
            for (int i = 0; i < jsonArray.size(); i++) {
                ProductTypeAttr attr = new ProductTypeAttr();
                Map<String, String> map = (Map<String, String>) jsonArray.get(i);
                attr.setName(map.get("name"));
                attr.setType(Integer.valueOf(map.get("type")));
                String val = map.get("val");
                if (!StringUtil.isEmpty(val)) {
                    attr.setValue(val);
                }
                String productTypeAttrId = map.get("id");
                attr.setId(ConvertUtil.toInt(productTypeAttrId, 0));
                attr.setCreateId(createId);
                attrs.add(attr);
            }
            return attrs;
        }
        return null;
    }
}
