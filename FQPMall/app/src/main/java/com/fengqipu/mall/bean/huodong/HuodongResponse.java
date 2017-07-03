package com.fengqipu.mall.bean.huodong;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/6/27 0027.
 */

public class HuodongResponse extends BaseResponse {

    /**
     * activity : {"id":"2","posID":"2","pos":null,"activityName":"位置2活动","words":"位置2短语","cover":"","picUrl":"","startTime":"2017-06-16","startClock":"08:00","endTime":"2017-06-30","endClock":"22:00","currentTime":null,"createAdmin":null,"createTime":"2017-06-18","createTimeStr":"2017-06-18 15:27","picUrlRequestUrl":"","coverRequestUrl":"","status":2}
     * contentList : [{"id":"1","contentCode":null,"contentName":"智能榨汁机","keyword":null,"category1":null,"category2":null,"contentType":null,"model":null,"abstracts":"摘要信息","picUrl1":"","picUrl2":null,"picUrl3":null,"picUrl4":null,"description":null,"shopID":"3","originalPrice":0,"price":1000,"score":null,"viewCount":null,"appraiseCount":null,"favoriteCount":null,"adminID":null,"status":null,"delFlag":null,"createTime":null,"picUrl1RequestUrl":"","shopName":"商铺1","descriptionLink":"","picUrl2RequestUrl":"","picUrl3RequestUrl":"","picUrl4RequestUrl":""},{"id":"2","contentCode":null,"contentName":"透气休闲鞋","keyword":null,"category1":null,"category2":null,"contentType":null,"model":null,"abstracts":"透气休闲鞋","picUrl1":"/file/content/88fc3f0a-552a-49fe-82f4-7701f1e61b69.JPG","picUrl2":null,"picUrl3":null,"picUrl4":null,"description":null,"shopID":"3","originalPrice":null,"price":null,"score":null,"viewCount":null,"appraiseCount":null,"favoriteCount":null,"adminID":null,"status":null,"delFlag":null,"createTime":null,"picUrl1RequestUrl":"http://116.62.116.15/f/file/content/88fc3f0a-552a-49fe-82f4-7701f1e61b69.JPG","shopName":"商铺1","descriptionLink":"","picUrl2RequestUrl":"","picUrl3RequestUrl":"","picUrl4RequestUrl":""},{"id":"3","contentCode":null,"contentName":"老板油烟机","keyword":null,"category1":null,"category2":null,"contentType":null,"model":null,"abstracts":"老板油烟机老板油烟机","picUrl1":"","picUrl2":null,"picUrl3":null,"picUrl4":null,"description":null,"shopID":"3","originalPrice":null,"price":null,"score":null,"viewCount":null,"appraiseCount":null,"favoriteCount":null,"adminID":null,"status":null,"delFlag":null,"createTime":null,"picUrl1RequestUrl":"","shopName":"商铺1","descriptionLink":"","picUrl2RequestUrl":"","picUrl3RequestUrl":"","picUrl4RequestUrl":""}]
     * totalCount : 3
     */

    private ActivityBean activity;
    private int totalCount;
    private List<ContentListBean> contentList;

    public ActivityBean getActivity() {
        return activity;
    }

    public void setActivity(ActivityBean activity) {
        this.activity = activity;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    public static class ActivityBean {
        /**
         * id : 2
         * posID : 2
         * pos : null
         * activityName : 位置2活动
         * words : 位置2短语
         * cover : 
         * picUrl : 
         * startTime : 2017-06-16
         * startClock : 08:00
         * endTime : 2017-06-30
         * endClock : 22:00
         * currentTime : null
         * createAdmin : null
         * createTime : 2017-06-18
         * createTimeStr : 2017-06-18 15:27
         * picUrlRequestUrl : 
         * coverRequestUrl : 
         * status : 2
         */

        private String id;
        private String posID;
        private String pos;
        private String activityName;
        private String words;
        private String cover;
        private String picUrl;
        private String startTime;
        private String startClock;
        private String endTime;
        private String endClock;
        private String currentTime;
        private String createAdmin;
        private String createTime;
        private String createTimeStr;
        private String picUrlRequestUrl;
        private String coverRequestUrl;
        private int status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosID() {
            return posID;
        }

        public void setPosID(String posID) {
            this.posID = posID;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStartClock() {
            return startClock;
        }

        public void setStartClock(String startClock) {
            this.startClock = startClock;
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

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }

        public String getCreateAdmin() {
            return createAdmin;
        }

        public void setCreateAdmin(String createAdmin) {
            this.createAdmin = createAdmin;
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

        public String getPicUrlRequestUrl() {
            return picUrlRequestUrl;
        }

        public void setPicUrlRequestUrl(String picUrlRequestUrl) {
            this.picUrlRequestUrl = picUrlRequestUrl;
        }

        public String getCoverRequestUrl() {
            return coverRequestUrl;
        }

        public void setCoverRequestUrl(String coverRequestUrl) {
            this.coverRequestUrl = coverRequestUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class ContentListBean {
        /**
         * id : 1
         * contentCode : null
         * contentName : 智能榨汁机
         * keyword : null
         * category1 : null
         * category2 : null
         * contentType : null
         * model : null
         * abstracts : 摘要信息
         * picUrl1 : 
         * picUrl2 : null
         * picUrl3 : null
         * picUrl4 : null
         * description : null
         * shopID : 3
         * originalPrice : 0.0
         * price : 1000.0
         * score : null
         * viewCount : null
         * appraiseCount : null
         * favoriteCount : null
         * adminID : null
         * status : null
         * delFlag : null
         * createTime : null
         * picUrl1RequestUrl : 
         * shopName : 商铺1
         * descriptionLink : 
         * picUrl2RequestUrl : 
         * picUrl3RequestUrl : 
         * picUrl4RequestUrl : 
         */

        private String id;
        private String contentCode;
        private String contentName;
        private String keyword;
        private String category1;
        private String category2;
        private String contentType;
        private String model;
        private String abstracts;
        private String picUrl1;
        private String picUrl2;
        private String picUrl3;
        private String picUrl4;
        private String description;
        private String shopID;
        private double originalPrice;
        private double price;
        private String score;
        private String viewCount;
        private String appraiseCount;
        private String favoriteCount;
        private String adminID;
        private String status;
        private String delFlag;
        private String createTime;
        private String picUrl1RequestUrl;
        private String shopName;
        private String descriptionLink;
        private String picUrl2RequestUrl;
        private String picUrl3RequestUrl;
        private String picUrl4RequestUrl;
        private String sales;

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContentCode() {
            return contentCode;
        }

        public void setContentCode(String contentCode) {
            this.contentCode = contentCode;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getCategory1() {
            return category1;
        }

        public void setCategory1(String category1) {
            this.category1 = category1;
        }

        public String getCategory2() {
            return category2;
        }

        public void setCategory2(String category2) {
            this.category2 = category2;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getAbstracts() {
            return abstracts;
        }

        public void setAbstracts(String abstracts) {
            this.abstracts = abstracts;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getAppraiseCount() {
            return appraiseCount;
        }

        public void setAppraiseCount(String appraiseCount) {
            this.appraiseCount = appraiseCount;
        }

        public String getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(String favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getAdminID() {
            return adminID;
        }

        public void setAdminID(String adminID) {
            this.adminID = adminID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPicUrl1RequestUrl() {
            return picUrl1RequestUrl;
        }

        public void setPicUrl1RequestUrl(String picUrl1RequestUrl) {
            this.picUrl1RequestUrl = picUrl1RequestUrl;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
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
    }
}
