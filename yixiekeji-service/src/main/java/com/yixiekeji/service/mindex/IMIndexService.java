package com.yixiekeji.service.mindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.mindex.MIndexBanner;
import com.yixiekeji.entity.mindex.MIndexFloor;
import com.yixiekeji.entity.mindex.MIndexFloorData;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "mIndex")
@Service(value = "mIndexService")
public interface IMIndexService {

    /**
     * 根据id取得移动端首页轮播图
     * @param  mIndexBannerId
     * @return
     */
    @RequestMapping(value = "getMIndexBannerById", method = RequestMethod.GET)
    ServiceResult<MIndexBanner> getMIndexBannerById(@RequestParam("mIndexBannerId") Integer mIndexBannerId);

    /**
     * 保存移动端首页轮播图
     * @param  mIndexBanner
     * @return
     */
    @RequestMapping(value = "saveMIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMIndexBanner(MIndexBanner mIndexBanner);

    /**
     * 更新移动端首页轮播图
     * @param mIndexBanner
     * @return
     */
    @RequestMapping(value = "updateMIndexBanner", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMIndexBanner(MIndexBanner mIndexBanner);

    /**
     * 删除移动端首页轮播图
     * @param  mIndexBanner
     * @return
     */
    @RequestMapping(value = "deleteMIndexBanner", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMIndexBanner(@RequestParam("mIndexBannerId") Integer mIndexBannerId);

    /**
     * 根据条件取得移动端首页轮播图
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getMIndexBanners", method = RequestMethod.POST)
    ServiceResult<List<MIndexBanner>> getMIndexBanners(FeignUtil feignUtil);

    /**
     * 取得移动端首页轮播图<br>
     * <li>如果isPreview=true取所有轮播图
     * <li>如果isPreview=false取使用状态且当前时间在展示时间范围内的轮播图
     * 
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getMIndexBannerForView", method = RequestMethod.GET)
    ServiceResult<List<MIndexBanner>> getMIndexBannerForView(@RequestParam("isPreview") Boolean isPreview);

    /**
     * 根据id取得移动端首页楼层表
     * @param  mIndexFloorId
     * @return
     */
    @RequestMapping(value = "getMIndexFloorById", method = RequestMethod.GET)
    ServiceResult<MIndexFloor> getMIndexFloorById(@RequestParam("mIndexFloorId") Integer mIndexFloorId);

    /**
     * 保存移动端首页楼层表
     * @param  mIndexFloor
     * @return
     */
    @RequestMapping(value = "saveMIndexFloor", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMIndexFloor(MIndexFloor mIndexFloor);

    /**
     * 更新移动端首页楼层表
     * @param mIndexFloor
     * @return
     */
    @RequestMapping(value = "updateMIndexFloor", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMIndexFloor(MIndexFloor mIndexFloor);

    /**
     * 删除移动端首页楼层表
     * @param mIndexFloor
     * @return
     */
    @RequestMapping(value = "deleteMIndexFloor", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMIndexFloor(@RequestParam("mIndexFloorId") Integer mIndexFloorId);

    /**
     * 根据条件取得移动端首页楼层表
     * @param  mIndexFloorId
     * @return
     */
    @RequestMapping(value = "getMIndexFloors", method = RequestMethod.POST)
    ServiceResult<List<MIndexFloor>> getMIndexFloors(FeignUtil feignUtil);

    /**
     * 取得移动端首页楼层表<br>
     * <li>如果isPreview=true取所有楼层
     * <li>如果isPreview=false只取显示状态的楼层
     * <li>封装楼层对应的数据（MIndexFloorData）
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getMIndexFloorsWithData", method = RequestMethod.GET)
    ServiceResult<List<MIndexFloor>> getMIndexFloorsWithData(@RequestParam("isPreview") Boolean isPreview);

    /**
     * 根据id取得首页楼层数据表
     * @param  mIndexFloorDataId
     * @return
     */
    @RequestMapping(value = "getMIndexFloorDataById", method = RequestMethod.GET)
    ServiceResult<MIndexFloorData> getMIndexFloorDataById(@RequestParam("mIndexFloorDataId") Integer mIndexFloorDataId);

    /**
     * 保存首页楼层数据表
     * @param  mIndexFloorData
     * @return
     */
    @RequestMapping(value = "saveMIndexFloorData", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMIndexFloorData(MIndexFloorData mIndexFloorData);

    /**
     * 更新首页楼层数据表
     * @param mIndexFloorData
     * @return
     */
    @RequestMapping(value = "updateMIndexFloorData", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMIndexFloorData(MIndexFloorData mIndexFloorData);

    /**
     * 删除首页楼层数据表
     * @param  mIndexFloorData
     * @return
     */
    @RequestMapping(value = "deleteMIndexFloorData", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMIndexFloorData(@RequestParam("mIndexFloorDataId") Integer mIndexFloorDataId);

    /**
     * 根据楼层ID取得首页楼层数据表
     * @param mIndexFloorId
     * @return
     */
    @RequestMapping(value = "getMIndexFloorDatasByFloorId", method = RequestMethod.GET)
    ServiceResult<List<MIndexFloorData>> getMIndexFloorDatasByFloorId(@RequestParam("mIndexFloorId") Integer mIndexFloorId);

    /**
     * 根据条件取得首页楼层数据表
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getMIndexFloorDatas", method = RequestMethod.POST)
    ServiceResult<List<MIndexFloorData>> getMIndexFloorDatas(FeignUtil feignUtil);

}