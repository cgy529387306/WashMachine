package com.android.mb.wash.presenter.interfaces;

import java.util.Map;

public interface IRegisterPresenter {
    void userRegister(Map<String, Object> requestMap);

    void getCode(Map<String, Object> requestMap);
}
