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
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.vo.member.FrontMemberCollectionSellerVO;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberCollectionSeller")
@Service(value = "memberCollectionSellerService")
public interface IMemberCollectionSellerService {

    /**
     * 会员收藏商铺
     * @param sellerId
     * @param memberId
     * @return 如果会员已经收藏过该店铺，返回true，收藏成功返回true
     */
    @RequestMapping(value = "collectionSeller", method = RequestMethod.GET)
    ServiceResult<Boolean> collectionSeller(@RequestParam("sellerId") Integer sellerId,
                                            @RequestParam("memberId") Integer memberId);

    /**
     * 取消收藏商铺
     * @param sellerId
     * @param memberId
     * @return
     */
    @RequestMapping(value = "cancelCollectionSeller", method = RequestMethod.GET)
    ServiceResult<Boolean> cancelCollectionSeller(@RequestParam("sellerId") Integer sellerId,
                                                  @RequestParam("memberId") Integer memberId);

    /**
     * 根据id取得会员收藏商铺表
     * @param  memberCollectionSellerId
     * @return
     */
    @RequestMapping(value = "getMemberCollectionSellerById", method = RequestMethod.GET)
    ServiceResult<MemberCollectionSeller> getMemberCollectionSellerById(@RequestParam("memberCollectionSellerId") Integer memberCollectionSellerId);

    /**
     * 根据会员id取得会员收藏商铺表
     * @param request
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCollectionSellerByMemberid", method = RequestMethod.POST)
    ServiceResult<List<FrontMemberCollectionSellerVO>> getCollectionSellerByMemberid(@RequestParam("memberId") Integer memberId,
                                                                                     @RequestBody PagerInfo pager);

    /**
     * 根据用户ID和店铺ID获取收藏信息
     * @param sellerId
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getBySellerIdAndMId", method = RequestMethod.GET)
    ServiceResult<List<MemberCollectionSeller>> getBySellerIdAndMId(@RequestParam("sellerId") Integer sellerId,
                                                                    @RequestParam("memberId") Integer memberId);
    /** 
    * @Description: 根据会员id获取店铺数量
    * @Author: mofan 
    * @Date: 2019/10/17 
    */
    @RequestMapping(value = "getCollectionSeller", method = RequestMethod.GET)
    Integer getCollectionSeller(@RequestParam("memberId") Integer memberId);
}