package com.oracle.sun.design.email.design.project4;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 
* @ClassName: MailUtil
* @Description: 发成功了 但是太慢了
* @author sun
* @date 2018年5月15日 上午10:57:25
 */
public class MailUtilCopy1 {

    private String host;
    private String user;
    private String password;

    static Logger logger = LoggerFactory.getLogger(MailUtilCopy1.class);

    public MailUtilCopy1(String host, String user, String password){
        this.host = host;
        this.user = user;
        this.password = password;
    }

  public boolean sendMail(String subject, String toMail, String content,List<File> files,String ccList) {
        boolean isFlag = false;
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);   // 指定SMTP服务器
            props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
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
                //整封邮件的MINE消息体
                //MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
                
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
                    multipart.addBodyPart(mbp);//附件加入到 MINE消息体中
                  }
                }

                //设置邮件的MINE消息体
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
  
}
  