package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.UserBean;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface ILoginView extends BaseMvpView {
    void loginSuccess(UserBean result);
}
