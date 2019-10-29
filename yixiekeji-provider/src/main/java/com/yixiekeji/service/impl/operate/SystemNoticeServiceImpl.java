package com.yixiekeji.service.impl.operate;

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
import com.yixiekeji.entity.operate.SystemNotice;
import com.yixiekeji.model.operate.SystemNoticeModel;
import com.yixiekeji.service.system.ISystemNoticeService;

@RestController
public class SystemNoticeServiceImpl implements ISystemNoticeService {
    private static Logger     log = LoggerFactory.getLogger(SystemNoticeServiceImpl.class);

    @Resource
    private SystemNoticeModel systemNoticeModel;

    /**
    * 根据id取得商城公告
    * @param  systemNoticeId
    * @return
    */
    @Override
    public ServiceResult<SystemNotice> getSystemNoticeById(@RequestParam("systemNoticeId") Integer systemNoticeId) {
        ServiceResult<SystemNotice> result = new ServiceResult<SystemNotice>();
        try {
            result.setResult(systemNoticeModel.getSystemNoticeById(systemNoticeId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISystemNoticeService][getSystemNoticeById]根据id[" + systemNoticeId
                      + "]取得商城公告时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISystemNoticeService][getSystemNoticeById]根据id[" + systemNoticeId
                      + "]取得商城公告时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存商城公告
     * @param  systemNotice
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSystemNotice(@RequestBody SystemNotice systemNotice) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemNoticeModel.saveSystemNotice(systemNotice));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISystemNoticeService][saveSystemNotice]保存商城公告时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISystemNoticeService][saveSystemNotice]保存商城公告时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新商城公告
    * @param  systemNotice
    * @return
    * @see com.yixiekeji.service.SystemNoticeService#updateSystemNotice(SystemNotice)
    */
    @Override
    public ServiceResult<Integer> updateSystemNotice(@RequestBody SystemNotice systemNotice) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemNoticeModel.updateSystemNotice(systemNotice));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISystemNoticeService][updateSystemNotice]更新商城公告时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISystemNoticeService][updateSystemNotice]更新商城公告时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemNotice>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SystemNotice>> serviceResult = new ServiceResult<List<SystemNotice>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(systemNoticeModel.page(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[SystemNoticeServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(systemNoticeModel.del(id) > 0);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[SystemNoticeServiceImpl][del] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SystemNotice> toDetail(@RequestParam("id") Integer id,
                                                @RequestParam("sellerid") Integer sellerid) {
        ServiceResult<SystemNotice> result = new ServiceResult<SystemNotice>();
        try {
            result.setResult(systemNoticeModel.toDetail(id, sellerid));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemNotice>> getUnreadNotice(@RequestParam("sellerId") Integer sellerId,
                                                             @RequestBody PagerInfo pager) {
        ServiceResult<List<SystemNotice>> result = new ServiceResult<List<SystemNotice>>();
        result.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemNoticeModel.getUnreadNoticeCount(sellerId));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            result.setResult(systemNoticeModel.getUnreadNotice(sellerId, start, size));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
        }
        return result;
    }
}