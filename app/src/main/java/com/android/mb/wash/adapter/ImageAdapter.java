package com.android.mb.wash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.android.mb.wash.entity.ProductBean;
import com.android.mb.wash.utils.ImageUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.shinichi.library.ImagePreview;


/**
 * Created by necer on 2017/6/7.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mDataList;
    public ImageAdapter(Context context, String[] list) {
        mContext = context;
        mDataList = list;
    }

    public int getCount() {
        return mDataList==null?0:mDataList.length;
    }

    @Override
    public String getItem(int position) {
        return mDataList[position];
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
        String imageUrl = getItem(position);
        ImageUtils.loadImageUrl(viewHolder.ivCover,imageUrl);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ImagePreview.getInstance()
                            .setContext(mContext)
                            .setIndex(position)
                            .setImageList(Arrays.asList(mDataList))
                            .start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivCover;
    }
}




