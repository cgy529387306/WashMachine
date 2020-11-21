package com.android.mb.wash.entity;

import com.android.mb.wash.utils.Helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频
 *
 */
public class PostBean implements Serializable{

    /**
     * commentCount : 0
     * content : 惺惺惜惺惺想寻寻
     * createTime : 1600878209000
     * dynamicId : 00000006
     * nickName : 888888888
     * praiseCount : 0
     * praised : false
     * userAvatar : http://211.149.168.217:58080/ljbathroom/res/file/avatar/2020/09/20/00000001/org.png?nocache=1600878955487
     * userId : 00000001
     * videoUrl:http://xxx
     * imageUrls=http://211.149.168.217:58080/ljbathroom/res/file/file/2020/09/21/00000002/1601329808255.png?nocache=1602211348724,http://211.149.168.217:58080/ljbathroom/res/file/file/2020/09/21/00000002/1601329808256.png?nocache=1602211348724,http://211.149.168.217:58080/ljbathroom/res/file/file/2020/09/21/00000002/1601329808257.png?nocache=1602211348724,http://211.149.168.217:58080/ljbathroom/res/file/file/2020/09/21/00000002/1601329808258.png?nocache=1602211348724,http://211.149.168.217:58080/ljbathroom/res/file/file/2020/09/21/00000002/1601329808259.png?nocache=1602211348724,http://211.149.168.217:58080/ljbathroom/res/file/file/2020/09/21/00000002/1601329808260.png?nocache=1602211348724
     */

    private int commentCount;
    private String content;
    private long createTime;
    private String dynamicId;
    private String nickName;
    private int praiseCount;
    private boolean praised;
    private String userAvatar;
    private String userId;
    private String imageUrls;
    private String videoUrl;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDynamicId() {
        return dynamicId == null ? "" : dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
    }

    public String getUserAvatar() {
        return userAvatar == null ? "" : userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrls() {
        return imageUrls == null ? "" : imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl == null ? "" : videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String[] getImageList() {
        String[] imageList = {};
        if (Helper.isNotEmpty(imageUrls)){
            imageList = imageUrls.split(",");
        }
        return imageList;
    }
}
