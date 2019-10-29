package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcIndexFloorPatch;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcIndexFloorPatch")
@Service(value = "pcIndexFloorPatchService")
public interface IPcIndexFloorPatchService {

    /**
     * 根据id取得PC端首页楼层碎屑
     * @param  pcIndexFloorPatchId
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorPatchById", method = RequestMethod.GET)
    ServiceResult<PcIndexFloorPatch> getPcIndexFloorPatchById(@RequestParam("pcIndexFloorPatchId") Integer pcIndexFloorPatchId);

    /**
     * 保存PC端首页楼层碎屑
     * @param  pcIndexFloorPatch
     * @return
     */
    @RequestMapping(value = "savePcIndexFloorPatch", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcIndexFloorPatch(PcIndexFloorPatch pcIndexFloorPatch);

    /**
     * 更新PC端首页楼层碎屑
     * @param pcIndexFloorPatch
     * @return
     */
    @RequestMapping(value = "updatePcIndexFloorPatch", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcIndexFloorPatch(PcIndexFloorPatch pcIndexFloorPatch);

    /**
     * 删除PC端首页楼层碎屑
     * @param  pcIndexFloorPatch
     * @return
     */
    @RequestMapping(value = "deletePcIndexFloorPatch", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcIndexFloorPatch(@RequestParam("pcIndexFloorPatchId") Integer pcIndexFloorPatchId);

    /**
     * 根据条件取得PC端首页楼层碎屑
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorPatchs", method = RequestMethod.POST)
    ServiceResult<List<PcIndexFloorPatch>> getPcIndexFloorPatchs(FeignUtil feignUtil);

    /**
     * 根据楼层ID取得PC端首页楼层碎屑<br>
     * <li>如果isPreview=true取使用和预使用状态的楼层碎屑
     * <li>如果isPreview=false取使用状态的楼层碎屑
     * 
     * @param floorId
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcIndexFloorPatchForView", method = RequestMethod.GET)
    ServiceResult<List<PcIndexFloorPatch>> getPcIndexFloorPatchForView(@RequestParam("floorId") Integer floorId,
                                                                       @RequestParam("isPreview") Boolean isPreview);

}