package com.fengqipu.mall.adapter.index;

/**
 * Created by huqing on 2016/7/18.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqipu.mall.R;

public class HorizontalListViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public HorizontalListViewAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_home_vedio, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.show_iv);
            holder.mTitle1 = (TextView) convertView.findViewById(R.id.info_tv1);
            holder.mTitle2 = (TextView) convertView.findViewById(R.id.info_tv2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle1.setText(position + "个");
        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle1;
        private TextView mTitle2;
        private ImageView mImage;
    }


}
