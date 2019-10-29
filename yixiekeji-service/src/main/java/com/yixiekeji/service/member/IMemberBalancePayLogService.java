package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalancePayLog;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberBalancePayLog")
@Service(value = "memberBalancePayLogService")
public interface IMemberBalancePayLogService {

    /**
     * 根据id取得会员充值记录
     * @param  memberBalancePayLogId
     * @return
     */
    @RequestMapping(value = "getMemberBalancePayLogById", method = RequestMethod.GET)
    ServiceResult<MemberBalancePayLog> getMemberBalancePayLogById(@RequestParam("memberBalancePayLogId") Integer memberBalancePayLogId);

    /**
     * 保存会员充值记录
     * @param  memberBalancePayLog
     * @return
     */
    @RequestMapping(value = "saveMemberBalancePayLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveMemberBalancePayLog(MemberBalancePayLog memberBalancePayLog);

    /**
    * 更新会员充值记录
    * @param  memberBalancePayLog
    * @return
    */
    @RequestMapping(value = "updateMemberBalancePayLog", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberBalancePayLog(MemberBalancePayLog memberBalancePayLog);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<MemberBalancePayLog>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    /**
     * 以订单号获取支付日志
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "getByOrderSn", method = RequestMethod.GET)
    ServiceResult<MemberBalancePayLog> getByOrderSn(@RequestParam("orderNo") String orderNo);

    /**
     * 支付后更改支付记录
     * @param orderNo
     * @param amount
     * @param tradeSn
     * @return
     */
    @RequestMapping(value = "payAfter", method = RequestMethod.GET)
    ServiceResult<Boolean> payAfter(@RequestParam("orderNo") String orderNo,
                                    @RequestParam("amount") String amount,
                                    @RequestParam("tradeSn") String tradeSn);

    /**
     * 余额支付前操作：创建支付记录
     * @param optionsRadios
     * @param amount
     * @param ordersn
     * @param member
     * @return
     */
    @RequestMapping(value = "payBefore", method = RequestMethod.POST)
    ServiceResult<Boolean> payBefore(@RequestParam("optionsRadios") String optionsRadios,
                                     @RequestParam("amount") String amount,
                                     @RequestParam("ordersn") String ordersn,
                                     @RequestBody Member member);

}