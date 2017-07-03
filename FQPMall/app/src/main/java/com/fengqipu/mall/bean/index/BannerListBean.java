package com.fengqipu.mall.bean.index;

import java.io.Serializable;

/**
 * Created by huqing on 2016/7/12.
 */
public class BannerListBean implements Serializable{

    /**
     * id : 7
     * position : 1
     * shopID :
     * categoryID : null
     * title : 111
     * cover : /file/banner/ad052fc8-e0b6-46e4-8b69-53aec6388333.jpg
     * openType : 3
     * link : www.baidu.com
     * content :
     * toShopID :
     * toContentID :
     * createTime : 2017-06-22
     * coverRequestUrl : http://116.62.116.15/f/file/banner/ad052fc8-e0b6-46e4-8b69-53aec6388333.jpg
     */

    private String id;
    private int position;
    private String shopID;
    private Object categoryID;
    private String title;
    private String cover;
    private int openType;
    private String link;
    private String content;
    private String toShopID;
    private String toContentID;
    private String createTime;
    private String coverRequestUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public Object getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Object categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getCoverRequestUrl() {
        return coverRequestUrl;
    }

    public void setCoverRequestUrl(String coverRequestUrl) {
        this.coverRequestUrl = coverRequestUrl;
    }
}
