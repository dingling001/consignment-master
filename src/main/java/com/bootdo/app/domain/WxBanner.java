package com.bootdo.app.domain;

/**
 * Created by ziteng016 on 2019/1/10.
 */
public class WxBanner {
    private Integer banId;

    private String banName;

    private  Integer  fileId;

    private String banImg;

    public Integer getBanId() {
        return banId;
    }

    public void setBanId(Integer banId) {
        this.banId = banId;
    }

    public String getBanName() {
        return banName;
    }

    public void setBanName(String banName) {
        this.banName = banName;
    }

    public String getBanImg() {
        return banImg;
    }

    public void setBanImg(String banImg) {
        this.banImg = banImg;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "WxBanner{" +
                "banId=" + banId +
                ", banName='" + banName + '\'' +
                ", fileId=" + fileId +
                ", banImg='" + banImg + '\'' +
                '}';
    }
}
