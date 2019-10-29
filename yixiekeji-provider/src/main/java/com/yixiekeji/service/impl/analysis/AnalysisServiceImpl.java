package com.yixiekeji.service.impl.analysis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.analysis.BrowseLog;
import com.yixiekeji.entity.analysis.BrowseLogMobile;
import com.yixiekeji.entity.analysis.ProductLookLog;
import com.yixiekeji.model.analysis.AnalysisModel;
import com.yixiekeji.service.analysis.IAnalysisService;

@RestController
public class AnalysisServiceImpl implements IAnalysisService {

    private static Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    @Resource
    private AnalysisModel analysisModel;

    /**
     * 根据id取得browse_log对象
     * @param  browseLogId
     * @return
     * @see com.yixiekeji.analysis.service.BrowseLogService#getBrowseLogById(java.lang.Integer)
     */
    @Override
    public ServiceResult<BrowseLog> getBrowseLogById(@RequestParam("browseLogId") Integer browseLogId) {
        ServiceResult<BrowseLog> result = new ServiceResult<BrowseLog>();
        try {
            result.setResult(analysisModel.getBrowseLogById(browseLogId));
        } catch (Exception e) {
            log.error("[BrowseLogServiceImpl][getBrowseLogById]根据id[" + browseLogId
                      + "]取得browse_log对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[BrowseLogServiceImpl][getBrowseLogById]根据id[" + browseLogId
                              + "]取得browse_log对象时出现未知异常");
        }
        return result;
    }

    /**
     * 保存browse_log对象
     * @param  browseLog
     * @return
     * @see com.yixiekeji.analysis.service.BrowseLogService#saveBrowseLog(BrowseLog)
     */
    @Override
    public ServiceResult<Integer> saveBrowseLog(@RequestBody BrowseLog browseLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(analysisModel.saveBrowseLog(browseLog));
        } catch (Exception e) {
            log.error("[BrowseLogServiceImpl][saveBrowseLog]保存browse_log对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[BrowseLogServiceImpl][saveBrowseLog]保存browse_log对象时出现未知异常");
        }
        return result;
    }

    /**
    * 更新browse_log对象
    * @param  browseLog
    * @return
    * @see com.yixiekeji.analysis.service.BrowseLogService#updateBrowseLog(BrowseLog)
    */
    @Override
    public ServiceResult<Integer> updateBrowseLog(@RequestBody BrowseLog browseLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(analysisModel.updateBrowseLog(browseLog));
        } catch (Exception e) {
            log.error("[BrowseLogServiceImpl][updateBrowseLog]更新browse_log对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[BrowseLogServiceImpl][updateBrowseLog]更新browse_log对象时出现未知异常");
        }
        return result;
    }

    /**
     * 记录用户访问单品页日志
     * @param productLookLog
     * @return
     * @see com.yixiekeji.service.analysis.IAnalysisService#saveProductLookLog(com.yixiekeji.entity.analysis.ProductLookLog)
     */
    @Override
    public ServiceResult<Integer> saveProductLookLog(@RequestBody ProductLookLog productLookLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(analysisModel.saveProductLookLog(productLookLog));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BrowseLogServiceImpl][saveProductLookLog]记录用户访问单品页日志时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[BrowseLogServiceImpl][saveProductLookLog]记录用户访问单品页日志时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductLookLog>> getProductLookLogs(@RequestBody FeignUtil feignUtil) {
        PagerInfo pager = feignUtil.getPager();
        Map<String, String> queryMap = feignUtil.getQueryMap();

        ServiceResult<List<ProductLookLog>> serviceResult = new ServiceResult<List<ProductLookLog>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(analysisModel.getProductLookLogsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(analysisModel.getProductLookLogs(feignUtil.getQueryMap(), start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[BrowseLogServiceImpl][getProductLookLogs]查询会员商品浏览表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[BrowseLogServiceImpl][getProductLookLogs]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[BrowseLogServiceImpl][getProductLookLogs]查询会员商品浏览发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 记录移动端日志
     * @param browseLog
     * @return
     * @see com.yixiekeji.service.analysis.IAnalysisService#saveBrowseLogMobile(com.yixiekeji.entity.analysis.BrowseLogMobile)
     */
    @Override
    public ServiceResult<Integer> saveBrowseLogMobile(@RequestBody BrowseLogMobile browseLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(analysisModel.saveBrowseLogMobile(browseLog));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BrowseLogServiceImpl][saveBrowseLogMobile]保存BrowseLogMobile对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage(
                "[BrowseLogServiceImpl][saveBrowseLogMobile]保存BrowseLogMobile对象时出现未知异常");
        }
        return result;
    }

    @Override
    public Integer getProductBrowseCount(@RequestParam("memberId")Integer memberId) {
        return analysisModel.getProductBrowseCount(memberId);
    }
}
