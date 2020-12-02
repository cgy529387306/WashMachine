package com.android.mb.wash.adapter;

import android.support.annotation.Nullable;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.AreaBean;
import com.android.mb.wash.entity.DescBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class DescAdapter extends BaseQuickAdapter<DescBean, BaseViewHolder> {

    public DescAdapter(int layoutResId, @Nullable List<DescBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DescBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_value,item.getValue());
    }
}
