package com.yixiekeji.service.seller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.util.FeignProjectUtil;
import com.yixiekeji.vo.seller.TransportJson;

/**
 * 商家运费
 *                       
 * @Filename: ISellerTransportService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerTransport")
@Service(value = "sellerTransportService")
public interface ISellerTransportService {

    /**
     * 根据id取得卖家运费模板
     * @param  sellerTransportId
     * @return
     */
    @RequestMapping(value = "getSellerTransportById", method = RequestMethod.GET)
    ServiceResult<SellerTransport> getSellerTransportById(@RequestParam("sellerTransportId") Integer sellerTransportId);

    /**
     * 保存卖家运费模板
     * @param  sellerTransport
     * @return
     */
    @RequestMapping(value = "saveSellerTransport", method = RequestMethod.POST)
    ServiceResult<Integer> saveSellerTransport(SellerTransport sellerTransport);

    /**
    * 更新卖家运费模板
    * @param  sellerTransport
    * @return
    */
    @RequestMapping(value = "updateSellerTransport", method = RequestMethod.POST)
    ServiceResult<Integer> updateSellerTransport(SellerTransport sellerTransport);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SellerTransport>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @param sellerId 
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("sellerId") Integer sellerId,
                               @RequestParam("id") Integer id);

    /**
     * 运费信息解析json数据
     * @param st
     * @return
     */
    @RequestMapping(value = "assembleTransportInfo", method = RequestMethod.POST)
    ServiceResult<List<TransportJson>> assembleTransportInfo(SellerTransport st);

    /**
     * 取卖家的运费模板
     * @param id
     * @return
     */
    @RequestMapping(value = "getEffectTransportBySellerId", method = RequestMethod.GET)
    ServiceResult<List<SellerTransport>> getEffectTransportBySellerId(@RequestParam("sellerId") Integer sellerId);

    /**
     * 根据运费模板ID启用某个模板
     * @param sellerId
     * @param id
     * @param state 
     * @return
     */
    @RequestMapping(value = "transportInUse", method = RequestMethod.GET)
    ServiceResult<Boolean> transportInUse(@RequestParam("id") Integer id,
                                          @RequestParam("state") Integer state);

    /**
     * 按照计算类型获取商家的运费模板
     * @param transportType1
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "getTransportByTypeAndSellerId", method = RequestMethod.GET)
    ServiceResult<List<SellerTransport>> getTransportByTypeAndSellerId(@RequestParam("transportType") Integer transportType,
                                                                       @RequestParam("sellerId") Integer sellerId);

    /**
     * 计算运费
     * @param product
     * @param productGoods
     * @param number
     * @param cityId
     * @return
     */
    @RequestMapping(value = "calculateTransFee", method = RequestMethod.POST)
    ServiceResult<BigDecimal> calculateTransFee(@RequestBody FeignProjectUtil feignProjectUtil,
                                                @RequestParam("number") Integer number,
                                                @RequestParam("cityId") Integer cityId);

    /**
     * 获取商品运费价格
     * @param request
     * @param response
     * @param productId
     * @param cityId
     * @return
     */
    @RequestMapping(value = "getTransFee", method = RequestMethod.POST)
    ServiceResult<BigDecimal> getTransFee(@RequestParam("productId") Integer productId,
                                          @RequestParam("cityId") Integer cityId,
                                          @RequestParam("num") Integer num);
}