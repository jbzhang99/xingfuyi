package com.yixiekeji.service.seller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.seller.FrontSellerComplaintVO;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerComplaint")
@Service(value = "sellerComplaintService")
public interface ISellerComplaintService {

    /**
     * 根据id取得商家投诉管理
     * @param  sellerComplaintId
     * @return
     */
    @RequestMapping(value = "getSellerComplaintById", method = RequestMethod.GET)
    ServiceResult<SellerComplaint> getSellerComplaintById(@RequestParam("sellerComplaintId") Integer sellerComplaintId);

    /**
     * 保存商家投诉管理
     * @param request
     * @param sellerComplaint
     * @return
     */
    @RequestMapping(value = "saveSellerComplaint", method = RequestMethod.POST)
    ServiceResult<SellerComplaint> saveSellerComplaint(FeignObjectUtil feignObjectUtil);

    /**
     * 根据登录用户获得申诉列表
     * @param pager
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getSellerComplaintList", method = RequestMethod.POST)
    ServiceResult<List<FrontSellerComplaintVO>> getSellerComplaintList(@RequestBody PagerInfo pager,
                                                                       @RequestParam("memberId") Integer memberId);

    /**
    * 更新商家投诉管理
    * @param  sellerComplaint
    * @return
    */
    @RequestMapping(value = "updateSellerComplaint", method = RequestMethod.POST)
    ServiceResult<Integer> updateSellerComplaint(SellerComplaint sellerComplaint);

    /**
     * 根据登录用户获得申诉列表(封装商品对象和网单对象)
     * @param pager
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getComplaintListWithPrdAndOp", method = RequestMethod.POST)
    ServiceResult<List<FrontSellerComplaintVO>> getComplaintListWithPrdAndOp(@RequestBody PagerInfo pager,
                                                                             @RequestParam("memberId") Integer memberId);
    
    /** 
    * @Description: 客服投诉 
    * @Author: mofan 
    * @Date: 2019/10/21 
    */ 
    @PostMapping(value = "addSellerComplaint")
    Integer addSellerComplaint(@RequestBody SellerComplaint sellerComplaint);
}