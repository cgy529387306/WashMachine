package com.android.mb.wash.adapter;

import android.support.annotation.Nullable;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.AreaBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class AreaAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {

    public AreaAdapter(int layoutResId, @Nullable List<AreaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaBean item) {
        helper.setText(R.id.tv_area,item.getName());
    }
}
