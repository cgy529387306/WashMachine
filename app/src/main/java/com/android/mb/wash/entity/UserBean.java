package com.android.mb.wash.entity;

public class UserBean {

    /**
     * avatar_url : http://211.149.168.217:58080/ljbathroom/res/file/avatar/2020/09/20/00000001/org.png?nocache=1600787159457
     * create_time : 1600550712000
     * phone : 13521021451
     * sex : 1
     * nickname : 管理员
     * accesstoken : 8DBC48628AA770DFD41643B62B003574|eyJ1c2VySWQiOiIwMDAwMDAwMSIsImV4cGlyZWRUaW1lIjoiMTU5OTA4NDE5MjA5MSJ9
     * remark_name :
     * sexText : 男
     * remainCount : 0
     * userid : 00000001
     * username : admin
     * watchCount : 0
     */

    private String avatar_url;
    private long create_time;
    private String phone;
    private String sex;
    private String nickname;
    private String accesstoken;
    private String remark_name;
    private String sexText;
    private int remainCount;
    private String userid;
    private String username;
    private int watchCount;

    public String getAvatar_url() {
        return avatar_url == null ? "" : avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccesstoken() {
        return accesstoken == null ? "" : accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getRemark_name() {
        return remark_name == null ? "" : remark_name;
    }

    public void setRemark_name(String remark_name) {
        this.remark_name = remark_name;
    }

    public String getSexText() {
        return sexText == null ? "" : sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }
}
