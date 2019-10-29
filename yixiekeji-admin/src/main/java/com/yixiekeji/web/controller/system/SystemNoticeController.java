package com.yixiekeji.web.controller.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.yixiekeji.entity.operate.SystemNotice;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.system.ISystemNoticeService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 商城公告相关action
 *                       
 * @Filename: SystemNotice.java
 * @Version: 1.0
 * 
 */
@Controller
@RequestMapping(value = "/admin/systemNotice", produces = "application/json;charset=UTF-8")
public class SystemNoticeController extends BaseController {
    @Resource
    private ISystemNoticeService systemNoticeService;

    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, ModelMap dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        return "/admin/operate/systemnotice/systemnoticelist";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SystemNotice>> list(HttpServletRequest request,
                                                                 ModelMap dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SystemNotice>> serviceResult = systemNoticeService.page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<SystemNotice>> jsonResult = new HttpJsonResult<List<SystemNotice>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, ModelMap dataMap, Integer id) {
        if (id != null) {
            SystemNotice systemNotice = systemNoticeService.getSystemNoticeById(id).getResult();
            if (!isNull(systemNotice.getContent())) {
                systemNotice.setContent(systemNotice.getContent().replace(
                    "${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
            }
            dataMap.put("obj", systemNotice);
        }

        return "/admin/operate/systemnotice/systemnoticeedit";
    }

    /**
     * 新增/编辑
     * @param request
     * @param response
     * @param cate
     */
    @RequestMapping(value = "doAdd", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> doAdd(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       SystemNotice systemNotice) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Integer> serviceResult = null;
        SystemAdmin admin = WebAdminSession.getAdminUser(request);
        try {
            if (!isNull(systemNotice.getContent())) {
                systemNotice.setContent(systemNotice.getContent().replace(
                    "${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
            }

            if (systemNotice.getId() != null && 0 != systemNotice.getId()) {
                systemNotice.setUpdateTime(new Date());
                systemNotice.setUpdateUserId(admin.getId());
                serviceResult = systemNoticeService.updateSystemNotice(systemNotice);
            } else {
                systemNotice.setCreateTime(new Date());
                systemNotice.setCreateUserId(admin.getId());
                serviceResult = systemNoticeService.saveSystemNotice(systemNotice);
            }
            jsonResult.setData(serviceResult.getResult() > 0);
        } catch (Exception e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 删除
     * @param request
     * @param response
     * @param cate
     */
    @RequestMapping(value = "del", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> del(HttpServletRequest request,
                                                     HttpServletResponse response, Integer id) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        try {
            ServiceResult<Boolean> sr = systemNoticeService.del(id);
            if (!sr.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                    throw new RuntimeException(sr.getMessage());
                } else {
                    throw new BusinessException(sr.getMessage());
                }
            }
        } catch (Exception e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 置顶
     * @param request
     * @param response
     * @param id
     * @param type 类型 1-置顶 2-取消置顶
     * @return
     */
    @RequestMapping(value = "totop", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<String> totop(HttpServletRequest request,
                                                      HttpServletResponse response, Integer id,
                                                      Integer type) {
        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        String msg = "";
        try {
            if (isNull(type))
                throw new BusinessException("未知操作");
            SystemNotice notice = systemNoticeService.getSystemNoticeById(id).getResult();
            if (isNull(notice))
                throw new BusinessException("数据为空，请稍后重试");
            if (type == 1) {
                notice.setIsTop(SystemNotice.IS_TOP_YES);
                msg = "置顶成功";
            } else {
                notice.setIsTop(SystemNotice.IS_TOP_NO);
                msg = "取消置顶成功";
            }
            ServiceResult<Integer> sr = systemNoticeService.updateSystemNotice(notice);
            if (!sr.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                    throw new RuntimeException(sr.getMessage());
                } else {
                    throw new BusinessException(sr.getMessage());
                }
            }
        } catch (Exception e) {
            msg = e.getMessage();
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        jsonResult.setData(msg);
        return jsonResult;
    }

}
