package com.fengqipu.mall.main.acty.index.zfb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.fengqipu.mall.R;
import com.fengqipu.mall.main.acty.index.zfb.util.OrderInfoUtil2_0;

import java.util.Map;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2017081108139963";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "2088721461016800";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "";
	public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQFGV0y6Jr2bVlDNrxJWqtHywkIWSUcFP3k8jHB+A+A2qVygRg/z2Nrgn3QZ58uxFBc9oTdHeMYxr89LWHgGHzegYnUqmfIVI4HXsAQqnzjj8WNpIyk4VzvsXtjM0Y095RERLIWI4U0tuMHuN44mOt7fOJjVOlsYT3DCITNe12XU2SRqSdvflDIhmMG3esoOo01uhpi2fNcsFd43cdGx0xVoUyUG4VVjTTrHKJ8EwZk1uYI8O78MUa1S+K6B5Z/R1sS+f5lM3IhudO2QVHvk/8zxb54C7WAwFiku0vwuUcQZBDIkLq/LbWYnq49EKTQxJ6iKaWsXlg7i4NPTE6Y2udAgMBAAECggEAZSsNOn+qYtSR/IyUR/Of/kP/OLeB6oEdVKCewLKVELZRZzBc9scCr7NfKBEZejfYm/7/R93l+9uzHZ+uv6C0JsSuAXqXxDcfhmuc7MfikjR1s+FJst3HPUjdrjnJ4f8TAhVc/PTBf3m306H00/MTTxPgh9uGurf1x4rrelXJkexP2i8AiAQZ9jOvkLkWN5K/ZBFUjnCzC3YSKSFWnxV2cxO4hywNjC7Ynz3MeXUywsg+r+7q3lYbSxazrZPuENGLh0t1yjI3nJqxM8m6MehvrTipefW+PPmeRBxRWIOBZgTbEeB3b9zMfm7bG9Lw3ICIqPaguODzZHTcBj16TNrHwQKBgQDLlnbyiH/dNo9R0eZKzjU33mQxs6FzEuy32+s3Eh1ujXtUPN9GibFkJn+YwSW7ZovyaX2fFfx3OAI6ZIihIf/12YUV4exJl0cyHlB/GikmPNfAO7Ll4kax6MR53JJ0pNB+enQ9k37ApcBMvsikdF9JwbyBU8H5jHS3Z7kbvlSZRQKBgQC1LAbpD/n5O+InqlDoSLPpIQzOdHaah1CttfnPJ8YN67rRCS0rjK/+DFK23/UkvjoxeRLUUnVyXJ8M2M3cob6UXuEMyM7E12pJ0fqLV7PXCl1IglhdMpOVA+wgdAowN1IWoXwzvFZoDv3qq/nbpqdRh+yuEgyjFKjQOkZA11GyeQKBgE6iOiUpRcHKjga7POjNxs5ibm6oBAbvW1EUd0BZFdcafHUn4dFb45FcMVJt38YHMKybxDKLCm19y5aEOCWMfVGXB4lRLrOp44zfGpZNI3GqdGbCaDHUeRvEXQb4g/Tbp9ACOh2Cd6WvH5xE4Dhs3lUdqFKHaFebwYUJYfzuQbzFAoGALBkbVX9eCRxaX+1E5EVOBWDWDSqr89EcAixZMeMsghbu0hK5u+/yppO5TmoU8WycHY6q1qN7kuqYjDf5Y8q66MJvZP071w3YAT/rDB+81orfSdbwSz8UTSE3SG72S+YrRPbplG4zWgDMLWLO3ngiIeZD5dre2JMexBBrKT0/1YECgYArK4FEzDkas3PZcUj+AHyrUPLdrERvDNmG1kgj9mY1wWyxsnGw0Kf8MH/MeG+VZGgnxNoWWdzGaE/Ehgt9GhqoferYEvPJzqLIsh+l6eSXJVMssRCianF9mICUjVTSHk+m52Bd4k4PlN/Z8NdcNLUrs08auwZxIfpEcp0tHgSoxw==";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
	}
	
	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */
	public void payV2(View v) {
//		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialoginterface, int i) {
//							//
//							finish();
//						}
//					}).show();
//			return;
//		}
//
//		/**
//		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//		 *
//		 * orderInfo的获取必须来自服务端；
//		 */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//		final String orderInfo = orderParam + "&" + sign;
//
//		Runnable payRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				PayTask alipay = new PayTask(PayDemoActivity.this);
//				Map<String, String> result = alipay.payV2(orderInfo, true);
//				Log.i("msp", result.toString());
//
//				Message msg = new Message();
//				msg.what = SDK_PAY_FLAG;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		};
//
//		Thread payThread = new Thread(payRunnable);
//		payThread.start();
	}

	/**
	 * 支付宝账户授权业务
	 * 
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
		 *
		 * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
		 * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
		 * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
		 * 进行测试。
		 * 
		 * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
		 * 可以参考它实现自定义的 URL 拦截逻辑。
		 */
		String url = "http://m.taobao.com";
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

}
