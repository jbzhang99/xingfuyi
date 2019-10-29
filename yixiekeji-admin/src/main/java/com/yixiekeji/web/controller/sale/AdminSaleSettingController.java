package com.yixiekeji.web.controller.sale;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.sale.SaleSetting;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.sale.ISaleScaleService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 
 * 分销设置                     
 * @Filename: AdminSaleSetting.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "admin/sale/salesetting", produces = "application/json;charset=UTF-8")
public class AdminSaleSettingController extends BaseController {

    @Resource
    private ISaleScaleService saleScaleService;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        ServiceResult<SaleSetting> result = saleScaleService.getSaleSettingDesc();
        if (result.getSuccess()) {
            SaleSetting saleSetting = result.getResult();
            dataMap.put("saleSetting", saleSetting);
        }
        return "admin/sale/salesetting/salesetting";
    }

    /**
     * 更新设置
     * @param seller
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(SaleSetting saleSetting, HttpServletRequest request,
                         Map<String, Object> dataMap) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        saleSetting.setCreateId(adminUser.getId());
        saleSetting.setCreateName(adminUser.getName());

        ServiceResult<Integer> result = saleScaleService.saveSaleSetting(saleSetting);
        if (result.getSuccess()) {
            dataMap.put("message", "修改成功");
        } else {
            dataMap.put("message", "修改失败");
        }
        ServiceResult<SaleSetting> resultSetting = saleScaleService.getSaleSettingDesc();
        if (resultSetting.getSuccess()) {
            SaleSetting saleSetting2 = resultSetting.getResult();
            dataMap.put("saleSetting", saleSetting2);
        }
        return "admin/sale/salesetting/salesetting";
    }

}
