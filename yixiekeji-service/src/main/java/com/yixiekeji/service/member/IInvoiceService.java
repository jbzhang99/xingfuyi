package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.order.Invoice;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "invoice")
@Service(value = "invoiceService")
public interface IInvoiceService {

    /**
     * 根据用户ID获取发票信息表，只取状态为1显示的记录（state=1）
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getInvoiceByMId", method = RequestMethod.GET)
    ServiceResult<List<Invoice>> getInvoiceByMId(@RequestParam("memberId") Integer memberId);

    /**
     * 根据id取得发票信息表
     * @param  invoiceId
     * @return
     */
    @RequestMapping(value = "getInvoiceById", method = RequestMethod.GET)
    ServiceResult<Invoice> getInvoiceById(@RequestParam("invoiceId") Integer invoiceId);

    /**
     * 保存发票信息表
     * 注意返回的是保存的ID
     * @param invoice
     * @param memberId
     * @return
     */
    @RequestMapping(value = "saveInvoice", method = RequestMethod.POST)
    ServiceResult<Integer> saveInvoice(@RequestBody Invoice invoice,
                                       @RequestParam("memberId") Integer memberId);

    /**
    * 更新发票信息表
    * @param  invoice
    * @return
    */
    @RequestMapping(value = "updateInvoice", method = RequestMethod.POST)
    ServiceResult<Integer> updateInvoice(Invoice invoice);

    /**
     * 删除发票
     * @param invoiceId
     * @return
     */
    @RequestMapping(value = "delInvoice", method = RequestMethod.GET)
    ServiceResult<Boolean> delInvoice(@RequestParam("invoiceId") Integer invoiceId);
}