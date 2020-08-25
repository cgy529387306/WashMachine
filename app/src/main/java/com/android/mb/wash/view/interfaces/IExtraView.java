package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.CountData;
import com.android.mb.wash.entity.VersionBean;

public interface IExtraView extends BaseMvpView{
    void getSuccess(CountData result);

    void getQQSuccess(String result);

    void getVersionSuccess(VersionBean result);
}
