package com.fengqipu.mall.bean.category;

/**
 * Created by jwei on 2016/7/23 0023.
 */
public class EduVBean {
    private String id;
    private String name;
    private String info;
    private String pic;
    private String history;

    public EduVBean() {
    }

    public EduVBean(String id, String name, String info, String pic, String history) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.pic = pic;
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
