package com.android.mb.wash.entity;

import java.io.Serializable;

public class DescBean implements Serializable {

    /**
     * createTime : 1606756341000
     * id : 00000003
     * name : 产品型号
     * updateTime : 1606756341000
     * value : 123
     */

    private long createTime;
    private String id;
    private String name;
    private long updateTime;
    private String value;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
