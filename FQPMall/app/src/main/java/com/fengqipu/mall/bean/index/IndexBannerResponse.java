package com.fengqipu.mall.bean.index;


import com.fengqipu.mall.bean.BaseResponse;

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
}
