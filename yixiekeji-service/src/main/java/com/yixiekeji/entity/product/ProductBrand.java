package com.yixiekeji.entity.product;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品品牌
 * <p>Table: <strong>product_brand</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>name</td><td>{@link java.lang.String}</td><td>name</td><td>varchar</td><td>品牌名称</td></tr>
 *   <tr><td>nameFirst</td><td>{@link java.lang.String}</td><td>name_first</td><td>varchar</td><td>品牌首字母</td></tr>
 *   <tr><td>image</td><td>{@link java.lang.String}</td><td>image</td><td>varchar</td><td>品牌图片</td></tr>
 *   <tr><td>lookMethod</td><td>{@link java.lang.Integer}</td><td>look_method</td><td>tinyint</td><td>展示方式1、图片；2、文字</td></tr>
 *   <tr><td>top</td><td>{@link java.lang.Integer}</td><td>top</td><td>tinyint</td><td>推荐1、推荐；2、不推荐</td></tr>
 *   <tr><td>sort</td><td>{@link java.lang.Integer}</td><td>sort</td><td>int</td><td>排序</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>创建人</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateId</td><td>{@link java.lang.Integer}</td><td>update_id</td><td>int</td><td>更新人</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>状态 0、默认；1、提交审核；2、显示中；3、审核失败；4、删除</td></tr>
 * </table>
 *
 */
public class ProductBrand implements Serializable {
    private static final long serialVersionUID = 1541894852131265200L;
    private Integer           id;
    private String            name;                                   //品牌名称
    private String            nameFirst;                              //品牌首字母
    private String            image;                                  //图片大小比例3:1
    private Integer           lookMethod;                             //展示方式1、图片；2、文字
    private Integer           top;                                    //推荐1、推荐；2、不推荐
    private Integer           sort;                                   //排序
    private Integer           createId;                               //创建人
    private Date              createTime;                             //创建时间
    private Integer           updateId;                               //更新人
    private Date              updateTime;                             //更新时间
    private Integer           state;                                  //状态 0:默认，1:提交审核，2:审核通过（显示中），3:审核失败，4:删除
    private String            createUser;                             //创建人
    private String            updateUser;                             //更新人

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

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLookMethod() {
        return lookMethod;
    }

    public void setLookMethod(Integer lookMethod) {
        this.lookMethod = lookMethod;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public enum Status {
                        //0:默认，1:提交审核，2:审核通过（显示中），3:审核失败，4:删除
                        DEFAULT("默认", 0), COMMIT("提交审核", 1), SUCCESS("显示中", 2), ERROR("审核失败",
                                                                                      3), DEL("删除",
                                                                                              4);

        private String name;  //显示的名字
        private int    value; //实际存储的值

        //构造方法
        private Status(String name, int value) {
            this.name = name;
            this.value = value;
        }

        /**
         * 检查value是否合法
         *
         * @param value
         * @return
         */
        public static Boolean chkStatus(int value) {
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
