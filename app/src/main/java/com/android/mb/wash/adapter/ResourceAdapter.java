package com.android.mb.wash.adapter;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.entity.ResourceBean;
import com.android.mb.wash.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class ResourceAdapter extends BaseQuickAdapter<ResourceBean, BaseViewHolder> {


    public ResourceAdapter(List data) {
        super(R.layout.item_product_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResourceBean item) {
        ImageUtils.loadImageUrl(helper.getView(R.id.iv_product),item.getResUrl());
    }


}




