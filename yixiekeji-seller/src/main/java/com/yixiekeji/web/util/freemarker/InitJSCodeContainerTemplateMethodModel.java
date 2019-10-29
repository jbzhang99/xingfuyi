package com.yixiekeji.web.util.freemarker;

import java.util.List;

import com.yixiekeji.web.config.CodeManager;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class InitJSCodeContainerTemplateMethodModel implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return CodeManager.getCodesJsonByDivs(arguments.toArray());
    }

}
