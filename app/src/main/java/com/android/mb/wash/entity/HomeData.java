package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeData implements Serializable{

    private List<Advert> advertList;

    private List<PostBean> dynamicList;

    private List<ProductType> productList;

    public List<Advert> getAdvertList() {
        if (advertList == null) {
            return new ArrayList<>();
        }
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

    public List<PostBean> getDynamicList() {
        if (dynamicList == null) {
            return new ArrayList<>();
        }
        return dynamicList;
    }

    public void setDynamicList(List<PostBean> dynamicList) {
        this.dynamicList = dynamicList;
    }

    public List<ProductType> getProductList() {
        if (productList == null) {
            return new ArrayList<>();
        }
        return productList;
    }

    public void setProductList(List<ProductType> productList) {
        this.productList = productList;
    }
}
