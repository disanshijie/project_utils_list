package com.oracle.sun.design.email.design.project2;

public class Test {

	public static void main(String[] args) {
		Test.send();
	}
  public static void send(){
        String mail = "2245864259@qq.com"; //发送对象的邮箱
        String title = "我有一句话跟你说";
        String content = "<div>你不在学校吗？</div><br/><hr/><div>记得28号来学校</div>";
        MailInfo info = new MailInfo();
        info.setToAddress(mail);
        info.setSubject(title);
        info.setContent(content);
        try {
        	//MailSendUtil.sendTextMail(info);
            MailSendUtil.sendHtmlMail(info);
        } catch (Exception e) {
            System.out.print("'"+title+"'的邮件发送失败！");
            e.printStackTrace();
        }
    }
}
