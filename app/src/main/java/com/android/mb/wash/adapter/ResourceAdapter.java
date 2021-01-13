package com.android.mb.wash.adapter;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.ResourceBean;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.ProjectHelper;
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
        boolean isVideo = ProjectHelper.isVideo(item.getResUrl());
        helper.setVisible(R.id.iv_play, isVideo);
        if (isVideo) {
            ImageUtils.loadVideoScreenshot(mContext,item.getResUrl(),helper.getView(R.id.iv_product), 0);
        } else {
            ImageUtils.loadImageUrl(helper.getView(R.id.iv_product),item.getResUrl());
        }
    }


}




