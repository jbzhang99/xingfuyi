package com.yixiekeji.service.pcseller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcseller.PcSellerRecommendData;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcSellerRecommendData")
@Service(value = "pcSellerRecommendDataService")
public interface IPcSellerRecommendDataService {

    /**
     * 根据id取得PC端商家推荐数据
     * @param  pcSellerRecommendDataId
     * @return
     */
    @RequestMapping(value = "getPcSellerRecommendDataById", method = RequestMethod.GET)
    ServiceResult<PcSellerRecommendData> getPcSellerRecommendDataById(@RequestParam("pcSellerRecommendDataId") Integer pcSellerRecommendDataId);

    /**
     * 保存PC端商家推荐数据
     * @param  pcSellerRecommendData
     * @return
     */
    @RequestMapping(value = "savePcSellerRecommendData", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcSellerRecommendData(PcSellerRecommendData pcSellerRecommendData);

    /**
     * 更新PC端商家推荐数据
     * @param pcSellerRecommendData
     * @return
     */
    @RequestMapping(value = "updatePcSellerRecommendData", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcSellerRecommendData(PcSellerRecommendData pcSellerRecommendData);

    /**
     * 删除PC端商家推荐数据
     * @param  pcSellerRecommendData
     * @return
     */
    @RequestMapping(value = "deletePcSellerRecommendData", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcSellerRecommendData(@RequestParam("pcSellerRecommendDataId") Integer pcSellerRecommendDataId);

    /**
     * 根据条件取得PC端商家推荐数据
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcSellerRecommendDatas", method = RequestMethod.POST)
    ServiceResult<List<PcSellerRecommendData>> getPcSellerRecommendDatas(FeignUtil feignUtil);

    /**
     * 根据楼层分类ID取得PC端商家推荐数据<br>
     * 
     * @param recommendId
     * @return
     */
    @RequestMapping(value = "getPcSellerRecommendDataForView", method = RequestMethod.GET)
    ServiceResult<List<PcSellerRecommendData>> getPcSellerRecommendDataForView(@RequestParam("recommendId") Integer recommendId);

}