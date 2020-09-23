package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostListData extends BaseListData implements Serializable {

    private List<PostBean> list;

    public List<PostBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<PostBean> list) {
        this.list = list;
    }
}
