package com.fengqipu.mall.bean.category;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by jwei on 2016/7/15 0015.
 */
public class CategoryResponse extends BaseResponse {

    /**
     * 一级分类列表，用于左侧展示，右上的广告图取一级分类里的，广告图可选
     */
    private List<Category> parentCategoryList;

    /**
     * 二级分类列表，用于右侧展示，根据选中的一级获取到对应的二级列表
     */
    private Map<String, List<Category>> subCategoryListMap;

    private Map<String, List<ContentType>> contentTypeListMap;

    private Map<String, List<Shop>> shopListMap;

    public List<Category> getParentCategoryList() {
        return parentCategoryList;
    }

    public void setParentCategoryList(List<Category> parentCategoryList) {
        this.parentCategoryList = parentCategoryList;
    }

    public Map<String, List<Category>> getSubCategoryListMap() {
        return subCategoryListMap;
    }

    public void setSubCategoryListMap(Map<String, List<Category>> subCategoryListMap) {
        this.subCategoryListMap = subCategoryListMap;
    }

    public Map<String, List<ContentType>> getContentTypeListMap() {
        return contentTypeListMap;
    }

    public void setContentTypeListMap(Map<String, List<ContentType>> contentTypeListMap) {
        this.contentTypeListMap = contentTypeListMap;
    }

    public Map<String, List<Shop>> getShopListMap() {
        return shopListMap;
    }

    public void setShopListMap(Map<String, List<Shop>> shopListMap) {
        this.shopListMap = shopListMap;
    }

    public class Shop {

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
         * advPicUrlList : ["http://116.62.116.15/f/file/shop/946fe26b-cd27-4f60-9515-8b938ed9300f.JPG"]
         */

        private String id;
        private int type;
        private String shopCode;
        private String shopName;
        private String notice;
        private String picUrl;
        private String advPicUrl1;
        private Object advPicUrl2;
        private Object advPicUrl3;
        private String province;
        private String city;
        private String area;
        private String addressDetail;
        private String gpsLong;
        private String gpsLati;
        private Object category1;
        private String category2;
        private String description;
        private String phone;
        private String businessLicenseFile;
        private String legalPersonIDCardAFile;
        private String legalPersonIDCardBFile;
        private String legalPerson;
        private String bank;
        private String bankCode;
        private Object score;
        private Object viewCount;
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

        public Object getAdvPicUrl2() {
            return advPicUrl2;
        }

        public void setAdvPicUrl2(Object advPicUrl2) {
            this.advPicUrl2 = advPicUrl2;
        }

        public Object getAdvPicUrl3() {
            return advPicUrl3;
        }

        public void setAdvPicUrl3(Object advPicUrl3) {
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

        public Object getCategory1() {
            return category1;
        }

        public void setCategory1(Object category1) {
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

        public Object getScore() {
            return score;
        }

        public void setScore(Object score) {
            this.score = score;
        }

        public Object getViewCount() {
            return viewCount;
        }

        public void setViewCount(Object viewCount) {
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

        public List<String> getAdvPicUrlList() {
            return advPicUrlList;
        }

        public void setAdvPicUrlList(List<String> advPicUrlList) {
            this.advPicUrlList = advPicUrlList;
        }
    }

    public class Category {

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

    public class ContentType {

        /**
         * id : 1
         * category1 : 1
         * category2 : 3
         * typeName : 油烟机
         * picUrl : /file/contentType/71895d44-17bc-41f9-a89e-8be96ecb4671.jpg
         * createTime : 2017-06-27
         * picUrlRequestUrl : http://116.62.116.15/f/file/contentType/71895d44-17bc-41f9-a89e-8be96ecb4671.jpg
         */

        private String id;
        private String category1;
        private String category2;
        private String typeName;
        private String picUrl;
        private String createTime;
        private String picUrlRequestUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
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
    }
}
