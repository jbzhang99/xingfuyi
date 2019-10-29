package com.yixiekeji.service.system;

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
import com.yixiekeji.entity.operate.SystemNotice;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "systemNotice")
@Service(value = "systemNoticeService")
public interface ISystemNoticeService {

    /**
     * 根据id取得商城公告
     * @param  systemNoticeId
     * @return
     */
    @RequestMapping(value = "getSystemNoticeById", method = RequestMethod.GET)
    ServiceResult<SystemNotice> getSystemNoticeById(@RequestParam("systemNoticeId") Integer systemNoticeId);

    /**
     * 保存商城公告
     * @param  systemNotice
     * @return
     */
    @RequestMapping(value = "saveSystemNotice", method = RequestMethod.POST)
    ServiceResult<Integer> saveSystemNotice(SystemNotice systemNotice);

    /**
    * 更新商城公告
    * @param  systemNotice
    * @return
    */
    @RequestMapping(value = "updateSystemNotice", method = RequestMethod.POST)
    ServiceResult<Integer> updateSystemNotice(SystemNotice systemNotice);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SystemNotice>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    /**
     * 公告详情
     * @param id
     * @param integer 
     * @return
     */
    @RequestMapping(value = "toDetail", method = RequestMethod.GET)
    ServiceResult<SystemNotice> toDetail(@RequestParam("id") Integer id,
                                         @RequestParam("sellerid") Integer sellerid);

    /**
     * 未读公告
     * @param sellerId
     * @param pager 
     * @return
     */
    @RequestMapping(value = "getUnreadNotice", method = RequestMethod.POST)
    ServiceResult<List<SystemNotice>> getUnreadNotice(@RequestParam("sellerId") Integer sellerId,
                                                      @RequestBody PagerInfo pager);
}