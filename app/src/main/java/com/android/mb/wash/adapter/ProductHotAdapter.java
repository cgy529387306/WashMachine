package com.android.mb.wash.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class ProductHotAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductBean> mDataList = new ArrayList<ProductBean>();
    public ProductHotAdapter(Context context, List<ProductBean> list) {
        mContext = context;
        mDataList = list;
    }

    public int getCount() {
        return mDataList==null?0:mDataList.size();
    }

    @Override
    public ProductBean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_hot, null);
            viewHolder = new ViewHolder();
            viewHolder.ivCover = convertView.findViewById(R.id.iv_product);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ProductBean productBean = mDataList.get(position);
        ImageUtils.loadImageUrl(viewHolder.ivCover,productBean.getCoverUrl());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("productBean",productBean);
                NavigationHelper.startActivity((Activity) mContext, ProductDetailActivity.class,bundle,false);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivCover;
    }
}




