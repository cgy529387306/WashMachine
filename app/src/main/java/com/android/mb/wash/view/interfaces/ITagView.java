package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.Tag;
import com.android.mb.wash.entity.VideoListData;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ITagView extends BaseMvpView {
    void getSuccess(List<Tag> result);

    void querySuccess(VideoListData result);
}
