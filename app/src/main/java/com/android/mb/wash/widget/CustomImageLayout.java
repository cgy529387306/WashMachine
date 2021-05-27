package com.android.mb.wash.widget;

import android.content.Context;

import com.youth.banner.loader.ImageLoaderInterface;

public abstract class CustomImageLayout implements ImageLoaderInterface<MyImageLayout> {

    @Override
    public MyImageLayout createImageView(Context context) {
        return new MyImageLayout(context);
    }
}
