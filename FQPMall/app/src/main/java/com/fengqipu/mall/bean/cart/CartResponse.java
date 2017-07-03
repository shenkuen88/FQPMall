package com.fengqipu.mall.bean.cart;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CartResponse extends BaseResponse {

    /**
     * cartRecordList : [{"createTime":"2016-07-23","userID":"9","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","price":null,"style":"","count":1,"shopID":"1","objectName":"内容1","shopName":"111","recordID":"3","contentID":"1","size":""},{"createTime":"2016-07-22","userID":"9","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","price":null,"style":"","count":1,"shopID":"1","objectName":"内容1","shopName":"111","recordID":"2","contentID":"1","size":""},{"createTime":"2016-07-22","userID":"9","picUrl":"http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg","price":null,"style":"","count":1,"shopID":"1","objectName":"内容1","shopName":"111","recordID":"1","contentID":"1","size":""}]
     * totalCount : 3
     */

    private int totalCount;
    /**
     * createTime : 2016-07-23
     * userID : 9
     * picUrl : http://221.226.118.110:18080/skin/upload/content/5fbd985d-64a5-4c7c-babf-32a0db15b91e.jpg
     * price : null
     * style :
     * count : 1
     * shopID : 1
     * objectName : 内容1
     * shopName : 111
     * recordID : 3
     * contentID : 1
     * size :
     */

    private List<CartRecordListBean> cartRecordList;
    private Map<String,Shop> shopMap;
    private Map<String,List<CartRecord>> cartRecordMap;

    public Map<String, Shop> getShopMap() {
        return shopMap;
    }

    public void setShopMap(Map<String, Shop> shopMap) {
        this.shopMap = shopMap;
    }

    public Map<String, List<CartRecord>> getCartRecordMap() {
        return cartRecordMap;
    }

    public void setCartRecordMap(Map<String, List<CartRecord>> cartRecordMap) {
        this.cartRecordMap = cartRecordMap;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CartRecordListBean> getCartRecordList() {
        return cartRecordList;
    }

    public void setCartRecordList(List<CartRecordListBean> cartRecordList) {
        this.cartRecordList = cartRecordList;
    }

    public static class CartRecordListBean {


        /**
         * id : 3
         * shopID : 3
         * userID : 1
         * contentID : 1
         * contentName : 智能榨汁机
         * picUrl : /file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         * count : 1
         * style : null
         * color : null
         * price : null
         * createTime : 2017-07-03
         * picUrlRequestUrl : http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         * shopName : 商铺1
         */

        private String id;
        private String shopID;
        private String userID;
        private String contentID;
        private String contentName;
        private String picUrl;
        private int count;
        private String style;
        private String color;
        private String price;
        private String createTime;
        private String picUrlRequestUrl;
        private String shopName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getContentID() {
            return contentID;
        }

        public void setContentID(String contentID) {
            this.contentID = contentID;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPicUrlRequestUrl() {
            return picUrlRequestUrl;
        }

        public void setPicUrlRequestUrl(String picUrlRequestUrl) {
            this.picUrlRequestUrl = picUrlRequestUrl;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }

    public static class CartRecord {

        /**
         * id : 3
         * shopID : 3
         * userID : 1
         * contentID : 1
         * contentName : 智能榨汁机
         * picUrl : /file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         * count : 1
         * style : null
         * color : null
         * price : null
         * createTime : 2017-07-03
         * picUrlRequestUrl : http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         * shopName : 商铺1
         */

        private String id;
        private String shopID;
        private String userID;
        private String contentID;
        private String contentName;
        private String picUrl;
        private int count;
        private String style;
        private String color;
        private String price;
        private String createTime;
        private String picUrlRequestUrl;
        private String shopName;

        public CartRecord() {
        }

        public CartRecord(String id, String shopID, String userID, String contentID, String contentName, String picUrl, int count, String style, String color, String price, String createTime, String picUrlRequestUrl, String shopName) {
            this.id = id;
            this.shopID = shopID;
            this.userID = userID;
            this.contentID = contentID;
            this.contentName = contentName;
            this.picUrl = picUrl;
            this.count = count;
            this.style = style;
            this.color = color;
            this.price = price;
            this.createTime = createTime;
            this.picUrlRequestUrl = picUrlRequestUrl;
            this.shopName = shopName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getContentID() {
            return contentID;
        }

        public void setContentID(String contentID) {
            this.contentID = contentID;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPicUrlRequestUrl() {
            return picUrlRequestUrl;
        }

        public void setPicUrlRequestUrl(String picUrlRequestUrl) {
            this.picUrlRequestUrl = picUrlRequestUrl;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
    public static class Shop {

        /**
         * id : 3
         * type : 2
         * shopCode : 17001
         * shopName : 商铺1
         * notice : null
         * picUrl :
         * advPicUrl1 : null
         * advPicUrl2 : null
         * advPicUrl3 : null
         * province : 320000
         * city : 320800
         * area : 320802
         * addressDetail : 苏果超市
         * gpsLong : 119.063206
         * gpsLati : 33.596983
         * category1 : null
         * category2 : 5
         * description :  <p>发</p>
         * phone : 15052923299
         * businessLicenseFile :
         * legalPersonIDCardAFile :
         * legalPersonIDCardBFile :
         * legalPerson : 法人1
         * bank :
         * bankCode :
         * score : 0
         * favoriteCount : 0
         * viewCount : 0
         * delFlag : 0
         * status : 2
         * suspend : 0
         * maintainer : 1
         * createAdmin : 1
         * createTime : 2017-06-15
         * picUrlRequestUrl :
         * advPicUrl1RequestUrl :
         * advPicUrl2RequestUrl :
         * advPicUrl3RequestUrl :
         * advPicUrlList : []
         * descriptionLink : http://116.62.116.15/api/h5/detail?type=4&id=3
         */

        private String id;
        private int type;
        private String shopCode;
        private String shopName;
        private String notice;
        private String picUrl;
        private String advPicUrl1;
        private String advPicUrl2;
        private String advPicUrl3;
        private String province;
        private String city;
        private String area;
        private String addressDetail;
        private String gpsLong;
        private String gpsLati;
        private String category1;
        private String category2;
        private String description;
        private String phone;
        private String businessLicenseFile;
        private String legalPersonIDCardAFile;
        private String legalPersonIDCardBFile;
        private String legalPerson;
        private String bank;
        private String bankCode;
        private int score;
        private int favoriteCount;
        private int viewCount;
        private int delFlag;
        private int status;
        private int suspend;
        private String maintainer;
        private String createAdmin;
        private String createTime;
        private String picUrlRequestUrl;
        private String advPicUrl1RequestUrl;
        private String advPicUrl2RequestUrl;
        private String advPicUrl3RequestUrl;
        private String descriptionLink;
        private List<?> advPicUrlList;

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

        public String getShopCode() {
            return shopCode;
        }

        public void setShopCode(String shopCode) {
            this.shopCode = shopCode;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getAdvPicUrl1() {
            return advPicUrl1;
        }

        public void setAdvPicUrl1(String advPicUrl1) {
            this.advPicUrl1 = advPicUrl1;
        }

        public String getAdvPicUrl2() {
            return advPicUrl2;
        }

        public void setAdvPicUrl2(String advPicUrl2) {
            this.advPicUrl2 = advPicUrl2;
        }

        public String getAdvPicUrl3() {
            return advPicUrl3;
        }

        public void setAdvPicUrl3(String advPicUrl3) {
            this.advPicUrl3 = advPicUrl3;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getGpsLong() {
            return gpsLong;
        }

        public void setGpsLong(String gpsLong) {
            this.gpsLong = gpsLong;
        }

        public String getGpsLati() {
            return gpsLati;
        }

        public void setGpsLati(String gpsLati) {
            this.gpsLati = gpsLati;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBusinessLicenseFile() {
            return businessLicenseFile;
        }

        public void setBusinessLicenseFile(String businessLicenseFile) {
            this.businessLicenseFile = businessLicenseFile;
        }

        public String getLegalPersonIDCardAFile() {
            return legalPersonIDCardAFile;
        }

        public void setLegalPersonIDCardAFile(String legalPersonIDCardAFile) {
            this.legalPersonIDCardAFile = legalPersonIDCardAFile;
        }

        public String getLegalPersonIDCardBFile() {
            return legalPersonIDCardBFile;
        }

        public void setLegalPersonIDCardBFile(String legalPersonIDCardBFile) {
            this.legalPersonIDCardBFile = legalPersonIDCardBFile;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
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

        public int getSuspend() {
            return suspend;
        }

        public void setSuspend(int suspend) {
            this.suspend = suspend;
        }

        public String getMaintainer() {
            return maintainer;
        }

        public void setMaintainer(String maintainer) {
            this.maintainer = maintainer;
        }

        public String getCreateAdmin() {
            return createAdmin;
        }

        public void setCreateAdmin(String createAdmin) {
            this.createAdmin = createAdmin;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPicUrlRequestUrl() {
            return picUrlRequestUrl;
        }

        public void setPicUrlRequestUrl(String picUrlRequestUrl) {
            this.picUrlRequestUrl = picUrlRequestUrl;
        }

        public String getAdvPicUrl1RequestUrl() {
            return advPicUrl1RequestUrl;
        }

        public void setAdvPicUrl1RequestUrl(String advPicUrl1RequestUrl) {
            this.advPicUrl1RequestUrl = advPicUrl1RequestUrl;
        }

        public String getAdvPicUrl2RequestUrl() {
            return advPicUrl2RequestUrl;
        }

        public void setAdvPicUrl2RequestUrl(String advPicUrl2RequestUrl) {
            this.advPicUrl2RequestUrl = advPicUrl2RequestUrl;
        }

        public String getAdvPicUrl3RequestUrl() {
            return advPicUrl3RequestUrl;
        }

        public void setAdvPicUrl3RequestUrl(String advPicUrl3RequestUrl) {
            this.advPicUrl3RequestUrl = advPicUrl3RequestUrl;
        }

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
        }

        public List<?> getAdvPicUrlList() {
            return advPicUrlList;
        }

        public void setAdvPicUrlList(List<?> advPicUrlList) {
            this.advPicUrlList = advPicUrlList;
        }
    }
}
