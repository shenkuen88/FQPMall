package com.fengqipu.mall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.bean.search.CityResponse;


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
    EditText minPrice;
    EditText maxPrice;
    TextView fenleiTv;
    Button btnCz;
    Button btnConfirm;
    LinearLayout sxLl;

    public ShaiXuanDialog(final Activity ctx, View view, int style, final CityResponse cityResponse) {
        super(view.getContext(), style);
        // // 透明背景
        // Drawable myDrawable =
        // context.getResources().getDrawable(R.drawable.dialog_root_bg);
        // myDrawable.setAlpha(150);
        // view.setBackgroundDrawable(myDrawable);
        setContentView(view);
        Window window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
//        params.width = width;
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        params.width = dm.widthPixels*2/3;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        // params.height = (int) (default_height * density);
        // view.measure(0, 0);
//         params.width = view.getMeasuredWidth();
//         params.height = view.getMeasuredHeight();
        window.setAttributes(params);

        // 此处可以设置dialog显示的位置
        window.setGravity(Gravity.RIGHT);

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
