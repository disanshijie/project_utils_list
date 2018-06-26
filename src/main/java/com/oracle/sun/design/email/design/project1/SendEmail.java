package com.oracle.sun.design.email.design.project1;

import java.util.Date;
import java.util.Properties;
import com.oracle.sun.design.email.util.Constant;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * 
* @ClassName: SendEmail
* @Description: 最简单的发送邮件，<br>依赖jar： javax.mail-1.6.1.jar	activation-1.1.jar
* @author sun
* @date 2018年5月14日 下午5:11:51
 */
public class SendEmail {

	public static void main(String[] args) throws MessagingException {
		for (int j = 0; j < 10; j++) {
			sendTxtMail("测试","hello word"+j);
		}
	}
	public  static void sendTxtMail(String subject,String context) throws MessagingException {
		 Properties prop = new Properties();
	        prop.setProperty("mail.host", Constant.SEND_HOST);
		    prop.setProperty("mail.transport.protocol", "smtp");
		    prop.setProperty("mail.smtps.ssl.enable", "true");
//		    prop.setProperty("mail.smtp.port", "25");
		    prop.setProperty("mail.smtp.auth", "true");
		    
	       Session session = Session.getInstance(prop);  //创建应用会话
	       
	       Message message = new MimeMessage(session);	 //消息摘要，是邮件的主体
	       message.setSubject(subject);       //设置主题
	       message.setText(context);    //邮件内容
	       message.setSentDate(new Date());  //发送日期
	       message.setFrom(new InternetAddress(Constant.FROM_MAIL)); //发送方
	       message.addRecipient(Message.RecipientType.TO, new InternetAddress(Constant.TO_MAIL)); //接受方
	       message.saveChanges();  //保存邮件主体对象内容
	       
	       Transport transport = session.getTransport();    //传输对象
	       transport.connect(Constant.SEND_HOST, Constant.FROM_MAIL, Constant.SEND_PWD);  //连接服务器中的邮箱
	       transport.sendMessage(message, message.getAllRecipients());  //发送
	       transport.close();  //关闭传输
	       System.out.println("Successfully  send mail to all user");
	}
	
}
