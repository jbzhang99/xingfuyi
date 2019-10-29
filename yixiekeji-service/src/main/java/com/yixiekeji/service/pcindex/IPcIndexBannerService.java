package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcIndexBanner;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcIndexBanner")
@Service(value = "pcIndexBannerService")
public interface IPcIndexBannerService {

    /**
     * 根据id取得PC端首页轮播图
     * @param  pcIndexBannerId
     * @return
     */
    @RequestMapping(value = "getPcIndexBannerById", method = RequestMethod.GET)
    ServiceResult<PcIndexBanner> getPcIndexBannerById(@RequestParam("pcIndexBannerId") Integer pcIndexBannerId);

    /**
     * 保存PC端首页轮播图
     * @param  pcIndexBanner
     * @return
     */
    @RequestMapping(value = "savePcIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcIndexBanner(PcIndexBanner pcIndexBanner);

    /**
     * 更新PC端首页轮播图
     * @param pcIndexBanner
     * @return
     */
    @RequestMapping(value = "updatePcIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcIndexBanner(PcIndexBanner pcIndexBanner);

    /**
     * 删除PC端首页轮播图
     * @param  pcIndexBanner
     * @return
     */
    @RequestMapping(value = "deletePcIndexBanner", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcIndexBanner(@RequestParam("pcIndexBannerId") Integer pcIndexBannerId);

    /**
     * 根据条件取得PC端首页轮播图
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcIndexBanners", method = RequestMethod.POST)
    ServiceResult<List<PcIndexBanner>> getPcIndexBanners(FeignUtil feignUtil);

    /**
     * 取得PC端首页轮播图（当前时间在展示时间范围内的轮播图）<br>
     * <li>如果isPreview=true取使用和预使用状态的轮播图
     * <li>如果isPreview=false取使用状态的轮播图
     * 
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcIndexBannerForView", method = RequestMethod.GET)
    ServiceResult<List<PcIndexBanner>> getPcIndexBannerForView(@RequestParam("isPreview") Boolean isPreview);

}