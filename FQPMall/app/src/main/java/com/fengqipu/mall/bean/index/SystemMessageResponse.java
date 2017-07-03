package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class SystemMessageResponse extends BaseResponse
{


    /**
     * totalCount : 1
     * messageList : [{"messageID":"1","messageType":1,"userID":"9","orderID":"2","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","message":"您购买的XXX已经发货","createTime":"2016-08-01"}]
     */

    private int totalCount;
    /**
     * messageID : 1
     * messageType : 1
     * userID : 9
     * orderID : 2
     * picUrl : http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg
     * message : 您购买的XXX已经发货
     * createTime : 2016-08-01
     */

    private List<MessageListBean> messageList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageListBean> messageList) {
        this.messageList = messageList;
    }

    public static class MessageListBean {
        private String messageID;
        private int messageType;
        private String userID;
        private String orderID;
        private String picUrl;
        private String message;
        private String createTime;

        public String getMessageID() {
            return messageID;
        }

        public void setMessageID(String messageID) {
            this.messageID = messageID;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
