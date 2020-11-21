package com.android.mb.wash.entity;

import java.io.Serializable;

public class AreaBean implements Serializable {

    /**
     * createTime : 1601431639000
     * id : 00000002
     * name : 浙江省
     * updateTime : 1601431639000
     */

    private long createTime;
    private String id;
    private String name;
    private long updateTime;

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
}
