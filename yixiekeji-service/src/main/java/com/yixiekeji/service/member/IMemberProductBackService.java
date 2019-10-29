package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.util.FeignObjectUtil;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberProductBack")
@Service(value = "memberProductBackService")
public interface IMemberProductBackService {

    /**
     * 根据id取得用户退货
     * @param  memberProductBackId
     * @return
     */
    @RequestMapping(value = "getMemberProductBackById", method = RequestMethod.GET)
    ServiceResult<MemberProductBack> getMemberProductBackById(@RequestParam("memberProductBackId") Integer memberProductBackId);

    /**
     * 根据登录用户取得用户退货列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getMemberProductBackList", method = RequestMethod.POST)
    ServiceResult<List<MemberProductBack>> getMemberProductBackList(@RequestBody PagerInfo pager,
                                                                    @RequestParam("memberId") Integer memberId);

    /**
     * 判断是否可以发起退换货申请
     * @param orderId
     * @param orderProductId
     * @param request
     * @return
     */
    @RequestMapping(value = "canApplyProductBackOrExchange", method = RequestMethod.GET)
    public ServiceResult<Integer> canApplyProductBackOrExchange(@RequestParam("orderId") Integer orderId,
                                                                @RequestParam("orderProductId") Integer orderProductId,
                                                                @RequestParam("memberId") Integer memberId);

    /**
     * 保存用户退货
     * @param memberProductBack
     * @param request
     * @return
     */
    @RequestMapping(value = "saveMemberProductBack", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMemberProductBack(FeignObjectUtil feignObjectUtil);

    /**
    * 更新用户退货
    * @param  memberProductBack
    * @return
    */
    @RequestMapping(value = "updateMemberProductBack", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMemberProductBack(MemberProductBack memberProductBack);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<MemberProductBack>> page(FeignUtil feignUtil);

    /**
     * 用户退货时退款
     * @param memberProductBackId 退货申请ID
     * @param optId 操作人ID
     * @param optName 操作人名称
     * @return
     */
    @RequestMapping(value = "backMoney", method = RequestMethod.GET)
    ServiceResult<Boolean> backMoney(@RequestParam("memberProductBackId") Integer memberProductBackId,
                                     @RequestParam("optId") Integer optId,
                                     @RequestParam("optName") String optName);

    /**
     * 获取结算周期内的退货申请
     * @param sellerId
     * @param startTime
     * @param endTime
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSettleBacks", method = RequestMethod.POST)
    ServiceResult<List<MemberProductBack>> getSettleBacks(@RequestParam("sellerId") Integer sellerId,
                                                          @RequestParam("startTime") String startTime,
                                                          @RequestParam("endTime") String endTime,
                                                          @RequestBody PagerInfo pager);

    /**
     * 根据登录用户取得用户退货列表（封装商品对象和网单对象）
     * @param pager
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getBackListWithPrdAndOp", method = RequestMethod.POST)
    ServiceResult<List<MemberProductBack>> getBackListWithPrdAndOp(@RequestBody PagerInfo pager,
                                                                   @RequestParam("memberId") Integer memberId);

    /**
     * 审核退货信息
     * @param memberProductBack
     * @return
     */
    @RequestMapping(value = "audit", method = RequestMethod.POST)
    ServiceResult<Boolean> audit(MemberProductBack memberProductBack);

    /**
     * 用户发货
     * @param memberProductBack
     * @param member
     * @return
     */
    @RequestMapping(value = "doBackDeliverGoods", method = RequestMethod.POST)
    ServiceResult<Boolean> doBackDeliverGoods(FeignObjectUtil feignObjectUtil);

    /**
     * 店铺收货
     * @param user 
     * @param back
     * @return
     */
    @RequestMapping(value = "takeDeliver", method = RequestMethod.POST)
    ServiceResult<Boolean> takeDeliver(FeignObjectUtil feignObjectUtil);

}