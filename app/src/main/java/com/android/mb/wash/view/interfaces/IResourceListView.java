package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.entity.ResourceListData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IResourceListView extends BaseMvpView {
    void getSuccess(ResourceListData result);
}
