package com.yixiekeji.service.settlement;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.settlement.SettlementSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 结算设置
 *                       
 * @Filename: ISettlementSettingService.java
 * @Version: 1.0
 * @Author: 李洪林
 * @Email: leehonglim@163.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "settlementSetting")
@Service(value = "settlementSettingService")
public interface ISettlementSettingService {


    /**
     * 保存结算设置表
     * @param  settlementSetting
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ServiceResult<Integer> save(SettlementSetting settlementSetting);

    /**
     * 更新结算设置表
     * @param settlementSetting
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    ServiceResult<Integer> update(SettlementSetting settlementSetting);

    /**
     * 获取结算设置列表
     * @return
     */
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    ServiceResult<List<SettlementSetting>> getList(FeignUtil feignUtil);


    /**
     * 删除结算设置
     * @param settlementSetting
     * @return
     */
    @PostMapping("delete")
    ServiceResult<Integer> delete(SettlementSetting settlementSetting);
}