package com.fengqipu.mall.bean.video;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6.
 */
public class VideoPLResponse extends BaseResponse {

    /**
     * appraiseList : [{"appraiseID":"1021","userID":"9","userNickName":"15251813022","userPortrait":"http://221.226.118.110:18080/skin/images/avatars.png","contentID":"11","orderID":null,"score":null,"anonymous":null,"text":"fc","picUrl1":null,"picUrl2":null,"picUrl3":null,"picUrl4":null,"createTime":"2016-08-07","createTimeStr":"20160807110941","createTimeShow":"20160807110941"}]
     * totalCount : 1
     */

    private int totalCount;
    /**
     * appraiseID : 1021
     * userID : 9
     * userNickName : 15251813022
     * userPortrait : http://221.226.118.110:18080/skin/images/avatars.png
     * contentID : 11
     * orderID : null
     * score : null
     * anonymous : null
     * text : fc
     * picUrl1 : null
     * picUrl2 : null
     * picUrl3 : null
     * picUrl4 : null
     * createTime : 2016-08-07
     * createTimeStr : 20160807110941
     * createTimeShow : 20160807110941
     */

    private List<AppraiseListBean> appraiseList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AppraiseListBean> getAppraiseList() {
        return appraiseList;
    }

    public void setAppraiseList(List<AppraiseListBean> appraiseList) {
        this.appraiseList = appraiseList;
    }

    public static class AppraiseListBean {
        private String appraiseID;
        private String userID;
        private String userNickName;
        private String userPortrait;
        private String contentID;
        private String orderID;
        private String score;
        private String anonymous;
        private String text;
        private String picUrl1;
        private String picUrl2;
        private String picUrl3;
        private String picUrl4;
        private String createTime;
        private String createTimeStr;
        private String createTimeShow;

        public String getAppraiseID() {
            return appraiseID;
        }

        public void setAppraiseID(String appraiseID) {
            this.appraiseID = appraiseID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserPortrait() {
            return userPortrait;
        }

        public void setUserPortrait(String userPortrait) {
            this.userPortrait = userPortrait;
        }

        public String getContentID() {
            return contentID;
        }

        public void setContentID(String contentID) {
            this.contentID = contentID;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(String anonymous) {
            this.anonymous = anonymous;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPicUrl1() {
            return picUrl1;
        }

        public void setPicUrl1(String picUrl1) {
            this.picUrl1 = picUrl1;
        }

        public String getPicUrl2() {
            return picUrl2;
        }

        public void setPicUrl2(String picUrl2) {
            this.picUrl2 = picUrl2;
        }

        public String getPicUrl3() {
            return picUrl3;
        }

        public void setPicUrl3(String picUrl3) {
            this.picUrl3 = picUrl3;
        }

        public String getPicUrl4() {
            return picUrl4;
        }

        public void setPicUrl4(String picUrl4) {
            this.picUrl4 = picUrl4;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getCreateTimeShow() {
            return createTimeShow;
        }

        public void setCreateTimeShow(String createTimeShow) {
            this.createTimeShow = createTimeShow;
        }
    }
}
