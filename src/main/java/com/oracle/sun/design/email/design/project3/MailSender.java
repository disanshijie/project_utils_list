package com.oracle.sun.design.email.design.project3;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private final Properties prop = new Properties();
    private final Session session;
    private final Message msg;
    private final Transport transport;

    //构建器
    public static class Builder{
        private final String mailContent;
        private final String toAddress;

        private String debug = "true";
        private String auth = "true";
        private String host = "smtp.163.com";
        private String protocol = "smtp";

        private String subject = "通知邮件";
        //发件人地址
        private String fromAddress= "15314537570@163.com";
        //发件账户密码
        private String fromCount = "15314537570";
        private String fromPassword = "qwe123asd456";
        public Builder Debug(String debug) {
            this.debug = debug;
            return this;
        }
        public Builder Subject(String subject) {
            this.subject = subject;
            return this;
        }
        public Builder Auth(String auth) {
            this.auth = auth;
            return this;
        }
        public Builder Host(String host) {
            this.host = host;
            return this;
        }
        public Builder FromCount(String fromCount) {
            this.fromCount = fromCount;
            return this;
        }
        public Builder FromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
            return this;
        }
        public Builder FromPassword(String fromPassword) {
            this.fromPassword = fromPassword;
            return this;
        }
        public Builder(String mailContent, String toAddress) {
            this.mailContent = mailContent;
            this.toAddress = toAddress;
        }
        public Builder Protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }
        public MailSender send() throws Exception{
            return new MailSender(this);
        }

    }
    private MailSender(Builder builder) throws Exception{
        prop.setProperty("mail.debug", builder.debug);
        prop.setProperty("mail.smtp.auth", builder.auth);
        prop.setProperty("mail.host", builder.host);
        prop.setProperty("mail.transport.protocol",builder.protocol);

        session = Session.getInstance(prop);
        msg = new MimeMessage(session);
        transport = session.getTransport();
        msg.setSubject(builder.subject);
        msg.setFrom(new InternetAddress(builder.fromAddress,"通知邮件"));
        transport.connect(builder.fromCount,builder.fromPassword);
        //在这里contentType要设置成text/html,编码格式也要视情况而设定
        msg.setContent(builder.mailContent, "text/html;charset=utf-8");
        transport.sendMessage(msg, new Address[] {new InternetAddress(builder.toAddress)});
    }

}