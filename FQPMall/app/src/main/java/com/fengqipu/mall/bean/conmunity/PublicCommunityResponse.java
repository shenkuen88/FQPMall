package com.fengqipu.mall.bean.conmunity;


import com.fengqipu.mall.bean.BaseResponse;

import java.util.List;

public class PublicCommunityResponse extends BaseResponse {


    /**
     * articleList : [{"articleID":"6","communityID":"1","communityName":"南京岔路口社区","userID":"9","userNickName":"15251813022","userPortrait":"http://221.226.118.110:18080/skin/images/avatars.png","title":"啦啦啦","content":"啦啦啦","pic1Url":null,"thumPic1Url":null,"pic2Url":null,"thumPic2Url":null,"pic3Url":null,"thumPic3Url":null,"pic4Url":null,"thumPic4Url":null,"pic5Url":null,"thumPic5Url":null,"pic6Url":null,"thumPic6Url":null,"pic7Url":null,"thumPic7Url":null,"pic8Url":null,"thumPic8Url":null,"pic9Url":null,"thumPic9Url":null,"goodList":null,"commentList":null,"createTime":"2016-08-02","createTimeStr":"20160802010958","createTimeShow":"20160802010958","thumPicUrlList":["http://imgsrc.baidu.com/forum/w%3D580/sign=83ea1a202034349b74066e8df9eb1521/15ed377adab44aed88b445debb1c8701a08bfb0c.jpg","http://imgsrc.baidu.com/forum/pic/item/01b0d788d43f8794c0f1ac14da1b0ef41ad53af7.jpg"],"picUrlList":["http://imgsrc.baidu.com/forum/w%3D580/sign=83ea1a202034349b74066e8df9eb1521/15ed377adab44aed88b445debb1c8701a08bfb0c.jpg","http://imgsrc.baidu.com/forum/pic/item/01b0d788d43f8794c0f1ac14da1b0ef41ad53af7.jpg"]}]
     * totalCount : 6
     */

    private int totalCount;
    /**
     * articleID : 6
     * communityID : 1
     * communityName : 南京岔路口社区
     * userID : 9
     * userNickName : 15251813022
     * userPortrait : http://221.226.118.110:18080/skin/images/avatars.png
     * title : 啦啦啦
     * content : 啦啦啦
     * pic1Url : null
     * thumPic1Url : null
     * pic2Url : null
     * thumPic2Url : null
     * pic3Url : null
     * thumPic3Url : null
     * pic4Url : null
     * thumPic4Url : null
     * pic5Url : null
     * thumPic5Url : null
     * pic6Url : null
     * thumPic6Url : null
     * pic7Url : null
     * thumPic7Url : null
     * pic8Url : null
     * thumPic8Url : null
     * pic9Url : null
     * thumPic9Url : null
     * goodList : null
     * commentList : null
     * createTime : 2016-08-02
     * createTimeStr : 20160802010958
     * createTimeShow : 20160802010958
     * thumPicUrlList : ["http://imgsrc.baidu.com/forum/w%3D580/sign=83ea1a202034349b74066e8df9eb1521/15ed377adab44aed88b445debb1c8701a08bfb0c.jpg","http://imgsrc.baidu.com/forum/pic/item/01b0d788d43f8794c0f1ac14da1b0ef41ad53af7.jpg"]
     * picUrlList : ["http://imgsrc.baidu.com/forum/w%3D580/sign=83ea1a202034349b74066e8df9eb1521/15ed377adab44aed88b445debb1c8701a08bfb0c.jpg","http://imgsrc.baidu.com/forum/pic/item/01b0d788d43f8794c0f1ac14da1b0ef41ad53af7.jpg"]
     */

    private List<ArticleListBean> articleList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ArticleListBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleListBean> articleList) {
        this.articleList = articleList;
    }


}
