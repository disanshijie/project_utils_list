package com.oracle.sun.core.email.demo.method1.core;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
* @ClassName: SendEmail
* @Description: 参考 Project2；还有另一种形式的 Transport 可以参考Project1或3
* @author sun
* @date 2018年5月15日 下午6:14:32
 */
public class SendEmail {
	 static Logger logger = LoggerFactory.getLogger(SendEmail.class);
	 
	/*
	 * 文本
	 */
	public static void sendTextMail(MailInfo info) throws Exception {
        Message message =EmailModule.getMessage(info);
        //消息发送的内容
        message.setText(info.getContent());
        Transport.send(message);
    }
	/*
	 * html
	 */
	public static void sendHtmlMail(MailInfo info)throws Exception{
        Message message = EmailModule.getMessage(info);
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(info.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        message.setContent(mainPart);
        Transport.send(message);
    }
	/*
	 * 发送附件
	 */
	 public static boolean sendMail(MailInfo info) {
	        boolean isFlag = false;
            try {
            	Message message = EmailModule.getMessage(info);
                
            	//整封邮件的MINE消息体
                //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
                Multipart multipart = new MimeMultipart();  
                //MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
                
                /*添加正文内容*/
                if(info.getContent()!=null) {
                	EmailModule.addHtmlContent(info.getContent(), multipart);
                }
                
                //添加附件
                if(info.getFiles()!=null) {
                	EmailModule.addAccessory(info.getFiles(), multipart);
                }

                //设置邮件的MINE消息体
                message.setContent(multipart);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport.send(message);
                isFlag = true;
                logger.info(Calendar.getInstance().getTime()+":#Send mail to"+info.getToAddress()+"success #");
            } catch (Exception e) {
                logger.info(Calendar.getInstance().getTime()+":#Send mail to"+info.getToAddress()+"error #");
                logger.info(e.toString());
                //e.printStackTrace();
                isFlag = false;
            }
	        return isFlag;
	  }
	 /**
	  * 
	 * @Title: sendMail
	 * @Description: 附件是流的形式 
	 * @author sun
	 * @date  2018年5月21日 下午5:26:53
	 * @param info	可以是html内容
	 * @param fileName 附件名
	 * @param is	附件
	 * @return
	  */
	 public static boolean sendMail(MailInfo info,String fileName, InputStream is) {
		 boolean isFlag = false;
		 try {
			 Message message = EmailModule.getMessage(info);
			 //整封邮件的MINE消息体
			 //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
			 Multipart multipart = new MimeMultipart();   
			 //MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
			 
			 /*添加正文内容*/
             if(info.getContent()!=null) {
             	EmailModule.addHtmlContent(info.getContent(), multipart);
             }
             //添加附件
             if(info.getFiles()!=null) {
             	EmailModule.addAccessory(multipart,is,fileName);
             }
			 //设置邮件的MINE消息体
			 message.setContent(multipart);
			 message.setSentDate(new Date());
			 message.saveChanges();
			 Transport.send(message);
			 isFlag = true;
			 logger.info(Calendar.getInstance().getTime()+":#Send mail to"+info.getToAddress()+"success #");
		 } catch (Exception e) {
			 logger.info(Calendar.getInstance().getTime()+":#Send mail to"+info.getToAddress()+"error #");
			 logger.info(e.toString());
			 //e.printStackTrace();
			 isFlag = false;
		 }
		 return isFlag;
	 }
	 
	 
}
