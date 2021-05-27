package com.android.mb.wash.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.mb.wash.R;
import com.android.mb.wash.utils.ImageUtils;

public class MyImageLayout extends FrameLayout {

    private ImageView mIvImage, mIvPlay;

    public MyImageLayout(@NonNull Context context) {
        this(context, null);
    }

    public MyImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = inflate(getContext(), R.layout.item_my_image, this);
        mIvImage = view.findViewById(R.id.iv_image);
        mIvPlay = view.findViewById(R.id.iv_play);
    }

    public void loadImage(String url){
        ImageUtils.loadImageUrl(mIvImage,url);
    }

    public void showPlay(boolean isShow){
        mIvPlay.setVisibility(isShow?VISIBLE:GONE);
    }


}
