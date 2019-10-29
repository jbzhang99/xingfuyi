package com.yixiekeji.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerManageCate;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerManageCate")
@Service(value = "sellerManageCateService")
public interface ISellerManageCateService {
    /**
    * 保存商家可以经营商品分类表
    * @param  sellerManageCate
    * @return
    */
    @RequestMapping(value = "saveSellerManageCate", method = RequestMethod.POST)
    ServiceResult<Boolean> saveSellerManageCate(SellerManageCate sellerManageCate);

    /**
    * 更新商家可以经营商品分类表
    * @param  sellerManageCate
    * @return
    */
    @RequestMapping(value = "updateSellerManageCate", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSellerManageCate(SellerManageCate sellerManageCate);

    /**
    * 删除商家可以经营商品分类表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delSellerManageCate", method = RequestMethod.GET)
    ServiceResult<Boolean> delSellerManageCate(@RequestParam("id") Integer id);

    /**
     * 平台审核
     * @param map
     * key: id、optId
     * @return
     */
    @RequestMapping(value = "auditing", method = RequestMethod.POST)
    ServiceResult<Boolean> auditing(Map<String, String> map);

    /**
     * 平台停用
     * @param map
     * key: id、stopId、stopReason
     * @return
     */
    @RequestMapping(value = "stop", method = RequestMethod.POST)
    ServiceResult<Boolean> stop(Map<String, String> map);

    /**
     * 提交平台审核
     * @param id
     * @return
     */
    @RequestMapping(value = "commit", method = RequestMethod.GET)
    ServiceResult<Boolean> commit(@RequestParam("id") Integer id);

    /**
    * 根据id取得商家可以经营商品分类表
    * @param sellerManageCateId
    * @return
    */
    @RequestMapping(value = "getSellerManageCateById", method = RequestMethod.GET)
    ServiceResult<SellerManageCate> getSellerManageCateById(@RequestParam("sellerManageCateId") Integer sellerManageCateId);

    /**
    * 分页
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageSellerManageCate", method = RequestMethod.POST)
    ServiceResult<List<SellerManageCate>> pageSellerManageCate(FeignUtil feignUtil);
}