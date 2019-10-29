package com.yixiekeji.service.impl.sale;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.sale.SaleScaleProductLog;
import com.yixiekeji.entity.sale.SaleSetting;
import com.yixiekeji.model.sale.SaleScaleModel;
import com.yixiekeji.service.sale.ISaleScaleService;

@RestController
public class SaleScaleServiceImpl implements ISaleScaleService {
    private static Logger  log = LoggerFactory.getLogger(SaleScaleServiceImpl.class);

    @Resource
    private SaleScaleModel saleScaleModel;

    /**
    * 根据id取得商家总体分佣表
    * @param  saleScaleId
    * @return
    */
    @Override
    public ServiceResult<SaleScale> getSaleScaleById(@RequestParam("saleScaleId") Integer saleScaleId) {
        ServiceResult<SaleScale> result = new ServiceResult<SaleScale>();
        try {
            result.setResult(saleScaleModel.getSaleScaleById(saleScaleId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleService][getSaleScaleById]根据id[" + saleScaleId
                      + "]取得商家总体分佣表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[ISaleScaleService][getSaleScaleById]根据id[" + saleScaleId + "]取得商家总体分佣表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 根据商家id取得商家总体分佣表
     * @param  sellerId
     * @return
     */
    @Override
    public ServiceResult<SaleScale> getSaleScaleBySellerId(@RequestParam("saleScaleId") Integer saleScaleId) {
        ServiceResult<SaleScale> result = new ServiceResult<SaleScale>();
        try {
            result.setResult(saleScaleModel.getSaleScaleBySellerId(saleScaleId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleService][getSaleScaleById]根据id[" + saleScaleId
                      + "]取得商家总体分佣表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[ISaleScaleService][getSaleScaleById]根据商家id[" + saleScaleId + "]取得商家总体分佣表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存商家总体分佣表
     * @param  saleScale
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleScale(@RequestBody SaleScale saleScale) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleScaleModel.saveSaleScale(saleScale));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleService][saveSaleScale]保存商家总体分佣表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleScaleService][saveSaleScale]保存商家总体分佣表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 商家设置了三级分销更新，没有设置创建
     * @param  saleScale
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleScaleOrUpdateBySellerId(@RequestBody SaleScale saleScale) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleScaleModel.saveSaleScaleOrUpdateBySellerId(saleScale));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleService][saveSaleScaleOrUpdateBySellerId]保存商家总体分佣表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleScaleService][saveSaleScaleOrUpdateBySellerId]保存商家总体分佣表时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新商家总体分佣表
    * @param  saleScale
    * @return
    * @see com.yixiekeji.service.SaleScaleService#updateSaleScale(SaleScale)
    */
    @Override
    public ServiceResult<Integer> updateSaleScale(@RequestBody SaleScale saleScale) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleScaleModel.updateSaleScale(saleScale));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleService][updateSaleScale]更新商家总体分佣表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleScaleService][updateSaleScale]更新商家总体分佣表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存商品分佣日志表
     * @param  saleScaleProductLog
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleScaleProductLog(@RequestBody SaleScaleProductLog saleScaleProductLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleScaleModel.saveSaleScaleProductLog(saleScaleProductLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleProductLogService][saveSaleScaleProductLog]保存商品分佣日志表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleScaleProductLogService][saveSaleScaleProductLog]保存商品分佣日志表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询正在上架的商家的分佣
     * @return
     * @see com.yixiekeji.service.sale.ISaleScaleService#getSaleScales()
     */
    @Override
    public ServiceResult<List<SaleScale>> getSaleScales() {
        ServiceResult<List<SaleScale>> result = new ServiceResult<List<SaleScale>>();
        try {
            result.setResult(saleScaleModel.getSaleScales());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleScaleProductLogService][getSaleScales]查询正在上架的商家的分佣时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleScaleProductLogService][getSaleScales]查询正在上架的商家的分佣时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询最新的分销设置的一条记录
     * @param  saleSettingId
     * @return
     */
    @Override
    public ServiceResult<SaleSetting> getSaleSettingDesc() {
        ServiceResult<SaleSetting> result = new ServiceResult<SaleSetting>();
        try {
            result.setResult(saleScaleModel.getSaleSettingDesc());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[ISaleSettingService][getSaleSettingDesc]查询最新的分销设置的一条记录时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleSettingService][getSaleSettingDesc]查询最新的分销设置的一条记录时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存分销设置表
     * @param  saleSetting
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleSetting(@RequestBody SaleSetting saleSetting) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleScaleModel.saveSaleSetting(saleSetting));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleSettingService][saveSaleSetting]保存分销设置表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleSettingService][saveSaleSetting]保存分销设置表时出现未知异常：", e);
        }
        return result;
    }
}