package com.yixiekeji.service.impl.settlement;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.settlement.Settlement;
import com.yixiekeji.entity.settlement.SettlementSetting;
import com.yixiekeji.model.settlement.SettlementModel;
import com.yixiekeji.model.settlement.SettlementSettingModel;
import com.yixiekeji.service.settlement.ISettlementService;
import com.yixiekeji.service.settlement.ISettlementSettingService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class SettlementSettingServiceImpl implements ISettlementSettingService {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
        .getLogger(SettlementOpServiceImpl.class);

    @Resource
    private SettlementSettingModel settlementSettingModel;


    /**
     * 保存结算表
     * @param  settlementSetting
     * @return
     */
    @Override
    public ServiceResult<Integer> save(@RequestBody SettlementSetting settlementSetting) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(settlementSettingModel.save(settlementSetting));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SettlementSettingService][save]保存结算表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SettlementSettingService][save]保存结算表时出现未知异常：", e);
        }
        return serviceResult;
    }

    /**
    * 更新结算表
    * @param  settlementSetting
    * @return
    */
    @Override
    public ServiceResult<Integer> update(@RequestBody SettlementSetting settlementSetting) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(settlementSettingModel.update(settlementSetting));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SettlementSettingService][update]更新结算表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SettlementSettingService][update]更新结算表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SettlementSetting>> getList(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SettlementSetting>> serviceResult = new ServiceResult<List<SettlementSetting>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(settlementSettingModel.getList(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SettlementSettingService][getList]查询结算表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SettlementSettingService][getList]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SettlementSettingService][getList]查询结算信息发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> delete(@RequestBody SettlementSetting settlementSetting) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(settlementSettingModel.delete(settlementSetting));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SettlementSettingService][delete] exception:" + e.getMessage());
        }
        return serviceResult;
    }

}