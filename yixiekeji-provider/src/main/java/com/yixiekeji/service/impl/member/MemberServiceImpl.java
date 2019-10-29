package com.yixiekeji.service.impl.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalanceLogs;
import com.yixiekeji.entity.member.MemberGradeConfig;
import com.yixiekeji.entity.member.MemberGradeIntegralLogs;
import com.yixiekeji.entity.member.MemberGradeUpLogs;
import com.yixiekeji.entity.member.MemberLoginLogs;
import com.yixiekeji.entity.member.MemberRule;
import com.yixiekeji.model.member.MemberModel;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.vo.member.FrontCheckPwdVO;
import com.yixiekeji.vo.member.FrontMemberProductBehaveStatisticsVO;

@RestController
public class MemberServiceImpl implements IMemberService {
    private static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    private MemberModel   memberModel;

    @Override
    public ServiceResult<Member> getMemberById(@RequestParam("memberId") Integer memberId) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.getMemberById(memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                    "[MemberService][getMemberById]根据id[" + memberId + "]取得会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberById]根据id[" + memberId + "]取得会员表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> saveMember(@RequestBody Member member) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(memberModel.saveMember(member));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][saveMember]保存会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][saveMember] param:" + JSON.toJSONString(member));
            log.error("[MemberService][saveMember]保存会员表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMember(@RequestBody Member member) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.updateMember(member));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][updateMember]更新会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][updateMember] param:" + JSON.toJSONString(member));
            log.error("[MemberService][updateMember]更新会员表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Member>> getMembers(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<Member>> serviceResult = new ServiceResult<List<Member>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMembersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMembers(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][getMembers]查询会员表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMembers]param1:" + JSON.toJSONString(queryMap)
                    + " &param2:" + JSON.toJSONString(pager));
            log.error("[MemberService][getMembers]查询会员信息发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMemberValue(@RequestBody MemberGradeIntegralLogs logs) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");

