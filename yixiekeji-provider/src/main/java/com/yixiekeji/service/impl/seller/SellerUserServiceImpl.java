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
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.seller.SellerUserLoginLog;
import com.yixiekeji.model.seller.SellerUserModel;
import com.yixiekeji.service.seller.ISellerUserService;

@RestController
public class SellerUserServiceImpl implements ISellerUserService {
    private static Logger   log = LoggerFactory.getLogger(SellerUserServiceImpl.class);

    @Resource
    private SellerUserModel sellerUserModel;

    @Override
    public ServiceResult<SellerUser> getSellerUserById(@RequestParam("sellerUserId") Integer sellerUserId) {
        ServiceResult<SellerUser> result = new ServiceResult<SellerUser>();
        try {
            result.setResult(sellerUserModel.getSellerUserById(sellerUserId));
        } catch (Exception e) {
            log.error("根据id[" + sellerUserId + "]取得系统管理员表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + sellerUserId + "]取得系统管理员表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveSellerUser(@RequestBody SellerUser sellerUser) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(sellerUserModel.saveSellerUser(sellerUser));
            result.setMessage("保存成功");
        } catch (Exception e) {
            log.error("保存系统管理员表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存系统管理员表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateSellerUser(@RequestBody SellerUser sellerUser) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(sellerUserModel.updateSellerUser(sellerUser));
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新系统管理员表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新系统管理员表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SellerUser>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SellerUser>> serviceResult = new ServiceResult<List<SellerUser>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(sellerUserModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<SellerUser> list = sellerUserModel.page(queryMap, start, size);

            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerUserServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SellerUserServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {

        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            sr.setResult(sellerUserModel.del(id));
        } catch (Exception e) {
            log.error("[SellerUserServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }

    @Override
    public ServiceResult<SellerUser> getSellerUserByNamePwd(@RequestParam("name") String name,
                                                            @RequestParam("password") String password) {
        ServiceResult<SellerUser> result = new ServiceResult<SellerUser>();
        try {
            result.setResult(sellerUserModel.getSellerUserByNamePwd(name, password));
        } catch (Exception e) {
            log.error("[SellerUserServiceImpl][getSellerUserByNamePwd] exception:", e);
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SellerUser>> getSellerUserByName(@RequestParam("name") String name) {
        ServiceResult<List<SellerUser>> result = new ServiceResult<List<SellerUser>>();
        try {
            result.setResult(sellerUserModel.getSellerUserByName(name));
        } catch (BusinessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerUserServiceImpl][getSellerUserByName]根据用户名取商家用户时发生错误:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveSellerUserLoginLog(@RequestBody SellerUserLoginLog sellerUserLoginLog) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(sellerUserModel.saveSellerUserLoginLog(sellerUserLoginLog));
        } catch (BusinessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            log.error("保存系统管理员登录日志表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存系统管理员登录日志表时出现未知异常");
        }
        return result;
    }
}