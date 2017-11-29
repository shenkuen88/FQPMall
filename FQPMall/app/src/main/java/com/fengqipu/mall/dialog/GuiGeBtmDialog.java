package com.fengqipu.mall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.adapter.CommonAdapter;
import com.fengqipu.mall.adapter.ViewHolder;
import com.fengqipu.mall.bean.NoticeEvent;
import com.fengqipu.mall.bean.goods.GoodsDetailResponse;
import com.fengqipu.mall.bean.goods.GuiGeBean;
import com.fengqipu.mall.tools.GeneralUtils;
import com.fengqipu.mall.tools.V;
import com.fengqipu.mall.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 自定义Dialog
 */
public class GuiGeBtmDialog extends Dialog {
    private String TAG = "GuiGeCSBtmDialog";

    public GuiGeBtmDialog(Activity ctx, GoodsDetailResponse goodsDetailResponse) {
        this(ctx, ctx.getLayoutInflater().inflate(R.layout.sel_btm_layout, null), goodsDetailResponse);
    }

    public GuiGeBtmDialog(Activity ctx, View view, GoodsDetailResponse goodsDetailResponse) {
        this(ctx, view, R.style.BottomDialog, goodsDetailResponse);
    }

    private ImageView gg_pic, gg_cancel;
    public TextView gg_name, gg_price, tv_guige, btn_jian, btn_jia;
    public EditText num_txt;
    private ListView my_list_view;
    private Button btn_addgwc, btn_buy;
    int num = 1;

    private String stylestr = "";
    private String colorstr = "";
    private List<GuiGeBean> styleList = new ArrayList<>();
    private CommonAdapter<GuiGeBean> styleAdapter;

    public void setGwcClickListener(View.OnClickListener listener){
        btn_addgwc.setOnClickListener(listener);
    }
    public void setBuyClickListener(View.OnClickListener listener){
        btn_buy.setOnClickListener(listener);
    }
    private List<String> selstyles=new ArrayList<>();
    private List<String> selcolors=new ArrayList<>();

