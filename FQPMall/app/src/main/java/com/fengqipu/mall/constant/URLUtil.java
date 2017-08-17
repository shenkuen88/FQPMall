package com.fengqipu.mall.constant;

public class URLUtil
{
    /**
     * 测试环境
     */
    public static final String SERVER_BASE = "http://api.fengqipu.com/";
//    public static final String SERVER_BASE = "http://221.226.118.110:18080/api/";

    /**
     * 默认url
     */
    public static final String DEFAULT_WEB_URL = "http://baidu.com/";
    /**
     * 上传图片
     */
    public static final String UPLOAD_PIC = SERVER_BASE + "file/upload";

    /**
     * 应用初始化
     */
    public static final String INIT_APP = SERVER_BASE + "app/init";
    /**
     * 应用初始化
     */
    public static final String ONEBUTTONSHOP = SERVER_BASE + "shop/apply";

    /**
     * 获取Banner
     */
    public static final String GET_BANNER_LIST = SERVER_BASE + "banner/list";
    /**
     * 获取推广商家列表
     */
    public static final String GET_SHOPS_PROMOTE_LIST = SERVER_BASE + "shop/promote";
    /**
     * 查询栏目列表
     */
    public static final String GET_COLUMN_LIST = SERVER_BASE + "column/list";
    /**
     * 查询活动
    */
    public static final String GET_HUODONG_LIST = SERVER_BASE + "activity/detail";

    /**
     * 查询企业详情
     */
    public static final String GET_SHOP_DETAIL = SERVER_BASE + "shop/detail";

    /**
     * 查询推荐商品
     */
    public static final String GET_TUIJIAN_LIST = SERVER_BASE + "content/recommend";
    /**
     * 查询我的足迹收藏数量
     */
    public static final String GET_USER_COUNT = SERVER_BASE + "user/getCount";
    /**
     * 查询栏目内容
     */
    public static final String GET_COLUMN_LIST_CONTENT = SERVER_BASE + "content/list";

    /**
     * 查询内容详情
     */
    public static final String GET_CONTENT_DETAIL = SERVER_BASE + "content/detail";

    /**
     * 查询内容评价列表
     */
    public static final String GET_COMMENT_LIST = SERVER_BASE + "appraise/list";
    /**
     * 查询内容评价列表
     */
    public static final String GET_HOTKEYWORD_LIST = SERVER_BASE + "hotKeywords/list";
    /**
     * 添加内容评价
     */
    public static final String ADD_PRODUCT_COMMENT = SERVER_BASE + "appraise/add";

    /**
     * 查询系统消息
     */
    public static final String SYSTEM_MESSAGE = SERVER_BASE + "message/list";

    /**
     * 查询通知
     */
    public static final String NOTICE_LIST = SERVER_BASE + "notice/list";

    /**
     * 查询热搜词
     */
    public static final String GET_KEY_WORDS_LIST = SERVER_BASE + "hotKeyword/list";

    /**
     * 1.2.9 查询品牌列表
     */
    public static final String GET_BRAND_LIST = SERVER_BASE + "brand/list";

    /**
     * 根据关键词搜索
     */
    public static final String SEARCH_KEYWORD = SERVER_BASE + "content/search";
    /**
     * 根据关键词搜索企业
     */
    public static final String SEARCH_SHOPS_KEYWORD = SERVER_BASE + "shop/search";
    /**
     * 获取短信验证码
     */
    public static final String GET_YZM_CODE = SERVER_BASE + "smsVerifyCode/send";

    /**
     * 校验短信验证码
     */
    public static final String CHECK_YZM_CODE = SERVER_BASE + "smsVerifyCode/verify";

    /**
     * 注册
     */
    public static final String REGISTER_NEXT = SERVER_BASE + "user/register";

    /**
     * 修改密码
     */
    public static final String EDIT_PASSWORD = SERVER_BASE + "user/updatePassword";

    /**
     * 新增收货地址
     */
    public static final String ADD_RECEIVE_ADDRESS = SERVER_BASE + "userAddress/add";

    /**
     * 获取收货地址列表
     */
    public static final String GET_RECEIVE_ADDRESS_ADDRESS = SERVER_BASE + "userAddress/list";

    /**
     * 修改收货地址
     */
    public static final String EDIT_RECEIVE_ADDRESS = SERVER_BASE + "userAddress/update";

    /**
     * 删除收货地址
     */
    public static final String DELETE_ADDRESS = SERVER_BASE + "userAddress/delete";

    /**
     * 优惠券
     */
    public static final String SEARCH_COUPON = SERVER_BASE + "coupon/list";

    /**
     * 用户登录
     */
    public static final String USER_LOGIN = SERVER_BASE + "user/login";

    /**
     * 点赞
     */
    public static final String ADD_ZAN = SERVER_BASE + "good/add";

    /**
     * 评论
     */
    public static final String ADD_COMMENT = SERVER_BASE + "comment/add";

