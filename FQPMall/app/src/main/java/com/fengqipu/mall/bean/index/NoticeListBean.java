package com.fengqipu.mall.bean.index;

/**
 * Created by huqing on 2016/7/12.
 */
public class NoticeListBean {

    /**
     * id : 2
     * type : 1
     * shopID :
     * title : 通知测试的标题123
     * openType : 3
     * link : www.taobao.com
     * content :
     * toShopID :
     * toContentID :
     * createTime : 2017-06-12
     */

    private String id;
    private int type;
    private String shopID;
    private String title;
    private int openType;
    private String link;
    private String content;
    private String toShopID;
    private String toContentID;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToShopID() {
        return toShopID;
    }

    public void setToShopID(String toShopID) {
        this.toShopID = toShopID;
    }

    public String getToContentID() {
        return toContentID;
    }

    public void setToContentID(String toContentID) {
        this.toContentID = toContentID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
