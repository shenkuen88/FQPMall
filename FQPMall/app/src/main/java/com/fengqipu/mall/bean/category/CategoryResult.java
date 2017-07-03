package com.fengqipu.mall.bean.category;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CategoryResult {


    /**
     * id : 1
     * categoryName : 电器
     * parentID : -1
     * seq : 2
     * cover : null
     * openType : 1
     * link :
     * content :
     * toShopID : null
     * toContentID : null
     * status : 1
     * delFlag : 0
     * createTime : 2017-06-12
     * coverRequestUrl :
     */

    private String id;
    private String categoryName;
    private String parentID;
    private int seq;
    private String cover;
    private int openType;
    private String link;
    private String content;
    private String toShopID;
    private String toContentID;
    private int status;
    private int delFlag;
    private String createTime;
    private String coverRequestUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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
