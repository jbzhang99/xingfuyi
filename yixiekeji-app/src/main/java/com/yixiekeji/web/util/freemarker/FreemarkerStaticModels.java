package com.yixiekeji.web.util.freemarker;

import java.util.HashMap;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

public class FreemarkerStaticModels extends HashMap<String, Object> {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long             serialVersionUID = 5036247670659057528L;
    private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS;
    //    private Properties                    staticModels;

    public FreemarkerStaticModels() {
    }

    public static FreemarkerStaticModels getInstance() {
        if (FREEMARKER_STATIC_MODELS == null) {
            FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();
        }
        return FREEMARKER_STATIC_MODELS;
    }

    public void setStaticModels(String key, String value) {
        FREEMARKER_STATIC_MODELS.put(key, useStaticPackage(value));
    }

    static TemplateHashModel useStaticPackage(String packageBName) {
        try {
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageBName);
            return fileStatics;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}