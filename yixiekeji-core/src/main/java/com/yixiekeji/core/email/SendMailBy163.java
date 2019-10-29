package com.yixiekeji.core.email;

import com.yixiekeji.core.EjavashopConfig;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailBy163 {

    public boolean sendMail(String to, String subject, String body) throws MessagingException {
        try {
            Properties props = new Properties();//key value:配置参数。真正发送邮件时再配置
            props.setProperty("mail.transport.protocol", "smtp");//指定邮件发送的协议，参数是规范规定的
            props.setProperty("mail.host", "smtp.163.com");//指定发件服务器的地址，参数是规范规定的
            props.setProperty("mail.smtp.port", EjavashopConfig.MAIL_SERVER_PORT);//邮箱端口
            props.setProperty("mail.smtp.auth", "true");//请求服务器进行身份认证。参数与具体的JavaMail实现有关
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.port", EjavashopConfig.MAIL_SERVER_PORT);
            Session session = Session.getInstance(props);//发送邮件时使用的环境配置
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);

            //设置邮件的头
            message.setFrom(new InternetAddress(EjavashopConfig.SEND_EMAIL_NAME));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject);
            //设置正文
            message.setContent(body,"text/html; charset=utf-8");
            message.saveChanges();

            //发送邮件
            Transport ts = session.getTransport();
            ts.connect(EjavashopConfig.SEND_EMAIL_NAME, EjavashopConfig.SEND_EMAIL_PASSWORD);       // 密码为授权码不是邮箱的登录密码
            ts.sendMessage(message, message.getAllRecipients());//对象，用实例方法}
            return  true;
        }catch (Exception ex){
            throw new RuntimeException(ex.getClass().getName() + " : " + ex.getMessage());
        }
    }
}
