package com.yixiekeji.service.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.vo.system.RegionsVO;

/**
 * 区域
 *                       
 * @Filename: IRegionsService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "regions")
@Service(value = "regionsService")
public interface IRegionsService {

    /**
     * 根据id取得regions对象
     * @param  regionsId
     * @return
     */
    @RequestMapping(value = "getRegionsById", method = RequestMethod.GET)
    ServiceResult<Regions> getRegionsById(@RequestParam("regionsId") Integer regionsId);

    @RequestMapping(value = "getProvince", method = RequestMethod.GET)
    List<Regions> getProvince();

    /**
     * 以上级地区获取其下所有子地区
     * @param parent
     * @param type
     * @return
     */
    @RequestMapping(value = "getChildrenArea", method = RequestMethod.GET)
    List<Regions> getChildrenArea(@RequestParam("parent") Integer parent,
                                  @RequestParam("type") String type);

    /**
     * 所有地区
     * @return
     */
    @RequestMapping(value = "getAllArea", method = RequestMethod.GET)
    List<RegionsVO> getAllArea();

    /**
     * 根据父id取得regions对象
     * @param parentId
     * @return
     */
    @RequestMapping(value = "getRegionsByParentId", method = RequestMethod.GET)
    ServiceResult<List<Regions>> getRegionsByParentId(@RequestParam("parentId") Integer parentId);
}