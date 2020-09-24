package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.VideoData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IPostDetailView extends BaseMvpView {

    void getPostDetail(VideoData result);

    void comment(Object result);

    void praise(Object result);

    void getPostComments(CommentListData result);
}
