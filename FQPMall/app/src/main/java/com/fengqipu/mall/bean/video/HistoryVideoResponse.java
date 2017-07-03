package com.fengqipu.mall.bean.video;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2016/7/9.
 */
public class HistoryVideoResponse extends BaseResponse {

    /**
     * userOperationList : [{"createTime":"2016-08-03","picUrl":"http://221.226.118.110:18080/skin/upload/content/0a433309-d5ec-441e-b3eb-ae413deb0beb.jpg","extend1":"3","beginTime":null,"extend2":null,"contentName":"内容3","endTime":null,"operationID":"3","userID":"9","createTimeStr":"2016-08-03 11:03:43","price":null,"operationType":5,"userName":"15251813022","clientIP":null,"delFlag":null},{"createTime":"2016-08-03","picUrl":"http://221.226.118.110:18080/skin/upload/content/d92875ea-55ea-4064-afc9-3a364aa52971.jpg","extend1":"2","beginTime":null,"extend2":null,"contentName":"内容2","endTime":null,"operationID":"2","userID":"9","createTimeStr":"2016-08-03 11:03:41","price":null,"operationType":5,"userName":"15251813022","clientIP":null,"delFlag":null},{"createTime":"2016-08-03","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","extend1":"1","beginTime":null,"extend2":null,"contentName":"内容1","endTime":null,"operationID":"1","userID":"9","createTimeStr":"2016-08-03 11:03:11","price":null,"operationType":5,"userName":"15251813022","clientIP":null,"delFlag":null}]
     * totalCount : 3
     */

    private int totalCount;
    /**
     * createTime : 2016-08-03
     * picUrl : http://221.226.118.110:18080/skin/upload/content/0a433309-d5ec-441e-b3eb-ae413deb0beb.jpg
     * extend1 : 3
     * beginTime : null
     * extend2 : null
     * contentName : 内容3
     * endTime : null
     * operationID : 3
     * userID : 9
     * createTimeStr : 2016-08-03 11:03:43
     * price : null
     * operationType : 5
     * userName : 15251813022
     * clientIP : null
     * delFlag : null
     */

    private List<UserOperationListBean> userOperationList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserOperationListBean> getUserOperationList() {
        return userOperationList;
    }

    public void setUserOperationList(List<UserOperationListBean> userOperationList) {
        this.userOperationList = userOperationList;
    }

    public static class UserOperationListBean {
        private String createTime;
        private String picUrl;
        private String extend1;
        private Object beginTime;
        private Object extend2;
        private String contentName;
        private Object endTime;
        private String operationID;
        private String userID;
        private String createTimeStr;
        private Object price;
        private int operationType;
        private String userName;
        private Object clientIP;
        private Object delFlag;

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

        public String getExtend1() {
            return extend1;
        }

        public void setExtend1(String extend1) {
            this.extend1 = extend1;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public Object getExtend2() {
            return extend2;
        }

        public void setExtend2(Object extend2) {
            this.extend2 = extend2;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public String getOperationID() {
            return operationID;
        }

        public void setOperationID(String operationID) {
            this.operationID = operationID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public int getOperationType() {
            return operationType;
        }

        public void setOperationType(int operationType) {
            this.operationType = operationType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getClientIP() {
            return clientIP;
        }

        public void setClientIP(Object clientIP) {
            this.clientIP = clientIP;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }
    }
}
