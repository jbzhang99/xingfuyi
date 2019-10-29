package com.yixiekeji.web.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.vo.system.RegionsVO;
import com.yixiekeji.web.controller.BaseController;

/**
 * 地区controller
 * 
 * @Filename: SellerRegionsController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/system/regions")
public class SellerRegionsController extends BaseController {

    @Resource
    private IRegionsService regionsService;

    /**
     * 获取省<br>
     * 查询一次后不再查询数据库，放入ServletContext以减少数据库访问
     * @param request
     * @param response
     * @return 返回json形式的数据
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getProvince", method = { RequestMethod.GET })
    public void getProvince(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        ServletContext sc = request.getSession().getServletContext();

        List<Regions> list = new ArrayList<Regions>();

        Object pros = sc.getAttribute("provinceList");
        if (pros == null) {
            list = regionsService.getProvince();
            sc.setAttribute("provinceList", list);
        } else {
            list = (List<Regions>) pros;
        }

        Gson obj = new Gson();
        String json = obj.toJson(list);
        PrintWriter pw;
        try {
            pw = response.getWriter();
            pw.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以上级地区id获取其子地区<br>
     * @param request
     * @param response
     * @param p 上级地区id
     * @param type 地区类型
     * @return 返回json形式的数据
     */
    @RequestMapping(value = "getChildrenArea", method = { RequestMethod.GET })
    public void getCity(HttpServletRequest request, HttpServletResponse response, String p,
                        String type) {
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        if (StringUtil.isEmpty(p))
            return;
        List<Regions> list = regionsService.getChildrenArea(Integer.valueOf(p), type);

        Gson obj = new Gson();
        String json = obj.toJson(list);
        PrintWriter pw;
        try {
            pw = response.getWriter();
            pw.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "getAllArea", method = { RequestMethod.GET })
    public String getAllArea(HttpServletRequest request, Map<String, Object> dataMap) {
        List<RegionsVO> allArea = this.regionsService.getAllArea();
        dataMap.put("allArea", allArea);
        return "seller/operate/sellertransport/all_area";
    }

    /**
     * 根据parentId获取其子地区
     * @param parentId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "getRegionByParentId", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Regions>> getRegionByParentId(Integer parentId,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           Map<String, Object> dataMap) {

        ServiceResult<List<Regions>> serviceResult = regionsService.getRegionsByParentId(parentId);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Regions>> jsonResult = new HttpJsonResult<List<Regions>>();
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }
}
