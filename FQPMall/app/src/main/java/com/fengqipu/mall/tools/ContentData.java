package com.fengqipu.mall.tools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import dalvik.system.DexClassLoader;

/**
 *
 *
 */
@SuppressLint("DefaultLocale")
@TargetApi(Build.VERSION_CODES.DONUT)
public class ContentData {
    public static final String NetErroMsg = "服务器忙,请重试";
    public static boolean isDownImageSuccess = true;
    public static final String TIMEOUTERR = "timeouterr";
    public static Map<String, SoftReference<Bitmap>> superIndexImageCache = new HashMap<String, SoftReference<Bitmap>>(
            1);

    /***
     * 四舍五入
     *
     * @param msg
     * @return
     */
    public static int siSheWuRu(double msg) {
        return (int) Math.round(msg);
    }

    public static SimpleDateFormat dataSimple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static SimpleDateFormat fileSimple = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static SimpleDateFormat Simpledata = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat Simpledata2 = new SimpleDateFormat("yyyy年MM月dd日\nHH:mm:SS");
    public static SimpleDateFormat Simpledata3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

    public static String getCurrentTime() {
        Date date = new Date();
        String currentTime = dataSimple.format(date);
        return currentTime;
    }

    public static final int WAITTIME = 20;//暂定为20秒  好测试
//	public static final int WAITTIME = 120;
    /***
     * 程序应用版本
     */
    public static int myAppVerCode = 0;

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    public static void bmpIsNullDel(Bitmap btp, String fileName) {

        if (null == btp) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();

            }
        }
    }

    /**
     * 根据手机的分辨率从dp 的单位 转成为px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /***
     * 选择的联系人
     */
    public static String phoneNum = "";

    public static boolean iscomplete = false;

    public static String testdata = "";

    /****
     * 判断List是否为空
     *
     * @param dataList
     * @return true list为空或没有值；false list不为空，并有值
     */
    public static boolean isListNull(List<?> dataList) {
        return !(null != dataList && dataList.size() > 0);

    }


    /***
     * 判断当前Activity是否关闭
     * <p/>
     * true 关闭 false 没有关闭
     *
     * @return
     */
    public static boolean isActivityISNull(Activity context) {
        return !(null != context && !context.isFinishing());
    }

    /***
     * 判断是否存在SD卡 true：存在 false：不存在
     *
     * @return
     */
    public static boolean isSD() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String uuid32len() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /*****
     * shared之是否启动通话
     */
    public static final String SHARED_PHONECLIENT = "sharedphoneclient";
    /*****
     * shared之是否启动通话默认开关
     */
    public static final boolean SHARED_PHONECLIENT_FLAG = true;

    /**
     * SD卡--总目录
     */
    public static String BASE_DIR = Environment.getExternalStorageDirectory() + "";
    /**
     * SD卡--日志目录
     */
    public static String BASE_LOG = BASE_DIR + "/logs";
    /**
     * SD卡--Main日志目录
     */
    public static String BASE_MAIN_LOG = BASE_DIR + "/mainlogs";
    /**
     * SD卡--裁剪图片目录
     */
    public static String BASE_CROPIMG = BASE_DIR + "/cropimg";
    /**
     * SD卡--图片下载目录
     */
    public static String BASE_PICS = BASE_DIR + "/pics";
    public static String BASE_USER_PICS = BASE_DIR + "/pics";
    public static String BASE_SUBJECT = BASE_DIR + "/subject";
    public static String BASE_SUBJECT_DB = BASE_SUBJECT + "/db";
    public static String BASE_SUBJECT_APKS = BASE_SUBJECT + "/apks";
    /**
     * SD卡--上传图片zip文件夹
     */
    public static String BASE_LOADZIPS = BASE_DIR + "/loadzips";

    public static void refreshLogDir(String appname) {
        if(BASE_DIR.contains(appname)){
            return;
        }
        BASE_DIR=BASE_DIR+"/"+appname;
        BASE_LOG = BASE_DIR + "/logs";
        BASE_MAIN_LOG = BASE_DIR + "/mainlogs";
        BASE_CROPIMG = BASE_DIR + "/cropimg";
        BASE_PICS = BASE_DIR + "/pics";
        BASE_USER_PICS = BASE_DIR + "/pics";
        BASE_SUBJECT = BASE_DIR + "/subject";
        BASE_SUBJECT_DB = BASE_SUBJECT + "/db";
        BASE_SUBJECT_APKS = BASE_SUBJECT + "/apks";
        BASE_LOADZIPS = BASE_DIR + "/loadzips";
    }

    /**
     * 创建所有文件夹
     */
    public static void createMKDir(String appname) {
        refreshLogDir(appname);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File f = new File(BASE_DIR);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_LOG);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_MAIN_LOG);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_PICS);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_CROPIMG);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_USER_PICS);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_SUBJECT);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_SUBJECT_DB);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_SUBJECT_APKS);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(BASE_LOADZIPS);
            if (!f.exists()) {
                f.mkdirs();
            }
        }

    }

