package com.android.mb.wash.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductBean {

    /**
     * cateId : 00000002
     * content :
     * coverUrl : http://211.149.168.217:58080/ljbathroom/res/file/image/1601180863119.png?nocache=1601190760033
     * createTime : 1601180863000
     * creator : 00000001
     * id : 00000001
     * imageUrls : ["http://211.149.168.217:58080/ljbathroom/res/file/image/1601180863100.png?nocache=1601190760033","http://211.149.168.217:58080/ljbathroom/res/file/image/1601180863106.png?nocache=1601190760033"]
     * name : 123
     * price : 5
     * updateTime : 1601180863000
     * videoUrl : http://211.149.168.217:58080/ljbathroom//upload/8A2844C296219B833EA449C25928B83D.mp4?nocache=1601190760033
     */

    private String cateId;
    private String content;
    private String coverUrl;
    private long createTime;
    private String creator;
    private String id;
    private String name;
    private double price;
    private long updateTime;
    private String videoUrl;
    private List<String> imageUrls;

    public String getCateId() {
        return cateId == null ? "" : cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverUrl() {
        return coverUrl == null ? "" : coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator == null ? "" : creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getVideoUrl() {
        return videoUrl == null ? "" : videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getImageUrls() {
        if (imageUrls == null) {
            return new ArrayList<>();
        }
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
