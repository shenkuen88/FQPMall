package com.fengqipu.mall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.search.CityResponse;
import com.fengqipu.mall.constant.Global;
import com.fengqipu.mall.main.acty.search.SearchCategoryActivity;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.MyGridView;


/**
 * 自定义Dialog
 */
public class ShaiXuanDialog extends Dialog {
    private String TAG = "ShaiXuanDialog";

    public ShaiXuanDialog(Activity ctx, CityResponse cityResponse) {
        this(ctx, ctx.getLayoutInflater().inflate(R.layout.sel_shaixuan_layout, null), cityResponse);
    }

    public ShaiXuanDialog(Activity ctx, View view, CityResponse cityResponse) {
        this(ctx, view, R.style.RightDialog, cityResponse);
    }
    public EditText minPrice;
    public EditText maxPrice;
    public TextView fenleiTv;
    public RelativeLayout fenlei_rl;
    public Button btnCz;
    public Button btnConfirm;
    public MyGridView my_grid_view;
    public CommonAdapter<CityResponse.HotAddressBean> mAdapter;
    public String selID="";
    public String selName="";
    private TextView tv_cur_city;

    public ShaiXuanDialog(final Activity ctx, View view, int style, final CityResponse cityResponse) {
        super(view.getContext(), style);
        // // 透明背景
        // Drawable myDrawable =
        // context.getResources().getDrawable(R.drawable.dialog_root_bg);
        // myDrawable.setAlpha(150);
        // view.setBackgroundDrawable(myDrawable);
//        this.setCancelable(false);
        setContentView(view);
        Window window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
//        params.width = width;
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        float scaleHeight=dm.heightPixels/1280f;
        params.width = dm.widthPixels*3/4;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
                //-(int)(200*scaleHeight);
        // params.height = (int) (default_height * density);
        // view.measure(0, 0);
//         params.width = view.getMeasuredWidth();
//         params.height = view.getMeasuredHeight();
        window.setAttributes(params);

        // 此处可以设置dialog显示的位置
        window.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        minPrice= V.f(view,R.id.min_price);
        maxPrice= V.f(view,R.id.max_price);
        fenleiTv= V.f(view,R.id.fenlei_tv);
        btnCz= V.f(view,R.id.btn_cz);
        btnConfirm= V.f(view,R.id.btn_confirm);
        my_grid_view= V.f(view,R.id.my_grid_view);
        fenlei_rl=V.f(view,R.id.fenlei_rl);
        tv_cur_city=V.f(view,R.id.tv_cur_city);
        String curCity="";
        try {
            curCity=Global.getCity().replace("市","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_cur_city.setText(curCity);
        fenlei_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, SearchCategoryActivity.class));
            }
        });
        mAdapter=new CommonAdapter<CityResponse.HotAddressBean>(ctx,cityResponse.getAddressList(),R.layout.item_hot_city) {
            @Override
            public void convert(ViewHolder helper,final CityResponse.HotAddressBean item) {
                TextView tv_name=helper.getView(R.id.tv_name);
                tv_name.setText(""+item.getName());
                if(selID.equals(item.getAddressID())){
                    tv_name.setTextColor(ctx.getResources().getColor(R.color.app_color));
                    tv_name.setBackgroundResource(R.drawable.price_rec_2);
                }else{
                    tv_name.setTextColor(Color.parseColor("#979797"));
                    tv_name.setBackgroundResource(R.drawable.price_rec);
                }
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selID=item.getAddressID();
                        selName=item.getName();
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        my_grid_view.setAdapter(mAdapter);
    }

    public void setBtnCzListener(View.OnClickListener listener){
        btnCz.setOnClickListener(listener);
    }
    public void setBtnConfirmListener(View.OnClickListener listener){
        btnConfirm.setOnClickListener(listener);
    }

    public void setOutsideTouchable(boolean touchable) {
    }

    public void setBackgroundDrawable(Drawable background) {
    }

    public void setAnimationStyle(int animationStyle) {
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        show();
    }

}
