package com.fengqipu.mall.bean.category;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2016/7/26 0026.
 */
public class CategoryRightResponse extends BaseResponse{


    /**
     * contentList : [{"id":"1","contentCode":"1717001001","contentName":"智能榨汁机","keyword":"关键词","category1":"1","category2":"3","contentType":null,"model":"T100","abstracts":"摘要信息","picUrl1":"","picUrl2":"","picUrl3":"","picUrl4":"","description":"  <p>的方式的范德萨范德萨发多少发是到付<\/p>","shopID":"3","originalPrice":0,"price":1000,"score":null,"viewCount":null,"appraiseCount":null,"favoriteCount":null,"adminID":"1","status":1,"delFlag":0,"createTime":"2017-06-15","descriptionLink":"http://116.62.116.15/api/h5/detail?type=4&id=1","shopName":"商铺1"}]
     * totalCount : 1
     */

    private int totalCount;
    private List<ContentListBean> contentList;

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

    public static class ContentListBean {
        /**
         * id : 1
         * contentCode : 1717001001
         * contentName : 智能榨汁机
         * keyword : 关键词
         * category1 : 1
         * category2 : 3
         * contentType : null
         * model : T100
         * abstracts : 摘要信息
         * picUrl1 :
         * picUrl2 :
         * picUrl3 :
         * picUrl4 :
         * description :   <p>的方式的范德萨范德萨发多少发是到付</p>
         * shopID : 3
         * originalPrice : 0.0
         * price : 1000.0
         * score : null
         * viewCount : null
         * appraiseCount : null
         * favoriteCount : null
         * adminID : 1
         * status : 1
         * delFlag : 0
         * createTime : 2017-06-15
         * descriptionLink : http://116.62.116.15/api/h5/detail?type=4&id=1
         * shopName : 商铺1
         */

        private String id;
        private String contentCode;
        private String contentName;
        private String keyword;
        private String category1;
        private String category2;
        private Object contentType;
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
        private Object score;
        private Object viewCount;
        private Object appraiseCount;
        private Object favoriteCount;
        private String adminID;
        private int status;
        private int delFlag;
        private String createTime;
        private String descriptionLink;
        private String shopName;

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

        public Object getContentType() {
            return contentType;
        }

        public void setContentType(Object contentType) {
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

        public Object getScore() {
            return score;
        }

        public void setScore(Object score) {
            this.score = score;
        }

        public Object getViewCount() {
            return viewCount;
        }

        public void setViewCount(Object viewCount) {
            this.viewCount = viewCount;
        }

        public Object getAppraiseCount() {
            return appraiseCount;
        }

        public void setAppraiseCount(Object appraiseCount) {
            this.appraiseCount = appraiseCount;
        }

        public Object getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(Object favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getAdminID() {
            return adminID;
        }

        public void setAdminID(String adminID) {
            this.adminID = adminID;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
}
