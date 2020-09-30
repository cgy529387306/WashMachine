package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResourceListData extends BaseListData implements Serializable {

    private List<ResourceBean> list;

    public List<ResourceBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ResourceBean> list) {
        this.list = list;
    }
}
