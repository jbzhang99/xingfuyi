package com.yixiekeji.web.controller.operate;

import java.util.Date;
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
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.operate.CourierCompany;
import com.yixiekeji.service.operate.ICourierCompanyService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;

/**
 * 快递公司管理controller
 *                       
 * @Filename: AdminCourierCompanyController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/operate/courierCompany", produces = "application/json;charset=UTF-8")
public class AdminCourierCompanyController extends BaseController {
    @Resource
    private ICourierCompanyService courierCompanyService;

    @Resource
    private DomainUrlUtil          domainUrlUtil;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        return "admin/operate/couriercompany/list";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<CourierCompany>> list(HttpServletRequest request,
                                                                   Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<CourierCompany>> serviceResult = courierCompanyService.page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<CourierCompany>> jsonResult = new HttpJsonResult<List<CourierCompany>>();
        jsonResult.setRows((List<CourierCompany>) serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 新增页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request) {
        return "admin/operate/couriercompany/edit";
    }

    /**
     * 编辑页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, Map<String, Object> dataMap, String id) {
        CourierCompany obj = this.courierCompanyService.getCourierCompanyById(Integer.valueOf(id))
            .getResult();
        dataMap.put("obj", obj);
        return "admin/operate/couriercompany/edit";
    }

    /**
     * 设计快递打印模板
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "editCourierCompany", method = { RequestMethod.GET })
    public String editCourierCompany(HttpServletRequest request, Map<String, Object> dataMap,
                                     Integer id) {
        CourierCompany obj = this.courierCompanyService.getCourierCompanyById(id).getResult();
        dataMap.put("obj", obj);
        return "admin/operate/couriercompany/couriercompany";
    }

    /**
     * 添加快递公司
     * @param request
     * @param response
     * @param news
     * @return
     */
    @RequestMapping(value = "doAdd", method = { RequestMethod.POST })
    public String doAdd(HttpServletRequest request, HttpServletResponse response,
                        CourierCompany obj) {

        // 上传图片
        String image = UploadUtil.getInstance().courierUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageCourierCompany");
        if (image != null && !"".equals(image)) {
            obj.setImagePath(image);
        }

        obj.setCreateTime(new Date());
        if (obj.getId() != null && obj.getId() != 0) {
            this.courierCompanyService.updateCourierCompany(obj);
        } else {
            obj.setCreateTime(new Date());
            obj.setSellerId(0);
            this.courierCompanyService.saveCourierCompany(obj);
        }

        return "redirect:/admin/operate/courierCompany";
    }

    /**
     * 删除
     * @param request
     * @param response
     * @param obj
     * @return
     */
    @RequestMapping(value = "del", method = { RequestMethod.GET })
    public void del(HttpServletRequest request, HttpServletResponse response, String id) {
        this.courierCompanyService.del(Integer.valueOf(id));
    }
}
