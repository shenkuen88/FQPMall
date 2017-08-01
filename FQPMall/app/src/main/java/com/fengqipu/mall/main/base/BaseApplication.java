package com.fengqipu.mall.main.base;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.constant.Constants;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.constant.NotiTag;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.tools.GeneralUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.mob.MobSDK;

import org.apache.http.client.CookieStore;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * <baseapplication>
 *
 * @author wangtao
 */
public class BaseApplication extends MultiDexApplication {
    /**
     * 导航栏高度
     */
    public static int statusBarHeight = 0;
    /**
     * cookie缓存
     */
    public static CookieStore cookieStore = null;

    /**
     * app实例
     */
    private static BaseApplication sInstance;

    /**
     * 本地activity
     */
    public static List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 当前avtivity
     */
    public static String currentActivity = "";

    /**
     * Fragment实例
     */
    public static String modelName = "";

    /**
     * 设备号
     */
    public static String DEVICE_TOKEN = "";


    /**
     * url Tag
     */
    public static String urlTag = "";

    public static String isForced = "0";
    public LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        //系统崩溃日志
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        Global.saveOpenApp(true);
        DEVICE_TOKEN = GeneralUtils.getDeviceId(this);
        MobSDK.init(this, null, null);
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1180170717178776#kefuchannelapp44668");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId("44668");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        try {
            if (!ChatClient.getInstance().init(this, options)){
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);

//        EaseUI.getInstance().init(this, null);
    }

    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }

    /**
     * <删除>
     */
    public void deleteActivity(Activity activity) {
        if (activity != null) {
            activitys.remove(activity);
            activity.finish();
        }
    }

    /**
     * <添加activity>
     */
    public void addActivity(Activity activity) {
        activitys.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Global.logoutApplication();
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());//获得当前时间
            sb.append("\nerror code : ");
            sb.append(location.getLocType());//获得erro code得知定位现状
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());//获得纬度
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());//获得经度
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {//通过GPS定位
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());//获得速度
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());//获得当前地址
                sb.append(location.getDirection());//获得方位
                sb.append(location.getCity());//获得方位
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//通过网络连接定位
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());//获得当前地址
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());//获得经营商？
                sb.append(location.getCity());//获得方位
            }
            if (GeneralUtils.isNotNullOrZeroLenght(location.getCity())) {
                Global.saveCity(location.getCity());
                Global.saveDistrict(location.getDistrict());
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOCATION_CITY_SUCCESS));
            }
            Global.savelangitude(location.getLongitude() + "");//经度
            Global.savelatitude(location.getLatitude() + "");//纬度
            //发个通知
            EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOCATION_SUCCESS,location.getAddrStr()));
            CMLog.e(Constants.HTTP_TAG, sb.toString());
        }
    }


}
