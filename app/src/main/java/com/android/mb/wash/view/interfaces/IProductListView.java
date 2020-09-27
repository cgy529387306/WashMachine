package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.Category;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.ProductListData;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IProductListView extends BaseMvpView {
    void getSuccess(List<Category> result);
    void getProductSuccess(ProductListData result);
}
