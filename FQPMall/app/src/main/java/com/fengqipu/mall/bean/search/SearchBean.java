package com.fengqipu.mall.bean.search;

/**
 * Created by jwei on 2017/6/19 0019.
 */

public class SearchBean {
    private int id;
    private String name;
    private int type;//0.热门 1.历史

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
