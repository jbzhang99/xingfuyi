package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcIndexFloorClass;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcIndexFloorClass")
@Service(value = "pcIndexFloorClassService")
public interface IPcIndexFloorClassService {

    /**
     * 根据id取得PC端首页楼层分类
     * @param  pcIndexFloorClassId
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorClassById", method = RequestMethod.GET)
    ServiceResult<PcIndexFloorClass> getPcIndexFloorClassById(@RequestParam("pcIndexFloorClassId") Integer pcIndexFloorClassId);

    /**
     * 保存PC端首页楼层分类
     * @param  pcIndexFloorClass
     * @return
     */
    @RequestMapping(value = "savePcIndexFloorClass", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcIndexFloorClass(PcIndexFloorClass pcIndexFloorClass);

    /**
     * 更新PC端首页楼层分类
     * @param pcIndexFloorClass
     * @return
     */
    @RequestMapping(value = "updatePcIndexFloorClass", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcIndexFloorClass(PcIndexFloorClass pcIndexFloorClass);

    /**
     * 删除PC端首页楼层分类
     * @param  pcIndexFloorClass
     * @return
     */
    @RequestMapping(value = "deletePcIndexFloorClass", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcIndexFloorClass(@RequestParam("pcIndexFloorClassId") Integer pcIndexFloorClassId);

    /**
     * 根据条件取得PC端首页楼层分类
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorClasss", method = RequestMethod.POST)
    ServiceResult<List<PcIndexFloorClass>> getPcIndexFloorClasss(FeignUtil feignUtil);

    /**
     * 根据楼层ID取得PC端首页楼层分类<br>
     * <li>如果isPreview=true取使用和预使用状态的楼层分类
     * <li>如果isPreview=false取使用状态的楼层分类
     * 
     * @param floorId
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorClassForView", method = RequestMethod.GET)
    ServiceResult<List<PcIndexFloorClass>> getPcIndexFloorClassForView(@RequestParam("floorId") Integer floorId,
                                                                       @RequestParam("isPreview") Boolean isPreview);

}