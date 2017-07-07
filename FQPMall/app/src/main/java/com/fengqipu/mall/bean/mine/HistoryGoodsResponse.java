package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2016/7/10.
 */
public class HistoryGoodsResponse extends BaseResponse {

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

        /**
         * id : 2
         * operationType : 5
         * userID : 3
         * userName :
         * clientIP :
         * extend1 : 2
         * extend2 :
         * contentName : null
         * picUrl : null
         * price : null
         * beginTime : null
         * endTime : null
         * delFlag : 0
         * createTime : 2017-07-06
         * createTimeStr : 2017-07-06 13:53:52
         * thumPicUrl :
         */

        private String id;
        private int operationType;
        private String userID;
        private String userName;
        private String clientIP;
        private String extend1;
        private String extend2;
        private String contentName;
        private String picUrl;
        private String price;
        private String beginTime;
        private String endTime;
        private int delFlag;
        private String createTime;
        private String createTimeStr;
        private String thumPicUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getOperationType() {
            return operationType;
        }

        public void setOperationType(int operationType) {
            this.operationType = operationType;
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

        public String getClientIP() {
            return clientIP;
        }

        public void setClientIP(String clientIP) {
            this.clientIP = clientIP;
        }

        public String getExtend1() {
            return extend1;
        }

        public void setExtend1(String extend1) {
            this.extend1 = extend1;
        }

        public String getExtend2() {
            return extend2;
        }

        public void setExtend2(String extend2) {
            this.extend2 = extend2;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getThumPicUrl() {
            return thumPicUrl;
        }

        public void setThumPicUrl(String thumPicUrl) {
            this.thumPicUrl = thumPicUrl;
        }
    }
}
