package com.fengqipu.mall.bean.goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class GuiGeBean {
    private String name;
    private List<String> strList=new ArrayList<>();

    public GuiGeBean() {
    }

    public GuiGeBean(String name, List<String> strList) {
        this.name = name;
        this.strList = strList;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
