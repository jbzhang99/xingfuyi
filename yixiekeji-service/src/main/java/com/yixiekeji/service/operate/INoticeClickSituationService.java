package com.yixiekeji.service.operate;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.operate.NoticeClickSituation;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "noticeClickSituation")
@Service(value = "noticeClickSituationService")
public interface INoticeClickSituationService {

    /**
     * 根据id取得商家公告查看情况
     * @param  noticeClickSituationId
     * @return
     */
    @RequestMapping(value = "getNoticeClickSituationById", method = RequestMethod.GET)
    ServiceResult<NoticeClickSituation> getNoticeClickSituationById(@RequestParam("noticeClickSituationId") Integer noticeClickSituationId);

    /**
     * 保存商家公告查看情况
     * @param  noticeClickSituation
     * @return
     */
    @RequestMapping(value = "saveNoticeClickSituation", method = RequestMethod.POST)
    ServiceResult<Integer> saveNoticeClickSituation(NoticeClickSituation noticeClickSituation);

    /**
    * 更新商家公告查看情况
    * @param  noticeClickSituation
    * @return
    */
    @RequestMapping(value = "updateNoticeClickSituation", method = RequestMethod.POST)
    ServiceResult<Integer> updateNoticeClickSituation(NoticeClickSituation noticeClickSituation);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<NoticeClickSituation>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}