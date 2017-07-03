package com.fengqipu.mall.bean.mine;


import com.fengqipu.mall.bean.BaseResponse;

public class AddReceiveAddressResponse extends BaseResponse
{

    /**
     * recordID : 1
     * userID : 9
     * province : 江苏省
     * city : 南京市
     * area : 丹徒区
     * detail : aaaaaaaddress
     * deliveryUser : name
     * phone : 15211111111
     * isDefault : null
     * createTime : null
     */

    private AddressBean userAddress;

    public AddressBean getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(AddressBean userAddress) {
        this.userAddress = userAddress;
    }


}
