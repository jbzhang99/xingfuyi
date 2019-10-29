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
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.model.sale.SaleMemberModel;
import com.yixiekeji.service.sale.ISaleMemberService;

@RestController
public class SaleMemberServiceImpl implements ISaleMemberService {
    private static Logger   log = LoggerFactory.getLogger(SaleMemberServiceImpl.class);

    @Resource
    private SaleMemberModel saleMemberModel;

    /**
     * 根据id取得用户推广表
     * @param  saleMemberId
     * @return
     */
    @Override
    public ServiceResult<SaleMember> getSaleMemberById(@RequestParam("saleMemberId") Integer saleMemberId) {
        ServiceResult<SaleMember> result = new ServiceResult<SaleMember>();
        try {
            result.setResult(saleMemberModel.getSaleMemberById(saleMemberId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                    "[ISaleMemberService][getSaleMemberById]根据id[" + saleMemberId + "]取得用户推广表时出现未知异常：",
                    e);
        }
        return result;
    }

    /**
     * 保存用户推广表
     * @param  saleMember
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleMember(@RequestBody SaleMember saleMember) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleMemberModel.saveSaleMember(saleMember));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleMemberService][saveSaleMember]保存用户推广表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 更新用户推广表
     * @param  saleMember
     * @return
     * @see com.yixiekeji.service.SaleMemberService#updateSaleMember(SaleMember)
     */
    @Override
    public ServiceResult<Integer> updateSaleMember(@RequestBody SaleMember saleMember) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleMemberModel.updateSaleMember(saleMember));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleMemberService][updateSaleMember]更新用户推广表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 根据用户ID查询用户推广表
     * @param  saleMemberId
     * @return
     */
    @Override
    public ServiceResult<SaleMember> getSaleMemberByMemberId(@RequestParam("saleMemberId") Integer saleMemberId) {
        ServiceResult<SaleMember> result = new ServiceResult<SaleMember>();
        try {
            result.setResult(saleMemberModel.getSaleMemberByMemberId(saleMemberId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleMemberService][getSaleMemberByMemberId]根据用户ID查询用户推广表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 通过用户ID开启推广员
     * @param  saleMember
     * @return
     * @see com.yixiekeji.service.SaleMemberService#saveSaleMemberByMemberId(SaleMember)
     */
    @Override
    public ServiceResult<Integer> saveSaleMemberByMemberId(@RequestBody SaleMember saleMember) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleMemberModel.saveSaleMemberByMemberId(saleMember));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleMemberService][saveSaleMemberByMemberId]通过用户ID开启推广员出现未知异常：", e);
        }
        return result;
    }

    /**
     * 查询推广用户列表
     * @param queryMap
     * @param pager
     * @return
     * @see com.yixiekeji.service.sale.ISaleMemberService#getSaleMembers(java.util.Map, com.yixiekeji.core.PagerInfo)
     */
    @Override
    public ServiceResult<List<SaleMember>> getSaleMembers(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SaleMember>> serviceResult = new ServiceResult<List<SaleMember>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(saleMemberModel.getSaleMembers(queryMap, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ISaleMemberService][getSaleMembers]查询查询推广用户列表发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据推广码来获取推广用户
     * @param  saleCode
     * @return
     * @see com.yixiekeji.service.SaleMemberService#getSaleMemberBySaleCode(saleCode)
     */
    @Override
    public ServiceResult<SaleMember> getSaleMemberBySaleCode(@RequestParam("salecode") String saleCode) {
        ServiceResult<SaleMember> result = new ServiceResult<SaleMember>();
        try {
            result.setResult(saleMemberModel.getSaleMemberBySaleCode(saleCode));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleMemberService][getSaleMemberBySaleCode]通根据推广码来获取推广用户出现未知异常：", e);
        }
        return result;
    }

    @Override
    public List<SaleMember> checkSaleCode(@RequestParam("saleCode") String saleCode) {
        return  saleMemberModel.checkSaleCode(saleCode);
    }

    @Override
    public SaleMember getAuthInfoById(@RequestParam("saleMemberId")Integer saleMemberId,@RequestParam("state") Integer state) {
        return saleMemberModel.getAuthInfoById(saleMemberId,state);
    }
}