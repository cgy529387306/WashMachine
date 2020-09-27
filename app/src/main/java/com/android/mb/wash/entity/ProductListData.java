package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductListData extends BaseListData implements Serializable {

    private List<ProductBean> list;

    public List<ProductBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ProductBean> list) {
        this.list = list;
    }
}
