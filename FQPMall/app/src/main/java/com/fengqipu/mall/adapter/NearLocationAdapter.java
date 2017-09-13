package com.fengqipu.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.fengqipu.mall.R;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class NearLocationAdapter extends RecyclerView.Adapter<NearLocationAdapter.ViewHolder> implements View.OnClickListener
{

    private Context mContext;
    private List<PoiInfo> mItems;
    private LayoutInflater mInflater;

    public NearLocationAdapter(Context mContext, List<PoiInfo> mItems) {
        this.mItems = mItems;
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_location,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_location.setText(mItems.get(position).name);
        holder.tv_detail_location.setText(mItems.get(position).address);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onClick(View v)
    {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_location;
        private TextView tv_detail_location;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_location= (TextView) itemView.findViewById(R.id.tv_location);
            tv_detail_location= (TextView) itemView.findViewById(R.id.tv_detail_location);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener = null;
}
