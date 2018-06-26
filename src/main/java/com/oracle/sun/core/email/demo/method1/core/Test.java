package com.oracle.sun.core.email.demo.method1.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		test1();
		//test2();
		//test3();
	}
	public static void test1() throws Exception {
		MailInfo info=new MailInfo();
		info.setContent("fhalighlih改；hi二个hi后火箭炮；融合进平台；文件黑屏；突然机票；就怕跑prtjho@改；李特户");
		//info.setToHead("标题");
		SendEmail.sendTextMail(info);
		
	}
	public static void test2() throws Exception {
		MailInfo info=new MailInfo();
		info.setContent("<h2 style='color:red;'>fhalighlih改；hi二个hi后火箭炮；融合进平台；文件黑屏；突然机票；就怕跑prtjho@改；李特户</h2>");
		SendEmail.sendHtmlMail(info);
		
	}
	/*
	 * 发送邮件 带附件
	 */
	public static void test3() {
		MailInfo info=new MailInfo();
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		List<File> files=new ArrayList<File>();
		String path="D:/测试文件";
		File fs=new File(path);
		for (File file : fs.listFiles()) {
			if(file.isFile()) {
				files.add(file);
			}
		}
		info.setContent(content);
		info.setFiles(files);
		SendEmail.sendMail(info);
	}
	
	public static void test4() {
	
		MailInfo info=new MailInfo();
		String content="<a href='http://120.27.18.84:9090/index.do'>点击进入</a>";
		info.setContent(content);
		String fileName="temp.html";
		InputStream is=null;
		try {
			is=new FileInputStream(new File("C:\\Users\\admin\\Desktop\\天际旅行网\\app.html"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		SendEmail.sendMail(info,fileName,is);
	}
	
}
