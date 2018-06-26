package com.oracle.sun.core.email.demo.method1.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailModule {
	 static Logger logger = LoggerFactory.getLogger(SendEmail.class);
	 
	 
	 protected static void addHtmlContent(String content,Multipart multipart) throws MessagingException {
		 MimeBodyPart contentPart = new MimeBodyPart();
		 contentPart.setText(content,"UTF-8");
		 contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
		 multipart.addBodyPart(contentPart);
	 }
	 /*
	  * 添加附件 一次添加一个附件 且是流的形式
	  * fileName 给附件命名
	  * is 附件
	  */
	 protected static void addAccessory(Multipart multipart, InputStream is,String fileName) throws IOException, MessagingException{
		 /*添加附件*/
         if(is != null) {
             MimeBodyPart fileBody = new MimeBodyPart();
             DataSource source = new ByteArrayDataSource(is, "application/msexcel");
             fileBody.setDataHandler(new DataHandler(source));
             // 中文乱码问题
             fileBody.setFileName(MimeUtility.encodeText(fileName));
             multipart.addBodyPart(fileBody);
         }
	 }
	 /**
	  * 
	 * @Title: accessory
	 * @Description: 向邮件中添加一个或多个附件 Multipart
	 * @author sun
	 * @date  2018年5月21日 下午5:35:00
	 * @param info
	 * @param multipart
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	  */
	 protected static void addAccessory(List<File> files,Multipart multipart) throws UnsupportedEncodingException, MessagingException {
		 /*添加附件*/
         //List<MimeBodyPart> mbps = new Vector<MimeBodyPart>();
         if (files != null && !files.isEmpty()) {
           for (File f : files) {
             if (!f.exists()) continue;

             MimeBodyPart mbp = new MimeBodyPart();
             //FileDataSource fds = new FileDataSource(f.getAbsolutePath());
             FileDataSource fds = new FileDataSource(f);
             mbp.setDataHandler(new DataHandler(fds));
             mbp.setFileName(MimeUtility.encodeText(fds.getName()));
             //mbps.add(mbp);
             multipart.addBodyPart(mbp);//附件加入到 MINE消息体中
           }
         }
	 }
	 /**
	  * 
	 * @Title: getMessage
	 * @Description: 发送邮件组件  Message 用户名密码在此输入
	 * @author sun
	 * @date  2018年5月21日 下午5:35:06
	 * @param info
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	  */
	 protected static Message getMessage(MailInfo info) throws MessagingException, UnsupportedEncodingException  {
			//	Properties p = new Properties();
		        final Properties p = System.getProperties() ;
		        p.setProperty("mail.smtp.host", info.getSendHost()); // 指定SMTP服务器
			    p.setProperty("mail.transport.protocol", "smtp");
			    p.setProperty("mail.smtps.ssl.enable", "true");
//				    p.setProperty("mail.smtp.port", "25");
		        p.setProperty("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
		        p.setProperty("mail.smtp.user", info.getFromAddress());
		        p.setProperty("mail.smtp.pass", info.getFromPassword());

		     // 根据邮件会话属性和密码验证器构造一个发送邮件的session
		        Session session = Session.getInstance(p, new Authenticator(){
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(p.getProperty("mail.smtp.user"),p.getProperty("mail.smtp.pass"));
		            }
		        });
		        session.setDebug(info.getDebug());
		        MimeMessage message = new MimeMessage(session);
		        //消息发送的主题
		        message.setSubject(info.getSubject());
		        message.addHeader("charset", "UTF-8");
		        
		        //接受回复地址
		        if(info.getReplayAddress()!=null && !"".equals(info.getReplayAddress())) {
		        	message.setReplyTo(InternetAddress.parse(info.getReplayAddress()));
		        }
		        //消息的发送者
		        //message.setFrom(new InternetAddress(info.getFromAddress())); //发送方
		        message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"),info.getToHead(),"UTF-8")); //TODO 这个sun怎么办
		        // 创建邮件的接收者地址，并设置到邮件消息中
		        message.setRecipient(Message.RecipientType.TO,new InternetAddress(info.getToAddress())); 
		        
		        //TODO 发送给多个人
		       /* InternetAddress[] toEmails=new InternetAddress[5];
		        toEmails[0]=new InternetAddress(info.getToAddress());
		        message.setRecipients(Message.RecipientType.TO,toEmails); */
		        
		        //指定抄送人
		        if(info.getCcList()!=null && !"".equals(info.getCcList())) {
		        	message.addRecipients(Message.RecipientType.CC,info.getCcList());
		        }
		        // 消息发送的时间
		        message.setSentDate(new Date());
		        return message ;
		    }
}
