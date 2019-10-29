package com.yixiekeji.web.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * 返回json格式化日期
 *                       
 * @Filename: FastJsonDateConfig.java
 * @Version: 1.0
 * @Author: 齐驱科技
 * @Email: wangpeng@yixiekeji.com
 *
 */
@Configuration
public class FastJsonDateConfig extends FastJsonHttpMessageConverter {

    private static SerializeConfig mapping = new SerializeConfig();
    private static String          dateFormat;

    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Override
    protected void writeInternal(Object obj,
                                 HttpOutputMessage outputMessage) throws IOException,
                                                                  HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = JSONObject.toJSONString(obj, mapping,
            SerializerFeature.WriteMapNullValue.DisableCircularReferenceDetect);
        byte[] bytes = text.getBytes(this.getCharset());
        out.write(bytes);
    }

}
