package com.fengqipu.mall.adapter.index;

/**
 * Created by huqing on 2016/7/18.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.index.ColumnListBean;
import com.fengqipu.mall.bean.index.ContentListBean;
import com.fengqipu.mall.constant.IntentCode;
import com.fengqipu.mall.main.acty.index.ProductActy;
import com.fengqipu.mall.tools.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

public class SixViewAdapter extends BaseAdapter
{
    private final int type;

    private List<ContentListBean> contentList = new ArrayList<>();

    private Context mContext;

    private LayoutInflater mInflater;

    private ColumnListBean mColumnContentResponse;

    private ViewGroup.LayoutParams params;

    public SixViewAdapter(Context context, ColumnListBean mColumnContentResponse)
    {
        this.mContext = context;
        this.mColumnContentResponse = mColumnContentResponse;
        contentList = mColumnContentResponse.getContentList();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
        // 1 在线教育  2 母婴儿童  3 装修  4 全部
        type = mColumnContentResponse.getContentList().get(0).getServiceType();

    }

    @Override
    public int getCount()
    {
        return contentList.size();
    }

    @Override
    public ContentListBean getItem(int position)
    {
        return contentList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_home_vedio, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.show_iv);
            holder.mTitle1 = (TextView) convertView.findViewById(R.id.info_tv1);
            holder.mTitle2 = (TextView) convertView.findViewById(R.id.info_tv2);
            holder.six_ll = (LinearLayout) convertView.findViewById(R.id.six_ll);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.mTitle1.setText(Html.fromHtml(getItem(position).getDescription()));
        holder.mTitle2.setText(holder.mTitle1.getText().toString());
        holder.mTitle1.setText(getItem(position).getContentName()); //多一步操作，获取html中的内容
        if (type == 1)
        {//视屏
            Drawable videoImg = mContext.getResources().getDrawable(R.mipmap.icon_video);
            videoImg.setBounds(0, 0, videoImg.getMinimumWidth(), videoImg.getMinimumHeight()); //设置边界
            holder.mTitle2.setCompoundDrawables(videoImg, null, null, null);//画在右边

        }

        if (GeneralUtils.isNotNullOrZeroLenght(getItem(position).getThumPicUrl1()))
        {
            GeneralUtils.setImageViewWithUrl(mContext, getItem(position).getThumPicUrl1(), holder.mImage, R.drawable.default_bg);
        }
        holder.six_ll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int type = mColumnContentResponse.getContentList().get(position).getServiceType();
                Intent intent = new Intent();
                if (type == 1)
                {
//                    intent.setClass(mContext, EduOlineVideoActivity.class);
                }
                else if (type == 2)
                {
                    intent.setClass(mContext, ProductActy.class);
                }
                else if (type == 3)
                {
//                    intent.setClass(mContext, DecorateActy.class);
                }
                else
                {

                }
                intent.putExtra(IntentCode.contentID, mColumnContentResponse.getContentList().get(position).getContentID());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class ViewHolder
    {
        private TextView mTitle1;

        private TextView mTitle2;

        private ImageView mImage;

        private LinearLayout six_ll;

    }


}
