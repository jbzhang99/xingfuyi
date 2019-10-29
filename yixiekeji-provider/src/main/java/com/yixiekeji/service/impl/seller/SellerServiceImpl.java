package com.yixiekeji.service.impl.seller;

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
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.model.seller.SellerModel;
import com.yixiekeji.service.seller.ISellerService;

@RestController
public class SellerServiceImpl implements ISellerService {
    private static Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Resource
    private SellerModel   sellerModel;

    @Override
    public ServiceResult<Seller> getSellerById(@RequestParam("sellerId") Integer sellerId) {
        ServiceResult<Seller> serviceResult = new ServiceResult<Seller>();
        try {
            serviceResult.setResult(sellerModel.getSellerById(sellerId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[SellerService][getSellerById]根据id[" + sellerId + "]取得商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerService][getSellerById]根据id[" + sellerId + "]取得商家表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> saveSeller(@RequestBody Seller seller) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(sellerModel.saveSeller(seller));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][saveSeller]保存商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerService][saveSeller] param:" + JSON.toJSONString(seller));
            log.error("[SellerService][saveSeller]保存商家表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> updateSeller(@RequestBody Seller seller) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(sellerModel.updateSeller(seller));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][updateSeller]更新商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerService][updateSeller] param:" + JSON.toJSONString(seller));
            log.error("[SellerService][updateSeller]更新商家表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Seller>> getSellers(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<Seller>> serviceResult = new ServiceResult<List<Seller>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(sellerModel.getSellersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            serviceResult.setResult(sellerModel.getSellers(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][getSellers]根据条件查询商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerService][getSellers] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SellerService][getSellers]根据条件查询商家表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Seller> getSellerByMemberId(@RequestParam("memberId") Integer memberId) {
        ServiceResult<Seller> serviceResult = new ServiceResult<Seller>();
        try {
            serviceResult.setResult(sellerModel.getSellerByMemberId(memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][getSellerByMemberId]根据用户ID条件查询商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerService][getSellerByMemberId] memberId:" + memberId);
            log.error("[SellerService][getSellerByMemberId]根据用户ID条件查询商家表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> freezeSeller(@RequestParam("sellerId") Integer sellerId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerModel.freezeSeller(sellerId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][freezeSeller]冻结商家时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerService][freezeSeller]冻结商家时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> unFreezeSeller(@RequestParam("sellerId") Integer sellerId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerModel.unFreezeSeller(sellerId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][unFreezeSeller]解冻商家时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerService][unFreezeSeller]解冻商家时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> getSellerLocationByMId(@RequestParam("memberId") Integer memberId) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            serviceResult.setResult(sellerModel.getSellerLocationByMId(memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[SellerService][getSellerLocationByMId]获得商家省市级地址时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerService][getSellerLocationByMId]获得商家省市级地址时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> jobSetSellerScore() {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerModel.jobSetSellerScore());
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[SellerService][jobSetSellerScore]定时任务设定商家的评分时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerService][jobSetSellerScore]定时任务设定商家的评分时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> jobSellerStatistics() {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerModel.jobSellerStatistics());
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[SellerService][jobSellerStatistics]定时任务设定商家各项统计数据时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerService][jobSellerStatistics]定时任务设定商家各项统计数据时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Seller>> getSellerByName(@RequestParam("name") String name) {
        ServiceResult<List<Seller>> serviceResult = new ServiceResult<List<Seller>>();
        try {
            serviceResult.setResult(sellerModel.getSellerByName(name));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][getSellerByName]根据名称获取商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerService][getSellerByName] param1:" + name);
            log.error("[SellerService][getSellerByName]根据名称获取商家表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Seller>> getSellerBySellerName(@RequestParam("sellerName") String sellerName) {
        ServiceResult<List<Seller>> serviceResult = new ServiceResult<List<Seller>>();
        try {
            serviceResult.setResult(sellerModel.getSellerBySellerName(sellerName));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerService][getSellerBySellerName]根据名称获取商家表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerService][getSellerBySellerName] param1:" + sellerName);
            log.error("[SellerService][getSellerBySellerName]根据名称获取商家表时出现未知异常：", e);
        }
        return serviceResult;
    }
}