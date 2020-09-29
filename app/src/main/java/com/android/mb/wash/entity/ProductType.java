package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductType implements Serializable {

    private String typeName;
    private String typeId;
    private List<ProductBean> products;

    public String getTypeName() {
        return typeName == null ? "" : typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId == null ? "" : typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<ProductBean> getProducts() {
        if (products == null) {
            return new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }
}
