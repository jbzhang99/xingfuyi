/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.yixiekeji.web.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yixiekeji.core.ConstantsEJS;

/**
 * @author Administrator
 *
 */
public class PageModel implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -162018020244253403L;
    /**
     * 日志
     */
    protected static Logger   log              = LoggerFactory.getLogger(Filter.class);

    /**
     * Constructor
     */
    public PageModel() {
        super();
    }

    /**
     * 当前页
     */
    private int    curPage         = 1;
    /**
     * 每页的记录数
     */
    private int    pageSize        = ConstantsEJS.DEFAULT_PAGE_SIZE;;
    /**
     * 所有的记录数
     */
    private int    rowsCount;
    /**
     * 一共多少页
     */
    private int    pageCount;
    /**
     * 开始行
     */
    private int    rowS;
    /**
     * 结束行
     */
    private int    rowE;
    /**
     * url
     */
    private String url;
    /**
     * 查询对象
     */
    private Object model;
    /**
     * 加载页面目标
     */
    private String targetDiv       = "";

    /**
     * 默认显示几页
     */
    private int    defaultShowPage = 6;

    /**
     * @param request HttpServletRequest
     * @param rowsCount int
     * @param url string
     * @param model object
     */
    public void init(HttpServletRequest request, int rowsCount, String url, Object model) {
        if (url != null && !"".equals(url)) {
            this.url = url;
        } else {
            this.url = request.getRequestURI() + "";
        }
        this.targetDiv = request.getParameter("targetDiv");
        this.model = model;
        this.curPage = parseInt(request.getParameter("page"), 1);
        this.rowsCount = rowsCount;
        this.pageSize = parseInt(request.getParameter("pageSize"), this.pageSize);

        this.pageCount = rowsCount / pageSize + (rowsCount % pageSize == 0 ? 0 : 1);
        this.rowS = (curPage - 1) * pageSize;
        this.rowE = pageSize;

    }

    /**
     * @param request HttpServletRequest
     * @param rowsCount int
     * @param url string
     * @param model object
     */
    public void init1(HttpServletRequest request, int rowsCount, String url, Object model) {
        if (url != null && !"".equals(url)) {
            this.url = url;
        } else {
            this.url = request.getRequestURL() + "";
        }
        this.targetDiv = request.getParameter("targetDiv");
        this.model = model;
        this.curPage = parseInt(request.getParameter("curPage"), 1);
        this.rowsCount = rowsCount;
        this.pageSize = parseInt(request.getParameter("pageSize"), 5);

        this.pageCount = rowsCount / pageSize + (rowsCount % pageSize == 0 ? 0 : 1);
        this.rowS = (curPage - 1) * pageSize;
        this.rowE = pageSize;

    }

    /**
     * @return 导航条
     */
    @SuppressWarnings("unchecked")
    public String getPageNavigation() {
        // 最终返回的分页导航条
        //String pageNavigation = "共有" + this.rowsCount + "条数据\n";
        StringBuffer pageNavigation = new StringBuffer("");
        int curPageNos = this.rowsCount % pageSize == 0 ? this.rowsCount / pageSize
            : this.rowsCount / pageSize + 1;
        /*
         * 为了保证一个页面可以有多个分页，对gotoPage函数的名称动态生成
         */
        String jsFunName = "gotoPage$" + targetDiv;
        // 记录数超过一页,需要分页
        //if (this.rowsCount > pageSize) {
        if (url != null && !"".equals(url)) {

            //            if (url.indexOf("?") > -1) {
            //                // 如果url中已经包含了其他的参数,就把curPageNo参数接在后面
            //                url += "&";
            //            } else {
            //                // 如果url中没有别的参数
            //                url += "?";
            //            }
            // 生成一个提交页面的函数

            pageNavigation.append("<script>\n");
            pageNavigation.append("function " + jsFunName + "(page_num,flag){");
            pageNavigation.append("if(page_num < 1){return;}");
            pageNavigation.append("if(flag=='go' && page_num > " + curPageNos + "){return;}");
            pageNavigation.append("$('#" + targetDiv + "curPage').attr('value',page_num);\n");
            pageNavigation.append("var params=").append("$('#").append(targetDiv).append(
                " input').map(function(){  return $(this).attr('name') + '='+ $(this).val();  }).get().join('&');\n");
            pageNavigation.append("$.ajax({");
            pageNavigation.append("type:'POST',");
            //				pageNavigation .append("url:pageForm.attr('action'),");
            pageNavigation.append("url:'" + url + "',");
            pageNavigation.append("data:encodeURI(params+'&targetDiv=" + targetDiv + "'),");
            pageNavigation.append("dataType:'html',");
            pageNavigation.append("success:function(data){");
            pageNavigation.append("$('#" + targetDiv + "').html(data);");
            pageNavigation.append("}");
            pageNavigation.append("});");
            pageNavigation.append("}");
            pageNavigation.append("\n</script>\n");
        }

        pageNavigation.append("<div class='ui-page'>");

        // 如果不是第一页,导航条将包含"首页"和"上一页"的连接
        if (curPage > 1) {
            pageNavigation.append("<a href=\"javascript:void(0);\" onclick=\"" + jsFunName)
                .append("(1);return false;\">首页</a>&nbsp;")
                .append("<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "(")
                .append((curPage - 1) + ");return false;\">上一页</a>&nbsp;&nbsp;");
        } else {
            pageNavigation.append(
                "<a href=\"javascript:void(0);\">首页</a>&nbsp;<a href=\"javascript:void(0);\">上一页</a>&nbsp;&nbsp;");
        }

        int beginPage = curPage - 3 < 1 ? 1 : curPage - 3;
        if (beginPage <= pageCount) {
            int i = beginPage;
            for (int j = 0; (i <= pageCount) && (j < 6); j++) {
                if (curPage != i) {
                    pageNavigation.append("<a href=\"javascript:void(0);\" onclick='javascript:")
                        .append(jsFunName + "(" + i + ",\"go\")'>" + i + "</a>&nbsp;");
                } else {
                    pageNavigation.append(
                        "<a href='javascript:void(0)' class='ui-page-curr'>" + i + "</a>&nbsp;");
                }
                i++;
            }
        }

        // 如果不是最后一页,导航条将包含"末页"和"下一页"
        if (curPage < curPageNos) {
            pageNavigation.append("<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "(")
                .append((curPage + 1) + ");return false;\">下一页</a>&nbsp;")
                .append("<a href=\"javascript:void(0);\" onclick=\"" + jsFunName + "(")
                .append(curPageNos + ");return false;\">末页</a>");
        } else {
            pageNavigation.append(
                "<a href=\"javascript:void(0);\">下一页</a>&nbsp;<a href=\"javascript:void(0);\">末页</a>&nbsp;");
        }
        //}

        pageNavigation.append("</div>\n");
        pageNavigation.append("\n");
        pageNavigation.append("<input id='" + targetDiv
                              + "curPage' name='page' type='hidden' value='" + curPage + "'/>");
        pageNavigation.append("\n");
        if (model != null) {
            if (model instanceof Map) {
                Map map = (Map) model;
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Object value = map.get(key);
                    pageNavigation.append("<input id='" + key + "' name='" + key)
                        .append("' type='hidden' value='" + (null == value ? "" : value))
                        .append("'/>\n");
                }
            } else {
                Field[] fields = model.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType().equals(int.class) || field.getType().equals(Integer.class)
                        || field.getType().equals(long.class) || field.getType().equals(Long.class)
                        || field.getType().equals(double.class)
                        || field.getType().equals(String.class)
                        || field.getType().equals(boolean.class)) {
                        try {
                            pageNavigation.append("<input id='" + field.getName() + "' name='")
                                .append(field.getName() + "' type='hidden' value='")
                                .append((null == field.get(model) ? "" : field.get(model)))
                                .append("'/>\n");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (field.getType().equals(Date.class)
                        || field.getType().equals(Timestamp.class)) {
                        try {
                            pageNavigation.append("<input id='").append(field.getName())
                                .append("' name='").append(field.getName())
                                .append("' type='hidden' value='")
                                .append((null == field.get(model) ? ""
                                    : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                        .format((Date) field.get(model))) + "'/>\n");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        log.debug("分页模型信息：" + pageNavigation);
        return pageNavigation.toString();
    }

    /**
     * @return string
     */
    public String getTargetDiv() {
        return targetDiv;
    }

    /**
     * @param targetDiv string
     */
    public void setTargetDiv(String targetDiv) {
        this.targetDiv = targetDiv;
    }

    /**
     * @return object
     */
    public Object getModel() {
        return model;
    }

    /**
     * @param model model
     */
    public void setModel(Object model) {
        this.model = model;
    }

    /**
     * @param s string
     * @param defaultValue int
     * @return int
     */
    private int parseInt(Object s, int defaultValue) {
        if (s != null && s.toString().matches("\\d+")) {
            return Integer.parseInt(s.toString());
        } else {
            return defaultValue;
        }
    }

    /**
     * @return start row
     */
    public int getRowS() {
        return this.rowS;
    }

    /**
     * @return end row
     */
    public int getRowE() {
        return this.rowE;
    }

    /**
     * @return current page num
     */
    public int getCurPage() {
        return curPage;
    }

    /**
     * @param curPage current page num
     */
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    /**
     * @return int
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return int
     */
    public int getRowsCount() {
        return rowsCount;
    }

    /**
     * @param rowsCount row count
     */
    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    /**
     * @return int
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount page count
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url ulr
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param rowS row start
     */
    public void setRowS(int rowS) {
        this.rowS = rowS;
    }

    /**
     * @param rowE row end
     */
    public void setRowE(int rowE) {
        this.rowE = rowE;
    }

    public int getDefaultShowPage() {
        return defaultShowPage;
    }

    public void setDefaultShowPage(int defaultShowPage) {
        this.defaultShowPage = defaultShowPage;
    }

}
