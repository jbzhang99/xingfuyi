package com.yixiekeji.service.sale;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.sale.SaleScaleProductLog;
import com.yixiekeji.entity.sale.SaleSetting;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "saleScale")
@Service(value = "saleScaleService")
public interface ISaleScaleService {

    /**
     * 根据id取得商家总体分佣表
     * @param  saleScaleId
     * @return
     */
    @RequestMapping(value = "getSaleScaleById", method = RequestMethod.GET)
    ServiceResult<SaleScale> getSaleScaleById(@RequestParam("saleScaleId") Integer saleScaleId);

    /**
     * 保存商家总体分佣表
     * @param  saleScale
     * @return
     */
    @RequestMapping(value = "saveSaleScale", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleScale(SaleScale saleScale);

    /**
    * 更新商家总体分佣表
    * @param  saleScale
    * @return
    */
    @RequestMapping(value = "updateSaleScale", method = RequestMethod.POST)
    ServiceResult<Integer> updateSaleScale(SaleScale saleScale);

    /**
     * 根据商家id取得商家总体分佣表
     * @param id
     * @return
     */
    @RequestMapping(value = "getSaleScaleBySellerId", method = RequestMethod.GET)
    ServiceResult<SaleScale> getSaleScaleBySellerId(@RequestParam("saleScaleId") Integer saleScaleId);

    /**
     * 商家设置了三级分销更新，没有设置创建
     * @param saleScale
     * @return
     */
    @RequestMapping(value = "saveSaleScaleOrUpdateBySellerId", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleScaleOrUpdateBySellerId(SaleScale saleScale);

    /**
     * 保存商品分佣日志表
     * @param  saleScaleProductLog
     * @return
     */
    @RequestMapping(value = "saveSaleScaleProductLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleScaleProductLog(SaleScaleProductLog saleScaleProductLog);

    /**
     * 查询正在上架的商家的分佣
     * @return
     */
    @RequestMapping(value = "getSaleScales", method = RequestMethod.GET)
    ServiceResult<List<SaleScale>> getSaleScales();

    /**
     * 查询最新的分销设置的一条记录
     * @param  saleSettingId
     * @return
     */
    @RequestMapping(value = "getSaleSettingDesc", method = RequestMethod.GET)
    ServiceResult<SaleSetting> getSaleSettingDesc();

    /**
     * 保存分销设置表
     * @param  saleSetting
     * @return
     */
    @RequestMapping(value = "saveSaleSetting", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleSetting(SaleSetting saleSetting);

}