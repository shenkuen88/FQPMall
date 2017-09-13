package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 */
public class OrderResponse extends BaseResponse implements Serializable{

    /**
     * orderList : [{"orderID":"31","orderCode":"1608040006","shopID":"1","userID":"9","payType":2,"outerCode":null,"originalPrice":null,"realPrice":0,"orderContentList":[{"relationID":"71","orderID":"31","contentID":"1","contentName":"内容1","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","count":1,"style":"款式1","originalPrice":null,"realPrice":0,"createTime":"2016-08-04"}],"status":1,"delFlag":0,"payTime":null,"deliveryTime":null,"createTime":"2016-08-04","payTimeShow":"","deliveryTimeShow":"","createTimeStr":"20160804010243","createTimeShow":"20160804010243","shopName":"中巢"},{"orderID":"30","orderCode":"1608040005","shopID":"1","userID":"9","payType":2,"outerCode":null,"originalPrice":null,"realPrice":0,"orderContentList":[{"relationID":"70","orderID":"30","contentID":"1","contentName":"内容1","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","count":1,"style":"款式1","originalPrice":null,"realPrice":0,"createTime":"2016-08-04"}],"status":1,"delFlag":0,"payTime":null,"deliveryTime":null,"createTime":"2016-08-04","payTimeShow":"","deliveryTimeShow":"","createTimeStr":"20160804005949","createTimeShow":"20160804005949","shopName":"中巢"},{"orderID":"29","orderCode":"1608040004","shopID":"1","userID":"9","payType":2,"outerCode":null,"originalPrice":null,"realPrice":0,"orderContentList":[{"relationID":"69","orderID":"29","contentID":"1","contentName":"内容1","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","count":1,"style":"款式1","originalPrice":null,"realPrice":0,"createTime":"2016-08-04"}],"status":1,"delFlag":0,"payTime":null,"deliveryTime":null,"createTime":"2016-08-04","payTimeShow":"","deliveryTimeShow":"","createTimeStr":"20160804005931","createTimeShow":"20160804005931","shopName":"中巢"},{"orderID":"28","orderCode":"1608040003","shopID":"1","userID":"9","payType":2,"outerCode":null,"originalPrice":null,"realPrice":0,"orderContentList":[{"relationID":"68","orderID":"28","contentID":"1","contentName":"内容1","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","count":1,"style":"款式1","originalPrice":null,"realPrice":0,"createTime":"2016-08-04"}],"status":1,"delFlag":0,"payTime":null,"deliveryTime":null,"createTime":"2016-08-04","payTimeShow":"","deliveryTimeShow":"","createTimeStr":"20160804004958","createTimeShow":"20160804004958","shopName":"中巢"},{"orderID":"27","orderCode":"1608040002","shopID":"1","userID":"9","payType":2,"outerCode":null,"originalPrice":null,"realPrice":0,"orderContentList":[{"relationID":"67","orderID":"27","contentID":"1","contentName":"内容1","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","count":1,"style":"款式1","originalPrice":null,"realPrice":0,"createTime":"2016-08-04"}],"status":1,"delFlag":0,"payTime":null,"deliveryTime":null,"createTime":"2016-08-04","payTimeShow":"","deliveryTimeShow":"","createTimeStr":"20160804004924","createTimeShow":"20160804004924","shopName":"中巢"}]
     * totalCount : null
     */

    private int totalCount;
    /**
     * orderID : 31
     * orderCode : 1608040006
     * shopID : 1
     * userID : 9
     * payType : 2
     * outerCode : null
     * originalPrice : null
     * realPrice : 0.0
     * orderContentList : [{"relationID":"71","orderID":"31","contentID":"1","contentName":"内容1","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","count":1,"style":"款式1","originalPrice":null,"realPrice":0,"createTime":"2016-08-04"}]
     * status : 1
     * delFlag : 0
     * payTime : null
     * deliveryTime : null
     * createTime : 2016-08-04
     * payTimeShow :
     * deliveryTimeShow :
     * createTimeStr : 20160804010243
     * createTimeShow : 20160804010243
     * shopName : 中巢
     */

