package com.yixiekeji.service.pcseller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcseller.PcSellerIndexBanner;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcSellerIndexBanner")
@Service(value = "pcSellerIndexBannerService")
public interface IPcSellerIndexBannerService {

    /**
     * 根据id取得PC端商家首页轮播图
     * @param  pcSellerIndexBannerId
     * @return
     */
    @RequestMapping(value = "getPcSellerIndexBannerById", method = RequestMethod.GET)
    ServiceResult<PcSellerIndexBanner> getPcSellerIndexBannerById(@RequestParam("pcSellerIndexBannerId") Integer pcSellerIndexBannerId);

    /**
     * 保存PC端商家首页轮播图
     * @param  pcSellerIndexBanner
     * @return
     */
    @RequestMapping(value = "savePcSellerIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcSellerIndexBanner(PcSellerIndexBanner pcSellerIndexBanner);

    /**
     * 更新PC端商家首页轮播图
     * @param pcSellerIndexBanner
     * @return
     */
    @RequestMapping(value = "updatePcSellerIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcSellerIndexBanner(PcSellerIndexBanner pcSellerIndexBanner);

    /**
     * 删除PC端商家首页轮播图
     * @param  pcSellerIndexBanner
     * @return
     */
    @RequestMapping(value = "deletePcSellerIndexBanner", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcSellerIndexBanner(@RequestParam("pcSellerIndexBannerId") Integer pcSellerIndexBannerId);

    /**
     * 根据条件取得PC端商家首页轮播图
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcSellerIndexBanners", method = RequestMethod.POST)
    ServiceResult<List<PcSellerIndexBanner>> getPcSellerIndexBanners(FeignUtil feignUtil);

    /**
     * 取得PC端商家首页轮播图（当前时间在展示时间范围内的轮播图）<br>
     * <li>如果isPreview=true取使用和预使用状态的轮播图
     * <li>如果isPreview=false取使用状态的轮播图
     * 
     * @param sellerId
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcSellerIndexBannerForView", method = RequestMethod.GET)
    ServiceResult<List<PcSellerIndexBanner>> getPcSellerIndexBannerForView(@RequestParam("sellerId") Integer sellerId,
                                                                           @RequestParam("isPreview") Boolean isPreview);

}