            serviceResult.setResult(memberModel.updateMemberValue(logs));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][updateMemberValue]更新会员经验值与积分发生异常:", be);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][updateMemberValue]更新会员经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberGradeUpLogs>> getMemberGradeUpLogs(@RequestParam("memberId") Integer memberId,
                                                                       @RequestBody PagerInfo pager) {
        ServiceResult<List<MemberGradeUpLogs>> serviceResult = new ServiceResult<List<MemberGradeUpLogs>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberGradeUpLogsCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMemberGradeUpLogs(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberGradeUpLogs]查询会员等级升级日志发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberGradeUpLogs]查询会员等级升级日志发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberGradeIntegralLogs>> getMemberGradeIntegralLogs(@RequestParam("memberId") Integer memberId,
                                                                                   @RequestParam("type") Integer type,
                                                                                   @RequestBody PagerInfo pager) {
        ServiceResult<List<MemberGradeIntegralLogs>> serviceResult = new ServiceResult<List<MemberGradeIntegralLogs>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberGradeIntegralLogsCount(memberId, type));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                    .setResult(memberModel.getMemberGradeIntegralLogs(memberId, type, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberGradeIntegralLogs]根据会员ID和类型取得会员经验值积分值变更日志发生异常:"
                    + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberGradeIntegralLogs]根据会员ID和类型取得会员经验值积分值变更日志发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberLoginLogs>> getMemberLoginLogs(@RequestParam("memberId") Integer memberId,
                                                                   @RequestBody PagerInfo pager) {
        ServiceResult<List<MemberLoginLogs>> serviceResult = new ServiceResult<List<MemberLoginLogs>>();
        serviceResult.setPager(pager);
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberModel.getMemberLoginLogsCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(memberModel.getMemberLoginLogs(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberLoginLogs]根据会员ID获取会员登录日志发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberLoginLogs]根据会员ID获取会员登录日志发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMemberBalance(@RequestBody MemberBalanceLogs memberBalanceLogs) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            Assert.notNull(memberModel, "Property 'memberModel' is required.");

            serviceResult.setResult(memberModel.updateMemberBalance(memberBalanceLogs));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][updateMemberBalance]更新会员余额发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][updateMemberBalance]更新会员余额发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Member> memberLogin(@RequestParam("memberName") String memberName,
                                             @RequestParam("password") String password,
                                             @RequestParam("ip") String ip,
                                             @RequestParam("source") Integer source) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.memberLogin(memberName, password, ip, source));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][memberLogin]会员登录时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][memberLogin]会员登录时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<Member> memberLogins(@RequestParam("mobile") String mobile,
                                              @RequestParam("password") String password,
                                              @RequestParam("ip") String ip,
                                              @RequestParam("source") Integer source) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.memberLogins(mobile, password, ip, source));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][memberLogin]会员登录时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][memberLogin]会员登录时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<Member> memberRegister(@RequestBody Member member) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.memberRegister(member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][memberRegister]会员注册时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][memberRegister]member:" + JSON.toJSONString(member));
            log.error("[MemberService][memberRegister]会员注册时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<List<Member>> getMemberByName(@RequestParam("name") String name) {
        ServiceResult<List<Member>> serviceResult = new ServiceResult<List<Member>>();
        try {
            serviceResult.setResult(memberModel.getMemberByName(name));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberService][getMemberByName]根据会员名称取会员时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberByName]name:" + name);
            log.error("[MemberService][getMemberByName]根据会员名称取会员时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<MemberGradeConfig> getMemberGradeConfig(@RequestParam("memberGradeConfigId") Integer memberGradeConfigId) {
        ServiceResult<MemberGradeConfig> serviceResult = new ServiceResult<MemberGradeConfig>();
        try {
            serviceResult.setResult(memberModel.getMemberGradeConfig(memberGradeConfigId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][getMemberRule]根据ID获取商城会员等级配置发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<MemberRule> getMemberRule(@RequestParam("memberRuleId") Integer memberRuleId,
                                                   @RequestParam("state") Integer state) {
        ServiceResult<MemberRule> serviceResult = new ServiceResult<MemberRule>();
        try {
            serviceResult.setResult(memberModel.getMemberRule(memberRuleId, state));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][getMemberRule]根据ID获取会员经验值积分规则发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberRegistSendValue(@RequestParam("memberId") Integer memberId,
                                                        @RequestParam("memberName") String memberName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.memberRegistSendValue(memberId, memberName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberRegistSendValue]会员注册时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberLoginSendValue(@RequestParam("memberId") Integer memberId,
                                                       @RequestParam("memberName") String memberName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.memberLoginSendValue(memberId, memberName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberLoginSendValue]会员登录时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberOrderSendValue(@RequestParam("memberId") Integer memberId,
                                                       @RequestParam("memberName") String memberName,
                                                       @RequestParam("orderId") Integer orderId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                    .setResult(memberModel.memberOrderSendValue(memberId, memberName, orderId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberOrderSendValue]会员下单时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> memberEvaluateSendValue(@RequestParam("memberId") Integer memberId,
                                                          @RequestParam("memberName") String memberName,
                                                          @RequestParam("ordersProductId") Integer ordersProductId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                    memberModel.memberEvaluateSendValue(memberId, memberName, ordersProductId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberService][memberEvaluateSendValue]会员评论时送经验值与积分发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 修改密码提交
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Member> editPassword(@RequestParam("oldPwd") String oldPwd,
                                              @RequestParam("newPwd") String newPwd,
                                              @RequestBody Member member) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.editPassword(oldPwd, newPwd, member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][editPassword]修改密码时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据产品获得用户评价数
     * @param request
     * @param pager
     * @return
     */
    @Override
    public ServiceResult<FrontMemberProductBehaveStatisticsVO> getBehaveStatisticsByProductId(@RequestParam("productId") Integer productId,
                                                                                              @RequestParam("memberId") Integer memberId) {
        ServiceResult<FrontMemberProductBehaveStatisticsVO> serviceResult = new ServiceResult<FrontMemberProductBehaveStatisticsVO>();
        try {
            serviceResult
                    .setResult(memberModel.getBehaveStatisticsByProductId(productId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][getBehaveStatisticsByProductId]获得用户评价数时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 判断支付密码是否正确
     * @param balancePwd
     * @param request
     * @return  返回错误次数
     */
    @Override
    public ServiceResult<FrontCheckPwdVO> checkcheckBalancePwd(@RequestParam("balancePwd") String balancePwd,
                                                               @RequestParam("memberId") Integer memberId) {
        ServiceResult<FrontCheckPwdVO> serviceResult = new ServiceResult<FrontCheckPwdVO>();
        try {
            serviceResult.setResult(memberModel.checkcheckBalancePwd(balancePwd, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][checkcheckBalancePwd]验证余额支付密码时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 支付密码修改
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Member> editBalancePassword(@RequestParam("oldPwd") String oldPwd,
                                                     @RequestParam("newPwd") String newPwd,
                                                     @RequestParam("memberId") Integer memberId) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();

        try {
            serviceResult.setResult(memberModel.editBalancePassword(oldPwd, newPwd, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][editBalancePassword]修改支付密码时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 设置支付密码
     * @param password 支付密码
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Member> addBalancePassword(@RequestParam("password") String password,
                                                    @RequestBody Member member) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.addBalancePassword(password, member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[FrontMemberExtendService][addBalancePassword]设置支付密码时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> isMobExists(@RequestParam("mobile") String mobile) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberModel.isMobExists(mobile));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Member> getMemberBySessionId(@RequestParam("sessionId") String sessionId) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.getMemberBySessionId(sessionId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][getMemberById]根据sessionId[" + sessionId + "]取得会员表时出现异常："
                    + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberById]根据sessionId[" + sessionId + "]取得会员表时出现未知异常：",
                    e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Member> getMemberByToken(@RequestParam("token") String token) {
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        try {
            serviceResult.setResult(memberModel.getMemberByToken(token));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[MemberService][getMemberByToken]根据token[" + token + "]取得会员表时出现异常："
                    + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberByToken]根据token[" + token + "]取得会员表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public Member checkMobile(@RequestParam("mobile") String mobile) {
        return memberModel.checkMobile(mobile);
    }

//    @Override
    public int updatePassword(@RequestParam("id") Integer id, @RequestParam("password") String password) {
        return memberModel.updatePassword(id,password);
    }

    @Override
    public boolean updateMobileById(@RequestParam("memberId")Integer memberId, @RequestParam("mobile")String mobile) {
        Member member = new Member();
        member.setIsSmsVerify(1);
        member.setId(memberId);
        member.setMobile(mobile);
        return memberModel.updateMember(member);
    }

    @Override
    public List<Member> getPromotionUser(String saleCode) {
        return memberModel.getPromotionUser(saleCode);
    }

}