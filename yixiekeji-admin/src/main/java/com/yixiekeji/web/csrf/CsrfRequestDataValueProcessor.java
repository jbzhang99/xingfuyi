package com.yixiekeji.web.csrf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.yixiekeji.core.StringUtil;

/**
 * 与页面中spring mvc <@from.form>配合使用，生成token隐藏域
 *                       
 * @Filename: CsrfRequestDataValueProcessor.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class CsrfRequestDataValueProcessor implements RequestDataValueProcessor {

    @Override
    public String processAction(HttpServletRequest request, String action, String httpMethod) {
        return action;
    }

    @Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value,
                                        String type) {
        return value;
    }

    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        //此处是当使用spring的taglib标签<@form:form>创建表单时候，增加的隐藏域参数
        Map<String, String> hiddenFields = new HashMap<String, String>();

        String memKey = CsrfTokenManager.getMemkeyFromRequest(request);
        if (StringUtil.isEmpty(memKey)) {
            memKey = UUID.randomUUID().toString();
        }
        hiddenFields.put(CsrfTokenManager.MEM_KEY_NAME, memKey);
        hiddenFields.put(CsrfTokenManager.CSRF_PARAM_NAME,
            CsrfTokenManager.getTokenForSession(memKey, request.getSession()));

        return hiddenFields;
    }

    @Override
    public String processUrl(HttpServletRequest request, String url) {
        return url;
    }

}