//    public static void createMkdir(String name) {
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            File file = new File(BASE_DIR + "/" + name);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            if (file.exists()) {
//                L.printLog("创建:" + BASE_DIR + "/" + name + "成功!");
//            }
//
//        }
//
//    }

    /**
     * 判断有无网络
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()){
            return true;
        }
        return false;
    }
    public static final boolean ping() {

        String result = null;
        try {
            String ip = "121.43.154.100";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.e("jia", "result = " + result);
        }
        return false;
    }
    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /***
     * 将px转换成dp
     */
    public static int px_TO_Dip(Context context, float pxValue) {
        return (int) (pxValue
                / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /***
     * 将dp转换成xp
     */
    public static int Dp_To_XP(Context context, int paramFloat) {
        return (int) (paramFloat * (context.getResources().getDisplayMetrics().densityDpi / 160.0F));
    }

    /**
     * 获取文件实际路径
     *
     * @param
     * @return
     */
    public static String getReadFilePath(String typeid, String url) {
        String lasturl = url.substring(url.lastIndexOf("/"));
        String filename = ContentData.BASE_DIR + "/" + typeid + lasturl;
        return filename;

    }

    @SuppressWarnings("resource")
    public static boolean isHaveGif(String url) {
        try {
            url = url.substring(url.lastIndexOf("/") + 1);
            String filenamePath = BASE_PICS + "/" + url;
            File f = new File(filenamePath);
            InputStream is = new FileInputStream(f);
            if (is != null) {
                is.close();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return false;
    }

    public static InputStream getInputStreamGif(String url) {
        try {
            url = url.substring(url.lastIndexOf("/") + 1);
            String filenamePath = BASE_PICS + "/" + url;
            File f = new File(filenamePath);
            InputStream is = new FileInputStream(f);
            if (is != null) {
                return is;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        if (offset < 0) {
            return null;
        }
        if (len <= 0) {
            return null;
        }
        if (offset + len > (int) file.length()) {
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // 创建合适文件大小的数组
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 解压
     *
     * @param file
     * @return
     */
    @SuppressWarnings("resource")
    public static boolean UnZipByFileName(String file) {
        String unzipfile = file; // 解压缩的文件名包含路径
        try {
            File olddirec = new File(unzipfile); // 解压缩的文件路径(为了获取路径)
            if (!olddirec.exists()) {
                return false;
            } else {
                System.out.println("");
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(
                    unzipfile));
            ZipEntry entry;
            // 创建文件夹
            while ((entry = zin.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(olddirec.getParent(),
                            entry.getName());
                    if (!directory.exists())
                        if (!directory.mkdirs())
                            System.exit(0);
                    zin.closeEntry();
                }
                if (!entry.isDirectory()) {
                    String na = entry.getName();
                    // 输出路径
                    String ofile = file.replace(".zip", "");
                    File fo = new File(ofile);
                    if (!fo.exists()) {
                        fo.mkdir();
                    }
                    FileOutputStream fout = new FileOutputStream(ofile + "/"
                            + na.substring(na.lastIndexOf("/") + 1) + ".cach");
                    DataOutputStream dout = new DataOutputStream(fout);
                    byte[] b = new byte[1024];
                    int len = 0;
                    while ((len = zin.read(b)) != -1) {
                        dout.write(b, 0, len);
                    }
                    dout.close();
                    fout.close();
                    zin.closeEntry();

                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /***
     * 升级包名称
     */
    public static String filename = "updateapp.apk";

    /***
     * 升级包路径
     */
    public static String path = Environment.getExternalStorageDirectory() + "/"
            + filename;

    /***
     * 如果存在升级包 就删除
     *
     * @param context
     */
    public static void checkApk(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/" + filename;
            // 如果存在升级包 就删除
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
        } else {
            try {
                path = context.getFilesDir().getPath() + "/" + filename;
                File file_apk = new File(path);
                if (file_apk.exists()) {
                    file_apk.delete();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    /***
     * 安装包
     *
     * @param ctx
     * @param file
     */
    public static void installApk(Context ctx, File file) {
        Intent intent = new Intent();

        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), getMIMEType(file));
        ctx.startActivity(intent);
    }

    public static String getMIMEType(File file) {
        String type = "";
        String fName = file.getName();

        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        if ((end.equals("m4a")) || (end.equals("mp3")) || (end.equals("mid"))
                || (end.equals("xmf")) || (end.equals("ogg"))
                || (end.equals("wav"))) {
            type = "audio";
        } else if ((end.equals("3gp")) || (end.equals("mp4"))) {
            type = "video";
        } else if ((end.equals("jpg")) || (end.equals("gif"))
                || (end.equals("png")) || (end.equals("jpeg"))
                || (end.equals("bmp"))) {
            type = "image";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }

        if (!end.equals("apk")) {
            type = type + "/*";
        }
        return type;
    }

    /***
     * 数据加载通用提示语
     */
    public static String pdShowMsg = "数据加载中,请稍后... ...";

    /***
     * 获得MAC
     */
    public static String getLocalMacAddress2(Context context) {

        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();

    }

    /**
     * 判断输入是否是手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobileNumber(String phoneNumber) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(phoneNumber);

        return m.matches();
    }

    /**
     * 判断是佛是邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmailFormat(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        return b;
    }

    /**
     * 判断密码输入是否正确
     *
     * @param
     * @return
     */
    public static boolean isPassWordFormat(String psd) {

        return !(psd.length() < 6 || psd.length() > 16);
    }

    public static String getSubjectApksFileName(String url) {
        return ContentData.BASE_SUBJECT_APKS
                + "/"
                + url.substring(url.lastIndexOf("/") + 1,
                url.lastIndexOf(".") + 4);
    }

    public static String getBaseSubjectDbFileName(String url) {
        return ContentData.BASE_SUBJECT_DB + "/"
                + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
    }

    public static String getFileName(String url) {

        return ContentData.BASE_USER_PICS
                + "/"
                + url.substring(url.lastIndexOf("/") + 1,
                url.lastIndexOf(".") + 4) + "z";
    }

    public static InputStream getGifViewFromPath(String path) {
        InputStream is = null;
        try {
            File f = new File(path);
            if (f.exists()) {
                is = new FileInputStream(f);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return is;
        }
    }

    public static String getSmallUrl(String file_Url) {
        String result = "";
        try {
            int lens = file_Url.length();
            result = file_Url.substring(0, file_Url.lastIndexOf(".")) + "_s"
                    + file_Url.substring(file_Url.lastIndexOf("."), lens);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static String getSmallFileName(String url) {
        url = getSmallUrl(url);
        return getFileName(url);
    }

    static Class libProviderClazz = null;
    static DexClassLoader cl;


    public static int hours = 3600000;
    public static int mins = 60000;
    public static int mm = 1000;

    public static String showTime(String times) {
        if (null == times || "".equals(times)) {
            return "";
        }
        int timeNum = Integer.parseInt(times);
        int temp = 0;
        String result = "";
        if (timeNum >= hours) {
            temp = timeNum / hours;
            result = result + (temp < 10 ? ("0" + temp) : temp) + ":";
            timeNum = timeNum - hours * temp;
            if (timeNum >= mins) {
                temp = timeNum / mins;
                result = result + temp + ":";
                timeNum = timeNum - mins * temp;
                if (timeNum >= mm) {
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = "00:00";
                }
            } else {
                if (timeNum >= mm) {
                    result = result + ":00:";
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = "00:00";
                }
            }
        } else {
            if (timeNum >= mins) {
                temp = timeNum / mins;
                result = result + (temp < 10 ? ("0" + temp) : temp) + ":";
                timeNum = timeNum - mins * temp;
                if (timeNum >= mm) {
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = result + "00";
                }
            } else {
                if (timeNum >= mm) {
                    result = "00:";
                    temp = timeNum / mm;
                    result = result + (temp < 10 ? ("0" + temp) : temp);
                } else {
                    result = "00:00";
                }
            }
        }
        return result;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sptopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getTimeFormatValue(long time) {
        return MessageFormat.format(
                "{0,number,00}:{1,number,00}:{2,number,00}",
                time / 1000 / 60 / 60, time / 1000 / 60 % 60, time / 1000 % 60);
    }

    /**
     * 生成8位数随机数
     *
     * @return
     */
    public static String getRandomPwd() {
        Random rd = new Random();
        String n = "";
        int getNum;
        do {
            getNum = Math.abs(rd.nextInt()) % 10 + 48;// 产生数字0-9的随机数
            // getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
            char num1 = (char) getNum;
            String dn = Character.toString(num1);
            n += dn;
        } while (n.length() < 8);
        return n;
    }

    public static String leftTime(int second) {
        String yanzhenmsg = "";
        int min = second / 60;
        int sec = second % 60;
        if (min > 0) {
            yanzhenmsg = "<font color='red'>" + min + "</font>" + "<font color='white'>分 </font>" + "<font color='red'>" + sec + "</font>";
        } else {
            yanzhenmsg = "<font color='red'>" + sec + "</font>";
        }
        return yanzhenmsg;

    }

    public static void checkCanSend(SharedPreferences spf, TextView tv) {


    }

    //普通的alertDialog
    public static void AlertMethod(Context context, String title, String info, String PositiveName, DialogInterface.OnClickListener positiveListener, String negativeName, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(info);
        if (!PositiveName.equals("")) {
            builder.setPositiveButton(PositiveName, positiveListener);
        }
        if (!negativeName.equals("")) {
            builder.setNegativeButton(negativeName, negativeListener);
        }
        builder.show();
    }
    //获取当前ActivityName
    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }
    /**

     * 发送短信

     * @param smsBody

     */

    public static void sendSMS(Context context,String smsBody)
    {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }
}
