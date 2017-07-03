package com.fengqipu.mall.bean.category;

/**
 * Created by Administrator on 2016/7/13.
 */
public class LeftBean {
    private String title;
    private String id;
    private String name;
    private String pic;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LeftBean() {
    }

    public LeftBean(String title, String id, String name, String pic,String link) {
        this.title = title;
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.link=link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
