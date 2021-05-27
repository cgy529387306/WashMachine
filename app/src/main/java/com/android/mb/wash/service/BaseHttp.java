package com.android.mb.wash.service;


import com.android.mb.wash.app.MBApplication;
import com.android.mb.wash.constants.ProjectConstants;
import com.android.mb.wash.entity.CurrentUser;
import com.android.mb.wash.retrofit.http.entity.HttpResult;
import com.android.mb.wash.retrofit.http.exception.ApiException;
import com.android.mb.wash.rxbus.RxBus;
import com.android.mb.wash.utils.ActivityManager;
import com.android.mb.wash.utils.DeviceHelper;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.utils.ToastHelper;
import com.android.mb.wash.view.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * @Description 基础
 * @created by cgy on 2017/6/19
 * @version v1.0
 *
 */

public class BaseHttp {

    public static final String BASE_URL = "http://8.140.185.23";

    public String getServerHost() {
        return BASE_URL;
    }

    @SuppressWarnings("unchecked")
    static class HttpCacheResultFunc<T> implements Func1 {
        @Override
        public Object call(Object o) {
            HttpResult<T> httpResult;
            if (o instanceof HttpResult) {
                httpResult = (HttpResult<T>) o;
                if (httpResult.getCode() == 200){
                    return httpResult.getData();
                }  if (httpResult.getCode() == 201){
                    if (Helper.isNotEmpty(httpResult.getMessage())){
                        ToastHelper.showLongToast(httpResult.getMessage());
                    }
                    RxBus.getInstance().send(ProjectConstants.EVENT_TO_LOGIN,o);
                } else {
                    throw new ApiException(ApiException.REQUEST_FAIL, httpResult.getMessage());
                }
            }
            return null;
        }
    }

    /**
     * 获取头部信息
     *
     * @return Map
     */
    Map<String, String> getHead() {
        Map<String, String> cloudOfficeHeader = new HashMap<String, String>();
        cloudOfficeHeader.put("openId", DeviceHelper.getDeviceId(MBApplication.getInstance()));
        if (CurrentUser.getInstance().isLogin()){
            cloudOfficeHeader.put("accessToken",CurrentUser.getInstance().getAccesstoken());
        }
        return cloudOfficeHeader;
    }

}
