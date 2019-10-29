package com.yixiekeji.web.controller.order;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Invoice;
import com.yixiekeji.service.member.IInvoiceService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 购物流程-订单发票<br>
 * 本controller中得请求都需要登录才能访问
 * 
 * @Filename: OrdersInvoiceController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class OrdersInvoiceController extends BaseController {

    @Resource
    private IInvoiceService invoiceService;

    /**
    * 获取发票信息
    * @param request
    * @param response
    * @param invoiceId
    * @return
    */
    @RequestMapping(value = "order/getinvoicebyid.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Invoice> getinvoicebyid(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Integer invoiceId) {

        HttpJsonResult<Invoice> jsonResult = new HttpJsonResult<Invoice>();
        ServiceResult<Invoice> serviceResult = invoiceService.getInvoiceById(invoiceId);

        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Invoice>(serviceResult.getMessage());
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 保存发票抬头
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/saveinvoice.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Integer> saveInvoice(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             Invoice invoice) {

        Member member = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        serviceResult = invoiceService.saveInvoice(invoice, member.getId());

        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Integer>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 更新发票抬头
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/updateinvoice.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> updateInvoice(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Invoice invoice) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        serviceResult = invoiceService.updateInvoice(invoice);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 删除发票
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "order/deleteinvoice.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> delInvoice(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            Invoice invoice,
                                                            @RequestParam(value = "invoiceId", required = true) Integer invoiceId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        serviceResult = invoiceService.delInvoice(invoiceId);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

}
