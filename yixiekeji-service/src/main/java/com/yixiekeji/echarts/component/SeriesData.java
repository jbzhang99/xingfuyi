package com.yixiekeji.echarts.component;

import java.io.Serializable;

/**
 * 系列数据
 *                       
 * @Filename: SeriesData.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class SeriesData extends Object implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4819458350674045298L;
    private int               value;
    private String            name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
