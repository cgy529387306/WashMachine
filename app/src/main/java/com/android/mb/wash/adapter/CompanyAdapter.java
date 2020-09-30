package com.android.mb.wash.adapter;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.ResourceBean;
import com.android.mb.wash.utils.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class CompanyAdapter extends BaseQuickAdapter<ResourceBean, BaseViewHolder> {

    public CompanyAdapter(List<ResourceBean> data) {
        super(R.layout.item_company, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResourceBean resourceBean) {
        ImageUtils.loadImageUrl(helper.getView(R.id.iv_company),resourceBean.getResUrl());
        helper.setText(R.id.tv_name,resourceBean.getName());
    }



}


