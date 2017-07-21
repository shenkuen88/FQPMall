package com.hyphenate.helpdesk.easeui.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.R;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.emojicon.Emojicon;
import com.hyphenate.helpdesk.easeui.provider.CustomChatRowProvider;
import com.hyphenate.helpdesk.easeui.recorder.MediaManager;
import com.hyphenate.helpdesk.easeui.runtimepermission.PermissionsManager;
import com.hyphenate.helpdesk.easeui.runtimepermission.PermissionsResultAction;
import com.hyphenate.helpdesk.easeui.util.CommonUtils;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.easeui.widget.AlertDialog;
import com.hyphenate.helpdesk.easeui.widget.AlertDialog.AlertDialogUser;
import com.hyphenate.helpdesk.easeui.widget.EaseChatInputMenu;
import com.hyphenate.helpdesk.easeui.widget.EaseChatInputMenu.ChatInputMenuListener;
import com.hyphenate.helpdesk.easeui.widget.ExtendMenu.EaseChatExtendMenuItemClickListener;
import com.hyphenate.helpdesk.easeui.widget.MessageList;
import com.hyphenate.helpdesk.easeui.widget.MessageList.MessageListItemClickListener;
import com.hyphenate.helpdesk.model.AgentIdentityInfo;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.QueueIdentityInfo;
import com.hyphenate.helpdesk.model.VisitorInfo;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.hyphenate.util.PathUtil;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 可以直接new出来使用的聊天对话页面fragment，
 * 使用时需调用setArguments方法传入IM服务号
 * app也可继承此fragment续写
 * 参数传入示例可查看demo里的ChatActivity
 */
public class ChatFragment extends BaseFragment implements ChatManager.MessageListener {

    protected static final String TAG = ChatFragment.class.getSimpleName();
    protected static final int REQUEST_CODE_CAMERA = 1;
    protected static final int REQUEST_CODE_LOCAL = 2;
    private static final int REQUEST_CODE_SELECT_VIDEO = 3;

    public static final int REQUEST_CODE_EVAL = 5;
    public static final int REQUEST_CODE_SELECT_FILE = 6;
    /**
     * 传入fragment的参数
     */
    protected Bundle fragmentArgs;
    protected String toChatUsername;
    protected boolean showUserNick;
    protected MessageList messageList;
    protected EaseChatInputMenu inputMenu;

    protected Conversation conversation;
    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;
    protected String cameraFilePath = null;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected Message contextMenuMessage;

    protected static final int ITEM_TAKE_PICTURE = 1;
    protected static final int ITEM_PICTURE = 2;
    protected static final int ITEM_VIDEO = 3;
    protected static final int ITEM_FILE = 4;
    private static final int ITEM_MAP = 11;
    private static final int REQUEST_CODE_SELECT_MAP = 11;

