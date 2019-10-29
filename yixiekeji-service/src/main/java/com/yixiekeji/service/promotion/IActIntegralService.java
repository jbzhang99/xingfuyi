package com.yixiekeji.service.promotion;

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
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralType;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "actIntegral")
@Service(value = "actIntegralService")
public interface IActIntegralService {

    /**
     * 根据id取得积分商城
     * @param  actIntegralId
     * @return
     */
    @RequestMapping(value = "getActIntegralById", method = RequestMethod.GET)
    ServiceResult<ActIntegral> getActIntegralById(@RequestParam("actIntegralId") Integer actIntegralId);

    /**
     * 保存积分商城
     * @param  actIntegral
     * @return
     */
    @RequestMapping(value = "saveActIntegral", method = RequestMethod.POST)
    ServiceResult<Integer> saveActIntegral(ActIntegral actIntegral);

    /**
    * 更新积分商城
    * @param  actIntegral
    * @return
    */
    @RequestMapping(value = "updateActIntegral", method = RequestMethod.POST)
    ServiceResult<Integer> updateActIntegral(ActIntegral actIntegral);

    /**
     * 根据id取得积分商城分类
     * @param  actIntegralTypeId
     * @return
     */
    @RequestMapping(value = "getActIntegralTypeById", method = RequestMethod.GET)
    ServiceResult<ActIntegralType> getActIntegralTypeById(@RequestParam("actIntegralTypeId") Integer actIntegralTypeId);

    /**
     * 保存积分商城分类
     * @param  actIntegralType
     * @return
     */
    @RequestMapping(value = "saveActIntegralType", method = RequestMethod.POST)
    ServiceResult<Integer> saveActIntegralType(ActIntegralType actIntegralType);

    /**
    * 更新积分商城分类
    * @param  actIntegralType
    * @return
    */
    @RequestMapping(value = "updateActIntegralType", method = RequestMethod.POST)
    ServiceResult<Integer> updateActIntegralType(ActIntegralType actIntegralType);

    /**
     * 查询积分商城分类
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getActIntegralTypes", method = RequestMethod.POST)
    ServiceResult<List<ActIntegralType>> getActIntegralTypes(FeignUtil feignUtil);

    /**
     * 删除积分商城分类
     * @param id
     * @return
     */
    @RequestMapping(value = "delActIntegralType", method = RequestMethod.GET)
    ServiceResult<Boolean> delActIntegralType(@RequestParam("id") Integer id);

    /**
     * 启用积分商城分类
     * @param id
     * @return
     */
    @RequestMapping(value = "auditYesActIntegralType", method = RequestMethod.GET)
    ServiceResult<Boolean> auditYesActIntegralType(@RequestParam("id") Integer id);

    /**
     * 停用积分商城分类
     * @param id
     * @return
     */
    @RequestMapping(value = "auditNoActIntegralType", method = RequestMethod.GET)
    ServiceResult<Boolean> auditNoActIntegralType(@RequestParam("id") Integer id);

    /**
     * 查询所有积分商城信息
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getActIntegrals", method = RequestMethod.POST)
    ServiceResult<List<ActIntegral>> getActIntegrals(FeignUtil feignUtil);

    /**
     * 查询所有可用的积分商城分类
     * @return
     */
    @RequestMapping(value = "getActIntegralTypesAll", method = RequestMethod.GET)
    ServiceResult<List<ActIntegralType>> getActIntegralTypesAll();

    /**
     * 更改积分商城的审核状态
     * @param id
     * @param state2
     * @return
     */
    @RequestMapping(value = "updateActIntegralState", method = RequestMethod.GET)
    ServiceResult<Boolean> updateActIntegralState(@RequestParam("id") Integer id,
                                                  @RequestParam("state") int state);

    /**
     * 更改积分商城的活动状态
     * @param id
     * @param activityState
     * @return
     */
    @RequestMapping(value = "updateActIntegralActivityState", method = RequestMethod.GET)
    ServiceResult<Boolean> updateActIntegralActivityState(@RequestParam("id") Integer id,
                                                          @RequestParam("activityState") int activityState);

    /**
     * 平台管理员审核，审核通过之后更改状态，并填写审核意见
     * @param id
     * @param state
     * @param auditOpinion
     * @return
     */
    @RequestMapping(value = "updateActIntegralStateAndAuditOpinion", method = RequestMethod.GET)
    ServiceResult<Boolean> updateActIntegralStateAndAuditOpinion(@RequestParam("id") Integer id,
                                                                 @RequestParam("state") int state,
                                                                 @RequestParam("auditOpinion") String auditOpinion);

    /**
     * 积分商城前台查看
     * @param page 分页
     * @param type 积分商城类型
     * @param channel 渠道
     * @param grade 等级
     * @param sort 排序 0：默认；1、最新；2、销量；3、价格从低到高；4、价格从高到低
     * @return
     */
    @RequestMapping(value = "getActIntegralsFront", method = RequestMethod.POST)
    ServiceResult<List<ActIntegral>> getActIntegralsFront(@RequestBody PagerInfo page,
                                                          @RequestParam("type") int type,
                                                          @RequestParam("channel") int channel,
                                                          @RequestParam("grade") int grade,
                                                          @RequestParam("sort") int sort);

    /**
     * 查询所有可用的积分商城分类
     * @return
     */
    @RequestMapping(value = "getActIntegralTypesFront", method = RequestMethod.GET)
    ServiceResult<List<ActIntegralType>> getActIntegralTypesFront();

    /**
     * 根据类型查询topNum条积分商城
     * @param type
     * @param topNum
     * @return
     */
    @RequestMapping(value = "getActIntegralsByType", method = RequestMethod.GET)
    ServiceResult<List<ActIntegral>> getActIntegralsByType(@RequestParam("type") Integer type,
                                                           @RequestParam("topNum") Integer topNum);
}