package com.android.mb.wash.view.interfaces;

import com.android.mb.wash.base.BaseMvpView;
import com.android.mb.wash.entity.AreaBean;

import java.util.List;

/**
 * Created by cgy on 2018/2/11 0011.
 */
public interface IAreaListView extends BaseMvpView {
    void getSuccess(List<AreaBean> result);
}
