package com.hyphenate.helpdesk.easeui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.R;
import com.hyphenate.helpdesk.model.AgentInfo;
import com.hyphenate.helpdesk.model.MessageHelper;

/**
 */
public class UserUtil {

    public static void setAgentNickAndAvatar(Context context, Message message, ImageView userAvatarView, TextView usernickView){
        AgentInfo agentInfo = MessageHelper.getAgentInfo(message);
        if (usernickView != null){
            usernickView.setText(message.getFrom());
            if (agentInfo != null){
                if (!TextUtils.isEmpty(agentInfo.getNickname())) {
                    usernickView.setText(agentInfo.getNickname());
                }
            }
        }
        if (userAvatarView != null){
//            userAvatarView.setImageResource(R.drawable.hd_default_avatar);
            String strUrl = "";
            if (agentInfo != null){
                if (!TextUtils.isEmpty(agentInfo.getAvatar())) {
                    strUrl = agentInfo.getAvatar();
                    // 设置客服头像
                    if (!TextUtils.isEmpty(strUrl)) {
                        if (!strUrl.startsWith("http")) {
                            strUrl = "http:" + strUrl;
                        }
                        //正常的string路径
                    }
                }
            }
            Glide.with(context).load(strUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CircleTransform(context))
                    .placeholder(R.drawable.default_head).into(userAvatarView);

        }
    }

    public static void setCurrentUserNickAndAvatar(Context context,ImageView userAvatarView, TextView userNickView){
        if (userAvatarView != null){
//            userAvatarView.setImageResource(R.drawable.hd_default_avatar);
            SharedPreferences sharedPreferences=context.getSharedPreferences("fqpmall",Context.MODE_PRIVATE);
            String pic=sharedPreferences.getString("THUM_PORTRAIT","");
            Glide.with(context).load(pic).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CircleTransform(context))
                    .placeholder(R.drawable.default_head).into(userAvatarView);
        }
    }

} class CircleTransform extends BitmapTransformation {
    public CircleTransform(Context context) {
        super(context);
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override public String getId() {
        return getClass().getName();
    }
}
