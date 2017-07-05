package com.fengqipu.mall.bean.cart;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/6/30 0030.
 */
public class GWCGoodsDetailResponse extends BaseResponse{

    /**
     * content : {"id":"1","contentCode":"1717001001","contentName":"智能榨汁机","keyword":"关键词","category1":"1","category2":"3","contentType":"","model":"T100","abstracts":"摘要信息","picUrl1":"/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","picUrl2":"/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg","picUrl3":"","picUrl4":"","description":"   <p>的方式的范德萨范德萨发多少发是到付<\/p>","shopID":"3","originalPrice":0,"price":1000,"sales":null,"monthSales":3,"score":null,"viewCount":null,"appraiseCount":null,"favoriteCount":null,"adminID":"1","status":1,"delFlag":0,"createTime":"2017-06-15","shopName":"商铺1","picUrlList":["http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg"],"picUrl1RequestUrl":"http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","picUrl2RequestUrl":"http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg","picUrl3RequestUrl":"","picUrl4RequestUrl":"","descriptionLink":"http://116.62.116.15/api/h5/detail?type=4&id=1","shopProvince":"320000","shopCity":"320800"}
     * contentStyleList : [{"id":"1","contentID":"1","style":"高效版","color":"红色","price":1500,"createTime":"2017-06-15"}]
     * freight : 0
     */

    private ContentBean content;
    private int isFavorite;//1。收藏 0。未收藏
    private int freight;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    private List<ContentStyleListBean> contentStyleList;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public List<ContentStyleListBean> getContentStyleList() {
        return contentStyleList;
    }

    public void setContentStyleList(List<ContentStyleListBean> contentStyleList) {
        this.contentStyleList = contentStyleList;
    }

    public static class ContentBean {
        /**
         * id : 1
         * contentCode : 1717001001
         * contentName : 智能榨汁机
         * keyword : 关键词
         * category1 : 1
         * category2 : 3
         * contentType : 
         * model : T100
         * abstracts : 摘要信息
         * picUrl1 : /file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg
         * picUrl2 : /file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg
         * picUrl3 : 
         * picUrl4 : 
         * description :    <p>的方式的范德萨范德萨发多少发是到付</p>
         * shopID : 3
         * originalPrice : 0.0
         * price : 1000.0
         * sales : null
         * monthSales : 3
         * score : null
         * viewCount : null
         * appraiseCount : null
         * favoriteCount : null
         * adminID : 1
         * status : 1
         * delFlag : 0
         * createTime : 2017-06-15
         * shopName : 商铺1
         * picUrlList : ["http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg"]
         * picUrl1RequestUrl : http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg
         * picUrl2RequestUrl : http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg
         * picUrl3RequestUrl : 
         * picUrl4RequestUrl : 
         * descriptionLink : http://116.62.116.15/api/h5/detail?type=4&id=1
         * shopProvince : 320000
         * shopCity : 320800
         */

        private String id;
        private String contentCode;
        private String contentName;
        private String keyword;
        private String category1;
        private String category2;
        private String contentType;
        private String model;
        private String abstracts;
        private String picUrl1;
        private String picUrl2;
        private String picUrl3;
        private String picUrl4;
        private String description;
        private String shopID;
        private double originalPrice;
        private double price;
        private String sales;
        private int monthSales;
        private String score;
        private String viewCount;
        private String appraiseCount;
        private String favoriteCount;
        private String adminID;
        private int status;
        private int delFlag;
        private String createTime;
        private String shopName;
        private String picUrl1RequestUrl;
        private String picUrl2RequestUrl;
        private String picUrl3RequestUrl;
        private String picUrl4RequestUrl;
        private String descriptionLink;
        private String shopProvince;
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

        public String getCategory2() {
            return category2;
        }

        public void setCategory2(String category2) {
            this.category2 = category2;
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

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
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

        public int getMonthSales() {
            return monthSales;
        }

        public void setMonthSales(int monthSales) {
            this.monthSales = monthSales;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getAppraiseCount() {
            return appraiseCount;
        }

        public void setAppraiseCount(String appraiseCount) {
            this.appraiseCount = appraiseCount;
        }

        public String getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(String favoriteCount) {
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

    public static class ContentStyleListBean {
        /**
         * id : 1
         * contentID : 1
         * style : 高效版
         * color : 红色
         * price : 1500.0
         * createTime : 2017-06-15
         */

        private String id;
        private String contentID;
        private String style;
        private String color;
        private double price;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContentID() {
            return contentID;
        }

        public void setContentID(String contentID) {
            this.contentID = contentID;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}