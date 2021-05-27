package com.android.mb.wash.entity;

import java.io.Serializable;

public class ImageBean implements Serializable {
    private int imageType;//0:图片，1：视频
    private String imageUrl;
    private String videoUrl;

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl == null ? "" : videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public ImageBean(int imageType, String imageUrl, String videoUrl) {
        this.imageType = imageType;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }
}
