package com.yixiekeji.service.mseller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.mseller.MSellerIndexBanner;
import com.yixiekeji.entity.mseller.MSellerIndexFloor;
import com.yixiekeji.entity.mseller.MSellerIndexFloorData;

/**
 * 商家首页service
 *                       
 * @Filename: IMSellerIndexService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "mSellerIndex")
@Service(value = "mSellerIndexService")
public interface IMSellerIndexService {

    /**
     * 根据id取得移动端首页轮播图
     * @param  mSellerIndexBannerId
     * @return
     */
    @RequestMapping(value = "getMSellerIndexBannerById", method = RequestMethod.GET)
    ServiceResult<MSellerIndexBanner> getMSellerIndexBannerById(@RequestParam("mSellerIndexBannerId") Integer mSellerIndexBannerId);

    /**
     * 保存移动端首页轮播图
     * @param  mSellerIndexBanner
     * @return
     */
    @RequestMapping(value = "saveMSellerIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMSellerIndexBanner(MSellerIndexBanner mSellerIndexBanner);

    /**
     * 更新移动端首页轮播图
     * @param mSellerIndexBanner
     * @return
     */
    @RequestMapping(value = "updateMSellerIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMSellerIndexBanner(MSellerIndexBanner mSellerIndexBanner);

    /**
     * 删除移动端首页轮播图
     * @param  mSellerIndexBanner
     * @return
     */
    @RequestMapping(value = "deleteMSellerIndexBanner", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMSellerIndexBanner(@RequestParam("mSellerIndexBannerId") Integer mSellerIndexBannerId);

    /**
     * 根据条件取得移动端首页轮播图
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getMSellerIndexBanners", method = RequestMethod.POST)
    ServiceResult<List<MSellerIndexBanner>> getMSellerIndexBanners(FeignUtil feignUtil);

    /**
     * 取得移动端首页轮播图<br>
     * <li>如果isPreview=true取所有轮播图
     * <li>如果isPreview=false取使用状态且当前时间在展示时间范围内的轮播图
     * 
     * @param sellerId
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getMSellerIndexBannerForView", method = RequestMethod.GET)
    ServiceResult<List<MSellerIndexBanner>> getMSellerIndexBannerForView(@RequestParam("sellerId") Integer sellerId,
                                                                         @RequestParam("isPreview") Boolean isPreview);

    /**
     * 根据id取得移动端首页楼层表
     * @param  mSellerIndexFloorId
     * @return
     */
    @RequestMapping(value = "getMSellerIndexFloorById", method = RequestMethod.GET)
    ServiceResult<MSellerIndexFloor> getMSellerIndexFloorById(@RequestParam("mSellerIndexFloorId") Integer mSellerIndexFloorId);

    /**
     * 保存移动端首页楼层表
     * @param  mSellerIndexFloor
     * @return
     */
    @RequestMapping(value = "saveMSellerIndexFloor", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMSellerIndexFloor(MSellerIndexFloor mSellerIndexFloor);

    /**
     * 更新移动端首页楼层表
     * @param mSellerIndexFloor
     * @return
     */
    @RequestMapping(value = "updateMSellerIndexFloor", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMSellerIndexFloor(MSellerIndexFloor mSellerIndexFloor);

    /**
     * 删除移动端首页楼层表
     * @param mSellerIndexFloor
     * @return
     */
    @RequestMapping(value = "deleteMSellerIndexFloor", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMSellerIndexFloor(@RequestParam("mSellerIndexFloorId") Integer mSellerIndexFloorId);

    /**
     * 根据条件取得移动端首页楼层表
     * @param  mSellerIndexFloorId
     * @return
     */
    @RequestMapping(value = "getMSellerIndexFloors", method = RequestMethod.POST)
    ServiceResult<List<MSellerIndexFloor>> getMSellerIndexFloors(FeignUtil feignUtil);

    /**
     * 取得移动端首页楼层表<br>
     * <li>如果isPreview=true取所有楼层
     * <li>如果isPreview=false只取显示状态的楼层
     * <li>封装楼层对应的数据（MSellerIndexFloorData）
     * 
     * @param sellerId
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getMSellerIndexFloorsWithData", method = RequestMethod.GET)
    ServiceResult<List<MSellerIndexFloor>> getMSellerIndexFloorsWithData(@RequestParam("sellerId") Integer sellerId,
                                                                         @RequestParam("isPreview") Boolean isPreview);

    /**
     * 根据id取得首页楼层数据表
     * @param  mSellerIndexFloorDataId
     * @return
     */
    @RequestMapping(value = "getMSellerIndexFloorDataById", method = RequestMethod.GET)
    ServiceResult<MSellerIndexFloorData> getMSellerIndexFloorDataById(@RequestParam("mSellerIndexFloorDataId") Integer mSellerIndexFloorDataId);

    /**
     * 保存首页楼层数据表
     * @param  mSellerIndexFloorData
     * @return
     */
    @RequestMapping(value = "saveMSellerIndexFloorData", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMSellerIndexFloorData(MSellerIndexFloorData mSellerIndexFloorData);

    /**
     * 更新首页楼层数据表
     * @param mSellerIndexFloorData
     * @return
     */
    @RequestMapping(value = "updateMSellerIndexFloorData", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMSellerIndexFloorData(MSellerIndexFloorData mSellerIndexFloorData);

    /**
     * 删除首页楼层数据表
     * @param  mSellerIndexFloorData
     * @return
     */
    @RequestMapping(value = "deleteMSellerIndexFloorData", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMSellerIndexFloorData(@RequestParam("mSellerIndexFloorDataId") Integer mSellerIndexFloorDataId);

    /**
     * 根据楼层ID取得首页楼层数据表
     * @param mSellerIndexFloorId
     * @return
     */
    @RequestMapping(value = "getMSellerIndexFloorDatasByFloorId", method = RequestMethod.GET)
    ServiceResult<List<MSellerIndexFloorData>> getMSellerIndexFloorDatasByFloorId(@RequestParam("mSellerIndexFloorId") Integer mSellerIndexFloorId);

    /**
     * 根据条件取得首页楼层数据表
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "collectionSeller", method = RequestMethod.POST)
    ServiceResult<List<MSellerIndexFloorData>> getMSellerIndexFloorDatas(FeignUtil feignUtil);

}