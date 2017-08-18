package com.fengqipu.mall.bean.shop;

import com.fengqipu.mall.bean.BaseResponse;

/**
 * Created by jwei on 2017/8/18 0018.
 */
public class AlreadyAppliedResponse extends BaseResponse{

    /**
     * status : 1
     * shop : null
     */

    private int status;
    private Object shop;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getShop() {
        return shop;
    }

    public void setShop(Object shop) {
        this.shop = shop;
    }
}
