package com.yixiekeji.service.pcseller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcseller.PcSellerRecommend;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcSellerRecommend")
@Service(value = "pcSellerRecommendService")
public interface IPcSellerRecommendService {

    /**
     * 根据id取得PC端商家推荐类型
     * @param  pcSellerRecommendId
     * @return
     */
    @RequestMapping(value = "getPcSellerRecommendById", method = RequestMethod.GET)
    ServiceResult<PcSellerRecommend> getPcSellerRecommendById(@RequestParam("pcSellerRecommendId") Integer pcSellerRecommendId);

    /**
     * 保存PC端商家推荐类型
     * @param  pcSellerRecommend
     * @return
     */
    @RequestMapping(value = "savePcSellerRecommend", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcSellerRecommend(PcSellerRecommend pcSellerRecommend);

    /**
     * 更新PC端商家推荐类型
     * @param pcSellerRecommend
     * @return
     */
    @RequestMapping(value = "updatePcSellerRecommend", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcSellerRecommend(PcSellerRecommend pcSellerRecommend);

    /**
     * 删除PC端商家推荐类型
     * @param  pcSellerRecommend
     * @return
     */
    @RequestMapping(value = "deletePcSellerRecommend", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcSellerRecommend(@RequestParam("pcSellerRecommendId") Integer pcSellerRecommendId);

    /**
     * 根据条件取得PC端商家推荐类型
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcSellerRecommends", method = RequestMethod.POST)
    ServiceResult<List<PcSellerRecommend>> getPcSellerRecommends(FeignUtil feignUtil);

    /**
     * 取得PC端商家推荐类型<br>
     * <li>如果isPreview=true取使用和预使用状态的推荐类型
     * <li>如果isPreview=false取使用状态的推荐类型
     * 
     * @param sellerId
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcSellerRecommendForView", method = RequestMethod.GET)
    ServiceResult<List<PcSellerRecommend>> getPcSellerRecommendForView(@RequestParam("sellerId") Integer sellerId,
                                                                       @RequestParam("isPreview") Boolean isPreview);

}