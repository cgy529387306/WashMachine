package com.android.mb.wash.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HomeItem implements MultiItemEntity {

    //动态
    public static final int POST = 0;
    //新品
    public static final int NEW = 1;
    //热门
    public static final int HOT = 2;
    //item类型
    private int fieldType;

    public HomeItem(int fieldType) {
        //将传入的type赋值
        this.fieldType = fieldType;
    }

    @Override
    public int getItemType() {
        //返回传入的itemType
        return fieldType;
    }
}
