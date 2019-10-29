package com.yixiekeji.service.order;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.seller.SellerComplaintVO;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "adminComplaint")
@Service(value = "adminComplaintService")
public interface IAdminComplaintService {

    /**
     * 根据id取得商家投诉管理
     * @param  sellerComplaintId
     * @return
     */
    @RequestMapping(value = "getSellerComplaintById", method = RequestMethod.GET)
    ServiceResult<SellerComplaint> getSellerComplaintById(@RequestParam("sellerComplaintId") Integer sellerComplaintId);

    /**
     * 保存商家投诉管理
     * @param  sellerComplaint
     * @return
     */
    @RequestMapping(value = "saveSellerComplaint", method = RequestMethod.POST)
    ServiceResult<Integer> saveSellerComplaint(FeignObjectUtil feignObjectUtil);

    /**
    * 更新商家投诉管理
    * @param  sellerComplaint
    * @return
    */
    @RequestMapping(value = "updateSellerComplaint", method = RequestMethod.POST)
    ServiceResult<Integer> updateSellerComplaint(SellerComplaint sellerComplaint);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SellerComplaintVO>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    ServiceResult<SellerComplaintVO> getById(@RequestParam("id") Integer id);

    /**
     * 重置申诉信息
     * @param source
     * @param state
     * @param backExchangeId
     * @param backExchangeId2 
     * @return
     */
    @RequestMapping(value = "resetState", method = RequestMethod.GET)
    ServiceResult<Boolean> resetState(@RequestParam("id") Integer id,
                                      @RequestParam("source") Integer source,
                                      @RequestParam("state") Integer state,
                                      @RequestParam("backExchangeId") Integer backExchangeId);
}