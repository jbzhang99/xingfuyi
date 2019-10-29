package com.yixiekeji.entity.settlement;

import java.io.Serializable;
import java.util.Date;

/**
 * 结算设置表
 */
public class SettlementSetting implements Serializable {

    /** 结算运行状态：1、已运行  */
    public static final String      STATUS_1           = "1";
    /** 结算运行状态：0、未运行  */
    public static final String      STATUS_0           = "0";


    /** 结算删除状态：1、已删除  */
    public static final String      DEL_STATUS_1           = "1";
    /** 结算删除状态：0、未删除  */
    public static final String      DEL_STATUS_0           = "0";


    private Integer id;
    private Date runTime;
    private Date startDate;
    private Date endDate;
    private String ip;
    private Integer operator;
    private Date createdatetime;
    private Integer editor;
    private Date editordatetime;
    private String status;
    private String delStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    public Integer getEditor() {
        return editor;
    }

    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    public Date getEditordatetime() {
        return editordatetime;
    }

    public void setEditordatetime(Date editordatetime) {
        this.editordatetime = editordatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus;
    }
}
