package com.yixiekeji.service.sale;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.sale.SaleApplyMoney;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "saleApplyMoney")
@Service(value = "saleApplyMoneyService")
public interface ISaleApplyMoneyService {

    /**
     * 根据id取得提款申请表
     * @param  saleApplyMoneyId
     * @return
     */
    @RequestMapping(value = "getSaleApplyMoneyById", method = RequestMethod.GET)
    ServiceResult<SaleApplyMoney> getSaleApplyMoneyById(@RequestParam("saleApplyMoneyId") Integer saleApplyMoneyId);

    /**
     * 保存提款申请表
     * @param  saleApplyMoney
     * @return
     */
    @RequestMapping(value = "saveSaleApplyMoney", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleApplyMoney(SaleApplyMoney saleApplyMoney);

    /**
    * 更新提款申请表
    * @param  saleApplyMoney
    * @return
    */
    @RequestMapping(value = "updateSaleApplyMoney", method = RequestMethod.POST)
    ServiceResult<Integer> updateSaleApplyMoney(SaleApplyMoney saleApplyMoney);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSaleApplyMoneys", method = RequestMethod.POST)
    ServiceResult<List<SaleApplyMoney>> getSaleApplyMoneys(FeignUtil feignUtil);
}