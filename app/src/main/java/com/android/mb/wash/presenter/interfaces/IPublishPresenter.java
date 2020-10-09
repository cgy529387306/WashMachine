package com.android.mb.wash.presenter.interfaces;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IPublishPresenter {
    void publishDynamic(List<File> fileList, Map<String,Object> requestMap);

}
