package com.android.mb.wash.presenter.interfaces;

import java.util.Map;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IPostDetailPresenter {

    void getPostDetail(Map<String, Object> requestMap);

    void comment(Map<String, Object> requestMap);

    void praise(Map<String, Object> requestMap);

    void getPostComments(Map<String, Object> requestMap);

}
