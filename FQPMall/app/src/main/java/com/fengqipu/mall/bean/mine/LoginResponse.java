package com.fengqipu.mall.bean.mine;


import com.fengqipu.mall.bean.BaseResponse;

public class LoginResponse extends BaseResponse
{

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }


}
