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
import com.yixiekeji.entity.member.MemberCollectionProduct;
import com.yixiekeji.vo.member.FrontMemberCollectionProductVO;

/**
 * 用户收藏商品
 *                       
 * @Filename: IMemberCollectionProductService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberCollectionProduct")
@Service(value = "memberCollectionProductService")
public interface IMemberCollectionProductService {

    /**
     * 根据id取得会员收藏商品表
     * @param  memberCollectionProductId
     * @return
     */
    @RequestMapping(value = "getMemberCollectionProductById", method = RequestMethod.GET)
    ServiceResult<MemberCollectionProduct> getMemberCollectionProductById(@RequestParam("memberCollectionProductId") Integer memberCollectionProductId);

    /**
     * 根据会员id取得会员收藏商品表
     * @param request
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCollectionProductByMemberId", method = RequestMethod.POST)
    ServiceResult<List<FrontMemberCollectionProductVO>> getCollectionProductByMemberId(@RequestBody FeignUtil feignUtil,
                                                                                       @RequestParam("memberId") Integer memberId);

    /**
     * 保存会员收藏商品表
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "saveMemberCollectionProduct", method = RequestMethod.GET)
    public ServiceResult<MemberCollectionProduct> saveMemberCollectionProduct(@RequestParam("productId") Integer productId,
                                                                              @RequestParam("memberId") Integer memberId);

    /**
     * 取消收藏商品
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "cancelCollectionProduct", method = RequestMethod.GET)
    public ServiceResult<MemberCollectionProduct> cancelCollectionProduct(@RequestParam("productId") Integer productId,
                                                                          @RequestParam("memberId") Integer memberId);
    /** 
    * @Description:  根据id取得会员收藏商品表数量
    * @Author: mofan 
    * @Date: 2019/10/17 
    */
    @RequestMapping(value = "getCollectionProductCount", method = RequestMethod.GET)
    Integer getCollectionProductCount(@RequestParam("memberId") Integer memberId);
}