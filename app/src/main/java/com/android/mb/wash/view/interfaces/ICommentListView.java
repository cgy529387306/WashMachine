package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.CommentListData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ICommentListView extends BaseMvpView {
    void getSuccess(CommentListData result);

    void deleteSuccess(Object result);
}
