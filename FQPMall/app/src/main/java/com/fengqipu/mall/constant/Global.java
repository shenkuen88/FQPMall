package com.fengqipu.mall.constant;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.mine.LoginResult;
import com.fengqipu.mall.bean.mine.UserBean;
import com.fengqipu.mall.bean.search.HistorySearchBean;
import com.fengqipu.mall.main.base.BaseApplication;
import com.fengqipu.mall.network.GsonHelper;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.CookieUtils;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.SharePref;
import com.fengqipu.mall.tools.StringEncrypt;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import de.greenrobot.event.EventBus;

import static com.fengqipu.mall.tools.SharePref.getString;

/**
 * <全局静态缓存数据>
 * <功能详细描述>
 *
 * @version [版本号, 2015-4-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Global {
    /**
     * 启动引导页
     */
    private static final String IS_NEW_USER = "is_new_user";

    /**
     * app是否处于开启状态
     */
    private static final String OPEN_APP = "open_app";

    /**
     * 用户名
     */
    private static final String LOGIN_USERNAME = "login_username";
    /**
     * 出生日期
     */
    private static final String BIRTHDAY = "BIRTHDAY";
    /**
     * 用户唯一标识，数据局中自增长序列
     */
    private static final String USERID = "userID";
    /**
     * 社区id
     */
    private static final String COMMUBITY_ID = "COMMUBITY_ID";
    /**
     * 用户类型
     * 1-老师；
     * 2-家长
     */
    private static final String USERTYPE = "userType";
    /**
     * 密码
     */
    private static final String PASSWORD = "password";
    /**
     * 昵称
     */
    private static final String NICKNAME = "nickName";
    /**
     * 头像
     */
    private static final String PORTRAIT = "portrait";
    private static final String THUM_PORTRAIT = "THUM_PORTRAIT";
    /**
     * 手机号
     */
    private static final String PHONE = "phone";
    /**
     * 邮箱
     */
    private static final String EMAIL = "email";
    /**
     * 地址
     */
    private static final String ADDRESS = "address";
    /**
     * 地址
     */
    private static final String GENDER = "GENDER";
    /**
     * 状态
     * 1-待审核；2-审核通过
     * 当userType为2时有效
     */
    private static final String STATUS = "status";
    /**
     * 创建时间
     */
    private static final String CREATETIMESTR = "createTimeStr";


    /**
     * 登录名
     */
    private static final String LOGIN_NAME = "LOGIN_NAME";
    /**
     * 客户端标识密码（x-s-password），加密时使用明文
     */
    private static final String XS_PASSWORD_WORD = "XS_PASSWORD_WORD";
    /**
     * 未加密字符串
     */
    private static String XS_SECURITY = "";
    /**
     * 正在处理的订单号
     */
    private static String OPERATE_ORDER_ID = "OPERATE_ORDER_ID";
    /**
     * 退款最多的金额
     */
    private static String REFUND_MAX_MONEY = "REFUND_MAX_MONEY";
    /**
     * 七牛上传图片
     */
    public static String uptoken = "uptoken";
    public static String home_fragment_index = "home_fragment_index";

    /**
     * 上传图片的token
     */
    public static String getToken() {
        return getString(uptoken, "");
    }

    public static void saveToken(String upToken) {
        SharePref.saveString(uptoken, upToken);
    }
    /**
     * 首页目前展示的Fragment
     */
    public static String getNowIndex() {
        return getString(home_fragment_index, "");
    }

    public static void saveNowIndex(String upToken) {
        SharePref.saveString(home_fragment_index, upToken);
    }


    /**
     * 是否启动引导页
     */
    public static int getUserGuide() {
        return SharePref.getInt(IS_NEW_USER, -1);
    }

    public static void saveUserGuide(int newUser) {
        SharePref.saveInt(IS_NEW_USER, newUser);
    }

    /**
     * 是否启动引导页
     */
    public static String getOrderId() {
        return getString(OPERATE_ORDER_ID, "");
    }

    public static void saveOrderId(String orderId) {
        SharePref.saveString(OPERATE_ORDER_ID, orderId);
    }

    /**
     * 是否启动引导页
     */
    public static String getRefundMoney() {
        if (GeneralUtils.isNotNullOrZeroLenght(getString(REFUND_MAX_MONEY, "")) ){
            return getString(REFUND_MAX_MONEY, "");
        }else{
            return "0";
        }
    }

    public static void saveRefundMoney(String orderId) {
        SharePref.saveString(REFUND_MAX_MONEY, orderId);
    }


    /**
     * 获取登录名
     */
    public static String getLoginName() {
        return getString(LOGIN_NAME, "");
    }

    public static void saveLoginName(String username) {
        SharePref.saveString(LOGIN_NAME, username);
    }

    /**
     * 获取密码
     */
    public static String getXS_PASSWORD_WORD() {
        return getString(XS_PASSWORD_WORD, "");
    }

    public static void saveXS_PASSWORD_WORD(String username) {
        SharePref.saveString(XS_PASSWORD_WORD, username);
    }


    /**
     * app是否处于开启状态
     */
    public static boolean getOpenApp() {
        return SharePref.getBoolean(OPEN_APP, false);
    }

    public static void saveOpenApp(boolean openApp) {
        SharePref.saveBoolean(OPEN_APP, openApp);
    }


    /**
     * <保存登陆信息>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public static void saveLoginData(Context context, LoginResult loginResult, String phone) {
        saveUserName(phone);
        CookieUtils.getInstance(context).saveCookies(BaseApplication.cookieStore);
    }

    public static void saveLoginUserData(Context context, UserBean userBean) {
        saveUserName(userBean.getUserName());
        saveUserId(userBean.getId());
        saveuserType(userBean.getUserType() + "");
        savePassword(userBean.getPassword());
        saveNickname(userBean.getNickName());
        saveUserHeadUrl(userBean.getPortraitRequestUrl());
        saveThumUserHeadUrl(userBean.getPortraitRequestUrl());
        savePhone(userBean.getPhone());
        saveGender(userBean.getGender());
        saveEmail(userBean.getEmail());
        saveBirth(userBean.getBirthday());
//        saveAddress(userBean.get);
        saveStatus(userBean.getStatus() + "");
        saveCreatetimestr(userBean.getCreateTime());
    }

    private static void saveBirth(String birthday) {
        SharePref.saveString(BIRTHDAY, birthday);
    }

    public static String getBirthday() {
        return getString(BIRTHDAY, "");
    }


    /**
     * 获取用户名
     */
    public static String getUserName() {
        return getString(LOGIN_USERNAME, "");
    }

    public static void saveUserName(String username) {
        SharePref.saveString(LOGIN_USERNAME, username);
    }

    /**
     * 用户唯一标识，数据局中自增长序列
     */
    public static String getUserId() {
        return getString(USERID, "");
    }

    public static void saveUserId(String username) {
        SharePref.saveString(USERID, username);
    }

    /**
     * 用户唯一标识，数据局中自增长序列
     */
    public static String getCommunityId() {
        return getString(COMMUBITY_ID, "1");
    }

    public static void saveCommunityId(String username) {
        SharePref.saveString(COMMUBITY_ID, username);
    }

    /**
     * 用户类型 1-老师；2-家长
     */
    public static String getuserType() {
        return getString(USERTYPE, "");
    }

    public static void saveuserType(String username) {
        SharePref.saveString(USERTYPE, username);
    }

    /**
     * 密码
     */
    public static String getPassword() {
        return getString(PASSWORD, "");
    }

    public static void savePassword(String username) {
        SharePref.saveString(PASSWORD, username);
        saveXS_PASSWORD_WORD(StringEncrypt.Encrypt(username));
    }

    /**
     * 昵称
     */
    public static String getNickName() {
        return getString(NICKNAME, "");
    }

    public static void saveNickname(String username) {
        SharePref.saveString(NICKNAME, username);
    }

    /**
     * 头像
     */
    public static String getUserHeadUrl() {
        return getString(PORTRAIT, "");
    }

    public static void saveUserHeadUrl(String username) {
        SharePref.saveString(PORTRAIT, username);
    }
    /**
     * 头像
     */
    public static String getThumUserHeadUrl() {
        return getString(THUM_PORTRAIT, "");
    }

    public static void saveThumUserHeadUrl(String username) {
        SharePref.saveString(THUM_PORTRAIT, username);
    }

    /**
     * 电话
     */
    public static String getPhone() {
        return getString(PHONE, "");
    }

    public static void savePhone(String username) {
        SharePref.saveString(PHONE, username);
    }

    /**
     * 性别
     */
    public static String getGender() {
        return getString(GENDER, "");
    }

    public static void saveGender(String mGENDER) {
        SharePref.saveString(GENDER, mGENDER);
    }

    /**
     * 邮箱
     */
    public static String getEmail() {
        return getString(EMAIL, "");
    }

    public static void saveEmail(String username) {
        SharePref.saveString(EMAIL, username);
    }

    /**
     * 地址
     */
    public static String getAddress() {
        return getString(ADDRESS, "");
    }

    public static void saveAddress(String username) {
        SharePref.saveString(ADDRESS, username);
    }

    /**
     * 状态 1-待审核；2-审核通过 当userType为2时有效
     */
    public static String getStatus() {
        return getString(STATUS, "");
    }

    public static void saveStatus(String username) {
        SharePref.saveString(STATUS, username);
    }

    /**
     * 创建时间 yyyyMMddDDMMSS
     */
    public static String getCreatetimestr() {
        return getString(CREATETIMESTR, "");
    }

    public static void saveCreatetimestr(String username) {
        SharePref.saveString(CREATETIMESTR, username);
    }

    /**
     * <注销登录>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public static void loginOut(Context context) {
        if (BaseApplication.cookieStore != null) {
            BaseApplication.cookieStore.clear();
            CookieUtils.getInstance(context).saveCookies(BaseApplication.cookieStore);
        }
        saveUserName("");
        saveThumUserHeadUrl("");
        saveUserHeadUrl("");
        saveUserId("");
        saveuserType("");
        savePassword("");
        saveNickname("");
        savePhone("");
        saveGender("");
        saveEmail("");
        saveBirth("");
//        saveAddress(userBean.get);
        saveStatus("");
        saveCreatetimestr("");
        //收货地址，防止其他账号登录看到
        SharePref.saveString(Constants.ADDRESS_LIST, "");
        //发个通知，让其他页面知道已经退出了
        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_EXIT));
    }

    public static void addSearchHistory(int searchType,String keyword){
        ArrayList<HistorySearchBean> searchHistoryList=new ArrayList<>();
        String hislistStr=SharePref.getString("SearchHistory", "");
        if(!hislistStr.equals("")) {
            final Type searchHistorytype = new TypeToken<ArrayList<HistorySearchBean>>() {
            }.getType();
            searchHistoryList=GsonHelper.fromJson(hislistStr,searchHistorytype);
        }
        if(searchHistoryList==null)searchHistoryList=new ArrayList<>();
        if(searchHistoryList.size()>=6){
            searchHistoryList.remove(0);
        }
        int index=-1;
        for(int i=0;i<searchHistoryList.size();i++){
            if(searchType==searchHistoryList.get(i).getSearchType()&&searchHistoryList.get(i).getKeyword().equals(keyword)){
                index=i;
            }
        }
        if(index!=-1){
            searchHistoryList.remove(index);
        }
        searchHistoryList.add(new HistorySearchBean(searchType,keyword));
        SharePref.saveString("SearchHistory", GsonHelper.toJson(searchHistoryList));
    }
    public static ArrayList<HistorySearchBean> getSearchHistory(){
        ArrayList<HistorySearchBean> searchHistoryList=new ArrayList<>();
        String hislistStr=SharePref.getString("SearchHistory", "");
        if(!hislistStr.equals("")) {
            final Type searchHistorytype = new TypeToken<ArrayList<HistorySearchBean>>() {
            }.getType();
            searchHistoryList=GsonHelper.fromJson(hislistStr,searchHistorytype);
        }
        if(searchHistoryList==null)searchHistoryList=new ArrayList<>();
        return searchHistoryList;
    }
    public static void delSearchHistory(String keyword){
        ArrayList<HistorySearchBean> searchHistoryList=new ArrayList<>();
        String hislistStr=SharePref.getString("SearchHistory", "");
        if(!hislistStr.equals("")) {
            final Type searchHistorytype = new TypeToken<ArrayList<HistorySearchBean>>() {
            }.getType();
            searchHistoryList=GsonHelper.fromJson(hislistStr,searchHistorytype);
        }
        if(searchHistoryList==null)searchHistoryList=new ArrayList<>();
        int index=-1;
        for(int i=0;i<searchHistoryList.size();i++){
            if(searchHistoryList.get(i).getKeyword().equals(keyword)){
                index=i;
            }
        }
        Log.e("sub","index="+index);
        if(index!=-1){
            searchHistoryList.remove(index);
        }
        SharePref.saveString("SearchHistory", GsonHelper.toJson(searchHistoryList));
    }
    public static void delAllSearchHistory(){
       SharePref.saveString("SearchHistory", "");
    }
    /**
     * <退出应用>
     */
    public static void logoutApplication() {
        Global.saveOpenApp(false);
        try {
            for (Activity activity : BaseApplication.activitys) {
                activity.finish();
            }
        } catch (Exception e) {
            CMLog.e("", "finish activity exception:" + e.getMessage());
        } finally {
//            ImageLoader.getInstance().clearMemoryCache();
            System.exit(0);
        }
    }


    public static String getXSSecurity() {
        if (GeneralUtils.isNullOrZeroLenght(XS_SECURITY)) {
            String part1 = "webContent";
            String part2 = BaseApplication.DEVICE_TOKEN;
            String part3 = getXS_PASSWORD_WORD();
            String part4 = GeneralUtils.formatDate(new Date(), GeneralUtils.DATE_PATTERN);
            String part5 = getLoginName();
            XS_SECURITY = StringEncrypt.Encrypt(part1 + part2 + part3 + part4 + part5);
        }
        return XS_SECURITY;
    }


    public static void savelangitude(String name) {
        SharePref.saveString(Constants.LANGITUDE, name);
    }

    public static String getlangitude() {
        return getString(Constants.LANGITUDE, "");
    }

    public static void savelatitude(String name) {
        SharePref.saveString(Constants.LATITUDE, name);
    }

    public static String getlatitude() {
        return getString(Constants.LATITUDE, "");
    }

    public static void saveCity(String name) {
        SharePref.saveString(Constants.CITY_NAME, name);
    }

    public static String getCity() {
        return getString(Constants.CITY_NAME, "");
    }

    public static void saveDistrict(String name) {
        SharePref.saveString(Constants.DISTRICT_NAME, name);
    }

    public static String getDistrict() {
        return getString(Constants.DISTRICT_NAME, "");
    }
}
