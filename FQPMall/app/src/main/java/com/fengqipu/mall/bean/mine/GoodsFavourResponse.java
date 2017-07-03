package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2016/7/23 0023.
 */
public class GoodsFavourResponse extends BaseResponse{


    /**
     * favoriteList : [{"id":"2","userID":"3","objectType":2,"objectID":"3","objectName":"商铺1","picUrl":"","price":null,"favoriteCount":null,"sales":null,"shopProvince":null,"shopCity":null,"advPicUrl1":null,"advPicUrl2":null,"advPicUrl3":null,"createTime":"2017-06-29","createTimeStr":"20170629145907","picUrlRequestUrl":"","createTimeShow":"20170629145907","advPicUrl1RequestUrl":"","advPicUrl2RequestUrl":"","advPicUrl3RequestUrl":"","advPicUrlList":[]}]
     * totalCount : 1
     */

    private int totalCount;
    private List<FavoriteListBean> favoriteList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<FavoriteListBean> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<FavoriteListBean> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public static class FavoriteListBean {
        /**
         * id : 2
         * userID : 3
         * objectType : 2
         * objectID : 3
         * objectName : 商铺1
         * picUrl :
         * price : null
         * favoriteCount : null
         * sales : null
         * shopProvince : null
         * shopCity : null
         * advPicUrl1 : null
         * advPicUrl2 : null
         * advPicUrl3 : null
         * createTime : 2017-06-29
         * createTimeStr : 20170629145907
         * picUrlRequestUrl :
         * createTimeShow : 20170629145907
         * advPicUrl1RequestUrl :
         * advPicUrl2RequestUrl :
         * advPicUrl3RequestUrl :
         * advPicUrlList : []
         */

        private String id;
        private String userID;
        private int objectType;
        private String objectID;
        private String objectName;
        private String picUrl;
        private String price;
        private String favoriteCount;
        private String sales;
        private String shopProvince;
        private String shopCity;
        private String advPicUrl1;
        private String advPicUrl2;
        private String advPicUrl3;
        private String createTime;
        private String createTimeStr;
        private String picUrlRequestUrl;
        private String createTimeShow;
        private String advPicUrl1RequestUrl;
        private String advPicUrl2RequestUrl;
        private String advPicUrl3RequestUrl;
        private List<String> advPicUrlList;

        public int getObjectType() {
            return objectType;
        }

        public void setObjectType(int objectType) {
            this.objectType = objectType;
        }

        public String getObjectID() {
            return objectID;
        }

        public void setObjectID(String objectID) {
            this.objectID = objectID;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

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

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(String favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getShopProvince() {
            return shopProvince;
        }

        public void setShopProvince(String shopProvince) {
            this.shopProvince = shopProvince;
        }

        public String getShopCity() {
            return shopCity;
        }

        public void setShopCity(String shopCity) {
            this.shopCity = shopCity;
        }

        public String getAdvPicUrl1() {
            return advPicUrl1;
        }

        public void setAdvPicUrl1(String advPicUrl1) {
            this.advPicUrl1 = advPicUrl1;
        }

        public String getAdvPicUrl2() {
            return advPicUrl2;
        }

        public void setAdvPicUrl2(String advPicUrl2) {
            this.advPicUrl2 = advPicUrl2;
        }

        public String getAdvPicUrl3() {
            return advPicUrl3;
        }

        public void setAdvPicUrl3(String advPicUrl3) {
            this.advPicUrl3 = advPicUrl3;
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

        public String getCreateTimeShow() {
            return createTimeShow;
        }

        public void setCreateTimeShow(String createTimeShow) {
            this.createTimeShow = createTimeShow;
        }

        public String getAdvPicUrl1RequestUrl() {
            return advPicUrl1RequestUrl;
        }

        public void setAdvPicUrl1RequestUrl(String advPicUrl1RequestUrl) {
            this.advPicUrl1RequestUrl = advPicUrl1RequestUrl;
        }

        public String getAdvPicUrl2RequestUrl() {
            return advPicUrl2RequestUrl;
        }

        public void setAdvPicUrl2RequestUrl(String advPicUrl2RequestUrl) {
            this.advPicUrl2RequestUrl = advPicUrl2RequestUrl;
        }

        public String getAdvPicUrl3RequestUrl() {
            return advPicUrl3RequestUrl;
        }

        public void setAdvPicUrl3RequestUrl(String advPicUrl3RequestUrl) {
            this.advPicUrl3RequestUrl = advPicUrl3RequestUrl;
        }

        public List<String> getAdvPicUrlList() {
            return advPicUrlList;
        }

        public void setAdvPicUrlList(List<String> advPicUrlList) {
            this.advPicUrlList = advPicUrlList;
        }
    }
}
