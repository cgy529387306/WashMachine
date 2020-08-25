package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.VideoListData;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IFindView extends BaseMvpView {
    void getSuccess(VideoListData result);

    void praise(Object result);

    void watch(Object result);
}
