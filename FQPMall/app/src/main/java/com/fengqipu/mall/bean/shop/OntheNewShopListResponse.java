package com.fengqipu.mall.bean.shop;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2017/6/29 0029.
 */
public class OntheNewShopListResponse extends BaseResponse{

    /**
     * contentList : [{"id":"1","contentCode":"1717001001","contentName":"智能榨汁机","keyword":"关键词","category1":"1","category2":"3","contentType":"","model":"T100","abstracts":"摘要信息","picUrl1":"/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","picUrl2":"/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg","picUrl3":"","picUrl4":"","description":"   <p>的方式的范德萨范德萨发多少发是到付<\/p>","shopID":"3","originalPrice":0,"price":1000,"sales":null,"monthSales":null,"score":null,"viewCount":null,"appraiseCount":null,"favoriteCount":null,"adminID":"1","status":1,"delFlag":0,"createTime":"2017-06-15","shopCity":"320800","shopName":"商铺1","picUrl1RequestUrl":"http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","picUrlList":["http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg"],"picUrl4RequestUrl":"","descriptionLink":"http://116.62.116.15/api/h5/detail?type=4&id=1","picUrl2RequestUrl":"http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg","picUrl3RequestUrl":"","shopProvince":"320000"},{"id":"2","contentCode":"1717001001","contentName":"透气休闲鞋","keyword":"跑步","category1":"4","category2":"5","contentType":"3","model":"1234343","abstracts":"透气休闲鞋","picUrl1":"/file/content/88fc3f0a-552a-49fe-82f4-7701f1e61b69.JPG","picUrl2":"/file/content/07f0a72b-1762-440d-b931-3140eca3ffde.JPG","picUrl3":"","picUrl4":"","description":" <p>范德萨范德萨地方的发<\/p>","shopID":"3","originalPrice":null,"price":null,"sales":null,"monthSales":null,"score":null,"viewCount":0,"appraiseCount":0,"favoriteCount":0,"adminID":"1","status":1,"delFlag":0,"createTime":"2017-06-27","shopCity":"320800","shopName":"商铺1","picUrl1RequestUrl":"http://116.62.116.15/f/file/content/88fc3f0a-552a-49fe-82f4-7701f1e61b69.JPG","picUrlList":["http://116.62.116.15/f/file/content/88fc3f0a-552a-49fe-82f4-7701f1e61b69.JPG","http://116.62.116.15/f/file/content/07f0a72b-1762-440d-b931-3140eca3ffde.JPG"],"picUrl4RequestUrl":"","descriptionLink":"http://116.62.116.15/api/h5/detail?type=4&id=2","picUrl2RequestUrl":"http://116.62.116.15/f/file/content/07f0a72b-1762-440d-b931-3140eca3ffde.JPG","picUrl3RequestUrl":"","shopProvince":"320000"},{"id":"3","contentCode":"1717001002","contentName":"老板油烟机","keyword":"","category1":"1","category2":"3","contentType":"1","model":"Y66","abstracts":"老板油烟机老板油烟机","picUrl1":"/file/content/81d2e84a-3500-4bd8-b45d-370d45292d5c.jpg","picUrl2":"","picUrl3":"","picUrl4":"","description":"  <p>方法方法<\/p>","shopID":"3","originalPrice":null,"price":null,"sales":null,"monthSales":null,"score":null,"viewCount":0,"appraiseCount":0,"favoriteCount":0,"adminID":"1","status":1,"delFlag":0,"createTime":"2017-06-27","shopCity":"320800","shopName":"商铺1","picUrl1RequestUrl":"http://116.62.116.15/f/file/content/81d2e84a-3500-4bd8-b45d-370d45292d5c.jpg","picUrlList":["http://116.62.116.15/f/file/content/81d2e84a-3500-4bd8-b45d-370d45292d5c.jpg"],"picUrl4RequestUrl":"","descriptionLink":"http://116.62.116.15/api/h5/detail?type=4&id=3","picUrl2RequestUrl":"","picUrl3RequestUrl":"","shopProvince":"320000"}]
     * totalCount : 3
     */

    private int totalCount;
    private List<ContentListBean> contentList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    public static class ContentListBean {
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
         * monthSales : null
         * score : null
         * viewCount : null
         * appraiseCount : null
         * favoriteCount : null
         * adminID : 1
         * status : 1
         * delFlag : 0
         * createTime : 2017-06-15
         * shopCity : 320800
         * shopName : 商铺1
         * picUrl1RequestUrl : http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg
         * picUrlList : ["http://116.62.116.15/f/file/content/a255302f-a9a7-4949-969c-d29dae05a605.jpg","http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg"]
         * picUrl4RequestUrl : 
         * descriptionLink : http://116.62.116.15/api/h5/detail?type=4&id=1
         * picUrl2RequestUrl : http://116.62.116.15/f/file/content/9c1f6391-7965-400d-bbc9-a6c6d9666704.jpg
         * picUrl3RequestUrl : 
         * shopProvince : 320000
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
        private String monthSales;
        private String score;
        private String viewCount;
        private String appraiseCount;
        private String favoriteCount;
        private String adminID;
        private int status;
        private int delFlag;
        private String createTime;
        private String shopCity;
        private String shopName;
        private String picUrl1RequestUrl;
        private String picUrl4RequestUrl;
        private String descriptionLink;
        private String picUrl2RequestUrl;
        private String picUrl3RequestUrl;
        private String shopProvince;
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

        public String getShopCity() {
            return shopCity;
        }

        public void setShopCity(String shopCity) {
            this.shopCity = shopCity;
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

        public String getShopProvince() {
            return shopProvince;
        }

        public void setShopProvince(String shopProvince) {
            this.shopProvince = shopProvince;
        }

        public List<String> getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(List<String> picUrlList) {
            this.picUrlList = picUrlList;
        }
    }
}
