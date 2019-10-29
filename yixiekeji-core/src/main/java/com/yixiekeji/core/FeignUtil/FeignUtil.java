package com.yixiekeji.core.FeignUtil;

import java.io.Serializable;
import java.util.Map;

import com.yixiekeji.core.PagerInfo;

public class FeignUtil implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID = -4615150437537727586L;

    public static final String FEIGN_NAME       = "YIXIEKEJI";

    private static FeignUtil   feignUtil        = new FeignUtil();

    private FeignUtil() {
    }

    public static FeignUtil getFeignUtil() {
        if (feignUtil == null) {
            feignUtil = new FeignUtil();
        }
        return feignUtil;
    }

    public static FeignUtil getFeignUtil(Map<String, String> queryMap, PagerInfo pager) {
        if (feignUtil == null) {
            feignUtil = new FeignUtil();
        }
        feignUtil.setQueryMap(queryMap);
        feignUtil.setPager(pager);
        return feignUtil;
    }

    public static FeignUtil getFeignUtilObject(Map<String, Object> queryMap, PagerInfo pager) {
        if (feignUtil == null) {
            feignUtil = new FeignUtil();
        }
        feignUtil.setQueryMapObject(queryMap);
        feignUtil.setPager(pager);
        return feignUtil;
    }

    private Map<String, String> queryMap;
    private PagerInfo           pager;

    private Map<String, Object> queryMapObject;

    public Map<String, String> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    public PagerInfo getPager() {
        return pager;
    }

    public void setPager(PagerInfo pager) {
        this.pager = pager;
    }

    public Map<String, Object> getQueryMapObject() {
        return queryMapObject;
    }

    public void setQueryMapObject(Map<String, Object> queryMapObject) {
        this.queryMapObject = queryMapObject;
    }

}
