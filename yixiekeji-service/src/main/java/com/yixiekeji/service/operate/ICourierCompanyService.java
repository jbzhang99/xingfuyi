package com.yixiekeji.service.operate;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.operate.CourierCompany;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "courierCompany")
@Service(value = "courierCompanyService")
public interface ICourierCompanyService {

    /**
     * 根据id取得快递公司
     * @param  courierCompanyId
     * @return
     */
    @RequestMapping(value = "getCourierCompanyById", method = RequestMethod.GET)
    ServiceResult<CourierCompany> getCourierCompanyById(@RequestParam("courierCompanyId") Integer courierCompanyId);

    /**
     * 保存快递公司
     * @param  courierCompany
     * @return
     */
    @RequestMapping(value = "saveCourierCompany", method = RequestMethod.POST)
    ServiceResult<Integer> saveCourierCompany(CourierCompany courierCompany);

    /**
    * 更新快递公司
    * @param  courierCompany
    * @return
    */
    @RequestMapping(value = "updateCourierCompany", method = RequestMethod.POST)
    ServiceResult<Integer> updateCourierCompany(CourierCompany courierCompany);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<CourierCompany>> page(FeignUtil feignUtil);

    /**
     * 所有CourierCompany列表
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    List<CourierCompany> list();

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}