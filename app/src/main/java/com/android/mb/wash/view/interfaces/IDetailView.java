package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.VideoData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IDetailView extends BaseMvpView {

    void getVideoDetail(VideoData result);

    void comment(Object result);

    void praise(Object result);

    void watch(Object result);

    void getVideoComments(CommentListData result);
}
