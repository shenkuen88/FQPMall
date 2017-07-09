package com.fengqipu.mall.network;

import android.content.Context;

import com.fengqipu.mall.bean.cart.CartResponse;
import com.fengqipu.mall.bean.index.OrderContent;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.URLUtil;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.StringEncrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关的接口实现类
 */
public class UserServiceImpl
{
    private UserServiceImpl()
    {
    }

    private static Context mContext;


    private static class UserServiceImplServiceHolder
    {
        private static UserServiceImpl userServiceImplSingleton = new UserServiceImpl();
    }

    public static UserServiceImpl instance()
    {
        return UserServiceImplServiceHolder.userServiceImplSingleton;
    }

    /**
     * 应用初始化
     */
    public void initAPP(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("lastUpdateTime", SharePref.getString(Constants.lastUpdateTime, ""));
        param.put("gpsLong", Global.getlangitude());
        param.put("gpsLati", Global.getlatitude());
        new NetWork().startPost(URLUtil.INIT_APP, param, tag);
    }
    public void addFavour(Context mContext,String type, String sid, String tag) {
        //商品 1 企业 商铺 2
        Map<String, String> param = new HashMap<String, String>();
        param.put("objectType",type);
        param.put("objectID",sid);
        new NetWork().startPost(URLUtil.AddFavour, param, tag);
    }
    /**
     * 获取Banner
     * 业务类型0-首页  1-在线教育  2-母婴儿童   3装修
     */
    public void getBannerList(Context mContext, String serviceType, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("position", serviceType);
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_BANNER_LIST, param, tag);
    }
    public void getBannerList(Context mContext, String serviceType,String sid, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("position", serviceType);
        if(serviceType.equals("2")){//企业
            param.put("shopID", sid);
        }else{//分类
            param.put("categoryID", sid);
        }
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_BANNER_LIST, param, tag);
    }
    public void getShopPromote(Context mContext, int pageNum ,int pageSize, String tag){
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageNum", pageNum+"");
        param.put("pageSize", pageSize+"");
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_SHOPS_PROMOTE_LIST, param, tag);
    }

    /**
     * 查询栏目列表
     * 0-首页  1-在线教育  2-母婴儿童   3装修
     */
    public void getColumnList(Context mContext, String serviceType, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("position", serviceType);
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_COLUMN_LIST, param, tag);
    }

    public void getHuoDongList(Context mContext,String id,int pageNum,int pageSize, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("activityID", id);
        param.put("pageNum",pageNum+"");
        param.put("pageSize",pageSize+"");
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_HUODONG_LIST, param, tag);
    }
    //查询企业详情
    public void getShopDetail(Context mContext,String id, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("shopID", id);
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_SHOP_DETAIL, param, tag);
    }
    //查询企业详情
    public void getTuiJianList(Context mContext,String position, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("position", position);
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_TUIJIAN_LIST, param, tag);
    }
    //查询我的足迹收藏数量
    public void getUserCount(Context mContext, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_USER_COUNT, param, tag);
    }
    /**
     * 查询内容详情
     */
    public void getContentDetail(String contentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        new NetWork()
                .startPost(URLUtil.GET_CONTENT_DETAIL, param, tag);
    }

    /**
     * 查询内容详情
     */
    public void feedback(String content, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("content", content);
        new NetWork()
                .startPost(URLUtil.FEEDBACK, param, tag);
    }

    /**
     * 查询商品详情评价列表
     */
    public void getProductCommentList(String contentID, int pageNo,int pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("objectType","0");
        param.put("contentID", contentID);
        param.put("pageSize", pageSize + "");
        param.put("pageNo", pageNo + "");
        new NetWork()
                .startPost(URLUtil.GET_COMMENT_LIST, param, tag);
    }

    /**
     * 查询内容评价列表
     */
    public void getProductCommentList(String contentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        param.put("pageSize", 1 + "");
        param.put("pageNo", 1 + "");
        new NetWork()
                .startPost(URLUtil.GET_COMMENT_LIST, param, tag);
    }

    /**
     * 添加商品的内容评价 母婴 装修  视频
     */
    public void addProductComment(String orderID, String anonymous, String score,
                                  String text, String picUrl1, String picUrl2, String picUrl3, String picUrl4, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", orderID);
        param.put("anonymous", anonymous);
        param.put("score", score);
        param.put("text", text);
        param.put("text", text);
        param.put("picUrl1", picUrl1);
        param.put("picUrl2", picUrl2);
        param.put("picUrl3", picUrl3);
        param.put("picUrl4", picUrl4);
        new NetWork()
                .startPost(URLUtil.ADD_PRODUCT_COMMENT, param, tag);
    }

    /**
     * 添加商品的内容评价 母婴 装修  视频
     */
    public void addProductComment(String contentID, String text, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", "");
        param.put("contentID", contentID);
        param.put("score", "");
        param.put("text", text);
        param.put("picUrl1", "");
        param.put("picUrl2", "");
        param.put("picUrl3", "");
        param.put("picUrl4", "");
        new NetWork()
                .startPost(URLUtil.ADD_PRODUCT_COMMENT, param, tag);
    }

    /**
     * 1.1.2 查询系统消息
     */
    public void getSystemMessage(int pageNo, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageSize", Constants.LIST_NUM + "");
        param.put("pageNo", pageNo + "");
        new NetWork()
                .startPost(URLUtil.SYSTEM_MESSAGE, param, tag);
    }

    /**
     * 消息通知
     */
    public void getNoticeList(int pageNo, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
//        param.put("pageSize", Constants.LIST_NUM + "");
        param.put("pageNo", pageNo + "");
        new NetWork()
                .startPost(URLUtil.NOTICE_LIST, param, tag);
    }

    /**
     * 查询热搜词
     */
    public void getHotWords(Context mContext, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_KEY_WORDS_LIST, param, tag);
    }

    /**
     * 搜索内容
     */
    public void search(String serviceType, String categoryID, String subCategoryID,
                       String keyword, String order, String brandID, int pageNo, int pageSize, String sort, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("serviceType", serviceType);
        param.put("categoryID", categoryID);
        param.put("subCategoryID", subCategoryID);
        param.put("keyword", keyword);
        param.put("brandID", brandID);
        param.put("pageNo", pageNo + "");
        param.put("pageSize", pageSize + "");
        param.put("order", order);//1 升序 2 降序
        param.put("sort", sort);//1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
        new NetWork().
                startPost(URLUtil.SEARCH_KEYWORD, param, tag);
    }
    public void getGoodsDetial(Context context, String gid, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", gid);
        new NetWork().
                startPost(URLUtil.GET_GOODS_DETAIL, param, tag);
    }

    /**
     * 搜索商家接口
     */
    public void getShopsList(Context context,String shopID,String queryType,String order,String sort, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param=new HashMap<String, String>();
        param.put("shopID",shopID);
        param.put("queryType",queryType);
        param.put("pageNo",pageNo+"");
        param.put("pageSize",pageSize+"");
        if(!order.equals("")){
            param.put("order",order+"");
            if(order.equals("4")){
                param.put("sort",sort+"");
            }
        }
        new NetWork().
                startPost(URLUtil.SEARCH_KEYWORD, param, tag);
    }
    /**
     * 搜索企业商品
     */
    public void getShopsGList(Context context,String contentType,String category2,String model,String order,String sort, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param=new HashMap<String, String>();
        param.put("contentType",contentType);
        param.put("category2",category2);
        param.put("model",model);
        param.put("pageNo",pageNo+"");
        param.put("pageSize",pageSize+"");
        if(!order.equals("")){
            param.put("order",order+"");
            if(order.equals("4")){
                param.put("sort",sort+"");
            }
        }
        new NetWork().
                startPost(URLUtil.SEARCH_KEYWORD, param, tag);
    }

    /**
     * 搜索企业商品
     */
    public void getSearchGList(Context context,String contentType,String keyword,String order,String sort, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param=new HashMap<String, String>();
        param.put("contentType",contentType);
        param.put("keyword",keyword);
        param.put("pageNo",pageNo+"");
        param.put("pageSize",pageSize+"");
        if(!order.equals("")){
            param.put("order",order+"");
            if(order.equals("4")){
                if(!sort.equals("")) {
                    param.put("sort", sort + "");
                }
            }
        }
        new NetWork().
                startPost(URLUtil.SEARCH_KEYWORD, param, tag);
    }

    /**
     * 上传图片
     */
    public void uploadPic(List<File> files, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        Map<String, List<File>> fileparams = new HashMap<String, List<File>>();
        fileparams.put("file", files);
//        fileparams.put("file", files);
        new NetWork().startPost(URLUtil.UPLOAD_PIC, param, fileparams, tag);
    }

    /**
     * 搜索内容 首页头部的搜索
     */
    public void search(
            String keyword, int sort, int pageNo, String brandID, String order, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("serviceType", "");//业务类型
        param.put("categoryID", "");
        param.put("subCategoryID", "");//二级分类ID
        param.put("pageSize", Constants.LIST_NUM + "");
        param.put("keyword", keyword);
        param.put("sort", sort + "");//1-综合排序 2-评价时间 3-上架时间 4-销量 5价格
        param.put("order", order);//1 升序 2 降序
        param.put("pageNo", pageNo + "");
        param.put("brandID", brandID);//品牌ID
        new NetWork().
                startPost(URLUtil.SEARCH_KEYWORD, param, tag);
    }

    /**
     * 查询栏目列表内容
     */
    public void getBrandList(String keyword, String subcategoryID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("keyword", keyword);
        param.put("subCategoryID", subcategoryID);
        new NetWork()
                .startPost(URLUtil.GET_BRAND_LIST, param, tag);
    }

    /**
     * 查询栏目列表内容
     */
    public void getColumnListContent(Context mContext, String columnID, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("columnID", columnID);
        param.put("pageNo", pageNo + "");
        param.put("pageSize", pageSize + "");
        new NetWork().needCache(mContext)
                .startPost(URLUtil.GET_COLUMN_LIST_CONTENT, param, tag);
    }

    /**
     * 发送验证码
     */
    public void getYZMCode(String type, String phone, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("type", type);
        param.put("phone", phone);
        new NetWork().startPost(URLUtil.GET_YZM_CODE, param, tag);
    }

    /**
     * 校验验证码
     */
    public void checkYZMCode(String type, String phone, String smsCode, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("type", type);
        param.put("phone", phone);
        param.put("smsCode", smsCode);
        new NetWork().startPost(URLUtil.CHECK_YZM_CODE, param, tag);
    }

    /**
     * 注册
     */
    public void register(String userName, String password, String smsCode, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);
        param.put("password", StringEncrypt.Encrypt(password));
        param.put("smsCode", smsCode);
        new NetWork().startPost(URLUtil.REGISTER_NEXT, param, tag);
    }

    /**
     * 修改密码
     */
    public void editPassword(String type, String userName, String oldPassword, String newPassword, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("type", type);
        param.put("userName", userName);
        if (!type.equals("2"))
        {
            param.put("oldPassword", StringEncrypt.Encrypt(oldPassword));
        }
        param.put("newPassword", newPassword);
        new NetWork().startPost(URLUtil.EDIT_PASSWORD, param, tag);
    }

    /**
     * 获取收货地址列表
     */
    public void getReceiveAddressList(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork().startPost(URLUtil.GET_RECEIVE_ADDRESS_ADDRESS, param, tag);
    }

    /**
     * 添加收货地址
     */
    public void addReceiveAddress(String province, String city, String area, String detail, String deliveryUser,
                                  String phone, String isDefault, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("province", province);
        param.put("city", city);
        param.put("area", area);
        param.put("detail", detail);
        param.put("deliveryUser", deliveryUser);
        param.put("phone", phone);
        param.put("isDefault", isDefault);
        new NetWork().startPost(URLUtil.ADD_RECEIVE_ADDRESS, param, tag);
    }

    /**
     * 修改收货地址
     */
    public void editReceiveAddress(String recordID, String province, String city, String area, String detail, String deliveryUser,
                                   String phone, String isDefault, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("recordID", recordID);
        param.put("province", province);
        param.put("city", city);
        param.put("area", area);
        param.put("detail", detail);
        param.put("deliveryUser", deliveryUser);
        param.put("phone", phone);
        param.put("isDefault", isDefault);
        new NetWork().startPost(URLUtil.EDIT_RECEIVE_ADDRESS, param, tag);
    }

    /**
     * 删除收货地址
     */
    public void deleteAddress(String recordID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("recordID", recordID);
        new NetWork().startPost(URLUtil.DELETE_ADDRESS, param, tag);
    }


    /**
     * 查询优惠券
     */
    public void searchCoupon(String status, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("status", status);
        param.put("pageNo", pageNo + "");
        param.put("pageSize", pageSize + "");
        new NetWork().startPost(URLUtil.SEARCH_COUPON, param, tag);
    }

    /**
     * 登录
     */
    public void login(String userName, String password, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);
        param.put("password", password);

        new NetWork().startPost(URLUtil.USER_LOGIN, param, tag);
    }

    /**
     * 点赞
     */
    public void addZan(String objectType, String objectID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("objectType", objectType);
        param.put("objectID", objectID);
        new NetWork().startPost(URLUtil.ADD_ZAN, param, tag);
    }

    /**
     * 评论
     */
    public void addComment(String objectType, String objectID, String text, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("objectType", objectType);
        param.put("objectID", objectID);
        param.put("text", text);
        new NetWork().startPost(URLUtil.ADD_COMMENT, param, tag);
    }

    /**
     * 动态详情
     */
    public void getCommentBean(String articleID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("articleID", articleID);
        param.put("pageSize", "1");
        new NetWork().startPost(URLUtil.ARTICAL_DDETAIL, param, tag);
    }

    /**
     * 评论
     */
    public void addContentComment(String contentID, String text, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        param.put("text", text);
        new NetWork().startPost(URLUtil.ADD_PRODUCT_COMMENT, param, tag);
    }

    /**
     * 获取帖子评论列表
     */
    public void getCommonCommentList(Context context, String objectType, String objectID, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("objectType", objectType);
        param.put("objectID", objectID);
        param.put("pageNo", pageNo + "");
        param.put("pageSize", pageSize + "");
        if (pageNo == 1)
        {
            new NetWork().needCache(context)//当需要缓存时，传入context
                    .startPost(URLUtil.GET_COMMUNITY_COMMENT_LIST, param, tag);
        }
        else
        {
            new NetWork().startPost(URLUtil.GET_COMMUNITY_COMMENT_LIST, param, tag);
        }
    }

    /**
     * 获取帖子评论列表
     */
    public void getMyCommentList(Context context, int pageNo, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userID", Global.getUserId());
        param.put("pageNo", pageNo + "");
        param.put("objectType", "1");
        param.put("objectID", "");
//        param.put("pageSize", "1");
        param.put("pageSize", Constants.LIST_NUM + "");
        if (pageNo == 1)
        {
            new NetWork().needCache(context)//当需要缓存时，传入context
                    .startPost(URLUtil.GET_COMMUNITY_COMMENT_LIST, param, tag);
        }
        else
        {
            new NetWork().startPost(URLUtil.GET_COMMUNITY_COMMENT_LIST, param, tag);
        }
    }

    /**
     * 删除帖子评论
     */
    public void deleteComment(String commentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("commentID", commentID);
        new NetWork().startPost(URLUtil.DELETE_COMMUNITY_COMMENT, param, tag);
    }

    /**
     * 删除帖子评论
     */
    public void deleteComment(List<String> deleteList, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("commentIDList", deleteList);
        new NetWork().startPost2(URLUtil.DELETE_COMMUNITY_COMMENT, param, tag);
    }

    /**
     * 删除帖子
     */
    public void deleteCommunity(String articleID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("articleID", articleID);
        new NetWork().startPost(URLUtil.DELETE_COMMUNITY, param, tag);
    }

    /**
     * 删除帖子
     */
    public void deleteCommunity(List<String> deleteList, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleIDList", deleteList);
        new NetWork().startPost2(URLUtil.DELETE_COMMUNITY, param, tag);
    }

    /**
     * 上传图片url
     */
    public void getUploadUrl(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("scene", "1");
        new NetWork().startPost(URLUtil.GET_UP_LOAD_PIC_URL, param, tag);
    }

    /**
     * 发布帖子
     */
    public void publicCommunity(String communityID, String title, String content, String pic1Url, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("communityID", communityID);
        param.put("content", content);
        param.put("title", title);
        param.put("picUrlList", pic1Url);
        new NetWork().startPost2(URLUtil.ADD_COMMUNITY, param, tag);
    }

    /**
     * 发布帖子
     */
    public void publicCommunity(String communityID, String title, String content, List<String> pic1Url, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("communityID", communityID);
        param.put("content", content);
        param.put("title", title);
        param.put("picUrlList", pic1Url);
        new NetWork().startPost2(URLUtil.ADD_COMMUNITY, param, tag);
    }

    /**
     * 发布帖子
     */
    public void publicCommunity(String communityID, String title, String content, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("communityID", communityID);
        param.put("content", content);
        param.put("title", title);
        new NetWork().startPost(URLUtil.ADD_COMMUNITY, param, tag);
    }

    /**
     * 获取收货地址列表
     */
    public void getAddressList(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork().startPost(URLUtil.GET_ADDRESS_LIST, param, tag);
    }

    /**
     * 获取帖子列表
     */
    public void getCommunityList(Context context, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("communityID", Global.getCommunityId());
        param.put("pageNo", pageNo + "");
        param.put("pageSize", pageSize + "");
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.GET_COMMUNITY_LIST, param, tag);

    }

    /**
     * 获取帖子列表
     */
    public void getMyCommunityList(Context context, int pageNo, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageNo", pageNo + "");
//        param.put("pageSize", 2+"");
        param.put("pageSize", Constants.LIST_NUM + "");
        param.put("userID", Global.getUserId());
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.GET_COMMUNITY_LIST, param, tag);
    }

    /**
     * 修改个人信息
     */
    public void editUserInfo(int key, String value, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        //用户昵称
        if (key == 1)
        {
            param.put("nickName", value);
        }
        else
        {
            param.put("nickName", Global.getNickName());
        }
        //头像
        if (key == 2)
        {
            param.put("portrait", value);
        }
        else
        {
            param.put("portrait", Global.getUserHeadUrl());
        }
        //性别
        if (key == 3)
        {
            param.put("gender", value);
        }
        else
        {
            param.put("gender", Global.getGender());
        }
        //用户名 手机号
        if (key == 4)
        {
            param.put("email", value);
        }
        else
        {
            param.put("email", Global.getEmail());
        }
        //出生日期
        if (key == 5)
        {
            param.put("birthday", value);
        }
        else
        {
            param.put("birthday", Global.getBirthday());
        }
        new NetWork().startPost(URLUtil.UPDATE_USER_INFO, param, tag);
    }

    /**
     * 获取订单列表
     */
    public void getOrderList(int status, String keyword, int pageNo, int pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        if (status != 0)
        {
            param.put("status", "" + status);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(keyword))
        {
            param.put("keyword", "" + keyword);
        }
        param.put("pageNo", "" + pageNo);
        param.put("pageSize", "" + pageSize);
        new NetWork()
                .startPost(URLUtil.GET_ORDER_LIST, param, tag);
    }

    /**
     * 获取订单列表
     */
    public void getOrderDetail(String orderID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", orderID);
        new NetWork()
                .startPost(URLUtil.GET_ORDER_DETAIL, param, tag);
    }
    /**
     * 获取订单列表
     */
    public void REMINDDELIVER(String orderID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", orderID);
        new NetWork()
                .startPost(URLUtil.REMINDDELIVER, param, tag);
    }

    /**
     * 搜索内容
     */
    public void getSearchContent(Context context, String serviceType, String categoryID, String subCategoryID, String keyword, String order, String pageNo, String pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("serviceType", serviceType);
        param.put("categoryID", categoryID);
        param.put("subCategoryID", subCategoryID);
        param.put("keyword", keyword);
        param.put("order", order);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.GET_CATEGORY_FOUND, param, tag);
    }

    /**
     * 分类右侧列表
     */
    public void getCategoryRight(Context context,String type, String parentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
//        param.put("parentID", parentID);
//        param.put("needCotnent", needCotnent);
//        param.put("pageNo", pageNo);
//        param.put("pageSize", pageSize);
        param.put("category1",parentID);
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.GET_CATEGORY_RIGHT, param, tag);
    }
