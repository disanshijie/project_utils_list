package com.oracle.sun.core.email.demo.method2.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.oracle.sun.core.email.demo.method2.core.MailUtil;
import com.oracle.sun.core.email.util.Constant;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		test2();
		//MailUtilTest();
		//MailUtilTest2();
	}
	
	
	public static void test2() {
		//MailUtil mu=new MailUtil();
		MailUtil mu=new MailUtil(Constant.SEND_HOST,Constant.FROM_MAIL,Constant.SEND_PWD);
		String subject="校园通知";
		String toMail="878455649@qq.com";
		String content="<div>我正在做一个邮箱测试<a href='baidu.com'>点击进入</a></div><h2 style='color:red;'></h2>";
		mu.sendMail(subject, toMail, content, null, "2245864259@qq.com");
	}
	//测试通过
	public static void MailUtilTest() {
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		String fileName="";
		InputStream is=null;
		String ccList="";
		MailUtil mu=new MailUtil(Constant.SEND_HOST,Constant.FROM_MAIL,Constant.SEND_PWD);
		mu.sendMail("测试", Constant.TO_MAIL, content, fileName, is, ccList);

	}
	//测试通过
	public static void MailUtilTest2() throws FileNotFoundException {
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		String fileName="525FO确认单.xlsx";
		String path="D:/sunjinchao/data/WeChat/WeChat Files/wxid_aj2dr5redjio22/Files/【平安银行】华美欧确认单模板-525FO白立民海三.xlsx";
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		//InputStream in=Test.class.getResourceAsStream("");
		String ccList="";
		MailUtil mu=new MailUtil(Constant.SEND_HOST,Constant.FROM_MAIL,Constant.SEND_PWD);
		mu.sendMail("测试", Constant.TO_MAIL, content, fileName, is, ccList);
		
	}
}
