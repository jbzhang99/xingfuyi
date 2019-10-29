package com.yixiekeji.service.pcindex;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcindex.PcRecommend;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcRecommend")
@Service(value = "pcRecommendService")
public interface IPcRecommendService {

    /**
     * 根据id取得PC端推荐商品
     * @param  pcRecommendId
     * @return
     */
    @RequestMapping(value = "getPcRecommendById", method = RequestMethod.GET)
    ServiceResult<PcRecommend> getPcRecommendById(@RequestParam("pcRecommendId") Integer pcRecommendId);

    /**
     * 保存PC端推荐商品
     * @param  pcRecommend
     * @return
     */
    @RequestMapping(value = "savePcRecommend", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcRecommend(PcRecommend pcRecommend);

    /**
     * 更新PC端推荐商品
     * @param pcRecommend
     * @return
     */
    @RequestMapping(value = "updatePcRecommend", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcRecommend(PcRecommend pcRecommend);

    /**
     * 删除PC端推荐商品
     * @param  pcRecommend
     * @return
     */
    @RequestMapping(value = "deletePcRecommend", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcRecommend(@RequestParam("pcRecommendId") Integer pcRecommendId);

    /**
     * 根据条件取得PC端推荐商品
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcRecommends", method = RequestMethod.POST)
    ServiceResult<List<PcRecommend>> getPcRecommends(FeignUtil feignUtil);

    /**
     * 取得PC端推荐商品（当前时间在展示时间范围内的推荐商品）<br>
     * <li>如果isPreview=true取使用和预使用状态的推荐商品
     * <li>如果isPreview=false取使用状态的推荐商品
     * 
     * @param recommendType 推荐类型：固定为1：多惠部落
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getPcRecommendForView", method = RequestMethod.POST)
    ServiceResult<List<PcRecommend>> getPcRecommendForView(@RequestParam("pcRecommendId") Integer recommendType,
                                                           @RequestParam("isPreview") Boolean isPreview,
                                                           @RequestBody PagerInfo pager);

}