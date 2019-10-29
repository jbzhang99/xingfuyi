package com.yixiekeji.service.analysis;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.analysis.BrowseLog;
import com.yixiekeji.entity.analysis.BrowseLogMobile;
import com.yixiekeji.entity.analysis.ProductLookLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "analysis")
@Service(value = "analysisService")
public interface IAnalysisService {

    /**
     * 根据id取得browse_log对象
     * @param  browseLogId
     * @return
     */
    @RequestMapping(value = "getBrowseLogById", method = RequestMethod.GET)
    ServiceResult<BrowseLog> getBrowseLogById(@RequestParam("browseLogId") Integer browseLogId);

    /**
     * 保存browse_log对象
     * @param  browseLog
     * @return
     */
    @RequestMapping(value = "saveBrowseLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveBrowseLog(BrowseLog browseLog);

    /**
    * 更新browse_log对象
    * @param  browseLog
    * @return
    */
    @RequestMapping(value = "updateBrowseLog", method = RequestMethod.POST)
    ServiceResult<Integer> updateBrowseLog(BrowseLog browseLog);

    /**
     * 记录用户访问单品页日志
     * @param productLookLog
     * @return
     */
    @RequestMapping(value = "saveProductLookLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveProductLookLog(ProductLookLog productLookLog);

    /**
     * 根据条件取得会员商品浏览记录
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductLookLogs", method = RequestMethod.POST)
    ServiceResult<List<ProductLookLog>> getProductLookLogs(FeignUtil feignUtil);

    /**
     * 记录移动端日志
     * @param browseLog
     * @return
     */
    @RequestMapping(value = "saveBrowseLogMobile", method = RequestMethod.POST)
    ServiceResult<Integer> saveBrowseLogMobile(BrowseLogMobile browseLog);

    /** 
    * @Description: 根据会员id获取浏览数量
    * @Author: mofan 
    * @Date: 2019/10/17 
    */ 
    @GetMapping(value = "getProductBrowseCount")
    Integer getProductBrowseCount(@RequestParam("memberId")Integer memberId);
}
