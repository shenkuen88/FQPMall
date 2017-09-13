package com.fengqipu.mall.bean.mine;

import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

/**
 * Created by jwei on 2016/7/23 0023.
 */
public class OrderDetailResponse extends BaseResponse{

    /**
     * orderID : 31
     * orderCode : 1608040006
     * shopID : 1
     * userID : 9
     * payType : 2
     * outerCode : 2016080421001004580201863729
     * originalPrice : null
     * realPrice : 0
     * freight : null
     * orderContentList : null
     * status : 3
     * refundStatus : null
     * delFlag : 0
     * payTime : 2016-08-04
     * deliveryTime : 2016-08-04
     * signTime : null
     * createTime : 2016-08-04
     * shopName : 中巢
     * createTimeShow : 20160804010243
     * createTimeStr : 20160804010243
     * deliveryTimeShow : 2016-08-04 13:51:53
     * endTimeShow : 10天0小时0分钟
     * payTimeShow : 2016-08-04 12:26:30
     */

    private OrderBean order;

    private ShopBean shop;

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    /**
     * recordID : 31
     * orderID : 31
     * deliveryAddress : 收货地址：江苏省南京市浦口区1469442172617
     * deliveryUser : 收货人：修改后的
     * phone : 手机号码：15211111111
     * freight : 0
     * expressCode : yuantong
     * deliveryNum : 882430616691113340
     * status : 2
     * createTime : 2016-08-04
     * expressName :
     */

    private DeliveryBean delivery;
    /**
     * order : {"orderID":"31","orderCode":"1608040006","shopID":"1","userID":"9","payType":2,"outerCode":"2016080421001004580201863729","originalPrice":null,"realPrice":0,"freight":null,"orderContentList":null,"status":3,"refundStatus":null,"delFlag":0,"payTime":"2016-08-04","deliveryTime":"2016-08-04","signTime":null,"createTime":"2016-08-04","shopName":"中巢","createTimeShow":"20160804010243","createTimeStr":"20160804010243","deliveryTimeShow":"2016-08-04 13:51:53","endTimeShow":"10天0小时0分钟","payTimeShow":"2016-08-04 12:26:30"}
     * delivery : {"recordID":"31","orderID":"31","deliveryAddress":"收货地址：江苏省南京市浦口区1469442172617","deliveryUser":"收货人：修改后的","phone":"手机号码：15211111111","freight":0,"expressCode":"yuantong","deliveryNum":"882430616691113340","status":2,"createTime":"2016-08-04","expressName":""}
     * deliveryRecordList : null
     */

