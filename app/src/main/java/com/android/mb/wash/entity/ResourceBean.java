package com.android.mb.wash.entity;

import java.io.Serializable;

public class ResourceBean implements Serializable {

    /**
     * createTime : 1601378236000
     * id : 00000001
     * name : 11
     * resUrl : http://xxx
     * type : 1
     * updateTime : 1601378245000
     */

    private long createTime;
    private String id;
    private String name;
    private String resUrl;
    private int type;
    private long updateTime;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
