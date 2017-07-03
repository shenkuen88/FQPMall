package com.fengqipu.mall.bean.mine;

import java.io.Serializable;

/**
 * Created by huqing on 2016/7/14.
 */
public class AddressBean implements Serializable{
    private String id;
    private String userID;
    private String province;
    private String city;
    private String area;
    private String detail;
    private String deliveryUser;
    private String phone;
    private String isDefault;
    private String createTime;

    @Override
    public String toString() {
        return "AddressBean{" +
                "recordID='" + id + '\'' +
                ", userID='" + userID + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", detail='" + detail + '\'' +
                ", deliveryUser='" + deliveryUser + '\'' +
                ", phone='" + phone + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
