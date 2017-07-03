package com.fengqipu.mall.bean.cart;

import java.util.List;

public class UpdateCartRecordReq
{
    private List<CartResponse.CartRecord> cartRecordList;

  

    public List<CartResponse.CartRecord> getCartRecordList()
    {
        return cartRecordList;
    }

    public void setCartRecordList(List<CartResponse.CartRecord> cartRecordList)
    {
        this.cartRecordList = cartRecordList;
    }
    
}
