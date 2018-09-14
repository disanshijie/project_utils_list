package com.oracle.sun.design.email.design.project5;

public class Test {

	
	public static void main(String[] args) {
		
		test1();
	}
	
	public static void test1() {
		MailUtil mu=new MailUtil();
		String subject="校园通知";
		String toMail="878455649@qq.com";
		String content="<div>我正在做一个邮箱测试<a href='baidu.com'>点击进入</a></div><h2 style='color:red;'></h2>";
		mu.sendSSLMailOfThread(subject, toMail, content, null, "2245864259@qq.com");
	}
}
