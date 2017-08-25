package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/6/28 0028.
 */
public class TuiJianResponse extends BaseResponse{


    /**
     * banner : {"id":null,"position":0,"shopID":null,"categoryID":null,"title":"111","cover":"https://img10.360buyimg.com/cms/s780x260_jfs/t6049/98/9696659757/13573/879641a0/5996c480N85f524ef.jpg!q90","openType":1,"link":"www.baidu.com","content":null,"toShopID":null,"toContentID":null,"createTime":null,"coverRequestUrl":"http://file.fengqipu.comhttps://img10.360buyimg.com/cms/s780x260_jfs/t6049/98/9696659757/13573/879641a0/5996c480N85f524ef.jpg!q90"}
     * contentList : [{"id":"5","contentCode":"1717002001","contentName":"1毛测试商品","keyword":"22,33","category1":"1","category1Name":"电器","category2":"3","category2Name":"厨卫电器","contentType":"1","model":"","abstracts":"121212","picUrl1":"/file/content/4ef27d9a-f2d3-483f-ae20-d725a79806d1.jpg","picUrl2":"/file/content/b5fcfcb0-fea1-4e44-b77a-d82eda91ad02.jpg","picUrl3":"","picUrl4":"","description":"  <p>32人发顺风<\/p>","shopID":"6","originalPrice":null,"price":0.01,"sales":null,"monthSales":null,"score":null,"viewCount":0,"appraiseCount":0,"favoriteCount":1,"adminID":"1","status":1,"delFlag":0,"createTime":"2017-08-16","contentTypeName":"油烟机","descriptionLink":"http://api.fengqipu.com/h5/detail?type=4&id=5","shopProvince":"江苏省","shopName":"测试商铺AAAAAAAA","picUrl1RequestUrl":"http://file.fengqipu.com/file/content/4ef27d9a-f2d3-483f-ae20-d725a79806d1.jpg","picUrl2RequestUrl":"http://file.fengqipu.com/file/content/b5fcfcb0-fea1-4e44-b77a-d82eda91ad02.jpg","picUrl3RequestUrl":"","picUrl4RequestUrl":"","picUrlList":["http://file.fengqipu.com/file/content/4ef27d9a-f2d3-483f-ae20-d725a79806d1.jpg","http://file.fengqipu.com/file/content/b5fcfcb0-fea1-4e44-b77a-d82eda91ad02.jpg"],"shopCity":"南京市"}]
     */

