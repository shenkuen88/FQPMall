package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;
import com.fengqipu.mall.bean.shop.ShopDetailResponse;

import java.util.ArrayList;
import java.util.List;

public class IndexBannerResponse extends BaseResponse
{

    /**
     * recordID : null
     * serviceType : 0
     * title : 222
     * cover : http://njhuadai.com/skin/default/portal/images/index_02.png
     * type : 1
     * link : www.163.com
     * content : null
     * contentID : null
     * status : null
     * createTime : null
     */

    private List<BannerListBean> bannerList;
    /**
     * recordID : null
     * title : 123456789
     * type : 1
     * link : www.baidu.com
     * content : null
     * contentID : null
     * status : null
     * createTime : null
     */

    private List<NoticeListBean> noticeList;
    private List<ActivityListBean> activityList = new ArrayList<>();
    private ShopDetailResponse.ShopBean top;

    public ShopDetailResponse.ShopBean getTop() {
        return top;
    }

    public void setTop(ShopDetailResponse.ShopBean top) {
        this.top = top;
    }

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<NoticeListBean> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeListBean> noticeList) {
        this.noticeList = noticeList;
    }

    public List<ActivityListBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityListBean> activityList) {
        this.activityList = activityList;
    }

    public static class ShopBean {
        /**
         * id : 4
         * type : 1
         * shopCode : 17001
         * shopName : 南京XXX信息科技有限公司
         * notice : 456
         * picUrl : /file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG
         * advPicUrl1 : /file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG
         * advPicUrl2 : null
         * advPicUrl3 : null
         * province : 320000
         * city : 320100
         * area : 320101
         * addressDetail : 软件大道
         * gpsLong : 118.775101
         * gpsLati : 31.981865
         * category1 : null
         * category2 : 3
         * description :  <p>方法</p>
         * phone : 15052923299
         * businessLicenseFile :
         * legalPersonIDCardAFile :
         * legalPersonIDCardBFile :
         * legalPerson : 王哈哈
         * bank :
         * bankCode :
         * score : null
         * favoriteCount : 0
         * viewCount : null
         * delFlag : 0
         * status : 1
         * suspend : 0
         * maintainer : 1
         * createAdmin : 1
         * createTime : 2017-06-23
         * advPicUrl1RequestUrl : http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG
         * advPicUrl2RequestUrl :
         * advPicUrl3RequestUrl :
         * picUrlRequestUrl : http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG
         * descriptionLink : http://116.62.116.15/api/h5/detail?type=4&id=4
         * advPicUrlList : ["http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG"]
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
        private String score;
        private int favoriteCount;
        private String viewCount;
        private int delFlag;
        private int status;
        private int suspend;
        private String maintainer;
        private String createAdmin;
        private String createTime;
        private String advPicUrl1RequestUrl;
        private String advPicUrl2RequestUrl;
        private String advPicUrl3RequestUrl;
        private String picUrlRequestUrl;
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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
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

        public String getPicUrlRequestUrl() {
            return picUrlRequestUrl;
        }

        public void setPicUrlRequestUrl(String picUrlRequestUrl) {
            this.picUrlRequestUrl = picUrlRequestUrl;
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
