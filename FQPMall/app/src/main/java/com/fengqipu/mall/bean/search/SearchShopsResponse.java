package com.fengqipu.mall.bean.search;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public class SearchShopsResponse extends BaseResponse{

    /**
     * shopList : [{"id":"5","type":1,"shopCode":"17001","keyword":null,"shopName":"1测试企业AAAAAAAA","notice":null,"picUrl":"","advPicUrl1":"","advPicUrl2":"","advPicUrl3":"","province":null,"city":null,"area":null,"addressDetail":"ddd","gpsLong":"","gpsLati":"","category1":null,"category2":"3","description":" ","phone":"15251811111","businessLicenseFile":"","legalPersonIDCardAFile":"","legalPersonIDCardBFile":"","legalPerson":"1测试企业法人","bank":"","bankCode":"","score":0,"favoriteCount":0,"viewCount":0,"delFlag":0,"status":2,"suspend":0,"maintainer":"1","createAdmin":"1","createTime":"2017-06-27","picUrlRequestUrl":"","advPicUrl3RequestUrl":"","advPicUrlList":[],"advPicUrl1RequestUrl":"","advPicUrl2RequestUrl":"","descriptionLink":"http://116.62.116.15/api/h5/detailStringtype=4&id=5"},{"id":"1","type":1,"shopCode":"17001","keyword":null,"shopName":"测试企业","notice":"123","picUrl":"","advPicUrl1":"/file/shop/9fbe5f95-b24d-4f9e-8840-c9700a6a5f24.jpg","advPicUrl2":"/file/shop/dcda5cdb-c95b-4320-af08-0855d059b7df.JPG","advPicUrl3":"/file/shop/2c62abd9-c67b-4922-833e-39dcb353a432.JPG","province":"320000","city":"320100","area":"320102","addressDetail":"玄武大道","gpsLong":"","gpsLati":"","category1":null,"category2":"3,6","description":" <p>22发顺风递四方速递<\/p>","phone":"18852867726","businessLicenseFile":"","legalPersonIDCardAFile":"","legalPersonIDCardBFile":"","legalPerson":"哈哈哈","bank":"中国银行","bankCode":"11111111111111111111","score":0,"favoriteCount":0,"viewCount":0,"delFlag":0,"status":2,"suspend":0,"maintainer":"1","createAdmin":"1","createTime":"2017-06-14","picUrlRequestUrl":"","advPicUrl3RequestUrl":"http://116.62.116.15/f/file/shop/2c62abd9-c67b-4922-833e-39dcb353a432.JPG","advPicUrlList":["http://116.62.116.15/f/file/shop/9fbe5f95-b24d-4f9e-8840-c9700a6a5f24.jpg","http://116.62.116.15/f/file/shop/dcda5cdb-c95b-4320-af08-0855d059b7df.JPG","http://116.62.116.15/f/file/shop/2c62abd9-c67b-4922-833e-39dcb353a432.JPG"],"advPicUrl1RequestUrl":"http://116.62.116.15/f/file/shop/9fbe5f95-b24d-4f9e-8840-c9700a6a5f24.jpg","advPicUrl2RequestUrl":"http://116.62.116.15/f/file/shop/dcda5cdb-c95b-4320-af08-0855d059b7df.JPG","descriptionLink":"http://116.62.116.15/api/h5/detailStringtype=4&id=1"}]
     * totalCount : 2
     */

    private int totalCount;
    private List<ShopListBean> shopList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ShopListBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopListBean> shopList) {
        this.shopList = shopList;
    }

    public static class ShopListBean {
        /**
         * id : 5
         * type : 1
         * shopCode : 17001
         * keyword : null
         * shopName : 1测试企业AAAAAAAA
         * notice : null
         * picUrl : 
         * advPicUrl1 : 
         * advPicUrl2 : 
         * advPicUrl3 : 
         * province : null
         * city : null
         * area : null
         * addressDetail : ddd
         * gpsLong : 
         * gpsLati : 
         * category1 : null
         * category2 : 3
         * description :  
         * phone : 15251811111
         * businessLicenseFile : 
         * legalPersonIDCardAFile : 
         * legalPersonIDCardBFile : 
         * legalPerson : 1测试企业法人
         * bank : 
         * bankCode : 
         * score : 0.0
         * favoriteCount : 0
         * viewCount : 0
         * delFlag : 0
         * status : 2
         * suspend : 0
         * maintainer : 1
         * createAdmin : 1
         * createTime : 2017-06-27
         * picUrlRequestUrl : 
         * advPicUrl3RequestUrl : 
         * advPicUrlList : []
         * advPicUrl1RequestUrl : 
         * advPicUrl2RequestUrl : 
         * descriptionLink : http://116.62.116.15/api/h5/detailStringtype=4&id=5
         */

        private String id;
        private int type;
        private String shopCode;
        private String keyword;
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
        private double score;
        private int favoriteCount;
        private int viewCount;
        private int delFlag;
        private int status;
        private int suspend;
        private String maintainer;
        private String createAdmin;
        private String createTime;
        private String picUrlRequestUrl;
        private String advPicUrl3RequestUrl;
        private String advPicUrl1RequestUrl;
        private String advPicUrl2RequestUrl;
        private String descriptionLink;
        private List<String> advPicUrlList;

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

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
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

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
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

        public String getAdvPicUrl3RequestUrl() {
            return advPicUrl3RequestUrl;
        }

        public void setAdvPicUrl3RequestUrl(String advPicUrl3RequestUrl) {
            this.advPicUrl3RequestUrl = advPicUrl3RequestUrl;
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

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
        }

        public List<String> getAdvPicUrlList() {
            return advPicUrlList;
        }

        public void setAdvPicUrlList(List<String> advPicUrlList) {
            this.advPicUrlList = advPicUrlList;
        }
    }
}
