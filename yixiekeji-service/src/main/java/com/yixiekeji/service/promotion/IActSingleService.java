package com.yixiekeji.service.promotion;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.single.ActSingle;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "actSingle")
@Service(value = "actSingleService")
public interface IActSingleService {

    /**
     * 根据id取得单品立减活动
     * @param  actSingleId
     * @return
     */
    @RequestMapping(value = "getActSingleById", method = RequestMethod.GET)
    ServiceResult<ActSingle> getActSingleById(@RequestParam("actSingleId") Integer actSingleId);

    /**
     * 保存单品立减活动
     * @param  actSingle
     * @return
     */
    @RequestMapping(value = "saveActSingle", method = RequestMethod.POST)
    ServiceResult<Boolean> saveActSingle(ActSingle actSingle);

    /**
     * 更新单品立减活动
     * @param actSingle
     * @return
     */
    @RequestMapping(value = "updateActSingle", method = RequestMethod.POST)
    ServiceResult<Boolean> updateActSingle(ActSingle actSingle);

    /**
     * 更新单品立减活动状态（只修改活动状态、审核意见、修改者信息）
     * @param actSingle
     * @return
     */
    @RequestMapping(value = "updateActSingleStatus", method = RequestMethod.POST)
    ServiceResult<Boolean> updateActSingleStatus(ActSingle actSingle);

    /**
     * 删除单品立减活动
     * @param actSingleId
     * @param userId 删除人ID
     * @param userName 删除人名称
     * @return
     */
    @RequestMapping(value = "deleteActSingle", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteActSingle(@RequestParam("actSingleId") Integer actSingleId,
                                           @RequestParam("userId") Integer userId,
                                           @RequestParam("userName") String userName);

    /**
     * 根据条件取得单品立减活动
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getActSingles", method = RequestMethod.POST)
    ServiceResult<List<ActSingle>> getActSingles(FeignUtil feignUtil);

    /**
     * 根据商家ID、渠道、商品ID取得单品立减活动（当前时间有效的活动，如果有多个，只取最新的一个）
     * 
     * @param sellerId
     * @param channel
     * @param productId
     * @return
     */
    @RequestMapping(value = "getEffectiveActSingle", method = RequestMethod.GET)
    ServiceResult<ActSingle> getEffectiveActSingle(@RequestParam("sellerId") Integer sellerId,
                                                   @RequestParam("channel") Integer channel,
                                                   @RequestParam("productId") Integer productId);

}