    private List<OrderListBean> orderList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }


    public static class OrderListBean implements Serializable{

        /**
         * id : 4453
         * orderCode : 17070414991587305030008
         * shopID : 3
         * userID : 1
         * userName : null
         * userPortrait : null
         * payType : 2
         * outerCode : null
         * originalPrice : null
         * realPrice : 1500
         * freight : null
         * orderContentList : [{"id":"5","orderID":"5","contentID":"1","contentName":"智能榨汁机","picUrl":"/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg","count":1,"style":"高效版","color":null,"realPrice":1500,"createTime":"2017-07-04"}]
         * status : 1
         * refundStatus : null
         * delFlag : 0
         * payTime : null
         * deliveryTime : null
         * signTime : null
         * createTime : 2017-07-04
         * deliveryTimeShow :
         * endTimeShow : 2天0小时0分钟
         * createTimeStr : 20170704165850
         * createTimeShow : 20170704165850
         * shopName : 商铺1
         * payTimeShow :
         */

        private String id;
        private String orderCode;
        private String shopID;
        private String userID;
        private String userName;
        private String userPortrait;
        private int payType;
        private String outerCode;
        private String originalPrice;
        private double realPrice;
        private String freight;
        private int status;
        private String refundStatus;
        private int delFlag;
        private String payTime;
        private String deliveryTime;
        private String signTime;
        private String createTime;
        private String deliveryTimeShow;
        private String endTimeShow;
        private String createTimeStr;
        private String createTimeShow;
        private String shopName;
        private String payTimeShow;
        private String remark;
        private List<OrderContentListBean> orderContentList;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPortrait() {
            return userPortrait;
        }

        public void setUserPortrait(String userPortrait) {
            this.userPortrait = userPortrait;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getOuterCode() {
            return outerCode;
        }

        public void setOuterCode(String outerCode) {
            this.outerCode = outerCode;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(double realPrice) {
            this.realPrice = realPrice;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(String refundStatus) {
            this.refundStatus = refundStatus;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeliveryTimeShow() {
            return deliveryTimeShow;
        }

        public void setDeliveryTimeShow(String deliveryTimeShow) {
            this.deliveryTimeShow = deliveryTimeShow;
        }

        public String getEndTimeShow() {
            return endTimeShow;
        }

        public void setEndTimeShow(String endTimeShow) {
            this.endTimeShow = endTimeShow;
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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getPayTimeShow() {
            return payTimeShow;
        }

        public void setPayTimeShow(String payTimeShow) {
            this.payTimeShow = payTimeShow;
        }

        public List<OrderContentListBean> getOrderContentList() {
            return orderContentList;
        }

        public void setOrderContentList(List<OrderContentListBean> orderContentList) {
            this.orderContentList = orderContentList;
        }

        public static class OrderContentListBean implements Serializable{
            /**
             * id : 5
             * orderID : 5
             * contentID : 1
             * contentName : 智能榨汁机
             * picUrl : /file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
             * count : 1
             * style : 高效版
             * color : null
             * realPrice : 1500
             * createTime : 2017-07-04
             */

            private String id;
            private String orderID;
            private String contentID;
            private String contentName;
            private String picUrl;
            private int count;
            private String style;
            private String color;
            private double realPrice;
            private String createTime;
            private String originalPrice;
            private String picUrlRequestUrl;

            public String getPicUrlRequestUrl() {
                return picUrlRequestUrl;
            }

            public void setPicUrlRequestUrl(String picUrlRequestUrl) {
                this.picUrlRequestUrl = picUrlRequestUrl;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderID() {
                return orderID;
            }

            public void setOrderID(String orderID) {
                this.orderID = orderID;
            }

            public String getContentID() {
                return contentID;
            }

            public void setContentID(String contentID) {
                this.contentID = contentID;
            }

            public String getContentName() {
                return contentName;
            }

            public void setContentName(String contentName) {
                this.contentName = contentName;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public double getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(double realPrice) {
                this.realPrice = realPrice;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
