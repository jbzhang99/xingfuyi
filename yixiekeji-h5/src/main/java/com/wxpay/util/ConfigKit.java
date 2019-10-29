//package com.wxpay.util;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.Properties;
//
///**
// * 微信支付配置工具类
// *                       
// * @Filename: ConfigKit.java
// * @Version: 1.0
// * @Author: zhaihl
// * @Email: zhaihl_0@126.com
// *
// */
//public class ConfigKit {
//
//    private static final Properties prop = new Properties();
//
//    static {
//        Reader reader = null;
//        try {
//            reader = new FileReader(new File(ConfigKit.class.getResource("/").getPath()
//                                             + "/properties-config/wxpayConfig.properties"));
//            prop.load(reader);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    //    public static String get(String key) {
//    //        try {
//    //            String val = prop.getProperty(key);
//    //            if (val == null || "".equals(val))
//    //                throw new IllegalArgumentException("没有此配置");
//    //            return val.trim();
//    //        } catch (Exception e) {
//    //            e.printStackTrace();
//    //        }
//    //        return null;
//    //    }
//
//}
