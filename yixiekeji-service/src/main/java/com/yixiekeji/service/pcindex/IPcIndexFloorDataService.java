package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcIndexFloorData;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcIndexFloorData")
@Service(value = "pcIndexFloorDataService")
public interface IPcIndexFloorDataService {

    /**
     * 根据id取得PC端首页楼层分类数据
     * @param  pcIndexFloorDataId
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorDataById", method = RequestMethod.GET)
    ServiceResult<PcIndexFloorData> getPcIndexFloorDataById(@RequestParam("pcIndexFloorDataId") Integer pcIndexFloorDataId);

    /**
     * 保存PC端首页楼层分类数据
     * @param  pcIndexFloorData
     * @return
     */
    @RequestMapping(value = "savePcIndexFloorData", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcIndexFloorData(@RequestBody PcIndexFloorData pcIndexFloorData);

    /**
     * 更新PC端首页楼层分类数据
     * @param pcIndexFloorData
     * @return
     */
    @RequestMapping(value = "updatePcIndexFloorData", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcIndexFloorData(@RequestBody PcIndexFloorData pcIndexFloorData);

    /**
     * 删除PC端首页楼层分类数据
     * @param  pcIndexFloorData
     * @return
     */
    @RequestMapping(value = "deletePcIndexFloorData", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcIndexFloorData(@RequestParam("pcIndexFloorDataId") Integer pcIndexFloorDataId);

    /**
     * 根据条件取得PC端首页楼层分类数据
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorDatas", method = RequestMethod.POST)
    ServiceResult<List<PcIndexFloorData>> getPcIndexFloorDatas(FeignUtil feignUtil);

    /**
     * 根据楼层分类ID取得PC端首页楼层分类数据<br>
     * 
     * @param floorClassId
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorDataForView", method = RequestMethod.GET)
    ServiceResult<List<PcIndexFloorData>> getPcIndexFloorDataForView(@RequestParam("floorClassId") Integer floorClassId);

}