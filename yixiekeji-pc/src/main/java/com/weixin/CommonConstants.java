package com.weixin;

public class CommonConstants {

    /** 
     * 在公众平台里面：开发者ID，应用ID，在基本配置里面 APPID
     */
    public static String APPID               = "wx9279d201a8b5fc3f";

    /**
     * 在公众平台里面：商户号
     */
    public static String MCHID               = "1555310231";

    /**
     * 在公众平台里面：在基本配置里面，应用秘钥，APPSecret
     */
    public static String APPSECRET           = "0d5ac70e311c77a9ea75d0695467543d";

    /**
     * 商户平台里面：商户支付密钥Key。API安全中API秘钥设置
     */
    public static String KEY                 = "1EDD8D30998D9CFA3458CE6BD46FDD3A";

    //    /**
    //     * 异步回调地址
    //     */
    //    public static String NOTIFY_URL          = DomainUrlUtil.EJS_URL_RESOURCES + "/wx/notify.html";

    /**
     * 生成二维码数据的连接two-dimensional code data
     */
    public static String CREATEORDERURL      = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 虚拟商品价
     */
    public static String VIRTUALPRODUCTPRICE = "1000";

}
