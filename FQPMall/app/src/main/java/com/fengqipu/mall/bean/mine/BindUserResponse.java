package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

/**
 * Created by jwei on 2017/8/4 0004.
 */
public class BindUserResponse extends BaseResponse{

    /**
     * user : null
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
