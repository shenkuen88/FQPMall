package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by huqing on 2016/7/14.
 */
public class AddressListResponse extends BaseResponse{

    private List<AddressBean> userAddressList;

    public List<AddressBean> getUserAddressList() {
        return userAddressList;
    }

    public void setUserAddressList(List<AddressBean> userAddressList) {
        this.userAddressList = userAddressList;
    }

}
