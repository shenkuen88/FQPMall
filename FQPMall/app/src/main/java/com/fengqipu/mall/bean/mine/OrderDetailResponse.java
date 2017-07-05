package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2016/7/23 0023.
 */
public class OrderDetailResponse extends BaseResponse{

    /**
     * orderID : 31
     * orderCode : 1608040006
     * shopID : 1
     * userID : 9
     * payType : 2
     * outerCode : 2016080421001004580201863729
     * originalPrice : null
     * realPrice : 0
     * freight : null
     * orderContentList : null
     * status : 3
     * refundStatus : null
     * delFlag : 0
     * payTime : 2016-08-04
     * deliveryTime : 2016-08-04
     * signTime : null
     * createTime : 2016-08-04
     * shopName : 中巢
     * createTimeShow : 20160804010243
     * createTimeStr : 20160804010243
     * deliveryTimeShow : 2016-08-04 13:51:53
     * endTimeShow : 10天0小时0分钟
     * payTimeShow : 2016-08-04 12:26:30
     */

    private OrderBean order;
    /**
     * recordID : 31
     * orderID : 31
     * deliveryAddress : 收货地址：江苏省南京市浦口区1469442172617
     * deliveryUser : 收货人：修改后的
     * phone : 手机号码：15211111111
     * freight : 0
     * expressCode : yuantong
     * deliveryNum : 882430616691113340
     * status : 2
     * createTime : 2016-08-04
     * expressName :
     */

    private DeliveryBean delivery;
    /**
     * order : {"orderID":"31","orderCode":"1608040006","shopID":"1","userID":"9","payType":2,"outerCode":"2016080421001004580201863729","originalPrice":null,"realPrice":0,"freight":null,"orderContentList":null,"status":3,"refundStatus":null,"delFlag":0,"payTime":"2016-08-04","deliveryTime":"2016-08-04","signTime":null,"createTime":"2016-08-04","shopName":"中巢","createTimeShow":"20160804010243","createTimeStr":"20160804010243","deliveryTimeShow":"2016-08-04 13:51:53","endTimeShow":"10天0小时0分钟","payTimeShow":"2016-08-04 12:26:30"}
     * delivery : {"recordID":"31","orderID":"31","deliveryAddress":"收货地址：江苏省南京市浦口区1469442172617","deliveryUser":"收货人：修改后的","phone":"手机号码：15211111111","freight":0,"expressCode":"yuantong","deliveryNum":"882430616691113340","status":2,"createTime":"2016-08-04","expressName":""}
     * deliveryRecordList : null
     */

    private List<DeliveryRecordListBean> deliveryRecordList;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public DeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryBean delivery) {
        this.delivery = delivery;
    }

    public List<DeliveryRecordListBean> getDeliveryRecordList() {
        return deliveryRecordList;
    }

    public void setDeliveryRecordList(List<DeliveryRecordListBean> deliveryRecordList) {
        this.deliveryRecordList = deliveryRecordList;
    }

    public static class DeliveryRecordListBean {
        private String context;
        private String time;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    public static class OrderBean {


        /**
         * id : 6
         * orderCode : 17070514992228942280001
         * shopID : 3
         * userID : 3
         * userName : null
         * userPortrait : null
         * payType : 2
         * outerCode : null
         * originalPrice : null
         * realPrice : 4000
         * freight : null
         * orderContentList : [{"id":"4454","orderID":"6","contentID":"1","contentName":"智能榨汁机","picUrl":"/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg","count":2,"style":"高效版","color":null,"realPrice":1500,"createTime":"2017-07-05","picUrlRequestUrl":"http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg"},{"id":"4455","orderID":"6","contentID":"1","contentName":"智能榨汁机","picUrl":"/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg","count":1,"style":"普通版","color":"黄色","realPrice":1000,"createTime":"2017-07-05","picUrlRequestUrl":"http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg"}]
         * status : 1
         * refundStatus : null
         * delFlag : 0
         * payTime : null
         * deliveryTime : null
         * signTime : null
         * createTime : 2017-07-05
         * payTimeShow : 
         * deliveryTimeShow : 
         * endTimeShow : 2天0小时0分钟
         * createTimeStr : 20170705104814
         * shopName : 商铺1
         * createTimeShow : 20170705104814
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
        private int realPrice;
        private String freight;
        private int status;
        private String refundStatus;
        private int delFlag;
        private String payTime;
        private String deliveryTime;
        private String signTime;
        private String createTime;
        private String payTimeShow;
        private String deliveryTimeShow;
        private String endTimeShow;
        private String createTimeStr;
        private String shopName;
        private String createTimeShow;
        /**
         * id : 4454
         * orderID : 6
         * contentID : 1
         * contentName : 智能榨汁机
         * picUrl : /file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         * count : 2
         * style : 高效版
         * color : null
         * realPrice : 1500
         * createTime : 2017-07-05
         * picUrlRequestUrl : http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         */

        private List<OrderContentListBean> orderContentList;

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

        public int getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(int realPrice) {
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

        public String getPayTimeShow() {
            return payTimeShow;
        }

        public void setPayTimeShow(String payTimeShow) {
            this.payTimeShow = payTimeShow;
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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getCreateTimeShow() {
            return createTimeShow;
        }

        public void setCreateTimeShow(String createTimeShow) {
            this.createTimeShow = createTimeShow;
        }

        public List<OrderContentListBean> getOrderContentList() {
            return orderContentList;
        }

        public void setOrderContentList(List<OrderContentListBean> orderContentList) {
            this.orderContentList = orderContentList;
        }

        public static class OrderContentListBean {
            private String id;
            private String orderID;
            private String contentID;
            private String contentName;
            private String picUrl;
            private int count;
            private String style;
            private String color;
            private int realPrice;
            private String createTime;
            private String picUrlRequestUrl;

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

            public int getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(int realPrice) {
                this.realPrice = realPrice;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getPicUrlRequestUrl() {
                return picUrlRequestUrl;
            }

            public void setPicUrlRequestUrl(String picUrlRequestUrl) {
                this.picUrlRequestUrl = picUrlRequestUrl;
            }
        }
    }

    public static class DeliveryBean {
        private String recordID;
        private String orderID;
        private String deliveryAddress;
        private String deliveryUser;
        private String phone;
        private int freight;
        private String expressCode;
        private String deliveryNum;
        private int status;
        private String createTime;
        private String expressName;

        public String getRecordID() {
            return recordID;
        }

        public void setRecordID(String recordID) {
            this.recordID = recordID;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getDeliveryUser() {
            return deliveryUser;
        }

        public void setDeliveryUser(String deliveryUser) {
            this.deliveryUser = deliveryUser;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getFreight() {
            return freight;
        }

        public void setFreight(int freight) {
            this.freight = freight;
        }

        public String getExpressCode() {
            return expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public String getDeliveryNum() {
            return deliveryNum;
        }

        public void setDeliveryNum(String deliveryNum) {
            this.deliveryNum = deliveryNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getExpressName() {
            return expressName;
        }

        public void setExpressName(String expressName) {
            this.expressName = expressName;
        }
    }
}
