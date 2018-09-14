package com.oracle.sun.core.email.demo.method3.core;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
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

import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;

/***
 * 地址 https://blog.csdn.net/a1786223749/article/details/78753184
* @ClassName: MailUtil
* @Description: 简单形式 一个工具类就可以完成 效果
* @author sun
* @date 2018年5月15日 上午10:57:25
 */
public class MailUtil {

    private String host="smtp.163.com";
    private String user="15314537570@163.com";
    private String password="qwe123asd456";
    private String port="465";

    static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    public MailUtil() {};
    
    public MailUtil(String host, String user, String password, String port){
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
    }

  /**
   * 
  * @Title: sendMail
  * @Description: TODO(这里用一句话描述这个方法的作用)
  * @author sun
  * @date  2018年5月23日 下午4:02:23
  * @param subject
  * @param toMail
  * @param content
  * @param files
  * @param ccList 抄送人  eg 18333615368@163.com
  * @return
   */
  public boolean sendSSLMail(String subject, String toMail, String content,List<File> files,String ccList) {
      boolean isFlag = false;
      try {
    	  Properties props = System.getProperties();
          props.setProperty("mail.smtp.socketFactory.fallback", "true");
          props.setProperty("mail.store.protocol", "smtp");
          props.setProperty("mail.smtp.host", host);
          props.setProperty("mail.smtp.socketFactory.port", port);
          props.setProperty("mail.smtp.port", port);
          props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.put("mail.smtp.auth", "true");
          
          Session session = Session.getDefaultInstance(props);
          session.setDebug(false);

          MimeMessage message = new MimeMessage(session);
          try {
              //指定发送人
              message.setFrom(new InternetAddress(user));  
              //指定接收人
              message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail)); 
              //指定抄送人
              if(ccList!=null || !"".equals(ccList)){
                  message.addRecipients(Message.RecipientType.CC,ccList);
              }
              //设置标题
              message.setSubject(subject);
              message.addHeader("charset", "UTF-8");

              /*添加正文内容*/
              //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
              Multipart multipart = new MimeMultipart();   

              MimeBodyPart contentPart = new MimeBodyPart();
              contentPart.setText(content,"UTF-8");
              contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
              multipart.addBodyPart(contentPart);

              /*添加附件*/
              //List<MimeBodyPart> mbps = new Vector<MimeBodyPart>();
              if (files != null && !files.isEmpty()) {
                for (File f : files) {
                  if (!f.exists())
                    continue;

                  MimeBodyPart mbp = new MimeBodyPart();
                  //FileDataSource fds = new FileDataSource(f.getAbsolutePath());
                  FileDataSource fds = new FileDataSource(f);
                  mbp.setDataHandler(new DataHandler(fds));
                  mbp.setFileName(MimeUtility.encodeText(fds.getName()));
                  //mbps.add(mbp);
                  multipart.addBodyPart(mbp);
                }
              }

              message.setContent(multipart);
              message.setSentDate(new Date());
              message.saveChanges();
              Transport transport = session.getTransport("smtp");             
              transport.connect(host, user, password);
              transport.sendMessage(message, message.getAllRecipients());
              transport.close();
              isFlag = true;
              logger.info(Calendar.getInstance().getTime()+":#Send mail to"+toMail+"success #");
          } catch (Exception e) {
                logger.info(Calendar.getInstance().getTime()+":#Send mail to"+toMail+"error #");
              logger.info(e.toString());
              e.printStackTrace();
              isFlag = false;
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return isFlag;
	}
  
  /**
	 * 线程池 发送邮箱
	 * @Description
	 * 返回类型 boolean
	 * @param subject
	 * @param toMail
	 * @param content
	 * @param files
	 * @param ccList
	 * @return
	 * @注
	 */
	public void sendSSLMailOfThread(String subject, String toMail, String content,List<File> files,String ccList) {
		
		class sendJob implements Runnable {
			private String a="";
			private String t="";
			private String c="";
			private List<File> f=null;
			private String l="";
			protected sendJob(String a, String t, String c,List<File> f,String l){
				this.a=a;
				this.t=t;
				this.c=c;
				this.f=f;
				this.l=l;
			}
			@Override
			public void run() {
				sendSSLMail(a,t,c,f,l);
			}  
		}
		ThreadPoolExecutorFactory.getThreadPoolExecutor().execute(new sendJob(subject, toMail, content,files,ccList));
	}
  	
}
  