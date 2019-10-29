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
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.util.FeignObjectUtil;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberProductExchange")
@Service(value = "memberProductExchangeService")
public interface IMemberProductExchangeService {

    /**
     * 根据id取得用户换货
     * @param  memberProductExchangeId
     * @return
     */
    @RequestMapping(value = "getMemberProductExchangeById", method = RequestMethod.GET)
    ServiceResult<MemberProductExchange> getMemberProductExchangeById(@RequestParam("memberProductExchangeId") Integer memberProductExchangeId);

    /**
     * 根据登录用户取得用户换货列表 分页
     * @param pager
     * @param request
     * @return
     */
    @RequestMapping(value = "getMemberProductExchangeList", method = RequestMethod.POST)
    ServiceResult<List<MemberProductExchange>> getMemberProductExchangeList(@RequestBody FeignUtil feignUtil,
                                                                            @RequestParam("memberId") Integer memberId);

    /**
     * 保存用户换货
     * @param memberProductExchange
     * @param request
     * @return
     */
    @RequestMapping(value = "saveMemberProductExchange", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMemberProductExchange(FeignObjectUtil feignObjectUtil);

    /**
    * 更新用户换货
    * @param  memberProductExchange
    * @return
    */
    @RequestMapping(value = "updateMemberProductExchange", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMemberProductExchange(MemberProductExchange memberProductExchange);

    /**
    * 根据条件分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "getMemberProductExchanges", method = RequestMethod.POST)
    ServiceResult<List<MemberProductExchange>> getMemberProductExchanges(FeignUtil feignUtil);

    /**
     * 根据登录用户取得用户换货列表(封装商品对象和网单对象)
     * @param pager
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getExchangeListWithPrdAndOp", method = RequestMethod.POST)
    ServiceResult<List<MemberProductExchange>> getExchangeListWithPrdAndOp(@RequestBody PagerInfo pager,
                                                                           @RequestParam("memberId") Integer memberId);

    /**
     * 处理换货信息
     * @param memberProductExchange
     * @param user
     * @return
     */
    @RequestMapping(value = "audit", method = RequestMethod.POST)
    ServiceResult<Boolean> audit(FeignObjectUtil feignObjectUtil);

    /**
     * 用户换货时退件发货
     * @param memberProductExchange
     * @param member
     * @return
     */
    @RequestMapping(value = "doExchangeDeliverGoods", method = RequestMethod.POST)
    ServiceResult<Boolean> doExchangeDeliverGoods(FeignObjectUtil feignObjectUtil);

}