package com.android.mb.wash.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDetail implements Serializable {
    private ProductBean product;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }
}
