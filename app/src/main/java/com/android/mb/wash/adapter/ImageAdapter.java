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
import com.android.mb.wash.entity.PostBean;
import com.android.mb.wash.utils.Helper;
import com.android.mb.wash.utils.ImageUtils;
import com.android.mb.wash.utils.NavigationHelper;
import com.android.mb.wash.view.PlayVideoActivity;

import java.util.Arrays;

import cc.shinichi.library.ImagePreview;


/**
 * Created by necer on 2017/6/7.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mDataList;
    private PostBean mPostBean;
    public ImageAdapter(Context context, PostBean postBean) {
        mContext = context;
        mPostBean = postBean;
        mDataList = postBean.getImageList();
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
            viewHolder.ivPlay = convertView.findViewById(R.id.iv_play);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String imageUrl = getItem(position);
        boolean isVideo = Helper.isNotEmpty(mPostBean.getVideoUrl());
        ImageUtils.loadImageUrl(viewHolder.ivCover,imageUrl);
        viewHolder.ivPlay.setVisibility(isVideo?View.VISIBLE:View.GONE);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVideo) {
                    Bundle bundle = new Bundle();
                    bundle.putString("videoUrl",mPostBean.getVideoUrl());
                    NavigationHelper.startActivity((Activity) mContext, PlayVideoActivity.class,bundle,false);
                } else {
                    ImagePreview.getInstance()
                            .setContext(mContext)
                            .setIndex(position)
                            .setShowDownButton(false)
                            .setImageList(Arrays.asList(mDataList))
                            .start();
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivCover;
        private ImageView ivPlay;
    }
}




