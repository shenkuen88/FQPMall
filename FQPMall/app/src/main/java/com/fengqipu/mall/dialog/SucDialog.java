package com.fengqipu.mall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengqipu.mall.R;
import com.fengqipu.mall.tools.V;


/**
 * jw
 */
public class SucDialog extends Dialog {
	private Context context;
	private String title;

	public boolean needCloseActy=false;

	public SucDialog(Context context, String title) {
		super(context, R.style.MyDialog);
		this.context = context;
		this.title = title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View layout = getLayoutInflater().inflate(R.layout.dialog_showmsg, null);
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		float scaleWidth = (float) dm.heightPixels / 720f;
		float scaleHeight = (float) dm.heightPixels / 1280f;
		int swidth=(int)(scaleWidth*250);
		int sheight=(int)(scaleHeight*230);
		this.setContentView(layout, new LinearLayout.LayoutParams(swidth, sheight));
		TextView tv_msg=V.f(layout,R.id.tv_msg);
		tv_msg.setText(title+"");
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
