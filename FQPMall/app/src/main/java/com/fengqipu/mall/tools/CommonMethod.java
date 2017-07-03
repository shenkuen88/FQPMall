package com.fengqipu.mall.tools;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by jw on 2015/11/25.
 */
public class CommonMethod {
    public static final int YZM = 60;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        // 设置参数
        listView.setLayoutParams(params);
    }
    //计算离今多少天
    public static String toDate(String s) {
        Date d=new Date(Long.valueOf(s)*1000);
        String ds= ContentData.sdf.format(d);
        String cs= ContentData.sdf.format(new Date());
//        String day = (Long.valueOf(s)*1000-System.currentTimeMillis())/1000 /  (24 * 60 * 60) +"天";
        String hour=(Long.valueOf(s)*1000-System.currentTimeMillis())/1000 % (24 * 60 * 60) / (60 * 60) + "小时";
        String minte=(Long.valueOf(s)*1000-System.currentTimeMillis())/1000 % (24 * 60 * 60) % (60 * 60) / 60 + "分";
        return hour+minte;
    }
    //计算离今多少天
    public static boolean isOut30Day(String s) {
        int d=30;
        String day = (Long.valueOf(s)-System.currentTimeMillis())/1000 /  (24 * 60 * 60) +"天";
        if(Integer.valueOf(day)>=30){
                return true;
        }
        return false;
    }
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        Log.e("jw", hex.toString());
        return hex.toString();
    }
    public static String dfDouble(double num){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");
        return df.format(num);
    }
    public static String defDouble(double num){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#");
        return df.format(num);
    }
}
