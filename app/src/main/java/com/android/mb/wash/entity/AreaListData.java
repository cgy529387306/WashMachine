package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AreaListData extends BaseListData implements Serializable {

    private List<AreaBean> list;

    public List<AreaBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<AreaBean> list) {
        this.list = list;
    }
}
