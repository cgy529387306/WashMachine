package com.android.mb.wash.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IPostListPresenter {
    void getPostList(Map<String, Object> requestMap);

    void deletePost(Map<String, Object> requestMap);
}
