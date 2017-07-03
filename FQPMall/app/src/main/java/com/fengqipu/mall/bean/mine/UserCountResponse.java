package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

/**
 * Created by jwei on 2017/6/29 0029.
 */
public class UserCountResponse extends BaseResponse{


    /**
     * viewContentCount : 0
     * contentFavoriteCount : 0
     * shop1FavoriteCount : 0
     * shop2FavoriteCount : 0
     */

    private int viewContentCount;
    private int contentFavoriteCount;
    private int shop1FavoriteCount;
    private int shop2FavoriteCount;

    public int getViewContentCount() {
        return viewContentCount;
    }

    public void setViewContentCount(int viewContentCount) {
        this.viewContentCount = viewContentCount;
    }

    public int getContentFavoriteCount() {
        return contentFavoriteCount;
    }

    public void setContentFavoriteCount(int contentFavoriteCount) {
        this.contentFavoriteCount = contentFavoriteCount;
    }

    public int getShop1FavoriteCount() {
        return shop1FavoriteCount;
    }

    public void setShop1FavoriteCount(int shop1FavoriteCount) {
        this.shop1FavoriteCount = shop1FavoriteCount;
    }

    public int getShop2FavoriteCount() {
        return shop2FavoriteCount;
    }

    public void setShop2FavoriteCount(int shop2FavoriteCount) {
        this.shop2FavoriteCount = shop2FavoriteCount;
    }
}
