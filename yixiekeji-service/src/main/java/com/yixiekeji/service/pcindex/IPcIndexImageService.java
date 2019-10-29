package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcIndexImage;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcIndexImage")
@Service(value = "pcIndexImageService")
public interface IPcIndexImageService {

    /**
     * 根据id取得PC端首页的一些图片
     * @param  pcIndexImageId
     * @return
     */
    @RequestMapping(value = "getPcIndexImageById", method = RequestMethod.GET)
    ServiceResult<PcIndexImage> getPcIndexImageById(@RequestParam("pcIndexImageId") Integer pcIndexImageId);

    /**
     * 保存PC端首页的一些图片
     * @param  pcIndexImage
     * @return
     */
    @RequestMapping(value = "savePcIndexImage", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcIndexImage(PcIndexImage pcIndexImage);

    /**
     * 更新PC端首页的一些图片
     * @param pcIndexImage
     * @return
     */
    @RequestMapping(value = "updatePcIndexImage", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcIndexImage(PcIndexImage pcIndexImage);

    /**
     * 删除PC端首页的一些图片
     * @param  pcIndexImage
     * @return
     */
    @RequestMapping(value = "deletePcIndexImage", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcIndexImage(@RequestParam("pcIndexImageId") Integer pcIndexImageId);

    /**
     * 根据条件取得PC端首页的一些图片
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcIndexImages", method = RequestMethod.POST)
    ServiceResult<List<PcIndexImage>> getPcIndexImages(FeignUtil feignUtil);

    /**
     * 取得PC端首页的一些图片（当前时间在展示时间范围内的轮播图）<br>
     * <li>如果isPreview=true取使用和预使用状态的轮播图
     * <li>如果isPreview=false取使用状态的轮播图
     * 
     * @param isPreview
     * @param type
     * @return
     */
    @RequestMapping(value = "getPcIndexImageForView", method = RequestMethod.GET)
    ServiceResult<List<PcIndexImage>> getPcIndexImageForView(@RequestParam("isPreview") Boolean isPreview,
                                                             @RequestParam("type") int type);

}