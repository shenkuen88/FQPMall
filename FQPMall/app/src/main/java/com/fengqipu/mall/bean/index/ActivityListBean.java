package com.fengqipu.mall.bean.index;

import java.io.Serializable;

/**
 * Created by qing on 2016/7/15.
 */
public class ActivityListBean implements Serializable {


    /**
     * createTime : 2017-06-18
     * picUrl : /file/activity/f4668292-4305-4c98-a074-72274448e3b7.jpg
     * picUrlRequestUrl : http://116.62.116.15/f/file/activity/f4668292-4305-4c98-a074-72274448e3b7.jpg
     * endTime : 2017-06-30
     * endClock : 22:20
     * pos : 2
     * coverRequestUrl : http://116.62.116.15/f/file/activity/7cbad236-4f73-4150-833a-8359f835256f.jpg
     * currentTime : null
     * startTime : 2017-06-16
     * id : 2
     * cover : /file/activity/7cbad236-4f73-4150-833a-8359f835256f.jpg
     * words : 短语2
     * createAdmin : 1
     * startClock : 02:00
     * activityName : 活动2
     * activityID : 2
     */

    private String createTime;
    private String picUrl;
    private String picUrlRequestUrl;
    private String endTime;
    private String endClock;
    private int pos;
    private String coverRequestUrl;
    private String currentTime;
    private String startTime;
    private String id;
    private String cover;
    private String words;
    private String createAdmin;
    private String startClock;
    private String activityName;
    private String activityID;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrlRequestUrl() {
        return picUrlRequestUrl;
    }

    public void setPicUrlRequestUrl(String picUrlRequestUrl) {
        this.picUrlRequestUrl = picUrlRequestUrl;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndClock() {
        return endClock;
    }

    public void setEndClock(String endClock) {
        this.endClock = endClock;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getCoverRequestUrl() {
        return coverRequestUrl;
    }

    public void setCoverRequestUrl(String coverRequestUrl) {
        this.coverRequestUrl = coverRequestUrl;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(String createAdmin) {
        this.createAdmin = createAdmin;
    }

    public String getStartClock() {
        return startClock;
    }

    public void setStartClock(String startClock) {
        this.startClock = startClock;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }
}
