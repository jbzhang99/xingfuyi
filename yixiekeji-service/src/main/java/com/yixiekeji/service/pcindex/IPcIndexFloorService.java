package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcIndexFloor;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcIndexFloor")
@Service(value = "pcIndexFloorService")
public interface IPcIndexFloorService {

    /**
     * 根据id取得PC端首页楼层
     * @param  pcIndexFloorId
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorById", method = RequestMethod.GET)
    ServiceResult<PcIndexFloor> getPcIndexFloorById(@RequestParam("pcIndexFloorId") Integer pcIndexFloorId);

    /**
     * 保存PC端首页楼层
     * @param  pcIndexFloor
     * @return
     */
    @RequestMapping(value = "savePcIndexFloor", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcIndexFloor(PcIndexFloor pcIndexFloor);

    /**
     * 更新PC端首页楼层
     * @param pcIndexFloor
     * @return
     */
    @RequestMapping(value = "updatePcIndexFloor", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcIndexFloor(PcIndexFloor pcIndexFloor);

    /**
     * 删除PC端首页楼层
     * @param  pcIndexFloor
     * @return
     */
    @RequestMapping(value = "deletePcIndexFloor", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcIndexFloor(@RequestParam("pcIndexFloorId") Integer pcIndexFloorId);

    /**
     * 根据条件取得PC端首页楼层
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcIndexFloors", method = RequestMethod.POST)
    ServiceResult<List<PcIndexFloor>> getPcIndexFloors(FeignUtil feignUtil);

    /**
     * 取得PC端首页楼层<br>
     * <li>如果isPreview=true取使用和预使用状态的楼层
     * <li>如果isPreview=false取使用状态的楼层
     * 
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorForView", method = RequestMethod.GET)
    ServiceResult<List<PcIndexFloor>> getPcIndexFloorForView(@RequestParam("isPreview") Boolean isPreview);

}