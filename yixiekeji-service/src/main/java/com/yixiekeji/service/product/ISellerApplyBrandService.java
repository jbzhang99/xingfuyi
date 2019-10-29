package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerApplyBrand;

/**
 * 商家品牌管理
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerApplyBrand")
@Service(value = "sellerApplyBrandService")
public interface ISellerApplyBrandService {

    /**
     * 保存品牌信息
     * 用途：商家中心添加品牌信息
     * @param brand
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ServiceResult<Boolean> save(SellerApplyBrand brand);

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    ServiceResult<SellerApplyBrand> getById(@RequestParam("id") Integer id);

    /**
     * 分页查询品牌信息
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SellerApplyBrand>> page(FeignUtil feignUtil);

    /**
     * 分页查询待提交品牌信息
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "todoList", method = RequestMethod.POST)
    ServiceResult<List<SellerApplyBrand>> todoList(FeignUtil feignUtil);

    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    ServiceResult<Boolean> update(SellerApplyBrand brand);

    /**
     * 提交到平台审核
     * @param id
     * @return
     */
    @RequestMapping(value = "commit", method = RequestMethod.GET)
    ServiceResult<Boolean> commit(@RequestParam("id") Integer id);

    /**
     * 删除品牌信息，逻辑删除
     * @param id
     * @param sellerId 
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id,
                               @RequestParam("sellerId") Integer sellerId);
}