    protected int[] itemStrings = {R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location};
    protected int[] itemdrawables = {R.drawable.hd_chat_takepic_selector, R.drawable.hd_chat_image_selector, R.drawable.hd_chat_location_selector};

    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_MAP};
    private boolean isMessageListInited;
    protected MyMenuItemClickListener extendMenuItemClickListener;
    private VisitorInfo visitorInfo;
    private AgentIdentityInfo agentIdentityInfo;
    private QueueIdentityInfo queueIdentityInfo;
    private String titleName;
    protected TextView tvTipWaitCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hd_fragment_chat, container, false);
        tol_layout = (LinearLayout) view.findViewById(R.id.tol_layout);
        img = (ImageView) view.findViewById(R.id.img);
        goods_name = (TextView) view.findViewById(R.id.goods_name);
        goods_price = (TextView) view.findViewById(R.id.goods_price);
        goods_num = (TextView) view.findViewById(R.id.goods_num);
        return view;
    }

    private String name, price, desc, imageurl, desurl;
    private String goodsinfo;
    private LinearLayout tol_layout;
    private ImageView img;
    private TextView goods_name, goods_price, goods_num;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        fragmentArgs = getArguments();
        // IM服务号
        toChatUsername = fragmentArgs.getString(Config.EXTRA_SERVICE_IM_NUMBER);
        // 是否显示用户昵称
        showUserNick = fragmentArgs.getBoolean(Config.EXTRA_SHOW_NICK, false);
        //指定技能组
        queueIdentityInfo = fragmentArgs.getParcelable(Config.EXTRA_QUEUE_INFO);
        //指定客服
        agentIdentityInfo = fragmentArgs.getParcelable(Config.EXTRA_AGENT_INFO);
        visitorInfo = fragmentArgs.getParcelable(Config.EXTRA_VISITOR_INFO);

        goodsinfo = fragmentArgs.getString(Config.EXTRA_TITLE_NAME);
        Log.e("sub", "goodsinfo1=" + goodsinfo);
        titleName = "客服聊天";
        //在父类中调用了initView和setUpView两个方法
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            cameraFilePath = savedInstanceState.getString("cameraFilePath");
        }
        ChatClient.getInstance().chatManager().bindChatUI(toChatUsername);
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(String permission) {

            }
        });
    }

    /**
     * init view
     */
    @Override
    protected void initView() {
        // 消息列表layout
        messageList = (MessageList) getView().findViewById(R.id.message_list);
        messageList.setShowUserNick(showUserNick);
        listView = messageList.getListView();
        tvTipWaitCount = (TextView) getView().findViewById(R.id.tv_tip_waitcount);
        extendMenuItemClickListener = new MyMenuItemClickListener();
        inputMenu = (EaseChatInputMenu) getView().findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init();
        inputMenu.setChatInputMenuListener(new ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                // 发送文本消息
                sendTextMessage(content);
            }

            @Override
            public void onBigExpressionClicked(Emojicon emojicon) {
                //发送大表情(动态表情) 暂不支持,后期支持后添加接口

            }

            @Override
            public void onRecorderCompleted(float seconds, String filePath) {
                // 发送语音消息
                int time = seconds > 1 ? (int) seconds : 1;
                sendVoiceMessage(filePath, time);
            }
        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ChatClient.getInstance().chatManager().addVisitorWaitListener(new ChatManager.VisitorWaitListener() {
            @Override
            public void waitCount(final int num) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (num > 0) {
                            tvTipWaitCount.setVisibility(View.VISIBLE);
                            tvTipWaitCount.setText(getString(R.string.current_wait_count, num));
                        } else {
                            tvTipWaitCount.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        ChatClient.getInstance().chatManager().addAgentInputListener(agentInputListener);
        try {
            JSONArray ja=new JSONArray(goodsinfo);
            name = ja.get(0).toString();
            price = ja.get(1).toString();
            desc = ja.get(2).toString();
            imageurl = ja.get(3).toString();
            desurl = ja.get(4).toString();
            if (name != null && !name.equals("")) {
                VisitorTrack track = ContentFactory.createVisitorTrack(null);
                track.title(name)  //显示标题
                        .price("￥" + price) //显示价格
                        .desc(desc) //描述
                        .imageUrl(imageurl)//显示图片
                        .itemUrl(desurl); //点击会跳转到哪
                Message message = Message.createTxtSendMessage("正在询问的商品", toChatUsername);
                message.addContent(track);
//                message.setAttribute("nickname",name);
//                message.setAttribute("avatar",imageurl);
                ChatClient.getInstance().getChat().sendMessage(message, new Callback() {
                    @Override
                    public void onSuccess() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tol_layout.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(imageurl)
                                        .into(img);
                                goods_name.setText(name);
                                goods_price.setText("￥" + price);
                                goods_num.setText("1件");
                            }
                        });

                    }

                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ChatManager.AgentInputListener agentInputListener = new ChatManager.AgentInputListener() {
        @Override
        public void onInputState(final String input) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (input != null) {
                        titleBar.setTitle(input);
                    } else {
                        if (!TextUtils.isEmpty(titleName)) {
                            titleBar.setTitle(titleName);
                        } else {
                            titleBar.setTitle(toChatUsername);
                        }
                    }
                }
            });

        }
    };

    /**
     * 设置属性，监听等
     */
    @Override
    protected void setUpView() {
        if (!TextUtils.isEmpty(titleName)) {
            titleBar.setTitle(titleName);
        } else {
            titleBar.setTitle(toChatUsername);
        }

        titleBar.setRightImageResource(R.drawable.hd_mm_title_remove);

        onConversationInit();
        onMessageListInit();

        // 设置标题栏点击事件
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
        titleBar.setRightLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                emptyHistory();
            }
        });
        setRefreshLayoutListener();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ChatClient.getInstance().chatManager().unBind();
        ChatClient.getInstance().chatManager().removeAgentInputListener(agentInputListener);
    }

    /**
     * 注册底部菜单扩展栏item; 覆盖此方法时如果不覆盖已有item，item的id需大于3
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }

    }

    protected void onConversationInit() {
        // 获取当前conversation对象
        conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);
        if (conversation != null) {
            // 把此会话的未读数置为0
            conversation.markAllMessagesAsRead();
            final List<Message> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                }
                conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
            }
        }

    }

    protected void onMessageListInit() {
        messageList.init(toChatUsername, chatFragmentListener != null ?
                chatFragmentListener.onSetCustomChatRowProvider() : null);
        //设置list item里的控件的点击事件
        setListItemClickListener();

        messageList.getListView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!inputMenu.isVoiceRecording()) {//录音时，点击列表不做操作
                    hideKeyboard();
                    inputMenu.hideExtendMenuContainer();
                }
                return false;
            }
        });

        isMessageListInited = true;
    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if (chatFragmentListener != null) {
                    chatFragmentListener.onAvatarClick(username);
                }
            }

            @Override
            public void onResendClick(final Message message) {
                new AlertDialog(getActivity(), R.string.resend, R.string.confirm_resend, null, new AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (!confirmed) {
                            return;
                        }
                        ChatClient.getInstance().chatManager().reSendMessage(message);
                    }
                }, true).show();
            }

            @Override
            public void onBubbleLongClick(Message message) {
                contextMenuMessage = message;
                if (chatFragmentListener != null) {
                    chatFragmentListener.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(Message message) {
                if (chatFragmentListener != null) {
                    return chatFragmentListener.onMessageBubbleClick(message);
                }
                return false;
            }
        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<Message> messages = null;
                            try {
                                messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                        pagesize);
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages != null && messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
                sendImageMessage(cameraFilePath);
            } else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_SELECT_FILE) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        sendFileByUri(uri);
                    }
                }
            } else if (requestCode == REQUEST_CODE_SELECT_VIDEO) {
                if (data != null) {
                    int duration = data.getIntExtra("dur", 0);
                    String videoPath = data.getStringExtra("path");
                    File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                        ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                        sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if (requestCode == REQUEST_CODE_SELECT_MAP) {
                Log.e("sub","REQUEST_CODE_SELECT_MAP");
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    Log.e("sub","toChatUsername"+toChatUsername);
                    sendLocationMessage(latitude, longitude, locationAddress, toChatUsername);
                } else {
                    Toast.makeText(getActivity(), "不能获取您的位置!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited)
            messageList.refresh();
        MediaManager.resume();
        UIProvider.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        ChatClient.getInstance().chatManager().addMessageListener(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        // unregister this event listener when this activity enters the
        // background
        ChatClient.getInstance().chatManager().removeMessageListener(this);
        // 把此activity 从foreground activity 列表里移除
        UIProvider.getInstance().popActivity(getActivity());
    }


    public void onBackPressed() {
        inputMenu.onBackPressed();
    }

    /**
     * 扩展菜单栏item点击事件
     */
    class MyMenuItemClickListener implements EaseChatExtendMenuItemClickListener {

        @Override
        public void onExtendMenuItemClick(int itemId, View view) {
            if (chatFragmentListener != null) {
                if (chatFragmentListener.onExtendMenuItemClick(itemId, view)) {
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE: // 拍照
                    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(ChatFragment.this, new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            selectPicFromCamera();
                        }

                        @Override
                        public void onDenied(String permission) {

                        }
                    });
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal(); // 图库选择图片
                    break;
                case ITEM_VIDEO:
                    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(ChatFragment.this, new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            selectVideoFromLocal();
                        }

                        @Override
                        public void onDenied(String permission) {

                        }
                    });

                    break;
                case ITEM_FILE:
                    //一般文件
                    //demo这里是通过系统api选择文件，实际app中最好是做成qq那种选择发送文件
                    selectFileFromLocal();
                    break;
                case ITEM_MAP: //地图
                    try {
                        startActivityForResult(new Intent(getActivity(), Class.forName("com.fengqipu.mall.main.acty.BaiduMapActivity")), REQUEST_CODE_SELECT_MAP);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private void selectVideoFromLocal() {
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
    }

    /**
     * 选择文件
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { //19以后这个api不可用，demo这里简单处理成图库选择图片
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }


    /**
     * 根据图库图片uri发送图片
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * 根据uri发送文件
     *
     * @param uri
     */
    protected void sendFileByUri(Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            Toast.makeText(getActivity(), R.string.File_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        //大于10M不让发送
        if (file.length() > 10 * 1024 * 1024) {
            Toast.makeText(getActivity(), R.string.The_file_is_not_greater_than_10_m, Toast.LENGTH_SHORT).show();
            return;
        }
        sendFileMessage(filePath);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (cameraFilePath != null) {
            outState.putString("cameraFile", cameraFilePath);
        }
    }

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera() {
        if (!CommonUtils.isExitsSdcard()) {
            Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        File cameraFile = new File(PathUtil.getInstance().getImagePath(), ChatClient.getInstance().getCurrentUserName()
                + System.currentTimeMillis() + ".jpg");
        cameraFilePath = cameraFile.getAbsolutePath();
        cameraFile.getParentFile().mkdirs();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext().getApplicationContext(), "com.easemob.helpdeskdemo.fileprovider", cameraFile));
        }
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 从图库获取图片
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }


    /**
     * 点击清空聊天记录
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        new AlertDialog(getActivity(), null, msg, null, new AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    MediaManager.release();
                    ChatClient.getInstance().chatManager().clearConversation(toChatUsername);
                    messageList.refresh();
                }
            }
        }, true).show();
    }


    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected EaseChatFragmentListener chatFragmentListener;

    public void setChatFragmentListener(EaseChatFragmentListener chatFragmentListener) {
        this.chatFragmentListener = chatFragmentListener;
    }

    public interface EaseChatFragmentListener {
        /**
         * 用户头像点击事件
         *
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * 消息气泡框点击事件
         */
        boolean onMessageBubbleClick(Message message);

        /**
         * 消息气泡框长按事件
         */
        void onMessageBubbleLongClick(Message message);

        /**
         * 扩展输入栏item点击事件,如果要覆盖EaseChatFragment已有的点击事件，return true
         *
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * 设置自定义chatrow提供者
         *
         * @return
         */
        CustomChatRowProvider onSetCustomChatRowProvider();
    }

    @Override
    public void onMessage(List<Message> msgs) {
        for (Message message : msgs) {
            String username = null;
            username = message.getFrom();

            // 如果是当前会话的消息，刷新聊天页面
            if (username != null && username.equals(toChatUsername)) {
                messageList.refreshSelectLast();
                // 声音和震动提示有新消息
                UIProvider.getInstance().getNotifier().viberateAndPlayTone(message);
            } else {
                // 如果消息不是和当前聊天ID的消息
                UIProvider.getInstance().getNotifier().onNewMsg(message);
            }
        }

    }

    @Override
    public void onCmdMessage(List<Message> msgs) {

    }


    @Override
    public void onMessageStatusUpdate() {
        messageList.refreshSelectLast();
    }

    @Override
    public void onMessageSent() {
        messageList.refreshSelectLast();
    }

    // 发送消息方法
    //=============================================
    protected void sendTextMessage(String content) {
        if (content != null && content.length() > 1500) {
            Toast.makeText(getContext(), R.string.message_content_beyond_limit, Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = Message.createTxtSendMessage(content, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        Message message = Message.createVoiceSendMessage(filePath, length, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        File imageFile = new File(imagePath);
        if (imageFile == null || !imageFile.exists()) {
            return;
        }

        Message message = Message.createImageSendMessage(imagePath, false, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    protected void sendFileMessage(String filePath) {
        Message message = Message.createFileSendMessage(filePath, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress, String toChatUsername) {
        Message message = Message.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        Message message = Message.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        attachMessageAttrs(message);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }


    public void attachMessageAttrs(Message message) {
        if (visitorInfo != null) {
            message.addContent(visitorInfo);
        }
        if (queueIdentityInfo != null) {
            message.addContent(queueIdentityInfo);
        }
        if (agentIdentityInfo != null) {
            message.addContent(agentIdentityInfo);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        MediaManager.pause();
    }


}
