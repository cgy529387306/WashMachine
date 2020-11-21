package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.ProductBean;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IProductDetailView extends BaseMvpView {
    void getDetail(ProductBean result);
}
