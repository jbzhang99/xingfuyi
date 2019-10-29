package com.yixiekeji.core;

/**
 * TODO：yixiekeji 核心配置类，商城启动时首先配置此配置文件<br/>
 * 其中支付有打印的一些报文，用于替换正式账号之后调试使用，调试通过之后建议去掉<br/>
 * 配置常量类，主要配置有支付方式、短信通道、快递100、邮箱等等
 *                       
 * @Filename: EjavashopConfig.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class EjavashopConfig {

    // -----------邮箱配置start-------------
    /** 邮箱服务提供商host，根据使用的邮箱服务供应商对应填写 */
    public static final String MAIL_SERVER_HOST          = "smtp.ym.163.com";
    /** 邮箱服务提供商port，根据使用的邮箱服务供应商对应填写 */
    public static final String MAIL_SERVER_PORT          = "465";
    /** 发送邮件的邮箱地址 */
    public static final String SEND_EMAIL_NAME           = "qiqu_dev@163.com";
    /** 发送邮件的邮箱密码 */
    public static final String SEND_EMAIL_PASSWORD       = "qiqu123456";

    // -----------邮箱配置end---------------

    // -----------JAVA定时器执行配置start-------------
    //是web项目的定时器，主要对首页进行静态化更新的，可以根据自己的业务逻辑进行调整
    /** java定时器第一次执行时间（毫秒） */
    public static final long   TIME_DELAY                = 60000L;
    /** java定时器执行间隔时间（毫秒） */
    public static final long   TIME_PERIOD               = 600000L;

    public static final long   ACCESS_TOKEN_DELAY        = 0;
    public static final long   ACCESS_TOKEN_PERIOD       = 1000 * 60 * 60 * 2;
    // -----------JAVA定时器执行配置end---------------

    /** 快递100授权key */
    public static String       KUAIDI100_KEY             = "fIlukCnG3384";

    /** 国信互联短信发送URL(向短信供应商申请) 其中URL中包括用户名和密码 */
    public static String       SEND_SMS_URL              = "";

    ///////alipay start////////////
    /**
     * 支付宝，显示订单名称，PC和Mobile公用，一般设置商城的名称
     */
    public final static String ALIPAY_ALL_SUBJECT        = "幸福易商城订单";

    /**
     * HTML5 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
     */
    public static String       ALIPAY_MOBILE_PARTNER     = "2088631264108825";

    /**
     * HTML5 支付宝商户的私钥 HTML5支付采用RSA加密方式
     */
    public static String       ALIPAY_MOBILE_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrsRAhw5dr+aGGE2sqvRQDYrB6u/uOQ9ZXrejqvCZhiE7yXY8VqPbuz1jcobI6b8lzZ91M5HvRo3SVqbKnXrQaG+46TA0x8t6A858MhjezyJ2H4lgGG4d4o8M5zshhXiLKJJJ+gtELdads3gjwL4P+kA0nNX7s2/DzmXpLXioXCXEwPuK123Z7clIFE7vXTBhb9GACMQ+S7IJ/inAntw/7jogkBTlSuAfZt+p5RirmfneMibNNbJ6C0iKB3wj96i8m6zb2YG5mJQ5+L/VFSyIKjSJXis7XIE5R5yxNAc+9p8FX6rBs78YK2siM0nM39cogiEe4d/iej7L7uf7e4USfAgMBAAECggEBAIknE+z080RlERwq7QQiMReXQcV537VBCbvXCGZj0QE+w1onVQMHi+Mvrhvp65d072eKhCVFrIfGXH6mJjo/k/37a+4UDMJm8knu05JMsbijvPvs20ZdvXWo1G6cekQV0BLM7l2zoMzXutxj7p34RygW6Pr2aCNMe4oNg70rRtJJAJxTm6uu0SlIR03yg999spTA2DjdwVaQmHcRq0zEmlj3cnYstaT6YcbQaet7uBqS4RmofKckNLpuhA0o1jd9Fp0eviYO7KETCo6b3+1EokZWJMhwUM86TztAUQ7SRtNUwCdjcxFPAhIve5Hpm+811iLSyGOiGcQ5RKNSoAlDkKECgYEA1vMfLsrETnrq2dZzIf+UlPcUj7veqc3SWDpcMnh0TgUinTajZ5PJaA0QX+qS0UGfei1ItFXnmIO/uD7NYE8Jfj2xlbC209fcShXoST+svkghPfl8Vycgba+VWXE6w3yaLc4wt6keYHl48JsZeCQ5PyH1zkT2cssAlUt2+LX/azECgYEAzHsO2i9j01LQX+6eotcKDJoX+pfYmW9TAAS2Nkx7Eaj1AOE6240zr2qwi3jq/UDPOfIAU2R7R01yHhRHOwpj7htdPd3emM9jxp1IwABVu7CAvK7kAynxXLAIdtIKRpPhQwfBXO4xfGhX9le6wu1uJswpqyUg5tDNDUjztVrdGM8CgYEApr8DubDiiF8iQMrzlbRG9yiVcssy+FcKjhb33s9CqiOhroPsTzV+UJGR2laGfn6B0K3AcOUTPGU4HR3bBJNLsfr6AD2OFtils3M7N1eGEjayUvlvU8yug2hqW3BTPcMzd8U0VcWEIyMYhs5K+8BeCHO308P7yyFf2nEoYdgNxIECgYBSZ4k9vnlhUW9uKbC+eSCwoWzmbUk51FZDnKJ90WaLYGaUGzBoCEh6+ej53Bn/Q53/gRpGgSj5PCmwG4X1MiPBcj+qTf6F4JYaFYqa7cefIutOnsB+0elV7A8NK2o+tRvDsp1nQQqqUzaXrRUztxF8Cy0DrqvKuPzZzLuCK4MMmwKBgQCqnjyGxqyral/7WjPtCqW+JQtrlVjtC4rOhhU7Bqi8mvXo4Wa5ELogIi0BXykr04IQKzgYaAHfYSPqQgnl+XyXz2VevK3Z8Ss/bMC1ug4sYzFd/VpL/1nrtIvFLaWXlbpkW5LlhKkeYbQFFbyacIYeTWpVwpu/SmoCjNW7tj1Tug==";

    /**
     * HTML5 支付宝的公钥，无需修改该值， HTML5支付采用RSA加密方式
     */
    public static String       ALIPAY_MOBILE_PUBLIC_KEY  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq7EQIcOXa/mhhhNrKr0UA2Kwerv7jkPWV63o6rwmYYhO8l2PFaj27s9Y3KGyOm/Jc2fdTOR70aN0lamyp160GhvuOkwNMfLegPOfDIY3s8idh+JYBhuHeKPDOc7IYV4iyiSSfoLRC3WnbN4I8C+D/pANJzV+7Nvw85l6S14qFwlxMD7itdt2e3JSBRO710wYW/RgAjEPkuyCf4pwJ7cP+46IJAU5UrgH2bfqeUYq5n53jImzTWyegtIigd8I/eovJus29mBuZiUOfi/1RUsiCo0iV4rO1yBOUecsTQHPvafBV+qwbO/GCtrIjNJzN/XKIIhHuHf4no+y+7n+3uFEnwIDAQAB";

    /**
     * PC 支付宝：合作身份者ID，以2088开头由16位纯数字组成的字符串
     */
    public static String       ALIPAY_PC_PARTNER         = "2088631264108825";

    /**
     * PC 支付宝，收款支付宝账号，一般情况下收款账号就是签约账号
     */
    public static String       ALIPAY_PC_SELLER_EMAIL    = "shiaizhou2000@163.com";

    /**
     * PC 支付宝，商户的私钥，PC支付采用MD5 方式
     */
    public static String       ALIPAY_PC_KEY             = "o7bh64esj7622bocwu241vuqd5afjthe";

    ///////alipay end///////////

    ////////银联支付 start 根据业务需求acp_sdk.properties中key 和 value 值进行替换/////////////
    /**
     * 银联支付，用户ID
     */
    public final static String UNIONPAY_MERID            = "777290058126056";

    /**
     * 银联支付，请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节  
     */
    public final static String UNIONPAY_REQRESERVED      = "齐驱科技";

    /** 配置文件中的前台URL常量. 银联支付中对应的文件：acpsdk.frontTransUrl */
    public static final String SDK_FRONT_URL             = "https://101.231.204.80:5000/gateway/api/frontTransReq.do";
    /** 配置文件中的后台URL常量. 银联支付中对应的文件：acpsdk.backTransUrl*/
    public static final String SDK_BACK_URL              = "https://101.231.204.80:5000/gateway/api/backTransReq.do";
    /** 配置文件中的单笔交易查询URL常量. 银联支付中对应的文件：acpsdk.singleQueryUrl*/
    public static final String SDK_SIGNQ_URL             = "https://101.231.204.80:5000/gateway/api/queryTrans.do";
    /** 配置文件中的批量交易查询URL常量. 银联支付中对应的文件：acpsdk.batchQueryUrl*/
    public static final String SDK_BATQ_URL              = "acpsdk.batchQueryUrl";
    /** 配置文件中的批量交易URL常量. 银联支付中对应的文件：acpsdk.batchTransUrl*/
    public static final String SDK_BATTRANS_URL          = "https://101.231.204.80:5000/gateway/api/batchTransReq.do";
    /** 配置文件中的文件类交易URL常量. 银联支付中对应的文件：acpsdk.fileTransUrl*/
    public static final String SDK_FILETRANS_URL         = "https://101.231.204.80:9080/";
    /** 配置文件中的有卡交易URL常量. 银联支付中对应的文件：acpsdk.cardTransUrl*/
    public static final String SDK_CARD_URL              = "acpsdk.cardTransUrl";
    /** 配置文件中的app交易URL常量. 银联支付中对应的文件：acpsdk.appTransUrl*/
    public static final String SDK_APP_URL               = "acpsdk.appTransUrl";

    /** 配置文件中签名证书路径常量. 银联支付中对应的文件：acpsdk.signCert.path*/
    //    public static final String SDK_SIGNCERT_PATH         = "/Users/wpjava/Downloads/ACPSample_B2C/src/assets/700000000000001_acp.pfx";
    public static final String SDK_SIGNCERT_PATH         = "C:/Users/Administrator/Desktop/assets/700000000000001_acp.pfx";
    /** 配置文件中签名证书密码常量. 银联支付中对应的文件：acpsdk.signCert.pwd*/
    public static final String SDK_SIGNCERT_PWD          = "000000";
    /** 配置文件中签名证书类型常量. 银联支付中对应的文件：acpsdk.signCert.type*/
    public static final String SDK_SIGNCERT_TYPE         = "PKCS12";
    /** 配置文件中密码加密证书路径常量. 银联支付中对应的文件：acpsdk.encryptCert.path*/
    //    public static final String SDK_ENCRYPTCERT_PATH      = "/Users/wpjava/Downloads/ACPSample_B2C/src/assets/acp_test_enc.cer";
    public static final String SDK_ENCRYPTCERT_PATH      = "C:/Users/Administrator/Desktop/assets/acp_test_enc.cer";
    /** 配置文件中磁道加密证书路径常量. 银联支付中对应的文件：acpsdk.encryptTrackCert.path*/
    public static final String SDK_ENCRYPTTRACKCERT_PATH = "acpsdk.encryptTrackCert.path";
    /** 配置文件中验证签名证书目录常量. 银联支付中对应的文件：acpsdk.validateCert.dir*/
    //    public static final String SDK_VALIDATECERT_DIR      = "/Users/wpjava/Downloads/ACPSample_B2C/src/assets/";
    public static final String SDK_VALIDATECERT_DIR      = "C:/Users/Administrator/Desktop/assets/";

    /** 配置文件中是否加密cvn2常量. 银联支付中对应的文件：acpsdk.cvn2.enc*/
    public static final String SDK_CVN_ENC               = "acpsdk.cvn2.enc";
    /** 配置文件中是否加密cvn2有效期常量. 银联支付中对应的文件：acpsdk.date.enc*/
    public static final String SDK_DATE_ENC              = "acpsdk.date.enc";
    /** 配置文件中是否加密卡号常量. 银联支付中对应的文件：acpsdk.pan.enc*/
    public static final String SDK_PAN_ENC               = "acpsdk.pan.enc";
    /** 配置文件中证书使用模式 银联支付中对应的文件：true*/
    public static final String SDK_SINGLEMODE            = "true";

    ////////银联支付 end//////////////

    //////////微信支付 start////////////////////
    /**
     * 微信支付显示的订单名称
     */
    public static final String WEIXIN_ORDER_NAME         = "幸福易订单";

    //////////微信支付 end/////////////////////

    //------------------------短信发送相关参数bg--------------------------//
    /**
     * 默认固定线程数
     */
    public static final int    DEFAULT_RUN_THREAD_NUM    = 2;

    /**
     * 用户每日最多获取的短信验证码数量
     */
    public static final int    SMS_MAX_GIVEN_NUM         = 5;

    public static final String SMS_URL                   = "http://dx.ipyy.net/smsJson.aspx";
    public static final String SMS_VERIFY_CODE           = "您好，您已成功注册,您的验证码是@【齐驱科技】";

    //------------------------短信发送相关参数ed--------------------------//

    //-------------------------h5微信支付 bg-------------------------//
    /** 开发者测试验证token */
    public static final String WXPAY_VALIDATE_TOKEN      = "yixiekeji";
    /** 微信支付商户号 */
    public static final String WXPAY_APPID               = "wx9279d201a8b5fc3f";
    /** 应用密钥 */
    public static final String WXPAY_APPSECRET           = "0d5ac70e311c77a9ea75d0695467543d";
    /** 商户号 */
    public static final String WXPAY_PARTNER             = "1555310231";
    /** 商户支付密钥Key。审核通过后，在微信发送的邮件中查看 */
    public static final String WXPAY_PARTNER_KEY         = "1EDD8D30998D9CFA3458CE6BD46FDD3A";
    /**网页授权地址，无需修改*/
    public static final String WXPAY_OAUTH2_URL          = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    /**获取网页授权access-token地址,无需修改*/
    public static final String WXPAY_OAUTH2_TOKEN        = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**创建订单,微信内部操作,无需修改*/
    public static final String WXPAY_CREATE_ORDER_URL    = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**微信访问token,无需修改*/
    public static final String WXPAY_ACCESS_TOKEN        = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    /**获取微信用户身份信息*/
    public static final String WXPAY_USER_INFO           = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    /**
     * 应用授权作用域,有两种:
     * 1、snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
     * 2、snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     */
    public static final String WXPAY_SCOPE_BASE          = "snsapi_base";

    public static final String WXPAY_SCOPE_USERINFO      = "snsapi_userinfo";
    /**重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节*/
    public static final String WXPAY_STATE               = "ejs";
    /**支付成功后,由公共号发送此消息给用户*/
    public static final String WXPAY_PAYSUCCESS_MESSAGE  = "您的订单:[ORERID]已经支付成功！欢迎关注我们，祝您生活愉快SUFFIX";
    //-------------------------h5微信支付 ed-------------------------//

    //-------------------------app支付宝支付 bg-------------------------//
    /**
     * app 获取签名请求地址
     */
    public static String       ALIPAY_APP_URL            = "https://openapi.alipay.com/gateway.do";
    /**
     * app 支付宝分配给开发者的应用ID
     */
    public static String       ALIPAY_APP_ID             = "2019092467807114";

    /**
     * app 支付宝商户的私钥 app支付采用RSA加密方式
     */
    public static String       ALIPAY_APP_PRIVATE_KEY    = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrsRAhw5dr+aGGE2sqvRQDYrB6u/uOQ9ZXrejqvCZhiE7yXY8VqPbuz1jcobI6b8lzZ91M5HvRo3SVqbKnXrQaG+46TA0x8t6A858MhjezyJ2H4lgGG4d4o8M5zshhXiLKJJJ+gtELdads3gjwL4P+kA0nNX7s2/DzmXpLXioXCXEwPuK123Z7clIFE7vXTBhb9GACMQ+S7IJ/inAntw/7jogkBTlSuAfZt+p5RirmfneMibNNbJ6C0iKB3wj96i8m6zb2YG5mJQ5+L/VFSyIKjSJXis7XIE5R5yxNAc+9p8FX6rBs78YK2siM0nM39cogiEe4d/iej7L7uf7e4USfAgMBAAECggEBAIknE+z080RlERwq7QQiMReXQcV537VBCbvXCGZj0QE+w1onVQMHi+Mvrhvp65d072eKhCVFrIfGXH6mJjo/k/37a+4UDMJm8knu05JMsbijvPvs20ZdvXWo1G6cekQV0BLM7l2zoMzXutxj7p34RygW6Pr2aCNMe4oNg70rRtJJAJxTm6uu0SlIR03yg999spTA2DjdwVaQmHcRq0zEmlj3cnYstaT6YcbQaet7uBqS4RmofKckNLpuhA0o1jd9Fp0eviYO7KETCo6b3+1EokZWJMhwUM86TztAUQ7SRtNUwCdjcxFPAhIve5Hpm+811iLSyGOiGcQ5RKNSoAlDkKECgYEA1vMfLsrETnrq2dZzIf+UlPcUj7veqc3SWDpcMnh0TgUinTajZ5PJaA0QX+qS0UGfei1ItFXnmIO/uD7NYE8Jfj2xlbC209fcShXoST+svkghPfl8Vycgba+VWXE6w3yaLc4wt6keYHl48JsZeCQ5PyH1zkT2cssAlUt2+LX/azECgYEAzHsO2i9j01LQX+6eotcKDJoX+pfYmW9TAAS2Nkx7Eaj1AOE6240zr2qwi3jq/UDPOfIAU2R7R01yHhRHOwpj7htdPd3emM9jxp1IwABVu7CAvK7kAynxXLAIdtIKRpPhQwfBXO4xfGhX9le6wu1uJswpqyUg5tDNDUjztVrdGM8CgYEApr8DubDiiF8iQMrzlbRG9yiVcssy+FcKjhb33s9CqiOhroPsTzV+UJGR2laGfn6B0K3AcOUTPGU4HR3bBJNLsfr6AD2OFtils3M7N1eGEjayUvlvU8yug2hqW3BTPcMzd8U0VcWEIyMYhs5K+8BeCHO308P7yyFf2nEoYdgNxIECgYBSZ4k9vnlhUW9uKbC+eSCwoWzmbUk51FZDnKJ90WaLYGaUGzBoCEh6+ej53Bn/Q53/gRpGgSj5PCmwG4X1MiPBcj+qTf6F4JYaFYqa7cefIutOnsB+0elV7A8NK2o+tRvDsp1nQQqqUzaXrRUztxF8Cy0DrqvKuPzZzLuCK4MMmwKBgQCqnjyGxqyral/7WjPtCqW+JQtrlVjtC4rOhhU7Bqi8mvXo4Wa5ELogIi0BXykr04IQKzgYaAHfYSPqQgnl+XyXz2VevK3Z8Ss/bMC1ug4sYzFd/VpL/1nrtIvFLaWXlbpkW5LlhKkeYbQFFbyacIYeTWpVwpu/SmoCjNW7tj1Tug==";

    /**
     * app 支付宝的公钥，无需修改该值， app支付采用RSA加密方式
     */
    public static String       ALIPAY_APP_PUBLIC_KEY     = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhawK1bIVZU3t7fkTiIkdYj1bSeQeDGwu/Gz30l5QEHg9LMhpaB5FpL7jejwyu6YZaiieHehJ+HDj6uiTAJgkdj2M5cmcD9pcbxLi3JXXjNNfSlZgNz2OCxAFqyoapNM77l67LlqTMDfgMTZB7//0REiYc8tAAjEfVZqlFAfALDHd7SV/TLYpKpoeEve5RGBx84egUP93OhrGqQ3hzeXG0NQsjY71wbXj7E0Bx4C4AAS2GtCXVUs2YaAdTlvOgG9ZvgFnxXNDeNwBaj4sXTVn7WOqu8pOC9D+UMZWmqzX/Lkj84F3ZEL07MvsVrzrtG+eEM45mkpP5tmL4ra67luwtwIDAQAB";
    //-------------------------app支付宝支付 ed-------------------------//

    //-------------------------app微信支付 ed-------------------------//
    /** 微信支付商户号 */
    public static final String WXPAY_APPID_APP           = "wx43a16ede3c1b527a";
    /** 应用密钥 */
    public static final String WXPAY_APPSECRET_APP       = "134aefb9713e867c731a23da08688538";
    /** 商户号 */
    public static final String WXPAY_PARTNER_APP         = "1555310231";
    /** 商户支付密钥Key */
    public static final String WXPAY_PARTNER_KEY_APP     = "1EDD8D30998D9CFA3458CE6BD46FDD3A";
    //-------------------------app微信支付 ed-------------------------//
    //-------------------------H5登陆接口Token-------------------------//
    public static final String H5_APP_LOGIN_TOKEN        = "JIUYGB896kpsljbhtyUYV821";
    //-------------------------H5登陆接口Token-------------------------//
    //-------------------------XCX微信支付 ed-------------------------//
    /** 微信支付商户号 */
    public static final String WXPAY_APPID_XCX           = "wx9279d201a8b5fc3f";
    /** 应用密钥 */
    public static final String WXPAY_APPSECRET_XCX       = "0d5ac70e311c77a9ea75d0695467543d";
    /** 商户号 */
    public static final String WXPAY_PARTNER_XCX         = "1555310231";
    /** 商户支付密钥Key */
    public static final String WXPAY_PARTNER_KEY_XCX     = "1EDD8D30998D9CFA3458CE6BD46FDD3A";

    public static final String WXPAY_OAUTH2_TOKEN_XCX    = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    //-------------------------XCX微信支付 ed-------------------------//
}
