package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerCate;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerCate")
@Service(value = "sellerCateService")
public interface ISellerCateService {
    /**
    * 保存商家分类
    * @param  sellerCate
    * @return
    */
    @RequestMapping(value = "saveSellerCate", method = RequestMethod.POST)
    ServiceResult<Boolean> saveSellerCate(SellerCate sellerCate);

    /**
    * 更新商家分类
    * @param  sellerCate
    * @return
    */
    @RequestMapping(value = "updateSellerCate", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSellerCate(SellerCate sellerCate);

    /**
    * 删除商家分类
    * @param  id
     * @param sellerId 
    * @return
    */
    @RequestMapping(value = "delSellerCate", method = RequestMethod.GET)
    ServiceResult<Boolean> delSellerCate(@RequestParam("id") Integer id,
                                         @RequestParam("sellerId") Integer sellerId);

    /**
    * 根据id取得商家分类
    * @param sellerCateId
    * @return
    */
    @RequestMapping(value = "getSellerCateById", method = RequestMethod.GET)
    ServiceResult<SellerCate> getSellerCateById(@RequestParam("sellerCateId") Integer sellerCateId);

    /**
    * 分页
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageSellerCate", method = RequestMethod.POST)
    ServiceResult<List<SellerCate>> pageSellerCate(FeignUtil feignUtil);

    /**
     * 根据pid查询商家分类
     * @param pid
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "getByPid", method = RequestMethod.GET)
    ServiceResult<List<SellerCate>> getByPid(@RequestParam("pid") Integer pid,
                                             @RequestParam("sellerId") Integer sellerId);

    /**
     * 获得所有商家分类（显示状态），封装了分类的子分类
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "getOnuseSellerCate", method = RequestMethod.GET)
    public ServiceResult<List<SellerCate>> getOnuseSellerCate(@RequestParam("sellerId") Integer sellerId);
}