package com.yixiekeji.service.pcseller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.pcseller.PcSellerIndex;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "pcSellerIndex")
@Service(value = "pcSellerIndexService")
public interface IPcSellerIndexService {

    /**
     * 根据id取得PC端商家首页信息
     * @param  pcSellerIndexId
     * @return
     */
    @RequestMapping(value = "getPcSellerIndexById", method = RequestMethod.GET)
    ServiceResult<PcSellerIndex> getPcSellerIndexById(@RequestParam("pcSellerIndexId") Integer pcSellerIndexId);

    /**
     * 根据商家id取得PC端商家首页信息
     * @param  sellerId
     * @return
     */
    @RequestMapping(value = "getPcSellerIndexBySellerId", method = RequestMethod.GET)
    ServiceResult<PcSellerIndex> getPcSellerIndexBySellerId(@RequestParam("sellerId") Integer sellerId);

    /**
     * 保存PC端商家首页信息
     * @param  pcSellerIndex
     * @return
     */
    @RequestMapping(value = "savePcSellerIndex", method = RequestMethod.POST)
    ServiceResult<Boolean> savePcSellerIndex(PcSellerIndex pcSellerIndex);

    /**
     * 更新PC端商家首页信息
     * @param pcSellerIndex
     * @return
     */
    @RequestMapping(value = "updatePcSellerIndex", method = RequestMethod.POST)
    ServiceResult<Boolean> updatePcSellerIndex(PcSellerIndex pcSellerIndex);

    /**
     * 删除PC端商家首页信息
     * @param  pcSellerIndex
     * @return
     */
    @RequestMapping(value = "deletePcSellerIndex", method = RequestMethod.GET)
    ServiceResult<Boolean> deletePcSellerIndex(@RequestParam("pcSellerIndexId") Integer pcSellerIndexId);

    /**
     * 根据条件取得PC端商家首页信息
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getPcSellerIndexs", method = RequestMethod.POST)
    ServiceResult<List<PcSellerIndex>> getPcSellerIndexs(FeignUtil feignUtil);

}