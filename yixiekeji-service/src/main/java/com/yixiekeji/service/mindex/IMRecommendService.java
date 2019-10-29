package com.yixiekeji.service.mindex;

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
import com.yixiekeji.entity.mindex.MRecommend;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "mRecommend")
@Service(value = "mRecommendService")
public interface IMRecommendService {

    /**
     * 根据id取得M端推荐商品
     * @param  pcRecommendId
     * @return
     */
    @RequestMapping(value = "getMRecommendById", method = RequestMethod.GET)
    ServiceResult<MRecommend> getMRecommendById(@RequestParam("pcRecommendId") Integer pcRecommendId);

    /**
     * 保存M端推荐商品
     * @param  pcRecommend
     * @return
     */
    @RequestMapping(value = "saveMRecommend", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMRecommend(MRecommend pcRecommend);

    /**
     * 更新M端推荐商品
     * @param pcRecommend
     * @return
     */
    @RequestMapping(value = "updateMRecommend", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMRecommend(MRecommend pcRecommend);

    /**
     * 删除M端推荐商品
     * @param  pcRecommend
     * @return
     */
    @RequestMapping(value = "deleteMRecommend", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMRecommend(@RequestParam("pcRecommendId") Integer pcRecommendId);

    /**
     * 根据条件取得M端推荐商品
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getMRecommends", method = RequestMethod.POST)
    ServiceResult<List<MRecommend>> getMRecommends(FeignUtil feignUtil);

    /**
     * 取得M端推荐商品（当前时间在展示时间范围内的推荐商品）<br>
     * <li>如果isPreview=true取所有的商品
     * <li>如果isPreview=false取使用状态的推荐商品
     * 
     * @param recommendType 推荐类型：固定为1：多惠部落
     * @param isPreview
     * @return
     */
    @RequestMapping(value = "getMRecommendForView", method = RequestMethod.POST)
    ServiceResult<List<MRecommend>> getMRecommendForView(@RequestParam("recommendType") Integer recommendType,
                                                         @RequestParam("isPreview") Boolean isPreview,
                                                         @RequestBody PagerInfo pager);

}