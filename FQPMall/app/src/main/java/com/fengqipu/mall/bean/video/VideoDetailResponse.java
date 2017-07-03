package com.fengqipu.mall.bean.video;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6.
 */
public class VideoDetailResponse extends BaseResponse {


    /**
     * contentID : 11
     * contentCode : null
     * contentName : 内容11
     * keyword : null
     * serviceType : 1
     * contentType : null
     * abstracts : 分等第三方第三方
     * picUrl1 : http://221.226.118.110:18080/skin/upload/content/97751e35-9427-487b-bd5d-7d1d4fc9168a.jpg
     * picUrl2 :
     * picUrl3 :
     * picUrl4 :
     * description :  <p>敷敷</p>
     * specification :
     * shopID : 1
     * categoryID : 8
     * categoryName : null
     * subCategoryID : 10
     * subCategoryName : null
     * brandID : null
     * brandName : null
     * free : 1
     * originalPrice : null
     * unifiedPrice : null
     * price : null
     * score : null
     * playUrl : null
     * playCount : null
     * appraiseCount : null
     * adminID : 1
     * delFlag : 0
     * status : 1
     * createTime : 2016-07-22
     * shopName : 中巢
     * descriptionLink : http://221.226.118.110:18080/api/h5/detail?type=4&id=11
     * specificationLink : http://221.226.118.110:18080/api/h5/detail?type=5&id=11
     */

    private ContentBean content;
    /**
     * content : {"contentID":"11","contentCode":null,"contentName":"内容11","keyword":null,"serviceType":1,"contentType":null,"abstracts":"分等第三方第三方","picUrl1":"http://221.226.118.110:18080/skin/upload/content/97751e35-9427-487b-bd5d-7d1d4fc9168a.jpg","picUrl2":"","picUrl3":"","picUrl4":"","description":" <p>敷敷<\/p>","specification":" ","shopID":"1","categoryID":"8","categoryName":null,"subCategoryID":"10","subCategoryName":null,"brandID":null,"brandName":null,"free":1,"originalPrice":null,"unifiedPrice":null,"price":null,"score":null,"playUrl":null,"playCount":null,"appraiseCount":null,"adminID":"1","delFlag":0,"status":1,"createTime":"2016-07-22","shopName":"中巢","descriptionLink":"http://221.226.118.110:18080/api/h5/detail?type=4&id=11","specificationLink":"http://221.226.118.110:18080/api/h5/detail?type=5&id=11"}
     * contentStyleList : []
     */

    private List<?> contentStyleList;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public List<?> getContentStyleList() {
        return contentStyleList;
    }

    public void setContentStyleList(List<?> contentStyleList) {
        this.contentStyleList = contentStyleList;
    }

    public static class ContentBean {
        private String contentID;
        private Object contentCode;
        private String contentName;
        private Object keyword;
        private int serviceType;
        private Object contentType;
        private String abstracts;
        private String picUrl1;
        private String picUrl2;
        private String picUrl3;
        private String picUrl4;
        private String description;
        private String specification;
        private String shopID;
        private String categoryID;
        private Object categoryName;
        private String subCategoryID;
        private Object subCategoryName;
        private Object brandID;
        private Object brandName;
        private int free;
        private Object originalPrice;
        private Object unifiedPrice;
        private Object price;
        private Object score;
        private Object playUrl;
        private Object playCount;
        private Object appraiseCount;
        private String adminID;
        private int delFlag;
        private int status;
        private String createTime;
        private String shopName;
        private String descriptionLink;
        private String specificationLink;

        public String getContentID() {
            return contentID;
        }

        public void setContentID(String contentID) {
            this.contentID = contentID;
        }

        public Object getContentCode() {
            return contentCode;
        }

        public void setContentCode(Object contentCode) {
            this.contentCode = contentCode;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public Object getContentType() {
            return contentType;
        }

        public void setContentType(Object contentType) {
            this.contentType = contentType;
        }

        public String getAbstracts() {
            return abstracts;
        }

        public void setAbstracts(String abstracts) {
            this.abstracts = abstracts;
        }

        public String getPicUrl1() {
            return picUrl1;
        }

        public void setPicUrl1(String picUrl1) {
            this.picUrl1 = picUrl1;
        }

        public String getPicUrl2() {
            return picUrl2;
        }

        public void setPicUrl2(String picUrl2) {
            this.picUrl2 = picUrl2;
        }

        public String getPicUrl3() {
            return picUrl3;
        }

        public void setPicUrl3(String picUrl3) {
            this.picUrl3 = picUrl3;
        }

        public String getPicUrl4() {
            return picUrl4;
        }

        public void setPicUrl4(String picUrl4) {
            this.picUrl4 = picUrl4;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(String categoryID) {
            this.categoryID = categoryID;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public String getSubCategoryID() {
            return subCategoryID;
        }

        public void setSubCategoryID(String subCategoryID) {
            this.subCategoryID = subCategoryID;
        }

        public Object getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(Object subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public Object getBrandID() {
            return brandID;
        }

        public void setBrandID(Object brandID) {
            this.brandID = brandID;
        }

        public Object getBrandName() {
            return brandName;
        }

        public void setBrandName(Object brandName) {
            this.brandName = brandName;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public Object getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Object originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Object getUnifiedPrice() {
            return unifiedPrice;
        }

        public void setUnifiedPrice(Object unifiedPrice) {
            this.unifiedPrice = unifiedPrice;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getScore() {
            return score;
        }

        public void setScore(Object score) {
            this.score = score;
        }

        public Object getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(Object playUrl) {
            this.playUrl = playUrl;
        }

        public Object getPlayCount() {
            return playCount;
        }

        public void setPlayCount(Object playCount) {
            this.playCount = playCount;
        }

        public Object getAppraiseCount() {
            return appraiseCount;
        }

        public void setAppraiseCount(Object appraiseCount) {
            this.appraiseCount = appraiseCount;
        }

        public String getAdminID() {
            return adminID;
        }

        public void setAdminID(String adminID) {
            this.adminID = adminID;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
        }

        public String getSpecificationLink() {
            return specificationLink;
        }

        public void setSpecificationLink(String specificationLink) {
            this.specificationLink = specificationLink;
        }
    }
}
