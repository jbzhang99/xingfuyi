package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerTypeLogs;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerTypeLogs")
@Service(value = "sellerTypeLogsService")
public interface ISellerTypeLogsService {
    /**
    * 保存商家类型修改日志表
    * @param  sellerTypeLogs
    * @return
    */
    @RequestMapping(value = "saveSellerTypeLogs", method = RequestMethod.POST)
    ServiceResult<Boolean> saveSellerTypeLogs(SellerTypeLogs sellerTypeLogs);

    /**
    * 更新商家类型修改日志表
    * @param  sellerTypeLogs
    * @return
    */
    @RequestMapping(value = "updateSellerTypeLogs", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSellerTypeLogs(SellerTypeLogs sellerTypeLogs);

    /**
    * 删除商家类型修改日志表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delSellerTypeLogs", method = RequestMethod.GET)
    ServiceResult<Boolean> delSellerTypeLogs(@RequestParam("id") Integer id);

    /**
    * 根据id取得商家类型修改日志表
    * @param sellerTypeLogsId
    * @return
    */
    @RequestMapping(value = "getSellerTypeLogsById", method = RequestMethod.GET)
    ServiceResult<SellerTypeLogs> getSellerTypeLogsById(@RequestParam("sellerTypeLogsId") Integer sellerTypeLogsId);

    /**
     * 根据商品分类id获取商家类型修改日志
     * @param cateId
     * @return
     */
    @RequestMapping(value = "getSellerTypeLogsByCateId", method = RequestMethod.GET)
    ServiceResult<List<SellerTypeLogs>> getSellerTypeLogsByCateId(@RequestParam("cateId") Integer cateId);

}