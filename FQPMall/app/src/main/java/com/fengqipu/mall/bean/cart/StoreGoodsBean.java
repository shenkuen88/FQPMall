package com.fengqipu.mall.bean.cart;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwei on 2016/8/2 0002.
 */
public class StoreGoodsBean implements Serializable{
    private StoreBean storeBean;

    private List<GoodsBean> goodsBeanList=new ArrayList<>();

    public StoreGoodsBean() {
    }

    public StoreGoodsBean(StoreBean storeBean, List<GoodsBean> goodsBeanList) {
        this.storeBean = storeBean;
        this.goodsBeanList = goodsBeanList;
    }

    protected StoreGoodsBean(Parcel in) {
        goodsBeanList = in.createTypedArrayList(GoodsBean.CREATOR);
    }

    public StoreBean getStoreBean() {
        return storeBean;
    }

    public void setStoreBean(StoreBean storeBean) {
        this.storeBean = storeBean;
    }

    public List<GoodsBean> getGoodsBeanList() {
        return goodsBeanList;
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList) {
        this.goodsBeanList = goodsBeanList;
    }
}
