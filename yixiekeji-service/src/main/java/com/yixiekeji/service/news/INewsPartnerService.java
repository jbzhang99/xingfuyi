package com.yixiekeji.service.news;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.news.NewsPartner;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "newsPartner")
@Service(value = "newsPartnerService")
public interface INewsPartnerService {

    /**
     * 根据id取得合作伙伴
     * @param  newsPartnerId
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    ServiceResult<NewsPartner> getById(@RequestParam("newsPartnerId") Integer newsPartnerId);

    /**
     * 保存合作伙伴
     * @param  newsPartner
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ServiceResult<Integer> save(NewsPartner newsPartner);

    /**
    * 更新合作伙伴
    * @param  newsPartner
    * @return
    */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    ServiceResult<Integer> update(NewsPartner newsPartner);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<NewsPartner>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}