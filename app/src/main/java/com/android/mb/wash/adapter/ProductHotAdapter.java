package com.android.mb.wash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.android.mb.wash.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class ProductHotAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDataList = new ArrayList<String>();
    public ProductHotAdapter(Context context, List<String> list) {
        mContext = context;
        mDataList = list;
    }

    public int getCount() {
        return mDataList==null?0:mDataList.size();
    }

    @Override
    public String getItem(int position) {
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
        ImageUtils.loadImageUrl(viewHolder.ivCover,mDataList.get(position));
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivCover;
    }
}




