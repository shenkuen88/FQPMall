package com.fengqipu.mall.bean.search;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/7/13 0013.
 */

public class CityResponse extends BaseResponse {
    private List<HotAddressBean>  addressList;

    public List<HotAddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<HotAddressBean> addressList) {
        this.addressList = addressList;
    }

    public class HotAddressBean {

        /**
         * addressID : 320000
         * name : 江苏省
         * level : 1
         * detail : 江苏省
         * hot : null
         * status : 1
         * parentID : -1
         */

        private String addressID;
        private String name;
        private int level;
        private String detail;
        private Object hot;
        private int status;
        private String parentID;

        public String getAddressID() {
            return addressID;
        }

        public void setAddressID(String addressID) {
            this.addressID = addressID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public Object getHot() {
            return hot;
        }

        public void setHot(Object hot) {
            this.hot = hot;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getParentID() {
            return parentID;
        }

        public void setParentID(String parentID) {
            this.parentID = parentID;
        }
    }
}
