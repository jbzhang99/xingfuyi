package com.yixiekeji.service.promotion;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.full.ActFull;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "actFull")
@Service(value = "actFullService")
public interface IActFullService {

    /**
     * 根据id取得满减活动
     * @param  actFullId
     * @return
     */
    @RequestMapping(value = "getActFullById", method = RequestMethod.GET)
    ServiceResult<ActFull> getActFullById(@RequestParam("actFullId") Integer actFullId);

    /**
     * 保存满减活动
     * @param  actFull
     * @return
     */
    @RequestMapping(value = "saveActFull", method = RequestMethod.POST)
    ServiceResult<Boolean> saveActFull(ActFull actFull);

    /**
     * 更新满减活动
     * @param actFull
     * @return
     */
    @RequestMapping(value = "updateActFull", method = RequestMethod.POST)
    ServiceResult<Boolean> updateActFull(ActFull actFull);

    /**
     * 更新满减活动状态（只修改活动状态、审核意见、修改者信息）
     * @param actFull
     * @return
     */
    @RequestMapping(value = "updateActFullStatus", method = RequestMethod.POST)
    ServiceResult<Boolean> updateActFullStatus(ActFull actFull);

    /**
     * 删除满减活动
     * 
     * @param actFullId
     * @param userId 删除人ID
     * @param userName 删除人名称
     * @return
     */
    @RequestMapping(value = "deleteActFull", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteActFull(@RequestParam("actFullId") Integer actFullId,
                                         @RequestParam("userId") Integer userId,
                                         @RequestParam("userName") String userName);

    /**
     * 根据条件取得满减活动
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getActFulls", method = RequestMethod.POST)
    ServiceResult<List<ActFull>> getActFulls(FeignUtil feignUtil);

    /**
     * 根据商家ID和渠道取得满减活动（当前时间有效的活动，如果有多个，只取最新的一个）
     * 
     * @param sellerId
     * @param channel 渠道
     * @return
     */
    @RequestMapping(value = "getEffectiveActFull", method = RequestMethod.GET)
    ServiceResult<ActFull> getEffectiveActFull(@RequestParam("sellerId") Integer sellerId,
                                               @RequestParam("channel") Integer channel);

}