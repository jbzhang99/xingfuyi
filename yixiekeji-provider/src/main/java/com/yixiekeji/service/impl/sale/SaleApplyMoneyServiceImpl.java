package com.yixiekeji.service.impl.sale;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.sale.SaleApplyMoney;
import com.yixiekeji.model.sale.SaleApplyMoneyModel;
import com.yixiekeji.service.sale.ISaleApplyMoneyService;

@RestController
public class SaleApplyMoneyServiceImpl implements ISaleApplyMoneyService {
    private static Logger       log = LoggerFactory.getLogger(SaleApplyMoneyServiceImpl.class);

    @Resource
    private SaleApplyMoneyModel saleApplyMoneyModel;

    /**
    * 根据id取得提款申请表
    * @param  saleApplyMoneyId
    * @return
    */
    @Override
    public ServiceResult<SaleApplyMoney> getSaleApplyMoneyById(@RequestParam("saleApplyMoneyId") Integer saleApplyMoneyId) {
        ServiceResult<SaleApplyMoney> result = new ServiceResult<SaleApplyMoney>();
        try {
            result.setResult(saleApplyMoneyModel.getSaleApplyMoneyById(saleApplyMoneyId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleApplyMoneyService][getSaleApplyMoneyById]根据id[" + saleApplyMoneyId
                      + "]取得提款申请表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleApplyMoneyService][getSaleApplyMoneyById]根据id[" + saleApplyMoneyId
                      + "]取得提款申请表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存提款申请表
     * @param  saleApplyMoney
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleApplyMoney(@RequestBody SaleApplyMoney saleApplyMoney) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleApplyMoneyModel.saveSaleApplyMoney(saleApplyMoney));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[ISaleApplyMoneyService][saveSaleApplyMoney]保存提款申请表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleApplyMoneyService][saveSaleApplyMoney]保存提款申请表时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新提款申请表
    * @param  saleApplyMoney
    * @return
    * @see com.yixiekeji.service.SaleApplyMoneyService#updateSaleApplyMoney(SaleApplyMoney)
    */
    @Override
    public ServiceResult<Integer> updateSaleApplyMoney(@RequestBody SaleApplyMoney saleApplyMoney) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleApplyMoneyModel.updateSaleApplyMoney(saleApplyMoney));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[ISaleApplyMoneyService][updateSaleApplyMoney]更新提款申请表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleApplyMoneyService][updateSaleApplyMoney]更新提款申请表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     * @see com.yixiekeji.service.sale.ISaleApplyMoneyService#getSaleApplyMoneys(java.util.Map, com.yixiekeji.core.PagerInfo)
     */
    @Override
    public ServiceResult<List<SaleApplyMoney>> getSaleApplyMoneys(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SaleApplyMoney>> serviceResult = new ServiceResult<List<SaleApplyMoney>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(saleApplyMoneyModel.getSaleApplyMoneys(queryMap, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ISaleMemberService][getSaleApplyMoneys]查询分页查询体现流水发生异常:", e);
        }
        return serviceResult;
    }
}