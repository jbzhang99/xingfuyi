package com.yixiekeji.web.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.system.Code;
import com.yixiekeji.service.system.ICodeService;

/**
 * 字典初始化类
 *                       
 * @Filename: CodeManager.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Component
public class CodeManager {

    private static Logger       log = LoggerFactory.getLogger(CodeManager.class);

    private static ICodeService codeService;

    public static ICodeService getCodeService() {
        return codeService;
    }

    @Autowired
    public void setCodeService(ICodeService codeService) {
        CodeManager.codeService = codeService;
    }

    public static Map<String, List<Code>> codeMap;

    public static Map<String, List<Code>> getCodeMap() {
        return codeMap;
    }

    public void setCodeMap(Map<String, List<Code>> codeMap) {
        CodeManager.codeMap = codeMap;
    }

    /**
     * 初始化字典组件。
     * <p>该方法用于容器启动时初始化字典组件，以及生产环境中数据字典变更时的重新初始化，<br/>
     * 正式环境由于部署多台，需要将字典数据在发版前导入数据库中。
     */
    @PostConstruct //1在构造函数执行完之后执行
    public static void init() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("q_useYn", ConstantsEJS.USE_YN_Y + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(map, null);
        ServiceResult<List<Code>> serviceResult = codeService.getCodes(feignUtil);
        if (!serviceResult.getSuccess()) {
            throw new RuntimeException(serviceResult.getMessage());
        }
        codeMap = new HashMap<String, List<Code>>();
        List<Code> divList = new ArrayList<Code>();
        for (Code code : serviceResult.getResult()) {
            divList = codeMap.get(code.getCodeDiv());
            if (divList == null) {
                divList = new ArrayList<Code>();
            }
            divList.add(code);
            codeMap.put(code.getCodeDiv(), divList);
        }
        log.info("=========CodeManager初始化完成===============" + codeMap.size());
    }

    public static List<Code> getCodes(String codeDiv) {
        return codeMap.get(codeDiv);
    }

    public static String getCodesJsonByDivs(Object[] codeDivs) {
        Map<String, Map<String, String>> divMap = new HashMap<String, Map<String, String>>();
        Map<String, String> cdMap = new HashMap<String, String>();
        List<Code> lstCodes = new ArrayList<Code>();
        for (Object codeDiv : codeDivs) {
            lstCodes = codeMap.get(codeDiv.toString());
            if (lstCodes != null) {
                cdMap = new HashMap<String, String>();
                for (Code code : lstCodes) {
                    cdMap.put(code.getCodeCd(), code.getCodeText());
                }
                divMap.put(codeDiv.toString(), cdMap);
            }
        }
        return JSON.toJSONString(divMap);
    }

    public static String getCodeText(String codeDiv, String codeCd) {
        String codeText = "";
        List<Code> codes = codeMap.get(codeDiv);
        for (Code code : codes) {
            if (code.getCodeCd().equals(codeCd)) {
                codeText = code.getCodeText();
                break;
            }
        }
        return codeText;
    }

    public static List<Code> getFilterCodes(String codeDiv, List<String> excludeCodeCDs) {
        List<Code> codes = codeMap.get(codeDiv);
        for (Code code : codes) {
            if (excludeCodeCDs.contains(code.getCodeCd())) {
                codes.remove(code);
            }
        }
        return codes;
    }

}