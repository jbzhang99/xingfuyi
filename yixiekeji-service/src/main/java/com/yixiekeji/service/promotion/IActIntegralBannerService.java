package com.yixiekeji.service.promotion;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.integral.ActIntegralBanner;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "actIntegralBanner")
@Service(value = "actIntegralBannerService")
public interface IActIntegralBannerService {

    /**
     * 根据id取得积分商城首页轮播图
     * @param  pcIndexBannerId
     * @return
     */
    @RequestMapping(value = "getActIntegralBannerById", method = RequestMethod.GET)
    ServiceResult<ActIntegralBanner> getActIntegralBannerById(@RequestParam("pcIndexBannerId") Integer pcIndexBannerId);

    /**
     * 保存积分商城首页轮播图
     * @param  pcIndexBanner
     * @return
     */
    @RequestMapping(value = "saveActIntegralBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> saveActIntegralBanner(ActIntegralBanner pcIndexBanner);

    /**
     * 更新积分商城首页轮播图
     * @param pcIndexBanner
     * @return
     */
    @RequestMapping(value = "updateActIntegralBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> updateActIntegralBanner(ActIntegralBanner pcIndexBanner);

    /**
     * 删除积分商城首页轮播图
     * @param  pcIndexBanner
     * @return
     */
    @RequestMapping(value = "deleteActIntegralBanner", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteActIntegralBanner(@RequestParam("pcIndexBannerId") Integer pcIndexBannerId);

    /**
     * 根据条件取得积分商城首页轮播图
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getActIntegralBanners", method = RequestMethod.POST)
    ServiceResult<List<ActIntegralBanner>> getActIntegralBanners(FeignUtil feignUtil);

    /**
     * 查询积分商城首页轮播图
     * @param pcMobilePc
     * @param channel2
     * @return
     */
    @RequestMapping(value = "getActIntegralBannersPcMobile", method = RequestMethod.GET)
    ServiceResult<List<ActIntegralBanner>> getActIntegralBannersPcMobile(@RequestParam("pcMobile") int pcMobile);

}