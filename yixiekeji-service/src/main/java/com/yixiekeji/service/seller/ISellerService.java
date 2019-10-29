package com.yixiekeji.service.seller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.Seller;

/**
 * 商家基本信息service
 *                       
 * @Filename: ISellerService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "seller")
@Service(value = "sellerService")
public interface ISellerService {

    /**
     * 根据id取得商家表
     * @param  sellerId
     * @return
     */
    @RequestMapping(value = "getSellerById", method = RequestMethod.GET)
    ServiceResult<Seller> getSellerById(@RequestParam("sellerId") Integer sellerId);

    /**
     * 根据用户id获取商家
     * @param userid
     * @return
     */
    @RequestMapping(value = "getSellerByMemberId", method = RequestMethod.GET)
    ServiceResult<Seller> getSellerByMemberId(@RequestParam("memberId") Integer memberId);

    /**
     * 保存商家表
     * @param  seller
     * @return
     */
    @RequestMapping(value = "saveSeller", method = RequestMethod.POST)
    ServiceResult<Integer> saveSeller(Seller seller);

    /**
    * 更新商家表
    * @param  seller
    * @return
    */
    @RequestMapping(value = "updateSeller", method = RequestMethod.POST)
    ServiceResult<Integer> updateSeller(Seller seller);

    /**
     * 根据条件分页查询商家，PagerInfo传null返回全部数据
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSellers", method = RequestMethod.POST)
    ServiceResult<List<Seller>> getSellers(FeignUtil feignUtil);

    /**
     * 冻结商家，修改商家状态，修改商家下所有商品状态
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "freezeSeller", method = RequestMethod.GET)
    ServiceResult<Boolean> freezeSeller(@RequestParam("sellerId") Integer sellerId);

    /**
     * 解冻商家，修改商家状态，修改商家下所有商品状态（修改为7下架）
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "unFreezeSeller", method = RequestMethod.GET)
    ServiceResult<Boolean> unFreezeSeller(@RequestParam("sellerId") Integer sellerId);

    /**
     * 根据商家的用户ID，获取商家所在的地址（省市级）
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getSellerLocationByMId", method = RequestMethod.GET)
    ServiceResult<String> getSellerLocationByMId(@RequestParam("memberId") Integer memberId);

    /**
     * 定时任务设定商家的评分，用户评论各项求平均值设置为商家各项的综合评分
     * @return
     */
    @RequestMapping(value = "jobSetSellerScore", method = RequestMethod.GET)
    ServiceResult<Boolean> jobSetSellerScore();

    /**
     * 定时任务设定商家各项统计数据
     * @return
     */
    @RequestMapping(value = "jobSellerStatistics", method = RequestMethod.GET)
    ServiceResult<Boolean> jobSellerStatistics();

    /**
     * 根据名称获取商家
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSellerByName", method = RequestMethod.GET)
    ServiceResult<List<Seller>> getSellerByName(@RequestParam("name") String name);

    /**
     * 根据店铺名称获取商家
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSellerBySellerName", method = RequestMethod.GET)
    ServiceResult<List<Seller>> getSellerBySellerName(@RequestParam("sellerName") String sellerName);
}