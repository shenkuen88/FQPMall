package com.fengqipu.mall.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fengqipu.mall.R;
import com.fengqipu.mall.main.acty.index.wx.Constants;
import com.fengqipu.mall.main.acty.mine.PaySucActivity;
import com.fengqipu.mall.tools.CMLog;
import com.fengqipu.mall.view.citylist.utils.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{


	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		CMLog.e("sub", "onPayFinish, errCode = " + resp.errCode);
		if(resp.errCode == 0){
			startActivity(new Intent(this,PaySucActivity.class));
			finish();
		}else {
			ToastUtils.showToast(this,"支付失败!");
			finish();
		}
	}
}
