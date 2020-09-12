package com.android.mb.wash.adapter;

import com.android.mb.wash.R;
import com.android.mb.wash.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class CompanyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CompanyAdapter(List<String> data) {
        super(R.layout.item_company, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String string) {
        ImageUtils.loadImageUrlLight(helper.getView(R.id.iv_company),string);
    }



}