    private BannerBean banner;
    private List<ContentListBean> contentList;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    public static class BannerBean {
        /**
         * id : null
         * position : 0
         * shopID : null
         * categoryID : null
         * title : 111
         * cover : https://img10.360buyimg.com/cms/s780x260_jfs/t6049/98/9696659757/13573/879641a0/5996c480N85f524ef.jpg!q90
         * openType : 1
         * link : www.baidu.com
         * content : null
         * toShopID : null
         * toContentID : null
         * createTime : null
         * coverRequestUrl : http://file.fengqipu.comhttps://img10.360buyimg.com/cms/s780x260_jfs/t6049/98/9696659757/13573/879641a0/5996c480N85f524ef.jpg!q90
         */

        private String id;
        private int position;
        private String shopID;
        private String categoryID;
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

        public String getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(String categoryID) {
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

    public static class ContentListBean {
        /**
         * id : 5
         * contentCode : 1717002001
         * contentName : 1毛测试商品
         * keyword : 22,33
         * category1 : 1
         * category1Name : 电器
         * category2 : 3
         * category2Name : 厨卫电器
         * contentType : 1
         * model : 
         * abstracts : 121212
         * picUrl1 : /file/content/4ef27d9a-f2d3-483f-ae20-d725a79806d1.jpg
         * picUrl2 : /file/content/b5fcfcb0-fea1-4e44-b77a-d82eda91ad02.jpg
         * picUrl3 : 
         * picUrl4 : 
         * description :   <p>32人发顺风</p>
         * shopID : 6
         * originalPrice : null
         * price : 0.01
         * sales : null
         * monthSales : null
         * score : null
         * viewCount : 0
         * appraiseCount : 0
         * favoriteCount : 1
         * adminID : 1
         * status : 1
         * delFlag : 0
         * createTime : 2017-08-16
         * contentTypeName : 油烟机
         * descriptionLink : http://api.fengqipu.com/h5/detail?type=4&id=5
         * shopProvince : 江苏省
         * shopName : 测试商铺AAAAAAAA
         * picUrl1RequestUrl : http://file.fengqipu.com/file/content/4ef27d9a-f2d3-483f-ae20-d725a79806d1.jpg
         * picUrl2RequestUrl : http://file.fengqipu.com/file/content/b5fcfcb0-fea1-4e44-b77a-d82eda91ad02.jpg
         * picUrl3RequestUrl : 
         * picUrl4RequestUrl : 
         * picUrlList : ["http://file.fengqipu.com/file/content/4ef27d9a-f2d3-483f-ae20-d725a79806d1.jpg","http://file.fengqipu.com/file/content/b5fcfcb0-fea1-4e44-b77a-d82eda91ad02.jpg"]
         * shopCity : 南京市
         */

        private String id;
        private String contentCode;
        private String contentName;
        private String keyword;
        private String category1;
        private String category1Name;
        private String category2;
        private String category2Name;
        private String contentType;
        private String model;
        private String abstracts;
        private String picUrl1;
        private String picUrl2;
        private String picUrl3;
        private String picUrl4;
        private String description;
        private String shopID;
        private String originalPrice;
        private double price;
        private String sales;
        private String monthSales;
        private String score;
        private int viewCount;
        private int appraiseCount;
        private int favoriteCount;
        private String adminID;
        private int status;
        private int delFlag;
        private String createTime;
        private String contentTypeName;
        private String descriptionLink;
        private String shopProvince;
        private String shopName;
        private String picUrl1RequestUrl;
        private String picUrl2RequestUrl;
        private String picUrl3RequestUrl;
        private String picUrl4RequestUrl;
        private String shopCity;
        private List<String> picUrlList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContentCode() {
            return contentCode;
        }

        public void setContentCode(String contentCode) {
            this.contentCode = contentCode;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getCategory1() {
            return category1;
        }

        public void setCategory1(String category1) {
            this.category1 = category1;
        }

        public String getCategory1Name() {
            return category1Name;
        }

        public void setCategory1Name(String category1Name) {
            this.category1Name = category1Name;
        }

        public String getCategory2() {
            return category2;
        }

        public void setCategory2(String category2) {
            this.category2 = category2;
        }

        public String getCategory2Name() {
            return category2Name;
        }

        public void setCategory2Name(String category2Name) {
            this.category2Name = category2Name;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
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

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getMonthSales() {
            return monthSales;
        }

        public void setMonthSales(String monthSales) {
            this.monthSales = monthSales;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public int getAppraiseCount() {
            return appraiseCount;
        }

        public void setAppraiseCount(int appraiseCount) {
            this.appraiseCount = appraiseCount;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getAdminID() {
            return adminID;
        }

        public void setAdminID(String adminID) {
            this.adminID = adminID;
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

        public String getContentTypeName() {
            return contentTypeName;
        }

        public void setContentTypeName(String contentTypeName) {
            this.contentTypeName = contentTypeName;
        }

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
        }

        public String getShopProvince() {
            return shopProvince;
        }

        public void setShopProvince(String shopProvince) {
            this.shopProvince = shopProvince;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getPicUrl1RequestUrl() {
            return picUrl1RequestUrl;
        }

        public void setPicUrl1RequestUrl(String picUrl1RequestUrl) {
            this.picUrl1RequestUrl = picUrl1RequestUrl;
        }

        public String getPicUrl2RequestUrl() {
            return picUrl2RequestUrl;
        }

        public void setPicUrl2RequestUrl(String picUrl2RequestUrl) {
            this.picUrl2RequestUrl = picUrl2RequestUrl;
        }

        public String getPicUrl3RequestUrl() {
            return picUrl3RequestUrl;
        }

        public void setPicUrl3RequestUrl(String picUrl3RequestUrl) {
            this.picUrl3RequestUrl = picUrl3RequestUrl;
        }

        public String getPicUrl4RequestUrl() {
            return picUrl4RequestUrl;
        }

        public void setPicUrl4RequestUrl(String picUrl4RequestUrl) {
            this.picUrl4RequestUrl = picUrl4RequestUrl;
        }

        public String getShopCity() {
            return shopCity;
        }

        public void setShopCity(String shopCity) {
            this.shopCity = shopCity;
        }

        public List<String> getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(List<String> picUrlList) {
            this.picUrlList = picUrlList;
        }
    }
}
