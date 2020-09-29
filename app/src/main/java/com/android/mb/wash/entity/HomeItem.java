package com.android.mb.wash.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class HomeItem implements MultiItemEntity {

    //动态
    public static final int POST = 0;
    //产品
    public static final int PRODUCT = 1;
    //item类型
    private int fieldType;

    private List<PostBean> postBeanList;

    private List<ProductType> productTypeList;

    public HomeItem(int fieldType) {
        //将传入的type赋值
        this.fieldType = fieldType;
    }

    @Override
    public int getItemType() {
        //返回传入的itemType
        return fieldType;
    }

    public List<PostBean> getPostBeanList() {
        if (postBeanList == null) {
            return new ArrayList<>();
        }
        return postBeanList;
    }

    public void setPostBeanList(List<PostBean> postBeanList) {
        this.postBeanList = postBeanList;
    }

    public List<ProductType> getProductTypeList() {
        if (productTypeList == null) {
            return new ArrayList<>();
        }
        return productTypeList;
    }

    public void setProductTypeList(List<ProductType> productTypeList) {
        this.productTypeList = productTypeList;
    }
}