    public static final String ARTICAL_DDETAIL = SERVER_BASE + "article/detail";

    /**
     * 删除帖子
     */
    public static final String DELETE_COMMUNITY = SERVER_BASE + "article/delete";

    public static final String DELETE_COMMUNITY_COMMENT = SERVER_BASE + "comment/delete";

    /**
     * 发布帖子
     */
    public static final String ADD_COMMUNITY = SERVER_BASE + "article/add";

    /**
     * 获取帖子评论列表
     */
    public static final String GET_COMMUNITY_COMMENT_LIST = SERVER_BASE + "comment/list";

    /**
     * 上传图片
     */
    public static final String GET_UP_LOAD_PIC_URL = SERVER_BASE + "file/getUpToken";

    /**
     * 修改用户信息
     */
    public static final String UPDATE_USER_INFO = SERVER_BASE + "user/update";

    /**
     * 获取订单列表
     */
    public static final String GET_ORDER_LIST = SERVER_BASE + "order/list";

    /**
     * 获取订单列表详情
     */
    public static final String GET_ORDER_DETAIL = SERVER_BASE + "order/detail";
    /**
     * 获取订单列表详情
     */
    public static final String REMINDDELIVER = SERVER_BASE + "order/remindDeliver";

    /**
     * 订单退款
     */
    public static final String ORDER_REFUND = SERVER_BASE + "order/refund";

    /**
     * 获取分类列表
     */
    public static final String GET_CATEGORY_LIST = SERVER_BASE + "category/list";

    /**
     * 获取分类右侧列表
     */
    public static final String GET_CATEGORY_RIGHT = SERVER_BASE + "content/search";

    /**
     * 搜索内容
     */
    public static final String GET_CATEGORY_FOUND = SERVER_BASE + "content/search";

    /**
     * 查询物流信息
     */
    public static final String Logistics = SERVER_BASE + "delivery/detail";

    /**
     * 获取播放地址
     */
    public static final String PLAY_VIDEO = SERVER_BASE + "content/getPlayUrl";

    /**
     * 获取商品详情
     */
    public static final String GET_GOODS_DETAIL = SERVER_BASE + "content/detail";

    /**
     * content/detail
     * 获取播放评论
     */
    public static final String VIDEO_PL = SERVER_BASE + "appraise/list";

    /**
     * 查询帖子列表
     */
    public static final String GET_COMMUNITY_LIST = SERVER_BASE + "article/list";

    /**
     * 获取收货地址
     */
    public static final String GET_ADDRESS_LIST = SERVER_BASE + "userAddress/list";

    /**
     * 添加商品到购物车
     */
    public static final String ADD_TO_BUY_CAR = SERVER_BASE + "cart/add";

    /**
     * 创建订单
     */
    public static final String ADD_ORDER = SERVER_BASE + "order/add";

    /**
     * 查询购物车列表
     */
    public static final String GET_CART_LIST = SERVER_BASE + "cart/list";

    /**
     * 购物车数量修改
     */
    public static final String SET_CART_NUM = SERVER_BASE + "cart/update";

    /**
     * 购物车删除
     */
    public static final String DEL_CART_NUM = SERVER_BASE + "cart/delete";

    /**
     * 购物车数量修改
     */
    public static final String DEL_CART = SERVER_BASE + "cart/delete";

    /**
     * 查询我的收藏列表
     */
    public static final String GET_FAVOUR_LIST = SERVER_BASE + "favorite/list";

    /**
     * 删除我的收藏
     */
    public static final String DEL_FAVOUR = SERVER_BASE + "favorite/delete";

    /**
     * 收藏内容
     */
    public static final String AddFavour = SERVER_BASE + "favorite/add";
    /**
     * 热门城市
     */
    public static final String HOTCITY = SERVER_BASE + "address/hot";


    /**
     * 意见反馈
     */
    public static final String FEEDBACK = SERVER_BASE + "feedback/add";

    /**
     * 登录
     */
    public static final String LOGIN = SERVER_BASE + "login";

    /**
     * 浏览历史列表
     */
    public static final String GET_HISTORY_LIST = SERVER_BASE + "userOperation/list";

    /**
     * 删除浏览历史
     */
    public static final String DEL_HISTORY_GOODS = SERVER_BASE + "userOperation/delete";

    /**
     * 更新订单状态
     */
    public static final String UPDATA_ORDER = SERVER_BASE + "order/update";

    /**
     * 删除订单
     */
    public static final String D_ORDER = SERVER_BASE + "order/delete";

    /**
     * 找相似
     */
    public static final String GETSIMILAR = SERVER_BASE + "content/similar";

    /**
     * 查询是否有这个用户
     */
    public static final String SEARCH_USER = SERVER_BASE + "user/get";
    /**
     * 第三方登录绑定帐号
     */
    public static final String BIND_USER = SERVER_BASE + "user/bind";
    /**
     * 获取快修地址
     */
    public static final String GET_SHOP_O2O = SERVER_BASE + "shop/o2o";
}
