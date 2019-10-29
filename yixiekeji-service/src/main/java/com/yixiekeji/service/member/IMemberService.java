package com.yixiekeji.service.member;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.*;
import com.yixiekeji.vo.member.FrontCheckPwdVO;
import com.yixiekeji.vo.member.FrontMemberProductBehaveStatisticsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "member")
@Service(value = "memberService")
public interface IMemberService {

    /**
     * 根据id取得会员
     * @param  memberId
     * @return
     */
    @RequestMapping(value = "getMemberById", method = RequestMethod.GET)
    ServiceResult<Member> getMemberById(@RequestParam("memberId") Integer memberId);

    /**
     * 根据条件取得会员
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getMembers", method = RequestMethod.POST)
    ServiceResult<List<Member>> getMembers(FeignUtil feignUtil);

    /**
     * 保存会员
     * @param  member
     * @return
     */
    @RequestMapping(value = "saveMember", method = RequestMethod.POST)
    ServiceResult<Integer> saveMember(Member member);

    /**
     * 更新会员
     * @param  member
     * @return
     */
    @RequestMapping(value = "updateMember", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMember(Member member);

    /**
     * 会员经验值、积分变更</br>
     * <li>1、会员ID必须
     * <li>2、经验值或积分值必须
     * <li>3、具体操作类型必须：1、会员注册；2、会员登录；3、商品购买；4、商品评论；5、系统添加；6、系统减少
     * <li>4、操作的类型必须：1、经验值；2、积分
     * <li>5、会员名称不能为null
     * <li>6、操作描述不能为null
     * <li>7、具体操作类型中6是减少经验值或积分，其余都是增加，经验值或积分值都是正整数
     * <li>8、当动作是减少时，判断是否够扣减，不够时返回false及错误信息
     * @param memberGradeIntegralLogs
     * @return
     */
    @RequestMapping(value = "updateMemberValue", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMemberValue(MemberGradeIntegralLogs memberGradeIntegralLogs);

    /**
     * 根据会员ID取得会员等级升级日志
     * @param memberId 会员ID
     * @param pager 分页信息
     * @return
     */
    @RequestMapping(value = "getMemberGradeUpLogs", method = RequestMethod.POST)
    ServiceResult<List<MemberGradeUpLogs>> getMemberGradeUpLogs(@RequestParam("memberId") Integer memberId,
                                                                @RequestBody PagerInfo pager);

    /**
     * 根据会员ID和类型取得会员经验值积分值变更日志
     * @param memberId 会员ID
     * @param type 类型：1、经验值；2、积分
     * @param pager 分页信息
     * @return
     */
    @RequestMapping(value = "getMemberGradeIntegralLogs", method = RequestMethod.POST)
    ServiceResult<List<MemberGradeIntegralLogs>> getMemberGradeIntegralLogs(@RequestParam("memberId") Integer memberId,
                                                                            @RequestParam("type") Integer type,
                                                                            @RequestBody PagerInfo pager);

    /**
     * 根据会员ID获取会员登录日志
     * @param memberId 会员ID
     * @param pager 分页信息
     * @return
     */
    @RequestMapping(value = "getMemberLoginLogs", method = RequestMethod.POST)
    ServiceResult<List<MemberLoginLogs>> getMemberLoginLogs(@RequestParam("memberId") Integer memberId,
                                                            @RequestBody PagerInfo pager);

    /**
     * 会员余额变更</br>
     * <li>1、会员ID必须
     * <li>2、变更金额必须且传正数（money字段）
     * <li>3、具体动作类型必须：1、充值；2、退款；3、消费；4、提款；5、系统添加；6、系统减少
     * <li>4、会员名称不能为null
     * <li>5、操作描述不能为null
     * <li>6、具体动作类型中3、4、6是扣除余额，其余都是增加，变更金额都是正数
     * <li>7、当动作是减少余额时，判断是否够扣减，不够时返回false及错误信息
     * @param memberBalanceLogs
     * @return
     */
    @RequestMapping(value = "updateMemberBalance", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMemberBalance(MemberBalanceLogs memberBalanceLogs);

    /**
     * 会员登录，修改会员登录信息，记录登录日志
     * @param memberName 用户名
     * @param password 密码
     * @param ip
     * @param source 登录来源
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "memberLogin", method = RequestMethod.GET)
    ServiceResult<Member> memberLogin(@RequestParam("memberName") String memberName,
                                      @RequestParam("password") String password,
                                      @RequestParam("ip") String ip,
                                      @RequestParam("source") Integer source);

    @RequestMapping(value = "memberLogins", method = RequestMethod.GET)
    ServiceResult<Member> memberLogins(@RequestParam("mobile") String mobile,
                                       @RequestParam("password") String password,
                                       @RequestParam("ip") String ip,
                                       @RequestParam("source") Integer source);

    /**
     * 会员注册
     * @param member
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "memberRegister", method = RequestMethod.POST)
    ServiceResult<Member> memberRegister(Member member);

    /**
     * 根据会员名称取会员
     * @param name
     * @return
     */
    @RequestMapping(value = "getMemberByName", method = RequestMethod.GET)
    ServiceResult<List<Member>> getMemberByName(@RequestParam("name") String name);

    /**
     * 用户手机号是否存在
     * @param mobile
     * @return
     */
    @RequestMapping(value = "isMobExists", method = RequestMethod.GET)
    ServiceResult<Boolean> isMobExists(@RequestParam("mobile") String mobile);

    /**
     * 根据id取得商城会员等级配置
     * @param  memberGradeConfigId
     * @return
     */
    @RequestMapping(value = "getMemberGradeConfig", method = RequestMethod.GET)
    ServiceResult<MemberGradeConfig> getMemberGradeConfig(@RequestParam("memberGradeConfigId") Integer memberGradeConfigId);

    /**
     * 根据id取得会员经验值和积分规则
     * <li>当state传入null时根据ID取数据
     * <li>当state不为null时根据ID及状态取数据
     *
     * @param  memberRuleId 主键
     * @param  state 状态：1、开始；2、关闭
     * @return
     */
    @RequestMapping(value = "getMemberRule", method = RequestMethod.GET)
    ServiceResult<MemberRule> getMemberRule(@RequestParam("memberRuleId") Integer memberRuleId,
                                            @RequestParam("state") Integer state);

    /**
     * 会员注册时送经验值和积分</br>
     * 涉及表：
     * <li>1、member
     * <li>2、member_grade_integral_logs
     * <li>3、member_grade_up_logs
     *
     * @param memberId 会员ID，必须
     * @param memberName 会员名，不能为null
     *
     * @return Member 会员对象
     */
    @RequestMapping(value = "memberRegistSendValue", method = RequestMethod.GET)
    ServiceResult<Boolean> memberRegistSendValue(@RequestParam("memberId") Integer memberId,
                                                 @RequestParam("memberName") String memberName);

    /**
     * 会员登录时送经验值和积分</br>
     * 用户每日第一次登陆时送积分，之后的登陆不再送积分。</br>
     * 涉及表：
     * <li>1、member
     * <li>2、member_grade_integral_logs
     * <li>3、member_grade_up_logs
     *
     * @param memberId 会员ID，必须
     * @param memberName 会员名，不能为null
     *
     * @return Member 会员对象
     */
    @RequestMapping(value = "memberLoginSendValue", method = RequestMethod.GET)
    ServiceResult<Boolean> memberLoginSendValue(@RequestParam("memberId") Integer memberId,
                                                @RequestParam("memberName") String memberName);

    /**
     * 会员下单时送经验值和积分</br>
     * 涉及表：
     * <li>1、member
     * <li>2、member_grade_integral_logs
     * <li>3、member_grade_up_logs
     *
     * @param memberId 会员ID，必须
     * @param memberName 会员名，不能为null
     * @param orderId 订单号
     *
     * @return Member 会员对象
     */
    @RequestMapping(value = "memberOrderSendValue", method = RequestMethod.GET)
    ServiceResult<Boolean> memberOrderSendValue(@RequestParam("memberId") Integer memberId,
                                                @RequestParam("memberName") String memberName,
                                                @RequestParam("orderId") Integer orderId);

    /**
     * 会员评论时送经验值和积分</br>
     * 涉及表：
     * <li>1、member
     * <li>2、member_grade_integral_logs
     * <li>3、member_grade_up_logs
     *
     * @param memberId 会员ID，必须
     * @param memberName 会员名，不能为null
     * @param ordersProductId 网单ID
     *
     * @return Member 会员对象
     */
    @RequestMapping(value = "memberEvaluateSendValue", method = RequestMethod.GET)
    ServiceResult<Boolean> memberEvaluateSendValue(@RequestParam("memberId") Integer memberId,
                                                   @RequestParam("memberName") String memberName,
                                                   @RequestParam("ordersProductId") Integer ordersProductId);

    /**
     * 修改密码提交
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @RequestMapping(value = "editPassword", method = RequestMethod.POST)
    ServiceResult<Member> editPassword(@RequestParam("oldPwd") String oldPwd,
                                       @RequestParam("newPwd") String newPwd,
                                       @RequestBody Member member);

    /**
     * 根据查询条件取所有的评论 单品页使用
     * @param request
     * @param pager
     * @return
     */
    @RequestMapping(value = "getBehaveStatisticsByProductId", method = RequestMethod.GET)
    public ServiceResult<FrontMemberProductBehaveStatisticsVO> getBehaveStatisticsByProductId(@RequestParam("productId") Integer productId,
                                                                                              @RequestParam("memberId") Integer memberId);

    /**
     * 判断 余额支付密码是否正确
     * @param balancePwd
     * @param request
     * @return  返回错误次数
     */
    @RequestMapping(value = "checkcheckBalancePwd", method = RequestMethod.GET)
    public ServiceResult<FrontCheckPwdVO> checkcheckBalancePwd(@RequestParam("balancePwd") String balancePwd,
                                                               @RequestParam("memberId") Integer memberId);

    /**
     * 支付密码修改
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @RequestMapping(value = "editBalancePassword", method = RequestMethod.GET)
    ServiceResult<Member> editBalancePassword(@RequestParam("oldPwd") String oldPwd,
                                              @RequestParam("newPwd") String newPwd,
                                              @RequestParam("memberId") Integer memberId);

    /**
     * 设置支付密码
     * @param password 支付密码
     * @param request
     * @return
     */
    @RequestMapping(value = "addBalancePassword", method = RequestMethod.POST)
    ServiceResult<Member> addBalancePassword(@RequestParam("password") String password,
                                             @RequestBody Member member);

    /**
     * 根据sessionId获取会员
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "getMemberBySessionId", method = RequestMethod.GET)
    ServiceResult<Member> getMemberBySessionId(@RequestParam("sessionId") String sessionId);

    /**
     * 根据token获取会员
     * @param token
     * @return
     */
    @RequestMapping(value = "getMemberByToken", method = RequestMethod.GET)
    ServiceResult<Member> getMemberByToken(@RequestParam("token") String token);

    /**
     * @Description: 手机号是否存在
     * @Author: mofan
     * @Date: 2019/10/9
     */
    @GetMapping(value = "checkMobile")
    Member checkMobile(@RequestParam("mobile") String mobile);


    /**
     * @Description: 根据手机号来改密码
     * @Author: mofan
     * @Date: 2019/10/10
     */
    @GetMapping(value = "updatePassword")
    int updatePassword(@RequestParam("id") Integer id, @RequestParam("password") String password);

    /** 
    * @Description: 根据id修改手机号
    * @Author: mofan 
    * @Date: 2019/10/18 
    */ 
    @GetMapping(value = "updateMobileById")
    boolean updateMobileById(@RequestParam("memberId") Integer memberId,@RequestParam("mobile")String mobile);
        
    /** 
    * @Description: 根据推广码获取推广用户列表
    * @Author: mofan 
    * @Date: 2019/10/21 
    */ 
    @GetMapping(value = "getPromotionUser")
    List<Member> getPromotionUser(@RequestParam("saleCode")String saleCode);
}