package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.PostListData;
import com.android.mb.wash.entity.Tag;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IPostListView extends BaseMvpView {
    void getSuccess(PostListData result);

    void deleteSuccess(Object result);
}
