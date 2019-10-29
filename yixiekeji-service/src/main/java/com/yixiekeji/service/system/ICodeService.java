package com.yixiekeji.service.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.system.Code;

/**
 * 字典服务类
 *                       
 * @Filename: CodeService.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "code")
@Service(value = "codeService")
public interface ICodeService {

    /**
     * 获取字典数据。
     * @param queryMap
     * @param pager
     * @return ServiceResult<List<Code>>
     */
    @RequestMapping(value = "getCodes", method = RequestMethod.POST)
    ServiceResult<List<Code>> getCodes(FeignUtil feignUtil);

    /**
     * 根据条件获取单个字典数据。
     * @param codeDiv
     * @param codeCd
     * @return ServiceResult<Code>
     */
    @RequestMapping(value = "getCode", method = RequestMethod.GET)
    ServiceResult<Code> getCode(@RequestParam("codeDiv") String codeDiv,
                                @RequestParam("codeCd") String codeCd);

    /**
     * 新增字典数据。
     * @param code
     * @return ServiceResult<Integer>
     */
    @RequestMapping(value = "createCode", method = RequestMethod.POST)
    ServiceResult<Integer> createCode(Code code);

    /**
     * 修改字典数据。
     * @param code
     * @return ServiceResult<Boolean>
     */
    @RequestMapping(value = "updateCode", method = RequestMethod.POST)
    ServiceResult<Boolean> updateCode(Code code);
}