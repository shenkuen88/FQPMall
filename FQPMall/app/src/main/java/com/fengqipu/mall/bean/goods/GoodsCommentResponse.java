package com.fengqipu.mall.bean.goods;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class GoodsCommentResponse extends BaseResponse{


    /**
     * appraiseList : [{"id":"1","userID":"1","userNickName":null,"userPortrait":null,"contentID":"1","shopID":"1","orderID":"","score":4,"anonymous":0,"text":"东西不错","picUrl1":"","picUrl2":"","picUrl3":"","picUrl4":"","createTime":"2017-07-03","createTimeStr":"20170703092714","createTimeShow":"20170703092714","picUrlList":[],"picUrl1RequestUrl":"","picUrl2RequestUrl":"","picUrl3RequestUrl":"","picUrl4RequestUrl":""},{"id":"2","userID":"2","userNickName":null,"userPortrait":null,"contentID":"1","shopID":"1","orderID":"","score":5,"anonymous":1,"text":"好评一个","picUrl1":"/file/banner/3ff92cd9-b063-41ca-971e-2e9064f564a3.JPG","picUrl2":"/file/banner/3ff92cd9-b063-41ca-971e-2e9064f564a3.JPG","picUrl3":"","picUrl4":"","createTime":"2017-07-03","createTimeStr":"20170703092747","createTimeShow":"20170703092747","picUrlList":["http://116.62.116.15/f/file/banner/3ff92cd9-b063-41ca-971e-2e9064f564a3.JPG","http://116.62.116.15/f/file/banner/3ff92cd9-b063-41ca-971e-2e9064f564a3.JPG"],"picUrl1RequestUrl":"http://116.62.116.15/f/file/banner/3ff92cd9-b063-41ca-971e-2e9064f564a3.JPG","picUrl2RequestUrl":"http://116.62.116.15/f/file/banner/3ff92cd9-b063-41ca-971e-2e9064f564a3.JPG","picUrl3RequestUrl":"","picUrl4RequestUrl":""}]
     * totalCount : 2
     */

    private int totalCount;
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
        /**
         * id : 1
         * userID : 1
         * userNickName : null
         * userPortrait : null
         * contentID : 1
         * shopID : 1
         * orderID :
         * score : 4.0
         * anonymous : 0
         * text : 东西不错
         * picUrl1 :
         * picUrl2 :
         * picUrl3 :
         * picUrl4 :
         * createTime : 2017-07-03
         * createTimeStr : 20170703092714
         * createTimeShow : 20170703092714
         * picUrlList : []
         * picUrl1RequestUrl :
         * picUrl2RequestUrl :
         * picUrl3RequestUrl :
         * picUrl4RequestUrl :
         */

        private String id;
        private String userID;
        private String userNickName;
        private String userPortrait;
        private String contentID;
        private String shopID;
        private String orderID;
        private double score;
        private int anonymous;
        private String text;
        private String picUrl1;
        private String picUrl2;
        private String picUrl3;
        private String picUrl4;
        private String createTime;
        private String createTimeStr;
        private String createTimeShow;
        private String picUrl1RequestUrl;
        private String picUrl2RequestUrl;
        private String picUrl3RequestUrl;
        private String picUrl4RequestUrl;
        private List<String> picUrlList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(int anonymous) {
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

        public String getPicUrl1RequestUrl() {
            return picUrl1RequestUrl;
        }

        public void setPicUrl1RequestUrl(String picUrl1RequestUrl) {
            this.picUrl1RequestUrl = picUrl1RequestUrl;
        }

        public String getPicUrl2RequestUrl() {
            return picUrl2RequestUrl;
        }

        public void setPicUrl2RequestUrl(String picUrl2RequestUrl) {
            this.picUrl2RequestUrl = picUrl2RequestUrl;
        }

        public String getPicUrl3RequestUrl() {
            return picUrl3RequestUrl;
        }

        public void setPicUrl3RequestUrl(String picUrl3RequestUrl) {
            this.picUrl3RequestUrl = picUrl3RequestUrl;
        }

        public String getPicUrl4RequestUrl() {
            return picUrl4RequestUrl;
        }

        public void setPicUrl4RequestUrl(String picUrl4RequestUrl) {
            this.picUrl4RequestUrl = picUrl4RequestUrl;
        }

        public List<String> getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(List<String> picUrlList) {
            this.picUrlList = picUrlList;
        }
    }
}
