package com.yixiekeji.entity.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品规格表
 * <p>Table: <strong>product_norm</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>name</td><td>{@link java.lang.String}</td><td>name</td><td>varchar</td><td>规格名称</td></tr>
 *   <tr><td>sort</td><td>{@link java.lang.Integer}</td><td>sort</td><td>int</td><td>排序</td></tr>
 *   <tr><td>type</td><td>{@link java.lang.Integer}</td><td>type</td><td>tinyint</td><td>规格类型1、文字；2、图片</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>创建人</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateId</td><td>{@link java.lang.Integer}</td><td>update_id</td><td>int</td><td>更新人</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>状态 0：删除 1:正常</td></tr>
 * </table>
 *
 */
public class ProductNorm implements Serializable {
    private static final long     serialVersionUID = -6231999472879865717L;

    private Integer               id;
    private String                name;                                    //规格名称
    private Integer               sort;                                    //排序
    private Integer               type;                                    //规格类型1、文字；2、图片
    private Integer               createId;                                //创建人
    private Date                  createTime;                              //创建时间
    private Integer               updateId;                                //更新人
    private Date                  updateTime;                              //更新时间
    private Integer               state;                                   //状态
    private String                createUser;                              //创建人
    private String                updateUser;                              //修改人
    private List<ProductNormAttr> attrList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public List<ProductNormAttr> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<ProductNormAttr> attrList) {
        this.attrList = attrList;
    }

    public enum Status {
                        //0、删除；1、正常；2、停用
                        DEL("删除", 0), DEFAULT("正常", 1), STOP("停用", 2);

        private String name;  //显示的名字
        private int    value; //实际存储的值

        //构造方法
        private Status(String name, int value) {
            this.name = name;
            this.value = value;
        }

        /**
         * 检查value是否合法
         * @param value
         * @return
         */
        public static Boolean chkStatus(Integer value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value)
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
