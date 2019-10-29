package com.yixiekeji.model.member;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.member.InvoiceReadDao;
import com.yixiekeji.dao.shop.write.member.InvoiceWriteDao;
import com.yixiekeji.entity.order.Invoice;

@Component(value = "invoiceModel")
public class InvoiceModel {
    private static Logger   log = LoggerFactory.getLogger(InvoiceModel.class);

    @Resource
    private InvoiceWriteDao invoiceWriteDao;

    @Resource
    private InvoiceReadDao  invoiceReadDao;

    /**
     * 根据用户ID获取发票信息表，只取状态为1显示的记录（state=1）
     * @param memberId
     * @return
     */
    public List<Invoice> getInvoiceByMId(Integer memberId) {
        return invoiceWriteDao.getByMId(memberId);
    }

    /**
    * 根据id取得发票信息表
    * @param  invoiceId
    * @return
    */
    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceWriteDao.get(invoiceId);
    }

    /**
     * 保存发票信息表
     * @param  invoice
     * @return
     */
    public Integer saveInvoice(Invoice invoice, Integer memberId) {
        // 参数校验
        if (invoice == null) {
            throw new BusinessException("发票信息不能为空，请重试！");
        } else if (StringUtil.isEmpty(invoice.getContent())) {
            throw new BusinessException("发票抬头不能为空，请重试！");
        }
        invoice.setCreateId(memberId);
        invoice.setState(Invoice.STATE_1);
        Integer save = invoiceWriteDao.save(invoice);
        if (save.intValue() == 0) {
            throw new BusinessException("发票保存失败！");
        }
        return invoice.getId();
    }

    /**
    * 更新发票信息表
    * @param  invoice
    * @return
    */
    public Integer updateInvoice(Invoice invoice) {
        return invoiceWriteDao.update(invoice);
    }

    /**
     * 删除发票
     * @param invoiceId
     * @param request
     * @return
     */
    public boolean delInvoice(Integer invoiceId) {
        if (invoiceId == null) {
            log.error("发票ID为空。");
            throw new BusinessException("发票ID为空，请重试！");
        }

        int count = invoiceWriteDao.delete(invoiceId);
        return count > 0;
    }
}
