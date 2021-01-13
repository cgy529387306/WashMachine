package com.android.mb.wash.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ICommentListPresenter {
    void getPostComments(Map<String, Object> requestMap);

    void deleteComment(Map<String, Object> requestMap);
}