    public List<String> stylestrs=new ArrayList<>();
    public List<String> colorstrs=new ArrayList<>();
    public GuiGeBtmDialog(final Activity ctx, View view, int style,final GoodsDetailResponse goodsDetailResponse) {
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
        float scaleHeight = (float) dm.heightPixels / 1280f;
        params.width = dm.widthPixels;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // params.height = (int) (default_height * density);
        // view.measure(0, 0);
//         params.width = view.getMeasuredWidth();
//         params.height = view.getMeasuredHeight();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        // 此处可以设置dialog显示的位置
        window.setGravity(Gravity.BOTTOM);
        gg_pic = V.f(view, R.id.gg_pic);
        gg_cancel = V.f(view, R.id.gg_cancel);
        gg_name = V.f(view, R.id.gg_name);
        gg_price = V.f(view, R.id.gg_price);
        tv_guige = V.f(view, R.id.tv_guige);
        btn_jian = V.f(view, R.id.btn_jian);
        num_txt = V.f(view, R.id.num_txt);
        btn_jia = V.f(view, R.id.btn_jia);
        my_list_view = V.f(view, R.id.my_list_view);
        btn_addgwc = V.f(view, R.id.btn_addgwc);
        btn_buy = V.f(view, R.id.btn_buy);
        if (goodsDetailResponse.getContent().getPicUrl1RequestUrl() != null
                && !goodsDetailResponse.getContent().getPicUrl1RequestUrl().equals("")) {
            GeneralUtils.setImageViewWithUrl(ctx, goodsDetailResponse.getContent().getPicUrl1RequestUrl(), gg_pic, R.drawable.bg_image_classification);
        }
        gg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        gg_name.setText(goodsDetailResponse.getContent().getContentName() + "");
        gg_price.setText("￥" + goodsDetailResponse.getContent().getPrice());
        if (goodsDetailResponse.getContentStyleList() != null && goodsDetailResponse.getContentStyleList().size() > 0) {
            if (stylestr.equals("")) {
                stylestr = goodsDetailResponse.getContentStyleList().get(0).getStyle().trim();
            }
            selcolors.clear();
            for (GoodsDetailResponse.ContentStyleListBean item : goodsDetailResponse.getContentStyleList()) {
                if (item.getStyle() != null && item.getStyle().equals(stylestr)) {
                    selcolors.add(item.getColor());
                }
            }
            if (colorstr.equals("")) {
                colorstr = goodsDetailResponse.getContentStyleList().get(0).getColor().trim();
            }
            for (GoodsDetailResponse.ContentStyleListBean item : goodsDetailResponse.getContentStyleList()) {
                if(item.getStyle().equals(stylestr)&&item.getColor().equals(colorstr)){
                    gg_price.setText("￥" + item.getPrice());
                }
            }
            String str="";
            try {
                if(stylestr!=null
                        &&!stylestr.equals("")){
                    str= stylestr+"、";
                }
                if(colorstr!=null
                        &&!colorstr.equals("")){
                    str=str+colorstr+ "、";
                }
                str= str + num_txt.getText().toString() + "件";
                tv_guige.setText(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tv_guige.setText(str);
            for (GoodsDetailResponse.ContentStyleListBean item : goodsDetailResponse.getContentStyleList()) {
                if(!stylestrs.contains(item.getStyle())){
                    stylestrs.add(item.getStyle());
                }
                if(!colorstrs.contains(item.getColor())){
                    colorstrs.add(item.getColor());
                }
            }
            if(stylestrs.size()>0){
                styleList.add(new GuiGeBean("规格",stylestrs));
            }
            if(colorstrs.size()>0){
                styleList.add(new GuiGeBean("颜色",colorstrs));
            }
            styleAdapter = new CommonAdapter<GuiGeBean>(ctx, styleList, R.layout.gwc_canshu_item) {
                @Override
                public void convert(ViewHolder helper, final GuiGeBean item) {
                    TextView name = helper.getView(R.id.type_name);
                    name.setText(item.getName() + "");
                    MyGridView gridView = helper.getView(R.id.sub_listview);
                    CommonAdapter<String> subAdapter = new CommonAdapter<String>(ctx, item.getStrList(), R.layout.gwc_cs_sub_item) {
                        @Override
                        public void convert(ViewHolder helper, final String subitem) {
                            final TextView name = helper.getView(R.id.goods_name);
                            name.setText(subitem);
                            if (item.getName().equals("规格")) {
                                if (subitem.equals(stylestr)) {
                                    name.setBackgroundResource(R.drawable.canshu_sel);
                                } else {
                                    name.setBackgroundResource(R.drawable.canshu_nol);
                                }
                                if(selstyles.size()>0) {
                                    if (selstyles.contains(subitem)) {
                                        name.setClickable(true);
                                    } else {
                                        name.setBackgroundResource(R.drawable.canshu_noclick);
                                        name.setClickable(false);
                                    }
                                }
                            } else {
                                if (subitem.equals(colorstr)) {
                                    name.setBackgroundResource(R.drawable.canshu_sel);
                                } else {
                                    name.setBackgroundResource(R.drawable.canshu_nol);
                                }
                                if(selcolors.size()>0) {
                                    if (selcolors.contains(subitem)) {
                                        name.setClickable(true);
                                    } else {
                                        name.setBackgroundResource(R.drawable.canshu_noclick);
                                        name.setClickable(false);
                                    }
                                }
                            }
                            name.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (item.getName().equals("规格")) {
                                        if(selstyles.size()>0) {
                                            if(!selstyles.contains(subitem)){
                                                return;
                                            }
                                        }
                                        selcolors.clear();
                                        if(stylestr.equals(subitem)){
                                            stylestr="";
                                            name.setBackgroundResource(R.drawable.canshu_nol);
                                        }else {
                                            stylestr = subitem;
                                            for (GoodsDetailResponse.ContentStyleListBean item : goodsDetailResponse.getContentStyleList()) {
                                                if (item.getStyle() != null && item.getStyle().equals(stylestr)) {
                                                    selcolors.add(item.getColor());
                                                }
                                            }
                                        }
                                        if(selcolors.size()>0) {
                                            if(!selcolors.contains(colorstr)){
                                                colorstr="";
                                            }
                                        }
                                    } else {
                                        if(selcolors.size()>0) {
                                            if(!selcolors.contains(subitem)){
                                                return;
                                            }
                                        }
                                        selstyles.clear();
                                        if(colorstr.equals(subitem)){
                                            colorstr="";
                                            name.setBackgroundResource(R.drawable.canshu_nol);
                                        }else {
                                            colorstr = subitem;
                                            for (GoodsDetailResponse.ContentStyleListBean item : goodsDetailResponse.getContentStyleList()) {
                                                if (item.getColor() != null && item.getColor().equals(colorstr)) {
                                                    selstyles.add(item.getStyle());
                                                }
                                            }
                                        }
                                        if(selstyles.size()>0) {
                                            if(!selstyles.contains(stylestr)){
                                                stylestr="";
                                            }
                                        }
                                    }
                                    styleAdapter.notifyDataSetChanged();
                                    String str="";
                                    try {
                                        if(stylestr!=null
                                                &&!stylestr.equals("")){
                                            str= stylestr+"、";
                                        }
                                        if(colorstr!=null
                                                &&!colorstr.equals("")){
                                            str=str+colorstr+ "、";
                                        }
                                        str= str + num_txt.getText().toString() + "件";
                                        tv_guige.setText(str);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    tv_guige.setText(str);
                                    for (GoodsDetailResponse.ContentStyleListBean item : goodsDetailResponse.getContentStyleList()) {
                                        if(item.getStyle().equals(stylestr)&&item.getColor().equals(colorstr)){
                                            gg_price.setText("￥" + item.getPrice());
                                        }
                                    }
                                    EventBus.getDefault().post(new NoticeEvent("GUIGEREFRESH"));
                                }
                            });
                        }
                    };
                    gridView.setAdapter(subAdapter);
                }
            };
            my_list_view.setAdapter(styleAdapter);
        }

        btn_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num <= 1) return;
                num--;
                num_txt.setText(num + "");
                String str="";
                try {
                    if(stylestr!=null
                            &&!stylestr.equals("")){
                        str= stylestr+"、";
                    }
                    if(colorstr!=null
                            &&!colorstr.equals("")){
                        str=str+colorstr+ "、";
                    }
                    str= str + num_txt.getText().toString() + "件";
                    tv_guige.setText(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tv_guige.setText(str);
                EventBus.getDefault().post(new NoticeEvent("GUIGEREFRESH"));
            }
        });
        btn_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                num_txt.setText(num + "");
                String str="";
                try {
                    if(stylestr!=null
                            &&!stylestr.equals("")){
                        str= stylestr+"、";
                    }
                    if(colorstr!=null
                            &&!colorstr.equals("")){
                        str=str+colorstr+ "、";
                    }
                    str= str + num_txt.getText().toString() + "件";
                    tv_guige.setText(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tv_guige.setText(str);
                EventBus.getDefault().post(new NoticeEvent("GUIGEREFRESH"));

            }
        });
        num_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ctx.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                num=Integer.valueOf(num_txt.getText().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                num=1;
                            }
                            num_txt.setText(num + "");
                            String str="";
                            try {
                                if(stylestr!=null
                                        &&!stylestr.equals("")){
                                    str= stylestr+"、";
                                }
                                if(colorstr!=null
                                        &&!colorstr.equals("")){
                                    str=str+colorstr+ "、";
                                }
                                str= str + num_txt.getText().toString() + "件";
                                tv_guige.setText(str);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tv_guige.setText(str);
                            EventBus.getDefault().post(new NoticeEvent("GUIGEREFRESH"));
                        }
                    });
                }

                return false;
            }
        });
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