    private List<DeliveryRecordListBean> deliveryRecordList;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public DeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryBean delivery) {
        this.delivery = delivery;
    }

    public List<DeliveryRecordListBean> getDeliveryRecordList() {
        return deliveryRecordList;
    }

    public void setDeliveryRecordList(List<DeliveryRecordListBean> deliveryRecordList) {
        this.deliveryRecordList = deliveryRecordList;
    }

    public static class DeliveryRecordListBean {
        private String context;
        private String time;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    public static class OrderBean {


        /**
         * id : 6
         * orderCode : 17070514992228942280001
         * shopID : 3
         * userID : 3
         * userName : null
         * userPortrait : null
         * payType : 2
         * outerCode : null
         * originalPrice : null
         * realPrice : 4000
         * freight : null
         * orderContentList : [{"id":"4454","orderID":"6","contentID":"1","contentName":"智能榨汁机","picUrl":"/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg","count":2,"style":"高效版","color":null,"realPrice":1500,"createTime":"2017-07-05","picUrlRequestUrl":"http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg"},{"id":"4455","orderID":"6","contentID":"1","contentName":"智能榨汁机","picUrl":"/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg","count":1,"style":"普通版","color":"黄色","realPrice":1000,"createTime":"2017-07-05","picUrlRequestUrl":"http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg"}]
         * status : 1
         * refundStatus : null
         * delFlag : 0
         * payTime : null
         * deliveryTime : null
         * signTime : null
         * createTime : 2017-07-05
         * payTimeShow : 
         * deliveryTimeShow : 
         * endTimeShow : 2天0小时0分钟
         * createTimeStr : 20170705104814
         * shopName : 商铺1
         * createTimeShow : 20170705104814
         */

        private String id;
        private String orderCode;
        private String shopID;
        private String userID;
        private String userName;
        private String userPortrait;
        private int payType;
        private String outerCode;
        private String originalPrice;
        private double realPrice;
        private String freight;
        private int status;
        private String refundStatus;
        private int delFlag;
        private String payTime;
        private String deliveryTime;
        private String signTime;
        private String createTime;
        private String payTimeShow;
        private String deliveryTimeShow;
        private String endTimeShow;
        private String createTimeStr;
        private String shopName;
        private String createTimeShow;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        /**
         * id : 4454
         * orderID : 6
         * contentID : 1
         * contentName : 智能榨汁机
         * picUrl : /file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         * count : 2
         * style : 高效版
         * color : null
         * realPrice : 1500
         * createTime : 2017-07-05
         * picUrlRequestUrl : http://116.62.116.15/f/file/content/78fd39f1-2bbb-47a0-8798-4375cca855db.jpg
         */

        private List<OrderContentListBean> orderContentList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPortrait() {
            return userPortrait;
        }

        public void setUserPortrait(String userPortrait) {
            this.userPortrait = userPortrait;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getOuterCode() {
            return outerCode;
        }

        public void setOuterCode(String outerCode) {
            this.outerCode = outerCode;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(double realPrice) {
            this.realPrice = realPrice;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(String refundStatus) {
            this.refundStatus = refundStatus;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPayTimeShow() {
            return payTimeShow;
        }

        public void setPayTimeShow(String payTimeShow) {
            this.payTimeShow = payTimeShow;
        }

        public String getDeliveryTimeShow() {
            return deliveryTimeShow;
        }

        public void setDeliveryTimeShow(String deliveryTimeShow) {
            this.deliveryTimeShow = deliveryTimeShow;
        }

        public String getEndTimeShow() {
            return endTimeShow;
        }

        public void setEndTimeShow(String endTimeShow) {
            this.endTimeShow = endTimeShow;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getCreateTimeShow() {
            return createTimeShow;
        }

        public void setCreateTimeShow(String createTimeShow) {
            this.createTimeShow = createTimeShow;
        }

        public List<OrderContentListBean> getOrderContentList() {
            return orderContentList;
        }

        public void setOrderContentList(List<OrderContentListBean> orderContentList) {
            this.orderContentList = orderContentList;
        }

        public static class OrderContentListBean {
            private String id;
            private String orderID;
            private String contentID;
            private String contentName;
            private String picUrl;
            private int count;
            private String style;
            private String color;
            private double realPrice;
            private String createTime;
            private String picUrlRequestUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderID() {
                return orderID;
            }

            public void setOrderID(String orderID) {
                this.orderID = orderID;
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

            public double getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(double realPrice) {
                this.realPrice = realPrice;
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

    public static class DeliveryBean {
        private String recordID;
        private String orderID;
        private String deliveryAddress;
        private String deliveryUser;
        private String phone;
        private int freight;
        private String expressCode;
        private String deliveryNum;
        private int status;
        private String createTime;
        private String expressName;

        public String getRecordID() {
            return recordID;
        }

        public void setRecordID(String recordID) {
            this.recordID = recordID;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getDeliveryUser() {
            return deliveryUser;
        }

        public void setDeliveryUser(String deliveryUser) {
            this.deliveryUser = deliveryUser;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getFreight() {
            return freight;
        }

        public void setFreight(int freight) {
            this.freight = freight;
        }

        public String getExpressCode() {
            return expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public String getDeliveryNum() {
            return deliveryNum;
        }

        public void setDeliveryNum(String deliveryNum) {
            this.deliveryNum = deliveryNum;
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

        public String getExpressName() {
            return expressName;
        }

        public void setExpressName(String expressName) {
            this.expressName = expressName;
        }
    }

    public class ShopBean {

        /**
         * id : 3
         * type : 2
         * shopCode : 17001
         * keyword : null
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
         * userID : null
         * createAdmin : 1
         * createTime : 2017-06-15
         * picUrlRequestUrl :
         * descriptionLink : http://116.62.116.15/api/h5/detail?type=4&id=3
         * advPicUrl2RequestUrl :
         * advPicUrlList : []
         * advPicUrl3RequestUrl :
         * advPicUrl1RequestUrl :
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
        private int score;
        private int favoriteCount;
        private int viewCount;
        private int delFlag;
        private int status;
        private int suspend;
        private String maintainer;
        private String userID;
        private String createAdmin;
        private String createTime;
        private String picUrlRequestUrl;
        private String descriptionLink;
        private String advPicUrl2RequestUrl;
        private String advPicUrl3RequestUrl;
        private String advPicUrl1RequestUrl;
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

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
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

        public String getDescriptionLink() {
            return descriptionLink;
        }

        public void setDescriptionLink(String descriptionLink) {
            this.descriptionLink = descriptionLink;
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

        public String getAdvPicUrl1RequestUrl() {
            return advPicUrl1RequestUrl;
        }

        public void setAdvPicUrl1RequestUrl(String advPicUrl1RequestUrl) {
            this.advPicUrl1RequestUrl = advPicUrl1RequestUrl;
        }

        public List<String> getAdvPicUrlList() {
            return advPicUrlList;
        }

        public void setAdvPicUrlList(List<String> advPicUrlList) {
            this.advPicUrlList = advPicUrlList;
        }
    }
}
