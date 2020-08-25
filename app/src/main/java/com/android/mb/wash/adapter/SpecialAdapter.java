package com.android.mb.wash.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.Special;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.VideoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class SpecialAdapter extends BaseAdapter{
    private Context mContext;
    public List<Special> mData = new ArrayList<Special>();
    public SpecialAdapter(Context context, List<Special> htList) {
        mContext = context;
        mData = htList;
    }

    public int getCount() {
        return mData==null?0:mData.size();
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public Special getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cate, null);
            viewHolder = new ViewHolder();
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Special special = mData.get(position);
        viewHolder.tvTitle.setText(special.getName());
        ImageUtils.loadImageUrlDark(viewHolder.ivCover,special.getIcon());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = special.getName();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("specialId",special.getId());
                NavigationHelper.startActivity((Activity) mContext, VideoListActivity.class,bundle,false);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
    }

}