//    /**
//     * 分类右侧列表
//     */
//    public void getCategoryRight( String parentID, String needCotnent, String pageNo, String pageSize, String tag) {
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("parentID", parentID);
//        param.put("needCotnent", needCotnent);
//        param.put("pageNo", pageNo);
//        param.put("pageSize", pageSize);
//        new NetWork()
//                .startPost(URLUtil.GET_CATEGORY_RIGHT, param, tag);
//    }

    /**
     * 获取分类列表
     */
    public void getCategoryList(Context context, String type, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("type",type);
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.GET_CATEGORY_LIST, param, tag);
    }

    /**
     * 获取分类列表
     */
    public void getCategoryListByType(Context context, String serviceType, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("serviceType", serviceType);
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.GET_CATEGORY_LIST, param, tag);
    }
//    /**
//     * 获取分类列表
//     */
//    public void getCategoryListByType(String serviceType, String tag) {
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("serviceType", serviceType);
//        new NetWork()
//                .startPost(URLUtil.GET_CATEGORY_LIST, param, tag);
//    }

    /**
     * 1.5.2 添加商品到购物车
     */
    public void addToBuyCar(String contentID, int count, String style, String color, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        param.put("count", count + "");
//        param.put("style", style);
//        param.put("size", size);
        if (GeneralUtils.isNotNullOrZeroLenght(style))
        {
            param.put("style", style);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(color))
        {
            param.put("color", color);
        }
        new NetWork().startPost(URLUtil.ADD_TO_BUY_CAR, param, tag);
    }

    /**
     * 1.5.2 添加商品到购物车
     */
    public void addToBuyCar(String contentID, int count, String tag)
    {
        addToBuyCar(contentID, count, "", "", tag);
    }

    /**
     * 获取购物车列表
     */
    public void getCartList(String pageNo, String pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        new NetWork()
                .startPost(URLUtil.GET_CART_LIST, param, tag);
    }


    /**
     * 确认订单
     */
    public void addOrder(ArrayList<OrderContent> orderContentList, int payType, String deliveryAddress,
                         String deliveryUser, String phone, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderContentList", orderContentList);
        param.put("couponID", "");//暂无优惠券
        param.put("payType", payType);
        param.put("deliveryAddress", deliveryAddress);
        param.put("deliveryUser", deliveryUser);
        param.put("phone", phone);
        new NetWork().startPost2(URLUtil.ADD_ORDER, param, tag);
    }

    /**
     * 确认订单
     */
    public void addOrder(String orderID, int payType, String deliveryAddress,
                         String deliveryUser, String phone, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderID", orderID);
        param.put("couponID", "");//暂无优惠券
        param.put("payType", payType);
        param.put("deliveryAddress", deliveryAddress);
        param.put("deliveryUser", deliveryUser);
        param.put("phone", phone);
        new NetWork().startPost2(URLUtil.ADD_ORDER, param, tag);
    }

    /**
     * 订单退款
     */
    public void refund(String orderID, String reason, String remark,
                       String price, String picUrl1, String picUrl2, String picUrl3, String picUrl4, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderID", orderID);
        param.put("reason", reason);//退款原因
        param.put("remark", remark);//备注
        param.put("price", price);
        param.put("picUrl1", picUrl1);
        param.put("picUrl2", picUrl2);
        param.put("picUrl3", picUrl3);
        param.put("picUrl4", picUrl4);
        new NetWork().startPost2(URLUtil.ORDER_REFUND, param, tag);
    }

    /**
     * 修改购物车数量
     */
    public void setCartNum(List<CartResponse.CartRecord> list, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("cartRecordList", list);
        new NetWork()
                .startPost2(URLUtil.SET_CART_NUM, param, tag);
    }

    /**
     * 删除购物车
     */
    public void delCart(List<String> list, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordIDList", list);
        new NetWork()
                .startPost2(URLUtil.DEL_CART, param, tag);

    }

    /**
     * 我的收藏列表
     */
    public void getFavourList(Context context,String objectType,String pageNo, String pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("objectType", objectType);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        new NetWork()
                .startPost(URLUtil.GET_FAVOUR_LIST, param, tag);
    }

    /**
     * 浏览历史列表
     */
    public void getGoodsHistory(String operationType, String pageNo, String pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("operationType", operationType);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        new NetWork()
                .startPost(URLUtil.GET_HISTORY_LIST, param, tag);
    }

    /**
     * 删除浏览历史item
     */
    public void DelHistoryGoods(List<String> operationIDs, String operationType, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        if (operationIDs!=null)
        {
            param.put("operationIDList", operationIDs);
        }
        else
        {
            param.put("clear", "1");
        }
        param.put("operationType", operationType);
        new NetWork()
                .startPost2(URLUtil.DEL_HISTORY_GOODS, param, tag);
    }

    /**
     * 删除播放历史item
     */
    public void DelHistoryVideo(List<String> operationIDList, String operationType, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        List<String> strs = new ArrayList<>();
        param.put("operationIDList", operationIDList);
        param.put("operationType", operationType);
        new NetWork()
                .startPost2(URLUtil.DEL_HISTORY_GOODS, param, tag);
    }

    /**
     * 删除收藏
     */
    public void DelFavour(String favoriteID,String objectType,String objectID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("favoriteID", favoriteID);
        param.put("objectType", objectType);
        param.put("objectID", objectID);
        new NetWork()
                .startPost(URLUtil.DEL_FAVOUR, param, tag);
    }

    /**
     * 1.4.1 收藏内容
     */
    public void collectContent(String contentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        new NetWork().startPost(URLUtil.AddFavour, param, tag);
    }

    /**
     * 改变订单状态
     */
    public void upDateOrder(String orderID, String type, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", orderID);
        param.put("type", type);
        new NetWork()
                .startPost(URLUtil.UPDATA_ORDER, param, tag);
    }

    /**
     * 改变订单状态
     */
    public void DelOrder(String orderID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", orderID);
        new NetWork()
                .startPost(URLUtil.D_ORDER, param, tag);
    }

    /**
     * 获取物流信息
     */
    public void getLogisticsInfo(Context context, String orderID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderID", orderID);
        new NetWork()
                .needCache(context)//当需要缓存时，传入context
                .startPost(URLUtil.Logistics, param, tag);
    }

    /**
     * 获取播放地址
     */
    public void getVideoURL(String contentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        new NetWork()
                .startPost(URLUtil.PLAY_VIDEO, param, tag);
    }


    /**
     * 找相似
     */
    public void getSimilar(String contentID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        new NetWork()
                .startPost(URLUtil.GETSIMILAR, param, tag);
    }

    /**
     * 获取评论列表
     */
    public void getPLLIST(String contentID, String pageNo, String pageSize, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("contentID", contentID);
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        new NetWork()
                .startPost(URLUtil.VIDEO_PL, param, tag);
    }
}
