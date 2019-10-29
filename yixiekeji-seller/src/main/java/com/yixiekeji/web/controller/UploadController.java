package com.yixiekeji.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.util.Uploader;

@Controller
public class UploadController extends BaseController {

    @Resource
    private DomainUrlUtil domainUrlUtil;

    @RequestMapping("/uploadUEImage")
    public void uploadUEImage(MultipartFile upfile, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        Uploader up = new Uploader(request);
        up.setSavePath("/upload");
        String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); //单位KB
        up.upload(upfile, domainUrlUtil.getImageResources());

        String callback = request.getParameter("callback");
        String result = "{\"name\":\"" + up.getFileName() + "\", \"originalName\": \""
                        + up.getOriginalName() + "\", \"size\": " + up.getSize() + ", \"state\": \""
                        + up.getState() + "\", \"type\": \"" + up.getType() + "\", \"url\": \""
                        + up.getUrl() + "\"}";

        result = result.replaceAll("\\\\", "\\\\");
        if (callback == null) {
            response.getWriter().print(result);
        } else {
            response.getWriter().print("<script>" + callback + "(" + result + ")</script>");
        }
    }

}
