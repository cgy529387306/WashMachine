package com.android.mb.wash.adapter;

import com.android.mb.wash.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class PostAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public PostAdapter(List data) {
        super(R.layout.item_post, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }


}




