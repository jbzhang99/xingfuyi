package com.yixiekeji.web.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 商家相关操作action
 *                       
 */
@Controller
public class RegionsController extends BaseController {

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
    @RequestMapping(value = "/system/getProvince.html", method = { RequestMethod.GET })
    public void getProvince(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        ServletContext sc = request.getSession().getServletContext();

        List<Regions> list = new ArrayList<Regions>();

        Object pros = sc.getAttribute("provinceList");
        if (pros == null) {
            log.debug("没有缓存，查询数据库。。。");
            list = regionsService.getProvince();
            sc.setAttribute("provinceList", list);
        } else {
            log.debug("有缓存，直接返回。。。");
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
    @RequestMapping(value = "/system/getChildrenArea.html", method = { RequestMethod.GET })
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
}
