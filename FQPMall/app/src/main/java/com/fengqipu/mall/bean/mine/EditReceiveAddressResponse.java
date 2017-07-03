package com.fengqipu.mall.bean.mine;


import com.fengqipu.mall.bean.BaseResponse;

public class EditReceiveAddressResponse extends BaseResponse
{


    private AddressBean userAddress;

    public AddressBean getUserAddress()
    {
        return userAddress;
    }

    public void setUserAddress(AddressBean userAddress)
    {
        this.userAddress = userAddress;
    }


}
