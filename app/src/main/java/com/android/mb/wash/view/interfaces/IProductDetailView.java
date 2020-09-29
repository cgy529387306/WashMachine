package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.CommentListData;
import com.android.mb.wash.entity.VideoData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IProductDetailView extends BaseMvpView {
    void getDetail(VideoData result);
}
