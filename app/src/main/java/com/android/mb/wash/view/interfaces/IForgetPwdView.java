package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.CodeBean;
import com.android.mb.wash.entity.UserBean;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IForgetPwdView extends BaseMvpView {
    void changeSuccess(Object result);

    void getSuccess(Object result);
}
