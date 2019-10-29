package com.yixiekeji.service.seller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerQq;

/**
 * 商家QQ管理
 *                       
 * @Filename: ISellerQqService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerQq")
@Service(value = "sellerQqService")
public interface ISellerQqService {

    /**
     * 根据店铺ID获取店铺正在使用的QQ
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "getInusedSellerQqBySId", method = RequestMethod.GET)
    ServiceResult<List<SellerQq>> getInusedSellerQqBySId(@RequestParam("sellerId") Integer sellerId);

    /**
     * 根据id取得商家客服QQ
     * @param  sellerQqId
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    ServiceResult<SellerQq> getById(@RequestParam("sellerQqId") Integer sellerQqId);

    /**
     * 保存商家客服QQ
     * @param  sellerQq
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ServiceResult<Integer> save(SellerQq sellerQq);

    /**
    * 更新商家客服QQ
    * @param  sellerQq
    * @return
    */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    ServiceResult<Integer> update(SellerQq sellerQq);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SellerQq>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}